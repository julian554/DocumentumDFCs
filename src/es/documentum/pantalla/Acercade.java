package es.documentum.pantalla;

import com.documentum.com.IDfClientX;
import es.documentum.utilidades.Utilidades;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author julian.collado
 */
public class Acercade extends javax.swing.JDialog {

    private Properties pro = new Properties();
    private Dimension screenSize = new Dimension();
    public String usuario = "";
    public String repositorio = "";
    public String docbroker = "";
    public String versiondocumentum = "";
    public String puerto = "";
    public String dirdfc = "";
    public String idrepositorio = "";
    public String versiondfcs = "";

    public String getVersiondfcs() {
        return versiondfcs;
    }

    public void setVersiondfcs(String versiondfcs) {
        this.versiondfcs = versiondfcs;
    }

    public String getIdrepositorio() {
        return idrepositorio;
    }

    public void setIdrepositorio(String idrepositorio) {
        this.idrepositorio = idrepositorio;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setRepositorio(String repositorio) {
        this.repositorio = repositorio;
    }

    public void setDocbroker(String docbroker) {
        this.docbroker = docbroker;
    }

    public void setVersiondocumentum(String versiondocumentum) {
        this.versiondocumentum = versiondocumentum;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public void setDirdfc(String dirdfc) {
        this.dirdfc = dirdfc;
    }

    public Acercade(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        PantallaDocumentum docum = (PantallaDocumentum) parent;
        setLocationRelativeTo(parent);
        initComponents();
        try {
            setIconImage(new ImageIcon(getLogo()).getImage());
        } catch (NullPointerException e) {
            Utilidades.escribeLog("\nError cargando el Logo " + e.getMessage() + "\n");
        }
        asignarIcono();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dirdfc = docum.dirdfc;
        repositorio = docum.repositorio;
        versiondocumentum = docum.versiondocumentum;
        idrepositorio = docum.idrepositorio;
        docbroker = docum.docbroker;
        usuario = docum.usuario;
        puerto = docum.puerto;
        versiondfcs = docum.versiondfcs;
        valoresPc();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonAceptar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelVersion = new javax.swing.JLabel();
        labelPC = new javax.swing.JLabel();
        labelUsuario = new javax.swing.JLabel();
        labelRuta = new javax.swing.JLabel();
        labelDisco = new javax.swing.JLabel();
        labelIP = new javax.swing.JLabel();
        labelJava = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        labelSO = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        labelResolucion = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        labelDocbroker = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        labelPuerto = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        labelRepositorio = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        labelVersionRepositorio = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        labelUsuarioDocumentum = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        labelIdRepositorio = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        labelVersionDFCs = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DocumentumDFCs");
        setResizable(false);

        botonAceptar.setMnemonic('A');
        botonAceptar.setText("Aceptar");
        botonAceptar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Nombre de máquina:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Usuario S. O.:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("Ruta de trabajo:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("Espacio libre en disco:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("IP:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setText("Runtime Java:");

        labelVersion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelVersion.setForeground(new java.awt.Color(0, 51, 153));
        labelVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelVersion.setText("DocumentumDFCs v1.0");
        labelVersion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        labelVersion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelVersion.setName("labelVersion"); // NOI18N
        labelVersion.setOpaque(true);

        labelPC.setText(" ");
        labelPC.setName("labelPC"); // NOI18N

        labelUsuario.setText(" ");
        labelUsuario.setName("labelUsuario"); // NOI18N

        labelRuta.setText(" ");
        labelRuta.setName("labelRuta"); // NOI18N

        labelDisco.setText(" ");
        labelDisco.setName("labelDisco"); // NOI18N

        labelIP.setText(" ");
        labelIP.setName("labelIP"); // NOI18N

        labelJava.setText(" ");
        labelJava.setName("LabelIP"); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setText("Sistema Operativo:");

        labelSO.setText(" ");
        labelSO.setName("labelSO"); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setText("Resolución pantalla: ");

        labelResolucion.setText(" ");
        labelResolucion.setName("LabelIP"); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setText("Docbroker: ");

        labelDocbroker.setText(" ");
        labelDocbroker.setName("LabelIP"); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel10.setText("Puerto Docbroker: ");

        labelPuerto.setText(" ");
        labelPuerto.setName("LabelIP"); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel11.setText("Repositorio: ");

        labelRepositorio.setText(" ");
        labelRepositorio.setName("LabelIP"); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel12.setText("Versión Repositorio: ");

        labelVersionRepositorio.setText(" ");
        labelVersionRepositorio.setName("LabelIP"); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel13.setText("Usuario Documentum: ");

        labelUsuarioDocumentum.setText(" ");
        labelUsuarioDocumentum.setName("LabelIP"); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel14.setText("ID Repositorio: ");

        labelIdRepositorio.setText(" ");
        labelIdRepositorio.setName("LabelIP"); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel15.setText("Versión DFCs:");

        labelVersionDFCs.setText(" ");
        labelVersionDFCs.setName("LabelIP"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelDisco, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelSO, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelJava, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelIP, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelPC, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelResolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelDocbroker, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelRepositorio, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelUsuarioDocumentum, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(161, 161, 161)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelVersionRepositorio, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelIdRepositorio, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelVersionDFCs, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(labelVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(274, 274, 274))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {labelDisco, labelIP, labelJava, labelPC, labelResolucion, labelSO, labelUsuario});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(labelVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(labelPC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelIP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelUsuario))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelRuta))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(labelDisco))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(labelSO))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(labelJava))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(labelResolucion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(labelDocbroker))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(labelPuerto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(labelRepositorio))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(labelIdRepositorio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(labelVersionRepositorio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(labelUsuarioDocumentum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(labelVersionDFCs))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {labelDisco, labelIP, labelJava, labelPC, labelResolucion, labelRuta, labelSO, labelUsuario});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_botonAceptarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Utilidades.escribeLog("Error en el main de Acercade " + " - " + ex.getMessage());
        } catch (InstantiationException ex) {
            Utilidades.escribeLog("Error en el main de Acercade " + " - " + ex.getMessage());
        } catch (IllegalAccessException ex) {
            Utilidades.escribeLog("Error en el main de Acercade " + " - " + ex.getMessage());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Utilidades.escribeLog("Error en el main de Acercade " + " - " + ex.getMessage());
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Acercade dialog = new Acercade(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setLocationRelativeTo(dialog.getParent());
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel labelDisco;
    private javax.swing.JLabel labelDocbroker;
    private javax.swing.JLabel labelIP;
    private javax.swing.JLabel labelIdRepositorio;
    private javax.swing.JLabel labelJava;
    private javax.swing.JLabel labelPC;
    private javax.swing.JLabel labelPuerto;
    private javax.swing.JLabel labelRepositorio;
    private javax.swing.JLabel labelResolucion;
    private javax.swing.JLabel labelRuta;
    private javax.swing.JLabel labelSO;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JLabel labelUsuarioDocumentum;
    private javax.swing.JLabel labelVersion;
    private javax.swing.JLabel labelVersionDFCs;
    private javax.swing.JLabel labelVersionRepositorio;
    // End of variables declaration//GEN-END:variables

    private void valoresPc() {
        Utilidades pc = new Utilidades();
        CargarConfiguraciones();
        labelVersion.setText(pro.getProperty("nombre").toString() + "  v. " + pro.getProperty("version").toString());
        labelIP.setText(pc.ip());
        labelPC.setText(pc.nombrePC());

        labelRuta.setText(dirdfc);
        labelUsuario.setText(pc.usuario());
        labelJava.setText(pc.versionJDK() + " - " + pc.versionJavaBits());
        if (pc.so().toLowerCase().contains("windows")) {
            labelSO.setText(pc.so() + " " + pc.soBits());
        } else {
            labelSO.setText(pc.so());
        }
        long disco = (pc.discoLibre(dirdfc) / 1024 / 1024);
        if (disco > 10000) {
            labelDisco.setText(disco / 1024 + " GB");
        } else {
            labelDisco.setText(disco + " MB");
        }
        labelResolucion.setText(screenSize.width + " x " + screenSize.height);
        labelDocbroker.setText(docbroker);
        labelRepositorio.setText(repositorio);
        labelVersionRepositorio.setText(versiondocumentum);
        labelPuerto.setText(puerto);
        labelUsuarioDocumentum.setText(usuario);
        labelIdRepositorio.setText(idrepositorio);
        labelVersionDFCs.setText(versiondfcs);
    }

    public void CargarConfiguraciones() {
        try {
            InputStream in = Acercade.class.getClassLoader().getResourceAsStream("es/documentum/propiedades/version.txt");
            if (in == null) {
                Utilidades.escribeLog("Error al cargar el fichero version.txt");
            }
            pro = new java.util.Properties();
            pro.load(in);
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al cargar el fichero version.txt. Error: " + ex.getMessage());
        }
    }

    private void asignarIcono() {
        java.net.URL imgURL = Acercade.class.getClassLoader().getResource("es/documentum/imagenes/si.png");
        Icon imgicon = new ImageIcon(imgURL);
        this.botonAceptar.setIcon(imgicon);

    }

    protected static Image getLogo() {
        //   java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/documentum_logo_mini.gif");
        java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/DCTM_32.png");

        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            return null;
        }
    }
}