package upm.etsisi.poo.controller;

import upm.etsisi.poo.model.Admin;
import upm.etsisi.poo.model.ModelException;
import upm.etsisi.poo.model.Team;

import java.util.ArrayList;

public class TeamsController {
    private static TeamsController uniqueInstance;
    private ArrayList<Team> teams;

    private TeamsController() {
        this.teams = new ArrayList<>();
    }

    public static TeamsController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new TeamsController();
        }
        return uniqueInstance;
    }

    public Team getTeam(String name) {
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

    public boolean createTeam(String name, Admin creator) throws ModelException {
        boolean result = false;
        if (getTeam(name) == null) {
            teams.add(new Team(name, creator));
            result = true;
        }
        return result;
    }

    public boolean deleteTeam(String name) {
        boolean result = false;
        if (getTeam(name) != null) {
            teams.remove(getTeam(name));
            result = true;
        }
        return result;
    }

    public Team isInTeam(String username){
        Team team = null;
        for (int i = 0; i<teams.size(); i++){
            if (teams.get(i).getPlayer(username)!=null){
                team = teams.get(i);
            }
        }
        return team;
    }
}
