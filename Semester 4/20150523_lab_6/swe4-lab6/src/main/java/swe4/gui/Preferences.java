package swe4.gui;

public final class Preferences {
   private static final Preferences instance = new Preferences ();

   private int segmentLength = 10;

   public static Preferences getInstance () {
      return instance;
   }

   private Preferences () {   // Singleton
   }

   public int getSegmentLength () {
      return segmentLength;
   }

   public void setSegmentLength (int segmentLength) {
      this.segmentLength = segmentLength;
   }

}
