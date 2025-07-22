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

public class Lab extends HospitalManagement {
	private String lab;
	private int cost;
	
	public Lab() {
		lab = "";
		cost = 0;
	}
	
	public Lab(String lab, int cost) {
		this.lab = lab;
		this.cost = cost;
	}
	
	public String getLab() {
		return lab;
	}
	public void setLab(String lab) {
		this.lab = lab;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public BorderPane newLab() {
		Label lbLab = createLabel("Lab");
		Label colon1 = createLabel(":");
		TextField inputLab = new TextField();
		
		Label lbCost = createLabel("Cost");
		Label colon2 = createLabel(":");
		TextField inputCost = new TextField();
		
		Button btRegister = createBtn("Add");
		Button btClear = createBtn("Clear");
		
		Text newLabMsg = new Text("New Lab");
		newLabMsg.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 100px; -fx-font-weight: bold; -fx-fill: white; -fx-stroke-width: 3;");
		
		// Create GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);
        
        // Add components to GridPane
        gridPane.add(lbLab, 0, 0);
        gridPane.add(colon1, 1, 0);
        gridPane.add(inputLab, 2, 0);
        
        gridPane.add(lbCost, 0, 1);
        gridPane.add(colon2, 1, 1);
        gridPane.add(inputCost, 2, 1);
        
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
		borderPane.setTop(newLabMsg);
		BorderPane.setAlignment(newLabMsg, Pos.CENTER);
		BorderPane.setAlignment(gridPane, Pos.CENTER);
		
		BorderPane.setMargin(gridPane, new Insets(0,150,0,150)); 
		
		btRegister.setOnAction(e -> {
		    try {
		        // Validate that all required fields are filled
		        if (inputLab.getText().trim().isEmpty() || inputCost.getText().trim().isEmpty()) {
		            throw new IllegalArgumentException("All fields are required.");
		        }
		        if(Integer.parseInt(inputCost.getText()) < 0) {
		        	throw new IllegalArgumentException("Cost field value MUST be a positive integer.");
		        }

		     // Set the values if all fields are filled
		        this.setLab(inputLab.getText());
		        this.setCost(Integer.parseInt(inputCost.getText()));
		        
		        // Add the doctor to the list
		        laboratories.add(this);
		        
		        Text successMessage = new Text("New Lab Added Successfully!");
				successMessage.setStyle("-fx-fill: #66BB6A; -fx-font-size: 50; -fx-font-weight: bold;");
				
				StackPane pane = new StackPane(successMessage);
		        borderPane.setCenter(pane);

		    } catch (NumberFormatException ex) {
		        // Show an alert if number conversion fails
		        showAlert("Input Error", "Please enter valid numbers in Cost fields.");
		    } catch (IllegalArgumentException ex) {
		        // Show an alert if any field is empty
		        showAlert("Input Error", ex.getMessage());
		    }
		});
		
		btClear.setOnAction(e -> {
            // Clear all the TextFields
			inputLab.clear();
	        inputCost.clear();
        });
		
		return borderPane;
	}
	
	public static TableView<Lab> LabList() {
		// Create a TableView
        TableView<Lab> labTable = new TableView<>();
        
        // Create columns
        TableColumn<Lab, String> labColumn = new TableColumn<>("Lab");
        labColumn.setCellValueFactory(new PropertyValueFactory<>("lab"));

        TableColumn<Lab, String> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        // Add columns to the TableView
        labTable.getColumns().add(labColumn);
        labTable.getColumns().add(costColumn);
        
        // Create an ObservableList and add sample data
        ObservableList<Lab> labData = FXCollections.observableArrayList(laboratories);
        
        // Set up the layout
        labTable.setItems(labData);
        
        return labTable;
	}
	
	public static void searchLab(TableView<Lab> table) {
		TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Find Lab");
        dialog.setHeaderText("Find Lab by name");
        dialog.setContentText("Please enter Lab name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            for (Lab item : laboratories) {
                if (item.getLab().toLowerCase().contains(name.toLowerCase())) {
                	int index = laboratories.indexOf(item);
                    table.getSelectionModel().select(index);
                    table.scrollTo(index);
                    break;
                }
            }
        });
	}
	
	public static void modifyLab(TableView<Lab> table) {
        try {
       	 Lab selected = laboratories.get(table.getSelectionModel().getSelectedIndex());
       	 
       	 //Create the custom dialog
            Dialog<Lab> dialog = new Dialog<>();
            dialog.setTitle("Modify Lab");
            dialog.setHeaderText("Modify Lab Information");
            
            // Create the grid pane for input fields
            GridPane gp = new GridPane();
            gp.setHgap(10);
            gp.setVgap(10);
            gp.setPadding(new Insets(20, 150, 10, 10));
            
            // Create input fields
            TextField labField = new TextField(selected.getLab());
            TextField costField = new TextField(selected.getCost() + "");
            
            // Add input fields to the grid
            gp.add(new Label("ID:"), 0, 0);
            gp.add(labField, 1, 0);
            gp.add(new Label("Name:"), 0, 1);
            gp.add(costField, 1, 1);
            
            // Enable the OK button by default
            ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            // Add grid to the dialog
            dialog.getDialogPane().setContent(gp);
            
            // Convert the result to a Lab object when OK is pressed
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButtonType) {
               	 try {
        		        // Validate that all required fields are filled
        		        if (labField.getText().trim().isEmpty() ||
        			            costField.getText().trim().isEmpty()) {
        		            throw new IllegalArgumentException("All fields are required.");
        		        }
        		        if(Integer.parseInt(costField.getText()) < 0) {
        		        	throw new IllegalArgumentException("Cost field value MUST be a positive integer.");
        		        }
        		        return new Lab(labField.getText(), Integer.parseInt(costField.getText()));
        		        
        		    } catch (NumberFormatException ex) {
        		        // Show an alert if number conversion fails
        		        showAlert("Input Error", "Please enter valid numbers in Cost field.");
        		    } catch (IllegalArgumentException ex) {
        		        // Show an alert if any field is empty or invalid
        		        showAlert("Input Error", ex.getMessage());
        		    }
                }
                return null;
            });

            Optional<Lab> result = dialog.showAndWait();
            result.ifPresent(updated -> {
                selected.setLab(updated.getLab());
                selected.setCost(updated.getCost());
                table.refresh();
            });
        } catch(IndexOutOfBoundsException e) {
            showAlert("No Selection", "Please select a laboratory to modify.");
        }
	}
	
	public static void deleteLab(TableView<Lab> table) {
       try {
       	Lab selected = laboratories.get(table.getSelectionModel().getSelectedIndex());
       	laboratories.remove(selected);
       	table.setItems(FXCollections.observableArrayList(laboratories));
       } catch(IndexOutOfBoundsException e) {
           showAlert("No Selection", "Please select a laboratory to delete.");
       }
	}
}
