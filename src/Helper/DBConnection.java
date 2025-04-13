//Enes Öner 220610026
//Yusuf Ilıca 220610025
//Mehmet Salih Demirci 220610007
//Göktuğ Çakıroğlu 220611008
//Cafer Aydın 220611035
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
