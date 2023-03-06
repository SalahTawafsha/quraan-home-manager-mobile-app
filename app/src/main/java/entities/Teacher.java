package entities;

import java.util.ArrayList;

public class Teacher extends Person {
    private String password;
    private final ArrayList<String> rules;


    public Teacher() {
        rules = new ArrayList<>();
    }

    public Teacher(String name, String password) {
        super(name);
        this.password = password;
        rules = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getRules() {
        return rules;
    }
}
