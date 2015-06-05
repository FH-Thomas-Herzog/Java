package at.fh.ooe.swe4.campina.fx.view.admin.login.model;

import java.util.Objects;

import at.fh.ooe.swe4.campina.fx.view.annotation.FormField;
import at.fh.ooe.swe4.campina.fx.view.api.AbstractViewModel;
import at.fh.ooe.swe4.campina.fx.view.form.FormUtils.FormFieldType;
import at.fh.ooe.swe4.campina.jpa.LoginEvent;

/**
 * The model backing the {@link LoginEvent} entity.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class LoginModel extends AbstractViewModel<Integer, LoginEvent> {

	private Integer				counter							= 0;
	private String				username						= "hello";
	private String				password						= "hello";

	public static final Integer	MAX_COUNTER						= Integer.valueOf(5);
	public static final String	LOGIN_GREETING_MESSAGE_PATTERN	= "Sie haben %d Versuche um sich einzuloggen";
	public static final String	LOGIN_FAILED_MESSAGE_PATTERN	= "Login ungültig !!! %d Versuche verbleiben";
	public static final String	LOGIN_BLOCKED_MESSAGE			= "Zu viele Fehlversuche !!! Zugang gesperrt";

	@Override
	public void reset() {
		prepare(new LoginEvent());
	}

	@Override
	public void prepare(LoginEvent loginEvent) {
		Objects.requireNonNull(loginEvent);

		setEntity(loginEvent);
		setId(loginEvent.getId());
	}

	public void increaseCounter() {
		counter++;
	}

	@FormField(
			id = "login-username",
			label = "Benutzernamen",
			ordinal = 1,
			required = true,
			requiredMessage = "Bitte Benutzernamen angeben",
			type = FormFieldType.INPUT_TEXT)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@FormField(
			id = "login-password",
			label = "Passwort",
			ordinal = 1,
			required = true,
			requiredMessage = "Bitte Passwort angeben",
			type = FormFieldType.INPUT_TEXT)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getCounter() {
		return counter;
	}

}
