public class TournamentLogic {
    private static final TournamentsController TOURNAMENTS_CONTROLLER = TournamentsController.getInstance();
    private static final TeamsController TEAMS_CONTROLLER = TeamsController.getInstance();
    private static final PlayersController PLAYERS_CONTROLLER = PlayersController.getInstance();
    private static final Authentication AUTHENTICATION = Authentication.getInstance();

    public String tournamentList() {
        return TOURNAMENTS_CONTROLLER.listTournaments(AUTHENTICATION.getUserType());
    }

    public boolean teamCreate(String name) {
        if (TEAMS_CONTROLLER.getTeam(name) == null && PLAYERS_CONTROLLER.getPlayer(name) == null) {
            return TEAMS_CONTROLLER.createTeam(name, (Admin) AUTHENTICATION.getCurrentUser());
        } else return false;
    }

    public boolean playerDelete(String username) {
        if (PLAYERS_CONTROLLER.getPlayer(username) != null) {
            if (TEAMS_CONTROLLER.isInTeam(username) != null) {
                if (TOURNAMENTS_CONTROLLER.isParticipant(TEAMS_CONTROLLER.isInTeam(username)) == null) {
                    TEAMS_CONTROLLER.isInTeam(username).removePlayer(username);
                    return PLAYERS_CONTROLLER.deletePlayer(username);
                } else return false;
            } else if (TOURNAMENTS_CONTROLLER.isParticipant(PLAYERS_CONTROLLER.getPlayer(username)) == null) {
                return PLAYERS_CONTROLLER.deletePlayer(username);
            } else return false;
        } else return false;
    }

    public boolean teamDelete(String name){
        if (TEAMS_CONTROLLER.getTeam(name)!=null){
            if (TOURNAMENTS_CONTROLLER.isParticipant(TEAMS_CONTROLLER.getTeam(name))==null){
                return TEAMS_CONTROLLER.deleteTeam(name);
            } else return false;
        } else return false;
    }

    public boolean teamAdd(String username, String team){
        if (PLAYERS_CONTROLLER.getPlayer(username)!=null){
            if (TEAMS_CONTROLLER.getTeam(team)!=null){
                if (TEAMS_CONTROLLER.isInTeam(username)==null){
                    return TEAMS_CONTROLLER.getTeam(team).addPlayer(PLAYERS_CONTROLLER.getPlayer(username));
                } else return false;
            } else return false;
        } else return false;
    }

    public boolean teamRemove(String username, String team){
        if (PLAYERS_CONTROLLER.getPlayer(username)!=null){
            if (TEAMS_CONTROLLER.getTeam(team)!=null){
                if (TEAMS_CONTROLLER.isInTeam(username).equals(TEAMS_CONTROLLER.getTeam(team))){
                    return TEAMS_CONTROLLER.getTeam(team).removePlayer(username);
                } else return false;
            } else return false;
        } else return false;
    }

    public boolean tournamentCreate(String name, String startDate, String endDate, String league, String sport, String categoryRank){
        if (TOURNAMENTS_CONTROLLER.getTournament(name)==null){
            if (Date.isCorrect(startDate)&&Date.isCorrect(endDate)){
                if (Categories.getCategory(categoryRank)!=null){
                    return TOURNAMENTS_CONTROLLER.createTournament(name, new Date(startDate), new Date(endDate), league, sport, Categories.getCategory(categoryRank));
                } else return false;
            } else return false;
        } else return false;
    }

    public boolean tournamentDelete(String name){
        if (TOURNAMENTS_CONTROLLER.getTournament(name)!=null){
            return TOURNAMENTS_CONTROLLER.deleteTournament(name);
        } else return false;
    }

