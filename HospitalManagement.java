import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HospitalManagement extends Application{
	static ArrayList<Doctor> doctors = new ArrayList<Doctor>(25);
	static ArrayList<Patient> patients = new ArrayList<Patient>(100);
	static ArrayList<Lab> laboratories = new ArrayList<Lab>(20);
	static ArrayList<Facility> facilities = new ArrayList<Facility>(20);
	static ArrayList<Medicine> medicine = new ArrayList<Medicine>(100);
	static ArrayList<Staff> staff = new ArrayList<Staff>(100);
	
	public void start(Stage primaryStage) {
	// ----- Main Page -----
		// ----- Main Page -----
        BorderPane bpForMainPage = new BorderPane();

        // Gradient Background Color for Main Page
        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#FFF9C4")),  
                new Stop(0.5, Color.web("#FFCCBC")), 
                new Stop(1, Color.web("#B3E5FC")) 
        );

        bpForMainPage.setBackground(new Background(new BackgroundFill(gradient, null, null)));

        //Hospital Icon Gif
        Image hospitalGif = new Image("Hospital.gif"); 
        ImageView imageView = new ImageView(hospitalGif);
        imageView.setFitWidth(500); 
        imageView.setFitHeight(300); 
        
        // Welcome Message
        Text welcomeMessage = new Text("Welcome to the HMS");
        welcomeMessage.setFont(Font.font("Arial", FontWeight.BOLD, 100));
        welcomeMessage.setStyle("-fx-fill: white; -fx-stroke:dimgrey; -fx-stroke-width:3;");

        // Current Date and Time
        Label timeLabel = new Label();
        timeLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white;");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd   |   hh:mm:ss a");
            timeLabel.setText(now.format(formatter));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Menu Button & Close Button
        Button btnMenu = new Button("Menu");
        Button btnExit = new Button("Exit");

        btnMenu.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        btnMenu.setPadding(new Insets(10, 20, 10, 20));
        btnMenu.setStyle("-fx-background-color: #81C784; -fx-text-fill: #2C6B3F; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color:white");

        btnExit.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        btnExit.setPadding(new Insets(10, 28, 10, 28));
        btnExit.setStyle("-fx-background-color: #FF8A65; -fx-text-fill: #B44C4C; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color:white");

     // Adding hover effect for btnMenu
        btnMenu.setOnMouseEntered(event -> {
            btnMenu.setStyle("-fx-background-color: #66BB6A; -fx-text-fill: #1B5E20; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color:white");
            applyScaleTransition(btnMenu, 1.0, 1.1); // Scale up to 1.1 times
        });

        btnMenu.setOnMouseExited(event -> {
            btnMenu.setStyle("-fx-background-color: #81C784; -fx-text-fill: #2C6B3F; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color:white");
            applyScaleTransition(btnMenu, 1.1, 1.0); // Scale back to original size
        });

        // Adding hover effect for btnExit
        btnExit.setOnMouseEntered(event -> {
            btnExit.setStyle("-fx-background-color: #FF7043; -fx-text-fill: #BF5B5C; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color:white");
            applyScaleTransition(btnExit, 1.0, 1.1); // Scale up to 1.1 times
        });

        btnExit.setOnMouseExited(event -> {
            btnExit.setStyle("-fx-background-color: #FF8A65; -fx-text-fill: #B44C4C; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color:white");
            applyScaleTransition(btnExit, 1.1, 1.0); // Scale back to original size
        });

        HBox hboxForButton = new HBox(30);
        hboxForButton.getChildren().addAll(btnMenu, btnExit);
        hboxForButton.setAlignment(Pos.CENTER);
        
        VBox vboxForMainPage = new VBox(10);
        vboxForMainPage.getChildren().addAll(imageView, welcomeMessage, timeLabel, hboxForButton);
        vboxForMainPage.setAlignment(Pos.CENTER);

        bpForMainPage.setCenter(vboxForMainPage);
        BorderPane.setMargin(vboxForMainPage, new Insets(50,0,100,0));

        // Create a Scene for Main Page & Display
        Scene sceneForMainPage = new Scene(bpForMainPage, 1450, 800);

