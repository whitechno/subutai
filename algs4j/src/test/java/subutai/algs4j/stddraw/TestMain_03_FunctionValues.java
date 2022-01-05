package subutai.algs4j.stddraw;

import edu.princeton.cs.algs4.StdDraw;

public class TestMain_03_FunctionValues {
    public static void main(String[] args) {
        StdDraw.setCanvasSize(800, 800);

        int N = 100;
        StdDraw.setXscale(-5, N + 5);
        StdDraw.setYscale(-5 * N, N * N + 5 * N);
        StdDraw.setPenRadius(.01);
        for (int i = 1; i <= N; i++) {
            StdDraw.setPenColor(StdDraw.BOOK_BLUE);
            StdDraw.point(i, i);
            StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
            StdDraw.point(i, i * i);
            StdDraw.setPenColor(StdDraw.DARK_GRAY);
            StdDraw.point(i, i * Math.log(i));
        }
    }
}
