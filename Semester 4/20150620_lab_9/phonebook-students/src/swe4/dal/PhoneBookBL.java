package swe4.dal;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import swe4.dal.Person;
import swe4.dal.PersonDao;
import swe4.dal.PersonDaoJdbc;

public class PhoneBookBL {
  // private static final String CONNECTION_STRING =
  // "jdbc:derby://localhost/PhoneBookDb";
  private static final String CONNECTION_STRING = "jdbc:mysql://localhost/PhoneBookDb";
  private static final String USER_NAME = "root";
  private static final String PASSWORD = null;
  

  public static String promptFor(BufferedReader in, String p) {
    System.out.print(p + "> ");
    try {
      return in.readLine();
    }
    catch (Exception e) {
      return promptFor(in, p);
    } // try/catch
  } // prompt

  public static void main(String[] args) {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));;
    String userCmd;

    try 
    {

      System.out.println();
  
      System.out.println();
      System.out.println("valid commands: quit, list, find, insert, update and delete");
      System.out.println();
  
      userCmd = promptFor(in, "");
      while (!userCmd.equals("quit")) {
  
        if (userCmd.equals("list")) {
  
  
        }
        else if (userCmd.equals("find")) {
  
          String lastName = promptFor(in, "  last name ");
  
        }
        else if (userCmd.equals("insert")) {
  
          Person p =
              new Person(promptFor(in, "  first name   "), promptFor(in, "  last name    "),
                  promptFor(in, "  address      "), promptFor(in, "  phone number "));
        }
        else if (userCmd.equals("update")) {
  
          int id = Integer.parseInt(promptFor(in, "  id "));
  
        }
        else if (userCmd.equals("delete")) {
  
          int id = Integer.parseInt(promptFor(in, "  id "));
  
        }
        else {
          System.out.println("ERROR: invalid command");
        } // else
  
        userCmd = promptFor(in, "");
  
      } // while  
      System.out.println();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  } // main

} // PhoneBookApplicationDAL

