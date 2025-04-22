class Utils {
  Bullet createBullet(MyPosn p, int newGen, int i, int height) {
    MyPosn v = new MyPosn(0, -8);
    if (newGen != 0) {
      double theta = i * (2.0 * Math.PI / (newGen + 1.0));
      int x = (int) Math.round(8 * Math.cos(theta));
      int y = (int) Math.round(8 * Math.sin(theta));

      v = new MyPosn(x, y);
    }
    return new Bullet(p, v, newGen, 0, height);
  }

  Bullet createBullet(IGamePiece oldBullet, int i, int height) {
    return this.createBullet(oldBullet.getPosn(), oldBullet.getGen() + 1, i, height);
  }
}