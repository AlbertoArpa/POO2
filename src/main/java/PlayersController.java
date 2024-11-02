import java.util.ArrayList;

public class PlayersController {
    private ArrayList<Player> players;

    public PlayersController(){
        this.players = new ArrayList<>();
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

    public boolean createPlayer(String username, String password, String name, String surname, String dni, Admin creator) {
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

}
