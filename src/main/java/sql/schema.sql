CREATE TABLE account(
    idAccount SERIAL PRIMARY KEY,
    nome VARCHAR(24) NOT NULL,
    password VARCHAR(32) NOT NULL,
    dataCreazione DATE DEFAULT CURRENT_DATE,

    CONSTRAINT chk_account_password CHECK(LENGTH(password)>=8)
);

CREATE TABLE utente(
    idUtente INT PRIMARY KEY,
    genere VARCHAR(7) NOT NULL,
    saldo INT DEFAULT 0,
    bannato BOOLEAN DEFAULT FALSE,
    dataNascita DATE NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,

    CONSTRAINT fk_utente_account FOREIGN KEY (idUtente) REFERENCES account(idAccount),
    CONSTRAINT chk_utente_dataNascita CHECK(dataNascita <= CURRENT_DATE),
    CONSTRAINT chk_utente_genere CHECK(genere IN('Maschio','Femmina','Altro'))
);

CREATE TABLE admin(
    idAdmin INT PRIMARY KEY,

    CONSTRAINT fk_admin_account FOREIGN KEY (idAdmin) REFERENCES account(idAccount)
);

CREATE TABLE sviluppatore(
    idSviluppatore INT PRIMARY KEY,
    strike INT DEFAULT 0,
    descrizione VARCHAR(500) NOT NULL,
    fondi INT DEFAULT 0,

    CONSTRAINT fk_sviluppatore_account FOREIGN KEY (idSviluppatore) REFERENCES account(idAccount),
    CONSTRAINT chk_sviluppatore_strike CHECK(strike >= 0 AND strike <=3),
    CONSTRAINT chk_sviluppatore_fondi CHECK(fondi >= 0)
);

CREATE TABLE amici(
    idAmico1 INT,
    idAmico2 INT,

    PRIMARY KEY (idAmico1,idAmico2),
    CONSTRAINT fk_amici_utente1 FOREIGN KEY (idAmico1) REFERENCES utente(idUtente),
    CONSTRAINT fk_amici_utente2 FOREIGN KEY (idAmico2) REFERENCES utente(idUtente),
    CONSTRAINT chk_amici_amici CHECK (idAmico1 < idAmico2)
);

CREATE TABLE gioco(
    idGioco SERIAL PRIMARY KEY,
    titolo VARCHAR(40) NOT NULL,
    categoria VARCHAR(5) NOT NULL,
    pegi INT NOT NULL,
    idSviluppatore INT NOT NULL,

    CONSTRAINT fk_gioco_sviluppatore FOREIGN KEY (idSviluppatore) REFERENCES sviluppatore(idSviluppatore),
    CONSTRAINT chk_gioco_pegi CHECK ( pegi >= 3 AND pegi <= 18 ),
    CONSTRAINT chk_gioco_categoria CHECK ( categoria IN ('INDIE', 'A', 'AA', 'AAA', 'AAAA'))
);

CREATE TABLE genere(
    idGenere SERIAL PRIMARY KEY,
    nome VARCHAR(24) UNIQUE NOT NULL
);

CREATE TABLE gioco_genere(
    idGioco  INT,
    idGenere INT,

    PRIMARY KEY (idGioco, idGenere),
    CONSTRAINT fk_gioco_genere_gioco FOREIGN KEY (idGioco) REFERENCES gioco (idGioco),
    CONSTRAINT fk_gioco_genere_genere FOREIGN KEY (idGenere) REFERENCES genere (idGenere)
);

CREATE TABLE promozione(
    idPromozione SERIAL PRIMARY KEY,
    nome VARCHAR(32) UNIQUE NOT NULL,
    dataInizio DATE NOT NULL,
    dataFine DATE NOT NULL,

    CONSTRAINT chk_promozione_data CHECK ( dataFine >= dataInizio)
);

CREATE TABLE gioco_in_promozione(
    idGioco INT,
    idPromozione INT,
    percentuale INT NOT NULL,

    PRIMARY KEY (idGioco, idPromozione),
    CONSTRAINT fk_gioco_in_promozione_gioco FOREIGN KEY (idGioco) REFERENCES gioco (idGioco),
    CONSTRAINT fk_gioco_in_promozione_promozione FOREIGN KEY (idPromozione) REFERENCES promozione (idPromozione),
    CONSTRAINT chk_gioco_in_promozione_sconto CHECK ( percentuale >= 0 AND percentuale <= 100 )
);

CREATE TABLE piattaforma_di_gioco(
    nome VARCHAR(20) PRIMARY KEY,
    produttore VARCHAR(20) NOT NULL,
    portatile BOOLEAN NOT NULL
);

CREATE TABLE edizione_gioco(
    idEdizione SERIAL PRIMARY KEY,
    idGioco INT NOT NULL,
    nomePiattaforma VARCHAR(20) NOT NULL,
    prezzo INT NOT NULL,
    dataRilascio DATE NOT NULL,

    CONSTRAINT fk_edizione_gioco_gioco FOREIGN KEY (idGioco) REFERENCES gioco (idGioco),
    CONSTRAINT fk_edizione_gioco_piattaforma FOREIGN KEY (nomePiattaforma) REFERENCES piattaforma_di_gioco (nome),
    CONSTRAINT unq_edizione_gioco_gioco_piattaforma UNIQUE (idGioco, nomePiattaforma),
    CONSTRAINT chk_edizione_gioco_prezzo CHECK ( prezzo >= 0 ),
    CONSTRAINT chk_edizione_gioco_data CHECK ( dataRilascio >= '1952-01-01' )
);


