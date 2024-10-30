import java.util.List;

public class Player extends User{
    private String name;
    private String surname;
    private String dni;
    private List<Stat> stats;
    private Admin creator;
    public Player(String username, String password, String name, String surname, String dni, Admin creator){
        super(username, password);
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.stats = initialStats();
        this.creator = creator;
    }

    private List<Stat> initialStats(){
        return null;
    }
}
