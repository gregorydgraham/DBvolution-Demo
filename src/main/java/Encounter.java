
import java.util.Date;
import nz.co.gregs.dbvolution.DBRow;
import nz.co.gregs.dbvolution.annotations.DBAutoIncrement;
import nz.co.gregs.dbvolution.annotations.DBColumn;
import nz.co.gregs.dbvolution.annotations.DBForeignKey;
import nz.co.gregs.dbvolution.annotations.DBPrimaryKey;
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
public class Encounter extends DBRow {

	@DBColumn
	@DBPrimaryKey
	@DBAutoIncrement
	DBInteger encounter_pk = new DBInteger();
	
	@DBColumn
	DBString name = new DBString();
	
	@DBColumn
	@DBForeignKey(Monster.class)
	DBInteger monster = new DBInteger();
	
	@DBColumn
	DBInteger experienceEarned = new DBInteger();
	
	@DBColumn
	DBDate dateEncountered = new DBDate();

	public Encounter() {
		super();
	}

	public Encounter(String name, Integer exp, Date date) {
		super();
		this.name.setValue(name);
		this.experienceEarned.setValue(exp);
		this.dateEncountered.setValue(date);
	}

	public Encounter(String name, Monster monster, Integer exp, Date date) {
		super();
		this.name.setValue(name);
		this.monster.setValue(monster.monsterID.intValue());
		this.experienceEarned.setValue(exp);
		this.dateEncountered.setValue(date);
	}

}
