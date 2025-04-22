import javalib.funworld.WorldScene;

class ConsLoGamePiece implements ILoGamePiece {
  IGamePiece first;
  ILoGamePiece rest;

  ConsLoGamePiece(IGamePiece first, ILoGamePiece rest) {
    this.first = first;
    this.rest = rest;
  }

  public WorldScene placeAll(WorldScene scene) {
    return this.rest.placeAll(this.first.place(scene));
  }

  public ILoGamePiece moveAll() {
    return new ConsLoGamePiece(this.first.move(), this.rest.moveAll());
  }

  public ILoGamePiece removeOffScreen(int width, int height) {
    if (this.first.isOffScreen(width, height)) {
      return this.rest.removeOffScreen(width, height);
    }
    else {
      return new ConsLoGamePiece(this.first, this.rest.removeOffScreen(width, height));
    }
  }

  public ILoGamePiece collidesWithAnyOtherGamePiece(ILoGamePiece logp) {
    if (logp.collidesWithOtherGamePiece(this.first)) {
      return new ConsLoGamePiece(this.first, this.rest.collidesWithAnyOtherGamePiece(logp));
    }
    else {
      return this.rest.collidesWithAnyOtherGamePiece(logp);
    }
  }

  public boolean collidesWithOtherGamePiece(IGamePiece gp) {
    return this.first.collidesWith(gp) || this.rest.collidesWithOtherGamePiece(gp);
  }

  public ILoGamePiece removeGamePiece(ILoGamePiece logp) {
    if (logp.contains(this.first)) {
      return this.rest.removeGamePiece(logp);
    }
    else {
      return new ConsLoGamePiece(this.first, this.rest.removeGamePiece(logp));
    }
  }

  public boolean contains(IGamePiece gp) {
    return this.first.same(gp) || this.rest.contains(gp);
  }

  public ILoGamePiece nextGen(ILoGamePiece logp, int screenHeight) {
    ILoGamePiece newBullets = this.rest.nextGen(logp, screenHeight);

    for (int i = 0; i <= this.first.getGen() + 1; ++i) {
      newBullets = new ConsLoGamePiece((new Utils()).createBullet(this.first, i, screenHeight),
          newBullets);
    }

    return newBullets;
  }

  public int length(int acc) {
    return this.rest.length(acc + 1);
  }
}