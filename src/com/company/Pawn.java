package com.company;

import java.util.ArrayList;

public class Pawn extends Piece{
    Pawn(int x, int y, Boolean color){
        super(x,y,color);
    }
    // surcharge de méthode
    @Override
    public void ifThisCanTake() {
        if(this.isColor()){

        }
    }

    public void ifTaked(){

    }
}
