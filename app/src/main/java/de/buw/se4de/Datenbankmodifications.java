package de.buw.se4de;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Datenbankmodifications {
	/*
	public String categories() throws Exception{
		String result = "";
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/Kategorien", "", "");

		Statement stmt = conn.createStatement();

		//Kategorien haben einen Namen, gehören zu einen Konto, haben eine Anzahl an Einträgen und sind ein oder Ausgaben
		
		//TODO verschiedene Konten müssen hinzugefügt werden
		//TODO Anzahl muss getracked werden, um löschen zu ermöglichen
		String createQ = "CREATE TABLE IF NOT EXISTS Kategorien"
				+ "(ID INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL, NAME VARCHAR(255), IST_AUSGABE BOOLEAN)";
		stmt.executeUpdate(createQ);
		ResultSet selectRS = stmt.executeQuery("SELECT * FROM Kategorien");
		stmt.executeUpdate("INSERT INTO Kategorien (NAME, IST_AUSGABE) VALUES('Haushalt', true)");
		//Ausgabe
		while (selectRS.next()) {
		    int columns = selectRS.getMetaData().getColumnCount();
		    for (int i = 1; i <= columns; i++) {
		        System.out.printf("%s ", selectRS.getString(i));
		    }
		    System.out.println();  // Move to the next line for the next row
		}
		return result;
	}
	*/
	public String getGreeting() throws Exception {
		
		String result = "";

		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/FUFA", "", "");

		Statement stmt = conn.createStatement();

		//Creates Database after the Requirements Ausgabe-Eingabe, Betrag, Kategorie und eine Notiz
		//ToDo Datum:
		String createQ = "CREATE TABLE IF NOT EXISTS Konto"
				+ "(ID INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL, EINGABEAUSGABE VARCHAR, WERT FLOAT, KATEGORIE VARCHAR(255), NOTIZ VARCHAR(255))";
		stmt.executeUpdate(createQ);
		ResultSet selectRS = stmt.executeQuery("SELECT * FROM Konto");
		
		//Ausgabe
		while (selectRS.next()) {
		    int columns = selectRS.getMetaData().getColumnCount();
		    for (int i = 1; i <= columns; i++) {
		        System.out.printf("%s ", selectRS.getString(i));
		    }
		    System.out.println();  // Move to the next line for the next row
		}
		return result;
	}
	public void addGreeting(String art, float wert, String notiz, String kategorie) throws Exception {
		
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/FUFA", "", "");

		Statement stmt = conn.createStatement();

		//Creates Database after the Requirements Ausgabe-Eingabe, Betrag, Kategorie und eine Notiz
		//ToDo Datum:
		String createQ = "CREATE TABLE IF NOT EXISTS Konto"
				+ "(ID INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL, EINGABEAUSGABE VARCHAR, WERT FLOAT, KATEGORIE VARCHAR(255), NOTIZ VARCHAR(255))";
		stmt.executeUpdate(createQ);
		
		//Wenn man die Parameter direkt einfügt, entsteht ein Syntax Error, weswegen wir einen SQL Placeholder nutzen.
		String insertQ = "INSERT INTO Konto (EINGABEAUSGABE, WERT, KATEGORIE, NOTIZ) VALUES (?, ?, ?, ?)";
	    try (PreparedStatement preparedStatement = conn.prepareStatement(insertQ)) {
	        preparedStatement.setString(1, art);
	        preparedStatement.setFloat(2, wert);
	        preparedStatement.setString(3, kategorie);
	        preparedStatement.setString(4, notiz);

	        preparedStatement.executeUpdate();
	    }
	}

}
