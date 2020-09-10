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

//    private JPanel          Main;
//    private JPanel          Main2;
    private JPanel          MainBtns;
    private JLabel          Map;
    private JButton         South, East, North, West;
    private CombatReport    combatreport;
    private ActionEngine    game;

    public MapFrame(ActionEngine gameEngine) {

        game = gameEngine;
        this.setTitle("World of Anime");
        this.setSize(550, 550);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new GridLayout(2,1));

//        Main = new JPanel(new GridLayout(1, 1));
//        Main2 = new JPanel(new GridLayout(1, 1));
        MainBtns = new JPanel();
        South = new JButton("Move: South [s]");
        West = new JButton("Move: West [a]");
        North = new JButton("Move: North [w]");
        East = new JButton("Move: East [d]");

        System.out.println("Y: "+game.getWorld().boundsY);
        System.out.println("X: "+game.getWorld().boundsX);
        System.out.println("new Y: "+game.getWorld().boundsY*20);
        System.out.println("new X: "+game.getWorld().boundsX*20);

        if (game.getWorld().boundsY > 15)
            this.setSize(1500, 1500);

        /// Set Map
        Map = new JLabel("No map generated");
        if (gameEngine.getWorld().exportMapHtml() != null)
            Map.setText(gameEngine.getWorld().exportMapHtml());

        //Graph layout
        MainBtns.add(West);
        MainBtns.add(North);
        MainBtns.add(South);
        MainBtns.add(East);
        Map.setHorizontalAlignment(SwingConstants.CENTER);
        Map.setVerticalAlignment(SwingConstants.CENTER);
//        Main2.add(new JLabel(""));
        this.add(Map);
//        Main2.add(new JLabel(""));
        this.add(MainBtns);
//        this.add(Main2);
//        this.add(Main);

        North.addActionListener(this);
        East.addActionListener(this);
        South.addActionListener(this);
        West.addActionListener(this);

        this.setResizable(false);
        this.setVisible(true);
    }

    public void combatSimulator(int i){
        if (i == 1){
            if (combatreport.result == null){
                JOptionPane.showMessageDialog(null,"YOU DIED...");
                try {
                    setVisible(false); //you can't see me!
                    dispose(); //Destroy the JFrame object
                    new MainMenu(game);
                } catch (IOException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
            else {
                game.getGameData().setTmpHero(combatreport.result);
                game.getWorld().defeatEnemy(combatreport.enemy);
                JOptionPane.showMessageDialog(null,"You won the battle");
                int n = JOptionPane.showConfirmDialog(null,"You have found "+combatreport.drop.getName()+" do you wish to keep it?","Loot",JOptionPane.YES_NO_OPTION );
                if (n == 0){
                    game.getGameData().getTmpHero().updateBackPack(combatreport.drop);
                }
                this.Map.setText(game.getWorld().exportMapHtml());
            }
        }
        else {
            if (combatreport.escape){
                JOptionPane.showMessageDialog(null,"You have successfully run like a little bitch... Subaru");
            }
            else {
                JOptionPane.showMessageDialog(null, "Fight or die there is no escape");
                combatSimulator(1);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ae = e.getSource();

        if (ae.equals(this.North)){
            System.out.println("Move action North");
            this.combatreport = game.preMove("n");
            if (combatreport.combat){
                int n = JOptionPane.showConfirmDialog(null,"You have encountered and enemy! Do you wish to Fight?","Fight or die",JOptionPane.YES_NO_OPTION );
                if (n == 0){
                    combatSimulator(1);
                }
                else {
                    combatSimulator(0);
                }
            }
            else {
                System.out.println("Moving North");
                game.move("n", combatreport);
                if (combatreport.proceed) {
                    game.genWorld(game.getGameData().getTmpHero());
                    JOptionPane.showMessageDialog(null,"You have proceeded to the next stage");
                }
                this.Map.setText(game.getWorld().exportMapHtml());
            }
        }
        else if (ae.equals(this.East)){
            System.out.println("Move action East");
            this.combatreport = game.preMove("e");
            if (combatreport.combat){
                int n = JOptionPane.showConfirmDialog(null,"You have encountered and enemy! Do you wish to Fight?","Fight or die",JOptionPane.YES_NO_OPTION );
                if (n == 0){
                    combatSimulator(1);
                }
                else {
                    combatSimulator(0);
                }
            }
            else {
                System.out.println("Moving East");
                game.move("e", combatreport);
                if (combatreport.proceed) {
                    game.genWorld(game.getGameData().getTmpHero());
                    JOptionPane.showMessageDialog(null,"You have proceeded to the next stage");
                }
                this.Map.setText(game.getWorld().exportMapHtml());
            }
        }
        else if (ae.equals(this.South)){
            System.out.println("Move action South");
            this.combatreport = game.preMove("s");
            if (combatreport.combat){
                int n = JOptionPane.showConfirmDialog(null,"You have encountered and enemy! Do you wish to Fight?","Fight or die",JOptionPane.YES_NO_OPTION );
                if (n == 0){
                    combatSimulator(1);
                }
                else {
                    combatSimulator(0);
                }
            }
            else {
                System.out.println("Moving South");
                game.move("s", combatreport);
                if (combatreport.proceed) {
                    game.genWorld(game.getGameData().getTmpHero());
                    JOptionPane.showMessageDialog(null,"You have proceeded to the next stage");
                }
                this.Map.setText(game.getWorld().exportMapHtml());
            }
        }
        else if (ae.equals(this.West)){
            System.out.println("Move action West");
            this.combatreport = game.preMove("w");
            if (combatreport.combat){
                int n = JOptionPane.showConfirmDialog(null,"You have encountered and enemy! Do you wish to Fight?","Fight or die",JOptionPane.YES_NO_OPTION );
                if (n == 0){
                    combatSimulator(1);
                }
                else {
                    combatSimulator(0);
                }
            }
            else {
                System.out.println("Moving West");
                game.move("w", combatreport);
                if (combatreport.proceed) {
                    game.genWorld(game.getGameData().getTmpHero());
                    JOptionPane.showMessageDialog(null,"You have proceeded to the next stage");
                }
                this.Map.setText(game.getWorld().exportMapHtml());
            }
        }

    }
}
