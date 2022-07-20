package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Bot extends Cell implements MouseListener {
    static Boolean colorBot;
    static int coeffY;
    /**
     *
     * @param color Color choosed by the player
     */
    public Bot(Boolean color){
        System.out.println("Start bot");
        colorBot = color;
        coeffY = (color) ? -1 : 1;
    }

    /**
     *
     * @return
     */
    public static ValidCell findCell(Piece pieceTaked, Piece pToMoove){
        if(pToMoove.getX() - pieceTaked.getX() > 0 && pToMoove.getY() - pieceTaked.getY() > 0) {
            return(ValidCell) Cell.verifObjectInCase(pieceTaked.getX()-1, pieceTaked.getY()-1);
        }
        // bas droite
        if(pToMoove.getX() - pieceTaked.getX() < 0 && pToMoove.getY() - pieceTaked.getY() < 0) {
            // pieceTake.getX()+1
            return(ValidCell) Cell.verifObjectInCase(pieceTaked.getX()+1, pieceTaked.getY()+1);
        }
        if(pToMoove.getX() - pieceTaked.getX() > 0 && pToMoove.getY() - pieceTaked.getY() < 0) {
            // pieceTake.getX()+1
            return(ValidCell) Cell.verifObjectInCase(pieceTaked.getX()-1, pieceTaked.getY()+1);
        }
        if(pToMoove.getX() - pieceTaked.getX() < 0 && pToMoove.getY() - pieceTaked.getY() > 0) {
            // pieceTake.getX()+1
            return(ValidCell) Cell.verifObjectInCase(pieceTaked.getX()+1, pieceTaked.getY()-1);
        }
        return null;
    }

    /**
     *
     * @param p
     * @param x
     * @return
     */
    public static ValidCell findCaseForMoove(Piece p, int x){
        if(Cell.verifObjectInCase(p.getX() + x, p.getY() +coeffY) instanceof ValidCell){
            return (ValidCell) Cell.verifObjectInCase(p.getX() + x, p.getY() +coeffY);
        } else return null;
    }
    /**
     * Main method for moove the bot
     */
    public static void mooveBot(){

        //ifOneCanTake();
        ArrayList<Piece> arrayPieces = null;
        int countMax = 0;
        Piece pieceToMoove = null;
        ValidCell caseToMoove = null;

        if(colorBot) arrayPieces = Cell.whitePiece;
        else arrayPieces = Cell.blackPiece;

        // on check si un pion peut prendre, on le bouge le cas échéant
        // on boucle sur les piéces
        for(Piece p : arrayPieces){
            int count = 0;
            if(p.ifThisCanTake() != null){
                count += 400;
            }
            if(findCaseForMoove(p, 1) != null) {
                count += 20;
            }
            if(findCaseForMoove(p, -1) != null) {
                count += 20;
            }
            if(count > countMax){
                countMax = count;
                pieceToMoove = p;

            }
        }

        if(pieceToMoove.ifThisCanTake() == null){
            if(findCaseForMoove(pieceToMoove, 1) != null) {
                pieceToMoove.moove(findCaseForMoove(pieceToMoove, 1));
            } else pieceToMoove.moove(findCaseForMoove(pieceToMoove, -1));
        } else {
            Case cell = findCell((Piece) pieceToMoove.ifThisCanTake(),pieceToMoove);
            System.out.println("CELL : "+ cell);
//            System.out.println("Piece to moove: "+ pieceToMoove);
//            System.out.println("Piece to Take: "+ pieceToMoove.ifThisCanTake());
            pieceToMoove.eat((ValidCell) cell, (Piece) pieceToMoove.ifThisCanTake());
        }
        //
//        if (!arrayPiecesQueen.isEmpty()) {
//            for(int i = 0; i < arrayPiecesQueen.size(); i++){
//                //currentPiece = arrayPiecesQueen.get(i);
//                Case c = arrayPiecesQueen.get(i).ifThisCanTake();
//                if(c != null) {
//                    currentPiece = arrayPiecesQueen.get(i);
//                    currentPiece.tr
//                }
//                if (taked) {
//                    Piece.pieceTaked.clear();
//                    swapTurn(true);
//                }
//                return;
//
//            }
//        }
//        if(!piecesWhoCanMoove.isEmpty()) {
////          Piece pieceToMoove = piecesWhoCanMoove.stream()
////                    .filter(p -> p.isColor() == colorBot)
////                    .findFirst()
////                    .get();
//          //pieceToMoove.tr
//        } else {
//            for (Piece p : arrayPieces){
//                if(!(p instanceof Queen)){
//
//
//                }
//            }
//        }


    }

    public static void endGame(String result){
        JFrame f=new JFrame("End of the game");
        f.setLayout(new FlowLayout());
        JLabel labelText = new JLabel(result);
        labelText.setFont(new Font("Serif", Font.PLAIN, 70));
        f.add(labelText);
        f.setSize(800,300);
        //f.setLayout(null);
        f.setVisible(true);
    }

    static int generateRamdomInt(int maxValue){
        Random rand = new Random();
        int int_random = rand.nextInt(maxValue);
        return int_random;
    }
}
