import javalib.funworld.WorldScene;

interface ILoShip {
  WorldScene placeAll(WorldScene scene);

  ILoShip moveAll();

  ILoShip removeOffScreen(int width, int height);

  ILoShip collidesWithAnyBullet(ILoBullet lob);

  boolean collidesWithBullet(Bullet b);
  
  ILoShip removeShips(ILoShip los);
  
  boolean contains(Ship s);
  
  int length(int acc);

}

class MtLoShip implements ILoShip {
  public WorldScene placeAll(WorldScene scene) {
    return scene;
  }

  public ILoShip moveAll() {
    return this;
  }

  public ILoShip removeOffScreen(int width, int height) {
    return this;
  }

  public ILoShip collidesWithAnyBullet(ILoBullet lob) {
    return this;
  }

  public boolean collidesWithBullet(Bullet b) {
    return false;
  }
  
  public ILoShip removeShips(ILoShip los) {
    return this;
  }
  
  public boolean contains(Ship s) {
    return false;
  }
  
  public int length(int acc) {
    return acc;
  }
}

class ConsLoShip implements ILoShip {
  Ship first;
  ILoShip rest;

  ConsLoShip(Ship first, ILoShip rest) {
    this.first = first;
    this.rest = rest;
  }

  public WorldScene placeAll(WorldScene scene) {
    return this.rest.placeAll(this.first.place(scene));
  }

  public ILoShip moveAll() {
    return new ConsLoShip(this.first.move(), this.rest.moveAll());
  }

  public ILoShip removeOffScreen(int width, int height) {
    if (this.first.isOffScreen(width, height)) {
      return this.rest.removeOffScreen(width, height);
    }
    else {
      return new ConsLoShip(this.first, this.rest.removeOffScreen(width, height));
    }
  }

  public ILoShip collidesWithAnyBullet(ILoBullet lob) {
    if (lob.collidesWithShip(this.first)) {
      return new ConsLoShip(this.first, this.rest.collidesWithAnyBullet(lob));
    }
    else {
      return this.rest.collidesWithAnyBullet(lob);
    }
  }

  public boolean collidesWithBullet(Bullet b) {
    return this.first.collidesWith(b) || this.rest.collidesWithBullet(b);
  }
  
  public ILoShip removeShips(ILoShip los) {
    if (los.contains(this.first)) {
      return this.rest.removeShips(los);
    } else {
      return new ConsLoShip(this.first, this.rest.removeShips(los));
    }
  }
  
  public boolean contains(Ship s) {
    return this.first.same(s) || this.rest.contains(s);
  }
  
  public int length(int acc) {
    return this.rest.length(acc + 1);
  }

}