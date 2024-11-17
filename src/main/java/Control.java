public class Control {
    //Clase Facade
    private static final UserLogic userLogic = new UserLogic();
    private static final TournamentLogic tournamentLogic = new TournamentLogic();

    public static void initialUsers(){
        userLogic.initialUsers();
    }

    public static boolean logIn(String username, String password){
        return userLogic.logIn(username, password);
    }

    public static String tournamentList(){
        return tournamentLogic.tournamentList();
    }

    public static boolean playerCreate(String username, String password, String name, String surname, String dni){
        return userLogic.playerCreate(username, password, name, surname, dni);
    }

    public static boolean teamCreate(String name){
        return tournamentLogic.teamCreate(name);
    }

    public static boolean playerDelete(String username){
        return tournamentLogic.playerDelete(username);
    }

    public static boolean teamDelete(String name){
        return tournamentLogic.teamDelete(name);
    }

    public static boolean teamAdd(String username, String team){
        return tournamentLogic.teamAdd(username, team);
    }

    public static boolean teamRemove(String username, String team){
        return tournamentLogic.teamRemove(username, team);
    }

    public static boolean tournamentCreate(String name, String startDate, String endDate, String league, String sport, String categoryRank){
        return tournamentLogic.tournamentCreate(name, startDate, endDate, league, sport, categoryRank);
    }

    public static boolean tournamentDelete(String name){
        return tournamentLogic.tournamentDelete(name);
    }

    public static boolean tournamentMatchmakingM(String name, String participant1, String participant2){
        return tournamentLogic.tournamentMatchmakingM(name, participant1, participant2);
    }

    public static boolean tournamentMatchmakingA(String name){
        return tournamentLogic.tournamentMatchmakingA(name);
    }

    public static boolean tournamentAdd(String tournament, String team){
        return tournamentAdd(tournament, team);
    }

    public static boolean tournamentRemove(String tournament, String team){
        return tournamentRemove(tournament, team);
    }

    public static boolean statisticsShow(String option){
        return userLogic.statisticsShow(option);
    }
}
