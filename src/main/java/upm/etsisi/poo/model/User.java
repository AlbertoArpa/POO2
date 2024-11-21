package upm.etsisi.poo.model;

import java.util.Objects;

public class User {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void usernameValidate(String username) throws ModelException {
        try{
            String[] user = username.split("@");
            if (user[0].isEmpty() || !user[1].equals("alumnos.upm.es")){
                throw new ModelException("El email no tiene el formato válido.");
            }
        } catch (Exception e){
            throw new ModelException("Formato incorrecto: El username debe de ser un email.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
