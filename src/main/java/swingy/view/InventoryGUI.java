package swingy.view;

import swingy.controller.ActionEngine;
import swingy.model.Artifact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class InventoryGUI extends JFrame implements ActionListener {

    private JPanel          mainInv;
    private JPanel          btns;
    private JLabel          title;
    private JPanel          item;
    private JButton         equip, remove, next, prev, back;
    private JLabel          itemName;
    private JPanel          itemStats;
    private ActionEngine    game;
    private long            atk;
    private int             armor;
    private long            hp;
    private int             index = 0;
    private Artifact        backPack;

    public InventoryGUI(ActionEngine gameEngine){

        this.game = gameEngine;
        Color myColor = new Color(90,151,255);
        this.setTitle("World of Anime");
        this.getContentPane().setBackground(Color.BLUE);
        this.setSize(450, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setLayout(new GridLayout(3,1));

        mainInv = new JPanel(new GridLayout(1, 2));
        btns = new JPanel(new GridLayout(2, 2));
        item = new JPanel(new GridLayout(1,2));
        equip = new JButton("Equip");
        title = new JLabel("Inventory");
        remove = new JButton("Remove");
        back = new JButton("Back");
        next = new JButton("Next");
        prev = new JButton("Prev");
        if (game.getGameData().getTmpHero().getBackPack().size() != 0){
            backPack = game.getGameData().getTmpHero().getBackPack().get(this.index);
            itemName = new JLabel(backPack.getName());
            atk = backPack.getDamage();
            armor = backPack.getArmour();
            hp = backPack.getHp();
        }
        else {
            itemName = new JLabel("No items available");
            atk = 0;
            armor = 0;
            hp = 0;
        }
        itemStats = new JPanel(new GridLayout(3,1));

        mainInv.setBackground(myColor);
        btns.setBackground(myColor);
        title.setForeground(Color.WHITE);

        title.setHorizontalAlignment(SwingConstants.CENTER);

        itemStats.add(new JLabel("Atk: "+atk));
        itemStats.add(new JLabel("Armor: "+armor));
        itemStats.add(new JLabel("HP: "+hp));
        item.add(itemName);
        item.add(itemStats);
        mainInv.add(item);
        btns.add(prev);
        btns.add(next);
        btns.add(remove);
        btns.add(equip);
        this.add(title);
        this.add(mainInv);
        this.add(btns);

        prev.addActionListener(this);
        next.addActionListener(this);
        remove.addActionListener(this);
        equip.addActionListener(this);
        back.addActionListener(this);

        this.setResizable(false);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ae = e.getSource();
        List<Artifact> bag = game.getGameData().getTmpHero().getBackPack();
        int listSize = bag.size();

        if (ae.equals(this.next)){
            if (index < listSize){
                this.index += 1;
            }
            else {
                this.index = 0;
            }
            this.backPack = bag.get(this.index);
        }
        else if (ae.equals(this.prev)){
            if (index >= 0)
                index = listSize-1;
            else
                index -= 1;
            this.backPack = bag.get(this.index);
        }
        else if (ae.equals(this.remove)){
            bag.remove(this.index);
            this.backPack = bag.get(this.index);
        }
        else if (ae.equals(this.equip)){
            game.getGameData().getTmpHero().equipItem(bag.get(this.index));
            JOptionPane.showMessageDialog(null,"You have equipped "+bag.get(this.index).getName());
        }
//        else if (ae.equals(this.back)){
//            setVisible(false);
//            dispose();
//        }
    }
}
