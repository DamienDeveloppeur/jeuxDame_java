package queengame;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(int x, int y, Boolean color){
        super(x,y,color);
    }

    public Queen() {
        super(0, 0, true);
    }

    /**
     *
     * @param o case d'arrivée
     */
    @Override
    public void eat(ValidCell o, Piece pieceToEat) {
        deleteAnPiece(pieceToEat);
        this.moove(o, true);
        if(this.ifThisCanTake() != null) {
            Cell.swapTurn();
            Cell.currentPiece = this;
        } else Cell.pieceMustMoove = null;
    }

    public boolean ifNotSameColor(Case p){
        if(p == null) return false;
        if(this.isColor() == p.isColor()) return false;
        else return true;
    }

    public Case testDiago(int coefX, int coefY){
        boolean flagBotRight = false;
        Piece pieceToEat = null;
        for(int i = 1; i < 10; i++){
            Case c = Cell.verifObjectInCase(this.getX()+i * coefX, this.getY()+i * coefY);
            //caseChecked.add(Cell.verifObjectInCase(this.getX()+i, this.getY()+i));
            if(flagBotRight && c instanceof ValidCell)
                return pieceToEat;
            if (flagBotRight && c instanceof Piece)
                return null;

            if(c instanceof Piece &&
                    ifNotSameColor(c)) {
                flagBotRight = true;
                pieceToEat = (Piece) c;
            }
            if(c == null) break;
        }
        return null;
    }

    /**
     * Vérifie qu'une game peut prendre après avoir pris
     * @return
     */
    @Override
    public Case ifThisCanTake(){
        if(testDiago(1,1) != null) return testDiago(1,1);
        else if (testDiago(-1,1) != null) return testDiago(-1,1);
        else if (testDiago(1,-1) != null) return testDiago(1,-1);
        else if (testDiago(-1,-1) != null) return testDiago(-1,-1);
        else return null;
    }

    /**
     *
     * @param o Case to check
     */
    @Override
    public void tryingMoove(ValidCell o) {
        Cell.piecesWhoCanMoove.clear();
        if(isOnADiagonal(o)){
            ArrayList<Case> caseChecked = new ArrayList<>();
            System.out.println("DIAGO");
            // Nbr case to check : this.getX() - o.getX()
            // haut gauche
            int coeffx = this.getCoefficient(this.getX(), o.getX());
            int coeffy = this.getCoefficient(this.getY(), o.getY());
            for(int i = 1; i < Math.abs(this.getX() - o.getX()) + 1; i++) {
                caseChecked.add(Cell.verifObjectInCase(this.getX()+i*coeffx, this.getY()+i*coeffy));
            }
            long count = caseChecked.stream().filter(ctn -> ctn instanceof Piece).count();
            System.out.println("count : "+ count);
            if(count == 0 && Cell.pieceMustMoove == null) {
                this.moove(o, true);
            }
            Case piece = (count == 0) ? null : caseChecked.stream().filter(ctn -> ctn instanceof Piece).findFirst().get();
            if(count == 1 && (piece.isColor() != this.isColor())) {
                System.out.println(piece);
                this.eat(o, (Piece) piece);
            }
        }

    }

    private boolean isOnADiagonal(ValidCell o) {
        return Math.abs(this.getX() - o.getX()) == Math.abs(this.getY() - o.getY());
    }
}
