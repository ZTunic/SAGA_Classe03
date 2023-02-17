package com.saga.unipass;

import com.saga.unipass.controller.ViaggioController;
import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Veicolo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreaViaggioTest {

    private static ViaggioController viaggioController;
    private static Utente utente;
    private static Veicolo veicolo;

    @BeforeAll
    public static void init() {
        viaggioController = new ViaggioController();
        utente = new Utente();
        veicolo = new Veicolo("FZ353TR", "FORD", "FIESTA", "ROSSO", 5, utente);
        utente.setVeicolo(veicolo);
    }

    @Test
    public void valoreDataNonValido() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = viaggioController.controlloValoriCreazioneViaggio("Scafati",
                "2019/02/14 18:00", "3","7.55", veicolo);

        assertTrue(parametriNonValidi.contains("Data non valida."));
    }

    @Test
    public void valorePostiNonValido() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = viaggioController.controlloValoriCreazioneViaggio("Pompei",
                "2023/02/18 17:20", "8","24.45", veicolo);

        assertTrue(parametriNonValidi.contains("Numero posti non valido."));
    }

    @Test
    public void valorePrezzoNonValido() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = viaggioController.controlloValoriCreazioneViaggio("Scafati",
                "2023/03/18 14:55", "2","55.70", veicolo);

        assertTrue(parametriNonValidi.contains("Prezzo non valido."));
    }

    @Test
    public void creazioneViaggioCorretta() {
        ArrayList<String> parametriNonValidi;

        parametriNonValidi = viaggioController.controlloValoriCreazioneViaggio("Pagani",
                "2023/02/24 16:20", "3","30.45", veicolo);

        assertTrue(parametriNonValidi.isEmpty());
    }


}
