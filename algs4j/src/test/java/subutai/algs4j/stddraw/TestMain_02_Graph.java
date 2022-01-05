package subutai.algs4j.stddraw;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class TestMain_02_Graph {
    public static void main(String[] args) {
        StdDraw.setCanvasSize(1000, 1000);
        StdDraw.setScale(-.1, 1.1);
        StdDraw.setPenColor(StdDraw.BLUE);

        // x- and y- coords of nodes
        double[] xs = {0, 1, .5, 1, .5, 0};
        double[] ys = {0, 0, .5, .5, 1, .5};
        int n = xs.length;
        int[][] ess = {{1, 2, 5}, {2, 3}, {3, 5}, {4}, {5}, {}}; // links as adjacency lists
        double[][] wss = {{7, 9, 14}, {10, 15}, {11, 2}, {6}, {9}, {}}; // link weights

        for (int i = 0; i < n; i++) { // srcId
            StdDraw.filledCircle(xs[i], ys[i], .05); // draw nodes
            for (int j : ess[i]) { // dstId
                StdDraw.line(xs[i], ys[i], xs[j], ys[j]); // draw links
            }
        }

        // The default font is a Sans Serif font with point size 16
        Font nodeFont = new Font("Arial", Font.BOLD, 64);
        Font linkFont = new Font("Arial", Font.BOLD, 48);
        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
        for (int i = 0; i < n; i++) { // srcId
            StdDraw.setFont(nodeFont);
            StdDraw.text(xs[i], ys[i] - .01, String.valueOf(i));
            int[] es = ess[i];
            double[] ws = wss[i];
            for (int ej = 0; ej < es.length; ej++) {
                int j = es[ej]; // dstId
                // middle of the link
                double x = (xs[i] + xs[j]) / 2;
                double y = (ys[i] + ys[j]) / 2;
                double w = ws[ej];
                StdDraw.setFont(linkFont);
                StdDraw.text(x, y, String.valueOf(w));
            }
        }

    }
}
