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
('MarcoGrim', 'Wdux67????');

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
(15,'Altro','1994-02-10','ilpianobardisusy@gmail.com');

INSERT INTO amici (idamico1, idamico2) VALUES
(14,15),
(13,14),
(9,13),
(10,15),
(9,12);