// ----- Menu Page -----
        BorderPane bpForMenuPage = new BorderPane();

        // Gradient Background Color for Menu Page
        LinearGradient gradientForMenuPage = new LinearGradient(
                0, 0, 1, 1,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#B3E5FC")),
                new Stop(0.5, Color.web("#C8E6C9")),
                new Stop(1, Color.web("#FFF9C4"))
        );

        bpForMenuPage.setBackground(new Background(new BackgroundFill(gradientForMenuPage, null, null)));

        // Menu Page Title
        Text menuPageTitle = new Text("Main Menu");
        menuPageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 100));

        menuPageTitle.setFill(Color.web("#4682B4")); 
        menuPageTitle.setStyle("-fx-stroke: white; -fx-stroke-width: 3;");
        
        HBox hboxForMenuPageTitle = new HBox(menuPageTitle);
        hboxForMenuPageTitle.setPadding(new Insets(50, 0, 20, 0));
        hboxForMenuPageTitle.setAlignment(Pos.CENTER);
        bpForMenuPage.setTop(hboxForMenuPageTitle);

        // Selections
        Button btnDoctors = new Button("Doctors");
        Button btnPatients = new Button("Patients");
        Button btnMedicine = new Button("Medicine");
        Button btnLaboratories = new Button("Laboratories");
        Button btnFacilities = new Button("Facilities");
        Button btnStaff = new Button("Staff");
        
        String selectionButtonStyle = "-fx-background-color: #DCDCDC; -fx-text-fill: #5C4033; -fx-font-size: 25; " 
                + "-fx-font-weight: bold; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: white; -fx-border-width: 2;";
        
        btnDoctors.setStyle(selectionButtonStyle);
        btnPatients.setStyle(selectionButtonStyle);
        btnMedicine.setStyle(selectionButtonStyle);
        btnLaboratories.setStyle(selectionButtonStyle + "-fx-font-size: 18");
        btnFacilities.setStyle(selectionButtonStyle);
        btnStaff.setStyle(selectionButtonStyle);
        
     // Load icons
        ImageView doctorsIcon = new ImageView(new Image("Doctor.png"));
        doctorsIcon.setFitWidth(60);
        doctorsIcon.setFitHeight(60);
        ImageView patientsIcon = new ImageView(new Image("Patient.png"));
        patientsIcon.setFitWidth(60);
        patientsIcon.setFitHeight(60);
        ImageView medicineIcon = new ImageView(new Image("Medicine.png"));
        medicineIcon.setFitWidth(60);
        medicineIcon.setFitHeight(60);
        ImageView laboratoriesIcon = new ImageView(new Image("Laboratories.png"));
        laboratoriesIcon.setFitWidth(60);
        laboratoriesIcon.setFitHeight(60);
        ImageView facilitiesIcon = new ImageView(new Image("Facility.png"));
        facilitiesIcon.setFitWidth(60);
        facilitiesIcon.setFitHeight(60);
        ImageView staffIcon = new ImageView(new Image("Staff.png"));
        staffIcon.setFitWidth(60);
        staffIcon.setFitHeight(60);

        // Set icons and preferred size
        btnDoctors.setGraphic(doctorsIcon);
        btnDoctors.setContentDisplay(ContentDisplay.LEFT);
        btnDoctors.setPrefSize(200, 100);
        btnPatients.setGraphic(patientsIcon);
        btnPatients.setContentDisplay(ContentDisplay.LEFT);
        btnPatients.setPrefSize(200, 100); 
        btnMedicine.setGraphic(medicineIcon);
        btnDoctors.setContentDisplay(ContentDisplay.LEFT);
        btnDoctors.setPrefSize(200, 100);
        btnLaboratories.setGraphic(laboratoriesIcon);
        btnLaboratories.setContentDisplay(ContentDisplay.LEFT);
        btnLaboratories.setPrefSize(200, 100);
        btnFacilities.setGraphic(facilitiesIcon);
        btnFacilities.setContentDisplay(ContentDisplay.LEFT);
        btnFacilities.setPrefSize(200, 100);
        btnStaff.setGraphic(staffIcon);
        btnStaff.setContentDisplay(ContentDisplay.LEFT);
        btnStaff.setPrefSize(200, 100);
        
        btnDoctors.setMinSize(220, 100);
        btnPatients.setMinSize(220, 100);
        btnMedicine.setMinSize(220, 100);
        btnLaboratories.setMinSize(220, 100);
        btnFacilities.setMinSize(220, 100);
        btnStaff.setMinSize(220, 100);

        String hoverButtonStyle = selectionButtonStyle + "-fx-background-color: #B0B0B0; -fx-text-fill: white;";
        
        btnDoctors.setOnMouseEntered(event ->{
        	btnDoctors.setStyle(hoverButtonStyle);
        	applyScaleTransition(btnDoctors, 1.0, 1.1);
        });
        btnDoctors.setOnMouseExited(event -> {
        	btnDoctors.setStyle(selectionButtonStyle);
            applyScaleTransition(btnDoctors, 1.1, 1.0); 
        });
        
        btnPatients.setOnMouseEntered(event ->{
        	btnPatients.setStyle(hoverButtonStyle);
        	applyScaleTransition(btnPatients, 1.0, 1.1);
        });
        btnPatients.setOnMouseExited(event -> {
        	btnPatients.setStyle(selectionButtonStyle);
            applyScaleTransition(btnPatients, 1.1, 1.0); 
        });
        
        btnMedicine.setOnMouseEntered(event ->{
        	 btnMedicine.setStyle(hoverButtonStyle);
        	applyScaleTransition( btnMedicine, 1.0, 1.1);
        });
       btnMedicine.setOnMouseExited(event -> {
    	   btnMedicine.setStyle(selectionButtonStyle);
            applyScaleTransition( btnMedicine, 1.1, 1.0); 
        });
        
       btnLaboratories.setOnMouseEntered(event ->{
    	   btnLaboratories.setStyle(hoverButtonStyle + "-fx-font-size: 18");
        	applyScaleTransition(btnLaboratories, 1.0, 1.1);
        });
       btnLaboratories.setOnMouseExited(event -> {
    	   btnLaboratories.setStyle(selectionButtonStyle + "-fx-font-size: 18");
            applyScaleTransition(btnLaboratories, 1.1, 1.0); 
        });
        
       btnFacilities.setOnMouseEntered(event ->{
    	   btnFacilities.setStyle(hoverButtonStyle);
        	applyScaleTransition( btnFacilities, 1.0, 1.1);
        });
       btnFacilities.setOnMouseExited(event -> {
    	   btnFacilities.setStyle(selectionButtonStyle);
            applyScaleTransition( btnFacilities, 1.1, 1.0); 
        });
        
       btnStaff.setOnMouseEntered(event ->{
    	   btnStaff.setStyle(hoverButtonStyle);
        	applyScaleTransition(btnStaff, 1.0, 1.1);
        });
       btnStaff.setOnMouseExited(event -> {
    	   btnStaff.setStyle(selectionButtonStyle);
            applyScaleTransition(btnStaff, 1.1, 1.0); 
        });
        
        GridPane gridPaneForMenuPage = new GridPane();
        gridPaneForMenuPage.setHgap(50); 
        gridPaneForMenuPage.setVgap(50);
        gridPaneForMenuPage.setAlignment(Pos.CENTER);
        
        gridPaneForMenuPage.add(btnDoctors, 0, 0);
        gridPaneForMenuPage.add(btnPatients, 1, 0);
        gridPaneForMenuPage.add(btnMedicine, 0, 1);
        gridPaneForMenuPage.add(btnLaboratories, 1, 1);
        gridPaneForMenuPage.add(btnFacilities, 0, 2);
        gridPaneForMenuPage.add(btnStaff, 1, 2);
        bpForMenuPage.setCenter(gridPaneForMenuPage);
	        
	        //Back Button
	        Button btnBack = createBtn("Back");
	        
	        btnBack.setOnAction(event -> primaryStage.setScene(sceneForMainPage));
	        
	        HBox hboxForBackButton = new HBox(btnBack);
	        hboxForBackButton.setPadding(new Insets(20, 0, 50, 0));
	        hboxForBackButton.setAlignment(Pos.CENTER);
	        bpForMenuPage.setBottom( hboxForBackButton);   

	        // Create a Scene for Menu Page
	        Scene sceneForMenuPage = new Scene(bpForMenuPage, 1450, 800);
	        
    //----- Doctor -----
	        BorderPane bpForDoctorPage = new BorderPane();

	        // Gradient Background Color for Doctor Page
	        LinearGradient gradientForDoctorPage = new LinearGradient(
	        	    0, 0, 1, 1,
	        	    true,
	        	    CycleMethod.NO_CYCLE,
	        	    new Stop(0, Color.web("#FFAB91")),
	        	    new Stop(0.5, Color.web("#FFF59D")), 
	        	    new Stop(1, Color.web("#FFEBEE"))    
	        	);

	        bpForDoctorPage.setBackground(new Background(new BackgroundFill(gradientForDoctorPage, null, null)));
	        
	        Text textForDoctorPage = new Text("or");
	        textForDoctorPage.setStyle("-fx-fill: #8B6A50; -fx-font-size: 30; -fx-font-weight: bold;");
	        
	        //Buttons
	        Button btnNewDoctor = new Button("New Doctor");
	        Button btnShowDoctorList = new Button("Show Doctor List");
	        
	        String selectionDoctorButtonStyle = "-fx-text-fill: #5C4033; -fx-font-size: 50; -fx-font-weight: bold; -fx-border-width: 0; -fx-background-color: transparent;"
	        		+ " -fx-border-color: transparent; -fx-background-radius: 70px; -fx-border-radius: 70px;";
	        
	        btnNewDoctor.setMinSize(400, 200);
	        btnNewDoctor.setStyle(selectionDoctorButtonStyle);
	        
	        btnShowDoctorList.setMinSize(400, 200);
	        btnShowDoctorList.setStyle(selectionDoctorButtonStyle);
	        
	        String hoverDoctorButtonStyle = selectionDoctorButtonStyle + "-fx-background-color: rgba(0, 0, 0, 0.22); -fx-text-fill: white;";

	        btnNewDoctor.setOnMouseEntered(event -> btnNewDoctor.setStyle(hoverDoctorButtonStyle));
	        btnNewDoctor.setOnMouseExited(event -> btnNewDoctor.setStyle(selectionDoctorButtonStyle));

	        btnShowDoctorList.setOnMouseEntered(event -> btnShowDoctorList.setStyle(hoverDoctorButtonStyle));
	        btnShowDoctorList.setOnMouseExited(event -> btnShowDoctorList.setStyle(selectionDoctorButtonStyle));

	        HBox hboxForDoctorPage = new HBox(20);
	        hboxForDoctorPage.getChildren().addAll(btnNewDoctor, textForDoctorPage, btnShowDoctorList);
	        hboxForDoctorPage.setAlignment(Pos.CENTER);
	        bpForDoctorPage.setCenter(hboxForDoctorPage);
	        
	        //Back Button
	        Button btnBackForDoctorPage = createBtn("Back");
	        
	        btnBackForDoctorPage.setOnAction(event -> primaryStage.setScene(sceneForMenuPage));
	        
	        HBox hboxForDoctorPageBackButton = new HBox(btnBackForDoctorPage);
	        hboxForDoctorPageBackButton.setPadding(new Insets(20, 0, 50, 0));
	        hboxForDoctorPageBackButton.setAlignment(Pos.CENTER);
	        bpForDoctorPage.setBottom(hboxForDoctorPageBackButton);  
	        
	        Scene sceneForDoctorPage = new Scene(bpForDoctorPage, 1450, 800);
	        
	        //----- New Doctor-----//
	        BorderPane bpForNewDoctorPage = new BorderPane();
	        
	     // Gradient Background Color for New Doctor Page
	        LinearGradient gradientForNewDoctorPage = new LinearGradient(
	                0, 0, 1, 1,
	                true,
	                CycleMethod.NO_CYCLE,
	                new Stop(0, Color.web("#C5CAE9")),
	                new Stop(0.5, Color.web("#F8BBD0")),
	                new Stop(1, Color.web("#E1BEE7"))
	        );

	        bpForNewDoctorPage.setBackground(new Background(new BackgroundFill(gradientForNewDoctorPage, null, null)));
	        
	        Text textForNewDoctor = new Text("Select Type of Specialist: ");
	        textForNewDoctor.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 100px; -fx-font-weight: bold; -fx-fill: white; -fx-stroke-width: 3;");
	        
	        //Selection Button
	        Button btnCardiologist = createBtn("Cardiologist");
	        Button btnSurgeon = createBtn("Surgeon");
	        Button btnPediatrician = createBtn("Pediatrician");
	        
	        btnCardiologist.setPrefSize(250, 100);
	        btnSurgeon.setPrefSize(250, 100);
	        btnPediatrician.setPrefSize(250, 100);
	        
	        VBox vboxForSpecialistTypes = new VBox(40);
	        vboxForSpecialistTypes.getChildren().addAll(btnCardiologist, btnSurgeon, btnPediatrician);
	        vboxForSpecialistTypes.setAlignment(Pos.CENTER);
	        vboxForSpecialistTypes.setPadding(new Insets(20));
	        
	        // Adding border to VBox
	        vboxForSpecialistTypes.setBorder(new Border(new BorderStroke(
	                Color.WHITE, // Border color
	                BorderStrokeStyle.SOLID, // Border style
	                new CornerRadii(10), // Corner radius
	                new BorderWidths(2)))); // Border width
	        
	        //Back Button
	        Button btnBackToDoctorPage = createBtn("Back");
	        btnBackToDoctorPage.setOnAction(e -> primaryStage.setScene(sceneForDoctorPage));
	        
	        bpForNewDoctorPage.setTop(textForNewDoctor);
	        bpForNewDoctorPage.setCenter(vboxForSpecialistTypes);
	        bpForNewDoctorPage.setBottom(btnBackToDoctorPage);
	        
	        BorderPane.setAlignment(textForNewDoctor, Pos.CENTER);
	        BorderPane.setMargin(textForNewDoctor, new Insets(100, 0, 50, 0));
	        BorderPane.setAlignment(vboxForSpecialistTypes, Pos.CENTER);
	        BorderPane.setMargin(vboxForSpecialistTypes, new Insets(0,150,0,150)); 
	        BorderPane.setAlignment(btnBackToDoctorPage, Pos.CENTER);
	        BorderPane.setMargin(btnBackToDoctorPage, new Insets(50, 0, 50, 0));

	        Scene sceneForNewDoctor = new Scene(bpForNewDoctorPage, 1450, 800);
	        
	        Button btnBackToNewDoctorPage = createBtn("Back");
        	btnBackToNewDoctorPage.setOnAction(e -> primaryStage.setScene(sceneForNewDoctor));
        	HBox hboxForbtnBackToNewDoctorPage  = new HBox();
        	hboxForbtnBackToNewDoctorPage.getChildren().add(btnBackToNewDoctorPage);
        	hboxForbtnBackToNewDoctorPage.setPadding(new Insets(10));
        	
        	LinearGradient gradientForNewSpecialistPage = new LinearGradient(
	                0, 0, 1, 1,
	                true,
	                CycleMethod.NO_CYCLE,
	                new Stop(0, Color.web("#C5CAE9")),  
	                new Stop(0.5, Color.web("#B3E5FC")), 
	                new Stop(1, Color.web("#CFD8DC"))   
	        );
        	
	        EventHandler<ActionEvent> specialistHandler = e -> {
	        	Doctor newDoctor = null;
	        	if(e.getTarget() == btnCardiologist) {
	        		newDoctor = new Cardiologist();
	        	}else if(e.getTarget() == btnSurgeon) {
	        		newDoctor = new Surgeon();
	        	}else if(e.getTarget() == btnPediatrician) {
	        		newDoctor = new Pediatrician();
	        	}
	        	
	        	BorderPane bpForSpecialistSelection = new BorderPane();
	        	bpForSpecialistSelection.setBackground(new Background(new BackgroundFill(gradientForNewSpecialistPage, null, null)));
	        	
	        	bpForSpecialistSelection.setCenter(newDoctor.newDoctor());
	        	bpForSpecialistSelection.setBottom(hboxForbtnBackToNewDoctorPage);
	        	
	        	Scene sceneForSpecialistSelection = new Scene(bpForSpecialistSelection, 1450, 800);
	        	primaryStage.setScene(sceneForSpecialistSelection);
	        };
	        
	        btnCardiologist.setOnAction(specialistHandler);
	        btnSurgeon.setOnAction(specialistHandler);
	        btnPediatrician.setOnAction(specialistHandler);
	        
	      //----- Medicine -----
	        BorderPane bpForMedicinePage = new BorderPane();

	        bpForMedicinePage.setBackground(new Background(new BackgroundFill(gradientForDoctorPage, null, null)));
	        
	        Text textForMedicinePage = new Text("or");
	        textForMedicinePage.setStyle("-fx-fill: #8B6A50; -fx-font-size: 30; -fx-font-weight: bold;");
	        
	        //Buttons
	        Button btnNewMedicine = new Button("New Medicine");
	        Button btnShowMedicineList = new Button("Show Medicine List");
	        
	        btnNewMedicine.setMinSize(400, 200);
	        btnNewMedicine.setStyle(selectionDoctorButtonStyle);
	        
	        btnShowMedicineList.setMinSize(400, 200);
	        btnShowMedicineList.setStyle(selectionDoctorButtonStyle);

	        btnNewMedicine.setOnMouseEntered(event -> btnNewMedicine.setStyle(hoverDoctorButtonStyle));
	        btnNewMedicine.setOnMouseExited(event -> btnNewMedicine.setStyle(selectionDoctorButtonStyle));

	        btnShowMedicineList.setOnMouseEntered(event -> btnShowMedicineList.setStyle(hoverDoctorButtonStyle));
	        btnShowMedicineList.setOnMouseExited(event -> btnShowMedicineList.setStyle(selectionDoctorButtonStyle));

	        HBox hboxForMedicinePage = new HBox(20);
	        hboxForMedicinePage.getChildren().addAll(btnNewMedicine, textForMedicinePage, btnShowMedicineList);
	        hboxForMedicinePage.setAlignment(Pos.CENTER);
	        bpForMedicinePage.setCenter(hboxForMedicinePage);
	        
	        //Back Button
	        Button btnBackForMedicinePage = createBtn("Back");
	        
	        btnBackForMedicinePage.setOnAction(event -> primaryStage.setScene(sceneForMenuPage));
	        
	        HBox hboxForMedicinePageBackButton = new HBox(btnBackForMedicinePage);
	        hboxForMedicinePageBackButton.setPadding(new Insets(20, 0, 50, 0));
	        hboxForMedicinePageBackButton.setAlignment(Pos.CENTER);
	        bpForMedicinePage.setBottom(hboxForMedicinePageBackButton);  
	        
	        Scene sceneForMedicinePage = new Scene(bpForMedicinePage, 1450, 800);
	        
	      //----- New Medicine -----//
	        BorderPane bpForNewMedicinePage = new BorderPane();
	        
	     // Gradient Background Color for Main Page

	        bpForNewMedicinePage.setBackground(new Background(new BackgroundFill(gradientForNewDoctorPage, null, null)));
	        
	        Text textForNewMedicine = new Text("Select Type of Madecine");
	        textForNewMedicine.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 100px; -fx-font-weight: bold; -fx-fill: white; -fx-stroke-width: 3;");
	        
	        //Selection Button
	        Button btnAntibiotic = createBtn("Antibiotic");
	        Button btnAnalgesic = createBtn("Analgesic");
	        Button btnVaccine = createBtn("Vaccine");
	        
	        btnAntibiotic.setPrefSize(250, 100);
	        btnAnalgesic.setPrefSize(250, 100);
	        btnVaccine.setPrefSize(250, 100);
	        
	        VBox vboxForMedicineTypes = new VBox(40);
	        vboxForMedicineTypes.getChildren().addAll(btnAntibiotic, btnAnalgesic, btnVaccine);
	        vboxForMedicineTypes.setAlignment(Pos.CENTER);
	        vboxForMedicineTypes.setPadding(new Insets(20));
	        
	        // Adding border to VBox
	        vboxForMedicineTypes.setBorder(new Border(new BorderStroke(
	                Color.WHITE, // Border color
	                BorderStrokeStyle.SOLID, // Border style
	                new CornerRadii(10), // Corner radius
	                new BorderWidths(2)))); // Border width
	        
	        //Back Button
	        Button btnBackToMedicinePage = createBtn("Back");
	        btnBackToMedicinePage.setOnAction(e -> primaryStage.setScene(sceneForMedicinePage));
	        
	        bpForNewMedicinePage.setTop(textForNewMedicine);
	        bpForNewMedicinePage.setCenter(vboxForMedicineTypes);
	        bpForNewMedicinePage.setBottom(btnBackToMedicinePage);
	        
	        BorderPane.setAlignment(textForNewMedicine, Pos.CENTER);
	        BorderPane.setMargin(textForNewMedicine, new Insets(100, 0, 50, 0));
	        BorderPane.setAlignment(vboxForMedicineTypes, Pos.CENTER);
	        BorderPane.setMargin(vboxForMedicineTypes, new Insets(0,150,0,150)); 
	        BorderPane.setAlignment(btnBackToMedicinePage, Pos.CENTER);
	        BorderPane.setMargin(btnBackToMedicinePage, new Insets(50, 0, 50, 0));

	        Scene sceneForNewMedicine = new Scene(bpForNewMedicinePage, 1450, 800);
	        
	        Button btnBackToNewMedicinePage = createBtn("Back");
        	btnBackToNewMedicinePage.setOnAction(e -> primaryStage.setScene(sceneForNewMedicine));
        	HBox hboxForbtnBackToNewMedicinePage  = new HBox();
        	hboxForbtnBackToNewMedicinePage.getChildren().add(btnBackToNewMedicinePage);
        	hboxForbtnBackToNewMedicinePage.setPadding(new Insets(10));
        	
	        EventHandler<ActionEvent> medicineHandler = e -> {
	        	Medicine newMedicine = null;
	        	if(e.getTarget() == btnAntibiotic) {
	        		newMedicine = new Antibiotic();
	        	}else if(e.getTarget() == btnAnalgesic) {
	        		newMedicine = new Analgesic();
	        	}else if(e.getTarget() == btnVaccine) {
	        		newMedicine = new Vaccine();
	        	}
	        	
	        	BorderPane bpForMedicineSelection = new BorderPane();

	        	bpForMedicineSelection.setBackground(new Background(new BackgroundFill(gradientForNewSpecialistPage, null, null)));
	        	
	        	bpForMedicineSelection.setCenter(newMedicine.newMedicine());
	        	bpForMedicineSelection.setBottom(hboxForbtnBackToNewMedicinePage);
	        	
	        	Scene sceneForMedicineSelection = new Scene(bpForMedicineSelection, 1450, 800);
	        	primaryStage.setScene(sceneForMedicineSelection);
	        };
	        
	        btnAntibiotic.setOnAction(medicineHandler);
	        btnAnalgesic.setOnAction(medicineHandler);
	        btnVaccine.setOnAction(medicineHandler);
	        
	      //----- Patient -----
	        BorderPane bpForPatientPage = new BorderPane();

	        bpForPatientPage.setBackground(new Background(new BackgroundFill(gradientForDoctorPage, null, null)));
	        
	        Text textForPatientPage = new Text("or");
	        textForPatientPage.setStyle("-fx-fill: #8B6A50; -fx-font-size: 30; -fx-font-weight: bold;");
	        
	        //Buttons
	        Button btnNewPatient = new Button("New Patient");
	        Button btnShowPatientList = new Button("Show Patient List");
	        
	        btnNewPatient.setMinSize(400, 200);
	        btnNewPatient.setStyle(selectionDoctorButtonStyle);
	        
	        btnShowPatientList.setMinSize(400, 200);
	        btnShowPatientList.setStyle(selectionDoctorButtonStyle);

	        btnNewPatient.setOnMouseEntered(event -> btnNewPatient.setStyle(hoverDoctorButtonStyle));
	        btnNewPatient.setOnMouseExited(event -> btnNewPatient.setStyle(selectionDoctorButtonStyle));

	        btnShowPatientList.setOnMouseEntered(event -> btnShowPatientList.setStyle(hoverDoctorButtonStyle));
	        btnShowPatientList.setOnMouseExited(event -> btnShowPatientList.setStyle(selectionDoctorButtonStyle));

	        HBox hboxForPatientPage = new HBox(20);
	        hboxForPatientPage.getChildren().addAll(btnNewPatient, textForPatientPage, btnShowPatientList);
	        hboxForPatientPage.setAlignment(Pos.CENTER);
	        bpForPatientPage.setCenter(hboxForPatientPage);
	        
	        //Back Button
	        Button btnBackForPatientPage = createBtn("Back");
	        
	        btnBackForPatientPage.setOnAction(event -> primaryStage.setScene(sceneForMenuPage));
	        
	        HBox hboxForPatientPageBackButton = new HBox(btnBackForPatientPage);
	        hboxForPatientPageBackButton.setPadding(new Insets(20, 0, 50, 0));
	        hboxForPatientPageBackButton.setAlignment(Pos.CENTER);
	        bpForPatientPage.setBottom(hboxForPatientPageBackButton);  
	        
	        Scene sceneForPatientPage = new Scene(bpForPatientPage, 1450, 800);
	        
	      //----- New Patient -----//
	        BorderPane bpForNewPatientPage = new BorderPane();
	        
	     // Gradient Background Color for New Patient Page

	        bpForNewPatientPage.setBackground(new Background(new BackgroundFill(gradientForNewDoctorPage, null, null)));
	        
	        Text textForNewPatient = new Text("Select Admit Status");
	        textForNewPatient.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 100px; -fx-font-weight: bold; -fx-fill: white; -fx-stroke-width: 3;");
	        
	        //Selection Button
	        Button btnInPatient = createBtn("In");
	        Button btnOutPatient = createBtn("Out");
	        Button btnEmergencyPatient = createBtn("Emergency");
	        
	        btnInPatient.setPrefSize(250, 100);
	        btnOutPatient.setPrefSize(250, 100);
	        btnEmergencyPatient.setPrefSize(250, 100);
	        
	        VBox vboxForPatientTypes = new VBox(40);
	        vboxForPatientTypes.getChildren().addAll(btnInPatient, btnOutPatient, btnEmergencyPatient);
	        vboxForPatientTypes.setAlignment(Pos.CENTER);
	        vboxForPatientTypes.setPadding(new Insets(20));
	        
	        // Adding border to VBox
	        vboxForPatientTypes.setBorder(new Border(new BorderStroke(
	                Color.WHITE, // Border color
	                BorderStrokeStyle.SOLID, // Border style
	                new CornerRadii(10), // Corner radius
	                new BorderWidths(2)))); // Border width
	        
	        //Back Button
	        Button btnBackToPatientPage = createBtn("Back");
	        btnBackToPatientPage.setOnAction(e -> primaryStage.setScene(sceneForPatientPage));
	        
	        bpForNewPatientPage.setTop(textForNewPatient);
	        bpForNewPatientPage.setCenter(vboxForPatientTypes);
	        bpForNewPatientPage.setBottom(btnBackToPatientPage);
	        
	        BorderPane.setAlignment(textForNewPatient, Pos.CENTER);
	        BorderPane.setMargin(textForNewPatient, new Insets(100, 0, 50, 0));
	        BorderPane.setAlignment(vboxForPatientTypes, Pos.CENTER);
	        BorderPane.setMargin(vboxForPatientTypes, new Insets(0,150,0,150)); 
	        BorderPane.setAlignment(btnBackToPatientPage, Pos.CENTER);
	        BorderPane.setMargin(btnBackToPatientPage, new Insets(50, 0, 50, 0));

	        Scene sceneForNewPatient = new Scene(bpForNewPatientPage, 1450, 800);
	        
	        Button btnBackToNewPatientPage = createBtn("Back");
        	btnBackToNewPatientPage.setOnAction(e -> primaryStage.setScene(sceneForNewPatient));
        	HBox hboxForbtnBackToNewPatientPage  = new HBox();
        	hboxForbtnBackToNewPatientPage.getChildren().add(btnBackToNewPatientPage);
        	hboxForbtnBackToNewPatientPage.setPadding(new Insets(10));
        	
	        EventHandler<ActionEvent> patientHandler = e -> {
	        	Patient newPatient = null;
	        	if(e.getTarget() == btnInPatient) {
	        		newPatient = new InPatient();
	        	}else if(e.getTarget() == btnOutPatient) {
	        		newPatient = new OutPatient();
	        	}else if(e.getTarget() == btnEmergencyPatient) {
	        		newPatient = new EmergencyPatient();
	        	}
	        	
	        	BorderPane bpForPatientSelection = new BorderPane();

	        	bpForPatientSelection.setBackground(new Background(new BackgroundFill(gradientForNewSpecialistPage, null, null)));
	        	
	        	bpForPatientSelection.setCenter(newPatient.newPatient());
	        	bpForPatientSelection.setBottom(hboxForbtnBackToNewPatientPage);
	        	
	        	Scene sceneForPatientSelection = new Scene(bpForPatientSelection, 1450, 800);
	        	primaryStage.setScene(sceneForPatientSelection);
	        };
	        
	        btnInPatient.setOnAction(patientHandler);
	        btnOutPatient.setOnAction(patientHandler);
	        btnEmergencyPatient.setOnAction(patientHandler);
	        
	      //----- Facility -----
	        BorderPane bpForFacilityPage = new BorderPane();

	        bpForFacilityPage.setBackground(new Background(new BackgroundFill(gradientForDoctorPage, null, null)));
	        
	        Text textForFacilityPage = new Text("or");
	        textForFacilityPage.setStyle("-fx-fill: #8B6A50; -fx-font-size: 30; -fx-font-weight: bold;");
	        
	        //Buttons
	        Button btnNewFacility = new Button("New Facility");
	        Button btnShowFacilityList = new Button("Show Facility List");
	        
	        btnNewFacility.setMinSize(400, 200);
	        btnNewFacility.setStyle(selectionDoctorButtonStyle);
	        
	        btnShowFacilityList.setMinSize(400, 200);
	        btnShowFacilityList.setStyle(selectionDoctorButtonStyle);

	        btnNewFacility.setOnMouseEntered(event -> btnNewFacility.setStyle(hoverDoctorButtonStyle));
	        btnNewFacility.setOnMouseExited(event -> btnNewFacility.setStyle(selectionDoctorButtonStyle));

	        btnShowFacilityList.setOnMouseEntered(event -> btnShowFacilityList.setStyle(hoverDoctorButtonStyle));
	        btnShowFacilityList.setOnMouseExited(event -> btnShowFacilityList.setStyle(selectionDoctorButtonStyle));

	        HBox hboxForFacilityPage = new HBox(20);
	        hboxForFacilityPage.getChildren().addAll(btnNewFacility, textForFacilityPage, btnShowFacilityList);
	        hboxForFacilityPage.setAlignment(Pos.CENTER);
	        bpForFacilityPage.setCenter(hboxForFacilityPage);
	        
	        //Back Button
	        Button btnBackForFacilityPage = createBtn("Back");
	        
	        btnBackForFacilityPage.setOnAction(event -> primaryStage.setScene(sceneForMenuPage));
	        
	        HBox hboxForFacilityPageBackButton = new HBox(btnBackForFacilityPage);
	        hboxForFacilityPageBackButton.setPadding(new Insets(20, 0, 50, 0));
	        hboxForFacilityPageBackButton.setAlignment(Pos.CENTER);
	        bpForFacilityPage.setBottom(hboxForFacilityPageBackButton);  
	        
	        Scene sceneForFacilityPage = new Scene(bpForFacilityPage, 1450, 800);
	        
	      //----- New Facility -----//
	        BorderPane bpForNewFacilityPage = new BorderPane();
	        
	        // Gradient Background Color for New Facility Page
	        bpForNewFacilityPage.setBackground(new Background(new BackgroundFill(gradientForNewSpecialistPage, null, null)));
	        
	        //Back Button
	        Button btnBackToFacilityPage = createBtn("Back");
	        btnBackToFacilityPage.setOnAction(e -> primaryStage.setScene(sceneForFacilityPage));
	        
	        bpForNewFacilityPage.setBottom(btnBackToFacilityPage);
	        BorderPane.setAlignment(btnBackToFacilityPage, Pos.CENTER);
	        BorderPane.setMargin(btnBackToFacilityPage, new Insets(50, 0, 50, 0));

	        Scene sceneForNewFacility = new Scene(bpForNewFacilityPage, 1450, 800);
        	
	        btnNewFacility.setOnAction(e -> {
	        	Facility newFacility = new Facility();
	        	
	        	bpForNewFacilityPage.setCenter(newFacility.newFacility());
	        	primaryStage.setScene(sceneForNewFacility);
	        });
	        
	      //----- Staff -----
	        BorderPane bpForStaffPage = new BorderPane();

	        bpForStaffPage.setBackground(new Background(new BackgroundFill(gradientForDoctorPage, null, null)));
	        
	        Text textForStaffPage = new Text("or");
	        textForStaffPage.setStyle("-fx-fill: #8B6A50; -fx-font-size: 30; -fx-font-weight: bold;");
	        
	        //Buttons
	        Button btnNewStaff = new Button("New Staff");
	        Button btnShowStaffList = new Button("Show Staff List");
	        
	        btnNewStaff.setMinSize(400, 200);
	        btnNewStaff.setStyle(selectionDoctorButtonStyle);
	        
	        btnShowStaffList.setMinSize(400, 200);
	        btnShowStaffList.setStyle(selectionDoctorButtonStyle);

	        btnNewStaff.setOnMouseEntered(event -> btnNewStaff.setStyle(hoverDoctorButtonStyle));
	        btnNewStaff.setOnMouseExited(event -> btnNewStaff.setStyle(selectionDoctorButtonStyle));

	        btnShowStaffList.setOnMouseEntered(event -> btnShowStaffList.setStyle(hoverDoctorButtonStyle));
	        btnShowStaffList.setOnMouseExited(event -> btnShowStaffList.setStyle(selectionDoctorButtonStyle));

	        HBox hboxForStaffPage = new HBox(20);
	        hboxForStaffPage.getChildren().addAll(btnNewStaff, textForStaffPage, btnShowStaffList);
	        hboxForStaffPage.setAlignment(Pos.CENTER);
	        bpForStaffPage.setCenter(hboxForStaffPage);
	        
	        //Back Button
	        Button btnBackForStaffPage = createBtn("Back");
	        
	        btnBackForStaffPage.setOnAction(event -> primaryStage.setScene(sceneForMenuPage));
	        
	        HBox hboxForStaffPageBackButton = new HBox(btnBackForStaffPage);
	        hboxForStaffPageBackButton.setPadding(new Insets(20, 0, 50, 0));
	        hboxForStaffPageBackButton.setAlignment(Pos.CENTER);
	        bpForStaffPage.setBottom(hboxForStaffPageBackButton);  
	        
	        Scene sceneForStaffPage = new Scene(bpForStaffPage, 1450, 800);
	        
	      //----- New Staff -----//
	        BorderPane bpForNewStaffPage = new BorderPane();
	        
	        // Gradient Background Color for New Staff Page
	        bpForNewStaffPage.setBackground(new Background(new BackgroundFill(gradientForNewSpecialistPage, null, null)));
	        
	        //Back Button
	        Button btnBackToStaffPage = createBtn("Back");
	        btnBackToStaffPage.setOnAction(e -> primaryStage.setScene(sceneForStaffPage));
	        
	        bpForNewStaffPage.setBottom(btnBackToStaffPage);
	        BorderPane.setAlignment(btnBackToStaffPage, Pos.CENTER);
	        BorderPane.setMargin(btnBackToStaffPage, new Insets(50, 0, 50, 0));

	        Scene sceneForNewStaff = new Scene(bpForNewStaffPage, 1450, 800);
        	
	        btnNewStaff.setOnAction(e -> {
	        	Staff newStaff = new Staff();
	        	
	        	bpForNewStaffPage.setCenter(newStaff.newStaff());
	        	primaryStage.setScene(sceneForNewStaff);
	        });
	        
	      //----- Lab -----
	        BorderPane bpForLabPage = new BorderPane();

	        bpForLabPage.setBackground(new Background(new BackgroundFill(gradientForDoctorPage, null, null)));
	        
	        Text textForLabPage = new Text("or");
	        textForLabPage.setStyle("-fx-fill: #8B6A50; -fx-font-size: 30; -fx-font-weight: bold;");
	        
	        //Buttons
	        Button btnNewLab = new Button("New Lab");
	        Button btnShowLabList = new Button("Show Lab List");
	        
	        btnNewLab.setMinSize(400, 200);
	        btnNewLab.setStyle(selectionDoctorButtonStyle);
	        
	        btnShowLabList.setMinSize(400, 200);
	        btnShowLabList.setStyle(selectionDoctorButtonStyle);

	        btnNewLab.setOnMouseEntered(event -> btnNewLab.setStyle(hoverDoctorButtonStyle));
	        btnNewLab.setOnMouseExited(event -> btnNewLab.setStyle(selectionDoctorButtonStyle));

	        btnShowLabList.setOnMouseEntered(event -> btnShowLabList.setStyle(hoverDoctorButtonStyle));
	        btnShowLabList.setOnMouseExited(event -> btnShowLabList.setStyle(selectionDoctorButtonStyle));

	        HBox hboxForLabPage = new HBox(20);
	        hboxForLabPage.getChildren().addAll(btnNewLab, textForLabPage, btnShowLabList);
	        hboxForLabPage.setAlignment(Pos.CENTER);
	        bpForLabPage.setCenter(hboxForLabPage);
	        
	        //Back Button
	        Button btnBackForLabPage = createBtn("Back");
	        
	        btnBackForLabPage.setOnAction(event -> primaryStage.setScene(sceneForMenuPage));
	        
	        HBox hboxForLabPageBackButton = new HBox(btnBackForLabPage);
	        hboxForLabPageBackButton.setPadding(new Insets(20, 0, 50, 0));
	        hboxForLabPageBackButton.setAlignment(Pos.CENTER);
	        bpForLabPage.setBottom(hboxForLabPageBackButton);  
	        
	        Scene sceneForLabPage = new Scene(bpForLabPage, 1450, 800);
	        
	      //----- New Lab -----//
	        BorderPane bpForNewLabPage = new BorderPane();
	        
	        // Gradient Background Color for New Lab Page
	        bpForNewLabPage.setBackground(new Background(new BackgroundFill(gradientForNewSpecialistPage, null, null)));
	        
	        //Back Button
	        Button btnBackToLabPage = createBtn("Back");
	        btnBackToLabPage.setOnAction(e -> primaryStage.setScene(sceneForLabPage));
	        
	        bpForNewLabPage.setBottom(btnBackToLabPage);
	        BorderPane.setAlignment(btnBackToLabPage, Pos.CENTER);
	        BorderPane.setMargin(btnBackToLabPage, new Insets(50, 0, 50, 0));

	        Scene sceneForNewLab = new Scene(bpForNewLabPage, 1450, 800);
        	
	        btnNewLab.setOnAction(e -> {
	        	Lab newLab = new Lab();
	        	
	        	bpForNewLabPage.setCenter(newLab.newLab());
	        	primaryStage.setScene(sceneForNewLab);
	        });
	        
	        //----- Show List -----//
	        
	        BorderPane bpForShowList = new BorderPane();
	        LinearGradient gradientForShowList = new LinearGradient(
	        	    0, 0, 1, 1,
	        	    true,
	        	    CycleMethod.NO_CYCLE,
	        	    new Stop(0, Color.web("#E8EAF6")),  
	        	    new Stop(0.5, Color.web("#F8BBD0")), 
	        	    new Stop(1, Color.web("#FFFDE7"))    
	        	);
	        bpForShowList.setBackground(new Background(new BackgroundFill(gradientForShowList, null, null)));
	        
	        //Table Title
	        Text doctorsListTitle = createTitle("Doctors List");
	        Text patientsListTitle = createTitle("Patients List");
	        Text medicineListTitle = createTitle("Medicine List");
	        Text laboratoriesListTitle = createTitle("Laboratories List");
	        Text facilitiesListTitle = createTitle("Facilities List");
	        Text staffListTitle = createTitle("Staff List");
	        
	      //Side Bar Buttons
	        Button btnDoctorsList = createSidebarButton("Doctors");
	        Button btnPatientsList = createSidebarButton("Patients");
	        Button btnMedicineList = createSidebarButton("Medicine");
	        Button btnLaboratoriesList = createSidebarButton("Laboratories");
	        Button btnFacilitiesList = createSidebarButton("Facilities");
	        Button btnStaffList = createSidebarButton("Staff");
	        
	        Button btnBackForShowList = createBtn("Back");
	        
	        //VBox for the Sidebar
	        VBox sidebar = new VBox(10, btnDoctorsList, btnPatientsList, btnMedicineList, btnLaboratoriesList, btnFacilitiesList, btnStaffList);
	        sidebar.setStyle("-fx-background-color: #E8EAF6; -fx-padding: 20;");
	        
	        bpForShowList.setLeft(sidebar);
	        bpForShowList.setBottom(btnBackForShowList);
	        BorderPane.setAlignment(btnBackForShowList, Pos.CENTER);
	        
	        // Set buttons style
	        sidebar.getChildren().forEach(node -> {
	            if (node instanceof Button) {
	                Button button = (Button) node;
	                button.setStyle("-fx-font-size: 14px; -fx-pref-width: 150px; -fx-background-color: #FFFDE7; -fx-text-fill: dimgrey; -fx-font-weight: bold");

	                button.setOnMouseEntered(event -> {
	                    button.setStyle("-fx-font-size: 14px; -fx-pref-width: 150px; -fx-background-color: #F0E5B3; -fx-text-fill: dimgrey; -fx-font-weight: bold");
	                    applyScaleTransition(button, 1.0, 1.1);
	                });

	                button.setOnMouseExited(event -> {
	                    button.setStyle("-fx-font-size: 14px; -fx-pref-width: 150px; -fx-background-color: #FFFDE7; -fx-text-fill: dimgrey; -fx-font-weight: bold");
	                    applyScaleTransition(button, 1.1, 1.0); 
	                });
	            }
	        });
	        
	        // Inner BorderPane
	        BorderPane innerBPforShowList = new BorderPane();
	        
	        Button findBtn = createBtn("Find");
	        Button modifyBtn = createBtn("Modify");
	        Button deleteBtn = createBtn("Delete");
	        HBox hboxforListActionBtns = new HBox(10);
	        hboxforListActionBtns.setPadding(new Insets(10));
	        hboxforListActionBtns.getChildren().addAll(findBtn, modifyBtn, deleteBtn);
	        
	        innerBPforShowList.setTop(hboxforListActionBtns);
	        bpForShowList.setCenter(innerBPforShowList);
	        
	        Scene sceneForShowList = new Scene(bpForShowList, 1450, 800);
        	primaryStage.setScene(sceneForShowList);
        	
	        EventHandler<ActionEvent> changeListContent = e -> {
	        	if(e.getTarget() == btnDoctorsList || e.getTarget() == btnShowDoctorList) {
	        		TableView<Doctor> targetTable = Doctor.showDoctorInfo();
	        		findBtn.setOnAction(event -> Doctor.searchDoctor(targetTable));
	        		modifyBtn.setOnAction(event -> Doctor.modifyDoctor(targetTable));
	        		deleteBtn.setOnAction(event -> Doctor.deleteDoctor(targetTable));
	        		innerBPforShowList.setCenter(targetTable);
	        		bpForShowList.setTop(doctorsListTitle);
		        	BorderPane.setAlignment(doctorsListTitle,Pos.CENTER);
		        	if(e.getTarget() == btnShowDoctorList) {
		        		btnBackForShowList.setOnAction(event -> primaryStage.setScene(sceneForDoctorPage));
			        	primaryStage.setScene(sceneForShowList);
		        	}
	        	}else if(e.getTarget() == btnPatientsList || e.getTarget() == btnShowPatientList) {
	        		TableView<Patient> targetTable = Patient.showPatientInfo();
	        		findBtn.setOnAction(event -> Patient.searchPatient(targetTable));
	        		modifyBtn.setOnAction(event -> Patient.modifyPatient(targetTable));
	        		deleteBtn.setOnAction(event -> Patient.deletePatient(targetTable));
	        		innerBPforShowList.setCenter(targetTable);
	        		bpForShowList.setTop(patientsListTitle);
		        	BorderPane.setAlignment(patientsListTitle,Pos.CENTER);
		        	if(e.getTarget() == btnShowPatientList) {
		        		btnBackForShowList.setOnAction(event -> primaryStage.setScene(sceneForPatientPage));
			        	primaryStage.setScene(sceneForShowList);
		        	}
	        	}else if(e.getTarget() == btnMedicineList || e.getTarget() == btnShowMedicineList) {
	        		TableView<Medicine> targetTable = Medicine.findMedicine();
	        		findBtn.setOnAction(event -> Medicine.searchMedicine(targetTable));
	        		modifyBtn.setOnAction(event -> Medicine.modifyMedicine(targetTable));
	        		deleteBtn.setOnAction(event -> Medicine.deleteMedicine(targetTable));
	        		innerBPforShowList.setCenter(targetTable);
	        		bpForShowList.setTop(medicineListTitle);
		        	BorderPane.setAlignment(medicineListTitle,Pos.CENTER);
		        	if(e.getTarget() == btnShowMedicineList) {
		        		btnBackForShowList.setOnAction(event -> primaryStage.setScene(sceneForMedicinePage));
			        	primaryStage.setScene(sceneForShowList);
		        	}
	        	}else if(e.getTarget() == btnFacilitiesList || e.getTarget() == btnShowFacilityList) {
	        		TableView<Facility> targetTable = Facility.showFacility();
	        		findBtn.setOnAction(event -> Facility.searchFacility(targetTable));
	        		modifyBtn.setOnAction(event -> Facility.modifyFacility(targetTable));
	        		deleteBtn.setOnAction(event -> Facility.deleteFacility(targetTable));
	        		innerBPforShowList.setCenter(targetTable);
	        		bpForShowList.setTop(facilitiesListTitle);
		        	BorderPane.setAlignment(facilitiesListTitle,Pos.CENTER);
		        	if(e.getTarget() == btnShowFacilityList) {
		        		btnBackForShowList.setOnAction(event -> primaryStage.setScene(sceneForFacilityPage));
			        	primaryStage.setScene(sceneForShowList);
		        	}
	        	}else if(e.getTarget() == btnStaffList || e.getTarget() == btnShowStaffList) {
	        		TableView<Staff> targetTable = Staff.showStaffInfo();
	        		findBtn.setOnAction(event -> Staff.searchStaff(targetTable));
	        		modifyBtn.setOnAction(event -> Staff.modifyStaff(targetTable));
	        		deleteBtn.setOnAction(event -> Staff.deleteStaff(targetTable));
	        		innerBPforShowList.setCenter(targetTable);
	        		bpForShowList.setTop(staffListTitle);
		        	BorderPane.setAlignment(staffListTitle,Pos.CENTER);
		        	if(e.getTarget() == btnShowStaffList) {
		        		btnBackForShowList.setOnAction(event -> primaryStage.setScene(sceneForStaffPage));
			        	primaryStage.setScene(sceneForShowList);
		        	}
	        	}else {
	        		TableView<Lab> targetTable = Lab.LabList();
	        		findBtn.setOnAction(event -> Lab.searchLab(targetTable));
	        		modifyBtn.setOnAction(event -> Lab.modifyLab(targetTable));
	        		deleteBtn.setOnAction(event -> Lab.deleteLab(targetTable));
	        		innerBPforShowList.setCenter(targetTable);
	        		bpForShowList.setTop(laboratoriesListTitle);
		        	BorderPane.setAlignment(laboratoriesListTitle,Pos.CENTER);
		        	if(e.getTarget() == btnShowLabList) {
		        		btnBackForShowList.setOnAction(event -> primaryStage.setScene(sceneForLabPage));
			        	primaryStage.setScene(sceneForShowList);
		        	}
	        	}
	        };
	        
	        btnDoctorsList.setOnAction(changeListContent);
	        btnShowDoctorList.setOnAction(changeListContent);

	        btnMedicineList.setOnAction(changeListContent);
	        btnShowMedicineList.setOnAction(changeListContent);
	        
	        btnPatientsList.setOnAction(changeListContent);
	        btnShowPatientList.setOnAction(changeListContent);
	        
	        btnFacilitiesList.setOnAction(changeListContent);
	        btnShowFacilityList.setOnAction(changeListContent);
	        
	        btnStaffList.setOnAction(changeListContent);
	        btnShowStaffList.setOnAction(changeListContent);
	        
	        btnLaboratoriesList.setOnAction(changeListContent);
	        btnShowLabList.setOnAction(changeListContent);
	        
    //----- Set the initial scene to be the main page -----
	        primaryStage.setTitle("HMS");
	        primaryStage.setScene(sceneForMainPage);
	        primaryStage.show();
	        
   //----- Set button actions to switch scenes -----
	        btnMenu.setOnAction(event -> primaryStage.setScene(sceneForMenuPage));
	        btnExit.setOnAction(event -> primaryStage.close());
	        
	        btnDoctors.setOnAction(event -> primaryStage.setScene(sceneForDoctorPage));
	        btnNewDoctor.setOnAction(event -> primaryStage.setScene(sceneForNewDoctor));
	        
	        btnPatients.setOnAction(event -> primaryStage.setScene(sceneForPatientPage));
	        btnNewPatient.setOnAction(event -> primaryStage.setScene(sceneForNewPatient));
	        
	        btnMedicine.setOnAction(event -> primaryStage.setScene(sceneForMedicinePage));
	        btnNewMedicine.setOnAction(event -> primaryStage.setScene(sceneForNewMedicine));
	        
	        btnFacilities.setOnAction(event -> primaryStage.setScene(sceneForFacilityPage));
	        
	        btnStaff.setOnAction(event -> primaryStage.setScene(sceneForStaffPage));
	        
	        btnLaboratories.setOnAction(event -> primaryStage.setScene(sceneForLabPage));
	}
	
	public static void main(String[] args) {
		doctors.add(new Cardiologist("D898", "Ling Sheng Han", "9 AM - 5 PM", "MD, Cardiology", 101, 20, 10));
        doctors.add(new Surgeon("D563", "Vicky Yii Shu Chi", "10 AM - 6 PM", "MD, FACS", 202, "Heart Surgery", 150));
        doctors.add(new Pediatrician("D003", "Michael Brown", "8 AM - 4 PM", "MD, Pediatrics", 303, 300));

        patients.add(new InPatient("P001", "Alice Johnson", "Diabetes", "Female", 45, "A", "A12", 3));
        patients.add(new OutPatient("P002", "Bob Smith", "Hypertension", "Male", 60, "2024-09-15", "Dr. Adams"));
        patients.add(new EmergencyPatient("P003", "Alice Johnson", "Chest Pain", "Female", 28, "John Johnson", "Heart Attack", "2024-09-02 14:30"));

        laboratories.add(new Lab("Genetics Lab", 1500));
        laboratories.add(new Lab("Microbiology Lab", 2000));
        laboratories.add(new Lab("Biochemistry Lab", 1800));

        facilities.add(new Facility("MRI Scanner"));
        facilities.add(new Facility("Blood banks"));
        facilities.add(new Facility("Birth centers"));

        medicine.add(new Antibiotic("Penicillin", "Pfizer", "2025-12-31", 50, 100, "500 mg", "Streptococcus"));
        medicine.add(new Vaccine("COVID-19 Vaccine", "Moderna", "2025-12-31", 100, 500, "COVID-19", 2));
        medicine.add(new Analgesic("Ibuprofen", "Advil", "2026-12-31", 15, 200, "Headache", "400 mg"));

        staff.add(new Staff("S001", "James Wilson", "Nurse", "Male", 45000));
        staff.add(new Staff("S002", "Linda Carter", "Administrative Assistant", "Female", 38000));
        staff.add(new Staff("S003", "Robert Johnson", "Technician", "Male", 42000));
        
        launch(args);
	}
	
	public static Button createBtn(String btnMsg) {
		Button btn = new Button(btnMsg);
		String btnLayout = "-fx-text-fill: #5C4033; -fx-font-size: 25; -fx-font-weight: bold; -fx-border-width: 0; -fx-background-color: transparent;"
        		+ " -fx-border-color: transparent; -fx-background-radius: 70px; -fx-border-radius: 70px;";
        btn.setStyle(btnLayout);
        
        btn.setOnMouseEntered(event -> {
        	btn.setStyle(btnLayout + "-fx-background-color: rgba(0, 0, 0, 0.22); -fx-text-fill: white;");
        	applyScaleTransition(btn, 1.0, 1.1);
        });
        btn.setOnMouseExited(event -> {
        	btn.setStyle(btnLayout);
        	applyScaleTransition(btn, 1.1, 1.0); 
        });
        
        return btn;
	}
	
	public static Text createTitle(String titleName) {
		Text title = new Text(titleName);
		String titleStyle = "-fx-font-family: 'Times New Roman'; -fx-font-size: 100px; -fx-font-weight: bold; -fx-fill: dimgrey;";
		title.setStyle(titleStyle);
                
        return title;
	}
	
    private static Button createSidebarButton(String btnName) {
        Button button = new Button(btnName);
        button.setMaxWidth(Double.MAX_VALUE); 
        return button;
    }
    
 // Method to apply scale transition
    private static void applyScaleTransition(Button button, double fromScale, double toScale) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setFromX(fromScale);
        scaleTransition.setFromY(fromScale);
        scaleTransition.setToX(toScale);
        scaleTransition.setToY(toScale);
        scaleTransition.play();
    }
    
  //Method to show an alert
    protected static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    protected static Label createLabel(String msg) {
    	Label label = new Label(msg);
    	label.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: dimgray; ");
    	
    	return label;
    }
}
