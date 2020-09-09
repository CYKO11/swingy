package swingy.controller;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import swingy.model.*;
import swingy.view.*;

import java.io.IOException;

public class Engine {
    private GameData gameData = new GameData();
    private Renderer gameRenderer = new Renderer();
    private Interaction interactionEngine = new Interaction();
    private WorldGeneration world = null;

    public void init() throws IOException {
        System.out.println("Welcome to WOW");
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
        String in;
        if (load == null){
            System.out.println("No previously saved game");
            menu();
        } else {
            in = gameRenderer.terminalRender(
                "Do you wish to load your previous game(y,n)",
                new String[]{"y","n"},
                1);
            if (in.equals("n")){
                menu();
            } else if (in.equals("y")){
                gameData.loadHero();
                preGame();
            }
        }

    }
    void save() throws IOException {
        String in = gameRenderer.terminalRender(
                "Do you wish save your current game(y,n)",
                new String[]{"y","n"},
                1
        );
        if (in.equals("y")){
            gameData.saveHero();
        } else if (in.equals("n")) {
            menu();
        }
    }
    void preGame() throws IOException {
        System.out.print("Current Hero " + gameData.getHero().getName() + "\n");
        System.out.print("Class " + gameData.getHero().getClass() + "\n");
        System.out.print("XP " + gameData.getHero().getStats().getXp() + "\n");
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
            System.out.println("New world Generated");
        }
        move();
    }
    void move() throws IOException {
        world.printWorld();
        String in = gameRenderer.terminalRender(
                "Move (n,s,e,w)",
                new String[]{"n","s","e","w","menu"},
                1);
        if (in.equals("menu")){
            inGameMenu();
        } else {
            System.out.println("not menu");
            if (world.ghostMove(in) != null){
                // interaction
                System.out.println("interactions");
                if (combat(world.ghostMove(in))){
                    drop();
                    world.defeatEnemy(world.ghostMove(in));
                    if (world.move(in))
                        nextLevel();
                    move();
                } else {
                    die();
                }
            } else {
                System.out.println("No enemies encountered");
                if (world.move(in))
                    nextLevel();
                move();
            }
        }
    }

    boolean combat(Enemy enemy) throws IOException {
        String in = gameRenderer.terminalRender(
                "You have encountered an enemy\n Run away (r) or stay and fight (f)",
                new String[]{"r","f"},
                1
        );
        if (in.equals("r")){
            if (interactionEngine.runAway()){
                System.out.println("You have escaped");
                return true;
            } else {
                System.out.println("You will not run from this fight");
                return fight(enemy);
            }
        } else if (in.equals("f")){
            return fight(enemy);
        }
        return true;
    }

    boolean fight(Enemy enemy) throws IOException {
        if (interactionEngine.combat(gameData.tmpHero, enemy) == null)
            return false;
        return true;
    }

    void drop() throws IOException {
        System.out.println("You have found");
        Artifact drop = new Artifact("");
        System.out.println(drop.getName());
        String in = gameRenderer.terminalRender(
                "Do you wish to add this item to your inventory",
                new String[]{"y","n"},
                1
        );
        if (in.equals("y")){
            gameData.getTmpHero().updateBackPack(drop);
        }
    }

    void nextLevel() throws IOException {
        world.generateWorld(gameData.getTmpHero());
        move();
    }

    void die() throws IOException {
        gameData.mirrorHero();
        world = null;
        System.out.println("you died");
        menu();
    }

    void exit() throws IOException {
        if (gameData.getHero() != null)
            save();
        return;
    }
}