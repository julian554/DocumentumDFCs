package es.documentum.pantalla;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfAttr;
import com.documentum.fc.common.IDfValue;
import es.documentum.utilidades.CambiarColorTexto;
import es.documentum.utilidades.ClassPathUpdater;
import es.documentum.utilidades.FormatoSql;
import es.documentum.utilidades.Utilidades;
import es.documentum.utilidades.UtilidadesDocumentum;
import static es.documentum.utilidades.UtilidadesDocumentum.getDfObjectValue;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import org.apache.commons.lang.StringUtils;

public class PantallaSqlconTabs extends javax.swing.JFrame {

    Utilidades util = new Utilidades();
    UtilidadesDocumentum utildocum = new UtilidadesDocumentum();
    PantallaBarra barradocum = null;
    Boolean botonderecho = false;
    String componente = "";
    public static PantallaDocumentum ventanapadre = null;
    IDfSession gsesion = sesionDocumentum();
    String ERROR = "";
    SimpleAttributeSet AtributoRojo = new SimpleAttributeSet();
    SimpleAttributeSet AtributoAzul = new SimpleAttributeSet();
    SimpleAttributeSet AtributoNegro = new SimpleAttributeSet();
    ArrayList listaCombo = new ArrayList();
    JScrollPane[] scroll;
    JTable[] tabla;
    int SQLsExcel = 1;
    StyleContext styleContext = new StyleContext();
    Style defaultStyle = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
    Style cwStyle = styleContext.addStyle("ConstantWidth", null);
    Style comillaStyle = styleContext.addStyle("ConstantWidth", null);
    String gestorBBDD;
    int posicionTextoSql = 0;

    public int getPosicionTextoSql() {
        return textoSql.getCaretPosition();
    }

    public void setPosicionTextoSql(int posicionTextoSql) {
        this.posicionTextoSql = posicionTextoSql;
    }

    public void insertartexto(int posicion, String texto) {
        try {
            textoSql.getDocument().insertString(posicion, texto, null);
        } catch (BadLocationException ex) {

        }
    }

    public void setGestorBBDD(String gestorBBDD) {
        this.gestorBBDD = gestorBBDD;
        CambiarColorTexto cambiarcolor = (CambiarColorTexto) textoSql.getStyledDocument();
        cambiarcolor.setTipoSQL(gestorBBDD);
        textoSql.setStyledDocument(cambiarcolor);
    }

    public PantallaSqlconTabs(PantallaDocumentum parent, boolean modal, String gestor) {
        gestorBBDD = gestor;
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
        checkExportarExcel.setEnabled(false);
        textoNumLineasExcel.setEnabled(false);
        StyleConstants.setForeground(AtributoRojo, Color.RED);
        StyleConstants.setForeground(AtributoAzul, Color.BLUE);
        StyleConstants.setForeground(AtributoNegro, Color.BLACK);
        StyleConstants.setForeground(cwStyle, Color.BLUE);
        StyleConstants.setItalic(cwStyle, true);
        StyleConstants.setBold(cwStyle, true);
        StyleConstants.setForeground(comillaStyle, Color.RED);
        StyleConstants.setBold(comillaStyle, true);
        int tamletra = textoSql.getFont().getSize();
        textoSql.setFont(new Font(textoSql.getFont().getFontName(), Font.BOLD, tamletra + 2));
        CambiarColorTexto cambiarcolor = (CambiarColorTexto) textoSql.getStyledDocument();
        cambiarcolor.setTipoSQL(gestorBBDD);
        textoSql.setStyledDocument(cambiarcolor);
        textoMultiplesLogs.setPreferredSize(new Dimension(panelSql.getWidth() - panelMultipleExcel.getWidth() - 12 - 17 - 18, textoMultiplesLogs.getHeight()));
    }

    public PantallaSqlconTabs(PantallaDocumentum parent, boolean modal) {
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
        checkExportarExcel.setEnabled(false);
        textoNumLineasExcel.setEnabled(false);
        StyleConstants.setForeground(AtributoRojo, Color.RED);
        StyleConstants.setForeground(AtributoAzul, Color.BLUE);
        StyleConstants.setForeground(AtributoNegro, Color.BLACK);
        StyleConstants.setForeground(cwStyle, Color.BLUE);
        StyleConstants.setBold(cwStyle, true);
//        textoSql.setFont(new Font("Courier New", Font.PLAIN, 12));
        CambiarColorTexto cambiarcolor = (CambiarColorTexto) textoSql.getStyledDocument();
        cambiarcolor.setTipoSQL(gestorBBDD);
        textoSql.setStyledDocument(cambiarcolor);

    }

