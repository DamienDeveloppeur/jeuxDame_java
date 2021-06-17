package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Human extends Cellule implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        boolean taked = false;
        Point pt = e.getPoint();
        pt.x/=ech.width;
        pt.y/=ech.height;
        String caseVerif = verifCaseValide(pt.x,pt.y);
        if(caseVerif.equals("PB")  || caseVerif.equals("PN") || caseVerif.equals("DB") || caseVerif.equals("DN")){
            if(currentPion == null && caseVerif.substring(1,2).equals(getTurn())){
                currentPion = new Piece(pt.x, pt.y,caseVerif);
                String errorOne = currentPion.ifOneCanTake(pt.x, pt.y,caseVerif);
                if(!errorOne.equals("prise")){
                    String error = currentPion.ifCanTake(pt.x, pt.y,caseVerif);
                    if(!(error.equals("erreur") || error.equals("VIDE") )){
                        currentPion = null;
                    } else {
                        currentPion = null;
                        currentPion = new Piece(pt.x, pt.y,caseVerif);
                    }
                } else {
                    currentPion = new Piece(pt.x, pt.y,caseVerif);
                }
            } else {
                currentPion = null;
            }
        } else if(caseVerif.equals("VIDE") && currentPion != null){
            String verifPrise =  currentPion.verifPrise(pt.x, pt.y);
            if (verifPrise.equals("PRISE_PB_G")){
                taked = currentPion.prise(pt.x + 1, pt.y + 1,pt.x, pt.y);
                currentPion.deplacement(pt.x,pt.y);
            } else if (verifPrise.equals("PRISE_PB_D")){
                taked = currentPion.prise(pt.x - 1, pt.y + 1,pt.x, pt.y);
                currentPion.deplacement(pt.x,pt.y);
            } else if (verifPrise.equals("PRISE_PN_G")){
                taked = currentPion.prise(pt.x + 1, pt.y - 1,pt.x, pt.y);
                currentPion.deplacement(pt.x,pt.y);
            } else if (verifPrise.equals("PRISE_PN_D")){
                taked = currentPion.prise(pt.x - 1, pt.y - 1,pt.x, pt.y);
                currentPion.deplacement(pt.x,pt.y);
            }else if (verifPrise.equals("PRISE_D")){
                taked = currentPion.prise(Piece.getPieceTaked().get(0).get(0), Piece.getPieceTaked().get(0).get(1),pt.x, pt.y);
                currentPion.deplacement(pt.x,pt.y);
                Piece.pieceTaked.clear();
            }
            if(verifPrise.equals("PRISE_PB_G") || verifPrise.equals("PRISE_PB_D") || verifPrise.equals("PRISE_PN_G") || verifPrise.equals("PRISE_PN_D")){

            }
            if (verifPrise.equals("VIDE")) {
                currentPion.deplacement(pt.x,pt.y);
            }
        }
        System.out.println(taked);
        if (!taked) {
            Piece.pieceTaked.clear();
            swapTurn();
        }else {

        }
        repaint();
    }
}
