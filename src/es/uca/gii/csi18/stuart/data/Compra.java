package es.uca.gii.csi18.stuart.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.omg.CosNaming._BindingIteratorStub;

import com.mysql.jdbc.Statement;

public class Compra{
	
	private int _iId;
	private String _sNombre;
	private double _dImporte;
	private boolean _bIsDeleted = false;
	
	public Compra(int iId) throws Exception {
		
		Connection con = null;
	    ResultSet rs = null;
	    
	    try {  	
	        con = Data.Connection();
	        rs = con.createStatement().executeQuery("SELECT nombre, importe FROM compra WHERE ID = " + iId);
	        rs.next();   
	        
	        _iId = iId;
	        _sNombre = rs.getString("nombre");
	        _dImporte = rs.getDouble("importe");

	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (rs != null) rs.close();
	    	if (con != null) con.close();
	    }
	}
	
	public int getId() { return _iId; }
	
	public String getNombre() { return _sNombre; }
	
	public double getImporte() { return _dImporte; }
	
	public boolean getIsDeleted() { return _bIsDeleted; }
	
	public void setNombre(String sNombre) { _sNombre = sNombre; }
	
	public void setImporte(double dImporte) { _dImporte = dImporte; }
	
	public String toString() {
		return super.toString() + ":" + _iId + ":" + _sNombre + ":" + _dImporte; 
	}
	
	/**
	 * @param sNombre
	 * @param dImporte
	 * @return
	 * @throws Exception
	 */
	public static Compra Create(String sNombre, double dImporte) throws Exception{
		
		Connection con = null;
		Statement stmt = null;
	    sNombre = Data.String2Sql(sNombre, true, false);
	     
	    try {  	
	        con = Data.Connection();
	        stmt = (Statement) con.createStatement();

	        stmt.executeUpdate("INSERT INTO compra (nombre, importe) VALUES (" + sNombre + ", " + dImporte + ")" );
	        
	        return new Compra(Data.LastId(con));
	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (stmt != null) stmt.close();
	    	if (con != null) con.close();
	    }
	}
	
	
	/**
	 * Eliminar instancia.
	 * @throws Exception
	 */
	public void Delete() throws Exception{
	    
		Connection con = null;
		Statement stmt = null;
		
		if(!_bIsDeleted) {
			_bIsDeleted = true;
		    try {  	
		        con = Data.Connection();
		        stmt = (Statement) con.createStatement();
		        
		        stmt.executeUpdate("DELETE FROM compra WHERE id = " + _iId);
		    }
		    catch (SQLException ee) { throw ee; }
		    finally {
		    	if (stmt != null) stmt.close();
		    	if (con != null) con.close();
		    }
		} else throw new Exception("La compra ya está eliminada.");
	}
	
	public void Update() throws Exception{
	    
		Connection con = null;
		Statement stmt = null;
		
		if(!_bIsDeleted) {
		    try {  	
		        con = Data.Connection();
		        stmt = (Statement) con.createStatement();
		        String sNombre = Data.String2Sql(_sNombre, true, false);
		        stmt.executeUpdate("UPDATE compra SET nombre = " + sNombre + 
		        		", importe = " + _dImporte + " WHERE id = " + _iId );
		    }
		    catch (SQLException ee) { throw ee; }
		    finally {
		    	if (stmt != null) stmt.close();
		    	if (con != null) con.close();
		    }
		} else throw new Exception("La compra está eliminada.");
	}
	
	public static ArrayList<Compra> Select (String sNombre, Double dImporte) throws Exception {
		
		ArrayList aCompra = new ArrayList<Compra>();
		Connection con = null;
		ResultSet rs = null;
	    
	    try {  	
	    	 con = Data.Connection();
	    	 //sNombre = Data.String2Sql(sNombre, true, false);
		     rs = con.createStatement().executeQuery("SELECT nombre, importe "
		    		 + "FROM compra" + Where(sNombre, dImporte));
		     while(rs.next())
		    	 aCompra.add(rs);
	         
		     return aCompra;
	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (rs != null) rs.close();
	    	if (con != null) con.close();
	    }
	}
	
	private static String Where(String sNombre, Double dImporte) {
		
		String sQuery = "";
		
		if(sNombre != null) 
			if(sNombre.contains("%") || sNombre.contains("?"))
				sQuery += "nombre = " + sNombre + " and ";
			else
				sQuery += "nombre LIKE " + sNombre + " and ";
		
		if(dImporte != null) 
			sQuery += "importe = " + dImporte + " and ";
		
		if(sQuery != null) 
			sQuery = " WHERE " + sQuery.substring(0, sQuery.length()-5);

		return sQuery;
	}
}
