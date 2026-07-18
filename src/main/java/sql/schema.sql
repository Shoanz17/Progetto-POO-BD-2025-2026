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
