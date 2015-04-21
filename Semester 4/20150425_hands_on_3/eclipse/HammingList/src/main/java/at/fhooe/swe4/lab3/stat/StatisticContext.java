package at.fhooe.swe4.lab3.stat;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * This class represents a statistic context which is used for statistic code
 * analysis.
 * 
 * @author Thomas Herzog
 *
 */
public class StatisticContext {
	private final String key;
	public Calendar startCalendar;
	public Calendar endCalendar;
	public final SortedSet<CodeStatistics> statisticsSet;

	/**
	 * Default constructor which creates a context identified by the given
	 * unique key.
	 * 
	 * @param key
	 *            the unique key for the created context
	 * @throws IllegalArgumentException
	 *             if the key is either null or an empty string
	 */
	public StatisticContext(final String key) {
		super();
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException("StatisticContext instance must be identified by an unique string key");
		}
		this.key = key;
		this.statisticsSet = new TreeSet<CodeStatistics>(new Comparator<CodeStatistics>() {
			@Override
			public int compare(CodeStatistics left, CodeStatistics right) {
				return left.getKey().compareTo(right.getKey());
			}
		});
	}

	/**
	 * Gets the start calendar instance
	 * 
	 * @return the start calendar
	 */
	public Calendar getStartCalendar() {
		return startCalendar;
	}

	/**
	 * Sets the start calendar instance
	 * 
	 * @param startCalendar
	 *            the start calendar instance
	 */
	public void setStartCalendar(Calendar startCalendar) {
		this.startCalendar = startCalendar;
	}

	/**
	 * Gets the end calendar instance
	 * 
	 * @return the end calendar instance
	 */
	public Calendar getEndCalendar() {
		return endCalendar;
	}

	/**
	 * Sets the end calendar instance
	 * 
	 * @param endCalendar
	 *            the end calendar instance
	 */
	public void setEndCalendar(Calendar endCalendar) {
		this.endCalendar = endCalendar;
	}

	/**
	 * Gets the statistic set which contains the code statistics of the current
	 * context.
	 * 
	 * @return the code statistics of the current context
	 */
	public Set<CodeStatistics> getStatisticsSet() {
		return Collections.unmodifiableSet(statisticsSet);
	}

	/**
	 * Adds a code statistic instance to the backed set.
	 * 
	 * @param statistics
	 *            the code statistics to be added to the set
	 * @return the current instance
	 */
	public StatisticContext addStatistics(final CodeStatistics statistics) {
		statisticsSet.add(statistics);
		return this;
	}

	/**
	 * @param key
	 *            the key of the code statistic instance.
	 * @return the code statistic instance, null otherwise
	 * @see StatisticContext#byKey(String, boolean)
	 */
	public CodeStatistics byKey(final String key) {
		return byKey(key, Boolean.FALSE);
	}

	/**
	 * Gets a code statistics identified by the given key.
	 * 
	 * @param statKey
	 *            the key of the code statistic instance.
	 * @param newIfNot
	 *            true if a new instance should be created if not found in the
	 *            backed set
	 * @return the code statistic instance, null otherwise
	 */
	public CodeStatistics byKey(final String statKey, boolean newIfNot) {
		CodeStatistics stat = CollectionUtils.find(statisticsSet, new Predicate<CodeStatistics>() {
			@Override
			public boolean evaluate(CodeStatistics object) {
				return object.getKey().equals(statKey.trim().toLowerCase());
			}
		});
		return (stat != null) ? stat : newStatistic(statKey);
	}

	/**
	 * Creates a new code statistic instance
	 * 
	 * @param key
	 *            the key for the code statistic instance
	 * @return the new code statistic instance
	 */
	public CodeStatistics newStatistic(final String key) {
		final CodeStatistics stat = new CodeStatistics(key);
		statisticsSet.add(stat);
		return stat;
	}

	/**
	 * @return the formatted start calendar string representation
	 * @see StatisticContext#formatDate(Calendar)
	 */
	public String formatedStartDate() {
		return formatDate(startCalendar);
	}

	/**
	 * @return the formatted end calendar string representation
	 * @see StatisticContext#formatDate(Calendar)
	 */
	public String formatedEndDate() {
		return formatDate(endCalendar);
	}

	/**
	 * Creates a string representation of the given calendar instance
	 * 
	 * @param cal
	 *            the calendar instance to be formatted
	 * @return the formatted calendar string
	 */
	private String formatDate(final Calendar cal) {
		return DateFormatUtils.format(cal, "HH:mm:ss:SSS");
	}

	/**
	 * @return the key of this statistic context.
	 */
	public String getKey() {
		return key;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatisticContext other = (StatisticContext) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	/**
	 * Prints the statistic context and its code statistics
	 */
	@Override
	public String toString() {
		final String ln = System.getProperty("line.separator");
		final StringBuilder sb = new StringBuilder(500);
		sb.append("#################################################################################").append(ln);
		sb.append("## statistic-context-key: ").append(key).append(ln);
		sb.append("## start-date: ").append(formatedStartDate()).append(ln);
		sb.append("## end-date: ").append(formatedEndDate()).append(ln);
		sb.append("#################################################################################").append(ln);
		for (CodeStatistics statistics : statisticsSet) {
			sb.append(statistics.toString()).append(ln);
			sb.append("#################################################################################").append(ln);
		}
		return sb.toString();
	}
}
