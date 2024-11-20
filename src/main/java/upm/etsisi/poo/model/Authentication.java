package upm.etsisi.poo.model;

import upm.etsisi.poo.controller.AdminsController;
import upm.etsisi.poo.controller.PlayersController;

public class Authentication {
    private static final String ATTR_CURRENTUSER_NAME = "currentUser";
    private static final String ATTR_USERTYPE_NAME = "userType";
    private static Authentication uniqueInstance;
    private User currentUser;
    private UserType userType;

    public enum UserType {
        ADMIN, PLAYER
    }

    private Authentication() {
        currentUser = null;
        userType = null;
    }

    public static Authentication getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Authentication();
        }
        return uniqueInstance;
    }

    public boolean logIn(AdminsController admins, PlayersController players, String username, String password) {
        boolean result = false;
        if (!isLoggedIn()) {
            if (admins.getAdmin(username) != null) {
                if (admins.getAdmin(username).getPassword().equals(password)) {
                    this.currentUser = admins.getAdmin(username);
                    userType = UserType.ADMIN;
                    result = true;
                }
            } else {
                if (players.getPlayer(username) != null) {
                    if (players.getPlayer(username).getPassword().equals(password)) {
                        this.currentUser = players.getPlayer(username);
                        userType = UserType.PLAYER;
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    public void logOut() {
        userType = null;
        currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String getUserType() {
        if (userType != null) return userType.name();
        else return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
