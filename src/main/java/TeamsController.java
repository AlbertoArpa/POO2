import java.util.ArrayList;

public class TeamsController {
    private ArrayList<Team> teams;

    public TeamsController() {
        this.teams = new ArrayList<>();
    }

    public Team getTeam(String name) {
        Team result = null;
        int i = 0;
        while (i < teams.size() && result == null) {
            if (teams.get(i).getName().equals(name)) {
                result = teams.get(i);
            }
            i++;
        }
        return result;
    }

    public boolean createTeam(String name, Admin creator) {
        boolean result = false;
        if (getTeam(name) == null) {
            teams.add(new Team(name, creator));
            result = true;
        }
        return result;
    }

    public boolean deleteTeam(String name) { // GENERICO
        boolean result = false;
        return result;
    }
}
