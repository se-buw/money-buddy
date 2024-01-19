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
import java.util.Calendar;
import javafx.scene.paint.*;
import javafx.util.Pair;

public class Eingaben {
	
	static String notizTextString = "Kann leer gelassen werden";
	static boolean categorySelected = false;
	
	/**
	 * Erster Teil legt das Layout fest. 
	 * Zweiter Teil ist die Verbindung mit der Datenbank
	 */
	public static String note_test(String note, String notizTextString) {
		if (note.equals(notizTextString)) {
			note = "";
		}
		return note;
	}//prüft ob die Notiz im Notizfeld geändert wurde oder nicht, falls nein wird die Notiz als Transaktion gesetzt

	public static Pair<Boolean, Double> money_parse_test(String geld_betrag, Double money) {
		try {
			money = Double.parseDouble(geld_betrag);
			Pair<Boolean, Double> returnPair = new Pair(true, money);
			return returnPair;
		} catch (Exception var5) {
			Pair<Boolean, Double> returnPair = new Pair(false, money);
			return returnPair;
		}
	}//prüft, ob die Eingabe ein Zahlenwert oder etwas anderes darstellt,
	 //wenn es eine Zahl ist gibt es sie zurück, falls nicht gib falsch

	public static Boolean calendar_test(Calendar calendar, int day, int month, int year) {
		month = month - 1;

		Calendar tempCal = Calendar.getInstance();
		tempCal.set(year, month, 1);
		if(month < 12 && month >= 0 && year > 0 && day > 0 && day <= tempCal.getActualMaximum(tempCal.DAY_OF_MONTH)){
			calendar.set(year, month, day);
			return true;
		} else {
			return false;
		}
	}//prüft, ob die angegebenen Daten ein korrektes Datum ergeben

	public static void display () {
		Stage eingabe = new Stage();
		Button abschicken = new Button("Eintragen");
		Label notizText = new Label("Notiz");
		Label betragText = new Label("Betrag");
		Label datumText = new Label("Datum");
		Label ErrorText = new Label();
		ErrorText.setTextFill(Color.RED);
		TextField notiz = new TextField(notizTextString);
		TextField betrag = new TextField("In Euro");
		TextField datumDay = new TextField("DD");
		datumDay.setMaxWidth(50);
		TextField datumMonth = new TextField("MM");
		datumMonth.setMaxWidth(50);
		TextField datumYear = new TextField("YYYY");
		datumYear.setMaxWidth(50);
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
		Label userInputField = new Label();
		
		
		
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
        

        // Überprüft eine Änderung, was ausgewählt wurde.
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Display the selected option in the TextField
            userInputField.setText(newValue);
            categorySelected = true;
            
        });

        VBox vbox = new VBox(10); // 10 is the spacing between nodes
        vbox.getChildren().addAll(listView, userInputField);

		VBox layout = new VBox(10);
		layout.getChildren().add(new HBox(10,notizText,notiz));
		layout.getChildren().add(radiobuttons);  
		layout.getChildren().add(new HBox(10,betragText,betrag));  
		layout.getChildren().add(new HBox(10,datumText,datumDay,datumMonth,datumYear));
		layout.getChildren().add(vbox);  
		layout.getChildren().add(abschicken);
		layout.getChildren().add(ErrorText);
		Scene scene = new Scene (layout, 400, 400);
		
		
		abschicken.setOnAction(e-> {
			//ein Array weil sonst kommt die Fehlermeldung:
			//Local variable order defined in an enclosing scope must be final or effectively final
			String dateStringDay = datumDay.getText(); // Get the date string from the text field
			String dateStringMonth = datumMonth.getText();
			String dateStringYear = datumYear.getText();
			String note = notiz.getText();
			boolean test;

			note = note_test(note, notizTextString);
			
			if (!categorySelected) {
				ErrorText.setText("Bitte w\u00e4hlen Sie eine Kategorie aus");
				return;
			}
			String category = userInputField.getText();
			double geld_betrag_d = 0.0;
			try {
				geld_betrag_d = Double.parseDouble(betrag.getText());
			}catch(Exception n){
				ErrorText.setText("Bitte korrekten Betrag eingeben");
				return;
			}
			/*
			test = (Boolean)money_parse_test(betrag.getText(), geld_betrag_d).getKey();
			if (!test){
				ErrorText.setText("Bitte geben Sie einen gültigen Betrag an");
				return;
			}
			*/

			//wenn die Werte nicht stimmen, funktioniert das einfügen nicht
			try {
				//Damit wir für die Datenbank ein Datums Format haben
				//SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
		        //Date date = dateFormat.parse(dateStringDay);
		        Calendar calendar = Calendar.getInstance();
				/*
		        int day = Integer.parseInt(dateStringDay);
		        int month = Integer.parseInt(dateStringMonth)-1;
		        int year = Integer.parseInt(dateStringYear);
		        calendar.set(year, month,1);*/
				test = calendar_test(calendar, Integer.parseInt(dateStringDay), Integer.parseInt(dateStringMonth), Integer.parseInt(dateStringYear));
				if (!test){
					ErrorText.setText("Bitte geben Sie ein gültiges Datum an");
					return;
				}
		        
				new Datenbankmodifications().addGreeting("jdbc:h2:./src/main/resources/FUFA", order[0],geld_betrag_d, note, category, calendar);
			} catch (Exception e1) {
				ErrorText.setText("Bitte korrektes Datum eigeben");
				return;
			}
		eingabe.close();
		});
		
		eingabe.setScene(scene);
		eingabe.showAndWait();
	}
}
