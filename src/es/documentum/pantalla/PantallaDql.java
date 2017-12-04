package es.documentum.pantalla;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfAttr;
import com.documentum.fc.common.IDfValue;
import es.documentum.utilidades.ClassPathUpdater;
import es.documentum.utilidades.Utilidades;
import es.documentum.utilidades.UtilidadesDocumentum;
import static es.documentum.utilidades.UtilidadesDocumentum.getDfObjectValue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author julian.collado
 */
public class PantallaDql extends javax.swing.JFrame {

    Utilidades util = new Utilidades();
    UtilidadesDocumentum utildocum = new UtilidadesDocumentum();
    PantallaBarra barradocum = null;
    Boolean botonderecho = false;
    String componente = "";
    public static PantallaDocumentum ventanapadre = null;
    IDfSession sesion = sesionDocumentum();

    public PantallaDql(PantallaDocumentum parent, boolean modal) {
        ventanapadre = parent;
        initComponents();
        try {
            setIconImage(new ImageIcon(getLogo()).getImage());
        } catch (NullPointerException e) {
            Utilidades.escribeLog("\nError cargando el Logo " + e.getMessage() + "\n");
        }

        DefaultTableModel modeloLotes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };;
        tablaResultados.setModel(modeloLotes);
        tablaResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaResultados.setAutoscrolls(true);
        setLocationRelativeTo(null);
        cargarComboHistorial();
        TextoNumReg.setText("0");
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupEditar = new javax.swing.JPopupMenu();
        opcionCopiar = new javax.swing.JMenuItem();
        opcionPegar = new javax.swing.JMenuItem();
        popupDatos = new javax.swing.JPopupMenu();
        opcionCopiarValor = new javax.swing.JMenuItem();
        opcionExportarExcel = new javax.swing.JMenuItem();
        popupHistorial = new javax.swing.JPopupMenu();
        opcionVaciarHistorial = new javax.swing.JMenuItem();
        panelDql = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textoDql = new javax.swing.JTextArea();
        botonConsultar = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textoLog = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboHistorial = new javax.swing.JComboBox();
        checkDameSQL = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        TextoNumReg = new javax.swing.JFormattedTextField();
        panelResultado = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();
        panelEstado = new javax.swing.JPanel();
        EtiquetaEstado = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        opciones = new javax.swing.JMenu();
        opcionConsultar = new javax.swing.JMenuItem();
        opcionSalir = new javax.swing.JMenuItem();

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

