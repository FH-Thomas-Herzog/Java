package at.fhooe.swe4.lab3.stat;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

public class StatisticContext {
	private final String key;
	public Calendar startCalendar;
	public Calendar endCalendar;
	public final SortedSet<CodeStatistics> statisticsSet;

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

	public Calendar getStartCalendar() {
		return startCalendar;
	}

	public void setStartCalendar(Calendar startCalendar) {
		this.startCalendar = startCalendar;
	}

	public Calendar getEndCalendar() {
		return endCalendar;
	}

	public void setEndCalendar(Calendar endCalendar) {
		this.endCalendar = endCalendar;
	}

	public Set<CodeStatistics> getStatisticsSet() {
		return Collections.unmodifiableSet(statisticsSet);
	}

	public StatisticContext addStatistics(final CodeStatistics statistics) {
		statisticsSet.add(statistics);
		return this;
	}

	public CodeStatistics byKey(final String key) {
		return byKey(key, Boolean.FALSE);
	}

	public CodeStatistics byKey(final String key, boolean newIfNot) {
		CodeStatistics stat = CollectionUtils.find(statisticsSet, new Predicate<CodeStatistics>() {
			@Override
			public boolean evaluate(CodeStatistics object) {
				return object.getKey().equals(key);
			}
		});
		return (stat != null) ? stat : newStatistic(key);
	}

	public CodeStatistics newStatistic(final String key) {
		final CodeStatistics stat = new CodeStatistics(key);
		statisticsSet.add(stat);
		return stat;
	}

	public String formatedStartDate() {
		return formatDate(startCalendar);
	}

	public String formatedEndDate() {
		return formatDate(endCalendar);
	}

	private String formatDate(final Calendar cal) {
		return DateFormatUtils.format(cal, "HH:mm:ss:SSS");
	}

	public String getKey() {
		return key;
	}

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
