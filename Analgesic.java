import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import javafx.scene.text.Text;

public class Analgesic extends Medicine {
    private String painType;
    private String strength;

    public Analgesic() {
        super();
        this.painType = "";
        this.strength = "";
    }

    public Analgesic(String name, String manufacturer, String expiryDate, int cost, int count, String painType, String strength) {
        super(name, manufacturer, expiryDate, cost, count);
        this.painType = painType;
        this.strength = strength;
    }

    public String getPainType() {
        return painType;
    }

    public void setPainType(String painType) {
        this.painType = painType;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }
    
    @Override
    public BorderPane newMedicine() {
        Label lbName = createLabel("Name");
        Label colon1 = createLabel(":");
        TextField inputName = new TextField();

        Label lbManufacturer = createLabel("Manufacturer");
        Label colon2 = createLabel(":");
        TextField inputManufacturer = new TextField();

        Label lbExpiryDate = createLabel("Expiry Date");
        Label colon3 = createLabel(":");
        DatePicker inputExpiryDate = new DatePicker();

        Label lbCost = createLabel("Cost");
        Label colon4 = createLabel(":");
        TextField inputCost = new TextField();

        Label lbCount = createLabel("Count");
        Label colon5 = createLabel(":");
        TextField inputCount = new TextField();

        Label lbPainType = createLabel("Pain Type");
        Label colon6 = createLabel(":");
        TextField inputPainType = new TextField();
        inputPainType.setPromptText("Headache");

        Label lbStrength = createLabel("Strength");
        Label colon7 = createLabel(":");
        TextField inputStrength = new TextField();
        inputStrength.setPromptText("i.e. 400mg");

        Button btRegister = createBtn("Register");
        Button btClear = createBtn("Clear");

        Text newMedicineMsg = new Text("New Analgesic");
        newMedicineMsg.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 100px; -fx-font-weight: bold; -fx-fill: white; -fx-stroke-width: 3;");

        // Create GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);

        // Add components to GridPane
        gridPane.add(lbName, 0, 0);
        gridPane.add(colon1, 1, 0);
        gridPane.add(inputName, 2, 0);

        gridPane.add(lbManufacturer, 0, 1);
        gridPane.add(colon2, 1, 1);
        gridPane.add(inputManufacturer, 2, 1);

        gridPane.add(lbExpiryDate, 0, 2);
        gridPane.add(colon3, 1, 2);
        gridPane.add(inputExpiryDate, 2, 2);

        gridPane.add(lbCost, 0, 3);
        gridPane.add(colon4, 1, 3);
        gridPane.add(inputCost, 2, 3);

        gridPane.add(lbCount, 0, 4);
        gridPane.add(colon5, 1, 4);
        gridPane.add(inputCount, 2, 4);

        gridPane.add(lbPainType, 0, 5);
        gridPane.add(colon6, 1, 5);
        gridPane.add(inputPainType, 2, 5);

        gridPane.add(lbStrength, 0, 6);
        gridPane.add(colon7, 1, 6);
        gridPane.add(inputStrength, 2, 6);

        HBox hbox = new HBox(20);
        hbox.getChildren().addAll(btRegister, btClear);
        hbox.setAlignment(Pos.CENTER);

        gridPane.add(hbox, 0, 7, 3, 1);
        gridPane.setPadding(new Insets(20, 0, 0, 0));

        gridPane.setBorder(new Border(new BorderStroke(
                Color.WHITE, // Border color
                BorderStrokeStyle.SOLID, // Border style
                new CornerRadii(10), // Corner radius
                new BorderWidths(2)))); // Border width

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(30));
        borderPane.setCenter(gridPane);
        borderPane.setTop(newMedicineMsg);
        BorderPane.setAlignment(newMedicineMsg, Pos.CENTER);
        BorderPane.setAlignment(gridPane, Pos.CENTER);

        BorderPane.setMargin(gridPane, new Insets(0, 150, 0, 150));

        btRegister.setOnAction(e -> {
            try {
                // Validate that all required fields are filled
                if (inputName.getText().trim().isEmpty() ||
                    inputManufacturer.getText().trim().isEmpty() ||
                    inputExpiryDate.getValue() == null ||
                    inputCost.getText().trim().isEmpty() ||
                    inputCount.getText().trim().isEmpty() ||
                    inputPainType.getText().trim().isEmpty() ||
                    inputStrength.getText().trim().isEmpty()) {

                    throw new IllegalArgumentException("All fields are required.");
                }
                if(Integer.parseInt(inputCost.getText()) < 0) {
                	throw new IllegalArgumentException("Cost field value MUST be a positive integer.");
                }
                if(Integer.parseInt(inputCount.getText()) < 0) {
                	throw new IllegalArgumentException("Count field value MUST be a positive integer.");
                }

                // Create a new Analgesic instance
                Analgesic analgesic = new Analgesic(
                    inputName.getText(),
                    inputManufacturer.getText(),
                    inputExpiryDate.getValue().toString(),
                    Integer.parseInt(inputCost.getText()),
                    Integer.parseInt(inputCount.getText()),
                    inputPainType.getText(),
                    inputStrength.getText()
                );

                // Add the analgesic to the list
                medicine.add(analgesic);

                // Show a success message
                Text successMessage = new Text("New Medicine Added Successfully!");
                successMessage.setStyle("-fx-fill: #66BB6A; -fx-font-size: 50; -fx-font-weight: bold;");
                StackPane pane = new StackPane(successMessage);
                borderPane.setCenter(pane);

            } catch (NumberFormatException ex) {
                // Show an alert if number conversion fails
                showAlert("Input Error", "Please enter valid numbers in Cost and Count fields.");
            } catch (IllegalArgumentException ex) {
                // Show an alert if any field is empty
                showAlert("Input Error", ex.getMessage());
            }
        });

        btClear.setOnAction(e -> {
            // Clear all the TextFields
            inputName.clear();
            inputManufacturer.clear();
            inputExpiryDate.setValue(null);
            inputCost.clear();
            inputCount.clear();
            inputPainType.clear();
            inputStrength.clear();
        });

        return borderPane;
    }
}
