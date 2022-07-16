package com.company;

import java.util.Objects;

public abstract class Piece extends Case {

    public Piece(int x, int y, boolean color) {
        super(x,y,color);
        if(color) this.coeffY = -1;
        else coeffY = 1;
    }

    private int coeffX;
    private int coeffY;

    public abstract boolean ifThisCanTake();

    /**
     *
     * @param o Case to check
     */
    public abstract void tryingMoove(ValidCell o);

    /**
     *
     * @param p
     */
    public void moove (ValidCell p) {
        // verif if a queen is made
        this.setX(p.getX());
        this.setY(p.getY());
        if(this.isColor() && p.getY() == 0 && this instanceof Pawn) {
            // make queen
            Queen queen = new Queen(this.getX(), this.getY(), true);
            Cell.whitePiece.remove(this);
            Cell.whitePiece.add(queen);
        }
        if(!this.isColor() && p.getY() == 9 && this instanceof Pawn) {
            // make queen
            Queen queen = new Queen(this.getX(), this.getY(), false);
            Cell.blackPiece.remove(this);
            Cell.blackPiece.add(queen);
        }
        Cell.currentPiece = null;
        Cell.swapTurn(false);
    }



    /**
     *
     * @param o
     * @return
     */
    public int[] tryingToEat(ValidCell o){
        if (this.getX() - o.getX() > 0 && this.getY() - o.getY() > 0) return new int[]{-1, -1};
        else if (this.getX() - o.getX() < 0 && this.getY() - o.getY() > 0) return new int[]{1, -1};
        else if (this.getX() - o.getX() < 0 && this.getY() - o.getY() < 0) return new int[]{1, 1};
        else if (this.getX() - o.getX() > 0 && this.getY() - o.getY() < 0) return new int[]{-1, 1};
        else return null;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public Boolean verifForEat(int x, int y){
        Case objectToCheck = Cell.verifObjectInCase(this.getX() + x,this.getY() + y);
        if(objectToCheck instanceof Piece && objectToCheck.isColor() != this.isColor()) return true;
        return false;
    }


    /**
     *
     * @param o
     */
    public void deleteAnPiece(Piece o){
        if(o.isColor()) Cell.whitePiece.remove(o);
        else Cell.blackPiece.remove(o);
    }


    /**
     *
     * @param startMoove
     * @param finalMoove
     * @return
     */
    public int getHeuristic(Piece startMoove, Case finalMoove) {
        return Math.abs(startMoove.getX() - finalMoove.getX()) + Math.abs(startMoove.getY() - finalMoove.getY());
    }



    //public void

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;
        Piece piece = (Piece) o;
        return getX() == piece.getX() && getY() == piece.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
