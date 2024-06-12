import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Practice05 extends CustomPanel {

    private double[][] vertex;
    private double angleX = 0, angleY = 0, angleZ = 0; // Angles of rotation around each axis
    private final int size = 50; // Size of the cube

    public Practice05() {
        setSize(800, 600);

        initializeVertices();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        angleY -= Math.toRadians(10);
                        break;
                    case KeyEvent.VK_RIGHT:
                        angleY += Math.toRadians(10);
                        break;
                    case KeyEvent.VK_UP:
                        angleX -= Math.toRadians(10);
                        break;
                    case KeyEvent.VK_DOWN:
                        angleX += Math.toRadians(10);
                        break;
                    case KeyEvent.VK_A:
                        angleZ -= Math.toRadians(10);
                        break;
                    case KeyEvent.VK_D:
                        angleZ += Math.toRadians(10);
                        break;
                }
                drawCube();
                repaint();
            }
        });

        drawCube();
        setFocusable(true);
    }

    private void initializeVertices() {
        vertex = new double[][] {
                { -size, -size, -size }, { size, -size, -size }, { size, size, -size }, { -size, size, -size },
                { -size, -size, size }, { size, -size, size }, { size, size, size }, { -size, size, size }
        };
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.drawBuffer, 0, 0, getWidth(), getHeight(), null);
    }

    private void drawCube() {
        drawBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        double[][] rotatedVertex = new double[8][3];

        for (int i = 0; i < vertex.length; i++) {
            double[] rotatedX = rotateX(vertex[i], angleX);
            double[] rotatedXY = rotateY(rotatedX, angleY);
            double[] rotatedXYZ = rotateZ(rotatedXY, angleZ);
            rotatedVertex[i] = rotatedXYZ;
        }

        int[][] projectedVertex = new int[8][2];
        for (int i = 0; i < rotatedVertex.length; i++) {
            projectedVertex[i] = projectVertex(rotatedVertex[i]);
        }

        drawEdges(projectedVertex);
    }

    private double[] rotateX(double[] vertex, double angle) {
        double[] rotated = new double[3];
        rotated[0] = vertex[0];
        rotated[1] = vertex[1] * Math.cos(angle) - vertex[2] * Math.sin(angle);
        rotated[2] = vertex[1] * Math.sin(angle) + vertex[2] * Math.cos(angle);
        return rotated;
    }

    private double[] rotateY(double[] vertex, double angle) {
        double[] rotated = new double[3];
        rotated[0] = vertex[0] * Math.cos(angle) + vertex[2] * Math.sin(angle);
        rotated[1] = vertex[1];
        rotated[2] = -vertex[0] * Math.sin(angle) + vertex[2] * Math.cos(angle);
        return rotated;
    }

    private double[] rotateZ(double[] vertex, double angle) {
        double[] rotated = new double[3];
        rotated[0] = vertex[0] * Math.cos(angle) - vertex[1] * Math.sin(angle);
        rotated[1] = vertex[0] * Math.sin(angle) + vertex[1] * Math.cos(angle);
        rotated[2] = vertex[2];
        return rotated;
    }

    private int[] projectVertex(double[] vertex) {
        int x = (int) (vertex[0] * 2) + 400; // Translate to center
        int y = (int) (vertex[1] * 2) + 300; // Translate to center
        return new int[] { x, y };
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
