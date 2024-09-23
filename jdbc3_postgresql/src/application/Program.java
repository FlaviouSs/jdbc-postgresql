package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {
	public static void main(String[] args) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Connection conn = null;
			PreparedStatement st = null;
			try {
				conn = DB.getConnection();
				
				st = conn.prepareStatement(
							"INSERT INTO seller "
							+ "(name, email, birth_date, base_salary, department_id) "
							+ "VALUES (?, ?, ?, ?, ?);", 
							Statement.RETURN_GENERATED_KEYS);
				
				st.setString(1, "Kevin Evelen");
				st.setString(2, "kevin@gmail.com");
				st.setDate(3, new java.sql.Date(sdf.parse("18/05/2004").getTime()));
				st.setDouble(4, 3500.0);
				st.setInt(5, 2);
				
				/*
				st = conn.prepareStatement("INSERT INTO department (name) VALUES ('D1'), ('D2');",
						Statement.RETURN_GENERATED_KEYS);
				*/
				int rowsAffected = st.executeUpdate();

				if(rowsAffected > 0) {
					ResultSet rs = st.getGeneratedKeys();
					while(rs.next()) {
						int id = rs.getInt(1);
						System.out.println("Done! Id = " + id);
					}
				}
				
			}
			catch(SQLException e) {
				e.getStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			finally {
				DB.closeStatement(st);
				DB.closeConnection();
			}
		
	}
}
