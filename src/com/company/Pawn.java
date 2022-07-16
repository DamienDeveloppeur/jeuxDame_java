package com.company;

import java.util.ArrayList;

public class Pawn extends Piece{
    Pawn(int x, int y, Boolean color){
        super(x,y,color);
    }
    @Override
    public boolean ifThisCanTake() {
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
    // surcharge de méthode
    @Override
    public void tryingMoove(ValidCell o) {
        Cell.piecesWhoCanMoove.clear();
        // On doit obligatoirement manger si pieceMustMoove != null
        if(getHeuristic(this,o) == 2 && this.getX() != o.getX() &&
                ((this.isColor() && this.getY() - o.getY() > 0) || (!this.isColor() && this.getY() - o.getY() < 0)) &&
                Cell.pieceMustMoove == null
        ) {
            this.moove(o);
        } else if(getHeuristic(this,o) == 4 && this.getX() != o.getX() &&
                (Math.abs(this.getX() - o.getX()) == 2 && Math.abs(this.getY() - o.getY()) == 2)){
            int[] arr = this.tryingToEat(o);
            if(arr != null && verifForEat(arr[0], arr[1])){
                this.eat(o, arr[0], arr[1]);
            }
        }
    }

    /**
     *
     * @param o case d'arrivée
     * @param x
     * @param y
     */
    public void eat(ValidCell o,int x, int y){
        Case objectToCheck = Cell.verifObjectInCase(this.getX() + x,this.getY() + y);
        // go delete the piece and moove
        System.out.println("MIAM MIAM MIAM");
        // test if piece can eat again with valid cell
        deleteAnPiece((Piece) objectToCheck);
        this.moove(o);
        // launch ifThisCanTake
        if(this.ifThisCanTake()) {
            Cell.swapTurn(false);
            Cell.currentPiece = this;
            Cell.pieceMustMoove = this;
        } else Cell.pieceMustMoove = null;
    }
}
