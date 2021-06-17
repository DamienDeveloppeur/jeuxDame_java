package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class plateau extends JFrame  {
    public JFrame frame = new JFrame();;
    public plateau(){
        JFrame f=new JFrame("Button Example");
        JButton bt_human=new JButton("Play agains't human ?");
        JButton b2=new JButton("Play agains't bot ?");
        bt_human.setBounds(50,10,200,30);
        b2.setBounds(50,100,200,30);
        f.add(bt_human);
        f.add(b2);
        f.setSize(300,200);
        f.setLayout(null);
        f.setVisible(true);
        bt_human.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                frame.setVisible(true);
            }
        });

        Cellule Cellule = new Cellule();
        Cellule.validate();
        frame.setTitle("Jeux de dame");
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(Cellule);
        SwingUtilities.updateComponentTreeUI(frame);

    }
}

