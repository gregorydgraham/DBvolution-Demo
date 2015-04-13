package nz.co.gregs.dbvolutiondemo;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import nz.co.gregs.dbvolution.databases.H2DB;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gregorygraham
 */
public class DemoDB extends H2DB{

	public DemoDB() throws IOException, SQLException {
		super(new File("demodb"), "demo", "demo");
	}
	
}
