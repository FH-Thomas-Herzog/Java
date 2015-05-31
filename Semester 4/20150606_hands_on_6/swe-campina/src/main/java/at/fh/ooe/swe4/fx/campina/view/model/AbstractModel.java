package at.fh.ooe.swe4.fx.campina.view.model;

public abstract class AbstractModel {

	private String	id;

	public AbstractModel() {
		super();
	}

	public AbstractModel(final String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
