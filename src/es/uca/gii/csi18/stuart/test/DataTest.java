package es.uca.gii.csi18.stuart.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.uca.gii.csi18.stuart.data.Data;

class DataTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception { Data.LoadDriver(); }

	@Test
	void testTableAccess() throws Exception {
	    
		Connection con = null;
	    ResultSet rs = null;
	    
	    try {  	
	        con = Data.Connection();
	        rs = con.createStatement().executeQuery("SELECT * FROM compras;");
	        
	        int i = 0;	        
	        while (rs.next()) {	        	
	        	System.out.println(rs.getString("id") + " " + rs.getString("nombre") + " " + rs.getString("importe"));
	        	i++;
	        }
	        
	        assertEquals(3, rs.getMetaData().getColumnCount());
	        assertEquals(2, i);
	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (rs != null) rs.close();
	    	if (con != null) con.close();
	    }
	}
	
	@Test
	void testString2Sql() {
		
		assertEquals(Data.String2Sql("hola", false, false), "hola");
		assertEquals(Data.String2Sql("hola", true, false), "'hola'");
		assertEquals(Data.String2Sql("hola", false, true), "%hola%");
		assertEquals(Data.String2Sql("hola", true, true), "'%hola%'");
		
		assertEquals(Data.String2Sql("O'Connell", false, false), "O''Connell");
		assertEquals(Data.String2Sql("O'Connell", true, false), "'O''Connell'");
		
		assertEquals(Data.String2Sql("'Smith'", false, true), "%''Smith''%");
		assertEquals(Data.String2Sql("'Smith'", true, false), "'''Smith'''");
		assertEquals(Data.String2Sql("'Smith'", true, true), "'%''Smith''%'");
		
	}
	
	
	@Test
	void testBoolean2Sql() {
		
		assertEquals(1, Data.Boolean2Sql(true));
		assertEquals(0, Data.Boolean2Sql(false));
		
	}

}
