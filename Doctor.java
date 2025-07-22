import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.HBox;

public abstract class Doctor extends HospitalManagement{
	private String id;
	private String name;
	private String specialist;
	private String workTime;
	private String qualification;
	private int room;
	
	public Doctor() {
		id = "";
		name = "";
		specialist = "";
		workTime = "";
		qualification = "";
		room = 0;
	}
	
	public Doctor(String id, String name, String specialist, String workTime, String qualification, int room) {
		this.id = id;
		this.name = name;
		this.specialist = specialist;
		this.workTime = workTime;
		this.qualification = qualification;
		this.room = room;
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
	
	public String getSpecialist() {
		return specialist;
	}
	
	public void setSpecialist(String specialist) {
		this.specialist = specialist;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	
	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}
	
	public abstract BorderPane newDoctor();
	
	protected static void validateTimeSelection(ComboBox<String> startTime, ComboBox<String> endTime) {
        // Get selected values
        String start = startTime.getValue();
        String end = endTime.getValue();

        // Convert time to a comparable format
        int startIndex = startTime.getItems().indexOf(start);
        int endIndex = endTime.getItems().indexOf(end);

        // If start time is greater than end time, reset to valid range
        if (startIndex > endIndex) {
            endTime.setValue(start);
        }
	}
	
	public static TableView<Doctor> showDoctorInfo() {
		// Create a TableView
        TableView<Doctor> doctorTable = new TableView<>();
        
        // Create columns
        TableColumn<Doctor, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Doctor, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Doctor, String> specialistColumn = new TableColumn<>("Specialist");
        specialistColumn.setCellValueFactory(new PropertyValueFactory<>("specialist"));

        TableColumn<Doctor, String> workTimeColumn = new TableColumn<>("Work Time");
        workTimeColumn.setCellValueFactory(new PropertyValueFactory<>("workTime"));

        TableColumn<Doctor, String> qualificationColumn = new TableColumn<>("Qualification");
        qualificationColumn.setCellValueFactory(new PropertyValueFactory<>("qualification"));

        TableColumn<Doctor, Integer> roomColumn = new TableColumn<>("Room No.");
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));

        // Add columns to the TableView
        doctorTable.getColumns().add(idColumn);
        doctorTable.getColumns().add(nameColumn);
        doctorTable.getColumns().add(specialistColumn);
        doctorTable.getColumns().add(workTimeColumn);
        doctorTable.getColumns().add(qualificationColumn);
        doctorTable.getColumns().add(roomColumn);
        
        // Create an ObservableList and add sample data
        ObservableList<Doctor> doctorData = FXCollections.observableArrayList(doctors);
        
        // Set up the layout
        doctorTable.setItems(doctorData);
        
