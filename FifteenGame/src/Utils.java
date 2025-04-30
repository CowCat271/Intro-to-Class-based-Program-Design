import java.util.ArrayList;

import java.util.function.UnaryOperator;

class Utils {

  ArrayList<ArrayList<Integer>> initSolution() {
    ArrayList<ArrayList<Integer>> solution = new ArrayList<ArrayList<Integer>>();
    for (int i = 0; i < 4; ++i) {
      ArrayList<Integer> row = new ArrayList<Integer>();
      for (int j = 1; j <= 4; ++j) {
        row.add(i * 4 + j);
      }
      if (i == 3) {
        row.set(3, 0);
      }
      solution.add(row);
    }

    return solution;
  }

  <T, R> void update2DArray(ArrayList<ArrayList<T>> arr, UnaryOperator<T> func) {
    for (int i = 0; i < arr.size(); ++i) {
      for (int j = 0; j < arr.get(i).size(); ++j) {
        arr.get(i).set(j, func.apply(arr.get(i).get(j)));
      }
    }
  }
}