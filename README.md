# mpp
#test
Roses are red. Violets are blue. Unsuspected comma on line 32.

```
//Roses are red, 
//Violets are blue, 
//If you're reading this,
//I'm sorry for you.
```
Cerinta mpp:
Organizarorii unei curde de raliu in desert folosesc un sistem soft 
pentru gestiunea punctelor de control. Fiecare persoana responsabila de 
un punct de control foloseste o aplicatie cu urm functionalitati:
1.Login: Dupa autentificarea cu succes se deschide o noua fereastra in 
care sunt afisate numarul punctului de control de care este responsabila 
persoana sio o lista cu masini astfel:
	a. daca este locul de start(puctul de control 0) sunt afisate 
toate masinile, iar pentru fiecare masina ultimul punct de control pe 
care la care a trecut si ora la care a trecut(hh:mm)
	b.pentru celelalte puncte de control sunt afisate masinile care 
au trecut pe la punctul de control anterior punctului curent si ora 
(hh:mm) in ordine crescatoare dupa ora la care au trecut pe la punctul 
de control anterior

2.Marcare trecere. Cand o masina trece pe la un punct de control , 
persoana responsabila va introduce timpul la care a trecut pe acolo si 
va marca trecerea. Dupa marcare, masina va fi stearsa din lista afisata 
la punctul de control respectiv si va aparea automat in lista afisata la 
punctul urmator (conform cerintei 1)

3.Logout

4.REST care permite vizualizarea punctelor de control pe la care a 
trecut o anumita masina si timpul la care a trecut (hh:mm) in ordine 
descrescatoare dupa numarul punctului de control.

Pentru testare folositi 3 puncte de controle, exceptand punctul de start 
(1,2,3) 
>>>>>>> 313f17e2d7bf717f800713789a6c29c9612df23d
