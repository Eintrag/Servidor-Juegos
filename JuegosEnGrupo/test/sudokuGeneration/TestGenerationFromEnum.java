package sudokuGeneration;

import static org.junit.Assert.*;

import org.junit.Test;

import com.maco.juegosEnGrupo.server.modelo.SudokuBoardsEnum;

public class TestGenerationFromEnum {

	@Test
	public void testGetOneBoard() {
		String board = SudokuBoardsEnum.BOARD1.toString();
		for (int row = 0; row < 9; row++)
		assertEquals(board.toString(), "53  7    "+
				"6  195   "+
				" 98    6 "+
				"8   6   3"+
				"4  8 3  1"+
				"7   2   6"+
				" 6    28 "+
				"   418  5"+
				"    8  79");
	}
	@Test
	public void testGetAnyBoard(){
		int vecesAcierto = 0;
		int vecesIntento = 1000;
		for (int i = 0; i<vecesIntento; i++){
			SudokuBoardsEnum sudokuBoardsEnum = SudokuBoardsEnum.getAny();
			if (sudokuBoardsEnum.toString().equals(SudokuBoardsEnum.BOARD1.toString()) ||
					sudokuBoardsEnum.toString().equals(SudokuBoardsEnum.BOARD2.toString()) || 
					sudokuBoardsEnum.toString().equals(SudokuBoardsEnum.BOARD3.toString())){
				vecesAcierto += 1;
			}
		}
		assertEquals(vecesAcierto, vecesIntento);
	}
}
