package swe4.tests;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import swe4.Board;
import swe4.Move;
import swe4.NoSolutionException;
import swe4.SlidingPuzzle;

public class SlidingPuzzleSolverTest {

  @Test
  public void solveSimplePuzzleTest1() {
    try {
      SlidingPuzzle solver = new SlidingPuzzle();
      Board board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 0);
      board.setTile(3, 3, 8);
    
      List<Move> moves = solver.solve(board);
      assertEquals(1, moves.size());
      assertTrue(moves.get(0).row == 3 && moves.get(0).column == 3);
    }
    catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }

  
  @Test
  public void solveSimplePuzzleTest2() {
    try {
      SlidingPuzzle solver = new SlidingPuzzle();
      Board board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 0);
      board.setTile(3, 2, 7);
      board.setTile(3, 3, 8);
    
      List<Move> moves = solver.solve(board);
      assertEquals(2, moves.size());
      assertTrue(moves.get(0).row == 3 && moves.get(0).column == 2);
      assertTrue(moves.get(1).row == 3 && moves.get(1).column == 3);
    }
    catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }

  @Test
  public void solveComplexPuzzleTest1() {

    try {
      SlidingPuzzle solver = new SlidingPuzzle();

      //  8  2  7 
      //  1  4  6 
      //  3  5  X 
      Board board = new Board(3);      
      board.setTile(1, 1, 8);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 7);
      board.setTile(2, 1, 1);
      board.setTile(2, 2, 4);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 3);
      board.setTile(3, 2, 5);
      board.setTile(3, 3, 0);
    
      List<Move> moves = solver.solve(board);
      board.makeMoves(moves);
      assertEquals(new Board(3), board);
    }
    catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }

  @Test
  public void solveRandomPuzzlesTest() {
    SlidingPuzzle solver = new SlidingPuzzle();

    for (int k = 0; k < 50; k++) {
      try {
        Board board = new Board(3);
        int n = 1;
        int maxN = board.size() * board.size();
        for (int i = 1; i <= board.size(); i++)
          for (int j = 1; j <= board.size(); j++)
            board.setTile(i, j, (n++) % maxN);

        board.shuffle();
                
        List<Move> moves = solver.solve(board);
        board.makeMoves(moves);
        assertEquals(new Board(3), board);
      }
      catch (NoSolutionException nse) {
        fail("NoSolutionException is not expected.");
      }
    }
  }
  
  @Test
  public void solveSimplePuzzleTest_4x4() {
    try {
      SlidingPuzzle solver = new SlidingPuzzle();
      Board board = new Board(4);      

      board.moveLeft();
      
      List<Move> moves = solver.solve(board);
      assertEquals(1, moves.size());
      assertTrue(moves.get(0).row == 4 && moves.get(0).column == 4);
    }
    catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }

  @Test
  public void solveComplexPuzzleTest_4x4() {
    try {
      SlidingPuzzle solver = new SlidingPuzzle();
      Board board = new Board(4);      

      board.moveLeft();
      board.moveLeft();
      board.moveUp();
      board.moveLeft();
      board.moveUp();
      board.moveUp();
      board.moveRight();
      board.moveDown();
      board.moveLeft();
      
      List<Move> moves = solver.solve(board);
      board.makeMoves(moves);
      assertEquals(new Board(4), board);
    }
    catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }
}
