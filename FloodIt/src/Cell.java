import java.awt.Color;

import javalib.impworld.WorldScene;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

class Cell {
  int x;
  int y;
  Color color;
  boolean flooded;

  Cell left;
  Cell top;
  Cell right;
  Cell bottom;

  public Cell(int x, int y, Color color, boolean flooded, Cell left, Cell top) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = flooded;

    this.left = left;
    this.top = top;
    
    this.right = null;
    this.bottom = null;
    
    if (this.left != null) this.left.right = this;
    if (this.top != null) this.top.bottom = this;
  }
  
  void place(WorldScene scene, int cellSize) {
    scene.placeImageXY(this.draw(cellSize), this.x, this.y);
  }
  
  WorldImage draw(int cellSize) {
    return new RectangleImage(cellSize, cellSize, "solid", this.color);
  }
}