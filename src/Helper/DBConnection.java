package Helper;
import java.sql.*;

public class DBConnection {
	Connection con=null;
	
	public DBConnection() {}
	
	public Connection connDB()
	{
		try {
			this.con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/dbbilet?user=root&password=12345");
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
