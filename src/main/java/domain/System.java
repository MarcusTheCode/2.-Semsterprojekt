package domain;

import data.*;

import java.util.ArrayList;

public class System {

    private SuperUser currentUser;

    //region Production

    /**
     * This method is used to insert a production into the database.
     * @param production The production to save
     */
    protected void insertProduction(Production production) {
        DataFacade.insertProduction(production);
    }

    /**
     * This method is used to delete a production from the database.
     * @param production The production to delete
     */
    protected void deleteProduction(Production production) {
        if (production.isOwner(currentUser) || currentUser.isSysAdmin())
            DataFacade.deleteProduction(production.getId());
        else
            throw new RuntimeException("ERROR: user is not allowed to edit");
    }

    /**
     * This method is used to edit a production
     * @param production The production to edit
     */
    protected void updateProduction(Production production) {
        if (production.isOwner(currentUser) || currentUser.isSysAdmin())
            DataFacade.updateProduction(production);
        else
            throw new RuntimeException("User is not allowed to edit this production");
    }

    /**
     * This method is used to retrieve a production with the given ID.
     * @param productionID The ID of the production
     * @return Production Returns the production or null.
     */
    protected Production getProduction(int productionID) {
        return DataFacade.getProduction(productionID);
    }

    /**
     * This method is used to retrieve all productions whose titles matches the search pattern.
     * @param pattern The pattern to search for
     * @return ArrayList<Production> Returns a list of matching productions.
     */
    protected ArrayList<Production> getProductionsByTitle(String pattern) {
        return DataFacade.getProductionsByTitle(pattern, getCurrentUser());
    }

    /**
     * This method is used to retrieve all productions whose series matches the search pattern.
     * @param pattern The pattern to search for
     * @return ArrayList<Production> Returns a list of matching productions.
     */
    protected ArrayList<Production> getProductionsBySeries(String pattern) {
        return DataFacade.getProductionsBySeries(pattern, getCurrentUser());
    }

    //endregion

    //region SuperUser

    /**
     * This method is used to create a SuperUser from the given parameters.
     * @param password The password of the SuperUser
     * @param username The username of the SuperUser
     * @param sysAdmin Whether the SuperUser is a system administrator
     * @return SuperUser Returns the SuperUser.
     */
    public SuperUser createUser(String password, String username, boolean sysAdmin) {
        SuperUser user = new SuperUser(0, password, username, sysAdmin);
        saveSuperUser(user);
        return user;
    }

    /**
     * This method is used to insert a SuperUser into the database.
     * @param superUser The SuperUser to save
     */
    public void saveSuperUser(SuperUser superUser) {
        if (currentUser.isSysAdmin())
            DataFacade.insertSuperUser(superUser);
        else
            throw new RuntimeException("User is not allowed to insert a SuperUser");
    }

    /**
     * This method is used to delete a SuperUser from the database.
     * @param userID The ID of the SuperUser
     */
    public void deleteSuperUser(int userID) {
        if (currentUser.isSysAdmin())
            DataFacade.deleteSuperUser(userID);
        else
            throw new RuntimeException("User is not allowed to delete a SuperUser");
    }

    /**
     * This method is used to update a SuperUser in the database.
     * @param superUser The SuperUser to save
     */
    public void updateSuperUser(SuperUser superUser) {
        if (currentUser.isSysAdmin())
            DataFacade.updateSuperUser(superUser);
        else
            throw new RuntimeException("User is not allowed to update a SuperUser");
    }

    /**
     * This method is used to create a unique artist in the database.
     * @return ArrayList<SuperUser> Returns a list of all SuperUsers.
     */
    public ArrayList<SuperUser> getUsers() {
        return DataFacade.getUsers();
    }

    /**
     * This method is used to retrieve the currently logged in SuperUser.
     * @return SuperUser Returns the current SuperUser or null if not logged in.
     */
    public SuperUser getCurrentUser() {
        return currentUser;
    }

    /**
     * This method is used to log the user in.
     * @param inputUsername The username to login as
     * @param inputPassword The password to match against
     * @return boolean Whether the username and password match.
     */
    public boolean login(String inputUsername, String inputPassword)  {
        currentUser = DataFacade.login(inputUsername, inputPassword);
        return currentUser != null;
    }

    /**
     * This method is used to log the user out of the system.
     */
    public boolean logout() {
        if (currentUser != null) {
            currentUser = null;
            return true;
        }
        return false;
    }

    //endregion

    //region Artist

    /**
     * This method is used to create a unique artist in the database.
     * @param name The name of the artist to insert into the database
     * @return Artist Returns the newly created artist.
     */
    public Artist createArtist(String name, String email) {
        if (currentUser != null) {
            Artist artist = new Artist(name, email);
            DataFacade.insertArtist(artist);
            return artist;
        }
        return null;
    }

    /**
     * This method is used to delete an artist from the database.
     * @param artistID The ID of the artist to delete from the database
     */
    public boolean deleteArtist(int artistID) {
        if (currentUser != null)
            return DataFacade.deleteArtist(artistID);
        return false;
    }

    /**
     * This method is used to edit an Artist.
     * @param artist The Artist to edit
     */
    public void updateArtist(Artist artist) {
        if (currentUser != null)
            DataFacade.updateArtist(artist);
    }

    public void saveArtistChanges(Artist artist) {
        if (currentUser != null)
            DataFacade.updateArtist(artist);
        else
            throw new RuntimeException("User is not allowed to update an artist");
    }

    //endregion

    //region Series

    /**
     * This method is used to create a series and save it in the database.
     * @param seriesName The name of the series
     */
    public boolean createSeries(String seriesName) {
        Series series = new Series(seriesName);
        return DataFacade.insertSeries(series);
    }

    //endregion

    //region Genre

    /**
     * This method is used to insert a genre into a production and save it in the database.
     * @param production The production
     * @param genre The genre to add
     * @return boolean Whether the execution was successful.
     */
    public boolean insertGenre(Production production, Genre genre) {
        return DataFacade.insertGenre(production, genre);
    }

    /**
     * This method is used to delete a genre from a production and save it in the database.
     * @param production The production
     * @param genre The genre to remove
     * @return boolean Whether the execution was successful.
     */
    public boolean deleteGenre(Production production, Genre genre) {
        return DataFacade.deleteGenre(production, genre);
    }

    //endregion

    //region Category

    /**
     * This method is used to create a season and save it in the database.
     * @param seasonNumber The season number
     * @param seriesID The series ID
     * @return boolean Whether the execution was successful.
     */
    public boolean createSeason(int seasonNumber, int seriesID) {
        if (currentUser != null) {
            Season season = new Season(seasonNumber, seriesID);
            return DataFacade.insertSeason(season);
        }
        return false;
    }

    //endregion

    //region CastMember

    public boolean insertCastMember(CastMember castMember) {
        // TODO: Check if user is owner
        if (currentUser != null)
            return DataFacade.insertCastMember(castMember);
        return false;
    }

    public boolean deleteCastMember(CastMember castMember) {
        // TODO: Check if user is owner
        if (currentUser != null)
            return DataFacade.deleteCastMember(castMember);
        return false;
    }

    //endregion
}
