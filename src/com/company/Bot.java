package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Bot extends Cellule implements MouseListener {
    static String colorBot;
    /**
     *
     * @param color Color choosed by the player
     */
    public Bot(String color){
        if(color.equals("N")){
            colorBot = "B";
        }else {
            colorBot = "N";
        }
    }

    /**
     * Main method for moove the bot
     */
    public static void mooveBot(){
        int mooveY = 0,mooveYFinal = 0,maxValue = -1000,pionToMooveX = 0,pionToMooveY = 0;
        String actualColor = "P"+ getTurn();
        boolean taked = false;
        ArrayList<ArrayList<Integer>> arrayPieces = new ArrayList<>();
        ArrayList<ArrayList<Integer>> arrayPiecesQueen = new ArrayList<>();
        if(colorBot.equals("N")) {
            arrayPieces = Cellule.pionNoir;
            arrayPiecesQueen = Cellule.dameNoir;
        } else if (colorBot.equals("B")){
            arrayPieces = Cellule.pionBlanc;
            arrayPiecesQueen = Cellule.dameBlanc;
        }
        if(arrayPieces.isEmpty() && arrayPiecesQueen.isEmpty()) {
            endGame("Victory");
            return;
        }
        // buckle on queen and check if one on them can take
        if (arrayPiecesQueen.isEmpty()) {
            for(int i = 0; i < arrayPiecesQueen.size(); i++){
                currentPion = new Piece(arrayPiecesQueen.get(i).get(0), arrayPiecesQueen.get(i).get(1),"D"+ getTurn());
                Map<String, Integer> map = currentPion.ifQueenCanTake(false);
                int pieceTakedX = map.get("pieceTakedX");
               if(map.get("pieceTakedX") != null) {
                   taked = currentPion.prise(pieceTakedX, pieceTakedX,arrayPiecesQueen.get(i).get(0), arrayPiecesQueen.get(i).get(1));
                   currentPion.deplacement(map.get("arrivalSquareX"),map.get("arrivalSquareY"));
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
            currentPion = new Piece(arrayPieces.get(i).get(0), arrayPieces.get(i).get(1),actualColor);
            String ifOneCanTake = currentPion.ifOneCanTake(arrayPieces.get(i).get(0),arrayPieces.get(i).get(1),actualColor);
            if(ifOneCanTake.equals("erreur") || ifOneCanTake.equals("VIDE")) {
                // point system
                if (arrayPieces.get(i).get(0) == 0) {
                    currentPion.counter -= 5;
                }else if (arrayPieces.get(i).get(0) == 9) {
                    currentPion.counter -= 5;
                }
                if(colorBot.equals("N")) {
                    mooveY = getMooveBlackY(arrayPieces.get(i).get(1));
                    if(arrayPieces.get(i).get(1) == 0){
                        currentPion.counter -= 10;
                    }
                    currentPion.counter += arrayPieces.get(i).get(1);
                } else {
                    mooveY = getMooveWhiteY(arrayPieces.get(i).get(1));
                    if(arrayPieces.get(i).get(1) == 9){
                        currentPion.counter -= 10;
                    } else if (arrayPieces.get(i).get(1) <= 6) {
                        currentPion.counter += 5;
                    }
                }
                String verifMooveLeft = verifCaseValide(arrayPieces.get(i).get(0) - 1, mooveY);
                String verifMooveRight = verifCaseValide(arrayPieces.get(i).get(0) + 1, mooveY);
                if (verifMooveLeft.equals("VIDE") || verifMooveRight.equals("VIDE")) {
                    if((!currentPion.ifOneCanBeTaked(arrayPieces.get(i).get(0) - 1, mooveY) && verifMooveLeft.equals("VIDE"))) {
                        currentPion.counter += 70;
                    } else if ((!currentPion.ifOneCanBeTaked(arrayPieces.get(i).get(0) + 1, mooveY) && verifMooveRight.equals("VIDE"))) {
                        currentPion.counter += 70;
                    }  else {
                        currentPion.counter -= 70;
                    }
                    if(currentPion.counter >= maxValue) {
                        maxValue = currentPion.counter;
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

                currentPion = new Piece(InttempX,InttempY ,actualColor);
                String verifPriseBot = Bot.verifPriseBot(InttempX,InttempY);
                if (verifPriseBot.equals("PRISE_PB_G")){
                    taked = currentPion.prise(InttempX - 1, InttempY - 1,InttempX - 2, InttempY - 2);
                    currentPion.deplacement(InttempX - 2,InttempY - 2);
                } else if (verifPriseBot.equals("PRISE_PB_D")){
                    taked = currentPion.prise(InttempX + 1, InttempY - 1,InttempX + 2, InttempY - 2);
                    currentPion.deplacement(InttempX + 2,InttempY - 2);
                } else if (verifPriseBot.equals("PRISE_PN_G")){
                    taked = currentPion.prise(InttempX - 1, InttempY + 1,InttempX - 2, InttempY + 2);
                    currentPion.deplacement(InttempX - 2,InttempY + 2);
                } else if (verifPriseBot.equals("PRISE_PN_D")){
                    taked = currentPion.prise(InttempX + 1, InttempY + 1,InttempX + 2, InttempY + 2);
                    currentPion.deplacement(InttempX + 2,InttempY + 2);
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
            currentPion = new Piece(arrayPiecesQueen.get(0).get(0), arrayPiecesQueen.get(0).get(1),"D"+ getTurn());
            Map<String, Integer> map = currentPion.ifQueenCanTake(true);
            // moove a queen
            currentPion.deplacement(map.get("arrivalSquareX"),map.get("arrivalSquareY"));
            return;
        } else {
            currentPion = new Piece(pionToMooveX,pionToMooveY ,actualColor);
//            int random_int = (int)Math.floor(Math.random()*(1-0+1)+1);
            String verifMooveLeft = verifCaseValide(pionToMooveX - 1, mooveYFinal);
            String verifMooveRight = verifCaseValide(pionToMooveX + 1, mooveYFinal);

            if(verifMooveLeft.equals("VIDE") && currentPion.ifOneCanBeTaked(pionToMooveX - 1,mooveYFinal) == false) {
                currentPion.deplacement(pionToMooveX - 1,mooveYFinal);
            } else if (verifMooveRight.equals("VIDE") && currentPion.ifOneCanBeTaked(pionToMooveX + 1,mooveYFinal) == false) {
                currentPion.deplacement(pionToMooveX + 1,mooveYFinal);
            } else if (verifMooveLeft.equals("VIDE")) {
                currentPion.deplacement(pionToMooveX - 1,mooveYFinal);
            } else {
                currentPion.deplacement(pionToMooveX + 1,mooveYFinal);
            }

//            if(random_int == 1) {
//                if(verifMooveLeft.equals("VIDE") && !currentPion.ifOneCanBeTaked(pionToMooveX - 1,mooveYFinal)) {
//                    currentPion.deplacement(pionToMooveX - 1,mooveYFinal);
//                } else {
//                    currentPion.deplacement(pionToMooveX + 1,mooveYFinal);
//                }
//            } else {
//                //&&
//                if(verifMooveRight.equals("VIDE") && !currentPion.ifOneCanBeTaked(pionToMooveX + 1,mooveYFinal) ) {
//                    currentPion.deplacement(pionToMooveX + 1,mooveYFinal);
//                } else {
//                    currentPion.deplacement(pionToMooveX - 1,mooveYFinal);
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
            verifPrise =  currentPion.verifPrise(x + 2, y + 2);
            if(!(verifPrise.equals("erreur") || verifPrise.equals("vide"))) {
                return verifPrise;
            } else {
                verifPrise =  currentPion.verifPrise(x - 2, y + 2);
            }
        } else {
            verifPrise =  currentPion.verifPrise(x + 2, y - 2);
            if(!(verifPrise.equals("erreur") || verifPrise.equals("vide"))) {
                return verifPrise;
            } else {
                verifPrise =  currentPion.verifPrise(x - 2, y - 2);
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
