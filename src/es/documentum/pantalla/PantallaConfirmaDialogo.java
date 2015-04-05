package es.documentum.pantalla;


import es.documentum.utilidades.Utilidades;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author julian.collado
 */
public class PantallaConfirmaDialogo extends javax.swing.JDialog {

    public static javax.swing.JDialog ventanapadre = null;
    public static javax.swing.JFrame ventanapadreF = null;
    public String boton = "N";

    public PantallaConfirmaDialogo(javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        ventanapadre = parent;
        initComponents();
        asignariconos();
        setLocationRelativeTo(ventanapadre);
        repaint();
    }

    public PantallaConfirmaDialogo(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        asignariconos();
        setLocationRelativeTo(parent);
        repaint();
    }

    public boolean respuesta() {
        return (boton.equals("S") ? true : false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonSi = new javax.swing.JButton();
        botonNo = new javax.swing.JButton();
        labelIcono = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        etiqueta = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        botonSi.setMnemonic('S');
        botonSi.setText("SI");
        botonSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSiActionPerformed(evt);
            }
        });

        botonNo.setMnemonic('N');
        botonNo.setText("NO");
        botonNo.setMaximumSize(new java.awt.Dimension(47, 32));
        botonNo.setMinimumSize(new java.awt.Dimension(47, 32));
        botonNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNoActionPerformed(evt);
            }
        });

        labelIcono.setMaximumSize(new java.awt.Dimension(32, 32));
        labelIcono.setMinimumSize(new java.awt.Dimension(32, 32));
        labelIcono.setPreferredSize(new java.awt.Dimension(32, 32));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setOpaque(false);

        etiqueta.setEditable(false);
        etiqueta.setColumns(20);
        etiqueta.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        etiqueta.setLineWrap(true);
        etiqueta.setRows(5);
        etiqueta.setWrapStyleWord(true);
        etiqueta.setOpaque(false);
        jScrollPane1.setViewportView(etiqueta);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(botonSi, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonNo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelIcono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {botonNo, botonSi});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(labelIcono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonSi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSiActionPerformed
        boton = "S";
        dispose();
    }//GEN-LAST:event_botonSiActionPerformed

    private void botonNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNoActionPerformed
        boton = "N";
        dispose();
    }//GEN-LAST:event_botonNoActionPerformed

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
            Utilidades.escribeLog("Error en el main de PantallaConfirmaDialogo " + " - " + ex.getMessage());
        } catch (InstantiationException ex) {
            Utilidades.escribeLog("Error en el main de PantallaConfirmaDialogo " + " - " + ex.getMessage());
        } catch (IllegalAccessException ex) {
            Utilidades.escribeLog("Error en el main de PantallaConfirmaDialogo " + " - " + ex.getMessage());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Utilidades.escribeLog("Error en el main de PantallaConfirmaDialogo " + " - " + ex.getMessage());
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PantallaConfirmaDialogo dialog = null;
                if (ventanapadre != null) {
                    dialog = new PantallaConfirmaDialogo(ventanapadre, true);
                    dialog.setLocationRelativeTo(ventanapadre);
                } else {
                    dialog = new PantallaConfirmaDialogo(ventanapadreF, true);
                    dialog.setLocationRelativeTo(ventanapadreF);
                }

                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });

                asignariconos();
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton botonNo;
    private static javax.swing.JButton botonSi;
    public javax.swing.JTextArea etiqueta;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JLabel labelIcono;
    // End of variables declaration//GEN-END:variables

    public static void asignariconos() {
        java.net.URL imgURL = PantallaConfirmaDialogo.class.getClassLoader().getResource("es/documentum/imagenes/si.png");
        Icon imgicon = new ImageIcon(imgURL);
        botonSi.setIcon(imgicon);

        imgURL = PantallaConfirmaDialogo.class.getClassLoader().getResource("es/documentum/imagenes/no.png");
        imgicon = new ImageIcon(imgURL);
        botonNo.setIcon(imgicon);
        imgURL = PantallaConfirmaDialogo.class.getClassLoader().getResource("es/documentum/imagenes/interroga.png");
        imgicon = new ImageIcon(imgURL);
   //     labelIcono = new JLabel(imgicon);
        labelIcono.setIcon(imgicon);
    }
}
