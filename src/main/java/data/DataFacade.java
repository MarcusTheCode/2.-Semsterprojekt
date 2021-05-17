package data;

import domain.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>DataFacade</h1>
 * This class functions as a bridge between the domain layer and persistence layer
 * in order to funnel method calls
 */

public class DataFacade {

    private static DatabaseManager databaseManager = new DatabaseManager();

    /**
     * This method is used to retrieve a production from the database, given an ID.
     * @param productionID The ID of the production
     * @return Production Returns the production with the ID or null.
     */
    public static Production getProduction(int productionID) {
        return databaseManager.getProduction(productionID);
    }

    /**
     * This method is used insert a new production into the database.
     * @param production The production to insert into the database
     * @return boolean Returns whether the execution succeeded.
     */
    public static boolean insertProduction(Production production) {
        return databaseManager.insertProduction(production);
    }

    /**
     * This method is used to delete a Production from the database, given an ID.
     * @param productionID The ID of the Production
     * @return boolean Returns whether the execution succeeded.
     */
    public static boolean deleteProduction(int productionID) {
        return databaseManager.deleteProduction(productionID);
    }

    /**
     * This method is used to edit a Production in the database, given an ID.
     * @param production The production to insert into the database
     * @deprecated Not up to date with current codebase.
     */
    public static void editProduction(Production production) {
        databaseManager.updateProduction(production);
    }

    /**
     * This method is used to insert a unique artist into the database.
     * @param artist The artist to insert into the database
     * @return boolean Returns whether the execution succeeded.
     */
    public static boolean insertArtist(Artist artist) {
        return databaseManager.insertArtist(artist);
    }

    public static void insertCastMember(CastMember castMember){
        databaseManager.insertCastMember(castMember);
    }

    /**
     * This method is used to delete an artist from the database.
     * @param artistID The ID of the artist to delete from the database
     * @return boolean Returns whether the execution succeeded.
     */
    public static boolean deleteArtist(int artistID) {
        return databaseManager.deleteArtist(artistID);
    }

    /**
     * This method is used to edit an Artist.
     * @param artist The Artist to edit
     * @return boolean Returns whether the execution succeeded.
     */
    public static boolean editArtist(Artist artist) {
        return databaseManager.editArtist(artist);
    }

    /**
     * This method is used to retrieve a list of artists from the database.
     * @return ArrayList<Artist> Returns all artists from the database.
     */
    public static ArrayList<Artist> getArtists() {
        return databaseManager.getArtists();
    }

    public static Artist getArtist(String email){
        return databaseManager.getArtist(email);
    }

    public static Artist getArtist(int artistID){
        return databaseManager.getArtist(artistID);
    }

    /**
     * This method is used in the persistence tests to retrieve the ID in a non-hacky way
     * @return HashMap with artists name as key and ID as value
     */
    public static HashMap<String,Integer> getArtistsMap(){
        return databaseManager.getArtistsMap();
    }

    /**
     * This method is used to retrieve a SuperUser from the database, given an ID.
     * @param userID The ID of the SuperUser
     * @return SuperUser Returns the SuperUser with the ID or null.
     */
    public static SuperUser getSuperUser(int userID) {
        return databaseManager.getSuperUser(userID);
    }

    /**
     * This method is used to retrieve a SuperUser from the database, given a name.
     * @param superUserUsername The username of the SuperUser
     * @return SuperUser Returns the SuperUser with the username or null.
     */
    public static SuperUser getSuperUser(String superUserUsername) {
        return databaseManager.getSuperUser(superUserUsername);
    }

    /**
     * This method is used insert a new SuperUser into the database.
     * @param superUser The SuperUser to insert into the database
     * @return boolean Returns whether the execution succeeded.
     */
    public static boolean insertSuperUser(SuperUser superUser) {
        return databaseManager.insertSuperUser(superUser);
    }

    /**
     * This method is used to delete a SuperUser from the database, given an ID.
     * @param userID The ID of the SuperUser
     * @return boolean Returns whether the execution succeeded.
     */
    public static boolean deleteSuperUser(int userID) {
        return databaseManager.deleteSuperUser(userID);
    }

    /**
     * This method is used to edit a SuperUser
     * @param superUserUsername the username of the SuperUser that should be edited
     * @param newSuperUser what the SuperUser with the ID SuperUserID should be edited to
     */
    public static void editSuperUser(String superUserUsername,SuperUser newSuperUser){
        databaseManager.updateSuperUser(superUserUsername,newSuperUser);
    }

    /**
     * This method is used to retrieve all SuperUsers from the database.
     * @return ArrayList<SuperUser> Returns a list of all SuperUsers.
     */
    public static ArrayList<SuperUser> getUsers() {
        return databaseManager.getSuperUsers();
    }

    /**
     * This method is used in the persistence tests to retrieve the ID in a non-hacky way
     * @return HashMap with superUser name as key and ID as value
     */
    public static HashMap<String,Integer> getSuperUsersMap(){ return databaseManager.getSuperUsersMap();}

