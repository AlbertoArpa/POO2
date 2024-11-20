public class Control {
    //Clase Facade
    private static final UserLogic USER_LOGIC = new UserLogic();
    private static final TournamentLogic TOURNAMENT_LOGIC = new TournamentLogic();

    public static void initialUsers(){
        USER_LOGIC.initialUsers();
    }

    public static boolean logIn(String username, String password){
        return USER_LOGIC.logIn(username, password);
    }

    public static String tournamentList(){
        return TOURNAMENT_LOGIC.tournamentList();
    }

    public static boolean playerCreate(String username, String password, String name, String surname, String dni){
        return USER_LOGIC.playerCreate(username, password, name, surname, dni);
    }

    public static boolean teamCreate(String name){
        return TOURNAMENT_LOGIC.teamCreate(name);
    }

    public static boolean playerDelete(String username){
        return TOURNAMENT_LOGIC.playerDelete(username);
    }

    public static boolean teamDelete(String name){
        return TOURNAMENT_LOGIC.teamDelete(name);
    }

    public static boolean addPoints(String username, String stat, double points){
        return USER_LOGIC.addPoints(username, stat, points);

    }
    public static boolean teamAdd(String username, String team){
        return TOURNAMENT_LOGIC.teamAdd(username, team);
    }

    public static boolean teamRemove(String username, String team){
        return TOURNAMENT_LOGIC.teamRemove(username, team);
    }

    public static boolean tournamentCreate(String name, String startDate, String endDate, String league, String sport, String categoryRank){
        return TOURNAMENT_LOGIC.tournamentCreate(name, startDate, endDate, league, sport, categoryRank);
    }

    public static boolean tournamentDelete(String name){
        return TOURNAMENT_LOGIC.tournamentDelete(name);
    }

    public static boolean tournamentMatchmakingM(String name, String participant1, String participant2){
        return TOURNAMENT_LOGIC.tournamentMatchmakingM(name, participant1, participant2);
    }

    public static boolean tournamentMatchmakingA(String name){
        return TOURNAMENT_LOGIC.tournamentMatchmakingA(name);
    }

    public static boolean tournamentAdd(String tournament, String team){
        return TOURNAMENT_LOGIC.tournamentAdd(tournament, team);
    }

    public static boolean tournamentRemove(String tournament, String team){
        return TOURNAMENT_LOGIC.tournamentRemove(tournament, team);
    }

    public static boolean statisticsShow(String option){
        return USER_LOGIC.statisticsShow(option);
    }
}
