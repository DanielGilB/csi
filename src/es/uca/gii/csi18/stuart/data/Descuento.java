package es.uca.gii.csi18.stuart.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Descuento{
	
	private int _iId;
	private String _sNombre;
	
	public Descuento(int iId) throws Exception {
		
		Connection con = null;
	    ResultSet rs = null;
	    
	    try {  	
	        con = Data.Connection();
	        rs = con.createStatement().executeQuery("SELECT nombre FROM descuento WHERE ID = " + iId);
	        rs.next();   
	        
	        _iId = iId;
	        _sNombre = rs.getString("nombre");
	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (rs != null) rs.close();
	    	if (con != null) con.close();
	    }
	}
	
	public int getId() { return _iId; }
	
	public String getNombre() { return _sNombre; }
	
	public void setNombre(String sNombre) { _sNombre = sNombre; }
	
	public String toString() {
		return _sNombre;
	}
	
	public static ArrayList<Descuento> Select() throws Exception {
		
		ArrayList<Descuento> aDescuento = new ArrayList<Descuento>();
		Connection con = null;
		ResultSet rs = null;
	    
	    try {  	
	    	 con = Data.Connection();
		     rs = con.createStatement().executeQuery("SELECT id, nombre FROM descuento"
		     		+ " ORDER BY nombre;");
		     while(rs.next())
		    	 aDescuento.add(new Descuento(rs.getInt("id")));

		     return aDescuento;
	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (rs != null) rs.close();
	    	if (con != null) con.close();
	    }
	}
}
