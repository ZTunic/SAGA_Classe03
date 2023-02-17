//chiudi modal
function chiudiModal(id){
    let modalClose = document.getElementById(id);
    modalClose.style.display = "none";
}

// controlli generali sui campi dell'utente
function controllaDatiUtente(nome, cognome, telefono, email, password, id){
    let modalID = document.getElementById(id);

    let rexNomeCognome = /^[A-zÀ-ù ‘-]{1,35}$/;
    let rexPassword = /^[A-z0-9._//%+-]{8,32}$/;
    let rexTelefono = /^[0-9]{10}$/;
    let rexEmail = /^[A-z0-9._%+-]+@[A-z0-9.-]+\.[A-z]{2,10}$/;

    if(!rexEmail.test(email.value)){
        email.placeholder = "Formato errato";
        email.value = "";
        email.focus();
        modalID.classList.add("show");
        modalID.style.display = "block";
        modalID.setAttribute('aria-modal', 'true');
        modalID.setAttribute('role', 'dialog');
        return false;
    }

    if(!(rexPassword.test(password.value))){
        password.placeholder = "Formato errato";
        password.value = "";
        password.focus();
        modalID.classList.add("show");
        modalID.style.display = "block";
        modalID.setAttribute('aria-modal', 'true');
        modalID.setAttribute('role', 'dialog');
        return false;
    }

    if(!(rexNomeCognome.test(cognome.value))){
        cognome.placeholder = "Formato errato";
        cognome.value = "";
        cognome.focus();
        modalID.classList.add("show");
        modalID.style.display = "block";
        modalID.setAttribute('aria-modal', 'true');
        modalID.setAttribute('role', 'dialog');
        return false;
    }

    if(!(rexNomeCognome.test(nome.value))){
        nome.placeholder = "Formato errato";
        nome.value = "";
        nome.focus();
        modalID.classList.add("show");
        modalID.style.display = "block";
        modalID.setAttribute('aria-modal', 'true');
        modalID.setAttribute('role', 'dialog');
        return false;
    }

    if(!(rexTelefono.test(telefono.value))){
        telefono.placeholder = "Formato errato";
        telefono.value = "";
        telefono.focus();
        modalID.classList.add("show");
        modalID.style.display = "block";
        modalID.setAttribute('aria-modal', 'true');
        modalID.setAttribute('role', 'dialog');
        return false;
    }

    console.log("Tutto bene");
    return true;
}

// Valida funzione registrazione utente
function validaRegistrazione(id){
    let nome = document.getElementById("nome");
    let cognome = document.getElementById("cognome");
    let telefono = document.getElementById("basic-url");
    let email = document.getElementById("email");
    let password = document.getElementById("password");

    return controllaDatiUtente(nome, cognome, telefono, email, password, id);
}

//Valida funzione aggiornamento utente
function validaAggiornamento(id){
    let nome = document.getElementById("nomeID");
    let cognome = document.getElementById("cognomeID");
    let telefono = document.getElementById("telefonoID");
    let email = document.getElementById("emailID");
    let password = document.getElementById("PasswordID");

    return controllaDatiUtente(nome, cognome, telefono, email, password, id);
}

//Controlli generali dati veicolo
function controllaDatiVeicolo(targa, marca, modello, colore, posti){

    let rexColoreModelloMarca = /^[A-zÀ-ù0-9 ‘-]{1,100}$/;
    let rexTarga = /^[A-Za-z]{2}[0-9]{3}[A-Za-z]{2}$/;
    let rexPosti = /^[2-9]$/;

    if(!(rexTarga.test(targa.value))){
        targa.placeholder = "Formato errato";
        targa.value = "";
        targa.focus();
        console.log("targa");
        return false;
    }

    if(!(rexColoreModelloMarca.test(marca.value))){
        marca.placeholder = "Troppi o nessun carattere";
        marca.value = "";
        marca.focus();
        console.log("marca");
        return false;
    }

    if(!(rexColoreModelloMarca.test(modello.value))){
        modello.placeholder = "Troppi o nessun carattere";
        modello.value = "";
        modello.focus();
        console.log("modello");
        return false;
    }

    if(!(rexColoreModelloMarca.test(colore.value))){
        colore.placeholder = "Troppi o nessun carattere";
        colore.value = "";
        colore.focus();
        console.log("colore");
        return false;
    }

    if(!(rexPosti.test(posti.value))){
        posti.placeholder = "Min: 2 - Max: 9";
        posti.value = "";
        posti.focus();
        console.log("posti");
        return false;
    }

    console.log("Tutto bene");
    return true;
}

//Valida funzioni aggiunta e aggiornamento veicolo
function validaDatiVeicolo(){
    let targa = document.getElementById("targaID");
    let marca = document.getElementById("marcaID");
    let modello = document.getElementById("modelloID");
    let colore = document.getElementById("coloreID");
    let posti = document.getElementById("postiID");


    return controllaDatiVeicolo(targa, marca, modello, colore, posti);
}


//Controllo generale creazione e ricerca
function controlloCreazioneRicerca(destinazione, dataOra, posti, prezzo, minutiDaSottrarre, modalID){
    let modal = document.getElementById(modalID);

    let rexPrezzo = /^(?:[0-9]|[1-4][0-9]|50)(?:\.\d+)?$/;
    let rexPosti = /^-?\d+$/;
    let rexDestinazione = /^[A-zÀ-ù '-]{1,100}$/;

    if(!(rexDestinazione.test(destinazione.value))){
        destinazione.placeholder = "Formato errato";
        destinazione.focus();
        modal.classList.add("show");
        modal.style.display = "block";
        modal.setAttribute('aria-modal', 'true');
        modal.setAttribute('role', 'dialog');
        return false;
    }

    let y = parseInt(dataOra.value.substring(0,4));
    let m = parseInt(dataOra.value.substring(5,7));
    let d = parseInt(dataOra.value.substring(8,10));
    let h = parseInt(dataOra.value.substring(11,13));
    let mm = parseInt(dataOra.value.substring(14,16)) - minutiDaSottrarre;

    //Controllo valore minuti
    if(mm < 0){
        h--;
        mm = 60 + mm;
    }

    //Controllo valore ora
    if(h < 0){
        d--;
        h = 23;
    }

    //Controllo valore mese
    if(d < 0){
        if (m === 4 || m === 6 || m === 9 || m === 11) {
            d = 30;
            m--;
        }
        else if(m === 3){
            if(isLeapYear(y))
                d = 29;
            else
                d = 28;
        }
        else {
            d = 31;
            m--
        }
    }

    //Controllo valore anno
    if(m < 0){
        y--;
        m = 12;
    }

    let dataSave = y+"-"+m+"-"+d;

    let dataDaVerificare = new Date(dataSave);
    let dataAttuale = new Date();

    dataDaVerificare.setHours(h, mm);

    if(dataAttuale > dataDaVerificare){
        let time = dataAttuale.toLocaleDateString();

        let month = time.substring(3,4);
        let day = time.substring(0,2);

        if(parseInt(month)<10)
            month = "0"+month;
        if(parseInt(day) < 10)
            day = "0"+day;

        dataOra.value = time.substring(5,9) + "-" + month + "-" + day + "T" + dataAttuale.toLocaleTimeString().substring(0,5);

        dataOra.focus();
        modal.classList.add("show");
        modal.style.display = "block";
        modal.setAttribute('aria-modal', 'true');
        modal.setAttribute('role', 'dialog');
        return false;
    }

    if(!(rexPrezzo.test(prezzo.value))){
        prezzo.placeholder = "Formato errato";
        prezzo.value = "";
        prezzo.focus();
        modal.classList.add("show");
        modal.style.display = "block";
        modal.setAttribute('aria-modal', 'true');
        modal.setAttribute('role', 'dialog');
        return false;
    }

    if(!(rexPosti.test(posti.value))){
        posti.placeholder = "Formato errato";
        posti.value = "";
        posti.focus();
        modal.classList.add("show");
        modal.style.display = "block";
        modal.setAttribute('aria-modal', 'true');
        modal.setAttribute('role', 'dialog');
        return false;
    }
    return true;
}

//Verifica anno bisestile
function isLeapYear(year) {
    return (year % 4 === 0 && year % 100 !== 0) || year % 400 === 0;
}

//Valida funzione ricerca viaggio
function validaRicercaViaggio(id){
    let destinazione = document.getElementById("destinazione");
    let dataOra = document.getElementById("dataOra");
    let prezzo = document.getElementById("prezzo");

    if(prezzo.value.length === 0)
        prezzo.value = 50;

    return controlloCreazioneRicerca(destinazione, dataOra, 1, prezzo, 40, id);
}

//Valida funzione creazione viaggio
function validaCreazioneViaggio(id){
    let destinazione = document.getElementById("destinazione")
    let dataOra = document.getElementById("dataOra");
    let posti = document.getElementById("posti")
    let prezzo = document.getElementById("prezzo");

    return controlloCreazioneRicerca(destinazione, dataOra, posti, prezzo, 60, id);
}