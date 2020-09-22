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
	Id CHAR(6) PRIMARY KEY,
	DataEmissione DATE NOT NULL DEFAULT'2020-09-10', 
	Saldo INTEGER NOT NULL
);
	
DROP TABLE IF EXISTS UTENTE CASCADE;
CREATE TABLE UTENTE(
	Email VARCHAR PRIMARY KEY CHECK(Email<>''),
	Matricola VARCHAR, 
	Nome VARCHAR NOT NULL CHECK(Nome<>''),
	Cognome VARCHAR NOT NULL CHECK(Cognome<>''),
	Indirizzo VARCHAR NOT NULL CHECK(Indirizzo<>''),
	Citta VARCHAR NOT NULL CHECK(Citta<>''),
	Cap cap_domain NOT NULL, 
	Telefono phone_domain NOT NULL,
	Password VARCHAR NOT NULL CHECK(Password<>''),
	CartaFed CHAR(6) REFERENCES CARTAFED(Id),
	Ruolo ruolo_domain NOT NULL
);

DROP TABLE IF EXISTS PRODOTTO CASCADE;
CREATE TABLE PRODOTTO(
	Id CHAR(4) PRIMARY KEY,
	Tipo VARCHAR NOT NULL CHECK(Tipo<>''),
	Nome VARCHAR NOT NULL CHECK(Nome <>''),
	Marca VARCHAR NOT NULL CHECK(Marca<>''),
	Descrizione VARCHAR,
	Quantita INTEGER NOT NULL DEFAULT '50',
	Quantita_Conf INTEGER DEFAULT  '1'  ,
	Prezzo DECIMAL(5, 2) NOT NULL CHECK(Prezzo >='0.00') DEFAULT '1.5'
);

DROP TABLE IF EXISTS ORDINE CASCADE;
CREATE TABLE ORDINE(
	Id CHAR(4) PRIMARY KEY,
	DataConsegna DATE NOT NULL,
	OraConsegna ora_domain,
	emailCliente VARCHAR NOT NULL REFERENCES UTENTE(email),
	Totale DECIMAL(6, 2) NOT NULL, 
	PuntiGenerati INTEGER NOT NULL
);

DROP TABLE IF EXISTS PRODOTTO_IN_ORDINE;
CREATE TABLE PRODOTTO_IN_ORDINE(
	Id_ordine CHAR(4) NOT NULL REFERENCES ORDINE(Id),
	Id_prodotto CHAR(4) NOT NULL REFERENCES PRODOTTO(Id),
	Quantita INTEGER NOT NULL CHECK(Quantita > 0),
	PrezzoUnitario DECIMAL(5, 2) NOT NULL CHECK(PrezzoUnitario >='0.00'),
	PRIMARY KEY(Id_ordine, Id_prodotto)
);

