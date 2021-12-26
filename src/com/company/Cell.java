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

public class Cell extends JPanel implements MouseListener {
    Cell[][] grille = new Cell[10][10];
    Dimension ech = new Dimension();
    // Array list des pions et des cases
    // first number : X
    public static ArrayList<Piece> pionBlanc = new ArrayList<>();
    public static ArrayList<Piece> pionNoir = new ArrayList<>();
    public static ArrayList<ValidCell> caseValide = new ArrayList<>();
    static int pionBlancIndex = 0, pionNoirIndex = 0, index = 0;
    static Boolean turn = true;
    static boolean initialized, botMooved;
    static Piece currentPion;
    static Bot Bot;

    public Cell(){
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        calculerEchelle();
        for(int x = 0; x < grille.length; x++){
            for(int y=0; y<grille[x].length; y++){
                grille[x][y] = new Cell();
                try {
                    grille[x][y].goDraw(g,x,y, ech);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        initialized = true;
    }
    public void goDraw(Graphics g, int x, int y, Dimension ech) throws IOException {
        if (Cell.index == 100) Cell.index = 0;
        //let's draw square
        g.drawRect(x*ech.width, y*ech.height, ech.width, ech.height);
        // add all case
        if (((x % 2) == 0 && (y % 2) == 0 ) || (x % 2) != 0 && (y % 2) != 0){
            if(initialized){
                BufferedImage IMAGE = null;
                String value = verifCaseValide(x,y);
                System.out.println(pionBlanc);
                System.out.println("~~~~~~~~~~~~~~");
                System.out.println(value);
                if(currentPion != null && x == currentPion.getX() && currentPion.getY() == y ){
                    IMAGE = ImageIO.read(new File("img\\"+value+"Select.png"));
                } else IMAGE = ImageIO.read(new File("img\\"+value+".png"));

                g.drawImage(IMAGE,x*ech.width, y*ech.height, ech.width, ech.height,null );
                if(Bot.colorBot != null  && Bot.colorBot && !botMooved) {
                    botMooved = true;
                    Bot.mooveBot();
                }
            }else {
                g.fillRect(x*ech.width, y*ech.height, ech.width, ech.height);
                if(caseValide.size() < 50){
                    caseValide.add(new ValidCell(x,y,false));
//                    caseValide.add(new ArrayList<Integer>());
//                    caseValide.get(Cell.index).add(0, x);
//                    caseValide.get(Cell.index).add(1, y);
                }
                if (y >= 6 ){
                    if(Cell.pionBlancIndex < 20){
                        pionBlanc.add(new Pawn(x,y,true));
                        Cell.pionBlancIndex++;
                    }
                    BufferedImage IMAGE = ImageIO.read(new File("img\\PB.png"));
                    g.drawImage(IMAGE,x*ech.width, y*ech.height, ech.width, ech.height,null );
                }
                else if(y <= 3){
                    if(Cell.pionNoirIndex < 20){
                        pionNoir.add(new Pawn(x,y,false));
                        Cell.pionNoirIndex++;
                    }
                    BufferedImage IMAGE = ImageIO.read(new File("img\\PN.png"));
                    g.drawImage(IMAGE,x*ech.width, y*ech.height, ech.width, ech.height,null );
                }
                Cell.index ++;
            }
        }
    }
    public void calculerEchelle() {
        ech.width = getWidth()/grille.length;
        ech.height=getHeight()/grille[0].length;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        repaint();
    }
    /**
     * Verify what does this square contain
     * @param x ordonne of the square to test
     * @param y abscisse of square to test
     * @return The type of square (white/black pion/queen, void or error)
     */
    static String verifCaseValide(int x, int y){
        Piece verif = new Pawn(x,y,true);
        for (Piece piece : pionBlanc){
            if(piece.equals(verif)) return "PB";
        }
        for (Piece piece : pionNoir){
            if(piece.equals(verif)) return "PN";
        }
        for (Piece piece : caseValide){
            if(piece.equals(verif)) return "VIDE";
        }
        return "erreur";
    }

    public static Boolean getTurn() {
        return turn;
    }

    /**
     *
     * @param ifMooveBot
     */
    public static void swapTurn(boolean ifMooveBot) {
        if(turn) Cell.turn = false;
        else Cell.turn = true;

        if (Bot.colorBot != null && ifMooveBot && Bot.colorBot == getTurn()){
            Bot.mooveBot();
        }
        
    }
    public static void setTurn(Boolean turn) {
        Cell.turn = turn;
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