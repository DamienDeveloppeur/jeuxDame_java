package com.company;

import java.util.ArrayList;

public class Pawn extends Piece{
    Pawn(int x, int y, Boolean color){
        super(x,y,color);
    }
    // surcharge de méthode
    @Override
    public Boolean ifThisCanTake(Piece p) {
        if(verifForEat(1,1) &&
                Cell.verifObjectInCase(this.getX() + 2, this.getY() + 2) instanceof ValidCell)
            return true;
        if (verifForEat(-1,-1) &&
                Cell.verifObjectInCase(this.getX() - 2, this.getY() - 2) instanceof ValidCell)
            return true;
        if (verifForEat(1,-1) &&
                Cell.verifObjectInCase(this.getX() + 2, this.getY() - 2) instanceof ValidCell)
            return true;
        if (verifForEat(-1,1) &&
                Cell.verifObjectInCase(this.getX() - 2, this.getY() + 2) instanceof ValidCell)
            return true;

        return false;
    }
}
