package de.buw.se4de;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
import javafx.util.Pair;
import java.util.ArrayList;

public class Datenbankmodifications {
	// getTransaction
	public String getGreeting(String url) throws Exception {

		String result = ""; //hat keine Funktion

		Class.forName("org.h2.Driver"); //hat keine FUnktion
		Connection conn = DriverManager.getConnection(url, "", "");

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


		return result; //hat keine Funktion
	}


	// addTransaction
	public void addGreeting(String url, String art, double wert, String notiz, String kategorie, Calendar calendar) throws Exception {

		Class.forName("org.h2.Driver"); //hat keine Funktion
		Connection conn = DriverManager.getConnection(url, "", "");

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
			preparedStatement.setTimestamp(5, new Timestamp(calendar.getTimeInMillis()));
			preparedStatement.executeUpdate();
		}
	}



	/**
	 * 
	 * @return Die Summe wenn man alle 
	 * @throws Exception
	 */
	//gibt eine ArrayList mit den jeweiligen Geldeträgen der Kategorien zurück
	public ArrayList sum(String url) throws Exception {
		double value = 0;
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection(url, "", "");

		Statement stmt = conn.createStatement();

		String createQ = "CREATE TABLE IF NOT EXISTS Konto"
				+ "(ID INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL, EINGABEAUSGABE VARCHAR, WERT DOUBLE, KATEGORIE VARCHAR(255), NOTIZ VARCHAR(255), DATUM DATE)";
		stmt.executeUpdate(createQ);
		ResultSet selectRS = stmt.executeQuery("SELECT * FROM Konto");

		double mieteSum = 0;
		double lebensmittelSum = 0;
		double freizeitSum = 0;
		double gehaltSum = 0;
		double geschenkSum = 0;
		
		while (selectRS.next()) {
			if (selectRS.getString(2).equals("Ausgabe")) {
				value -= selectRS.getDouble(3);
				if (selectRS.getString(4).equals("Miete"))
				{
					mieteSum += selectRS.getDouble(3);
				}
				else if (selectRS.getString(4).equals("Lebensmittel"))
				{
					lebensmittelSum += selectRS.getDouble(3);
				}
				else if (selectRS.getString(4).equals("Freizeit"))
				{
					freizeitSum += selectRS.getDouble(3);
				}
			} 
			
			else {
				value += selectRS.getDouble(3);
				if (selectRS.getString(4).equals("Gehalt"))
				{
					gehaltSum += selectRS.getDouble(3);
				}
				else if (selectRS.getString(4).equals("Geschenke"))
				{
					geschenkSum += selectRS.getDouble(3);
				}
			}
			

		}

		ArrayList<Double> returnList = new ArrayList<Double>();
		returnList.add(value);
		returnList.add(mieteSum);
		returnList.add(lebensmittelSum);
		returnList.add(freizeitSum);
		returnList.add(gehaltSum);
		returnList.add(geschenkSum);
		return returnList;
	}

	/**
	 * Wir senden zwei Anfragen an die Datenbankk und printen dann das Ergebnis aus.
	 * @return Eine Array mit den Datenbankeinträgen mit den jüngsten Daten
	 * @throws Exception
	 */

	public Pair<Integer, String[]> datesWithDetails(String url) throws Exception {
	    String[] dates1 = new String[1000];
	    int resultSetSize = 0;
	    
	    
	    try (Connection conn = DriverManager.getConnection(url, "", "")) {
	    	
	    	
	        // Anfrage an die Datenbank für die niedrigsten Daten
	        String dateQuery = "SELECT DATUM FROM Konto ORDER BY DATUM DESC";
	        try (PreparedStatement dateStatement = conn.prepareStatement(dateQuery);
	             ResultSet dateResultSet = dateStatement.executeQuery()) {
	            int i = 0;
	            while (dateResultSet.next()) {
	                String date = dateResultSet.getString("DATUM");
	                dates1[i] = date;
	                i++;
	                resultSetSize++;
	            }
	            //dateResultSet.last();
	            // = dateResultSet.getRow();
	        }
	        catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // Vollständige Zeilen abrufen
	        String detailsQuery = "SELECT * FROM Konto WHERE DATUM IN (" + String.join(",", Collections.nCopies(resultSetSize, "?")) + ") ORDER BY DATUM DESC";
	        try (PreparedStatement detailsStatement = conn.prepareStatement(detailsQuery)) {
	            // Parameter für die IN-Klausel setzen
	            for (int j = 0; j < resultSetSize; j++) {
	                detailsStatement.setString(j + 1, dates1[j]);
	            }

	            try (ResultSet detailsResultSet = detailsStatement.executeQuery()) {
	                int j = 0;
	                while (detailsResultSet.next() && j < resultSetSize) {
	                    String zeile = "";
	                    int columns = detailsResultSet.getMetaData().getColumnCount();
	                    for (int k = 2; k < columns; k++) {
	                        zeile = zeile + detailsResultSet.getString(k) + " ";
	                    }
	                    dates1[j] += ": " + zeile.trim(); // Trim, um Leerzeichen am Ende zu entfernen
	                    j++;
	                }
	            }
	        }
	    }
	    String[] dates = new String[resultSetSize];
	    for (int i = 0; i < resultSetSize; i++)
	    {
	    	dates[i] = dates1[i];
	    }
	    Pair<Integer, String[]> pair = new Pair(resultSetSize, dates);
	    return pair;
	}
}
