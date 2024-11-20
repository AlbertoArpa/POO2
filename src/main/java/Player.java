import java.util.ArrayList;

public class Player extends User implements Participant{
    private static final String ATTR_NAME_NAME = "name";
    private static final String ATTR_SURNAME_NAME = "surname";
    private static final String ATTR_DNI_NAME = "dni";
    private static final String ATTR_STATS_NAME = "stats";
    private static final String ATTR_CREATOR_NAME = "creator";
    private String name;
    private String surname;
    private String dni;
    private ArrayList<Stat> stats;
    private Admin creator;

    public Player(String username, String password, String name, String surname, String dni, Admin creator){
        setUsername(username);
        setPassword(password);
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.stats = initialStats();
        this.creator = creator;
    }

    private ArrayList<Stat> initialStats(){
        ArrayList<Stat> statList = new ArrayList<>();
        for (int i = 0; i< Categories.getCategories().length; i++){
            Stat stat = new Stat(Categories.getCategories()[i]);
            statList.add(stat);
        }
        return statList;
    }

    public void showStatsJson(){
        for (int i = 0; i<stats.size(); i++){
            System.out.println(stats.get(i).getCategory() + " \t\t" + stats.get(i).getValue());
        }
    }

    public void showStatsCsv(){
        for (int i = 0; i<stats.size(); i++){
            System.out.println(stats.get(i).getCategory() + ";" + stats.get(i).getValue());
        }
    }

    @Override
    public Stat getStat(String category){
        Stat stat = null;
        for (int i = 0; i<stats.size(); i++){
            if (stats.get(i).getCategory().equals(Categories.getCategory(category))){
                stat = stats.get(i);
            }
        }
        return stat;
    }


    public boolean updateStat(String category, double value){
        boolean updated = false;
        Stat stat = getStat(category);
        if (stat!=null){
            stat.setValue(stat.getValue()+value);
            updated = true;
        }
        return updated;
    }

    public String getName() {
        return getUsername();
    }

    public String getName1(){
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDni() {
        return dni;
    }

    public Admin getCreator() {
        return creator;
    }

    public ArrayList<Stat> getStats() {
        return stats;
    }

    @Override
    public String toString() {
        return getSurname() + ", " + getName1() + "(" + getDni() + ")";
    }
}