        opcionCopiarValor.setText("Copiar Valor");
        opcionCopiarValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarValorActionPerformed(evt);
            }
        });
        popupDatos.add(opcionCopiarValor);

        opcionExportarExcel.setText("Exportar a Excel");
        opcionExportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionExportarExcelActionPerformed(evt);
            }
        });
        popupDatos.add(opcionExportarExcel);

        opcionVaciarHistorial.setText("Vaciar Historial de DQL");
        opcionVaciarHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionVaciarHistorialActionPerformed(evt);
            }
        });
        popupHistorial.add(opcionVaciarHistorial);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Consultas a Documentum por DQL");
        setMinimumSize(new java.awt.Dimension(1022, 740));

        panelDql.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        textoDql.setColumns(20);
        textoDql.setLineWrap(true);
        textoDql.setRows(5);
        textoDql.setWrapStyleWord(true);
        textoDql.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                textoDqlMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(textoDql);
        textoDql.getAccessibleContext().setAccessibleName("textoDql");

        botonConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/ejecutar_peq.png"))); // NOI18N
        botonConsultar.setText("Ejecutar");
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
        botonSalir.setMaximumSize(new java.awt.Dimension(100, 40));
        botonSalir.setMinimumSize(new java.awt.Dimension(100, 40));
        botonSalir.setPreferredSize(new java.awt.Dimension(100, 40));
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

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
        textoLog.getAccessibleContext().setAccessibleName("textoLog");

        jLabel1.setText("Salida");

        jLabel2.setText("DQL");

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

        checkDameSQL.setText("Mostrar SQL");

        jLabel3.setText("Nº de registros de salida");

        TextoNumReg.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        TextoNumReg.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TextoNumReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextoNumRegActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDqlLayout = new javax.swing.GroupLayout(panelDql);
        panelDql.setLayout(panelDqlLayout);
        panelDqlLayout.setHorizontalGroup(
            panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDqlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDqlLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDqlLayout.createSequentialGroup()
                        .addGroup(panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelDqlLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelDqlLayout.createSequentialGroup()
                                .addGroup(panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                                    .addComponent(comboHistorial, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(28, 28, 28)
                                .addGroup(panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(checkDameSQL, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(TextoNumReg, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(33, 33, 33))))
        );
        panelDqlLayout.setVerticalGroup(
            panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDqlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDqlLayout.createSequentialGroup()
                        .addComponent(botonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(checkDameSQL)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addComponent(comboHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelDqlLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextoNumReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelResultado.setAutoscrolls(true);

        tablaResultados.setAutoCreateRowSorter(true);
        tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaResultados.setColumnSelectionAllowed(true);
        tablaResultados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaResultadosMousePressed(evt);
            }
        });
        panelResultado.setViewportView(tablaResultados);
        tablaResultados.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        panelEstado.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelEstadoLayout = new javax.swing.GroupLayout(panelEstado);
        panelEstado.setLayout(panelEstadoLayout);
        panelEstadoLayout.setHorizontalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EtiquetaEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelEstadoLayout.setVerticalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EtiquetaEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        opciones.setText("Opciones");

        opcionConsultar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionConsultar.setText("Consultar");
        opcionConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionConsultarActionPerformed(evt);
            }
        });
        opciones.add(opcionConsultar);

        opcionSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        opcionSalir.setText("Salir");
        opcionSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionSalirActionPerformed(evt);
            }
        });
        opciones.add(opcionSalir);

        jMenuBar1.add(opciones);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelResultado, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
            .addComponent(panelDql, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelDql, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelResultado, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        salir();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void botonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarActionPerformed
        if (!textoDql.getText().isEmpty()) {
            ejecutarDql(textoDql.getText(), TextoNumReg.getText());
        }
    }//GEN-LAST:event_botonConsultarActionPerformed

    private void opcionConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionConsultarActionPerformed
        if (!textoDql.getText().isEmpty()) {
            ejecutarDql(textoDql.getText(), TextoNumReg.getText());
        }
    }//GEN-LAST:event_opcionConsultarActionPerformed

    private void opcionSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionSalirActionPerformed
        salir();
    }//GEN-LAST:event_opcionSalirActionPerformed

    private void opcionCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarActionPerformed
        if (componente.equals("textoDql")) {
            if (textoDql.getSelectedText() == null) {
                Utilidades.copiarTextoPortapapeles(textoDql.getText());
            } else {
                Utilidades.copiarTextoPortapapeles(textoDql.getSelectedText());
            }
        }
        if (componente.equals("textoLog")) {
            if (textoLog.getSelectedText() == null) {
                Utilidades.copiarTextoPortapapeles(textoLog.getText());
            } else {
                Utilidades.copiarTextoPortapapeles(textoLog.getSelectedText());
            }
        }
    }//GEN-LAST:event_opcionCopiarActionPerformed

    private void opcionPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionPegarActionPerformed
        if (componente.equals("textoDql")) {
            textoDql.setText(Utilidades.pegarTextoPortapapeles());
        }

    }//GEN-LAST:event_opcionPegarActionPerformed

    private void opcionCopiarValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarValorActionPerformed
        if (componente.equals("tablaResultados")) {
            Utilidades.copiarTextoPortapapeles(tablaResultados.getModel().getValueAt(tablaResultados.convertRowIndexToModel(tablaResultados.getSelectedRow()), tablaResultados.getSelectedColumn()).toString());
        }
    }//GEN-LAST:event_opcionCopiarValorActionPerformed

    private void textoDqlMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textoDqlMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "textoDql";
            popupmenu(evt);
        }
    }//GEN-LAST:event_textoDqlMousePressed

    private void textoLogMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textoLogMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "textoLog";
            popupmenu(evt);
        }
    }//GEN-LAST:event_textoLogMousePressed

    private void tablaResultadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaResultadosMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "tablaResultados";
            popupmenu(evt);
        }
    }//GEN-LAST:event_tablaResultadosMousePressed

    private void opcionExportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionExportarExcelActionPerformed
        if (tablaResultados.getModel().getRowCount() > 0) {
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
                util.exportaExcel(tablaResultados, fichero);
//                EtiquetaEstado.setText(resultado);
            } else {
                Utilidades.escribeLog("No se ha seleccionado el fichero de salida ");
            }
        }
    }//GEN-LAST:event_opcionExportarExcelActionPerformed

    private void comboHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboHistorialActionPerformed
        String valor = (String) comboHistorial.getSelectedItem();
        if (textoDql.getText().isEmpty() || !textoDql.getText().equals(valor)) {
            if (!valor.isEmpty()) {
                textoDql.setText(valor);
            }
        }
    }//GEN-LAST:event_comboHistorialActionPerformed

    private void comboHistorialMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboHistorialMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            componente = "comboHistorial";
            botonderecho = true;
            popupmenu(evt);
        }
    }//GEN-LAST:event_comboHistorialMousePressed

    private void opcionVaciarHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionVaciarHistorialActionPerformed

        try {
            DefaultComboBoxModel modelo = new DefaultComboBoxModel();
            comboHistorial.setModel(modelo);
            String dirhist = util.usuarioHome() + util.separador() + "documentumdcfs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador() + "historial-dql.log";
            BufferedWriter bw = new BufferedWriter(new FileWriter(dirhist));
            bw.write("");
            bw.close();
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_opcionVaciarHistorialActionPerformed

    private void TextoNumRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextoNumRegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextoNumRegActionPerformed

    private void ejecutarDql(String pdql, String numreg) {
        String sqlfinal = pdql;
        final long numregsalida = Long.parseLong(numreg);
        if (numregsalida > 0) {
            sqlfinal = sqlfinal + " enable (return_top " + numreg + ")";
        }
        final String dql = sqlfinal;
        DefaultTableModel modeloLotes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaResultados.setModel(modeloLotes);
        EtiquetaEstado.setText("");
        textoLog.setText("");

        new Thread() {
            public void run() {
                DefaultTableModel modeloLotes = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int fila, int columna) {
                        return false;
                    }
                };
                tablaResultados.setModel(modeloLotes);
                String dirdfc = util.usuarioHome() + util.separador() + "documentumdcfs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();
                barradocum = new PantallaBarra(PantallaDql.this, false);
                barradocum.setTitle("Consultando en Documentum ...");
                barradocum.barra.setIndeterminate(true);
                barradocum.botonParar.setVisible(false);
                barradocum.setLabelMensa("");
                barradocum.barra.setOpaque(true);
                barradocum.barra.setStringPainted(false);
                barradocum.validate();
                barradocum.setVisible(true);

                try {
                    IDfCollection col = utildocum.ejecutarDql(dql, sesion);
                    if (!utildocum.dameError().equals("")) {
                        textoLog.setText(utildocum.dameError());
                        barradocum.dispose();
                        return;
                    }
                    if (!BuscarEnComboHistorial(textoDql.getText())) {
                        try {
                            FileOutputStream historial = new FileOutputStream(new File(dirdfc + "historial-dql.log"), true);
                            historial.write(("\n" + textoDql.getText().replaceAll("(\r\n|\n)", " ")).getBytes());
                            historial.close();
                            cargarComboHistorial();
                        } catch (Exception ex) {
                        }
                    }
                    ArrayList filas = new ArrayList();
                    while (col.next()) {
                        filas.add(col.getTypedObject());
                    }
                    col.close();

                    if (filas.size() <= 0) {
                        EtiquetaEstado.setText("0 Registro(s) encontrado(s) ");
                        textoLog.setText("0 Registro(s) encontrado(s) ");
                        barradocum.dispose();
                        return;
                    }

                    int cont = filas.size();
                    IDfTypedObject primerafila = (IDfTypedObject) filas.get(0);
                    int tam = primerafila.getAttrCount();
                    Object[] cabecera = new Object[tam];
                    Integer[] tamcabecera = new Integer[tam];
                    Object[][] datos = new Object[cont][tam];
                    for (int l = 0; l < tam; l++) {
                        cabecera[l] = primerafila.getAttr(l).getName();
                        tamcabecera[l] = 0;
                    }

                    for (int i = 0; i < cont; i++) {
                        IDfTypedObject row = (IDfTypedObject) filas.get(i);

                        for (int n = 0; n < row.getAttrCount(); n++) {
                            IDfAttr attr = row.getAttr(n);
                            IDfValue attrValue = row.getValue(attr.getName());

                            datos[i][n] = getDfObjectValue(attrValue);
                            if (getDfObjectValue(attrValue).toString().length() > tamcabecera[n]) {
                                tamcabecera[n] = getDfObjectValue(attrValue).toString().length();
                            }
                            if (tamcabecera[n] < attr.getName().length()) {
                                tamcabecera[n] = attr.getName().length();
                            }
                        }
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

                    for (int i = 0; i < modeloLotes.getColumnCount(); i++) {
                        TableColumn columna = tablaResultados.getColumnModel().getColumn(i);
                        columna.setPreferredWidth(tamcabecera[i] * tampixel);
                        columna.setMinWidth(tamcabecera[i] * tampixel);
                    }
                    tablaResultados.doLayout();
                    EtiquetaEstado.setText(cont + " Registro(s) encontrado(s) ");
                    textoLog.setText(cont + " Registro(s) encontrado(s) ");
                } catch (Exception ex) {
                    textoLog.setText(ex.getMessage());

                }
                if (checkDameSQL.isSelected()) {
                    String textoSql = utildocum.dameSql(sesion);
                    textoLog.setText(textoLog.getText() + "\n" + textoSql);
                }
                barradocum.dispose();
            }
        }.start();
    }

    private void cargarComboHistorial() {
        ArrayList comboBoxItems = new ArrayList();
        String dirhist = util.usuarioHome() + util.separador() + "documentumdcfs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador() + "historial-dql.log";
        BufferedReader br = null;

        try {
            String linea;
            br = new BufferedReader(new FileReader(dirhist));
            while ((linea = br.readLine()) != null) {
                comboBoxItems.add(linea);
            }
        } catch (IOException e) {
            Utilidades.escribeLog("Error al cargar el fichero de historial. (cargarComboHistorial) Error: " + e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                Utilidades.escribeLog("Error al cerrar el fichero de historial. (cargarComboHistorial) Error: " + ex.getMessage());
            }
        }

        DefaultComboBoxModel modelo = new DefaultComboBoxModel(comboBoxItems.toArray());
        comboHistorial.setModel(modelo);
    }

    private Boolean BuscarEnComboHistorial(String texto) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) comboHistorial.getModel();
        return (model.getIndexOf(texto) > 0);
    }

    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaDql(ventanapadre, true).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EtiquetaEstado;
    private javax.swing.JFormattedTextField TextoNumReg;
    private javax.swing.JButton botonConsultar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JCheckBox checkDameSQL;
    private javax.swing.JComboBox comboHistorial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem opcionConsultar;
    private javax.swing.JMenuItem opcionCopiar;
    private javax.swing.JMenuItem opcionCopiarValor;
    private javax.swing.JMenuItem opcionExportarExcel;
    private javax.swing.JMenuItem opcionPegar;
    private javax.swing.JMenuItem opcionSalir;
    private javax.swing.JMenuItem opcionVaciarHistorial;
    private javax.swing.JMenu opciones;
    private javax.swing.JPanel panelDql;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JScrollPane panelResultado;
    private javax.swing.JPopupMenu popupDatos;
    private javax.swing.JPopupMenu popupEditar;
    private javax.swing.JPopupMenu popupHistorial;
    private javax.swing.JTable tablaResultados;
    private javax.swing.JTextArea textoDql;
    private javax.swing.JTextArea textoLog;
    // End of variables declaration//GEN-END:variables

    private void salir() {
        DefaultTableModel modeloLotes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaResultados.setModel(modeloLotes);
        System.gc();
        if (sesion.isConnected()) {
            try {
                sesion.disconnect();
            } catch (DfException ex) {
                Utilidades.escribeLog("Error al desconecta la sesión de Documentum (PantallaDql)  - Error: " + ex.getMessage());
            }
        }
        this.dispose();
    }

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
                if (componente.equals("tablaResultados")) {
                    if (row >= 0 && column >= 0 && tablaResultados.getModel().getRowCount() > 0) {
                        popupDatos.show(evt.getComponent(), evt.getX(), evt.getY());
                    }
                }
            }
            if (evt.getSource().getClass().getName().equals("javax.swing.JTextArea")) {
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
                opcionPegar.setEnabled(true);
            }
            if (evt.getSource() == textoLog) {
                //          popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
                opcionPegar.setEnabled(false);
            }
            if (evt.getSource() == comboHistorial) {
                popupHistorial.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }

    private IDfSession sesionDocumentum() {
        String dirdfc = util.usuarioHome() + util.separador() + "documentumdcfs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();
        try {
            ClassPathUpdater.add(dirdfc);
            ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "jsafeFIPS.jar");
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al actualizar el Classpath  - Error: " + ex.getMessage());
        }
        UtilidadesDocumentum utildocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
        IDfSession nuevasesion = utildocum.conectarDocumentum();
        return nuevasesion;
    }
}
