package de.buw.se4de;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Eingaben {
	/**
	 * Erster Teil legt das Layout fest. 
	 * Zweiter Teil ist die Verbindung mit der Datenbank
	 */
	public static void display () {
		Stage eingabe = new Stage();
		Button abschicken = new Button("Eintragen");
		TextField notiz = new TextField("Kann leer gelassen werden");
		TextField betrag = new TextField("In Euro");
		notiz.setMaxSize(200, 25);
		betrag.setMaxSize(200, 25);
		eingabe.setTitle("Neuer Eintrag");
		//Fenster kann dann nicht ohne weiteres verlassen werden. Muss erst geschlossen werden
		eingabe.initModality(Modality.APPLICATION_MODAL);
		//only 1 radiobutton can be selectet
		ToggleGroup toggleGroup = new ToggleGroup();
		RadioButton art = new RadioButton("Eingabe");
		RadioButton art2 = new RadioButton("Ausgabe");
		art.setToggleGroup(toggleGroup);
		art2.setToggleGroup(toggleGroup);
		HBox radiobuttons = new HBox(5);
		radiobuttons.getChildren().add(art);
		radiobuttons.getChildren().add(art2);
		
		//TODO Kategorien müssen automatisch ausgewählt werden.
		ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3","Option 1",
                "Option 2",
                "Option 3"
        );
        listView.setItems(items);

        // Create a TextField for user input
        TextField userInputField = new TextField();

        // Überprüft eine Änderung, was ausgewählt wurde.
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Display the selected option in the TextField
            userInputField.setText(newValue);
        });

        VBox vbox = new VBox(10); // 10 is the spacing between nodes
        vbox.getChildren().addAll(listView, userInputField);
		
		
		
		
		
		VBox layout = new VBox(10);
		layout.getChildren().add(notiz);
		layout.getChildren().add(radiobuttons);  
		layout.getChildren().add(betrag);  
		layout.getChildren().add(vbox);  
		layout.getChildren().add(abschicken); 
		Scene scene = new Scene (layout, 300, 300);
		
		abschicken.setOnAction(e-> {
			//ein Array weil sonst kommt die Fehlermeldung:
			//Local variable order defined in an enclosing scope must be final or effectively final
			String[] order = { " " };
			Boolean eintragArt;
			String note = notiz.getText();
			String category = userInputField.getText();
			float money = (float) 0.0;
			try {
			money = (float)Double.parseDouble(betrag.getText());
			}catch(Exception n){
				
			}
			 toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
		            if (newValue != null) {
		                // A radio button was selected
		            	order[0] = ((RadioButton) newValue).getText();
		            } 
		       });
			if (order[0].equals("Eingabe")) {
				eintragArt = true;
			}else {
				eintragArt = false;
			}
			System.out.println(note);
			//wenn die Werte nicht stimmen, funktioniert das einfügen nicht
			try {
				new Datenbankmodifications().addGreeting(eintragArt,money, note, category);
			} catch (Exception e1) {
			}
		eingabe.close();});
		
		eingabe.setScene(scene);
		eingabe.showAndWait();
	}
}
