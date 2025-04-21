class Utils {
  Bullet createBullet(MyPosn p, int gen, int i, int height) {
    MyPosn v = new MyPosn(0, -8);
    if (gen != 0) {
      double theta = i * (2.0 * Math.PI / (gen + 1.0));
      int x = (int) Math.round(8 * Math.cos(theta));
      int y = (int) Math.round(8 * Math.sin(theta));

      v = new MyPosn(x, y);
    }
    return new Bullet(p, v, gen, 0, height);
  }
  
  Bullet createBullet(Bullet oldBullet, int i, int height) {
    return this.createBullet(oldBullet.p, oldBullet.gen+1, i, height);
  }
}