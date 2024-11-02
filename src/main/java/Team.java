import java.util.ArrayList;

public class Team{
    private String name;
    private ArrayList<Player> players;
    private ArrayList<Stat> stats;
    private Admin creator;

    public Team (String name, Admin creator){
        this.name = name;
        this.players = new ArrayList<>();
        this.stats = initialStats();
        this.creator = creator;
    }

    private ArrayList<Stat> initialStats(){
        ArrayList<Stat> statList = new ArrayList<>();
        for (int i = 0; i< EnumCategory.getCategories().length; i++){
            Stat stat = new Stat(EnumCategory.getCategories()[i]);
            statList.add(stat);
        }
        return statList;
    }

    public void showStats(){
        for (int i = 0; i<stats.size(); i++){
            System.out.println(stats.get(i));
        }
    }

    public Stat getStat(String category){
        Stat stat = null;
        for (int i = 0; i<stats.size(); i++){
            if (stats.get(i).getCategory().equals(EnumCategory.getCategory(category))){
                stat = stats.get(i);
            }
        }
        return stat;
    }

    public void updateStats(){
        for (int i = 0; i<stats.size(); i++){
            stats.get(i).setValue(0);
        }
        for (int i = 0; i<players.size(); i++){
            for( int d = 0; d<stats.size(); d++){
                stats.get(i).setValue(stats.get(i).getValue()*players.get(i).getStats().get(i).getValue());
            }
        }
        for (int i = 0; i<stats.size(); i++){
            stats.get(i).setValue(Math.pow(stats.get(i).getValue(), 1.0/players.size()));
        }
    }

    public ArrayList<Stat> getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }

    public Player getPlayer(String name) {
        Player result = null;
        int i = 0;
        while (i < players.size() && result == null) {
            if (players.get(i).getName().equals(name)) result = players.get(i);
            i++;
        }
        return result;
    }

    public boolean addPlayer(Player player) {
        boolean result = false;
        if(player == null) {
            return false;
        }
        if (getPlayer(player.getName()) == null) {
            players.add(player);
            result = true;
        }
        return result;
    }

    public boolean removePlayer(String name) {
        boolean result = false;
        if (getPlayer(name) == null) {
            players.remove(getPlayer(name));
            result = true;
        }
        return result;
    }

}
