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
public class Encounter extends DBRow {

	private static final long serialVersionUID = 1L;

	@DBColumn
	@DBPrimaryKey
	@DBAutoIncrement
	public DBInteger encounter_pk = new DBInteger();
	
	@DBColumn
	public DBString name = new DBString();
	
	@DBColumn
	@DBForeignKey(Antagonist.class)
	public DBInteger antagonist = new DBInteger();
	
	@DBColumn
	public DBInteger experienceEarned = new DBInteger();
	
	@DBColumn
	public DBDate dateEncountered = new DBDate();

	public Encounter() {
		super();
	}

	public Encounter(String name, Integer exp, Date date) {
		super();
		this.name.setValue(name);
		this.experienceEarned.setValue(exp);
		this.dateEncountered.setValue(date);
	}

	public Encounter(String name, Antagonist anatagonist, Integer exp, Date date) {
		super();
		this.name.setValue(name);
		this.antagonist.setValue(anatagonist.antagonistID.intValue());
		this.experienceEarned.setValue(exp);
		this.dateEncountered.setValue(date);
	}

}
