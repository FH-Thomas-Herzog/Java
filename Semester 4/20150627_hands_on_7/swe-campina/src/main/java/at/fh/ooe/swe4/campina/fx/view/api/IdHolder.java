package at.fh.ooe.swe4.campina.fx.view.api;

/**
 * Marks an id holder.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 3, 2015
 * @param <T>
 *            the type of the hold id
 */
public interface IdHolder<T> {

	/**
	 * Teh id of this id holder
	 * 
	 * @return the id
	 */
	public T getId();

}
