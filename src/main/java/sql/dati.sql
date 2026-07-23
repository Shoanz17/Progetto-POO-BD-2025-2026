INSERT INTO account (idAccount, nome, password) VALUES
-- Admin
(1, 'CiroVivenzius', 'BlackOps3@'),
(2, 'AntonioDeCurtis', 'ForzaNapoli10!'),
(3, 'Cristianus', 'Dpfzsvl23#'),

-- Sviluppatori
(4, 'Nintendo', 'MarioBros1!'),
(5, 'SEGA', 'SonicFast99@'),
(6, 'Capcom', 'ResidenEvil7!'),
(7, 'SquareEnix', 'KingdomNonHearts1!'),
(8, 'Konami', 'KojimaGod123#'),
(9, 'ATLUS', 'persona5CashCow!'),
(10, 'SandFall Interactive', 'G0TY!!!!'),
(11, 'FromSoftware', 'MiyazakiP0ison!'),
(12, 'NaughtyDog', 'CrashT0Ellie!'),
(13, 'CDProjektRed', 'Witch3rGeralt!'),
(14, 'RockstarGames', 'Gta6When123?'),
(15, 'LarianStudios', 'BearRomance99!'),

-- Utente
(16, 'Marc0x', 'MarcoTheBest1!'),
(17, 'Tulliocin', 'PasswordSicura88%'),
(18, 'Giulia', 'SuperGiulia99&'),
(19, 'xxCiroxx', 'Qwerty12345?'),
(20, 'AndreaPignieri', 'Bobbi1926#'),
(21, 'Musella', 'bnlsB56!!!'),
(22, 'MarcoGrim', 'Wdux67????'),
(23, 'JillValentine', 'St4rs!Nemesis'),
(24, 'ChrisRedfield', 'B0ulderPunch!'),
(25, 'AlbertWesker', 'Ur0boros$99'),
(26, 'LeonKennedy', 'R3sidentEvil!'),
(27, 'Lucio', 'passworD111!'),
(28, 'BomberKvara', 'TiroAggi1ro!'),
(29, 'MatadorCavani', 'PallaInRete9!'),
(30, 'GymBro99', 'PancaPiana100$'),
(31, 'PeppeOStort', 'BellaFra123?'),
(32, 'AnnaLaPazza', 'CrazyAnna88*'),
(33, 'FranchinoErCriminale', 'Ciaomondo123!');

INSERT INTO admin (idadmin) VALUES (1),(2),(3);

INSERT INTO sviluppatore (idSviluppatore, descrizione) VALUES
(4, 'Giochi adatti a tutte le etá'),
(5, 'Andiamo velocissimi e facciamo i giochi di Sonic'),
(6, 'Maestri dei survival horror e picchiaduro'),
(7, 'Sforniamo JRPG chilometrici e piangiamo per le traduzioni'),
(8, 'Pachinko, calcio e ci piangiamo ancora Kojima'),
(9, 'Non faremo mai giochi con protagonisti adulti'),
(10, 'Abbiamo vinto il goty!!!'),
(11, 'Maestri dei giochi difficili e delle paludi velenose'),
(12, 'Narrativa cinematografica e avventure lineari di altissimo livello'),
(13, 'Creiamo mondi enormi e maturi basati su libri o giochi da tavolo'),
(14, 'Facciamo uscire un gioco ogni 10 anni ma rivoluziona il mercato'),
(15, 'Abbiamo riportato in vita i classici GDR con dadi e scelte multiple');

INSERT INTO utente (idUtente, genere, dataNascita, email, saldo) VALUES
(16, 'Maschio', '2001-05-12', 'marcox@hotmail.com', 300),
(17, 'Maschio', '1999-10-23', 'tullio.cin@gmail.com', 300),
(18, 'Femmina', '2002-08-15', 'giulia.super@fastmail.com', 300),
(19, 'Maschio', '1998-01-30', 'ciro.xx@gmail.com', 300),
(20, 'Maschio', '2003-11-05', 'andrea.pignieri@libero.com', 300),
(21, 'Altro', '2000-04-18', 'musella.gamer@hotmail.com', 300),
(22, 'Altro', '1994-02-10', 'ilpianobardisusy@gmail.com', 300),
(23, 'Femmina', '1974-02-14', 'jill@stars.com', 300),
(24, 'Maschio', '1973-05-05', 'chris@bsaa.org', 300),
(25, 'Maschio', '1960-08-11', 'wesker@umbrella.com', 300),
(26, 'Maschio', '1977-09-09', 'leon.kennedy@rcpd.gov', 300),
(27, 'Maschio', '1990-01-01', 'luciopt@libero.it', 300),
(28, 'Maschio', '2001-02-12', 'bomber.kvara@napoli.it', 300),
(29, 'Maschio', '1998-05-20', 'ciro.gol@hotmail.it', 300),
(30, 'Maschio', '2002-11-11', 'gymbro99@gmail.com', 300),
(31, 'Maschio', '1999-12-25', 'peppe.stort@libero.it', 300),
(32, 'Femmina', '2003-04-14', 'anna.pazza@gmail.com', 300),
(33, 'Maschio', '1980-08-20', 'franchino@gmail.com', 300);

