package chapter4;

import chapter6.Chapter6;

public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    public enum Type {
        MEAT, FISH, OTHER
    }

    public Chapter6.CaloricLevel getCaloricLevel() {
        if (this.getCalories() < 400) return Chapter6.CaloricLevel.DIET;
        else if (this.getCalories() < 700) return Chapter6.CaloricLevel.NORMAL;
        else return Chapter6.CaloricLevel.FAT;
    }

}