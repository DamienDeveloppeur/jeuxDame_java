package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class plateau extends JFrame  {
    public JFrame frame = new JFrame();;
    public plateau(){
        JFrame f=new JFrame("Choix mode de jeu");
        JButton bt_human=new JButton("Play agains't human ?");
        JButton bt_bot=new JButton("Play agains't bot ?");
        bt_human.setBounds(50,10,200,30);
        bt_bot.setBounds(50,100,200,30);
        f.add(bt_human);
        f.add(bt_bot);
        f.setSize(300,200);
        f.setLayout(null);
        f.setVisible(true);

        bt_human.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Human Human = new Human();
                f.setVisible(false);
                frame.setVisible(true);
                frame.setContentPane(Human);
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });

        bt_bot.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JFrame colorChoice =new JFrame("Choix de la couleur");
                JButton bt_white =new JButton("Black");
                JButton bt_Black =new JButton("White");
                bt_white.setBounds(50,100,200,30);
                colorChoice.add(bt_white);
                colorChoice.setSize(300,200);
                colorChoice.setLayout(null);
                colorChoice.setVisible(true);


                Bot Bot = new Bot();
                f.setVisible(false);
                frame.setVisible(true);
                frame.setContentPane(Bot);
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });


        frame.setTitle("Jeux de dame");
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

