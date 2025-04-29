import java.awt.Color;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

class FifteenGame extends World {

  int WIDTH;
  int HEIGHT;

  boolean solved;
  boolean debug;
  Utils utils;

  ArrayList<ArrayList<Tile>> grid;
  ArrayList<ArrayList<Integer>> solution;
  ArrayList<String> history;

  FifteenGame(int width, int height, boolean debug) {
    this(width, height, false, debug, new PuzzleGenerator2D().generateSolvablePuzzle2D(debug));
  }

  FifteenGame(int width, int height, boolean solved, boolean debug,
      ArrayList<ArrayList<Tile>> grid) {
    if (width != height) {
      throw new IllegalArgumentException("The width and height should be the same.");
    }

    this.WIDTH = width;
    this.HEIGHT = height;

    this.solved = solved;
    this.debug = debug;
    this.utils = new Utils();

    this.grid = grid;
    this.solution = utils.initSolution();
    this.history = new ArrayList<String>();

    this.utils.update2DArray(this.grid, new AddSideLenTile());
  }

  class AddSideLenTile implements UnaryOperator<Tile> {
    public Tile apply(Tile t) {
      return new Tile(t.value, HEIGHT / 5);
    }
  }

  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(this.WIDTH, this.HEIGHT);

    scene = this.drawGrid(scene);
    scene = this.placeTiles(scene);

    return scene;
  }

  WorldScene drawGrid(WorldScene scene) {
    int tileSide = this.HEIGHT / 4;

    WorldImage rect = new RectangleImage(tileSide, tileSide, "outline", Color.BLACK);

    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        scene.placeImageXY(rect, j * tileSide + tileSide / 2, i * tileSide + tileSide / 2);
      }
    }

    return scene;
  }

  WorldScene placeTiles(WorldScene scene) {
    int tileSide = this.HEIGHT / 4;

    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        scene.placeImageXY(this.grid.get(i).get(j).draw(), j * tileSide + tileSide / 2,
            i * tileSide + tileSide / 2);
      }
    }

    return scene;
  }

  public void onKeyEvent(String key) {
    this.takeMove(key, true);
  }

  void takeMove(String key, boolean save) {
    if (key.equals("u")) {
      if (this.history.isEmpty())
        return;

      this.takeMove(this.history.remove(this.history.size() - 1), false);
      return;
    }

    if (key.equals("up")) {
      if (save)
        this.history.add("down");
      this.moveDown();
    }
    else if (key.equals("down")) {
      if (save)
        this.history.add("up");
      this.moveUp();
    }
    else if (key.equals("right")) {
      if (save)
        this.history.add("left");
      this.moveLeft();
    }
    else if (key.equals("left")) {
      if (save)
        this.history.add("right");
      this.moveRight();
    }

    if (save && this.isSolved()) {
      for (int i = 0; i < 4; ++i) {
        for (int j = 0; j < 4; ++j) {
          this.grid.get(i).get(j).color = Color.GREEN;
        }
      }

      this.endOfWorld("Puzzil Solved");
    }
  }

  void moveDown() {
    this.move(1, 0);
  }

  void moveUp() {
    this.move(-1, 0);
  }

  void moveRight() {
    this.move(0, 1);
  }

  void moveLeft() {
    this.move(0, -1);
  }

  void move(int x, int y) {
    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        if (this.grid.get(i).get(j).value == 0) {
          this.swap(i, j, i + x, j + y);

          return;
        }
      }
    }
  }

  // where (i1, j1) is the zero tile position
  void swap(int i1, int j1, int i2, int j2) {
    if (this.checkPos(i1) && this.checkPos(j1) && this.checkPos(i2) && this.checkPos(j2)) {
      this.grid.get(i1).get(j1).setValue(this.grid.get(i2).get(j2).setZero());
    }
  }

  boolean checkPos(int x) {
    return 0 <= x && x <= 3;
  }

  boolean isSolved() {
    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        if (this.grid.get(i).get(j).value != this.solution.get(i).get(j)) {
          return false;
        }
      }
    }

    return true;
  }
}