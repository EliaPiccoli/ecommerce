
DROP DOMAIN IF EXISTS cap_domain CASCADE;
CREATE DOMAIN cap_domain AS VARCHAR(5)
	CHECK(VALUE SIMILAR TO '[0-9]{5}')
	NOT NULL;

DROP DOMAIN IF EXISTS phone_domain CASCADE;
CREATE DOMAIN phone_domain AS VARCHAR(10)
	CHECK(VALUE SIMILAR TO '[0-9]{10}')
	NOT NULL;

DROP DOMAIN IF EXISTS pay_domain CASCADE;
CREATE DOMAIN pay_domain AS VARCHAR
	CHECK(VALUE IN('Carta di Credito', 'PayPal', 'Consegna'))
	NOT NULL;

DROP DOMAIN IF EXISTS ruolo_domain CASCADE;
CREATE DOMAIN ruolo_domain AS VARCHAR
	CHECK(VALUE IN ('Cliente', 'Responsabile'));

DROP DOMAIN IF EXISTS ora_domain CASCADE;
CREATE DOMAIN ora_domain AS TIME
	CHECK(VALUE IN ('9:00:00', '10:00:00', '11:00:00', '12:00:00'))
	NOT NULL;

DROP TABLE IF EXISTS CARTAFED CASCADE;
CREATE TABLE CARTAFED(
	Id SERIAL PRIMARY KEY,
	DataEmissione DATE NOT NULL DEFAULT'2020-10-10',
	Saldo INTEGER NOT NULL
);

DROP TABLE IF EXISTS UTENTE CASCADE;
CREATE TABLE UTENTE(
	Email VARCHAR PRIMARY KEY,
	Matricola VARCHAR,
	Nome VARCHAR NOT NULL CHECK(Nome<>''),
	Cognome VARCHAR NOT NULL CHECK(Cognome<>''),
	Indirizzo VARCHAR NOT NULL CHECK(Indirizzo<>''),
	Citta VARCHAR NOT NULL CHECK(Citta<>''),
	Cap cap_domain NOT NULL,
	Telefono phone_domain NOT NULL,
	Password VARCHAR NOT NULL CHECK(Password<>''),
	CartaFed INTEGER REFERENCES CARTAFED(Id),
	Ruolo ruolo_domain NOT NULL
);

DROP TABLE IF EXISTS PRODOTTO CASCADE;
CREATE TABLE PRODOTTO(
	Id SERIAL PRIMARY KEY,
	Tipo VARCHAR NOT NULL CHECK(Tipo<>''),
	Nome VARCHAR NOT NULL CHECK(Nome <>''),
	Marca VARCHAR NOT NULL CHECK(Marca<>''),
	Descrizione VARCHAR,
	Quantita INTEGER,
	Quantita_Conf INTEGER,
	Prezzo DECIMAL(5, 2) NOT NULL CHECK(Prezzo >='0.00') DEFAULT '1.5'
);

DROP TABLE IF EXISTS ORDINE CASCADE;
CREATE TABLE ORDINE(
	Id SERIAL PRIMARY KEY,
	DataConsegna DATE NOT NULL,
	OraConsegna ora_domain,
	Id_cliente VARCHAR NOT NULL REFERENCES UTENTE(email),
	Totale DECIMAL(6, 2) NOT NULL,
	PuntiGenerati INTEGER NOT NULL
);
DROP TABLE IF EXISTS PRODOTTO_IN_ORDINE;
CREATE TABLE PRODOTTO_IN_ORDINE(
	Id_ordine SERIAL NOT NULL REFERENCES ORDINE(Id),
	Id_prodotto SERIAL NOT NULL REFERENCES PRODOTTO(Id),
	Quantita INTEGER NOT NULL CHECK(Quantita > 0),
	PrezzoUnitario DECIMAL(5, 2) NOT NULL CHECK(PrezzoUnitario >='0.00'),
	PRIMARY KEY(Id_ordine, Id_prodotto)
);


