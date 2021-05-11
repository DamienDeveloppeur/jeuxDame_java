package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class plateau extends JFrame  {
    Dimension ech = new Dimension();
    JFrame fenetre = new JFrame();

    public plateau(){
        this.setTitle("Jeux de dame");
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new Cellule());
        this.setVisible(true);
    }

}
