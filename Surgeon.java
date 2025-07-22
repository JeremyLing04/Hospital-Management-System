import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class Surgeon extends Doctor {
    private String surgeryType;
    private int surgeriesPerformed;

    public Surgeon() {
        super();
        this.surgeryType = "";
        this.surgeriesPerformed = 0;
    }

    public Surgeon(String id, String name, String workTime, String qualification, int room, String surgeryType, int surgeriesPerformed) {
        super(id, name, "Surgeon", workTime, qualification, room);
        this.surgeryType = surgeryType;
        this.surgeriesPerformed = surgeriesPerformed;
    }

    public String getSurgeryType() {
        return surgeryType;
    }

    public void setSurgeryType(String surgeryType) {
        this.surgeryType = surgeryType;
    }

    public int getSurgeriesPerformed() {
        return surgeriesPerformed;
    }

    public void setSurgeriesPerformed(int surgeriesPerformed) {
        this.surgeriesPerformed = surgeriesPerformed;
    }
    
    @Override
    public BorderPane newDoctor() {
		Label lbId = createLabel("ID");
		Label colon1 = createLabel(":");
		TextField inputId = new TextField();
		inputId.setPromptText("i.e. D001");
		
		Label lbName = createLabel("Name");
		Label colon2 = createLabel(":");
		TextField inputName = new TextField();
		
		Label lbSpecialist = createLabel("Specialist");
		Label colon3 = createLabel(":");
		TextField inputSpecialist = new TextField("Surgeon");
		inputSpecialist.setEditable(false);
		
		Label lbWorkTime = createLabel("Work Time");
		Label colon4 = createLabel(":");
		Label lbWorkTimeDash = new Label(" - ");
        
     // Populate time options
        String[] times = {
        		"12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", 
        		"12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM"
        };
        
        ComboBox<String> startTime = new ComboBox<>(FXCollections.observableArrayList(times));
        ComboBox<String> endTime = new ComboBox<>(FXCollections.observableArrayList(times));
        startTime.setValue("9 AM");
        endTime.setValue("5 PM");
        
        startTime.setOnAction(e -> validateTimeSelection(startTime, endTime));
        endTime.setOnAction(e -> validateTimeSelection(startTime, endTime));
        
        HBox workTimeComboxes = new HBox();
        workTimeComboxes.getChildren().addAll(startTime, lbWorkTimeDash, endTime);
        
		Label lbQualification = createLabel("Qualification");
		Label colon5 = createLabel(":");
		TextField inputQualification = new TextField();
		
		Label lbSurgeryType = createLabel("Surgery Type");
		Label colon6 = createLabel(":");
		TextField inputSurgeryType = new TextField();
		
		Label lbSurgeriesPerformed = createLabel("Surgeries Performed");
		Label colon7 = createLabel(":");
		TextField inputSurgeriesPerformed = new TextField();
		
		Label lbRoom = createLabel("Room No.");
		Label colon8 = createLabel(":");
		TextField inputRoom = new TextField();
		
		Button btRegister = createBtn("Register");
		Button btClear = createBtn("Clear");
		
		Text newDoctorMsg = new Text("Surgeon");
		newDoctorMsg.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 100px; -fx-font-weight: bold; -fx-fill: white; -fx-stroke-width: 3;");
		
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
        
        gridPane.add(lbSpecialist, 0, 2);
        gridPane.add(colon3, 1, 2);
        gridPane.add(inputSpecialist, 2, 2);
        
        gridPane.add(lbWorkTime, 0, 3);
        gridPane.add(colon4, 1, 3);
        gridPane.add(workTimeComboxes, 2, 3);
        
        gridPane.add(lbQualification, 0, 4);
        gridPane.add(colon5, 1, 4);
        gridPane.add(inputQualification, 2, 4);
        
        gridPane.add(lbRoom, 0, 5);
        gridPane.add(colon6, 1, 5);
        gridPane.add(inputRoom, 2, 5);
        
        gridPane.add(lbSurgeryType, 0, 6);
        gridPane.add(colon7, 1, 6);
        gridPane.add(inputSurgeryType, 2, 6);
        
        gridPane.add(lbSurgeriesPerformed, 0, 7);
        gridPane.add(colon8, 1, 7);
        gridPane.add(inputSurgeriesPerformed, 2, 7);
		
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
		borderPane.setTop(newDoctorMsg);
		BorderPane.setAlignment(newDoctorMsg, Pos.CENTER);
		BorderPane.setAlignment(gridPane, Pos.CENTER);
		
		BorderPane.setMargin(gridPane, new Insets(0,150,0,150)); 
		
		btRegister.setOnAction(e -> {
		    try {
		        // Validate that all required fields are filled
		        if (inputId.getText().trim().isEmpty() ||
		            inputName.getText().trim().isEmpty() ||
		            inputSpecialist.getText().trim().isEmpty() ||
		            inputQualification.getText().trim().isEmpty() ||
		            inputRoom.getText().trim().isEmpty() ||
		            inputSurgeryType.getText().trim().isEmpty() ||
		            inputSurgeriesPerformed.getText().trim().isEmpty()) {

		            throw new IllegalArgumentException("All fields are required.");
		        }
		        if(inputId.getText().length() != 4 ||
		        	inputId.getText().charAt(0) != 'D' || 
			        !Character.isDigit(inputId.getText().charAt(1)) || 
			        !Character.isDigit(inputId.getText().charAt(2)) ||
			        !Character.isDigit(inputId.getText().charAt(3))) {
		        	throw new IllegalArgumentException("ID must start with 'D' followed by 3 digits. Example: D001");
			    }
		        if(Integer.parseInt(inputRoom.getText()) < 0) {
		        	throw new IllegalArgumentException("Room No. field value MUST be a positive integer.");
		        }
		        if(Integer.parseInt(inputSurgeriesPerformed.getText()) < 0) {
		        	throw new IllegalArgumentException("Surgeries Performed field value MUST be a positive integer.");
		        }

		        // Set the values if all fields are filled
		        this.setId(inputId.getText());
		        this.setName(inputName.getText());
		        this.setSpecialist(inputSpecialist.getText());
		        this.setWorkTime(startTime.getValue() + " - " + endTime.getValue());
		        this.setQualification(inputQualification.getText());
		        this.setRoom(Integer.parseInt(inputRoom.getText()));
		        this.setSurgeryType(inputSurgeryType.getText());
		        this.setSurgeriesPerformed(Integer.parseInt(inputSurgeriesPerformed.getText()));

		        // Add the doctor to the list
		        doctors.add(this);
		        
		        Text successMessage = new Text("New Doctor Added Successfully!");
				successMessage.setStyle("-fx-fill: #66BB6A; -fx-font-size: 50; -fx-font-weight: bold;");
				
				StackPane pane = new StackPane(successMessage);
		        borderPane.setCenter(pane);

		    } catch (NumberFormatException ex) {
		        // Show an alert if number conversion fails
		        showAlert("Input Error", "Please enter valid numbers in Room and Patients fields.");
		    } catch (IllegalArgumentException ex) {
		        // Show an alert if any field is empty
		        showAlert("Input Error", ex.getMessage());
		    }
		});
		
		btClear.setOnAction(e -> {
            // Clear all the TextFields
            inputId.clear();
            inputName.clear();
            startTime.setValue("9 AM");
            endTime.setValue("5 PM");
            inputQualification.clear();
            inputRoom.clear();
            inputSurgeryType.clear();
            inputSurgeriesPerformed.clear();
        });
		
		return borderPane;
	}
}
