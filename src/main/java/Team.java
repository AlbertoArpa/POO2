import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Player> players;
    private List<Stat> stats;
    private Admin creator;

    public Team (String name, Admin creator){
        this.name = name;
        this.players = new ArrayList<>();
        this.stats = initialStats();
        this.creator = creator;
    }

    private List<Stat> initialStats(){
        List<Stat> statList = new ArrayList<>();
        Stat stat0 = new Stat("Scored points");
        statList.add(stat0);
        Stat stat1 = new Stat("Assist points");
        statList.add(stat1);
        Stat stat2 = new Stat("Won games");
        statList.add(stat2);
        Stat stat3 = new Stat("Won tournaments");
        statList.add(stat3);
        Stat stat4 = new Stat("Money generated");
        statList.add(stat4);
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
}
