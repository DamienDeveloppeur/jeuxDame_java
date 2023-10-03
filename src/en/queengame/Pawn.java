package en.queengame;

public class Pawn extends Piece{
    Pawn(int x, int y, Boolean color){
        super(x,y,color);
    }

    @Override
    public Case ifThisCanTake() {
        int[][] directions = {{1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

        for (int[] direction : directions) {
            int xOffset = direction[0] * 2;
            int yOffset = direction[1] * 2;

            if (verifForEat(direction[0], direction[1]) != null &&
                    Cell.verifObjectInCase(this.getX() + xOffset, this.getY() + yOffset) instanceof ValidCell) {
                return verifForEat(direction[0], direction[1]);
            }
        }

        return null;
    }

    // surcharge de méthode
    @Override
    public void tryingMoove(ValidCell o) {
        Cell.piecesWhoCanMoove.clear();
        // On doit obligatoirement manger si pieceMustMoove != null
        if(getHeuristic(this,o) == 2 && this.getX() != o.getX() &&
                ((this.isColor() && this.getY() - o.getY() > 0) || (!this.isColor() && this.getY() - o.getY() < 0)) &&
                Cell.pieceMustMoove == null
        ) {
            this.moove(o, true);
        } else if(getHeuristic(this,o) == 4 && this.getX() != o.getX() &&
                (Math.abs(this.getX() - o.getX()) == 2 && Math.abs(this.getY() - o.getY()) == 2)){
            int[] arr = this.tryingToEat(o);
            if(arr != null && verifForEat(arr[0], arr[1]) != null){
                this.eat(o, arr[0], arr[1]);
            }
        }
    }

    /**
     *
     * @param o case d'arrivée
     * @param x
     * @param y
     */

    public void eat(ValidCell o,int x, int y){
        System.out.println("THIS : " + this);
        Case objectToCheck = Cell.verifObjectInCase(this.getX() + x,this.getY() + y);
        // go delete the piece and moove
        // test if piece can eat again with valid cell
        deleteAnPiece((Piece) objectToCheck);
        System.out.println("ALERTE, this.ifThisCanTake() : " + this.ifThisCanTake());
        // launch ifThisCanTake
        this.moove(o, false);
        if(this.ifThisCanTake() != null) {
            System.out.println("ALERTE, this : " + this);
            //Cell.swapTurn();
            Cell.currentPiece = this;
            Cell.pieceMustMoove = this;
        } else {
            Cell.pieceMustMoove = null;
            Cell.swapTurn();
        }
    }

    /**
     *
     * Used by the bot
     * @param o
     * @param p
     */
    @Override
    public void eat(ValidCell o,Piece p){
        System.out.println("THIS : "+ this);
        // go delete the piece and moove
        System.out.println("MIAM MIAM MIAM 2");
        deleteAnPiece(p);
        // launch ifThisCanTake
        this.moove(o, false);
        if(this.ifThisCanTake() != null) {
            System.out.println("ALERTE IL DEVRAIT PRENDRE ");
            Cell.currentPiece = this;
            Cell.pieceMustMoove = this;
            Bot.mooveBot();
        } else {
            Cell.pieceMustMoove = null;
            Cell.swapTurn();
        }
    }

}
