package at.fhooe.swe4.lab3.stat;

import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;

/**
 * This is the default statistics provider implementation.
 * 
 * @author Thomas Herzog
 *
 */
public class DefaultStatisticsProviderImpl implements StatisticsProvider {

	private StatisticContext ctx = null;
	private final Map<String, StatisticsProvider> providers;
	private final SortedSet<StatisticContext> statContextSet = new TreeSet<StatisticContext>(new Comparator<StatisticContext>() {
		@Override
		public int compare(StatisticContext left, StatisticContext right) {
			return left.getStartCalendar().compareTo(right.getStartCalendar());
		}
	});

	/**
	 * Default constructor which creates an context with the given key
	 * 
	 * @param contextKey
	 *            the key for the initial context
	 */
	public DefaultStatisticsProviderImpl() {
		super();
		this.providers = new HashMap<String, StatisticsProvider>();
	}

	@Override
	public StatisticsProvider initContext(final String contextKey) {
		if (ctx != null) {
			endContext();
		}
		if (StringUtils.isEmpty(contextKey)) {
			throw new IllegalArgumentException("Context key must be given fopr context");
		}
		ctx = new StatisticContext(contextKey.trim().toLowerCase());
		ctx.setStartCalendar(Calendar.getInstance());
		statContextSet.add(ctx);
		return this;
	}

	@Override
	public StatisticsProvider endContext() {
		if (ctx != null) {
			ctx.setEndCalendar(Calendar.getInstance());
			ctx = null;
		}
		return this;
	}

	@Override
	public StatisticsProvider removeContext(final String ctxDelKey) {
		final StatisticContext ctxDel = CollectionUtils.find(statContextSet, new Predicate<StatisticContext>() {
			@Override
			public boolean evaluate(StatisticContext object) {
				return object.getKey().equals(ctxDelKey.trim().toLowerCase());
			}
		});
		if (ctxDel != null) {
			statContextSet.remove(ctxDel);
		}
		return this;
	}

	@Override
	public StatisticsProvider takeOver(final String key, final StatisticsProvider provider) {
		if (StringUtils.isEmpty(key) || (provider == null)) {
			throw new IllegalArgumentException("Key and provider must be given");
		}
		this.providers.put(key, provider);
		return this;
	}

	@Override
	public StatisticContext getCtx() {
		return ctx;
	}

	@Override
	public String toString() {
		final String ln = System.getProperty("line.separator");
		final StringBuilder sb = new StringBuilder(500);
		sb.append("#################################################################################").append(ln);
		sb.append("## statistic-context-ptovider").append(ln).append("##").append(ln);
		sb.append("## statistics-contexts-count:").append(statContextSet.size()).append(ln).append("##").append(ln);
		for (StatisticContext ctx : statContextSet) {
			sb.append(ctx.toString()).append(ln);
		}
		// Other providers
		if (!providers.isEmpty()) {
			sb.append("Managed providers:").append(ln);
			for (Entry<String, StatisticsProvider> entry : providers.entrySet()) {
				sb.append("Key:").append(entry.getKey()).append(ln);
				sb.append(entry.getValue().toString()).append(ln);
			}
			sb.append("#################################################################################").append(ln);
		}

		return sb.toString();
	}
}
