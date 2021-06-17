package com.company;

import javax.swing.*;
import java.awt.*;

public class plateau extends JFrame  {

    public JFrame frame = new JFrame();;
    public plateau(){
        JFrame frame = new JFrame();
        frame.setSize(500,500);
        JButton b1 = new JButton("Play agains't bot");
        JButton b2 = new JButton("Play agains't human");
        b1.setSize(100,100);
        b1.setVisible(true);
        frame.add(b1);

        b2.setSize(100,100);
        b2.setVisible(true);
        frame.add(b2);

        frame.setVisible(true);


/*
        Cellule Cellule = new Cellule(frame);
        Cellule.validate();
        this.setTitle("Jeux de dame");
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(pan);
        pan.setLayout(new OverlayLayout(pan));
        pan.add(blackPan);
        pan.add(redPan);
        blackPan.setBackground(Color.black);
        redPan.setBackground(Color.red);
        blackPan.setVisible(true);
        redPan.setVisible(false);

        this.setContentPane(Cellule);
        SwingUtilities.updateComponentTreeUI(frame);
        this.setVisible(true);
        */
    }

}