INSERT INTO amici (idamico1, idamico2) VALUES
(21, 22),
(20, 21),
(16, 20),
(17, 22),
(16, 19),
(23, 24),
(23, 26),
(24, 25),
(27, 30),
(27, 33),
(28, 29),
(31, 32),
(16, 28),
(17, 30),
(20, 27);

INSERT INTO gioco (idGioco, titolo, categoria, pegi, idSviluppatore) VALUES
(1, 'Persona 5: The Phantom X', 'AAA', 16, 9),
(2, 'Phoenix Wright: Ace Attorney Trilogy', 'AA', 12, 6),
(3, 'Clair Obscur: Expedition 33', 'AA', 18, 10),
(4, 'Super Mario Odyssey', 'AAA', 3, 4),
(5, 'Metal Gear Solid V', 'AAA', 18, 8),
(6, 'Elden Ring', 'AAA', 16, 11),
(7, 'The Last of Us Part I', 'AAA', 18, 12),
(8, 'The Witcher 3: Wild Hunt', 'AAA', 18, 13),
(9, 'Red Dead Redemption 2', 'AAA', 18, 14),
(10, 'Baldur''s Gate 3', 'AAA', 18, 15),
(11, 'Cyberpunk 2077', 'AAA', 18, 13);

INSERT INTO genere (idGenere, nome) VALUES
(1, 'JRPG'),
(2, 'Visual Novel'),
(3, 'Action RPG'),
(4, 'Platform'),
(5, 'Stealth'),
(6, 'Gacha'),
(7, 'FPS'),
(8, 'TPS'),
(9, 'RPG'),
(10, 'MMORPG'),
(11, 'MOBA'),
(12, 'Picchiaduro'),
(13, 'Corse'),
(14, 'Sport'),
(15, 'Puzzle'),
(16, 'Sandbox'),
(17, 'Survival'),
(18, 'Battle Royale'),
(19, 'Strategia'),
(20, 'Roguelike'),
(21, 'Avventura Grafica'),
(22, 'Azione'),
(23, 'Turn-Based'),
(24, 'Soulslike'),
(25, 'Open World');

INSERT INTO gioco_genere (idGioco, idGenere) VALUES
(1, 1),
(1, 6),
(1, 22),
(1, 23),
(2, 2),
(2, 15),
(3, 3),
(3, 22),
(3, 23),
(4, 4),
(5, 5),
(6, 3),
(6, 24),
(6, 25),
(7, 8),
(7, 17),
(7, 22),
(8, 3),
(8, 9),
(8, 25),
(9, 8),
(9, 22),
(9, 25),
(10, 9),
(10, 23),
(11, 3),
(11, 7),
(11, 25);

INSERT INTO promozione (idPromozione, nome, dataInizio, dataFine) VALUES
(1, 'Saldi Invernali 2026', '2026-12-20', '2027-01-05'),
(2, 'Golden Week', '2026-04-29', '2026-05-06'),
(3, 'Black Friday 2026', '2026-11-23', '2026-11-30');

INSERT INTO piattaforma_di_gioco(nome, produttore, portatile) VALUES
('Switch','Nintendo',true),
('Switch 2','Nintendo',true),
('PlayStation 1','Sony',false),
('PlayStation 2','Sony',false),
('PlayStation 3','Sony',false),
('PlayStation 4','Sony',false),
('PlayStation 5','Sony',false),
('PS Vita','Sony',True),
('Xbox','Microsoft',false),
('Xbox 360','Microsoft',false),
('Xbox One','Microsoft',false),
('Xbox Series X|S','Microsoft',false),
('DS','Nintendo',true),
('3DS','Nintendo',true),
('PC','Steam',false);

INSERT INTO edizione_gioco (idEdizione, idGioco, nomePiattaforma, prezzo, dataRilascio) VALUES
(1, 1, 'PC', 0, '2025-06-26'),
(2, 2, 'PC', 30, '2019-04-09'),
(3, 2, 'Switch', 30, '2019-04-09'),
(4, 3, 'PC', 50, '2025-10-15'),
(5, 4, 'Switch', 60, '2017-10-27'),
(6, 5, 'PC', 20, '2015-09-01'),
(7, 5, 'PlayStation 5', 25, '2015-09-01'),
(8, 3, 'PlayStation 5', 50, '2025-10-15'),
(9, 3, 'Xbox Series X|S', 50, '2025-10-15'),
(10, 6, 'PC', 60, '2022-02-25'),
(11, 6, 'PlayStation 5', 60, '2022-02-25'),
(12, 6, 'Xbox Series X|S', 60, '2022-02-25'),
(13, 7, 'PC', 70, '2022-09-02'),
(14, 7, 'PlayStation 5', 70, '2022-09-02'),
(15, 8, 'PC', 30, '2015-05-19'),
(16, 8, 'PlayStation 5', 30, '2015-05-19'),
(17, 8, 'Xbox Series X|S', 30, '2015-05-19'),
(18, 8, 'Switch', 30, '2015-05-19'),
(19, 9, 'PC', 60, '2018-10-26'),
(20, 9, 'PlayStation 5', 60, '2018-10-26'),
(21, 9, 'Xbox Series X|S', 60, '2018-10-26'),
(22, 10, 'PC', 60, '2023-08-03'),
(23, 10, 'PlayStation 5', 60, '2023-08-03'),
(24, 10, 'Xbox Series X|S', 60, '2023-08-03'),
(25, 11, 'PC', 60, '2020-12-10'),
(26, 11, 'PlayStation 5', 60, '2020-12-10'),
(27, 11, 'Xbox Series X|S', 60, '2020-12-10');

