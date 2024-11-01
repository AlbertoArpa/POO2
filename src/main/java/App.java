import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        AdminsController adminsController = new AdminsController();
        PlayersController playersController = new PlayersController();
        TeamsController teamsController = new TeamsController();
        TournamentsController tournamentsController = new TournamentsController();
        initialUsers(adminsController);
        System.out.println("SISTEMA DE GESTION DEPORTIVA INICIADO:");
        Scanner sc = new Scanner(System.in);
        boolean end = false;
        Authentication auth = new Authentication();
        while (!end) {
            System.out.print("Comandos:\n> login\n> tournament-list\n----------\n> ");
            String option = sc.nextLine();
            switch (option.toLowerCase()) {
                case "login":
                    System.out.print("username: ");
                    String username = sc.nextLine();
                    System.out.print("password: ");
                    String password = sc.nextLine();
                    if (auth.logIn(adminsController, playersController, username, password)){
                        System.out.println("\nBIENVENIDO, " + auth.getUsername().toUpperCase());
                        if (auth.getUserType().equals("ADMIN")){
                            menuAdmin(sc, auth, playersController, teamsController, tournamentsController);
                        } else menuPlayer(sc, auth, tournamentsController);
                    } else System.out.println("No se ha podido iniciar sesión.");
                    break;
                case "tournament-list":
                    break;
                default:
                    System.out.println("No existe esa opción. Se procederá a finalizar la ejecución.");
                    end = true;
                    break;
            }
        } sc.close();
    }

    private static void initialUsers(AdminsController adminsController){
        Admin alberto = new Admin("alberto", "4321");
        Admin javier = new Admin("javier", "1234");
        Admin adri = new Admin("adrian", "1432");
        adminsController.addAdmin(alberto);
        adminsController.addAdmin(javier);
        adminsController.addAdmin(adri);
    }

    private static void menuAdmin(Scanner sc, Authentication auth, PlayersController playersController,
                                  TeamsController teamsController, TournamentsController tournamentsController){
        boolean exit = false;
        while(!exit){
            System.out.print("Comandos:\n> player-create [argumentos separados por ;]\n" + "> team-create [argumentos separados por ;]\n" +
                    "> player-delete [argumentos separados por ;]\n" + "> team-delete [argumentos separados por ;]\n" +
                    "> team-add [argumentos separados por ;]\n" + "> team-remove [argumentos separados por ;]\n" +
                    "> tournament-create [argumentos separados por ;]\n" + "> tournament-delete [argumentos separados por ;]\n" +
                    "> tournament-matchmaking [argumentos separados por ;]\n" + "> tournament-list\n" + "> logout\n----------\n> ");
            String option = sc.nextLine().split(" ")[0];
            switch (option.toLowerCase()){
                case "player-create":
                    break;
                case "team-create":
                    break;
                case "player-delete":
                    break;
                case "team-delete":
                    break;
                case "team-add":
                    break;
                case "team-remove":
                    break;
                case "tournament-create":
                    break;
                case "tournament-delete":
                    break;
                case "tournament-matchmaking":
                    break;
                case "tournament-list":
                    break;
                case "logout":
                    auth.logOut();
                    exit = true;
                    break;
                default:
                    System.out.println("No existe esa opción.");
                    break;
            }
        }
    }

    private static void menuPlayer(Scanner sc, Authentication auth, TournamentsController tournamentsController){
        boolean exit = false;
        while(!exit){
            System.out.print("Comandos:\n> tournament-add [argumentos separados por ;]\n" +
                    "> tournament-remove [argumentos separados por ;]\n" +
                    "> statistics-show [argumentos separados por ;]" + "> tournament-list\n" + "> logout\n----------\n> ");
            String option = sc.nextLine().split(" ")[0];
            switch (option.toLowerCase()){
                case "tournament-add":
                    break;
                case "tournament-remove":
                    break;
                case "statistics-show":
                    break;
                case "tournament-list":
                    break;
                case "logout":
                    auth.logOut();
                    exit = true;
                    break;
                default:
                    System.out.println("No existe esa opción.");
                    break;
            }
        }
    }
}
