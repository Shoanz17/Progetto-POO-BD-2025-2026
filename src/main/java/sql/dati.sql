INSERT INTO account (nome, password) VALUES
-- Admin
('CiroVivenzius', 'BlackOps3@'),
('AntonioDeCurtis', 'ForzaNapoli10!'),
('Cristianus', 'Dpfzsvl23#'),

-- Sviluppatori
('Nintendo', 'MarioBros1!'),
('SEGA', 'SonicFast99@'),
('Capcom', 'ResidenEvil7!'),
('SquareEnix', 'KingdomNonHearts1!'),
('Konami', 'KojimaGod123#'),
('ATLUS', 'persona5CashCow!'),
('SandFall Interactive', 'G0TY!!!!'),
('FromSoftware', 'MiyazakiP0ison!'),
('NaughtyDog', 'CrashT0Ellie!'),
('CDProjektRed', 'Witch3rGeralt!'),
('RockstarGames', 'Gta6When123?'),
('LarianStudios', 'BearRomance99!'),

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

INSERT INTO gioco (titolo, categoria, pegi, idSviluppatore) VALUES
('Persona 5: The Phantom X', 'AAA', 16, 9),
('Phoenix Wright: Ace Attorney Trilogy', 'AA', 12, 6),
('Clair Obscur: Expedition 33', 'AA', 18, 10),
('Super Mario Odyssey', 'AAA', 3, 4),
('Metal Gear Solid V', 'AAA', 18, 8),
('Elden Ring', 'AAA', 16, 11),
('The Last of Us Part I', 'AAA', 18, 12),
('The Witcher 3: Wild Hunt', 'AAA', 18, 13),
('Red Dead Redemption 2', 'AAA', 18, 14),
('Baldur''s Gate 3', 'AAA', 18, 15),
('Cyberpunk 2077', 'AAA', 18, 13);

INSERT INTO genere (nome) VALUES
('JRPG'),
('Visual Novel'),
('Action RPG'),
('Platform'),
('Stealth'),
('Gacha'),
('FPS'),
('TPS'),
('RPG'),
('MMORPG'),
('MOBA'),
('Picchiaduro'),
('Corse'),
('Sport'),
('Puzzle'),
('Sandbox'),
('Survival'),
('Battle Royale'),
('Strategia'),
('Roguelike'),
('Avventura Grafica'),
('Azione'),
('Turn-Based'),
('Soulslike'),
('Open World');

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

INSERT INTO promozione (nome, dataInizio, dataFine) VALUES
('Saldi Invernali 2026', '2026-12-20', '2027-01-05'),
('Golden Week', '2026-04-29', '2026-05-06'),
('Black Friday 2026', '2026-11-23', '2026-11-30');


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

INSERT INTO edizione_gioco (idGioco, nomePiattaforma, prezzo, dataRilascio) VALUES
(1, 'PC', 0, '2025-06-26'),
(2, 'PC', 30, '2019-04-09'),
(2, 'Switch', 30, '2019-04-09'),
(3, 'PC', 50, '2025-10-15'),
(4, 'Switch', 60, '2017-10-27'),
(5, 'PC', 20, '2015-09-01'),
(5, 'PlayStation 5', 25, '2015-09-01'),
(3, 'PlayStation 5', 50, '2025-10-15'),
(3, 'Xbox Series X|S', 50, '2025-10-15'),
(6, 'PC', 60, '2022-02-25'),
(6, 'PlayStation 5', 60, '2022-02-25'),
(6, 'Xbox Series X|S', 60, '2022-02-25'),
(7, 'PC', 70, '2022-09-02'),
(7, 'PlayStation 5', 70, '2022-09-02'),
(8, 'PC', 30, '2015-05-19'),
(8, 'PlayStation 5', 30, '2015-05-19'),
(8, 'Xbox Series X|S', 30, '2015-05-19'),
(8, 'Switch', 30, '2015-05-19'),
(9, 'PC', 60, '2018-10-26'),
(9, 'PlayStation 5', 60, '2018-10-26'),
(9, 'Xbox Series X|S', 60, '2018-10-26'),
(10, 'PC', 60, '2023-08-03'),
(10, 'PlayStation 5', 60, '2023-08-03'),
(10, 'Xbox Series X|S', 60, '2023-08-03'),
(11, 'PC', 60, '2020-12-10'),
(11, 'PlayStation 5', 60, '2020-12-10'),
(11, 'Xbox Series X|S', 60, '2020-12-10');


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

INSERT INTO gioco_in_promozione (idGioco, idPromozione, percentuale) VALUES
(4, 1, 10),
(5, 3, 75);

INSERT INTO fattura (idUtente, idEdizione, prezzoAcquisto, key, dataAcquisto) VALUES
(16, 10, 60, '123e4567-e89b-12d3-a456-426614174001', '2022-03-01'),
(17, 5, 60, '123e4567-e89b-12d3-a456-426614174002', '2018-12-25'),
(23, 13, 70, '123e4567-e89b-12d3-a456-426614174003', '2022-10-10'),
(24, 6, 20, '123e4567-e89b-12d3-a456-426614174004', '2016-01-15'),
(26, 22, 60, '123e4567-e89b-12d3-a456-426614174005', '2023-09-01'),
(28, 2, 30, '123e4567-e89b-12d3-a456-426614174006', '2020-05-20'),
(33, 19, 60, '123e4567-e89b-12d3-a456-426614174007', '2019-11-05'),
(18, 1, 0, '123e4567-e89b-12d3-a456-426614174008', '2025-07-01'),
(20, 8, 50, '123e4567-e89b-12d3-a456-426614174009', '2025-11-01'),
(30, 25, 60, '123e4567-e89b-12d3-a456-426614174010', '2021-01-10'),
(31, 15, 30, '123e4567-e89b-12d3-a456-426614174011', '2016-06-01'),
(19, 11, 60, '123e4567-e89b-12d3-a456-426614174012', '2022-04-12');

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













