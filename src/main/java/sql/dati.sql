INSERT INTO account (nome, password) VALUES
-- Admin
('CiroVivenzius', 'BlackOps3@'),
('AntonioDeCurtis', 'ForzaNapoli10!'),
('Christianus', 'Dpfzsvl23#'),

-- Sviluppatori
('Nintendo', 'MarioBros1!'),
('SEGA', 'SonicFast99@'),
('Capcom', 'ResidenEvil7!'),
('SquareEnix', 'KingdomNonHearts1!'),
('Konami', 'KojimaGod123#'),

-- Utente
('Marc0x', 'MarcoTheBest1!'),
('Tulliocin', 'PasswordSicura88%'),
('Giulia', 'SuperGiulia99&'),
('xxCiroxx', 'Qwerty12345?'),
('AndreaPignieri', 'Bobbi1926#'),
('Musella', 'bnlsB56!!!'),
('MarcoGrim', 'Wdux67????'),
('JillValentine', 'St4rs!Nemesis'),
('ChrisRedfield', 'B0ulderPunch!'),
('AlbertWesker', 'Ur0boros$99'),
('LeonKennedy', 'R3sidentEvil!'),
('Lucio', 'passworD111!'),
('BomberKvara', 'TiroAggi1ro!'),
('MatadorCavani', 'PallaInRete9!'),
('GymBro99', 'PancaPiana100$'),
('PeppeOStort', 'BellaFra123?'),
('AnnaLaPazza', 'CrazyAnna88*'),
('FranchinoErCriminale', 'Ciaomondo123!');

INSERT INTO admin(idadmin) VALUES (1),(2),(3);

INSERT INTO sviluppatore (idSviluppatore, descrizione) VALUES
(4, 'Giochi adatti a tutte le etá'),
(5, 'Andiamo velocissimi e facciamo i giochi di Sonic'),
(6, 'Maestri dei survival horror e picchiaduro'),
(7, 'Sforniamo JRPG chilometrici e piangiamo per le traduzioni'),
(8, 'Pachinko, calcio e ci piangiamo ancora Kojima');

INSERT INTO utente (idUtente, genere, dataNascita, email) VALUES
(9, 'Maschio', '2001-05-12', 'marcox@hotmail.com'),
(10, 'Maschio', '1999-10-23', 'tullio.cin@gmail.com'),
(11, 'Femmina', '2002-08-15', 'giulia.super@fastmail.com'),
(12, 'Maschio', '1998-01-30', 'ciro.xx@gmail.com'),
(13, 'Maschio', '2003-11-05', 'andrea.pignieri@libero.com'),
(14, 'Altro', '2000-04-18', 'musella.gamer@hotmail.com'),
(15,'Altro','1994-02-10','ilpianobardisusy@gmail.com'),
(16, 'Femmina', '1974-02-14', 'jill@stars.com'),
(17, 'Maschio', '1973-05-05', 'chris@bsaa.org'),
(18, 'Maschio', '1960-08-11', 'wesker@umbrella.com'),
(19, 'Maschio', '1977-09-09', 'leon.kennedy@rcpd.gov'),
(20, 'Maschio', '1990-01-01', 'luciopt@libero.it'),
(21, 'Maschio', '2001-02-12', 'bomber.kvara@napoli.it'),
(22, 'Maschio', '1998-05-20', 'ciro.gol@hotmail.it'),
(23, 'Maschio', '2002-11-11', 'gymbro99@gmail.com'),
(24, 'Maschio', '1999-12-25', 'peppe.stort@libero.it'),
(25, 'Femmina', '2003-04-14', 'anna.pazza@gmail.com'),
(26, 'Maschio', '1980-08-20', 'franchino@gmail.com');

INSERT INTO amici (idamico1, idamico2) VALUES
(14,15),
(13,14),
(9,13),
(10,15),
(9,12),
(16, 17),
(16, 19),
(17, 18),
(20, 23),
(20, 26),
(21, 22),
(24, 25),
(9, 21),
(10, 23),
(13, 20);
