package cripto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Gui extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private final Criptador criptador;
    private final JButton btnDescifrar, btnEncriptar, btnLimpiarEntrada, btnLimpiarSalida;
    private final JPanel panelBotones, panelPass, panelTextsAreas;
    private final JPasswordField txtPassword;
    private final JTextArea txtEntrada, txtSalida;

    Gui() {
        super(new GridBagLayout());
        this.criptador = new Criptador();

        this.btnDescifrar = new JButton("Descifrar");
        this.btnEncriptar = new JButton("Encriptar");
        this.btnLimpiarEntrada = new JButton("Limpiar");
        this.btnLimpiarSalida = new JButton("Limpiar");

        this.panelBotones = new JPanel(new GridBagLayout());
        this.panelPass = new JPanel(new GridBagLayout());
        this.panelTextsAreas = new JPanel(new GridBagLayout());

        this.txtEntrada = new JTextArea();
        this.txtPassword = new JPasswordField();
        this.txtSalida = new JTextArea();

        configurarPass();
        configurarBotones();
        configurarTextsAreas();
        juntarPartes();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object o = actionEvent.getSource();
        if (o.equals(btnDescifrar)) { // BOTÓN DESCIFRAR
            criptador.setClave(txtPassword.getPassword());
            txtSalida.setText(criptador.descifrar(txtEntrada.getText()));
        } else if (o.equals(btnEncriptar)) { // BOTÓN ENCRIPTAR
            criptador.setClave(txtPassword.getPassword());
            txtSalida.setText(criptador.encriptar(txtEntrada.getText()));
        } else if (o.equals(btnLimpiarEntrada)) { // BOTÓN LIMPIAR ENTRADA
            txtEntrada.setText("");
            txtEntrada.requestFocus();
        } else if (o.equals(btnLimpiarSalida)) { // BOTÓN LIMPIAR SALIDA
            txtSalida.setText("");
        }
    }

    private void configurarBotones() {
        btnDescifrar.addActionListener(this);
        btnEncriptar.addActionListener(this);

        Dimension dimBotones = new Dimension(100, 30);
        btnDescifrar.setPreferredSize(dimBotones);
        btnEncriptar.setPreferredSize(dimBotones);

        GridBagConstraints botonesC = new GridBagConstraints();
        botonesC.insets = new Insets(0, 5, 0, 5);
        panelBotones.add(btnDescifrar, botonesC);

        botonesC.gridx = 1;
        panelBotones.add(btnEncriptar, botonesC);
    }

    private void configurarPass() {
        JLabel passLabel = new JLabel("Contraseña:");
        txtPassword.setPreferredSize(new Dimension(200, 20));
        GridBagConstraints txtPassC = new GridBagConstraints();

        txtPassC.weightx = 1.0;
        txtPassC.gridwidth = GridBagConstraints.REMAINDER;
        txtPassC.gridx = 1;
        txtPassC.insets = new Insets(0, 20, 0, 0);

        panelPass.add(passLabel, new GridBagConstraints());
        panelPass.add(txtPassword, txtPassC);
    }

    private void configurarTextsAreas() {
        JLabel labelEntrada = new JLabel("Entrada de texto:");
        JLabel labelSalida = new JLabel("Texto de salida:");

        txtSalida.setEditable(false);

        Font fuente = new Font(Font.MONOSPACED, Font.PLAIN, 13);
        txtEntrada.setFont(fuente);
        txtSalida.setFont(fuente);

        txtEntrada.setLineWrap(true);
        txtEntrada.setWrapStyleWord(true);
        txtSalida.setLineWrap(true);
        txtSalida.setWrapStyleWord(true);

        Dimension dimTxt = new Dimension(280, 300);
        JScrollPane scrollEntrada = new JScrollPane(txtEntrada);
        JScrollPane scrollSalida = new JScrollPane(txtSalida);
        scrollEntrada.setPreferredSize(dimTxt);
        scrollSalida.setPreferredSize(dimTxt);

        btnLimpiarEntrada.addActionListener(this);
        btnLimpiarSalida.addActionListener(this);

        Dimension dimBotones = new Dimension(100, 30);
        btnLimpiarEntrada.setPreferredSize(dimBotones);
        btnLimpiarSalida.setPreferredSize(dimBotones);

        GridBagConstraints txtC = new GridBagConstraints();
        txtC.insets = new Insets(0, 5, 0, 5);

        panelTextsAreas.add(labelEntrada, txtC);
        txtC.gridx = 1;
        panelTextsAreas.add(labelSalida, txtC);

        txtC.gridx = 0;
        txtC.gridy = 1;
        panelTextsAreas.add(scrollEntrada, txtC);
        txtC.gridx = 1;
        panelTextsAreas.add(scrollSalida, txtC);
        txtC.gridx = 0;
        txtC.gridy = 2;
        txtC.insets = new Insets(10, 0, 10, 0);
        panelTextsAreas.add(btnLimpiarEntrada, txtC);
        txtC.gridx = 1;
        panelTextsAreas.add(btnLimpiarSalida, txtC);
    }

    private void juntarPartes() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 15, 5, 15);
        this.add(panelPass, constraints);
        constraints.gridx = 1;
        this.add(panelBotones, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        this.add(panelTextsAreas, constraints);
    }
}
