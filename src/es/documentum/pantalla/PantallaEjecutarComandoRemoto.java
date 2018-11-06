package es.documentum.pantalla;

import es.documentum.utilidades.Utilidades;
import static es.documentum.utilidades.Utilidades.escribeLog;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PantallaEjecutarComandoRemoto extends javax.swing.JFrame {

    static String tipo = "FRAME";
    String usuario = "";
    String clave = "";
    String servidor = "";
    private boolean botonderecho;
    private String componente;
    public static PantallaDocumentum ventanapadre = null;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public void setTextoServidor(String servidor) {
        TextoServidor.setText(servidor);
    }

    public void setTextoUsuario(String usuario) {
        TextoUsuario.setText(usuario);
    }

    public void setTextoClave(String clave) {
        TextoClave.setText(clave);
    }

    public PantallaEjecutarComandoRemoto(PantallaDocumentum parent, boolean modal) {
        ventanapadre = parent;
        initComponents();
        setLocationRelativeTo(null);
        //  setVisible(true);
        repaint();
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }

            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }

        });
        cargarComboHistorial();
        try {
            setIconImage(new ImageIcon(getLogo()).getImage());
        } catch (NullPointerException e) {
            Utilidades.escribeLog("\nError cargando el Logo " + e.getMessage() + "\n");
        }
        opcionRBMetalActionPerformed(null);
        TextoComando.requestFocus();
        TextoServidor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!TextoServidor.getText().isEmpty()) {
                    setServidor(TextoServidor.getText());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!TextoServidor.getText().isEmpty()) {
                    setServidor(TextoServidor.getText());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!TextoServidor.getText().isEmpty()) {
                    setServidor(TextoServidor.getText());
                }
            }
        });

        TextoUsuario.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!TextoUsuario.getText().isEmpty()) {
                    setUsuario(TextoUsuario.getText());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!TextoUsuario.getText().isEmpty()) {
                    setUsuario(TextoUsuario.getText());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!TextoUsuario.getText().isEmpty()) {
                    setUsuario(TextoUsuario.getText());
                }
            }
        });

        TextoClave.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!String.valueOf(TextoClave.getPassword()).isEmpty()) {
                    setClave(String.valueOf(TextoClave.getPassword()));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!String.valueOf(TextoClave.getPassword()).isEmpty()) {
                    setClave(String.valueOf(TextoClave.getPassword()));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!String.valueOf(TextoClave.getPassword()).isEmpty()) {
                    setUsuario(String.valueOf(TextoClave.getPassword()));
                }
            }
        });
    }

    protected static Image getLogo() {
        //   java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/documentum_logo_mini.gif");
        java.net.URL imgURL = PantallaEjecutarComandoRemoto.class.getClassLoader().getResource("es/documentum/imagenes/terminal.png");

        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            return null;
        }
    }

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {
        this.requestFocus();
        this.toFront();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupEditar = new javax.swing.JPopupMenu();
        opcionCopiar = new javax.swing.JMenuItem();
        opcionPegar = new javax.swing.JMenuItem();
        opcionCopiarTodo = new javax.swing.JMenuItem();
        botonCerrar = new javax.swing.JButton();
        TextoComando = new javax.swing.JTextField();
        LabelComando = new javax.swing.JLabel();
        botonEjecutar = new javax.swing.JButton();
        comboHistorial = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        Texto = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        TextoServidor = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        TextoUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TextoClave = new javax.swing.JPasswordField();
        jMenuBar1 = new javax.swing.JMenuBar();
        Menu = new javax.swing.JMenu();
        opcionCerrar = new javax.swing.JMenuItem();
        opcionApariencia = new javax.swing.JMenu();
        opcionRBMetal = new javax.swing.JRadioButtonMenuItem();
        opcionRBNimbus = new javax.swing.JRadioButtonMenuItem();
        opcionRBWindows = new javax.swing.JRadioButtonMenuItem();
        opcionRBWindowsClassic = new javax.swing.JRadioButtonMenuItem();
        opcionRBPorDefecto = new javax.swing.JRadioButtonMenuItem();

        opcionCopiar.setText("Copiar Ctrl+C");
        opcionCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarActionPerformed(evt);
            }
        });
        popupEditar.add(opcionCopiar);

        opcionPegar.setText("Pegar  Ctrl+V");
        opcionPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionPegarActionPerformed(evt);
            }
        });
        popupEditar.add(opcionPegar);

        opcionCopiarTodo.setText("Copiar Todo");
        opcionCopiarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarTodoActionPerformed(evt);
            }
        });
        popupEditar.add(opcionCopiarTodo);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ejecutar Comandos SSH en remoto");
        setAlwaysOnTop(true);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setName("VentanaLeerFichero"); // NOI18N

        botonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        botonCerrar.setText("Cerrar");
        botonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarActionPerformed(evt);
            }
        });

        TextoComando.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TextoComandoMousePressed(evt);
            }
        });
        TextoComando.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextoComandoActionPerformed(evt);
            }
        });
        TextoComando.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextoComandoKeyPressed(evt);
            }
        });

        LabelComando.setText("Comando");

        botonEjecutar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/ejecutar.png"))); // NOI18N
        botonEjecutar.setText("Ejecutar");
        botonEjecutar.setToolTipText("Ejecutar comando");
        botonEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEjecutarActionPerformed(evt);
            }
        });

        comboHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                comboHistorialMousePressed(evt);
            }
        });
        comboHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboHistorialActionPerformed(evt);
            }
        });

        Texto.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        Texto.setForeground(new java.awt.Color(0, 51, 102));
        Texto.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Texto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TextoMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(Texto);

        jLabel1.setText("Servidor");

        TextoServidor.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                TextoServidorInputMethodTextChanged(evt);
            }
        });

        jLabel2.setText("Usuario");

        jLabel3.setText("Contraseña");

        Menu.setMnemonic('A');
        Menu.setText("Archivo");

        opcionCerrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        opcionCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        opcionCerrar.setText("Cerrar");
        opcionCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCerrarActionPerformed(evt);
            }
        });
        Menu.add(opcionCerrar);

        jMenuBar1.add(Menu);

        opcionApariencia.setMnemonic('A');
        opcionApariencia.setText("Apariencia");

        opcionRBMetal.setSelected(true);
        opcionRBMetal.setText("Metal");
        opcionRBMetal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBMetalActionPerformed(evt);
            }
        });
        opcionApariencia.add(opcionRBMetal);

        opcionRBNimbus.setSelected(true);
        opcionRBNimbus.setText("Nimbus");
        opcionRBNimbus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBNimbusActionPerformed(evt);
            }
        });
        opcionApariencia.add(opcionRBNimbus);

        opcionRBWindows.setSelected(true);
        opcionRBWindows.setText("Windows");
        opcionRBWindows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBWindowsActionPerformed(evt);
            }
        });
        opcionApariencia.add(opcionRBWindows);

        opcionRBWindowsClassic.setSelected(true);
        opcionRBWindowsClassic.setText("Windows Classic");
        opcionRBWindowsClassic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBWindowsClassicActionPerformed(evt);
            }
        });
        opcionApariencia.add(opcionRBWindowsClassic);

        opcionRBPorDefecto.setSelected(true);
        opcionRBPorDefecto.setText("Propia del Sistema Operativo");
        opcionRBPorDefecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBPorDefectoActionPerformed(evt);
            }
        });
        opcionApariencia.add(opcionRBPorDefecto);

        jMenuBar1.add(opcionApariencia);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LabelComando, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(TextoServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TextoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TextoClave, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                            .addComponent(TextoComando))
                        .addGap(29, 29, 29)
                        .addComponent(botonEjecutar))
                    .addComponent(comboHistorial, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextoServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(TextoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(TextoClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextoComando, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelComando)
                    .addComponent(botonEjecutar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(botonCerrar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opcionCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCerrarActionPerformed
        botonCerrarActionPerformed(evt);
    }//GEN-LAST:event_opcionCerrarActionPerformed

    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_botonCerrarActionPerformed

    private void TextoComandoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextoComandoActionPerformed

    }//GEN-LAST:event_TextoComandoActionPerformed

    private void botonEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEjecutarActionPerformed
        Ejecutar();
    }//GEN-LAST:event_botonEjecutarActionPerformed

    private void Ejecutar() {
        if (TextoComando.getText().isEmpty()) {
            return;
        }
        try {
            Utilidades util = new Utilidades();
            List<String> resultado = util.comandoRemoto(this.getServidor(), this.getUsuario(), this.getClave(), TextoComando.getText());
            DefaultListModel ModeloLista = new DefaultListModel();
            Texto.setModel(ModeloLista);

            for (int x = 0; x < resultado.size(); x++) {
                //    System.out.println(resultado.get(x));
                //          Texto.add(resultado.get(x) + "\n");
                ModeloLista.add(x, resultado.get(x));
                //  Texto.append(resultado.get(x).replace(" ","\t")+"\n");
            }
            if (!TextoComando.getText().equals(comboHistorial.getSelectedItem())) {
                try {
                    FileOutputStream historial = new FileOutputStream(new File(util.usuarioHome() + util.separador() + "tmp" + util.separador() + "historialCmd.log"), true);
                    historial.write(("\n" + TextoComando.getText().replaceAll("(\r\n|\n)", " ")).getBytes());
                    historial.close();
                    cargarComboHistorial();
                } catch (Exception ex) {
                }
            }
//            Texto.read(reader, null);
        } catch (Exception ex) {
            escribeLog("Error obtener stream remoto. Error - " + ex.getMessage());
        }
    }

    private void opcionCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarActionPerformed
        if (componente.equals("Texto")) {
            if (Texto.getModel().getElementAt(Texto.getSelectedIndex()) != null) {
                Utilidades.copiarTextoPortapapeles(Texto.getModel().getElementAt(Texto.getSelectedIndex()));
            }
        }

        if (componente.equals("TextoComando")) {
            if (TextoComando.getSelectedText() == null) {
                Utilidades.copiarTextoPortapapeles(TextoComando.getText());
            } else {
                Utilidades.copiarTextoPortapapeles(TextoComando.getSelectedText());
            }
        }
    }//GEN-LAST:event_opcionCopiarActionPerformed

    private void opcionPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionPegarActionPerformed
        if (componente.equals("TextoComando")) {
            TextoComando.setText(Utilidades.pegarTextoPortapapeles());
        }
    }//GEN-LAST:event_opcionPegarActionPerformed

    private void TextoComandoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TextoComandoMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "TextoComando";
            popupmenu(evt);
        }
    }//GEN-LAST:event_TextoComandoMousePressed

    private void comboHistorialMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboHistorialMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            componente = "comboHistorial";
            botonderecho = true;
            popupmenu(evt);
        }
    }//GEN-LAST:event_comboHistorialMousePressed

    private void comboHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboHistorialActionPerformed
        String valor = (String) comboHistorial.getSelectedItem();
        if (TextoComando.getText().isEmpty() || !TextoComando.getText().equals(valor)) {
            if (!valor.isEmpty()) {
                TextoComando.setText(valor);
            }
        }
    }//GEN-LAST:event_comboHistorialActionPerformed

    private void TextoComandoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextoComandoKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            Ejecutar();
        }
    }//GEN-LAST:event_TextoComandoKeyPressed

    private void opcionRBMetalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRBMetalActionPerformed
        opcionRBMetal.setSelected(true);
        opcionRBNimbus.setSelected(false);
        opcionRBWindows.setSelected(false);
        opcionRBWindowsClassic.setSelected(false);
        opcionRBPorDefecto.setSelected(false);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
            System.gc();
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al cambiar Aspecto Visual a Metal - " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionRBMetalActionPerformed

    private void opcionRBNimbusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRBNimbusActionPerformed
        opcionRBMetal.setSelected(false);
        opcionRBNimbus.setSelected(true);
        opcionRBWindows.setSelected(false);
        opcionRBWindowsClassic.setSelected(false);
        opcionRBPorDefecto.setSelected(false);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
            System.gc();
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al cambiar Aspecto Visual a Nimbus - " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionRBNimbusActionPerformed

    private void opcionRBWindowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRBWindowsActionPerformed
        opcionRBMetal.setSelected(false);
        opcionRBNimbus.setSelected(false);
        opcionRBWindows.setSelected(true);
        opcionRBWindowsClassic.setSelected(false);
        opcionRBPorDefecto.setSelected(false);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
            System.gc();
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al cambiar Aspecto Visual a Windows - " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionRBWindowsActionPerformed

    private void opcionRBWindowsClassicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRBWindowsClassicActionPerformed
        opcionRBMetal.setSelected(false);
        opcionRBNimbus.setSelected(false);
        opcionRBWindows.setSelected(false);
        opcionRBWindowsClassic.setSelected(true);
        opcionRBPorDefecto.setSelected(false);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
            System.gc();
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al cambiar Aspecto Visual a Windows Classic - " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionRBWindowsClassicActionPerformed

    private void opcionRBPorDefectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRBPorDefectoActionPerformed
        try {
            opcionRBMetal.setSelected(false);
            opcionRBNimbus.setSelected(false);
            opcionRBWindows.setSelected(false);
            opcionRBWindowsClassic.setSelected(false);
            opcionRBPorDefecto.setSelected(true);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
            System.gc();
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al cambiar Aspecto por Defecto - " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionRBPorDefectoActionPerformed

    private void TextoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TextoMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3 && Texto.getModel().getSize() > 0) {
            botonderecho = true;
            componente = "Texto";
            popupmenu(evt);
        }
    }//GEN-LAST:event_TextoMousePressed

    private void TextoServidorInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_TextoServidorInputMethodTextChanged
        if (!TextoServidor.getText().isEmpty()) {
            setServidor(TextoServidor.getText());
        }
    }//GEN-LAST:event_TextoServidorInputMethodTextChanged

    private void opcionCopiarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarTodoActionPerformed
        String textocompleto = "";
        for (int i = 0; i < Texto.getModel().getSize(); i++) {
//    System.out.println(Texto.getModel().getElementAt(i));
            textocompleto = textocompleto + Texto.getModel().getElementAt(i) + "\n";
        }
        Utilidades.copiarTextoPortapapeles(textocompleto);
    }//GEN-LAST:event_opcionCopiarTodoActionPerformed
    private void popupmenu(MouseEvent evt) {
        if (evt.isPopupTrigger() || botonderecho) {
            botonderecho = false;
            if (evt.getSource().getClass().getName().equals("javax.swing.JTable")) {
                JTable source = (JTable) evt.getSource();
                int row = source.rowAtPoint(evt.getPoint());
                int column = source.columnAtPoint(evt.getPoint());
                if (!source.isRowSelected(row)) {
                    source.changeSelection(row, column, false, false);
                }
            }

            if (evt.getSource() == Texto) {
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
                opcionPegar.setEnabled(false);
                opcionPegar.setVisible(false);
            }
            if (evt.getSource() == TextoComando) {
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
                opcionPegar.setEnabled(true);
                opcionPegar.setVisible(true);
            }

        }
    }

    private void cargarComboHistorial() {
        ArrayList comboBoxItems = new ArrayList();
        Utilidades util = new Utilidades();
        util.crearDirectorio(util.usuarioHome() + util.separador() + "tmp" + util.separador());
        util.crearFichero(util.usuarioHome() + util.separador() + "tmp" + util.separador() + "historialCmd.log", "texto");
        String dirhist = util.usuarioHome() + util.separador() + "tmp" + util.separador() + "historialCmd.log";
        BufferedReader br = null;

        try {
            String linea;
            br = new BufferedReader(new FileReader(dirhist));
            while ((linea = br.readLine()) != null) {
                comboBoxItems.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        DefaultComboBoxModel modelo = new DefaultComboBoxModel(comboBoxItems.toArray());
        comboHistorial.setModel(modelo);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            escribeLog("Error al establecer el estilo de la ventana. Error: " + ex.getMessage());
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PantallaEjecutarComandoRemoto frame = new PantallaEjecutarComandoRemoto(ventanapadre, true);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });

            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelComando;
    private javax.swing.JMenu Menu;
    private javax.swing.JList<String> Texto;
    private javax.swing.JPasswordField TextoClave;
    private javax.swing.JTextField TextoComando;
    private javax.swing.JTextField TextoServidor;
    private javax.swing.JTextField TextoUsuario;
    public static javax.swing.JButton botonCerrar;
    private javax.swing.JButton botonEjecutar;
    private javax.swing.JComboBox comboHistorial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu opcionApariencia;
    private javax.swing.JMenuItem opcionCerrar;
    private javax.swing.JMenuItem opcionCopiar;
    private javax.swing.JMenuItem opcionCopiarTodo;
    private javax.swing.JMenuItem opcionPegar;
    private javax.swing.JRadioButtonMenuItem opcionRBMetal;
    private javax.swing.JRadioButtonMenuItem opcionRBNimbus;
    private javax.swing.JRadioButtonMenuItem opcionRBPorDefecto;
    private javax.swing.JRadioButtonMenuItem opcionRBWindows;
    private javax.swing.JRadioButtonMenuItem opcionRBWindowsClassic;
    private javax.swing.JPopupMenu popupEditar;
    // End of variables declaration//GEN-END:variables

    public void LimpiarTexto() {
        Texto.removeAll();
    }

    public void CargarFichero(String fichero) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fichero));
            String aux = "";
            DefaultListModel ModeloLista = new DefaultListModel();
            Texto.setModel(ModeloLista);
            int i = 0;
            while ((aux = br.readLine()) != null) {
                // builder.append(aux);
                ModeloLista.add(i, aux);
                i++;
            }
            //     Texto.read(br, null);
        } catch (FileNotFoundException ex) {
            Utilidades.escribeLog("Fichero de log " + fichero + " no encontrado - Error " + ex.getMessage());
        } catch (IOException ex) {
            Utilidades.escribeLog("Error al leer el Fichero de log " + fichero + " - Error " + ex.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Utilidades.escribeLog("Error cerrar el Fichero " + fichero + " - Error " + ex.getMessage());
                }
            }
        }
    }

    public Boolean CargarLista(List<String> lista) {
        if (lista == null) {
            return false;
        }
        Iterator<String> nombreIterator = lista.iterator();
        int i = 0;
        while (nombreIterator.hasNext()) {
            String elemento = nombreIterator.next();
            DefaultListModel Modelo = new DefaultListModel();
            Texto.setModel(Modelo);
            try {
                Modelo.add(i, elemento);
                i++;
            } catch (Exception ex) {
                Utilidades.escribeLog("Error el escribir texto - Error " + ex.getMessage());
            }
        }
        return true;
    }

}
