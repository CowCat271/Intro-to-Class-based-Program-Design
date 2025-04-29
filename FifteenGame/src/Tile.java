import java.awt.Color;

import javalib.worldimages.OverlayImage;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;

class Tile {
  int value;
  int sideLen;
  Color color;

  Tile(int value) {
    this(value, 0);
  }

  Tile(int value, int sideLen) {
    this.value = value;
    this.sideLen = sideLen;
    this.color = Color.CYAN;
  }

  int setZero() {
    int oldValue = this.value;
    this.value = 0;
    
    return oldValue;
  }
  
  void setValue(int newValue) {
    this.value = newValue;
  }

  void setSideLen(int newSideLen) {
    this.sideLen = newSideLen;
  }

  WorldImage draw() {
    if (this.value == 0) {
      return new RectangleImage(0, 0, "outline", Color.WHITE);
    }

    WorldImage num = new TextImage(Integer.toString(this.value), sideLen / 2, Color.BLACK);

    WorldImage rect = new RectangleImage(sideLen, sideLen, "solid", this.color);

    return new OverlayImage(num, rect);
  }
}