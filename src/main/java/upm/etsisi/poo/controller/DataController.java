package upm.etsisi.poo.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import upm.etsisi.poo.model.*;
import upm.etsisi.poo.view.PublicView;

import java.util.ArrayList;
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
            SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();;
            session = sessionFactory.openSession();
            return true;
        } catch (Exception es){
            System.out.println(es.getMessage());
            return false;
        }
    }

    public static boolean getData(){
        try{
            List<Admin> admins = session.createQuery("FROM Admin a", Admin.class).getResultList();
            for (Admin admin : admins){
                AdminsController.addAdmin(admin);
            }
            List<Team> teams = session.createQuery("SELECT DISTINCT t FROM Team t JOIN FETCH t.stats", Team.class).getResultList();
            for (Team team : teams){
                TeamsController.addTeam(team);
            }
            List<Player> players = session.createQuery("SELECT DISTINCT p FROM Player p JOIN FETCH p.stats", Player.class).getResultList();
            for (Player player : players){
                PlayersController.addPlayer(player);
                if (player.getTeam()!=null){
                    TeamsController.getTeam(player.getTeam().getName()).addPlayer(player);
                }
            }
            List<Tournament> tournaments = session.createQuery("FROM Tournament t", Tournament.class).getResultList();
            for (Tournament tournament : tournaments){
                ArrayList<Participant> participants = new ArrayList<>();
                List<Player> players1 = session.createQuery("SELECT p FROM Player p JOIN p.tournaments t WHERE t.name = :tournamentName", Player.class)
                        .setParameter("tournamentName", tournament.getName()).getResultList();
                List<Team> teams1 = session.createQuery("SELECT te FROM Team te JOIN te.tournaments t WHERE t.name = :tournamentName", Team.class)
                        .setParameter("tournamentName", tournament.getName()).getResultList();
                participants.addAll(players1);
                participants.addAll(teams1);
                tournament.setParticipants(participants);
                for (Matchmaking matchmaking : tournament.getMatchmaking().getMatchmaking()){
                    matchmaking.initializateParticipant();
                }
                TournamentsController.addTournament(tournament);
            }
            return true;
        } catch (Exception es){
            System.out.println(es.getMessage());
            return false;
        }
    }

    public static void saveData() {
        try {
            session.beginTransaction();
            session.createNativeMutationQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
            session.createNativeMutationQuery("TRUNCATE TABLE admins").executeUpdate();
            session.createNativeMutationQuery("TRUNCATE TABLE dates").executeUpdate();
            session.createNativeMutationQuery("TRUNCATE TABLE matchmakings").executeUpdate();
            session.createNativeMutationQuery("TRUNCATE TABLE players").executeUpdate();
            session.createNativeMutationQuery("TRUNCATE TABLE stats").executeUpdate();
            session.createNativeMutationQuery("TRUNCATE TABLE tournaments").executeUpdate();
            session.createNativeMutationQuery("TRUNCATE TABLE users").executeUpdate();
            session.createNativeMutationQuery("TRUNCATE TABLE teams").executeUpdate();
            session.createNativeMutationQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
            for (Admin admin : AdminsController.getAdmins()) {
                session.persist(admin);
            }
            for (Player player : PlayersController.getPlayers()) {
                for (Stat stat : player.getStats()) {
                    stat.setPlayer(player);
                    session.persist(stat);
                }
            }
            for (Team team : TeamsController.getTeams()) {
                for (Stat stat : team.getStats()) {
                    stat.setTeam(team);
                    session.persist(stat);
                }
                session.persist(team);
            }
            for (Tournament tournament : TournamentsController.getTournaments()) {
                session.persist(tournament.getStartDate());
                session.persist(tournament.getEndDate());
                session.persist(tournament);
            }
            session.getTransaction().commit();
            PublicView.saveData(true);
        } catch (Exception es) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println(es.getMessage());
            PublicView.saveData(false);
        }
    }

    public static void close(){
        session.close();
    }
}
