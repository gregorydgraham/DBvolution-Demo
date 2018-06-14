package nz.co.gregs.dbvolutiondemo;

import nz.co.gregs.dbvolution.DBRow;
import nz.co.gregs.dbvolution.annotations.*;
import nz.co.gregs.dbvolution.datatypes.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gregorygraham
 */
@DBRequiredTable /* DBRequiredTable instructs the database to create this table if necessary */
public class Customer extends DBRow {

	private static final long serialVersionUID = 1L;

	@DBColumn
	@DBPrimaryKey
	@DBAutoIncrement
	public DBInteger customerID = new DBInteger();

	@DBColumn
	public DBString name = new DBString();

	@DBColumn
	public DBBoolean hasAgent = new DBBoolean();

	public Customer(String name) {
		super();
		this.name.setValue(name);
		this.hasAgent.setValue(false);
	}

	public Customer() {
		super();
	}

	// Some examples of pre-defined selections
	static public class Dahab extends Customer {

		private static final long serialVersionUID = 1L;

		{
			this.name.permittedPatternIgnoreCase("%dahab%");
		}
	}

	static public class CustomerWithAgent extends Customer {

		private static final long serialVersionUID = 1L;

		{
			this.hasAgent.permittedValues(Boolean.TRUE);
		}
	}

	static public class Prospect extends Customer {

		private static final long serialVersionUID = 1L;

		{
			this.hasAgent.permittedValues(Boolean.FALSE);
		}
	}

}
