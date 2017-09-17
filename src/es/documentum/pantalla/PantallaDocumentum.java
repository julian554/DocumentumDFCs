package es.documentum.pantalla;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.IDfAttr;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfValue;
import es.documentum.Beans.AtributosDocumentum;
import es.documentum.utilidades.UtilidadesDocumentum;
import es.documentum.utilidades.ClassPathUpdater;
import es.documentum.utilidades.Correo;
import es.documentum.utilidades.CorreoPropiedades;
import es.documentum.utilidades.Utilidades;
import static es.documentum.utilidades.UtilidadesDocumentum.getDfObjectValue;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class PantallaDocumentum extends javax.swing.JFrame {

    public String titulo = "Consultas en Documentum";
    private Boolean botonderecho = false;
    private String componente = "";
    UtilidadesDocumentum utilDocum = new UtilidadesDocumentum();
    Utilidades util = new Utilidades();
    String dirbase = util.usuarioHome() + util.separador() + "documentumdcfs";
    File ficherolog = null;
    FileOutputStream fis;
    PrintStream out;
    String dirdfc = "";
    static Logger logger = Logger.getLogger(PantallaDocumentum.class);
    PantallaBarra barradocum = new PantallaBarra(PantallaDocumentum.this, false);

    public PantallaBarra getBarradocum() {
        return barradocum;
    }

    public void setBarradocum(PantallaBarra barradocum) {
        this.barradocum = barradocum;
    }
    Color colornoconex = new Color(255, 200, 200);
    Color colorconex = new Color(200, 255, 200);
    Color coloradmin = new Color(200, 200, 255);
    Color colorborrado = new Color(255, 215, 255);
    Color coloraviso = new Color(0, 0, 255);
    String usuario = "";
    String clave = "";
    String repositorio = "";
    String docbroker = "";
    String versiondocumentum = "";
    String idrepositorio = "";
    String puerto = "";
    String versiondfcs = "";
    boolean esadmin = false;
    ArrayList<AtributosDocumentum> atributos = new ArrayList<AtributosDocumentum>();
    public javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String memoria = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB / " + Runtime.getRuntime().totalMemory() / 1024 + " KB ";
            if (!idrepositorio.isEmpty()) {
                setTitle(titulo + "     -     " + repositorio + " - " + idrepositorio + " (" + Integer.toHexString(Integer.parseInt(idrepositorio)) + ")" + "     -     Memoria utilizada: " + memoria);
            } else {
                setTitle(titulo + "     -     Memoria utilizada: " + memoria);
            }
            panelEntrada.revalidate();
        }
    });

    public PantallaDocumentum() {        // Opciones del menú "Apariencia" , por defecto se usa el Nimbus como tema visual
        initComponents();
        opcionRBMetal.setSelected(false);
        opcionRBNimbus.setSelected(true);
        opcionRBWindows.setSelected(false);
        opcionRBWindowsClassic.setSelected(false);
        opcionRBPorDefecto.setSelected(false);
        setLocationRelativeTo(null);
        inicializar();
        setVisible(true);
        timer.start();
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

        popupDocumentos = new javax.swing.JPopupMenu();
        opcionAbrirDocumento = new javax.swing.JMenuItem();
        opcionExportar = new javax.swing.JMenuItem();
        opcionBorrarDocumento = new javax.swing.JMenuItem();
        opcionBorradoLogico = new javax.swing.JMenuItem();
        opcionCheckin = new javax.swing.JMenuItem();
        opcionCancelCheckout = new javax.swing.JMenuItem();
        opcionCheckout = new javax.swing.JMenuItem();
        separador = new javax.swing.JPopupMenu.Separator();
        opcionCopiarNombre = new javax.swing.JMenuItem();
        opcionCopiarIDDocumentum = new javax.swing.JMenuItem();
        opcionDumpAtributos = new javax.swing.JMenuItem();
        opcionRenditions = new javax.swing.JMenuItem();
        opcionConvertir = new javax.swing.JMenuItem();
        opcionExportarDocumentosExcel = new javax.swing.JMenuItem();
        popupEditar = new javax.swing.JPopupMenu();
        opcionCopiar = new javax.swing.JMenuItem();
        opcionPegar = new javax.swing.JMenuItem();
        popupAtributos = new javax.swing.JPopupMenu();
        opcionCopiarValor = new javax.swing.JMenuItem();
        opcionCopiarAtributo = new javax.swing.JMenuItem();
        opcionActualizarAtributo = new javax.swing.JMenuItem();
        opcionExportarAtributosExcel = new javax.swing.JMenuItem();
        panelDocumentos = new javax.swing.JPanel();
        scrollGrid = new javax.swing.JScrollPane(tablaDocumentos);
        tablaDocumentos = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaAtributos = new javax.swing.JTable();
        panelEstado = new javax.swing.JPanel();
        EtiquetaEstado = new javax.swing.JLabel();
        EtiquetaDocbroker = new javax.swing.JLabel();
        EtiquetaRepositorio = new javax.swing.JLabel();
        panelEntrada = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textoRutaDocumentum = new javax.swing.JTextField();
        botonSalir = new javax.swing.JButton();
        textoIdDocumentum = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        botonBuscar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        textoCarpeta = new javax.swing.JTextField();
        botonConectar = new javax.swing.JButton();
        botonArribaDir = new javax.swing.JButton();
        MenuDocumentum = new javax.swing.JMenuBar();
        opcionOpciones = new javax.swing.JMenu();
        opcionBuscar = new javax.swing.JMenuItem();
        opcionConectar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        opcionSalir = new javax.swing.JMenuItem();
        opcionApariencia = new javax.swing.JMenu();
        opcionRBMetal = new javax.swing.JRadioButtonMenuItem();
        opcionRBNimbus = new javax.swing.JRadioButtonMenuItem();
        opcionRBWindows = new javax.swing.JRadioButtonMenuItem();
        opcionRBWindowsClassic = new javax.swing.JRadioButtonMenuItem();
        opcionRBPorDefecto = new javax.swing.JRadioButtonMenuItem();
        opcionUtilidades = new javax.swing.JMenu();
        opcionDql = new javax.swing.JMenuItem();
        opcionAPI = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        importarADocumentum = new javax.swing.JMenuItem();
        opcionExportarCarpeta = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        opcionLeerLog = new javax.swing.JMenuItem();
        opcionPasswordLDAP = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        opcionEstadisticasRepos = new javax.swing.JMenuItem();
        opcionIndexador = new javax.swing.JMenuItem();
        opcionJobs = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        opcionEjecutarComandoSSOO = new javax.swing.JMenuItem();
        opcionAcercade = new javax.swing.JMenu();
        opcionManual = new javax.swing.JMenuItem();
        Acercade = new javax.swing.JMenuItem();

        opcionAbrirDocumento.setText("Abrir Documento");
        opcionAbrirDocumento.setToolTipText("Abre el Documento seleccionado.");
        opcionAbrirDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionAbrirDocumentoActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionAbrirDocumento);

        opcionExportar.setText("Exportar a fichero");
        opcionExportar.setToolTipText("Exportar a fichero desde Documentum");
        opcionExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionExportarActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionExportar);

        opcionBorrarDocumento.setText("Borrar Documento");
        opcionBorrarDocumento.setToolTipText("Borrar todas las versiones de un Documento");
        opcionBorrarDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionBorrarDocumentoActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionBorrarDocumento);

        opcionBorradoLogico.setText("Borrado Lógico del Documento");
        opcionBorradoLogico.setEnabled(false);
        opcionBorradoLogico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionBorradoLogicoActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionBorradoLogico);

        opcionCheckin.setText("Check In");
        opcionCheckin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCheckinActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionCheckin);

        opcionCancelCheckout.setText("Cancelar Check Out");
        opcionCancelCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCancelCheckoutActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionCancelCheckout);

        opcionCheckout.setText("Check Out");
        opcionCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCheckoutActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionCheckout);
        popupDocumentos.add(separador);

        opcionCopiarNombre.setText("Copiar Nombre");
        opcionCopiarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarNombreActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionCopiarNombre);

        opcionCopiarIDDocumentum.setText("Copiar ID de Documentum");
        opcionCopiarIDDocumentum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarIDDocumentumActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionCopiarIDDocumentum);

        opcionDumpAtributos.setText("Volcar Atributos del Objeto");
        opcionDumpAtributos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionDumpAtributosActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionDumpAtributos);

        opcionRenditions.setText("Ver Renditions");
        opcionRenditions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRenditionsActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionRenditions);

        opcionConvertir.setText("Convertir a PDF (ADTS)");
        opcionConvertir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionConvertirActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionConvertir);

        opcionExportarDocumentosExcel.setText("Exportar lista de Documentos a Excel");
        opcionExportarDocumentosExcel.setActionCommand("ExportarDocumentosExcel");
        opcionExportarDocumentosExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionExportarDocumentosExcelActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionExportarDocumentosExcel);

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
        popupAtributos.add(opcionCopiarValor);

        opcionCopiarAtributo.setText("Copiar nombre de Atributo");
        opcionCopiarAtributo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarAtributoActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionCopiarAtributo);

        opcionActualizarAtributo.setText("Actualizar Atributo");
        opcionActualizarAtributo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionActualizarAtributoActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionActualizarAtributo);

        opcionExportarAtributosExcel.setText("Exportar lista de Atributos a Excel");
        opcionExportarAtributosExcel.setActionCommand("ExportarAtributosExcel");
        opcionExportarAtributosExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionExportarAtributosExcelActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionExportarAtributosExcel);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consultas en Documentum");
        setMinimumSize(new java.awt.Dimension(1088, 770));
        setName("tabla"); // NOI18N

        panelDocumentos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelDocumentos.setAutoscrolls(true);
        panelDocumentos.setMaximumSize(new java.awt.Dimension(1020, 640));
        panelDocumentos.setMinimumSize(new java.awt.Dimension(1020, 640));
        panelDocumentos.setPreferredSize(new java.awt.Dimension(1020, 640));

        scrollGrid.setAutoscrolls(true);
        scrollGrid.setMinimumSize(new java.awt.Dimension(1012, 200));
        scrollGrid.setPreferredSize(new java.awt.Dimension(1012, 200));
        scrollGrid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                scrollGridMousePressed(evt);
            }
        });

        tablaDocumentos.setAutoCreateRowSorter(true);
        tablaDocumentos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tablaDocumentos.setForeground(new java.awt.Color(102, 102, 102));
        tablaDocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDocumentos.setFillsViewportHeight(true);
        tablaDocumentos.setMaximumSize(null);
        tablaDocumentos.setMinimumSize(null);
        tablaDocumentos.setPreferredSize(null);
        tablaDocumentos.setSelectionBackground(new java.awt.Color(204, 255, 255));
        tablaDocumentos.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tablaDocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDocumentosMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaDocumentosMousePressed(evt);
            }
        });
        tablaDocumentos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tablaDocumentosKeyTyped(evt);
            }
        });
        scrollGrid.setViewportView(tablaDocumentos);

        tablaAtributos.setAutoCreateRowSorter(true);
        tablaAtributos.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        tablaAtributos.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaAtributos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaAtributosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaAtributos);

        panelEstado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelEstado.setPreferredSize(new java.awt.Dimension(1012, 30));

        EtiquetaEstado.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        EtiquetaEstado.setToolTipText("");
        EtiquetaEstado.setAutoscrolls(true);
        EtiquetaEstado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        EtiquetaEstado.setMaximumSize(new java.awt.Dimension(panelEstado.getHeight() - EtiquetaDocbroker.getHeight() - EtiquetaRepositorio.getHeight(), EtiquetaEstado.getWidth()));
        EtiquetaEstado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                EtiquetaEstadoMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EtiquetaEstadoMousePressed(evt);
            }
        });

        EtiquetaDocbroker.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EtiquetaDocbroker.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        EtiquetaDocbroker.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        EtiquetaDocbroker.setMaximumSize(new java.awt.Dimension(209, 26));
        EtiquetaDocbroker.setPreferredSize(new java.awt.Dimension(209, 26));

        EtiquetaRepositorio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EtiquetaRepositorio.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        EtiquetaRepositorio.setMaximumSize(new java.awt.Dimension(135, 26));
        EtiquetaRepositorio.setPreferredSize(new java.awt.Dimension(135, 26));

        javax.swing.GroupLayout panelEstadoLayout = new javax.swing.GroupLayout(panelEstado);
        panelEstado.setLayout(panelEstadoLayout);
        panelEstadoLayout.setHorizontalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEstadoLayout.createSequentialGroup()
                .addComponent(EtiquetaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(EtiquetaDocbroker, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(EtiquetaRepositorio, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelEstadoLayout.setVerticalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEstadoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EtiquetaDocbroker, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(EtiquetaRepositorio, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(EtiquetaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout panelDocumentosLayout = new javax.swing.GroupLayout(panelDocumentos);
        panelDocumentos.setLayout(panelDocumentosLayout);
        panelDocumentosLayout.setHorizontalGroup(
            panelDocumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEstado, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, 1018, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(scrollGrid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelDocumentosLayout.setVerticalGroup(
            panelDocumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDocumentosLayout.createSequentialGroup()
                .addComponent(scrollGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("ID de Documentum (r_object_id):");

        textoRutaDocumentum.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        textoRutaDocumentum.setForeground(java.awt.Color.blue);
        textoRutaDocumentum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                textoRutaDocumentumMousePressed(evt);
            }
        });
        textoRutaDocumentum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textoRutaDocumentumKeyPressed(evt);
            }
        });

        botonSalir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonSalir.setMnemonic('S');
        botonSalir.setText("Salir");
        botonSalir.setToolTipText("Volver a la pantalla anterior");
        botonSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSalir.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        botonSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        textoIdDocumentum.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        textoIdDocumentum.setForeground(new java.awt.Color(0, 0, 255));
        textoIdDocumentum.setMinimumSize(new java.awt.Dimension(6, 26));
        textoIdDocumentum.setPreferredSize(new java.awt.Dimension(6, 26));
        textoIdDocumentum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                textoIdDocumentumMousePressed(evt);
            }
        });
        textoIdDocumentum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textoIdDocumentumKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Ruta en Documentum:");

        botonBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonBuscar.setToolTipText("Imágenes escaneadas o importadas pendientes de envío. ");
        botonBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonBuscar.setLabel("Buscar");
        botonBuscar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        botonBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Carpeta de Documentum:");

        textoCarpeta.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        textoCarpeta.setForeground(new java.awt.Color(0, 0, 255));
        textoCarpeta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                textoCarpetaMousePressed(evt);
            }
        });
        textoCarpeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textoCarpetaKeyPressed(evt);
            }
        });

        botonConectar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonConectar.setText("Conexión");
        botonConectar.setToolTipText("Imágenes escaneadas o importadas pendientes de envío. ");
        botonConectar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonConectar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        botonConectar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConectarActionPerformed(evt);
            }
        });

        botonArribaDir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/arrow_up.png"))); // NOI18N
        botonArribaDir.setMargin(new java.awt.Insets(2, 2, 2, 2));
        botonArribaDir.setMaximumSize(new java.awt.Dimension(26, 26));
        botonArribaDir.setMinimumSize(new java.awt.Dimension(26, 26));
        botonArribaDir.setPreferredSize(new java.awt.Dimension(26, 26));
        botonArribaDir.setRequestFocusEnabled(false);
        botonArribaDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonArribaDirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEntradaLayout = new javax.swing.GroupLayout(panelEntrada);
        panelEntrada.setLayout(panelEntradaLayout);
        panelEntradaLayout.setHorizontalGroup(
            panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEntradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelEntradaLayout.createSequentialGroup()
                        .addGroup(panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28))
                    .addGroup(panelEntradaLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonArribaDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEntradaLayout.createSequentialGroup()
                        .addGroup(panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textoIdDocumentum, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoCarpeta))
                        .addGap(35, 35, 35)
                        .addComponent(botonBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(botonConectar)
                        .addGap(18, 18, 18)
                        .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textoRutaDocumentum))
                .addContainerGap())
        );

        panelEntradaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {botonBuscar, botonSalir});

        panelEntradaLayout.setVerticalGroup(
            panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEntradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEntradaLayout.createSequentialGroup()
                        .addGroup(panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textoIdDocumentum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textoCarpeta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEntradaLayout.createSequentialGroup()
                        .addGroup(panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonConectar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textoRutaDocumentum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(botonArribaDir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        textoRutaDocumentum.getAccessibleContext().setAccessibleName("textoRutaDocumentum");
        textoIdDocumentum.getAccessibleContext().setAccessibleName("textoIdDocumentum");
        textoCarpeta.getAccessibleContext().setAccessibleName("textoCarpeta");
        botonArribaDir.getAccessibleContext().setAccessibleName("botonSubir");

        opcionOpciones.setMnemonic('O');
        opcionOpciones.setText("Opciones");
        opcionOpciones.setToolTipText("");

        opcionBuscar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionBuscar.setText("Buscar");
        opcionBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionBuscarActionPerformed(evt);
            }
        });
        opcionOpciones.add(opcionBuscar);

        opcionConectar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionConectar.setText("Conectar");
        opcionConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionConectarActionPerformed(evt);
            }
        });
        opcionOpciones.add(opcionConectar);
        opcionOpciones.add(jSeparator1);

        opcionSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        opcionSalir.setMnemonic('S');
        opcionSalir.setText("Salir");
        opcionSalir.setToolTipText("Volver a la pantalla anterior");
        opcionSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionSalirActionPerformed(evt);
            }
        });
        opcionOpciones.add(opcionSalir);

        MenuDocumentum.add(opcionOpciones);

        opcionApariencia.setMnemonic('A');
        opcionApariencia.setText("Apariencia");

        opcionRBMetal.setSelected(true);
        opcionRBMetal.setText("Metal");
        opcionRBMetal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBMetalActionPerformed(evt);
            }
        });
        opcionApariencia.add(opcionRBMetal);

        opcionRBNimbus.setSelected(true);
        opcionRBNimbus.setText("Nimbus");
        opcionRBNimbus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBNimbusActionPerformed(evt);
            }
        });
        opcionApariencia.add(opcionRBNimbus);

        opcionRBWindows.setSelected(true);
        opcionRBWindows.setText("Windows");
        opcionRBWindows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBWindowsActionPerformed(evt);
            }
        });
        opcionApariencia.add(opcionRBWindows);

        opcionRBWindowsClassic.setSelected(true);
        opcionRBWindowsClassic.setText("Windows Classic");
        opcionRBWindowsClassic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBWindowsClassicActionPerformed(evt);
            }
        });
        opcionApariencia.add(opcionRBWindowsClassic);

        opcionRBPorDefecto.setSelected(true);
        opcionRBPorDefecto.setText("Propia del Sistema Operativo");
        opcionRBPorDefecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBPorDefectoActionPerformed(evt);
            }
        });
        opcionApariencia.add(opcionRBPorDefecto);

        MenuDocumentum.add(opcionApariencia);

        opcionUtilidades.setText("Utilidades");

        opcionDql.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionDql.setText("Editor de DQL");
        opcionDql.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionDqlActionPerformed(evt);
            }
        });
        opcionUtilidades.add(opcionDql);

        opcionAPI.setText("Ejecutar comando API");
        opcionAPI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionAPIActionPerformed(evt);
            }
        });
        opcionUtilidades.add(opcionAPI);
        opcionUtilidades.add(jSeparator3);

        importarADocumentum.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        importarADocumentum.setText("Importar a Documentum");
        importarADocumentum.setToolTipText("");
        importarADocumentum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importarADocumentumActionPerformed(evt);
            }
        });
        opcionUtilidades.add(importarADocumentum);

        opcionExportarCarpeta.setText("Exportar Carpeta de Documentum");
        opcionExportarCarpeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionExportarCarpetaActionPerformed(evt);
            }
        });
        opcionUtilidades.add(opcionExportarCarpeta);
        opcionUtilidades.add(jSeparator2);

        opcionLeerLog.setText("Leer fichero Log");
        opcionLeerLog.setActionCommand("LeerFicheroLog");
        opcionLeerLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionLeerLogActionPerformed(evt);
            }
        });
        opcionUtilidades.add(opcionLeerLog);

        opcionPasswordLDAP.setText("Cambiar Password LDAP");
        opcionPasswordLDAP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionPasswordLDAPActionPerformed(evt);
            }
        });
        opcionUtilidades.add(opcionPasswordLDAP);
        opcionUtilidades.add(jSeparator4);

        opcionEstadisticasRepos.setText("Estadísticas de Repositorios");
        opcionEstadisticasRepos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionEstadisticasReposActionPerformed(evt);
            }
        });
        opcionUtilidades.add(opcionEstadisticasRepos);

        opcionIndexador.setText("Indexador");
        opcionIndexador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionIndexadorActionPerformed(evt);
            }
        });
        opcionUtilidades.add(opcionIndexador);

        opcionJobs.setText("Jobs");
        opcionJobs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionJobsActionPerformed(evt);
            }
        });
        opcionUtilidades.add(opcionJobs);
        opcionUtilidades.add(jSeparator5);

        opcionEjecutarComandoSSOO.setText("Ejecutar comando de Sistema");
        opcionEjecutarComandoSSOO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionEjecutarComandoSSOOActionPerformed(evt);
            }
        });
        opcionUtilidades.add(opcionEjecutarComandoSSOO);

        MenuDocumentum.add(opcionUtilidades);

        opcionAcercade.setText("Acerca de");

        opcionManual.setText("Manual de la Aplicación");
        opcionManual.setToolTipText("Manual de la Aplicación");
        opcionManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionManualActionPerformed(evt);
            }
        });
        opcionAcercade.add(opcionManual);

        Acercade.setText("Acerca de ...");
        Acercade.setToolTipText("Acerca de DocumentumDFCs");
        Acercade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AcercadeActionPerformed(evt);
            }
        });
        opcionAcercade.add(Acercade);

        MenuDocumentum.add(opcionAcercade);

        setJMenuBar(MenuDocumentum);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDocumentos, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
            .addComponent(panelEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 571, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opcionExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionExportarActionPerformed
        ExportarDeDocumentum();
    }//GEN-LAST:event_opcionExportarActionPerformed

    private void tablaDocumentosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDocumentosMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            componente = "tablaDocumentos";
            botonderecho = true;
            popupmenu(evt);
        }
    }//GEN-LAST:event_tablaDocumentosMousePressed

    private void tablaDocumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDocumentosMouseClicked
        if (tablaDocumentos.getSelectedRow() < 0) {
            return;
        }

        if (evt.getClickCount() == 2 && evt.getButton() == java.awt.event.MouseEvent.BUTTON1 && tablaDocumentos.getModel().getRowCount() > 0) {
            //     Utilidades.escribeLog("Doble click : " + tablaDocumentos.getModel().getValueAt(tablaDocumentos.getSelectedRow(), 0).toString());
            cargarAtributos(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString());
            utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
            textoRutaDocumentum.setText(utilDocum.DameAtributo(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString(), "r_folder_path").toString().replace("[", "").replace("]", ""));
            if (tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 2).toString().equals("dm_folder")
                    || tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString().startsWith("0b")) {
                textoRutaDocumentum.setText(textoRutaDocumentum.getText() + "/" + tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString());
                BuscarEnDocumentum();
            } else if (tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 2).toString().equals("dm_cabinet")) {
                textoRutaDocumentum.setText("/" + tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString());
                BuscarEnDocumentum();
            } else {
                Utilidades.copiarTextoPortapapeles(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString());
            }
        } else if (tablaDocumentos.getModel().getRowCount() > 0) {
            cargarAtributos(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString());
            utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
            textoRutaDocumentum.setText(utilDocum.DameAtributo(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString(), "r_folder_path").toString().replace("[", "").replace("]", ""));

        } else if (evt.getClickCount() == 1 && evt.getButton() == java.awt.event.MouseEvent.BUTTON3 && tablaDocumentos.getModel().getRowCount() > 0) {
            botonderecho = true;
            popupmenu(evt);
        }
    }//GEN-LAST:event_tablaDocumentosMouseClicked

    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarActionPerformed
        BuscarEnDocumentum();
    }//GEN-LAST:event_botonBuscarActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        salir();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void scrollGridMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollGridMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            popupmenu(evt);
        }
    }//GEN-LAST:event_scrollGridMousePressed

    private void opcionSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionSalirActionPerformed
        salir();
    }//GEN-LAST:event_opcionSalirActionPerformed

    private void textoIdDocumentumMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textoIdDocumentumMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "textoIdDocumentum";
            popupmenu(evt);
        }
    }//GEN-LAST:event_textoIdDocumentumMousePressed

    private void opcionCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarActionPerformed
        if (componente.equals("textoIdDocumentum")) {
            if (textoIdDocumentum.getText() == null) {
                return;
            }
            if (textoIdDocumentum.getSelectedText().isEmpty()) {
                Utilidades.copiarTextoPortapapeles(textoIdDocumentum.getText());
            } else {
                Utilidades.copiarTextoPortapapeles(textoIdDocumentum.getSelectedText());
            }
        }
        if (componente.equals("textoCarpeta")) {
            if (textoCarpeta.getText() == null) {
                return;
            }
            if (textoCarpeta.getSelectedText().isEmpty()) {
                Utilidades.copiarTextoPortapapeles(textoCarpeta.getText());
            } else {
                Utilidades.copiarTextoPortapapeles(textoCarpeta.getSelectedText());
            }
        }
        if (componente.equals("textoRutaDocumentum")) {
            if (textoRutaDocumentum.getText() == null) {
                return;
            }
            if (textoRutaDocumentum.getSelectedText().isEmpty()) {
                Utilidades.copiarTextoPortapapeles(textoRutaDocumentum.getText());
            } else {
                Utilidades.copiarTextoPortapapeles(textoRutaDocumentum.getSelectedText());
            }
        }

        if (componente.equals("EtiquetaEstado")) {
            Utilidades.copiarTextoPortapapeles(EtiquetaEstado.getText());
        }
    }//GEN-LAST:event_opcionCopiarActionPerformed

    private void opcionPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionPegarActionPerformed
        if (componente.equals("textoIdDocumentum")) {
            textoIdDocumentum.setText(Utilidades.pegarTextoPortapapeles());
        }
        if (componente.equals("textoCarpeta")) {
            textoCarpeta.setText(Utilidades.pegarTextoPortapapeles());
        }
        if (componente.equals("textoRutaDocumentum")) {
            textoRutaDocumentum.setText(Utilidades.pegarTextoPortapapeles());
        }

    }//GEN-LAST:event_opcionPegarActionPerformed

    private void textoCarpetaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textoCarpetaMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "textoCarpeta";
            popupmenu(evt);
        }
    }//GEN-LAST:event_textoCarpetaMousePressed

    private void botonConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConectarActionPerformed
        ConexionDocumentum();
        LimpiarPantalla();
    }//GEN-LAST:event_botonConectarActionPerformed

    private void opcionConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionConectarActionPerformed
        ConexionDocumentum();
        LimpiarPantalla();
    }//GEN-LAST:event_opcionConectarActionPerformed

    private void opcionBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionBuscarActionPerformed
        BuscarEnDocumentum();
        System.gc();
    }//GEN-LAST:event_opcionBuscarActionPerformed

    private void EtiquetaEstadoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EtiquetaEstadoMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "EtiquetaEstado";
            popupmenu(evt);
        }
    }//GEN-LAST:event_EtiquetaEstadoMousePressed

    private void EtiquetaEstadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EtiquetaEstadoMouseEntered
        if (!EtiquetaEstado.getText().isEmpty()) {
            EtiquetaEstado.setToolTipText("<html>" + EtiquetaEstado.getText());
        }
    }//GEN-LAST:event_EtiquetaEstadoMouseEntered

    private void textoRutaDocumentumMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textoRutaDocumentumMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "textoRutaDocumentum";
            popupmenu(evt);
        }
    }//GEN-LAST:event_textoRutaDocumentumMousePressed

    private void tablaAtributosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaAtributosMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "tablaAtributos";
            popupmenu(evt);
        }
    }//GEN-LAST:event_tablaAtributosMousePressed

    private void opcionCopiarValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarValorActionPerformed
        if (componente.equals("tablaAtributos")) {
            if (tablaAtributos.getModel().getValueAt(tablaAtributos.getSelectedRow(), 1) != null) {
                Utilidades.copiarTextoPortapapeles(tablaAtributos.getModel().getValueAt(tablaAtributos.convertRowIndexToModel(tablaAtributos.getSelectedRow()), 1).toString());
            }
        }
    }//GEN-LAST:event_opcionCopiarValorActionPerformed

    private void opcionCopiarAtributoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarAtributoActionPerformed
        if (componente.equals("tablaAtributos")) {
            Utilidades.copiarTextoPortapapeles(tablaAtributos.getModel().getValueAt(tablaAtributos.convertRowIndexToModel(tablaAtributos.getSelectedRow()), 0).toString());
        }
    }//GEN-LAST:event_opcionCopiarAtributoActionPerformed

    private void opcionCopiarIDDocumentumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarIDDocumentumActionPerformed
        if (componente.equals("tablaDocumentos")) {
            Utilidades.copiarTextoPortapapeles(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString());
        }
    }//GEN-LAST:event_opcionCopiarIDDocumentumActionPerformed

    private void opcionCopiarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarNombreActionPerformed
        if (componente.equals("tablaDocumentos")) {
            Utilidades.copiarTextoPortapapeles(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString());
        }
    }//GEN-LAST:event_opcionCopiarNombreActionPerformed

    private void opcionRBMetalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRBMetalActionPerformed
        opcionRBMetal.setSelected(true);
        opcionRBNimbus.setSelected(false);
        opcionRBWindows.setSelected(false);
        opcionRBWindowsClassic.setSelected(false);
        opcionRBPorDefecto.setSelected(false);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
            System.gc();
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al cambiar Aspecto Visual a Metal - " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionRBMetalActionPerformed

    private void opcionRBNimbusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRBNimbusActionPerformed
        opcionRBMetal.setSelected(false);
        opcionRBNimbus.setSelected(true);
        opcionRBWindows.setSelected(false);
        opcionRBWindowsClassic.setSelected(false);
        opcionRBPorDefecto.setSelected(false);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
            System.gc();
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al cambiar Aspecto Visual a Nimbus - " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionRBNimbusActionPerformed

    private void opcionRBWindowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRBWindowsActionPerformed
        opcionRBMetal.setSelected(false);
        opcionRBNimbus.setSelected(false);
        opcionRBWindows.setSelected(true);
        opcionRBWindowsClassic.setSelected(false);
        opcionRBPorDefecto.setSelected(false);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
            System.gc();
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al cambiar Aspecto Visual a Windows - " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionRBWindowsActionPerformed

    private void opcionRBWindowsClassicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRBWindowsClassicActionPerformed
        opcionRBMetal.setSelected(false);
        opcionRBNimbus.setSelected(false);
        opcionRBWindows.setSelected(false);
        opcionRBWindowsClassic.setSelected(true);
        opcionRBPorDefecto.setSelected(false);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
            System.gc();
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al cambiar Aspecto Visual a Windows Classic - " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionRBWindowsClassicActionPerformed

    private void opcionRBPorDefectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRBPorDefectoActionPerformed
        try {
            opcionRBMetal.setSelected(false);
            opcionRBNimbus.setSelected(false);
            opcionRBWindows.setSelected(false);
            opcionRBWindowsClassic.setSelected(false);
            opcionRBPorDefecto.setSelected(true);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
            System.gc();
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al cambiar Aspecto por Defecto - " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionRBPorDefectoActionPerformed

    private void opcionAbrirDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionAbrirDocumentoActionPerformed
        AbrirFicheroDeDocumentum();
    }//GEN-LAST:event_opcionAbrirDocumentoActionPerformed

    private void opcionManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionManualActionPerformed
        String manual = dirdfc + "manual" + util.separador() + "DocumentumDFCs.pdf";
        try {
            File path = new File(manual);
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            Utilidades.escribeLog("Error al abrir el manual de la aplicación (" + manual + ") - Error " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionManualActionPerformed

    private void AcercadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AcercadeActionPerformed
        mostrarAcercade();
    }//GEN-LAST:event_AcercadeActionPerformed

    private void opcionDqlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionDqlActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaDql pantallaDql = new PantallaDql(this, true);
            pantallaDql.setTitle("Consultas a Documentum por DQL  -  " + EtiquetaDocbroker.getText() + " / " + EtiquetaRepositorio.getText());
            pantallaDql.setVisible(true);
        } else {
            EtiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionDqlActionPerformed

    private void opcionActualizarAtributoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionActualizarAtributoActionPerformed
        ActualizarAtributo();
    }//GEN-LAST:event_opcionActualizarAtributoActionPerformed

    private void opcionBorrarDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionBorrarDocumentoActionPerformed
        PantallaConfirmaDialogo confirma = new PantallaConfirmaDialogo(this, true);
        confirma.setTitle("Borrar Documento de Documentum");
        confirma.etiqueta.setText("¿Desea realmente borrar el Documento con ID " + tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString() + "?");
        confirma.etiqueta.setOpaque(false);
        confirma.etiqueta.setBorder(BorderFactory.createEmptyBorder());
        confirma.etiqueta.setBackground(new Color(0, 0, 0, 0));

        confirma.repaint();
        confirma.setVisible(true);
        Boolean resultado = confirma.respuesta();
        if (resultado) {
            BorrarDocumento();
        }
    }//GEN-LAST:event_opcionBorrarDocumentoActionPerformed

    private void opcionBorradoLogicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionBorradoLogicoActionPerformed
        PantallaConfirmaDialogo confirma = new PantallaConfirmaDialogo(this, true);
        confirma.setTitle("Borrado Lógico de Documento de Documentum");
        confirma.etiqueta.setText("¿Desea realmente marcar como borrado el Documento con ID " + tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString() + "?");
        confirma.etiqueta.setOpaque(false);
        confirma.etiqueta.setBorder(BorderFactory.createEmptyBorder());
        confirma.etiqueta.setBackground(new Color(0, 0, 0, 0));

        confirma.repaint();
        confirma.setVisible(true);
        Boolean resultado = confirma.respuesta();
        if (resultado) {
            BorradoLogicoDocumento();
        }
    }//GEN-LAST:event_opcionBorradoLogicoActionPerformed

    private void importarADocumentumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importarADocumentumActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaImportar pantallaimportar = new PantallaImportar(this, true);
            pantallaimportar.setLocationRelativeTo(this);
            pantallaimportar.setVisible(true);
        } else {
            EtiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_importarADocumentumActionPerformed

    private void opcionExportarDocumentosExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionExportarDocumentosExcelActionPerformed
        ExportarDocumentosExcel();
    }//GEN-LAST:event_opcionExportarDocumentosExcelActionPerformed

    private void opcionExportarAtributosExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionExportarAtributosExcelActionPerformed
        ExportarAtributosExcel();
    }//GEN-LAST:event_opcionExportarAtributosExcelActionPerformed

    private void tablaDocumentosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaDocumentosKeyTyped
        mostrarAtributos();
    }//GEN-LAST:event_tablaDocumentosKeyTyped

    private void opcionLeerLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionLeerLogActionPerformed
        Calendar cal = Calendar.getInstance();
        String anio = String.valueOf(cal.get(Calendar.YEAR));
        String mes = String.valueOf((cal.get(Calendar.MONTH) + 1)).length() == 1 ? "0" + String.valueOf((cal.get(Calendar.MONTH) + 1)) : String.valueOf((cal.get(Calendar.MONTH) + 1));
        String dia = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)).length() == 1 ? "0" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        PantallaLeerFichero log = new PantallaLeerFichero(this, true);
        log.setVisible(true);
        log.CargarFichero(dirbase + util.separador() + "DocumentumDFCs-" + dia + "-" + mes + "-" + anio + ".log");
        log.setTitle("Fichero de log " + dirbase + util.separador() + "DocumentumDFCs-" + dia + "-" + mes + "-" + anio + ".log");
        System.gc();
    }//GEN-LAST:event_opcionLeerLogActionPerformed

    private void botonArribaDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonArribaDirActionPerformed
        String ruta = textoRutaDocumentum.getText();
        int posicion = ruta.lastIndexOf("/");

        if (ruta.equals("/")) {
            botonArribaDir.setEnabled(false);
            textoRutaDocumentum.setText("/");
        } else {
            botonArribaDir.setEnabled(true);
            if (posicion >= 0) {
                String rutapadre = posicion == 0 ? "/" : ruta.substring(0, posicion);
                textoRutaDocumentum.setText(rutapadre);
                BuscarEnDocumentum();
            }
        }
    }//GEN-LAST:event_botonArribaDirActionPerformed

    private void opcionDumpAtributosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionDumpAtributosActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaAtributos dump = new PantallaAtributos(this, true);
            dump.setTitle("Volcado de Atributos de Objetos de Documentum   -   " + EtiquetaRepositorio.getText() + " / " + EtiquetaDocbroker.getText());
            String idDocumentum = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
            dump.setIdDocumentum(idDocumentum);
            dump.cargarAtributos(idDocumentum);
            dump.setVisible(true);
            System.gc();
        } else {
            EtiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }

    }//GEN-LAST:event_opcionDumpAtributosActionPerformed

    private void opcionPasswordLDAPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionPasswordLDAPActionPerformed
        /*
        try {
            utilDocum.setLdapPassword("documentum");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PantallaDocumentum.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
    }//GEN-LAST:event_opcionPasswordLDAPActionPerformed

    private void opcionExportarCarpetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionExportarCarpetaActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaPedirDatos pedirDatos = new PantallaPedirDatos(this, true);
            pedirDatos.setTitle("Datos para exportar carpeta de Documentum");
            pedirDatos.setVisible(true);
            String carpeta = pedirDatos.getCarpeta();
            String ruta = pedirDatos.getRuta();
            if (carpeta != null && !carpeta.isEmpty() && ruta != null && !ruta.isEmpty()) {
                exportarCarpeta(carpeta, ruta);
            }
        } else {
            EtiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionExportarCarpetaActionPerformed

    private void opcionEstadisticasReposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionEstadisticasReposActionPerformed
        PantallaPedirFechas pedirDatos = new PantallaPedirFechas(this, true);
        pedirDatos.setTitle("Fechas para Estadísticas de Repositorios");
        pedirDatos.setVisible(true);
        final String fechainicial = pedirDatos.getFechainicial();
        final String fechafinal = pedirDatos.getFechafinal();
        final String correodestino = pedirDatos.getCorreo();
        final boolean resultado = pedirDatos.getRespuesta();

        final String fechafichero = fechainicial.replace("/", "") + "-" + fechafinal.replace("/", "");

        if (!resultado || (fechainicial == null) || (fechafinal == null)) {
            return;
        }

        new Thread() {
            public void run() {
                barradocum = new PantallaBarra(PantallaDocumentum.this, false);
                barradocum.setTitle("Calculando estadísticas de repositorios ...");
                barradocum.barra.setIndeterminate(true);
                barradocum.botonParar.setVisible(false);
                barradocum.setLabelMensa("");
                barradocum.barra.setOpaque(true);
                barradocum.barra.setStringPainted(false);
                barradocum.validate();
                barradocum.setVisible(true);
                BufferedReader br = null;
                String fichero = dirbase + util.separador() + "repos.txt";
                try {
                    String s1;
                    // Cargamos el buffer con el contenido del archivo
                    br = new BufferedReader(new FileReader(fichero));
                    int lNumeroLineas = 0;
                    while (br.readLine() != null) {
                        lNumeroLineas++;
                    }
                    Object[][] datos = new Object[lNumeroLineas][4];
                    br.close();
                    br = new BufferedReader(new FileReader(fichero));
                    lNumeroLineas = 0;
                    while ((s1 = br.readLine()) != null) {
                        String[] result = s1.split(";");
                        String servidor = result[0];
                        String repositorio = result[1];
                        String usuario = result[2];
                        String clave = result[3];
                        String puerto = result[4];
                        barradocum.setLabelMensa(servidor + "  <-->  " + repositorio);
                        //   System.out.println(repositorio + "-" + servidor + "-" + puerto + "-" + usuario + "-" + clave);
                        IDfSession sesion = utilDocum.conectarDocumentum(usuario, clave, repositorio, servidor, puerto);
                        IDfCollection col = utilDocum.ejecutarDql("select count(*) from dmr_content where r_object_id in (select i_contents_id from  dm_sysobject where A_STORAGE_TYPE='filestore_01' and (R_CREATION_DATE > DATE('" + fechainicial + "','DD/MM/YYYY')) and (R_CREATION_DATE < DATE('" + fechafinal + "','DD/MM/YYYY')) )", sesion);
                        ArrayList filas = new ArrayList();
                        datos[lNumeroLineas][0] = servidor;
                        datos[lNumeroLineas][1] = repositorio;
                        try {
                            while (col.next()) {
                                filas.add(col.getTypedObject());
                            }
                            col.close();
                            IDfTypedObject row = (IDfTypedObject) filas.get(0);
                            IDfAttr attr = row.getAttr(0);
                            IDfValue attrValue = row.getValue(attr.getName());
                            String resultado = (String) getDfObjectValue(attrValue);
                            DecimalFormat formateador = new DecimalFormat("###,###");
                            datos[lNumeroLineas][2] = formateador.format(Double.parseDouble(resultado));
                            System.out.println(repositorio + " - Ficheros: " + formateador.format(Double.parseDouble(resultado)));
                        } catch (Exception Ex) {
                            System.out.println(Ex.getMessage());
                        }

                        col = utilDocum.ejecutarDql("select sum(full_content_size)  from dmr_content where r_object_id in (select i_contents_id from  dm_sysobject where A_STORAGE_TYPE='filestore_01' and (R_CREATION_DATE > DATE('" + fechainicial + "','DD/MM/YYYY')) and (R_CREATION_DATE < DATE('" + fechafinal + "','DD/MM/YYYY')) )", sesion);
                        filas = new ArrayList();
                        try {
                            while (col.next()) {
                                filas.add(col.getTypedObject());
                            }
                            col.close();
                            IDfTypedObject row = (IDfTypedObject) filas.get(0);
                            IDfAttr attr = row.getAttr(0);
                            IDfValue attrValue = row.getValue(attr.getName());
                            Double resultado = Double.parseDouble((String) getDfObjectValue(attrValue));
                            datos[lNumeroLineas][3] = util.humanReadableByteCount(Math.round(resultado), false);
                            System.out.println(repositorio + " - Tamaño de los Ficheros: " + util.humanReadableByteCount(Math.round(resultado), false));
                        } catch (Exception Ex) {
                            System.out.println(Ex.getMessage());
                        }
                        lNumeroLineas++;

                    }

                    String[] cabecera = {"Servidor", "Repositorio", "Nº de Ficheros", "Tamaño de los Ficheros"};
                    DefaultTableModel modeloLotes = new DefaultTableModel() {
                        @Override
                        public boolean isCellEditable(int fila, int columna) {
                            return false;
                        }
                    };
                    if (datos.length > 0) {
                        modeloLotes = new DefaultTableModel(datos, cabecera) {
                            @Override
                            public boolean isCellEditable(int fila, int columna) {
                                return false;
                            }
                        };
                        modeloLotes.setRowCount(lNumeroLineas);
                        javax.swing.JTable tablaResultados = new JTable(modeloLotes);
                        fichero = dirbase + util.separador() + "Documentum-estadisticas-" + fechafichero + ".xls";
                        util.exportaExcel(tablaResultados, fichero);
                        EtiquetaEstado.setText("Generado el fichero " + fichero);
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                } finally {
                    try {
                        if (br != null) {
                            br.close();
                        }
                        if (!correodestino.isEmpty()) {
                            Correo correo = new Correo();
                            correo.setCorreoPropiedades(new CorreoPropiedades());
                            correo.getCorreoPropiedades().setUsuario("null");
                            correo.getCorreoPropiedades().setClave("null");
                            correo.getCorreoPropiedades().setServidorsmtp("smtp.adif.es");
                            correo.getCorreoPropiedades().setPuertosmtp("25");
                            correo.getCorreoPropiedades().setPropiedades(new Properties());
                            correo.setAsunto("Estádisticas de Repositorios de Documentum");
                            correo.setCuerpo("Hola,"
                                    + "\n\nEstádisticas de Repositorios de Documentum desde el " + fechainicial + " hasta el " + fechafinal
                                    + "\n\n Un saludo");
                            correo.setEnviarA(correodestino);
                            // correo.setEnviarBCC("jcollado@gmail.com");
                            correo.setEnviadoDesde("documentum@adif.es");
                            correo.setAdjuntos(new String[]{fichero});
                            String res = correo.Enviar();
                            System.out.println("Correo enviado: " + res);
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                barradocum.dispose();

            }
        }.start();
        System.gc();
    }//GEN-LAST:event_opcionEstadisticasReposActionPerformed

    private void opcionAPIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionAPIActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaApi pantallaApi = new PantallaApi(this, true);
            pantallaApi.setTitle("Comandos de API  -  " + EtiquetaDocbroker.getText() + " / " + EtiquetaRepositorio.getText());
            pantallaApi.setVisible(true);
        } else {
            EtiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }

    }//GEN-LAST:event_opcionAPIActionPerformed

    private void opcionEjecutarComandoSSOOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionEjecutarComandoSSOOActionPerformed
        PantallaEjecutarComandoRemoto pantallaComandoRemoto = new PantallaEjecutarComandoRemoto(this, true);
        pantallaComandoRemoto.setTitle("Ejecutar comando bash de Sistema Operativo remoto  -  " + docbroker);
        pantallaComandoRemoto.setUsuario(usuario);
        pantallaComandoRemoto.setClave(clave);
        pantallaComandoRemoto.setServidor(docbroker);
        pantallaComandoRemoto.setTextoServidor(docbroker);
        pantallaComandoRemoto.setTextoUsuario(usuario);
        pantallaComandoRemoto.setTextoClave(clave);
        pantallaComandoRemoto.setVisible(true);
    }//GEN-LAST:event_opcionEjecutarComandoSSOOActionPerformed

    private void opcionCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCheckoutActionPerformed
        IDfSession sesion = utilDocum.conectarDocumentum();
        if (sesion == null) {
            Utilidades.escribeLog("No se pudo obtener sesión de Documentum (checkout)");

        }
        String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
        try {
            String resultado = utilDocum.checkoutDoc(r_object_id, sesion);
            if (resultado.contains("Error al hacer checkout de")) {
                EtiquetaEstado.setText(resultado);
            } else {
                int fila = tablaDocumentos.getSelectedRow();
                int columna = 5;
                java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/bloqueado.gif");
                ImageIcon iconoCheckout = new ImageIcon(imgURL);
                tablaDocumentos.getModel().setValueAt(iconoCheckout, fila, columna);
                //   tablaDocumentos.getModel().setValueAt("*", fila, columna);            
                opcionCancelCheckout.setVisible(true);
                opcionCheckin.setVisible(true);
                opcionCheckout.setVisible(false);
            }
        } catch (Exception ex) {
            Utilidades.escribeLog("No se pudo hacer check out de " + r_object_id + " - Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionCheckoutActionPerformed

    private void opcionCheckinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCheckinActionPerformed
        IDfSession sesion = utilDocum.conectarDocumentum();
        if (sesion == null) {
            Utilidades.escribeLog("No se pudo obtener sesión de Documentum (checkin)");
        }
        String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
        String fichero = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString();
        try {
            PantallaCheckin pantallaCheckin = new PantallaCheckin(this, true);
            pantallaCheckin.setTitle("Check In de " + fichero);
            pantallaCheckin.setFichero(fichero);
            pantallaCheckin.setR_object_id(r_object_id);
            pantallaCheckin.setLabelFichero(fichero);
            pantallaCheckin.setVisible(true);

            Boolean resultado = pantallaCheckin.respuesta();
            String version = pantallaCheckin.getVersion();
            String descripcion = pantallaCheckin.getDescripcion();
            Boolean indexar = pantallaCheckin.getIndexar();
            if (resultado) {
                utilDocum.checkinDoc(r_object_id, sesion, fichero, version, descripcion, indexar);
                int fila = tablaDocumentos.getSelectedRow();
                int columna = 5;
                java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/vacio.gif");
                ImageIcon iconoCheckin = new ImageIcon(imgURL);
                tablaDocumentos.getModel().setValueAt(iconoCheckin, fila, columna);
                // tablaDocumentos.getModel().setValueAt(" ", fila, columna);
                opcionCancelCheckout.setVisible(false);
                opcionCheckin.setVisible(false);
                opcionCheckout.setVisible(true);
            }
        } catch (Exception ex) {
            Utilidades.escribeLog("No se pudo hacer check in de " + r_object_id + " - Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionCheckinActionPerformed

    private void opcionCancelCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCancelCheckoutActionPerformed
        IDfSession sesion = utilDocum.conectarDocumentum();
        if (sesion == null) {
            Utilidades.escribeLog("No se pudo obtener sesión de Documentum (CancelCheckout)");

        }
        String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
        try {
            utilDocum.cancelCheckout(r_object_id, sesion);
            int fila = tablaDocumentos.getSelectedRow();
            int columna = 5;
            java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/vacio.gif");
            ImageIcon iconoCheckin = new ImageIcon(imgURL);
            tablaDocumentos.getModel().setValueAt(iconoCheckin, fila, columna);
            // tablaDocumentos.getModel().setValueAt(" ", fila, columna);
            opcionCancelCheckout.setVisible(false);
            opcionCheckin.setVisible(false);
            opcionCheckout.setVisible(true);
        } catch (Exception ex) {
            Utilidades.escribeLog("No se pudo hacer cancel check out de " + r_object_id + " - Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionCancelCheckoutActionPerformed

    private void textoRutaDocumentumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoRutaDocumentumKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            BuscarEnDocumentum();
        }
    }//GEN-LAST:event_textoRutaDocumentumKeyPressed

    private void textoCarpetaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoCarpetaKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            BuscarEnDocumentum();
        }
    }//GEN-LAST:event_textoCarpetaKeyPressed

    private void textoIdDocumentumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoIdDocumentumKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            BuscarEnDocumentum();
        }
    }//GEN-LAST:event_textoIdDocumentumKeyPressed

    private void opcionRenditionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRenditionsActionPerformed
        if (botonBuscar.isEnabled()) {
            String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
            String nombre = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString();
            PantallaRenditions pantallaRenditions = new PantallaRenditions(this, true);
            pantallaRenditions.setTitle("Renditions del fichero " + r_object_id);
            pantallaRenditions.setIdDocumentum(r_object_id + " - " + nombre);
            pantallaRenditions.cargarRenditions(r_object_id);
            pantallaRenditions.setVisible(true);
        } else {
            EtiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionRenditionsActionPerformed

    private void opcionConvertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionConvertirActionPerformed
        String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
        if (utilDocum.hayADTS()) {
            utilDocum.convierteDocumento(utilDocum.conectarDocumentum(), r_object_id, "pdf");
        }
    }//GEN-LAST:event_opcionConvertirActionPerformed

    private void opcionIndexadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionIndexadorActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaTareasIndexador pantallaTareasIndexador = new PantallaTareasIndexador();
            pantallaTareasIndexador.setTitle("Indexador para " + repositorio);
            pantallaTareasIndexador.setDocbase(repositorio);
            pantallaTareasIndexador.setDocbroker(docbroker);
            IDfSession sesion = utilDocum.conectarDocumentum();
            pantallaTareasIndexador.setSesion(sesion);
            pantallaTareasIndexador.cargarTabla();
            pantallaTareasIndexador.setVisible(true);
        } else {
            EtiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionIndexadorActionPerformed

    private void opcionJobsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionJobsActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaJobs pantallaJobs = new PantallaJobs(this, true);
            pantallaJobs.setTitle("Jobs de " + repositorio);
            pantallaJobs.cargarJobs();
            pantallaJobs.setVisible(true);
        } else {
            EtiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionJobsActionPerformed

    public void mostrarAcercade() {
        Acercade about = new Acercade(this, true);
        about.setDirdfc(dirdfc);
        about.setDocbroker(docbroker);
        about.setRepositorio(repositorio);
        about.setUsuario(usuario);
        about.setVersiondocumentum(versiondocumentum);
        about.setIdrepositorio(utilDocum.DameIdRepositorio());
        about.setLocationRelativeTo(this);
        about.setVisible(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                final PantallaDocumentum dialog = new PantallaDocumentum();
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        dialog.dispose();
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Acercade;
    public javax.swing.JLabel EtiquetaDocbroker;
    public javax.swing.JLabel EtiquetaEstado;
    public javax.swing.JLabel EtiquetaRepositorio;
    private javax.swing.JMenuBar MenuDocumentum;
    private javax.swing.JButton botonArribaDir;
    private javax.swing.JButton botonBuscar;
    private javax.swing.JButton botonConectar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JMenuItem importarADocumentum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JMenuItem opcionAPI;
    private javax.swing.JMenuItem opcionAbrirDocumento;
    private javax.swing.JMenu opcionAcercade;
    private javax.swing.JMenuItem opcionActualizarAtributo;
    private javax.swing.JMenu opcionApariencia;
    private javax.swing.JMenuItem opcionBorradoLogico;
    private javax.swing.JMenuItem opcionBorrarDocumento;
    private javax.swing.JMenuItem opcionBuscar;
    private javax.swing.JMenuItem opcionCancelCheckout;
    private javax.swing.JMenuItem opcionCheckin;
    private javax.swing.JMenuItem opcionCheckout;
    private javax.swing.JMenuItem opcionConectar;
    private javax.swing.JMenuItem opcionConvertir;
    private javax.swing.JMenuItem opcionCopiar;
    private javax.swing.JMenuItem opcionCopiarAtributo;
    private javax.swing.JMenuItem opcionCopiarIDDocumentum;
    private javax.swing.JMenuItem opcionCopiarNombre;
    private javax.swing.JMenuItem opcionCopiarValor;
    private javax.swing.JMenuItem opcionDql;
    private javax.swing.JMenuItem opcionDumpAtributos;
    private javax.swing.JMenuItem opcionEjecutarComandoSSOO;
    private javax.swing.JMenuItem opcionEstadisticasRepos;
    private javax.swing.JMenuItem opcionExportar;
    private javax.swing.JMenuItem opcionExportarAtributosExcel;
    private javax.swing.JMenuItem opcionExportarCarpeta;
    private javax.swing.JMenuItem opcionExportarDocumentosExcel;
    private javax.swing.JMenuItem opcionIndexador;
    private javax.swing.JMenuItem opcionJobs;
    private javax.swing.JMenuItem opcionLeerLog;
    private javax.swing.JMenuItem opcionManual;
    private javax.swing.JMenu opcionOpciones;
    private javax.swing.JMenuItem opcionPasswordLDAP;
    private javax.swing.JMenuItem opcionPegar;
    private javax.swing.JRadioButtonMenuItem opcionRBMetal;
    private javax.swing.JRadioButtonMenuItem opcionRBNimbus;
    private javax.swing.JRadioButtonMenuItem opcionRBPorDefecto;
    private javax.swing.JRadioButtonMenuItem opcionRBWindows;
    private javax.swing.JRadioButtonMenuItem opcionRBWindowsClassic;
    private javax.swing.JMenuItem opcionRenditions;
    private javax.swing.JMenuItem opcionSalir;
    private javax.swing.JMenu opcionUtilidades;
    private javax.swing.JPanel panelDocumentos;
    private javax.swing.JPanel panelEntrada;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JPopupMenu popupAtributos;
    private javax.swing.JPopupMenu popupDocumentos;
    private javax.swing.JPopupMenu popupEditar;
    private javax.swing.JScrollPane scrollGrid;
    private javax.swing.JPopupMenu.Separator separador;
    private javax.swing.JTable tablaAtributos;
    private javax.swing.JTable tablaDocumentos;
    private javax.swing.JTextField textoCarpeta;
    private javax.swing.JTextField textoIdDocumentum;
    private javax.swing.JTextField textoRutaDocumentum;
    // End of variables declaration//GEN-END:variables

    private void cargarAtributos(String pr_object_id) {
        final String r_object_id = pr_object_id;
        new Thread() {
            public void run() {
                String mensajeborrado = "";
                Color colormensaje = Color.BLACK;
                DefaultTableModel modeloLotes = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int fila, int columna) {
                        if (columna == 0) //Con esto se pueden editar todas las celdas menos la de la columna 0
                        {
                            return false;
                        } else {
                            return true;
                        }
                    }
                };
                utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
                if (!esadmin) {
                    modeloLotes = new DefaultTableModel() {
                        @Override
                        public boolean isCellEditable(int fila, int columna) {
                            if (columna == 0) //Con esto se pueden editar todas las celdas menos la de la columna 0
                            {
                                return false;
                            } else {
                                return true;

                            }
                        }
                    };
                }
                if (r_object_id.isEmpty()) {
                    tablaAtributos.setModel(modeloLotes);
                    EtiquetaEstado.setText("");
                    return;
                }

                atributos = utilDocum.DameTodosAtributos(r_object_id);
                if (atributos.size() <= 0) {
                    tablaAtributos.setModel(modeloLotes);
                    if (!utilDocum.dameError().isEmpty()) {
                        if (utilDocum.dameError().contains("Bad ID given: 0000000000000000")) {
                            EtiquetaEstado.setText("No se encontró el ID de Documentum " + r_object_id);
                        } else {
                            EtiquetaEstado.setText(utilDocum.dameError());
                        }
                    } else {
                        EtiquetaEstado.setText("No se encontró el ID de Documentum " + r_object_id);
                    }
                    EtiquetaEstado.validate();
                    return;
                }

                Object[][] datos = new Object[atributos.size()][2];
                Object[] cabecera = {"Nombre del Atributo", "Valor del Atributo"};

                for (int n = 0; n < atributos.size(); n++) {
                    datos[n][0] = atributos.get(n).getNombre();
                    datos[n][1] = atributos.get(n).getValor();
                    if (atributos.get(n).getNombre().toLowerCase().endsWith("borrado_logico")) {
                        if (atributos.get(n).getValor() != null) {
                            if (atributos.get(n).getValor().equals("1")) {
                                mensajeborrado = "BORRADO LÓGICO DEL DOCUMENTO";
                                colormensaje = Color.RED;
                            } else {
                                mensajeborrado = "";
                            }
                        }
                    }
                }

                if (datos.length > 0) {
                    modeloLotes = new DefaultTableModel(datos, cabecera) {
                        @Override
                        public boolean isCellEditable(int fila, int columna) {
                            if (columna == 0) //Con esto se pueden editar todas las celdas menos la de la columna 0
                            {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    };

                } else {
                    modeloLotes = new DefaultTableModel() {
                        @Override
                        public boolean isCellEditable(int fila, int columna) {
                            if (columna == 0) //Con esto se pueden editar todas las celdas menos la de la columna 0
                            {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    };
                    EtiquetaEstado.setText("No se encontró el ID de Documentum " + r_object_id);
                }
                tablaAtributos.setModel(modeloLotes);
                TableColumn columna = tablaAtributos.getColumnModel().getColumn(0);
                columna.setPreferredWidth(250);
                columna.setMaxWidth(250);
                columna.sizeWidthToFit();
                tablaAtributos.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                tablaAtributos.doLayout();
                if (!mensajeborrado.isEmpty()) {
                    EtiquetaEstado.setText(mensajeborrado);
                    EtiquetaEstado.setForeground(colormensaje);
                    EtiquetaEstado.validate();
                }

            }
        }
                .start();

        System.gc();
    }

    private void cargarDocumentos(String pcarpeta, String ptipo) {
        final String carpeta = pcarpeta;
        final String tipo = ptipo;

        new Thread() {
            public void run() {
                DefaultTableModel modeloLotes = (DefaultTableModel) tablaDocumentos.getModel();
                try {
                    modeloLotes.setRowCount(0);
                    modeloLotes.fireTableDataChanged();
                } catch (Exception ex) {
                }
                if (carpeta.isEmpty()) {
                    EtiquetaEstado.setText("");
                    return;
                }

                barradocum.setTitle("Consultando en Documentum ...");
                barradocum.barra.setIndeterminate(true);
                barradocum.botonParar.setVisible(true);
                barradocum.setLabelMensa("");
                barradocum.barra.setOpaque(true);
                barradocum.barra.setStringPainted(false);
                barradocum.validate();
                barradocum.setVisible(true);

                utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
                ArrayList<AtributosDocumentum> documentos = new ArrayList<AtributosDocumentum>();
                utilDocum.setVentanapadre(PantallaDocumentum.this);
                if (tipo.toLowerCase().equals("carpeta")) {
                    documentos = utilDocum.ListarFicheros(carpeta);
                } else if (tipo.toLowerCase().equals("ruta")) {
                    documentos = utilDocum.ListarFicherosRuta(carpeta);
                } else if (tipo.toLowerCase().equals("id")) {
                    documentos = utilDocum.ListarFicherosId(carpeta);
                }

                if (documentos.size() <= 0) {
                    Object[][] datos = new Object[0][7];
                    Object[] cabecera = {"Nombre ", "ID Documentum (r_object_id)", "Tipo Documental", "Fecha Creación", "Usuario", "Check Out"};
                    modeloLotes = new DefaultTableModel(datos, cabecera) {
                        @Override
                        public boolean isCellEditable(int fila, int columna) {
                            return false;
                        }
                    };
                    tablaDocumentos.setModel(modeloLotes);
                    if (!utilDocum.dameError().isEmpty()) {
                        EtiquetaEstado.setText(utilDocum.dameError());
                    } else {
                        EtiquetaEstado.setText("No se encontraron Documentos en la carpeta " + carpeta);
                    }
                    barradocum.dispose();
                    return;
                }
                try {
                    Object[][] datos = new Object[documentos.size()][7];
                    Object[] cabecera = {"Nombre ", "ID Documentum (r_object_id)", "Tipo Documental", "Fecha Creación", "Usuario", "Check Out"};
                    java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/vacio.gif");
                    ImageIcon iconoCheckin = new ImageIcon(imgURL);
                    imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/bloqueado.gif");
                    ImageIcon iconoCheckout = new ImageIcon(imgURL);
                    for (int n = 0; n < documentos.size(); n++) {
                        datos[n][0] = documentos.get(n).getNombre();
                        datos[n][1] = documentos.get(n).getValor();
                        datos[n][2] = documentos.get(n).getTipoobjeto();
                        datos[n][3] = documentos.get(n).getFechacreacion();
                        datos[n][4] = documentos.get(n).getUsuario();
                        //  datos[n][5] = documentos.get(n).isCheckin() ? " " : "*";
                        datos[n][5] = documentos.get(n).isCheckin() ? iconoCheckin : iconoCheckout;
                    }

                    if (datos.length > 0) {
                        modeloLotes = new DefaultTableModel(datos, cabecera) {
                            @Override
                            public boolean isCellEditable(int fila, int columna) {
                                return false;
                            }
                            @Override
                            public Class<?> getColumnClass(int column) {
                                switch (column) {
                                    case 5:
                                        return ImageIcon.class;
                                    default:
                                        return Object.class;
                                }
                            }
                        };
                    }
                } catch (Exception ex) {
                }

                modeloLotes.setRowCount(documentos.size());
                tablaDocumentos.setModel(modeloLotes);
                TableColumn columna = tablaDocumentos.getColumnModel().getColumn(0);
                columna.setPreferredWidth(125);
                columna.setMinWidth(125);
                columna.sizeWidthToFit();

                tablaDocumentos.doLayout();
                if (tipo.toLowerCase().equals("id")) {
                    EtiquetaEstado.setText("Encontrado(s) " + documentos.size() + " documento(s) con id " + carpeta);
                } else {
                    EtiquetaEstado.setText("Encontrado(s) " + documentos.size() + " documento(s) en la carpeta " + carpeta);
                }
                barradocum.dispose();
                tablaDocumentos.setRowSelectionInterval(0, 0);
                mostrarAtributos();
            }
        }.start();
        System.gc();
    }

    private void mostrarAtributos() {
        cargarAtributos(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString());
        utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
//        textoRutaDocumentum.setText(utilDocum.DameAtributo(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString(), "r_folder_path").toString().replace("[", "").replace("]", ""));

    }

    private void asignarIconos() {
        java.net.URL imgURL = PantallaDocumentum.class
                .getClassLoader().getResource("es/documentum/imagenes/buscar_peq.png");
        Icon imgicon = new ImageIcon(imgURL);
        this.botonBuscar.setIcon(imgicon);

        imgURL
                = PantallaDocumentum.class
                .getClassLoader().getResource("es/documentum/imagenes/salir_peq.png");
        imgicon = new ImageIcon(imgURL);
        this.botonSalir.setIcon(imgicon);

        imgURL
                = PantallaDocumentum.class
                .getClassLoader().getResource("es/documentum/imagenes/conectar_peq.png");
        imgicon = new ImageIcon(imgURL);
        this.botonConectar.setIcon(imgicon);

    }

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
                if (componente.equals("tablaDocumentos")) {
                    if (row >= 0 && column >= 0 && tablaDocumentos.getModel().getRowCount() > 0) {
                        opcionExportar.setEnabled(true);
                        opcionBorradoLogico.setVisible(false);
                        String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
                        if (utilDocum.estaCheckin(r_object_id)) {
                            opcionCheckin.setVisible(false);
                            opcionCancelCheckout.setVisible(false);
                            opcionCheckout.setVisible(true);
                        } else {
                            opcionCheckin.setVisible(true);
                            opcionCancelCheckout.setVisible(true);
                            opcionCheckout.setVisible(false);
                        }
                        if (r_object_id.startsWith("09")) {
                            opcionRenditions.setVisible(true);
                            if (utilDocum.hayADTS()) {
                                opcionConvertir.setVisible(true);
                            } else {
                                opcionConvertir.setVisible(false);
                            }
                        } else {
                            opcionRenditions.setVisible(false);
                            opcionConvertir.setVisible(false);
                        }
                        popupDocumentos.show(evt.getComponent(), evt.getX(), evt.getY());
                    }

                }
                if (componente.equals("tablaAtributos")) {
                    if (row >= 0 && column >= 0 && tablaAtributos.getModel().getRowCount() > 0) {
                        popupAtributos.show(evt.getComponent(), evt.getX(), evt.getY());
                    }
                }

            }
            if (evt.getSource().getClass().getName().equals("javax.swing.JTextField")) {
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
                opcionPegar.setEnabled(true);
            }
            if (evt.getSource().getClass().getName().equals("javax.swing.JLabel")) {
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
                opcionPegar.setEnabled(false);
            }
        }
    }

    private void salir() {
        Utilidades.escribeLog("Salimos del programa DocumentumDFCs\n");
        System.exit(0);
    }

    private void inicializar() {
        asignarIconos();
        botonBuscar.setEnabled(false);
        opcionBuscar.setEnabled(false);
        botonConectar.setBackground(colornoconex);
        DefaultTableModel modeloLotes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        try {
            Object[][] datos = new Object[0][7];
            Object[] cabecera = {"Nombre ", "ID Documentum (r_object_id)", "Tipo Documental", "Fecha Creación", "Usuario", "Check Out"};
            modeloLotes = new DefaultTableModel(datos, cabecera) {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            modeloLotes.setColumnCount(0);
            modeloLotes.setRowCount(0);
            tablaDocumentos.setModel(modeloLotes);
        } catch (Exception ex) {
        }
        cargarAtributos("");
        cargarDocumentos("", "");

        Properties props = new Properties();
        try {
            props.load(getClass().getResourceAsStream("/log4j.properties"));
        } catch (IOException ex) {
            Utilidades.escribeLog("Error al cargar log4j.properties - " + ex.getMessage());
        }
        PropertyConfigurator.configure(props);

        Calendar cal = Calendar.getInstance();
        String anio = String.valueOf(cal.get(Calendar.YEAR));
        String mes = String.valueOf((cal.get(Calendar.MONTH) + 1)).length() == 1 ? "0" + String.valueOf((cal.get(Calendar.MONTH) + 1)) : String.valueOf((cal.get(Calendar.MONTH) + 1));
        String dia = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)).length() == 1 ? "0" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        util.crearDirectorio(dirbase);
        ficherolog = new File(dirbase + util.separador() + "DocumentumDFCs-" + dia + "-" + mes + "-" + anio + ".log");
        try {
            fis = new FileOutputStream(ficherolog, true);
            out = new PrintStream(fis);
            System.setOut(out);

        } catch (FileNotFoundException ex) {
            Utilidades.escribeLog(PantallaDocumentum.class
                    .getName().toString() + " - " + ex.getMessage());
        }

        Utilidades.escribeLog("Iniciamos el programa DocumentumDFCs");

        try {
            setIconImage(new ImageIcon(getLogo()).getImage());
        } catch (NullPointerException e) {
            Utilidades.escribeLog("\nError cargando el Logo de la aplicación - Error: " + e.getMessage() + "\n");
        }

        util.crearDirectorio(dirbase + util.separador() + "documentum" + util.separador() + "shared");
        dirdfc = dirbase + util.separador() + "documentum" + util.separador() + "shared" + util.separador();
        util.crearDirectorio(dirdfc + "config");
        util.crearDirectorio(dirdfc + "lib");
        util.crearDirectorio(dirdfc + "manual");
        util.crearDirectorio(dirdfc + "export");

        File fi = new File(dirdfc + "dfc.properties");
        if (!fi.exists()) {
            util.sacarArchivoJar("/es/documentum/propiedades/dfc.properties", dirdfc + "dfc.properties");
        }
        Properties prop = util.leerPropeties(dirdfc + "dfc.properties");
        prop.setProperty("dfc.data.dir", dirdfc);
        util.escribirProperties(dirdfc + "dfc.properties", prop);

        util.sacarArchivoJar("/es/documentum/manual/DocumentumDFCs.pdf", dirdfc + "manual" + util.separador() + "DocumentumDFCs.pdf");
        util.sacarArchivoJar("/es/documentum/lib/cryptojce.jar", dirdfc + "lib" + util.separador() + "cryptojce.jar");
        util.sacarArchivoJar("/es/documentum/lib/cryptojcommon.jar", dirdfc + "lib" + util.separador() + "cryptojcommon.jar");
        util.borrarFichero(dirdfc + "export", "*");

        try {
            ClassPathUpdater.add(dirdfc);
            ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "cryptojce.jar");
            ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "cryptojcommon.jar");
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al actualizar el Classpath  - Error: " + ex.getMessage());
        }

        opcionRBMetalActionPerformed(null);
        versiondfcs = utilDocum.DameVersionDFC();
        botonArribaDir.setEnabled(false);
        opcionPasswordLDAP.setVisible(false);
    }

    private void ConexionDocumentum() {
        PantallaConexion pantallaconectar = new PantallaConexion(this, true);
        EtiquetaDocbroker.setText(PantallaConexion.getDocbroker());
        EtiquetaRepositorio.setText(PantallaConexion.getRepositorio());
        docbroker = PantallaConexion.getDocbroker();
        repositorio = PantallaConexion.getRepositorio();
        puerto = PantallaConexion.getPuerto();
        versiondocumentum = PantallaConexion.getVersiondocumentum();

        usuario = PantallaConexion.getUsuario();
        clave = PantallaConexion.getClave();
        panelEstado.revalidate();

        if (PantallaConexion.getValor().equals("SALIR")) {
            botonBuscar.setEnabled(false);
            opcionBuscar.setEnabled(false);
            botonConectar.setBackground(colornoconex);
            EtiquetaRepositorio.setBackground(null);
            EtiquetaDocbroker.setText("");
            EtiquetaRepositorio.setText("");
        } else {
            botonBuscar.setEnabled(true);
            opcionBuscar.setEnabled(true);
            botonConectar.setBackground(colorconex);
            EtiquetaRepositorio.setOpaque(true);
            EtiquetaRepositorio.setBackground(colorconex);
            utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
            esadmin = utilDocum.EsUsuarioAdmin(usuario);
            idrepositorio = utilDocum.DameIdRepositorio();
            if (esadmin) {
                opcionActualizarAtributo.setEnabled(true);
                opcionBorrarDocumento.setEnabled(true);
                opcionBorradoLogico.setEnabled(true);
                EtiquetaDocbroker.setOpaque(true);
                EtiquetaDocbroker.setBackground(coloradmin);
            } else {
                opcionActualizarAtributo.setEnabled(false);
                opcionBorrarDocumento.setEnabled(false);
                opcionBorradoLogico.setEnabled(false);
                EtiquetaDocbroker.setOpaque(false);
                EtiquetaDocbroker.setBackground(null);
            }
            if (utilDocum.hayADTS()) {
                opcionConvertir.setEnabled(true);
            } else {
                opcionConvertir.setEnabled(false);
            }

            if (!utilDocum.dameFTIndex(pantallaconectar.getIdsesion()).isEmpty()) {
                opcionIndexador.setEnabled(true);
            } else {
                opcionIndexador.setEnabled(false);
            }

        }
        panelEstado.repaint();

    }

    private void BuscarEnDocumentum() {
        if (!textoIdDocumentum.getText().isEmpty()) {
            if (textoIdDocumentum.getText().length() == 16) {
                EtiquetaEstado.setText("Buscando atributos en Documentum para el ID de Documentum " + textoIdDocumentum.getText());
                panelEstado.revalidate();
                cargarAtributos(textoIdDocumentum.getText());
                utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
                cargarDocumentos(textoIdDocumentum.getText(), "id");
                textoRutaDocumentum.setText(utilDocum.DameAtributo(textoIdDocumentum.getText(), "r_folder_path").toString().replace("[", "").replace("]", ""));
                textoCarpeta.setText("");
            } else {
                EtiquetaEstado.setText("El ID de Documentum (r_object_id) tiene que tener 16 caracteres.");
            }
        } else if (!textoCarpeta.getText().isEmpty()) {
            cargarAtributos("");
            textoRutaDocumentum.setText("");
            EtiquetaEstado.setText("Buscando contenido de la carpeta de Documentum " + textoCarpeta.getText());
            panelEstado.revalidate();
            utilDocum.setVentanapadre(this);
            cargarDocumentos(textoCarpeta.getText(), "carpeta");
        } else if (!textoRutaDocumentum.getText().isEmpty()) {
            cargarAtributos("");
            //  textoRutaDocumentum.setText("");
            EtiquetaEstado.setText("Buscando contenido de la ruta de Documentum " + textoCarpeta.getText());
            panelEstado.revalidate();
            cargarDocumentos(textoRutaDocumentum.getText(), "ruta");
            String ruta = textoRutaDocumentum.getText();
            int posicion = ruta.lastIndexOf("/");
            if (posicion >= 0) {
                if (ruta.equals("/")) {
                    botonArribaDir.setEnabled(false);
                } else {
                    botonArribaDir.setEnabled(true);
                }
            } else {
                botonArribaDir.setEnabled(false);
            }
        }
        EtiquetaEstado.validate();

    }

    private void ExportarDeDocumentum() {
        if (tablaDocumentos.getModel().getRowCount() > 0) {
            String nombre = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
            if (!nombre.startsWith("09")) {
                EtiquetaEstado.setText("Sólo aplicable a Documentos");
                return;
            }

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Seleccionar directorio");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String directorio = chooser.getSelectedFile().toString();
                utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
                String resultado = utilDocum.GuardarFichero(nombre, directorio);
                EtiquetaEstado.setText(resultado);
            } else {
                Utilidades.escribeLog("No se ha seleccionado directorio de salida ");
            }
        }
    }

    private void AbrirFicheroDeDocumentum() {
        if (tablaDocumentos.getModel().getRowCount() > 0) {
            String nombre = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
            if (!nombre.startsWith("09")) {
                EtiquetaEstado.setText("Sólo aplicable a Documentos");
                return;
            }
            String directorio = dirdfc + "export";
            utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
            String resultado = utilDocum.GuardarFichero(nombre, directorio);
            EtiquetaEstado.setText(resultado);
            try {
                if (resultado.startsWith("OK. Exportado ")) {
                    resultado = resultado.substring("OK. Exportado ".length(), resultado.length());
                    File path = new File(resultado);
                    Desktop.getDesktop().open(path);
                }
            } catch (IOException ex) {
                Utilidades.escribeLog("Error al abrir el archivo (" + resultado + ") - Error " + ex.getMessage());
            }
        }
        System.gc();
    }

    private void ActualizarAtributo() {
        utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
        // ActualizarAtributo(int tipo, String r_object_id, String nombre, String valor)
        String nombre = tablaAtributos.getModel().getValueAt(tablaAtributos.convertRowIndexToModel(tablaAtributos.getSelectedRow()), 0).toString();
        /*       
        if (!nombre.equals("a_content_type") && !nombre.equals("subject") && !nombre.equals("title") && !nombre.equals("acl_name")
                && !nombre.equals("object_name") && !nombre.startsWith("map_atr")) {
            EtiquetaEstado.setText("Atributo " + nombre + " no modificable");
            return;
        }
         */
        String valor = "";
        if (tablaAtributos.getModel().getValueAt(tablaAtributos.convertRowIndexToModel(tablaAtributos.getSelectedRow()), 1) == null) {
            valor = null;
        } else {
            valor = tablaAtributos.getModel().getValueAt(tablaAtributos.convertRowIndexToModel(tablaAtributos.getSelectedRow()), 1).toString();
        }

        if (tablaDocumentos.getSelectedRow() < 0) {
            EtiquetaEstado.setForeground(Color.BLUE);
            EtiquetaEstado.setText("Debe tener seleccionado el Documento a modificar");
            return;
        }

        String r_object_id = tablaDocumentos.getModel().getValueAt(tablaAtributos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
        int tipo = BuscarTipo(nombre);
        int pos = -1;
        if (nombre.contains("[") && nombre.contains("]")) {
            pos = Integer.parseInt(nombre.substring(nombre.indexOf("[") + 1, nombre.indexOf("]")));
            nombre = nombre.substring(0, nombre.indexOf("["));
        }
        String resultado = utilDocum.ActualizarAtributo(tipo, r_object_id, nombre, valor, pos);
        EtiquetaEstado.setText(resultado);
        if (!resultado.isEmpty()) {
        }
    }

    private void BorrarDocumento() {
        utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
        String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();

        if (!r_object_id.startsWith("09")) {
            EtiquetaEstado.setText("Sólo aplicable a Documentos");
            return;
        }
        utilDocum.BorrarDocumento(r_object_id);
        if (utilDocum.dameError().isEmpty()) {
            ((DefaultTableModel) tablaDocumentos.getModel()).removeRow(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()));
            tablaDocumentos.revalidate();
            if (tablaDocumentos.getSelectedRow() >= 0) {
                cargarAtributos(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString());
            } else {
                cargarAtributos("");
            }
        }
    }

    private void BorradoLogicoDocumento() {
        utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
        String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
        if (!r_object_id.startsWith("09")) {
            EtiquetaEstado.setText("Sólo aplicable a Documentos");
            return;
        }
        String nombre = "map_atr_borrado_logico";
        int tipo = BuscarTipo(nombre);
        String resultado = utilDocum.ActualizarAtributo(tipo, r_object_id, nombre, "1", -1);
        if (utilDocum.dameError().isEmpty()) {
            if (tablaDocumentos.getSelectedRow() >= 0) {
                cargarAtributos(r_object_id);
            } else {
                cargarAtributos("");
            }
        }
    }

    private int BuscarTipo(String atributo) {
        int valor = 0;

        for (int n = 0; n < atributos.size(); n++) {
            if (atributos.get(n).getNombre().equals(atributo)) {
                valor = atributos.get(n).getTipo();
            }
        }

        return valor;
    }

    private void ExportarDocumentosExcel() {
        if (tablaDocumentos.getModel().getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Seleccionar directorio y nombre de fichero");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String fichero = chooser.getSelectedFile().toString();
                if (!fichero.toLowerCase().endsWith(".xls")) {
                    fichero = fichero + ".xls";
                }
                util.exportaExcel(tablaDocumentos, fichero);
            } else {
                Utilidades.escribeLog("No se ha seleccionado el fichero de salida ");
            }
        }
        System.gc();
    }

    private void ExportarAtributosExcel() {
        if (tablaAtributos.getModel().getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Seleccionar directorio y nombre de fichero");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String fichero = chooser.getSelectedFile().toString();
                if (!fichero.toLowerCase().endsWith(".xls")) {
                    fichero = fichero + ".xls";
                }
                util.exportaExcel(tablaAtributos, fichero);
            } else {
                Utilidades.escribeLog("No se ha seleccionado el fichero de salida ");
            }
            System.gc();
        }

    }

    public void exportarCarpeta(String rutaRepositorio, String rutaSistemaFicheros) {
        if (rutaRepositorio.isEmpty() || rutaSistemaFicheros.isEmpty()) {
            return;
        }
        String dirdfc = util.usuarioHome() + util.separador() + "documentumdcfs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();

        try {
            ClassPathUpdater.add(dirdfc);
            ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "jsafeFIPS.jar");
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            Utilidades.escribeLog("Error al exportarCarpeta - Error: " + ex.getMessage());
        }
        UtilidadesDocumentum ed = new UtilidadesDocumentum(dirdfc + "dfc.properties");
        IDfSession sesion = ed.conectarDocumentum();

        IDfFolder carpeta = null;
        try {
            carpeta = sesion.getFolderByPath(rutaRepositorio);
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al recuperar carpeta (exportarCarpeta) - Error: " + ex.getMessage());
        }
        try {
            if (carpeta != null) {
                deepExportFolder(carpeta.getObjectId(), rutaSistemaFicheros, rutaRepositorio);
            }
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al exportar carpeta (exportarCarpeta) - Error: " + ex.getMessage());
        }
    }

    public void deepExportFolder(IDfId folderId, String localFilesystemPath, String carpetaDocumentum) {

        final String ruta = localFilesystemPath;
        final IDfId idCarpeta = folderId;
        final String carpeta = carpetaDocumentum;

        new Thread() {
            public void run() {
                Utilidades.escribeLog("Iniciando exportación de datos de la carpeta " + carpeta);
                barradocum = new PantallaBarra(PantallaDocumentum.this, false);
                barradocum.setTitle("Exportando datos de la carpeta " + carpeta);
                barradocum.barra.setIndeterminate(true);
                barradocum.botonParar.setVisible(true);
                barradocum.setLabelMensa("");
                barradocum.barra.setOpaque(true);
                barradocum.barra.setStringPainted(false);
                barradocum.validate();
                barradocum.setVisible(true);
                IDfSession session = null;
                final StringBuilder bufAbsPath = new StringBuilder(32);
                try {
                    // Create the destination folder on the filesystem. This routine will create it 
                    // even if it is nested a few levels down.
                    utilDocum.createFolderPath(ruta);
                    String rutaSistemaFicheros = ruta;
                    Utilidades util = new Utilidades();
                    String dirdfc = util.usuarioHome() + util.separador() + "documentumdcfs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();

                    try {
                        ClassPathUpdater.add(dirdfc);
                        ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "jsafeFIPS.jar");
                    } catch (Exception ex) {
                        Utilidades.escribeLog("Error al actualizar el Classpath  - Error: " + ex.getMessage());
                    }
                    UtilidadesDocumentum ed = new UtilidadesDocumentum(dirdfc + "dfc.properties");

                    session = ed.conectarDocumentum();
                    // This is a deep export, so get all of the folders in the specified location
                    // and then export the contents of each folder 
                    String qualification
                            = "select r_object_id from dm_folder where FOLDER(ID('"
                            + idCarpeta
                            + "'), DESCEND)";
                    IDfCollection colDQL = utilDocum.execQuery(session, qualification);

                    IDfFolder myFolder = (IDfFolder) session.getObject(idCarpeta);

                    bufAbsPath
                            .append(rutaSistemaFicheros)
                            .append(System.getProperty("file.separator"))
                            .append(myFolder.getObjectName());
                    rutaSistemaFicheros = bufAbsPath.toString();
                    utilDocum.doExport(session, myFolder, bufAbsPath.toString());

                    String docbaseRootFolderPath = myFolder.getFolderPath(0);

                    while (colDQL.next() && barradocum.PARAR == false) {
                        // Get the r_object_id of each folder
                        barradocum.validate();
                        IDfAttr attr = colDQL.getAttr(0);
                        String objectId = colDQL.getString(attr.getName());

                        // Get the docbase folder object
                        myFolder = (IDfFolder) session.getObject(new DfId(objectId));

                        // Build the absolute filesystem folder path be appending the docbase path to the 
                        // file system root.
                        StringBuilder bufAbsFolderPath = new StringBuilder(32);
                        bufAbsFolderPath.append(ruta);

                        String path0 = null;
                        for (int ctrPaths = 0;
                                ctrPaths < myFolder.getFolderPathCount();
                                ctrPaths++) {
                            String tmpPath = myFolder.getFolderPath(ctrPaths);
                            if (tmpPath.startsWith(docbaseRootFolderPath)) {
                                path0 = tmpPath;
                                break;
                            }
                        }
                        if (path0 != null) {
                            String subFolderPath
                                    = myFolder.getFolderPath(0).substring(
                                    docbaseRootFolderPath.length(),
                                    path0.length());
                            bufAbsFolderPath.append(subFolderPath);
                            String absFolderPath = bufAbsFolderPath.toString();
                            barradocum.setLabelMensa(absFolderPath);
                            // Export this docbase folder using the Export Operation
                            utilDocum.doVirtualDocExport(session, myFolder, absFolderPath);
                            utilDocum.doExport(session, myFolder, absFolderPath);
                        }
                    }

                } catch (Exception ex) {

                    //    
                } finally {
                    barradocum.setVisible(false);
                    barradocum.dispose();
                    Utilidades.escribeLog("Fin de la exportación de datos de la carpeta " + carpeta);
                    if (session != null) {
                        try {
                            session.disconnect();
                        } catch (DfException ex) {
                            Utilidades.escribeLog("Error al cerrar sesión en Exportar Carpeta - Error: " + ex.getMessage());
                        }
                    }
                }
            }
        }.start();
//        barradocum.setVisible(false);
//        barradocum.dispose();
    }

    private void LimpiarPantalla() {
        DefaultTableModel modeloLotes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        try {
            tablaDocumentos.setModel(modeloLotes);
            tablaAtributos.setModel(modeloLotes);
        } catch (Exception ex) {
        }
        textoIdDocumentum.setText("");
        textoCarpeta.setText("");
        textoRutaDocumentum.setText("");
        EtiquetaEstado.setText("");
    }

}
