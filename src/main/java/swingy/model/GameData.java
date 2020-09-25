package swingy.model;

import swingy.controller.WorldGeneration;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;

public class GameData {
    private Hero hero = null;
    public Hero tmpHero = null;
    public Hero loadBuffer = null;

    public Hero getHero() {
        return hero;
    }

    public Hero getTmpHero() { return tmpHero; }

    public void setTmpHero(Hero hero){
        this.tmpHero = hero;
    }

    public void duplicateHero(){ this.tmpHero = hero.duplicateHero(); }

    public void createHero(String name, String heroClass){
        hero = new Hero(name, heroClass);
    }

    public boolean checkLoad() {
        try {
            this.loadBuffer = new LoadGame().load();
            if (loadBuffer == null)
                return false;
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void nextMap(){
        tmpHero.resetHealth();
        hero = tmpHero.duplicateHero();
        saveHero();
    }

    public void loadHero() { hero = loadBuffer.duplicateHero(); }

    public void saveHero(){ new SaveGame().saveData(hero); }

    public void saveState() {
        Hero state = tmpHero.duplicateHero();
        state.resetHealth();
        new SaveGame().saveData(state);
    }

}