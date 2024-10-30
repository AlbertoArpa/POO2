import java.util.List;

public class AdminsController {
    private List<Admin> admins;
    public Admin existAdmin(String username){
        Admin admin = null;
        for (int i = 0; i<admins.size(); i++){
            if (admins.get(i).getUsername().equalsIgnoreCase(username)){
                admin = admins.get(i);
            }
        }
        return admin;
    }
}
