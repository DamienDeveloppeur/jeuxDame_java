package en.queengame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @InjectMocks
    private Queen sut;

    @Mock
    private Piece queenMock;

    @BeforeEach
    void setUp() {
        sut = new Queen(4,6,true);
    }

    @Test
    void testTryingMooveQueen(){
        ValidCell validCell = new ValidCell(0,2);

        sut.tryingMoove(validCell);
        verify(queenMock, times(1))
                .moove(any(ValidCell.class), anyBoolean());
    }

}
