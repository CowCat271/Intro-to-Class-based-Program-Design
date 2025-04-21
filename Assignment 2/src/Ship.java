import java.awt.Color;

import javalib.worldimages.CircleImage;
import javalib.worldimages.WorldImage;

class Ship extends AGamePiece {

  Ship(MyPosn p, MyPosn v, int radius) {
    this.p = p;
    this.v = v;
    this.radius = radius;
  }

  Ship(MyPosn p, MyPosn v, int radius, int screenHeight) {
    if (this.radius != 0) {
      throw new IllegalArgumentException(
          "radius of ship has to be zero when provided screen height.");
    }

    this.p = p;
    this.v = v;
    this.radius = screenHeight / 30;
  }

  WorldImage draw() {
    return new CircleImage(this.radius, "solid", Color.CYAN);
  }

  Ship move() {
    return new Ship(this.p.add(this.v), this.v, this.radius);
  }
}