
//Zane Gray
//November 14 2019
//individual project question 5
//cs 4513 001 fall 2019
import java.io.*;
import java.text.*;
import java.lang.String.*;
import java.util.*;
import java.sql.*;
import java.util.*;
import microsoft.sql.*;
import java.sql.*;

//boilerplate class shamelessly taken from the example files.
public class q5 {
	static Scanner in;
	static String hostName;
	static String dbName;
	static String user;
	static String password;
	static String url;
	static Connection connection;
	static String schema;
	static Statement statement;

	public static void main(final String[] args) throws SQLException {
		in = new Scanner(System.in);
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (final ClassNotFoundException e) {
			System.out.println("Sql Server driver not found");
			e.printStackTrace();
			System.out.println(e);
			return;
		}
		// Connect to database
		hostName = "127.0.0.1";
		dbName = "master";
		user = "sa";
		password = "Jbermine41611";
		url = "jdbc:sqlserver://localhost:1433;user=sa;password=Jbermine41611";
		connection = DriverManager.getConnection(url);
		schema = connection.getSchema();
		statement = connection.createStatement();
		// main menu
		do {
			switch (iterateMenu()) {
			case 1:
				choiceOne();
				break;// new customer
			case 2:
				choiceTwo();
				break;// new dept
			case 3:
				choiceThree();
				break;// new assembly
			case 4:
				choiceFour();
				break;// new process
			case 5:
				choiceFive();
				break;// new account
			case 6:
				choiceSix();
				break;// new job
			case 7:
				choiceSeven();
				break;// update job
			case 8:
				choiceEight();
				break;// new transaction
			case 9:
				choiceNine();
				break;// get cost of assembly
			case 10:
				choiceTen();
				break;// get labor time by department and date
			case 11:
				choiceEleven();
				break;// get complete processes by assembly
			case 12:
				choiceTwelve();
				break;// get complete jobs by date and department
			case 13:
				choiceThirteen();
				break;// get customers by category
			case 14:
				choiceFourteen();
				break;// delete cut-jobs by range
			case 15:
				choiceFifteen();
				break;// change paint-job number
			case 16:
				choiceSixteen();
				break;// load customers from file
			case 17:
				choiceSeventeen();
				break;// save customers to file
			case 18:
				return;
			default:
				System.out.println("Invalid choice");
			}
		} while (true);
	}

	// Present the user with the main menu and get their selection
	public static int iterateMenu() {
		System.out.println("WELCOME TO THE JOB-SHOP ACCOUNTING DATABASE SYSTEM");
		System.out.println("(1) Enter a new customer");
		System.out.println("(2) Enter a new department");
		System.out.println(
				"(3) Enter a new assembly with its customer-name,assembly-details, assembly-id and date-ordered");
		System.out.println(
				"(4) Enter a new process-id and its department together with its type and information relevant to the type");
		System.out.println(
				"(5) Create a new account and associate it with the process, assembly, or department to which it is applicable");
		System.out
				.println("(6) Enter a new job, given its job-no, assembly-id, process-id, and date the job commenced");
		System.out.println(
				"(7) At the completion of a job, enter the date it completed and the information relevant to the type of job");
		System.out.println(
				"(8) Enter a transaction-no and its sup-cost and update all the costs (details) of the affected accounts by adding sup-cost to their current values of details");
		System.out.println("(9) Retrieve the cost incurred on an assembly-id");
		System.out.println(
				"(10) Retrieve the total labor time within a department for jobs completed in the department during a given date");
		System.out.println(
				"(11) Retrieve the processes through which a given assembly-id has passed so far (in datecommenced order) and the department responsible for each process");
		System.out.println(
				"(12) Retrieve the jobs (together with their type information and assembly-id) completed during a given date in a given department");
		System.out.println("(13) Retrieve the customers (in name order) whose category is in a given range");
		System.out.println("(14) Delete all cut-jobs whose job-no is in a given range");
		System.out.println("(15) Change the color of a given paint job");
		System.out.println("(16) Import: enter new customers from a data file until the file is empty");
		System.out.println(
				"(17) Export: Retrieve the customers (in name order) whose category is in a given range and output them to a data file instead of screen");
		System.out.println("(18) Quit");
		int i = -1; // error in case of invalid input
		try {
			i = in.nextInt();
			in.nextLine();
		} catch (final Exception e) {
			System.out.println(e);
		}
		return (i);
	}

