package at.fh.ooe.swe4.campina.fx.view.api;

import java.io.Serializable;
import java.util.Objects;

import at.fh.ooe.swe4.campina.persistence.api.AbstractEntity;

/**
 * This model is the base for all entity amanging models.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 * @param <I>
 *            the type of the entity id used in this model
 * @param <T>
 *            the entity type the current instance is managing
 */
public abstract class AbstractViewModel<I extends Serializable, T extends AbstractEntity<I>> {

	private I	id;
	private T	entity;

	/**
	 * Initially calls reset()
	 * 
	 * @see AbstractViewModel#reset()
	 */
	public AbstractViewModel() {
		super();
		reset();
	}

	/**
	 * Initializes this instance with the given entity
	 * 
	 * @param entity
	 *            the entity this models backs
	 * @throws NullPointerException
	 *             if the given entity is null
	 */
	public AbstractViewModel(final T entity) {
		prepare(entity);
	}

	/**
	 * Resets this model by setting a new entity.<br>
	 * This method shall call prepare right away so that this model is correctly
	 * initialized.
	 */
	public abstract void reset();

	/**
	 * Prepares this model with the given entity
	 * 
	 * @param entity
	 *            the entity this model backs.
	 */
	public void prepare(T entity) {
		Objects.requireNonNull(entity);

		setEntity(entity);
		setId(entity.getId());
	}

	/**
	 * The model id retrieved from backed entity
	 * 
	 * @return the model id
	 */
	public I getId() {
		return id;
	}

	/**
	 * Sets the model id, shall be equal to backed entity id.
	 */
	public void setId(I id) {
		this.id = id;
	}

	/**
	 * Gets the backed entity
	 * 
	 * @return the backed entity
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * Sets the backed entity
	 * 
	 * @param entity
	 *            the entity this model backs
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractViewModel<I, T> other = (AbstractViewModel<I, T>) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
