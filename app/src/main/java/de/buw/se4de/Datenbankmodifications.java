package de.buw.se4de;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;

public class Datenbankmodifications {
	public String getGreeting() throws Exception {

		String result = "";

		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/FUFA", "", "");

		Statement stmt = conn.createStatement();

		// Creates Database after the Requirements Ausgabe-Eingabe, Betrag, Kategorie
		// und eine Notiz
		// ToDo Datum:
		String createQ = "CREATE TABLE IF NOT EXISTS Konto"
				+ "(ID INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL, EINGABEAUSGABE VARCHAR, WERT DOUBLE, KATEGORIE VARCHAR(255), NOTIZ VARCHAR(255), DATUM DATE)";
		stmt.executeUpdate(createQ);
		ResultSet selectRS = stmt.executeQuery("SELECT * FROM Konto");

		// Ausgabe
		while (selectRS.next()) {
			int columns = selectRS.getMetaData().getColumnCount();
			for (int i = 1; i <= columns; i++) {
				System.out.printf("%s ", selectRS.getString(i));
			}
			System.out.println(); // Move to the next line for the next row
		}
		return result;
	}

	public void addGreeting(String art, double wert, String notiz, String kategorie, Date datum) throws Exception {

		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/FUFA", "", "");

		Statement stmt = conn.createStatement();

		// Creates Database after the Requirements Ausgabe-Eingabe, Betrag, Kategorie
		// und eine Notiz
		// ToDo Datum:
		String createQ = "CREATE TABLE IF NOT EXISTS Konto"
				+ "(ID INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL, EINGABEAUSGABE VARCHAR, WERT DOUBLE, KATEGORIE VARCHAR(255), NOTIZ VARCHAR(255), DATUM DATE)";
		stmt.executeUpdate(createQ);

		// Wenn man die Parameter direkt einfügt, entsteht ein Syntax Error, weswegen
		// wir einen SQL Placeholder nutzen.
		String insertQ = "INSERT INTO Konto (EINGABEAUSGABE, WERT, KATEGORIE, NOTIZ, DATUM) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement preparedStatement = conn.prepareStatement(insertQ)) {
			preparedStatement.setString(1, art);
			preparedStatement.setDouble(2, wert);
			preparedStatement.setString(3, kategorie);
			preparedStatement.setString(4, notiz);
			preparedStatement.setTimestamp(5, new Timestamp(datum.getTime()));
			preparedStatement.executeUpdate();
		}
	}
	/**
	 * 
	 * @return Die Summe wenn man alle 
	 * @throws Exception
	 */
	public double sum() throws Exception {
		double value = 0;
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/FUFA", "", "");

		Statement stmt = conn.createStatement();

		String createQ = "CREATE TABLE IF NOT EXISTS Konto"
				+ "(ID INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL, EINGABEAUSGABE VARCHAR, WERT DOUBLE, KATEGORIE VARCHAR(255), NOTIZ VARCHAR(255), DATUM DATE)";
		stmt.executeUpdate(createQ);
		ResultSet selectRS = stmt.executeQuery("SELECT * FROM Konto");

		while (selectRS.next()) {
			if (selectRS.getString(2).equals("Ausgabe")) {
				value -= selectRS.getDouble(3);
			} else {
				value += selectRS.getDouble(3);
			}

		}

		return value;
	}

	/**
	 * Wir senden zwei Anfragen an die Datenbankk und printen dann das Ergebnis aus.
	 * @return Eine Array mit den Datenbankeinträgen mit den jüngsten Daten
	 * @throws Exception
	 */
	public String[] datesWithDetails() throws Exception {
		//vorerst nur die 10 jüngsten
	    String[] dates = new String[10];

	    try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/FUFA", "", "")) {
	    	
	        // Anfrage an die Datenbank für die niedrigsten Daten
	        String dateQuery = "SELECT DATUM FROM Konto ORDER BY DATUM ASC LIMIT 10";
	        try (PreparedStatement dateStatement = conn.prepareStatement(dateQuery);
	             ResultSet dateResultSet = dateStatement.executeQuery()) {
	            int i = 9;
	            while (dateResultSet.next() && i > -1) {
	                String date = dateResultSet.getString("DATUM");
	                dates[i] = date;
	                i--;
	            }
	        }

	        // Vollständige Zeilen abrufen
	        String detailsQuery = "SELECT * FROM Konto WHERE DATUM IN (" + String.join(",", Collections.nCopies(10, "?")) + ") ORDER BY DATUM ASC";
	        try (PreparedStatement detailsStatement = conn.prepareStatement(detailsQuery)) {
	            // Parameter für die IN-Klausel setzen
	            for (int j = 0; j < 10; j++) {
	                detailsStatement.setString(j + 1, dates[j]);
	            }

	            try (ResultSet detailsResultSet = detailsStatement.executeQuery()) {
	                int j = 0;
	                while (detailsResultSet.next() && j < 10) {
	                    String zeile = "";
	                    int columns = detailsResultSet.getMetaData().getColumnCount();
	                    for (int k = 2; k <= columns; k++) {
	                        zeile = zeile + detailsResultSet.getString(k) + " ";
	                    }
	                    dates[j] += ": " + zeile.trim(); // Trim, um Leerzeichen am Ende zu entfernen
	                    j++;
	                }
	            }
	        }
	    }

	    return dates;
	}
}