    /**
     * This method is used to retrieve the SuperUser with the given username, if the passwords match.
     * @param inputUsername The username to check for
     * @param inputPassword The password of the user to match against
     * @return SuperUser Returns the SuperUser or null, if incorrect.
     */
    public static SuperUser login(String inputUsername, String inputPassword) {
        return databaseManager.checkIfUserExists(inputUsername, inputPassword);
    }

    /**
     * This method is used to retrieve all productions from the database.
     * @return ArrayList<Season> Returns a list of all productions.
     */
    public static ArrayList<Season> getSeasons(int seriesID) {
        return databaseManager.getSeasons(seriesID);
    }

    /**
     * This method is used to retrieve all productions from the database.
     * @return ArrayList<Series> Returns a list of all productions.
     */
    public static Season getSeason(int episodeNumber, int seriesID) {
        return databaseManager.getSeason(episodeNumber, seriesID);
    }

    /**
     * This method is used to retrieve all productions from the database.
     */
    public static boolean insertSeries(Series series) {
        return databaseManager.insertSeries(series);
    }

    /**
     * This method is used to retrieve all productions from the database.
     */
    public static boolean insertSeason(Season season) {
        return databaseManager.insertSeason(season);
    }

    /**
     * This method is used to retrieve all productions from the database.
     * @return ArrayList<Series> Returns a list of all productions.
     */
    public static Series getSeries(String name) {
        return databaseManager.getSeries(name);
    }

    /**
     * This method is used to retrieve all productions from the database.
     * @return ArrayList<Series> Returns a list of all productions.
     */
    public static Series getSeriesBySeason(int seasonID) {
        return databaseManager.getSeriesBySeason(seasonID);
    }

    /**
     * This method is used to retrieve all productions from the database.
     * @return ArrayList<Series> Returns a list of all productions.
     */
    public static ArrayList<Series> getAllSeries() {
        return databaseManager.getAllSeries();
    }

    /**
     * This method is used to retrieve all productions from the database.
     * @return ArrayList<Production> Returns a list of all productions.
     */
    public static ArrayList<Production> loadAllProductions() {
        return databaseManager.getAllProductions();
    }

    /**
     * This method is used in the persistence tests to retrieve the ID in a non-hacky way
     * @return HashMap with production name as key and ID as value
     */
    public static HashMap<String,Integer> getProductionsMap(){ return databaseManager.getProductionsMap();}

    /**
     * This method is used to retrieve the season number.
     * @param ID The ID of the season
     * @return int Returns a season number.
     */
    public static int getSeasonNumber(int ID) {
        return databaseManager.getSeasonNumber(ID);
    }

    public static boolean insertGenre(Production production, Genre genre) {
        return databaseManager.insertGenre(production, genre);
    }

    public static boolean deleteGenre(Production production, Genre genre) {
        return databaseManager.deleteGenre(production, genre);
    }

    /**
     * This method is used to retrieve all genres in a production from the database.
     * @param productionID The ID of the production
     * @return ArrayList<String> Returns a list of all genres in the production.
     */
    public static ArrayList<Genre> getGenres(int productionID) {
        return databaseManager.getGenres(productionID);
    }

    /**
     * This method is used to retrieve all genres in a production from the database.
     * @return ArrayList<String> Returns a list of all genres in the production.
     */
    public static ArrayList<Genre> getAllGenres() {
        return databaseManager.getAllGenres();
    }

    public static int getCategory(String name){
        return databaseManager.getCategoryID(name);
    }

    public static String getCategory(int id){
        return databaseManager.getCategoryID(id);
    }

    public static int getCategoryID(Production production){
        return databaseManager.getCategoryID(production);
    }

    public static boolean saveCastMember(CastMember castMember) {
        return databaseManager.insertCastMember(castMember);
    }

    public static boolean deleteCastMember(CastMember castMember) {
        return databaseManager.deleteCastMember(castMember);
    }

    public static boolean castMemberExists(CastMember castMember){
        return databaseManager.chekIfCastMemberExists(castMember);
    }

    public static ArrayList<CastMember> getCastMembers(int productionID) {
        return databaseManager.getCastMembers(productionID);
    }

    public static ArrayList<String> getSeriesAndProductionID(){
        return databaseManager.getSeriesAndProductionID();
    }

    public boolean changePassword(SuperUser user){
        return databaseManager.changePassword(user);
    }

    public boolean removeAdminStatus(SuperUser user){
        return databaseManager.changeAdminStatus(user);
    }

    public boolean changeEmail(Artist artist){
        return databaseManager.editArtist(artist);
    }

    public boolean changeCastMemberRole(CastMember castMember){
        return databaseManager.changeCastMemberRole(castMember);
    }

    public static void saveUserChanges(SuperUser superUser) {
        databaseManager.changePassword(superUser);
        databaseManager.changeAdminStatus(superUser);
        databaseManager.changeUsername(superUser);
    }
    public static void saveArtistChanges(Artist artist) {
        databaseManager.editArtistName(artist);
        databaseManager.editArtistEmail(artist);
    }
}