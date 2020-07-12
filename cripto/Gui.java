package cripto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class Gui extends JPanel implements ActionListener {

    private final Criptador criptador;
    private final JButton btnCargar, btnCopiar, btnDescifrar, btnEncriptar, btnGuardar, btnLimpiarEntrada,
            btnLimpiarSalida;
    private final JFileChooser fileChooser;
    private final JPanel panelBotones, panelPass, panelTextsAreas;
    private final JPasswordField txtPassword;
    private final JTextArea txtEntrada, txtSalida;

    Gui() {
        super(new GridBagLayout());
        this.criptador = new Criptador();
        this.fileChooser = new JFileChooser();

        this.btnCargar = new JButton("Leer archivo");
        this.btnCopiar = new JButton("<");
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
        } else if (o.equals(btnCopiar)) { // BOTÓN COPIAR (<)
            txtEntrada.setText(txtSalida.getText());
            txtSalida.setText("");
            txtEntrada.requestFocus();
        } else if (o.equals(btnLimpiarEntrada)) { // BOTÓN LIMPIAR ENTRADA
            txtEntrada.setText("");
            txtEntrada.requestFocus();
        } else if (o.equals(btnLimpiarSalida)) { // BOTÓN LIMPIAR SALIDA
            txtSalida.setText("");
        } else if (o.equals(btnCargar)) { // BOTÓN CARGAR ARCHIVO
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    cargarArchivo(fileChooser.getSelectedFile());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (o.equals(btnGuardar)) { // BOTÓN GUARDAR EN ARCHIVO
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
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

    private void configurarBotones() { // Botones descifrar y encriptar
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
        txtPassword.setPreferredSize(new Dimension(230, 20));
        GridBagConstraints txtPassC = new GridBagConstraints();

        txtPassC.gridx = 1;
        txtPassC.insets = new Insets(0, 10, 0, 0);

        panelPass.add(passLabel, new GridBagConstraints());
        panelPass.add(txtPassword, txtPassC);
    }

    private void configurarTextsAreas() {
        // Labels
        JLabel labelEntrada = new JLabel("Texto de entrada:");
        JLabel labelSalida = new JLabel("Texto de salida:");

        // Configuración de los textAreas
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

        // Configuración de los botones (cargar, copiar, guardar y los dos limpiar)
        btnCargar.addActionListener(this);
        btnCopiar.addActionListener(this);
        btnGuardar.addActionListener(this);
        btnLimpiarEntrada.addActionListener(this);
        btnLimpiarSalida.addActionListener(this);

        Dimension dimBotonesPeques = new Dimension(100, 30);
        btnCargar.setPreferredSize(new Dimension(130, 30));
        btnCopiar.setPreferredSize(new Dimension(20, 300));
        btnCopiar.setMargin(new Insets(0, 0, 0, 0));
        btnGuardar.setPreferredSize(dimBotonesPeques);
        btnLimpiarEntrada.setPreferredSize(dimBotonesPeques);
        btnLimpiarSalida.setPreferredSize(dimBotonesPeques);

        // Configuración fileChooser
        fileChooser.setFileHidingEnabled(false);

        // Maquetación de los botones cargar y limpiar entrada
        JPanel btnsEntradaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints btnsPanelC = new GridBagConstraints();
        btnsPanelC.insets = new Insets(0, 10, 0, 10);
        btnsEntradaPanel.add(btnCargar, btnsPanelC);
        btnsPanelC.gridx = 1;
        btnsEntradaPanel.add(btnLimpiarEntrada, btnsPanelC);

        // Maquetación de los botones guardar y limpiar salida
        JPanel btnsSalidaPanel = new JPanel(new GridBagLayout());
        btnsPanelC.gridx = 0;
        btnsSalidaPanel.add(btnGuardar, btnsPanelC);
        btnsPanelC.gridx = 1;
        btnsSalidaPanel.add(btnLimpiarSalida, btnsPanelC);

        // Maquetación de todo (juntar las partes que pertenecen al panelTextsAreas)
        GridBagConstraints txtC = new GridBagConstraints();
        panelTextsAreas.add(labelEntrada, txtC);
        txtC.gridx = 2;
        panelTextsAreas.add(labelSalida, txtC);

        txtC.gridx = 0;
        txtC.gridy = 1;
        panelTextsAreas.add(scrollEntrada, txtC);
        txtC.gridx = 1;
        panelTextsAreas.add(btnCopiar, txtC);
        txtC.gridx = 2;
        panelTextsAreas.add(scrollSalida, txtC);
        txtC.gridx = 0;
        txtC.gridy = 2;
        txtC.insets = new Insets(10, 0, 10, 0);
        panelTextsAreas.add(btnsEntradaPanel, txtC);
        txtC.gridx = 2;
        panelTextsAreas.add(btnsSalidaPanel, txtC);
    }

    private void guardarArchivo(File archivo) throws IOException {
        if (archivo.isFile()) { // El archivo ya existe y se está a punto de sobrescribir
            int respuesta = JOptionPane.showConfirmDialog(this, "Estás a punto de sobrescribir" +
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
        constraints.insets = new Insets(5, 10, 5, 10);
        this.add(panelPass, constraints);
        constraints.gridx = 1;
        this.add(panelBotones, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        this.add(panelTextsAreas, constraints);
    }
}
