
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
public class Monster extends DBRow{
	
	@DBColumn
	@DBPrimaryKey
	@DBAutoIncrement
	DBInteger monsterID = new DBInteger();
	
	@DBColumn
	DBString name = new DBString();
	
	public Monster(String name){
		super();
		this.name.setValue(name);
	}

	public Monster() {
		super();
	}
	
}
