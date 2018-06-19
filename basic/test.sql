    Create schema Raliu;
    use Raliu ;

DROP TABLE IF EXISTS User;
    Create table if not exists User(
		id INT auto_increment PRIMARY KEY,
        username NVARCHAR(100) UNIQUE,
        password NVARCHAR(100)
	);

    DROP TABLE IF EXISTS PunctControl;
    Create table if not exists PunctControl(
		id INT auto_increment PRIMARY KEY,
        numarControl INT,
        idUser INT references User(id)
	);

    DROP TABLE IF EXISTS Masina;
    Create table if not exists Masina(
		id INT auto_increment PRIMARY KEY,
        nume NVARCHAR(200)
	);


    DROP TABLE IF EXISTS MasinaPunctControl;
    Create table if not exists MasinaPunctControl(
		id INT auto_increment PRIMARY KEY,
        idMasina INT REFERENCES Masina(id),
        idPunctControl INT References PunctControl(id) ,
        timpTrecere DateTime
	);

insert into User values(0 ,"user1", "pass");
insert into User values(0 ,"user2", "pass");
insert into User values(0 ,"user3", "pass");
insert into User values(0 ,"user4", "pass");

insert into PunctControl values(0, 0, 1);
insert into PunctControl values(0, 1, 2);
insert into PunctControl values(0, 2, 3);
insert into PunctControl values(0, 3, 4);



insert into Masina values(0, "Masina1") ;
insert into Masina values(0, "Masina2") ;
insert into Masina values(0, "Masina3") ;


show variables like "max_connections";

set global max_connections = 500;

show processlist;

