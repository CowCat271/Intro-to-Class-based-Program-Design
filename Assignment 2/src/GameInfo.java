import java.util.Random;

class GameInfo {
  int WIDTH;
  int HEIGHT;

  boolean begin;

  double tick;
  long freq;
  int currentTick;

  int bullets;
  int score;

  ILoGamePiece los;
  ILoGamePiece lob;

  boolean debug;
  int randomShips;
  Random rand;

  GameInfo(int width, int height, double tick, int bullets) {
    this(width, height, false, tick, 0, bullets, 0, new MtLoGamePiece(), new MtLoGamePiece(), false, 3,
        new Random());
  }

  GameInfo(int width, int height, boolean begin, double tick, int currentTick, int bullets,
      int score, ILoGamePiece los, ILoGamePiece lob, boolean debug, int randomShips, Random rand) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("dimentions of the game should be positive");
    }

    this.WIDTH = width;
    this.HEIGHT = height;

    this.begin = begin;

    this.tick = tick;
    this.freq = Math.round(1.0 / tick);
    this.currentTick = currentTick;

    this.bullets = bullets;
    this.score = score;

    this.los = los;
    this.lob = lob;

    this.debug = debug;

    this.randomShips = randomShips;
    this.rand = rand;
  }

  GameInfo setBegin(boolean begin) {
    this.begin = begin;

    return this;
  }

  Ship randomShip() {
    int dir = this.rand.nextInt(2); // 0 => left | 1 => right
    return new Ship(
        new MyPosn(dir * this.WIDTH, this.rand.nextInt(5 * this.HEIGHT / 7) + this.HEIGHT / 7),
        new MyPosn(dir == 0 ? 4 : -4, 0), 0, this.HEIGHT);
  }

  GameInfo addShip(Ship s) {
    this.los = new ConsLoGamePiece(s, this.los);

    return this;
  }

  GameInfo moveShips() {
    this.los = this.los.moveAll().removeOffScreen(this.WIDTH, this.HEIGHT);

    return this;
  }

  ILoGamePiece getCollidedShips() {
    return this.los.collidesWithAnyOtherGamePiece(this.lob);
  }

  Bullet createBullet(MyPosn p, int gen, int i) {
    return (new Utils()).createBullet(p, gen, i, this.HEIGHT);
  }

  GameInfo addBullet(Bullet b) {
    this.lob = new ConsLoGamePiece(b, this.lob);

    return this;
  }

  GameInfo moveBullets() {
    this.lob = this.lob.moveAll().removeOffScreen(this.WIDTH, this.HEIGHT);

    return this;
  }

  ILoGamePiece getCollidedBullets() {
    return this.lob.collidesWithAnyOtherGamePiece(this.los);
  }

  ILoGamePiece spawnBullets(ILoGamePiece collidedBullets) {
    return collidedBullets.nextGen(this.lob, this.HEIGHT);
  }

  GameInfo setDebug(boolean debug) {
    this.debug = debug;

    return this;
  }

  GameInfo setRandomShips(int randomShips) {
    this.randomShips = randomShips;
    
    return this;
  }
  GameInfo setRand(Random rand) {
    this.rand = rand;

    return this;
  }
}