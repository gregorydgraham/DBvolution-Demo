/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.gregs.dbvolutiondemo;

import nz.co.gregs.dbvolution.DBRow;
import nz.co.gregs.dbvolution.annotations.DBAutoIncrement;
import nz.co.gregs.dbvolution.annotations.DBColumn;
import nz.co.gregs.dbvolution.annotations.DBForeignKey;
import nz.co.gregs.dbvolution.annotations.DBPrimaryKey;
import nz.co.gregs.dbvolution.annotations.DBRequiredTable;
import nz.co.gregs.dbvolution.annotations.DBTableName;
import nz.co.gregs.dbvolution.datatypes.DBInteger;

/**
 *
 * @author greg
 */
@DBRequiredTable /* DBRequiredTable instructs the database to create this table if necessary */
@DBTableName("possessions")
public class Possessions extends DBRow{

	private static final long serialVersionUID = 1L;
	
	@DBPrimaryKey
	@DBAutoIncrement
	@DBColumn("antaposs_id")
	public DBInteger posessionID = new DBInteger();
	
	@DBColumn
	@DBForeignKey(Antagonist.class)
	public DBInteger owner = new DBInteger();
	
	@DBColumn
	@DBForeignKey(Item.class)
	public DBInteger possessedItem = new DBInteger();
	
}
