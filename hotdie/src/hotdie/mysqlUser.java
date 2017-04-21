package hotdie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class mysqlUser {
	public static String driver="com.mysql.jdbc.Driver";
	public static String url="jdbc:mysql://localhost:3306/hotdie?useUnicode=true&characterEncoding=UTF-8&verifyServerCertificate=false&useSSL=false&autoReconnect=true";
	public static String user="root";
	public static String password="";
	public static void User(){
			ResourceBundle rB = ResourceBundle.getBundle("1");
			url  = rB.getString("url");
			user  = rB.getString("user");
			password  = rB.getString("password");
	}

	public static boolean checkConnection(Connection conn) {
		try {
			Statement pingStatement = null;
			try {
				pingStatement = conn.createStatement();
				pingStatement.executeQuery("SELECT 1").close();//测试是否超时
			} finally {
				if (pingStatement != null)
					pingStatement.close();
			}
			return false;
		} catch (Exception e) {
			return true;
		}
	}
	public static void over(ResultSet rs, Statement stat, Connection con){
		try {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}