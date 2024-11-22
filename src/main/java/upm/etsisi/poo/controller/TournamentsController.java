package upm.etsisi.poo.controller;

import java.util.ArrayList;

import upm.etsisi.poo.model.Admin;
import upm.etsisi.poo.model.Authentication;
import upm.etsisi.poo.model.Categories;
import upm.etsisi.poo.model.Date;
import upm.etsisi.poo.model.ModelException;
import upm.etsisi.poo.model.Participant;
import upm.etsisi.poo.model.Player;
import upm.etsisi.poo.model.Team;
import upm.etsisi.poo.model.Tournament;

public class TournamentsController {
    private static TournamentsController uniqueInstance;
    private ArrayList<Tournament> tournaments;

    private TournamentsController() {
        this.tournaments = new ArrayList<>();
    }

    public static TournamentsController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new TournamentsController();
        }
        return uniqueInstance;
    }

    public static Tournament getTournament(String name) {
        for (Tournament tournament : getInstance().tournaments) {
            if (tournament.getName().equals(name)) {
                return tournament;
            }
        }
        return null;
    }


    public static boolean deleteTournament(String name) {
        boolean result = false;
        if (getTournament(name) != null) {
            getInstance().tournaments.remove(getTournament(name));
            result = true;
        }
        return result;
    }

    private static boolean deletePastTournaments() {
        boolean result = false;
        for (int i = getInstance().tournaments.size()-1; i >= 0; i--) {
            if (getInstance().tournaments.get(i).getEndDate().lowerThan(new Date())) {
                getInstance().tournaments.remove(i);
                i--;
                result = true;
            }
        }
        return result;
    }

    public static Tournament isParticipant(Participant participant){
        Tournament result = null;
        for (int i = 0; i< getInstance().tournaments.size();i++){
            if (getInstance().tournaments.get(i).getParticipant(participant)!=null){
                result = getInstance().tournaments.get(i);
            }
        }
        return result;
    }

    public static String listTournaments(String type) {
        StringBuilder result = new StringBuilder();
        if (type == null) {
            ArrayList<Tournament> tournamentsAux = new ArrayList<>(), randomTournaments = new ArrayList<>();
            for (int i = 0; i < getInstance().tournaments.size(); i++) tournamentsAux.add(getInstance().tournaments.get(i));
            for (int i = 0; i < getInstance().tournaments.size(); i++) {
                int random = (int) (Math.random() * tournamentsAux.size());
                randomTournaments.add(tournamentsAux.get(random));
                ArrayList<Participant> randomizedParticipants = randomTournaments.get(random).getRandomizedParticipants();
                tournamentsAux.remove(random);
                result.append("\nNOMBRE: ").append(randomTournaments.get(i).getName())
                        .append("\nFECHA: ").append(randomTournaments.get(i).getStartDate()).append(" - ").append(randomTournaments.get(i).getEndDate())
                        .append("\nLIGA: ").append(randomTournaments.get(i).getLeague())
                        .append("\nDEPORTE: ").append(randomTournaments.get(i).getSport())
                        .append("\nCATEGORIA DE ORDEN: ").append(randomTournaments.get(i).getCategoryRank()).append("\n");
                for (int j = 0; j < randomizedParticipants.size(); j++) {
                    result.append("\t- ").append(randomTournaments.get(i).getParticipants()).append("\n");
                }
            }
        } else {
            if (type.equals("ADMIN")) {
                if (deletePastTournaments()) result.append("\nTORNEOS PASADOS BORRADOS.\n");
                else result.append("\nNO HAY TORNEOS PASADOS PARA BORRAR.\n");
            }
            for (int i = 0; i < getInstance().tournaments.size(); i++) {
                result.append("\nNOMBRE: ").append(getInstance().tournaments.get(i).getName())
                        .append("\nFECHA: ").append(getInstance().tournaments.get(i).getStartDate()).append(" - ").append(getInstance().tournaments.get(i).getEndDate())
                        .append("\nLIGA: ").append(getInstance().tournaments.get(i).getLeague())
                        .append("\nDEPORTE: ").append(getInstance().tournaments.get(i).getSport())
                        .append("\nCATEGORIA DE ORDEN: ").append(getInstance().tournaments.get(i).getCategoryRank()).append("\n");
                ArrayList rankedParticipants = getInstance().tournaments.get(i).getParticipantsRanked();
                for (int j = 0; j < rankedParticipants.size(); j++) {
                    if (rankedParticipants.get(j) instanceof Player) result.append("\n\t\tNOMBRE: ").append(((Player) rankedParticipants.get(j)).getName());
                    if (rankedParticipants.get(j) instanceof Team) result.append("\n\t\tNOMBRE: (team) ").append(((Team) rankedParticipants.get(j)).getName());
                    if (rankedParticipants.get(j) instanceof Player) result.append("\n\t\t").append(getInstance().tournaments.get(i).getCategoryRank()).append(": ").append(((Player) rankedParticipants.get(j)).getStat(getInstance().tournaments.get(i).getCategoryRank()).getValue());
                    if (rankedParticipants.get(j) instanceof Team) result.append("\n\t\t").append(getInstance().tournaments.get(i).getCategoryRank()).append(": ").append(((Team) rankedParticipants.get(j)).getStat(getInstance().tournaments.get(i).getCategoryRank()).getValue()).append("\n");
                }
            }
        }
        return result.toString();
    }

    // Métodos movidos de TournamentLogic
    public static boolean teamCreate(String name) throws ModelException {
        if (TeamsController.getTeam(name) == null && PlayersController.getPlayer(name) == null) {
            return TeamsController.createTeam(name, (Admin) Authentication.getCurrentUser());
        } else return false;
    }

    public static boolean playerDelete(String username) {
        PlayersController playersController = PlayersController.getInstance();
        TeamsController teamsController = TeamsController.getInstance();
        if (playersController.getPlayer(username) != null) {
            if (teamsController.isInTeam(username) != null) {
                if (isParticipant(teamsController.isInTeam(username)) == null) {
                    teamsController.isInTeam(username).removePlayer(username);
                    return playersController.deletePlayer(username);
                } else return false;
            } else if (isParticipant(playersController.getPlayer(username)) == null) {
                return playersController.deletePlayer(username);
            } else return false;
        } else return false;
    }

    public static boolean teamDelete(String name){
        TeamsController teamsController = TeamsController.getInstance();
        if (teamsController.getTeam(name)!=null){
            if (isParticipant(teamsController.getTeam(name))==null){
                return teamsController.deleteTeam(name);
            } else return false;
        } else return false;
    }

    public static boolean teamAdd(String username, String team){
        PlayersController playersController = PlayersController.getInstance();
        TeamsController teamsController = TeamsController.getInstance();
        if (playersController.getPlayer(username)!=null){
            if (teamsController.getTeam(team)!=null){
                if (teamsController.isInTeam(username)==null){
                    return teamsController.getTeam(team).addPlayer(playersController.getPlayer(username));
                } else return false;
            } else return false;
        } else return false;
    }

    public static boolean teamRemove(String username, String team){
        PlayersController playersController = PlayersController.getInstance();
        TeamsController teamsController = TeamsController.getInstance();
        if (playersController.getPlayer(username)!=null){
            if (teamsController.getTeam(team)!=null){
                if (teamsController.isInTeam(username).equals(teamsController.getTeam(team))){
                    return teamsController.getTeam(team).removePlayer(username);
                } else return false;
            } else return false;
        } else return false;
    }

    public static boolean createTournament(String name, String startDate, String endDate, String league, String sport, String categoryRank) throws ModelException {
        if (getTournament(name)==null){
            if (Date.isCorrect(startDate)&& Date.isCorrect(endDate)){
                Date date1 = new Date(startDate);
                Date date2 = new Date(endDate);
                if (date1.greaterThan(new Date()) && date2.greaterThan(date1) && Categories.getCategory(categoryRank)!=null){
                    getInstance().tournaments.add(new Tournament(name, date1, date2, league, sport, Categories.getCategory(categoryRank)));
                    return true;
                } else return false;
            } else return false;
        } else return false;
    }

    public static boolean tournamentMatchmakingM(String name, String participant1, String participant2){
        if (getTournament(name)!=null){
            if (PlayersController.getPlayer(participant1)!=null){
                if (PlayersController.getPlayer(participant2)!=null){
                    if (getTournament(name).getParticipant(PlayersController.getPlayer(participant1)) != null &&
                            getTournament(name).getParticipant(PlayersController.getPlayer(participant2)) != null){
                        return getTournament(name).getMatchmaking().createMatchmake(PlayersController.getPlayer(participant1), PlayersController.getPlayer(participant2));
                    } else return false;
                } else if (TeamsController.getTeam(participant2)!=null){
                    if (getTournament(name).getParticipant(PlayersController.getPlayer(participant1)) != null &&
                            getTournament(name).getParticipant(TeamsController.getTeam(participant2)) != null){
                        return getTournament(name).getMatchmaking().createMatchmake(PlayersController.getPlayer(participant1), TeamsController.getTeam(participant2));
                    } else return false;
                } else return false;
            } else if (TeamsController.getTeam(participant1)!=null){
                if (PlayersController.getPlayer(participant2)!=null){
                    if (getTournament(name).getParticipant(TeamsController.getTeam(participant1)) != null &&
                            getTournament(name).getParticipant(PlayersController.getPlayer(participant2)) != null){
                        return getTournament(name).getMatchmaking().createMatchmake(TeamsController.getTeam(participant1), PlayersController.getPlayer(participant2));
                    } else return false;
                } else if (TeamsController.getTeam(participant2)!=null){
                    if (getTournament(name).getParticipant(TeamsController.getTeam(participant1)) != null &&
                            getTournament(name).getParticipant(TeamsController.getTeam(participant2)) != null){
                        return getTournament(name).getMatchmaking().createMatchmake(TeamsController.getTeam(participant1), TeamsController.getTeam(participant2));
                    } else return false;
                } else return false;
            } else return false;
        } else return false;
    }

    public static boolean tournamentMatchmakingA(String name){
        if (getTournament(name)!=null){
            return getTournament(name).getMatchmaking().randomMatchmake(getTournament(name).getRandomizedParticipants());
        } else return false;
    }

    public static boolean tournamentAdd(String tournament, String team){
        TeamsController teamsController = TeamsController.getInstance();
        Authentication authentication = Authentication.getInstance();
        if (getTournament(tournament)!=null){
            if (getTournament(tournament).getStartDate().greaterThan(new Date())){
                if (team!=null){
                    if (teamsController.getTeam(team)!=null){
                        if (teamsController.isInTeam(authentication.getCurrentUser().getUsername()).equals(teamsController.getTeam(team))){
                            if(getTournament(tournament).getParticipant((Player) authentication.getCurrentUser())==null){
                                if (getTournament(tournament).getParticipant(teamsController.getTeam(team))==null){
                                    return getTournament(tournament).addParticipant(teamsController.getTeam(team));
                                } else return false;
                            } else return false;
                        } else return false;
                    } else return false;
                } else {
                    if (getTournament(tournament).getParticipant((Player) authentication.getCurrentUser())==null){
                        if (teamsController.isInTeam(authentication.getCurrentUser().getUsername())!=null){
                            if (getTournament(tournament).getParticipant(teamsController.isInTeam(authentication.getCurrentUser().getUsername()))==null){
                                return getTournament(tournament).addParticipant((Player) authentication.getCurrentUser());
                            } else return false;
                        } else return getTournament(tournament).addParticipant((Player) authentication.getCurrentUser());
                    } else return false;
                }
            } else return false;
        } else return false;
    }

    public static boolean tournamentRemove(String tournament, String team){
        TeamsController teamsController = TeamsController.getInstance();
        Authentication authentication = Authentication.getInstance();
        if (getTournament(tournament)!=null){
            if (team!=null){
                if (teamsController.getTeam(team)!=null){
                    if (teamsController.isInTeam(authentication.getCurrentUser().getUsername()).equals(teamsController.getTeam(team))){
                        if (getTournament(tournament).getParticipant(teamsController.getTeam(team))!=null){
                            return getTournament(tournament).removeParticipant(teamsController.getTeam(team));
                        } else return false;
                    } else return false;
                } else return false;
            } else {
                if (getTournament(tournament).getParticipant((Player) authentication.getCurrentUser())!=null){
                    return getTournament(tournament).removeParticipant((Player) authentication.getCurrentUser());
                } else return false;
            }
        } else return false;
    }
}