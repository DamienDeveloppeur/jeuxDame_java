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
    public static ArrayList<Piece> whitePiece = new ArrayList<>();
    public static ArrayList<Piece> blackPiece = new ArrayList<>();
    public static ArrayList<Case> caseValide = new ArrayList<>();
    public static ArrayList<Piece> piecesWhoCanMoove = new ArrayList<>();

    static Boolean turn = true;
    static boolean initialized, botMooved;
    static Piece currentPiece;
    static Bot Bot;
    public Point pt;
    static Piece pieceMustMoove;

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
        //let's draw square
        g.drawRect(x*ech.width, y*ech.height, ech.width, ech.height);
        // add all case
        if (((x % 2) == 0 && (y % 2) == 0  ) || (x % 2) != 0 && (y % 2) != 0){
            if(initialized){
                BufferedImage IMAGE = null;
                Case value = verifObjectInCase(x,y);

                //if(value.isColor()) System.out.println(value.toString());
                String imageToDraw = "";
                if(value != null){
                    if(value instanceof Pawn) {
                        if(value.isColor()) imageToDraw = "PB";
                        else imageToDraw = "PN";
                    } else if(value instanceof Queen) {
                        if(value.isColor()) imageToDraw = "DB";
                        else imageToDraw = "DN";
                    } else {
                        imageToDraw = "VIDE";
                    }
                }
                if(currentPiece != null && x == currentPiece.getX() && currentPiece.getY() == y){
                    IMAGE = ImageIO.read(new File("img\\"+imageToDraw+"Select.png"));
                } else IMAGE = ImageIO.read(new File("img\\"+imageToDraw+".png"));
                g.drawImage(IMAGE,x*ech.width, y*ech.height, ech.width, ech.height,null );
                if(Bot.colorBot != null && Bot.colorBot && !botMooved) {
                    botMooved = true;
                    Bot.mooveBot();
                }
            }else {
                g.fillRect(x*ech.width, y*ech.height, ech.width, ech.height);
                if(caseValide.size() < 50) caseValide.add(new ValidCell(x,y));
                if (y >= 6 ){
//                    whitePawn.add(new Pawn(x,y,true));
                    whitePiece.add(new Pawn(x,y,true));
                    g.drawImage(ImageIO.read(new File("img\\PB.png")),x*ech.width, y*ech.height, ech.width, ech.height,null);
                }
                else if(y <= 3){
//                    blackPawn.add(new Pawn(x,y,false));
                    blackPiece.add(new Pawn(x,y,false));
                    g.drawImage(ImageIO.read(new File("img\\PN.png")),x*ech.width, y*ech.height, ech.width, ech.height,null);
                } else g.drawImage(ImageIO.read(new File("img\\VIDE.png")),x*ech.width, y*ech.height, ech.width, ech.height,null);
            }
        }
    }
    public void calculerEchelle() {
        ech.width = getWidth()/grille.length;
        ech.height=getHeight()/grille[0].length;
    }
    /**
     *
     */
    public static void ifOneCanTake(){
        for(Piece p : Cell.whitePiece){
            if(p.ifThisCanTake() && !piecesWhoCanMoove.contains(p)) Cell.piecesWhoCanMoove.add(p);
        }

        for(Piece p : Cell.blackPiece){
            if(p.ifThisCanTake() && !piecesWhoCanMoove.contains(p)) Cell.piecesWhoCanMoove.add(p);
        }
    }

    /**
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        ifOneCanTake();
        this.pt = e.getPoint();
        pt.x/=ech.width;
        pt.y/=ech.height;
        System.out.println("x : " + pt.x);
        System.out.println("y : " + pt.y);
        if(currentPiece != null){
            // on doit forcément cliquer sur une case vide
            Case pieceClicked = verifObjectInCase(pt.x, pt.y);
            if(pieceClicked == null) return;
            if(currentPiece.equals(pieceClicked) && pieceMustMoove == null) {currentPiece = null;repaint();return;}
            if((pieceClicked instanceof Piece) && pieceClicked.isColor() == turn && pieceMustMoove == null) currentPiece = (Piece) pieceClicked;
            if(pieceClicked instanceof ValidCell)
                currentPiece.tryingMoove((ValidCell) pieceClicked);
        } else {
            System.out.println(piecesWhoCanMoove);
            // si il faut jouer une pièce parmis une liste
            if(piecesWhoCanMoove.isEmpty()) {
                Piece current = this.ifPieceExist(whitePiece);
                if(current != null && turn) {currentPiece = current;repaint();return;}
                current = this.ifPieceExist(blackPiece);
                if(current != null && !turn) currentPiece = current;
            } else {
                Piece current = this.ifPieceExist(whitePiece);
                if(current != null && turn && piecesWhoCanMoove.contains(current)) {currentPiece = current;repaint();return;}
                current = this.ifPieceExist(blackPiece);
                if(current != null && !turn && piecesWhoCanMoove.contains(current)) currentPiece = current;
            }

        }
        repaint();
    }
    /**
     *
     * @param listPawn
     * @return
     */
    public Piece ifPieceExist(ArrayList<Piece> listPawn){
        Piece p2 = new Pawn(pt.x,pt.y,true);
        for(Piece p : listPawn){
            if(p2.equals(p)) {return p;}
        }
        return null;
    }
    /**
     * Verify what does this square contain
     * @param x ordonne of the square to test
     * @param y abscisse of square to test
     * @return The type of square (white/black pion/queen, void or error)
     */
    static Case verifObjectInCase(int x, int y){
        Piece verif = new Pawn(x,y,true);
        for (Piece p : whitePiece){
            if(p.equals(verif)) return p;
        }
        verif = new Pawn(x,y,false);
        for (Piece p : blackPiece){
            if(p.equals(verif)) return p;
        }
        ValidCell verifCase = new ValidCell(x,y);
        for (Case p : caseValide){
            if(p.equals(verifCase)) return p;
        }
        return null;
    }


    public static Boolean getTurn() {return turn;}

    /**
     *
     * @param ifMooveBot
     */
    public static void swapTurn(boolean ifMooveBot) {
        if(turn) Cell.turn = false;
        else Cell.turn = true;
        if (Bot.colorBot != null && ifMooveBot && Bot.colorBot == getTurn()) Bot.mooveBot();
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