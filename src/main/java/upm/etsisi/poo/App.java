package upm.etsisi.poo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import upm.etsisi.poo.model.Admin;
import upm.etsisi.poo.model.ModelException;

public class App {
    public static void main(String[] args) {
        //PRUEBA
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Admin admin1 = new Admin("a.arpa@alumnos.upm.es", "4321");
            session.beginTransaction();
            session.persist(admin1);
            session.getTransaction().commit();
        } catch (ModelException e){
            System.out.println(e.getMessage());
        }
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
