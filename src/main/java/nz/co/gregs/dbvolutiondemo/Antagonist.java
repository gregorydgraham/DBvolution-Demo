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
public class Antagonist extends DBRow {

	private static final long serialVersionUID = 1L;

	@DBColumn
	@DBPrimaryKey
	@DBAutoIncrement
	public DBInteger antagonistID = new DBInteger();

	@DBColumn
	public DBString name = new DBString();

	@DBColumn
	public DBBoolean npc = new DBBoolean();

	public Antagonist(String name) {
		super();
		this.name.setValue(name);
		this.npc.setValue(false);
	}

	public Antagonist() {
		super();
	}

	// Some examples of pre-defined selections
	static public class Dragon extends Antagonist {

		private static final long serialVersionUID = 1L;

		{
			this.name.permittedPatternIgnoreCase("%dragon%");
		}
	}

	static public class NPC extends Antagonist {

		private static final long serialVersionUID = 1L;

		{
			this.npc.permittedValues(Boolean.TRUE);
		}
	}

	static public class Monster extends Antagonist {

		private static final long serialVersionUID = 1L;

		{
			this.npc.permittedValues(Boolean.FALSE);
		}
	}

}
