package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class LoginPanelController {
	
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label loginLabel;

    @FXML
    private Button loginbutton;

    @FXML
    private PasswordField passwordField;

    @FXML
    public TextField usernameTextField;
    
    private String username;
    
    @FXML
    private AnchorPane rootPane;

    
    
    @FXML
    public void button_click(ActionEvent event) throws IOException {
    	username=usernameTextField.getText();
    	
       	if(usernameTextField.getText().isBlank()==false && passwordField.getText().isBlank()==false) {
       		loginLabel.setText("You try to Login");
       		ValidateLogin();
       		     		     		
    		    	    	}
    	else {
    		loginLabel.setText("Kullanıcı adı ve Şifre Giriniz!");
    	}

    }
    public void ValidateLogin() throws IOException {
    	
    	DbHelper helper=new DbHelper();
    	Statement statement=null;
    	ResultSet resultSet;
    	Connection connection=null;
    	String username=usernameTextField.getText();
    	String password=passwordField.getText();
    	
    	
    	
    	String verifyLogin = "SELECT COUNT(1) FROM useraccounts WHERE username='"+usernameTextField.getText()+"' AND password='"+passwordField.getText()+"'";

    	try {
    	    connection = helper.getConnection();
    	    statement = connection.createStatement();
    	    resultSet = statement.executeQuery(verifyLogin);
    	    if (resultSet.next()) {//sorgu yapar ve sonuç kümesine girer ve sıradaki kayıda geçer
    	        int count = resultSet.getInt(1);//
    	        if (count == 1) {
    	            if (username.equalsIgnoreCase("ADMIN") && password.equals("admin")) {
    	                showAdminPanel();
    	            } else {
    	            	String checkUser = "SELECT COUNT(1) FROM useraccounts WHERE username='"+usernameTextField.getText()+"' AND password='"+passwordField.getText()+"'";
    	                ResultSet userResultSet = statement.executeQuery(checkUser);
    	                if (userResultSet.next() && userResultSet.getInt(1) == 1) {
    	                    showUserPanel();
    	                   
    	                } else {
    	                    loginLabel.setText("Please try again!");
    	                    Alert alert=new Alert(Alert.AlertType.ERROR);
    	                    alert.setContentText("Kullanıcı adı ya da şifreyi yanlış girdiniz!");
    	                    alert.show();
    	                }
    	            }
    	        } else {
    	            loginLabel.setText("Please try again!");
    	            Alert alert=new Alert(Alert.AlertType.ERROR);
    	            alert.setContentText("Kullanıcı adı ya da şifreyi yanlış girdiniz!");
    	            alert.show();
    	        }
    	    }
    	} catch(Exception exception){
    	    exception.printStackTrace();  
    	}


    
    }
    
    private void showAdminPanel() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Stage oldStage=(Stage)loginbutton.getScene().getWindow();
        oldStage.close();
    }
 
    private void showUserPanel() throws IOException {
    	
        String password = passwordField.getText();
    	FXMLLoader loader=new FXMLLoader(getClass().getResource("UserPanel.fxml"));
    	Parent root=loader.load();
    	UserPanelController userPanelController = loader.getController();
        userPanelController.setUserName(username);
       
    	 Scene Scene=new Scene(root);
    	Stage stage=new Stage();
    	stage.setScene(Scene);
    	stage.show();
    	Stage oldStage=(Stage)loginbutton.getScene().getWindow();
        oldStage.close();
    	
    	
    }
    

   
    
    
    
    
    
    	
    	
    	
   
}

 