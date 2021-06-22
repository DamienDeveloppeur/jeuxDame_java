package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Bot extends Cellule implements MouseListener {
    static String colorBot;
    /**
     *
     * @param color Color choosed by the player
     */
    public Bot(String color){
        System.out.println("color : "+ color);
        if(color.equals("N")){
            this.colorBot = "B";
        }else {
            this.colorBot = "N";
        }
    }

    /**
     * Main method for moove the bot
     */
    public static void mooveBot(){
        int mooveY = 0;
        int mooveYFinal = 0;
        int maxValue = -1000;
        int pionToMooveX = 0,pionToMooveY = 0;
        String validCase;
        String actualColor = "P"+ getTurn();
        boolean taked = false;
        String verifPrise;
        ArrayList<ArrayList<Integer>> arrayPieces = new ArrayList<ArrayList<Integer> >();
        if(colorBot.equals("N")) {
            arrayPieces = Cellule.pionNoir;
        } else if (colorBot.equals("B")){
            arrayPieces = Cellule.pionBlanc;
        }
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
//                System.out.println("max value : "+maxValue);
                if (verifMooveLeft.equals("VIDE") || verifMooveRight.equals("VIDE")) {
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

                currentPion =  new Piece(InttempX,InttempY ,actualColor);
                String verifPriseBot = Bot.verifPriseBot(InttempX,InttempY);
                if (verifPriseBot.equals("PRISE_PB_G")){
                    taked = currentPion.prise(InttempX + 1, InttempY - 1,InttempX, InttempY);
                    currentPion.deplacement(InttempX,InttempY);
                } else if (verifPriseBot.equals("PRISE_PB_D")){
                    taked = currentPion.prise(InttempX - 1, InttempY - 1,InttempX, InttempY);
                    currentPion.deplacement(InttempX,InttempY);
                } else if (verifPriseBot.equals("PRISE_PN_G")){
                    taked = currentPion.prise(InttempX - 1, InttempY + 1,InttempX, InttempY);
                    currentPion.deplacement(InttempX - 2,InttempY + 2);
                } else if (verifPriseBot.equals("PRISE_PN_D")){
                    taked = currentPion.prise(InttempX + 1, InttempY + 1,InttempX, InttempY);
                    currentPion.deplacement(InttempX + 2,InttempY + 2);
                }else if (verifPriseBot.equals("PRISE_D")){
                    taked = currentPion.prise(Piece.getPieceTaked().get(0).get(0), Piece.getPieceTaked().get(0).get(1),InttempX, InttempY);
                    currentPion.deplacement(InttempX,InttempY);
                    Piece.pieceTaked.clear();
                }
                // many take in a row
                if (taked) {
                    Piece.pieceTaked.clear();
                    swapTurn(true);
                }
                return;
            }
        }
        currentPion =  new Piece(pionToMooveX,pionToMooveY ,actualColor);
        System.out.println("Pion : "+pionToMooveX+ " "+ pionToMooveY);
        int random_int = (int)Math.floor(Math.random()*(1-0+1)+1);
        if(random_int == 0) {
            String verifMooveLeft = verifCaseValide(pionToMooveX - 1, mooveYFinal);
            if(verifMooveLeft.equals("VIDE")) {
                currentPion.deplacement(pionToMooveX - 1,mooveYFinal);
            } else {
                currentPion.deplacement(pionToMooveX + 1,mooveYFinal);
            }
        } else {
            String verifMooveRight = verifCaseValide(pionToMooveX + 1, mooveYFinal);
            if(verifMooveRight.equals("VIDE")) {
                currentPion.deplacement(pionToMooveX + 1,mooveYFinal);
            } else {
                currentPion.deplacement(pionToMooveX - 1,mooveYFinal);
            }
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


}
