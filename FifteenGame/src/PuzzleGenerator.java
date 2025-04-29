import java.util.ArrayList;
import java.util.Random;

class PuzzleGenerator2D {
  ArrayList<ArrayList<Tile>> generateSolvablePuzzle2D(boolean debug) {
    // Generate shuffled 1D array (0-15)
    int[] flatTiles = new int[16];
    for (int i = 0; i < 16; i++)
      flatTiles[i] = i + 1;

    flatTiles[14] = 0;
    flatTiles[15] = 15;

    if (debug) {
      return convertTo2D(flatTiles);
    }

    shuffle(flatTiles);

    // Ensure solvability
    if (!isSolvable(flatTiles))
      adjustForSolvability(flatTiles);

    // Convert to 2D ArrayList
    return convertTo2D(flatTiles);
  }

  void shuffle(int[] tiles) {
    Random rand = new Random();
    for (int i = tiles.length - 1; i > 0; i--) {
      int j = rand.nextInt(i + 1);
      int temp = tiles[i];
      tiles[i] = tiles[j];
      tiles[j] = temp;
    }
  }

  boolean isSolvable(int[] tiles) {
    int inversions = 0;
    int blankRowFromBottom = 0;

    // Find blank tile position (0-based index)
    for (int i = 0; i < tiles.length; i++) {
      if (tiles[i] == 0) {
        blankRowFromBottom = 3 - (i / 4); // Calculate row from bottom (0-3)
        break;
      }
    }

    // Count inversions
    for (int i = 0; i < tiles.length; i++) {
      if (tiles[i] == 0)
        continue;
      for (int j = i + 1; j < tiles.length; j++) {
        if (tiles[j] != 0 && tiles[i] > tiles[j])
          inversions++;
      }
    }

    return (inversions + blankRowFromBottom) % 2 == 0;
  }

  void adjustForSolvability(int[] tiles) {
    // Find first two non-blank tiles to swap
    int first = -1, second = -1;
    for (int i = 0; i < tiles.length; i++) {
      if (tiles[i] != 0) {
        if (first == -1)
          first = i;
        else {
          second = i;
          break;
        }
      }
    }
    // Swap to flip parity
    int temp = tiles[first];
    tiles[first] = tiles[second];
    tiles[second] = temp;
  }

  ArrayList<ArrayList<Tile>> convertTo2D(int[] flatArray) {
    ArrayList<ArrayList<Tile>> puzzle2D = new ArrayList<ArrayList<Tile>>();

    for (int row = 0; row < 4; row++) {
      ArrayList<Tile> currentRow = new ArrayList<>();

      for (int col = 0; col < 4; col++) {
        Tile t = new Tile(flatArray[row * 4 + col]);
        currentRow.add(t);
      }
      puzzle2D.add(currentRow);
    }
    return puzzle2D;
  }

}
