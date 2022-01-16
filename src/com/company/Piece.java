package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Piece {
    private int x;
    private int y;
    private boolean color;

    public Piece(int x, int y, boolean color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                '}';
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void moove (Piece p) {
        this.setX(p.getX());
        this.setY(p.getY());
        Cell.currentPiece = null;
        Cell.swapTurn(false);
    }
    public void tryingMoove(Piece o) {
        // si on se contente de bouger
        //System.out.println("get heuristic : "+getHeuristic(this,o));
        if(getHeuristic(this,o) == 2 && this.getX() != o.getX()) {
            this.moove(o);
        } else if(getHeuristic(this,o) == 4 && this.getX() != o.getX() && (Math.abs(this.getX() - o.getX()) == 2 && Math.abs(this.getY() - o.getY()) == 2)){
            // si la case entre les deux target est une pièce de l'autre couleur
            if(this.isColor() && this.getY() - o.getY() > 0){
                // on prend donc à gauche
                if(this.getX() - o.getY() > 0) {
                    Piece objectToCheck = Cell.verifObjectInCase(this.getX() -1,this.getY() -1);
                    if(objectToCheck instanceof Piece && objectToCheck.isColor() != this.isColor()) {
                        // go delete the piece and moove
                        deleteAnPiece(objectToCheck);
                        this.moove(o);
                    }
                    // on prend donc à droite
                } else {
                    Piece objectToCheck = Cell.verifObjectInCase(this.getX() + 1,this.getY() -1);
                    if(objectToCheck instanceof Piece && objectToCheck.isColor() != this.isColor()) {
                        // go delete the piece and moove
                        deleteAnPiece(objectToCheck);
                        this.moove(o);
                    }
                }
            }
            System.out.println("ON MANGE");
        }
    }
    public void deleteAnPiece(Piece o){
        if(o.isColor()) Cell.whitePiece.remove(o);
        else Cell.blackPiece.remove(o);
    }
    public abstract void ifOneCanTake();

    public int getHeuristic(Piece startMoove, Piece finalMoove) {
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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
