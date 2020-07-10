package cripto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class Gui extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private final Criptador criptador;
    private final JButton btnCargar, btnDescifrar, btnEncriptar, btnGuardar, btnLimpiarEntrada, btnLimpiarSalida;
    private final JFileChooser fileChooser;
    private final JPanel panelBotones, panelPass, panelTextsAreas;
    private final JPasswordField txtPassword;
    private final JTextArea txtEntrada, txtSalida;

    Gui() {
        super(new GridBagLayout());
        this.criptador = new Criptador();
        this.fileChooser = new JFileChooser();

        this.btnCargar = new JButton("Leer archivo");
        this.btnDescifrar = new JButton("Descifrar");
        this.btnEncriptar = new JButton("Encriptar");
        this.btnGuardar = new JButton("Guardar");
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
        } else if (o.equals(btnCargar)) { // BOTÓN CARGAR ARCHIVO
            fileChooser.setApproveButtonText("Abrir");
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    cargarArchivo(fileChooser.getSelectedFile());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (o.equals(btnGuardar)) { // BOTÓN GUARDAR EN ARCHIVO
            fileChooser.setApproveButtonText("Guardar");
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    guardarArchivo(fileChooser.getSelectedFile());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void cargarArchivo(File archivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        StringBuilder sb = new StringBuilder();
        String linea;

        while ((linea = br.readLine()) != null) sb.append(linea).append(System.lineSeparator());
        br.close();

        txtEntrada.setText(sb.toString());
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

        btnCargar.addActionListener(this);
        btnGuardar.addActionListener(this);
        btnLimpiarEntrada.addActionListener(this);
        btnLimpiarSalida.addActionListener(this);

        Dimension dimBotonesPeques = new Dimension(100, 30);
        Dimension dimBotonesGrandes = new Dimension(130, 30);
        btnCargar.setPreferredSize(dimBotonesGrandes);
        btnGuardar.setPreferredSize(dimBotonesGrandes);
        btnLimpiarEntrada.setPreferredSize(dimBotonesPeques);
        btnLimpiarSalida.setPreferredSize(dimBotonesPeques);

        fileChooser.setFileHidingEnabled(false);

        JPanel btnsEntradaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints btnsPanelC = new GridBagConstraints();
        btnsPanelC.insets = new Insets(0, 10, 0, 10);
        btnsEntradaPanel.add(btnCargar, btnsPanelC);
        btnsPanelC.gridx = 1;
        btnsEntradaPanel.add(btnLimpiarEntrada, btnsPanelC);

        JPanel btnsSalidaPanel = new JPanel(new GridBagLayout());
        btnsPanelC.gridx = 0;
        btnsSalidaPanel.add(btnGuardar, btnsPanelC);
        btnsPanelC.gridx = 1;
        btnsSalidaPanel.add(btnLimpiarSalida, btnsPanelC);

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
        panelTextsAreas.add(btnsEntradaPanel, txtC);
        txtC.gridx = 1;
        panelTextsAreas.add(btnsSalidaPanel, txtC);
    }

    private void guardarArchivo(File archivo) throws IOException {
        if (archivo.isFile()) {
            int respuesta = JOptionPane.showConfirmDialog(this, "Estás a punto de sobreescribir" +
                    " el archivo. ¿Quieres continuar?", "Aviso", JOptionPane.OK_CANCEL_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) return;
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        bw.write(txtSalida.getText());
        bw.close();

        JOptionPane.showMessageDialog(this, "Archivo guardado con éxito");
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
