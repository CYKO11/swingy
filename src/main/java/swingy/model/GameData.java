package swingy.model;

public class GameData {
    private Hero hero = null;

    public Hero getHero() {
        return hero;
    }

    public void loadHero(){}

    public void createHero(String name, String heroClass){
        System.out.println("<<<<<<<<<Creating hero>>>>>>>");
        System.out.println(name);
        System.out.println(heroClass);
        System.out.println("<<<<<<<<<Hero Created>>>>>>>>");
        hero = new Hero(name, heroClass);
    }
}
