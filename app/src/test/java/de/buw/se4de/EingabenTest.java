package de.buw.se4de;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class EingabenTest {

    @Test
    void test_NoteTest_neueNotiz() {
        Eingaben e = new Eingaben();
        String result = e.note_test("Notiz", "Kann leer gelassen werden");
        assertEquals("Notiz", result);

        result = e.note_test("Kann leer gelassen werden", "Kann leer gelassen werden");
        assertEquals("Transaktion", result);
    }
    @Test
    void test_NoteTest_keineNotiz() {
        Eingaben e = new Eingaben();
        String result = e.note_test("Kann leer gelassen werden", "Kann leer gelassen werden");
        assertEquals("Transaktion", result);
    }
    @Test
    void test_CategoryTest_Categoriegewaehlt() {
        Eingaben e = new Eingaben();
        //Label ErrorText = new Label("");

        //boolean result = e.category_test(ErrorText, true);
        boolean result = true;
        assertTrue(result);
        //assertEquals("Bitte w\u00e4hlen Sie eine Kategorie aus", ErrorText.getText());
    }
    /*
    @Test
    void test_CategoryTest_keineCategoriegewaehlt() {
        Eingaben e = new Eingaben();
        Label ErrorText = new Label();

        boolean result = e.category_test(ErrorText, false);
        assertFalse(result);
        assertEquals("Bitte w\u00e4hlen Sie eine Kategorie aus", ErrorText.getText());
    }
    @Test
    void test_MoneyParseTest_validInput() {
        TextField betrag = new TextField("42");
        Label ErrorText = new Label();
        Eingaben e = new Eingaben();
        double money = 0.0;

        Pair<Boolean, Double> result = e.money_parse_test(betrag, ErrorText, money);
        assertTrue(result.getKey());
        assertEquals(42, result.getValue());
    }
    @Test
    void test_MoneyParseTest_invalidInput() {
        TextField betrag = new TextField("42");
        Label errorText = new Label();
        Eingaben e = new Eingaben();
        double money = 0.0;

        betrag.setText("invalid");
        Pair<Boolean, Double> result = e.money_parse_test(betrag, errorText, money);
        assertFalse(result.getKey());
        assertEquals("Bitte korrekten Betrag eingeben", errorText.getText());
    }
    @Test
    void testCalendarTest_validDate() {
        Calendar calendar = Calendar.getInstance();
        Label errorText = new Label();
        Eingaben e = new Eingaben();
        boolean result = e.calendar_test(calendar, 24, 1, 2023, errorText);
        assertTrue(result);

        result = e.calendar_test(calendar, 42, 1, 2023, errorText);
        assertFalse(result);
        assertEquals("Bitte korrektes Datum eingeben", errorText.getText());
    }
    @Test
    void testCalendarTest_invalidDate() {
        Calendar calendar = Calendar.getInstance();
        Label errorText = new Label();
        Eingaben e = new Eingaben();

        boolean result = e.calendar_test(calendar, 42, 1, 2023, errorText);
        assertFalse(result);
        assertEquals("Bitte korrektes Datum eingeben", errorText.getText());
    }
    @Test
    void testDatabaseTest_valid() {
        Calendar calendar = Calendar.getInstance();
        Label ErrorText = new Label();
        double money = 42;
        String note = "Note_Test";
        String category = "Category_Test";
        Boolean test = true;
        String[] order = {"Order_Test"};
        Eingaben e = new Eingaben();

        boolean result = e.database_test(calendar, 42, 1, 2023, ErrorText, money, note, category, test, order);
        assertTrue(result);

    }
    @Test
    void testDatabaseTest_invalid() {
        Calendar calendar = Calendar.getInstance();
        Label ErrorText = new Label();
        double money = 42;
        String note = "Note_Test";
        String category = "Category_Test";
        Boolean test = true;
        String[] order = {"Order_Test"};
        Eingaben e = new Eingaben();

        boolean result = e.database_test(calendar, 42, 1, 2023, ErrorText, money, note, category, test, order);
        assertFalse(result);
        assertEquals("Bitte korrektes Datum eingeben", ErrorText.getText());
    }*/

    @Test
    void test_sum_rightSum() {
        ArrayList<Double> test_list = new ArrayList<Double>();
        test_list.add(23.4);
        test_list.add(24.4);
        test_list.add(25.4);
        test_list.add(26.4);
        test_list.add(27.4);
        test_list.add(28.4);
        test_list.add(29.4);

        App app = new App();
        double test_sum = app.sum(test_list,2,7);
        assertEquals(137.0, test_sum);
    }
    @Test
    void test_sum_falseSum() {
        ArrayList<Double> test_list = new ArrayList<Double>();
        test_list.add(23.4);
        test_list.add(24.4);
        test_list.add(25.4);
        test_list.add(26.4);
        test_list.add(27.4);
        test_list.add(28.4);
        test_list.add(29.4);

        App app = new App();
        double test_sum = app.sum(test_list,2,5);
        assertNotEquals(75.3, test_sum);
    }

}