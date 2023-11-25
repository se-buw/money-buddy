package de.buw.se4de;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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
		abschicken.setOnAction(e-> eingabe.close());
		RadioButton art = new RadioButton("Eingabe");
		RadioButton art2 = new RadioButton("Ausgabe");
		HBox radiobuttons = new HBox(5);
		radiobuttons.getChildren().add(art);
		radiobuttons.getChildren().add(art2);
		
		
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

        // Event handler for when an item in the list is selected
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
		eingabe.setScene(scene);
		eingabe.showAndWait();
	}
}
