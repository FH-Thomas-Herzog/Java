package at.fh.ooe.swe4.fx.campina.jpa;

public class LoginEvent extends AbstractEntity<Integer> {

	private static final long	serialVersionUID	= 1782498743658504195L;

	public LoginEvent() {
		this(null);
	}

	public LoginEvent(Integer id) {
		super(id);
	}

	@Override
	public Integer getId() {
		return _getId();
	}
}
