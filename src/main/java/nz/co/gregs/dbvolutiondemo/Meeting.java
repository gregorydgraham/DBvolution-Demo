package nz.co.gregs.dbvolutiondemo;


import java.util.Date;
import nz.co.gregs.dbvolution.DBRow;
import nz.co.gregs.dbvolution.annotations.DBAutoIncrement;
import nz.co.gregs.dbvolution.annotations.DBColumn;
import nz.co.gregs.dbvolution.annotations.DBForeignKey;
import nz.co.gregs.dbvolution.annotations.DBPrimaryKey;
import nz.co.gregs.dbvolution.annotations.DBRequiredTable;
import nz.co.gregs.dbvolution.datatypes.DBDate;
import nz.co.gregs.dbvolution.datatypes.DBInteger;
import nz.co.gregs.dbvolution.datatypes.DBString;

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
public class Meeting extends DBRow {

	private static final long serialVersionUID = 1L;

	@DBColumn
	@DBPrimaryKey
	@DBAutoIncrement
	public DBInteger meeting_pk = new DBInteger();
	
	@DBColumn
	public DBString name = new DBString();
	
	@DBColumn
	@DBForeignKey(Customer.class)
	public DBInteger customer = new DBInteger();
	
	@DBColumn
	public DBInteger commissionEarned = new DBInteger();
	
	@DBColumn
	public DBDate meetingDate = new DBDate();

	public Meeting() {
		super();
	}

	public Meeting(String name, Integer exp, Date date) {
		super();
		this.name.setValue(name);
		this.commissionEarned.setValue(exp);
		this.meetingDate.setValue(date);
	}

	public Meeting(String name, Customer customer, Integer commish, Date date) {
		super();
		this.name.setValue(name);
		this.customer.setValue(customer.customerID.intValue());
		this.commissionEarned.setValue(commish);
		this.meetingDate.setValue(date);
	}

}
