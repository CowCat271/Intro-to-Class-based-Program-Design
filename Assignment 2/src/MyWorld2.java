import tester.Tester;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;

class MyGame extends World {
  GameInfo gameInfo;

  MyGame(GameInfo gameInfo) {
    this.gameInfo = gameInfo;
  }

  @Override
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(this.gameInfo.WIDTH, this.gameInfo.HEIGHT);

    if (this.gameInfo.debug) {
      scene = this.addDebugInfo(scene);
    }

    if (!this.gameInfo.begin) { // we didn't start the game yet
      return this.addStart(scene);
    }

    scene = this.addInfo(scene);
    scene = this.addCanon(scene);

    scene = this.addShips(scene);
    scene = this.addBullets(scene);

    return scene;
  }

  WorldScene addStart(WorldScene scene) {
    WorldImage startText = new TextImage("Press space to start", scene.height / 35, Color.BLACK);
    return scene.placeImageXY(startText, scene.width / 2, scene.height / 2);
  }

  WorldScene addInfo(WorldScene scene) {
    String infoString = "Bullets left: " + Integer.toString(this.gameInfo.bullets) + " | Score: "
        + Integer.toString(this.gameInfo.score);

    WorldImage infoText = new TextImage(infoString, scene.height / 35, Color.BLACK);

    return scene.placeImageXY(infoText, (int) infoText.getWidth() / 2 + 2,
        (int) infoText.getHeight() / 2);
  }

  WorldScene addCanon(WorldScene scene) {
    /*
     * sideLen = 3*Height/(14sqrt(3)) using geometry
     */
    WorldImage canon = new EquilateralTriangleImage(scene.height * 0.1237, "solid", Color.BLACK);

    return scene.placeImageXY(canon, scene.width / 2, scene.height);
  }

  WorldScene addDebugInfo(WorldScene scene) {
    WorldImage line = new RectangleImage(scene.width, 5, "solid", Color.RED);

    scene = scene.placeImageXY(line, scene.width / 2, 6 * scene.height / 7);
    scene = scene.placeImageXY(line, scene.width / 2, scene.height / 7);

    String debugString = "Tick: " + Integer.toString(this.gameInfo.currentTick) + " | Time: "
        + Long.toString(this.gameInfo.currentTick / this.gameInfo.freq) + "s";
    WorldImage debugText = new TextImage(debugString, scene.height / 35, Color.RED);
    scene = scene.placeImageXY(debugText, (int) debugText.getWidth() / 2 + 2,
        scene.height - (int) debugText.getHeight() / 2);

    return scene;
  }

  WorldScene addShips(WorldScene scene) {
    return this.gameInfo.los.placeAll(scene);
  }

  WorldScene addBullets(WorldScene scene) {
    return this.gameInfo.lob.placeAll(scene);
  }

  @Override
  public MyGame onTick() {
    if (!this.gameInfo.begin)
      return this;

    ++this.gameInfo.currentTick;
    return this.spawnShips().moveShips().moveBullets().handleCollisions();
  }

  MyGame spawnShips() {
    if (this.gameInfo.currentTick % this.gameInfo.freq == 0) { // ever 1s

      for (int i = 0; i < this.gameInfo.rand.nextInt(3); i++) {
        this.gameInfo = this.gameInfo.addShip(this.gameInfo.randomShip());
      }

      return new MyGame(this.gameInfo);
    }

    return this;
  }

  MyGame moveShips() {
    return new MyGame(this.gameInfo.moveShips());
  }

  MyGame moveBullets() {
    return new MyGame(this.gameInfo.moveBullets());
  }

  MyGame handleCollisions() {
    ILoShip collidedShips = this.gameInfo.getCollidedShips();
    ILoBullet collidedBullets = this.gameInfo.getCollidedBullets();

    this.gameInfo.los = this.gameInfo.los.removeShips(collidedShips);
    this.gameInfo.lob = this.gameInfo.lob.removeBullets(collidedBullets);

    this.gameInfo.score += collidedShips.length(0);
    this.gameInfo.lob = this.gameInfo.spawnBullets(collidedBullets);

    return this;
  }

  public MyGame onKeyEvent(String key) {
    if (!key.equals(" ")) {
      return this;
    }

    if (!this.gameInfo.begin) {
      return new MyGame(this.gameInfo.setBegin(true));
    }

    --this.gameInfo.bullets;
    MyPosn org = new MyPosn(this.gameInfo.WIDTH / 2, 13 * this.gameInfo.HEIGHT / 14);
    return new MyGame(this.gameInfo.addBullet(this.gameInfo.createBullet(org, 0, 0)));
  }

  @Override
  public WorldEnd worldEnds() {
    if (this.gameInfo.bullets == 0) {
      return new WorldEnd(true, this.makeEndScene());
    }
    else {
      return new WorldEnd(false, this.makeEndScene());
    }
  }

  WorldScene makeEndScene() {
    WorldScene endScene = new WorldScene(this.gameInfo.WIDTH, this.gameInfo.HEIGHT);

    WorldImage end = new AboveImage(new TextImage("Game Over", endScene.height / 35, Color.BLACK),
        new TextImage("Your Score was: " + Integer.toString(this.gameInfo.score),
            endScene.height / 35, Color.BLACK));

    return endScene.placeImageXY(end, endScene.width / 2, endScene.height / 2);
  }

}

class ExamplesMyWorldProgram {
  boolean testBigBang(Tester t) {
    int width = 1500;
    int height = 900;
    int bullets = 11;
    double tickRate = 1.0 / 30.0;

//    MyPosn p1 = new MyPosn(100, 100);
////    MyPosn p2 = new MyPosn(150, 100);
////    MyPosn p3 = new MyPosn(100, 150);
//    MyPosn p4 = new MyPosn(200, 200);
//    MyPosn v1 = new MyPosn(0, 1);
//    MyPosn v2 = new MyPosn(1, 0);
//
//    ILoShip los = new ConsLoShip(new Ship(p1, v1), new MtLoShip());
//    ILoBullet lob = new ConsLoBullet(new Bullet(p4, v2, 4), new MtLoBullet());

    GameInfo gi = new GameInfo(width, height, tickRate, bullets);
    gi.setDebug(true);
    MyGame world = new MyGame(gi);
    return world.bigBang(width, height, tickRate);
  }
}