public class Authentication {


    private UserType userType;

    private String username;

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
                    this.username = username;
                    userType = UserType.ADMIN;
                    result = true;
                }
            } else {
                if (players.getPlayer(username) != null) {
                    if (players.getPlayer(username).getPassword().equals(password)) {
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

}
