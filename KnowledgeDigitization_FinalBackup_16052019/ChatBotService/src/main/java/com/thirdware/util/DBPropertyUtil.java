package com.thirdware.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

public class DBPropertyUtil {
	
	
	public static   ArrayList<HashMap<String, Object>> retrieveDataBySQL(String query,Connection con){
		ResultSet resultSet = null;
		 Statement stmt =null;
		ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		try{
			  stmt = con.createStatement();
	       
			 resultSet = stmt.executeQuery(query);
	          
			 
	  		while (resultSet.next()) {
	  			HashMap<String, Object> resultsMap = new HashMap<String, Object>();
	  			ResultSetMetaData rsmd = resultSet.getMetaData();
	  			for(int i =1; i<=rsmd.getColumnCount();i++){
	  			 String name = rsmd.getColumnName(i);
	  			 Object value = resultSet.getObject(name);
	  			resultsMap.put(name, value);
	  			name = null;
				value=null;
	  			}
	  			resultList.add(resultsMap);
				resultsMap = null;
	  		}
	  		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null)
				resultSet.close();
				if(con != null)
					con.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultList;
		
	}
	
	public static   ArrayList<HashMap<String, Object>> retrieveDataBySQLProc(String query,Connection con){
        int val = 0;
        Statement stmt =null;
        ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        try{
                 stmt = con.createStatement();
        
                 val = stmt.executeUpdate(query);
           System.out.println(val);
               
               
        } catch (Exception e) {
               e.printStackTrace();
        }finally {
               try {
                     
                     if(con != null)
                            con.close();
                     
               } catch (SQLException e) {
                     e.printStackTrace();
               }
        }
        return resultList;
        
 }


	
}
