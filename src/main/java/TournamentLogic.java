public class TournamentLogic {
    private final TournamentsController tournamentsController = TournamentsController.getInstance();
    private final TeamsController teamsController = TeamsController.getInstance();
    private final PlayersController playersController = PlayersController.getInstance();
    private final Authentication authentication = Authentication.getInstance();

    public String tournamentList() {
        return tournamentsController.listTournaments(authentication.getUserType());
    }

    public boolean teamCreate(String name) {
        if (teamsController.getTeam(name) == null && playersController.getPlayer(name) == null) {
            return teamsController.createTeam(name, (Admin) authentication.getCurrentUser());
        } else return false;
    }

    public boolean playerDelete(String username) {
        if (playersController.getPlayer(username) != null) {
            if (teamsController.isInTeam(username) != null) {
                if (tournamentsController.isParticipant(teamsController.isInTeam(username)) == null) {
                    teamsController.isInTeam(username).removePlayer(username);
                    return playersController.deletePlayer(username);
                } else return false;
            } else if (tournamentsController.isParticipant(playersController.getPlayer(username)) == null) {
                return playersController.deletePlayer(username);
            } else return false;
        } else return false;
    }

    public boolean teamDelete(String name){
        if (teamsController.getTeam(name)!=null){
            if (tournamentsController.isParticipant(teamsController.getTeam(name))==null){
                return teamsController.deleteTeam(name);
            } else return false;
        } else return false;
    }

    public boolean teamAdd(String username, String team){
        if (playersController.getPlayer(username)!=null){
            if (teamsController.getTeam(team)!=null){
                if (teamsController.isInTeam(username)==null){
                    return teamsController.getTeam(team).addPlayer(playersController.getPlayer(username));
                } else return false;
            } else return false;
        } else return false;
    }

    public boolean teamRemove(String username, String team){
        if (playersController.getPlayer(username)!=null){
            if (teamsController.getTeam(team)!=null){
                if (teamsController.isInTeam(username).equals(teamsController.getTeam(team))){
                    return teamsController.getTeam(team).removePlayer(username);
                } else return false;
            } else return false;
        } else return false;
    }

    public boolean tournamentCreate(String name, String startDate, String endDate, String league, String sport, String categoryRank){
        if (tournamentsController.getTournament(name)==null){
            if (Date.isCorrect(startDate)&&Date.isCorrect(endDate)){
                if (Categories.getCategory(categoryRank)!=null){
                    return tournamentsController.createTournament(name, new Date(startDate), new Date(endDate), league, sport, Categories.getCategory(categoryRank));
                } else return false;
            } else return false;
        } else return false;
    }

    public boolean tournamentDelete(String name){
        if (tournamentsController.getTournament(name)!=null){
            return tournamentsController.deleteTournament(name);
        } else return false;
    }

    public boolean tournamentMatchmakingM(String name, String participant1, String participant2){
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

    public boolean tournamentMatchmakingA(String name){
        if (tournamentsController.getTournament(name)!=null){
            return tournamentsController.getTournament(name).getMatchmaking().randomMatchmake(tournamentsController.getTournament(name).getRandomizedParticipants());
        } else return false;
    }

    public boolean tournamentAdd(String tournament, String team){
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

    public boolean tournamentRemove(String tournament, String team){
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
}