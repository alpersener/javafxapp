package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper {
	LoginPanelController loginPanelController=new LoginPanelController();

	private String databasename="users";
	private String username="root";
	private String password="1234";
	private String url="jdbc:mysql://localhost:3306/"+databasename;
	public Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DriverManager.getConnection(url,username,password);}
	
	public void showErrorMessage(SQLException exception) throws IOException {
		System.out.println("ERROR:"+exception.getMessage());
		System.out.println("ERROR:"+exception.getErrorCode());
		loginPanelController.ValidateLogin();
	}
	
	
	
	
	

}
