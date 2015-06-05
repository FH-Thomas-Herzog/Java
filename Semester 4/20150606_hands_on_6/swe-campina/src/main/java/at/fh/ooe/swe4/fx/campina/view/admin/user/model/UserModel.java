package at.fh.ooe.swe4.fx.campina.view.admin.user.model;

import java.util.Objects;

import at.fh.ooe.swe4.fx.campina.jpa.User;
import at.fh.ooe.swe4.fx.campina.view.annotation.FormField;
import at.fh.ooe.swe4.fx.campina.view.api.AbstractViewModel;
import at.fh.ooe.swe4.fx.campina.view.form.FormUtils.FormFieldType;

/**
 * The view model which backs the {@link User} entity.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class UserModel extends AbstractViewModel<Integer, User> {

	/**
	 * 
	 */
	public UserModel() {
		super();
	}

	/**
	 * @param user
	 */
	public UserModel(User user) {
		super(user);
	}

	@Override
	public void prepare(User user) {
		Objects.requireNonNull(user);

		setId(user.getId());
		setEntity(user);
	}

	@Override
	public void reset() {
		prepare(new User());
	}

	public String getSelectionName() {
		if (getId() == null) {
			return "Neuer Benutzer";
		} else {
			return new StringBuilder().append(getLastName())
										.append(", ")
										.append(getFirstName())
										.append(" (")
										.append(getEmail())
										.append(")")
										.toString();
		}
	}

	@FormField(
			id = "user-first-name",
			label = "Vorname",
			required = true,
			ordinal = 1,
			requiredMessage = "Bitte Vornamen angeben",
			type = FormFieldType.INPUT_TEXT)
	public String getFirstName() {
		return getEntity().getFirstName();
	}

	public void setFirstName(String firstName) {
		getEntity().setFirstName(firstName);
		;
	}

	@FormField(
			id = "user-last-name",
			label = "Nachname",
			required = true,
			ordinal = 2,
			requiredMessage = "Bitte Nachnamen angeben",
			type = FormFieldType.INPUT_TEXT)
	public String getLastName() {
		return getEntity().getLastName();
	}

	public void setLastName(String lastName) {
		getEntity().setLastName(lastName);
		;
	}

	@FormField(
			id = "user-email",
			label = "E-Mail",
			ordinal = 3,
			type = FormFieldType.INPUT_TEXT)
	public String getEmail() {
		return getEntity().getEmail();
	}

	public void setEmail(String email) {
		getEntity().setEmail(email);
	}

	@FormField(
			id = "user-username",
			label = "Benutzername",
			required = true,
			ordinal = 4,
			requiredMessage = "Bitte Benutzernamen angeben",
			type = FormFieldType.INPUT_TEXT)
	public String getUsername() {
		return getEntity().getUsername();
	}

	public void setUsername(String username) {
		getEntity().setUsername(username);
	}

	@FormField(
			id = "user-password",
			label = "Password",
			required = true,
			ordinal = 5,
			requiredMessage = "Bitte Password angeben",
			type = FormFieldType.INPUT_TEXT)
	public String getPassword() {
		return getEntity().getPassword();
	}

	public void setPassword(String password) {
		getEntity().setPassword(password);
	}

}
