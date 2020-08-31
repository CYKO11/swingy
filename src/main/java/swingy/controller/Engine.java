package swingy.controller;

import swingy.model.*;
import swingy.view.*;

import java.io.IOException;
import java.sql.SQLOutput;

public class Engine {
    private GameData gameData = new GameData();
    private Renderer gameRenderer = new Renderer();

    public void init() throws IOException {
        System.out.println("Welcome to WOW");
        menu();
    }
    void menu() throws IOException {
        String in = gameRenderer.terminalRender(
                "Main menu\nLoad (load)\nNew game (new)",
                new String[]{"load","new"},
                1
        );
        if (in.equals("load")){
            load();
        } else {
            create();
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
    void load(){}
    void save(){}
    void preGame(){
        Hero hero = gameData.getHero();
        System.out.print("Hero " + hero.getName() + "\n");
        System.out.print("Stats " + hero.getStats().getXp() + "\n");
    }
    void game(){}
}