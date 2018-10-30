package es.uca.gii.csi18.stuart.data;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class Compra{
	
	private int _iId;
	private String _sNombre;
	private double _dImporte;
	
	
	public Compra(int iId) throws Exception {
		
		Connection con = null;
	    ResultSet rs = null;
	    
	    try {  	
	        con = Data.Connection();
	        rs = con.createStatement().executeQuery("SELECT * FROM compras WHERE ID = " + iId);
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
	
	
	public double getId() { return _iId; }
	
	public String getNombre() { return _sNombre; }
	
	public double getImporte() { return _dImporte; }
	
	

	public void setNombre(String sNombre) { _sNombre = sNombre; }
	
	public void setImporte(double dImporte) { _dImporte = dImporte; }
	
	
	public String toString() {
		return super.toString() + ":" + _iId + ":" + _sNombre + ":" + _dImporte; 
	}
	
	public static Compra Create(String sNombre, double dImporte) throws Exception{
		
		Connection con = null;
		Statement stmt = null;
	    sNombre = Data.String2Sql(sNombre, true, false);
	    
	    
	    try {  	
	        con = Data.Connection();
	        int iId = Data.LastId(con) + 1;
	        stmt = (Statement) con.createStatement();

	        stmt.executeUpdate("INSERT INTO compras (id, nombre, importe) VALUES ("+ iId + ", " + sNombre + ", " + dImporte + ")" );
	        
	        return new Compra(iId);
	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (stmt != null) stmt.close();
	    	if (con != null) con.close();
	    }
	}
}
