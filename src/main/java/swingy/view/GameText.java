package swingy.view;

import swingy.controller.ActionEngine;
import swingy.controller.CombatReport;

import java.io.IOException;

public class GameText {
    public GameText(ActionEngine gameEngine) throws IOException {
        new TextRenderer().renderMap(gameEngine.getWorld());
        String in = new TextRenderer().render(
                "Move (n,s,e,w)",
                new String[]{"n","s","e","w","gui","exit"},
                1
        );
        if (in.equals("exit")){
            new MainMenuText(gameEngine);
        } else if (in.equals("i")) {
//            inventory
        } else if (in.equals("gui")) {
            new GameGUI(gameEngine);
        } else {
            CombatReport report = gameEngine.preMove(in);
            System.out.println(report.proceed);
            if (report.proceed) {
                gameEngine.genWorld(gameEngine.getGameData().tmpHero);
                new GameText(gameEngine);
            } else if (report.combat){
                in = new TextRenderer().render(
                        " << You have encountered an enemy >>\n\t(c): Cower and Run\n\t(f): Stand and Fight",
                        new String[]{"c","f"},
                        1
                );
                if (in.equals("c") && report.escape){
                    new TextRenderer().outAwait("\n <<< You live another day >>> \n  > press Enter to continue");
                } else {
                    new TextRenderer().outAwait("\n <<< Do or die >>> \n  > press Enter to proceed");
                    fight(report, gameEngine);
                }
            } else {
                gameEngine.move(in, report);
            }
            new GameText(gameEngine);
        }
    }

    private void fight(CombatReport report, ActionEngine gameEngine) throws IOException {
        if (report.result == null){
            die();
        } else {
            gameEngine.getGameData().setTmpHero(report.result);
            gameEngine.getWorld().defeatEnemy(report.enemy);
            new TextRenderer().out("You Survived the battle with " + gameEngine.getGameData().tmpHero.getStats().getHP() + " HP");
            System.out.println("You have found: " + report.drop.getName());
            String in = new TextRenderer().render(
                    "Do you wish to add this item to your inventory",
                    new String[]{"y","n"},
                    1
            );
            if (in.equals("y")){
                gameEngine.getGameData().getTmpHero().updateBackPack(report.drop);
            }
        }
    }
    private void die(){
        System.out.println(" << YOU DIED >>");
    }
}
