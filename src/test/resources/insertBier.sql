insert into bieren(naam, brouwerId, alcohol, prijs, besteld)
VALUES ('test', (select brouwers.id from brouwers where brouwers.naam = 'test'), 10, 10, 10),
       ('test2', (select brouwers.id from brouwers where brouwers.naam = 'test'), 10, 10, 10);