package swingy.model;

public class GameData {
    private Hero hero = null;

    public Hero getHero() {
        return hero;
    }

    public void loadHero(){}

    public void createHero(String name, String heroClass){
        hero = new Hero(name, heroClass);
    }
}
