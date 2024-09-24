package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DBException;

public class Program {
	public static void main(String[] args) {
		Connection conn = null;
		Statement st = null;
		
		try {
			conn = DB.getConnection();
			
			conn.setAutoCommit(false);
			
			st = conn.createStatement();
			
			int rows1 = st.executeUpdate("UPDATE seller SET base_salary = 2090 WHERE department_id = 1;");
			
			
			int rows2 = st.executeUpdate("UPDATE seller SET base_salary = 3090 WHERE department_id = 2;");
			
			conn.commit();
		}
		catch(SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transaction rolled back " + e.getMessage());
			} catch (SQLException e1) {
				throw new DBException("Error trying to roll back " + e1.getMessage());
			}
		}
		finally{
			DB.closeConnection();
			DB.closeStatement(st);
		}
	}
}
