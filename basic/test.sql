    Create schema CulturaGenerala;
    use CulturaGenerala ;

DROP TABLE IF EXISTS User; 
    Create table if not exists User(
		id INT auto_increment PRIMARY KEY,
        username NVARCHAR(100) UNIQUE,
        password NVARCHAR(100)
	); 
    
    DROP TABLE IF EXISTS TestCultura; 
    Create table if not exists TestCultura(
		id INT auto_increment PRIMARY KEY,
        nume NVARCHAR(100) 
	); 

    DROP TABLE IF EXISTS Intrebare; 
    Create table if not exists Intrebare(
		id INT auto_increment PRIMARY KEY,
        content NVARCHAR(200),
        correctAnswer NVARCHAR(200),
        idTest INT references TestCultura(id)
	); 
    
    
    DROP TABLE IF EXISTS Game; 
    Create table if not exists Game(
		id INT auto_increment PRIMARY KEY,
        idUser INT REFERENCES User(id),
        idTest INT References TestCultura(id) ,
        Punctaj INT
	);  

insert into User values(0 ,"user1", "pass"); 
insert into User values(0 ,"user2", "pass"); 
insert into User values(0 ,"user3", "pass"); 
insert into User values(0 ,"user4", "pass"); 

insert into TestCultura values(0,"Matematica"); 
insert into TestCultura values(0,"Geografie"); 


insert into intrebare values(0,"1+1 = ?", "2", 1) ;
insert into intrebare values(0,"5+2 = ?", "7", 1) ;
insert into intrebare values(0,"2*3 = ?", "6", 1) ;
insert into intrebare values(0,"Capitala Romaniei? ", "Bucuresti", 2) ;
insert into intrebare values(0,"Capitala Georgiei?", "Tbilisi", 2) ;
insert into intrebare values(0,"Capitala Frantei?", "Paris", 2) ;

select * from game

show variables like "max_connections";

set global max_connections = 500;

show processlist;

