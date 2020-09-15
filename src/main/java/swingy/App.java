package swingy;

import swingy.controller.ActionEngine;
import swingy.view.MainMenuGUI;
import swingy.view.MainMenuText;
import swingy.view.TextUI;

import java.io.IOException;

/**
 * Hello world!
 *
*/
public class App {
    public static void main( String[] args ) {
        ActionEngine gameEngine =  new ActionEngine();
        try{
            if (args.length == 1){
                if (args[0].equals("text"))
                    new MainMenuText(gameEngine);
                else if (args[0].equals("gui"))
                    new MainMenuGUI(gameEngine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
