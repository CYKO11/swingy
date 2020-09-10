package swingy.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Map extends JFrame implements ActionListener {

    private JPanel  Main;
    private JPanel  MainBtns;
    private JLabel  Map;
    private JButton South, East, North, West;

    public Map() {

        this.setTitle("World of Anime");
        this.setSize(450, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new GridLayout());

        Main = new JPanel(new GridLayout(2, 1));
        MainBtns = new JPanel(new GridLayout(2,2));
        South = new JButton("Move: South");
        East = new JButton("Move: East");
        North = new JButton("Move: North");
        West = new JButton("Move: West");


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
