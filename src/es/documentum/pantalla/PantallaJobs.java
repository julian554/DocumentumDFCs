package es.documentum.pantalla;

import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import es.documentum.utilidades.Utilidades;
import es.documentum.utilidades.UtilidadesDocumentum;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 * @author julian
 */
public class PantallaJobs extends javax.swing.JFrame {

    UtilidadesDocumentum utilDocum = new UtilidadesDocumentum();
    Utilidades util = new Utilidades();
    PantallaBarra barradocum = null;
    boolean esadmin = false;
    ArrayList jobs = new ArrayList();
    String dirbase = util.usuarioHome() + util.separador() + "documentumdfcs";
    String dirdfc = dirbase + util.separador() + "documentum" + util.separador() + "shared" + util.separador();
    public static PantallaDocumentum ventanapadre = null;
    private Boolean botonderecho = false;
    private String componente = "";
    private String idDocumentum = "";
    IDfSession gsesion;

    public String getIdDocumentum() {
        return idDocumentum;
    }

    public void setIdDocumentum(String idDocumentum) {
        this.idDocumentum = idDocumentum;
    }

    public PantallaJobs(PantallaDocumentum parent, boolean modal) {
        ventanapadre = parent;
        initComponents();
        setLocationRelativeTo(null);
        inicializar();
        //    setVisible(true);
    }

    private void inicializar() {
        try {
            setIconImage(new ImageIcon(getLogo()).getImage());
        } catch (NullPointerException e) {
            Utilidades.escribeLog("\nError cargando el Logo " + e.getMessage() + "\n");
        }
    }

    protected static Image getLogo() {
        //   java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/documentum_logo_mini.gif");
        java.net.URL imgURL = PantallaJobs.class.getClassLoader().getResource("es/documentum/imagenes/jobs.png");

        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
     * this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupAtributos = new javax.swing.JPopupMenu();
        opcionCopiarValor = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        opcionRunJob = new javax.swing.JMenuItem();
        opcionStopJob = new javax.swing.JMenuItem();
        opcionActivarJob = new javax.swing.JMenuItem();
        opcionJobLog = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        opcionExportarJobsExcel = new javax.swing.JMenuItem();
        botonSalir = new javax.swing.JButton();
        panelDocumentos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaJobs = new javax.swing.JTable();
        botonActualizar = new javax.swing.JButton();

        opcionCopiarValor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/copiar.png"))); // NOI18N
        opcionCopiarValor.setText("Copiar Valor");
        opcionCopiarValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarValorActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionCopiarValor);
        popupAtributos.add(jSeparator1);

