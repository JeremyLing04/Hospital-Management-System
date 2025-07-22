import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EmergencyPatient extends Patient {
    private String emergencyContact;
    private String emergencyCondition;
    private String arrivalTime;

    public EmergencyPatient() {
        super();
        this.emergencyContact = "";
        this.emergencyCondition = "";
        this.arrivalTime = "";
    }

    public EmergencyPatient(String id, String name, String disease, String sex, int age, String emergencyContact, String emergencyCondition, String arrivalTime) {
        super(id, name, disease, sex, "Emergency", age);
        this.emergencyContact = emergencyContact;
        this.emergencyCondition = emergencyCondition;
        this.arrivalTime = arrivalTime;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyCondition() {
        return emergencyCondition;
    }

    public void setEmergencyCondition(String emergencyCondition) {
        this.emergencyCondition = emergencyCondition;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    @Override
    public BorderPane newPatient() {
		Label lbId = createLabel("ID");
		Label colon1 = createLabel(":");
		TextField inputId = new TextField();
		inputId.setPromptText("i.e. P001");
		
		Label lbName = createLabel("Name");
		Label colon2 = createLabel(":");
		TextField inputName = new TextField();
		
		Label lbDisease = createLabel("Disease");
		Label colon3 = createLabel(":");
		TextField inputDisease = new TextField();
		
		Label lbSex = createLabel("Sex");
		Label colon4 = createLabel(":");
        String[] sex = {"Male", "Female"};
        ComboBox<String> inputSex = new ComboBox<>(FXCollections.observableArrayList(sex));
        inputSex.setValue("Male");
        
		Label lbAdmitStatus = createLabel("Admit Status");
		Label colon5 = createLabel(":");
		TextField inputAdmitStatus = new TextField("Admitted");
		inputAdmitStatus.setEditable(false);
        
		Label lbAge = createLabel("Age");
		Label colon6 = createLabel(":");
		TextField inputAge = new TextField();
		
		Label lbEmergencyContact = createLabel("Emergency Contact");
		Label colon7 = createLabel(":");
		TextField inputEmergencyContact = new TextField();
		inputEmergencyContact.setPromptText("Contact Name + Phone Number");
		
		Label lbEmergencyCondition = createLabel("Emergency Condition");
		Label colon8 = createLabel(":");
		TextField inputEmergencyCondition = new TextField();
		
		Label lbArrivalTime = createLabel("Arrival Time");
		Label colon9 = createLabel(":");
		DatePicker inputArrivalDate = new DatePicker();
		TextField inputArrivalHour = new TextField();
		Label lbArrivalHourMinute = new Label(" : ");
		lbArrivalHourMinute.setFont(Font.font(16));
		TextField inputArrivalMinute = new TextField();
		inputArrivalHour.setText("00");
		inputArrivalMinute.setText("00");
		inputArrivalHour.setPrefColumnCount(2);
		inputArrivalMinute.setPrefColumnCount(2);
		HBox hboxArrivalTime = new HBox();
		hboxArrivalTime.getChildren().addAll(inputArrivalDate, inputArrivalHour, lbArrivalHourMinute, inputArrivalMinute);
		
		Button btRegister = createBtn("Register");
		Button btClear = createBtn("Clear");
		
		Text newPatientMsg = new Text("Emergency Patient");
		newPatientMsg.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 100px; -fx-font-weight: bold; -fx-fill: white; -fx-stroke-width: 3;");
		
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
        
        gridPane.add(lbDisease, 0, 2);
        gridPane.add(colon3, 1, 2);
        gridPane.add(inputDisease, 2, 2);
        
        gridPane.add(lbSex, 0, 3);
        gridPane.add(colon4, 1, 3);
        gridPane.add(inputSex, 2, 3);
        
        gridPane.add(lbAdmitStatus, 0, 4);
        gridPane.add(colon5, 1, 4);
        gridPane.add(inputAdmitStatus, 2, 4);
        
        gridPane.add(lbAge, 0, 5);
        gridPane.add(colon6, 1, 5);
        gridPane.add(inputAge, 2, 5);
        
        gridPane.add(lbEmergencyContact, 0, 6);
        gridPane.add(colon7, 1, 6);
        gridPane.add(inputEmergencyContact, 2, 6);
        
        gridPane.add(lbEmergencyCondition, 0, 7);
        gridPane.add(colon8, 1, 7);
        gridPane.add(inputEmergencyCondition, 2, 7);
        
        gridPane.add(lbArrivalTime, 0, 8);
        gridPane.add(colon9, 1, 8);
        gridPane.add(hboxArrivalTime, 2, 8);
		
		HBox hbox = new HBox(20);
		hbox.getChildren().addAll(btRegister, btClear);
		hbox.setAlignment(Pos.CENTER);
		
		gridPane.add(hbox, 0, 9, 3, 1);
		gridPane.setPadding(new Insets(20,0,0,0));
		
		gridPane.setBorder(new Border(new BorderStroke(
                Color.WHITE, // Border color
                BorderStrokeStyle.SOLID, // Border style
                new CornerRadii(10), // Corner radius
                new BorderWidths(2)))); // Border width
		
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(30));
		borderPane.setCenter(gridPane);
		borderPane.setTop(newPatientMsg);
		BorderPane.setAlignment(newPatientMsg, Pos.CENTER);
		BorderPane.setAlignment(gridPane, Pos.CENTER);
		
		BorderPane.setMargin(gridPane, new Insets(0,150,0,150)); 
		
		btRegister.setOnAction(e -> {
		    try {
		        // Validate that all required fields are filled
		        if (inputId.getText().trim().isEmpty() ||
		            inputName.getText().trim().isEmpty() ||
		            inputDisease.getText().trim().isEmpty() ||
		            inputAge.getText().trim().isEmpty() ||
		            inputEmergencyContact.getText().trim().isEmpty() ||
		            inputEmergencyCondition.getText().trim().isEmpty() ||
		            inputArrivalDate.getValue() == null ||
		            inputArrivalHour.getText().trim().isEmpty() ||
		            inputArrivalMinute.getText().trim().isEmpty()) {

		            throw new IllegalArgumentException("All fields are required.");
		        }
		        if (inputId.getText().length() != 4 ||
				    inputId.getText().charAt(0) != 'P' || 
				    !Character.isDigit(inputId.getText().charAt(1)) || 
				    !Character.isDigit(inputId.getText().charAt(2)) ||
				    !Character.isDigit(inputId.getText().charAt(3))) {
				    throw new IllegalArgumentException("ID must start with 'P' followed by 3 digits. Example: P001");
			    }
		        if(Integer.parseInt(inputAge.getText()) < 0) {
		        	throw new IllegalArgumentException("Age field value MUST be a positive integer.");
		        }
		        if(Integer.parseInt(inputArrivalHour.getText()) < 0 || Integer.parseInt(inputArrivalHour.getText()) > 24) {
		        	throw new IllegalArgumentException("Arrival Hour field value MUST be between 0 to 23");
		        }
		        if(Integer.parseInt(inputArrivalMinute.getText()) < 0 || Integer.parseInt(inputArrivalMinute.getText()) > 59) {
		        	throw new IllegalArgumentException("Arrival Hour field value MUST be between 0 to 59");
		        }
		        
		        // Set the values if all fields are filled
		        this.setId(inputId.getText());
		        this.setName(inputName.getText());
		        this.setDisease(inputDisease.getText());
		        this.setSex(inputSex.getValue());
		        this.setAdmitStatus(inputAdmitStatus.getText());
		        this.setAge(Integer.parseInt(inputAge.getText()));
		        this.setEmergencyContact(inputEmergencyContact.getText());
		        this.setEmergencyCondition(inputEmergencyCondition.getText());
		        this.setArrivalTime(inputArrivalDate.getValue() + " " + inputArrivalHour.getText() + ":" + inputArrivalMinute.getText());

		        // Add the patient to the list
		        patients.add(this);
		        
		        Text successMessage = new Text("New Patient Added Successfully!");
				successMessage.setStyle("-fx-fill: #66BB6A; -fx-font-size: 50; -fx-font-weight: bold;");
				
				StackPane pane = new StackPane(successMessage);
		        borderPane.setCenter(pane);

		    } catch (NumberFormatException ex) {
		        // Show an alert if number conversion fails
		        showAlert("Input Error", "Please enter valid numbers in Age and Arrival Time fields.");
		    } catch (IllegalArgumentException ex) {
		        // Show an alert if any field is empty
		        showAlert("Input Error", ex.getMessage());
		    }
		});
		
		btClear.setOnAction(e -> {
            // Clear all the TextFields
            inputId.clear();
            inputName.clear();
            inputDisease.clear();
            inputSex.setValue("Male");
            inputAge.clear();
            inputEmergencyContact.clear();
            inputEmergencyCondition.clear();
            inputArrivalDate.setValue(null);
            inputArrivalHour.setText("00");
            inputArrivalMinute.setText("00");
        });
		
		return borderPane;
	}

}
