package de.buw.se4de;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class DatenbankmodificationsTest {

    @Test
    void testTest(){
        assertEquals(1, 1);
    }



    @Test
    void addGreetingTest() {
        Calendar calendar = Calendar.getInstance();
        int day = Integer.parseInt("02");
        int month = Integer.parseInt("02")-1;
        int year = Integer.parseInt("2020");
        calendar.set(year,month,day);
        try{
            new Datenbankmodifications().addGreeting("Eingabe", 100.0, "hey", "Geschenk", calendar);
        }
        catch(Exception e){
            fail();
        }

        try{
            Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/FUFA", "", "");

            Statement stmt = conn.createStatement();
            ResultSet selectRS = stmt.executeQuery("SELECT * FROM Konto WHERE ID = (SELECT MAX(ID) FROM Konto)");

            assertEquals(6, selectRS.getMetaData().getColumnCount());
            selectRS.next();
            String art = selectRS.getString(2);
            double betrag = selectRS.getDouble(3);
            String kategorie =  selectRS.getString(4);
            String notiz = selectRS.getString(5);
            String datum = selectRS.getString(6);

            assertEquals("Eingabe", art);
            assertEquals(100.0, betrag);
            assertEquals("hey", notiz);
            assertEquals("Geschenk", kategorie);
            assertEquals("2020-02-02", datum);


        }
        catch(Exception e){
            fail();
        }






    }


}