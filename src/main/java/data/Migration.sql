
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
    name VARCHAR(100) NOT NULL
);

CREATE TABLE seasons (
    id SERIAL PRIMARY KEY,
    seasonNumber INTEGER NOT NULL,
    seriesID INTEGER REFERENCES series(id)
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
    producerID INTEGER REFERENCES superUsers(id),
    productionTitle VARCHAR(150)
);

CREATE TABLE productionGenres (
    productionID INTEGER REFERENCES productions(id),
    genreID INTEGER REFERENCES genres(id)
);

CREATE TABLE artists (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE castMembers (
    productionID INTEGER REFERENCES productions(id),
    role VARCHAR(50) NOT NULL,
    artistID INTEGER REFERENCES artists(id)
);

-- load test data
INSERT INTO categories(name) VALUES ('news');
INSERT INTO categories(name) VALUES ('documentary');
INSERT INTO categories(name) VALUES ('whether');
INSERT INTO categories(name) VALUES ('entertainment');

INSERT INTO genres(name) VALUES ('fantasy');
INSERT INTO genres(name) VALUES ('comedy');
INSERT INTO genres(name) VALUES ('sci-fi');
INSERT INTO genres(name) VALUES ('action');
INSERT INTO genres(name) VALUES ('horror');
INSERT INTO genres(name) VALUES ('adventure');

INSERT INTO series(name) VALUES('suits');
INSERT INTO series(name) VALUES('lucifer');
INSERT INTO series(name) VALUES('cursed');
INSERT INTO series(name) VALUES('after life');

INSERT INTO seasons(seasonnumber, seriesid) VALUES(1,1);
INSERT INTO seasons(seasonnumber, seriesid) VALUES(1,2);

INSERT INTO superUsers(isAdmin, userName, password) VALUES (TRUE,'admin','admin');
INSERT INTO superUsers(isAdmin, userName, password) VALUES (false,'poul','1234');
INSERT INTO superUsers(isAdmin, userName, password) VALUES (false,'john','1234');

INSERT INTO productions(episodeNumber, type, categoryID, seasonID, producerID, productionTitle)
VALUES(1,'tv-series',4,1,2,'pilot part 1 & 2');
INSERT INTO productions(episodeNumber, type, categoryID, seasonID, producerID, productionTitle)
VALUES(2,'tv-series',4,1,2,'errors and omissions');
INSERT INTO productions(episodeNumber, type, categoryID, seasonID, producerID, productionTitle)
VALUES (1,'tv-series',4,2,3,'pilotepisode');
INSERT INTO productions(episodeNumber, type, categoryID, seasonID, producerID, productionTitle)
VALUES (2,'tv-series',4,2,3,'lucifer, stay. good devil.');
INSERT INTO productions(episodeNumber, type, categoryID, producerID, productionTitle)
VALUES (1,'movie',4,3,'bee movie');

INSERT INTO artists(name,email)
VALUES('Barry B. Benson ','BarryBeeBenson@bee.hive');

INSERT INTO castMembers(productionID, role, artistID)
VALUES (5,'Actor',1);

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
    RETURNS TABLE (role VARCHAR(50), name VARCHAR(100)) AS $$
BEGIN
    RETURN QUERY SELECT castMembers.role, artists.name FROM castMembers
    LEFT JOIN artists ON castMembers.artistID = artists.id;
end;
$$ LANGUAGE plpgsql;

-- ENDFILE is used as a delimiter when parsing this file in the ScriptRunner. Otherwise, ScriptRunner doesn't know where the file ends.

ENDFILE