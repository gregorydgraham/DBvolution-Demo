
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import nz.co.gregs.dbvolution.*;

/**
 * A working demonstration of the simple use of DBvolution.
 *
 * @author gregorygraham
 */
public class DBvolutionDemo {

	public static void main(String[] args) throws SQLException, IOException {
		DBvolutionDemo demo = new DBvolutionDemo();
		demo.createDatabase();
	}
	private DBDatabase database;

	private void createDatabase() throws SQLException, IOException {

          // The very first thing required is a connection to the database
		database = new DemoDB();

		// Now we need to create tables to store data in
		if (this.createTables()) {
			// and create the data we want to use
			this.createData();
		}

		// We're going to list all of the encounters that earned 100 or more experience
		// So we need an example Encounter object
		final Encounter encounterExample = new Encounter();

		// limit the results to only encounters with 100 or more experienceEarned
		encounterExample.experienceEarned.permittedRangeInclusive(100, null);

          // Get a DBTable instance based on the example
		final DBTable<Encounter> dbTable = database.getDBTable(encounterExample);

		// Retreive all the interesting rows from the database using the DBTable
		List<Encounter> allRows = dbTable.getAllRows();

          // Print out the results for demonstration purposes.
		System.out.println("");
		System.out.println("VALUABLE ENCOUNTERS");
		database.print(allRows);

		// Lets add the monsters details to the request
		// We'll need to move to a DBQuery instead of a DBTable as DBTable is designed fto make single table queries simple.
		DBQuery dbQuery = database.getDBQuery(encounterExample);

		// and add an instance of Monster to the mix
		// Optional means we don't require this table to have data connecting it to the rest of the query, DBvolution will create an outer join to accomodate our instruction
		dbQuery.addOptional(new Monster());

          // getting all the rows is similar to DBTable but returns a collection of DBQueryRows
		List<DBQueryRow> allQueryRows = dbQuery.getAllRows();

		// Printing them is just as easy
		System.out.println("");
		System.out.println("VALUABLE ENCOUNTERS AND THE MONSTER INVOLVED");
		database.print(allQueryRows);
		
		// however it's important to know how to access the individual rows
		System.out.println("");
		System.out.println("VALUABLE ENCOUNTERS AND THE MONSTER INVOLVED");
		for (DBQueryRow queryRow : allQueryRows) {
			
			//A DBQueryRow contains all the individual rows from the table tht are associated with each other
			//use the get method with an instance of the class you want to get the relevant row.
			Encounter encounter = queryRow.get(encounterExample);
			System.out.println("" + encounter);
			
			//Watch out though!  Optional rows will be NULL if there is no relevant row
			Monster monster = queryRow.get(new Monster());
			if (monster != null) {
				System.out.println("" + monster);
			} else {
				System.out.println("NO MONSTER INVOLVED");
			}
			// Thanks for reading this demo, I hope it helped.
			// If you like DBvolution please support it by telling people about, commenting on SourceForge, and donating at dbvolution.gregs.co.nz
			// 
			// Thanks
			// Greg
		}
	}

	private boolean createTables() throws SQLException {
		try {
			database.createTable(new Encounter());
			database.createTable(new Monster());
			return true;
		} catch (Exception ex) {
			; // An exception is thrown if the table already exists
		}
		return false;
	}

	private void createData() throws SQLException {
		//Create some monsters to fight
		//Note that the monsters will not have valid IDs until they're inserted into the database
		Monster goblin = new Monster("Goblin");
		Monster guard = new Monster("Guard");
		Monster dragon = new Monster("Dragon");
		database.insert(goblin, guard, dragon);

		//Now that the monsters have been inserted they have valid IDs 
		//and we can create some encounters
		Encounter encounter1 = new Encounter("First encounter", goblin, 20, new Date());
		Encounter encounter2 = new Encounter("Taking the guardtower", guard, 100, new Date());
		Encounter encounter3 = new Encounter("Slaying the dragon", dragon, 30000, new Date());
		// This one doesn't have a monster, and will demonstrate outer joins later
		Encounter encounter4 = new Encounter("Negoitiating with the King", 30000, new Date());
		database.insert(encounter1, encounter2, encounter3, encounter4);
	}

}
