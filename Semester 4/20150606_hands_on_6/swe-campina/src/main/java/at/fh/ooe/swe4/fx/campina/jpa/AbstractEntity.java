package at.fh.ooe.swe4.fx.campina.jpa;

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

}
