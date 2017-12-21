package nz.co.gregs.dbvolutiondemo;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import nz.co.gregs.dbvolution.databases.DBDatabaseCluster;
import nz.co.gregs.dbvolution.databases.H2DB;
import nz.co.gregs.dbvolution.databases.H2MemoryDB;
import nz.co.gregs.dbvolution.databases.SQLiteDB;

/**
 *
 * @author gregorygraham
 */

// To use a different database,
// change H2DB to any of the databases in 
// nz.co.gregs.dbvolution.databases
public class DemoDB extends DBDatabaseCluster{

	public DemoDB() throws IOException, SQLException {
		// You don't need to use a cluster but when it's this easy why not :)
		super(
				new H2DB(new File("demoDB.h2"), "demo", "demo"),
				new H2MemoryDB("demoDBMemory", "demo", "demo", true),
				new SQLiteDB(new File("demoDB.sqlite"), "demo", "demo")
		);
	}
	
}
