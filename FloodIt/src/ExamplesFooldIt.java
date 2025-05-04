import java.util.ArrayList;
import java.util.Arrays;

import tester.Tester;

class ExamplesFooldIt {
  void testGame(Tester t) {

    ArrayList<Integer> sizes = new ArrayList<Integer>(Arrays.asList(2, 6, 10, 14, 18, 22, 26));
    int selectedSize = 5;

    int cellSize = 30;
    int availableColors = 5;

    int width = sizes.get(selectedSize) * cellSize;
    int height = (sizes.get(selectedSize) + 2) * cellSize;
    double tick = 1.0 / 30.0;

    FloodItWorld game = new FloodItWorld(height, width, sizes.get(selectedSize), cellSize, availableColors, tick);

    game.bigBang(width, height, tick);
  }
}