package com.company;

import javax.swing.*;
import java.awt.*;

public class plateau extends JFrame  {
    private JPanel pan=new JPanel();
    private JPanel blackPan=new JPanel();
    private JPanel redPan=new JPanel();
    public JFrame frame = new JFrame();;
    public plateau(){
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
    }

}
