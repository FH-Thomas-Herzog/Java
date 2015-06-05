package at.fh.ooe.swe4.campina.jpa.api;

import java.io.Serializable;

public abstract class AbstractEntity<I extends Serializable> implements Serializable {

	private static final long	serialVersionUID	= 1095329951571671581L;

	private I					id;

	public AbstractEntity() {
	}

	public AbstractEntity(I id) {
		super();
		this.id = id;
	}

	public abstract I getId();

	protected I _getId() {
		return id;
	}

	public void setId(I id) {
		this.id = id;
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
		AbstractEntity<I> other = (AbstractEntity<I>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
