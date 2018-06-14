package nz.co.gregs.dbvolutiondemo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nz.co.gregs.dbvolution.DBQuery;
import nz.co.gregs.dbvolution.DBTable;
import nz.co.gregs.dbvolution.databases.DBDatabase;

import nz.co.gregs.dbvolution.exceptions.AccidentalBlankQueryException;
import nz.co.gregs.dbvolution.exceptions.AccidentalCartesianJoinException;

/**
 * A working demonstration of the simple use of DBvolution.
 *
 * Creates a complete database, inserts data, and demonstrates several queries.
 *
 * @author gregorygraham
 */
public class DBvolutionDemo {
	
	private static final String NEWLINE = System.getProperty("line.separator");

	public static void main(String[] args) throws SQLException, IOException {
		DBvolutionDemo demo = new DBvolutionDemo();

		// Create our database
		demo.createDatabase();

		// Now we need to create tables to store data in
		demo.createTables();

		// and create the data we want to use
		demo.createData();

		// Now start selecting results and processing them
		demo.listRowsMatchingSimpleConditionsFromOneTable();

		demo.listRowsFromTableWithPredefinedConditions();

		demo.listRowsMatchingSimpleConditionsOnSeveralTables();

		demo.listRowsMatchingSimpleConditionsOnSeveralTablesWithAnOptionalTable();

		demo.accessingEachRowWithOptionalTables();

		demo.displayTheDatabaseSchemaAsAQuery();

		System.out.println(NEWLINE
				+ "Thanks for trying this demo, I hope it helped." + NEWLINE
				+ "If you like DBvolution please support it by telling people about it," + NEWLINE
				+ "or commenting on GitHub." + NEWLINE
				+ NEWLINE
				+ "Thanks" + NEWLINE
				+ "Greg");
	}

	private DBDatabase database;
	private boolean tablesCreated = false;

	private void createDatabase() throws SQLException, IOException {

		// The very first thing required is a connection to the database
		database = new DemoDB();

		// Print out the generated SQL before executing
		database.setPrintSQLBeforeExecuting(true);

	}

	protected void accessingEachRowWithOptionalTables() throws SQLException {
		// We're going to list all of the meetings that earned 100 or more commission
		// and the customer involved
		// So we need example Meeting and Customer objects
		final Meeting meetingExample = new Meeting();
		final Customer customerExample = new Customer();

		// limit the results to only meetings with 100 or more commission
		meetingExample.commissionEarned.permittedRangeInclusive(100, null);

		// We'll need a DBQuery so we can use more than one table.
		nz.co.gregs.dbvolution.DBQuery dbQuery
				= database.getDBQuery(meetingExample);

		// and add an instance of Customer to the mix
		// Optional means we don't require this table to have 
		// data connecting it to the rest of the query, 
		// DBvolution will create an outer join to accomodate our instruction
		dbQuery.addOptional(customerExample);

		// getting all the rows is similar to DBTable 
		// but returns a collection of DBQueryRows
		List<nz.co.gregs.dbvolution.DBQueryRow> allQueryRows
				= dbQuery.getAllRows();

		// Now loop through the individual rows processing as you go
		System.out.println("");
		System.out.println("VALUABLE MEETINGS including THE CUSTOMER INVOLVED");
		for (nz.co.gregs.dbvolution.DBQueryRow queryRow : allQueryRows) {

			// A DBQueryRow contains all the rows from the tables 
			// that are associated with each other.
			// Use the get method, with an instance of the class you want, to get
			// the relevant row.
			// Only the class is important so we can use our example object.
			Meeting meeting = queryRow.get(meetingExample);
			// Using new instances is also ok
			Customer customer = queryRow.get(new Customer());

			// Simple processing of the required rows
			System.out.println("" + meeting);

			// Watch out though!  
			// Optional rows will be NULL if there is no relevant row
			if (customer != null) {
				System.out.println("" + customer);
			} else {
				System.out.println("NO CUSTOMER INVOLVED");
			}
		}
	}

	protected void listRowsMatchingSimpleConditionsOnSeveralTablesWithAnOptionalTable() throws AccidentalBlankQueryException, SQLException, AccidentalCartesianJoinException {

		// We're going to list all of the meetings that earned 100 or more commission
		// and the customer involved
		// So we need example Meeting and Customer objects
		final Meeting meetingExample = new Meeting();
		final Customer customerExample = new Customer();

		// limit the results to only meetings with 100 or more commission
		meetingExample.commissionEarned.permittedRangeInclusive(100, null);

		// We'll need a DBQuery so we can use more than one table.
		nz.co.gregs.dbvolution.DBQuery dbQuery
				= database.getDBQuery(meetingExample);

		// and add an instance of Customer to the mix
		// Optional means we don't require this table to have 
		// data connecting it to the rest of the query, 
		// DBvolution will create an outer join to accomodate our instruction
		dbQuery.addOptional(customerExample);

		// getting all the rows is similar to DBTable 
		// but returns a collection of DBQueryRows
		List<nz.co.gregs.dbvolution.DBQueryRow> allQueryRows
				= dbQuery.getAllRows();

		// Printing them is just as easy
		System.out.println("");
		System.out.println("VALUABLE MEETINGS (including THE CUSTOMER if any)");
		database.print(allQueryRows);
	}

