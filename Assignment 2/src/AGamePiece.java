import javalib.funworld.WorldScene;
import javalib.worldimages.WorldImage;

abstract class AGamePiece {
  MyPosn p;
  MyPosn v;
  int radius;
  
  abstract WorldImage draw();
  
  boolean same(AGamePiece other) {
    return this.p.same(other.p) && this.v.same(other.v);
  }

  boolean collidesWith(AGamePiece other) {
    double dx = this.p.x - other.p.x;
    double dy = this.p.y - other.p.y;
    
    return Math.hypot(dx, dy) <= (this.radius + other.radius) ;
  }
  
  WorldScene place(WorldScene scene) {
    return scene.placeImageXY(this.draw(), this.p.x, this.p.y);
  }
  
  boolean isOffScreen(int screenWidth, int screenHeight) {
    return this.p.isOffScreen(screenWidth, screenHeight);
  }

}