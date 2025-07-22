import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Staff extends HospitalManagement {
	private String id;
	private String name;
	private String designation;
	private String sex;
	private int salary;
	
	public Staff() {
		id = "";
		name = "";
		designation = "";
		sex = "";
		salary = 0;
	}
	
	public Staff(String id, String name, String designation, String sex, int salary) {
		this.id = id;
		this.name = name;
		this.designation = designation;
		this.sex = sex;
		this.salary = salary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public BorderPane newStaff() {
		Label lbId = createLabel("ID");
		Label colon1 = createLabel(":");
		TextField inputId = new TextField();
		inputId.setPromptText("i.e. S001");
		
		Label lbName = createLabel("Name");
		Label colon2 = createLabel(":");
		TextField inputName = new TextField();
		
		Label lbDesignation = createLabel("Designation");
		Label colon3 = createLabel(":");
		
        String[] designationOptions = {
        		"Nurse", "Technician", "Administrative Assistant"
        };
        
        ComboBox<String> inputDesignation = new ComboBox<>(FXCollections.observableArrayList(designationOptions));
        inputDesignation.setValue("Nurse");
        
        Label lbSex = createLabel("Sex");
		Label colon4 = createLabel(":");
        String[] sex = {"Male", "Female"};
        ComboBox<String> inputSex = new ComboBox<>(FXCollections.observableArrayList(sex));
        inputSex.setValue("Male");
		
		Label lbSalary = createLabel("Salary");
		Label colon5 = createLabel(":");
		TextField inputSalary = new TextField();
		
		Button btRegister = createBtn("Register");
		Button btClear = createBtn("Clear");
		
		Text newStaffMsg = new Text("New Staff");
		newStaffMsg.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 100px; -fx-font-weight: bold; -fx-fill: white; -fx-stroke-width: 3;");
		
		// Create GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);
        
        // Add components to GridPane
        gridPane.add(lbId, 0, 0);
        gridPane.add(colon1, 1, 0);
        gridPane.add(inputId, 2, 0);
        
        gridPane.add(lbName, 0, 1);
        gridPane.add(colon2, 1, 1);
        gridPane.add(inputName, 2, 1);
        
        gridPane.add(lbDesignation, 0, 2);
        gridPane.add(colon3, 1, 2);
        gridPane.add(inputDesignation, 2, 2);
        
        gridPane.add(lbSex, 0, 3);
        gridPane.add(colon4, 1, 3);
        gridPane.add(inputSex, 2, 3);
        
        gridPane.add(lbSalary, 0, 4);
        gridPane.add(colon5, 1, 4);
        gridPane.add(inputSalary, 2, 4);
		
		HBox hbox = new HBox(20);
		hbox.getChildren().addAll(btRegister, btClear);
		hbox.setAlignment(Pos.CENTER);
		
		gridPane.add(hbox, 0, 8, 3, 1);
		gridPane.setPadding(new Insets(20,0,0,0));
		
		gridPane.setBorder(new Border(new BorderStroke(
                Color.WHITE, // Border color
                BorderStrokeStyle.SOLID, // Border style
                new CornerRadii(10), // Corner radius
                new BorderWidths(2)))); // Border width
		
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(30));
		borderPane.setCenter(gridPane);
		borderPane.setTop(newStaffMsg);
		BorderPane.setAlignment(newStaffMsg, Pos.CENTER);
		BorderPane.setAlignment(gridPane, Pos.CENTER);
		
		BorderPane.setMargin(gridPane, new Insets(0,150,0,150)); 
		
		btRegister.setOnAction(e -> {
		    try {
		        // Validate that all required fields are filled
		        if (inputId.getText().trim().isEmpty() ||
			            inputName.getText().trim().isEmpty() ||
			            inputSalary.getText().trim().isEmpty()) {

		            throw new IllegalArgumentException("All fields are required.");
		        }
		        if (inputId.getText().length() != 4 ||
					inputId.getText().charAt(0) != 'S' || 
					!Character.isDigit(inputId.getText().charAt(1)) || 
					!Character.isDigit(inputId.getText().charAt(2)) ||
					!Character.isDigit(inputId.getText().charAt(3))) {
					throw new IllegalArgumentException("ID must start with 'S' followed by 3 digits. Example: S001");
				}
		        if(Integer.parseInt(inputSalary.getText()) < 0) {
		        	throw new IllegalArgumentException("Salary field value MUST be a positive integer.");
		        }

		     // Set the values if all fields are filled
		        this.setId(inputId.getText());
		        this.setName(inputName.getText());
		        this.setDesignation(inputDesignation.getValue());
		        this.setSex(inputSex.getValue());
		        this.setSalary(Integer.parseInt(inputSalary.getText()));

		        // Add the doctor to the list
		        staff.add(this);
		        
		        Text successMessage = new Text("New Staff Added Successfully!");
				successMessage.setStyle("-fx-fill: #66BB6A; -fx-font-size: 50; -fx-font-weight: bold;");
				
				StackPane pane = new StackPane(successMessage);
		        borderPane.setCenter(pane);

		    } catch (NumberFormatException ex) {
		        // Show an alert if number conversion fails
		        showAlert("Input Error", "Please enter valid number in Salary field.");
		    } catch (IllegalArgumentException ex) {
		        // Show an alert if any field is empty or invalid
		        showAlert("Input Error", ex.getMessage());
		    }
		});
		
		btClear.setOnAction(e -> {
            // Clear all the TextFields
			inputId.clear();
	        inputName.clear();
	        inputDesignation.setValue("Nurse");
	        inputSex.setValue("Male");
	        inputSalary.clear();
        });
		
		return borderPane;
	}
	
	
	
	public static TableView<Staff> showStaffInfo() {
		// Create a TableView
        TableView<Staff> staffTable = new TableView<>();
        
        // Create columns
        TableColumn<Staff, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Staff, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Staff, String> designationColumn = new TableColumn<>("Designation");
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));

        TableColumn<Staff, String> sexColumn = new TableColumn<>("Sex");
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));

        TableColumn<Staff, String> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        // Add columns to the TableView
        staffTable.getColumns().add(idColumn);
        staffTable.getColumns().add(nameColumn);
        staffTable.getColumns().add(designationColumn);
        staffTable.getColumns().add(sexColumn);
        staffTable.getColumns().add(salaryColumn);
        
        // Create an ObservableList and add sample data
        ObservableList<Staff> staffData = FXCollections.observableArrayList(staff);
        
        // Set up the layout
        staffTable.setItems(staffData);
        
        return staffTable;
	}
	
	public static void searchStaff(TableView<Staff> table) {
		TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Find Staff");
        dialog.setHeaderText("Find Staff by ID");
        dialog.setContentText("Please enter Staff ID: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(id -> {
            for (Staff item : staff) {
                if (item.getId().equalsIgnoreCase(id)) {
                	int index = staff.indexOf(item);
                    table.getSelectionModel().select(index);
                    table.scrollTo(index);
                    break;
                }
            }
        });
	}
	
	public static void modifyStaff(TableView<Staff> table) {
         try {
        	 Staff selected = staff.get(table.getSelectionModel().getSelectedIndex());
        	 
        	 //Create the custom dialog
             Dialog<Staff> dialog = new Dialog<>();
             dialog.setTitle("Modify Staff");
             dialog.setHeaderText("Modify Staff Information");
             
             // Create the grid pane for input fields
             GridPane gp = new GridPane();
             gp.setHgap(10);
             gp.setVgap(10);
             gp.setPadding(new Insets(20, 150, 10, 10));
             
             // Create input fields
             TextField idField = new TextField(selected.getId());
             TextField nameField = new TextField(selected.getName());
             TextField designationField = new TextField(selected.getDesignation());
             String[] sex = {"Male", "Female"};
             ComboBox<String> sexField = new ComboBox<>(FXCollections.observableArrayList(sex));
             sexField.setValue(selected.getSex());
             sexField.setEditable(false);
             TextField salaryField = new TextField(selected.getSalary() + "");
             
             // Add input fields to the grid
             gp.add(new Label("ID:"), 0, 0);
             gp.add(idField, 1, 0);
             gp.add(new Label("Name:"), 0, 1);
             gp.add(nameField, 1, 1);
             gp.add(new Label("Designation:"), 0, 2);
             gp.add(designationField, 1, 2);
             gp.add(new Label("Sex:"), 0, 3);
             gp.add(sexField, 1, 3);
             gp.add(new Label("Salary:"), 0, 4);
             gp.add(salaryField, 1, 4);
             
             // Enable the OK button by default
             ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
             dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

             // Add grid to the dialog
             dialog.getDialogPane().setContent(gp);
             
             // Convert the result to a Staff object when OK is pressed
             dialog.setResultConverter(dialogButton -> {
                 if (dialogButton == okButtonType) {
                	 try {
         		        // Validate that all required fields are filled
         		        if (idField.getText().trim().isEmpty() ||
         			            nameField.getText().trim().isEmpty() ||
         			            salaryField.getText().trim().isEmpty()) {
         		            throw new IllegalArgumentException("All fields are required.");
         		        }
         		        if (idField.getText().length() != 4 ||
         					idField.getText().charAt(0) != 'S' || 
         					!Character.isDigit(idField.getText().charAt(1)) || 
         					!Character.isDigit(idField.getText().charAt(2)) ||
         					!Character.isDigit(idField.getText().charAt(3))) {
         					throw new IllegalArgumentException("ID must start with 'S' followed by 3 digits. Example: S001");
         				}
         		        if(Integer.parseInt(salaryField.getText()) < 0) {
         		        	throw new IllegalArgumentException("Salary field value MUST be a positive integer.");
         		        }
         		        return new Staff(idField.getText(), nameField.getText(), designationField.getText(), sexField.getValue(), Integer.parseInt(salaryField.getText()));
         		        
         		    } catch (NumberFormatException ex) {
         		        // Show an alert if number conversion fails
         		        showAlert("Input Error", "Please enter valid numbers in Salary field.");
         		    } catch (IllegalArgumentException ex) {
         		        // Show an alert if any field is empty or invalid
         		        showAlert("Input Error", ex.getMessage());
         		    }
                 }
                 return null;
             });

             Optional<Staff> result = dialog.showAndWait();
             result.ifPresent(updated -> {
                 selected.id = updated.id;
                 selected.name = updated.name;
                 selected.designation = updated.designation;
                 selected.sex = updated.sex;
                 selected.salary = updated.salary;
                 table.refresh();
             });
         } catch(IndexOutOfBoundsException e) {
             showAlert("No Selection", "Please select a staff to modify.");
         }
	}
	
	public static void deleteStaff(TableView<Staff> table) {
        try {
        	Staff selected = staff.get(table.getSelectionModel().getSelectedIndex());
        	staff.remove(selected);
        	table.setItems(FXCollections.observableArrayList(staff));
        } catch(IndexOutOfBoundsException e) {
            showAlert("No Selection", "Please select a staff to modify.");
        }
	}
}
