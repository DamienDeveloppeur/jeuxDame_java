package com.company;

import javax.swing.*;
import java.awt.*;

public class plateau extends JFrame  {

    public plateau(){
        Dimension ech = new Dimension();
        JFrame frame = new JFrame();
        Cellule Cellule = new Cellule(frame);
        Cellule.validate();
        frame.add(Cellule);
       // frame.getContentPane().validate();
        //frame.getContentPane().repaint();
        this.setTitle("Jeux de dame");
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(Cellule);
        SwingUtilities.updateComponentTreeUI(frame);
        this.setVisible(true);
        System.out.println("plateau");
    }
}
