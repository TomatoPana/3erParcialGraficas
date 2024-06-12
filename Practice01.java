import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Practice01 extends CustomPanel {
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.drawBuffer == null) {
            this.drawBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        this.drawCube();

        g.drawImage(this.drawBuffer, 0, 0, getWidth(), getHeight(), null);
    }

    private void drawCube() {
        int[] initialPoint = { 200, 100, 100 };
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
        int px = x + (int) (0.5 * z);
        int py = y + (int) (0.5 * z);
        return new int[] { px, py };
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
