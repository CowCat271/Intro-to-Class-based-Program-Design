import tester.Tester;

class Examples {
  void testGame(Tester t) {
    int width = 500;
    int height = 500;
    double tick = 1.0 / 30.0;

    FifteenGame g = new FifteenGame(width, height, false);

    g.bigBang(width, height, tick);
  }
}