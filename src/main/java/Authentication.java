public class Authentication {


    private UserType userType;

    private String username;

    private User currentUser;

    public enum UserType {
        ADMIN, PLAYER
    }

    public Authentication() {
        userType = null;
        username = null;
    }

    public boolean logIn(AdminsController admins, PlayersController players, String username, String password) {
        boolean result = false;
        if (!isLoggedIn()) {
            if (admins.getAdmin(username) != null) {
                if (admins.getAdmin(username).getPassword().equals(password)) {
                    currentUser = admins.getAdmin(username);
                    this.username = username;
                    userType = UserType.ADMIN;
                    result = true;
                }
            } else {
                if (players.getPlayer(username) != null) {
                    if (players.getPlayer(username).getPassword().equals(password)) {
                        currentUser = players.getPlayer(username);
                        this.username = username;
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
        username = null;
    }

    public boolean isLoggedIn() {
        return username != null;
    }

    public String getUserType() {
        return userType.name();
    }

    public String getUsername() {
        return username;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
