package com.company;

public class Queen extends Piece {
    Queen(int x, int y, Boolean color){
        super(x,y,color);
    }

    @Override
    public Boolean ifThisCanTake(Piece p) {
        return true;
    }
}