INSERT INTO PRODOTTO(Id, Tipo, Nome, Marca, Descrizione, Quantita, Quantita_Conf, Prezzo)
	VALUES('0001','Frutta','Mele','Sudtirol','frutta con solo zuccheri naturali, 100%% Italiane', 100,NULL, '0.1'),
	('0002','Frutta','Pesca','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100,NULL,  '0.1'),
	('0003','Frutta','Banana','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100,NULL,  '0.2'),
	('0004','Frutta','Pera','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100, NULL, '0.1'),
	('0005','Frutta','Prugne','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100, NULL, '0.09'),
	('0006','Frutta','Uva','Sudtirol','frutta con solo zuccheri naturali, 100%% Italiane', 100,NULL,  '0.2'),
	('0007','Frutta','Ciliege','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100,NULL,  '0.09'),
	('0008','Frutta','Bacche','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100,NULL,  '0.03'),
	('0009','Frutta','More','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100, NULL, '0.03'),
	('0010','Frutta','Lamponi','Val Esaro','frutta con solo zuccheri naturali, 100%% Italiane', 100, NULL, '0.05'),
--------------------------------------------------------------------------------------------------------------------
	('0011','Verdura','Cipolla','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.09'),
	('0012','Verdura','Aglio','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.08'),
	('0013','Verdura','Basilico','DelizieOrto','Verdura 100%% Italiane', 100,NULL,  '0.03'),
	('0014','Verdura','Zucchine','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.1'),
	('0015','Verdura','Melanzane','DelizieOrto','Verdura 100%% Italiane', 100,NULL,  '0.09'),
	('0016','Verdura','Pomodori','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.08'),
	('0017','Verdura','Patate','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.09'),
	('0018','Verdura','Insalta','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.09'),
	('0019','Verdura','Carote','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.09'),
	('0020','Verdura','Cavolo','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.03'),
	('0021','Verdura','Cetriolo','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.03'),
	('0022','Verdura','Carciofi','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.09'),
	('0023','Verdura','Peperoni','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.09'),
	('0024','Verdura','Rapa','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.05'),
	('0025','Verdura','Prezzemolo','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.08'),
	('0026','Verdura','Sedano','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.08'),
	('0027','Verdura','Spinaci','Negrelli','Verdura 100%% Italiane', 100, NULL, '0.08'),
	('0028','Verdura','Tartufo','Negrelli','Verdura 100%% Italiane', 100, NULL,'0.1'),
	('0029','Verdura','Capperi','DelizieOrto','Verdura 100%% Italiane, vasetto da 100g', 100,NULL,  '1.20'),
	('0030','Verdura','Asparagi','DelizieOrto','Verdura 100%% Italiane', 100, NULL, '0.08'),
--------------------------------------------------------------------------------------------------------------------
	('0000','Carne','Macinato Manzo','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '2'),
	('0031','Carne','Macinato Vitello','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.5'),
	('0032','Carne','Macinato Cavallo','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100,NULL,  '2'),
	('0034','Carne','Bistecca Pollo','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100,NULL,  '1'),
	('0035','Carne','Bistecca Maiale','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100,NULL,  '1.3'),
	('0036','Carne','Speidini','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '2.3'),
	('0037','Carne','Popette di maiale','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100,NULL,  '1.4'),
	('0038','Carne','Bistecca Bovino','Aia','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.4'),
	('0039','Carne','Petto Coniglio','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.4'),
	('0040','Carne','Mortadella','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100,NULL,  '1'),
	('0041','Carne','Salame milano','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.5'),
	('0042','Carne','Prosciutto cotto','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.6'),
	('0043','Carne','Prosciutto crudo','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.7'),
	('0044','Carne','Salame ungherese','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.6'),
	('0045','Carne','Salame aglio','Alto Adige','Prezzo 0,5 hg, Insaccato 100%% Italiana', 100, NULL, '1.7'),
	('0046','Carne','Filetto di Manzo','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '2'),
	('0047','Carne','Costata di vitello','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.7'),
	('0048','Carne','Costata di manzo','Negroni','Prezzo 0,5 hg, Carne 100%% Italiana', 100, NULL, '1.7'),
--------------------------------------------------------------------------------------------------------------------
	('0049','Pesce','Gamberi rossi','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '1.7'),
	('0050','Pesce','Pesce Spada','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '2'),
	('0051','Pesce','Trota','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '2'),
	('0052','Pesce','Salmone','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '2.5'),
	('0053','Pesce','Branzino','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '1.5'),
	('0054','Pesce','Cozze','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '1.7'),
	('0055','Pesce','Sugo Scoglio','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '1.5'),
	('0056','Pesce','Vongole','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '2'),
	('0057','Pesce','Sugo vongole','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100,NULL,  '1.5'),
	('0058','Pesce','Cefalo','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '1.9'),
	('0059','Pesce','Merluzzo','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100, NULL, '1.5'),
	('0060','Pesce','Orata','Mare Blu','Prezzo 0,5 hg, Pesce 100%% Italiano', 100,NULL,  '1.9'),
--------------------------------------------------------------------------------------------------------------------
	('0142','Formaggi','Monte Veronese','Marzini','Prezzo 0,5 hg, Formaggio 100%% Italiano', 100, NULL, '1.8'),
	('0061','Formaggi','Grana Padano','Marzini','Prezzo 0,5 hg, Formaggio 100%% Italiano', 100, NULL, '2'),
	('0062','Formaggi','Parmiggiano Reggiano','Marzini','Prezzo 0,5 hg, Formaggio 100%% Italiano', 100, NULL, '2'),
	('0063','Formaggi','Formaggio Verde','Marzini','Prezzo 0,5 hg, Formaggio 100%% Italiano', 100, NULL, '1.5'),
	('0064','Formaggi','Asiago','Marzini','Prezzo 0,5 hg, Formaggio 100%% Italiano', 100, NULL, '1.5'),
	('0065','Latticini','Latte','Parmalat','Prezzo 0,5 L, Latte 100%% Italiano', 100, NULL, '1.5'),
	('0066','Formaggi','Yogurt','Parmalat','Prezzo 0,5 hg, Yogurt 100%% Italiano', 100,NULL,  '3'),
	('0067','Uova','Uova','Amdori','Prezzo 6 uova, Allevamenti 100%% Italiani', 100,NULL, '3'),
--------------------------------------------------------------------------------------------------------------------
	('0068','Alimentari','Olio Exstravergine','Turri','Prezzo confezione 1l', 100,NULL,  '7'),
	('0069','Alimentari','Spaghetti','Barilla','Prezzo confezione 1,5kg', 100,NULL,  '2'),
	('0070','Alimentari','Bucatini','Barilla','Prezzo confezione 1,5kg', 100, NULL, '2'),
	('0071','Alimentari','MezzeManiche','Barilla','Prezzo confezione 1,5kg', 100, NULL, '2'),
	('0072','Alimentari','Riso','Barilla','Prezzo confezione 1,5kg', 100, NULL, '2'),
	('0073','Alimentari','Farfalline','Barilla','Prezzo confezione 1,5kg', 100, NULL, '2'),
	('0074','Alimentari','Sottaceto Peperoncini','Riomare','Prezzo confezione 100g', 100, NULL, '1.2'),
	('0075','Alimentari','Tonno Barattolo','Riomare','Prezzo confezione 100g', 100, NULL, '4'),
	('0076','Alimentari','Insalatissime','Riomare','Prezzo confezione 100g', 100,NULL,  '4'),
	('0077','Alimentari','Vongole Barattolo','Riomare','Prezzo confezione 100g', 100,NULL,  '3.5'),
	('0078','Alimentari','Olive','Turri','Prezzo confezione 100g', 100, NULL, '3'),
	('0079','Alimentari','Olive denocciolate','Turri','Prezzo confezione 100g', 100, NULL, '3'),
	('0080','Alimentari','Salsa Maionese','kraft','Prezzo confezione 100g', 100, NULL, '1'),
	('0081','Alimentari','Salsa Ketchup','kraft','Prezzo confezione 100g', 100, NULL, '1'),
	('0082','Alimentari','Aceto','Turri','Prezzo confezione 0,5l', 100, NULL, '4'),
	('0083','Alimentari','Sale','Saltic','Prezzo confezione 100g', 100, NULL, '1.5'),
	('0084','Alimentari','Pepe nero','Carmel','Prezzo confezione 40g', 100, NULL, '1.5'),
	('0085','Alimentari','Pepe verde','Carmel','Prezzo confezione 40g', 100, NULL, '1.5'),
	('0086','Alimentari','Cannella','Carmel','Prezzo confezione 40g', 100, NULL, '1.5'),
	('0087','Alimentari','Noce Moscata','Carmel','Prezzo confezione 40g', 100, NULL, '1.5'),
	('0088','Alimentari','Origano','Carmel','Prezzo confezione 40g', 100, NULL, '1.5'),
	('0089','Alimentari','Pepe','Carmel','Prezzo confezione 40g', 100, NULL, '1.5'),
--------------------------------------------------------------------------------------------------------------------
	('0090','Dolci','Biscotti integrali','Pavesi','Prezzo confezione 500g', 100, NULL, '3'),
	('0091','Dolci','Biscotti Gocciole','Pavesi','Prezzo confezione 500g', 100,NULL, '4'),
	('0092','Dolci','Brioche crema','Bauli','Prezzo confezione 500g', 100, NULL, '3.5'),
	('0093','Dolci','Brioche cioccolato','Bauli','Prezzo confezione 500g', 100, NULL, '3.5'),
	('0094','Dolci','Brioche marmellata','Bauli','Prezzo confezione 500g', 100, NULL, '3.5'),
	('0095','Dolci','Marmellata Fragola','Zuegg','Prezzo confezione 200g', 100, NULL, '4.5'),
	('0096','Dolci','Marmellata Pesca','Zuegg','Prezzo confezione 200g', 100, NULL, '4.5'),
	('0097','Dolci','Marmellata Arancia','Zuegg','Prezzo confezione 200g', 100, NULL, '4.5'),
	('0098','Dolci','Marmellata Albicocca','Zuegg','Prezzo confezione 200g', 100, NULL, '4.5'),
	('0099','Dolci','Marmellata Frutti di bosco','Zuegg','Prezzo confezione 200g', 100,NULL,  '4.5'),
	('0100','Dolci','Caffè','Lavazza','Prezzo confezione 10 cialde', 100,NULL,  '2.5'),
	('0101','Dolci','Caffè','Pellini','Prezzo confezione 10 cialde', 100, NULL, '3'),
	('0102','Dolci','Caffè','Nespresso','Prezzo confezione 10 cialde', 100, NULL, '3.5'),
	('0103','Dolci','Zucchero','Lavazza','Prezzo confezione 100g', 100, NULL, '1.5'),
	('0104','Dolci','Zucchero canna','Lavazza','Prezzo confezione 100g', 100,NULL,  '2.5'),
	('0105','Dolci','Dolcificante','Lavazza','Prezzo confezione 100g', 100,NULL,  '3'),
--------------------------------------------------------------------------------------------------------------------
	('0106','Bevande','Acqua','Lete','Prezzo 6 bott. 1,5l', 100, NULL, '3'),
	('0107','Bevande','Acqua','Levissima','Prezzo 6 bott.1,5l', 100, NULL, '3.5'),
	('0108','Bevande','Acqua','BrioBlu','Prezzo 6 bott. 1,5l', 100, NULL, '3.5'),
	('0109','Bevande','Vino Bianco','Sartori','Prezzo 1 bott.0,7l', 100, NULL, '3.5'),
	('0110','Bevande','Vino Custoza','Custoza','Prezzo 1 bott.0,7l', 100, NULL, '2'),
	('0111','Bevande','Vino Rosso Valpolicella','Sartori','Prezzo 1 bott. 0,7l', 100,NULL,  '7'),
	('0112','Bevande','Birra','Forst','Prezzo 1 bott. 0,3l', 100,NULL,  '1.5'),
	('0113','Bevande','Birra','Bud','Prezzo 1 bott. 0,3l', 100, NULL, '1.7'),
	('0114','Bevande','CocaCola','CocaCola','Prezzo 1 bott. 0,3l', 100, NULL, '1.5'),
	('0115','Bevande','Fanta','Fanta','Prezzo 1 bott. 0,3l', 100, NULL, '1.5'),
	('0116','Bevande','Tonica','Sweeps','Prezzo 1 bott. 0,3l', 100, NULL, '2'),
	('0117','Bevande','Lemon','Sweeps','Prezzo 1 bott. 0,3l', 100, NULL, '2'),
	('0118','Bevande','Succo ACE','Zuegg','Prezzo 1 bott. 1l', 100, NULL, '2.5'),
	('0119','Bevande','Succo Pesca','Zuegg','Prezzo 1 bott. 1l', 100, NULL,'2.5'),
	('0120','Bevande','Succo Arancia','Zuegg','Prezzo 1 bott. 1l', 100, NULL,'2.5'),
--------------------------------------------------------------------------------------------------------------------	
	('0121','Pane','Pane Ciabattine','Forno','Prezzo 1 hg ', 100, NULL,'2.5'),
	('0122','Pane','Pane Arabo','Forno','Prezzo 1 hg ', 100, NULL,'2.5'),
	('0123','Pane','Pane Tartaruga','Forno','Prezzo 1 hg ', 100, NULL,'2.5'),
	('0124','Pane','Pane Mignon','Forno','Prezzo 1 hg ', 100, NULL,'2.5'),
--------------------------------------------------------------------------------------------------------------------	
	('0125','Surgelati','Patatine Fritte','ForniPa','Prezzo 1 kg ', 100,NULL,'3'),
	('0126','Surgelati','Minestrone','ForniPa','Prezzo 1 kg ', 100, NULL,'2'),
	('0127','Surgelati','Gelati Cornetti','ForniPa','Prezzo 6 gelati', 100,NULL, '3.5'),
	('0128','Surgelati','Gelato multigusto','ForniPa','Prezzo 6 gelati', 100,NULL, '3'),
--------------------------------------------------------------------------------------------------------------------	
	('0129','Igene','Carta','cartaSI','Prezzo Confezione 6p', 100, NULL,'3'),
	('0130','Igene','Tovaglioli','cartaSI','Prezzo Confezione 6p', 100, NULL,'3'),
	('0131','Igene','Carta igenica','cartaSI','Prezzo Confezione 6p', 100, NULL,'3'),
	('0132','Igene','Shampo','Oreal','Prezzo Confezione 0,4l', 100, NULL,'4.5'),
	('0133','Igene','Dentifricio','mentadent','Prezzo Confezione tubetto', 100, NULL,'2.5'),
	('0134','Igene','Pannolini','cartaSI','Prezzo Confezione 10p', 100, NULL,'5'),
--------------------------------------------------------------------------------------------------------------------
	('0135','Per la casa','Alcool','cartaSI','Prezzo Confezione 1l', 100, NULL,'4'),
	('0136','Per la casa','Ammoniaca','cartaSI','Prezzo Confezione 1l', 100, NULL,'4'),
	('0137','Per la casa','Detersivo','cartaSI','Prezzo Confezione 1l', 100, NULL,'4'),
	('0138','Per la casa','Sbrilla Acciaio','cartaSI','Prezzo Confezione 1l', 100,NULL, '4'),
	('0139','Per la casa','Guanti','cartaSI','Prezzo Confezione 100p', 100, NULL,'5'),
--------------------------------------------------------------------------------------------------------------------
	('0140','Animali','Crocchette','Royal','Prezzo Confezione 3kg', 100,NULL, '20'),
	('0141','Dolci','Cioccolata','Mila','Prezzo Confezione 100g', 100, NULL,'3');
------------------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO CARTAFED(Id, DataEmissione, Saldo)
	VALUES('000000','2020-09-19','000000'),
	('000001','2020-09-19','000000'),
	('000002','2020-09-19','000000');
	
INSERT INTO UTENTE(Email, Matricola, Nome, Cognome, Indirizzo, Citta, Cap, Telefono, Password, CartaFed, Ruolo)
	VALUES('matteo.rosa@gmail.it','', 'Matteo','Rosa','Celino 22','Verona','37134','3432178920','0000', '000000', 'Cliente'),
	('massimo.lugo@libero.it','', 'Massimo','lugo','Santa marta 4','Bari','17455','3452343538','1111', '000001', 'Cliente'),
	('nicola.sarti@hotmail.it','', 'Nicola','sarti','Mantova 43','Roma','20154','3404323678','2222', '000002', 'Cliente');


INSERT INTO ORDINE(Id, DataConsegna, OraConsegna, Totale, PuntiGenerati, emailCliente)
	VALUES('0000','2020-10-20','9:00:00','100','100','matteo.rosa@gmail.it');




	


