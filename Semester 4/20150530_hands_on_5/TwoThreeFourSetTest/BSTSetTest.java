package swe4.tests;

import static org.junit.Assert.*;
import java.util.Comparator;
import java.util.Iterator;
import org.junit.Ignore;
import org.junit.Test;
import swe4.collections.BSTSet;
import swe4.collections.SortedSet;

public class BSTSetTest extends SortedTreeSetTestBase {
  
  @Override
  protected <T> BSTSet<T> createSet(Comparator<T> comparator) {  
    return new BSTSet<T>(comparator);
  }

}