package swe4.dal;

public class Person {
  // fields
  // ------
  private int    id;         // -1: object not stored yet
  private String firstName;
  private String lastName;
  private String address;
  private String phoneNumber;

  /**
   * private constructor: needs an id
   */
  public Person(int i, String fn, String ln, String a, String pn) {
    id = i;
    firstName = fn;
    lastName = ln;
    address = a;
    phoneNumber = pn;
  } // Person

  /**
   * public constructor: needs no id
   */
  public Person(String fn, String ln, String a, String pn) {
    this(-1, fn, ln, a, pn);
  } // Person

  /**
   * stores new object into data base
   */
  public int getId() {
    return id;
  } // getId

  public void setId(int id) {
    this.id = id;
  } // setId

  public String getFirstName() {
    return firstName;
  } // getFirstName

  public void setFirstName(String fn) {
    firstName = fn;
  } // setFirstName

  public String getLastName() {
    return lastName;
  } // getLastName

  public void setLastName(String ln) {
    lastName = ln;
  } // setLastName

  public String getAddress() {
    return address;
  } // getAddress

  public void setAddress(String a) {
    address = a;
  } // setAddress

  public String getPhoneNumber() {
    return phoneNumber;
  } // getPhoneNumber

  public void setPhoneNumber(String pn) {
    phoneNumber = pn;
  } // setPhoneNumber

  public String toString() {
    return String.format("(%s) %s %s; %s; %s", id, firstName, lastName, address, phoneNumber);
  } // toString
}