        return doctorTable;
	}
	
	public static void searchDoctor(TableView<Doctor> table) {
		TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Find Doctor");
        dialog.setHeaderText("Find Doctor by ID");
        dialog.setContentText("Please enter Doctor ID: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(id -> {
            for (Doctor item : doctors) {
                if (item.getId().equalsIgnoreCase(id)) {
                	int index = doctors.indexOf(item);
                    table.getSelectionModel().select(index);
                    table.scrollTo(index);
                    break;
                }
            }
        });
	}
	
	public static void modifyDoctor(TableView<Doctor> table) {
        try {
        	
       	 	Doctor selected = doctors.get(table.getSelectionModel().getSelectedIndex());
       	 
       	 	//Create the custom dialog
            Dialog<Doctor> dialog = new Dialog<>();
            dialog.setTitle("Modify Doctor");
            dialog.setHeaderText("Modify Doctor Information");
            
            // Create the grid pane for input fields
            GridPane gp = new GridPane();
            gp.setHgap(10);
            gp.setVgap(10);
            gp.setPadding(new Insets(20, 150, 10, 10));
            // Create input fields
            TextField idField = new TextField(selected.getId());
            TextField nameField = new TextField(selected.getName());
            TextField specialistField = new TextField(selected.getSpecialist());
            specialistField.setEditable(false);
            
            String[] times = {
            		"12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", 
            		"12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM"
            };
            ComboBox<String> startTime = new ComboBox<>(FXCollections.observableArrayList(times));
            ComboBox<String> endTime = new ComboBox<>(FXCollections.observableArrayList(times));
            startTime.setValue(selected.getWorkTime().split(" - ")[0]);
            endTime.setValue(selected.getWorkTime().split(" - ")[1]);
            startTime.setOnAction(e -> validateTimeSelection(startTime, endTime));
            endTime.setOnAction(e -> validateTimeSelection(startTime, endTime));
            HBox workTimeComboxes = new HBox();
            Label lbWorkTimeDash = new Label(" - ");
            workTimeComboxes.getChildren().addAll(startTime, lbWorkTimeDash, endTime);
            workTimeComboxes.setAlignment(Pos.CENTER);
            
            TextField qualificationField = new TextField(selected.getQualification());
            TextField roomField = new TextField(selected.getRoom() + "");
            
            // Add input fields to the grid
            gp.add(new Label("ID:"), 0, 0);
            gp.add(idField, 1, 0);
            gp.add(new Label("Name:"), 0, 1);
            gp.add(nameField, 1, 1);
            gp.add(new Label("Specialist:"), 0, 2);
            gp.add(specialistField, 1, 2);
            gp.add(new Label("WorkTime:"), 0, 3);
            gp.add(workTimeComboxes, 1, 3);
            gp.add(new Label("Qualification:"), 0, 4);
            gp.add(qualificationField, 1, 4);
            gp.add(new Label("Room No.:"), 0, 5);
            gp.add(roomField, 1, 5);
            
            // Enable the OK button by default
            ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            // Add grid to the dialog
            dialog.getDialogPane().setContent(gp);
            
            // Convert the result to a Doctor object when OK is pressed
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButtonType) {
                	try {
        		        // Validate that all required fields are filled
        		        if (idField.getText().trim().isEmpty() ||
        		            nameField.getText().trim().isEmpty() ||
        		            specialistField.getText().trim().isEmpty() ||
        		            qualificationField.getText().trim().isEmpty() ||
        		            roomField.getText().trim().isEmpty()) {

        		            throw new IllegalArgumentException("All fields are required.");
        		        }
        		        if(idField.getText().length() != 4 ||
        		        	idField.getText().charAt(0) != 'D' || 
        			        !Character.isDigit(idField.getText().charAt(1)) || 
        			        !Character.isDigit(idField.getText().charAt(2)) ||
        			        !Character.isDigit(idField.getText().charAt(3))) {
        		        	throw new IllegalArgumentException("ID must start with 'D' followed by 3 digits. Example: D001");
        			    }
        		        if(Integer.parseInt(roomField.getText()) < 0) {
        		        	throw new IllegalArgumentException("Room No. field value MUST be a positive integer.");
        		        }
        		        return new Cardiologist(idField.getText(), nameField.getText(), startTime.getValue()+" - "+endTime.getValue(), qualificationField.getText(), Integer.parseInt(roomField.getText()), 0, 0);
        		    } catch (NumberFormatException ex) {
        		        // Show an alert if number conversion fails
        		        showAlert("Input Error", "Please enter valid numbers in Room and Patients fields.");
        		    } catch (IllegalArgumentException ex) {
        		        // Show an alert if any field is empty
        		        showAlert("Input Error", ex.getMessage());
        		    }
                }
                return null;
            });

            Optional<Doctor> result = dialog.showAndWait();
            result.ifPresent(updated -> {
                selected.setId(updated.getId());
                selected.setName(updated.getName());
                selected.setSpecialist(updated.getSpecialist());
                selected.setWorkTime(updated.getWorkTime());
                selected.setQualification(updated.getQualification());
                selected.setRoom(updated.getRoom());
                table.refresh();
            });
        } catch(IndexOutOfBoundsException e) {
            showAlert("No Selection", "Please select a doctor to modify.");
        }
	}
	
	public static void deleteDoctor(TableView<Doctor> table) {
       try {
       	Doctor selected = doctors.get(table.getSelectionModel().getSelectedIndex());
       	doctors.remove(selected);
       	table.setItems(FXCollections.observableArrayList(doctors));
       } catch(IndexOutOfBoundsException e) {
           showAlert("No Selection", "Please select a doctor to delete.");
       }
	}
}
