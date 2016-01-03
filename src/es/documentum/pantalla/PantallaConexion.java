package es.documentum.pantalla;

import com.documentum.fc.common.DfException;
import com.documentum.fc.impl.util.RegistryPasswordUtils;
import es.documentum.utilidades.Utilidades;
import es.documentum.utilidades.UtilidadesDocumentum;
import java.awt.Color;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author julian
 */
public class PantallaConexion extends javax.swing.JDialog {

    public static String valor = "";
    public static String docbroker = "";
    public static String repositorio = "";
    public static String versiondocumentum = "";
    public static String idrepositorio = "";

    public static String puerto = "";
    public static String usuario = "";
    private String dirdfc = "";
    private Utilidades util = new Utilidades();
    public static PantallaDocumentum ventanapadre = null;
    Color colornoconex = new Color(255, 200, 200);
    Color colorconex = new Color(200, 255, 200);
    public Boolean conexionOK = false;

    public static String getValor() {
        return valor;
    }

    public static String getDocbroker() {
        return docbroker;
    }

    public static String getRepositorio() {
        return repositorio;
    }

    public static String getVersiondocumentum() {
        return versiondocumentum;
    }

    public static String getPuerto() {
        return puerto;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static void setValor(String valor) {
        PantallaConexion.valor = valor;
    }

    public static String getIdrepositorio() {
        return idrepositorio;
    }

    public static void setIdrepositorio(String idrepositorio) {
        PantallaConexion.idrepositorio = idrepositorio;
    }

    public PantallaConexion(PantallaDocumentum parent, boolean modal) {
        super(parent, modal);
        ventanapadre = parent;
        dirdfc = ventanapadre.dirdfc;
        initComponents();
        setTitle("Conexión a Documentum");
        cargarLista();
        setLocationRelativeTo(ventanapadre);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupmenu = new javax.swing.JPopupMenu();
        opcionBorrar = new javax.swing.JMenuItem();
        PanelSeleccion = new javax.swing.JScrollPane();
        ListaSeleccion = new javax.swing.JList();
        botonGuardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textoDocbroker = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        textoPuerto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        textoRepositorio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        textoUsuario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        textoPassword = new javax.swing.JPasswordField();
        botonSalir = new javax.swing.JButton();
        botonSelecionar = new javax.swing.JButton();
        botonTestConex = new javax.swing.JButton();
        EtiquetaPanel = new javax.swing.JLabel();

        opcionBorrar.setText("Borrar conexión");
        opcionBorrar.setToolTipText("Borra la conexión seleccionada");
        opcionBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionBorrarActionPerformed(evt);
            }
        });
        popupmenu.add(opcionBorrar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        ListaSeleccion.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ListaSeleccion.setName("ListaSeleccion"); // NOI18N
        ListaSeleccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListaSeleccionMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ListaSeleccionMousePressed(evt);
            }
        });
        PanelSeleccion.setViewportView(ListaSeleccion);
        ListaSeleccion.getAccessibleContext().setAccessibleName("ListaSeleccion");

        botonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/guardar.png"))); // NOI18N
        botonGuardar.setMnemonic('A');
        botonGuardar.setText("Guardar");
        botonGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonGuardar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        botonGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });

        jLabel1.setText("Docbroker");
        jLabel1.setMaximumSize(new java.awt.Dimension(41, 22));
        jLabel1.setMinimumSize(new java.awt.Dimension(41, 22));
        jLabel1.setPreferredSize(new java.awt.Dimension(41, 22));

        textoDocbroker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoDocbrokerActionPerformed(evt);
            }
        });

        jLabel2.setText("Puerto");

        textoPuerto.setText("1489");

        jLabel3.setText("Repositorio");

        jLabel4.setText("Usuario");

        jLabel5.setText("Password");
        jLabel5.setMaximumSize(new java.awt.Dimension(46, 21));
        jLabel5.setMinimumSize(new java.awt.Dimension(46, 21));
        jLabel5.setPreferredSize(new java.awt.Dimension(46, 21));

        textoPassword.setPreferredSize(new java.awt.Dimension(111, 22));

        botonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        botonSalir.setText("Salir");
        botonSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSalir.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        botonSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        botonSelecionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/seleccionar.png"))); // NOI18N
        botonSelecionar.setText("Seleccionar");
        botonSelecionar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSelecionar.setMaximumSize(new java.awt.Dimension(101, 43));
        botonSelecionar.setMinimumSize(new java.awt.Dimension(101, 43));
        botonSelecionar.setPreferredSize(new java.awt.Dimension(101, 43));
        botonSelecionar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        botonSelecionar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSelecionarActionPerformed(evt);
            }
        });

        botonTestConex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/conectar_peq.png"))); // NOI18N
        botonTestConex.setMnemonic('A');
        botonTestConex.setText("Test Conexión");
        botonTestConex.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonTestConex.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        botonTestConex.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonTestConex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTestConexActionPerformed(evt);
            }
        });

        EtiquetaPanel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        EtiquetaPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        EtiquetaPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EtiquetaPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                EtiquetaPanelMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(botonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(botonTestConex, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(botonSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PanelSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoDocbroker)
                            .addComponent(textoPuerto)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoRepositorio, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
                        .addGap(51, 51, 51))))
            .addGroup(layout.createSequentialGroup()
                .addComponent(EtiquetaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                .addGap(2, 2, 2))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(textoDocbroker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textoPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textoRepositorio, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textoPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PanelSeleccion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonGuardar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonSalir, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonTestConex))
                .addGap(18, 18, 18)
                .addComponent(EtiquetaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {textoDocbroker, textoPuerto, textoRepositorio});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {botonGuardar, botonSalir, botonSelecionar, botonTestConex});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed
        String directorio = dirdfc + "repos" + util.separador() + textoDocbroker.getText() + util.separador();
        util.crearDirectorio(directorio);
        util.copiarFichero(dirdfc + "dfc.properties", directorio + "dfc.properties");
        Properties prop = util.leerPropeties(dirdfc + "dfc.properties");
        prop.setProperty("dfc.docbroker.host[0]", textoDocbroker.getText());
        prop.setProperty("dfc.docbroker.port[0]", textoPuerto.getText());
        prop.setProperty("usuario", textoUsuario.getText());
        try {
            prop.setProperty("password", RegistryPasswordUtils.encrypt(new String(textoPassword.getPassword())));
        } catch (DfException ex) {
            Logger.getLogger(PantallaConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        prop.setProperty("repositorio", textoRepositorio.getText());
        util.escribirPropeties(directorio + "dfc.properties", prop);

        int numelem = ListaSeleccion.getModel().getSize();
        int pos = 0;

        while (pos < numelem) {
            if (ListaSeleccion.getModel().getElementAt(pos).equals(textoDocbroker.getText().toLowerCase())) {
                break;
            }
            pos++;
        }
        if (pos >= numelem) {
            DefaultListModel ModeloLista = (DefaultListModel) ListaSeleccion.getModel();
            ModeloLista.addElement(textoDocbroker.getText().toLowerCase());
            ListaSeleccion.setModel(ModeloLista);
            ListaSeleccion.setSelectedIndex(pos);
        }

    }//GEN-LAST:event_botonGuardarActionPerformed

    private void textoDocbrokerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoDocbrokerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoDocbrokerActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        valor = "SALIR";
        this.dispose();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void botonSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSelecionarActionPerformed
        if (conexionOK) {
            SeleccionarConexion();
            this.dispose();
        } else {
            EtiquetaPanel.setText("Debe confirma que la conexión es correcta. Use el botón \"Test Conexión\"");
            botonTestConex.requestFocus();
        }
    }//GEN-LAST:event_botonSelecionarActionPerformed

    private void ListaSeleccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaSeleccionMouseClicked
        if (ListaSeleccion.getModel().getSize() > 0) {
            if (ListaSeleccion.getSelectedIndex() >= 0) {
                String directoriodfc = ListaSeleccion.getModel().getElementAt(ListaSeleccion.getSelectedIndex()).toString();
                String directorio = dirdfc + "repos" + util.separador() + directoriodfc + util.separador();
                Properties prop = util.leerPropeties(directorio + "dfc.properties");
                textoDocbroker.setText(prop.getProperty("dfc.docbroker.host[0]"));
                textoPuerto.setText(prop.getProperty("dfc.docbroker.port[0]"));
                textoRepositorio.setText(prop.getProperty("repositorio"));
                textoUsuario.setText(prop.getProperty("usuario"));
                try {
                    textoPassword.setText(RegistryPasswordUtils.decrypt(prop.getProperty("password")));
                } catch (DfException ex) {
                    Logger.getLogger(PantallaConexion.class.getName()).log(Level.SEVERE, null, ex);
                }
                textoDocbroker.requestFocus();
                this.repaint();
            }
        }
    }//GEN-LAST:event_ListaSeleccionMouseClicked

    private void botonTestConexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTestConexActionPerformed
        if (ComprobarValores()) {
            botonTestConex.setEnabled(false);
            EtiquetaPanel.setText("Estableciendo Conexión con Documentum ...");
            Properties prop = util.leerPropeties(dirdfc + "dfc.properties");
            prop.setProperty("dfc.docbroker.host[0]", textoDocbroker.getText());
            prop.setProperty("dfc.docbroker.port[0]", textoPuerto.getText());
            prop.setProperty("usuario", textoUsuario.getText());
            prop.setProperty("password", new String(textoPassword.getPassword()));
            prop.setProperty("repositorio", textoRepositorio.getText());
            util.escribirPropeties(dirdfc + "dfc.properties", prop);
            UtilidadesDocumentum utildocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                botonTestConex.setEnabled(true);
            }
            if (utildocum.conectarDocumentum() == null) {
                botonTestConex.setBackground(colornoconex);
                conexionOK = false;
                EtiquetaPanel.setText(utildocum.dameError());
            } else {
                botonTestConex.setBackground(colorconex);
                conexionOK = true;
                EtiquetaPanel.setText("Conexión correcta con Documentum");
                versiondocumentum = utildocum.DameVersionDocumentum();
                idrepositorio = utildocum.DameIdRepositorio();
                docbroker = textoDocbroker.getText();
                repositorio = textoRepositorio.getText();
                puerto = textoPuerto.getText();
                usuario = textoUsuario.getText();
            }
            botonTestConex.setEnabled(true);
        }
    }//GEN-LAST:event_botonTestConexActionPerformed

    private void EtiquetaPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EtiquetaPanelMouseEntered
        EtiquetaPanel.setToolTipText(EtiquetaPanel.getText());
    }//GEN-LAST:event_EtiquetaPanelMouseEntered

    private void ListaSeleccionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaSeleccionMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3 && ListaSeleccion.getModel().getSize() > 0) {
            popupmenu.show(evt.getComponent(), evt.getX(), evt.getY());;
        }
    }//GEN-LAST:event_ListaSeleccionMousePressed

    private void opcionBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionBorrarActionPerformed
        if (!ListaSeleccion.isSelectionEmpty()) {
            String directorio = ListaSeleccion.getModel().getElementAt(ListaSeleccion.getSelectedIndex()).toString();
            System.out.println(dirdfc + "repos" + util.separador() + directorio);
            util.borrarDirectorio(dirdfc + "repos" + util.separador() + directorio);
            ListaSeleccion.removeAll();
            cargarLista();
        }
    }//GEN-LAST:event_opcionBorrarActionPerformed

    private void EtiquetaPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EtiquetaPanelMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
            Utilidades.copiarTextoPortapapeles(EtiquetaPanel.getText());
        }
    }//GEN-LAST:event_EtiquetaPanelMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PantallaConexion dialog = new PantallaConexion(ventanapadre, true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setLocationRelativeTo(ventanapadre);
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EtiquetaPanel;
    private javax.swing.JList ListaSeleccion;
    private javax.swing.JScrollPane PanelSeleccion;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JButton botonSelecionar;
    private javax.swing.JButton botonTestConex;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem opcionBorrar;
    private javax.swing.JPopupMenu popupmenu;
    private javax.swing.JTextField textoDocbroker;
    private javax.swing.JPasswordField textoPassword;
    private javax.swing.JTextField textoPuerto;
    private javax.swing.JTextField textoRepositorio;
    private javax.swing.JTextField textoUsuario;
    // End of variables declaration//GEN-END:variables

    public void asignariconos() {
    }

    private void cargarLista() {
        File dirrepos = new File(dirdfc + "repos" + util.separador());
        String[] listadir = dirrepos.list();
        DefaultListModel ModeloLista = new DefaultListModel();
        if (listadir == null) {
            ListaSeleccion.setModel(ModeloLista);
            return;
        }

        if (listadir.length > 0) {
            for (int i = 0; i < listadir.length; i++) {
                ModeloLista.add(i, listadir[i]);
            }

        }
        ListaSeleccion.setModel(ModeloLista);
    }

    private void SeleccionarConexion() {
        String directorio = dirdfc;
        if (ListaSeleccion.getModel().getSize() > 0) {
            if (ListaSeleccion.getSelectedIndex() > 0) {
                directorio = directorio + "repos" + util.separador() + ListaSeleccion.getModel().getElementAt(ListaSeleccion.getSelectedIndex()) + util.separador();
            }
        }
        Properties prop = util.leerPropeties(directorio + "dfc.properties");
        prop.setProperty("dfc.docbroker.host[0]", textoDocbroker.getText());
        prop.setProperty("dfc.docbroker.port[0]", textoPuerto.getText());
        prop.setProperty("repositorio", textoRepositorio.getText());
        prop.setProperty("usuario", textoUsuario.getText());
        prop.setProperty("password", new String(textoPassword.getPassword()));
        util.escribirPropeties(dirdfc + "dfc.properties", prop);
        valor = "SELECION";
        docbroker = textoDocbroker.getText();
        repositorio = textoRepositorio.getText();
    }

    private Boolean ComprobarValores() {
        Boolean resultado = true;
        if (textoDocbroker.getText().isEmpty()) {
            EtiquetaPanel.setText("Debe dar un valor al Docbroker");
            textoDocbroker.requestFocus();
            return false;
        }
        if (textoPuerto.getText().isEmpty()) {
            EtiquetaPanel.setText("Debe dar un valor al Puerto");
            textoPuerto.requestFocus();
            return false;
        }
        if (textoRepositorio.getText().isEmpty()) {
            EtiquetaPanel.setText("Debe dar un valor al Repositorio");
            textoRepositorio.requestFocus();
            return false;
        }
        if (textoUsuario.getText().isEmpty()) {
            EtiquetaPanel.setText("Debe indicar el Usuario");
            textoUsuario.requestFocus();
            return false;
        }
        if (new String(textoPassword.getPassword()).isEmpty()) {
            EtiquetaPanel.setText("Debe indicar la Password del Usuario");
            textoPassword.requestFocus();
            return false;
        }

        return resultado;
    }
}