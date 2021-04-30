
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
    producerID INTEGER REFERENCES superUsers(id)
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

-- POPULATE

