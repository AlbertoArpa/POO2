package upm.etsisi.poo.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import upm.etsisi.poo.model.*;
import upm.etsisi.poo.view.PublicView;

import java.util.List;

public class DataController {
    private static Session session;
    public static boolean initialitation(){
        Authentication.getInstance();
        AdminsController.getInstance();
        TournamentsController.getInstance();
        TeamsController.getInstance();
        PlayersController.getInstance();
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
            SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            session = sessionFactory.openSession();
            return true;
        } catch (Exception es){
            return false;
        }
    }

    public static boolean getData(){
        try{
            List<Admin> admins = session.createQuery("FROM Admin a", Admin.class).getResultList();
            for (Admin admin : admins){
                AdminsController.addAdmin(admin);
            }
            List<Player> players = session.createQuery("SELECT DISTINCT p FROM Player p JOIN FETCH p.stats", Player.class).getResultList();
            for (Player player : players){
                PlayersController.addPlayer(player);
            }
            List<Team> teams = session.createQuery("FROM Team t", Team.class).getResultList();
            for (Team team : teams){
                TeamsController.addTeam(team);
            }
            return true;
        } catch (Exception es){
            System.out.println("Error al obtener los datos de la base de datos de administradores y jugadores");
            return false;
        }
    }

    public static void saveData(){
        try {
            session.beginTransaction();
            for (Player player : PlayersController.getPlayers()){
                for (Stat stat : player.getStats()){
                    stat.setPlayer(player);
                    session.persist(stat);
                }
                session.merge(player);
            }
            for (Admin admin : AdminsController.getAdmins()){
                session.merge(admin);
            }
            for (Team team : TeamsController.getTeams()){
                session.merge(team);
            }
            session.getTransaction().commit();
            session.close();
            PublicView.saveData(true);
        } catch (Exception es){
            PublicView.saveData(false);
        }
    }
}
