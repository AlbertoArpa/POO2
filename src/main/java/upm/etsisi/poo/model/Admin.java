package upm.etsisi.poo.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Admin extends User {
    private static final String ATTR_USERNAME_NAME = "username";
    private static final String ATTR_PASSWORD_NAME = "password";

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    private static EntityManager em = emf.createEntityManager();

    public Admin(String username, String password) throws ModelException {
        Validations.isNotNull(ATTR_USERNAME_NAME, username);
        usernameValidate(username);
        Validations.isMinimum(ATTR_PASSWORD_NAME, password, 3);
        setUsername(username);
        setPassword(password);
        em.getTransaction().begin();
        em.persist(this);
        em.getTransaction().commit();
    }
}
