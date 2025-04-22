import java.awt.Color;

import javalib.worldimages.CircleImage;
import javalib.worldimages.WorldImage;

class Bullet extends AGamePiece {
  int gen;

  Bullet(MyPosn p, MyPosn v, int gen, int radius) {
    this.p = p;
    this.v = v;
    this.gen = Math.min(gen, 10);
    this.radius = radius;
  }

  Bullet(MyPosn p, MyPosn v, int gen, int radius, int screenHeight) {
    if (this.radius != 0) {
      throw new IllegalArgumentException(
          "radius of bullet has to be zero when provided screen height.");
    }

    this.p = p;
    this.v = v;
    this.gen = Math.min(gen, 10);

    /*
     * H / 30 => ship, H / 30*5 => starting size, H / 30*30 => step size, H / 30*3
     * => max size
     */
    this.radius = (gen < 4) ? ((gen * screenHeight / 900) + screenHeight / 150) : screenHeight / 90;
  }

  public int getGen() {
    return this.gen;
  }
  

  @Override
  public WorldImage draw() {
    return new CircleImage(this.radius, "solid", Color.PINK);
  }

  @Override
  public IGamePiece move() {
    return new Bullet(this.p.add(this.v), this.v, this.gen, this.radius);
  }

}