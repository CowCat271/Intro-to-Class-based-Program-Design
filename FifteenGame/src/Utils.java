import java.util.ArrayList;
import java.util.function.Predicate;

class Utils {
  <T> ArrayList<T> filter(ArrayList<T> arr, Predicate<T> pred) {
    ArrayList<T> result = new ArrayList<T>();

    for (T item : arr) {
      if (pred.test(item)) {
        result.add(item);
      }
    }

    return result;
  }

  // remove everything except what pass the predicate
  <T> void removeExcept(ArrayList<T> arr, Predicate<T> pred) {
    int i = 0;

    while (i < arr.size()) {
      if (pred.test(arr.get(i))) {
        ++i;
      }
      else {
        arr.remove(i);
      }
    }
  }
}