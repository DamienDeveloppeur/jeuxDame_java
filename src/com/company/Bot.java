package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Bot extends Cell implements MouseListener {
    static Boolean colorBot;
    /**
     *
     * @param color Color choosed by the player
     */
    public Bot(Boolean color){
        colorBot = color;
    }
    /**
     * Main method for moove the bot
     */
    public static void mooveBot(){
        ifOneCanTake();
        int mooveY = 0,mooveYFinal = 0,maxValue = -1000,pionToMooveX = 0,pionToMooveY = 0;
        boolean taked = false;
        ArrayList<Piece> arrayPieces = new ArrayList<>();
        ArrayList<Piece> arrayPiecesQueen = new ArrayList<>();
        if(colorBot) {
            arrayPieces = Cell.blackPiece;
            arrayPiecesQueen = Cell.blackQueen;
        } else {
            arrayPieces = Cell.whitePiece;
            arrayPiecesQueen = Cell.whiteQueen;
        }
        if(arrayPieces.isEmpty() && arrayPiecesQueen.isEmpty()) {
            endGame("Victory");
            return;
        }
        // buckle on queen and check if one on them can take
        // si on peut prendre avec une dame
        //
        if (!arrayPiecesQueen.isEmpty()) {
            for(int i = 0; i < arrayPiecesQueen.size(); i++){
                currentPiece = new Queen(arrayPiecesQueen.get(i).getX(), arrayPiecesQueen.get(i).getY(),getTurn());
                Map<String, Integer> map = currentPiece.ifQueenCanTake(false);
                int pieceTakedX = map.get("pieceTakedX");
                if(map.get("pieceTakedX") != null) {
                    taked = currentPiece.prise(pieceTakedX, pieceTakedX,arrayPiecesQueen.get(i).get(0), arrayPiecesQueen.get(i).get(1));
                    currentPiece.deplacement(map.get("arrivalSquareX"),map.get("arrivalSquareY"));
                    Piece.pieceTaked.clear();
                    if (taked) {
                        Piece.pieceTaked.clear();
                        swapTurn(true);
                    }
                    return;
                }
            }
        }
        // buckle on all pion
        for (int i = 0; i < arrayPieces.size(); i++){
            currentPiece = new Pawn(arrayPieces.get(i).getX(), arrayPieces.get(i).getY(),getTurn());
            if(!piecesWhoCanMoove.isEmpty()) {

            } else {

            }

            if(ifOneCanTake.equals("erreur") || ifOneCanTake.equals("VIDE")) {
                // point system
                if (arrayPieces.get(i).get(0) == 0) {
                    currentPiece.counter -= 5;
                }else if (arrayPieces.get(i).get(0) == 9) {
                    currentPiece.counter -= 5;
                }
                if(colorBot.equals("N")) {
                    mooveY = getMooveBlackY(arrayPieces.get(i).get(1));
                    if(arrayPieces.get(i).get(1) == 0){
                        currentPiece.counter -= 10;
                    }
                    currentPiece.counter += arrayPieces.get(i).get(1);
                } else {
                    mooveY = getMooveWhiteY(arrayPieces.get(i).get(1));
                    if(arrayPieces.get(i).get(1) == 9){
                        currentPiece.counter -= 10;
                    } else if (arrayPieces.get(i).get(1) <= 6) {
                        currentPiece.counter += 5;
                    }
                }
                String verifMooveLeft = verifCaseValide(arrayPieces.get(i).get(0) - 1, mooveY);
                String verifMooveRight = verifCaseValide(arrayPieces.get(i).get(0) + 1, mooveY);
                if (verifMooveLeft.equals("VIDE") || verifMooveRight.equals("VIDE")) {
                    if((!currentPiece.ifOneCanBeTaked(arrayPieces.get(i).get(0) - 1, mooveY) && verifMooveLeft.equals("VIDE"))) {
                        currentPiece.counter += 70;
                    } else if ((!currentPiece.ifOneCanBeTaked(arrayPieces.get(i).get(0) + 1, mooveY) && verifMooveRight.equals("VIDE"))) {
                        currentPiece.counter += 70;
                    }  else {
                        currentPiece.counter -= 70;
                    }
                    if(currentPiece.counter >= maxValue) {
                        maxValue = currentPiece.counter;
                        pionToMooveX = arrayPieces.get(i).get(0);
                        pionToMooveY = arrayPieces.get(i).get(1);
                        mooveYFinal = mooveY;
                    }
                }
            } else {
                String tempX = ifOneCanTake.substring(0,1);
                String tempY = ifOneCanTake.substring(1,2);

                int InttempX = Integer.parseInt(tempX);
                int InttempY = Integer.parseInt(tempY);

                currentPiece = new Piece(InttempX,InttempY ,actualColor);
                String verifPriseBot = Bot.verifPriseBot(InttempX,InttempY);
                if (verifPriseBot.equals("PRISE_PB_G")){
                    taked = currentPiece.prise(InttempX - 1, InttempY - 1,InttempX - 2, InttempY - 2);
                    currentPiece.deplacement(InttempX - 2,InttempY - 2);
                } else if (verifPriseBot.equals("PRISE_PB_D")){
                    taked = currentPiece.prise(InttempX + 1, InttempY - 1,InttempX + 2, InttempY - 2);
                    currentPiece.deplacement(InttempX + 2,InttempY - 2);
                } else if (verifPriseBot.equals("PRISE_PN_G")){
                    taked = currentPiece.prise(InttempX - 1, InttempY + 1,InttempX - 2, InttempY + 2);
                    currentPiece.deplacement(InttempX - 2,InttempY + 2);
                } else if (verifPriseBot.equals("PRISE_PN_D")){
                    taked = currentPiece.prise(InttempX + 1, InttempY + 1,InttempX + 2, InttempY + 2);
                    currentPiece.deplacement(InttempX + 2,InttempY + 2);
                    // management of queen in comming
                }
                Piece.pieceTaked.clear();
                // many take in a row
                if (taked) {
                    Piece.pieceTaked.clear();
                    swapTurn(true);
                }
                return;
            }
        }
        // if no one pion can moove
        if(maxValue == -1000) {
            endGame("Victory");
            return;
        }
        // 4 in 5 chance to moove a queen
        if(Bot.generateRamdomInt(4) != 2 && arrayPiecesQueen.size() > 0){
            currentPiece = new Piece(arrayPiecesQueen.get(0).get(0), arrayPiecesQueen.get(0).get(1),"D"+ getTurn());
            Map<String, Integer> map = currentPiece.ifQueenCanTake(true);
            // moove a queen
            currentPiece.deplacement(map.get("arrivalSquareX"),map.get("arrivalSquareY"));
            return;
        } else {
            currentPiece = new Piece(pionToMooveX,pionToMooveY ,actualColor);
//            int random_int = (int)Math.floor(Math.random()*(1-0+1)+1);
            String verifMooveLeft = verifCaseValide(pionToMooveX - 1, mooveYFinal);
            String verifMooveRight = verifCaseValide(pionToMooveX + 1, mooveYFinal);

            if(verifMooveLeft.equals("VIDE") && currentPiece.ifOneCanBeTaked(pionToMooveX - 1,mooveYFinal) == false) {
                currentPiece.deplacement(pionToMooveX - 1,mooveYFinal);
            } else if (verifMooveRight.equals("VIDE") && currentPiece.ifOneCanBeTaked(pionToMooveX + 1,mooveYFinal) == false) {
                currentPiece.deplacement(pionToMooveX + 1,mooveYFinal);
            } else if (verifMooveLeft.equals("VIDE")) {
                currentPiece.deplacement(pionToMooveX - 1,mooveYFinal);
            } else {
                currentPiece.deplacement(pionToMooveX + 1,mooveYFinal);
            }

//            if(random_int == 1) {
//                if(verifMooveLeft.equals("VIDE") && !currentPiece.ifOneCanBeTaked(pionToMooveX - 1,mooveYFinal)) {
//                    currentPiece.deplacement(pionToMooveX - 1,mooveYFinal);
//                } else {
//                    currentPiece.deplacement(pionToMooveX + 1,mooveYFinal);
//                }
//            } else {
//                //&&
//                if(verifMooveRight.equals("VIDE") && !currentPiece.ifOneCanBeTaked(pionToMooveX + 1,mooveYFinal) ) {
//                    currentPiece.deplacement(pionToMooveX + 1,mooveYFinal);
//                } else {
//                    currentPiece.deplacement(pionToMooveX - 1,mooveYFinal);
//                }
//            }
        }
    }
    /**
     *
     * @param x
     * @param y
     * @return
     */
    public static String verifPriseBot(int x,int y){
        String verifPrise = "";
        if(colorBot.equals("N")){
            verifPrise =  currentPiece.verifPrise(x + 2, y + 2);
            if(!(verifPrise.equals("erreur") || verifPrise.equals("vide"))) {
                return verifPrise;
            } else {
                verifPrise =  currentPiece.verifPrise(x - 2, y + 2);
            }
        } else {
            verifPrise =  currentPiece.verifPrise(x + 2, y - 2);
            if(!(verifPrise.equals("erreur") || verifPrise.equals("vide"))) {
                return verifPrise;
            } else {
                verifPrise =  currentPiece.verifPrise(x - 2, y - 2);
            }
        }
        return verifPrise;
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
