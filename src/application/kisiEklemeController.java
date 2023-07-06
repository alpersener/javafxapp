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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class kisiEklemeController extends AdminPanelController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label debtLabel;

    @FXML
    private Button ekleButton;
   
   
    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private TextField txtfieldDebt;

    @FXML
    private TextField txtfieldName;

    @FXML
    private TextField txtfieldSurname;
    
    @FXML
    private TextField txtfieldpassword;

    @FXML
    private TextField txtfieldusername;
    
    //AdminPanelController ads=new AdminPanelController(); ?
    
    
    
    @FXML
    void buttonClick(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String name=txtfieldName.getText();
    	String surname=txtfieldSurname.getText();
    	String debt=txtfieldDebt.getText();
    	String kullaniciAd=txtfieldusername.getText();
    	String sifre=txtfieldpassword.getText();
    	DbHelper dbhelper=new DbHelper();
    	Connection connection=dbhelper.getConnection();
		  String query = "INSERT INTO debts (Name, Surname, Debt) VALUES (?, ?, ?)";
		  PreparedStatement statement = connection.prepareStatement(query);
		  statement.setString(1, name);
		  statement.setString(2, surname);
		  statement.setString(3, debt);
		  statement.executeUpdate();
		  String query2 = "INSERT INTO useraccounts (Username, Password) VALUES (?, ?)";
		  PreparedStatement statement2 = connection.prepareStatement(query2);
		  statement2.setString(1, kullaniciAd);
		  statement2.setString(2, sifre);
		  statement2.executeUpdate();
		  
		  
		  						
		  
		  String birlestirmeSorgusu="SELECT debts.Name, debts.Surname, debts.Debt, useraccounts.Password FROM debts JOIN useraccounts ON debts.Name = useraccounts.Username WHERE debts.Name = ?";
		  PreparedStatement birlestirPreparedStatement = connection.prepareStatement(birlestirmeSorgusu);
          birlestirPreparedStatement.setString(1, name);

          
          ResultSet sonucSeti = birlestirPreparedStatement.executeQuery();

          // Sonuçların işlenmesi
          while (sonucSeti.next()) {
              String kullaniciAdi = sonucSeti.getString("Name");
              String kullaniciSoyadi = sonucSeti.getString("Surname");
              double borcu = sonucSeti.getDouble("Debt");
              String sifre2 = sonucSeti.getString("Password");
              Alert alert=new Alert(AlertType.INFORMATION);
              alert.setTitle("BİLGİ");
              alert.setHeaderText("Kişi Eklendi.");
              alert.show();
              
              connection.close();
              System.out.println("Kullanıcı adı: " + kullaniciAdi);
              System.out.println("Kullanıcı soyadı: " + kullaniciSoyadi);
              System.out.println("Borcu: " + borcu);
              System.out.println("Şifre: " + sifre2);
          }
		
		  
		  
		  
		  
		  
		  
		  
    	
    	

    }

    @FXML
    void initialize() {
        
    }

}
