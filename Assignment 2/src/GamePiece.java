import javalib.funworld.WorldScene;
import javalib.worldimages.WorldImage;

interface IGamePiece {
  int getX();

  int getY();

  int getGen();
  
  MyPosn getPosn();

  MyPosn getV();

  int getRadius();

  boolean same(IGamePiece other);

  boolean isOffScreen(int screenWidth, int screenHeight);

  boolean collidesWith(IGamePiece other);

  WorldScene place(WorldScene scene);

  WorldImage draw();

  IGamePiece move();
}

abstract class AGamePiece implements IGamePiece {
  MyPosn p;
  MyPosn v;
  int radius;

  public int getX() {
    return this.p.x;
  }

  public int getY() {
    return this.p.y;
  }

  public MyPosn getPosn() {
    return this.p;
  }

  public MyPosn getV() {
    return this.v;
  }

  public int getRadius() {
    return this.radius;
  }

  public boolean same(IGamePiece other) {
    return this.p.same(other.getPosn()) && this.v.same(other.getV());
  }

  public boolean isOffScreen(int screenWidth, int screenHeight) {
    return this.p.isOffScreen(screenWidth, screenHeight);
  }

  public boolean collidesWith(IGamePiece other) {
    double dx = this.p.x - other.getX();
    double dy = this.p.y - other.getY();

    return Math.hypot(dx, dy) <= (this.radius + other.getRadius());
  }

  public WorldScene place(WorldScene scene) {
    return scene.placeImageXY(this.draw(), this.p.x, this.p.y);
  }

  public abstract WorldImage draw();

  public abstract IGamePiece move();

}


