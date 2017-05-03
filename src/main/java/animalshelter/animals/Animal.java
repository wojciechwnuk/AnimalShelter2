package animalshelter.animals;

import java.io.Serializable;


public abstract class Animal implements Serializable {

    private String type;



    private String mass;
    private String name;
    private String health;

    Animal(String type, String mass, String name, String health) {
        this.mass = mass;
        this.name = name;
        this.health = health;
        this.type=type;
            }

    public Animal() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }
}
