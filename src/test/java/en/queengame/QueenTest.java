package en.queengame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import queengame.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QueenTest {
    @Spy
    @InjectMocks
    private Queen sut;

    @Spy
    private Queen queenMock;

    @Mock
    private Cell cell;

    @BeforeEach
    void setUp() {
        //sut = new Queen(4,6,true);
        sut.setX(4);
        sut.setY(6);
        sut.setColor(true);
        Cell.blackPiece.clear();
        Cell.whitePiece.clear();
        Cell.caseValide.clear();
    }

    @Test
    void testTryingMooveQueenOK(){
        ValidCell validCell = new ValidCell(2,4);

        sut.tryingMoove(validCell);
        //https://stackoverflow.com/questions/30774358/how-can-i-mock-methods-of-injectmocks-class
        verify(sut, times(1))
                .moove(validCell, true);
    }
    @Test
    void testTryingMooveQueenNotOk(){
        ValidCell validCell = new ValidCell(4,4);

        sut.tryingMoove(validCell);
        //https://stackoverflow.com/questions/30774358/how-can-i-mock-methods-of-injectmocks-class
        verify(sut, times(0))
                .moove(validCell, true);
    }
    @Test
    void testQueenEat(){
        Piece pawnBlack = new Pawn(3,5,false);
        ValidCell validCell = new ValidCell(2,4);
        Cell.blackPiece.add(pawnBlack);
        sut.tryingMoove(validCell);
        verify(sut, times(1)).eat(validCell, pawnBlack);
    }
    @Test
    void testQueenNotEat(){
        Piece whitePawn = new Pawn(3,5,true);
        ValidCell validCell = new ValidCell(2,4);
        Cell.blackPiece.add(whitePawn);
        sut.tryingMoove(validCell);
        verify(sut, times(0)).eat(validCell, whitePawn);
        verify(sut, times(0)).moove(validCell, true);
    }
    @Test
    void testIfThisCanTakeOK(){
        Piece whitePawn = new Pawn(3,5,false);
        ValidCell validCell = new ValidCell(2,4);
        Cell.blackPiece.add(whitePawn);
        Cell.caseValide.add(validCell);
        Case casePotential = sut.ifThisCanTake();

        Assertions.assertNotNull(casePotential);
    }
    @Test
    void testIfThisCanTakeNotOK(){
        Piece whitePawn = new Pawn(3,5,false);
        ValidCell validCell = new ValidCell(5,7);
        Cell.blackPiece.add(whitePawn);
        Cell.caseValide.add(validCell);
        Case casePotential = sut.ifThisCanTake();

        Assertions.assertNull(casePotential);
    }
}