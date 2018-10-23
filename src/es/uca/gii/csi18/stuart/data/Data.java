package es.uca.gii.csi18.stuart.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
    	
    	if( !bAddQuotes && !bAddWildcards) {	
    		return s;
    	}else if(bAddQuotes && !bAddWildcards) {
    		s = "'" + s + "'";
    		return s;
    	}else if(!bAddQuotes && bAddWildcards) {
    		s = "%" + s + "%";
    		return s;
    	}else{
    		// (bAddQuotes && bAddWildcards)
    		s = "'%" + s + "%'";
    		return s;
    	}
    }
    
    public static int Boolean2Sql(boolean b) { return (b) ? 1 : 0; }
    

}