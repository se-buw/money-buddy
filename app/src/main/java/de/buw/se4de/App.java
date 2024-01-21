/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package de.buw.se4de;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;


public class App extends Application {
		public boolean showing_Income = true;

		public static double arraySum (ArrayList<Double> difference, int bound_left, int bound_right) {
			double sum = 0;
			for (int i = bound_left; i < bound_right; ++i) {
				sum += difference.get(i);
			}
			return sum;
		}//bekommt ein Array und bekommt zwei Grenzen und summiert die Werte im Array zwischen diesen Grenzen

		@Override
	    public void start(Stage stage) {
		  	//Setup
		  
		  	ArrayList<Double> difference = new ArrayList<Double>(10);
		  	try {
		  		difference = new Datenbankmodifications().sum("jdbc:h2:./src/main/resources/FUFA");
			} catch (Exception e) {
				System.out.println("Hier stimmt was nicht");
			}
		  	String diff = difference.get(0) + "";
		  	Button eingaben = new Button("Transaktion hinzuf\u00fcgen");
		  	Button aktualisieren = new Button("Aktualisieren");

			ComboBox<String> inOutComboBox = new ComboBox<>();
			ObservableList<String> categories = FXCollections.observableArrayList("Eingabe", "Ausgabe");
			inOutComboBox.setValue("Eingabe");
			inOutComboBox.setItems(categories);

		  	// Umlaute werden sonst nicht richtig dargestellt
		  	stage.setTitle("F\u00dcFA");
	        Text text = new Text("F\u00dcFA - Finanz \u00dcbersicht f\u00fcr Anf\u00e4nger");
	        Label differenz = new Label("   Aktuelle Differenz:     ");
	        differenz.setFont(Font.font("Standart",FontWeight.BOLD,15));
	        Label space = new Label("   "+diff + " Euro");
	        Label transaktion = new Label("Letzte Transaktionen");
	        BorderPane border = new BorderPane();
	        VBox leftborder = new VBox(2);
	        
	        Label ausgaben = new Label("   " + "Ausgaben:");
	        ausgaben.setFont(Font.font("Standart",FontWeight.BOLD,15));
	        Label mieteName = new Label("   " + "F\u00fcr Miete:");
	        mieteName.setFont(Font.font("Standart",FontPosture.ITALIC,13));
	        Label lebensmittelName = new Label("   " + "F\u00fcr Lebensmittel:");
	        lebensmittelName.setFont(Font.font("Standart",FontPosture.ITALIC,13));
	        Label freizeitName = new Label("   " + "F\u00fcr Freizeit:");
	        freizeitName.setFont(Font.font("Standart",FontPosture.ITALIC,13));
	        Label mieteZahl = new Label("");
	        Label lebensmittelZahl = new Label("");
	        Label freizeitZahl = new Label("");
	        Label einnahmen = new Label("   " + "Einnahmen:");
	        einnahmen.setFont(Font.font("Standart",FontWeight.BOLD,15));
	        Label gehaltName = new Label("   " + "Von Gehalt:");
	        gehaltName.setFont(Font.font("Standart",FontPosture.ITALIC,13));
	        Label geschenkeName = new Label("   " + "Von Geschenken:");
	        geschenkeName.setFont(Font.font("Standart",FontPosture.ITALIC,13));
	        Label gehaltZahl = new Label("");
	        Label geschenkeZahl = new Label("");
	        
	        Label mieteSpace = new Label("");
	        Label lebensmittelSpace = new Label("");
	        Label freizeitSpace = new Label("");
	        Label gehaltSpace = new Label("");
	        Label geschenkeSpace = new Label("");
	        
	        // berechnet den Prozentualen Geldwert jeder Kategorie
	        double sumAusgaben = arraySum(difference, 1, 4);
			double sumEinnahmen = arraySum(difference, 4, 6);
	        
	        double mieteProzent = difference.get(1) / sumAusgaben * 100; 
	        double lebensmittelProzent = difference.get(2) / sumAusgaben * 100; 
	        double freizeitProzent = difference.get(3) / sumAusgaben * 100; 
	        double gehaltProzent = difference.get(4) / sumEinnahmen * 100; 
	        double geschenkeProzent = difference.get(5) / sumEinnahmen * 100; 
	        
	        mieteZahl.setText("   " + String.valueOf(difference.get(1)) + " Euro - " + String.format("%.2f",mieteProzent) + " \u0025");
            lebensmittelZahl.setText("   " + String.valueOf(difference.get(2)) + " Euro - " + String.format("%.2f",lebensmittelProzent) + " \u0025");
            freizeitZahl.setText("   " + String.valueOf(difference.get(3)) + " Euro - " + String.format("%.2f",freizeitProzent) + " \u0025");
            gehaltZahl.setText("   " + String.valueOf(difference.get(4)) + " Euro - " + String.format("%.2f",gehaltProzent) + " \u0025");
            geschenkeZahl.setText("   " + String.valueOf(difference.get(5)) + " Euro - " + String.format("%.2f",geschenkeProzent) + " \u0025");
	        
	        VBox rightborder = new VBox(10);
	        VBox centerborder = new VBox(10);
	        HBox bottomborder = new HBox(20);
	        String[] dates = {" "};
	        Pair<Integer, String[]> pair = new Pair(0,dates);
	        //String[] dates = {" "};
	        int datesSize;
	        
	        
	        try {
				 pair = new Datenbankmodifications().datesWithDetails("jdbc:h2:./src/main/resources/FUFA", showing_Income);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        ListView<String> listView = new ListView<>();
	        ObservableList<String> items = FXCollections.observableArrayList(pair.getValue());
	        listView.setItems(items);
	        bottomborder.getChildren().addAll(eingaben);
	        rightborder.getChildren().addAll(eingaben,aktualisieren);
	        border.setRight(rightborder);
	        leftborder.getChildren().addAll( differenz, space, ausgaben, mieteName, mieteZahl, mieteSpace, lebensmittelName, lebensmittelZahl, lebensmittelSpace, freizeitName,  freizeitZahl, freizeitSpace, einnahmen, gehaltName, gehaltZahl, gehaltSpace, geschenkeName, geschenkeZahl, geschenkeSpace);
	        centerborder.getChildren().addAll(inOutComboBox, transaktion,listView);
	        text.setFill(Color.BLACK);
	        text.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 20));
	        border.setTop(new StackPane(text));
	        border.setBottom(bottomborder);
	        border.setLeft(leftborder);
	        border.setCenter(centerborder);

