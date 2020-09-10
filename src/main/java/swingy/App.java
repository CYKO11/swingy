package swingy;

import swingy.controller.ActionEngine;
import swingy.view.TextUI;

import java.io.IOException;

/**
 * Hello world!
 *
*/
public class App {
    public static void main( String[] args ) {
        ActionEngine gameEngine =  new ActionEngine();
        TextUI textGame = new TextUI(gameEngine);
        try {
            textGame.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
