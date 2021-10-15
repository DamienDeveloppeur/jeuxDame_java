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
    // first number : X
    public static ArrayList<ArrayList<Integer>> pionBlanc = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> pionNoir = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> dameBlanc = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> dameNoir = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> caseValide = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> caseShow = new ArrayList<>();
    static int pionBlancIndex = 0, pionNoirIndex = 0, index = 0;
    static String turn = "B";
    static boolean initialized, botMooved;
    static Piece currentPion;
    static Bot Bot;

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
    public void dessineToi(Graphics g, int x, int y, Dimension ech) throws IOException {
        if (Cellule.index == 100){
            Cellule.index = 0;
        }
        //let's draw square
        g.drawRect(x*ech.width, y*ech.height, ech.width, ech.height);
        // add red case
        if (((x % 2) == 0 && (y % 2) == 0 ) || (x % 2) != 0 && (y % 2) != 0){
            if(initialized){
                BufferedImage IMAGE = null;
                String value = verifCaseValide(x,y);
                if(currentPion != null && x == currentPion.getX() && currentPion.getY() == y ){
                    IMAGE = ImageIO.read(new File("img\\"+value+"Selected.png"));
                } else {
                    IMAGE = ImageIO.read(new File("img\\"+value+".png"));
                }
                System.out.println(IMAGE);
                System.out.println("img\\"+value+"Selected.png");
                System.exit(0);
                g.drawImage(IMAGE,x*ech.width, y*ech.height, ech.width, ech.height,null );

                 if(Bot.colorBot != null && Bot.colorBot != "" && Bot.colorBot.equals("B") && !botMooved) {
                    botMooved = true;
                    Bot.mooveBot();
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

                        pionBlanc.get(Cellule.pionBlancIndex).add(0, x);
                        pionBlanc.get(Cellule.pionBlancIndex).add(1, y);
                        Cellule.pionBlancIndex++;
                    }
                    BufferedImage IMAGE = ImageIO.read(new File("img\\PB.png"));
                    g.drawImage(IMAGE,x*ech.width, y*ech.height, ech.width, ech.height,null );
                }
                else if(y <= 3){
                    if(Cellule.pionNoirIndex < 20){
                        pionNoir.add(new ArrayList<Integer>());
                        pionNoir.get(Cellule.pionNoirIndex).add(0, x);
                        pionNoir.get(Cellule.pionNoirIndex).add(1, y);
                        Cellule.pionNoirIndex++;
                    }
                    BufferedImage IMAGE = ImageIO.read(new File("img\\PN.png"));
                    g.drawImage(IMAGE,x*ech.width, y*ech.height, ech.width, ech.height,null );
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
                if(errorOne.equals("erreur") || errorOne.equals("VIDE")){
                    String error = currentPion.ifCanTake(caseVerif);
                    // verif if queen can take


                    if(!(error.equals("erreur") || error.equals("VIDE"))){
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
            if (verifPrise.equals("VIDE")) {
                currentPion.deplacement(pt.x,pt.y);
            }
            if (taked) {
                Piece.pieceTaked.clear();
                swapTurn(false);
            }
        }
        repaint();
    }
    /**
     * Verify what does this square contain
     * @param x ordonne of the square to test
     * @param y abscisse of square to test
     * @return The type of square (white/black pion/queen, void or error)
     */
    static String verifCaseValide(int x, int y){
        String valueReturn;
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

    /**
     *
     * @param ifMooveBot
     */
    public static void swapTurn(boolean ifMooveBot) {
        if(turn.equals("B")){
            Cellule.turn = "N";
        } else {
            Cellule.turn = "B";
        }
        if (Bot.colorBot != null && Bot.colorBot != "" && ifMooveBot && Bot.colorBot == getTurn()){
            Bot.mooveBot();
        }
        
    }
    public static void setTurn(String turn) {
        Cellule.turn = turn;
    }
    public static int getMooveWhiteY(int y){
        y -= 1;
        return y;
    }
    public static int getTakeWhiteY(int y){
        y -= 2;
        return y;
    }
    public static int getMooveBlackY(int y){
        y += 1;
        return y;
    }
    public static int getTakeBlackY(int y){
        y += 2;
        return y;
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