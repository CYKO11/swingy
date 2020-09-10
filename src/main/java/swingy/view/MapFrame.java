package swingy.view;

import swingy.controller.ActionEngine;
import swingy.controller.CombatReport;
import swingy.controller.WorldGeneration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MapFrame extends JFrame implements ActionListener {

    private JPanel          Main;
    private JPanel          MainBtns;
    private JLabel          Map;
    private JButton         South, East, North, West, Fight, Run;
    private Boolean         combat = false;
    private CombatReport    combatreport;
    private ActionEngine    game;

    public MapFrame(ActionEngine gameEngine) {

        game = gameEngine;
        this.setTitle("World of Anime");
        this.setSize(650, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new GridLayout());

        Main = new JPanel(new GridLayout(2, 1));
        MainBtns = new JPanel(new GridLayout(2,3));
        South = new JButton("Move: South");
        East = new JButton("Move: East");
        North = new JButton("Move: North");
        West = new JButton("Move: West");
        Fight = new JButton("Action: Fight");
        Run = new JButton("Action: Run");

        /// Set Map
        Map = new JLabel("No map generated");
        if (gameEngine.getWorld().exportMapHtml() != null)
            Map.setText(gameEngine.getWorld().exportMapHtml());

        //Graph layout
        MainBtns.add(North);
        MainBtns.add(East);
        MainBtns.add(Fight);
        MainBtns.add(South);
        MainBtns.add(West);
        MainBtns.add(Run);
        Main.add(Map);
        Main.add(MainBtns);
        this.add(Main);

        North.addActionListener(this);
        East.addActionListener(this);
        South.addActionListener(this);
        West.addActionListener(this);
        Fight.addActionListener(this);
        Run.addActionListener(this);

        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ae = e.getSource();

        if (ae.equals(this.North)){
            System.out.println("Move action North");
            this.combatreport = game.preMove("n");
            if (combatreport.combat){
                this.combat = true;
                this.Map.setText("You have encountered and enemy!\n Do you wish to Fight like a warrior or Flee like a bitch?");
            }
            else {
                System.out.println("Moving North");
                game.move("n", combatreport);
            }
            this.Map.setText(game.getWorld().exportMapHtml());
        }
        else if (ae.equals(this.East)){
            System.out.println("Move action East");
            this.combatreport = game.preMove("e");
            if (combatreport.combat){
                this.combat = true;
                this.Map.setText("You have encountered and enemy!\n Do you wish to Fight like a warrior or Flee like a bitch?");
            }
            else {
                System.out.println("Moving East");
                game.move("e", combatreport);
            }
            this.Map.setText(game.getWorld().exportMapHtml());
        }
        else if (ae.equals(this.South)){
            System.out.println("Move action South");
            this.combatreport = game.preMove("s");
            if (combatreport.combat){
                this.combat = true;
                this.Map.setText("You have encountered and enemy!\n Do you wish to Fight like a warrior or Flee like a bitch?");
            }
            else {
                System.out.println("Moving South");
                game.move("s", combatreport);
            }
            this.Map.setText(game.getWorld().exportMapHtml());
        }
        else if (ae.equals(this.West)){
            System.out.println("Move action West");
            this.combatreport = game.preMove("w");
            if (combatreport.combat){
                this.combat = true;
                this.Map.setText("You have encountered and enemy!\n Do you wish to Fight like a warrior or Flee like a bitch?");
            }
            else {
                System.out.println("Moving West");
                game.move("w", combatreport);
            }
            this.Map.setText(game.getWorld().exportMapHtml());
        }
        else if (ae.equals(this.Fight)){
            if (combat){
                if (combatreport.result == null){
                    this.Map.setText("YOU DIED...");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                        setVisible(false); //you can't see me!
                        dispose(); //Destroy the JFrame object
                        new MainMenu(game);
                    } catch (InterruptedException | IOException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
                else {
                    game.getGameData().setTmpHero(combatreport.result);
                    game.getWorld().defeatEnemy(combatreport.enemy);
                    this.Map.setText("You won the battle");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    this.Map.setText(game.getWorld().exportMapHtml());
                }
            }
            else {
                this.Map.setText("Invalid Button selection");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                this.Map.setText(game.getWorld().exportMapHtml());
            }
        }
        else if (ae.equals(this.Run)){
            if (combat){
                if (combatreport.escape){
                    this.Map.setText("You have successfully run like a little bitch... Subaru");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    this.Map.setText(game.getWorld().exportMapHtml());
                }
                else {
                    this.Map.setText("Fight or die there is no escape");
                    try {
                        TimeUnit.SECONDS.sleep(3);

                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    if (combatreport.result == null){
                        this.Map.setText("YOU DIED...");
                        try {
                            TimeUnit.SECONDS.sleep(3);
                            setVisible(false); //you can't see me!
                            dispose(); //Destroy the JFrame object
                            new MainMenu(game);
                        } catch (InterruptedException | IOException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                    else {
                        game.getGameData().setTmpHero(combatreport.result);
                        game.getWorld().defeatEnemy(combatreport.enemy);
                        this.Map.setText("You won the battle");
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        this.Map.setText(game.getWorld().exportMapHtml());
                    }
                }
            }
            else {
                this.Map.setText("Invalid Button selection");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                this.Map.setText(game.getWorld().exportMapHtml());
            }
        }
    }
}
