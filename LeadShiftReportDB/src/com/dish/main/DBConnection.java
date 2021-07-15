package com.dish.main;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The DBConnection class handles the connection to the database, adding or
 * updating rows, committing the data to the database
 */

public class DBConnection {
	private Connection con = null;
	private Statement statement = null;

	/**
	 * Sole constructor.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public DBConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver"); // load the driver for the SQL database
		this.connectToDatabase();
	}

	/**
	 * Connects to the database with the default user
	 * 
	 * @throws SQLException
	 */
	public void connectToDatabase() throws SQLException {
		this.con = DriverManager.getConnection("jdbc:hsqldb:file:.\\src\\database\\database.db", "SA", "");
		con.setAutoCommit(false);
		this.statement = con.createStatement();
	}

	/**
	 * Updates an existing row in the database
	 * 
	 * @param tableName the name of the table to add the row to
	 * 
	 * @param columns   an array list of columns to add data to
	 * 
	 * @param values    an array list of values to add to the columns
	 * @throws SQLException
	 * 
	 */
	public void addComment(String tableName, String comment)
			throws SQLException {
		String shift = Code.getShiftText();
		String query = "INSERT INTO " + tableName + " VALUES (current_date, '" + shift + "', ?) ON DUPLICATE KEY UPDATE comments = ?";
		PreparedStatement pstate = con.prepareStatement(query);
		pstate.setString(1, comment);
		pstate.setString(2, comment);
		System.out.println(pstate);
		pstate.execute();
		pstate.close();

//			statement.executeQuery("INSERT INTO " + tableName + " (" + columnList + ") VALUES (current_date, " + shift + ", " + values + ")"
//					+ "ON DUPLICATE KEY UPDATE "
//					+ "comments = " + values + ";");

	}

	/**
	 * Selects a row from the specified table for display in the passdown report
	 * 
	 * @param tableName the name of the table to select from
	 * 
	 * @param columnOne the specific column to select from
	 * 
	 * @param columnTwo the column used as a constraint
	 * 
	 * @param condition the condition following the where clause of the SQL command
	 * 
	 * @return the values from the columns selected
	 * @throws SQLException
	 */
	public String selectCommentFromDatabase(String tableName)
			throws SQLException {
		ResultSet rs;
		String shift = Code.getShiftText();
		String checkDate = tableName + "_date";
		String checkShift = tableName + "_shift";
		System.out.println(checkDate + "   " + checkShift);
		// Prepare the statement to select the comment column from different tables based on date and shift
		String query = "SELECT * FROM " + tableName + " WHERE " + checkDate + " = current_date AND " + checkShift + " = (CAST(? AS VARCHAR(20)))";
		PreparedStatement pstate = con.prepareStatement(query);
		pstate.setString(1, shift);
		// Execute the prepared statement
		rs = pstate.executeQuery();
		System.out.println(pstate);
		// Check the result set for the comment returned
		String value = "";
		while(rs.next()) {
			value = rs.getString("comments");
		}
		pstate.close();
		return value;

	}

	/**
	 * Closes the database connection
	 * 
	 * @throws SQLException
	 */
	public void closeDatabaseConnection() throws SQLException {
//		this.resultSet.close();
//		this.statement.close();
		this.con.close();
	}

	/**
	 * Commits the current data to the database file
	 * 
	 * @throws SQLException
	 */
	public void commitDatabase() throws SQLException {
		con.commit();
	}

	/**
	 * Adds new rows to each table to initialize them in the database.
	 * 
	 * @throws SQLException
	 * 
	 */
	public void initiliazeRows(String shift) throws SQLException {
		ResultSet rs = null;
		rs = statement.executeQuery(
				"SELECT * FROM maintenance WHERE maintenance_date = current_date AND maintenance_shift = " + shift);
		if (!rs.next()) {
			String[] tableList = { "maintenance", "ongoing_outages", "requests", "special_monitoring", "takedowns",
					"equipment" };
			for (String i : tableList) {
				statement.executeQuery("INSERT INTO " + i + " VALUES current_date, " + shift + ", ''");
			}
		}
	}
	

	public List getEmployees(String shift) throws SQLException {
		ResultSet rs = null;
		List employees = new List();
		String query = "SELECT * FROM employees WHERE employees_shift = (CAST(? AS VARCHAR(20)))";
		PreparedStatement pstate = con.prepareStatement(query);
		pstate.setString(1, shift);
		rs = pstate.executeQuery();
		System.out.println(pstate);
		while(rs.next()) {
			employees.add(rs.getString("Name"));
		}
		return employees;
		
	}

}
