package de.buw.se4de;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        assertEquals("", result);
    }
    @Test
    void test_MoneyParseTest_validInput() {
        String geld_betrag = ("42");
        Eingaben e = new Eingaben();
        double money = 0.0;

        Pair<Boolean, Double> result = e.money_parse_test(geld_betrag, money);
        assertTrue(result.getKey());
        assertEquals(42, result.getValue());
    }
    @Test
    void test_MoneyParseTest_invalidInput() {
        String geld_betrag = ("invalid");
        Eingaben e = new Eingaben();
        double money = 0.0;

        Pair<Boolean, Double> result = e.money_parse_test(geld_betrag, money);
        assertFalse(result.getKey());
    }
    @Test
    void testCalendarTest_validDate() {
        Calendar calendar = Calendar.getInstance();
        Eingaben e = new Eingaben();
        boolean result = e.calendar_test(calendar, 24, 1, 2023);
        assertTrue(result);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(1, calendar.get(Calendar.MONTH));
        assertEquals(24, calendar.get(Calendar.DATE));
    }
    @Test
    void testCalendarTest_validDate2() {
        Calendar calendar = Calendar.getInstance();
        Eingaben e = new Eingaben();
        boolean result = e.calendar_test(calendar, 12, 5, 2023);
        assertTrue(result);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(5, calendar.get(Calendar.MONTH));
        assertEquals(12, calendar.get(Calendar.DATE));
    }

    @Test
    void testCalendarTest_invalidDate() {
        Calendar calendar = Calendar.getInstance();
        Eingaben e = new Eingaben();
        boolean result = e.calendar_test(calendar, 42, 1, 2023);
        assertFalse(result);
    }
}