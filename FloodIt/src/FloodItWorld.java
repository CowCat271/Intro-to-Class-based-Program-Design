import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.AlignModeX;
import javalib.worldimages.AlignModeY;
import javalib.worldimages.OverlayOffsetAlign;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;

class FloodItWorld extends World {
  int height;
  int width;

  int n;
  int cellSize;

  double tickRate;
  int freq;
  int currentTick;

  int score;
  int targetScore;

  boolean begin;
  boolean finish;

  Generator generator;
  ArrayList<Cell> board;
  Queue<Cell> worklist;

  public FloodItWorld(int height, int width, int n, int cellSize, int colors, double tickRate) {
    this.height = height;
    this.width = width;

    this.n = n;
    this.cellSize = cellSize;

    this.tickRate = tickRate;
    this.freq = (int) Math.round(1 / tickRate);

    this.targetScore = n + colors;

    this.generator = new Generator(colors);

    this.initState();
  }

  void initState() {
    this.begin = false;
    this.finish = false;
    this.currentTick = 0;
    this.score = 0;

    this.worklist = new ArrayDeque<Cell>();
    this.board = this.generator.generateBoard(this.n, this.cellSize);

    this.worklist.add(this.board.get(0));
  }

  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(this.width, this.height);

    this.placeBoard(scene);

    this.placeText(scene);

    return scene;
  }

  void placeBoard(WorldScene scene) {
    for (int i = 0; i < this.board.size(); ++i) {
      this.board.get(i).place(scene, this.cellSize);
    }

  }

  void placeText(WorldScene scene) {

    if (!this.begin) {
      this.placeBottomText(scene, "press any cell to start");
    }
    else if (this.finish && this.score <= this.targetScore) {
      this.placeBottomText(scene, "You won");
    }
    else if (this.score > this.targetScore) {
      this.placeBottomText(scene, "You fail");
    }

    String timeString = "Timer:  " + this.getTimeFormated();

    String scoreString = "Score: " + Integer.toString(this.score) + "/"
        + Integer.toString(this.targetScore) + " ";

    this.placeTopText(scene, timeString, scoreString);
  }

  void placeTopText(WorldScene scene, String strLeft, String strRight) {
    WorldImage background = new RectangleImage(this.width, this.cellSize, "solid", Color.WHITE);

    WorldImage leftText = new TextImage(strLeft, this.cellSize / 2 + this.cellSize / 5,
        Color.BLACK);

    WorldImage rightText = new TextImage(strRight, this.cellSize / 2 + this.cellSize / 5,
        Color.BLACK);

    WorldImage leftImage = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.MIDDLE, leftText, 0,
        0, background);
    WorldImage rightImage = new OverlayOffsetAlign(AlignModeX.RIGHT, AlignModeY.MIDDLE, rightText,
        10, 0, leftImage);

    scene.placeImageXY(rightImage, this.width / 2, this.cellSize / 2);
  }

  void placeBottomText(WorldScene scene, String str) {
    WorldImage text = new TextImage(str, this.cellSize / 2 + this.cellSize / 5, Color.BLACK);

    scene.placeImageXY(text, this.width / 2, this.height - this.cellSize / 2);
  }

  String getTimeFormated() {
    int totalSec = this.currentTick / this.freq;
    int sec = totalSec % 60;
    int min = totalSec / 60;

    String time = Integer.toString(sec);

    if (min != 0) {
      time = Integer.toString(min) + " : " + time;
    }

    return time;
  }

  @Override
  public void onTick() {

    if (this.begin && !this.finish)
      ++this.currentTick;

    if (!this.finish) {
      this.finish = true;
      for (int i = 1; i < this.board.size(); ++i) {
        if (this.board.get(i).color != this.board.get(i - 1).color) {
          this.finish = false;
        }
      }
    }

    if (!this.worklist.isEmpty()) {
      Cell mainCell = this.board.get(0);

      Queue<Cell> newWorkList = new ArrayDeque<Cell>();

      while (!this.worklist.isEmpty()) {
        Cell c = this.worklist.remove();

        if (c.top != null) {
          if (this.floodCell(c.top, mainCell.color)) {
            newWorkList.add(c.top);
          }
        }

        if (c.left != null) {
          if (this.floodCell(c.left, mainCell.color)) {
            newWorkList.add(c.left);
          }
        }

        if (c.right != null) {
          if (this.floodCell(c.right, mainCell.color)) {
            newWorkList.add(c.right);
          }
        }

        if (c.bottom != null) {
          if (this.floodCell(c.bottom, mainCell.color)) {
            newWorkList.add(c.bottom);
          }
        }

      }

      this.worklist.addAll(newWorkList);
    }
  }

  @Override
  public void onKeyEvent(String key) {
    if (key.equals("r")) { // we should generate a new board and reset time;
      this.initState();
    }
  }

  @Override
  public void onMouseClicked(Posn mouse, String buttonName) {
    if (buttonName.equals("LeftButton") && this.worklist.isEmpty()) {

      // get coords of the cell
      int col = mouse.x / this.cellSize;
      int row = mouse.y / this.cellSize - 1;
      Cell c = this.board.get(row * this.n + col);

      Cell mainCell = this.board.get(0);

      if (c.color != mainCell.color) {
        ++this.score;
        if (!this.begin)
          this.begin = true;

        mainCell.color = c.color;

        this.worklist.add(mainCell);
      }
    }
  }

  boolean floodCell(Cell c, Color newColor) {
    if (c.flooded) {
      if (c.color == newColor) {
        return false;
      }
      else {
        c.color = newColor;
        return true;
      }
    }
    else {
      if (c.color == newColor) {
        c.flooded = true;
        return true;
      }
      else {
        return false;
      }
    }
  }
}
