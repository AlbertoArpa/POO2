package upm.etsisi.poo.controller;

import upm.etsisi.poo.model.Admin;
import upm.etsisi.poo.model.ModelException;
import upm.etsisi.poo.model.Player;

import java.util.ArrayList;

public class PlayersController {
    private static PlayersController uniqueInstance;
    private ArrayList<Player> players;

    private PlayersController(){
        this.players = new ArrayList<>();
    }

    public static PlayersController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new PlayersController();
        }
        return uniqueInstance;
    }

    public Player getPlayer(String username) {
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

    public boolean createPlayer(String username, String password, String name, String surname, String dni, Admin creator) throws ModelException {
        boolean result = false;
        if (getPlayer(username) == null) {
            players.add(new Player(username, password, name, surname, dni, creator));
            result = true;
        }
        return result;
    }

    public boolean deletePlayer(String username) {
        boolean result = false;
        if (getPlayer(username) != null) {
            players.remove(getPlayer(username));
            result = true;
        }
        return result;
    }

    public boolean addPoints(String username, String stat, double points){
        boolean result = false;
        if (getPlayer(username) != null) {
            return getPlayer(username).updateStat(stat, points);
        }
        return result;
    }

}
