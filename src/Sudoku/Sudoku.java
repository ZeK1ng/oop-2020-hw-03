package Sudoku;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Encapsulates a Sudoku.Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solution_count if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 5 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_solution_count = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	public Sudoku(String sudstr) {
		this(textToGrid(sudstr));
	}
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);
		
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solution_count:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}
	
	private class Spot implements Comparable<Spot>{
		private int row,col,val ,validSize;
		
		public Spot (int row , int col) {
			this.row = row;
			this.col = col;
			val = 0 ;
			validSize = getvalidValues().size();
			assertEquals(this.getVal(), sudoku_board[row][col]);
		}
		public void setVal(int val) {
			this.val  = val;
			sudoku_board[row][col] = val;
		}
		public int getVal() {
			return val;
		}
		public int getValidSize() {
			return validSize;
		}
		/**
		 * returns the set containing the valid values 
		 * valid values are the valuse in range 1 to 9 inclusive those are not already in the board
		 * */
		public HashSet<Integer> getvalidValues(){
			HashSet<Integer> res = new HashSet<Integer>();
			for(int i = 1 ;i<SIZE+1; i++) {
				res.add(i);
			}
			// clear possible rows
			for(int i = 0 ; i < SIZE; i++) {
				res.remove(sudoku_board[i][this.col]);
			}
			for(int i = 0; i<SIZE; i++) {
				res.remove(sudoku_board[this.row][i]);
			}
			
			int startR = (this.row/PART)*PART;
			int startC = (this.col/PART)*PART;
			for(int i = startR; i < startR+PART; i++) {
				for(int j = startC ; j < startC+PART; j++) {
					res.remove(sudoku_board[i][j]);
				}
			}
//			System.out.println("GetValidVals.Size"+res.size());
			return res;
		}
		
		/**
		 *	returns the difference between this.spot`s and the otherSpot`s
		 *				validSizes   
		 * 
		 * */
		@Override
		public int compareTo(Spot otherSpot) {
			
			return this.validSize - otherSpot.getValidSize();
		}

	}
	
	
	private int sudoku_board[][];
	private int solution_board[][];
	private List<Spot> spotList;
	private int solution_count;
	private long timeElapsed;
	
	
	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		sudoku_board = new int[SIZE][SIZE];
		solution_board = new int[SIZE][SIZE];
		solution_count = 0;
		timeElapsed= 0;
		for(int i= 0; i< SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				sudoku_board[i][j]=ints[i][j];
			}
		}
		initSpots();
	}

	
	
	/**
	 * initializes the SpotsList. adds for the points whose values are zero
	 * */
	private void initSpots() {
		spotList = new ArrayList<Spot>();
		for(int i = 0; i< SIZE; i++) {
			for(int j =0 ; j< SIZE ; j++) {
				if(sudoku_board[i][j] == 0 ) {
					Spot newSp = new Spot(i,j);
					spotList.add(newSp);
				}
			}
		}
		Collections.sort(spotList);
	}


	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		long startMoment = System.currentTimeMillis();
		solvePuzzle(0);
		long endMoment = System.currentTimeMillis();
		timeElapsed = endMoment - startMoment;
		return solution_count;
	}
	/**This is a recursive function searching for
	 *  every possible solution and summing up their total count
	 * 
	 * @param point : current point of the board 
	 * 					if point equals to the size of the 
	 * 					spotList list than the puzzle is solved
	 */
	private void solvePuzzle(int point) {
		// `
		if(solution_count>=MAX_solution_count)return;
		if(point > spotList.size()) return;
		
		
		
		if(point == spotList.size()) {
			if(solution_count == 0) {
				for(int i=0 ; i< SIZE; i++) {
					for(int j=0;j<SIZE; j++) {
						solution_board[i][j]=sudoku_board[i][j];
					}
			
				}
			}
			
			solution_count++;
			return;
		}
		
		Spot curr = spotList.get(point);
		HashSet<Integer> valid = curr.getvalidValues();
			
			
		for(int n : valid) {
			curr.setVal(n);
			solvePuzzle(point+1);
			curr.setVal(0);
		}
		
		
	}

	/**
	 * If there exists a solution to the given sudoku board
	 * 	return it as a string 
	 * if there doesnt exist a solution then it returns an empty string
	 * */
	
	public String getSolutionText() {
		if(solution_count >=1) {
			Sudoku solutSudoku=new Sudoku(solution_board);
			return solutSudoku.toString();
		}
		return "";
	}
	/*
	 * overrides the toString method and convert sudoku board 
	 * 			to string using StringBuilder Class
	 * */
	
	@Override
	public String toString() {
		StringBuilder resStrBuffer = new StringBuilder();
		
		for(int i=0; i<SIZE; i++) {
			for(int j = 0 ; j< SIZE; j++) {
				if(j != 0 ) {
					resStrBuffer.append(" ");
				}
				resStrBuffer.append(sudoku_board[i][j]);
			}
			resStrBuffer.append("\n");
		}
		
		return resStrBuffer.toString();
	}
	/**
	 * return the time spent on the solving puzzle
	 * */
	public long getElapsed() {
		return timeElapsed; 
	}

}
