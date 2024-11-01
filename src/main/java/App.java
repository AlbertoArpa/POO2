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
            System.out.print("Comandos:\n> login\n> tournament-list\n----------\n\t> ");
            String[] arguments = sc.nextLine().split(" ", 2);
            String option = arguments[0];
            arguments = arguments[1].split(";");
            switch (option.toLowerCase()) {
                case "login":
                    System.out.print("username: ");
                    String username = sc.nextLine();
                    System.out.print("password: ");
                    String password = sc.nextLine();
                    if (auth.logIn(adminsController, playersController, username, password)) {
                        System.out.println("\nBIENVENIDO, " + auth.getUsername().toUpperCase());
                        if (auth.getUserType().equals("ADMIN")) {
                            menuAdmin(sc, auth, playersController, teamsController, tournamentsController, adminsController);
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
        }
        sc.close();
    }

    private static void initialUsers(AdminsController adminsController) {
        adminsController.addAdmin(new Admin("a.arpa", "4321"));
        adminsController.addAdmin(new Admin("javier", "1234"));
        adminsController.addAdmin(new Admin("adrian", "1432"));
    }

    private static void menuAdmin(Scanner sc, Authentication auth, PlayersController playersController,
                                  TeamsController teamsController, TournamentsController tournamentsController,
                                  AdminsController adminsController) {
        boolean exit = false;
        while (!exit) {
            System.out.print("Comandos:\n> player-create [username;password;name;surname;dni]\n" +
                    "> team-create [argumentos separados por ;]\n" +
                    "> player-delete [username]\n" +
                    "> team-delete [argumentos separados por ;]\n" +
                    "> team-add [argumentos separados por ;]\n" +
                    "> team-remove [argumentos separados por ;]\n" +
                    "> tournament-create [argumentos separados por ;]\n" +
                    "> tournament-delete [argumentos separados por ;]\n" +
                    "> tournament-matchmaking [argumentos separados por ;]\n" +
                    "> tournament-list\n" + "> logout\n----------\n\t> ");
            String[] arguments = sc.nextLine().split(" ", 2);
            String option = arguments[0];
            arguments = arguments[1].split(";");
            switch (option.toLowerCase()) {
                case "player-create":
                    if (arguments.length >= 5) {
                        if (adminsController.getAdmin(arguments[0]) == null) {
                            if (playersController.createPlayer(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], adminsController.getAdmin(auth.getUsername()))) {
                                System.out.println("JUGADOR" + arguments[2].toUpperCase() + "CREADO");
                            } else System.out.println("YA EXISTE EL JUGADOR" + arguments[2].toUpperCase());
                        } else System.out.println("YA EXISTE UN USUARIO CON EL NOMBRE DE USUARIO " + arguments[0].toUpperCase());
                    } else System.out.println("MUY POCOS ARGUMENTOS");
                    break;
                case "team-create":
                    if (arguments.length >= 1) {
                        if (teamsController.createTeam(arguments[0], adminsController.getAdmin(auth.getUsername()))) {
                            System.out.println("EQUIPO" + arguments[0].toUpperCase() + "CREADO");
                        }
                    }
                    break;
                case "player-delete":
                    if (arguments.length >= 1) {
                        if (playersController.deletePlayer(arguments[0])) {
                            System.out.println("JUGADOR" + arguments[0].toUpperCase() + "ELIMINADO");
                        } else System.out.println("MUY POCOS ARGUMENTOS");
                    } else System.out.println("NO EXISTE EL JUGADOR");
                    break;
                case "team-delete":
                    if (arguments.length >= 1) {

                    } else System.out.println("MUY POCOS ARGUMENTOS");
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

    private static void menuPlayer(Scanner sc, Authentication auth, TournamentsController tournamentsController) {
        boolean exit = false;
        while (!exit) {
            System.out.print("Comandos:\n> tournament-add [argumentos separados por ;]\n" +
                    "> tournament-remove [argumentos separados por ;]\n" +
                    "> statistics-show [argumentos separados por ;]" + "> tournament-list\n" + "> logout\n----------\n\t> ");
            String option = sc.nextLine().split(" ")[0];
            switch (option.toLowerCase()) {
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