	// Enter a new customer given name, address and category
	static void choiceOne() {
		System.out.print("Enter customer name:\t");
		final String name = in.nextLine().replaceAll("'", "''");// escape quotes
		System.out.print("\nEnter customer address:\t");
		final String address = in.nextLine().replaceAll("'", "''");
		System.out.print("\nEnter customer category:\t");
		final String category = in.nextLine().replaceAll("'", "''");
		System.out.println("");
		final String query = "INSERT INTO customers VALUES ('" + name + "','" + address + "'," + category + ");";
		try {
			statement.execute(query);

		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
		}
	}

	// Enter a new department given data
	static void choiceTwo() {
		System.out.print("Enter department data:\t");
		final String data = in.nextLine().replaceAll("'", "''");// escape quotes;
		System.out.println("");
		final String query = "INSERT INTO departments VALUES ('" + data + "');";
		try {
			statement.execute(query);

		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
		}
	}

	// Enter a new assembly given customer name, date
	// ordered and assembly details
	static void choiceThree() {
		System.out.print("Enter customer name for this assembly:\t");
		final String name = in.nextLine().replaceAll("'", "''");// escape quotes;
		System.out.println("\nEnter assembly date and time ordered");
		System.out.print(
				"yyyymmdd hh:mm:ss {am|pm}; " + "eg 20100202 10:10:10 am for february 2nd 2010 at 10:10:10 am):\t");
		final String date = in.nextLine().replaceAll("'", "''");// escape quotes;
		System.out.print("\nEnter assembly details :\t");
		final String details = in.nextLine().replaceAll("'", "''");// escape quotes;
		System.out.println("");
		final String query = "INSERT INTO assemblies VALUES ('" + name + "','" + date + "','" + details + "');";
		try {
			statement.execute(query);

		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
		}
	}

	// Enter a new process-id and its department together with
	// its type and information relevant to the type
	static void choiceFour() {
		// data for any type of process
		System.out.print("Enter a new process id:\t");
		final String id = in.nextLine().replaceAll("'", "''");// escape quotes;
		System.out.print("\nEnter the department id of the new process");
		final String dept = in.nextLine().replaceAll("'", "''");// escape quotes;
		final String values = " VALUES (" + id + "," + dept + ",'";// will be appended to a stringbuilder
		// once the table name is known
		System.out.println("\nEnter the process type");
		System.out.print("(must be one of 'Cut', 'Fit' or 'Paint'):\t");
		final String proctype = in.nextLine();
		// get the appropriate table for the process type
		final StringBuilder query = new StringBuilder("INSERT INTO ");
		switch (proctype.toLowerCase().charAt(0)) {
		// all info relevant to a fit process
		case 'f':
			query.append(" fit_processes ");
			query.append(values);
			System.out.print("\nEnter the fit type");
			query.append(in.nextLine().replaceAll("'", "''"));// escape quotes
			break;
		// all info relevant to a cut process
		case 'c':
			query.append(" cut_processes ");
			query.append(values);
			System.out.print("\nEnter the cutting type");
			query.append(in.nextLine().replaceAll("'", "''") + "','");
			System.out.print("\nEnter the machine type");
			query.append(in.nextLine().replaceAll("'", "''"));
			break;
		// all info relevant to a paint process
		case 'p':
			query.append(" paint_processes ");
			query.append(values);
			System.out.print("\nEnter the paint type");
			query.append(in.nextLine().replaceAll("'", "''") + "','");
			System.out.print("\nEnter the painting method");
			query.append(in.nextLine().replaceAll("'", "''"));
			break;
		default:
			System.out.println(proctype + " is not a valid process type");
			return;
		}
		query.append("');");
		System.out.println("");
		try {
			statement.execute(query.toString());

		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
		}
	}

	// Create a new account and associate it with the process, assembly, or
	// department to which it is applicable
	static void choiceFive() {
		System.out.println("Enter the account type");
		System.out.print("Must be one of Process, Assembly or Department:\t");
		final String accountType = in.nextLine().replaceAll("'", "''");// escape quotes
		System.out.print("\nEnter the accounts unique number:\t");
		final String accountNumber = in.nextLine().replaceAll("'", "''");
		System.out.print("\nEnter the Id associated with the " + accountType);
		final String acctId = in.nextLine().replaceAll("'", "''");
		// choose the relevant table
		String query;
		switch (accountType.toLowerCase().charAt(0)) {
		case 'p':
			query = ("INSERT INTO process_accounts VALUES (" + accountNumber + ",CURRENT_TIMESTAMP," + acctId + ");");
			break;
		case 'a':
			query = ("INSERT INTO assembly_accounts VALUES (" + accountNumber + ",CURRENT_TIMESTAMP," + acctId + ");");
			break;
		case 'd':
			query = ("INSERT INTO department_accounts VALUES (" + accountNumber + ",CURRENT_TIMESTAMP," + acctId + ");");
			break;
		default:
			System.out.println(accountType + " is not a valid account type");
			return;
		}
		System.out.println("");
		try {
			statement.execute(query.toString());

		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
		}

	}