	protected void listRowsMatchingSimpleConditionsOnSeveralTables() throws AccidentalBlankQueryException, SQLException, AccidentalCartesianJoinException {

		// We're going to list all of the meetings that earned 100 or more commission
		// and the customer involved
		// So we need example Meeting and Customer objects
		final Meeting meetingExample = new Meeting();
		final Customer customerExample = new Customer();

		// limit the results to only meetings with 100 or more commission
		meetingExample.commissionEarned.permittedRangeInclusive(100, null);

		// We'll need a DBQuery so we can use more than one table.
		nz.co.gregs.dbvolution.DBQuery dbQuery
				= database.getDBQuery(meetingExample, customerExample);

		// getting all the rows is similar to DBTable 
		// but returns a collection of DBQueryRows
		List<nz.co.gregs.dbvolution.DBQueryRow> allQueryRows
				= dbQuery.getAllRows();

		// Printing them is just as easy
		System.out.println("");
		System.out.println("VALUABLE MEETINGS WITH A CUSTOMER INVOLVED");
		database.print(allQueryRows);
	}

	private void displayTheDatabaseSchemaAsAQuery() {
		DBQuery dbQuery = this.database.getDBQuery(new Customer()).setBlankQueryAllowed(true);
		dbQuery.addAllConnectedBaseTablesAsOptional();
		dbQuery.displayQueryGraph();
		try {
			database.print(dbQuery.getAllRows());
		} catch (SQLException | AccidentalBlankQueryException | AccidentalCartesianJoinException ex) {
			Logger.getLogger(DBvolutionDemo.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// This is an example of using subclassing to create permanently defined
	// subsets of a table.
	// These are very useful for creating multiple connection between 2 tables.
	public static class ValuableMeeting extends Meeting {

		{
			this.commissionEarned.permittedRangeInclusive(100, null);
		}
	}

	protected void listRowsFromTableWithPredefinedConditions() throws SQLException {
		// We're going to list all of the meetings that earned 100 or more commission
		// Using the Meeting subclass we've defined above.
		final ValuableMeeting valuableExample
				= new ValuableMeeting();

		// Get a DBTable instance based on the example
		final DBTable<ValuableMeeting> dbTable
				= database.getDBTable(valuableExample);

		// Retreive all the interesting rows from the database using the DBTable
		List<ValuableMeeting> allRows
				= dbTable
						// We want to get all rows from the table so turn off blank query protection
						.setBlankQueryAllowed(true)
						// Retrieve all the rows
						.getAllRows();

		// Print out the results for demonstration purposes.
		System.out.println("");
		System.out.println("VALUABLE MEETINGS from a predefined subselect");
		// Database.print is a convenience method to print lists
		database.print(allRows);
	}

	protected void listRowsMatchingSimpleConditionsFromOneTable() throws SQLException {
		// We're going to list all of the meetings that earned 100 or more commission
		// So we need an Meeting object to define the requirements of the query
		final Meeting meetingExample = new Meeting();
		
		// limit the results to only meetings with 100 or more commission
		meetingExample.commissionEarned.permittedRangeInclusive(100, null);


		// We'll also need a query object to run the query
		// DBQuery, DBTable, and DBReport are options 
		// We've got a very simple querythat only uses 1 table so we'll use a DBTable
		final nz.co.gregs.dbvolution.DBTable<Meeting> dbTable;

		// Get a DBTable instance based on the example from the database
		dbTable= database.getDBTable(meetingExample);

		// Retreive all the interesting rows from the database using the DBTable
		List<Meeting> allRows = dbTable.getAllRows();

		// Print out the results for demonstration purposes.
		System.out.println("");
		System.out.println("VALUABLE MEETINGS");
		// Database.print is a convenience method to print lists
		database.print(allRows);
	}

	private void createTables() {
		//We use the @DBRequiredTable annotation to automatically create the tables
		tablesCreated = true;
		
		// However a table creation method to ensure the tables exist.
		// "NoExceptions" avoids any database exceptions about the tables already existing.
		/*database.createTablesNoExceptions(
				new Meeting(),
				new Customer(),
				new Item(),
				new Possessions());*/
	}

	private void createData() throws SQLException {
		// To avoid duplicated rows, 
		// only insert if the tables are freshly created
		if (tablesCreated) {

			//Create some customers to meet
			//Note that the customers will not have valid IDs 
			//until they're inserted into the database
			Customer gordon = new Customer("Gordon");
			Customer gwen = new Customer("Gwen");
			Customer dahab = new Customer("Dahab");
			database.insert(gordon, gwen, dahab);

			//Now that the customers have been inserted 
			//they have valid IDs 
			//and we can create some meetings
			Meeting meeting1 = new Meeting("Introduction meeting", gordon, 20, new Date());
			Meeting meeting2 = new Meeting("Pitch meeting", gwen, 100, new Date());
			Meeting meeting3 = new Meeting("Service level agreement", dahab, 30000, new Date());

			//This one doesn't have a customer, 
			//and will demonstrate outer joins later
			Meeting meeting4 = new Meeting("Brainstorming", 300, new Date());
			database.insert(meeting1, meeting2, meeting3, meeting4);
		}
	}

}
