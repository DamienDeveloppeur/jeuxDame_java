package com.company;

import java.util.Objects;

public abstract class Piece extends Case {

    public Piece(int x, int y, boolean color) {
        super(x,y,color);
        if(color) this.coeffY = -1;
        else coeffY = 1;
    }


    public void ifOneCanTake(){
        // check pawn

        // check queen
    };
    private int coeffX;
    private int coeffY;
    public abstract void ifThisCanTake(ValidCell o);

    public void moove (ValidCell p) {
        this.setX(p.getX());
        this.setY(p.getY());
        Cell.currentPiece = null;
        Cell.swapTurn(false);
    }
    public void eat(ValidCell o,int x, int y){
        Case objectToCheck = Cell.verifObjectInCase(this.getX() + x,this.getY() + y);
        if(objectToCheck instanceof Piece && objectToCheck.isColor() != this.isColor()) {
            // go delete the piece and moove
            System.out.println("MIAM MIAM MIAM");
            // test if piece can eat again with valid cell
            deleteAnPiece((Piece) objectToCheck);
            this.moove(o);
            System.out.println();
        }
    }

    /**
     *
     * @param o Case to check
     */
    public void tryingMoove(ValidCell o) {
        // si on se contente de bouger
        //System.out.println("get heuristic : "+getHeuristic(this,o));
        if(getHeuristic(this,o) == 2 && this.getX() != o.getX()) {
            this.moove(o);
        } else if(getHeuristic(this,o) == 4 && this.getX() != o.getX() && (Math.abs(this.getX() - o.getX()) == 2 && Math.abs(this.getY() - o.getY()) == 2)){
            int[] arr = this.tryingToEat(o);
            System.out.println("ARRAY : " + arr[0]);
            if(arr != null) this.eat(o, arr[0], arr[1]);
        }
    }

    /**
     *
     * @param o
     * @return
     */
    public int[] tryingToEat(ValidCell o){
        if (this.getX() - o.getX() > 0 && this.getY() - o.getY() > 0) return new int[]{-1, -1};
        else if (this.getX() - o.getX() < 0 && this.getY() - o.getY() > 0) return new int[]{1, -1};
        else if (this.getX() - o.getX() < 0 && this.getY() - o.getY() < 0) return new int[]{1, -1};
        else if (this.getX() - o.getX() > 0 && this.getY() - o.getY() < 0) return new int[]{1, 1};
        else return null;
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
