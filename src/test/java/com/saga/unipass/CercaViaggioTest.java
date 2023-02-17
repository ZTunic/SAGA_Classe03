package com.saga.unipass;

import com.saga.unipass.viaggio.controller.ViaggioController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CercaViaggioTest {

    private static ViaggioController viaggioController;

    @BeforeAll
    public static void init() {
        viaggioController = new ViaggioController();
    }

    @Test
    public void valoreDataNonValido() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = viaggioController.controlloValoriRicercaViaggio("Scafati",
                "2022/02/14 15:00","8");

        assertTrue(parametriNonValidi.contains("Data non valida."));
    }

    @Test
    public void valorePrezzoNonValido() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = viaggioController.controlloValoriRicercaViaggio("Pagani",
                "2023/12/21 15:30","60");

        assertTrue(parametriNonValidi.contains("Prezzo non valido."));
    }

    @Test
    public void creazioneViaggioCorretta() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = viaggioController.controlloValoriRicercaViaggio("Pompei",
                "2023/02/18 16:30","10");

        assertTrue(parametriNonValidi.isEmpty());
    }
}
