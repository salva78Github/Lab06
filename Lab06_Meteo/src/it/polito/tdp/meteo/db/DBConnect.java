package it.polito.tdp.meteo.db;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnect {

	static private final String jdbcUrl = "jdbc:mysql://localhost/meteo?user=root&password=salva_root";
	static private DBConnect instance = null;

	private DBConnect() {
		instance = this;
	}

	public static DBConnect getInstance() {
		if (instance == null)
			return new DBConnect();
		else {
			return instance;
		}
	}

	public Connection getConnection() {
		try {

			Connection conn = DriverManager.getConnection(jdbcUrl);
			return conn;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot get connection " + jdbcUrl, e);
		}
	}
	
	public void closeResources(Connection conn, PreparedStatement st, ResultSet rs) {
		// TODO Auto-generated method stub
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
