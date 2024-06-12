import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Practice04 extends CustomPanel {
    /** The most left-top point of the cube */
    private int[] initialPoint = { 10, 10, 10 };
    /** The size of the cube */
    private int size = 200;

    public Practice04() {
        setSize(800, 600);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_PLUS:
                    case KeyEvent.VK_ADD:
                        System.out.println(size);
                        size += 2;
                        break;
                    case KeyEvent.VK_MINUS:
                    case KeyEvent.VK_SUBTRACT:
                        size = Math.max(2, size - 2); // Ensure size doesn't go below 2
                        System.out.println(size);
                        break;
                }
                drawCube();
                repaint();
            }
        });

        drawCube();
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.drawBuffer, 0, 0, getWidth(), getHeight(), null);
    }

    private void drawCube() {
        this.drawBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

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
