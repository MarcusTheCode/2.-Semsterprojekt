package domain;

import data.DataFacade;

import java.util.ArrayList;

/**
 * <h1>DomainFacade</h1>
 * This class functions as a bridge between the presentation layer and domain layer
 * in order to funnel method calls
 */

public class DomainFacade {

    private static System system = new System();

    //region Production

    /**
     * This method is used to insert a production into the database.
     * @param production The production to save
     */
    public static void insertProduction(Production production) {
        system.insertProduction(production);
    }

    /**
     * This method is used to delete a production from the database.
     * @param production The production to delete
     */
    public static void deleteProduction(Production production) {
        system.deleteProduction(production);
    }

    /**
     * This method is used to edit a production in the database.
     * @param production The production to update
     */
    public static void updateProduction(Production production) {
        system.updateProduction(production);
    }

    /**
     * This method is used to retrieve a production with the given ID.
     * @param ID The ID of the production
     * @return Production Returns the production.
     */
    public static Production getProduction(int ID) {
        return system.getProduction(ID);
    }

    //endregion

    //region SuperUser

    /**
     * This method is used to create a SuperUser and save it in the database.
     * @param password The password for this user
     * @param username The name of the SuperUser
     * @param sysAdmin Whether this SuperUser is a system administrator
     * @return SuperUser Returns the newly created SuperUser.
     */
    public static SuperUser createUser(String password, String username, boolean sysAdmin) {
        return system.createUser(password, username, sysAdmin);
    }

    public static void saveUserChanges(SuperUser superUser) {
        system.updateSuperUser(superUser);
    }

    /**
     * This method is used to delete a SuperUser from the database.
     * @param userID The ID of the SuperUser
     */
    public static void deleteSuperUser(int userID) {
        system.deleteSuperUser(userID);
    }

    /**
     * This method is used to create a unique artist in the database.
     * @return ArrayList<SuperUser> Returns a list of all SuperUsers.
     */
    public static ArrayList<SuperUser> getUsers() {
        return system.getUsers();
    }

    /**
     * This method is used to retrieve the currently logged in SuperUser.
     * @return SuperUser Returns the current SuperUser or null.
     */
    public static SuperUser getCurrentUser() {
        return system.getCurrentUser();
    }

    /**
     * This method is used to log the user in.
     * @param inputUsername The username to login as
     * @param inputPassword The password to match against
     * @return boolean Whether the username and password match.
     */
    public static boolean login(String inputUsername, String inputPassword) {
        return system.login(inputUsername, inputPassword);
    }

    /**
     * This method is used to log the user out of the system.
     */
    public static boolean logout() {
        return system.logout();
    }

    //endregion

    //region Artist

    /**
     * This method is used to create a unique artist in the database.
     * @param name The name of the artist to insert into the database
     * @return Artist Returns the newly created artist.
     */
    public static Artist createArtist(String name, String email) {
        return system.createArtist(name, email);
    }

    /**
     * This method is used to delete an artist from the database.
     * @param artistID The ID of the artist to delete from the database
     */
    public static void deleteArtist(int artistID) {
        system.deleteArtist(artistID);
    }

    /**
     * This method is used to edit an Artist.
     * @param artist The Artist to edit
     */
    public static void updateArtist(Artist artist) {
        system.updateArtist(artist);
    }

    public static void saveArtistChanges(Artist artist) {
        system.saveArtistChanges(artist);
    }

    /**
     * This method is used to retrieve a list of artists from the database.
     * @return ArrayList<Artist> Returns all artists from the database.
     */
    public static ArrayList<Artist> getArtists() {
        return DataFacade.getArtists();
    }

    //endregion

    //region Series

    /**
     * This method is used to create a series and save it in the database.
     * @param seriesName The name of the series
     */
    public static boolean createSeries(String seriesName) {
        return system.createSeries(seriesName);
    }

    /**
     * This method is used to retrieve all productions from the database.
     * @return ArrayList<Series> Returns a list of all productions.
     */
    public static Series getSeries(String name) {
        return DataFacade.getSeries(name);
    }

    /**
     * This method is used to retrieve all productions from the database.
     * @return ArrayList<Series> Returns a list of all productions.
     */
    public static Series getSeriesBySeason(int seasonID) {
        return DataFacade.getSeriesBySeason(seasonID);
    }

    /**
     * This method is used to retrieve all productions from the database.
     * @return ArrayList<Series> Returns a list of all productions.
     */
    public static ArrayList<Series> getAllSeries() {
        return DataFacade.getAllSeries();
    }

    //endregion

    //region Genre

    public static boolean insertGenre(Production production, Genre genre) {
        return system.insertGenre(production, genre);
    }

    public static boolean deleteGenre(Production production, Genre genre) {
        return system.deleteGenre(production, genre);
    }

    /**
     * This method is used to retrieve all genres in a production from the database.
     * @return ArrayList<String> Returns a list of all genres in the production.
     */
    public static ArrayList<Genre> getAllGenres() {
        return DataFacade.getAllGenres();
    }

    //endregion

    //region Category

    public static ArrayList<String> getAllCategories() {
        return DataFacade.getAllCategories();
    }

    //endregion

    //region Season

    /**
     * This method is used to create a season and save it in the database.
     * @param seasonNumber The season number
     * @param seriesID The series ID
     * @return boolean Whether the execution was successful.
     */
    public static boolean createSeason(int seasonNumber, int seriesID) {
        return system.createSeason(seasonNumber, seriesID);
    }

    /**
     * This method is used to retrieve all productions from the database.
     * @return ArrayList<Series> Returns a list of all productions.
     */
    public static Season getSeason(int episodeNumber, int seriesID) {
        return DataFacade.getSeason(episodeNumber, seriesID);
    }

    /**
     * This method is used to retrieve all productions from the database.
     * @return ArrayList<Season> Returns a list of all productions.
     */
    public static ArrayList<Season> getSeasons(int seriesID) {
        return DataFacade.getSeasons(seriesID);
    }

    //endregion

    //region CastMember

    public static boolean insertCastMember(CastMember castMember) {
        return system.insertCastMember(castMember);
    }

    public static boolean deleteCastMember(CastMember castMember) {
        return system.deleteCastMember(castMember);
    }

    public static boolean castMemberExists(CastMember castMember){
        return DataFacade.castMemberExists(castMember);
    }

    public static ArrayList<CastMember> getCastMembers(int productionID) {
        return DataFacade.getCastMembers(productionID);
    }

    //endregion
}

