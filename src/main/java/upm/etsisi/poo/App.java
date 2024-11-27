package upm.etsisi.poo;

import upm.etsisi.poo.controller.AdminsController;
import upm.etsisi.poo.controller.PlayersController;
import upm.etsisi.poo.controller.TeamsController;
import upm.etsisi.poo.controller.TournamentsController;
import upm.etsisi.poo.model.Authentication;
import upm.etsisi.poo.view.I_O;

public class App {
    public static void main(String[] args) {
        initialitation();
        I_O.start();
    }

    public static void initialitation() {
        Authentication.getInstance();
        AdminsController.getInstance();
        TournamentsController.getInstance();
        TeamsController.getInstance();
        PlayersController.getInstance();
    }

    /*login adrian@alumnos.upm.es;1432
    player-create adri@alumnos.upm.es;123;adri;largo;87654321J
    player-create adria@alumnos.upm.es;123;adrian;largo;12345679F
    team-create god
    team-add adri@alumnos.upm.es;god
    tournament-create futbol;14/01/2025;15/01/2026;liga1;futbol7;money generated
    add-points adri@alumnos.upm.es;money generated;12
    logout
    login adri@alumnos.upm.es;123
    tournament-add futbol;god
    tournament-add futbol
    logout
    login adria@alumnos.upm.es;123
    tournament-add futbol;god
    tournament-add futbol
     */
}
