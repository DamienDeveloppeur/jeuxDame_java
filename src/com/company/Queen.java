package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Queen extends Piece {
    Queen(int x, int y, Boolean color){
        super(x,y,color);
    }

    @Override
    public Boolean ifThisCanTake(Piece p) {
        return true;
    }
    /**
     *
     * @param o case d'arrivée
     */
    public void eat(ValidCell o, Piece pieceToEat){
        deleteAnPiece((Piece) pieceToEat);
        this.moove(o);
        // launch ifThisCanTake
        if(this.ifThisCanTake(this)) {
            Cell.swapTurn(false);
            Cell.currentPiece = this;
        }
    }

    @Override
    public void tryingMoove(ValidCell o) {
        // on doit rester sur une diagonale
        if(Math.abs(this.getX() - o.getX()) == Math.abs(this.getY() - o.getY())){
            ArrayList<Case> caseChecked = new ArrayList<>();
            System.out.println("DIAGO");
            // Nbr case to check : this.getX() - o.getX()
            // haut gauche
            for(int i = 1; i < Math.abs(this.getX() - o.getX()) + 1; i++){
                if(this.getX() < o.getX() && this.getY() < o.getY()){
                    caseChecked.add(Cell.verifObjectInCase(this.getX()+i, this.getY()+i));
                }
                if(this.getX() > o.getX() && this.getY() > o.getY()){
                    caseChecked.add(Cell.verifObjectInCase(this.getX()-i, this.getY()-i));
                }
                if(this.getX() < o.getX() && this.getY() > o.getY()){
                    caseChecked.add(Cell.verifObjectInCase(this.getX()+i, this.getY()-i));
                }
                if(this.getX() > o.getX() && this.getY() < o.getY()){
                    caseChecked.add(Cell.verifObjectInCase(this.getX()-i, this.getY()+i));
                }

            }
            Stream<Case> strealcasechecked = caseChecked.stream().filter(ctn -> ctn instanceof ValidCell);
            if(strealcasechecked.count() == 0) this.moove(o);
            if(strealcasechecked.count() == 1 &&
                    (strealcasechecked.findFirst().get().isColor() && !this.isColor()) &&
                    (!strealcasechecked.findFirst().get().isColor() && this.isColor())) {
                this.eat(o, (Piece) strealcasechecked.findFirst().get());
            }
            if(strealcasechecked.count() > 1){
                return;
            }
        }
    }
}
