public class UserLogic {
    private final AdminsController adminsController = AdminsController.getInstance();
    private final PlayersController playersController = PlayersController.getInstance();
    private final TeamsController teamsController = TeamsController.getInstance();
    private final Authentication authentication = Authentication.getInstance();

    public void initialUsers() {
        adminsController.addAdmin(new Admin("a.arpa", "4321"));
        adminsController.addAdmin(new Admin("javier", "1234"));
        adminsController.addAdmin(new Admin("adrian", "1432"));
    }

    public boolean logIn(String username, String password) {
        return authentication.logIn(adminsController, playersController, username, password);
    }

    public boolean playerCreate(String username, String password, String name, String surname, String dni) {
        if (adminsController.getAdmin(username) == null && playersController.getPlayer(username) == null && teamsController.getTeam(username) == null) {
            return playersController.createPlayer(username, password, name, surname, dni, (Admin) authentication.getCurrentUser());
        } else return false;
    }

    public boolean statisticsShow(String option){
        if (option.equals("-csv")){
            ((Player) authentication.getCurrentUser()).showStatsCsv();
            return true;
        } else if (option.equals("-json")) {
            ((Player) authentication.getCurrentUser()).showStatsJson();
            return true;
        } else return false;
    }
}