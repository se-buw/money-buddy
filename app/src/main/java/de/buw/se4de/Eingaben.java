package de.buw.se4de;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Calendar;
import javafx.scene.paint.*;
public class Eingaben {
	
	static String notizTextString = "Kann leer gelassen werden";
	static boolean categorySelected = false;

	public static String note_test(String note, String default_note){
		if (note.equals(notizTextString))
		{
			note = "Transaktion";
		}
		return note;
	}
	//prüft, ob seperate Notiz erstellt wurde oder nicht, falls nicht wird einfach Transaktion ausgegeben
	//Übergabe: note = dass was im Notizfeld steht, deafultnote = Kann leer gelassen werden Rückgabe: falls Feld geändert wurde, neue Notiz, falls default stehenbleibt, Transaktion
	public static boolean category_test(Label ErrorText, boolean categorySelected){  //prüft, ob Kategorie gewählt wurde oder nicht
		if (!categorySelected) {
			ErrorText.setText("Bitte w\u00e4hlen Sie eine Kategorie aus");
			return false;
		}
		return true;
	}

	public static Pair<Boolean,Double> money_parse_test(TextField betrag, Label ErrorText, Double money){//legt money fest und prüft, ob valider Wert eingegeben wurde
		try {
			money = Double.parseDouble(betrag.getText());
			Pair<Boolean,Double> returnPair = new Pair<>(true, money);
			return returnPair;
		}catch(Exception n){
			ErrorText.setText("Bitte korrekten Betrag eingeben");
			Pair<Boolean,Double> returnPair = new Pair<>(false, money);
			return returnPair;
		}
	}
	public static Boolean calendar_test(Calendar calendar, int day, int month, int year, Label ErrorText){
		if(day <= calendar.getActualMaximum(calendar.DAY_OF_MONTH) && month < 13 && month > 0 && year > 0 && day > 0){
			calendar.set(year, month, day);
			return true;
		}
		else {
			ErrorText.setText("Bitte korrektes Datum eigeben");
			return false;
		}
	}
//nicht sicher was die folgende Try and Catch Funktion abfangen soll
	public static Boolean database_test(Calendar calendar, int day, int month, int year, Label ErrorText, double money, String note, String category, Boolean test, String[] order){
		try {
			test = calendar_test(calendar, day, month, year, ErrorText);
			if (!test)//else leave function
				return false;
			new Datenbankmodifications().addTransaction(order[0],money, note, category, calendar);
		} catch (Exception e1) {
			ErrorText.setText("Bitte korrektes Datum eigeben");
			return false;
		}
		return true;
	}

	/**
	 * Erster Teil legt das Layout fest. 
	 * Zweiter Teil ist die Verbindung mit der Datenbank
	 */
	public static void display () {
	//Deklarationen
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
		//Programm kann erst geschlossen werden, wenn Transaktionenfenster geschlossen wurde
		eingabe.initModality(Modality.APPLICATION_MODAL);
		//only 1 radiobutton can be selected
		ToggleGroup toggleGroup = new ToggleGroup();
		RadioButton art = new RadioButton("Eingabe");
		RadioButton art2 = new RadioButton("Ausgabe");
		art.setToggleGroup(toggleGroup);
		art2.setToggleGroup(toggleGroup);
		HBox radiobuttons = new HBox(5);
		radiobuttons.getChildren().add(art);
		radiobuttons.getChildren().add(art2);
		Label userInputField = new Label();
	//bis hier her

	//Radiobutton, welcher gewählt wurde
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

//auch nur Deklarationen
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
//bis hier



		abschicken.setOnAction(e-> {
			//ein Array weil sonst kommt die Fehlermeldung:
			//Local variable order defined in an enclosing scope must be final or effectively final
			String dateStringDay = datumDay.getText(); // Get the date string from the text field
			String dateStringMonth = datumMonth.getText();
			String dateStringYear = datumYear.getText();
			String note = notiz.getText();
			Boolean test = true; //if test is false, one input isnt right

			note = note_test(note, notizTextString);

			test = category_test(ErrorText, categorySelected); //prüft, ob Kategorie gewählt wurde oder nicht
			if (!test)
				return;
			
			String category = userInputField.getText();

			double money =  0.0;
			test = money_parse_test(betrag, ErrorText, money).getKey();
			if (!test)
				return;
			money = money_parse_test(betrag, ErrorText, money).getValue();

			//wenn die Werte nicht stimmen, funktioniert das Einfügen nicht

			//Damit wir für die Datenbank ein Datums Format haben
			//SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
			//Date date = dateFormat.parse(dateStringDay);
			Calendar calendar = Calendar.getInstance();
			int day = Integer.parseInt(dateStringDay);
			int month = Integer.parseInt(dateStringMonth)-1;
			int year = Integer.parseInt(dateStringYear);
			calendar.set(year,month,1);

			test = database_test(calendar, day, month, year, ErrorText, money,note, category, test, order);
			if (!test)
				return;//leave function

		eingabe.close();
		});
		
		eingabe.setScene(scene);
		eingabe.showAndWait();
	}


}





//falls bei Ausgabe keine Kategorie angegeben wird, ist das ok und es steht nur Transaktion da, bei einnahme geht das nichz