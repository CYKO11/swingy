package swingy.controller;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import swingy.model.*;
import swingy.view.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Engine {
    private GameData gameData = new GameData();
    private Renderer gameRenderer = new Renderer();
    private Interaction interactionEngine = new Interaction();
    private WorldGeneration world = null;

    public void init() throws IOException {
        gameRenderer.terminalOut("Welcome to WOW");
        menu();
    }

    void menu() throws IOException {
        String in = gameRenderer.terminalRender(
                "\n <<<< Main menu >>>> \n\n Select an option:" +
                        "\n(l): Load Save\n(n): New game\n(e): exit",
                new String[]{"l","n","e"},
                1
        );
        switch (in) {
            case "l" : load();
                        break;
            case "n" : create();
                        break;
            case "e" : exit();
        }
    }

    void create() throws IOException {
        String name = gameRenderer.terminalRender(
                "Enter name of hero",
                new String[]{""},
                0
        );
        String heroClass = gameRenderer.terminalRender(
                "Choose your hero class : normie,weeb,otaku or MethHead",
                new String[]{"normie","weeb","otaku","MethHead"},
                1
        );
        gameData.createHero(name, heroClass);
        preGame();
    }

    void load() throws IOException {
        Hero load = gameData.checkLoad();
        if (load == null){
            gameRenderer.terminalOut("No previously saved game");
            menu();
        } else {
            gameData.loadHero();
            preGame();
        }
    }

    void save() throws IOException {
        String in = gameRenderer.terminalRender(
                "Do you wish save your current progress(y,n)",
                new String[]{"y","n"},
                1
        );
        if (in.equals("y")){
            gameData.saveHero();
        }
    }

    void preGame() throws IOException {
        gameRenderer.terminalOut("Hero: \t" + gameData.getHero().getName());
        gameRenderer.terminalOut("Class: \t" + gameData.getHero().getHeroClass());
        gameRenderer.terminalOut("XP: \t" + gameData.getHero().getStats().getXp());
        String in = gameRenderer.terminalRender(
                "Do you wish to proceed with this hero?(y,n)",
                new String[]{"y","n"},
                1
        );
        if (in.equals("y")){
            game();
        } else {
            menu();
        }
    }

    void inGameMenu() throws IOException {
          String in = gameRenderer.terminalRender(
                  "Menu\nsave (save)\nexit game (exit)\nReturn (r)",
                  new String[]{"save","exit", "r"},
                  1
          );
          if (in.equals("save")){
              in = gameRenderer.terminalRender(
                      "Do you wish to save your Previous Checkpoint?(y,n)",
                      new String[]{"y","n"},
                      1
              );
              if (in.equals("y"))
                  gameData.saveHero();
              inGameMenu();
          } else if (in.equals("exit")) {
              world = null;
              menu();
          } else if (in.equals("r")) {
              game();
          }
    }
    void game() throws IOException {
        // for later
        if (world == null){
            gameData.mirrorHero();
            world = new WorldGeneration();
            world.generateWorld(gameData.getTmpHero());
        }
        move();
    }
    void move() throws IOException {
        world.printWorld();
        String in = gameRenderer.terminalRender(
                "Move (n,s,e,w)",
                new String[]{"n","s","e","w","menu","i"},
                1);
        if (in.equals("menu")) {
            inGameMenu();
        } else if (in.equals("i")){
            inventory();
            move();
        } else {
            if (world.ghostMove(in) != null){
                // interaction
                int combatResult = combat(world.ghostMove(in));
                switch (combatResult){
                    case 0 : die();
                            break;

                    case 1 : drop();
                            world.defeatEnemy(world.ghostMove(in));
                            if (world.move(in))
                                nextLevel();
                            else
                                move();
                            break;
                    case 2 : move();
                            break;
                }
            } else {
                if (world.move(in))
                    nextLevel();
                move();
            }
        }
    }

    int combat(Enemy enemy) throws IOException {
        String in = gameRenderer.terminalRender(
                " << You have encountered an enemy >>\n\t(c): Cower and Run\n\t(f): Stand and Fight",
                new String[]{"c","f"},
                1
        );
        switch (in){
            case "c" : if (interactionEngine.runAway()){
                            gameRenderer.terminalOutAwait("\n <<< You live another day >>> \n  > press Enter to continue");
                            return 2;
                        } else {
                            gameRenderer.terminalOutAwait("\n <<< Do or die >>> \n  > press Enter to proceed");
                            return fight(enemy);
                        }
            case "f": return fight(enemy);
        }
        return 1;
    }

    int fight(Enemy enemy) throws IOException {
        if (interactionEngine.combat(gameData.tmpHero, enemy) == null)
            return 0;
        return 1;
    }

    void drop() throws IOException {
        Artifact drop = new Artifact("");
        System.out.println("You have found: " + drop.getName());
        String in = gameRenderer.terminalRender(
                "Do you wish to add this item to your inventory",
                new String[]{"y","n"},
                1
        );
        if (in.equals("y")){
            gameData.getTmpHero().updateBackPack(drop);
        }
    }

    void inventory() throws IOException {
        gameRenderer.terminalOut(" <<< Inventory >>> ");
        int pos = 0;
        while (pos < 20) {
            if (pos < gameData.getTmpHero().getBackPack().size())
                gameRenderer.terminalOut("\titem: " + formatItem(gameData.getTmpHero().getBackPack().get(pos)));
            pos++;
        }
        gameRenderer.terminalOut("\n <<< Equiped >>> ");
        pos = 0;
        if (gameData.getTmpHero().getEquipped().size() > 0){
            while (pos < 3){
                if (pos < gameData.getTmpHero().getEquipped().size())
                    gameRenderer.terminalOut("\titem: " + formatItem(gameData.getTmpHero().getEquipped().get(pos)));
                pos++;
            }
        } else {
            gameRenderer.terminalOut("\t No items equipped");
        }
        String in = gameRenderer.terminalRender(
                "\n(e): Equip item\n(u): Unequip item \n(d): Drop Item\n(r): Return to game",
                new String[]{"e","u","d","r"},
                1
        );
        if (!in.equals("r")){
            String item = gameRenderer.terminalRender(
                        "Name the item you wish to " + in + " (x) to cancel",
                        artifactList(gameData.getTmpHero().getBackPack(), gameData.getTmpHero().getEquipped()),
                        1
            );
            if (item.equals("x")){
                inventory();
            } else if (item.equals("equip")){
                if (gameData.isEquiped(item)){
                    gameRenderer.terminalOut("Item is already equipped");
                    inventory();
                } else {
                    gameData.equipItem(item);
                }
            }
        } else {
            move();
        }
    }

    private String formatItem(Artifact item){
        String newFormat = item.getName() + " >>> HP:" + item.getHp() + " ATK:" + item.getDamage() + " ARM:" + item.getArmour();
        return newFormat;
    }

    private String[] artifactList(List<Artifact> artifactList, List<Artifact> equipped){
        List<String> newList = new ArrayList<>();
        int pos = 0;
        while (pos < artifactList.size()){
            newList.add(artifactList.get(pos).getName().toLowerCase());
            pos++;
        }
        pos = 0;
        while (pos < equipped.size()){
            newList.add(artifactList.get(pos).getName().toLowerCase());
            pos++;
        }
        newList.add("x");
        return newList.toArray(new String[artifactList.size()]);
    }

    void nextLevel() throws IOException {
        world.generateWorld(gameData.getTmpHero());
        save();
        move();
    }

    void die() throws IOException {
        gameData.mirrorHero();
        world = null;
        System.out.println(" <<< you died >>> ");
        menu();
    }

    void exit() throws IOException {
        if (gameData.getHero() != null)
            save();
        return;
    }
}