package de.buw.se4de;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Eingaben {
	/**
	 * Erster Teil legt das Layout fest. 
	 * Zweiter Teil ist die Verbindung mit der Datenbank
	 */
	public static void display () {
		Stage eingabe = new Stage();
		Button abschicken = new Button("Eintragen");
		Label notizText = new Label("Notiz");
		Label betragText = new Label("Betrag");
		Label datumText = new Label("Datum");
		TextField notiz = new TextField("Kann leer gelassen werden");
		TextField betrag = new TextField("In Euro");
		TextField datum = new TextField("DD-MM-YYYY");
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
		
		//TODO Kategorien müssen anpassbar sein.
		String[] order = {""};
		ListView<String> listView = new ListView<>();
		 toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
	            if (newValue.equals(art)) {
	            	 ObservableList<String> items = FXCollections.observableArrayList(
	                         "Gehalt",
	                         "Geschenke"
	                 );
	            	 listView.setItems(items);
	            	 order[0] = "Eingabe";
	            }else {
	            	ObservableList<String> items = FXCollections.observableArrayList(
	                         "Miete",
	                         "Lebensmittel",
	                         "Freizeit"
	                 );
	            	 listView.setItems(items);
	            	 order[0] = "Ausgabe";
	            }
	       });

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
		layout.getChildren().add(new HBox(10,notizText,notiz));
		layout.getChildren().add(radiobuttons);  
		layout.getChildren().add(new HBox(10,betragText,betrag));  
		layout.getChildren().add(new HBox(10,datumText,datum));
		layout.getChildren().add(vbox);  
		layout.getChildren().add(abschicken); 
		Scene scene = new Scene (layout, 300, 300);
		
		abschicken.setOnAction(e-> {
			//ein Array weil sonst kommt die Fehlermeldung:
			//Local variable order defined in an enclosing scope must be final or effectively final
			String dateString = datum.getText(); // Get the date string from the text field
			String note = notiz.getText();
			String category = userInputField.getText();
			double money =  0.0;
			try {
			money = Double.parseDouble(betrag.getText());
			}catch(Exception n){
				
			}
			//wenn die Werte nicht stimmen, funktioniert das einfügen nicht
			try {
				//Damit wir für die Datenbank ein Datums Format haben
				SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
		        Date date = dateFormat.parse(dateString);
				new Datenbankmodifications().addGreeting(order[0],money, note, category, date);
			} catch (Exception e1) {
			}
		eingabe.close();
		});
		
		eingabe.setScene(scene);
		eingabe.showAndWait();
	}
}
