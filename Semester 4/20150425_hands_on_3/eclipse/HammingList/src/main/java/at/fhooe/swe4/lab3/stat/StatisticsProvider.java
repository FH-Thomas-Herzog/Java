package at.fhooe.swe4.lab3.stat;

/**
 * This interfaces specifies the functionalities of an statistics provider
 * instance.
 * 
 * @author Thomas Herzog
 *
 */
public interface StatisticsProvider {

	/**
	 * Initializes a new context where code statistics are placed
	 * 
	 * @param key
	 *            the key of the context. If present in backed set then existing
	 *            statistics will be lost
	 * @return the current instance
	 */
	public StatisticsProvider initContext(String key);

	/**
	 * Ends the current context by setting its end date and by setting the
	 * current context null.
	 * 
	 * @return the current instance
	 */
	public StatisticsProvider endContext();

	/**
	 * Removes an existing context. Does nothing if key not found.
	 * 
	 * @param key
	 *            the key of the context to be removed
	 * @return the current instance
	 */
	public StatisticsProvider removeContext(String key);

	/**
	 * Takes and statistic provider. If a provider is already present with the
	 * given key then the existing provider will be lost.
	 * 
	 * @param key
	 *            the key to map this provider to
	 * @param provider
	 *            the provider to be taken over
	 * @return the current instance
	 */
	public StatisticsProvider takeOver(String key, StatisticsProvider provider);

	/**
	 * Gets the current active {@link StatisticContext}.
	 * 
	 * @return the current active statistics context, can be null
	 */
	public StatisticContext getCtx();
}
