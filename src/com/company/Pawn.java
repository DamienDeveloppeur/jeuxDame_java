package com.company;

import java.util.ArrayList;

public class Pawn extends Piece{
    Pawn(int x, int y, Boolean color){
        super(x,y,color);
    }
    // surcharge de méthode
    @Override
    public void ifThisCanTake(ValidCell o) {
        Case objectToCheck = Cell.verifObjectInCase(this.getX() + 1,this.getY() + 1);

    }

    public void ifTaked(){

    }
}