INSERT INTO gioco_in_promozione (idGioco, idPromozione, percentuale) VALUES
(2, 1, 50),
(4, 1, 10),
(5, 3, 75);

INSERT INTO seguiti(idutente, idsviluppatore) VALUES
(16, 6),
(17, 6),
(19, 6),
(16, 4),
(18, 4),
(17, 4),
(20, 7),
(21, 7),
(22, 7),
(21, 5),
(24, 5),
(26, 8),
(20, 8),
(18, 8),
(16, 7);

INSERT INTO fattura (idFattura, idUtente, idEdizione, prezzoAcquisto, key, dataAcquisto) VALUES
(1, 16, 10, 60, '123e4567-e89b-12d3-a456-426614174001', '2022-03-01'),
(2, 17, 5, 60, '123e4567-e89b-12d3-a456-426614174002', '2018-12-25'),
(3, 23, 13, 70, '123e4567-e89b-12d3-a456-426614174003', '2022-10-10'),
(4, 24, 6, 20, '123e4567-e89b-12d3-a456-426614174004', '2016-01-15'),
(5, 26, 22, 60, '123e4567-e89b-12d3-a456-426614174005', '2023-09-01'),
(6, 28, 2, 30, '123e4567-e89b-12d3-a456-426614174006', '2020-05-20'),
(7, 33, 19, 60, '123e4567-e89b-12d3-a456-426614174007', '2019-11-05'),
(8, 18, 1, 0, '123e4567-e89b-12d3-a456-426614174008', '2025-07-01'),
(9, 20, 8, 50, '123e4567-e89b-12d3-a456-426614174009', '2025-11-01'),
(10, 30, 25, 60, '123e4567-e89b-12d3-a456-426614174010', '2021-01-10'),
(11, 31, 15, 30, '123e4567-e89b-12d3-a456-426614174011', '2016-06-01'),
(12, 19, 11, 60, '123e4567-e89b-12d3-a456-426614174012', '2022-04-12');

INSERT INTO recensione (idFattura, voto, descrizione, differenzaLike) VALUES
(1, 95, 'Capolavoro assoluto, mi ha fatto esplorare per ore senza mai annoiarmi.', 45),
(2, 100, 'Super Mario non delude mai, puro divertimento per tutte le eta.', 12),
(3, 85, 'Ottimo porting per PC, una storia fantastica e straziante.', 8),
(4, 90, 'Gameplay stealth semplicemente perfetto.', 30),
(5, 98, 'GOTY senza alcun dubbio. Un gioco di ruolo immenso e profondo.', 150),
(6, 80, 'Objection! Trama avvincente e casi intricati da risolvere.', 5),
(7, 99, 'Il miglior gioco open world di sempre, un mondo vivo e pulsante.', 88),
(8, 70, 'Buon titolo ma richiede un bel po di tempo per i potenziamenti.', 2),
(9, 85, 'Grafica pazzesca e sistema di combattimento a turni molto interessante.', 14),
(10, 75, 'Bello ma all uscita aveva troppi bug, ora e molto migliorato.', 40),
(11, 95, 'Geralt di Rivia non sbaglia un colpo, e le espansioni sono dei capolavori.', 60),
(12, 92, 'Difficile al punto giusto, ambientazione e direzione artistica incredibili.', 22);

-- Sincronizzazione dei contatori SERIAL dopo l'inserimento manuale degli ID
SELECT setval(pg_get_serial_sequence('account', 'idaccount'), (SELECT MAX(idaccount) FROM account));
SELECT setval(pg_get_serial_sequence('gioco', 'idgioco'), (SELECT MAX(idgioco) FROM gioco));
SELECT setval(pg_get_serial_sequence('genere', 'idgenere'), (SELECT MAX(idgenere) FROM genere));
SELECT setval(pg_get_serial_sequence('promozione', 'idpromozione'), (SELECT MAX(idpromozione) FROM promozione));
SELECT setval(pg_get_serial_sequence('edizione_gioco', 'idedizione'), (SELECT MAX(idedizione) FROM edizione_gioco));
SELECT setval(pg_get_serial_sequence('fattura', 'idfattura'), (SELECT MAX(idfattura) FROM fattura));