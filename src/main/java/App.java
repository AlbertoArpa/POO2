import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("SISTEMA DE GESTION DEPORTIVA INICIADO:");
        Scanner sc = new Scanner(System.in);
        String name;
        String[] arguments = sc.nextLine().split(";");
        String option = arguments[0].split(" ")[0];
        arguments[0] = arguments[0].split(" ")[1];
        boolean end = false;
        Authentication auth = new Authentication();
        while (!end) {
            if (!auth.isLoggedIn()) {
                System.out.println("- login\n- tournament-list");
            } else {
                System.out.println("- logout\n- tournament-list");
                if (auth.getUserType().equals("ADMIN")) {
                    System.out.println("- player-create\n- team-create\n- player-delete\n- team delete\n- team-add" +
                            "\n- team-remove\n- tournament-create\n- tournament-delete\n- tournament-matchmaking");
                } else {
                    System.out.println("- tournament-add\n- tournament-remove\n- statistics-show");
                }
            }
            switch (option) {
                case "login":
            }
        }
    }
}
