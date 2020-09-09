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

    public boolean isInBackpack(String item){
        int pos = 0;
        while (pos < tmpHero.getBackPack().size()){
            if (item.equals(tmpHero.getBackPack().get(pos).getName()))
                return true;
            pos++;
        }
        return false;
    }
    public boolean isEquiped(String item){
        int pos = 0;
        while (pos < tmpHero.getEquipped().size()){
            if (item.equals(tmpHero.getEquipped().get(pos).getName()))
                return true;
            pos++;
        }
        return false;
    }

    public void equipItem(String item){
        Artifact itemObject = null;
        int pos = 0;
        while (pos < tmpHero.getBackPack().size()){
            if (item.equals(tmpHero.getBackPack().get(pos).getName())){
                itemObject = tmpHero.getBackPack().get(pos);
            }
            pos++;
        }
        pos = 0;
        while (pos < tmpHero.getEquipped().size()){
            if (tmpHero.getEquipped().get(pos).getClass().equals(itemObject.getClass()))
                tmpHero.unequipItem(tmpHero.getEquipped().get(pos));
            pos++;
        }
        tmpHero.equipItem(itemObject);
    }
}