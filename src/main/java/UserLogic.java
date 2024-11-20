public class UserLogic {
    private static final AdminsController ADMINS_CONTROLLER = AdminsController.getInstance();
    private static final PlayersController PLAYERS_CONTROLLER = PlayersController.getInstance();
    private static final TeamsController TEAMS_CONTROLLER = TeamsController.getInstance();
    private static final Authentication AUTHENTICATION = Authentication.getInstance();

    public void initialUsers() {
        ADMINS_CONTROLLER.addAdmin(new Admin("a.arpa", "4321"));
        ADMINS_CONTROLLER.addAdmin(new Admin("javier", "1234"));
        ADMINS_CONTROLLER.addAdmin(new Admin("adrian", "1432"));
    }

    public boolean logIn(String username, String password) {
        return AUTHENTICATION.logIn(ADMINS_CONTROLLER, PLAYERS_CONTROLLER, username, password);
    }

    public boolean playerCreate(String username, String password, String name, String surname, String dni) {
        if (ADMINS_CONTROLLER.getAdmin(username) == null && PLAYERS_CONTROLLER.getPlayer(username) == null && TEAMS_CONTROLLER.getTeam(username) == null) {
            return PLAYERS_CONTROLLER.createPlayer(username, password, name, surname, dni, (Admin) AUTHENTICATION.getCurrentUser());
        } else return false;
    }

    public boolean addPoints(String username, String stat, double points){
        return PLAYERS_CONTROLLER.addPoints(username, stat, points);
    }

    public boolean statisticsShow(String option){
        if (option.equals("-csv")){
            ((Player) AUTHENTICATION.getCurrentUser()).showStatsCsv();
            return true;
        } else if (option.equals("-json")) {
            ((Player) AUTHENTICATION.getCurrentUser()).showStatsJson();
            return true;
        } else return false;
    }
}