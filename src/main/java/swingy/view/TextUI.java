package swingy.view;

import swingy.controller.ActionEngine;
import swingy.controller.CombatReport;
import swingy.model.MapData;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;

public class TextUI {
    ActionEngine gameEngine;
    Renderer terminal = new Renderer();

    public TextUI(ActionEngine gameEngine){
        this.gameEngine = gameEngine;
    }

    public void init() throws IOException {
        terminal.out("<<< Welcome to UWU >>>");
        menu();
    }

    private void menu() throws IOException {
        String in = terminal.render(
                " < Menu > \n(l): load game\n(n): new game\n(e): exit",
                new String[]{"l","n","e"},
                1
        );
        if (in.equals("l")){
            gameEngine.loadSave();
            preGame();
        } else if (in.equals("n")) {
            createHero();
            preGame();
        } else if (in.equals("e")) {
            exit();
        }
    }

    private void createHero() throws IOException {
        String name = terminal.render(
                "Enter name of hero",
                new String[]{""},
                0
        );
        String heroClass = terminal.render(
                "Choose your hero class : normie,weeb,otaku or MethHead",
                new String[]{"normie","weeb","otaku","MethHead"},
                1
        );
        gameEngine.getGameData().createHero(name, heroClass);
    }

    private void preGame() throws IOException {
        terminal.out("Hero: \t" + gameEngine.getGameData().getHero().getName());
        terminal.out("Class: \t" + gameEngine.getGameData().getHero().getHeroClass());
        terminal.out("XP: \t" + gameEngine.getGameData().getHero().getStats().getXp());
        String in = terminal.render(
                "Do you wish to proceed with this hero?(y,n)",
                new String[]{"y","n"},
                1
        );
        if (in.equals("y")){
            gameEngine.init();
            game();
        } else {
            menu();
        }
    }

    private void game() throws IOException {
        terminal.renderMap(gameEngine.getWorld());
        String in = terminal.render(
                "Move (n,s,e,w)",
                new String[]{"n","s","e","w","menu","i"},
                1
        );
        if (in.equals("menu")){

        } else if (in.equals("i")) {

        } else {
            CombatReport report = gameEngine.preMove(in);
            if (report.combat){
                in = terminal.render(
                        " << You have encountered an enemy >>\n\t(c): Cower and Run\n\t(f): Stand and Fight",
                        new String[]{"c","f"},
                        1
                );
                if (in.equals("c") && report.escape){
                    terminal.outAwait("\n <<< You live another day >>> \n  > press Enter to continue");
                } else {
                    terminal.outAwait("\n <<< Do or die >>> \n  > press Enter to proceed");
                    fight(report);
                }
            } else {
                gameEngine.move(in, report);
            }
            game();
        }
    }

    private void fight(CombatReport report) throws IOException {
        if (report.result == null){
            die();
        } else {
            gameEngine.getGameData().setTmpHero(report.result);
            gameEngine.getWorld().defeatEnemy(report.enemy);
            terminal.out("You Survived the battle with " + gameEngine.getGameData().tmpHero.getStats().getHP() + " HP");
            System.out.println("You have found: " + report.drop.getName());
            String in = terminal.render(
                    "Do you wish to add this item to your inventory",
                    new String[]{"y","n"},
                    1
            );
            if (in.equals("y")){
                gameEngine.getGameData().getTmpHero().updateBackPack(report.drop);
            }
            game();
        }
    }

    private void die(){
        System.out.println(" << YOU DIED >>");
    }

    private void exit(){
        return;
    }
}

