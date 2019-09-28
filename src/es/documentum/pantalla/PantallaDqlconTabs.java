package es.documentum.pantalla;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfAttr;
import com.documentum.fc.common.IDfValue;
import es.documentum.Beans.Pistas;
import es.documentum.utilidades.CambiarColorTexto;
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
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
import java.util.Collections;
import java.util.StringTokenizer;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import org.apache.commons.lang.StringUtils;

public class PantallaDqlconTabs extends javax.swing.JFrame {

    Utilidades util = new Utilidades();
    UtilidadesDocumentum utildocum = new UtilidadesDocumentum();
    PantallaBarra barradocum = null;
    Boolean botonderecho = false;
    String componente = "";
    public static PantallaDocumentum ventanapadre = null;
    IDfSession gsesion = utildocum.sesionDocumentum();
    String ERROR = "";
    SimpleAttributeSet AtributoRojo = new SimpleAttributeSet();
    SimpleAttributeSet AtributoAzul = new SimpleAttributeSet();
    SimpleAttributeSet AtributoNegro = new SimpleAttributeSet();
    ArrayList listaCombo = new ArrayList();
    JScrollPane[] scroll;
    JTable[] tabla;
    int DQLsExcel = 1;
    StyleContext styleContext = new StyleContext();
    Style defaultStyle = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
    Style cwStyle = styleContext.addStyle("ConstantWidth", null);
    Style comillaStyle = styleContext.addStyle("ConstantWidth", null);
    ArrayList<Pistas> listapistasDqlInicio = utildocum.leerDatosAyuda("dql-inicio.xml");
    ArrayList<Pistas> listapistasExecute = utildocum.leerDatosAyuda("dql-execute.xml");
    PantallaAyudaLista pantallaayuda = new PantallaAyudaLista(this, true);
    ArrayList<String> listaPistasLimpia = new ArrayList<>();

    int posicionTextoDql = 0;
    String tipoLista = "";
    String tipoListaold = "";
    String tipoconsulta = "";
    String textoayudaescrito = "";
    String WHITESPACE = " \n\r\f\t";

    public String getTextoayudaescrito() {
        return textoayudaescrito;
    }

    public void borrarTextoDql(int pos, int tam) {
        try {
            if (pos >= 0 && tam >= 0) {
                textoDql.getDocument().remove(pos, tam);
            }
        } catch (BadLocationException ex) {

        }
    }

    public void asignarFocoTextoDql() {
        textoDql.requestFocus();
    }

    public void setTextoayudaescrito(String textoayudaescrito) {
        this.textoayudaescrito = textoayudaescrito;
    }

    public int getPosicionTextoDql() {
        return textoDql.getCaretPosition();
    }

