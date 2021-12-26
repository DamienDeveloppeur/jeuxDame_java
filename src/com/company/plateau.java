package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class plateau extends JFrame  {
    public JFrame frame = new JFrame();
    public plateau(){
        // first frame let the choice to the player
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

        // prepare the main frame
        frame.setTitle("Jeux de dame");
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // if the player choose against human, open the regular jframe
        bt_human.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Human Human = new Human();
                f.setVisible(false);
                frame.setVisible(true);
                frame.setContentPane(Human);
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });

        // if player choose agains't bot
        bt_bot.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);

                JFrame colorChoice =new JFrame("Choix de la couleur");
                JButton bt_white =new JButton("White");
                JButton bt_black =new JButton("Black");
                bt_white.setBounds(50,10,200,30);
                bt_black.setBounds(50,100,200,30);
                colorChoice.add(bt_white);
                colorChoice.add(bt_black);
                colorChoice.setSize(300,200);
                colorChoice.setLayout(null);
                colorChoice.setVisible(true);

                bt_white.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        Bot Bot = new Bot(true);
                        colorChoice.setVisible(false);
                        frame.setVisible(true);
                        frame.setContentPane(Bot);
                        SwingUtilities.updateComponentTreeUI(frame);
                    }
                });

                bt_black.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        Bot Bot = new Bot(false);
                        colorChoice.setVisible(false);
                        frame.setVisible(true);
                        frame.setContentPane(Bot);
                        SwingUtilities.updateComponentTreeUI(frame);
                    }
                });
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });
    }
}

