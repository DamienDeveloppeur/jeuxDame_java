package com.company;

import javax.swing.*;
import java.awt.*;

public class plateau extends JFrame  {
    private JPanel pan=new JPanel();
    private JPanel blackPan=new JPanel();
    private JPanel redPan=new JPanel();
    public JFrame frame = new JFrame();;
    public plateau(){
        JFrame f=new JFrame("Button Example");
        JButton b1=new JButton("Play agains't human ?");
        JButton b2=new JButton("Play agains't bot ?");
        b1.setBounds(50,10,200,30);
        b2.setBounds(50,100,200,30);
        f.add(b1);
        f.add(b2);
        f.setSize(300,200);
        f.setLayout(null);
        f.setVisible(true);
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