    public void setPosicionCursorTextoDql(int pos) {
        final int posicion = pos;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textoDql.setCaretPosition(posicion);
            }
        });
    }

    public void setPosicionTextoDql(int posicionTextoDql) {
        this.posicionTextoDql = posicionTextoDql;
    }

    public void insertartexto(int posicion, String texto) {
        try {
            textoDql.getDocument().insertString(posicion, texto, null);
        } catch (BadLocationException ex) {

        }
    }

    public PantallaDqlconTabs(PantallaDocumentum parent, boolean modal) {
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
        textoNumReg.setText("0");
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
        int tamletra = textoDql.getFont().getSize();
        textoDql.setFont(new Font(textoDql.getFont().getFontName(), Font.BOLD, tamletra + 2));
        textoDql.getCaret().setMagicCaretPosition(new Point(0, 0));
    }

    protected static Image getLogo() {
        //   java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/documentum_logo_mini.gif");
        java.net.URL imgURL = PantallaDqlconTabs.class.getClassLoader().getResource("es/documentum/imagenes/dql-nuevo.png");

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
        panelDql = new javax.swing.JPanel();
        botonConsultar = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboHistorial = new javax.swing.JComboBox();
        checkDameSQL = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        textoNumReg = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        checkMultiplesDqls = new javax.swing.JCheckBox();
        checkExportarExcel = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        textoNumLineasExcel = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        textoListaDQL = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        textoDql = new javax.swing.JTextPane(new CambiarColorTexto(defaultStyle, cwStyle, comillaStyle,"documentum"));
        jPanel2 = new javax.swing.JPanel();
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
        opcionFormato.setToolTipText("Poner o quitar formato a la(s) Dql(s)");
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
        panelDql.setMinimumSize(new java.awt.Dimension(1108, 398));

        botonConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/ejecutar-dql.png"))); // NOI18N
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
        checkDameSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkDameSQLActionPerformed(evt);
            }
        });

        jLabel3.setText("Nº de registros de salida");

        textoNumReg.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        textoNumReg.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textoNumReg.setText("0");
        textoNumReg.setToolTipText("El valor 0 indica que no hay límite de líneas a devolver");
        textoNumReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoNumRegActionPerformed(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 153, 255), 1, true));
        jPanel1.setMinimumSize(new java.awt.Dimension(165, 138));

        checkMultiplesDqls.setText("Multiples DQLs");
        checkMultiplesDqls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMultiplesDqlsActionPerformed(evt);
            }
        });

        checkExportarExcel.setText("Exportar a Excel");

        jLabel5.setText("Límite de registros Excel");

        textoNumLineasExcel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        textoNumLineasExcel.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textoNumLineasExcel.setText("12000");
        textoNumLineasExcel.setToolTipText("Un número superior a 12.000 línea hará muy lento el proceso");

        jLabel4.setText("(DQLs separadas por ; )");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkMultiplesDqls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkExportarExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(textoNumLineasExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(checkMultiplesDqls, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkExportarExcel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(2, 2, 2)
                .addComponent(textoNumLineasExcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        textoListaDQL.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                textoListaDQLCaretUpdate(evt);
            }
        });
        textoListaDQL.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                textoListaDQLCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });

        textoDql.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                textoDqlMousePressed(evt);
            }
        });
        textoDql.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textoDqlKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textoDqlKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(textoDql);

        textoMultiplesLogs.setEditable(false);
        textoMultiplesLogs.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        textoMultiplesLogs.setAutoscrolls(false);
        textoMultiplesLogs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                textoMultiplesLogsMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(textoMultiplesLogs);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout panelDqlLayout = new javax.swing.GroupLayout(panelDql);
        panelDql.setLayout(panelDqlLayout);
        panelDqlLayout.setHorizontalGroup(
            panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDqlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDqlLayout.createSequentialGroup()
                        .addGroup(panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDqlLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelDqlLayout.createSequentialGroup()
                                .addComponent(comboHistorial, 0, 629, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textoListaDQL, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDqlLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(botonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(checkDameSQL, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textoNumReg, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelDqlLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelDqlLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {botonConsultar, botonSalir});

        panelDqlLayout.setVerticalGroup(
            panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDqlLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addGroup(panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDqlLayout.createSequentialGroup()
                        .addComponent(botonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkDameSQL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(textoNumReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDqlLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoListaDQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        panelDqlLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {botonConsultar, botonSalir});

        panelDqlLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {comboHistorial, textoListaDQL});

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

        panelTabResultados.addTab("DQL", panelResultado);

        menuOpciones.setText("Opciones");

        opcionConsultar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/ejecutar-dql.png"))); // NOI18N
        opcionConsultar.setText("Ejecutar");
        opcionConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionConsultarActionPerformed(evt);
            }
        });
        menuOpciones.add(opcionConsultar);

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
            .addComponent(panelDql, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTabResultados, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelDql, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(panelTabResultados, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(panelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelTabResultados.getAccessibleContext().setAccessibleName("Pestaña");

        getAccessibleContext().setAccessibleParent(this);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ejecutarMultiplesDQL(String mutiplesDqls) {
        final StringTokenizer misDql = new StringTokenizer(mutiplesDqls, ";");
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
                barradocum = new PantallaBarra(PantallaDqlconTabs.this, false);
                barradocum.setTitle("Ejecutar múltiples DQLs ...");
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
                    StyledDocument textoLogDQLMultiple = textoMultiplesLogs.getStyledDocument();
                    int contdqls = 0;
                    int tablasResultado = 1;
                    String repo = gsesion.getDocbaseName();
                    while (misDql.hasMoreElements()) {
                        contdqls++;
                        String dql = misDql.nextToken();

                        if (dql.isEmpty() || dql.equals("\n") || dql.length() < 9) {
                            continue;
                        }
                        if (dql.toLowerCase().trim().startsWith("describe")) {
                            textoLogDQLMultiple.insertString(textoLogDQLMultiple.getLength(), dql + "  -  ", AtributoNegro);
                            textoLogDQLMultiple.insertString(textoLogDQLMultiple.getLength(), "  -  Las sentencias 'Describe' solo se pueden ejecutar como DQL simples", AtributoRojo);
                            continue;
                        }

                        String numreg = textoNumReg.getText();
                        final long numregsalida = Long.parseLong(numreg);
                        if (numregsalida > 0 && dql.toLowerCase().trim().startsWith("select")) {
                            dql = dql + " enable (return_top " + numreg + ")";
                        }
                        barradocum.setLabelMensa(dql);
                        Object[][] datos = new Object[0][0];
                        Object[] cabecera = new Object[0];
                        Integer[] tamcabecera = new Integer[0];
                        IDfCollection col = utildocum.ejecutarDql(dql, gsesion);
                        if (!utildocum.dameError().equals("")) {
                            textoLogDQLMultiple.insertString(textoLogDQLMultiple.getLength(), dql + "  -  ", AtributoNegro);
                            textoLogDQLMultiple.insertString(textoLogDQLMultiple.getLength(), utildocum.dameError(), AtributoRojo);
                        } else {
                            String queEs = "encontrado(s) en " + repo;
                            if (dql.toLowerCase().trim().startsWith("update")) {
                                queEs = "actualizado(s) en " + repo;
                            }
                            if (dql.toLowerCase().trim().startsWith("insert")) {
                                queEs = "insertado(s) en " + repo;
                            }
                            if (dql.toLowerCase().trim().startsWith("delete")) {
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

                                if (contdqls == 1) {
                                    mostrarTablaResultado(tablaResultados, modeloLotes, tamcabecera, false, false);
                                    textoLogDQLMultiple.insertString(textoLogDQLMultiple.getLength(), "DQL - ", AtributoNegro);
                                } else {
                                    tabla[tablasResultado] = new JTable();
                                    scroll[tablasResultado] = new JScrollPane();
                                    scroll[tablasResultado].add(tabla[tablasResultado]);
                                    tabla[tablasResultado].setName("DQL" + tablasResultado);
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
                                                componente = "DQL" + posicion;
                                                evt.getComponent().addMouseListener(mouselistener[posicion]);
                                                popupmenu(evt);
                                            }
                                        }
                                    });
                                    panelTabResultados.addTab("DQL" + tablasResultado, scroll[tablasResultado]);
                                    mostrarTablaResultado(tabla[tablasResultado], modeloLotes, tamcabecera, false, false);
                                    textoLogDQLMultiple.insertString(textoLogDQLMultiple.getLength(), "\nDQL" + tablasResultado + " - ", AtributoNegro);
                                    tablasResultado++;
                                }
                                if (cont < limitelineas && checkExportarExcel.isSelected()) {
                                    rutaDqls = ventanapadre.dirbase + util.separador() + "DQLS" + util.separador();
                                    util.crearDirectorio(rutaDqls);
                                    barradocum.setLabelMensa("Generando Excel - " + dql);
                                    util.exportarArrayListAExcel(lista, rutaDqls + "DQL - " + repo + " - " + col.getObjectSession().getSessionConfig().getId("session_id") + "-" + DQLsExcel + ".xlsx", "DQL - " + col.getObjectSession().getSessionConfig().getId("session_id") + "-" + DQLsExcel, dql);
                                    DQLsExcel++;
                                }
                            }

                            textoLogDQLMultiple.insertString(textoLogDQLMultiple.getLength(), dql.trim(), AtributoNegro);
                            textoLogDQLMultiple.insertString(textoLogDQLMultiple.getLength(), "   -  " + cont + " registro(s) " + queEs, AtributoAzul);
                            if (cont < limitelineas && checkExportarExcel.isSelected()) {
                                textoLogDQLMultiple.insertString(textoLogDQLMultiple.getLength(), "\nGenerado fichero excel  " + rutaDqls + "DQL - " + repo + " - " + col.getObjectSession().getSessionConfig().getId("session_id") + "-" + (DQLsExcel - 1) + ".xlsx", AtributoNegro);
                            }

                        }
                        if (col != null) {
                            col.close();
                        }
                    }
                    barradocum.dispose();
                } catch (Exception ex) {
                    Utilidades.escribeLog("Error (EjecutarMultiplesDQL) - " + ex.getMessage());
                }
            }

        }.start();

    }

    private void opcionConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionConsultarActionPerformed
        if (!textoDql.getText().isEmpty()) {
            if (!checkMultiplesDqls.isSelected()) {
                ejecutarDql(textoDql.getText(), textoNumReg.getText());
            } else {
                ejecutarMultiplesDQL(textoDql.getText());
            }
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
        if (componente.equals("textoMultiplesLogs")) {
            if (textoMultiplesLogs.getSelectedText() == null) {
                Utilidades.copiarTextoPortapapeles(textoMultiplesLogs.getText());
            } else {
                Utilidades.copiarTextoPortapapeles(textoMultiplesLogs.getSelectedText());
            }
        }
    }//GEN-LAST:event_opcionCopiarActionPerformed

    private void opcionPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionPegarActionPerformed
        if (componente.equals("textoDql")) {
            if (checkMultiplesDqls.isSelected()) {
                int caretPos = textoDql.getCaretPosition();
                try {
                    textoDql.getDocument().insertString(caretPos, Utilidades.pegarTextoPortapapeles(), null);
                } catch (BadLocationException ex) {
                    Utilidades.escribeLog("Error al pegar texto en 'textoDql'" + ex.getMessage());
                }
            } else {
                textoDql.setText(Utilidades.pegarTextoPortapapeles());
            }
        }
    }//GEN-LAST:event_opcionPegarActionPerformed

    private void opcionCopiarValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarValorActionPerformed
        if (componente.equals("tablaResultados")) {
            Utilidades.copiarTextoPortapapeles(tablaResultados.getModel().getValueAt(tablaResultados.convertRowIndexToModel(tablaResultados.getSelectedRow()), tablaResultados.getSelectedColumn()).toString());
        }
        if (componente.startsWith("DQL")) {
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
                    String hoja = "Consulta - DQL";
                    if (textoDql.getText().trim().toLowerCase().startsWith("describe")) {
                        hoja = textoDql.getText().trim();
                    }
                    util.exportarAExcel(tablaResultados, fichero, hoja);
                } else {
                    Utilidades.escribeLog("No se ha seleccionado el fichero de salida ");
                }
            }
        }
        if (componente.startsWith("DQL")) {
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
                    String hoja = "DQL" + pos;
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
            String dirhist = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador() + "historial-dql.log";
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
        if (componente.startsWith("DQL")) {
            int pos = Integer.parseInt(componente.substring(3, componente.length()));
            tabla[pos].setRowSelectionInterval(0, tabla[pos].getRowCount() - 1);
        }
    }//GEN-LAST:event_opcionSeleccionarColumnaActionPerformed

    private void checkMultiplesDqlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMultiplesDqlsActionPerformed
        if (checkMultiplesDqls.isSelected()) {
            checkExportarExcel.setEnabled(true);
            textoNumLineasExcel.setEnabled(true);
            checkDameSQL.setEnabled(false);
        } else {
            checkExportarExcel.setEnabled(false);
            textoNumLineasExcel.setEnabled(false);
            checkDameSQL.setEnabled(true);
            int numtab = panelTabResultados.getTabCount();
            for (int t = numtab - 1; t > 0; t--) {
                panelTabResultados.remove(t);
            }
        }
    }//GEN-LAST:event_checkMultiplesDqlsActionPerformed

    private void textoMultiplesLogsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textoMultiplesLogsMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "textoMultiplesLogs";
            popupmenu(evt);
        }
    }//GEN-LAST:event_textoMultiplesLogsMousePressed

    private void checkDameSQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkDameSQLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkDameSQLActionPerformed

    private void comboHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboHistorialActionPerformed
        String valor = (String) comboHistorial.getSelectedItem();
        valor = valor.trim();
        if (textoDql.getText().isEmpty() || !textoDql.getText().equals(valor)) {
            if (!valor.isEmpty()) {
                if (checkMultiplesDqls.isSelected()) {
                    int count = StringUtils.countMatches(valor, ";");
                    if (count > 1) {
                        StringTokenizer st = new StringTokenizer(valor, ";");
                        if (!textoDql.getText().isEmpty()) {
                            textoDql.setText(textoDql.getText() + (textoDql.getText().endsWith(";") ? "\n" : ";\n"));
                        }
                        int num = 1;
                        while (st.hasMoreElements()) {
                            String elemento = st.nextToken();
                            if (!elemento.isEmpty()) {
                                if (num < count) {
                                    textoDql.setText(textoDql.getText() + elemento.trim() + ";\n");
                                } else {
                                    textoDql.setText(textoDql.getText() + elemento.trim() + ";");
                                }
                            }
                            num++;
                        }

                    } else {
                        if (textoDql.getText().isEmpty()) {
                            textoDql.setText(valor.endsWith(";") ? valor.trim() : valor.trim() + ";");
                        } else {
                            textoDql.setText(textoDql.getText() + (valor.endsWith(";") ? "\n" + valor.trim() : "\n" + valor.trim() + ";"));
                        }
                    }
                } else {
                    textoDql.setText(valor.trim());
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
        if (!textoDql.getText().isEmpty()) {
            if (!checkMultiplesDqls.isSelected()) {
                ejecutarDql(textoDql.getText(), textoNumReg.getText());
            } else {
                ejecutarMultiplesDQL(textoDql.getText());
            }
        }
    }//GEN-LAST:event_botonConsultarActionPerformed

    private void textoNumRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoNumRegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoNumRegActionPerformed

    private void textoListaDQLCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_textoListaDQLCaretUpdate
        ArrayList listaSeleccion = util.buscarEnLista(listaCombo, textoListaDQL.getText());
        DefaultComboBoxModel modelo = new DefaultComboBoxModel(listaSeleccion.toArray());
        comboHistorial.setModel(modelo);
        comboHistorial.setPopupVisible(true);

        if (textoListaDQL.getText().isEmpty()) {
            cargarComboHistorial();
            comboHistorial.setPopupVisible(true);
        }
    }//GEN-LAST:event_textoListaDQLCaretUpdate

    private void textoListaDQLCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_textoListaDQLCaretPositionChanged

    }//GEN-LAST:event_textoListaDQLCaretPositionChanged

    private void textoDqlMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textoDqlMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            componente = "textoDql";
            botonderecho = true;
            popupmenu(evt);
        }
    }//GEN-LAST:event_textoDqlMousePressed

    private void opcionFormatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionFormatoActionPerformed
        if (opcionFormato.isSelected()) {
            if (textoDql.getSelectedText() != null) {
                if (!textoDql.getSelectedText().isEmpty()) {
                    FormatoSql formato = new FormatoSql();
                    int caretPos = textoDql.getCaretPosition();
                    String cadena = textoDql.getSelectedText();
                    try {
                        textoDql.getDocument().remove(caretPos, cadena.length());
                        textoDql.getDocument().insertString(caretPos, formato.format(cadena, "documentum"), null);
                    } catch (Exception ex) {
                        Utilidades.escribeLog("Error al pegar texto formateado en 'textoDql' - " + ex.getMessage());
                    }
                }
            } else {
                if (textoDql.getText() != null && !textoDql.getText().isEmpty()) {
                    FormatoSql formato = new FormatoSql();
                    textoDql.setText(formato.format(textoDql.getText(), "documentum"));
                }
            }
        } else {
            String textohist = textoDql.getText();
            textoDql.setText(textohist.trim().replaceAll("\\s{2,}", " ").replaceAll("(\r\n|\n)", " "));
        }
    }//GEN-LAST:event_opcionFormatoActionPerformed

    private void textoDqlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoDqlKeyReleased
        ActualizarToolTipText();

        if (evt.getKeyCode() == KeyEvent.VK_SPACE && evt.isControlDown()) {
            mostrarVentanaAyuda();
        }

        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pantallaayuda.dispose();
        }

        if (pantallaayuda.isVisible()) {
            String textosqlabuscar = textoDql.getText();
            int posultimaoperacion = textosqlabuscar.lastIndexOf(";");
            if (posultimaoperacion < 0) {
                posultimaoperacion = 0;
            } else {
                posultimaoperacion = posultimaoperacion + 1;
            }
            if (textoDql.getCaretPosition() > posultimaoperacion) {
                if (!checkMultiplesDqls.isSelected()) {
                    textosqlabuscar = textoDql.getText().substring(posultimaoperacion, textoDql.getCaretPosition());
                } else {
                    int numretornos = StringUtils.countMatches(textoDql.getText(), "\n") - 1;
                    textosqlabuscar = textoDql.getText().substring(posultimaoperacion, textoDql.getCaretPosition());
                }
            } else {
                textosqlabuscar = textoDql.getText().substring(posultimaoperacion, textoDql.getText().length());
            }

            posultimaoperacion = textosqlabuscar.toUpperCase().lastIndexOf("UNION");
            if (posultimaoperacion > 0) {
                posultimaoperacion = posultimaoperacion + 6;
                if (textoDql.getCaretPosition() > posultimaoperacion) {
                    textosqlabuscar = textoDql.getText().substring(posultimaoperacion, textoDql.getText().length());
                }
            }

            String primera = utildocum.buscarPalabraInverso(textosqlabuscar, 1, false);
            if (primera.equals(" ") || primera.equals("(") || primera.equals(")")
                    || primera.equals(">") || primera.equals("<") || primera.equals("=")
                    || textosqlabuscar.replaceAll("\r", "").replaceAll("\t", "").replaceAll("\n", "").replaceAll("\f", "").trim().endsWith(";")
                    || primera.endsWith(".") || primera.endsWith(",")) {
            } else {
                setTextoayudaescrito(primera);
                ArrayList<String> listaFiltro = util.buscarEnArrayList(listaPistasLimpia, primera);
                pantallaayuda.cargarDatosAyuda(listaFiltro);
            }
        } else {
            KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
            InputMap inputMap = textoDql.getInputMap();
            inputMap.put(key, DefaultEditorKit.downAction);
            key = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
            inputMap.put(key, null);
        }
    }//GEN-LAST:event_textoDqlKeyReleased

    private void textoDqlKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoDqlKeyPressed
        if (pantallaayuda.isVisible()) {
            if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
                pantallaayuda.asignarFocoListaAyuda();
            }
        }
    }//GEN-LAST:event_textoDqlKeyPressed

    private void mostrarVentanaAyuda() {
        setPosicionTextoDql(textoDql.getCaretPosition());
        Caret caret = textoDql.getCaret();
        Point p = new Point(caret.getMagicCaretPosition());
        p.x += textoDql.getLocationOnScreen().x;
        p.y += textoDql.getLocationOnScreen().y + 24;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double anchopantalla = screenSize.getWidth();

        if ((p.x + pantallaayuda.getWidth() + 5) > anchopantalla) {
            p.x = anchopantalla.intValue() - pantallaayuda.getWidth() - 5;
        }

        pantallaayuda.setBounds(p.x, p.y, pantallaayuda.getWidth(), pantallaayuda.getHeight());
        pantallaayuda.setAlwaysOnTop(true);
        ArrayList<String> lista = new ArrayList<>();

        String textosqlabuscar = textoDql.getText();
        int posultimaoperacion = textosqlabuscar.lastIndexOf(";");
        if (posultimaoperacion < 0) {
            posultimaoperacion = 0;
        } else {
            posultimaoperacion = posultimaoperacion + 1;
        }
        if (textoDql.getCaretPosition() > posultimaoperacion) {
            if (!checkMultiplesDqls.isSelected()) {
                textosqlabuscar = textoDql.getText().substring(posultimaoperacion, textoDql.getCaretPosition());
            } else {
                int numretornos = StringUtils.countMatches(textoDql.getText(), "\n") - 1;
                textosqlabuscar = textoDql.getText().substring(posultimaoperacion, textoDql.getCaretPosition());
            }
        } else {
            textosqlabuscar = textoDql.getText().substring(posultimaoperacion, textoDql.getText().length());
        }

        posultimaoperacion = textosqlabuscar.toUpperCase().lastIndexOf("UNION");
        if (posultimaoperacion > 0) {
            posultimaoperacion = posultimaoperacion + 6;
            if (textoDql.getCaretPosition() > posultimaoperacion) {
                textosqlabuscar = textoDql.getText().substring(posultimaoperacion, textoDql.getText().length());
            }
        }

        String primerareservada = utildocum.buscarPalabraSqlInverso(textosqlabuscar, 1);
        String segundareservada = utildocum.buscarPalabraSqlInverso(textosqlabuscar, 2);
        String tercerareservada = utildocum.buscarPalabraSqlInverso(textosqlabuscar, 3);
        String primerainverso = utildocum.buscarPalabraInverso(textosqlabuscar, 1, true);
        String segundainverso = utildocum.buscarPalabraInverso(textosqlabuscar, 2, true);
        String tercerainverso = utildocum.buscarPalabraInverso(textosqlabuscar, 3, true);
        String primera = utildocum.buscarPalabra(textosqlabuscar, 1, true, true);
        String segunda = utildocum.buscarPalabra(textosqlabuscar, 2, true, true);
        String tercera = utildocum.buscarPalabra(textosqlabuscar, 3, true, true);
        String cuarta = utildocum.buscarPalabra(textosqlabuscar, 4, " \n\r\f\t", false, true);
        tipoconsulta = utildocum.buscarOperacionSqlInverso(textosqlabuscar);

        if (textoDql.getText().isEmpty() || primerareservada.isEmpty()) {
            tipoLista = "inicio";
        }

        switch (tipoconsulta) {
            case "SELECT":
                if (primerareservada.equals("BY") && segundareservada.equals("ORDER")) {
                    tipoLista = "campos";
                } else if (!textosqlabuscar.toUpperCase().contains("FROM")) {
                    tipoLista = "campos";
                } else if (primerainverso.endsWith(".")) {
                    tipoLista = "campos";
                } else if (primerareservada.equals("HAVING")) {
                    tipoLista = "having";
                } else if (primerareservada.equals("BY") && segundareservada.equals("GROUP")) {
                    tipoLista = "groupby";
                } else if (primerareservada.equals("WHERE") && (primerainverso.equals("(") || primerainverso.equals(")")
                        || primerainverso.equals(">") || primerainverso.equals("<") || primerainverso.equals("="))) {
                    tipoLista = "where";
                } else if (primerareservada.equals("WHERE") || segundareservada.equals("WHERE")
                        || tercerareservada.equals("WHERE") || primerareservada.equals("AND")
                        || primerareservada.equals("OR") || primerareservada.equals("DATE")
                        || primerareservada.equals("LOWER") || primerareservada.equals("UPPER")
                        || primerareservada.equals("CABINET") || primerareservada.equals("FOLDER")
                        || primerareservada.equals("NOT") || primerareservada.equals("IN")
                        || primerareservada.equals("YEAR") || primerareservada.equals("WEEK")
                        || primerareservada.equals("TOMORROW") || primerareservada.equals("TODAY")
                        || primerareservada.equals("NOW") || primerareservada.equals("DAY")) {
                    tipoLista = "where";
                } else if (primerareservada.equals("WHERE") && (tipoLista.equals("tablas")
                        || segundareservada.equals("FROM"))) {
                    tipoLista = "where";
                } else if ((primerareservada.equals("FROM")) || primerareservada.equals("UPDATE")
                        || (primerareservada.equals("INTO") && segundareservada.equals("INSERT"))) {
                    tipoLista = "tablas";
                } else if (primerareservada.equals("SELECT") && (segundareservada.equals("COUNT") || segundareservada.equalsIgnoreCase("SUM")
                        || segundareservada.equalsIgnoreCase("AVG") || segundareservada.equalsIgnoreCase("MIN")
                        || segundareservada.equalsIgnoreCase("MAX"))) {
                    tipoLista = "campos";
                } else if (primerareservada.equals("SELECT") || primerareservada.equals("COUNT") || primerareservada.equals("SUM")
                        || (primerareservada.equals("UPDATE") && segundareservada.equalsIgnoreCase("SET"))) {
                    tipoLista = "campos";
                }
                break;

            case "INSERT":
                if (primerareservada.equals("INTO") && segundareservada.equals("INSERT") && cuarta.equals("(") && !primerareservada.equalsIgnoreCase("VALUES")) {
                    tipoLista = "campos";
                } else if (primerareservada.equals("INTO") && segundareservada.equals("INSERT")) {
                    tipoLista = "tablas";
                }
                break;
            case "UPDATE":
                if (segundareservada.equals("UPDATE") && primerareservada.equals("SET")) {
                    tipoLista = "campos";
                } else if (primerareservada.equals("UPDATE")) {
                    tipoLista = "tablas";
                }
                break;
            case "EXECUTE":
                if (primera.equals("EXECUTE") && segunda.isEmpty()) {
                    tipoLista = "execute";
                } else if (primera.equals("EXECUTE") && !utildocum.encontrarEnSintaxis(segunda, listapistasExecute, "tipo")) {
                    tipoLista = "execute";
                } else {
                    tipoLista = "vacia";
                    lista.clear();
                }
                break;
            case "DELETE":
                if (primerareservada.equals("WHERE") && tipoLista.equals("tablas")) {
                    tipoLista = "where";
                } else if (segundareservada.equals("DELETE") && primerareservada.equals("FROM")) {
                    tipoLista = "tablas";
                }

                break;
            default:
//                tipoLista = "vacia";
//                lista.clear();
        }

        if (primerainverso.endsWith(";")) {
            tipoLista = "inicio";
        }

        if (tipoLista.equals("inicio")) {
            for (int n = 0; n < listapistasDqlInicio.size(); n++) {
                lista.add(listapistasDqlInicio.get(n).getTipo());
            }
        }

        if (tipoLista.equals("execute")) {
            for (int n = 0; n < listapistasExecute.size(); n++) {
                lista.add(listapistasExecute.get(n).getTipo());
            }
        }

        if (tipoLista.equals("tablas")) {
            if (primerareservada.toUpperCase().equals("ENABLE")) {
                lista.add("CONVERT_FOLDER_LIST_TO_OR");
                lista.add("FETCH_ALL_RESULTS ");
                lista.add("FORCE_ORDER");
                lista.add("FTDQL");
                lista.add("NOFTDQL");
                lista.add("FT_CONTAIN_FRAGMENT");
                lista.add("GENERATE_SQL_ONLY");
                lista.add("GROUP_LIST_LIMIT ");
                lista.add("HIDE_SHARED_PARENT ");
                lista.add("OPTIMIZE_ON_BASE_TABLE");
                lista.add("OPTIMIZE_TOP ");
                lista.add("RETURN_RANGE");
                lista.add("RETURN_TOP ");
                lista.add("ROW_BASED");
                lista.add("SQL_DEF_RESULT_SET ");
                lista.add("TRY_FTDQL_FIRST");
                lista.add("UNCOMMITTED_READ");
                lista.add("DM_LEFT_OUTER_JOIN_FOR_ACL");
            } else {
                lista = utildocum.listaTablasDocumentum(gsesion);
                switch (tipoconsulta) {
                    case "SELECT":
                        lista.add("WHERE ");
                        lista.add("LEFT ");
                        lista.add("RIGHT ");
                        lista.add("INNER ");
                        lista.add("OUTER ");
                        lista.add("JOIN ");
                        if (!util.esPalabraReservadaSQL(primerainverso, "documentum")) {
                            lista.add("ORDER BY ");
                            lista.add("UNION ");
                            lista.add("ENABLE");
                        }
                        if (primerareservada.equals("UNION")) {
                            lista.add("SELECT ");
                        }
                        break;
                    case "INSERT":
                        lista.add("VALUES");
                        break;
                    case "UPDATE":
                        lista.add("SET ");
                        break;
                    case "DELETE":
                        lista.add("WHERE ");
                        break;
                    default:
                }
            }
        }

//        String sqlactual = textoDql.getText();
//        posultimaoperacion = sqlactual.lastIndexOf(";");
//        if (posultimaoperacion < 0) {
//            posultimaoperacion = 0;
//        } else {
//            posultimaoperacion = posultimaoperacion + 1;
//        }
//        if (textoDql.getCaretPosition() < posultimaoperacion) {
//            sqlactual = textoDql.getText().substring(0, textoDql.getCaretPosition());
//        } else {
//            sqlactual = textoDql.getText().substring(posultimaoperacion, textoDql.getText().length());
//        }
        if (tipoLista.equals("campos")) {
            if (primerareservada.toUpperCase().equals("ENABLE")) {
                lista.add("CONVERT_FOLDER_LIST_TO_OR");
                lista.add("FETCH_ALL_RESULTS ");
                lista.add("FORCE_ORDER");
                lista.add("FTDQL");
                lista.add("NOFTDQL");
                lista.add("FT_CONTAIN_FRAGMENT");
                lista.add("GENERATE_SQL_ONLY");
                lista.add("GROUP_LIST_LIMIT ");
                lista.add("HIDE_SHARED_PARENT ");
                lista.add("OPTIMIZE_ON_BASE_TABLE");
                lista.add("OPTIMIZE_TOP ");
                lista.add("RETURN_RANGE");
                lista.add("RETURN_TOP ");
                lista.add("ROW_BASED");
                lista.add("SQL_DEF_RESULT_SET ");
                lista.add("TRY_FTDQL_FIRST");
                lista.add("UNCOMMITTED_READ");
                lista.add("DM_LEFT_OUTER_JOIN_FOR_ACL");
            } else {
                ArrayList<String> listaDeTablas = utildocum.obtenerNombreTablas(textosqlabuscar);
                if (listaDeTablas.isEmpty()) {
                    lista = utildocum.listaCamposTablaDocumentum(gsesion, "");
                } else {
                    for (int n = 0; n < listaDeTablas.size(); n++) {
                        String nombretabla = listaDeTablas.get(n);
                        if (nombretabla.endsWith("_s") || nombretabla.endsWith("_r")
                                || nombretabla.endsWith("_sp") || nombretabla.endsWith("_rp")) {
                            nombretabla = nombretabla.substring(0, nombretabla.lastIndexOf("_"));
                        }
                        ArrayList<String> listacampostabla = utildocum.listaCamposTablaDocumentum(gsesion, nombretabla);
                        for (int i = 0; i < listacampostabla.size(); i++) {
                            if (!util.estaEnLista(lista, listacampostabla.get(i))) {
                                lista.add(listacampostabla.get(i));
                            }
                        }
                    }
                    Collections.sort(lista);
                }
                switch (tipoconsulta) {
                    case "SELECT":
                        if (primerareservada.equals("UNION")) {
                            lista.add("SELECT ");
                        } else if (primerareservada.equals("COUNT")) {
                            lista.add("DISTINCT ");
                            lista.add("*");
                        } else {
                            if (primerareservada.equals("SUM")) {
                                lista.add("DISTINCT ");
                            } else if (primerareservada.equals("DATE")) {
                                lista.add("DISTINCT ");
                                lista.add("NOW");
                                lista.add("TODAY");
                                lista.add("YESTERDAY");
                                lista.add("TOMORROW");
                            } else if (primerareservada.equals("DATEDIFF")) {
                                lista.add("DAY");
                                lista.add("WEEK");
                                lista.add("MOTNH");
                                lista.add("YEAR");
                            } else if (primerareservada.equals("BY") && segundareservada.equals("ORDER")
                                    && !utildocum.encontrarEnSintaxis(primerainverso, listapistasDqlInicio, "tipo")) {
                                lista.add("ASC");
                                lista.add("DESC");
                            } else {
                                lista.add("ALL ");
                                lista.add("EXISTS ");
                                lista.add("SOME ");
                                lista.add("ANY ");
                                lista.add("DATE");
                                lista.add("DATEDIFF");
                                lista.add("LEFT ");
                                lista.add("RIGHT ");
                                lista.add("INNER ");
                                lista.add("OUTER ");
                                lista.add("JOIN ");
                                lista.add("UNION ");
                                lista.add("IS NULL ");
                                lista.add("IS NOT NULL ");
                                lista.add("IS NULLDATE ");
                                lista.add("IS NOT NULLDATE ");
                                lista.add("IS NULLSTRING ");
                                lista.add("IS NOT NULLSTRING ");
                                lista.add("IS NULLINT ");
                                lista.add("IS NOT NULLINT ");
                                lista.add("IS NULLID ");
                                lista.add("IS NOT NULLID ");
                                if (textosqlabuscar.toUpperCase().contains("FROM")) {
                                    lista.add("ENABLE ");
                                }
                            }
                        }
                        break;
                    case "UPDATE":
                        lista.add("WHERE");
                        break;
                    default:
                }
                if (!tipoconsulta.equalsIgnoreCase("UPDATE")) {
                    if (!(primerareservada.equals("BY") && segundareservada.equals("ORDER"))) {
                        lista.add("FROM ");
                    }
                }
            }
        }

        if (tipoLista.equals("where")) {
            if (primerareservada.toUpperCase().equals("ENABLE")) {
                lista.add("CONVERT_FOLDER_LIST_TO_OR");
                lista.add("FETCH_ALL_RESULTS ");
                lista.add("FORCE_ORDER");
                lista.add("FTDQL");
                lista.add("NOFTDQL");
                lista.add("FT_CONTAIN_FRAGMENT");
                lista.add("GENERATE_SQL_ONLY");
                lista.add("GROUP_LIST_LIMIT ");
                lista.add("HIDE_SHARED_PARENT ");
                lista.add("OPTIMIZE_ON_BASE_TABLE");
                lista.add("OPTIMIZE_TOP ");
                lista.add("RETURN_RANGE");
                lista.add("RETURN_TOP ");
                lista.add("ROW_BASED");
                lista.add("SQL_DEF_RESULT_SET ");
                lista.add("TRY_FTDQL_FIRST");
                lista.add("UNCOMMITTED_READ");
                lista.add("DM_LEFT_OUTER_JOIN_FOR_ACL");
            } else {
                ArrayList<String> listaDeTablas = utildocum.obtenerNombreTablas(textosqlabuscar);
                if (util.esPalabraReservadaSQL(primerainverso, "documentum") || segundainverso.equals("WHERE")) {
                    if (listaDeTablas.isEmpty()) {
                        lista = utildocum.listaCamposTablaDocumentum(gsesion, "");
                    } else {
                        for (int n = 0; n < listaDeTablas.size(); n++) {
                            String nombretabla = listaDeTablas.get(n);
                            if (nombretabla.endsWith("_s") || nombretabla.endsWith("_r")
                                    || nombretabla.endsWith("_sp") || nombretabla.endsWith("_rp")) {
                                nombretabla = nombretabla.substring(0, nombretabla.lastIndexOf("_"));
                            }
                            ArrayList<String> listacampostabla = utildocum.listaCamposTablaDocumentum(gsesion, nombretabla);
                            for (int i = 0; i < listacampostabla.size(); i++) {
                                if (!util.estaEnLista(lista, listacampostabla.get(i))) {
                                    lista.add(listacampostabla.get(i));
                                }
                            }
                        }
                        Collections.sort(lista);
                    }
                } else {
                    lista.add("AND ");
                    lista.add("OR ");
                    if (!tipoconsulta.equalsIgnoreCase("DELETE")) {
                        lista.add("GROUP BY ");
                        lista.add("ORDER BY ");
                        lista.add("ENABLE ");
                        lista.add("UNION ");
                    }
                    lista.add("LIKE ");
                    lista.add("IN");
                }
                if (primerareservada.equals("UNION")) {
                    lista.add("SELECT ");
                }
                lista.add("UPPER");
                lista.add("LOWER");
                lista.add("ANY ");
                lista.add("NOT ");
                lista.add("TYPE");
                lista.add("FOLDER");
                lista.add("CABINET");
                lista.add("DATE");
                lista.add("DATEDIFF");
                lista.add("SUBSTR");
                lista.add("IS NULL ");
                lista.add("IS NOT NULL ");
                lista.add("IS NULLDATE ");
                lista.add("IS NOT NULLDATE ");
                lista.add("IS NULLSTRING ");
                lista.add("IS NOT NULLSTRING ");
                lista.add("IS NULLINT ");
                lista.add("IS NOT NULLINT ");
                lista.add("IS NULLID ");
                lista.add("IS NOT NULLID ");
                if (primerareservada.equals("DATE")) {
                    lista.add("DISTINCT ");
                    lista.add("NOW");
                    lista.add("TODAY");
                    lista.add("YESTERDAY");
                    lista.add("TOMORROW");
                }
                if (primerareservada.equals("DATEDIFF")) {
                    lista.add("DAY");
                    lista.add("WEEK");
                    lista.add("MOTNH");
                    lista.add("YEAR");
                }
            }
        }

        if (tipoLista.equals("groupby")) {
            if (primerareservada.toUpperCase().equals("ENABLE")) {
                lista.add("CONVERT_FOLDER_LIST_TO_OR");
                lista.add("FETCH_ALL_RESULTS ");
                lista.add("FORCE_ORDER");
                lista.add("FTDQL");
                lista.add("NOFTDQL");
                lista.add("FT_CONTAIN_FRAGMENT");
                lista.add("GENERATE_SQL_ONLY");
                lista.add("GROUP_LIST_LIMIT ");
                lista.add("HIDE_SHARED_PARENT ");
                lista.add("OPTIMIZE_ON_BASE_TABLE");
                lista.add("OPTIMIZE_TOP ");
                lista.add("RETURN_RANGE");
                lista.add("RETURN_TOP ");
                lista.add("ROW_BASED");
                lista.add("SQL_DEF_RESULT_SET ");
                lista.add("TRY_FTDQL_FIRST");
                lista.add("UNCOMMITTED_READ");
                lista.add("DM_LEFT_OUTER_JOIN_FOR_ACL");
            } else {
                lista = utildocum.listaCamposTablaDocumentum(gsesion, "");
                lista.add("HAVING ");
                lista.add("ORDER BY ");
                lista.add("ENABLE ");
                lista.add("UNION ");
            }
        }

        if (tipoLista.equals("having")) {
            lista.add("COUNT");
        }

        // END_CLAUSES
        if (tipoLista.equals("END_CLAUSES")) {
            lista.add("WHERE ");
            lista.add("SET ");
            lista.add("HAVING ");
            lista.add("JOIN ");
            lista.add("FROM ");
            lista.add("BY ");
            lista.add("JOIN ");
            lista.add("INTO ");
            lista.add("UNION ");
        }

        // LOGICAL
        if (tipoLista.equals("LOGICAL")) {
            lista.add("AND ");
            lista.add("OR ");
            lista.add("WHEN");
            lista.add("ELSE ");
            lista.add("END");
        }

        // QUANTIFIERS
        if (tipoLista.equals("QUANTIFIERS")) {
            lista.add("IN ");
            lista.add("ALL ");
            lista.add("EXISTS ");
            lista.add("SOME ");
            lista.add("ANY ");
        }

//            if (!textoDql.getText().isEmpty()) {
//                String escrito = textoDql.getText().substring(textoDql.getText().lastIndexOf(" ", textoDql.getCaretPosition()), textoDql.getCaretPosition());
//                lista = buscarEnArrayList(lista, escrito);
//            }
        if (!lista.isEmpty()) {
            listaPistasLimpia = lista;
            pantallaayuda.cargarDatosAyuda(lista);
            pantallaayuda.setVisible(true);
            textoDql.requestFocus();
            textoDql.setFocusTraversalKeysEnabled(false);
            tipoListaold = tipoLista;
            KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
            InputMap inputMap = textoDql.getInputMap();
            Action cambiarFoco = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pantallaayuda.asignarFocoListaAyuda();
                }
            };
            inputMap.put(key, cambiarFoco);
            key = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
            inputMap = textoDql.getInputMap();
            inputMap.put(key, cambiarFoco);

        } else {
            pantallaayuda.dispose();
            textoDql.requestFocus();
            textoDql.setFocusTraversalKeysEnabled(true);
        }
    }

    public void ActualizarToolTipText() {
        String palabra = "";

        int pos = textoDql.getText().indexOf(",");
        if (pos < 0) {
            pos = textoDql.getText().indexOf(" ");
        }

        if (pos > 0) {
            palabra = textoDql.getText().substring(0, pos);
        }

        if (!palabra.isEmpty()) {
            String sintaxis = "";
            String textosqlabuscar = textoDql.getText().substring(0, textoDql.getCaretPosition());
            String primerainverso = utildocum.buscarPalabraInverso(textosqlabuscar, 1, true);
            String segundainverso = utildocum.buscarPalabraInverso(textosqlabuscar, 1, true);
            String primera = utildocum.buscarPalabra(textosqlabuscar, 1, true, true);
            String segunda = utildocum.buscarPalabra(textosqlabuscar, 2, true, true);
            if (primera.equalsIgnoreCase("EXECUTE")) {
                sintaxis = utildocum.buscarSintaxis(segunda, listapistasExecute, "tipo");
            } else if (primera.equalsIgnoreCase("ALTER")) {
                sintaxis = utildocum.buscarSintaxis(primera + " " + segunda, listapistasDqlInicio, "tipolike");
            } else {
                sintaxis = utildocum.buscarSintaxis(palabra, listapistasDqlInicio, "tipo");
            }
            sintaxis = sintaxis.replaceAll("@@", "<br>");
            if (!sintaxis.isEmpty()) {
                String textoHtml
                        = "<html><p><b><font color=\"#0e007d\" "
                        + "size=\"4\" face=\"Verdana\">" + sintaxis
                        + "</font></b></p></html>";
                UIManager.put("ToolTip.background", new Color(250, 250, 205));
                textoDql.setToolTipText(textoHtml);
            }
        } else {
            textoDql.setToolTipText("");
        }
    }

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
                barradocum = new PantallaBarra(PantallaDqlconTabs.this, false);
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

                    if (dql.toLowerCase().startsWith("describe ")) {
                        IDfCollection col = utildocum.ejecutarDescribe(dql, gsesion);

                        ArrayList filas = new ArrayList();
                        while (col.next()) {
                            filas.add(col.getTypedObject());
                        }
                        col.close();
                        if (filas.size() <= 0) {
                            etiquetaEstado.setText("0 Registro(s) encontrado(s) ");
                            if (esTipo) {
                                try {
                                    StringTokenizer param = new StringTokenizer(dql.trim(), " ");
                                    param.nextElement().toString();
                                    String tipo = param.nextElement().toString();
                                    if (tipo.toLowerCase().equalsIgnoreCase("type")) {
                                        tipo = param.nextElement().toString();
                                    }
                                    ERROR = "El tipo " + tipo + " no es un tipo documental en el repositorio " + gsesion.getDocbaseName();
                                } catch (DfException ex) {

                                }
                                textoMultiplesLogs.setForeground(Color.RED);
                                textoMultiplesLogs.setText(ERROR);
                            } else {
                                textoMultiplesLogs.setText("0 Registro(s) encontrado(s) ");
                            }
                            barradocum.dispose();
                            return;
                        }

                        cont = filas.size();
                        int tam = 0;

                        if (esTabla) {
                            tam = 2;
                            cabecera = new Object[tam];
                            tamcabecera = new Integer[tam];
                            datos = new Object[cont + 4][tam];
                            IDfTypedObject row = (IDfTypedObject) filas.get(0);
                            String owner_tabla = row.getString("table_owner");
                            String nombre_tabla = row.getString("table_name");
                            datos[0][0] = "Table Name:";
                            datos[0][1] = owner_tabla + "." + nombre_tabla;
                            datos[1][0] = "";
                            datos[1][1] = "";
                            datos[2][0] = "Columns:";
                            datos[2][1] = cont;
                            datos[3][0] = "";
                            datos[3][1] = "";
                            cabecera[0] = "";
                            cabecera[1] = "";

                            for (int i = 0; i < cont; i++) {
                                row = (IDfTypedObject) filas.get(i);
                                String nombre_columna = row.getString("column_name");
                                String tipo_columna = row.getString("column_datatype");
                                int tam_columna = row.getInt("column_length");
                                datos[i + 4][0] = nombre_columna;
                                datos[i + 4][1] = (tipo_columna.equalsIgnoreCase("String")) ? "CHAR" + " (" + tam_columna + ")" : tipo_columna.toUpperCase();
                            }
                        } else {
                            tam = 3;
                            cabecera = new Object[tam];
                            tamcabecera = new Integer[tam];
                            datos = new Object[cont + 5][tam];
                            IDfTypedObject row = (IDfTypedObject) filas.get(0);
                            String nombre_tipo = row.getString("name");
                            String supertipo = utildocum.dameSuperTipo(nombre_tipo, gsesion);
                            datos[0][0] = "Type Name:";
                            datos[0][1] = nombre_tipo;
                            datos[0][2] = "";
                            datos[1][0] = "SuperType Name:";
                            datos[1][1] = supertipo;
                            datos[1][2] = "";
                            datos[2][0] = "";
                            datos[2][1] = "";
                            datos[2][2] = "";
                            datos[3][0] = "Attributes:";
                            datos[3][1] = cont;
                            datos[3][2] = "";
                            datos[4][0] = "";
                            datos[4][1] = "";
                            datos[4][2] = "";
                            cabecera[0] = "";
                            cabecera[1] = "";
                            cabecera[2] = "";

                            for (int i = 0; i < cont; i++) {
                                row = (IDfTypedObject) filas.get(i);
                                String nombre_attr = row.getString("attr_name");
                                int tipo_attr = row.getInt("attr_type");
                                int tam_attr = row.getInt("attr_length");
                                int repeat = row.getInt("attr_repeating");
                                datos[i + 5][0] = nombre_attr;
                                datos[i + 5][1] = util.dameDescTipo(tipo_attr).equalsIgnoreCase("String") ? "CHAR" + " (" + tam_attr + ")" : util.dameDescTipo(tipo_attr).toUpperCase();
                                datos[i + 5][2] = repeat == 1 ? "REPEATING" : "";
                            }
                        }
                    } else {
                        IDfCollection col = utildocum.ejecutarDql(dql, gsesion);
                        if (!utildocum.dameError().equals("")) {
                            textoMultiplesLogs.setText(utildocum.dameError());
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
                    }

                    if (!buscarEnComboHistorial(textoDql.getText())) {
                        try {
                            FileOutputStream historial = new FileOutputStream(new File(dirdfc + "historial-dql.log"), true);
                            historial.write(("\n" + textoDql.getText().replaceAll("(\r\n|\n)", " ")).getBytes());
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
                } catch (Exception ex) {
                    if (ERROR.isEmpty()) {
                        textoMultiplesLogs.setText(ex.getMessage());
                    } else {
                        textoMultiplesLogs.setText(ERROR);
                        textoMultiplesLogs.setForeground(Color.RED);
                        etiquetaEstado.setText("0 Registro(s) encontrado(s) ");
                    }

                }
                if (checkDameSQL.isSelected()) {
                    String textoDql = utildocum.dameSql(gsesion);
                    textoMultiplesLogs.setText(textoMultiplesLogs.getText() + "\n" + textoDql);
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
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

    private void cargarComboHistorial() {
        ArrayList comboBoxItems = new ArrayList();
        String dirhist = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador() + "historial-dql.log";

        if (!util.existeFichero(dirhist)) {
            util.crearFichero(dirhist, "txt");
        }

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
                new PantallaDqlconTabs(ventanapadre, true).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JButton botonConsultar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JCheckBox checkDameSQL;
    private javax.swing.JCheckBox checkExportarExcel;
    private javax.swing.JCheckBox checkMultiplesDqls;
    private javax.swing.JComboBox comboHistorial;
    private javax.swing.JLabel etiquetaEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JPanel panelDql;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JScrollPane panelResultado;
    private javax.swing.JTabbedPane panelTabResultados;
    private javax.swing.JPopupMenu popupDatos;
    private javax.swing.JPopupMenu popupEditar;
    private javax.swing.JPopupMenu popupHistorial;
    private javax.swing.JTable tablaResultados;
    private javax.swing.JTextPane textoDql;
    private javax.swing.JTextField textoListaDQL;
    private javax.swing.JTextPane textoMultiplesLogs;
    private javax.swing.JFormattedTextField textoNumLineasExcel;
    private javax.swing.JFormattedTextField textoNumReg;
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
        if (pantallaayuda.isShowing()) {
            pantallaayuda.dispose();
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

                if (componente.startsWith("DQL")) {
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

}
