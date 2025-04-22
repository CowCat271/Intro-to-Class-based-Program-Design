import javalib.funworld.WorldScene;

interface ILoGamePiece {
  WorldScene placeAll(WorldScene scene);

  ILoGamePiece moveAll();

  ILoGamePiece removeOffScreen(int width, int height);

  ILoGamePiece collidesWithAnyOtherGamePiece(ILoGamePiece logp);

  boolean collidesWithOtherGamePiece(IGamePiece gp);

  ILoGamePiece removeGamePiece(ILoGamePiece logp);

  boolean contains(IGamePiece gp);
  
  ILoGamePiece nextGen(ILoGamePiece logp, int screenHeight);

  int length(int acc);
}
