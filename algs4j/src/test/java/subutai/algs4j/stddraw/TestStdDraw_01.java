package subutai.algs4j.stddraw;

import edu.princeton.cs.algs4.StdDraw;

public class TestStdDraw_01 {
    public static void main(String[] args) {
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.point(0, 0);
        StdDraw.point(0.5, 0.5);
        StdDraw.point(1, 1);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.line(0.2, 0.2, 0.8, 0.2);
    }
}
