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
    @Override
    public void tryingMoove(ValidCell o) {
        // si on se contente de bouger
        //System.out.println("get heuristic : "+getHeuristic(this,o));
        if(getHeuristic(this,o) == 2 && this.getX() != o.getX() &&
                ((this.isColor() && this.getY() - o.getY() > 0) || (!this.isColor() && this.getY() - o.getY() < 0))
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
}
