package upm.etsisi.poo.model;

public class Admin extends User {
    private static final String ATTR_USERNAME_NAME = "username";
    private static final String ATTR_PASSWORD_NAME = "password";

    public Admin(String username, String password) throws ModelException {
        Validations.isNotNull(ATTR_USERNAME_NAME, username);
        usernameValidate(username);
        Validations.isMinimum(ATTR_PASSWORD_NAME, password, 3);
        setUsername(username);
        setPassword(password);
    }
}
