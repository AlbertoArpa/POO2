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
            if (!option.equals("tournament-list")) {
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
                            } else menuPlayer(sc, auth, tournamentsController, teamsController, playersController);
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
                    "> team-add [player username;team]\n" +
                    "> team-remove [player username;team]\n" +
                    "> tournament-create [name;startDate;endDate;league;sport;categoryRank]\n" +
                    "> tournament-delete [tournament name]\n" +
                    "> tournament-matchmaking [-m/-a;tournament name(;team1;team2)]\n" +
                    "> tournament-list\n" + "> logout\n----------\n\t> ");
            String[] arguments = sc.nextLine().split(" ", 2);
            String option = arguments[0];
            if (!option.equals("logout") && !option.equals("tournament-list")) {
                if (arguments.length > 1)
                    arguments = arguments[1].split(";");
                else System.out.println("\nNO HAY ARGUMENTOS");
            }
            switch (option.toLowerCase()) {
                case "player-create":
                    if (arguments.length >= 5) {
                        if (adminsController.getAdmin(arguments[0]) == null) {
                            if (playersController.createPlayer(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], adminsController.getAdmin(auth.getUsername()))) {
                                System.out.println("\nJUGADOR " + arguments[0].toUpperCase() + " CREADO");
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
                        if (playersController.getPlayer(arguments[0])!=null){
                            if (teamsController.isInTeam(arguments[0]) != null) {
                                if (tournamentsController.isParticipant(teamsController.isInTeam(arguments[0]))==null){
                                    teamsController.getTeam(teamsController.isInTeam(arguments[0]).getName()).removePlayer(arguments[0]);
                                    playersController.deletePlayer(arguments[0]);
                                    System.out.println("\nJUGADOR BORRADO CORRECTAMENTE");
                                } else System.out.println("\nEL EQUIPO DEL JUGADOR ESTA EN ACTIVO");
                            } else if (tournamentsController.isParticipant(playersController.getPlayer(arguments[0]))==null){
                                playersController.deletePlayer(arguments[0]);
                                System.out.println("\nJUGADOR BORRADO CORRECTAMENTE");
                            } else System.out.println("\nEL JUGADOR ESTA EN ACTIVO");
                        } else System.out.println("\nNO EXISTE EL JUGADOR " + arguments[0].toUpperCase());
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
                    break;
                case "team-delete": // GENERICOS
                    if (arguments.length >= 1) {
                        if (teamsController.getTeam(arguments[0])!=null){
                            if (tournamentsController.isParticipant(teamsController.getTeam(arguments[0]))==null){
                                if (teamsController.deleteTeam(arguments[0])) {
                                    System.out.println("\nEQUIPO " + arguments[0].toUpperCase() + " ELIMINADO");
                                } else System.out.println("\nNO SE HA PODIDO ELIMINAR EL EQUIPO " + arguments[0].toUpperCase());
                            }
                        } else System.out.println("\nNO EXISTE EL EQUIPO " + arguments[0].toUpperCase());
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
                    break;
                case "team-add":
                    if (arguments.length >= 2) {
                        if (teamsController.getTeam(arguments[1]) != null) {
                            if (teamsController.addPlayer(playersController.getPlayer(arguments[0]), teamsController.getTeam(arguments[1]))) {
                                System.out.println("\nJUGADOR " + arguments[0].toUpperCase() + " ANADIDO AL EQUIPO " + arguments[1].toUpperCase());
                            } else
                                System.out.println("\nEL JUGADOR " + arguments[0].toUpperCase() + " NO EXISTE O YA SE ENCUENTRA EN UN EQUIPO.");
                        } else System.out.println("\nEL EQUIPO " + arguments[1].toUpperCase() + " NO EXISTE");
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
                    break;
                case "team-remove":
                    if (arguments.length >= 2) {
                        if (teamsController.getTeam(arguments[1]) != null) {
                            if (teamsController.getTeam(arguments[1]).removePlayer(arguments[0])) {
                                System.out.println("\nJUGADOR " + arguments[0].toUpperCase() + " BORRADO DEL EQUIPO " + arguments[1].toUpperCase());
                            } else System.out.println("\nNO EXISTE EL JUGADOR " + arguments[0].toUpperCase() +  " DENTRO DEL EQUIPO");
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
                    int i = 0;
                    while (i < arguments.length && !arguments[i].equals("-a") && !arguments[i].equals("-m")) {
                        i++;
                    }
                    if (i < arguments.length) {
                        String matchmaking = arguments[i];
                        switch (matchmaking) {
                            case "-m":
                                if (arguments.length >= 4) {
                                    while (i < 3) {
                                        arguments[i] = arguments[i + 1];
                                        i++;
                                    }
                                    if (tournamentsController.getTournament(arguments[0]) != null) {
                                        if (tournamentsController.getTournament(arguments[0]).matchmake(arguments[1], arguments[2])) {
                                            System.out.println("\nPARTICIPANTES " + arguments[1].toUpperCase() + " Y " + arguments[2].toUpperCase() + " EMPAREJADOS");
                                        } else System.out.println("\nNO SE HA PODIDO HACER EL EMPAREJAMIENTO");
                                    } else System.out.println("\nNO EXISTE EL TORNEO " + arguments[0].toUpperCase());
                                } else System.out.println("\nMUY POCOS ARGUMENTOS");
                                break;
                            case "-a":
                                if (arguments.length >= 2) {
                                    if (i == 0) arguments[0] = arguments[1];
                                    if (tournamentsController.getTournament(arguments[0]) != null) {
                                        if (tournamentsController.getTournament(arguments[0]).matchmake()) {
                                            System.out.println("PARTICIPANTES DE " + arguments[0].toUpperCase() + " EMPAREJADOS");
                                        } else System.out.println("\nNO SE HAN PODIDO HACER LOS EMPAREJAMIENTOS");
                                    } else System.out.println("\nNO EXISTE EL TORNEO " + arguments[0].toUpperCase());
                                } else System.out.println("\nMUY POCOS ARGUMENTOS");
                        }
                    } else System.out.println("\nFALTA ARGUMENTO \"-m\" O \"-a\"");
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
                    System.out.println("\nNO EXISTE LA OPCION " + option.toUpperCase());
                    break;
            }
        }
    }

    private static void menuPlayer(Scanner sc, Authentication auth, TournamentsController tournamentsController, TeamsController teamsController, PlayersController playersController) {
        boolean exit = false;
        while (!exit) {
            System.out.print("Comandos:\n> tournament-add [tournament name(;team)]\n" +
                    "> tournament-remove [tournament name(;team)]\n" +
                    "> statistics-show [-csv/-json]\n" +
                    "> tournament-list\n" + "> logout\n----------\n\t> ");
            String[] arguments = sc.nextLine().split(" ", 2);
            String option = arguments[0];
            if (!option.equals("logout") && !option.equals("tournament-list")) {
                if (arguments.length > 1)
                    arguments = arguments[1].split(";");
                else System.out.println("\nNO HAY ARGUMENTOS");
            }
            switch (option.toLowerCase()) {
                case "tournament-add":
                    if (arguments.length >= 1) {
                        if (tournamentsController.getTournament(arguments[0]) != null) {
                            if (arguments.length>1){
                                if (teamsController.getTeam(arguments[1])!=null){
                                    if (teamsController.getTeam(arguments[1]).getPlayer(auth.getUsername())!=null){
                                        if(tournamentsController.getTournament(arguments[0]).addParticipant(teamsController.getTeam(arguments[1]))){
                                            System.out.println("\nEQUIPO " + teamsController.getTeam(arguments[1]).getName().toUpperCase() + " ANADIDO A " + arguments[0].toUpperCase());
                                        } else {
                                            System.out.println("\nNO SE HA PODIDO ANADIR EL EQUIPO " + teamsController.getTeam(arguments[1]).getName().toUpperCase());
                                        }
                                    } else System.out.println("\nNO FORMAS PARTE DEL EQUIPO A ANADIR");
                                } else System.out.println("\nNO EXISTE EL EQUIPO A ANADIR");
                            } else{
                                if (tournamentsController.getTournament(arguments[0]).addParticipant(auth.getCurrentUser())) {
                                    System.out.println("\nJUGADOR " + auth.getUsername().toUpperCase() + " ANADIDO A " + arguments[0].toUpperCase());
                                } else
                                    System.out.println("\nNO SE HA PODIDO ANADIR EL JUGADOR " + auth.getUsername().toUpperCase() + " AL EQUIPO " + arguments[0].toUpperCase());
                            }
                        } else System.out.println("\nNO EXISTE EL TORNEO " + arguments[0].toUpperCase());
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
                    break;
                case "tournament-remove":
                    if (arguments.length >= 1) {
                        if (arguments.length >= 2) {
                            if (tournamentsController.getTournament(arguments[0]).removeParticipant(teamsController.getTeam(arguments[1]))) {
                                System.out.println("\nEQUIPO " + arguments[1].toUpperCase() + "ELIMINADO DE " + arguments[0].toUpperCase());
                            } else
                                System.out.println("\nNO SE HA PODIDO ELIMINAR EL EQUIPO " + arguments[1].toUpperCase() + " DE " + arguments[0].toUpperCase());
                        } else {
                            if (tournamentsController.getTournament(arguments[0]).removeParticipant(auth.getUsername())) {
                                System.out.println("\nJUGADOR " + auth.getUsername().toUpperCase() + "ELIMINADO DE " + arguments[0].toUpperCase());
                            } else
                                System.out.println("\nNO SE HA PODIDO ELIMINAR AL JUGADOR " + auth.getUsername().toUpperCase() + " DE " + arguments[0].toUpperCase());
                        }
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
                    break;
                case "statistics-show":
                    if (arguments.length >= 1) {
                        Player currentPlayer = playersController.getPlayer(auth.getUsername());
                        if (arguments[0].equals("-csv")){
                            currentPlayer.showStatsCsv();
                        } else if (arguments[0].equals("-json")) {
                            System.out.println("\nCATEGORÍA \t\t\tVALOR");
                            currentPlayer.showStatsJson();
                        } else System.out.println("\nARGUMENTO INVALIDO");
                    } else System.out.println("\nMUY POCOS ARGUMENTOS");
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
