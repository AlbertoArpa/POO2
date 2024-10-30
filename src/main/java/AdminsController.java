import java.util.ArrayList;

public class AdminsController {
    private ArrayList<Admin> admins;

    public Admin getAdmin(String username) {
        Admin result = null;
        int i = 0;
        if (!admins.isEmpty()) {
            while (admins.get(i) != null && result == null) {
                if (username.equals(admins.get(i).getUsername())) result = admins.get(i);
                i++;
            }
        }

        return result;
    }
}
