package com.company;

import javax.swing.*;
import java.util.ArrayList;

public class Pion extends Piece{


    public Pion(int x, int y, String couleur){
        this.x = x;
        this.y = y;
        this.couleur = couleur;

    }
    public void deplacement(int x, int y) {
        int pos = 0;
        ArrayList<ArrayList<Integer>> pionCourant = new ArrayList<ArrayList<Integer> >();
        pionCourant.add(new ArrayList<Integer>());
        pionCourant.get(0).add(0, getX());
        pionCourant.get(0).add(1, getY());

        if(this.couleur == "PB"){
            Cellule.pionBlanc.add(new ArrayList<Integer>());
            Cellule.pionBlanc.get(Cellule.pionBlanc.size()-1).add(0, x);
            Cellule.pionBlanc.get(Cellule.pionBlanc.size()-1).add(1, y);
            pos=Cellule.pionBlanc.indexOf(pionCourant.get(0));
            Cellule.pionBlanc.remove(pos);
        }else if (this.couleur== "PN"){
            Cellule.pionNoir.add(new ArrayList<Integer>());
            Cellule.pionNoir.get(Cellule.pionNoir.size()-1).add(0, x);
            Cellule.pionNoir.get(Cellule.pionNoir.size()-1).add(1, y);
            pos=Cellule.pionNoir.indexOf(pionCourant.get(0));
            Cellule.pionNoir.remove(pos);
        }
        System.out.println("121 pos"+Cellule.pionBlanc);
        Cellule.swapTurn();
        setCouleur("");
    }

    public void prise(int x, int y){
        System.out.println("PRISE X : "+x+ " Y : "+ y);
        int pos = 0;
        ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer> >();
        temp.add(new ArrayList<Integer>());
        temp.get(0).add(0, x);
        temp.get(0).add(1, y);
        if(this.couleur == "PN"){
            pos=Cellule.pionBlanc.indexOf(temp.get(0));
            Cellule.pionBlanc.remove(pos);
        }else if (this.couleur == "PB"){
            System.out.println("pos : " +Cellule.pionNoir.indexOf(temp.get(0)));
            pos=Cellule.pionNoir.indexOf(temp.get(0));
            Cellule.pionNoir.remove(pos);
        }
    }

    public String verifPrise(int x,int y){
        String valueReturn = "";
        if(this.couleur == "PB"){
            if(getY() == y + 1 || Math.abs(getX() - x) == 1  ) {
                valueReturn = "VIDE";
            } else if (getY()== y + 2 || Math.abs(getX()- x) == 2) {
                // manger à gauche
                if((getX() - x) == 2 && Cellule.verifCaseValide(getX() -1,getY()- 1) == "PN") {
                    valueReturn = "PRISE_PB_G";
                    //
                }else if (getX() - x == -2 && Cellule.verifCaseValide(getX() +1,getY()- 1) == "PN") {
                    valueReturn = "PRISE_PB_D";
                } else {
                    valueReturn = "erreur";
                }
            } else {
                valueReturn = "erreur";
            }
        } else if (this.couleur == "PN"){
            if(getY()== y - 1 || Math.abs(getX() + x) == 1  ) {
                valueReturn = "VIDE";
            } else if (getY()== y - 2 || Math.abs(getX() + x) == 2) {
                System.out.println("HERE");
                // manger à gauche
                if((getX() - x) == 2 && Cellule.verifCaseValide(getX()+1,getY()- 1) == "PN") {
                    valueReturn = "PRISE_PN_G";
                    // manger à droite
                }else if (getX() - x == -2 && Cellule.verifCaseValide(getX()-1,getY()- 1) == "PN") {
                    System.out.println("HERE2");
                    valueReturn = "PRISE_PN_D";
                } else {
                    valueReturn = "erreur";
                }
            } else {
                valueReturn = "erreur";
            }
        } else {
            valueReturn = "VIDE";
        }
        return valueReturn;
    }

    public int getX() {
        return x;
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

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void resetPion(){
        setCouleur("");
    }
}
