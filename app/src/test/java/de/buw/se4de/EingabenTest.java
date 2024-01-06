package de.buw.se4de;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

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

    void test_NoteTest_keineNotiz() {
        Eingaben e = new Eingaben();
        String result = e.note_test("Kann leer gelassen werden", "Kann leer gelassen werden");
        assertEquals("Transaktion", result);
    }
    void test_CategoryTest_Categoriegewählt() {
        Eingaben e = new Eingaben();
        Label ErrorText = new Label();

        boolean result = e.category_test(ErrorText, true);
        assertTrue(result);
        assertEquals("Bitte w\u00e4hlen Sie eine Kategorie aus", ErrorText.getText());
    }
    void test_CategoryTest_keineCategoriegewählt() {
        Eingaben e = new Eingaben();
        Label ErrorText = new Label();

        boolean result = e.category_test(ErrorText, true);
        assertTrue(result);
    }

    void test_MoneyParseTest_validInput() {
        TextField betrag = new TextField("42");
        Label ErrorText = new Label();
        Eingaben e = new Eingaben();
        double money = 0.0;

        Pair<Boolean, Double> result = e.money_parse_test(betrag, ErrorText, money);
        assertTrue(result.getKey());
        assertEquals(42, result.getValue());
    }

    void test_MoneyParseTest_invalidInput() {
        TextField betrag = new TextField("42");
        Label errorText = new Label();
        Eingaben e = new Eingaben();
        double money = 0.0;

        Pair<Boolean, Double> result = e.money_parse_test(betrag, errorText, money);
        assertTrue(result.getKey());
        assertEquals(42.5, result.getValue());

        betrag.setText("invalid");
        result = e.money_parse_test(betrag, errorText, money);
        assertFalse(result.getKey());
        assertEquals("Bitte korrekten Betrag eingeben", errorText.getText());
    }

    void test_MoneyParseTest_invalidInput2() {
        TextField betrag = new TextField("42");
        Label errorText = new Label();
        Eingaben e = new Eingaben();
        double money = 0.0;

        Pair<Boolean, Double> result = e.money_parse_test(betrag, errorText, money);
        assertTrue(result.getKey());
        assertEquals(42.5, result.getValue());

        betrag.setText("invalid");
        result = e.money_parse_test(betrag, errorText, money);
        assertFalse(result.getKey());
        assertEquals("Bitte korrekten Betrag eingeben", errorText.getText());
    }

}