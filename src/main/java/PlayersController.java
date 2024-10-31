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
            while (players.get(i) != null && result == null) {
                if (username.equals(players.get(i).getUsername())) result = players.get(i);
                i++;
            }
        }

        return result;
    }
}
