import java.util.ArrayList;

public class Player extends User{
    private String name;
    private String surname;
    private String dni;
    private ArrayList<Stat> stats;
    private Admin creator;

    public Player(String username, String password, String name, String surname, String dni, Admin creator){
        super(username, password);
        this.name = name;
        this.surname = surname;
        this.dni = dni;
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


    public boolean updateStat(String category, double value){
        boolean updated = false;
        Stat stat = getStat(category);
        if (stat!=null){
            stat.setValue(value);
            updated = true;
        }
        return updated;
    }

    public String getName() {
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
        return getSurname() + ", " + getName() + "(" + getDni() + ")";
    }
}
