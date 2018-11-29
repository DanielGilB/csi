package es.uca.gii.csi18.stuart.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import com.mysql.jdbc.Statement;

public class Compra{
	
	private int _iId;
	private String _sNombre;
	private double _dImporte;
	private boolean _bIsDeleted = false;
	private Descuento _descuento;
	
	public Compra(int iId) throws Exception {
		
		Connection con = null;
	    ResultSet rs = null;
	    
	    try {  	
	        con = Data.Connection();
	        rs = con.createStatement().executeQuery("SELECT nombre, importe, id_descuento FROM compra WHERE ID = " + iId);
	        rs.next();   
	        
	        _iId = iId;
	        _sNombre = rs.getString("nombre");
	        _dImporte = rs.getDouble("importe");
	        _descuento = new Descuento(rs.getInt("id_descuento"));
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
	
	public Descuento getDescuento() { return _descuento; }
	
	public void setNombre(String sNombre) { _sNombre = sNombre; }
	
	public void setImporte(double dImporte) { _dImporte = dImporte; }
	
	public void setDescuento(Descuento descuento) { _descuento = descuento; }
	
	public String toString() {
		return super.toString() + ":" + _iId + ":" + _sNombre + ":" + _dImporte + ";" + _descuento.toString() ; 
	}
	
	public static Compra Create(String sNombre, double dImporte, Descuento descuento) throws Exception{
		
		if(sNombre.isEmpty() || sNombre == null)
			throw new Exception("El nombre es un campo obligatorio");
		
		Connection con = null;
		Statement stmt = null;
	    sNombre = Data.String2Sql(sNombre, true, false);
	     
	    try {  	
	        con = Data.Connection();
	        stmt = (Statement) con.createStatement();

	        stmt.executeUpdate("INSERT INTO compra (nombre, importe, id_descuento) VALUES ("
	        		+ sNombre + ", " + dImporte + ", " + descuento.getId() + ")" );
	        
	        return new Compra(Data.LastId(con));
	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (stmt != null) stmt.close();
	    	if (con != null) con.close(); 
	    }
	}
	
	public void Delete() throws Exception{
	    
		if(_bIsDeleted)
			throw new Exception("La compra está eliminada.");
		
		Connection con = null;
		Statement stmt = null;
		
	    try {  	
	        con = Data.Connection();
	        stmt = (Statement) con.createStatement();
	        stmt.executeUpdate("DELETE FROM compra WHERE id = " + _iId);
	        _bIsDeleted = true;
	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (stmt != null) stmt.close();
	    	if (con != null) con.close();
	    }
	}
	
	public void Update() throws Exception{
	    
		if(_bIsDeleted) 
			throw new Exception("La compra está eliminada.");
		if(_sNombre.isEmpty() || _sNombre == null)
			throw new Exception("El nombre es un campo obligatorio");
		
		Connection con = null;
		Statement stmt = null;
		
		try {  	
	        con = Data.Connection();
	        stmt = (Statement) con.createStatement();
	        String sNombre = Data.String2Sql(_sNombre, true, false);
	        stmt.executeUpdate("UPDATE compra SET nombre = " + sNombre + 
	        		", importe = " + _dImporte + ", id_descuento = " + _descuento.getId() + " WHERE id = " + _iId );
	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (stmt != null) stmt.close();
	    	if (con != null) con.close();
	    }
	}
	
	public static ArrayList<Compra> Select (String sNombre, Double dImporte, String sDescuento) throws Exception {
		
		ArrayList<Compra> aCompra = new ArrayList<Compra>();
		Connection con = null;
		ResultSet rs = null;
	    
		if(sNombre != null) sNombre = Data.String2Sql(sNombre, true, false);
		if(sDescuento != null) sNombre = Data.String2Sql(sDescuento, true, false);
	    try {  	
	    	 con = Data.Connection();
		     rs = con.createStatement().executeQuery("SELECT id, nombre, importe, id_descuento "
		    		 + "FROM compra JOIN descuento ON descuento.id = compra.id_descuento " + Where(sNombre, dImporte, sDescuento));
		     while(rs.next())
		    	 aCompra.add(new Compra(rs.getInt("id")));

		     return aCompra;
	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (rs != null) rs.close();
	    	if (con != null) con.close();
	    }
	}
	
	private static String Where(String sNombre, Double dImporte, String sDescuento) {
		
		String sQuery = "";

		if(sNombre != null) 
			if(sNombre.contains("%") || sNombre.contains("?"))
				sQuery += "nombre = " + sNombre + " and ";
			else
				sQuery += "nombre LIKE " + sNombre + " and "; 
		
		if(sDescuento != null) 
			if(sDescuento.contains("%") || sDescuento.contains("?"))
				sQuery += "nombre = " + sDescuento + " and ";
			else
				sQuery += "nombre LIKE " + sDescuento + " and ";
		
		if(dImporte != null) 
			sQuery += "importe = " + dImporte + " and ";
		
		if(!sQuery.isEmpty()) 
			sQuery = " WHERE " + sQuery.substring(0, sQuery.length()-5);
		
		return sQuery;
	}
}
