package at.fh.ooe.swe4.fx.campina.jpa;

import java.io.Serializable;

public abstract class AbstractEntity<I extends Serializable> implements Serializable {

	private I	id;

	public AbstractEntity() {
		// TODO Auto-generated constructor stub
	}

	public I _getId() {
		return id;
	}

	public void setId(I id) {
		this.id = id;
	}

}
