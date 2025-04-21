import javalib.worldimages.Posn;

class MyPosn extends Posn {
  MyPosn(int x, int y) {
    super(x, y);
  }

  MyPosn(Posn p) {
    super(p.x, p.y);
  }

  MyPosn add(MyPosn that) {
    return new MyPosn(this.x + that.x, this.y + that.y);
  }
  
  boolean same(MyPosn other) {
    return this.x == other.x && this.y == other.y;
  }

  boolean isOffScreen(int screenWidth, int screenHeight) {
    /*
     * 0 <= x <= width and 0 <= y <= height
     */
    boolean insideScreen = (0 <= this.x && this.x <= screenWidth)
        && (0 <= this.y && this.y <= screenHeight);
    return !insideScreen;
  }
}