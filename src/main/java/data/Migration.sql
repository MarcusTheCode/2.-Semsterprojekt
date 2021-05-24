
-- CLEANUP

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

-- SETUP DATABASE

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE genres (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE series (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE seasons (
    id SERIAL PRIMARY KEY,
    seasonNumber INTEGER NOT NULL,
    seriesID INTEGER NOT NULL REFERENCES series(id)
);

CREATE TABLE artists (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE superUsers (
    id SERIAL PRIMARY KEY,
    isAdmin BOOLEAN NOT NULL,
    userName VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE productions (
    id SERIAL PRIMARY KEY,
    episodeNumber INTEGER,
    type VARCHAR(50) NOT NULL,
    categoryID INTEGER REFERENCES categories(id),
    seasonID INTEGER REFERENCES seasons(id),
    producerID INTEGER NOT NULL REFERENCES superUsers(id),
    productionTitle VARCHAR(150)
);

CREATE TABLE productionGenres (
    productionID INTEGER NOT NULL REFERENCES productions(id),
    genreID INTEGER NOT NULL REFERENCES genres(id),
    CONSTRAINT UQ_ProductionGenre UNIQUE(productionID, genreID)
);



CREATE TABLE castMembers (
    productionID INTEGER NOT NULL REFERENCES productions(id),
    role VARCHAR(50) NOT NULL,
    artistID INTEGER NOT NULL REFERENCES artists(id),
    CONSTRAINT UQ_CastMember UNIQUE(productionID, role, artistID)
);

-- load test data


-- Spiderman: Far from home artists
INSERT INTO artists(name,email) VALUES
('Tom Holland','Tomholland@spider.co.uk'),
('Zendaya','Zendaya@youmail.com'),
('Samuel L. Jackson','Samuel.L.Jackson@msn.com'),
('Jake Gyllenhaal','JakeGtllenhaal@live.com'),
('Jon Favreau','Jon.Favreau@hotmail.com'),
('Jacob Batalon','Jacob1Batalon@outlook.com'),
('Marisa Tomei','marisa.tomei@aol.com'),
('Combie Smulders','CombieSmulders@yahoo.ca'),
('Jon Watts','JonWatts@yahoo.com'),
('Kevin Feige','KevinFeige@Disney.marvel.com');

-- Star Wars prequel trilogy artists
INSERT INTO artists(name,email) VALUES
('George Lucas','GorgeLucas@lucas.film.com'),
('Rick McCallum','RickMcCallum@gmail.com'),
('Liam Neeson','LiamNeeson@jedi.com'),
('Ewan McGregor','EwanMcGregor@charter.net'),
('Natalie Portman','NataliePortman@hotmail.com'),
('Jake Lloyd','JakeLloyd@live.com'),
('Ian McDiarmid','IanMcDiarmid@rediffail.com'),
('Anthony Daniels','AnthonyDaniels@cox.net'),
('Kenny Baker','KennyBaker@sbcglobal.net'),
('Pernilla August','PernillaAugust@verizon.net'),
('Frank Oz','FrankOz@att.net'),
('Hayden Christensen','HaydenChristensen@win.com');

-- The Bee Movie artists
INSERT INTO artists(name,email) VALUES
('Barry B. Benson','BarryBeeBenson@bee.hive'),
('Steve Hickner','SteveHickner@gmail.com'),
('Simon J. Smith','Simon.J.Smith@youmail.com');

-- Lucifer artists
INSERT INTO artists(name,email) VALUES
('Tom Kapinos','TomKapinos@bee.hive'),
('Tom Ellis','TomEllis@gmail.com'),
('Lauren German','LaurenGerman@youmail.com'),
('D.B. Woodside','D.B.Woodside@verizon.net'),
('Rachael Harris','RachaelHarris@att.net'),
('Lesley-Ann Brandt','Lesley-AnnBrandt@win.com');


--Suits artists
INSERT INTO artists(name,email) VALUES
('Dave Bartis','DaveBartis@gmail.com'),
('Gabriel Macht','GabrielMacht@gmail.com'),
('Patrick J. Adams','Patrick.J.Adams@youmail.com'),
('Rick Hoffman','RickHoffman@verizon.net'),
('Meghan, Duchess of Sussex','MeghanSussex@att.net'),
('Sarah Rafferty','SarahRafferty@win.com'),
('Gina Torres','GinaTorres@att.net');


INSERT INTO categories(name) VALUES
('News'),
('Documentary'),
('Whether'),
('Entertainment');


INSERT INTO genres(name) VALUES
('Fantasy'),
('Comedy'),
('Sci-fi'),
('Action'),
('Horror'),
('Adventure'),
('Epic space-opera'),
('Legal drama');


INSERT INTO series(name) VALUES
('suits'),
('lucifer'),
('cursed'),
('after life');


INSERT INTO seasons(seasonnumber, seriesid) VALUES
(1,1),
(1,2);


-- SuperUsers
INSERT INTO superUsers(isAdmin, userName, password) VALUES
(true,'admin','admin'),
(false,'suits','1234'),
(false,'lucifer','1234'),
(false,'lucasfilms','jedi'),
(false,'dreamworks','1234'),
(false,'marvel','ragnarok');


-- Tv-Series
INSERT INTO productions(episodeNumber, type, categoryID, seasonID, producerID, productionTitle) VALUES
(1,'tv-series',4,1,1,'Pilot part 1 & 2'),
(2,'tv-series',4,1,1,'Errors and omissions'),
(1,'tv-series',3,2,2,'Pilot episode'),
(2,'tv-series',3,2,2,'Lucifer, stay. good devil.');


-- Movies
INSERT INTO productions(episodeNumber, type, categoryID, producerID, productionTitle) VALUES
(1,'movie',4,5,'Bee Movie'),
(1,'movie',4,4,'Star Wars Episode I: The Phantom Menace'),
(2,'movie',4,4,'Star Wars Episode II: Attack Of The Clones'),
(3,'movie',4,4,'Star Wars Episode III: Revenge Of The Sith'),
(2,'movie',4,6,'Spider-Man: Far From Home');


INSERT INTO productionGenres(productionID, genreID) VALUES
(1, 1),
(2, 2),
(3,1),
(4,8),
(5,3),
(5,4),
(6,7),
(7,7),
(8,7);


INSERT INTO castMembers(productionID, role, artistID) VALUES
-- Bee movie
(5,'Actor',23),
(5,'Producer',24),
(5,'Producer',25),

-- Spiderman
(9,'Actor',1),
(9,'Actor',2),
(9,'Actor',3),
(9,'Actor',4),
(9,'Actor',5),
(9,'Actor',6),
(9,'Actor',7),
(9,'Actor',8),
(9,'Director',9),
(9,'Producer',10),

-- suits ep 1
(1,'Actor',33),
(1,'Actor',34),
(1,'Actor',35),
(1,'Actor',36),
(1,'Actor',37),
(1,'Actor',38),
(1,'Producer',32),

-- suits ep 2
(2,'Actor',33),
(2,'Actor',34),
(2,'Actor',35),
(2,'Actor',36),
(2,'Actor',37),
(2,'Actor',38),
(2,'Producer',32),

-- lucifer ep 1
(3,'Actor',27),
(3,'Actor',28),
(3,'Actor',29),
(3,'Actor',30),
(3,'Actor',31),
(3,'Producer',26),

-- lucifer ep 2
(4,'Actor',27),
(4,'Actor',28),
(4,'Actor',29),
(4,'Actor',30),
(4,'Actor',31),
(4,'Producer',26),

-- Star wars episode I
(6,'Director',11),
(6,'Producer',12),
(6,'Actor',13),
(6,'Actor',14),
(6,'Actor',15),
(6,'Actor',16),
(6,'Actor',17),
(6,'Actor',18),
(6,'Actor',19),
(6,'Actor',20),
(6,'Actor',21),
(6,'Actor',3),

-- Star wars episode II
(7,'Actor',14),
(7,'Actor',15),
(7,'Actor',17),
(7,'Actor',18),
(7,'Actor',19),
(7,'Actor',20),
(7,'Actor',21),
(7,'Actor',22),
(7,'Actor',3),
(7,'Director',11),
(7,'Producer',12),

-- Star wars episode III
(8,'Director',11),
(8,'Producer',12),
(8,'Actor',14),
(8,'Actor',15),
(8,'Actor',17),
(8,'Actor',18),
(8,'Actor',19),
(8,'Actor',21),
(8,'Actor',22),
(8,'Actor',3);


-- getCategoryID
CREATE OR REPLACE FUNCTION getCategoryID(nameVal VARCHAR (50))
    RETURNS INTEGER AS $$
DECLARE
    categoryID INTEGER;
BEGIN
    SELECT categories.id INTO categoryID FROM categories
    WHERE categories.name = nameVal;
    RETURN categoryID;
END
$$ LANGUAGE plpgsql;

-- getGenreID
CREATE OR REPLACE FUNCTION getGenreID(nameVal VARCHAR (50))
    RETURNS INTEGER AS $$
DECLARE
    genreID INTEGER;
BEGIN
    SELECT genres.id INTO genreID FROM genres
    WHERE genres.name = nameVal;
    RETURN genreID;
END
$$ LANGUAGE plpgsql;

-- getSeasonID
CREATE OR REPLACE FUNCTION getSeasonID(nameVal VARCHAR (50))
    RETURNS INTEGER AS $$
DECLARE
    seasonID INTEGER;
BEGIN
    SELECT seasons.id INTO seasonID FROM seasons
    WHERE seasons.name = nameVal;
    RETURN seasonID;
END
$$ LANGUAGE plpgsql;

-- getSeriesID

CREATE OR REPLACE FUNCTION getSeriesID(nameVal VARCHAR (50))
    RETURNS INTEGER AS $$
DECLARE
    seriesID INTEGER;
BEGIN
    SELECT series.id INTO seriesID FROM series
    WHERE series.name = nameVal;
    RETURN seriesID;
END
$$ LANGUAGE plpgsql;

-- getArtistID

CREATE OR REPLACE FUNCTION getArtistID(nameVal VARCHAR (50))
    RETURNS INTEGER AS $$
DECLARE
    artistID INTEGER;
BEGIN
    SELECT artists.id INTO artistID FROM artists
    WHERE artists.name = nameVal;
    RETURN artistID;
END
$$ LANGUAGE plpgsql;

-- getProductionID

CREATE OR REPLACE FUNCTION getProductionID(nameVal VARCHAR (50))
    RETURNS INTEGER AS $$
DECLARE
    productionID INTEGER;
BEGIN
    SELECT productions.id INTO productionID FROM productions
    WHERE productions.name = nameVal;
    RETURN productionID;
END
$$ LANGUAGE plpgsql;

-- getProductionsByTitle

CREATE OR REPLACE FUNCTION getProductionsByTitle(search VARCHAR (50))
    RETURNS SETOF productions AS $$
BEGIN
    RETURN QUERY SELECT * FROM productions
    WHERE productions.productionTitle Like search;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getProductionsByTitle(search VARCHAR (50), userID INTEGER)
    RETURNS SETOF productions AS $$
BEGIN
    RETURN QUERY SELECT * FROM productions
    WHERE productions.productionTitle Like search
    AND productions.producerID = userID;
END
$$ LANGUAGE plpgsql;

-- getProductionsBySeries

CREATE OR REPLACE FUNCTION getProductionsBySeries(search VARCHAR (50))
    RETURNS SETOF productions AS $$
BEGIN
    RETURN QUERY SELECT productions.id,productions.episodenumber,productions.type,
                        productions.categoryid,productions.seasonid,
                        productions.producerid,productions.productiontitle FROM productions
        JOIN seasons ON productions.seasonID = seasons.id
        JOIN series ON seasons.seriesID = series.id
        WHERE series.name Like search;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getProductionsBySeries(search VARCHAR (50), userID INTEGER)
    RETURNS SETOF productions AS $$
BEGIN
    RETURN QUERY SELECT productions.id,productions.episodenumber,productions.type,
                        productions.categoryid,productions.seasonid,
                        productions.producerid,productions.productiontitle FROM productions
        JOIN seasons ON productions.seasonID = seasons.id
        JOIN series ON seasons.seriesID = series.id
        WHERE series.name Like search
        AND productions.producerID = userID;
END
$$ LANGUAGE plpgsql;

-- getSuperUserID

CREATE OR REPLACE FUNCTION getSuperUserID(nameVal VARCHAR (50))
    RETURNS INTEGER AS $$
DECLARE
    superUserID INTEGER;
BEGIN
    SELECT superUsers.id INTO superUserID FROM superUsers
    WHERE superUsers.name = nameVal;
    RETURN superUserID;
END
$$ LANGUAGE plpgsql;

-- GUI data

CREATE OR REPLACE FUNCTION getAllProductions()
    RETURNS SETOF productions
AS $$
BEGIN
    RETURN QUERY SELECT * FROM productions;
end;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getAllProducerProductions(producerID INTEGER)
    RETURNS SETOF productions AS $$
BEGIN
    RETURN QUERY SELECT * FROM productions
    WHERE productions.producerID = productions.producerID;
end;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getAllSuperUsers()
    RETURNS SETOF superUsers
AS $$
BEGIN
    RETURN QUERY SELECT * FROM superUsers;
end;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getAllArtists()
    RETURNS SETOF artists
AS $$
BEGIN
    RETURN QUERY SELECT * FROM artists;
end;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getCastMembers(productionIDVal INTEGER)
    RETURNS TABLE (role VARCHAR(50), name VARCHAR(100), email VARCHAR(100)) AS $$
BEGIN
    RETURN QUERY SELECT castMembers.role, artists.name, artists.email FROM castMembers
    JOIN artists ON castMembers.artistID = artists.id
    WHERE castMembers.productionID = productionIDVal;
end;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getSeriesAndProductionID()
    RETURNS TABLE (series varchar(50), id INTEGER)
AS $$
BEGIN
    RETURN QUERY SELECT DISTINCT(series.name), productions.id
FROM productions JOIN seasons ON productions.seasonID = seasons.id
	JOIN series ON seasons.seriesID = series.id;
END; $$
LANGUAGE plpgsql;


-- ENDFILE is used as a delimiter when parsing this file in the ScriptRunner. Otherwise, ScriptRunner doesn't know where the file ends.

ENDFILE