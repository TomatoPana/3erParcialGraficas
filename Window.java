import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

class Window extends JFrame {

    private static final ArrayList<String> practices = new ArrayList<>();

    static {
        practices.add("Practice01|01 Proyección Paralela");
        practices.add("Practice02|02 Proyección de Perspectiva");
        practices.add("Practice03|03 Traslación 3D");
        practices.add("Practice04|04 Escalamiento");
        practices.add("Practice05|05 Rotación 3D");
        practices.add("Practice06|06 Rotación 3D Automática");
        practices.add("Practice07|07 Curva Explícita 3D");
        practices.add("Practice08|08 Superficie 3D");
        practices.add("Practice09|09 Cilindro 3D");
    }

    public Window() {
        setTitle("3er Parcial");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setSize(600, 396);
        setMinimumSize(getSize());
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(practices.size(), 1));

        for (String practice : practices) {
            String key = practice.split("\\|")[0];
            String value = practice.split("\\|")[1];
            JButton button = new JButton(value);
            button.addActionListener(e -> {
                try {
                    Class<?> clazz = Class.forName(key);
                    JFrame frame = new JFrame(value);
                    JPanel innerPanel = (JPanel) clazz.getDeclaredConstructor().newInstance();
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(800, 600);
                    frame.setMinimumSize(frame.getSize());
                    frame.add(innerPanel);
                    frame.setLocationRelativeTo(null);
                    Window.this.setVisible(false);
                    frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            Window.this.setVisible(true);
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            panel.add(button);
        }

        add(panel);
    }

    public static void main(String[] args) {
        Window ventana = new Window();
        ventana.setVisible(true);
    }
}
