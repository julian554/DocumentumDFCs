package es.documentum.pantalla;

import com.documentum.fc.client.IDfSession;
import static es.documentum.pantalla.PantallaDocumentum.getLogo;
<<<<<<< HEAD
=======
import es.documentum.utilidades.TablaSinEditarCol;
>>>>>>> origin/master
import es.documentum.utilidades.Utilidades;
import es.documentum.utilidades.UtilidadesDocumentum;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * @author julian
 */
public class PantallaRenditions extends javax.swing.JFrame {
<<<<<<< HEAD

=======
    
>>>>>>> origin/master
    UtilidadesDocumentum utilDocum = new UtilidadesDocumentum();
    Utilidades util = new Utilidades();
    PantallaBarra barradocum = null;
    boolean esadmin = false;
    ArrayList renditions = new ArrayList();
    String dirbase = util.usuarioHome() + util.separador() + "documentumdcfs";
    String dirdfc = dirbase + util.separador() + "documentum" + util.separador() + "shared" + util.separador();
    String dir = util.usuarioHome() + util.separador() + "documentumdcfs" + util.separador() + "renditions" + util.separador();
    public static PantallaDocumentum ventanapadre = null;
    private Boolean botonderecho = false;
    private String componente = "";
    private String idDocumentum = "";
<<<<<<< HEAD

    public String getIdDocumentum() {
        return idDocumentum;
    }

=======
    
    public String getIdDocumentum() {
        return idDocumentum;
    }
    
>>>>>>> origin/master
    public void setIdDocumentum(String idDocumentum) {
        this.idDocumentum = idDocumentum;
        textoIdDocumentum.setText(idDocumentum);
    }
<<<<<<< HEAD

=======
    
>>>>>>> origin/master
    public PantallaRenditions(PantallaDocumentum parent, boolean modal) {
        ventanapadre = parent;
        initComponents();
        setLocationRelativeTo(null);
        inicializar();
        //    setVisible(true);
    }
<<<<<<< HEAD

=======
    
>>>>>>> origin/master
    private void inicializar() {
        try {
            setIconImage(new ImageIcon(getLogo()).getImage());
        } catch (NullPointerException e) {
            Utilidades.escribeLog("\nError cargando el Logo " + e.getMessage() + "\n");
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

        popupAtributos = new javax.swing.JPopupMenu();
        opcionCopiarValor = new javax.swing.JMenuItem();
        opcionCopiarFormato = new javax.swing.JMenuItem();
        opcionExportarRenditionsExcel = new javax.swing.JMenuItem();
        botonSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textoIdDocumentum = new javax.swing.JTextField();
        panelDocumentos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRenditions = new javax.swing.JTable();

        opcionCopiarValor.setText("Copiar Valor");
        opcionCopiarValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarValorActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionCopiarValor);

        opcionCopiarFormato.setText("Copiar nombre de Formato");
        opcionCopiarFormato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarFormatoActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionCopiarFormato);

        opcionExportarRenditionsExcel.setText("Exportar lista de renditions a Excel");
        opcionExportarRenditionsExcel.setActionCommand("ExportarAtributosExcel");
        opcionExportarRenditionsExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionExportarRenditionsExcelActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionExportarRenditionsExcel);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(830, 640));
        setPreferredSize(new java.awt.Dimension(830, 640));
        setResizable(false);

        botonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        botonSalir.setText("Cerrar");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Identificador de Documentun (r_object_id):");

        textoIdDocumentum.setEditable(false);
        textoIdDocumentum.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        textoIdDocumentum.setBorder(null);
        textoIdDocumentum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoIdDocumentumActionPerformed(evt);
            }
        });

        panelDocumentos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelDocumentos.setAutoscrolls(true);
        panelDocumentos.setMaximumSize(new java.awt.Dimension(1020, 640));
        panelDocumentos.setMinimumSize(new java.awt.Dimension(1020, 640));

        tablaRenditions.setAutoCreateRowSorter(true);
        tablaRenditions.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        tablaRenditions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaRenditions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaRenditionsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaRenditionsMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaRenditions);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 798, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textoIdDocumentum))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoIdDocumentum, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panelDocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 465, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaRenditionsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaRenditionsMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "tablaRenditions";
            popupmenu(evt);
        }
    }//GEN-LAST:event_tablaRenditionsMousePressed
