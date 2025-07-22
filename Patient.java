import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public abstract class Patient extends HospitalManagement {
	private String id;
	private String name;
	private String disease;
	private String sex;
	private String admitStatus;
	private int age;
	
	public Patient() {
		id = "";
		name = "";
		disease = "";
		sex = "";
		admitStatus = "";
		age = 0;
	}
	
	public Patient(String id, String name, String disease, String sex, String admitStatus, int age) {
		this.id = id;
		this.name = name;
		this.disease = disease;
		this.sex = sex;
		this.admitStatus = admitStatus;
		this.age = age;
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
	
	public String getDisease() {
		return disease;
	}
	
	public void setDisease(String disease) {
		this.disease = disease;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getAdmitStatus() {
		return admitStatus;
	}
	
	public void setAdmitStatus(String admitStatus) {
		this.admitStatus = admitStatus;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public abstract BorderPane newPatient();
	
	public static TableView<Patient> showPatientInfo() {
		// Create a TableView
        TableView<Patient> patientTable = new TableView<>();
        
        // Create columns
        TableColumn<Patient, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Patient, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Patient, String> diseaseColumn = new TableColumn<>("Disease");
        diseaseColumn.setCellValueFactory(new PropertyValueFactory<>("disease"));

        TableColumn<Patient, String> sexColumn = new TableColumn<>("Sex");
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));

        TableColumn<Patient, Integer> admitStatusColumn = new TableColumn<>("Admit Status");
        admitStatusColumn.setCellValueFactory(new PropertyValueFactory<>("admitStatus"));
        
        TableColumn<Patient, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        // Add columns to the TableView
        patientTable.getColumns().add(idColumn);
        patientTable.getColumns().add(nameColumn);
        patientTable.getColumns().add(diseaseColumn);
        patientTable.getColumns().add(admitStatusColumn);
        patientTable.getColumns().add(ageColumn);
        
        // Create an ObservableList and add sample data
        ObservableList<Patient> patientData = FXCollections.observableArrayList(patients);
        
        // Set up the layout
        patientTable.setItems(patientData);
        
        return patientTable;
	}
	
	public static void searchPatient(TableView<Patient> table) {
		TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Find Patient");
        dialog.setHeaderText("Find Patient by ID");
        dialog.setContentText("Please enter Patient id:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(id -> {
            for (Patient item : patients) {
                if (item.getId().equals(id)) {
                	int index = patients.indexOf(item);
                    table.getSelectionModel().select(index);
                    table.scrollTo(index);
                    break;
                }
            }
        });
	}
	
	public static void modifyPatient(TableView<Patient> table) {
        try {
       	 Patient selected = patients.get(table.getSelectionModel().getSelectedIndex());
       	 
       	 //Create the custom dialog
            Dialog<Patient> dialog = new Dialog<>();
            dialog.setTitle("Modify Patient");
            dialog.setHeaderText("Modify Patient Information");
            
            // Create the grid pane for input fields
            GridPane gp = new GridPane();
            gp.setHgap(10);
            gp.setVgap(10);
            gp.setPadding(new Insets(20, 150, 10, 10));
            
            // Create input fields
            TextField idField = new TextField(selected.getId());
            TextField nameField = new TextField(selected.getName());
            TextField diseaseField = new TextField(selected.getDisease());
            String[] sex = {"Male", "Female"};
            ComboBox<String> sexField = new ComboBox<>(FXCollections.observableArrayList(sex));
            sexField.setValue("Male");
            TextField admitStatusField = new TextField(selected.getAdmitStatus());
            TextField ageField = new TextField(selected.getAge() + "");
            
            // Add input fields to the grid
            gp.add(new Label("ID:"), 0, 0);
            gp.add(idField, 1, 0);
            gp.add(new Label("Name:"), 0, 1);
            gp.add(nameField, 1, 1);
            gp.add(new Label("Disease:"), 0, 2);
            gp.add(diseaseField, 1, 2);
            gp.add(new Label("Sex:"), 0, 3);
            gp.add(sexField, 1, 3);
            gp.add(new Label("Admit Status:"), 0, 4);
            gp.add(admitStatusField, 1, 4);
            gp.add(new Label("Salary:"), 0, 5);
            gp.add(ageField, 1, 5);
            
            // Enable the OK button by default
            ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            // Add grid to the dialog
            dialog.getDialogPane().setContent(gp);
            
            // Convert the result to a Patient object when OK is pressed
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButtonType) {
                	try {
        		        // Validate that all required fields are filled
        		        if (idField.getText().trim().isEmpty() ||
        		            nameField.getText().trim().isEmpty() ||
        		            diseaseField.getText().trim().isEmpty() ||
        		            ageField.getText().trim().isEmpty()) {

        		            throw new IllegalArgumentException("All fields are required.");
        		        }
        		        if (idField.getText().length() != 4 ||
        				    idField.getText().charAt(0) != 'P' || 
        				    !Character.isDigit(idField.getText().charAt(1)) || 
        				    !Character.isDigit(idField.getText().charAt(2)) ||
        				    !Character.isDigit(idField.getText().charAt(3))) {
        				    throw new IllegalArgumentException("ID must start with 'P' followed by 3 digits. Example: P001");
        			    }
        		        if(Integer.parseInt(ageField.getText()) < 0) {
        		        	throw new IllegalArgumentException("Age field value MUST be a positive integer.");
        		        }
        		        return new InPatient(idField.getText(), nameField.getText(), diseaseField.getText(), sexField.getValue(), Integer.parseInt(ageField.getText()), "", "", 0);
        		    } catch (NumberFormatException ex) {
        		        // Show an alert if number conversion fails
        		        showAlert("Input Error", "Please enter valid numbers in Age and Arrival Time fields.");
        		    } catch (IllegalArgumentException ex) {
        		        // Show an alert if any field is empty
        		        showAlert("Input Error", ex.getMessage());
        		    }
                }
                return null;
            });

            Optional<Patient> result = dialog.showAndWait();
            result.ifPresent(updated -> {
                selected.setId(updated.getId());
                selected.setName(updated.getName());
                selected.setDisease(updated.getDisease());
                selected.setSex(updated.getSex());
                selected.setAdmitStatus(updated.getAdmitStatus());
                selected.setAge(updated.getAge());
                table.refresh();
            });
        } catch(IndexOutOfBoundsException e) {
            showAlert("No Selection", "Please select a patient to modify.");
        }
	}
	
	public static void deletePatient(TableView<Patient> table) {
       try {
       	Patient selected = patients.get(table.getSelectionModel().getSelectedIndex());
       	patients.remove(selected);
       	table.setItems(FXCollections.observableArrayList(patients));
       } catch(IndexOutOfBoundsException e) {
           showAlert("No Selection", "Please select a patient to modify.");
       }
	}
}
