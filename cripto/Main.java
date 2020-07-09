package cripto;

import javax.swing.*;
import java.awt.*;

class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    Main() {
        super();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Cripto");

        this.add(new Gui());

        this.pack();

        centrarEnPantalla();

        this.setVisible(true);
    }

    private void centrarEnPantalla() {
        // Averigua el tamaño de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int anchuraPantalla = (int) screenSize.getWidth();
        int alturaPantalla = (int) screenSize.getHeight();

        // Centra la ventana sabiendo su tamaño
        int anchuraVentana = this.getWidth();
        int alturaVentana = this.getHeight();
        this.setBounds((anchuraPantalla - anchuraVentana) / 2, (alturaPantalla - alturaVentana) / 2,
                anchuraVentana, alturaVentana);
    }

    public static void main(String[] args) {
        new Main();
    }
}