    public boolean tournamentMatchmakingM(String name, String participant1, String participant2){
        if (TOURNAMENTS_CONTROLLER.getTournament(name)!=null){
            if (PLAYERS_CONTROLLER.getPlayer(participant1)!=null){
                if (PLAYERS_CONTROLLER.getPlayer(participant2)!=null){
                    if (TOURNAMENTS_CONTROLLER.getTournament(name).getParticipant(PLAYERS_CONTROLLER.getPlayer(participant1)) != null &&
                            TOURNAMENTS_CONTROLLER.getTournament(name).getParticipant(PLAYERS_CONTROLLER.getPlayer(participant2)) != null){
                        return TOURNAMENTS_CONTROLLER.getTournament(name).getMatchmaking().createMatchmake(PLAYERS_CONTROLLER.getPlayer(participant1), PLAYERS_CONTROLLER.getPlayer(participant2));
                    } else return false;
                } else if (TEAMS_CONTROLLER.getTeam(participant2)!=null){
                    if (TOURNAMENTS_CONTROLLER.getTournament(name).getParticipant(PLAYERS_CONTROLLER.getPlayer(participant1)) != null &&
                            TOURNAMENTS_CONTROLLER.getTournament(name).getParticipant(TEAMS_CONTROLLER.getTeam(participant2)) != null){
                        return TOURNAMENTS_CONTROLLER.getTournament(name).getMatchmaking().createMatchmake(PLAYERS_CONTROLLER.getPlayer(participant1), TEAMS_CONTROLLER.getTeam(participant2));
                    } else return false;
                } else return false;
            } else if (TEAMS_CONTROLLER.getTeam(participant1)!=null){
                if (PLAYERS_CONTROLLER.getPlayer(participant2)!=null){
                    if (TOURNAMENTS_CONTROLLER.getTournament(name).getParticipant(TEAMS_CONTROLLER.getTeam(participant1)) != null &&
                            TOURNAMENTS_CONTROLLER.getTournament(name).getParticipant(PLAYERS_CONTROLLER.getPlayer(participant2)) != null){
                        return TOURNAMENTS_CONTROLLER.getTournament(name).getMatchmaking().createMatchmake(TEAMS_CONTROLLER.getTeam(participant1), PLAYERS_CONTROLLER.getPlayer(participant2));
                    } else return false;
                } else if (TEAMS_CONTROLLER.getTeam(participant2)!=null){
                    if (TOURNAMENTS_CONTROLLER.getTournament(name).getParticipant(TEAMS_CONTROLLER.getTeam(participant1)) != null &&
                            TOURNAMENTS_CONTROLLER.getTournament(name).getParticipant(TEAMS_CONTROLLER.getTeam(participant2)) != null){
                        return TOURNAMENTS_CONTROLLER.getTournament(name).getMatchmaking().createMatchmake(TEAMS_CONTROLLER.getTeam(participant1), TEAMS_CONTROLLER.getTeam(participant2));
                    } else return false;
                } else return false;
            } else return false;
        } else return false;
    }

    public boolean tournamentMatchmakingA(String name){
        if (TOURNAMENTS_CONTROLLER.getTournament(name)!=null){
            return TOURNAMENTS_CONTROLLER.getTournament(name).getMatchmaking().randomMatchmake(TOURNAMENTS_CONTROLLER.getTournament(name).getRandomizedParticipants());
        } else return false;
    }

    public boolean tournamentAdd(String tournament, String team){
        if (TOURNAMENTS_CONTROLLER.getTournament(tournament)!=null){
            if (TOURNAMENTS_CONTROLLER.getTournament(tournament).getStartDate().greaterThan(new Date())){
                if (team!=null){
                    if (TEAMS_CONTROLLER.getTeam(team)!=null){
                        if (TEAMS_CONTROLLER.isInTeam(AUTHENTICATION.getCurrentUser().getUsername()).equals(TEAMS_CONTROLLER.getTeam(team))){
                            if(TOURNAMENTS_CONTROLLER.getTournament(tournament).getParticipant((Player) AUTHENTICATION.getCurrentUser())==null){
                                if (TOURNAMENTS_CONTROLLER.getTournament(tournament).getParticipant(TEAMS_CONTROLLER.getTeam(team))==null){
                                    return TOURNAMENTS_CONTROLLER.getTournament(tournament).addParticipant(TEAMS_CONTROLLER.getTeam(team));
                                } else return false;
                            } else return false;
                        } else return false;
                    } else return false;
                } else {
                    if (TOURNAMENTS_CONTROLLER.getTournament(tournament).getParticipant((Player) AUTHENTICATION.getCurrentUser())==null){
                        if (TEAMS_CONTROLLER.isInTeam(AUTHENTICATION.getCurrentUser().getUsername())!=null){
                            if (TOURNAMENTS_CONTROLLER.getTournament(tournament).getParticipant(TEAMS_CONTROLLER.isInTeam(AUTHENTICATION.getCurrentUser().getUsername()))==null){
                                return TOURNAMENTS_CONTROLLER.getTournament(tournament).addParticipant((Player) AUTHENTICATION.getCurrentUser());
                            } else return false;
                        } else return TOURNAMENTS_CONTROLLER.getTournament(tournament).addParticipant((Player) AUTHENTICATION.getCurrentUser());
                    } else return false;
                }
            } else return false;
        } else return false;
    }

    public boolean tournamentRemove(String tournament, String team){
        if (TOURNAMENTS_CONTROLLER.getTournament(tournament)!=null){
            if (team!=null){
                if (TEAMS_CONTROLLER.getTeam(team)!=null){
                    if (TEAMS_CONTROLLER.isInTeam(AUTHENTICATION.getCurrentUser().getUsername()).equals(TEAMS_CONTROLLER.getTeam(team))){
                        if (TOURNAMENTS_CONTROLLER.getTournament(tournament).getParticipant(TEAMS_CONTROLLER.getTeam(team))!=null){
                            return TOURNAMENTS_CONTROLLER.getTournament(tournament).removeParticipant(TEAMS_CONTROLLER.getTeam(team));
                        } else return false;
                    } else return false;
                } else return false;
            } else {
                if (TOURNAMENTS_CONTROLLER.getTournament(tournament).getParticipant((Player) AUTHENTICATION.getCurrentUser())!=null){
                    return TOURNAMENTS_CONTROLLER.getTournament(tournament).removeParticipant((Player) AUTHENTICATION.getCurrentUser());
                } else return false;
            }
        } else return false;
    }
}