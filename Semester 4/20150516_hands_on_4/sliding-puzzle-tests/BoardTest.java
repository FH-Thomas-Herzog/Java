package swe4.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import swe4.Board;
import swe4.BoardException;

public class BoardTest {

  @Test
  public void simpleIsValidTest() {
    Board board;
    try {
      board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 8);
      board.setTile(3, 3, 0);

      assertTrue(board.isValid());
    }
    catch (BoardException e) {
      fail("BoardException not expected.");
    } 
  }

  @Test
  public void simpleIsNotValidTest() {
    Board board;
    try {
      board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 1);
      board.setTile(3, 3, 0);

      assertTrue(! board.isValid());
    }
    catch (BoardException e) {
      fail("BoardException not expected.");
    } 
  }

  @Test
  public void simpleIsNotValidTest2() {
    Board board;
    try {
      board = new Board(3);      
      board.setTile(1, 1, 8);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 0);
      board.setTile(2, 1, 7);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 4);
      board.setTile(3, 1, 3);
      board.setTile(3, 2, 1);
      board.setTile(3, 3, 6);

      assertTrue(board.isValid());
    }
    catch (BoardException e) {
      fail("BoardException not expected.");
    } 
  }

}
