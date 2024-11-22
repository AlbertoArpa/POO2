package upm.etsisi.poo.controller;

import upm.etsisi.poo.model.Admin;
import upm.etsisi.poo.model.Authentication;
import upm.etsisi.poo.model.ModelException;
import upm.etsisi.poo.model.Player;

import java.util.ArrayList;

public class PlayersController {
    private static PlayersController uniqueInstance;
    private static ArrayList<Player> players;

    private PlayersController(){
        this.players = new ArrayList<>();
    }

    public static PlayersController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new PlayersController();
        }
        return uniqueInstance;
    }

    public static Player getPlayer(String username) {
        Player result = null;
        int i = 0;
        if (!players.isEmpty()) {
            while (i < players.size() && players.get(i) != null && result == null) {
                if (username.equals(players.get(i).getUsername())) result = players.get(i);
                i++;
            }
        }
        return result;
    }

    public static boolean createPlayer(String username, String password, String name, String surname, String dni, Admin creator) throws ModelException {
        if (AdminsController.getAdmin(username) == null && getPlayer(username) == null && TeamsController.getTeam(username) == null) {
            players.add(new Player(username, password, name, surname, dni, creator));
            return true;
        } return false;
    }

    public static boolean deletePlayer(String username) {
        if (getPlayer(username) != null) {
            if (TeamsController.isInTeam(username) != null) {
                if (TournamentsController.isParticipant(TeamsController.isInTeam(username)) == null) {
                    TeamsController.isInTeam(username).removePlayer(username);
                    players.remove(getPlayer(username));
                    return true;
                } else return false;
            } else if (TournamentsController.isParticipant(getPlayer(username)) == null) {
                players.remove(getPlayer(username));
                return true;
            } else return false;
        } else return false;
    }

    public static boolean addPoints(String username, String stat, double points){
        boolean result = false;
        if (getPlayer(username) != null) {
            if (TeamsController.isInTeam(username)!=null){
                TeamsController.isInTeam(username).updateStats();
            }
            return getPlayer(username).updateStat(stat, points);
        }
        return result;
    }

    public static boolean statisticsShow(String option) {
        if (option.equals("-csv")) {
            ((Player) Authentication.getCurrentUser()).showStatsCsv();
            return true;
        } else if (option.equals("-json")) {
            ((Player) Authentication.getCurrentUser()).showStatsJson();
            return true;
        } else return false;
    }

}
