package com.saga.unipass;

import com.saga.unipass.registrazione.controller.RegistrazioneController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrazioneTest {

    private static RegistrazioneController registrazioneController;

    @BeforeAll
    public static void init() {
        registrazioneController = new RegistrazioneController();
    }

    @Test
    public void formatoEmailNonCorretto() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = registrazioneController.controlloValori("Antonio.gmail.com",
                "password", "Antonio","Rosso", "3334720537");

        assertTrue(parametriNonValidi.contains("Data non valida."));
    }

    @Test
    public void formatoPasswordNonCorretto() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = registrazioneController.controlloValori("Antonioros4@gmail.com",
                "Intel", "Antonio","Rosso", "3133540977");

        assertTrue(parametriNonValidi.contains("Password non valida."));
    }

    @Test
    public void formatoNomeNonCorretto() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = registrazioneController.controlloValori("prisma@gmail.com",
                "hologram", "Llanfairpwllgwyngyllgogerychwyrndrobwllllantysiliogogogochverylongname",
                "Wales", "3333546724");

        assertTrue(parametriNonValidi.contains("Nome non valido."));
    }

    @Test
    public void formatoCognomeNonCorretto() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = registrazioneController.controlloValori("Mail456@gmail.com",
                "Password478", "Naomi","Vaðlaheiðarvegavinnuverkfærageymsluskúraútidyralyklakippuhringur",
                "3451109852");

        assertTrue(parametriNonValidi.contains("Cognome non valido."));
    }

    @Test
    public void formatoTelefonoNonCorretto() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = registrazioneController.controlloValori("pverdi@gmail.com",
                "verdi123", "Paolo","Verdi", "33345362974547");

        assertTrue(parametriNonValidi.contains("Telefono non valido."));
    }

    @Test
    public void registrazioneCorretta() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = registrazioneController.controlloValori("Ccaraccio98@gmail.com",
                "Carorl23", "Carlo","Caracciolo", "3385647381");

        assertTrue(parametriNonValidi.isEmpty());
    }

}

