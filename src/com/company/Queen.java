package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Queen extends Piece {
    Queen(int x, int y, Boolean color){
        super(x,y,color);
    }
    /**
     *
     * @param o case d'arrivée
     */
    public void eat(ValidCell o, Piece pieceToEat){
        deleteAnPiece((Piece) pieceToEat);
        this.moove(o);
        // launch ifThisCanTake
        if(this.ifThisCanTake()) {
            Cell.swapTurn(false);
            Cell.currentPiece = this;
        }
    }
    public boolean ifNotSameColor(Case p){
        if(p == null) return false;
        if(this.isColor() == p.isColor()) return false;
        else return true;
    }
    public boolean testDiago(int coefX, int coefY){
        boolean flagBotRight = false;
        for(int i = 1; i < 10; i++){
            //caseChecked.add(Cell.verifObjectInCase(this.getX()+i, this.getY()+i));
            if(flagBotRight && Cell.verifObjectInCase(this.getX()+i * coefX, this.getY()+i * coefY) instanceof ValidCell)
                return true;
            if (flagBotRight && Cell.verifObjectInCase(this.getX()+i * coefX, this.getY()+i * coefY) instanceof Piece)
                return false;

            if(Cell.verifObjectInCase(this.getX()+i * coefX, this.getY()+i * coefY) instanceof Piece &&
                    ifNotSameColor( Cell.verifObjectInCase(this.getX()+i * coefX, this.getY()+i * coefY)))
                flagBotRight = true;

            if(Cell.verifObjectInCase(this.getX()+i * coefX, this.getY()+i * coefY) == null) break;
        }
        return false;
    }

    @Override
    public boolean ifThisCanTake(){
        if(testDiago(1,1)) return true;
        else if (testDiago(-1,1)) return true;
        else if (testDiago(1,-1)) return true;
        else if (testDiago(-1,-1)) return true;
        else return false;
    }
    /**
     *
     * @param o Case to check
     */
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
            long count = caseChecked.stream().filter(ctn -> ctn instanceof Piece).count();
            Case piece = (count == 0) ? null :  caseChecked.stream().filter(ctn -> ctn instanceof Piece).findFirst().get();
            System.out.println("count : "+ count);
            if(count == 0) {

                this.moove(o);
                System.out.println(ifThisCanTake());
            }
            if(count == 1 &&
                    ((piece.isColor() && !this.isColor()) ||
                    (!piece.isColor() && this.isColor()))) {
                System.out.println(piece);
                this.eat(o, (Piece) piece);
                System.out.println(ifThisCanTake());
            }
            if(count > 1){
                return;
            }
        }
    }
}
