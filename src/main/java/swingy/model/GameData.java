package swingy.model;

import swingy.controller.WorldGeneration;

import java.io.FileNotFoundException;

public class GameData {
    private Hero hero = null;
    public Hero tmpHero = null;

    public Hero getHero() {
        return hero;
    }

    public Hero checkLoad() throws FileNotFoundException {
        return new LoadGame().load();
    }
    public void loadHero() throws FileNotFoundException {
        this.hero = new LoadGame().load();
    }
    public Hero getTmpHero(){
        return tmpHero;
    }
    public void setTmpHero(Hero tmpHero) { this.tmpHero = tmpHero; }
    public void mirrorHero(){
        tmpHero = hero;
    }

    public void saveHero(){
        new SaveGame().saveData(hero);
    }

    public void createHero(String name, String heroClass){
        hero = new Hero(name, heroClass);
    }
}