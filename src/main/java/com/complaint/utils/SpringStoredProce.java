package com.complaint.utils;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import com.complaint.model.GradekpiResults;

public class SpringStoredProce {
	
	public ResultSet execute(String storedProc, Map<String, Object> params){
		ResultSet rs = null;
		try{
			final DataSource ds = null;//new DataSourceInitializer();
			final JdbcTemplate template = new JdbcTemplate(ds);
			rs = (ResultSet)template.execute(new ProcCallableStatementCreator(storedProc, params),
					                                 new ProcCallableStatementCallback());
	    }catch(DataAccessException e){
	    	throw new RuntimeException("execute method error : DataAccessException " + e.getMessage());
	    }
	     return rs;
	}
	
	public ResultSet execute(String storedProc, Map<String,Object> params,DataSource ds){
		ResultSet rs = null;
		try{
			final JdbcTemplate template = new JdbcTemplate(ds);
			rs = (ResultSet)template.execute(new ProcCallableStatementCreator(storedProc, params),
					                                 new ProcCallableStatementCallback());
	    }catch(DataAccessException e){
	    	throw new RuntimeException("execute method error : DataAccessException " + e.getMessage());
	    }
	     return rs;
	}
	
	
	/**
	 * Create a callable statement in this connection.
	 */
	private class ProcCallableStatementCreator implements CallableStatementCreator {
		private String storedProc;
		private Map<String,Object> params;
		
	
		/**
		 * Constructs a callable statement.
		 * @param storedProc                 
		 * @param params                      
		 * @param outResultCount             
		 */
		public ProcCallableStatementCreator(String storedProc, Map<String,Object> params) {
			this.params = params;
			this.storedProc = storedProc;
		}
		
		
		/**
		 * Returns a callable statement
		 * @param conn          
		 * @return cs           
		 */
		public CallableStatement createCallableStatement(Connection conn) {
			StringBuffer storedProcName = new StringBuffer("call ");
			storedProcName.append(storedProc + "(");
			storedProcName.append("?");
			for(int i=0;i<params.size();i++){
			storedProcName.append(", ");
			storedProcName.append("?");
			}
			storedProcName.append(")");

			CallableStatement cs = null;
			try {

				cs = conn.prepareCall(storedProcName.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				cs.registerOutParameter (1, OracleTypes.CURSOR);
				int i=2;
				for(Map.Entry<String, Object> entry : params.entrySet()){
		    	cs.setObject(i, entry.getValue());
		    	i++;
				}
			} catch (SQLException e) {
				throw new RuntimeException("createCallableStatement method Error : SQLException " + e.getMessage());
			}
		    return cs;
		}
		
	}
	
	/**
	 * 
	 * The ProcCallableStatementCallback return a result object, 
	 * for example a collection of domain objects.
	 *
	 */
	private class ProcCallableStatementCallback implements CallableStatementCallback {
		
		/**
		 * Constructs a ProcCallableStatementCallback.
		 */
		public ProcCallableStatementCallback() {
			
		}

		/**
		 * Returns a List(Map) collection.
		 * @param cs                       
		 * @return resultsList            
		 */
		public Object doInCallableStatement(CallableStatement cs){
			ResultSet rs = null;
			try {
				cs.execute(); 
				rs = (ResultSet) cs.getObject(1);            
	        }catch(SQLException e) {
	        	throw new RuntimeException("doInCallableStatement method error : SQLException " + e.getMessage());
	        }
            return rs;
	   }
	}
}
