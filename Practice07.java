import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Practice07 extends CustomPanel {
    private double angleX = 0;
    private double angleY = 0;
    private double angleZ = 0;

    private double scale = 100;
    private double[] mainVertex = { 400, 300, 0 };
    private double[] perspectivePoint = { 400, 300, 1000 };

    private ArrayList<double[]> vertexList = new ArrayList<>();

    public Practice07() {
        setSize(800, 600);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                int key = ke.getKeyCode();

                switch (key) {
                    case KeyEvent.VK_W:
                        angleX += 0.1;
                        break;
                    case KeyEvent.VK_S:
                        angleX -= 0.1;
                        break;
                    case KeyEvent.VK_A:
                        angleY -= 0.1;
                        break;
                    case KeyEvent.VK_D:
                        angleY += 0.1;
                        break;
                    case KeyEvent.VK_E:
                        angleZ += 0.1;
                        break;
                    case KeyEvent.VK_Q:
                        angleZ -= 0.1;
                        break;
                }
                drawCurve();
                repaint();
            }
        });

        for (double angle = -Math.PI; angle <= Math.PI; angle += Math.PI / 100) {
            double[] vertex = new double[3];
            vertex[0] = Math.cos(3 * angle);
            vertex[1] = 2 * Math.pow(Math.cos(angle), 2);
            vertex[2] = Math.sin(2 * angle);
            vertexList.add(vertex);
        }

        drawCurve();
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.drawBuffer, 0, 0, getWidth(), getHeight(), null);
    }

    private void drawCurve() {
        drawBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        double[][] translatedVertex = new double[vertexList.size()][3];
        for (int i = 0; i < vertexList.size(); i++) {
            double[] vertex = vertexList.get(i);
            vertex = rotateX(vertex, angleX);
            vertex = rotateY(vertex, angleY);
            vertex = rotateZ(vertex, angleZ);
            translatedVertex[i] = vertex;
        }

        for (int i = 0; i < vertexList.size(); i++) {
            double[] v = translatedVertex[i];
            double[] trasladado = {
                    (v[0] * scale) + mainVertex[0],
                    (v[1] * scale) + mainVertex[1],
                    (v[2] * scale) + mainVertex[2]
            };
            translatedVertex[i] = trasladado;
        }

        for (int i = 0; i < vertexList.size() - 1; i++) {
            double x0 = translatedVertex[i][0];
            double y0 = translatedVertex[i][1];
            double z0 = translatedVertex[i][2];

            double x1 = translatedVertex[i + 1][0];
            double y1 = translatedVertex[i + 1][1];
            double z1 = translatedVertex[i + 1][2];

            Point2D p1 = point3DtoPoint2D(x0, y0, z0);
            Point2D p2 = point3DtoPoint2D(x1, y1, z1);

            drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
        }
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

    private Point2D point3DtoPoint2D(double x, double y, double z) {
        double u = -perspectivePoint[2] / (z - perspectivePoint[2]);

        double px = perspectivePoint[0] + (x - perspectivePoint[0]) * u;
        double py = perspectivePoint[1] + (y - perspectivePoint[1]) * u;

        return new Point2D.Double(px, py);
    }

}
