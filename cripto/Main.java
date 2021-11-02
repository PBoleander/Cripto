package cripto;

import javax.swing.*;

class Main extends JFrame {

    Main() {
        super();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Cripto");

        this.add(new Gui());

        this.pack();

        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
