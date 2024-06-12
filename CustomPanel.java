import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

abstract public class CustomPanel extends JPanel {

    protected BufferedImage drawBuffer;

    public void drawLine(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        // Si no hay diferencia en x y y, no dibujar
        if (dx == 0 && dy == 0) {
            return;
        }

        // Determinar la dirección de la linea
        int sx = (x1 < x2) ? 1 : -1; // Dirección de x
        int sy = (y1 < y2) ? 1 : -1; // Dirección de y
        int err = dx - dy; // Valor de error inicial

        // Punto inicial
        int x = x1;
        int y = y1;

        while (x != x2 || y != y2) {
            drawPixel(x, y);

            // Calcular el valor de error doblando su valor y ajustándolo en las coordenadas
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy; // Ajustar el valor de error en y sustrayendo el diferencial en y
                x += sx; // Ajustar siguiente X
            }
            if (e2 < dx) {
                err += dx; // Ajustar el valor de error en x sumando el diferencial en x
                y += sy; // Ajustar siguiente Y
            }
        }

        // Al finalizar, dibujar el último punto
        drawPixel(x2, y2);
    }

    public void drawPixel(int x, int y) {
        try {
            // Checar si el punto está fuera de los límites
            if (x < 0 || y < 0 || x >= drawBuffer.getWidth() || y >= drawBuffer.getHeight()) {
                throw new IndexOutOfBoundsException();
            }
            // Si no está fuera de los límites, dibujar el pixel
            // (Internamente también lanza una excepción, por cualquier cosa que pueda
            // pasar)
            drawBuffer.setRGB(x, y, Color.BLACK.getRGB());
        } catch (IndexOutOfBoundsException e) {
            // System.err.println("Warning! Point out of bounds (" + x + ", " + y + ")");
        }
    }
}
