package en.queengame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import queengame.Pawn;
import queengame.Piece;
import queengame.Queen;
import queengame.ValidCell;

class PawnTest {

    @InjectMocks
    private Pawn sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); // Initialise les mocks
    }

    @Test
    void testgetCoefficientX(){
        Piece queenToMoove = new Queen(4,6,true);
        ValidCell validCell = new ValidCell(0,2);
        int negativeValue = sut.getCoefficient(queenToMoove.getX(),validCell.getX());
        Assertions.assertEquals(-1,negativeValue);
    }

    @Test
    void testgetCoefficientY(){
        Piece queenToMoove = new Queen(0,2,true);
        ValidCell validCell = new ValidCell(4,6);
        int positivValue = sut.getCoefficient(queenToMoove.getX(),validCell.getX());
        Assertions.assertEquals(1,positivValue);
    }

}
