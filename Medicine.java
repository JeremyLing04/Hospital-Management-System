import java.time.LocalDate;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
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

public abstract class Medicine extends HospitalManagement{
	private String name;
	private String manufacturer;
	private String expiryDate;
	private int cost;
	private int count;
	
	public Medicine() {
		name = "";
		manufacturer = "";
		expiryDate = "";
		cost = 0;
		count = 0;
	}
	
	public Medicine(String name, String manufacturer, String expiryDate, int cost, int count) {
		this.name = name;
		this.manufacturer = manufacturer;
		this.expiryDate = expiryDate;
		this.cost = cost;
		this.count = count;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public abstract BorderPane newMedicine();
	
	public static TableView<Medicine> findMedicine() {
		// Create a TableView
        TableView<Medicine> medicineTable = new TableView<>();
        
        // Create columns
        TableColumn<Medicine, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Medicine, String> manufacturerColumn = new TableColumn<>("Manufacturer");
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

        TableColumn<Medicine, String> expiryDateColumn = new TableColumn<>("Expiry Date");
        expiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

        TableColumn<Medicine, Integer> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        
        TableColumn<Medicine, Integer> countColumn = new TableColumn<>("Count");
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

        // Add columns to the TableView
        medicineTable.getColumns().add(nameColumn);
        medicineTable.getColumns().add(manufacturerColumn);
        medicineTable.getColumns().add(expiryDateColumn);
        medicineTable.getColumns().add(costColumn);
        medicineTable.getColumns().add(countColumn);
        
        // Create an ObservableList and add sample data
        ObservableList<Medicine> medicineData = FXCollections.observableArrayList(medicine);
        
        // Set up the layout
        medicineTable.setItems(medicineData);
        
        return medicineTable;
	}
	
	public static void searchMedicine(TableView<Medicine> table) {
		TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Find Medicine");
        dialog.setHeaderText("Find Medicine by name");
        dialog.setContentText("Please enter Medicine name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            for (Medicine item : medicine) {
                if (item.getName().toLowerCase().contains(name.toLowerCase())) {
                	int index = medicine.indexOf(item);
                    table.getSelectionModel().select(index);
                    table.scrollTo(index);
                    break;
                }
            }
        });
	}
	
	public static void modifyMedicine(TableView<Medicine> table) {
        try {
       	 Medicine selected = medicine.get(table.getSelectionModel().getSelectedIndex());
       	 
       	 //Create the custom dialog
            Dialog<Medicine> dialog = new Dialog<>();
            dialog.setTitle("Modify Medicine");
            dialog.setHeaderText("Modify Medicine Information");
            
            // Create the grid pane for input fields
            GridPane gp = new GridPane();
            gp.setHgap(10);
            gp.setVgap(10);
            gp.setPadding(new Insets(20, 150, 10, 10));
            
            // Create input fields
            TextField nameField = new TextField(selected.getName());
            TextField manufacturerField = new TextField(selected.getManufacturer());
            DatePicker expiryDateField = new DatePicker();
            expiryDateField.setValue(LocalDate.parse(selected.getExpiryDate()));
            TextField costField = new TextField(selected.getCost()+"");
            TextField countField = new TextField(selected.getCount() + "");
            
            // Add input fields to the grid
            gp.add(new Label("Name:"), 0, 0);
            gp.add(nameField, 1, 0);
            gp.add(new Label("Manufacturer:"), 0, 1);
            gp.add(manufacturerField, 1, 1);
            gp.add(new Label("Expiry Date:"), 0, 2);
            gp.add(expiryDateField, 1, 2);
            gp.add(new Label("Cost:"), 0, 3);
            gp.add(costField, 1, 3);
            gp.add(new Label("Count:"), 0, 4);
            gp.add(countField, 1, 4);
            
            // Enable the OK button by default
            ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            // Add grid to the dialog
            dialog.getDialogPane().setContent(gp);
            
            // Convert the result to a Medicine object when OK is pressed
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButtonType) {
                	try {
                        // Validate that all required fields are filled
                        if (nameField.getText().trim().isEmpty() ||
                            manufacturerField.getText().trim().isEmpty() ||
                            expiryDateField.getValue() == null ||
                            costField.getText().trim().isEmpty() ||
                            countField.getText().trim().isEmpty()) {

                            throw new IllegalArgumentException("All fields are required.");
                        }
                        if(Integer.parseInt(costField.getText()) < 0) {
                        	throw new IllegalArgumentException("Cost field value MUST be a positive integer.");
                        }
                        if(Integer.parseInt(countField.getText()) < 0) {
                        	throw new IllegalArgumentException("Count field value MUST be a positive integer.");
                        }
                        return new Antibiotic(nameField.getText(), manufacturerField.getText(), expiryDateField.getValue().toString(), Integer.parseInt(costField.getText()), Integer.parseInt(countField.getText()), "", "");
                    } catch (NumberFormatException ex) {
                        // Show an alert if number conversion fails
                        showAlert("Input Error", "Please enter valid numbers in Cost and Count fields.");
                    } catch (IllegalArgumentException ex) {
                        // Show an alert if any field is empty or invalid
                        showAlert("Input Error", ex.getMessage());
                    }
                }
                return null;
            });

            Optional<Medicine> result = dialog.showAndWait();
            result.ifPresent(updated -> {
                selected.setName(updated.getName());
                selected.setManufacturer(updated.getManufacturer());
                selected.setExpiryDate(updated.getExpiryDate());
                selected.setCost(updated.getCost());
                selected.setCount(updated.getCount());
                table.refresh();
            });
        } catch(IndexOutOfBoundsException e) {
            showAlert("No Selection", "Please select a medicine to modify.");
        }
	}
	
	public static void deleteMedicine(TableView<Medicine> table) {
       try {
       	Medicine selected = medicine.get(table.getSelectionModel().getSelectedIndex());
       	medicine.remove(selected);
       	table.setItems(FXCollections.observableArrayList(medicine));
       } catch(IndexOutOfBoundsException e) {
           showAlert("No Selection", "Please select a medicine to modify.");
       }
	}
}