			inOutComboBox.setOnAction(e ->{
				String selectedCategory = inOutComboBox.getValue();
				if (selectedCategory.equals("Eingabe")){
					showing_Income = true;
				}
				else if (selectedCategory.equals("Ausgabe")){
					showing_Income = false;
				}
			});

	        //Eingaben von Ein-und Ausgaben realisieren
	        
	        eingaben.setOnAction(e -> Eingaben.display());
	        aktualisieren.setOnAction(e->{ 
	        	try {
	        		String neu = "";
	        		ArrayList<Double> difference1 = new ArrayList<Double>(10);
					difference1 = new Datenbankmodifications().sum("jdbc:h2:./src/main/resources/FUFA");
					neu = difference1.get(0) + "";
					space.setText("   "+neu+ " Euro");
					
					double sumAusgaben1 = arraySum(difference1, 1, 4);
			        double sumEinnahmen1 = arraySum(difference1, 4, 6);
			        
			        double mieteProzent1 = difference1.get(1) / sumAusgaben1 * 100; 
			        double lebensmittelProzent1 = difference1.get(2) / sumAusgaben1 * 100; 
			        double freizeitProzent1 = difference1.get(3) / sumAusgaben1 * 100; 
			        double gehaltProzent1 = difference1.get(4) / sumEinnahmen1 * 100; 
			        double geschenkeProzent1 = difference1.get(5) / sumEinnahmen1 * 100; 
			        
					
					mieteZahl.setText("   " + String.valueOf(difference1.get(1)) + " Euro - " + String.format("%.2f",mieteProzent1) + " \u0025");
		            lebensmittelZahl.setText("   " + String.valueOf(difference1.get(2)) + " Euro - " + String.format("%.2f",lebensmittelProzent1) + " \u0025");
		            freizeitZahl.setText("   " + String.valueOf(difference1.get(3)) + " Euro - " + String.format("%.2f",freizeitProzent1) + " \u0025");
		            gehaltZahl.setText("   " + String.valueOf(difference1.get(4)) + " Euro - " + String.format("%.2f",gehaltProzent1) + " \u0025");
		            geschenkeZahl.setText("   " + String.valueOf(difference1.get(5)) + " Euro - " + String.format("%.2f",geschenkeProzent1) + " \u0025");
					
					
					Pair<Integer, String[]> pair2;
					pair2 = new Datenbankmodifications().datesWithDetails("jdbc:h2:./src/main/resources/FUFA", showing_Income);
					items.removeAll(items);
					for (int i = 0; i < pair2.getKey(); i++  )
				       {
				        	items.add(pair2.getValue()[i]);
				       }
					listView.setItems(items);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	        });
	        Scene scene = new Scene(border, 640, 480);
	        stage.setScene(scene);
	        stage.show();
	    }

	public static void main(String[] args) throws Exception {
		launch();
		new Datenbankmodifications().getGreeting("jdbc:h2:./src/test/resources/FUFA");
	}
}
