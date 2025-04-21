import javalib.funworld.WorldScene;

interface ILoBullet {
  WorldScene placeAll(WorldScene scene);

  ILoBullet moveAll();

  ILoBullet removeOffScreen(int width, int height);

  ILoBullet collidesWithAnyShip(ILoShip los);

  boolean collidesWithShip(Ship s);

  ILoBullet removeBullets(ILoBullet lob);

  boolean contains(Bullet b);

  ILoBullet nextGen(ILoBullet lob, int screenHeight);

}

class MtLoBullet implements ILoBullet {
  public WorldScene placeAll(WorldScene scene) {
    return scene;
  }

  public ILoBullet moveAll() {
    return this;
  }

  public ILoBullet removeOffScreen(int width, int height) {
    return this;
  }

  public ILoBullet collidesWithAnyShip(ILoShip los) {
    return this;
  }

  public boolean collidesWithShip(Ship s) {
    return false;
  }

  public ILoBullet removeBullets(ILoBullet lob) {
    return this;
  }

  public boolean contains(Bullet b) {
    return false;
  }

  public ILoBullet nextGen(ILoBullet lob, int screenHeight) {
    return lob;
  }
}

class ConsLoBullet implements ILoBullet {
  Bullet first;
  ILoBullet rest;

  ConsLoBullet(Bullet first, ILoBullet rest) {
    this.first = first;
    this.rest = rest;
  }

  public WorldScene placeAll(WorldScene scene) {
    return this.rest.placeAll(this.first.place(scene));
  }

  public ILoBullet moveAll() {
    return new ConsLoBullet(this.first.move(), this.rest.moveAll());
  }

  public ILoBullet removeOffScreen(int width, int height) {
    if (this.first.isOffScreen(width, height)) {
      return this.rest.removeOffScreen(width, height);
    }
    else {
      return new ConsLoBullet(this.first, this.rest.removeOffScreen(width, height));
    }
  }

  public ILoBullet collidesWithAnyShip(ILoShip los) {
    if (los.collidesWithBullet(this.first)) {
      return new ConsLoBullet(this.first, this.rest.collidesWithAnyShip(los));
    }
    else {
      return this.rest.collidesWithAnyShip(los);
    }
  }

  public boolean collidesWithShip(Ship s) {
    return this.first.collidesWith(s) || this.rest.collidesWithShip(s);
  }

  public ILoBullet removeBullets(ILoBullet lob) {
    if (lob.contains(this.first)) {
      return this.rest.removeBullets(lob);
    }
    else {
      return new ConsLoBullet(this.first, this.rest.removeBullets(lob));
    }
  }

  public boolean contains(Bullet b) {
    return this.first.same(b) || this.rest.contains(b);
  }

  public ILoBullet nextGen(ILoBullet lob, int screenHeight) {
    ILoBullet newBullets = this.rest.nextGen(lob, screenHeight);

    for (int i = 0; i <= this.first.gen + 1; ++i) {
      newBullets = new ConsLoBullet((new Utils()).createBullet(this.first, i, screenHeight),
          newBullets);
    }

    return newBullets;
  }
}
