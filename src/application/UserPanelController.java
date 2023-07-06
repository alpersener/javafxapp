package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserPanelController extends AdminPanelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label debtLabel;
    
    @FXML
    private Label debtLabel2;

    @FXML
    private Label debtLabel3;

    @FXML
    private Label label;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Button payButton;
    
    @FXML
    public Label isimLabel;

    @FXML
    private TextField paymentTextfield;
    
    @FXML
    private TextField verificationCodeTextField;
    
    @FXML
    private Button verifyButton;
    
    
    @FXML
    private Button borcButton;
    
    
    LoginPanelController loginPanelController= new LoginPanelController();
    
    DbHelper dbhelper=new DbHelper();
    
    Debt debt=new Debt();
   
       String query = "SELECT debts.Debt " +
               "FROM debts " +
               "JOIN useraccounts ON debts.Name = useraccounts.username " +
               "WHERE Useraccounts.username = ?";
       
   
       public void setUserName(String username) { //Hoşgeldin kısmı için isimlabel kısmını çekmek için.
    	    isimLabel.setText(username);
    	}      
       
       private int getDebtForUser(String userName) {
    	    
    	    try (Connection connection = dbhelper.getConnection();
    	         PreparedStatement statement = connection.prepareStatement(query)) {
    	        statement.setString(1, userName);
    	        try (ResultSet resultSet = statement.executeQuery()) {
    	            if (resultSet.next()) {
    	                return resultSet.getInt("Debt");
    	            } else {
    	                throw new Exception("Borç bilgisi bulunamadı");
    	            }
    	        }
    	    } catch (Exception e) {
    	        // handle exception
    	        return 0;
    	    }
    	}

    
       
      
       @FXML
       void borcClick(ActionEvent event) {
           try {
               Connection connection = dbhelper.getConnection();
               String query = "SELECT debts.Debt " +
                   "FROM debts " +
                   "JOIN useraccounts ON debts.Name = useraccounts.username " +
                   "WHERE Useraccounts.username = ?";
               PreparedStatement statement = connection.prepareStatement(query);
               statement.setString(1, isimLabel.getText());
               ResultSet resultSet = statement.executeQuery();
               if (resultSet.next()) {
                   String borc = resultSet.getString("debt");
                   debtLabel.setText(borc);
               }
           } catch (Exception e) {
               // TODO: handle exception
           }
       }
       
       @FXML
       void verifyButtonClick(ActionEvent event) {
    	   String userName = isimLabel.getText();
    	    String verificationCode = verificationCodeTextField.getText();
    	    
    	    if (validateVerificationCode(userName, verificationCode)) {
    	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	        alert.setTitle("Kod Doğrulaması");
    	        alert.setHeaderText("Kod Doğrulaması yapıldı!");
    	        alert.setContentText("Başarılı!");
    	        alert.show();
    	        
    	        // Ödeme butonunu etkinleştir
    	        payButton.setVisible(true);
    	    } else {
    	        Alert alert = new Alert(Alert.AlertType.WARNING);
    	        alert.setTitle("Kod Doğrulaması");
    	        alert.setHeaderText("Kod Doğrulaması yapılamadı!");
    	        alert.setContentText("Başarısız!");
    	        alert.show();
    	        
    	        
    	    }

       }
       
       
       private boolean validateVerificationCode(String userName, String verificationCode) {
    	    String query = "SELECT verifyCode FROM verificationCodes WHERE Username = ?";
    	    
    	    try (Connection connection = dbhelper.getConnection();
    	         PreparedStatement statement = connection.prepareStatement(query)) {
    	        statement.setString(1, userName);
    	        
    	        try (ResultSet resultSet = statement.executeQuery()) {
    	            if (resultSet.next()) {
    	                String correctCode = resultSet.getString("verifyCode");
    	                return verificationCode.equals(correctCode);
    	            }
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	    
    	    return false;
    	}
       
       
       
       @FXML
       void payClick(ActionEvent event) throws SQLException {
    	   String userName = isimLabel.getText();
    	   Connection connection=dbhelper.getConnection();
    	   int currentDebt = getDebtForUser(userName);
    	   int payment = Integer.parseInt(paymentTextfield.getText());
    	   if (payment > currentDebt) {
    	       Alert alert = new Alert(Alert.AlertType.WARNING);
    	       alert.setContentText("Ödenmek İstenen Tutar Borç'dan yüksek olamaz!");
    	       alert.show();
    	       return;
    	   } else {
    	       int newDebt = currentDebt - payment;
    	       String updateQuery = "UPDATE debts SET Debt = ? WHERE Name = ?";
    	       try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
    	           updateStatement.setInt(1, newDebt);
    	           updateStatement.setString(2, userName);
    	           updateStatement.executeUpdate();
    	           debtLabel.setText(String.valueOf(newDebt));
    	       } catch (Exception e) {
    	           // handle exception
    	       }
    	   }
       }


    @FXML
    void initialize() {
    	debtLabel2.setVisible(false);
    	debtLabel3.setVisible(false);
    	// Ödeme butonunu devre dışı bırak
        payButton.setVisible(false);
    	
    	
        
    	
    	
    	

    }

}


