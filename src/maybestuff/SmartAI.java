public class SmartAI {
  public static void main(String[] args) {
    for (int[] i : caclulateProbabilities()) {
      for (int n : i) {
        System.out.printf("%5d", n);
      }
      System.out.println("\n");
    }
  }

  public static int[][] caclulateProbabilities() {
    int[][] probs = new int[10][10];
    for (int i = 0; i < 10; i ++) {
      for (int j = 0; j < 10; j++) {
        probs[i][j] = 0;
      }
    }
    for (int i = 0; i < 10; i ++) {
      for (int j = 0; j < 5; j++) {
        probs[i][j] += j + 1;
        probs[i][9 - j] += j + 1;
      }
    }
    for (int i = 0; i < 10; i ++) {
      for (int j = 0; j < 5; j++) {
        probs[j][i] += j + 1;
        probs[9 - j][i] += j + 1;
      }
    }
    return probs;
  }
}
