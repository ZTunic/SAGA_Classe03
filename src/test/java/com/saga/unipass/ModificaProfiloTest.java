package com.saga.unipass;

import com.saga.unipass.controller.AutenticazioneController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@AutoConfigureMockMvc
public class ModificaProfiloTest {

    private static AutenticazioneController autenticazioneController;

    @BeforeAll
    public static void init() {
        autenticazioneController = new AutenticazioneController();
    }

    @Test
    public void formatoEmailNonCorretto() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = autenticazioneController.controlloValori("pverdigmail.com",
                "verdi123", "Paolo","Verdi", "3334445050");

        assertTrue(parametriNonValidi.contains("Email non valida."));
    }

    @Test
    public void formatoPasswordNonCorretto() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = autenticazioneController.controlloValori("pverdi@gmail.com",
                "pverdi", "Paolo","Verdi", "3334445050");

        assertTrue(parametriNonValidi.contains("Password non valida."));
    }

    @Test
    public void formatoNomeNonCorretto() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = autenticazioneController.controlloValori("pverdi@gmail.com",
                "verdi123", "Bartolomeo Paolo Raffaele Antonio Massimo del Vecchio Verdi De Felice",
                "Verdi", "3334445050");

        assertTrue(parametriNonValidi.contains("Nome non valido."));
    }

    @Test
    public void formatoCognomeNonCorretto() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = autenticazioneController.controlloValori("pverdi@gmail.com",
                "verdi123", "Paolo","Bartolomeo Paolo Raffaele Antonio Massimo del Vecchio Verdi De Felice",
                "3334445050");

        assertTrue(parametriNonValidi.contains("Cognome non valido."));
    }

    @Test
    public void formatoTelefonoNonCorretto() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = autenticazioneController.controlloValori("pverdi@gmail.com",
                "verdi123", "Paolo","Verdi", "12345");

        assertTrue(parametriNonValidi.contains("Telefono non valido."));
    }

    @Test
    public void registrazioneCorretta() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = autenticazioneController.controlloValori("pverdi@gmail.com",
                "verdi123", "Paolo","Verdi", "3334445050");

        assertTrue(parametriNonValidi.isEmpty());
    }


}
