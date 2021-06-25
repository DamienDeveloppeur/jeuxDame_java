package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Piece {
    protected String couleur = "";
    protected int x;
    protected int y;
    static ArrayList<ArrayList<Integer>> pieceTaked = new ArrayList<ArrayList<Integer>>();
    protected String colorPieceTaked;
    public int counter = 0;

    public Piece(int x, int y, String couleur){
        this.x = x;
        this.y = y;
        this.couleur = couleur;
    }
    /**
     * @param x arrival abscisse of the piece
     * @param y arrival ordonne of the piece
     */
    public void deplacement(int x, int y) {
        int pos = 0;
        ArrayList<ArrayList<Integer>> pionCourant = new ArrayList<ArrayList<Integer> >();
        pionCourant.add(new ArrayList<Integer>());
        pionCourant.get(0).add(0, getX());
        pionCourant.get(0).add(1, getY());
        if(this.couleur.equals("PB")){
            if(y == 0){
                Cellule.dameBlanc.add(new ArrayList<Integer>());
                Cellule.dameBlanc.get(Cellule.dameBlanc.size()-1).add(0, x);
                Cellule.dameBlanc.get(Cellule.dameBlanc.size()-1).add(1, y);
            }else {
                Cellule.pionBlanc.add(new ArrayList<Integer>());
                Cellule.pionBlanc.get(Cellule.pionBlanc.size()-1).add(0, x);
                Cellule.pionBlanc.get(Cellule.pionBlanc.size()-1).add(1, y);
            }
            pos=Cellule.pionBlanc.indexOf(pionCourant.get(0));
            Cellule.pionBlanc.remove(pos);
        }else if (this.couleur.equals("PN")){
            if(y == 9){
                Cellule.dameNoir.add(new ArrayList<Integer>());
                Cellule.dameNoir.get(Cellule.dameNoir.size()-1).add(0, x);
                Cellule.dameNoir.get(Cellule.dameNoir.size()-1).add(1, y);
            }else {
                Cellule.pionNoir.add(new ArrayList<Integer>());
                Cellule.pionNoir.get(Cellule.pionNoir.size()-1).add(0, x);
                Cellule.pionNoir.get(Cellule.pionNoir.size()-1).add(1, y);
            }
            pos=Cellule.pionNoir.indexOf(pionCourant.get(0));
            Cellule.pionNoir.remove(pos);
        } else if (this.couleur.equals("DB")) {
            Cellule.dameBlanc.add(new ArrayList<Integer>());
            Cellule.dameBlanc.get(Cellule.dameBlanc.size()-1).add(0, x);
            Cellule.dameBlanc.get(Cellule.dameBlanc.size()-1).add(1, y);
            pos=Cellule.dameBlanc.indexOf(pionCourant.get(0));
            Cellule.dameBlanc.remove(pos);
        } else if (this.couleur.equals("DN")) {
            Cellule.dameNoir.add(new ArrayList<Integer>());
            Cellule.dameNoir.get(Cellule.dameNoir.size()-1).add(0, x);
            Cellule.dameNoir.get(Cellule.dameNoir.size()-1).add(1, y);
            pos=Cellule.dameNoir.indexOf(pionCourant.get(0));
            Cellule.dameNoir.remove(pos);
        }
        Cellule.swapTurn(true);
        setCouleur("");
        Cellule.currentPion = null;
    }

    /**
     *
     * @param x abscissa of piece witch be taked
     * @param y ordonne of piece witch be taked
     * @param initX abscisse initial of square witch piece will moove
     * @param initY ordonne initial of square witch piece will moove
     * @return if the piece who taken, can take again
     */
    public boolean prise(int x, int y, int initX, int initY){
        int pos = 0;
        ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer> >();
        temp.add(new ArrayList<Integer>());
        temp.get(0).add(0, x);
        temp.get(0).add(1, y);
        String checkCase = Cellule.verifCaseValide(x,y);
        if(checkCase.equals("PB")){
            pos=Cellule.pionBlanc.indexOf(temp.get(0));
            Cellule.pionBlanc.remove(pos);
        }else if (checkCase.equals("PN")){
            pos=Cellule.pionNoir.indexOf(temp.get(0));
            Cellule.pionNoir.remove(pos);
        }else if (checkCase.equals("DB")){
            pos=Cellule.dameBlanc.indexOf(temp.get(0));
            Cellule.dameBlanc.remove(pos);
        }else if (checkCase.equals("DN")){
            pos=Cellule.dameNoir.indexOf(temp.get(0));
            Cellule.dameNoir.remove(pos);
        }
        int tempX = getX();
        int tempY = getY();

        setX(initX);
        setY(initY);

        String ifOneCanTake = ifOneCanTake(initX, initY, getCouleur());
        if(ifOneCanTake.equals("VIDE") || ifOneCanTake.equals("erreur")){
            setX(tempX);
            setY(tempY);
            return false;
        } else {
            setX(tempX);
            setY(tempY);
            return true;
        }
    }
    /**
     * Check if a piece is taken when a piece moove
     * @param x arrival square to test
     * @param y arrival square to test
     * @return if a piece is taked and witch piece exactly
     */
    public String verifPrise(int x, int y){
        String valueReturn = "";
        if(this.couleur.equals("PB")){
            if(getY() == y + 1 && Math.abs(getX() - x) == 1  ) {
                valueReturn = "VIDE";
            } else if (getY()== y + 2 || Math.abs(getX()- x) == 2) {
                // manger à gauche
                // il manque la gestion des cases vides
                if((getX() - x) == 2 && Cellule.verifCaseValide(getX() -1,getY()- 1) == "PN" && Cellule.verifCaseValide(x,y) == "VIDE") {
                    valueReturn = "PRISE_PB_G";
                    //
                }else if (getX() - x == -2 && Cellule.verifCaseValide(getX() +1,getY()- 1) == "PN" && Cellule.verifCaseValide(x,y) == "VIDE") {
                    valueReturn = "PRISE_PB_D";
                } else {
                    valueReturn = "erreur";
                }
            } else {
                valueReturn = "erreur";
            }
        } else if (this.couleur.equals("PN")){
            if(getY()== y - 1 && Math.abs(getX() - x) == 1) {
                valueReturn = "VIDE";
            } else if (getY()== y - 2 || Math.abs(getX() + x) == 2) {
                // manger à gauche
                if(getX() - x == 2 && Cellule.verifCaseValide(getX() - 1,getY() + 1) == "PB" && Cellule.verifCaseValide(x,y) == "VIDE") {
                    valueReturn = "PRISE_PN_G";
                    // manger à droite
                }else if (getX() - x == -2 && Cellule.verifCaseValide(getX() + 1,getY() + 1) == "PB" && Cellule.verifCaseValide(x,y) == "VIDE") {
                    valueReturn = "PRISE_PN_D";
                } else {
                    valueReturn = "erreur";
                }
            } else {
                valueReturn = "erreur";
            }
        } else if (this.couleur.equals("DB") || this.couleur.equals("DN")){
            // le pion se trouve sur la diagonale
            if(Math.abs(getY() - y) == Math.abs(getX() - x)){
                String caseSelected = "erreur";
                int tempX = 0;
                int tempY = 0;
                for (int i=1; i<Math.abs(getY() - y); i++) {
                    caseSelected = "erreur";
                    // haut - gauche
                    if(getX() > x && getY() > y){
                        if(Cellule.verifCaseValide(getX() - i,getY() - i) != "VIDE" && Cellule.verifCaseValide(getX() - i,getY() - i) != "erreur") {
                            caseSelected =  Cellule.verifCaseValide(getX() - i,getY() - i);
                            tempX = getX() - i;
                            tempY = getY() - i;
                        }
                        //bas - droite
                    } else if(getX() > x && getY() < y) {
                        if(Cellule.verifCaseValide(getX() - i,getY() + i) != "VIDE" && Cellule.verifCaseValide(getX() - i,getY() + i) != "erreur") {
                            caseSelected =  Cellule.verifCaseValide(getX() - i,getY() + i);
                            tempX = getX() - i;
                            tempY = getY() + i;
                        }
                        // bas - gauche
                    } else if(getX() < x && getY() > y) {
                        if(Cellule.verifCaseValide(getX() + i,getY() - i) != "VIDE" && Cellule.verifCaseValide(getX() + i,getY() - i) != "erreur") {
                            caseSelected = Cellule.verifCaseValide(getX() + i,getY() - i);
                            tempX = getX() + i;
                            tempY = getY() - i;
                        }
                        // bas - droite
                    } else if(getX() < x && getY() < y) {
                        if(Cellule.verifCaseValide(getX() + i,getY() + i) != "VIDE" && Cellule.verifCaseValide(getX() + i,getY() + i) != "erreur") {
                            caseSelected =  Cellule.verifCaseValide(getX() + i,getY() + i);
                            tempX = getX() + i;
                            tempY = getY() + i;
                        }
                    }
                    // ajouter chaque case selectionnée dans un array list
                    if(caseSelected != "VIDE" && caseSelected != "erreur") {
                        pieceTaked.add(new ArrayList<Integer>());
                        pieceTaked.get(0).add(0, tempX);
                        pieceTaked.get(0).add(1, tempY);
                        colorPieceTaked = caseSelected;
                    }
                }
                if(pieceTaked.size() > 1) {
                    valueReturn = "erreur";
                } else if(this.couleur == "DB" && (colorPieceTaked == "PN" || colorPieceTaked == "DN") && pieceTaked.size() == 1){
                    valueReturn = "PRISE_D";
                }else if(this.couleur == "DN" && (colorPieceTaked == "PB" || colorPieceTaked == "DB") && pieceTaked.size() == 1){
                    valueReturn = "PRISE_D";
                } else if (pieceTaked.size() == 0) {
                    valueReturn = "VIDE";
                } else {
                    valueReturn = "erreur";
                }
            } else {
                valueReturn = "erreur";
            }
        } else {
            valueReturn = "VIDE";
        }
        colorPieceTaked = "";
        return valueReturn;
    }

    /**
     *
     * @param color color of array list to test (white or black)
     * @return error if no piece can be taked, prise in contrast
     */
    public String ifCanTake(String color) {
        ArrayList<ArrayList<Integer>> arrayPiece = new ArrayList<ArrayList<Integer> >();
        String error = "erreur";
        int tempX = getX();
        int tempY = getY();
        String vallueReturn;
        switch (color) {
            case "PB":
                arrayPiece = Cellule.pionBlanc;
                //Y -= 2;
                break;
            case "PN":
                arrayPiece = Cellule.pionNoir;
                //Y += 2;
                break;
        }
        for (int i = 0; i < arrayPiece.size(); i++){
            setX(arrayPiece.get(i).get(0));
            setY(arrayPiece.get(i).get(1));
                if (color.equals("PB")){
                    error = verifPrise(getX() + 2,getY() - 2);
                }else {
                    error = verifPrise(getX() + 2,getY() + 2);
                }
                if((error.equals("PRISE_PB_G") || error.equals("PRISE_PB_D") || error.equals("PRISE_PN_G") || error.equals("PRISE_PN_D"))){
                    vallueReturn = ""+ arrayPiece.get(i).get(0) + arrayPiece.get(i).get(1);
                    return vallueReturn;
                }
                if (color.equals("PB")){
                    error = verifPrise(getX() - 2,getY() - 2);
                }else {
                    error = verifPrise(getX() - 2,getY() + 2);
                }
                if((error.equals("PRISE_PB_G") || error.equals("PRISE_PB_D") || error.equals("PRISE_PN_G") || error.equals("PRISE_PN_D"))){
                    vallueReturn = ""+ arrayPiece.get(i).get(0) + arrayPiece.get(i).get(1);
                    return vallueReturn;
                }
        }
        setX(tempX);
        setY(tempY);
        return error;
    }

    /**
     *
     * @param X
     * @param Y
     * @param color "PB/PN"
     * @return
     */
    public String ifOneCanTake(int X, int Y, String color){
        int mooveY;
        String vallueReturn = "";
        String error = "erreur";
        if(color.substring(1,2).equals("N")) {
            mooveY = Cellule.getTakeBlackY(Y);
        } else {
            mooveY = Cellule.getTakeWhiteY(Y);
        }
        error = verifPrise(X + 2,mooveY);
        if((error.equals("PRISE_PB_G") || error.equals("PRISE_PB_D") || error.equals("PRISE_PN_G") || error.equals("PRISE_PN_D"))){
            vallueReturn = "" + X+Y;
            return vallueReturn;
        }
        error = verifPrise(X - 2,mooveY);
        if((error.equals("PRISE_PB_G") || error.equals("PRISE_PB_D") || error.equals("PRISE_PN_G") || error.equals("PRISE_PN_D"))){
            vallueReturn = "" + X+Y;
            return vallueReturn;
        }
        return error;
    }

    /**
     * Verify if queen can take for bot and human
     * @return
     */
    public String ifQueenCanBeTake(int x, int y) {
       return "";
    }

    public String ifOneCantBeTaked(){
        return "";
    }

    public Map<String, Integer> ifQueenCanTake(){
        Map<String, Integer> map = new HashMap<String, Integer>();
            String caseSelected = "erreur";
            int varOneX = 1, varOneY = 1;
            // haut - gauche
            while (Cellule.verifCaseValide(getX() - varOneX,getY() - varOneY) != "erreur"){
                caseSelected =  Cellule.verifCaseValide(getX() - varOneX,getY() - varOneY);
                if(this.couleur == "DN" && caseSelected.substring(1,2).equals("N") || this.couleur == "DB" && caseSelected.substring(1,2).equals("B")) {
                    break;
                } else if (!caseSelected.equals("VIDE") && Cellule.verifCaseValide(getX() - (varOneX + 1),getY() - (varOneY + 1)).equals("VIDE")) {
                    map.put("pieceTakedX", getX() - varOneX);
                    map.put("pieceTakedY", getY() - varOneY);
                    map.put("arrivalSquareX", getX() - (varOneX + 1));
                    map.put("arrivalSquareY", getY() - (varOneY + 1));
                    return map;
                }
                varOneX++;
                varOneY++;
            }
            varOneX = 1;
            varOneY = 1;
            pieceTaked.clear();
            // haut - droite
            while (Cellule.verifCaseValide(getX() + varOneX,getY() - varOneY) != "erreur"){
                caseSelected =  Cellule.verifCaseValide(getX() + varOneX,getY() - varOneY);
                if(this.couleur == "DN" && caseSelected.substring(1,2).equals("N") || this.couleur == "DB" && caseSelected.substring(1,2).equals("B")) {
                    break;
                } else if (!caseSelected.equals("VIDE") && Cellule.verifCaseValide(getX() - (varOneX + 1),getY() - (varOneY + 1)).equals("VIDE")) {
                    map.put("pieceTakedX", getX() + varOneX);
                    map.put("pieceTakedY", getY() - varOneY);
                    map.put("arrivalSquareX", getX() + (varOneX + 1));
                    map.put("arrivalSquareY", getY() - (varOneY + 1));
                    return map;
                }
                varOneX++;
                varOneY++;
            }
            varOneX = getX();
            varOneY = getY();
            pieceTaked.clear();
            // bas - gauche
            while (Cellule.verifCaseValide(getX() - varOneX,getY() + varOneY) != "erreur"){
                caseSelected =  Cellule.verifCaseValide(getX() - varOneX,getY() + varOneY);
                if(this.couleur == "DN" && caseSelected.substring(1,2).equals("N") || this.couleur == "DB" && caseSelected.substring(1,2).equals("B")) {
                    break;
                } else if (!caseSelected.equals("VIDE") && Cellule.verifCaseValide(getX() - (varOneX + 1),getY() + (varOneY + 1)).equals("VIDE")) {
                    map.put("pieceTakedX", getX() - varOneX);
                    map.put("pieceTakedY", getY() + varOneY);
                    map.put("arrivalSquareX", getX() - (varOneX + 1));
                    map.put("arrivalSquareY", getY() + (varOneY + 1));
                    return map;
                }
                varOneX++;
                varOneY++;
            }
            varOneX = getX();
            varOneY = getY();
            pieceTaked.clear();
            // bas - droite
            while (Cellule.verifCaseValide(getX() + varOneX,getY() + varOneY) != "erreur"){
                caseSelected =  Cellule.verifCaseValide(getX() + varOneX,getY() + varOneY);
                if(this.couleur == "DN" && caseSelected.substring(1,2).equals("N") || this.couleur == "DB" && caseSelected.substring(1,2).equals("B")) {
                    break;
                } else if (!caseSelected.equals("VIDE") && Cellule.verifCaseValide(getX() + (varOneX + 1),getY() + (varOneY + 1)).equals("VIDE")) {
                    map.put("pieceTakedX", getX() + varOneX);
                    map.put("pieceTakedY", getY() + varOneY);
                    map.put("arrivalSquareX", getX() - (varOneX + 1));
                    map.put("arrivalSquareY", getY() - (varOneY + 1));
                    return map;
                }
                varOneX++;
                varOneY++;
            }
        return map;
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

    public static ArrayList<ArrayList<Integer>> getPieceTaked() {
        return pieceTaked;
    }

    public static void setPieceTaked(ArrayList<ArrayList<Integer>> pieceTaked) {
        Piece.pieceTaked = pieceTaked;
    }
}