INSERT INTO PRODOTTO(Tipo, Nome, Marca, Descrizione, Quantita, Quantita_Conf, Prezzo)
	VALUES('Frutta','Mele','Sudtirol','frutta con solo zuccheri naturali, 100%% Italiane', 100,NULL, '0.1'),
	('Frutta','Pesca','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100,NULL,  '0.1'),
	('Frutta','Banana','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100,NULL,  '0.2'),
	('Frutta','Pera','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100, NULL, '0.1'),
	('Frutta','Prugne','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100, NULL, '0.09'),
	('Frutta','Uva','Sudtirol','frutta con solo zuccheri naturali, 100%% Italiane', 100,NULL,  '0.2'),
	('Frutta','Ciliege','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100,NULL,  '0.09'),
	('Frutta','Bacche','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100,NULL,  '0.03'),
	('Frutta','More','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100, NULL, '0.03'),
	('Frutta','Lamponi','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100, NULL, '0.05'),
--------------------------------------------------------------------------------------------------------------------
	('Verdura','Cipolla','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.09'),
	('Verdura','Aglio','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.08'),
	('Verdura','Basilico','DelizieOrto','Verdura 100%% Italiane', 100,NULL,  '0.03'),
	('Verdura','Zucchine','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.1'),
	('Verdura','Melanzane','DelizieOrto','Verdura 100%% Italiane', 100,NULL,  '0.09'),
	('Verdura','Pomodori','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.08'),
	('Verdura','Patate','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.09'),
	('Verdura','Insalta','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.09'),
	('Verdura','Carote','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.09'),
	('Verdura','Cavolo','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.03'),
	('Verdura','Cetriolo','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.03'),
	('Verdura','Carciofi','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.09'),
	('Verdura','Peperoni','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.09'),
	('Verdura','Rapa','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.05'),
	('Verdura','Prezzemolo','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.08'),
	('Verdura','Sedano','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.08'),
	('Verdura','Spinaci','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.08'),
	('Verdura','Tartufo','Negrelli','Verdura 100%% Italiane', 100, NULL,'0.1'),
	('Verdura','Capperi','DelizieOrto','Verdura 100%% Italiane, vasetto da 100g', 100,NULL,  '1.20'),
	('Verdura','Asparagi','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.08'),
--------------------------------------------------------------------------------------------------------------------
	('Carne','Macinato Manzo','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '2'),
	('Carne','Macinato Vitello','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.5'),
	('Carne','Macinato Cavallo','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100,NULL,  '2'),
	('Carne','Bistecca Pollo','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100,NULL,  '1'),
	('Carne','Bistecca Maiale','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100,NULL,  '1.3'),
	('Carne','Speidini','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '2.3'),
	('Carne','Popette di maiale','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100,NULL,  '1.4'),
	('Carne','Bistecca Bovino','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.4'),
	('Carne','Petto Coniglio','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.4'),
	('Carne','Mortadella','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100,NULL,  '1'),
	('Carne','Salame milano','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.5'),
	('Carne','Prosciutto cotto','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.6'),
	('Carne','Prosciutto crudo','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.7'),
	('Carne','Salame ungherese','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.6'),
	('Carne','Salame aglio','Alto Adige','Prezzo 0,5 hg, Insaccato 100%% Italiana', 100, NULL, '1.7'),
	('Carne','Filetto di Manzo','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '2'),
	('Carne','Costata di vitello','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.7'),
	('Carne','Costata di manzo','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.7'),
--------------------------------------------------------------------------------------------------------------------
	('Pesce','Gamberi rossi','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '1.7'),
	('Pesce','Pesce Spada','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '2'),
	('Pesce','Trota','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '2'),
	('Pesce','Salmone','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '2.5'),
	('Pesce','Branzino','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '1.5'),
	('Pesce','Cozze','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '1.7'),
	('Pesce','Sugo Scoglio','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '1.5'),
	('Pesce','Vongole','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '2'),
	('Pesce','Sugo vongole','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100,NULL,  '1.5'),
	('Pesce','Cefalo','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '1.9'),
	('Pesce','Merluzzo','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '1.5'),
	('Pesce','Orata','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100,NULL,  '1.9'),
--------------------------------------------------------------------------------------------------------------------
	('Formaggi','Monte Veronese','Marzini','Prezzo 0,5 hg, Formaggio 100%% Italiano', 100, NULL, '1.8'),
	('Formaggi','Grana Padano','Marzini','Prezzo 0,5 hg, Formaggio 100%% Italiano', 100, NULL, '2'),
	('Formaggi','Parmiggiano Reggiano','Marzini','Prezzo 0,5 hg, Formaggio 100%% Italiano', 100, NULL, '2'),
	('Formaggi','Formaggio Verde','Marzini','Prezzo 0,5 hg, Formaggio 100%% Italiano', 100, NULL, '1.5'),
	('Formaggi','Asiago','Marzini','Prezzo 0,5 hg, Formaggio 100%% Italiano', 100, NULL, '1.5'),
	('Latticini','Latte','Parmalat','Prezzo 0,5 L, Latte 100%% Italiano', 100, NULL, '1.5'),
	('Formaggi','Yogurt','Parmalat','Prezzo 0,5 hg, Yogurt 100%% Italiano', 100,NULL,  '3'),
	('Uova','Uova','Amdori','Prezzo 6 uova, Allevamenti 100%% Italiani', 100,NULL, '3'),
--------------------------------------------------------------------------------------------------------------------
	('Alimentari','Olio Exstravergine','Turri','Prezzo confezione 1l', 100,NULL,  '7'),
	('Alimentari','Spaghetti','Barilla','Prezzo confezione 1,5kg', 100,NULL,  '2'),
	('Alimentari','Bucatini','Barilla','Prezzo confezione 1,5kg', 100, NULL, '2'),
	('Alimentari','MezzeManiche','Barilla','Prezzo confezione 1,5kg', 100, NULL, '2'),
	('Alimentari','Riso','Barilla','Prezzo confezione 1,5kg', 100, NULL, '2'),
	('Alimentari','Farfalline','Barilla','Prezzo confezione 1,5kg', 100, NULL, '2'),
	('Alimentari','Sottaceto Peperoncini','Riomare','Prezzo confezione 100g', 100, NULL, '1.2'),
	('Alimentari','Tonno Barattolo','Riomare','Prezzo confezione 100g', 100, NULL, '4'),
	('Alimentari','Insalatissime','Riomare','Prezzo confezione 100g', 100,NULL,  '4'),
	('Alimentari','Vongole Barattolo','Riomare','Prezzo confezione 100g', 100,NULL,  '3.5'),
	('Alimentari','Olive','Turri','Prezzo confezione 100g', 100, NULL, '3'),
	('Alimentari','Olive denocciolate','Turri','Prezzo confezione 100g', 100, NULL, '3'),
	('Alimentari','Salsa Maionese','kraft','Prezzo confezione 100g', 100, NULL, '1'),
	('Alimentari','Salsa Ketchup','kraft','Prezzo confezione 100g', 100, NULL, '1'),
	('Alimentari','Aceto','Turri','Prezzo confezione 0,5l', 100, NULL, '4'),
	('Alimentari','Sale','Saltic','Prezzo confezione 100g', 100, NULL, '1.5'),
	('Alimentari','Pepe nero','Carmel','Prezzo confezione 40g', 100, NULL, '1.5'),
	('Alimentari','Pepe verde','Carmel','Prezzo confezione 40g', 100, NULL, '1.5'),
	('Alimentari','Cannella','Carmel','Prezzo confezione 40g', 100, NULL, '1.5'),
	('Alimentari','Noce Moscata','Carmel','Prezzo confezione 40g', 100, NULL, '1.5'),
	('Alimentari','Origano','Carmel','Prezzo confezione 40g', 100, NULL, '1.5'),
	('Alimentari','Pepe','Carmel','Prezzo confezione 40g', 100, NULL, '1.5'),
--------------------------------------------------------------------------------------------------------------------
	('Dolci','Biscotti integrali','Pavesi','Prezzo confezione 500g', 100, NULL, '3'),
	('Dolci','Biscotti Gocciole','Pavesi','Prezzo confezione 500g', 100,NULL, '4'),
	('Dolci','Brioche crema','Bauli','Prezzo confezione 500g', 100, NULL, '3.5'),
	('Dolci','Brioche cioccolato','Bauli','Prezzo confezione 500g', 100, NULL, '3.5'),
	('Dolci','Brioche marmellata','Bauli','Prezzo confezione 500g', 100, NULL, '3.5'),
	('Dolci','Marmellata Fragola','Zuegg','Prezzo confezione 200g', 100, NULL, '4.5'),
	('Dolci','Marmellata Pesca','Zuegg','Prezzo confezione 200g', 100, NULL, '4.5'),
	('Dolci','Marmellata Arancia','Zuegg','Prezzo confezione 200g', 100, NULL, '4.5'),
	('Dolci','Marmellata Albicocca','Zuegg','Prezzo confezione 200g', 100, NULL, '4.5'),
	('Dolci','Marmellata Frutti di bosco','Zuegg','Prezzo confezione 200g', 100,NULL,  '4.5'),
	('Dolci','Caffè','Lavazza','Prezzo confezione 10 cialde', 100,NULL,  '2.5'),
	('Dolci','Caffè','Pellini','Prezzo confezione 10 cialde', 100, NULL, '3'),
	('Dolci','Caffè','Nespresso','Prezzo confezione 10 cialde', 100, NULL, '3.5'),
	('Dolci','Zucchero','Lavazza','Prezzo confezione 100g', 100, NULL, '1.5'),
	('Dolci','Zucchero canna','Lavazza','Prezzo confezione 100g', 100,NULL,  '2.5'),
	('Dolci','Dolcificante','Lavazza','Prezzo confezione 100g', 100,NULL,  '3'),
--------------------------------------------------------------------------------------------------------------------
	('Bevande','Acqua','Lete','Prezzo 6 bott. 1,5l', 100, NULL, '3'),
	('Bevande','Acqua','Levissima','Prezzo 6 bott.1,5l', 100, NULL, '3.5'),
	('Bevande','Acqua','BrioBlu','Prezzo 6 bott. 1,5l', 100, NULL, '3.5'),
	('Bevande','Vino Bianco','Sartori','Prezzo 1 bott.0,7l', 100, NULL, '3.5'),
	('Bevande','Vino Custoza','Custoza','Prezzo 1 bott.0,7l', 100, NULL, '2'),
	('Bevande','Vino Rosso Valpolicella','Sartori','Prezzo 1 bott. 0,7l', 100,NULL,  '7'),
	('Bevande','Birra','Forst','Prezzo 1 bott. 0,3l', 100,NULL,  '1.5'),
	('Bevande','Birra','Bud','Prezzo 1 bott. 0,3l', 100, NULL, '1.7'),
	('Bevande','CocaCola','CocaCola','Prezzo 1 bott. 0,3l', 100, NULL, '1.5'),
	('Bevande','Fanta','Fanta','Prezzo 1 bott. 0,3l', 100, NULL, '1.5'),
	('Bevande','Tonica','Sweeps','Prezzo 1 bott. 0,3l', 100, NULL, '2'),
	('Bevande','Lemon','Sweeps','Prezzo 1 bott. 0,3l', 100, NULL, '2'),
	('Bevande','Succo ACE','Zuegg','Prezzo 1 bott. 1l', 100, NULL, '2.5'),
	('Bevande','Succo Pesca','Zuegg','Prezzo 1 bott. 1l', 100, NULL,'2.5'),
	('Bevande','Succo Arancia','Zuegg','Prezzo 1 bott. 1l', 100, NULL,'2.5'),
--------------------------------------------------------------------------------------------------------------------
	('Pane','Pane Ciabattine','Forno','Prezzo 1 hg ', 100, NULL,'2.5'),
	('Pane','Pane Arabo','Forno','Prezzo 1 hg ', 100, NULL,'2.5'),
	('Pane','Pane Tartaruga','Forno','Prezzo 1 hg ', 100, NULL,'2.5'),
	('Pane','Pane Mignon','Forno','Prezzo 1 hg ', 100, NULL,'2.5'),
--------------------------------------------------------------------------------------------------------------------
	('Surgelati','Patatine Fritte','ForniPa','Prezzo 1 kg ', 100,NULL,'3'),
	('Surgelati','Minestrone','ForniPa','Prezzo 1 kg ', 100, NULL,'2'),
	('Surgelati','Gelati Cornetti','ForniPa','Prezzo 6 gelati', 100,NULL, '3.5'),
	('Surgelati','Gelato multigusto','ForniPa','Prezzo 6 gelati', 100,NULL, '3'),
--------------------------------------------------------------------------------------------------------------------
	('Igene','Carta','cartaSI','Prezzo Confezione 6p', 100, NULL,'3'),
	('Igene','Tovaglioli','cartaSI','Prezzo Confezione 6p', 100, NULL,'3'),
	('Igene','Carta igenica','cartaSI','Prezzo Confezione 6p', 100, NULL,'3'),
	('Igene','Shampo','Oreal','Prezzo Confezione 0,4l', 100, NULL,'4.5'),
	('Igene','Dentifricio','mentadent','Prezzo Confezione tubetto', 100, NULL,'2.5'),
	('Igene','Pannolini','cartaSI','Prezzo Confezione 10p', 100, NULL,'5'),
--------------------------------------------------------------------------------------------------------------------
	('Per la casa','Alcool','cartaSI','Prezzo Confezione 1l', 100, NULL,'4'),
	('Per la casa','Ammoniaca','cartaSI','Prezzo Confezione 1l', 100, NULL,'4'),
	('Per la casa','Detersivo','cartaSI','Prezzo Confezione 1l', 100, NULL,'4'),
	('Per la casa','Sbrilla Acciaio','cartaSI','Prezzo Confezione 1l', 100,NULL, '4'),
	('Per la casa','Guanti','cartaSI','Prezzo Confezione 100p', 100, NULL,'5'),
--------------------------------------------------------------------------------------------------------------------
	('Animali','Crocchette','Royal','Prezzo Confezione 3kg', 100,NULL, '20'),
	('Dolci','Cioccolata','Mila','Prezzo Confezione 100g', 100, NULL,'3');
------------------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO CARTAFED(DataEmissione, Saldo)
	VALUES('2020-09-19','000000'),
	('2020-09-19','000000'),
	('2020-09-19','000000');

INSERT INTO UTENTE(Matricola, Nome, Cognome, Indirizzo, Citta, Cap, Email, Telefono, Password, CartaFed, Ruolo)
	VALUES('', 'Matteo','Rosa','Celino 22','Verona','37134','matteo.rosa@gmail.it','3432178920','0000', 1, 'Cliente'),
	('', 'Massimo','lugo','Santa marta 4','Bari','17455','massimo.lugo@libero.it','3452343538','1111', 2, 'Cliente'),
	('', 'Nicola','sarti','Mantova 43','Roma','20154','nicola.sarti@hotmail.it','3404323678','2222', 3, 'Cliente');
