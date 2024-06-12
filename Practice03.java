import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Practice03 extends CustomPanel {
    /** The most left-top point of the cube */
    private int[] initialPoint = { 200, 100, 100 };

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.drawBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        drawCube();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        initialPoint[0] -= 1;
                        break;
                    case KeyEvent.VK_RIGHT:
                        initialPoint[0] += 1;
                        break;
                    case KeyEvent.VK_UP:
                        initialPoint[1] -= 1;
                        break;
                    case KeyEvent.VK_DOWN:
                        initialPoint[1] += 1;
                        break;
                    case KeyEvent.VK_W:
                        initialPoint[2] -= 1;
                        break;
                    case KeyEvent.VK_S:
                        initialPoint[2] += 1;
                        break;
                }
                drawCube();
                repaint();
            }
        });

        setFocusable(true);

        g.drawImage(this.drawBuffer, 0, 0, getWidth(), getHeight(), null);
    }

    private void drawCube() {

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
