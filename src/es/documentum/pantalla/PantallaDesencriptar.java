package es.documentum.pantalla;

import com.documentum.fc.impl.util.RegistryPasswordUtils;
import es.documentum.utilidades.Utilidades;
import java.awt.Image;
import javax.swing.ImageIcon;

public class PantallaDesencriptar extends javax.swing.JDialog {

    String accion = "encriptar";
    public static PantallaDocumentum ventanapadre = null;

    public PantallaDesencriptar(PantallaDocumentum parent, boolean modal) {
        super(parent, modal);
        ventanapadre = parent;
        initComponents();
        try {
            setIconImage(new ImageIcon(getLogo()).getImage());
        } catch (NullPointerException e) {
            Utilidades.escribeLog("\nError cargando el Logo " + e.getMessage() + "\n");
        }
        setLocationRelativeTo(ventanapadre);
    }

    protected static Image getLogo() {
        //   java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/documentum_logo_mini.gif");
        java.net.URL imgURL = PantallaDesencriptar.class.getClassLoader().getResource("es/documentum/imagenes/encryption.png");

        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            return null;
        }
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
        opcionPegar = new javax.swing.JMenuItem();
        botonEncriptar = new javax.swing.JButton();
        etiquetaPlano = new javax.swing.JLabel();
        textoPlano = new javax.swing.JTextField();
        etiquetaEncriptado = new javax.swing.JLabel();
        textoEncriptado = new javax.swing.JTextField();
        botonSalir = new javax.swing.JButton();
        EtiquetaPanel = new javax.swing.JLabel();
        botonDesencriptar = new javax.swing.JButton();

        opcionPegar.setText("Pegar");
        opcionPegar.setToolTipText("Pegar");
        opcionPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionPegarActionPerformed(evt);
            }
        });
        popupmenu.add(opcionPegar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Encriptar / Desencriptar Texto");
        setResizable(false);

        botonEncriptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/bloqueado.gif"))); // NOI18N
        botonEncriptar.setMnemonic('A');
        botonEncriptar.setText("Encriptar");
        botonEncriptar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonEncriptar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        botonEncriptar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonEncriptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEncriptarActionPerformed(evt);
            }
        });

        etiquetaPlano.setText("Texto");
        etiquetaPlano.setMaximumSize(new java.awt.Dimension(41, 22));
        etiquetaPlano.setMinimumSize(new java.awt.Dimension(41, 22));
        etiquetaPlano.setPreferredSize(new java.awt.Dimension(41, 22));

        textoPlano.setMinimumSize(new java.awt.Dimension(6, 24));
        textoPlano.setPreferredSize(new java.awt.Dimension(6, 24));
        textoPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoPlanoActionPerformed(evt);
            }
        });

        etiquetaEncriptado.setText("Texto encriptado");

        textoEncriptado.setMinimumSize(new java.awt.Dimension(6, 24));
        textoEncriptado.setPreferredSize(new java.awt.Dimension(6, 24));
        textoEncriptado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoEncriptadoActionPerformed(evt);
            }
        });

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

        EtiquetaPanel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        EtiquetaPanel.setForeground(new java.awt.Color(0, 0, 153));
        EtiquetaPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        EtiquetaPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EtiquetaPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                EtiquetaPanelMouseEntered(evt);
            }
        });

        botonDesencriptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/documento-log.png"))); // NOI18N
        botonDesencriptar.setMnemonic('A');
        botonDesencriptar.setText("Desencriptar");
        botonDesencriptar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonDesencriptar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        botonDesencriptar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonDesencriptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDesencriptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(EtiquetaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(etiquetaEncriptado, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 344, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(etiquetaPlano, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textoPlano, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botonEncriptar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(84, 84, 84)
                                .addComponent(botonDesencriptar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textoEncriptado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(24, 24, 24))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(etiquetaPlano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(textoPlano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(etiquetaEncriptado, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(textoEncriptado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonEncriptar)
                    .addComponent(botonSalir)
                    .addComponent(botonDesencriptar))
                .addGap(28, 28, 28)
                .addComponent(EtiquetaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {textoEncriptado, textoPlano});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {botonEncriptar, botonSalir});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonEncriptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEncriptarActionPerformed
        accion = "encriptar";
        if (ComprobarValores()) {
            try {
                textoEncriptado.setText(RegistryPasswordUtils.encrypt(textoPlano.getText()));
            } catch (Exception ex) {

            }
        }
    }//GEN-LAST:event_botonEncriptarActionPerformed

    private void textoPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoPlanoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoPlanoActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void EtiquetaPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EtiquetaPanelMouseEntered
        EtiquetaPanel.setToolTipText(EtiquetaPanel.getText());
    }//GEN-LAST:event_EtiquetaPanelMouseEntered

    private void opcionPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionPegarActionPerformed
        if (evt.getSource().toString().equals("textoEncriptado")) {
            textoEncriptado.setText(Utilidades.pegarTextoPortapapeles());
        }
        if (evt.getSource().toString().equals("textoPlano")) {
            textoPlano.setText(Utilidades.pegarTextoPortapapeles());
        }
    }//GEN-LAST:event_opcionPegarActionPerformed

    private void EtiquetaPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EtiquetaPanelMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
            Utilidades.copiarTextoPortapapeles(EtiquetaPanel.getText());
        }
    }//GEN-LAST:event_EtiquetaPanelMouseClicked

    private void textoEncriptadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoEncriptadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoEncriptadoActionPerformed

    private void botonDesencriptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDesencriptarActionPerformed
        accion = "desencriptar";
        if (ComprobarValores()) {
            try {
                textoPlano.setText(RegistryPasswordUtils.decrypt(textoEncriptado.getText()));
            } catch (Exception ex) {

            }
        }
    }//GEN-LAST:event_botonDesencriptarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PantallaDesencriptar dialog = new PantallaDesencriptar(ventanapadre, true);
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
    private javax.swing.JButton botonDesencriptar;
    private javax.swing.JButton botonEncriptar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JLabel etiquetaEncriptado;
    private javax.swing.JLabel etiquetaPlano;
    private javax.swing.JMenuItem opcionPegar;
    private javax.swing.JPopupMenu popupmenu;
    public javax.swing.JTextField textoEncriptado;
    public javax.swing.JTextField textoPlano;
    // End of variables declaration//GEN-END:variables

    public void asignariconos() {
    }

    private Boolean ComprobarValores() {
        Boolean resultado = true;
        if (accion.toLowerCase().equals("encriptar")) {
            if (textoPlano.getText().isEmpty()) {
                EtiquetaPanel.setText("Debe indicar el texto a encriptar");
                textoPlano.requestFocus();
                return false;
            }
        }
        if (accion.toLowerCase().equals("desencriptar")) {
            if (textoEncriptado.getText().isEmpty()) {
                EtiquetaPanel.setText("Debe indicar el texto a desencriptar");
                textoEncriptado.requestFocus();
                return false;
            }
        }
        EtiquetaPanel.setText("");
        return resultado;
    }
}
