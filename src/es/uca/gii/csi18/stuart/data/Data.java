package es.uca.gii.csi18.stuart.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import es.uca.gii.csi18.stuart.util.Config;

public class Data {

    public static String getPropertiesUrl() { return "./db.properties"; }
    
    public static Connection Connection() throws Exception {
    	
        try {
        	
            Properties properties = Config.Properties(getPropertiesUrl());
            
            return DriverManager.getConnection(
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password"));
       }
       catch (Exception ee) { throw ee; }
    }
    
    public static void LoadDriver() throws InstantiationException, IllegalAccessException, 
    	ClassNotFoundException, IOException {
    	
    	Class.forName(Config.Properties(Data.getPropertiesUrl()).getProperty(
    		"jdbc.driverClassName")).newInstance();
    }    
    
    public static String String2Sql(String s, boolean bAddQuotes, boolean bAddWildcards) {
    	
    	s = s.replace("'", "''");
    	
		if(bAddWildcards)
			s = "%" + s + "%";
		if(bAddQuotes)
			s = "'" + s + "'";
		return s;
    }
    
    public static int Boolean2Sql(boolean b) { return (b) ? 1 : 0; }
    
    public static int LastId(Connection con) throws Exception { 
    	ResultSet rs = null;
    	try {
    		String sQuery = Config.Properties(getPropertiesUrl()).getProperty("jdbc.lastIdSentence");
			rs = con.createStatement().executeQuery(sQuery);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException ee) {
			 throw ee;
		}
    }
}