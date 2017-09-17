package es.documentum.pantalla;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;
import es.documentum.utilidades.Utilidades;
import es.documentum.utilidades.UtilidadesDocumentum;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultCaret;

public class PantallaTareasIndexador extends javax.swing.JFrame {

    boolean botonderecho;
    String componente;
    UtilidadesDocumentum utilDocu = new UtilidadesDocumentum();
    IDfSession sesion;
    String docbase;
    String docbroker = utilDocu.DameDocbroker();

    public IDfSession getSesion() {
        return sesion;
    }

    public void setSesion(IDfSession sesion) {
        this.sesion = sesion;
    }

    public String getDocbase() {
        return docbase;
    }

    public void setDocbase(String docbase) {
        this.docbase = docbase;
    }

    public String getDocbroker() {
        return docbroker;
    }

    public void setDocbroker(String docbroker) {
        this.docbroker = docbroker;
    }

    public PantallaTareasIndexador() {
        initComponents();
        inicializar();
        setVisible(true);
    }

    private void inicializar() {
        try {
            setIconImage(new ImageIcon(getLogo()).getImage());
        } catch (NullPointerException e) {
            Utilidades.escribeLog("\nError cargando el Logo " + e.getMessage() + "\n");
        }
        setLocationRelativeTo(null);
        DefaultCaret caret = (DefaultCaret) textoLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    protected static Image getLogo() {
        java.net.URL imgURL = PantallaTareasIndexador.class.getClassLoader().getResource("es/documentum/imagenes/DCTM_32.png");

        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupEditar = new javax.swing.JPopupMenu();
        opcionCopiar = new javax.swing.JMenuItem();
        opcionPegar = new javax.swing.JMenuItem();
        opcionActualizar = new javax.swing.JMenuItem();
        opcionRelanzar = new javax.swing.JMenuItem();
        panelResultado = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        textoLog = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        botonRecargar = new javax.swing.JButton();
        MenuOpciones = new javax.swing.JMenuBar();
        OpcionRefresco = new javax.swing.JMenu();
        OpcionSalir = new javax.swing.JMenuItem();

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

        opcionActualizar.setText("Actualizar información de fila");
        opcionActualizar.setAutoscrolls(true);
        opcionActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionActualizarActionPerformed(evt);
            }
        });
        popupEditar.add(opcionActualizar);

        opcionRelanzar.setText("Relanzar tareas fallidas");
        opcionRelanzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRelanzarActionPerformed(evt);
            }
        });
        popupEditar.add(opcionRelanzar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Indexadores de Documentum");

        panelResultado.setAutoscrolls(true);
        panelResultado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelResultadoMousePressed(evt);
            }
        });

        tablaResultados.setAutoCreateRowSorter(true);
        tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaResultados.setColumnSelectionAllowed(true);
        tablaResultados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaResultadosMousePressed(evt);
            }
        });
        panelResultado.setViewportView(tablaResultados);

        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(32767, 130));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(166, 72));

        textoLog.setEditable(false);
        textoLog.setColumns(20);
        textoLog.setLineWrap(true);
        textoLog.setRows(2);
        textoLog.setWrapStyleWord(true);
        textoLog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                textoLogMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(textoLog);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Log de salida");

        botonRecargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/update.gif"))); // NOI18N
        botonRecargar.setText(" Recargar datos");
        botonRecargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRecargarActionPerformed(evt);
            }
        });

        OpcionRefresco.setText("Opciones");

        OpcionSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        OpcionSalir.setText("Salir");
        OpcionSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpcionSalirActionPerformed(evt);
            }
        });
        OpcionRefresco.add(OpcionSalir);

        MenuOpciones.add(OpcionRefresco);

        setJMenuBar(MenuOpciones);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelResultado, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonRecargar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonRecargar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaResultadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaResultadosMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "tablaResultados";
            popupmenu(evt);
        }
    }//GEN-LAST:event_tablaResultadosMousePressed

    private void textoLogMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textoLogMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "textoLog";
            popupmenu(evt);
        }
    }//GEN-LAST:event_textoLogMousePressed

    private void opcionCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarActionPerformed
        if (componente.equals("textoLog")) {
            if (textoLog.getSelectedText() == null) {
                Utilidades.copiarTextoPortapapeles(textoLog.getText());
            } else {
                Utilidades.copiarTextoPortapapeles(textoLog.getSelectedText());
            }
        }

        if (componente.equals("tablaResultados")) {
            if (tablaResultados.getModel().getValueAt(tablaResultados.getSelectedRow(), 1) != null) {
                Utilidades.copiarTextoPortapapeles(tablaResultados.getModel().getValueAt(tablaResultados.convertRowIndexToModel(tablaResultados.getSelectedRow()), tablaResultados.getSelectedColumn()).toString());
            }
        }
    }//GEN-LAST:event_opcionCopiarActionPerformed

    private void opcionPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionPegarActionPerformed

    }//GEN-LAST:event_opcionPegarActionPerformed

    private void panelResultadoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelResultadoMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panelResultadoMousePressed

    private void opcionRelanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRelanzarActionPerformed
        ArrayList salida = utilDocu.relanzarTareasFallidasIndexador(sesion);
        if (salida.size() > 0) {
            textoLog.append("Relanzando tareas fallidas del indexador de " + docbase + "\n");
        }
        for (int i = 0; i < salida.size(); i++) {
            textoLog.append(salida.get(i).toString() + "\n");
        }
        int fila = tablaResultados.getSelectedRow();
        recargarFila(fila, docbase, docbroker);
    }//GEN-LAST:event_opcionRelanzarActionPerformed

    private void opcionActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionActualizarActionPerformed
        docbase = tablaResultados.getModel().getValueAt(tablaResultados.convertRowIndexToModel(tablaResultados.getSelectedRow()), 1).toString();
        docbroker = tablaResultados.getModel().getValueAt(tablaResultados.convertRowIndexToModel(tablaResultados.getSelectedRow()), 0).toString();
        int fila = tablaResultados.getSelectedRow();
        recargarFila(fila, docbase, docbroker);
    }//GEN-LAST:event_opcionActualizarActionPerformed

    private void botonRecargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRecargarActionPerformed
        new Thread() {
            @Override
            public void run() {
                cargarTabla();
            }
        }.start();
        System.gc();
    }//GEN-LAST:event_botonRecargarActionPerformed

    private void OpcionSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpcionSalirActionPerformed
        if (sesion.isConnected()) {
            try {
                sesion.disconnect();
            } catch (DfException ex) {
                Utilidades.escribeLog("Error al cerra sesión - PantallaTareasIndexador - Error: " + ex.getMessage());
            }
        }
        this.dispose();
    }//GEN-LAST:event_OpcionSalirActionPerformed

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
            if (evt.getSource() == textoLog) {
                //          popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
                opcionPegar.setVisible(false);
                opcionRelanzar.setVisible(false);
                opcionActualizar.setVisible(false);
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
            }
            if (evt.getSource() == tablaResultados) {
                opcionPegar.setVisible(false);
                opcionRelanzar.setVisible(true);
                opcionActualizar.setVisible(true);
                JTable source = (JTable) evt.getSource();
                int row = source.rowAtPoint(evt.getPoint());
                docbroker = tablaResultados.getModel().getValueAt(row, 0).toString();
                docbase = tablaResultados.getModel().getValueAt(row, 1).toString();
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
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

 /*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaTareasIndexador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaTareasIndexador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaTareasIndexador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaTareasIndexador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
         */
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PantallaTareasIndexador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar MenuOpciones;
    private javax.swing.JMenu OpcionRefresco;
    private javax.swing.JMenuItem OpcionSalir;
    private javax.swing.JButton botonRecargar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem opcionActualizar;
    private javax.swing.JMenuItem opcionCopiar;
    private javax.swing.JMenuItem opcionPegar;
    private javax.swing.JMenuItem opcionRelanzar;
    private javax.swing.JScrollPane panelResultado;
    private javax.swing.JPopupMenu popupEditar;
    private javax.swing.JTable tablaResultados;
    private javax.swing.JTextArea textoLog;
    // End of variables declaration//GEN-END:variables

    private void recargarFila(int fila, String repo, String servidor) {
        try {
            String tareas = null;
            IDfCollection col = utilDocu.ejecutarDql("select '" + servidor + "' as Servidor,'" + repo + "' as Repositorio, count(*)  from dmi_queue_item where name = 'dm_fulltext_index_user' and task_state='failed'", sesion);
            if (col == null) {
                return;
            }

            while (col.next()) {
                tareas = col.getValue(col.getAttr(2).getName()).toString();
            }

            String comandoAPI = "apply,c,,FTINDEX_AGENT_ADMIN,NAME,S," + utilDocu.dameFTIndex(sesion)
                    + ",AGENT_INSTANCE_NAME,S," + utilDocu.dameIndexAgent(sesion) + ",ACTION,S,status";
            String resul = utilDocu.ejecutarAPI(comandoAPI, "", sesion);
            comandoAPI = "next,c," + resul;
            //   String estado = utilDocu.ejecutarAPI(comandoAPI, "", sesion);
            comandoAPI = "get,c," + resul + ",status";
            String estado = utilDocu.ejecutarAPI(comandoAPI, "", sesion);

            switch (estado) {
                case "100":
                    estado = "Parado";
                    break;
                case "200":
                    estado = "Sin respuesta";
                    break;
                default:
                    estado = "En ejecución";
                    break;
            }

            tablaResultados.getModel().setValueAt(tareas, fila, 3);
            tablaResultados.getModel().setValueAt(estado, fila, 4);
            tablaResultados.validate();
        } catch (DfException ex) {
            textoLog.append("Error al actualizar datos de " + repo + " - Error: " + ex.getMessage() + "\n");
        }
    }

    public void cargarTabla() {
        DefaultTableModel modeloLotes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        //      tablaResultados.setModel(modeloLotes);
        ArrayList filas = new ArrayList();
        botonRecargar.setEnabled(false);

        try {
            String sentencia = "select '" + docbroker + "' as Servidor,'" + docbase + "' as Repositorio, count(*)  from dmi_queue_item where name = 'dm_fulltext_index_user' and task_state='failed'";
            IDfCollection col = utilDocu.ejecutarDql(sentencia, sesion);
            textoLog.append("Conectando con ... servidor: " + docbroker + " - repositorio: " + docbase + "\n");
            textoLog.append(sentencia + "\n");
            try {
                while (col.next()) {
                    String docbaseid = sesion.getDocbaseId();
                    String server = col.getValue(col.getAttr(0).getName()).toString();
                    String repositorio = col.getValue(col.getAttr(1).getName()).toString();
                    String comandoAPI = "apply,c,,FTINDEX_AGENT_ADMIN,NAME,S," + utilDocu.dameFTIndex(sesion)
                            + ",AGENT_INSTANCE_NAME,S," + utilDocu.dameIndexAgent(sesion) + ",ACTION,S,status";
                    String resul = utilDocu.ejecutarAPI(comandoAPI, "", sesion);
                    comandoAPI = "next,c," + resul;
                    String estado = utilDocu.ejecutarAPI(comandoAPI, "", sesion);
                    comandoAPI = "get,c," + resul + ",status";
                    estado = utilDocu.ejecutarAPI(comandoAPI, "", sesion);

                    if (estado.equals("100")) {
                        estado = "Parado";
                    } else if (estado.equals("200")) {
                        estado = "Sin respuesta";
                    } else {
                        estado = "En ejecución";
                    }

                    String tareas = col.getValue(col.getAttr(2).getName()).toString();
                    System.out.println(server + " <--> " + repositorio + " --> " + tareas + "  - Estado: " + estado);
                    textoLog.append("Repositorio: " + repositorio + " - Número de tareas: " + tareas + "  - Estado: " + estado + "\n");
                    ArrayList columnas = new ArrayList();
                    columnas.add(server);
                    columnas.add(repositorio);
                    columnas.add(docbaseid + "  (" + Integer.toHexString(Integer.parseInt(docbaseid)) + ")");
                    columnas.add(tareas);
                    columnas.add(estado);
                    filas.add(columnas);
                    textoLog.validate();
                }
                col.close();
                //   sesion.disconnect();
            } catch (DfException Ex) {
                textoLog.append("Error al recoger datos de la DQL - " + Ex.getMessage() + "\n");
            } finally {
                if (col != null) {
                    try {
                        col.close();
                    } catch (DfException ex) {
                        textoLog.append("Error al cerrar colection de la DQL - " + ex.getMessage() + "\n");
                    }
                }
            }

        } catch (Exception e) {
            textoLog.append("Error - " + e.getMessage() + "\n");
        }

        int cont = filas.size();
        String[] cabecera = {"Servidor", "Repositorio", "Docbase ID", "Tareas Fallidas", "Estado"};
        String[][] datos = new String[cont][5];

        for (int n = 0; n < filas.size(); n++) {
            ArrayList columnas;
            columnas = (ArrayList) filas.get(n);
            datos[n][0] = columnas.get(0).toString();
            datos[n][1] = columnas.get(1).toString();
            datos[n][2] = columnas.get(2).toString();
            datos[n][3] = columnas.get(3).toString();
            datos[n][4] = columnas.get(4).toString();

        }

        try {
            if (filas.size() <= 0) {
                textoLog.setText("0 Registro(s) encontrado(s) ");
                return;
            }

            if (datos.length > 0) {
                modeloLotes = new DefaultTableModel(datos, cabecera) {
                    @Override
                    public boolean isCellEditable(int fila, int columna) {
                        return false;
                    }
                };
            }

            modeloLotes.setRowCount(cont);
            tablaResultados.setModel(modeloLotes);

            Font font = tablaResultados.getFont();
            FontMetrics fm = getFontMetrics(font);
            char c = "B".charAt(0);
            int tampixel = fm.charWidth(c);

            int rows = modeloLotes.getRowCount();
            for (int i = 0; i < modeloLotes.getColumnCount(); i++) {
                TableColumn columna = tablaResultados.getColumnModel().getColumn(i);
            }
            tablaResultados.doLayout();
        } catch (Exception ex) {
            textoLog.append("Error - " + ex.getMessage() + "\n");
        }
        botonRecargar.setEnabled(true);
        this.setTitle("Tareas indexador de " + docbase + "  -  Ultima ejecución " + Utilidades.today() + " " + Utilidades.now());
    }

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(PantallaTareasIndexador.class);

}
