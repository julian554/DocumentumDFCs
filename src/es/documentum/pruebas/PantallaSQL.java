package es.documentum.pruebas;

import es.documentum.Beans.Pistas;
import es.documentum.pantalla.PantallaAyudaLista;
import es.documentum.utilidades.FormatoSql;
import es.documentum.utilidades.Utilidades;
import es.documentum.utilidades.UtilidadesDocumentum;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;

public class PantallaSQL extends javax.swing.JFrame {

    Utilidades util = new Utilidades();
    UtilidadesDocumentum utildocum = new UtilidadesDocumentum();
    PantallaAyudaLista pantallaayuda = new PantallaAyudaLista(this, true);
    int posicionTextoSql = 0;

    public int getPosicionTextoSql() {
        return textoSQL.getCaretPosition();
    }

    public void setPosicionTextoSql(int posicionTextoSql) {
        this.posicionTextoSql = posicionTextoSql;
    }

    public void insertartexto(int posicion, String texto) {
        try {
            textoSQL.getDocument().insertString(posicion, texto, null);
        } catch (BadLocationException ex) {
            Logger.getLogger(PantallaSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PantallaSQL() {
        initComponents();
        this.textoSQL.getCaret().setMagicCaretPosition(new Point(0, 0));
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
     * this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSQL = new javax.swing.JScrollPane();
        textoSQL = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaResultado = new javax.swing.JTable();
        botonConsultar = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textoSQL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textoSQLKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textoSQLKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textoSQLKeyTyped(evt);
            }
        });
        panelSQL.setViewportView(textoSQL);

        tablaResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablaResultado);

        botonConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/ejecutar_peq.png"))); // NOI18N
        botonConsultar.setText("Ejecutar");
        botonConsultar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        botonConsultar.setMaximumSize(new java.awt.Dimension(100, 40));
        botonConsultar.setMinimumSize(new java.awt.Dimension(100, 40));
        botonConsultar.setPreferredSize(new java.awt.Dimension(100, 40));
        botonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarActionPerformed(evt);
            }
        });

        botonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        botonSalir.setText("Salir");
        botonSalir.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        botonSalir.setMaximumSize(new java.awt.Dimension(100, 40));
        botonSalir.setMinimumSize(new java.awt.Dimension(100, 40));
        botonSalir.setPreferredSize(new java.awt.Dimension(100, 40));
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelSQL)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelSQL, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarActionPerformed
        FormatoSql format = new FormatoSql();
        textoSQL.setText(format.format(textoSQL.getText()));
    }//GEN-LAST:event_botonConsultarActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void textoSQLKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoSQLKeyTyped
//        if(evt.getKeyCode() == KeyEvent.VK_SPACE && evt.isControlDown()){ 
//            pantallaayuda.setVisible(true);
//        }
//        
//        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
//            pantallaayuda.dispose();
//        }
    }//GEN-LAST:event_textoSQLKeyTyped

    private void textoSQLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoSQLKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pantallaayuda.dispose();
        }
    }//GEN-LAST:event_textoSQLKeyPressed

    private void textoSQLKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoSQLKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_SPACE && evt.isControlDown()) {
            setPosicionTextoSql(textoSQL.getCaretPosition());
            Caret caret = textoSQL.getCaret();
            Point p = new Point(caret.getMagicCaretPosition());
            p.x += textoSQL.getLocationOnScreen().x;
            p.y += textoSQL.getLocationOnScreen().y + 24;

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Double anchopantalla = screenSize.getWidth();

            if ((p.x + pantallaayuda.getWidth() + 5) > anchopantalla) {
                p.x = anchopantalla.intValue() - pantallaayuda.getWidth() - 5;
            }

            pantallaayuda.setBounds(p.x, p.y, pantallaayuda.getWidth(), pantallaayuda.getHeight());
            pantallaayuda.setAlwaysOnTop(true);
            ArrayList<String> lista = new ArrayList<>();

            ArrayList<Pistas> listapistas = utildocum.leerDatosAyuda("dql-inicio.xml");
            for (int n = 0; n < listapistas.size(); n++) {
                lista.add(listapistas.get(n).getTipo());
            }
//            lista.add("Select");
//            lista.add("Update");
//            lista.add("Delete");
            pantallaayuda.cargarDatosAyuda(lista);
            pantallaayuda.setVisible(true);
//            textoSQL.requestFocus();
        }
    }//GEN-LAST:event_textoSQLKeyReleased

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaSQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaSQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaSQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaSQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaSQL().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonConsultar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane panelSQL;
    private javax.swing.JTable tablaResultado;
    private javax.swing.JTextPane textoSQL;
    // End of variables declaration//GEN-END:variables
}