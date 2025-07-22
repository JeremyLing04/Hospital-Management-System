import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonBar.ButtonData;
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

public class Facility extends HospitalManagement {
	private String facility;
	
	public Facility() {
		facility = "";
	}
	
	public Facility(String facility) {
		this.facility = facility;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}
	
	public BorderPane newFacility() {
		Label lbFacility = createLabel("Facility");
		Label colon1 = createLabel(":");
		TextField inputFacility = new TextField();
		
		Button btRegister = createBtn("Add");
		Button btClear = createBtn("Clear");
		
		Text newFacilityMsg = new Text("New Facility");
		newFacilityMsg.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 100px; -fx-font-weight: bold; -fx-fill: white; -fx-stroke-width: 3;");
		
		// Create GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);
        
        // Add components to GridPane
        gridPane.add(lbFacility, 0, 0);
        gridPane.add(colon1, 1, 0);
        gridPane.add(inputFacility, 2, 0);
        
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
		borderPane.setTop(newFacilityMsg);
		BorderPane.setAlignment(newFacilityMsg, Pos.CENTER);
		BorderPane.setAlignment(gridPane, Pos.CENTER);
		
		BorderPane.setMargin(gridPane, new Insets(0,150,0,150)); 
		
		btRegister.setOnAction(e -> {
		    try {
		        // Validate that all required fields are filled
		        if (inputFacility.getText().trim().isEmpty()) {

		            throw new IllegalArgumentException("All fields are required.");
		        }

		        // Set the values if all fields are filled
		        this.setFacility(inputFacility.getText());

		        // Add the doctor to the list
		        facilities.add(this);
		        
		        Text successMessage = new Text("New Facility Added Successfully!");
				successMessage.setStyle("-fx-fill: #66BB6A; -fx-font-size: 50; -fx-font-weight: bold;");
				
				StackPane pane = new StackPane(successMessage);
		        borderPane.setCenter(pane);

		    } catch (NumberFormatException ex) {
		        // Show an alert if number conversion fails
		        showAlert("Input Error", "Please enter valid numbers in Facility field.");
		    } catch (IllegalArgumentException ex) {
		        // Show an alert if any field is empty
		        showAlert("Input Error", ex.getMessage());
		    }
		});
		
		btClear.setOnAction(e -> {
            // Clear all the TextFields
            inputFacility.clear();
        });
		
		return borderPane;
	}
	
	public static TableView<Facility> showFacility() {
		// Create a TableView
        TableView<Facility> facilityTable = new TableView<>();
        
        // Create columns
        TableColumn<Facility, String> facilityColumn = new TableColumn<>("Facility");
        facilityColumn.setCellValueFactory(new PropertyValueFactory<>("facility"));

        // Add columns to the TableView
        facilityTable.getColumns().add(facilityColumn);
        
        // Create an ObservableList and add sample data
        ObservableList<Facility> facilityData = FXCollections.observableArrayList(facilities);
        
        // Set up the layout
        facilityTable.setItems(facilityData);
        
        return facilityTable;
	}
	
	public static void searchFacility(TableView<Facility> table) {
		TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Find Facility");
        dialog.setHeaderText("Find Facility by name");
        dialog.setContentText("Please enter Facility name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            for (Facility item : facilities) {
                if (item.getFacility().toLowerCase().contains(name.toLowerCase())) {
                	int index = facilities.indexOf(item);
                    table.getSelectionModel().select(index);
                    table.scrollTo(index);
                    break;
                }
            }
        });
	}
	
	public static void modifyFacility(TableView<Facility> table) {
        try {
       	 Facility selected = facilities.get(table.getSelectionModel().getSelectedIndex());
       	 
       	 //Create the custom dialog
            Dialog<Facility> dialog = new Dialog<>();
            dialog.setTitle("Modify Facility");
            dialog.setHeaderText("Modify Facility Information");
            
            // Create the grid pane for input fields
            GridPane gp = new GridPane();
            gp.setHgap(10);
            gp.setVgap(10);
            gp.setPadding(new Insets(20, 150, 10, 10));
            
            // Create input fields
            TextField facilityField = new TextField(selected.getFacility());
            
            // Add input fields to the grid
            gp.add(new Label("ID:"), 0, 0);
            gp.add(facilityField, 1, 0);
            
            // Enable the OK button by default
            ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            // Add grid to the dialog
            dialog.getDialogPane().setContent(gp);
            
            // Convert the result to a Facility object when OK is pressed
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButtonType) {
               	 try {
        		        // Validate that all required fields are filled
        		        if (facilityField.getText().trim().isEmpty()) {
        		            throw new IllegalArgumentException("All fields are required.");
        		        }
        		        return new Facility(facilityField.getText());
        		        
               	 } catch (IllegalArgumentException ex) {
        		        // Show an alert if any field is empty or invalid
        		        showAlert("Input Error", ex.getMessage());
        		 }
                }
                return null;
            });

            Optional<Facility> result = dialog.showAndWait();
            result.ifPresent(updated -> {
            	selected.setFacility(updated.getFacility());
                table.refresh();
            });
        } catch(IndexOutOfBoundsException e) {
            showAlert("No Selection", "Please select a facility to modify.");
        }
	}
	
	public static void deleteFacility(TableView<Facility> table) {
        try {
        	Facility selected = facilities.get(table.getSelectionModel().getSelectedIndex());
        	facilities.remove(selected);
        	table.setItems(FXCollections.observableArrayList(facilities));
        } catch(IndexOutOfBoundsException e) {
            showAlert("No Selection", "Please select a facility to delete.");
        }
	}
}
