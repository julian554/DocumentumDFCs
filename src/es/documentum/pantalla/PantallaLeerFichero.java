package es.documentum.pantalla;

import es.documentum.utilidades.Utilidades;
import static es.documentum.utilidades.Utilidades.escribeLog;
import java.awt.Font;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.text.DefaultCaret;


public class PantallaLeerFichero extends javax.swing.JFrame {

    static String tipo = "FRAME";
    java.awt.Frame ventanapadre = null;
    javax.swing.JFrame dialogopadre = null;

    public PantallaLeerFichero(java.awt.Frame parent, boolean modal) {
        ventanapadre = parent;
        initComponents();
        asignariconos();
        try {
            setIconImage(new ImageIcon(getLogo()).getImage());
        } catch (NullPointerException e) {
            Utilidades.escribeLog("\nError cargando el Logo " + e.getMessage() + "\n");
        }
        setLocationRelativeTo(ventanapadre);
        //  setVisible(true);
        repaint();
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }

            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }

        });
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {
        this.requestFocus();
        this.toFront();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Texto = new javax.swing.JTextArea();
        botonCerrar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        Menu = new javax.swing.JMenu();
        opcionCerrar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setName("VentanaLeerFichero"); // NOI18N

        Texto.setEditable(false);
        Texto.setColumns(20);
        Texto.setForeground(new java.awt.Color(0, 0, 102));
        Texto.setLineWrap(true);
        Texto.setRows(5);
        jScrollPane1.setViewportView(Texto);

        botonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir.png"))); // NOI18N
        botonCerrar.setText("Cerrar");
        botonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarActionPerformed(evt);
            }
        });

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

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(693, Short.MAX_VALUE)
                .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(463, Short.MAX_VALUE)
                .addComponent(botonCerrar)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                    .addGap(62, 62, 62)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opcionCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCerrarActionPerformed
        botonCerrarActionPerformed(evt);
    }//GEN-LAST:event_opcionCerrarActionPerformed

    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_botonCerrarActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PantallaLeerFichero frame = new PantallaLeerFichero(new javax.swing.JFrame(), false);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        //  System.exit(0);
                    }
                });

            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Menu;
    private javax.swing.JTextArea Texto;
    public static javax.swing.JButton botonCerrar;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem opcionCerrar;
    // End of variables declaration//GEN-END:variables

    public void LimpiarTexto() {
        Texto.setText("");
    }

    public void CargarFichero(String fichero) {
        DefaultCaret caret = (DefaultCaret) Texto.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        BufferedReader br = null;
        Utilidades util = new Utilidades();
        try {
            //     br = new BufferedReader(new FileReader(fichero));
            Locale locale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
            String idioma=locale.getLanguage()+"-"+locale.getCountry();
            String os = util.so();
            String encoding = System.getProperty("file.encoding");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fichero), (idioma.equals("es-ES") && os.toLowerCase().contains("windows")) ? "8859_1" : encoding));
            Texto.read(br, null);
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
        Texto.setCaretPosition(Texto.getDocument().getLength());
        int fontPoints = 13;
        Texto.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontPoints));
    }

    public Boolean CargarLista(List<String> lista) {
        if (lista == null) {
            return false;
        }
        Iterator<String> nombreIterator = lista.iterator();
        while (nombreIterator.hasNext()) {
            String elemento = nombreIterator.next();
            try {
                Texto.append(elemento + "\n");
            } catch (Exception ex) {
                Utilidades.escribeLog("Error el escribir texto - Error " + ex.getMessage());
            }
        }
        return true;
    }

    public static void asignariconos() {
        java.net.URL imgURL = PantallaLeerFichero.class.getClassLoader().getResource("es/documentum/imagenes/salir.png");
        Icon imgicon = new ImageIcon(imgURL);
        botonCerrar.setIcon(imgicon);
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
