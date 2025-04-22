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

