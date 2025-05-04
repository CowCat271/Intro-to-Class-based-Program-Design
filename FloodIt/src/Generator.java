import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class Generator {

  int selectedColors;
  ArrayList<Color> colors;
  Random rand;

  public Generator(int selectedColors) {
    this.selectedColors = selectedColors;

    this.rand = new Random();
    this.colors = new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW,
        Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA, Color.PINK));

    if (selectedColors < 3 || selectedColors > this.colors.size()) {
      throw new IllegalArgumentException(
          "the selected color set should be between 3 and " + this.colors.size());
    }
  }

  ArrayList<Cell> generateBoard(int n, int cellSize) {
    ArrayList<Cell> board = new ArrayList<Cell>();

    int biasX = cellSize / 2;
    int biasY = cellSize + cellSize / 2;

    for (int i = 0; i < n * n; ++i) {
      int row = i / n;
      int col = i % n;

      Cell top = null;
      if (row > 0) {
        top = board.get(i - n);
      }

      Cell left = null;
      if (col > 0) {
        left = board.get(i - 1);
      }

      Cell cell = new Cell(biasX + col * cellSize, biasY + row * cellSize,
          this.colors.get(this.rand.nextInt(this.selectedColors)), false, left, top);

      board.add(cell);
    }

    return board;
  }
}