    protected static Image getLogo() {
        //   java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/documentum_logo_mini.gif");
        java.net.URL imgURL = PantallaSqlconTabs.class.getClassLoader().getResource("es/documentum/imagenes/sql-nativo.png");

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
        opcionFormato = new javax.swing.JCheckBoxMenuItem();
        popupDatos = new javax.swing.JPopupMenu();
        opcionCopiarValor = new javax.swing.JMenuItem();
        opcionExportarExcel = new javax.swing.JMenuItem();
        opcionSeleccionarColumna = new javax.swing.JMenuItem();
        popupHistorial = new javax.swing.JPopupMenu();
        opcionVaciarHistorial = new javax.swing.JMenuItem();
        panelSql = new javax.swing.JPanel();
        botonConsultar = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboHistorial = new javax.swing.JComboBox();
        panelMultipleExcel = new javax.swing.JPanel();
        checkExportarExcel = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        textoNumLineasExcel = new javax.swing.JFormattedTextField();
        checkMultiplesSqls = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        textoListaSQL = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        textoSql = new javax.swing.JTextPane(new CambiarColorTexto(defaultStyle, cwStyle, comillaStyle, "documentum"));
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        textoMultiplesLogs = new javax.swing.JTextPane();
        panelEstado = new javax.swing.JPanel();
        etiquetaEstado = new javax.swing.JLabel();
        panelTabResultados = new javax.swing.JTabbedPane();
        panelResultado = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();
        barraMenu = new javax.swing.JMenuBar();
        menuOpciones = new javax.swing.JMenu();
        opcionConsultar = new javax.swing.JMenuItem();
        opcionSalir = new javax.swing.JMenuItem();

        opcionCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/copiar.png"))); // NOI18N
        opcionCopiar.setText("Copiar Ctrl+C");
        opcionCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarActionPerformed(evt);
            }
        });
        popupEditar.add(opcionCopiar);

        opcionPegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/pegar-doc.png"))); // NOI18N
        opcionPegar.setText("Pegar  Ctrl+V");
        opcionPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionPegarActionPerformed(evt);
            }
        });
        popupEditar.add(opcionPegar);

        opcionFormato.setText("Dar formato");
        opcionFormato.setToolTipText("Poner o quitar formato a la(s) Sql(s)");
        opcionFormato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/formato-sql.png"))); // NOI18N
        opcionFormato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionFormatoActionPerformed(evt);
            }
        });
        popupEditar.add(opcionFormato);

        opcionCopiarValor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/copiar-todo.png"))); // NOI18N
        opcionCopiarValor.setText("Copiar Valor");
        opcionCopiarValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarValorActionPerformed(evt);
            }
        });
        popupDatos.add(opcionCopiarValor);

        opcionExportarExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/excel-24.gif"))); // NOI18N
        opcionExportarExcel.setText("Exportar a Excel");
        opcionExportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionExportarExcelActionPerformed(evt);
            }
        });
        popupDatos.add(opcionExportarExcel);

        opcionSeleccionarColumna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/lista-seleccion.png"))); // NOI18N
        opcionSeleccionarColumna.setText("Seleccionar Columna");
        opcionSeleccionarColumna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionSeleccionarColumnaActionPerformed(evt);
            }
        });
        popupDatos.add(opcionSeleccionarColumna);

        opcionVaciarHistorial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/borrar_peq.png"))); // NOI18N
        opcionVaciarHistorial.setText("Vaciar Historial de SQL");
        opcionVaciarHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionVaciarHistorialActionPerformed(evt);
            }
        });
        popupHistorial.add(opcionVaciarHistorial);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Consultas a Documentum por SQL nativa");
        setMinimumSize(new java.awt.Dimension(1084, 657));

        panelSql.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelSql.setMinimumSize(new java.awt.Dimension(1084, 380));
        panelSql.setPreferredSize(new java.awt.Dimension(1084, 380));

        botonConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/ejecutar_sql_peq.png"))); // NOI18N
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

        jLabel1.setText("Salida");

        jLabel2.setText("SQL");

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

        panelMultipleExcel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 153, 255), 1, true));
        panelMultipleExcel.setMaximumSize(new java.awt.Dimension(165, 148));
        panelMultipleExcel.setMinimumSize(new java.awt.Dimension(165, 148));

        checkExportarExcel.setText("Exportar a Excel");

        jLabel5.setText("Límite de registros Excel");

        textoNumLineasExcel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        textoNumLineasExcel.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textoNumLineasExcel.setText("12000");
        textoNumLineasExcel.setToolTipText("Un número superior a 12.000 línea hará muy lento el proceso");

        checkMultiplesSqls.setText("Multiples SQLs");
        checkMultiplesSqls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMultiplesSqlsActionPerformed(evt);
            }
        });

        jLabel4.setText("(SQLs separadas por ; )");

        javax.swing.GroupLayout panelMultipleExcelLayout = new javax.swing.GroupLayout(panelMultipleExcel);
        panelMultipleExcel.setLayout(panelMultipleExcelLayout);
        panelMultipleExcelLayout.setHorizontalGroup(
            panelMultipleExcelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMultipleExcelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMultipleExcelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelMultipleExcelLayout.createSequentialGroup()
                        .addGroup(panelMultipleExcelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkExportarExcel)
                            .addComponent(textoNumLineasExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkMultiplesSqls)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelMultipleExcelLayout.setVerticalGroup(
            panelMultipleExcelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMultipleExcelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkMultiplesSqls, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(checkExportarExcel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(2, 2, 2)
                .addComponent(textoNumLineasExcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        textoListaSQL.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                textoListaSQLCaretUpdate(evt);
            }
        });
        textoListaSQL.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                textoListaSQLCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });

        jScrollPane2.setAutoscrolls(true);

        textoSql.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                textoSqlMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(textoSql);

        jScrollPane3.setAutoscrolls(true);

        textoMultiplesLogs.setEditable(false);
        textoMultiplesLogs.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        textoMultiplesLogs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                textoMultiplesLogsMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(textoMultiplesLogs);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout panelSqlLayout = new javax.swing.GroupLayout(panelSql);
        panelSql.setLayout(panelSqlLayout);
        panelSqlLayout.setHorizontalGroup(
            panelSqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSqlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSqlLayout.createSequentialGroup()
                        .addGroup(panelSqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSqlLayout.createSequentialGroup()
                                .addComponent(comboHistorial, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textoListaSQL, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelSqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelMultipleExcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelSqlLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(panelSqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(botonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(17, 17, 17))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelSqlLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {botonConsultar, botonSalir});

        panelSqlLayout.setVerticalGroup(
            panelSqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSqlLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelSqlLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoListaSQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelSqlLayout.createSequentialGroup()
                        .addComponent(botonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(panelMultipleExcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );

        panelSqlLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {botonConsultar, botonSalir});

        panelSqlLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {comboHistorial, textoListaSQL});

        panelEstado.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelEstadoLayout = new javax.swing.GroupLayout(panelEstado);
        panelEstado.setLayout(panelEstadoLayout);
        panelEstadoLayout.setHorizontalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelEstadoLayout.setVerticalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        panelTabResultados.setFocusCycleRoot(true);

        panelResultado.setAutoscrolls(true);

        tablaResultados.setAutoCreateRowSorter(true);
        tablaResultados.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
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

        panelTabResultados.addTab("SQL", panelResultado);

        menuOpciones.setText("Opciones");

        opcionConsultar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/ejecutar_sql_peq.png"))); // NOI18N
        opcionConsultar.setText("Ejecutar");
        opcionConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionConsultarActionPerformed(evt);
            }
        });
        menuOpciones.add(opcionConsultar);

        opcionSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        opcionSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        opcionSalir.setText("Salir");
        opcionSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionSalirActionPerformed(evt);
            }
        });
        menuOpciones.add(opcionSalir);

        barraMenu.add(menuOpciones);

        setJMenuBar(barraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelSql, javax.swing.GroupLayout.DEFAULT_SIZE, 1113, Short.MAX_VALUE)
            .addComponent(panelTabResultados, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelSql, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(panelTabResultados, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(panelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelTabResultados.getAccessibleContext().setAccessibleName("Pestaña");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ejecutarMultiplesSQL(String mutiplesDqls) {
        final StringTokenizer misSql = new StringTokenizer(mutiplesDqls, ";");
        int numlineas = 0;
        StringTokenizer numDqls = new StringTokenizer(mutiplesDqls, ";");
        while (numDqls.hasMoreElements()) {
            numlineas++;
            numDqls.nextToken();
        }
        scroll = new JScrollPane[numlineas];
        tabla = new JTable[numlineas];
        final MouseListener[] mouselistener = new MouseListener[numlineas];
        int numtab = panelTabResultados.getTabCount();
        for (int t = numtab - 1; t > 0; t--) {
            panelTabResultados.remove(t);
        }
        new Thread() {
            public void run() {
                barradocum = new PantallaBarra(PantallaSqlconTabs.this, false);
                barradocum.setTitle("Ejecutar múltiples SQLs ...");
                barradocum.barra.setIndeterminate(true);
                barradocum.botonParar.setVisible(false);
                barradocum.setLabelMensa("");
                barradocum.barra.setOpaque(true);
                barradocum.barra.setStringPainted(false);
                barradocum.validate();
                barradocum.setDefaultCloseOperation(HIDE_ON_CLOSE);
                barradocum.setVisible(true);
                try {
                    textoMultiplesLogs.setText("");
                    StyledDocument textoLogSQLMultiple = textoMultiplesLogs.getStyledDocument();
                    int contsqls = 0;
                    int tablasResultado = 1;
                    String repo = gsesion.getDocbaseName();
                    while (misSql.hasMoreElements()) {
                        contsqls++;
                        String sql = misSql.nextToken();
                        if (sql.trim().isEmpty()) {
                            continue;
                        }
                        sql = sql.replaceAll("\n", "");
                        sql = sql.replaceAll("\r", "");
                        sql = sql.replaceAll("\t", " ").trim();
                        String sqlfinal = sql.replaceAll("'", "''");
                        String numreg = "0";
                        if (sql.toUpperCase().startsWith("SELECT")) {
                            sqlfinal = "EXECUTE exec_select_sql with query='" + sqlfinal + "'";
                        } else {
                            sqlfinal = "EXECUTE exec_sql with query='" + sqlfinal + "'";
                        }

                        barradocum.setLabelMensa(sqlfinal);
                        Object[][] datos = new Object[0][0];
                        Object[] cabecera = new Object[0];
                        Integer[] tamcabecera = new Integer[0];
                        IDfCollection col = utildocum.ejecutarDql(sqlfinal, gsesion);
                        if (!utildocum.dameError().equals("")) {
                            textoLogSQLMultiple.insertString(textoLogSQLMultiple.getLength(), sqlfinal + "  -  ", AtributoNegro);
                            textoLogSQLMultiple.insertString(textoLogSQLMultiple.getLength(), utildocum.dameError(), AtributoRojo);
                        } else {
                            String queEs = "encontrado(s) en " + repo;
                            if (sqlfinal.toLowerCase().trim().startsWith("update")) {
                                queEs = "actualizado(s) en " + repo;
                            }
                            if (sqlfinal.toLowerCase().trim().startsWith("insert")) {
                                queEs = "insertado(s) en " + repo;
                            }
                            if (sqlfinal.toLowerCase().trim().startsWith("delete")) {
                                queEs = "borrado(s) en " + repo;
                            }

                            Long cont = 0L;
                            String rutaDqls = "";
                            int limitelineas = Integer.parseInt(textoNumLineasExcel.getText());
                            if (col != null) {
                                ArrayList lista = new ArrayList();
                                while (col.next()) {
                                    lista.add(col.getTypedObject());
                                    cont++;
                                }
                                int cuantas = lista.size();
                                IDfTypedObject primerafila = (IDfTypedObject) lista.get(0);
                                int tam = primerafila.getAttrCount();
                                cabecera = new Object[tam];
                                tamcabecera = new Integer[tam];
                                datos = new Object[cuantas][tam];
                                for (int l = 0; l < tam; l++) {
                                    cabecera[l] = primerafila.getAttr(l).getName();
                                    tamcabecera[l] = 0;
                                }

                                for (int i = 0; i < cont; i++) {
                                    IDfTypedObject row = (IDfTypedObject) lista.get(i);

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
                                DefaultTableModel modeloLotes = new DefaultTableModel(datos, cabecera) {
                                    @Override
                                    public boolean isCellEditable(int fila, int columna) {
                                        return false;
                                    }
                                };
                                if (datos.length > 0) {
                                    modeloLotes.setRowCount(datos.length);
                                }

                                if (contsqls == 1) {
                                    mostrarTablaResultado(tablaResultados, modeloLotes, tamcabecera, false, false);
                                    textoLogSQLMultiple.insertString(textoLogSQLMultiple.getLength(), "SQL - ", AtributoNegro);
                                } else {
                                    tabla[tablasResultado] = new JTable();
                                    scroll[tablasResultado] = new JScrollPane();
                                    scroll[tablasResultado].add(tabla[tablasResultado]);
                                    tabla[tablasResultado].setName("SQL" + tablasResultado);
                                    tabla[tablasResultado].setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                                    scroll[tablasResultado].setAutoscrolls(true);
                                    tabla[tablasResultado].setAutoCreateRowSorter(true);
                                    tabla[tablasResultado].setColumnSelectionAllowed(true);
                                    tabla[tablasResultado].setFont(tablaResultados.getFont());
                                    scroll[tablasResultado].setViewportView(tabla[tablasResultado]);
                                    tabla[tablasResultado].getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                                    tabla[tablasResultado].setModel(modeloLotes);
                                    final int posicion = tablasResultado;
                                    tabla[tablasResultado].addMouseListener(new java.awt.event.MouseAdapter() {
                                        public void mousePressed(java.awt.event.MouseEvent evt) {
                                            tablaMousePressed(evt);
                                        }

                                        public void tablaMousePressed(MouseEvent evt) {
                                            if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                                                botonderecho = true;
                                                componente = "SQL" + posicion;
                                                evt.getComponent().addMouseListener(mouselistener[posicion]);
                                                popupmenu(evt);
                                            }
                                        }
                                    });
                                    panelTabResultados.addTab("SQL" + tablasResultado, scroll[tablasResultado]);
                                    mostrarTablaResultado(tabla[tablasResultado], modeloLotes, tamcabecera, false, false);
                                    textoLogSQLMultiple.insertString(textoLogSQLMultiple.getLength(), "\nSQL" + tablasResultado + " - ", AtributoNegro);
                                    tablasResultado++;
                                }
                                if (cont < limitelineas && checkExportarExcel.isSelected()) {
                                    rutaDqls = ventanapadre.dirbase + util.separador() + "SQLS" + util.separador();
                                    util.crearDirectorio(rutaDqls);
                                    barradocum.setLabelMensa("Generando Excel - " + sqlfinal);
                                    util.exportarArrayListAExcel(lista, rutaDqls + "SQL - " + repo + " - " + col.getObjectSession().getSessionConfig().getId("session_id") + "-" + SQLsExcel + ".xlsx", "SQL - " + col.getObjectSession().getSessionConfig().getId("session_id") + "-" + SQLsExcel, sqlfinal);
                                    SQLsExcel++;
                                }
                            }

                            textoLogSQLMultiple.insertString(textoLogSQLMultiple.getLength(), sqlfinal.trim(), AtributoNegro);
                            textoLogSQLMultiple.insertString(textoLogSQLMultiple.getLength(), "   -  " + cont + " registro(s) " + queEs, AtributoAzul);
                            if (cont < limitelineas && checkExportarExcel.isSelected()) {
                                textoLogSQLMultiple.insertString(textoLogSQLMultiple.getLength(), "\nGenerado fichero excel  " + rutaDqls + "SQL - " + repo + " - " + col.getObjectSession().getSessionConfig().getId("session_id") + "-" + (SQLsExcel - 1) + ".xlsx", AtributoNegro);
                            }

                        }
                        if (col != null) {
                            col.close();
                        }
                    }
                    barradocum.dispose();
                } catch (Exception ex) {
                    Utilidades.escribeLog("Error (EjecutarMultiplesSQL) - " + ex.getMessage());
                }
            }

        }.start();

    }

    private void opcionConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionConsultarActionPerformed
        if (!textoSql.getText().isEmpty()) {
            ejecutarDqldeSQL(textoSql.getText(), "0");
        }
    }//GEN-LAST:event_opcionConsultarActionPerformed

    private void opcionSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionSalirActionPerformed
        salir();
    }//GEN-LAST:event_opcionSalirActionPerformed

    private void opcionCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarActionPerformed
        if (componente.equals("textoSql")) {
            if (textoSql.getSelectedText() == null) {
                Utilidades.copiarTextoPortapapeles(textoSql.getText());
            } else {
                Utilidades.copiarTextoPortapapeles(textoSql.getSelectedText());
            }
        }
        if (componente.equals("textoMultiplesLogs")) {
            if (textoMultiplesLogs.getSelectedText() == null) {
                Utilidades.copiarTextoPortapapeles(textoMultiplesLogs.getText());
            } else {
                Utilidades.copiarTextoPortapapeles(textoMultiplesLogs.getSelectedText());
            }
        }
    }//GEN-LAST:event_opcionCopiarActionPerformed

    private void opcionPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionPegarActionPerformed
        if (componente.equals("textoSql")) {
//            if (checkMultiplesSqls.isSelected()) {
            int caretPos = textoSql.getCaretPosition();
            try {
                textoSql.getDocument().insertString(caretPos, Utilidades.pegarTextoPortapapeles(), null);
            } catch (BadLocationException ex) {
                Utilidades.escribeLog("Error al pegar texto en 'textoSql'" + ex.getMessage());
            }
//            } else {
//                textoSql.setText(Utilidades.pegarTextoPortapapeles());
//            }
        }
    }//GEN-LAST:event_opcionPegarActionPerformed

    private void opcionCopiarValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarValorActionPerformed
        if (componente.equals("tablaResultados")) {
            Utilidades.copiarTextoPortapapeles(tablaResultados.getModel().getValueAt(tablaResultados.convertRowIndexToModel(tablaResultados.getSelectedRow()), tablaResultados.getSelectedColumn()).toString());
        }
        if (componente.startsWith("SQL")) {
            int pos = Integer.parseInt(componente.substring(3, componente.length()));
            Utilidades.copiarTextoPortapapeles(tabla[pos].getModel().getValueAt(tabla[pos].convertRowIndexToModel(tabla[pos].getSelectedRow()), tabla[pos].getSelectedColumn()).toString());
        }

    }//GEN-LAST:event_opcionCopiarValorActionPerformed

    private void tablaResultadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaResultadosMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "tablaResultados";
            popupmenu(evt);
        }
    }//GEN-LAST:event_tablaResultadosMousePressed

    private void opcionExportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionExportarExcelActionPerformed
        String fichero = "";
        if (componente.equals("tablaResultados")) {
            if (tablaResultados.getModel().getRowCount() > 0) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Seleccionar directorio y nombre de fichero");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    fichero = chooser.getSelectedFile().toString();
                    if (!fichero.toLowerCase().endsWith(".xlsx")) {
                        fichero = fichero + ".xlsx";
                    }
                    String hoja = "Consulta - SQL";
                    if (textoSql.getText().trim().toLowerCase().startsWith("describe")) {
                        hoja = textoSql.getText().trim();
                    }
                    util.exportarAExcel(tablaResultados, fichero, hoja);
                } else {
                    Utilidades.escribeLog("No se ha seleccionado el fichero de salida ");
                }
            }
        }
        if (componente.startsWith("SQL")) {
            int pos = Integer.parseInt(componente.substring(3, componente.length()));
            if (tabla[pos].getModel().getRowCount() > 0) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Seleccionar directorio y nombre de fichero");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    fichero = chooser.getSelectedFile().toString();
                    if (!fichero.toLowerCase().endsWith(".xlsx")) {
                        fichero = fichero + ".xlsx";
                    }
                    String hoja = "SQL" + pos;
                    util.exportarAExcel(tabla[pos], fichero, hoja);
                } else {
                    Utilidades.escribeLog("No se ha seleccionado el fichero de salida ");
                }
            }
        }
    }//GEN-LAST:event_opcionExportarExcelActionPerformed

    private void opcionVaciarHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionVaciarHistorialActionPerformed

        try {
            DefaultComboBoxModel modelo = new DefaultComboBoxModel();
            comboHistorial.setModel(modelo);
            String dirhist = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador() + "historial-sql.log";
            BufferedWriter bw = new BufferedWriter(new FileWriter(dirhist));
            bw.write("");
            bw.close();
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_opcionVaciarHistorialActionPerformed

    private void opcionSeleccionarColumnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionSeleccionarColumnaActionPerformed
        if (componente.endsWith("tablaResultados")) {
            tablaResultados.setRowSelectionInterval(0, tablaResultados.getRowCount() - 1);
        }
        if (componente.startsWith("SQL")) {
            int pos = Integer.parseInt(componente.substring(3, componente.length()));
            tabla[pos].setRowSelectionInterval(0, tabla[pos].getRowCount() - 1);
        }
    }//GEN-LAST:event_opcionSeleccionarColumnaActionPerformed

    private void textoMultiplesLogsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textoMultiplesLogsMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "textoMultiplesLogs";
            popupmenu(evt);
        }
    }//GEN-LAST:event_textoMultiplesLogsMousePressed

    private void comboHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboHistorialActionPerformed
        String valor = (String) comboHistorial.getSelectedItem();
        valor = valor.trim();
        if (textoSql.getText().isEmpty() || !textoSql.getText().equals(valor)) {
            if (!valor.isEmpty()) {
                if (checkMultiplesSqls.isSelected()) {
                    int count = StringUtils.countMatches(valor, ";");
                    if (count > 1) {
                        StringTokenizer st = new StringTokenizer(valor, ";");
                        if (!textoSql.getText().isEmpty()) {
                            textoSql.setText(textoSql.getText() + (textoSql.getText().endsWith(";") ? "\n" : ";\n"));
                        }
                        int num = 1;
                        while (st.hasMoreElements()) {
                            String elemento = st.nextToken();
                            if (!elemento.isEmpty()) {
                                if (num < count) {
                                    textoSql.setText(textoSql.getText() + elemento.trim() + ";\n");
                                } else {
                                    textoSql.setText(textoSql.getText() + elemento.trim() + ";");
                                }
                            }
                            num++;
                        }
                    } else {
                        if (textoSql.getText().isEmpty()) {
                            textoSql.setText(valor.endsWith(";") ? valor.trim() : valor.trim() + ";");
                        } else {
                            textoSql.setText(textoSql.getText() + (valor.endsWith(";") ? "\n" + valor.trim() : "\n" + valor.trim() + ";"));
                        }
                    }
                } else {
                    textoSql.setText(valor.trim());
                }
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

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        salir();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void botonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarActionPerformed
        if (!textoSql.getText().isEmpty()) {
            if (!checkMultiplesSqls.isSelected()) {
                ejecutarDqldeSQL(textoSql.getText(), "0");
            } else {
                ejecutarMultiplesSQL(textoSql.getText());
            }
        }
    }//GEN-LAST:event_botonConsultarActionPerformed

    private void textoListaSQLCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_textoListaSQLCaretUpdate
        ArrayList listaSeleccion = util.buscarEnLista(listaCombo, textoListaSQL.getText());
        DefaultComboBoxModel modelo = new DefaultComboBoxModel(listaSeleccion.toArray());
        comboHistorial.setModel(modelo);
        comboHistorial.setPopupVisible(true);

        if (textoListaSQL.getText().isEmpty()) {
            cargarComboHistorial();
            comboHistorial.setPopupVisible(true);
        }
    }//GEN-LAST:event_textoListaSQLCaretUpdate

    private void textoListaSQLCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_textoListaSQLCaretPositionChanged

    }//GEN-LAST:event_textoListaSQLCaretPositionChanged

    private void checkMultiplesSqlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMultiplesSqlsActionPerformed
        if (checkMultiplesSqls.isSelected()) {
            checkExportarExcel.setEnabled(true);
            textoNumLineasExcel.setEnabled(true);
        } else {
            checkExportarExcel.setEnabled(false);
            textoNumLineasExcel.setEnabled(false);
            int numtab = panelTabResultados.getTabCount();
            for (int t = numtab - 1; t > 0; t--) {
                panelTabResultados.remove(t);
            }
        }
    }//GEN-LAST:event_checkMultiplesSqlsActionPerformed

    private void textoSqlMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textoSqlMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "textoSql";
            popupmenu(evt);
        }
    }//GEN-LAST:event_textoSqlMousePressed

    private void opcionFormatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionFormatoActionPerformed
        if (opcionFormato.isSelected()) {
            if (textoSql.getSelectedText() != null) {
                if (!textoSql.getSelectedText().isEmpty()) {
                    FormatoSql formato = new FormatoSql();
                    int caretPos = textoSql.getCaretPosition();
                    String cadena = textoSql.getSelectedText();
                    try {
                        textoSql.getDocument().remove(caretPos, cadena.length());
                        textoSql.getDocument().insertString(caretPos, formato.format(cadena, gestorBBDD), null);
                    } catch (Exception ex) {
                        Utilidades.escribeLog("Error al pegar texto formateado en 'textoSql' - " + ex.getMessage());
                    }
                }
            } else {
                if (textoSql.getText() != null && !textoSql.getText().isEmpty()) {
                    FormatoSql formato = new FormatoSql();
                    textoSql.setText(formato.format(textoSql.getText(), gestorBBDD));
                }
            }
        } else {
            String textohist = textoSql.getText();
            textoSql.setText(textohist.trim().replaceAll("\\s{2,}", " ").replaceAll("(\r\n|\n)", " "));
        }
    }//GEN-LAST:event_opcionFormatoActionPerformed

    private void ejecutarDqldeSQL(String pdql, String numreg) {
        String sql = pdql;

        if (sql.contains(";")) {
            sql = sql.substring(0, sql.indexOf(";"));
        }

        if (sql.trim().isEmpty()) {
            return;
        }
        sql = sql.replaceAll("\n", "");
        sql = sql.replaceAll("\r", "");
        sql = sql.replaceAll("\t", " ").trim();
        String sqlfinal = sql.replaceAll("'", "''");

        if (sqlfinal.toUpperCase().startsWith("SELECT")) {
            sqlfinal = "EXECUTE exec_select_sql with query='" + sqlfinal + "'";
        } else {
            sqlfinal = "EXECUTE exec_sql with query='" + sqlfinal + "'";
        }
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
        etiquetaEstado.setText("");
        textoMultiplesLogs.setText("");
        ERROR = "";
        textoMultiplesLogs.setForeground(Color.BLACK);

        new Thread() {
            public void run() {
                DefaultTableModel modeloLotes = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int fila, int columna) {
                        return false;
                    }
                };

                tablaResultados.setModel(modeloLotes);
                String dirdfc = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();
                barradocum = new PantallaBarra(PantallaSqlconTabs.this, false);
                barradocum.setTitle("Consultando en Documentum ...");
                barradocum.barra.setIndeterminate(true);
                barradocum.botonParar.setVisible(false);
                barradocum.setLabelMensa("");
                barradocum.barra.setOpaque(true);
                barradocum.barra.setStringPainted(false);
                barradocum.validate();
                barradocum.setVisible(true);
                Boolean esTabla = dql.toLowerCase().contains(" table ");
                Boolean esTipo = dql.toLowerCase().trim().startsWith("describe") && !esTabla;

                try {
                    Object[][] datos = new Object[0][0];
                    Object[] cabecera = new Object[0];
                    Integer[] tamcabecera = new Integer[0];
                    int cont = 0;

                    IDfCollection col = utildocum.ejecutarDql(dql, gsesion);
                    if (!utildocum.dameError().equals("")) {
                        textoMultiplesLogs.setText(utildocum.dameError()); //kk
                        barradocum.dispose();
                        return;
                    }

                    ArrayList filas = new ArrayList();
                    while (col.next()) {
                        filas.add(col.getTypedObject());
                    }
                    col.close();

                    if (filas.size() <= 0) {
                        etiquetaEstado.setText("0 Registro(s) encontrado(s) ");
                        textoMultiplesLogs.setText("0 Registro(s) encontrado(s) ");
                        barradocum.dispose();
                        return;
                    }

                    cont = filas.size();
                    IDfTypedObject primerafila = (IDfTypedObject) filas.get(0);
                    int tam = primerafila.getAttrCount();
                    cabecera = new Object[tam];
                    tamcabecera = new Integer[tam];
                    datos = new Object[cont][tam];
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

                    String textohist = textoSql.getText();
                    textohist = textohist.trim().replaceAll("\\s{2,}", " ");
                    if (!buscarEnComboHistorial(textohist.replaceAll("(\r\n|\n)", " "))) {
                        try {
                            FileOutputStream historial = new FileOutputStream(new File(dirdfc + "historial-sql.log"), true);
                            historial.write(("\n" + textohist.replaceAll("(\r\n|\n)", " ")).getBytes());
                            historial.close();
                            cargarComboHistorial();
                        } catch (Exception ex) {
                        }
                    }

                    if (datos.length > 0) {
                        modeloLotes = new DefaultTableModel(datos, cabecera) {
                            @Override
                            public boolean isCellEditable(int fila, int columna) {
                                return false;
                            }
                        };

                        modeloLotes.setRowCount(datos.length);

                    }

                    int numtab = panelTabResultados.getTabCount();
                    for (int t = numtab - 1; t > 0; t--) {
                        panelTabResultados.remove(t);
                    }
                    mostrarTablaResultado(tablaResultados, modeloLotes, tamcabecera, esTabla, esTipo);
                    etiquetaEstado.setText(cont + " Registro(s) encontrado(s) ");
                    if (ERROR.isEmpty()) {
                        textoMultiplesLogs.setText(cont + " Registro(s) encontrado(s) ");
                    } else {
                        textoMultiplesLogs.setText(ERROR);
                        textoMultiplesLogs.setForeground(Color.RED);
                    }
                } catch (DfException ex) {
                    if (ERROR.isEmpty()) {
                        textoMultiplesLogs.setText(ex.getMessage());
                    } else {
                        textoMultiplesLogs.setText(ERROR);
                        textoMultiplesLogs.setForeground(Color.RED);
                        etiquetaEstado.setText("0 Registro(s) encontrado(s) ");
                    }

                }

                barradocum.dispose();
            }
        }.start();
    }

    private void mostrarTablaResultado(JTable tabla, DefaultTableModel modeloLotes, Integer[] tamcabecera, Boolean esTabla, Boolean esTipo) {
        tabla.setModel(modeloLotes);

        if (esTabla) {
            TableColumn columna = tabla.getColumnModel().getColumn(0);
            columna.setPreferredWidth(150);
            columna.setMinWidth(150);
            columna = tabla.getColumnModel().getColumn(1);
            columna.setPreferredWidth(350);
            columna.setMinWidth(250);
            tabla.setShowGrid(false);
            tabla.getTableHeader().setVisible(false);
        } else if (esTipo) {
            TableColumn columna = tabla.getColumnModel().getColumn(0);
            columna.setPreferredWidth(250);
            columna.setMinWidth(250);
            columna = tabla.getColumnModel().getColumn(1);
            columna.setPreferredWidth(150);
            columna.setMinWidth(150);
            tabla.setShowGrid(false);
            tabla.getTableHeader().setVisible(false);
        } else {
            Font font = tabla.getFont();
            FontMetrics fm = getFontMetrics(font);
            char c = "B".charAt(0);
            int tampixel = fm.charWidth(c);

            for (int i = 0; i < modeloLotes.getColumnCount(); i++) {
                TableColumn columna = tabla.getColumnModel().getColumn(i);
                columna.setPreferredWidth(tamcabecera[i] * tampixel);
                columna.setMinWidth(tamcabecera[i] * tampixel);
            }
            tabla.setShowGrid(true);
            tabla.getTableHeader().setVisible(true);
        }
        pintarTabla(tabla);
        tabla.doLayout();

    }

    private void pintarTabla(JTable tabla) {
        tabla.setDefaultRenderer(Object.class,
                new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                Color fg = null;
                Color bg = null;
                Color unselectedForeground = table.getSelectionForeground();
                Color unselectedBackground = table.getBackground();

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                table.getTableHeader().setFont(new Font(table.getTableHeader().getFont().getFontName(), Font.BOLD, table.getTableHeader().getFont().getSize()));

                setOpaque(true);
                setForeground(Color.BLACK);
                //  setBackground(new Color(245, 245, 245)); // gris claro
                if (table.getTableHeader().isVisible()) {
                    setBackground(Color.WHITE);
                } else {
                    setBackground(botonConsultar.getBackground());
                }
                String nombre = (String) table.getValueAt(row, 0);
                if (nombre.equals("Super Tipo")) {
                    //   setOpaque(true);
                    setForeground(Color.BLUE);
                }
                if (nombre.equals("Table Name:") || nombre.equals("Type Name:")) {
                    setForeground(Color.BLUE);
                    setFont(getFont().deriveFont(Font.BOLD));
                }

                if (isSelected) {
                    super.setForeground(fg == null ? table.getSelectionForeground() : fg);
                    super.setBackground(bg == null ? table.getSelectionBackground() : bg);
                } else {
                    Color background = unselectedBackground != null ? unselectedBackground : table.getBackground();
                    if (background == null || background instanceof javax.swing.plaf.UIResource) {
//                        Color alternateColor = DefaultLookup.getColor(this, ui, "Table.alternateRowColor");
                        //Color alternateColor = UIManager.getColor ( "Table.alternateRowColor" );
                        Color alternateColor = new Color(245, 245, 245); // gris claro;
                        if (alternateColor != null && row % 2 != 0) {
                            background = alternateColor;
                        }
                    }
                    super.setForeground(unselectedForeground != null ? unselectedForeground : table.getForeground());
                    super.setBackground(background);

                    if (nombre.equals("Super Tipo")) {
                        //   setOpaque(true);
                        setForeground(Color.BLUE);
                    }
                    if (nombre.equals("Table Name:") || nombre.equals("Type Name:")) {
                        setForeground(Color.BLUE);
                        setFont(getFont().deriveFont(Font.BOLD));
                    }
                }

                return this;
            }
        });
    }

    private String dameDescTipo(int tipo) {
        String valor = "";
        switch (tipo) {
            case 0:
                valor = "Boolean";
                break;
            case 1:
                valor = "Integer";
                break;
            case 2:
                valor = "String";
                break;
            case 3:
                valor = "ID";
                break;
            case 4:
                valor = "Date and Time";
                break;
            case 5:
                valor = "Double";
        }
        return valor;
    }

    private void cargarComboHistorial() {
        ArrayList comboBoxItems = new ArrayList();
        String dirhist = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador() + "historial-sql.log";
        BufferedReader br = null;

        try {
            String linea;
            br = new BufferedReader(new FileReader(dirhist));
            while ((linea = br.readLine()) != null) {
                comboBoxItems.add(linea);
                listaCombo.add(linea);
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

    private Boolean buscarEnComboHistorial(String texto) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) comboHistorial.getModel();
        return (util.buscarEnCombo(model, texto.trim()) != -1);
//        return (model.getIndexOf(texto.trim()) != -1);
    }

    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaSqlconTabs(ventanapadre, true).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JButton botonConsultar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JCheckBox checkExportarExcel;
    private javax.swing.JCheckBox checkMultiplesSqls;
    private javax.swing.JComboBox comboHistorial;
    private javax.swing.JLabel etiquetaEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenu menuOpciones;
    private javax.swing.JMenuItem opcionConsultar;
    private javax.swing.JMenuItem opcionCopiar;
    private javax.swing.JMenuItem opcionCopiarValor;
    private javax.swing.JMenuItem opcionExportarExcel;
    private javax.swing.JCheckBoxMenuItem opcionFormato;
    private javax.swing.JMenuItem opcionPegar;
    private javax.swing.JMenuItem opcionSalir;
    private javax.swing.JMenuItem opcionSeleccionarColumna;
    private javax.swing.JMenuItem opcionVaciarHistorial;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JPanel panelMultipleExcel;
    private javax.swing.JScrollPane panelResultado;
    private javax.swing.JPanel panelSql;
    private javax.swing.JTabbedPane panelTabResultados;
    private javax.swing.JPopupMenu popupDatos;
    private javax.swing.JPopupMenu popupEditar;
    private javax.swing.JPopupMenu popupHistorial;
    private javax.swing.JTable tablaResultados;
    private javax.swing.JTextField textoListaSQL;
    private javax.swing.JTextPane textoMultiplesLogs;
    private javax.swing.JFormattedTextField textoNumLineasExcel;
    private javax.swing.JTextPane textoSql;
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
        if (gsesion.isConnected()) {
            try {
                gsesion.disconnect();
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

                if (componente.startsWith("SQL")) {
                    int pos = Integer.parseInt(componente.substring(3, componente.length()));
//                    JScrollPane scroll = (JScrollPane) panelTabResultados.getComponent(pos - 1);
//                    JViewport jview = (JViewport) scroll.getComponent(0);
//                    JTable subTabla = (JTable) jview.getComponent(0);
                    if (row >= 0 && column >= 0 && tabla[pos].getModel().getRowCount() > 0) {
                        popupDatos.show(evt.getComponent(), evt.getX(), evt.getY());
                    }
                }
                if (componente.equals("tablaResultados")) {
                    if (row >= 0 && column >= 0 && tablaResultados.getModel().getRowCount() > 0) {
                        popupDatos.show(evt.getComponent(), evt.getX(), evt.getY());
                    }
                }
                opcionPegar.setEnabled(false);
            }
            if (evt.getSource().getClass().getName().equals("javax.swing.JTextArea")) {
                opcionPegar.setVisible(true);
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
            }
            if (evt.getSource().getClass().getName().equals("javax.swing.JTextPane")) {
                if (componente.equals("textoMultiplesLogs")) {
                    opcionPegar.setVisible(false);
                    opcionFormato.setVisible(false);
                } else {
                    opcionPegar.setVisible(true);
                    opcionFormato.setVisible(true);
                }

                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
            }
            if (evt.getSource() == comboHistorial) {
                popupHistorial.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }

    private IDfSession sesionDocumentum() {
        String dirdfc = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();
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
