import javalib.funworld.WorldScene;

class MtLoGamePiece implements ILoGamePiece {
  public WorldScene placeAll(WorldScene scene) {
    return scene;
  }

  public ILoGamePiece moveAll() {
    return this;
  }

  public ILoGamePiece removeOffScreen(int width, int height) {
    return this;
  }

  public ILoGamePiece collidesWithAnyOtherGamePiece(ILoGamePiece logp) {
    return this;
  }

  public boolean collidesWithOtherGamePiece(IGamePiece gp) {
    return false;
  }

  public ILoGamePiece removeGamePiece(ILoGamePiece logp) {
    return this;
  }

  public boolean contains(IGamePiece gp) {
    return false;
  }

  public ILoGamePiece nextGen(ILoGamePiece logp, int screenHeight) {
    return logp;
  }

  public int length(int acc) {
    return acc;
  }
}