<<<<<<< HEAD

=======
    
>>>>>>> origin/master
    private void popupmenu(MouseEvent evt) {
        if (evt.isPopupTrigger() || botonderecho) {
            botonderecho = false;
            int row = 0;
            int column = 0;
            if (evt.getSource().getClass().getName().equals("javax.swing.JTable")) {
                JTable source = (JTable) evt.getSource();
                row = source.rowAtPoint(evt.getPoint());
                column = source.columnAtPoint(evt.getPoint());
                if (!source.isRowSelected(row)) {
                    source.changeSelection(row, column, false, false);
                }
<<<<<<< HEAD

=======
                
>>>>>>> origin/master
                if (componente.equals("tablaRenditions")) {
                    if (row >= 0 && column >= 0 && tablaRenditions.getModel().getRowCount() > 0) {
                        popupAtributos.show(evt.getComponent(), evt.getX(), evt.getY());
                    }
                }
<<<<<<< HEAD

            }

        }
    }

=======
                
            }
            
        }
    }
    
>>>>>>> origin/master

    private void textoIdDocumentumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoIdDocumentumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoIdDocumentumActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        salir();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void opcionCopiarValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarValorActionPerformed
        if (tablaRenditions.getModel().getValueAt(tablaRenditions.getSelectedRow(), 1) != null) {
            Utilidades.copiarTextoPortapapeles(tablaRenditions.getModel().getValueAt(tablaRenditions.convertRowIndexToModel(tablaRenditions.getSelectedRow()), 1).toString());
        }

    }//GEN-LAST:event_opcionCopiarValorActionPerformed

    private void opcionCopiarFormatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarFormatoActionPerformed
        Utilidades.copiarTextoPortapapeles(tablaRenditions.getModel().getValueAt(tablaRenditions.convertRowIndexToModel(tablaRenditions.getSelectedRow()), 0).toString());
    }//GEN-LAST:event_opcionCopiarFormatoActionPerformed

    private void opcionExportarRenditionsExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionExportarRenditionsExcelActionPerformed
        ExportarAtributosExcel();
    }//GEN-LAST:event_opcionExportarRenditionsExcelActionPerformed

    private void tablaRenditionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaRenditionsMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == java.awt.event.MouseEvent.BUTTON1 && tablaRenditions.getModel().getRowCount() > 0) {
            AbrirRendition();
        }
    }//GEN-LAST:event_tablaRenditionsMouseClicked
<<<<<<< HEAD

=======
    
