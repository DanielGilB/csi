package es.uca.gii.csi18.stuart.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.uca.gii.csi18.stuart.data.Compra;
import es.uca.gii.csi18.stuart.data.Data;

class CompraTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception { Data.LoadDriver(); }
	
	@Test
	void testConstructor() throws Exception {
		Compra compra = new Compra(1);
		assertEquals(1, compra.getId());
		assertEquals("compra1", compra.getNombre());
		assertEquals(12.99, compra.getImporte());
	}

	@Test
	void testCreate() throws Exception {
		Compra compra = Compra.Create("compraTest", 9.99);
		assertEquals(11, compra.getId());
		assertEquals("compraTest", compra.getNombre());
		assertEquals(9.99, compra.getImporte());
	}
	
	@Test
	void testUpdate() throws Exception {
		
		String sNombre = "Dani";
		double dImporte = 0.01;
		Compra compra = Compra.Create("kike", 9.91);
		compra.setNombre(sNombre);
		compra.setImporte(dImporte);
		compra.Update();
		
		Connection con = null;
		ResultSet rs = null;
	    
	    try {  	
	    	 con = Data.Connection();
		     rs = con.createStatement().executeQuery("SELECT nombre, importe FROM compra WHERE id = "
		    	+ compra.getId());
		     rs.next();
		     
		    assertEquals(sNombre, rs.getString("nombre"));
			assertEquals(dImporte, rs.getDouble("importe"));
			
			compra.Delete();
	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (rs != null) rs.close();
	    	if (con != null) con.close();
	    }
	}
	
	@Test
	void testSelect() throws Exception {
		
	}
	
	@Test
	void testDelete() throws Exception {
		Compra compra = Compra.Create("Pepe", 5.5);
		compra.Delete();
		//assertEquals(expected, actual);
	}
}
