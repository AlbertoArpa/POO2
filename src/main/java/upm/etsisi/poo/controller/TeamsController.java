package upm.etsisi.poo.controller;

import upm.etsisi.poo.model.Admin;
import upm.etsisi.poo.model.ModelException;
import upm.etsisi.poo.model.Team;

import java.util.ArrayList;

public class TeamsController {
    private static TeamsController uniqueInstance;
    private static ArrayList<Team> teams;

    private TeamsController() {
        this.teams = new ArrayList<>();
    }

    public static TeamsController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new TeamsController();
        }
        return uniqueInstance;
    }

    public static Team getTeam(String name) {
        Team result = null;
        int i = 0;
        while (i < teams.size() && result == null) {
            if (teams.get(i).getName().equalsIgnoreCase(name)) {
                result = teams.get(i);
            }
            i++;
        }
        return result;
    }

    public static boolean createTeam(String name, Admin creator) throws ModelException {
        boolean result = false;
        if (getTeam(name) == null && PlayersController.getPlayer(name) == null) {
            teams.add(new Team(name, creator));
            result = true;
        }
        return result;
    }

    public static boolean deleteTeam(String name) {
        if (getTeam(name)!=null){
            if (TournamentsController.isParticipant(getTeam(name))==null){
                return teams.remove(getTeam(name));
            } else return false;
        } else return false;
    }

    public static Team isInTeam(String username){
        Team team = null;
        for (int i = 0; i<teams.size(); i++){
            if (teams.get(i).getPlayer(username)!=null){
                team = teams.get(i);
            }
        }
        return team;
    }

    public static boolean teamAdd(String username, String team){
        if (PlayersController.getPlayer(username)!=null){
            if (getTeam(team)!=null){
                if (isInTeam(username)==null){
                    return getTeam(team).addPlayer(PlayersController.getPlayer(username));
                } else return false;
            } else return false;
        } else return false;
    }


    public static boolean teamRemove(String username, String team){
        if (PlayersController.getPlayer(username)!=null){
            if (getTeam(team)!=null){
                if (isInTeam(username).equals(getTeam(team))){
                    return getTeam(team).removePlayer(username);
                } else return false;
            } else return false;
        } else return false;
    }
}
