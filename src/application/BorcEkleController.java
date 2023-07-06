package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class BorcEkleController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ekleButton;

    @FXML
    private TextField textField;
    
    @FXML
    private TextField textFieldName;
    
    @FXML
    private TextField textFieldSurname;
    
    

  DbHelper dbhelper=new DbHelper();
  
 	String url="jdbc:mysql://localhost:3306/users";
     String username="root";
     String password="1234";
     String query = "SELECT debts.Debt " +
             "FROM debts " +
             "JOIN useraccounts ON debts.Name = useraccounts.username " +
             "WHERE Useraccounts.username = ?";
  

    
    
    
    
    @FXML
    void ekleClick(ActionEvent event) {
    	try {
    		String username=textFieldName.getText();
    		String surname=textFieldSurname.getText();
    		 Connection connection = dbhelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             statement.setString(1, username);
             ResultSet resultSet = statement.executeQuery();
             if (resultSet.next()) {
                 int currentDebt = resultSet.getInt("debt");
                 int payment = Integer.parseInt(textField.getText());
                 int newDebt = currentDebt + payment;              
                 String updateQuery = "UPDATE debts SET Debt = ? WHERE Name = ? AND Surname= ?";
                 PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                 updateStatement.setInt(1, newDebt);
                 updateStatement.setString(2, username);
                 updateStatement.setString(3, surname);
                 updateStatement.executeUpdate();
                 Alert alert=new Alert(AlertType.INFORMATION);
                 alert.setTitle("BİLGİ");
                 alert.setHeaderText("Borç Eklendi.");
                 alert.show();
                 
                 
             }
	 
	 
	 
	 
	 
	 
	 
	
    		}catch (Exception e) {
	// TODO: handle exception
    		}
    	
    	
    	
    	
    }

    @FXML
    void initialize() {
    
       

    }

}
