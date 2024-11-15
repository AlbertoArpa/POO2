package Controllers;

import java.util.ArrayList;

public class AdminsController {
    private static AdminsController uniqueInstance;
    private ArrayList<Admin> admins;

    private AdminsController(){
        this.admins = new ArrayList<>();
    }

    public Admin getAdmin(String username) {
        Admin result = null;
        for (int i = 0; i<admins.size(); i++){
            if (admins.get(i).getUsername().equalsIgnoreCase(username)){
                result = admins.get(i);
            }
        }
        return result;
    }

    public static AdminsController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new AdminsController();
        }
        return uniqueInstance;
    }

    public void addAdmin(Admin admin){
        if (getAdmin(admin.getUsername())==null) {
            admins.add(admin);
        }
    }
}