CREATE TABLE carrello(
    idUtente INT,
    idEdizione INT,

    PRIMARY KEY (idUtente,idEdizione),
    CONSTRAINT fk_carrello_utente FOREIGN KEY (idUtente) REFERENCES utente (idUtente),
    CONSTRAINT fk_carrello_edizione_gioco FOREIGN KEY (idEdizione) REFERENCES edizione_gioco(idEdizione)
);

CREATE TABLE fattura(
    idFattura SERIAL PRIMARY KEY,
    idUtente INT NOT NULL,
    idEdizione INT NOT NULL,
    prezzoAcquisto INT NOT NULL,
    key VARCHAR(36) UNIQUE NOT NULL,
    dataAcquisto DATE DEFAULT CURRENT_DATE,

    CONSTRAINT fk_fattura_utente FOREIGN KEY (idUtente) REFERENCES utente (idUtente),
    CONSTRAINT fk_fattura_edizione_gioco FOREIGN KEY (idEdizione) REFERENCES edizione_gioco (idEdizione),
    CONSTRAINT chk_fattura_prezzoAcquisto CHECK ( prezzoAcquisto >= 0 ),
    CONSTRAINT chk_fattura_dataAcquisto CHECK ( dataAcquisto >= '1952-01-01' AND dataAcquisto <= CURRENT_DATE)
);

CREATE TABLE recensione(
    idFattura INT PRIMARY KEY,
    voto INT NOT NULL,
    descrizione VARCHAR(500),
    differenzaLike INT DEFAULT 0,

    CONSTRAINT fk_recensione_fattura FOREIGN KEY (idFattura) REFERENCES fattura (idFattura),
    CONSTRAINT chk_recensione_voto CHECK ( voto >= 0 AND voto <= 100 )
);

CREATE TABLE seguiti(
    idUtente INT,
    idSviluppatore INT,

    PRIMARY KEY (idUtente,idSviluppatore),
    CONSTRAINT fk_seguiti_utente FOREIGN KEY (idUtente) REFERENCES utente (idUtente),
    CONSTRAINT fk_seguiti_sviluppatore FOREIGN KEY (idSviluppatore) REFERENCES sviluppatore(idSviluppatore)
);




-- TRIGGERS
-- Funzione per evitare promozioni su giochi gratis
CREATE OR REPLACE FUNCTION check_gioco_gratuito()
    RETURNS TRIGGER AS $$
BEGIN
    -- Controlliamo nella tabella EDIZIONE_GIOCO se esiste almeno un'edizione a pagamento per questo gioco
    IF NOT EXISTS (
        SELECT 1
        FROM EDIZIONE_GIOCO
        WHERE idGioco = NEW.idGioco AND prezzo > 0
    ) THEN
        -- Se non troviamo nessuna edizione con prezzo > 0, blocchiamo l'operazione
        RAISE EXCEPTION 'Il gioco con ID % è gratis in tutte le sue edizioni e non può avere sconti.', NEW.idGioco;
    END IF;

    -- se non è gratis inserisci
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_gioco_gratuito
    BEFORE INSERT OR UPDATE ON GIOCO_IN_PROMOZIONE
    FOR EACH ROW
EXECUTE FUNCTION check_gioco_gratuito();

-- Funzione per evitare acquisti se il saldo è insufficiente
CREATE OR REPLACE FUNCTION verifica_saldo_utente()
    RETURNS TRIGGER AS $$
DECLARE
    saldo_attuale INT;
BEGIN
    -- Recupero il saldo dell'utente
    SELECT saldo INTO saldo_attuale
    FROM UTENTE
    WHERE idUtente = NEW.idUtente;

    IF saldo_attuale < NEW.prezzoAcquisto THEN
        RAISE EXCEPTION 'Saldo insufficiente. Il saldo attuale è %, ma il costo è %.', saldo_attuale, NEW.prezzoAcquisto;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_verifica_saldo_utente
    BEFORE INSERT ON FATTURA
    FOR EACH ROW
EXECUTE FUNCTION verifica_saldo_utente();

-- Funzione per aggiornare fondi in seguito a un acquisto
CREATE OR REPLACE FUNCTION trasferimento_fondi_acquisto()
    RETURNS TRIGGER AS $$
DECLARE
    sviluppatore_target INT;
BEGIN
    -- Aggiorno fondi utente
    UPDATE UTENTE
    SET saldo = saldo - NEW.prezzoAcquisto
    WHERE idUtente = NEW.idUtente;

    -- Trovo id Sviluppatore
    SELECT G.idSviluppatore INTO sviluppatore_target
    FROM EDIZIONE_GIOCO EG
             JOIN GIOCO G ON EG.idGioco = G.idGioco
    WHERE EG.idEdizione = NEW.idEdizione;

    -- Aggiungo i fondi allo Sviluppatore
    UPDATE SVILUPPATORE
    SET fondi = fondi + NEW.prezzoAcquisto
    WHERE idSviluppatore = sviluppatore_target;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_trasferimento_fondi
    AFTER INSERT ON FATTURA
    FOR EACH ROW
EXECUTE FUNCTION trasferimento_fondi_acquisto();