	// Enter a new job, given its job-no, assembly-id, process-id, and date the job
	// commenced
	static void choiceSix() {
		System.out.println("\nEnter the job type");
		System.out.print("(must be one of 'Cut', 'Fit' or 'Paint'):\t");
		final String proctype = in.nextLine();
		// get the appropriate table for the job type
		final StringBuilder query = new StringBuilder("INSERT INTO ");
		switch (proctype.toLowerCase().charAt(0)) {

		case 'f':
			query.append(" fit_jobs (id, assemblyid, processid, startdate) VALUES ");
			break;

		case 'c':
			query.append(" cut_jobs (id, assemblyid, processid, startdate) VALUES ");
			break;

		case 'p':
			query.append(" paint_jobs (id, assemblyid, processid, startdate) VALUES ");
			break;
		default:
			System.out.println(proctype + " is not a valid job type");
			return;
		}
		System.out.print("\nEnter a unique job Id:\t");
		query.append(" (" + in.nextLine().replaceAll("'", "''"));
		System.out.print("\nEnter an assembly Id:\t");
		query.append("," + in.nextLine().replaceAll("'", "''"));
		System.out.print("\nEnter a process Id:\t");
		query.append("," + in.nextLine().replaceAll("'", "''") + ",CURRENT_TIMESTAMP)");

		System.out.println("");
		try {
			statement.execute(query.toString());
		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
		}

	}

	// At the completion of a job, enter the date it completed and the
	// information relevant to the type of job
	static void choiceSeven() {

		System.out.print("Enter the job Id");
		// make sure that the job exists and find the appropriate table
		final String id = in.nextLine().replaceAll("'", "''");
		try {

			final ResultSet rs = statement.executeQuery(" SELECT jobtype FROM " + " ( select 'cut_jobs' AS jobtype "
					+ " FROM cut_jobs " + " where id = " + id + " UNION " + " SELECT 'paint_jobs' " + " AS jobtype "
					+ " FROM paint_jobs " + " where id = " + id + " UNION select 'fit_jobs' " + " AS jobtype "
					+ " FROM fit_jobs  " + " where id = " + id +") AS jobtypes");
			int count = 0;
String table = "error";
			while (rs.next()) {
				table = rs.getString(1);
				++count; // should be exactly 1
			}
			if (count != 1) {

				System.out.println("Invalid account id");
				return;
			}
			System.out.print("\nEnter the end date:\t");
			final String enddate = in.nextLine().replaceAll("'", "''");// escape quotes
			System.out.print("\nEnter the labor time:\t");
			final String labortime = in.nextLine().replaceAll("'", "''");
			final StringBuilder query = new StringBuilder("UPDATE " + table + " " + "SET enddate = CAST('" + enddate
					+ "' AS DATETIME)," + "labortime = " + labortime);
			switch (table.charAt(0)) {
			case 'c':
				System.out.print("\nEnter the material used:\t");
				query.append(",materialused = '" + in.nextLine().replaceAll("'", "''") + "';");
				break;
			case 'p':
				System.out.print("\nEnter the color of paint:\t");
				query.append(",paintcolor='" + in.nextLine().replaceAll("'", "''"));
				System.out.print("\nEnter the volume of paint");
				query.append("',paintvolume='" + in.nextLine().replaceAll("'", "''") + "';");
				break;
			default:
				query.append(";");
			}

			statement.execute(query.toString());
		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
		}

	}

	/*
	 * Enter a transaction-no and its sup-cost and update all the costs (details) of
	 * the affected accounts by adding sup-cost to their current values of details
	 */
	static void choiceEight() {
		System.out.print("\nEnter a unique transaction-no:\t");
		final String transNo = in.nextLine();
		System.out.print("\nEnter the sup-cost of the transaction:\t");
		final String supCost = in.nextLine();
		System.out.print("\nEnter the process account number:\t");
		final String procAcct = in.nextLine();
		System.out.print("\nEnter the assembly account number:\t");
		final String assNo = in.nextLine();
		System.out.print("\nEnter the department account number:\t");
		final String depNo = in.nextLine();
		final String query = "INSERT INTO transactions VALUES (" + transNo + "," + supCost + "," + assNo + ","
				+ procAcct + "," + depNo + ");";

		System.out.println("");
		try {
			statement.execute(query.toString());
		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
		}
	}

