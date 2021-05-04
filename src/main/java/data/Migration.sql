
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
    name VARCHAR(100) NOT NULL,
    seasonsCount INTEGER
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
    name VARCHAR(100) NOT NULL
);

CREATE TABLE castMembers (
    productionID INTEGER REFERENCES productions(id),
    role VARCHAR(50) NOT NULL,
    artistID INTEGER REFERENCES artists(id)
);


-- load test data
INSERT INTO categories(name) VALUES ('News');
INSERT INTO categories(name) VALUES ('Movie');
INSERT INTO categories(name) VALUES ('Whether');
INSERT INTO categories(name) VALUES ('Fantasy');



-- getCategoryID
CREATE OR REPLACE FUNCTION getCategoryID(nameVal VARCHAR (50))
    RETURNS INTEGER AS $categoryID$
DECLARE
    categoryID INTEGER;
BEGIN
    SELECT categories.id INTO categoryID FROM categories
    WHERE categories.name = nameVal;
    RETURN categoryID;
END
$categoryID$ LANGUAGE plpgsql;

-- getGenreID
CREATE OR REPLACE FUNCTION getGenreID(nameVal VARCHAR (50))
    RETURNS INTEGER AS $genreID$
DECLARE
    genreID INTEGER;
BEGIN
    SELECT genres.id INTO genreID FROM genres
    WHERE genres.name = nameVal;
    RETURN genreID;
END
$genreID$ LANGUAGE plpgsql;