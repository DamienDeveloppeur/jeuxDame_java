package en.queengame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import queengame.Piece;
import queengame.Queen;
import queengame.ValidCell;

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

    @BeforeEach
    void setUp() {
        //sut = new Queen(4,6,true);
        sut.setX(4);
        sut.setY(6);
        sut.setColor(true);
    }

    @Test
    void testTryingMooveQueen(){
        ValidCell validCell = new ValidCell(2,4);

        sut.tryingMoove(validCell);
        //https://stackoverflow.com/questions/30774358/how-can-i-mock-methods-of-injectmocks-class
        verify(sut, times(1))
                .moove(validCell, true);
    }
}

//    @Test
//    void testTryingMooveQueen2(){
//        ValidCell validCell = new ValidCell(0,2);
//
//        sut.tryingMoove(validCell);
//        verify(queenMock,
//                times(1))
//                .moove(any(), any());
//    }