        opcionRunJob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/ejecutar.png"))); // NOI18N
        opcionRunJob.setText("Ejecutar Job");
        opcionRunJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRunJobActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionRunJob);

        opcionStopJob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/stop_peq.gif"))); // NOI18N
        opcionStopJob.setText("Parar Job");
        opcionStopJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionStopJobActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionStopJob);

        opcionActivarJob.setText("Activar / Desactivar Job");
        opcionActivarJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionActivarJobActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionActivarJob);

        opcionJobLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/documento-log.png"))); // NOI18N
        opcionJobLog.setText("Ver fichero de log");
        opcionJobLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionJobLogActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionJobLog);
        popupAtributos.add(jSeparator2);

        opcionExportarJobsExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/excel-24.gif"))); // NOI18N
        opcionExportarJobsExcel.setText("Exportar lista de Jobs a Excel");
        opcionExportarJobsExcel.setActionCommand("ExportarAtributosExcel");
        opcionExportarJobsExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionExportarJobsExcelActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionExportarJobsExcel);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(1022, 731));

        botonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        botonSalir.setText("Cerrar");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        panelDocumentos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelDocumentos.setAutoscrolls(true);
        panelDocumentos.setMaximumSize(new java.awt.Dimension(1020, 640));
        panelDocumentos.setMinimumSize(new java.awt.Dimension(1020, 640));

        tablaJobs.setAutoCreateRowSorter(true);
        tablaJobs.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        tablaJobs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaJobs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaJobsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaJobsMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaJobs);

        javax.swing.GroupLayout panelDocumentosLayout = new javax.swing.GroupLayout(panelDocumentos);
        panelDocumentos.setLayout(panelDocumentosLayout);
        panelDocumentosLayout.setHorizontalGroup(
            panelDocumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDocumentosLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(1, 1, 1))
        );
        panelDocumentosLayout.setVerticalGroup(
            panelDocumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDocumentosLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(1, 1, 1))
        );

        botonActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/actualizar.png"))); // NOI18N
        botonActualizar.setText("Actualizar");
        botonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 864, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {botonActualizar, botonSalir});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(panelDocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 518, Short.MAX_VALUE)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonSalir, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonActualizar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(6, 6, 6))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {botonActualizar, botonSalir});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaJobsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaJobsMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "tablaJobs";
            popupmenu(evt);
        }
    }//GEN-LAST:event_tablaJobsMousePressed

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
                if (componente.equals("tablaJobs")) {
                    if (row >= 0 && column >= 0 && tablaJobs.getModel().getRowCount() > 0) {
                        String nombre = tablaJobs.getModel().getValueAt(tablaJobs.convertRowIndexToModel(tablaJobs.getSelectedRow()), 0).toString();

                        if (utilDocum.estaJobActivo(nombre, gsesion)) {
                            opcionActivarJob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/bombilla-off.png")));
                            opcionActivarJob.setText("Desactivar Job");
                        } else {
                            opcionActivarJob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/bombilla-on.png")));
                            opcionActivarJob.setText("Activar Job");
                        }
                        popupAtributos.show(evt.getComponent(), evt.getX(), evt.getY());

                    }
                }
            }
        }
    }


    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        salir();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void opcionCopiarValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarValorActionPerformed
        if (tablaJobs.getModel().getValueAt(tablaJobs.getSelectedRow(), tablaJobs.getSelectedColumn()) != null) {
            Utilidades.copiarTextoPortapapeles(tablaJobs.getModel().getValueAt(tablaJobs.convertRowIndexToModel(tablaJobs.getSelectedRow()), tablaJobs.getSelectedColumn()).toString());
        }

    }//GEN-LAST:event_opcionCopiarValorActionPerformed

    private void opcionRunJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRunJobActionPerformed
        String object_name = tablaJobs.getModel().getValueAt(tablaJobs.convertRowIndexToModel(tablaJobs.getSelectedRow()), 0).toString();
        String dql = "UPDATE dm_job OBJECT set run_now = true where object_name ='" + object_name + "'";
        utilDocum.ejecutarDql(dql);
        pintarTabla();
    }//GEN-LAST:event_opcionRunJobActionPerformed

    private void opcionExportarJobsExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionExportarJobsExcelActionPerformed
        exportarAtributosExcel();
    }//GEN-LAST:event_opcionExportarJobsExcelActionPerformed

    private void tablaJobsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaJobsMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == java.awt.event.MouseEvent.BUTTON1 && tablaJobs.getModel().getRowCount() > 0) {
            abrirJob();
        }

    }//GEN-LAST:event_tablaJobsMouseClicked

    private void opcionStopJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionStopJobActionPerformed
        String r_object_id = tablaJobs.getModel().getValueAt(tablaJobs.convertRowIndexToModel(tablaJobs.getSelectedRow()), 6).toString();
//        IDfSession sesion = utilDocum.conectarDocumentum();
        utilDocum.ejecutarAPI("unlock,c," + r_object_id, "", gsesion);
        String dql = "update dm_job object set run_now=FALSE, set a_current_status='FAILED', set a_special_app = '' where r_object_id ='" + r_object_id + "'";
        utilDocum.ejecutarDql(dql, gsesion);
