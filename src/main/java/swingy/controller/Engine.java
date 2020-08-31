package swingy.controller;

import swingy.model.*;
import swingy.view.*;

import java.io.IOException;
import java.sql.SQLOutput;

public class Engine {
    private GameData gameData = new GameData();
    private Renderer gameRenderer = new Renderer();

    public void init() throws IOException {
        String in = gameRenderer.terminalRender(
                "Welcome to wow\n" + "load game or start new options: load , new",
                new String[]{"load", "new"},
                1
        );
        if (in.equals("new"))
            create();
        else if (in.equals("load"))
            load();
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
        echoStats();
    }
    void load(){}
    void save(){}
    void game(){}
    void echoStats(){
        System.out.println(gameData.getHero().getName());
        System.out.println(gameData.getHero().getHeroClass());
    }
}