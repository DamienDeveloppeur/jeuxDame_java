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


public class Cellule extends JPanel implements MouseListener {
    Cellule[][] grille = new Cellule [10][10];
    Dimension ech = new Dimension();
    // Array list des pions et des cases
    public static ArrayList<ArrayList<Integer>> pionBlanc = new ArrayList<ArrayList<Integer> >();
    public static ArrayList<ArrayList<Integer>> pionNoir = new ArrayList<ArrayList<Integer> >();
    public static ArrayList<ArrayList<Integer>> caseValide = new ArrayList<ArrayList<Integer> >();
    static int index = 0;
    static int pionBlancIndex = 0;
    static int pionNoirIndex = 0;
    static String turn = "PB";
    static boolean initialized;
    public JFrame frame;
    public Pion currentPion;

    public Cellule(JFrame frame){
        addMouseListener(this);
        this.frame = frame;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("paintComponent");
        calculerEchelle();
        for(int x = 0; x < grille.length; x++){
            for(int y=0; y<grille[x].length; y++){
                grille[x][y] = new Cellule(this.frame);
                grille[x][y].dessineToi(g,x,y, ech);
            }
        }
        initialized = true;
    }
    public void dessineToi(Graphics g, int x, int y, Dimension ech) {
        BufferedImage PB = null;
        BufferedImage PN = null;
        BufferedImage VIDE = null;
        if (Cellule.index == 100){
            Cellule.index = 0;
        }
        try {
            PB = ImageIO.read(new File("D:\\DEV_Projet_java\\jeuxDame\\img\\pionBlanc10.png"));
            PN = ImageIO.read(new File("D:\\DEV_Projet_java\\jeuxDame\\img\\pionNoir10.png"));
            VIDE = ImageIO.read(new File("D:\\DEV_Projet_java\\jeuxDame\\img\\caseVide10.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // on dessine les carrés
        g.drawRect(x*ech.width, y*ech.height, ech.width, ech.height);
        // on colore les cases
        if (((x % 2) == 0 && (y % 2) == 0 ) || (x % 2) != 0 && (y % 2) != 0){
            if(initialized){
                String value = verifCaseValide(x,y);
                if(value == "PB"){
                    g.drawImage(PB,x*ech.width, y*ech.height, ech.width, ech.height,null );
                } else if (value == "PN"){
                    g.drawImage(PN,x*ech.width, y*ech.height, ech.width, ech.height,null );
                } else if(value == "VIDE") {
                    g.drawImage(VIDE,x*ech.width, y*ech.height, ech.width, ech.height,null );
                }
            }else {
                g.setColor(Color.RED);
                g.fillRect(x*ech.width, y*ech.height, ech.width, ech.height);
                if(caseValide.size() < 50){
                    caseValide.add(new ArrayList<Integer>());
                    caseValide.get(Cellule.index).add(0, x);
                    caseValide.get(Cellule.index).add(1, y);
                }
                if (y >= 6 ){
                    if(Cellule.pionBlancIndex < 20){
                        pionBlanc.add(new ArrayList<Integer>());
                        pionBlanc.get(Cellule.pionBlancIndex).add(0, x);
                        pionBlanc.get(Cellule.pionBlancIndex).add(1, y);
                        Cellule.pionBlancIndex++;
                    }
                    g.drawImage(PB,x*ech.width, y*ech.height, ech.width, ech.height,null );
                }
                else if(y <= 3){
                    if(Cellule.pionNoirIndex < 20){
                        pionNoir.add(new ArrayList<Integer>());
                        pionNoir.get(Cellule.pionNoirIndex).add(0, x);
                        pionNoir.get(Cellule.pionNoirIndex).add(1, y);
                        Cellule.pionNoirIndex++;
                    }
                    g.drawImage(PN,x*ech.width, y*ech.height, ech.width, ech.height,null );
                }
                Cellule.index ++;
            }
        }
    }
    public void calculerEchelle() {
        ech.width = getWidth()/grille.length;
        ech.height=getHeight()/grille[0].length;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point pt = e.getPoint();
        pt.x/=ech.width;
        pt.y/=ech.height;
        System.out.println("MOUSE CLICK X :"+pt.x + " Y :"+pt.y);
        String caseVerif = verifCaseValide(pt.x,pt.y);
        if(caseVerif == "PB" || caseVerif == "PN"){
            if(currentPion == null && caseVerif == getTurn()){
                currentPion = new Pion(pt.x, pt.y,caseVerif);
            } else {
                currentPion = null;
                swapTurn();
            }
        } else if(caseVerif == "VIDE" &&  currentPion != null){
          String verifPrise =  currentPion.verifPrise(pt.x, pt.y);
            System.out.println(verifPrise);
            if (verifPrise == "PRISE_PB_G"){
                currentPion.prise(pt.x + 1, pt.y + 1);
                currentPion.deplacement(pt.x,pt.y);
            } else if (verifPrise == "PRISE_PB_D"){
                currentPion.prise(pt.x - 1, pt.y + 1);
                currentPion.deplacement(pt.x,pt.y);
            } else if (verifPrise == "PRISE_PN_G"){
                currentPion.prise(pt.x + 1, pt.y - 1);
                currentPion.deplacement(pt.x,pt.y);
            } else if (verifPrise == "PRISE_PN_D"){
                currentPion.prise(pt.x - 1, pt.y - 1);
                currentPion.deplacement(pt.x,pt.y);
            } else if (verifPrise == "VIDE") {
                currentPion.deplacement(pt.x,pt.y);
            } else {

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }

    static String verifCaseValide(int x, int y){
        String valueReturn = "";
        ArrayList<ArrayList<Integer>> verif = new ArrayList<ArrayList<Integer> >();
        verif.add(new ArrayList<Integer>());
        verif.get(0).add(0, x);
        verif.get(0).add(1, y);
        if(pionBlanc.contains(verif.get(0))){
            valueReturn = "PB";
        }else if(pionNoir.contains(verif.get(0))){
            valueReturn = "PN";
        }else if(caseValide.contains(verif.get(0))){
            valueReturn = "VIDE";
        } else{
            valueReturn = "erreur";
        }
        return valueReturn;
    }

    public void setGrabbed (String grabbed) {
        this.grabbed = grabbed;
    }

    public static String getTurn() {
        return turn;
    }

    public static void swapTurn() {
        if(turn == "PB"){
            Cellule.turn= "PN";
        } else {
            Cellule.turn = "PB";
        }
    }
}