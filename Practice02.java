import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Practice02 extends CustomPanel {
    /** Observer's distance */
    private final int DISTANCE = 250;

    private final int focalX = 150;

    private final int focalY = 150;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.drawBuffer == null) {
            this.drawBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        this.drawCube();
        this.drawPixel(focalX, focalY);

        g.drawImage(this.drawBuffer, 0, 0, getWidth(), getHeight(), null);
    }

    private void drawCube() {
        int[] initialPoint = { 300, 100, 100 };
        int size = 200;

        int[][] vertex = {
                { initialPoint[0], initialPoint[1], initialPoint[2] },
                { initialPoint[0] + size, initialPoint[1], initialPoint[2] },
                { initialPoint[0] + size, initialPoint[1] + size, initialPoint[2] },
                { initialPoint[0], initialPoint[1] + size, initialPoint[2] },
                { initialPoint[0], initialPoint[1], initialPoint[2] + size },
                { initialPoint[0] + size, initialPoint[1], initialPoint[2] + size },
                { initialPoint[0] + size, initialPoint[1] + size, initialPoint[2] + size },
                { initialPoint[0], initialPoint[1] + size, initialPoint[2] + size }
        };

        int[][] projectedVertex = new int[8][2];

        for (int i = 0; i < 8; i++) {
            projectedVertex[i] = projectVertex(vertex[i]);
        }

        drawEdges(projectedVertex);
    }

    private int[] projectVertex(int[] vertex) {
        int x = vertex[0];
        int y = vertex[1];
        int z = vertex[2];
        int px = (int) (x * DISTANCE / (z + DISTANCE));
        int py = (int) (y * DISTANCE / (z + DISTANCE));
        return new int[] { px + focalX, py + focalY };
    }

    private void drawEdges(int[][] vertex) {
        int[][] edges = {
                { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 0 },
                { 4, 5 }, { 5, 6 }, { 6, 7 }, { 7, 4 },
                { 0, 4 }, { 1, 5 }, { 2, 6 }, { 3, 7 }
        };

        for (int[] edge : edges) {
            int x0 = vertex[edge[0]][0];
            int y0 = vertex[edge[0]][1];
            int x1 = vertex[edge[1]][0];
            int y1 = vertex[edge[1]][1];
            drawLine(x0, y0, x1, y1);
        }
    }

}
