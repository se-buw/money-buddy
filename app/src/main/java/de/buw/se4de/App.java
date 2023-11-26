/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package de.buw.se4de;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class App extends Application {
	  @Override
	    public void start(Stage stage) {
		  	//Setup
		  	Button eingaben = new Button("Eintrag erstellen");
		  	// Umlaute werden sonst nicht richtig dargestellt
		  	stage.setTitle("F\u00dcFA");
	        Text text = new Text("F\u00dcFA - Finanz \u00dcbersicht f\u00fcr Anf\u00e4nger");
	        BorderPane border = new BorderPane();
	        text.setFill(Color.BLACK);
	        text.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 20));
	        border.setTop(new StackPane(text));
	        border.setBottom(new StackPane(eingaben));
	        
	        //Eingaben von Ein-und Ausgaben realisieren
	        
	        eingaben.setOnAction(e -> Eingaben.display());
	        
	        Scene scene = new Scene(border, 640, 480);
	        stage.setScene(scene);
	        stage.show();
	    }

	public static void main(String[] args) throws Exception {
		launch();
		new Datenbankmodifications().getGreeting();
	}
}