>>>>>>> origin/master
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaRenditions(ventanapadre, true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem opcionCopiarFormato;
    private javax.swing.JMenuItem opcionCopiarValor;
    private javax.swing.JMenuItem opcionExportarRenditionsExcel;
    private javax.swing.JPanel panelDocumentos;
    private javax.swing.JPopupMenu popupAtributos;
    private javax.swing.JTable tablaRenditions;
    private javax.swing.JTextField textoIdDocumentum;
    // End of variables declaration//GEN-END:variables

    public void cargarRenditions(String pr_object_id) {
        final String r_object_id = pr_object_id;
        new Thread() {
            public void run() {
<<<<<<< HEAD
                DefaultTableModel modeloLotes = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int fila, int columna) {
                        return false;
                    }
                };
                utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
=======
                DefaultTableModel modeloLotes = new DefaultTableModel();
                utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
                if (!esadmin) {
                    modeloLotes = (DefaultTableModel) new TablaSinEditarCol();
                }
>>>>>>> origin/master
                if (r_object_id.isEmpty()) {
                    tablaRenditions.setModel(modeloLotes);
                    return;
                }
<<<<<<< HEAD

                IDfSession sesion = utilDocum.conectarDocumentum();

=======
                
                IDfSession sesion = utilDocum.conectarDocumentum();
                
>>>>>>> origin/master
                barradocum = new PantallaBarra(PantallaRenditions.this, false);
                barradocum.setTitle("Renditions de " + getIdDocumentum());
                barradocum.barra.setIndeterminate(true);
                barradocum.botonParar.setVisible(false);
                barradocum.setLabelMensa("");
                barradocum.barra.setOpaque(true);
                barradocum.barra.setStringPainted(false);
                barradocum.validate();
                barradocum.setVisible(true);
<<<<<<< HEAD

=======
                
>>>>>>> origin/master
                renditions = utilDocum.dameRenditions(sesion, r_object_id);
                if (renditions.size() <= 0) {
                    tablaRenditions.setModel(modeloLotes);
                    barradocum.dispose();
                    return;
                }
<<<<<<< HEAD

                Object[][] datos = new Object[renditions.size()][5];
                Object[] cabecera = {"Formato", "Tamaño", "Fecha modificación", "Filestore", "Ruta"};

=======
                
                Object[][] datos = new Object[renditions.size()][5];
                Object[] cabecera = {"Formato", "Tamaño", "Fecha modificación", "Filestore", "Ruta"};
                
>>>>>>> origin/master
                for (int n = 0; n < renditions.size(); n++) {
                    ArrayList valores = (ArrayList) renditions.get(n);
//                    for (int i = 0; i < valores.size(); i++) {
//                        System.out.println(valores.get(i));
//                        datos[n][i]=valores.get(i);
//                    }
                    datos[n][0] = valores.get(0);
                    datos[n][1] = util.humanReadableByteCount(Long.parseLong((String) valores.get(4)), false);
                    datos[n][2] = valores.get(5);
                    datos[n][3] = valores.get(6);
                    datos[n][4] = valores.get(3);
                    barradocum.labelMensa.setText((String) valores.get(3));
                }
<<<<<<< HEAD

                if (datos.length > 0) {
                    if (!esadmin) {
                        modeloLotes = new DefaultTableModel(datos, cabecera) {
                            @Override
                            public boolean isCellEditable(int fila, int columna) {
                                return false;
                            }
                        };
                    } else {
                        modeloLotes = new DefaultTableModel(datos, cabecera) {
                            @Override
                            public boolean isCellEditable(int fila, int columna) {
                                return false;
                            }
                        };
                    }

                } else {
                    modeloLotes = new DefaultTableModel() {
                        @Override
                        public boolean isCellEditable(int fila, int columna) {
                            return false;
                        }
                    };
=======
                
                if (datos.length > 0) {
                    if (!esadmin) {
                        modeloLotes = new TablaSinEditarCol(datos, cabecera);
                        //  modeloLotes = new DefaultTableModel(datos, cabecera);
                    } else {
                        modeloLotes = new DefaultTableModel(datos, cabecera);
                    }
                    
                } else {
                    modeloLotes = new TablaSinEditarCol();
>>>>>>> origin/master
                }
                tablaRenditions.setModel(modeloLotes);
                TableColumn columna = tablaRenditions.getColumnModel().getColumn(0);
                columna.setPreferredWidth(300);
                columna.setMaxWidth(300);
                columna.sizeWidthToFit();
                DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
                rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
                tablaRenditions.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
                tablaRenditions.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                tablaRenditions.getColumnModel().getColumn(4).setWidth(0);
                tablaRenditions.getColumnModel().getColumn(4).setMinWidth(0);
                tablaRenditions.getColumnModel().getColumn(4).setMaxWidth(0);
                tablaRenditions.doLayout();
                barradocum.dispose();
            }
        }.start();
        System.gc();
    }
<<<<<<< HEAD

=======
    
>>>>>>> origin/master
    private void salir() {
        this.dispose();
        util.borrarFichero(dir, "rendition*");
        System.gc();
    }
<<<<<<< HEAD

=======
    
>>>>>>> origin/master
    private void AbrirRendition() {
        String resultado = tablaRenditions.getModel().getValueAt(tablaRenditions.convertRowIndexToModel(tablaRenditions.getSelectedRow()), 4).toString();
        try {
            File path = new File(resultado);
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            Utilidades.escribeLog("Error al abrir el archivo (" + resultado + ") - Error " + ex.getMessage());
        }
    }
<<<<<<< HEAD

=======
    
>>>>>>> origin/master
    private void ExportarAtributosExcel() {
        if (tablaRenditions.getModel().getRowCount() > 0) {
            String fichero = "";
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Seleccionar directorio y nombre de fichero");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                fichero = chooser.getSelectedFile().toString();
                if (!fichero.toLowerCase().endsWith(".xls")) {
                    fichero = fichero + ".xls";
                }
                util.exportaExcel(tablaRenditions, fichero);
            } else {
                Utilidades.escribeLog("No se ha seleccionado el fichero de salida ");
            }
            System.gc();
        }
<<<<<<< HEAD

=======
        
>>>>>>> origin/master
    }
}
