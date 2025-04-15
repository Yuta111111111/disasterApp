package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

	private static final String NAME_DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/disasterdb?characterEncoding=UTF-8&serverTimeZone=JST";
	private static final String USER = "Recurrent6C";
	private static final String PASSWORD = "6cjava";

	
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName(NAME_DRIVER_MYSQL);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return conn;
	}
}

