package at.fh.ooe.swe4.fx.campina.view.model;

import java.io.Serializable;
import java.util.Objects;

import at.fh.ooe.swe4.fx.campina.jpa.AbstractEntity;
import at.fh.ooe.swe4.fx.campina.jpa.MenuEntry;
import at.fh.ooe.swe4.fx.campina.jpa.User;

public abstract class AbstractModel<I extends Serializable, T extends AbstractEntity<I>> {

	private I	id;
	private T	entity;

	public AbstractModel() {
		super();
		reset();
	}

	public AbstractModel(final T entity) {
		prepare(entity);
	}

	public abstract void reset();

	public void prepare(T entity) {
		Objects.requireNonNull(entity);

		setEntity(entity);
		setId(entity.getId());
	}

	public I getId() {
		return id;
	}

	public void setId(I id) {
		this.id = id;
	}

	public T getEntity() {
		return entity;
	}

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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractModel<I, T> other = (AbstractModel<I, T>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
