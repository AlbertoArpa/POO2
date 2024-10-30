public class Authentication {
    private enum UserType {
        ADMIN, PLAYER
    }

    private UserType userType;

    private String username;

    public boolean logIn(AdminsController admins, PlayersController players, String username, String password) {
        boolean result = false;
        if (admins.getAdmin(username) != null) {
            this.username = username;
            userType = UserType.ADMIN;
            result = true;
        } else {
            if (players.getPlayer(username) != null) {
                this.username = username;
                userType = UserType.PLAYER;
                result = true;
            }
        }
        return result;
    }

    public void logOut() {
        userType = null;
        username = null;
    }
}
