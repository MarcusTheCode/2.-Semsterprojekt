package data;

import domain.Artist;
import domain.CastMember;
import domain.Production;
import domain.SuperUser;

import java.util.ArrayList;

public class DataFacade {

    private static DatabaseManager databaseManager = new DatabaseManager();
    //private static DataManager dataManager = new DataManager();

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

    public static Artist getArtist(String name){
        return databaseManager.getArtist(name);
    }

    public static Artist getArtist(int artistID){
        return databaseManager.getArtist(artistID);
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
     * This method is used to retrieve all SuperUsers from the database.
     * @return ArrayList<SuperUser> Returns a list of all SuperUsers.
     */
    public static ArrayList<SuperUser> getUsers() {
        return databaseManager.getSuperUsers();
    }

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
     * @return ArrayList<Production> Returns a list of all productions.
     */
    public static ArrayList<Production> loadAllProductions() {
        return databaseManager.getAllProductions();
    }

    public static int getSeasonNumber(int ID){
        return databaseManager.getSeasonNumber(ID);
    }

    public static ArrayList<String> getGenres(int productionID){
        return databaseManager.getGenres(productionID);
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
}