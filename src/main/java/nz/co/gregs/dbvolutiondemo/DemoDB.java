package nz.co.gregs.dbvolutiondemo;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author gregorygraham
 */

// To use a different database,
// change H2DB to any of the databases in 
// nz.co.gregs.dbvolution.databases
public class DemoDB extends nz.co.gregs.dbvolution.databases.H2DB{

	public DemoDB() throws IOException, SQLException {
		// The H2DB constructor takes a file, username, and password
		super(new File("demodb"), "demo", "demo");
	}
	
}
