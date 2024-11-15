public class Control {
    //Clase Facade
    private static final AdminsController adminsController = AdminsController.getInstance();
    private static final TournamentsController tournamentsController = TournamentsController.getInstance();
    private static final PlayersController playersController = PlayersController.getInstance();
    private static final TeamsController teamsController = TeamsController.getInstance();
    private static final Authentication authentication = Authentication.getInstance();

    public static void initialUsers(){
        adminsController.addAdmin(new Admin("a.arpa", "4321"));
        adminsController.addAdmin(new Admin("javier", "1234"));
        adminsController.addAdmin(new Admin("adrian", "1432"));
    }

    public static boolean logIn(String username, String password){
        return authentication.logIn(adminsController, playersController, username, password);
    }

    public static String tournamentList(){
        return tournamentsController.listTournaments(authentication.getUserType());
    }

    public static boolean playerCreate(String username, String password, String name, String surname, String dni){
        if (adminsController.getAdmin(username)==null && playersController.getPlayer(username)==null && teamsController.getTeam(username)==null){
            return playersController.createPlayer(username, password, name, surname, dni, (Admin) authentication.getCurrentUser());
        } else return false;
    }

    public static boolean teamCreate(String name){
        if (teamsController.getTeam(name)==null && playersController.getPlayer(name)==null){
            return teamsController.createTeam(name, (Admin) authentication.getCurrentUser());
        } else return false;
    }

    public static boolean playerDelete(String username){
        if (playersController.getPlayer(username)!=null){
            if (teamsController.isInTeam(username)!=null){
                if (tournamentsController.isParticipant(teamsController.isInTeam(username))==null){
                    teamsController.isInTeam(username).removePlayer(username);
                    return playersController.deletePlayer(username);
                } else return false;
            } else if (tournamentsController.isParticipant(playersController.getPlayer(username))==null) {
                return playersController.deletePlayer(username);
            } else return false;
        } else return false;
    }

    public static boolean teamDelete(String name){
        if (teamsController.getTeam(name)!=null){
            if (tournamentsController.isParticipant(teamsController.getTeam(name))==null){
                return teamsController.deleteTeam(name);
            } else return false;
        } else return false;
    }

    public static boolean teamAdd(String username, String team){
        if (playersController.getPlayer(username)!=null){
            if (teamsController.getTeam(team)!=null){
                if (teamsController.isInTeam(username)==null){
                    return teamsController.getTeam(team).addPlayer(playersController.getPlayer(username));
                } else return false;
            } else return false;
        } else return false;
    }

    public static boolean teamRemove(String username, String team){
        if (playersController.getPlayer(username)!=null){
            if (teamsController.getTeam(team)!=null){
                if (teamsController.isInTeam(username).equals(teamsController.getTeam(team))){
                    return teamsController.getTeam(team).removePlayer(username);
                } else return false;
            } else return false;
        } else return false;
    }

    public static boolean tournamentCreate(String name, String startDate, String endDate, String league, String sport, String categoryRank){
        if (tournamentsController.getTournament(name)==null){
            if (Date.isCorrect(startDate)&&Date.isCorrect(endDate)){
                if (Categories.getCategory(categoryRank)!=null){
                    return tournamentsController.createTournament(name, new Date(startDate), new Date(endDate), league, sport, Categories.getCategory(categoryRank));
                } else return false;
            } else return false;
        } else return false;
    }

    public static boolean tournamentDelete(String name){
        if (tournamentsController.getTournament(name)!=null){
            return tournamentsController.deleteTournament(name);
        } else return false;
    }

    public static boolean tournamentMatchmakingM(String name, String participant1, String participant2){
        if (tournamentsController.getTournament(name)!=null){
            if (playersController.getPlayer(participant1)!=null){
                if (playersController.getPlayer(participant2)!=null){
                    if (tournamentsController.getTournament(name).getParticipant(playersController.getPlayer(participant1)) != null &&
                            tournamentsController.getTournament(name).getParticipant(playersController.getPlayer(participant2)) != null){
                        return tournamentsController.getTournament(name).getMatchmaking().createMatchmake(playersController.getPlayer(participant1), playersController.getPlayer(participant2));
                    } else return false;
                } else if (teamsController.getTeam(participant2)!=null){
                    if (tournamentsController.getTournament(name).getParticipant(playersController.getPlayer(participant1)) != null &&
                            tournamentsController.getTournament(name).getParticipant(teamsController.getTeam(participant2)) != null){
                        return tournamentsController.getTournament(name).getMatchmaking().createMatchmake(playersController.getPlayer(participant1), teamsController.getTeam(participant2));
                    } else return false;
                } else return false;
            } else if (teamsController.getTeam(participant1)!=null){
                if (playersController.getPlayer(participant2)!=null){
                    if (tournamentsController.getTournament(name).getParticipant(teamsController.getTeam(participant1)) != null &&
                            tournamentsController.getTournament(name).getParticipant(playersController.getPlayer(participant2)) != null){
                        return tournamentsController.getTournament(name).getMatchmaking().createMatchmake(teamsController.getTeam(participant1), playersController.getPlayer(participant2));
                    } else return false;
                } else if (teamsController.getTeam(participant2)!=null){
                    if (tournamentsController.getTournament(name).getParticipant(teamsController.getTeam(participant1)) != null &&
                            tournamentsController.getTournament(name).getParticipant(teamsController.getTeam(participant2)) != null){
                        return tournamentsController.getTournament(name).getMatchmaking().createMatchmake(teamsController.getTeam(participant1), teamsController.getTeam(participant2));
                    } else return false;
                } else return false;
            } else return false;
        } else return false;
    }

    public static boolean tournamentMatchmakingA(String name){
        if (tournamentsController.getTournament(name)!=null){
            return tournamentsController.getTournament(name).getMatchmaking().randomMatchmake(tournamentsController.getTournament(name).getRandomizedParticipants());
        } else return false;
    }

    public static boolean tournamentAdd(String tournament, String team){
        if (tournamentsController.getTournament(tournament)!=null){
            if (tournamentsController.getTournament(tournament).getStartDate().greaterThan(new Date())){
                if (team!=null){
                    if (teamsController.getTeam(team)!=null){
                        if (teamsController.isInTeam(authentication.getCurrentUser().getUsername()).equals(teamsController.getTeam(team))){
                            if(tournamentsController.getTournament(tournament).getParticipant((Player) authentication.getCurrentUser())==null){
                                if (tournamentsController.getTournament(tournament).getParticipant(teamsController.getTeam(team))==null){
                                    return tournamentsController.getTournament(tournament).addParticipant(teamsController.getTeam(team));
                                } else return false;
                            } else return false;
                        } else return false;
                    } else return false;
                } else {
                    if (tournamentsController.getTournament(tournament).getParticipant((Player) authentication.getCurrentUser())==null){
                        if (teamsController.isInTeam(authentication.getCurrentUser().getUsername())!=null){
                            if (tournamentsController.getTournament(tournament).getParticipant(teamsController.isInTeam(authentication.getCurrentUser().getUsername()))==null){
                                return tournamentsController.getTournament(tournament).addParticipant((Player) authentication.getCurrentUser());
                            } else return false;
                        } else return tournamentsController.getTournament(tournament).addParticipant((Player) authentication.getCurrentUser());
                    } else return false;
                }
            } else return false;
        } else return false;
    }

    public static boolean tournamentRemove(String tournament, String team){
        if (tournamentsController.getTournament(tournament)!=null){
            if (team!=null){
                if (teamsController.getTeam(team)!=null){
                    if (teamsController.isInTeam(authentication.getCurrentUser().getUsername()).equals(teamsController.getTeam(team))){
                        if (tournamentsController.getTournament(tournament).getParticipant(teamsController.getTeam(team))!=null){
                            return tournamentsController.getTournament(tournament).removeParticipant(teamsController.getTeam(team));
                        } else return false;
                    } else return false;
                } else return false;
            } else {
                if (tournamentsController.getTournament(tournament).getParticipant((Player) authentication.getCurrentUser())!=null){
                    return tournamentsController.getTournament(tournament).removeParticipant((Player) authentication.getCurrentUser());
                } else return false;
            }
        } else return false;
    }

    public static boolean statisticsShow(String option){
        if (option.equals("-csv")){
            ((Player) authentication.getCurrentUser()).showStatsCsv();
            return true;
        } else if (option.equals("-json")) {
            ((Player) authentication.getCurrentUser()).showStatsJson();
            return true;
        } else return false;
    }
}
