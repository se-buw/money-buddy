package de.buw.se4de;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Datenbankmodifications {
	
	public String getGreeting() throws Exception {
		String result = "";

		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/FUFA", "", "");

		Statement stmt = conn.createStatement();

		//Creates Database after the Requirements Ausgabe-Eingabe, Betrag, Kategorie und eine Notiz
		//ToDo Datum:
		String createQ = "CREATE TABLE IF NOT EXISTS Konto"
				+ "(ID INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL, IST_AUSGABE BOOLEAN, WERT FLOAT, KATEGORIE VARCHAR(255), NOTIZ VARCHAR(255))";
		stmt.executeUpdate(createQ);

		stmt.executeUpdate("INSERT INTO Konto (IST_AUSGABE, WERT, KATEGORIE, NOTIZ) VALUES(false,50,'Haushalt','Hello World!')");
		//stmt.executeUpdate("INSERT INTO Konto (NAME) VALUES('Hello again!')");
		//stmt.executeUpdate("INSERT INTO Konto (NAME) VALUES('Bye!')");

		ResultSet selectRS = stmt.executeQuery("SELECT * FROM Konto");
		/*
		while (selectRS.next()) {
			System.out.printf("%s, %s\n", selectRS.getString(1), selectRS.getString(2), selectRS.getString(3),selectRS.getString(4));
		}
*/
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

}
