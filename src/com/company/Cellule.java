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
    public static ArrayList<ArrayList<Integer>> dameBlanc = new ArrayList<ArrayList<Integer> >();
    public static ArrayList<ArrayList<Integer>> dameNoir = new ArrayList<ArrayList<Integer> >();
    public static ArrayList<ArrayList<Integer>> caseValide = new ArrayList<ArrayList<Integer> >();
    public static ArrayList<ArrayList<Integer>> caseShow = new ArrayList<ArrayList<Integer> >();
    static int pionBlancIndex = 0, pionNoirIndex = 0, index = 0;
    static String turn = "B";
    static boolean initialized;
    static Piece currentPion;

    public Cellule(){
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        calculerEchelle();
        for(int x = 0; x < grille.length; x++){
            for(int y=0; y<grille[x].length; y++){
                grille[x][y] = new Cellule();
                grille[x][y].dessineToi(g,x,y, ech);
            }
        }
        initialized = true;
    }
    public void dessineToi(Graphics g, int x, int y, Dimension ech) {
        BufferedImage PB = null;
        BufferedImage PBSELECT = null;
        BufferedImage PN = null;
        BufferedImage PNSELECT = null;
        BufferedImage DB = null;
        BufferedImage DBSELECT = null;
        BufferedImage DN = null;
        BufferedImage DNSELECT = null;
        BufferedImage VIDE = null;
        BufferedImage SHOW = null;

        if (Cellule.index == 100){
            Cellule.index = 0;
        }
        try {
            PB = ImageIO.read(new File("img\\pionBlanc10.png"));
            PBSELECT = ImageIO.read(new File("img\\pionBlanc10Select.png"));
            PN = ImageIO.read(new File("img\\pionNoir10.png"));
            PNSELECT = ImageIO.read(new File("img\\pionNoir10Select.png"));
            DB = ImageIO.read(new File("img\\dameBlanche10.png"));
            DBSELECT = ImageIO.read(new File("img\\dameBlanche10Select.png"));
            DN = ImageIO.read(new File("img\\dameNoire10.png"));
            DNSELECT = ImageIO.read(new File("img\\dameNoire10Select.png"));
            VIDE = ImageIO.read(new File("img\\caseVide10.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //let's draw square
        g.drawRect(x*ech.width, y*ech.height, ech.width, ech.height);
        // add red case
        if (((x % 2) == 0 && (y % 2) == 0 ) || (x % 2) != 0 && (y % 2) != 0){
            if(initialized){
                String value = verifCaseValide(x,y);
                if(value.equals("PB")){
                    if(currentPion != null && x == currentPion.getX() && currentPion.getY() == y ){
                        g.drawImage(PBSELECT,x*ech.width, y*ech.height, ech.width, ech.height,null );
                    }else {
                        g.drawImage(PB,x*ech.width, y*ech.height, ech.width, ech.height,null );
                    }
                } else if (value.equals("PN")){
                    if(currentPion != null && x == currentPion.getX() && currentPion.getY() == y ){
                        g.drawImage(PNSELECT,x*ech.width, y*ech.height, ech.width, ech.height,null );
                    }else {
                        g.drawImage(PN,x*ech.width, y*ech.height, ech.width, ech.height,null );
                    }
                }else if (value.equals("DB")){
                    if(currentPion != null && x == currentPion.getX() && currentPion.getY() == y ){
                        g.drawImage(DBSELECT,x*ech.width, y*ech.height, ech.width, ech.height,null );
                    }else {
                        g.drawImage(DB,x*ech.width, y*ech.height, ech.width, ech.height,null );
                    }
                }else if (value.equals("DN")){
                    if(currentPion != null && x == currentPion.getX() && currentPion.getY() == y ){
                        g.drawImage(DNSELECT,x*ech.width, y*ech.height, ech.width, ech.height,null );
                    }else {
                        g.drawImage(DN,x*ech.width, y*ech.height, ech.width, ech.height,null );
                    }
                } else if(value.equals("VIDE")) {
                    g.drawImage(VIDE,x*ech.width, y*ech.height, ech.width, ech.height,null );
                }
            }else {
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
        boolean taked = false;
        Point pt = e.getPoint();
        pt.x/=ech.width;
        pt.y/=ech.height;
        String caseVerif = verifCaseValide(pt.x,pt.y);
        if(caseVerif.equals("PB")  || caseVerif.equals("PN") || caseVerif.equals("DB") || caseVerif.equals("DN")){
            if(currentPion == null && caseVerif.substring(1,2).equals(getTurn())){
                currentPion = new Piece(pt.x, pt.y,caseVerif);
                String errorOne = currentPion.ifOneCanTake(pt.x, pt.y,caseVerif);
                if(!errorOne.equals("prise")){
                    String error = currentPion.ifCanTake(pt.x, pt.y,caseVerif);
                    if(!(error.equals("erreur") || error.equals("VIDE") )){
                        currentPion = null;
                    } else {
                        currentPion = null;
                        currentPion = new Piece(pt.x, pt.y,caseVerif);
                    }
                } else {
                    currentPion = new Piece(pt.x, pt.y,caseVerif);
                }
            } else {
                currentPion = null;
            }
        } else if(caseVerif.equals("VIDE") && currentPion != null){
          String verifPrise =  currentPion.verifPrise(pt.x, pt.y);
            if (verifPrise.equals("PRISE_PB_G")){
                taked = currentPion.prise(pt.x + 1, pt.y + 1,pt.x, pt.y);
                currentPion.deplacement(pt.x,pt.y);
            } else if (verifPrise.equals("PRISE_PB_D")){
                taked = currentPion.prise(pt.x - 1, pt.y + 1,pt.x, pt.y);
                currentPion.deplacement(pt.x,pt.y);
            } else if (verifPrise.equals("PRISE_PN_G")){
                taked = currentPion.prise(pt.x + 1, pt.y - 1,pt.x, pt.y);
                currentPion.deplacement(pt.x,pt.y);
            } else if (verifPrise.equals("PRISE_PN_D")){
                taked = currentPion.prise(pt.x - 1, pt.y - 1,pt.x, pt.y);
                currentPion.deplacement(pt.x,pt.y);
            }else if (verifPrise.equals("PRISE_D")){
                taked = currentPion.prise(Piece.getPieceTaked().get(0).get(0), Piece.getPieceTaked().get(0).get(1),pt.x, pt.y);
                currentPion.deplacement(pt.x,pt.y);
                Piece.pieceTaked.clear();
            }
            if(verifPrise.equals("PRISE_PB_G") || verifPrise.equals("PRISE_PB_D") || verifPrise.equals("PRISE_PN_G") || verifPrise.equals("PRISE_PN_D")){

            }
            if (verifPrise.equals("VIDE")) {
                currentPion.deplacement(pt.x,pt.y);
            }
        }
        System.out.println(taked);
        if (!taked) {
            Piece.pieceTaked.clear();
            swapTurn();
        }else {

        }
        repaint();
    }

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
        }else if(dameBlanc.contains(verif.get(0))){
            valueReturn = "DB";
        }else if(dameNoir.contains(verif.get(0))){
            valueReturn = "DN";
        }else if(caseValide.contains(verif.get(0))){
            valueReturn = "VIDE";
        } else{
            valueReturn = "erreur";
        }
        return valueReturn;
    }

    public static String getTurn() {
        return turn;
    }

    public static void swapTurn() {
        if(turn.equals("B")){
            Cellule.turn = "N";
        } else {
            Cellule.turn = "B";
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
}