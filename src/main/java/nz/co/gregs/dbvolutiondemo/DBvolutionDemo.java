package nz.co.gregs.dbvolutiondemo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nz.co.gregs.dbvolution.DBQuery;
import nz.co.gregs.dbvolution.DBTable;

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

		// Thanks for trying this demo, I hope it helped.
		// If you like DBvolution please support it by telling people about it,
		// or commenting on GitHub.
		// 
		// Thanks
		// Greg
	}

	private nz.co.gregs.dbvolution.DBDatabase database;
	private boolean tablesCreated = false;

	private void createDatabase() throws SQLException, IOException {

		// The very first thing required is a connection to the database
		database = new DemoDB();

		// Print out the generated SQL before executing
		database.setPrintSQLBeforeExecuting(true);

	}

	protected void accessingEachRowWithOptionalTables() throws SQLException {
		// We're going to list all of the encounters that earned 100 or more experience
		// and the antagonist involved
		// So we need example Encounter and Antagonist objects
		final Encounter encounterExample = new Encounter();
		final Antagonist antagonistExample = new Antagonist();

		// limit the results to only encounters with 100 or more experienceEarned
		encounterExample.experienceEarned.permittedRangeInclusive(100, null);

		// We'll need a DBQuery so we can use more than one table.
		nz.co.gregs.dbvolution.DBQuery dbQuery
				= database.getDBQuery(encounterExample);

		// and add an instance of Antagonist to the mix
		// Optional means we don't require this table to have 
		// data connecting it to the rest of the query, 
		// DBvolution will create an outer join to accomodate our instruction
		dbQuery.addOptional(antagonistExample);

		// getting all the rows is similar to DBTable 
		// but returns a collection of DBQueryRows
		List<nz.co.gregs.dbvolution.DBQueryRow> allQueryRows
				= dbQuery.getAllRows();

		// Now loop through the individual rows processing as you go
		System.out.println("");
		System.out.println("VALUABLE ENCOUNTERS including THE ANTAGONIST INVOLVED");
		for (nz.co.gregs.dbvolution.DBQueryRow queryRow : allQueryRows) {

			// A DBQueryRow contains all the rows from the tables 
			// that are associated with each other.
			// Use the get method, with an instance of the class you want, to get
			// the relevant row.
			// Only the class is important so we can use our example object.
			Encounter encounter = queryRow.get(encounterExample);
			// Using new instances is also ok
			Antagonist antagonist = queryRow.get(new Antagonist());

			// Simple processing of the required rows
			System.out.println("" + encounter);

			// Watch out though!  
			// Optional rows will be NULL if there is no relevant row
			if (antagonist != null) {
				System.out.println("" + antagonist);
			} else {
				System.out.println("NO ANTAGONIST INVOLVED");
			}
		}
	}

	protected void listRowsMatchingSimpleConditionsOnSeveralTablesWithAnOptionalTable() throws AccidentalBlankQueryException, SQLException, AccidentalCartesianJoinException {

		// We're going to list all of the encounters that earned 100 or more experience
		// and the antagonist involved
		// So we need example Encounter and Antagonist objects
		final Encounter encounterExample = new Encounter();
		final Antagonist antagonistExample = new Antagonist();

		// limit the results to only encounters with 100 or more experienceEarned
		encounterExample.experienceEarned.permittedRangeInclusive(100, null);

		// We'll need a DBQuery so we can use more than one table.
		nz.co.gregs.dbvolution.DBQuery dbQuery
				= database.getDBQuery(encounterExample);

		// and add an instance of Antagonist to the mix
		// Optional means we don't require this table to have 
		// data connecting it to the rest of the query, 
		// DBvolution will create an outer join to accomodate our instruction
		dbQuery.addOptional(antagonistExample);

		// getting all the rows is similar to DBTable 
		// but returns a collection of DBQueryRows
		List<nz.co.gregs.dbvolution.DBQueryRow> allQueryRows
				= dbQuery.getAllRows();

		// Printing them is just as easy
		System.out.println("");
		System.out.println("VALUABLE ENCOUNTERS (including THE ANTAGONIST if any)");
		database.print(allQueryRows);
	}

	protected void listRowsMatchingSimpleConditionsOnSeveralTables() throws AccidentalBlankQueryException, SQLException, AccidentalCartesianJoinException {

		// We're going to list all of the encounters that earned 100 or more experience
		// and the antagonist involved
		// So we need example Encounter and Antagonist objects
		final Encounter encounterExample = new Encounter();
		final Antagonist antagonistExample = new Antagonist();

		// limit the results to only encounters with 100 or more experienceEarned
		encounterExample.experienceEarned.permittedRangeInclusive(100, null);

		// We'll need a DBQuery so we can use more than one table.
		nz.co.gregs.dbvolution.DBQuery dbQuery
				= database.getDBQuery(encounterExample, antagonistExample);

		// getting all the rows is similar to DBTable 
		// but returns a collection of DBQueryRows
		List<nz.co.gregs.dbvolution.DBQueryRow> allQueryRows
				= dbQuery.getAllRows();

		// Printing them is just as easy
		System.out.println("");
		System.out.println("VALUABLE ENCOUNTERS WITH AN ANTAGONIST INVOLVED");
		database.print(allQueryRows);
	}

	private void displayTheDatabaseSchemaAsAQuery() {
		DBQuery dbQuery = this.database.getDBQuery(new Antagonist()).setBlankQueryAllowed(true);
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
	public static class Valuable extends Encounter {

		{
			this.experienceEarned.permittedRangeInclusive(100, null);
		}
	}

	protected void listRowsFromTableWithPredefinedConditions() throws SQLException {
		// We're going to list all of the encounters that earned 100 or more experience
		// Using the Encounter subclass we've defined above.
		final Valuable valuableExample
				= new Valuable();

		// Get a DBTable instance based on the example
		final DBTable<Valuable> dbTable
				= database.getDBTable(valuableExample);

		// Retreive all the interesting rows from the database using the DBTable
		List<Valuable> allRows
				= dbTable
						// We want to get all rows from the table so turn off blank query protection
						.setBlankQueryAllowed(true)
						// Retrieve all the rows
						.getAllRows();

		// Print out the results for demonstration purposes.
		System.out.println("");
		System.out.println("VALUABLE ENCOUNTERS from a predefined subselect");
		// Database.print is a convenience method to print lists
		database.print(allRows);
	}

	protected void listRowsMatchingSimpleConditionsFromOneTable() throws SQLException {
		// We're going to list all of the encounters that earned 100 or more experience
		// So we need an Encounter object to define the requirements of the query
		final Encounter encounterExample = new Encounter();
		
		// limit the results to only encounters with 100 or more experienceEarned
		encounterExample.experienceEarned.permittedRangeInclusive(100, null);


		// We'll also need a query object to run the query
		// DBQuery, DBTable, and DBReport are options 
		// We've got a very simple querythat only uses 1 table so we'll use a DBTable
		final nz.co.gregs.dbvolution.DBTable<Encounter> dbTable;

		// Get a DBTable instance based on the example from the database
		dbTable= database.getDBTable(encounterExample);

		// Retreive all the interesting rows from the database using the DBTable
		List<Encounter> allRows = dbTable.getAllRows();

		// Print out the results for demonstration purposes.
		System.out.println("");
		System.out.println("VALUABLE ENCOUNTERS");
		// Database.print is a convenience method to print lists
		database.print(allRows);
	}

	private void createTables() {
		// A table creation method to ensure the tables are exist.
		// "NoExceptions" avoids any database exceptions about the tables already existing.
		database.createTablesNoExceptions(
				new Encounter(),
				new Antagonist(),
				new Item(),
				new Possessions());
		tablesCreated = true;
	}

	private void createData() throws SQLException {
		// To avoid duplicated rows, 
		// only insert if the tables are freshly created
		if (tablesCreated) {

			//Create some antagonists to fight
			//Note that the antagonists will not have valid IDs 
			//until they're inserted into the database
			Antagonist goblin = new Antagonist("Goblin");
			Antagonist guard = new Antagonist("Guard");
			Antagonist dragon = new Antagonist("Dragon");
			database.insert(goblin, guard, dragon);

			//Now that the antagonists have been inserted 
			//they have valid IDs 
			//and we can create some encounters
			Encounter encounter1 = new Encounter("First encounter", goblin, 20, new Date());
			Encounter encounter2 = new Encounter("Taking the guardtower", guard, 100, new Date());
			Encounter encounter3 = new Encounter("Slaying the dragon", dragon, 30000, new Date());

			//This one doesn't have a antagonist, 
			//and will demonstrate outer joins later
			Encounter encounter4 = new Encounter("Defeating the trap", 300, new Date());
			database.insert(encounter1, encounter2, encounter3, encounter4);
		}
	}

}
