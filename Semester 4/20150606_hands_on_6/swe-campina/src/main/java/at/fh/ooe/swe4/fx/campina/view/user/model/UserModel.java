package at.fh.ooe.swe4.fx.campina.view.user.model;

import java.util.Calendar;

import at.fh.ooe.swe4.fx.campina.view.annotation.FormField;
import at.fh.ooe.swe4.fx.campina.view.constants.FormFieldType;
import at.fh.ooe.swe4.fx.campina.view.model.AbstractModel;

public class UserModel extends AbstractModel {

	private String		firstName;
	private String		lastName;
	private String		username;
	private String		email;
	private Calendar	birthdate;

	public UserModel() {
	}

	public UserModel(String id) {
		super(id);
	}

	@FormField(
			id = "user-first-name",
			label = "Vorname",
			required = true,
			ordinal = 1,
			requiredMessage = "Bitte Vornamen angeben",
			type = FormFieldType.INPUT_TEXT)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@FormField(
			id = "user-last-name",
			label = "Nachname",
			required = true,
			ordinal = 2,
			requiredMessage = "Bitte Nachnamen angeben",
			type = FormFieldType.INPUT_TEXT)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@FormField(
			id = "user-username",
			label = "Benutzernamen",
			required = true,
			ordinal = 5,
			requiredMessage = "Bitte Benutzernamen angeben",
			type = FormFieldType.INPUT_TEXT)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@FormField(
			id = "user-birthdate",
			label = "Geburtsdatum",
			required = true,
			ordinal = 3,
			requiredMessage = "Bitte Geburtsnamen angeben",
			type = FormFieldType.DATE_PICKER)
	public Calendar getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Calendar birthdate) {
		this.birthdate = birthdate;
	}

	@FormField(
			id = "user-email",
			label = "Email",
			required = true,
			ordinal = 4,
			requiredMessage = "Bitte Email angeben",
			type = FormFieldType.INPUT_TEXT)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
