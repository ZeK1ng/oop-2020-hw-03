import static org.junit.Assert.*;

import org.junit.Test;

public class SudokuTest {

	@Test
	public void test() {
		Sudoku newSudoku = new Sudoku(Sudoku.hardGrid);
		Sudoku.main(null);
		assertEquals(5, newSudoku.solve());
		
		assertFalse(newSudoku.getElapsed()<0);
	
	}
	@Test
	public void testMany() {
		Sudoku newSudoku;
		
		String testInp = "";
		for(int i=0;i<81; i++) {
			testInp+="0";
		}
		newSudoku = new Sudoku(testInp);
	}
	@Test
	public void  testbadInput() {
		Sudoku newSudoku;

		String badInput = "";
		for(int i=0; i<90; i++) {
			badInput+="0";
		}
		try {
			newSudoku = new Sudoku(badInput);
		}catch(Exception e) {
		}
	}
	

	

}
