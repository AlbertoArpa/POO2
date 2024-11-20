package upm.etsisi.poo.model;

import Controller.AdminsController;
import Controller.PlayersController;
import Controller.TeamsController;

public class Validations {

    public static boolean isUsernameValid(String username) {
        return username.matches("^[a-zA-Z0-9]{3,}$"); //explicacion de la expresion regular: que empiece por una letra o numero, que tenga al menos 3 caracteres
    }

    public static boolean isPasswordValid(String password) {
        return password.matches("^[a-zA-Z0-9]{3,}$");
    }

    public static boolean isNameValid(String name) {
        return name.matches("^[a-zA-Z]{3,}$");
    }

    public static boolean isSurnameValid(String surname) {
        return surname.matches("^[a-zA-Z]{3,}$");
    }

    public static boolean isPointsValid(double points) {
        return points >= 0;
    }

    public static boolean isOptionValid(String option) {
        return option.equals("-csv") || option.equals("-json");
    }

    public static boolean isUsernameAvailable(String username) {
        AdminsController adminsController = AdminsController.getInstance();
        PlayersController playersController = PlayersController.getInstance();
        TeamsController teamsController = TeamsController.getInstance();
        return adminsController.getAdmin(username) == null && playersController.getPlayer(username) == null && teamsController.getTeam(username) == null;
    }

    public static boolean isUserLoggedIn() {
        return Authentication.getInstance().getCurrentUser() != null;
    }

    public static boolean isUserAdmin() {
        return Authentication.getInstance().getCurrentUser() instanceof Admin;
    }

    public static boolean isUserPlayer() {
        return Authentication.getInstance().getCurrentUser() instanceof Player;
    }

    public static boolean isUserAdminOrPlayer() {
        return Authentication.getInstance().getCurrentUser() instanceof Admin || Authentication.getInstance().getCurrentUser() instanceof Player;
    }
}
