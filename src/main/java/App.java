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
            System.out.print("Comandos:\n> login [username;password]\n> tournament-list\n----------\n\t> ");
            String[] arguments = sc.nextLine().split(" ", 2);
            String option = arguments[0];
            if (!option.equals("tournament-list")){
                if (arguments.length > 1) arguments = arguments[1].split(";");
                else System.out.println("NO HAY ARGUMENTOS");
            }
            switch (option.toLowerCase()) {
                case "login":
                    if (arguments.length >= 2) {
                        if (auth.logIn(adminsController, playersController, arguments[0], arguments[1])) {
                            System.out.println("\nBIENVENIDO, " + auth.getUsername().toUpperCase());
                            if (auth.getUserType().equals("ADMIN")) {
                                menuAdmin(sc, auth, playersController, teamsController, tournamentsController, adminsController);
                            } else menuPlayer(sc, auth, tournamentsController);
                        } else System.out.println("\nNO SE HA PODIDO INICIAR SESION");
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
                    break;
                case "tournament-list":
                    System.out.println(tournamentsController.listTournaments(auth.getUserType()));
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
                    "> team-create [name]\n" +
                    "> player-delete [username]\n" +
                    "> team-delete [name]\n" +
                    "> team-add [name;team]\n" +
                    "> team-remove [name;team]\n" +
                    "> tournament-create [name;startDate;endDate;league;sport;categoryRank]\n" +
                    "> tournament-delete [name]\n" +
                    "> tournament-matchmaking [argumentos separados por ;]\n" +
                    "> tournament-list\n" + "> logout\n----------\n\t> ");
            String[] arguments = sc.nextLine().split(" ", 2);
            String option = arguments[0];
            if (!option.equals("logout") && !option.equals("tournament-list")){
                if (arguments.length > 1)
                    arguments = arguments[1].split(";");
                else System.out.println("\nNO HAY ARGUMENTOS");
            }
            switch (option.toLowerCase()) {
                case "player-create":
                    if (arguments.length >= 5) {
                        if (adminsController.getAdmin(arguments[0]) == null) {
                            if (playersController.createPlayer(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], adminsController.getAdmin(auth.getUsername()))) {
                                System.out.println("\nJUGADOR " + arguments[2].toUpperCase() + " CREADO");
                            } else System.out.println("\nYA EXISTE EL JUGADOR " + arguments[0].toUpperCase());
                        } else
                            System.out.println("\nYA EXISTE UN USUARIO CON EL NOMBRE DE USUARIO " + arguments[0].toUpperCase());
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
                    break;
                case "team-create":
                    if (arguments.length >= 1) {
                        if (teamsController.createTeam(arguments[0], adminsController.getAdmin(auth.getUsername()))) {
                            System.out.println("\nEQUIPO " + arguments[0].toUpperCase() + " CREADO");
                        } else System.out.println("\nEL EQUIPO " + arguments[0].toUpperCase() + " YA EXISTE");
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
                    break;
                case "player-delete":
                    if (arguments.length >= 1) {
                        if (playersController.deletePlayer(arguments[0])) {
                            System.out.println("\nJUGADOR " + arguments[0].toUpperCase() + " ELIMINADO");
                        } else System.out.println("\nNO EXISTE EL JUGADOR " + arguments[0].toUpperCase());
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
                    break;
                case "team-delete": // GENERICOS
/*                    if (arguments.length >= 1) {
                        if (teamsController.deleteTeam(arguments[0])) {
                            System.out.println("\nEQUIPO ");
                        } else System.out.println();
                    } else System.out.println("MUY POCOS ARGUMENTOS");*/
                    break;
                case "team-add":
                    if (arguments.length >= 2) {
                        if (teamsController.getTeam(arguments[1]) != null) {
                            if (teamsController.getTeam(arguments[1]).addPlayer(playersController.getPlayer(arguments[0]))) {
                                System.out.println("\nJUGADOR " + arguments[0].toUpperCase() + " ANADIDO AL EQUIPO " + arguments[1].toUpperCase());
                            } else System.out.println("\nEL JUGADOR " + arguments[0].toUpperCase() + " NO EXISTE O YA SE ENCUENTRA EN EL EQUIPO.");
                        } else System.out.println("\nEL EQUIPO " + arguments[1].toUpperCase() + " NO EXISTE");
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
                    break;
                case "team-remove":
                    if (arguments.length >= 2) {
                        if (teamsController.getTeam(arguments[1]) != null) {
                            if (teamsController.getTeam(arguments[1]).removePlayer(arguments[0])) {
                                System.out.println("\nJUGADOR " + arguments[0].toUpperCase() + " BORRADO DEL EQUIPO" + arguments[1].toUpperCase());
                            } else System.out.println("\nNO EXISTE EL JUGADOR " + arguments[0].toUpperCase());
                        } else System.out.println("\nNO EXISTE EL EQUIPO " + arguments[1].toUpperCase());
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
                    break;
                case "tournament-create":
                    if (arguments.length >= 6) {
                        if (Date.isCorrect(arguments[1]) && Date.isCorrect(arguments[2])) {
                            if (tournamentsController.createTournament(arguments[0], new Date(arguments[1]), new Date(arguments[2]), arguments[3], arguments[4], arguments[5])) {
                                System.out.println("\nTORNEO " + arguments[0].toUpperCase() + " CREADO");
                            } else System.out.println("\nNO SE HA PODIDO CREAR EL TORNEO.");
                        } else System.out.println("\nFORMATO DE FECHA INCORRECTO");
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
                    break;
                case "tournament-delete":
                    if (arguments.length >= 1) {
                        if (tournamentsController.deleteTournament(arguments[0])) {
                            System.out.println("\nTORNEO " + arguments[0].toUpperCase() + " BORRADO");
                        } else System.out.println("\nNO EXISTE EL TORNEO " + arguments[0].toUpperCase());
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
                    break;
                case "tournament-matchmaking":
                    break;
                case "tournament-list":
                    System.out.println(tournamentsController.listTournaments(auth.getUserType()));
                    break;
                case "logout":
                    auth.logOut();
                    System.out.println("\nCERRANDO SESIÓN...");
                    exit = true;
                    break;
                default:
                    System.out.println("NO EXISTE LA OPCION " + option.toUpperCase());
                    break;
            }
        }
    }

    private static void menuPlayer(Scanner sc, Authentication auth, TournamentsController tournamentsController) {
        boolean exit = false;
        while (!exit) {
            System.out.print("Comandos:\n> tournament-add [name]\n" +
                    "> tournament-remove [argumentos separados por ;]\n" +
                    "> statistics-show [argumentos separados por ;]" + "\n> tournament-list\n" + "> logout\n----------\n\t> ");
            String[] arguments = sc.nextLine().split(" ", 2);
            String option = arguments[0];
            if (!option.equals("logout") && !option.equals("tournament-list")){
                if (arguments.length > 1)
                    arguments = arguments[1].split(";");
                else System.out.println("\nNO HAY ARGUMENTOS");
            }
            switch (option.toLowerCase()) {
                case "tournament-add":
                    tournamentsController.getTournament(arguments[0]).addParticipant(auth.getCurrentUser());
                    break;
                case "tournament-remove":
                    break;
                case "statistics-show":
                    break;
                case "tournament-list":
                    System.out.println(tournamentsController.listTournaments(auth.getUserType()));
                    break;
                case "logout":
                    auth.logOut();
                    System.out.println("\nCERRANDO SESIÓN...\n");
                    exit = true;
                    break;
                default:
                    System.out.println("NO EXISTE LA OPCION " + option.toUpperCase());
                    break;
            }
        }
    }
}
