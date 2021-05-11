package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Cellule extends JPanel implements MouseListener {
    Cellule[][] grille = new Cellule [10][10];
    Dimension ech = new Dimension();

    public HashMap<Integer, Integer> pionBlanc = new HashMap<Integer, Integer>();
    public HashMap<Integer, Integer> pionNoir = new HashMap<Integer, Integer>();
    public static ArrayList<ArrayList<Integer>> caseValide = new ArrayList<ArrayList<Integer> >();
    static int index = 0;

    public Cellule(){
        addMouseListener(this);
    }

    public void paintComponent(Graphics g){
        for(int x = 0; x < grille.length; x++){
            for(int y=0; y<grille[x].length; y++){
                grille[x][y] = new Cellule();
            }
        }
        calculerEchelle();
        for(int x = 0; x < grille.length; x++){
            for(int y=0; y<grille[x].length; y++){
                grille[x][y].dessineToi(g,x,y, ech);
            }
        }
    }

    public void dessineToi(Graphics g, int x, int y, Dimension ech) {
        Image img = null;
        try {
            img = ImageIO.read(new File("data/pionBlanc.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // on colore les cases
        if (((x % 2) == 0 && (y % 2) == 0 ) || (x % 2) != 0 && (y % 2) != 0){
            g.setColor(Color.RED);
            g.fillRect(x*ech.width, y*ech.height, ech.width, ech.height);

            caseValide.add(new ArrayList<Integer>());
            caseValide.get(Cellule.index).add(0, y);
            caseValide.get(Cellule.index).add(1, x);

            System.out.println(Cellule.index);
           // System.out.println(pionBlanc);
            if (y >= 6){
                pionBlanc.put(x,y);
                g.drawImage(img, x*ech.width, y*ech.height, this.getWidth(), this.getHeight(), this);
            }
            if(y <= 3){
                pionNoir.put(x,y);
            }
            Cellule.index ++;
        }
        g.drawRect(x*ech.width, y*ech.height, ech.width, ech.height);
    }
    public void calculerEchelle() {
        ech.width= getWidth()/grille.length;
        ech.height=getHeight()/grille[0].length;
        grille[4][4].setBackground(Color.BLUE);
    }

    public void click() {
        //System.out.println(caseValide);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(caseValide);
        Point pt = e.getPoint();
        pt.x/=ech.width;
        pt.y/=ech.height;
        //System.out.println("x : "+pt.x);
        //System.out.println("y : "+pt.y);
        grille[pt.x][pt.y].click();
        repaint();
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
