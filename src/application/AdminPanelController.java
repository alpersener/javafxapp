package application;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader; 
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminPanelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private TextField idtxtfield;
    
    @FXML
    private TextField surnametxtfield;

    @FXML
    public TextField debttxtfield;
    
    @FXML
    private TextField nametxtfield;
    
    @FXML
    private TableView<Debt> tableView;
    
    @FXML
    private TableView<UserAccounts> tableViewAccounts;
    
    @FXML
    private TableView<VerificationCode> tableViewCodes;

    @FXML
    private TextField aramaTextField;

    @FXML
    private Button kEkleButton;
    
    @FXML
    private Button guncelleButton;
    
    @FXML
    private Button borcEkleButton;

    @FXML
    private Button kSilButton;

    @FXML
    private Label label;
    
    @FXML
    private Button userSilButton;
    
    @FXML
    private TextField usernametxtfield;
    
    private ObservableList<Debt> debts;
    
    private ObservableList<UserAccounts> userAccounts;
    
    private ObservableList<VerificationCode> verificationCodes;


    
    DbHelper dbhelper=new DbHelper();
	String url="jdbc:mysql://localhost:3306/users";
    String username="root";
    String password="1234";
    
 
    
    
    
    @FXML
    void ekleClick(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("kisiEkleme.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    	

    }
   
    @FXML
    void borcEkleClick(Event event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("BorcEkle.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    	
    	
    	
    }
    

    
 
    @FXML
    void silClick(ActionEvent event) {
    	deleteData();
    	

    }
    
    @FXML
    void guncelleClick(ActionEvent event) {
    	 getUpdatedData(); 
    	 getUpdatedDataFromUser();
    	 }
    
    
    
    @FXML
    void userSilClick(ActionEvent event) {
    	deleteDataFromUser();

    }
    
    public void getUpdatedData() {
        try {
        	Connection connection=dbhelper.getConnection();
            String query="select * from debts";
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            debts.clear(); //

            while (resultSet.next()) {
                Debt debt = new Debt();
                debt.setID(resultSet.getInt("id"));
                debt.setName(resultSet.getString("name"));
                debt.setSurname(resultSet.getString("surname"));
                debt.setDebt(resultSet.getInt("debt"));
                debts.add(debt);
            }
            tableView.setItems(debts);


        } catch (Exception e) {
            System.err.println("Hata: " + e.getMessage());
        }
    }
    
    private void deleteData() {
    	
    	try {
			Connection connection=dbhelper.getConnection();
			String query="delete from debts where ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, idtxtfield.getText());
			statement.execute();		
			getUpdatedData();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
   public void deleteDataFromUser(){
	   Connection connection;
	try {
		connection = dbhelper.getConnection();
		String query="delete from useraccounts where username=?";
		PreparedStatement statement=connection.prepareStatement(query);
		statement.setString(1, usernametxtfield.getText());
		statement.execute();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

   }
   
   public void getUpdatedDataFromUser() {
       try {
       	Connection connection=dbhelper.getConnection();
           String query="select * from useraccounts";
           Statement statement=connection.createStatement();
           ResultSet resultSet=statement.executeQuery(query);
           userAccounts.clear();
           while (resultSet.next()) {
        	   UserAccounts obj=new UserAccounts();
        	   obj.setID(resultSet.getInt("id"));
        	   obj.setUsername(resultSet.getString("username"));
        	   obj.setPassword(resultSet.getString("password"));
        	   userAccounts.add(obj);            
           }
           tableViewAccounts.setItems(userAccounts);

         
       } catch (Exception e) {
           System.err.println("Hata: " + e.getMessage());
       }
   }
    
    
  @FXML
  void initialize() {
	  try {
		  
		  Connection connection=dbhelper.getConnection();
		  String query="select * from debts";
		  Statement statement=connection.createStatement();
		  ResultSet resultSet=statement.executeQuery(query);
		  
		  TableColumn<Debt, Integer> idColumn=new TableColumn<>("ID"); //tablecolumnları oluşturma
		  TableColumn<Debt, String> nameColumn=new TableColumn<>("Name");
		  TableColumn<Debt, String> surnameColumn=new TableColumn<>("Surname");
		  TableColumn<Debt, Integer> debtColumn=new TableColumn<>("Debt");
		  
		  
		  idColumn.setCellValueFactory(new PropertyValueFactory<>("ID")); //idColumn sütunu için id alanından veri al demek aşağıdakilerde aynısı demek
		  nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
		  surnameColumn.setCellValueFactory(new PropertyValueFactory<>("Surname"));
		  debtColumn.setCellValueFactory(new PropertyValueFactory<>("Debt"));
		  
		 
		  tableView.getColumns().add(idColumn);
		  tableView.getColumns().add(nameColumn);
		  tableView.getColumns().add(surnameColumn);
		  tableView.getColumns().add(debtColumn);
		  
		  debts=FXCollections.observableArrayList(); //debts nesnesi oluşturarak liste oluşturup sırayla atarız.
		  
		  while (resultSet.next()) {
	            Debt debt = new Debt();
	            debt.setID(resultSet.getInt("id"));
	            debt.setName(resultSet.getString("name"));
	            debt.setSurname(resultSet.getString("surname"));
	            debt.setDebt(resultSet.getInt("debt"));
	            debts.add(debt);
	            
	        }

	        tableView.setItems(debts);//verileri görüntülemekj için.
	         
	        
	         aramaTextField.setOnKeyReleased(e -> {
	        	 
	             if (aramaTextField.getText().trim().isEmpty()) {
	               
	                 tableView.setItems(debts);
	             } else {
	                
	                 ObservableList<Debt> filteredList = FXCollections.observableArrayList();
	                 String searchText = aramaTextField.getText().toLowerCase();

	                 for (Debt debt : debts) {
	                     if (debt.getName().toLowerCase().contains(searchText) ||
	                             debt.getSurname().toLowerCase().contains(searchText)) {
	                         filteredList.add(debt);
	                     }
	                 }

	                 tableView.setItems(filteredList);
	             }
	         });
	        	
	        
	         tableView.setOnMouseClicked(e -> {
	        	    if (e.getClickCount() == 1) { // Sadece tek tıklama ile tepki veriyoruz
	        	    	Debt selected = tableView.getSelectionModel().getSelectedItem();
	        	    	
	        	    	if (selected != null) {
	        	    	    Integer id = selected.getID(); //id için 
	        	    	    idtxtfield.setText(id.toString());
	        	    	    
	        	    	    String name=selected.getName(); //name için
	        	    	    nametxtfield.setText(name.toString());
	        	    	    
	        	    	    String surname=selected.getSurname(); //surname için
	        	    	    surnametxtfield.setText(surname.toString());
	        	    	    
	        	    	    Integer debt=selected.getDebt(); //debt için.
	        	    	    debttxtfield.setText(debt.toString());
	        	    	           	    	  
	        	    	    
	        	    	}

	        	    }
	        	});
	         tableViewAccounts.setOnMouseClicked(e ->{
	        	 UserAccounts selected2=tableViewAccounts.getSelectionModel().getSelectedItem();
	        	 if (selected2 != null) {   	    	   
     	    	    String kullaniciadi=selected2.getUsername();
     	    	    usernametxtfield.setText(kullaniciadi.toString());  
     	    	}
	         });
	         
	         
	         
	         Connection connection2=dbhelper.getConnection();
	         String query2="select * from useraccounts";
			  Statement statement2=connection2.createStatement();
			  ResultSet resultSet2=statement2.executeQuery(query2);
			  
			  TableColumn<UserAccounts, Integer> idColumn2=new TableColumn<>("ID"); //tablecolumnları oluşturma
			  TableColumn<UserAccounts, String> usernameColumn=new TableColumn<>("username");
			  TableColumn<UserAccounts, String> passwordColumn=new TableColumn<>("password");
			 
			  
			  
			  idColumn2.setCellValueFactory(new PropertyValueFactory<>("ID")); 
			  usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
			  passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
			 
			  
			 
			  tableViewAccounts.getColumns().add(idColumn2);
			  tableViewAccounts.getColumns().add(usernameColumn);
			  tableViewAccounts.getColumns().add(passwordColumn);
			 
			  
			  userAccounts=FXCollections.observableArrayList(); //userAccounts nesnesi oluşturarak liste oluşturup sırayla atarız.
			  
			  while (resultSet2.next()) {
				  UserAccounts obj=new UserAccounts();
				  obj.setID((resultSet2.getInt("id")));
				  obj.setUsername(resultSet2.getString("username"));
				  obj.setPassword(resultSet2.getString("password"));
				  userAccounts.add(obj);
		        }
		        tableViewAccounts.setItems(userAccounts);         
	       
		       
		        Connection connection3=dbhelper.getConnection();
		        String query3="SELECT * FROM verificationcodes";
				Statement statement3=connection3.createStatement();
				ResultSet resultSet3=statement3.executeQuery(query3);
				
				TableColumn<VerificationCode,String> codeColumn=new TableColumn<>("verifyCode");
				TableColumn<VerificationCode,String> usernameColumn2=new TableColumn<>("username");
				
				codeColumn.setCellValueFactory(new PropertyValueFactory<>("verifyCode"));
				usernameColumn2.setCellValueFactory(new PropertyValueFactory<>("username"));
				
				tableViewCodes.getColumns().add(codeColumn);
				tableViewCodes.getColumns().add(usernameColumn2);
				
				verificationCodes=FXCollections.observableArrayList();
				
				while(resultSet3.next()) {
					VerificationCode code=new VerificationCode();
					code.setVerifyCode(resultSet3.getString("verifyCode"));
					code.setUsername(resultSet3.getString("username"));
					verificationCodes.add(code);
				}
				tableViewCodes.setItems(verificationCodes);
				
		  
	        
	        
	       
				  
		  
		  
		
	} catch (Exception e) {
		System.err.println("Hata: " + e.getMessage());
	}
  }
       

   
}

