package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Cellule extends JPanel implements MouseListener {
    Cellule[][] grille = new Cellule [10][10];
    Dimension ech = new Dimension();

    public static HashMap<Integer, Integer> pionBlanc = new HashMap<Integer, Integer>();
    public static HashMap<Integer, Integer> pionNoir = new HashMap<Integer, Integer>();
    public static ArrayList<ArrayList<Integer>> caseValide = new ArrayList<ArrayList<Integer> >();
    static int index = 0;

    public Cellule(){
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
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
         BufferedImage pionBlancImg = null;
         BufferedImage pionNoirImg = null;
        try {
            pionBlancImg = ImageIO.read(new File("D:\\DEV_Projet_java\\untitled1\\data\\pionBlanc10.png"));
            pionNoirImg = ImageIO.read(new File("D:\\DEV_Projet_java\\untitled1\\data\\pionNoir10.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // on colore les cases
        g.drawRect(x*ech.width, y*ech.height, ech.width, ech.height);

            if (((x % 2) == 0 && (y % 2) == 0 ) || (x % 2) != 0 && (y % 2) != 0){
                g.setColor(Color.RED);
                g.fillRect(x*ech.width, y*ech.height, ech.width, ech.height);
                if(caseValide.size() < 51){
                    caseValide.add(new ArrayList<Integer>());
                    caseValide.get(Cellule.index).add(0, y);
                    caseValide.get(Cellule.index).add(1, x);
                }
                System.out.println(Cellule.index);
                if (y >= 6){
                    pionBlanc.put(x,y);
                    g.drawImage(pionBlancImg,x*ech.width, y*ech.height, ech.width, ech.height,null );
                }
                else if(y <= 3 ){
                    pionNoir.put(x,y);
                    g.drawImage(pionNoirImg,x*ech.width, y*ech.height, ech.width, ech.height,null );
                }

            }


    }
    public void calculerEchelle() {
        ech.width= getWidth()/grille.length;
        ech.height=getHeight()/grille[0].length;
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
