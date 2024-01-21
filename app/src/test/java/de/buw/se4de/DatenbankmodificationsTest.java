package de.buw.se4de;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class DatenbankmodificationsTest {


    public void restoreTestDatabase() throws Exception{
        try{
            Connection conn = DriverManager.getConnection("jdbc:h2:./src/test/resources/FUFA", "", "");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM Konto WHERE ID > 148");
        }catch(Exception e){
            fail("Database restoring failed");
        }
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
        String category = "Geschenke";
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
        catch (Exception e){ fail();
        }

    }

    @Test
    void addGreetingTest_Ausgabe() {
        String e_a = "Ausgabe";
        double value = 100.0;
        String category = "Miete";
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
        String category = "Geschenke";
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
        String category = "Geschenke";
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
        String category = "Geschenke";
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

        //Dates before the 15.10.1582 don't work properly because the gregorian calendar didn't exist then.
        //This shouldn't realistically be a problem because it wouldn't make any sense letting the user input such a date,
        //so we restricted valid dates to be >1950.
    }
    @Test
    void addGreetingTest_year3000() {
        String e_a = "Eingabe";
        double value = 100.0;
        String category = "Geschenke";
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
        String category = "Geschenke";
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

    @Test
    public void sumTest(){
        try{restoreTestDatabase();
        }
        catch (Exception e){
            fail();
        }

        try{ ArrayList<Double> list = new Datenbankmodifications().sum("jdbc:h2:./src/test/resources/FUFA");
            assertEquals(56.5, list.get(0));
            assertEquals(200.0, list.get(1));
            assertEquals(76.5, list.get(2));
            assertEquals(118.0, list.get(3));
            assertEquals(335.0, list.get(4));
            assertEquals(116.0, list.get(5));

        }
        catch(Exception e){ fail(); }

    }


    @Test
    public void datesWithDetailsTest(){
        try{restoreTestDatabase();
        }
        catch (Exception e){
            fail();
        }

        try{
            Pair<Integer, String[]> pairRSIn = new Datenbankmodifications().datesWithDetails("jdbc:h2:./src/test/resources/FUFA", true);
            Pair<Integer, String[]> pairRSOut = new Datenbankmodifications().datesWithDetails("jdbc:h2:./src/test/resources/FUFA", false);

            assertEquals(4, pairRSIn.getKey());
            assertEquals(6, pairRSOut.getKey());

            assertEquals("2020-01-02: Eingabe 100.0 Gehalt Gehalt Dezember", pairRSIn.getValue()[3]);
            assertEquals("2020-06-15: Eingabe 235.0 Gehalt Gehalt Juni", pairRSIn.getValue()[2]);
            assertEquals("2020-10-30: Eingabe 66.0 Geschenke Ostern", pairRSIn.getValue()[1]);
            assertEquals("2020-10-30: Eingabe 50.0 Geschenke Geburtstag", pairRSIn.getValue()[0]);

            assertEquals("2020-01-10: Ausgabe 10.5 Lebensmittel Einkauf", pairRSOut.getValue()[5]);
            assertEquals("2020-11-01: Ausgabe 100.0 Miete Miete November", pairRSOut.getValue()[4]);
            assertEquals("2020-11-22: Ausgabe 100.0 Freizeit Shoppen", pairRSOut.getValue()[3]);
            assertEquals("2020-11-23: Ausgabe 18.0 Freizeit Essen gehen", pairRSOut.getValue()[2]);
            assertEquals("2021-01-22: Ausgabe 100.0 Miete Miete Januar", pairRSOut.getValue()[1]);
            assertEquals("2021-03-03: Ausgabe 66.0 Lebensmittel Wocheneinkauf", pairRSOut.getValue()[0]);




        }
        catch(Exception e){
            fail();
        }


    }










}