//        try {
//            if (sesion.isConnected()) {
//                sesion.disconnect();
//            }
//        } catch (DfException ex) {
//
//        }
        pintarTabla();
    }//GEN-LAST:event_opcionStopJobActionPerformed

    private void opcionJobLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionJobLogActionPerformed
        String nombre = tablaJobs.getModel().getValueAt(tablaJobs.convertRowIndexToModel(tablaJobs.getSelectedRow()), 0).toString();
        verJobReport(nombre);
    }//GEN-LAST:event_opcionJobLogActionPerformed

    private void botonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarActionPerformed
        cargarJobs();
    }//GEN-LAST:event_botonActualizarActionPerformed

    private void opcionActivarJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionActivarJobActionPerformed
        String nombre = tablaJobs.getModel().getValueAt(tablaJobs.convertRowIndexToModel(tablaJobs.getSelectedRow()), 0).toString();
        if (utilDocum.estaJobActivo(nombre, gsesion)) {
            utilDocum.cambiarEstadoJob(nombre, false, gsesion);
        } else {
            utilDocum.cambiarEstadoJob(nombre, true, gsesion);
        }
        cargarJobs();
    }//GEN-LAST:event_opcionActivarJobActionPerformed

    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaJobs(ventanapadre, true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonActualizar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem opcionActivarJob;
    private javax.swing.JMenuItem opcionCopiarValor;
    private javax.swing.JMenuItem opcionExportarJobsExcel;
    private javax.swing.JMenuItem opcionJobLog;
    private javax.swing.JMenuItem opcionRunJob;
    private javax.swing.JMenuItem opcionStopJob;
    private javax.swing.JPanel panelDocumentos;
    private javax.swing.JPopupMenu popupAtributos;
    private javax.swing.JTable tablaJobs;
    // End of variables declaration//GEN-END:variables

    public void cargarJobs() {
        utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
        gsesion = ventanapadre.gsesion;
        new Thread() {
            public void run() {
                DefaultTableModel modeloLotes = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int fila, int columna) {
                        return false;
                    }
                };

                //IDfSession sesion = utilDocum.conectarDocumentum();
                barradocum = new PantallaBarra(PantallaJobs.this, false);
                barradocum.setTitle("Jobs de " + getIdDocumentum());
                barradocum.barra.setIndeterminate(true);
                barradocum.botonParar.setVisible(false);
                barradocum.setLabelMensa("");
                barradocum.barra.setOpaque(true);
                barradocum.barra.setStringPainted(false);
                barradocum.validate();
                barradocum.setVisible(true);

                jobs = utilDocum.dameJobs(gsesion);
                if (jobs.size() <= 0) {
                    tablaJobs.setModel(modeloLotes);
                    barradocum.dispose();
                    return;
                }

                Object[][] datos = new Object[jobs.size()][9];
                Object[] cabecera = {"Nombre", "Descripción", "Tipo", "Ultima ejecución", "Activo", "Resultado", "ID", "Próxima ejecución", "Estado"};

                for (int n = 0; n < jobs.size(); n++) {
                    ArrayList valores = (ArrayList) jobs.get(n);
                    datos[n][0] = valores.get(0);
                    datos[n][1] = valores.get(1);
                    datos[n][2] = valores.get(2);
                    datos[n][3] = valores.get(3);
                    datos[n][4] = valores.get(4);
                    datos[n][5] = valores.get(5);
                    datos[n][6] = valores.get(6);
                    datos[n][7] = valores.get(7);
                    datos[n][8] = valores.get(8).equals("agentexec") ? "En ejecución" : "";
                    barradocum.labelMensa.setText((String) valores.get(0));
                }

                if (datos.length > 0) {
                    modeloLotes = new DefaultTableModel(datos, cabecera) {
                        @Override
                        public boolean isCellEditable(int fila, int columna) {
                            return false;
                        }
                    };

                } else {
                    modeloLotes = new DefaultTableModel() {
                        @Override
                        public boolean isCellEditable(int fila, int columna) {
                            return false;
                        }
                    };
                }
                tablaJobs.setModel(modeloLotes);
//                tablaJobs.setShowHorizontalLines(true);
//                tablaJobs.setRowSelectionAllowed(false);
//                tablaJobs.setAutoCreateRowSorter(true);

                TableColumn columna = tablaJobs.getColumnModel().getColumn(0);
                columna.setPreferredWidth(260);
                columna.setMaxWidth(350);
                columna.sizeWidthToFit();
                TableColumn columna2 = tablaJobs.getColumnModel().getColumn(2);
                columna2.setPreferredWidth(160);
                columna2.setMaxWidth(160);
                columna2.sizeWidthToFit();
                TableColumn columna3 = tablaJobs.getColumnModel().getColumn(3);
                columna3.setPreferredWidth(170);
                columna3.setMaxWidth(170);
                columna3.sizeWidthToFit();
                TableColumn columna4 = tablaJobs.getColumnModel().getColumn(4);
                columna4.setPreferredWidth(80);
                columna4.setMaxWidth(80);
                columna4.sizeWidthToFit();
//                DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
//                rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
//                tablaJobs.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
//                tablaJobs.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                tablaJobs.getColumnModel().getColumn(6).setWidth(0);
                tablaJobs.getColumnModel().getColumn(6).setMinWidth(0);
                tablaJobs.getColumnModel().getColumn(6).setMaxWidth(0);
                tablaJobs.doLayout();
                pintarTabla();
                JTableHeader header = tablaJobs.getTableHeader();
                header.addMouseListener(new TableHeaderMouseListener(tablaJobs));
//                try {
//                    if (sesion.isConnected()) {
//                        sesion.disconnect();
//                    }
//                } catch (DfException ex) {
//
//                }
                barradocum.dispose();
            }
        }.start();
        System.gc();
    }

    private void pintarTabla() {
        tablaJobs.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                boolean oddRow = (row % 2 == 0);
                if (oddRow) {
                    setBackground(new Color(245, 245, 245)); // gris claro
                } else {
                    setBackground(Color.WHITE);
                }

                String valor = (String) table.getValueAt(row, 4);
                String nombre = (String) table.getValueAt(row, 0);
                String estado = (String) table.getValueAt(row, 8);

                if (estado.equalsIgnoreCase("En ejecución")) {
                    setOpaque(true);
                    setForeground(Color.RED);
                } else {
                    if (valor.equals("Activo")) {
                        setOpaque(true);
                        setForeground(Color.BLUE);
                    } else {
                        setOpaque(false);
                        setForeground(Color.GRAY);
                    }
                }
//                if (valor.equals("Activo")) {
////                    setOpaque(true);
//                    setForeground(Color.BLUE);
//                } else {
////                    setOpaque(false);
//                    setForeground(Color.GRAY);
//                }

                if (isSelected) {
                    setBackground(new Color(175, 205, 235)); // azul claro selección
                }

                return this;
            }
        });
    }

    private void salir() {
        this.dispose();
        System.gc();
    }

    private void abrirJob() {
//        String resultado = tablaJobs.getModel().getValueAt(tablaJobs.convertRowIndexToModel(tablaJobs.getSelectedRow()), 4).toString();
//        try {
//            File path = new File(resultado);
//            Desktop.getDesktop().open(path);
//        } catch (IOException ex) {
//            Utilidades.escribeLog("Error al abrir el archivo (" + resultado + ") - Error " + ex.getMessage());
//        }
    }

    private void exportarAtributosExcel() {
        if (tablaJobs.getModel().getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Seleccionar directorio y nombre de fichero");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String fichero = chooser.getSelectedFile().toString();
                if (!fichero.toLowerCase().endsWith(".xlsx")) {
                    fichero = fichero + ".xlsx";
                }
                if (!fichero.isEmpty()) {
                    util.exportarAExcel(tablaJobs, fichero, "Jobs ");
                    //   util.exportaExcel(tablaJobs, fichero);
                }
            } else {
                Utilidades.escribeLog("No se ha seleccionado el fichero de salida ");
            }
            System.gc();
        }

    }

    private void verJobReport(String job) {
        try {
            String jobName = job;
            if (jobName.startsWith("dm_")) {
                jobName = jobName.substring(3);
            }
//            IDfSession sesion = utilDocum.conectarDocumentum();
            IDfFolder folderObject = gsesion.getFolderByPath("/System/Sysadmin/Reports");

            String folderId = folderObject.getObjectId().getId();
            StringBuilder qualificationClause = new StringBuilder(512);
            qualificationClause.append("dm_document ");
            qualificationClause.append("WHERE object_name = '");
            qualificationClause.append(jobName);
            qualificationClause.append("'");
            qualificationClause.append(" AND ANY i_folder_id = '");
            qualificationClause.append(folderId);
            qualificationClause.append("'");
            IDfSysObject reportObject = (IDfSysObject) gsesion.getObjectByQualification(qualificationClause.toString());
            if ((reportObject != null) && (!reportObject.getString("i_contents_id").equals("0000000000000000"))) {
                String reportContent = visualizarFicheroLogJob(reportObject);
            }

//            if (sesion.isConnected()) {
//                sesion.disconnect();
//            }

        } catch (DfException ex) {
            Utilidades.escribeLog("Error al recuperar report del Job. (verReport) Error: " + ex.getMessage());
        }
    }

    protected String visualizarFicheroLogJob(IDfSysObject object) {
        String fileName = "";
        String jobReportFileContent = "";
        try {
            String completeFilePath = dirbase + File.separator + "JobReports";
            File temp = new File(completeFilePath);
            if (!temp.exists()) {
                temp.mkdirs();
            }
            fileName = completeFilePath + File.separator + object.getObjectName();

            Long tam = object.getContentSize();
            // Máximo tamaño 50 MB
            if (tam > 52428800) {
                PantallaMensaje mensa = new PantallaMensaje(this, true);
                mensa.setMensaje("\nEl tamaño del fichero de log supera los 50 MB\nNo se muestra el contenido del fichero");
                mensa.setTitle("Log del job " + object.getObjectName());
                mensa.setVisible(true);
            } else {
                fileName = object.getFileEx2(fileName, "", 0, "", false);
                if ((fileName != null) && (!fileName.equals(""))) {
                    PantallaLeerFichero lee = new PantallaLeerFichero(this, true);
                    lee.cargarFichero(fileName);
                    lee.setTitle("Log de la ejecución del job " + object.getObjectName());
                    lee.setVisible(true);

                }
            }
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al recuperar report del Job. (verReport) Error: " + ex.getMessage());
        } finally {
            util.borrarFichero(fileName);
        }
        if (!jobReportFileContent.equals("")) {
            return jobReportFileContent;
        }
        return "";
    }

    public class TableHeaderMouseListener extends MouseAdapter {

        private final JTable table;

        public TableHeaderMouseListener(JTable table) {
            this.table = table;
        }

        @Override
        public void mouseClicked(MouseEvent event) {
            pintarTabla();
        }
    }
}
