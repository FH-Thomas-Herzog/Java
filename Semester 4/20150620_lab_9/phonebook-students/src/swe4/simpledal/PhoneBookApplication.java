package swe4.simpledal;

import java.io.*;
import java.sql.*;

public class PhoneBookApplication {
  // private static final String CONNECTION_STRING = "jdbc:derby://localhost/PhoneBookDb";
  private static final String CONNECTION_STRING = "jdbc:mysql://localhost/PhoneBookDb";
  private static final String USER_NAME = "root";
  private static final String PASSWORD = null;
  
  private static String promptFor(BufferedReader in, String p) {
    System.out.print(p + "> ");
 
    try {
      return in.readLine();
    }
    catch (Exception e) {
      return promptFor(in, p);
    } // try/catch

  } // prompt

  private static void printStatistics(Connection connection) throws SQLException {
    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("select count(id) as count from Person")) {
      int count = 0;
      if (resultSet.next()) count = resultSet.getInt(1);
      System.out.println();
      System.out.format("%d entries in phone book.%n", count);
    }
  }

  private static void list(Connection connection) throws SQLException {
    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("select * from Person")) {
      while (resultSet.next()) {
        System.out.format("  (%s): %s %s; %s; %s%n", resultSet.getInt("id"),
            resultSet.getString("first_name"), resultSet.getString("last_name"),
            resultSet.getString("address"), resultSet.getString("phone_number"));
      } // while
    }
  }

  private static void find(PreparedStatement prepFindStmt, String lastName) throws SQLException {

    // Variante 1: Standard SQL-Statement
    // statement = connection.createStatement();
    // resultSet = statement.executeQuery(String.format(
    // "SELECT * FROM Person WHERE LastName LIKE '%s'", lastName));
    // Variante 2: Prepared SQL-Statement:

    prepFindStmt.setString(1, lastName);
    try (ResultSet resultSet = prepFindStmt.executeQuery()) {
      int count = 0;
      while (resultSet.next()) {
        count++;
        System.out.format("  (%s): %s %s; %s; %s%n", resultSet.getInt("ID"),
            resultSet.getString("first_name"), resultSet.getString("last_name"),
            resultSet.getString("address"), resultSet.getString("phone_number"));
      } // while
  
      if (count == 0) 
        System.out.format("  no entries with last name %s found", lastName);
    }
  }

  private static void insert(Connection connection, String firstName, String lastName,
      String address, String phoneNumber) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(String.format(
          "insert into Person (first_name, last_name, address, phone_number) "
              + "values ('%s','%s','%s','%s')", 
              firstName, lastName, address, phoneNumber));
    }
  }

  private static void update(Connection connection, int id, String firstName, String lastName,
      String address, String phoneNumber) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(String.format(
          "update Person set first_name='%s', last_name='%s', address='%s', phone_number='%s' "
              + " where id = %d", firstName, lastName, address, phoneNumber, id));
    }
  }

  private static void delete(Connection connection, int id) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(String.format("delete from Person where id = %d", id));
    }
  }

  private static void meta(Connection connection) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      DatabaseMetaData dbmd = connection.getMetaData();
      System.out.println("Database: " + dbmd.getDatabaseProductName() + ", " +
          dbmd.getDatabaseProductVersion());
      System.out.println("Driver: " + dbmd.getDriverName() + ", " + dbmd.getDriverVersion());
  
      ResultSet resultSet = statement.executeQuery("select * from Person");
      ResultSetMetaData rsmd = resultSet.getMetaData();
      System.out.println("Metainfo for table Person:");
      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
  
        System.out.println("   column " + rsmd.getColumnName(i) + " (" + rsmd.getColumnTypeName(i) +
            ")");
      }
    }
  }

  public static void main(String args[]) {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String allowedCmds = "commands: quit, list, find, insert, update, delete, meta";
    String userCmd;

    Connection connection = null;
    PreparedStatement prepFindStmt = null;

    // In previous versions of JDBC (< JDBC 4.0), to obtain a connection,
    // you first had to initialize your JDBC driver by calling
    // the method Class.forName.
    //
    //    try {
    //      // Class.forName("org.apache.derby.jdbc.ClientDriver");
    //      // Class.forName("com.mysql.jdbc.Driver");
    //    }
    //    catch (Exception e) {
    //      System.out.println(e);
    //      System.exit(0);
    //    }

    try {
      System.out.println("connecting to " + CONNECTION_STRING + " ... ");
      
      long startTime = System.nanoTime();
      connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
      long endTime = System.nanoTime();
      
      System.out.printf("time(DriverManager.getConnection)=%.6f%n", (endTime-startTime)/1e9);

      printStatistics(connection);

      System.out.println();
      System.out.println(allowedCmds);
      System.out.println();

      userCmd = promptFor(in, "");

      while (!userCmd.equals("quit")) {

        if (userCmd.equals("list")) {
          list(connection);
        }
        else if (userCmd.equals("find")) {
          if (prepFindStmt == null) {
            prepFindStmt =
                connection.prepareStatement("select * from Person where last_name like ?");
          }

          String lastName = promptFor(in, "  last name ");
          find(prepFindStmt, lastName);
        }
        else if (userCmd.equals("insert")) {
          String firstName = promptFor(in, "  first name   ");
          String lastName = promptFor(in, "  last name    ");
          String address = promptFor(in, "  address      ");
          String phoneNumber = promptFor(in, "  phone number ");

          insert(connection, firstName, lastName, address, phoneNumber);
      	} 
        else if (userCmd.equals("update")) {
          int id = Integer.parseInt(promptFor(in, "  ID           "));
          String firstName = promptFor(in, "  first name   ");
          String lastName = promptFor(in, "  last name    ");
          String address = promptFor(in, "  address      ");
          String phoneNumber = promptFor(in, "  phone number ");

          update(connection, id, firstName, lastName, address, phoneNumber);
      	}
        else if (userCmd.equals("delete")) {
          int id = Integer.parseInt(promptFor(in, "  ID "));
          delete(connection, id);
        }
        else if (userCmd.equals("meta")) {
          meta(connection);
        }
        else {
          System.out.println("ERROR: invalid command; " + allowedCmds);
        } // else

        userCmd = promptFor(in, "");
      } // while

    }
    catch (SQLException e) {

      while (e != null) {
        System.out.println(e);
        e = e.getNextException();
      }
    } // catch
    finally {
      try {
        System.out.println();
        System.out.format("closing connection to %s ...%n", CONNECTION_STRING);
        if (connection != null)
          connection.close();
      }
      catch (SQLException e) {
        System.out.println(e);
      }
    } // finally
  } // main
} // PhoneBookApplication
