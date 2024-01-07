package de.buw.se4de;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class DatenbankmodificationsTest {

    @Test
    void testTest(){
        assertEquals(1, 1);
    }

    public void addGreetingTest(String url, String e_a, double value, String category, String note, String date) throws Exception{
        try{
            Connection conn = DriverManager.getConnection(url, "", "");

            Statement stmt = conn.createStatement();
            ResultSet selectRS = stmt.executeQuery("SELECT * FROM Konto WHERE ID = (SELECT MAX(ID) FROM Konto)");

            assertEquals(6, selectRS.getMetaData().getColumnCount());
            selectRS.next();
            String actual_e_a = selectRS.getString(2);
            double actual_value = selectRS.getDouble(3);
            String actual_category = selectRS.getString(4);
            String actual_note = selectRS.getString(5);
            String actual_date = selectRS.getString(6);

            assertEquals(e_a, actual_e_a);
            assertEquals(value, actual_value);
            assertEquals(note, actual_note);
            assertEquals(category, actual_category);
            assertEquals(date, actual_date);
        }
        catch (Exception e){
           fail();
        }


    }

    @Test
    void addGreetingTest_Eingabe() {
        String e_a = "Eingabe";
        double value = 100.0;
        String category = "Geschenk";
        String note = "hey";
        String date = "2020-02-02";

        Calendar calendar = Calendar.getInstance();
        int day = Integer.parseInt("02");
        int month = Integer.parseInt("02") - 1;
        int year = Integer.parseInt("2020");
        calendar.set(year, month, day);

        try { new Datenbankmodifications().addGreeting("jdbc:h2:./src/test/resources/FUFA", e_a, value, note, category, calendar); }
        catch (Exception e) { fail(); }

        try{ addGreetingTest("jdbc:h2:./src/test/resources/FUFA", e_a, value, category, note, date); }
        catch (Exception e){ fail(); }

    }

    @Test
    void addGreetingTest_Ausgabe() {
        String e_a = "Ausgabe";
        double value = 100.0;
        String category = "Geschenk";
        String note = "hey";
        String date = "2020-02-02";

        Calendar calendar = Calendar.getInstance();
        int day = Integer.parseInt("02");
        int month = Integer.parseInt("02") - 1;
        int year = Integer.parseInt("2020");
        calendar.set(year, month, day);

        try { new Datenbankmodifications().addGreeting("jdbc:h2:./src/test/resources/FUFA", e_a, value, note, category, calendar); }
        catch (Exception e) { fail(); }

        try{ addGreetingTest("jdbc:h2:./src/test/resources/FUFA", e_a, value, category, note, date); }
        catch (Exception e){ fail(); }

    }

    @Test
    void addGreetingTest_firstday() {
        String e_a = "Eingabe";
        double value = 100.0;
        String category = "Geschenk";
        String note = "hey";
        String date = "2020-02-01";

        Calendar calendar = Calendar.getInstance();
        int day = Integer.parseInt("01");
        int month = Integer.parseInt("02") - 1;
        int year = Integer.parseInt("2020");
        calendar.set(year, month, day);

        try { new Datenbankmodifications().addGreeting("jdbc:h2:./src/test/resources/FUFA", e_a, value, note, category, calendar); }
        catch (Exception e) { fail(); }

        try{ addGreetingTest("jdbc:h2:./src/test/resources/FUFA", e_a, value, category, note, date); }
        catch (Exception e){ fail(); }

    }

    @Test
    void addGreetingTest_firstmonth() {
        String e_a = "Eingabe";
        double value = 100.0;
        String category = "Geschenk";
        String note = "hey";
        String date = "2020-01-02";

        Calendar calendar = Calendar.getInstance();
        int day = Integer.parseInt("02");
        int month = Integer.parseInt("01") - 1;
        int year = Integer.parseInt("2020");
        calendar.set(year, month, day);

        try { new Datenbankmodifications().addGreeting("jdbc:h2:./src/test/resources/FUFA", e_a, value, note, category, calendar); }
        catch (Exception e) { fail(); }

        try{ addGreetingTest("jdbc:h2:./src/test/resources/FUFA", e_a, value, category, note, date); }
        catch (Exception e){ fail(); }

    }
    @Test
    void addGreetingTest_year1000() {
        String e_a = "Eingabe";
        double value = 100.0;
        String category = "Geschenk";
        String note = "hey";
        String date = "1000-02-02";

        Calendar calendar = Calendar.getInstance();
        int day = Integer.parseInt("02");
        int month = Integer.parseInt("02") - 1;
        int year = Integer.parseInt("1000");
        calendar.set(year, month, day);

        try { new Datenbankmodifications().addGreeting("jdbc:h2:./src/test/resources/FUFA", e_a, value, note, category, calendar); }
        catch (Exception e) { fail(); }

        try{ addGreetingTest("jdbc:h2:./src/test/resources/FUFA", e_a, value, category, note, date); }
        catch (Exception e){ fail(); }

    }
    @Test
    void addGreetingTest_year3000() {
        String e_a = "Eingabe";
        double value = 100.0;
        String category = "Geschenk";
        String note = "hey";
        String date = "3000-02-02";

        Calendar calendar = Calendar.getInstance();
        int day = Integer.parseInt("02");
        int month = Integer.parseInt("02") - 1;
        int year = Integer.parseInt("3000");
        calendar.set(year, month, day);

        try { new Datenbankmodifications().addGreeting("jdbc:h2:./src/test/resources/FUFA", e_a, value, note, category, calendar); }
        catch (Exception e) { fail(); }

        try{ addGreetingTest("jdbc:h2:./src/test/resources/FUFA", e_a, value, category, note, date); }
        catch (Exception e){ fail(); }

    }
    @Test
    void addGreetingTest_emptyNote() {
        String e_a = "Eingabe";
        double value = 100.0;
        String category = "Geschenk";
        String note = "";
        String date = "2020-02-02";

        Calendar calendar = Calendar.getInstance();
        int day = Integer.parseInt("02");
        int month = Integer.parseInt("02") - 1;
        int year = Integer.parseInt("2020");
        calendar.set(year, month, day);

        try { new Datenbankmodifications().addGreeting("jdbc:h2:./src/test/resources/FUFA", e_a, value, note, category, calendar); }
        catch (Exception e) { fail(); }

        try{ addGreetingTest("jdbc:h2:./src/test/resources/FUFA", e_a, value, category, note, date); }
        catch (Exception e){ fail(); }

    }











}