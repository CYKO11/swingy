package swingy.view;

import swingy.controller.ActionEngine;

import java.io.IOException;

public class MainMenuText {
    public void menu(ActionEngine gameEngine,String mode) throws IOException {
        if (mode.equals("text")) {
            String in = new Renderer().render(
                    " < Menu > \n(l): load game\n(n): new game\n(e): exit\n(g): gui",
                    new String[]{"l","n","e","g"},
                    1
            );
            if (in.equals("l")){
    //            gameEngine.loadSave();
    //            preGame();
            } else if (in.equals("n")) {
    //            createHero();
    //            preGame();
            } else if (in.equals("e")) {
    //            exit();
            } else if (in.equals("g")) {
                new MainMenuGUI(gameEngine);
            }
        } else if (mode.equals("g")) {
            new MainMenuGUI(gameEngine);
        }
    }
}