	// Retrieve the cost incurred on an assembly-id
	static void choiceNine() {
		System.out.print("\nEnter an assembly number:\t");
		final String query = "SELECT SUM(supcost) " + "FROM transactions t " + "INNER JOIN assembly_accounts aa "
				+ "ON t.assemblyaccountid = aa.id " + "INNER JOIN assemblies a " + "ON aa.assemblyid=a.id "
				+ "WHERE a.id = " + in.nextLine().replaceAll("'", "''");// escape quotes

		System.out.println("");
		try {
			final ResultSet rs = statement.executeQuery(query);
			rs.next();
			System.out.println("Total cost:\t " + rs.getString(1));
		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
			return;
		}
	}

	/*
	 * Retrieve the total labor time within a department for jobs completed in the
	 * department during a given date
	 */
	static void choiceTen() {
		System.out.print("\nEnter the department id:\t");
		final String depId = in.nextLine();
		System.out.print("\nEnter the date completed");
		System.out.print("yyyymmdd ; " + "eg 20100202 for february 2nd 2010 ):\t");
		final String date = in.nextLine();
		final String query = "		SELECT SUM(t) " + "FROM (SELECT j.labortime t, p.department d, j.enddate e "
				+ " FROM cut_jobs j INNER JOIN cut_processes p ON j.processid=p.id "
				+ " UNION SELECT j.labortime t,p.department d, j.enddate e "
				+ " FROM paint_jobs j INNER JOIN paint_processes p ON j.processid=p.id "
				+ " UNION SELECT j.labortime t, p.department d,  j.enddate e "
				+ " FROM fit_jobs j INNER JOIN fit_processes p ON j.processid=p.id) AS a" + " WHERE( a.d=" + depId
				+ " AND CAST( '"+date+"' AS DATE) = CAST (a.e AS DATE));";
		try {
			final ResultSet rs = statement.executeQuery(query);
			rs.next();
			System.out.println("Total labor time :" + rs.getString(1));
		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
			return;
		}
	}

