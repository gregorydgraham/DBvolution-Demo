/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.gregs.dbvolutiondemo;

import nz.co.gregs.dbvolution.DBRow;
import nz.co.gregs.dbvolution.annotations.DBAutoIncrement;
import nz.co.gregs.dbvolution.annotations.DBColumn;
import nz.co.gregs.dbvolution.annotations.DBPrimaryKey;
import nz.co.gregs.dbvolution.annotations.DBRequiredTable;
import nz.co.gregs.dbvolution.datatypes.DBInteger;
import nz.co.gregs.dbvolution.datatypes.DBString;

/**
 *
 * @author greg
 */
@DBRequiredTable /* DBRequiredTable instructs the database to create this table if necessary */
public class Product extends DBRow{

	private static final long serialVersionUID = 1L;
	
	@DBPrimaryKey
	@DBAutoIncrement
	@DBColumn("item_id")
	public DBInteger itemID = new DBInteger();
	
	
	@DBColumn
	public DBString name = new DBString();
	
}
