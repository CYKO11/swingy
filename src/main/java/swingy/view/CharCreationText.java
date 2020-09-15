package swingy.view;

import swingy.controller.ActionEngine;

import java.io.IOException;

public class CharCreationText {
    public CharCreationText(ActionEngine gameEngine) throws IOException {
        String name = new TextRenderer().render(
                "Enter name of hero",
                new String[]{""},
                0
        );
        String heroClass = new TextRenderer().render(
                "Choose your hero class : normie,weeb,otaku or MethHead",
                new String[]{"normie","weeb","otaku","MethHead"},
                1
        );
        gameEngine.getGameData().createHero(name, heroClass);
    }
}
