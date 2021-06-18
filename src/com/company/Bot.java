package com.company;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Bot extends Cellule implements MouseListener {
    private String colorChoosed = "";

    public Bot(String color){
        this.colorChoosed = color;
        if(color == "N"){
            mooveBot();
        }else {
            System.out.print(turn);
            setTurn("B");

        }
    }

    public void mouseClicked(MouseEvent e) {
        // we know the color of human player
        // if P choose black, bot moove one piece first
        // if getTurn != colorChosed => exe method for moove a piece


    }
    public void mooveBot(){
        // get array list of black and white point

    }

    public static void swapTurn() {
        if(turn.equals("B")){
            Cellule.turn = "N";
        } else {
            Cellule.turn = "B";
        }
    }
}
