package com.javapoke;

import java.util.Random;

public class Pokemon {
    // fields
    private final String name;
    private final int level;
    private int hitPoints;
    private boolean isFainted = false;
    private final String attack;
    private String art;

    // constructors
    public Pokemon(String name, int level, int hitPoints, String attack) {
        this.name = name;
        this.level = level;
        this.hitPoints = hitPoints;
        this.attack = attack;
    }

    public Pokemon(String name, int level, int hitPoints, String attack, String art) {
        this.name = name;
        this.level = level;
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.art = art;
    }

    public int attack(Pokemon other) {
        int damage = Attack.getDamage();
        other.takeDamage(damage);
        return damage;
    }

    private void takeDamage(int damage) {
        this.hitPoints -= damage;
    }

    // accessors
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public boolean isFainted() {
        return isFainted;
    }

    public void setFainted(boolean fainted) {
        isFainted = fainted;
    }

    public String getAttack() {
        return attack;
    }

    public String getArt() {
        return art;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": name=" + name + ", level=" + level + ", hitPoints=" +
                hitPoints + ", isFainted=" + isFainted + ", attack=" + attack + ", art=\n" + art;
    }

    //inner classes
    static class Attack {

        public static int getDamage() {
            Random random = new Random();
            // damage will be between 30~60, attack modifier will be for future code
            return random.nextInt(30) + 30;
        }
    }
}