	/*
	 * Retrieve the processes through which a given assembly-id has passed so far
	 * (in datecommenced order) and the department responsible for each process
	 */
	static void choiceEleven() {
		System.out.print("\nEnter the assembly-id\t");
		final String query =  " WITH jobs AS ( "  +  " 	SELECT id, assemblyid, processid, startdate, enddate  " 
				+  " FROM cut_jobs UNION "  +  " 	SELECT id, assemblyid, processid, startdate, enddate  " 
				+  " FROM paint_jobs UNION "  +  " 	SELECT id, assemblyid, processid, startdate, enddate  " 
				+  " FROM fit_jobs  "  +  " ), processes AS ( "  +  " 	SELECT id, department FROM cut_processes UNION " 
				+  " 	SELECT id, department FROM paint_processes UNION  "  +  " 	SELECT id, department FROM fit_processes " 
				+  " ) SELECT jobs.processid, processes.department " 
				+  " FROM jobs INNER JOIN processes ON jobs.processid = processes.id " 
				+  " WHERE jobs.enddate NOT NULL AND jobs.assemblyid = " +in.nextLine()+ "   ORDER BY jobs.startdate " ;
		try {

			final ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				System.out.printf("PROCESSId:\t%s| DEPARTMENT:\t%s", rs.getString(1), rs.getString(2));
			}
		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
			return;
		}
	}

	/*
	 * Retrieve the jobs (together with their type information and assembly-id)
	 * completed during a given date in a given department
	 */
	static void choiceTwelve() {
		System.out.print("\nEnter the department id:\t");
		final String depId = in.nextLine();
		System.out.print("\nEnter the date completed");
		System.out.print("yyyymmdd ; " + "eg 20100202 for february 2nd 2010 ):\t");
		final String date = in.nextLine();
		final String query =  " SELECT jobtype, assemblyid, processid FROM ( "  +  " 	select 'cutjob'  " 
				+  " AS jobtype, assemblyid, processid, enddate  "  +  " FROM cut_jobs UNION "  +  " 	select 'paintjob'  " 
				+  " AS jobtype, assemblyid, processid , enddate  "  +  " FROM paint_jobs UNION "  +  " 	select 'fitjob' " 
				+  "  AS jobtype, assemblyid, processid , enddate  "  +  " FROM fit_jobs  " 
				+  " ) as a WHERE CAST(enddate AS DATE) = CAST( '"  + date +  "'  AS DATE)  "  +  " AND processid IN ( " 
				+  " 	SELECT id FROM cut_processes WHERE department= "  + depId +  "  UNION " 
				+  " 	SELECT id FROM paint_processes WHERE department= "  + depId +  "  UNION " 
				+  " 	SELECT id FROM fit_processes WHERE department= "  + depId +  "  ); " ;
		try {

			final ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				System.out.printf("Job type:\t%s| id:\t%s| processid:\t%s", rs.getString(1), rs.getString(2),
						rs.getString(3));
			}
		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
			return;
		}
	}

	/*
	 * Retrieve the customers (in name order) whose category is in a given range
	 */
	static void choiceThirteen() {
		System.out.print("\nEnter the customer category lower bound:\t");
		final String lowerbound = in.nextLine();
		System.out.print("\nEnter the customer category upper bound:\t");
		final String upperbound = in.nextLine();
		final String query = "SELECT * FROM customers WHERE category BETWEEN " + lowerbound + " AND " + upperbound
				+ "ORDER BY name";
		try {

			final ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				System.out.printf("NAME:\t%s| ADDRESS:\t%s| CATEGORY:\t%s", rs.getString(1), rs.getString(2),
						rs.getString(3));
			}
		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
			return;
		}
	}

	/*
	 * Delete all cut-jobs whose job-no is in a given range
	 */
	static void choiceFourteen() {
		System.out.print("\nEnter the job-no lower bound:\t");
		final String lowerbound = in.nextLine();
		System.out.print("\nEnter the job-no upper bound:\t");
		final String upperbound = in.nextLine();
		final String query = "DELETE FROM cut_jobs WHERE id BETWEEN " + lowerbound + " AND " + upperbound + ";";
		try {
			statement.execute(query.toString());
		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
		}
	}

	static void choiceFifteen() {
		System.out.print("\nEnter the paint job id:\t");
		final String id = in.nextLine();
		System.out.print("\nEnter the new color:\t");
		final String color = in.nextLine().replaceAll("'", "''");// escape quotes
		final String query = "UPDATE paint_jobs SET paintcolor = '" + color + "' WHERE id=" + id + ";";
		try {
			statement.execute(query.toString());
		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
		}
	}

	// load customers from file
	// (format is a value list)
	static void choiceSixteen() {
		File file;
		String record;
		System.out.print("\nEnter the filename:\t");
		file = new File(in.nextLine());
		// try to read the file
		// (format is a value list)
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (final FileNotFoundException e) {
			System.out.print("file not found");
			return;
		}
		// try to build the "values " clause in the sql statement
		final StringBuilder query = new StringBuilder("INSERT INTO customers VALUES (");
		try {
			// no comma before the first record
			if ((record = br.readLine()) == null) {
				System.out.println("Empty file");
				return;
			} else {
				query.append(record);
			}
			query.append(")");
			// build the "VALUES" clause in the sql statement
			while ((record = br.readLine()) != null) {
				query.append(",(" + record + ")");
			}
			query.append(";");
		} catch (final IOException e) {
			System.out.println("an IO error has occoured");
			return;
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				System.out.println("an IO error has occoured");
				return;
			}
		}
		// insert
		try {
			statement.execute(query.toString());
		} catch (final SQLException e) {
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
		}
	}

	// save customers to file where customers in a given range
	// (format is a value list)
	static void choiceSeventeen() {
		PrintWriter out;
		System.out.print("\nEnter the customer category lower bound:\t");
		final String lowerbound = in.nextLine();
		System.out.print("\nEnter the customer category upper bound:\t");
		final String upperbound = in.nextLine();
		System.out.print("\nEnter the filename");
		final String filename = in.nextLine();
		final String query = "SELECT * FROM customers WHERE category BETWEEN " + lowerbound + " AND " + upperbound
				+ "ORDER BY name";
		// try to open a file for writing
		// (format is a value list)
		try {
			out = new PrintWriter(filename);
		} catch (final Exception e) {
			System.out.println("Could not write to file " + filename);
			return;
		}

		try {

			final ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				out.printf("'%s','%s',%s\n", rs.getString(1).replaceAll("'", "''"), // escape quotes
						rs.getString(2).replaceAll("'", "''"), rs.getString(3));
			}
			out.close();
		} catch (final SQLException e) {
			out.close();
			System.out.println("A SQL Error has occoured:");
			System.out.println(e);
			System.out.print("(state:");
			System.out.print(e.getSQLState() + ";");
			System.out.print("error code:");
			System.out.print(Integer.toString(e.getErrorCode()) + ")");
			return;
		}
	}
}
