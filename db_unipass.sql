drop database unipass;
create database unipass;
use unipass;

create table utente(
	email varchar(50) primary key,
    nome varchar(64) not null,
    cognome varchar(64) not null,
    passwordHash varchar(50) not null,
    telefono varchar(15) not null,
    tipo varchar(10) not null,
    numeroValutazioniPasseggero int,
    numeroValutazioniGuidatore int,
    sommaValutazioniPasseggero int,
    sommaValutazioniGuidatore int
);
    
create table veicolo(
	targa varchar(20) primary key,
    marca varchar(40) not null,
    modello varchar(40) not null,
    colore varchar(40) not null,
    postiDisponibili int not null,
    proprietario varchar(50),
    foreign key(proprietario) references utente(email)
);

create table viaggio(
	idViaggio int primary key auto_increment,
    destinazione varchar(50) not null,
    dataOraPartenza datetime not null,
    posti int not null,
    prezzo double(4,2) not null,
    prenotabile boolean not null,
    guidatore varchar(50),
    foreign key(guidatore) references utente(email)
);


create table partecipare(
	passeggero varchar(50),
    viaggio int, 
    valutazionePtoG boolean,
    valutazioneGtoP boolean,
    foreign key(passeggero) references utente(email) on update cascade, /* VINCOLO INTERRELAZIONALE: l'email del passeggero viene modificata qualora 
																									 l'email dell'utente cambia. */
    foreign key(viaggio) references viaggio(idViaggio) on delete cascade,/* VINCOLO INTERRELAZIONALE: tutte le tuple che fanno riferimento ad un viaggio che viene eliminato
																									  vengono automaticamente rimosse dalla tabella. */
    primary key(passeggero, viaggio)
);


