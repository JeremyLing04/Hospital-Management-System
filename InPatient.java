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

public class InPatient extends Patient {
    private String ward;
    private String bedNumber;
    private int daysAdmitted;

    public InPatient() {
        super();
        this.ward = "";
        this.bedNumber = "";
        this.daysAdmitted = 0;
    }

    public InPatient(String id, String name, String disease, String sex, int age, String ward, String bedNumber, int daysAdmitted) {
        super(id, name, disease, sex, "Admitted", age);
        this.ward = ward;
        this.bedNumber = bedNumber;
        this.daysAdmitted = daysAdmitted;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public int getDaysAdmitted() {
        return daysAdmitted;
    }

    public void setDaysAdmitted(int daysAdmitted) {
        this.daysAdmitted = daysAdmitted;
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
		
		Label lbWard = createLabel("Ward");
		Label colon7 = createLabel(":");
		TextField inputWard = new TextField();
		inputWard.setPromptText("Ward A-Z");
		
		Label lbBedNumber = createLabel("Bed Number");
		Label colon8 = createLabel(":");
		TextField inputBedNumber = new TextField();
		inputBedNumber.setPromptText("i.e. B001");
		
		Label lbDaysAdmitted = createLabel("Days Admitted");
		Label colon9 = createLabel(":");
		TextField inputDaysAdmitted = new TextField();
		
		Button btRegister = createBtn("Register");
		Button btClear = createBtn("Clear");
		
		Text newPatientMsg = new Text("In Patient");
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
        
        gridPane.add(lbWard, 0, 6);
        gridPane.add(colon7, 1, 6);
        gridPane.add(inputWard, 2, 6);
        
        gridPane.add(lbBedNumber, 0, 7);
        gridPane.add(colon8, 1, 7);
        gridPane.add(inputBedNumber, 2, 7);
        
        gridPane.add(lbDaysAdmitted, 0, 8);
        gridPane.add(colon9, 1, 8);
        gridPane.add(inputDaysAdmitted, 2, 8);
		
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
		            inputWard.getText().trim().isEmpty() ||
		            inputBedNumber.getText().trim().isEmpty() ||
		            inputDaysAdmitted.getText().trim().isEmpty()) {

		            throw new IllegalArgumentException("All fields are required.");
		        }
		        if (inputId.getText().length() != 4 ||
				    inputId.getText().charAt(0) != 'P' || 
				    !Character.isDigit(inputId.getText().charAt(1)) || 
				    !Character.isDigit(inputId.getText().charAt(2)) ||
				    !Character.isDigit(inputId.getText().charAt(3))) {
				    throw new IllegalArgumentException("ID must start with 'P' followed by 3 digits. Example: P001");
			    }
		        if (inputBedNumber.getText().length() != 4 ||
					inputBedNumber.getText().charAt(0) != 'B' || 
					!Character.isDigit(inputBedNumber.getText().charAt(1)) || 
					!Character.isDigit(inputBedNumber.getText().charAt(2)) ||
					!Character.isDigit(inputBedNumber.getText().charAt(3))) {
					throw new IllegalArgumentException("Bed Number must start with 'B' followed by 3 digits. Example: B001");
				}
		        if(Integer.parseInt(inputAge.getText()) < 0) {
		        	throw new IllegalArgumentException("Age field value MUST be a positive integer.");
		        }
		        if(Integer.parseInt(inputDaysAdmitted.getText()) < 0) {
		        	throw new IllegalArgumentException("Days Admitted field value MUST be a positive integer.");
		        }
		        
		        // Set the values if all fields are filled
		        this.setId(inputId.getText());
		        this.setName(inputName.getText());
		        this.setDisease(inputDisease.getText());
		        this.setSex(inputSex.getValue());
		        this.setAdmitStatus(inputAdmitStatus.getText());
		        this.setAge(Integer.parseInt(inputAge.getText()));
		        this.setWard(inputWard.getText());
		        this.setBedNumber(inputBedNumber.getText());
		        this.setDaysAdmitted(Integer.parseInt(inputDaysAdmitted.getText()));

		        // Add the patient to the list
		        patients.add(this);
		        
		        Text successMessage = new Text("New Patient Added Successfully!");
				successMessage.setStyle("-fx-fill: #66BB6A; -fx-font-size: 50; -fx-font-weight: bold;");
				
				StackPane pane = new StackPane(successMessage);
		        borderPane.setCenter(pane);

		    } catch (NumberFormatException ex) {
		        // Show an alert if number conversion fails
		        showAlert("Input Error", "Please enter valid numbers in Age and Days Admitted fields.");
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
            inputWard.clear();
            inputBedNumber.clear();
            inputDaysAdmitted.clear();
        });
		
		return borderPane;
	}
}
