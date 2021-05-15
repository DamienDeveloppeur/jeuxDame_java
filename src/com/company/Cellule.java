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
    static String grabbed = "";
    static String turn = "PB";
    public static ArrayList<ArrayList<Integer>> pionCourant = new ArrayList<ArrayList<Integer> >();
    static boolean initialized;
    public JFrame frame;

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

    public void deplacement(int x, int y) {
        int pos = 0;
        if(grabbed == "PB"){
            pionBlanc.add(new ArrayList<Integer>());
            pionBlanc.get(pionBlanc.size()-1).add(0, x);
            pionBlanc.get(pionBlanc.size()-1).add(1, y);
            pos=pionBlanc.indexOf(pionCourant.get(0));
            pionBlanc.remove(pos);
        }else if (grabbed == "PN"){
            pionNoir.add(new ArrayList<Integer>());
            pionNoir.get(pionNoir.size()-1).add(0, x);
            pionNoir.get(pionNoir.size()-1).add(1, y);
            pos=pionNoir.indexOf(pionCourant.get(0));
            pionNoir.remove(pos);
        }

        System.out.println("121 pos"+pionBlanc);
        System.out.println("122 pos"+pos);

        frame.repaint();
        plateau plateau = new plateau();
        setGrabbed("");
        swapTurn();
        clearPionCourant();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point pt = e.getPoint();
        pt.x/=ech.width;
        pt.y/=ech.height;
        System.out.println("X :"+pt.x);
        System.out.println("Y :"+pt.y);

        if(verifCaseValide(pt.x,pt.y) == "PB" ||verifCaseValide(pt.x,pt.y) == "PN"){
            if(grabbed != ""){
                return;
            } else {
                grabbed = verifCaseValide(pt.x,pt.y);
                pionCourant.add(new ArrayList<Integer>());
                pionCourant.get(0).add(0, pt.x);
                pionCourant.get(0).add(1, pt.y);
            }
            //System.out.println(caseValide);
        } else if(verifCaseValide(pt.x,pt.y) == "VIDE" && grabbed != ""){
            System.out.println("Pion courant y :"+pionCourant.get(0).get(1));
            //pionCourant.get(0).get(1)
            grille[pt.x][pt.y].deplacement(pt.x,pt.y);
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


    public String verifCaseValide(int x, int y){
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
            if(grabbed == "PB"){
                if(pionCourant.get(0).get(1) == y + 1 || Math.abs(pionCourant.get(0).get(0) - x) == 1  ) {
                    valueReturn = "VIDE";
                } else if (pionCourant.get(0).get(1) == y + 2 || Math.abs(pionCourant.get(0).get(0) - x) == 2) {
                    if((pionCourant.get(0).get(0) - x) == 2 && verifCaseValide(pionCourant.get(0).get(0)+1,pionCourant.get(0).get(1) + 1) == "PN"){
                        System.out.println("PRISE");
                    }
                } else {
                    valueReturn = "erreur";
                }
            } else if (grabbed == "PN"){

            }

        } else{
            valueReturn = "erreur";
        }
        return valueReturn;
    }

    public String prise(int x, int y){

       return "erreur";
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

    public static void clearPionCourant(){
        pionCourant.clear();
    }

}
