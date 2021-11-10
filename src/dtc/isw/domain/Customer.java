package dtc.isw.domain;

import java.io.Serializable;

public class Customer implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String user;
    private String password;

    public Customer() {
        this.setUser(new String());
        this.setPassword(new String());
    }

    public Customer(String user, String password) {
        this.setUser(user);
        this.setPassword(password);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}