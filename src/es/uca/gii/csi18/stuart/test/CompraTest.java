package es.uca.gii.csi18.stuart.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.uca.gii.csi18.stuart.data.Compra;
import es.uca.gii.csi18.stuart.data.Data;
import es.uca.gii.csi18.stuart.data.Descuento;

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
		Descuento descuento = new Descuento(1);
		Compra compra = Compra.Create("compraTest", 9.99, descuento);
		assertEquals(11, compra.getId());
		assertEquals("compraTest", compra.getNombre());
		assertEquals(9.99, compra.getImporte());
	}
	
	@Test
	void testUpdate() throws Exception {
		
		String sNombre = "Dani";
		double dImporte = 0.01;
		Descuento descuento = new Descuento(1);
		Compra compra = Compra.Create("kike", 9.91, descuento);
		compra.setNombre(sNombre);
		compra.setImporte(dImporte);
		compra.Update();
		
		Connection con = null;
		ResultSet rs = null;
	    
	    try {  	
	    	 con = Data.Connection();
		     rs = con.createStatement().executeQuery("SELECT nombre, importe FROM compra "
		     		+ "WHERE id = " + compra.getId());
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
		
		String sNombre = "testSelect";
        double dImporte = 6.66;
        Connection con = null;
        ResultSet rs = null;

        try { 
             con = Data.Connection();
             Descuento descuento = new Descuento(1);
             Compra compra = Compra.Create(sNombre, dImporte, descuento);

             rs = con.createStatement().executeQuery("SELECT nombre, importe "
                     + "FROM compra WHERE nombre LIKE " + Data.String2Sql(sNombre, true, false));
             int iFilas = 0;
             while(rs.next())
                 iFilas++;
             assertEquals(iFilas, Compra.Select(sNombre, null, null).size());
             rs.close();

             rs = con.createStatement().executeQuery("SELECT nombre, importe "
                     + "FROM compra WHERE nombre LIKE " + Data.String2Sql(sNombre, true, false) + " AND importe = " +dImporte);
             iFilas = 0;
             while(rs.next())
                 iFilas++;
             assertEquals(iFilas, Compra.Select(sNombre, dImporte, null).size());
             rs.close();

             rs = con.createStatement().executeQuery("SELECT nombre, importe "
                     + "FROM compra WHERE importe = " +dImporte);
             iFilas = 0;
             while(rs.next())
                 iFilas++;
             assertEquals(iFilas, Compra.Select(null, dImporte, null).size());
             rs.close();

             rs = con.createStatement().executeQuery("SELECT nombre, importe "
                     + "FROM compra");
             iFilas = 0;
             while(rs.next())
                 iFilas++;
             assertEquals(iFilas, Compra.Select(null, null, null).size());
             rs.close();
             
             compra.Delete();
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (rs != null) rs.close();
            if (con != null) con.close();
        }
	}
	
	@Test
	void testDelete() throws Exception {
		Descuento descuento = new Descuento(1);
		Compra compra = Compra.Create("Pepe", 1.11, descuento);
		int iId = compra.getId();
		assertEquals(false, compra.getIsDeleted());
		compra.Delete();
		assertEquals(true, compra.getIsDeleted());
		
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT nombre, importe FROM "
					+ "compra WHERE id = " + iId + "");
			assertEquals(rs.next(), false);
		}
		catch (SQLException ee) { throw ee; }
	    finally {
	    	if (rs != null) rs.close();
	    	if (con != null) con.close();
	    }
	}
	
}
