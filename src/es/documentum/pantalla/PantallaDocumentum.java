package es.documentum.pantalla;

import es.documentum.pruebas.PantallaArbolDocumentum;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfQuery;
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
import es.documentum.utilidades.ConexionUnica;
import es.documentum.utilidades.Correo;
import es.documentum.utilidades.CorreoPropiedades;
import es.documentum.utilidades.MiProperties;
import es.documentum.utilidades.Utilidades;
import static es.documentum.utilidades.UtilidadesDocumentum.getDfObjectValue;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
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
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class PantallaDocumentum extends javax.swing.JFrame {

    public String titulo = " Acceso a Documentum";
    public Boolean conectado = false;
    private Boolean botonderecho = false;
    private String componente = "";
    UtilidadesDocumentum utilDocum = new UtilidadesDocumentum();
    Utilidades util = new Utilidades();
    String dirbase = util.usuarioHome() + util.separador() + "documentumdfcs";
    File ficherolog = null;
    FileOutputStream fis;
    PrintStream out;
    Boolean primeraDocumentos = true;
    String dirdfc = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();
    static Logger logger = Logger.getLogger(PantallaDocumentum.class);
    public PantallaBarra barradocum = new PantallaBarra(PantallaDocumentum.this, false);
    public static PantallaDocumentum ventanapadre = null;
    DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("raiz");
    DefaultTreeModel modelo = new DefaultTreeModel(raiz);
    public IDfSession gsesion;
    ArrayList<CabinetConCarpeta> cabinetes = new ArrayList<>();
    Icon iconoCheckSeleccionado = new ImageIcon(PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/seleccionado.png"));
    Icon iconoCheckNoSeleccionado = new ImageIcon(PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/no-seleccionado.png"));

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
    ArrayList<AtributosDocumentum> atributos = new ArrayList<>();
    public javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String memoria = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB / " + Runtime.getRuntime().totalMemory() / 1024 + " KB ";
            if (!idrepositorio.isEmpty()) {
                setTitle(titulo + "  -  " + repositorio + " - " + idrepositorio + " (" + Integer.toHexString(Integer.parseInt(idrepositorio)) + ")" + "  -  Memoria: " + memoria);
            } else {
                setTitle(titulo + "  -  Uso de Memoria: " + memoria);
            }
            panelEntrada.revalidate();
        }
    });

    public PantallaDocumentum() {
        ventanapadre = this;
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
        limpiarPantalla();
    }

    protected static Image getLogo() {
        //   java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/storage.gif");
        java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/DCTM_32.png");

        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupDocumentos = new javax.swing.JPopupMenu();
        opcionAbrirDocumento = new javax.swing.JMenuItem();
        opcionExportar = new javax.swing.JMenuItem();
        opcionBorrarObjetos = new javax.swing.JMenuItem();
        opcionBorradoLogico = new javax.swing.JMenuItem();
        opcionCheckin = new javax.swing.JMenuItem();
        opcionCancelCheckout = new javax.swing.JMenuItem();
        opcionCheckout = new javax.swing.JMenuItem();
        separador = new javax.swing.JPopupMenu.Separator();
        opcionCopiarNombre = new javax.swing.JMenuItem();
        opcionCopiarIDDocumentum = new javax.swing.JMenuItem();
        opcionCambiarTipoDocumental = new javax.swing.JMenuItem();
        opcionDumpAtributos = new javax.swing.JMenuItem();
        opcionRenditions = new javax.swing.JMenuItem();
        opcionRelationsPopup = new javax.swing.JMenuItem();
        opcionConvertir = new javax.swing.JMenuItem();
        opcionExportarDocumentosExcel = new javax.swing.JMenuItem();
        opcionPropiedades = new javax.swing.JMenuItem();
        popupEditar = new javax.swing.JPopupMenu();
        opcionCopiar = new javax.swing.JMenuItem();
        opcionPegar = new javax.swing.JMenuItem();
        popupAtributos = new javax.swing.JPopupMenu();
        opcionCopiarValor = new javax.swing.JMenuItem();
        opcionCopiarAtributo = new javax.swing.JMenuItem();
        opcionActualizarAtributo = new javax.swing.JMenuItem();
        opcionExportarAtributosExcel = new javax.swing.JMenuItem();
        popupArbol = new javax.swing.JPopupMenu();
        opcionColapsarNodo = new javax.swing.JMenuItem();
        opcionCrearCarpetaDesdeArbol = new javax.swing.JMenuItem();
        panelDocumentos = new javax.swing.JPanel();
        panelEstado = new javax.swing.JPanel();
        etiquetaEstado = new javax.swing.JLabel();
        botonRefrescarArbol = new javax.swing.JButton();
        panelDocu = new javax.swing.JPanel();
        etiquetaDocbroker = new javax.swing.JLabel();
        etiquetaRepositorio = new javax.swing.JLabel();
        divisorArbol = new javax.swing.JSplitPane();
        scrollArbol = new javax.swing.JScrollPane();
        arbolRepo = new javax.swing.JTree();
        scrollFicheros = new javax.swing.JScrollPane();
        divisor = new javax.swing.JSplitPane();
        scrollGrid = new javax.swing.JScrollPane(tablaDocumentos);
        tablaDocumentos = new javax.swing.JTable();
        scrollAtributos = new javax.swing.JScrollPane();
        tablaAtributos = new javax.swing.JTable();
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
        botonActivarCripto = new javax.swing.JButton();
        textoNumRegistros = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        menuDocumentum = new javax.swing.JMenuBar();
        menuOpciones = new javax.swing.JMenu();
        opcionBuscar = new javax.swing.JMenuItem();
        opcionConectar = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        checkArbolSiNo = new javax.swing.JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        opcionSalir = new javax.swing.JMenuItem();
        menuApariencia = new javax.swing.JMenu();
        opcionRBMetal = new javax.swing.JRadioButtonMenuItem();
        opcionRBNimbus = new javax.swing.JRadioButtonMenuItem();
        opcionRBWindows = new javax.swing.JRadioButtonMenuItem();
        opcionRBWindowsClassic = new javax.swing.JRadioButtonMenuItem();
        opcionRBPorDefecto = new javax.swing.JRadioButtonMenuItem();
        menuUtilidades = new javax.swing.JMenu();
        opcionDql = new javax.swing.JMenuItem();
        opcionAPI = new javax.swing.JMenuItem();
        opcionEjecutarSql = new javax.swing.JMenuItem();
        Separador1 = new javax.swing.JPopupMenu.Separator();
        opcionCrearCarpeta = new javax.swing.JMenuItem();
        opcionExportarCarpeta = new javax.swing.JMenuItem();
        opcionImportarADocumentum = new javax.swing.JMenuItem();
        opcionRelations = new javax.swing.JMenuItem();
        Separador3 = new javax.swing.JPopupMenu.Separator();
        opcionTipos = new javax.swing.JMenuItem();
        opcionGrupos = new javax.swing.JMenuItem();
        opcionStorage = new javax.swing.JMenuItem();
        opcionSesiones = new javax.swing.JMenuItem();
        opcionJobs = new javax.swing.JMenuItem();
        opcionIndexador = new javax.swing.JMenuItem();
        opcionEstadisticasRepos = new javax.swing.JMenuItem();
        opcionInfoRepo = new javax.swing.JMenuItem();
        Separador4 = new javax.swing.JPopupMenu.Separator();
        opcionLeerLog = new javax.swing.JMenuItem();
        opcionEjecutarComandoSSOO = new javax.swing.JMenuItem();
        opcionPasswordLDAP = new javax.swing.JMenuItem();
        opcionCripto = new javax.swing.JMenuItem();
        menuAcercade = new javax.swing.JMenu();
        opcionManual = new javax.swing.JMenuItem();
        opcionAcercade = new javax.swing.JMenuItem();

        opcionAbrirDocumento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/doc-abrir.png"))); // NOI18N
        opcionAbrirDocumento.setText("Abrir Documento");
        opcionAbrirDocumento.setToolTipText("Abre el Documento seleccionado.");
        opcionAbrirDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionAbrirDocumentoActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionAbrirDocumento);

        opcionExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/exportar-doc.png"))); // NOI18N
        opcionExportar.setText("Exportar a fichero");
        opcionExportar.setToolTipText("Exportar a fichero desde Documentum");
        opcionExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionExportarActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionExportar);

        opcionBorrarObjetos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/borrar_peq.png"))); // NOI18N
        opcionBorrarObjetos.setText("Borrar objetos y su posible contenido");
        opcionBorrarObjetos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionBorrarObjetosActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionBorrarObjetos);

        opcionBorradoLogico.setText("Borrado Lógico del Documento");
        opcionBorradoLogico.setEnabled(false);
        opcionBorradoLogico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionBorradoLogicoActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionBorradoLogico);

        opcionCheckin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/si.png"))); // NOI18N
        opcionCheckin.setText("Check In");
        opcionCheckin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCheckinActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionCheckin);

        opcionCancelCheckout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/no.png"))); // NOI18N
        opcionCancelCheckout.setText("Cancelar Check Out");
        opcionCancelCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCancelCheckoutActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionCancelCheckout);

        opcionCheckout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/bloqueado.gif"))); // NOI18N
        opcionCheckout.setText("Check Out");
        opcionCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCheckoutActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionCheckout);
        popupDocumentos.add(separador);

        opcionCopiarNombre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/copiar-nombre-rojo.png"))); // NOI18N
        opcionCopiarNombre.setText("Copiar Nombre");
        opcionCopiarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarNombreActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionCopiarNombre);

        opcionCopiarIDDocumentum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/copiar-id.png"))); // NOI18N
        opcionCopiarIDDocumentum.setText("Copiar ID de Documentum");
        opcionCopiarIDDocumentum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarIDDocumentumActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionCopiarIDDocumentum);

        opcionCambiarTipoDocumental.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/actualizar-tipo.png"))); // NOI18N
        opcionCambiarTipoDocumental.setText("Cambiar Tipo Documental");
        opcionCambiarTipoDocumental.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCambiarTipoDocumentalActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionCambiarTipoDocumental);

        opcionDumpAtributos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/dump-atributos.png"))); // NOI18N
        opcionDumpAtributos.setText("Volcar Atributos del Objeto");
        opcionDumpAtributos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionDumpAtributosActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionDumpAtributos);

        opcionRenditions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/renditions.png"))); // NOI18N
        opcionRenditions.setText("Ver Renditions");
        opcionRenditions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRenditionsActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionRenditions);

        opcionRelationsPopup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/relation24.png"))); // NOI18N
        opcionRelationsPopup.setText("Relations del Documento");
        opcionRelationsPopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRelationsPopupActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionRelationsPopup);

        opcionConvertir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/convertir-pdf.png"))); // NOI18N
        opcionConvertir.setText("Convertir a PDF (ADTS)");
        opcionConvertir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionConvertirActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionConvertir);

        opcionExportarDocumentosExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/excel-24.gif"))); // NOI18N
        opcionExportarDocumentosExcel.setText("Exportar lista de Documentos a Excel");
        opcionExportarDocumentosExcel.setActionCommand("ExportarDocumentosExcel");
        opcionExportarDocumentosExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionExportarDocumentosExcelActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionExportarDocumentosExcel);

        opcionPropiedades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/doc-propiedades.png"))); // NOI18N
        opcionPropiedades.setText("Propiedades");
        opcionPropiedades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionPropiedadesActionPerformed(evt);
            }
        });
        popupDocumentos.add(opcionPropiedades);

        opcionCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/copiar.png"))); // NOI18N
        opcionCopiar.setText("Copiar Ctrl+C");
        opcionCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarActionPerformed(evt);
            }
        });
        popupEditar.add(opcionCopiar);

        opcionPegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/pegar.png"))); // NOI18N
        opcionPegar.setText("Pegar  Ctrl+V");
        opcionPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionPegarActionPerformed(evt);
            }
        });
        popupEditar.add(opcionPegar);

        opcionCopiarValor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/copiar.png"))); // NOI18N
        opcionCopiarValor.setText("Copiar Valor");
        opcionCopiarValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarValorActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionCopiarValor);

        opcionCopiarAtributo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/copiar_atr.png"))); // NOI18N
        opcionCopiarAtributo.setText("Copiar nombre de Atributo");
        opcionCopiarAtributo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarAtributoActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionCopiarAtributo);

        opcionActualizarAtributo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/actualizar-atr.png"))); // NOI18N
        opcionActualizarAtributo.setText("Actualizar Atributo");
        opcionActualizarAtributo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionActualizarAtributoActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionActualizarAtributo);

        opcionExportarAtributosExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/excel-24.gif"))); // NOI18N
        opcionExportarAtributosExcel.setText("Exportar lista de Atributos a Excel");
        opcionExportarAtributosExcel.setActionCommand("ExportarAtributosExcel");
        opcionExportarAtributosExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionExportarAtributosExcelActionPerformed(evt);
            }
        });
        popupAtributos.add(opcionExportarAtributosExcel);

        opcionColapsarNodo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/colapsar-nodo.png"))); // NOI18N
        opcionColapsarNodo.setText("Colapsar nodo");
        opcionColapsarNodo.setToolTipText("");
        opcionColapsarNodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionColapsarNodoActionPerformed(evt);
            }
        });
        popupArbol.add(opcionColapsarNodo);

        opcionCrearCarpetaDesdeArbol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/carpeta_azul.png"))); // NOI18N
        opcionCrearCarpetaDesdeArbol.setText("Crear carpeta en Repositorio");
        opcionCrearCarpetaDesdeArbol.setActionCommand("Crear carpeta");
        opcionCrearCarpetaDesdeArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCrearCarpetaDesdeArbolActionPerformed(evt);
            }
        });
        popupArbol.add(opcionCrearCarpetaDesdeArbol);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Acceso a Documentum");
        setMinimumSize(new java.awt.Dimension(1088, 660));
        setName("PantallaDocumentum"); // NOI18N
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        panelDocumentos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelDocumentos.setAutoscrolls(true);
        panelDocumentos.setMaximumSize(new java.awt.Dimension(1020, 640));
        panelDocumentos.setMinimumSize(new java.awt.Dimension(1020, 470));
        panelDocumentos.setPreferredSize(new java.awt.Dimension(1020, 640));

        panelEstado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelEstado.setPreferredSize(new java.awt.Dimension(687, 30));

        etiquetaEstado.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        etiquetaEstado.setToolTipText("");
        etiquetaEstado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        etiquetaEstado.setMaximumSize(new java.awt.Dimension(getHeight() - etiquetaDocbroker.getHeight() - etiquetaRepositorio.getHeight(), etiquetaEstado.getWidth()));
        etiquetaEstado.setMinimumSize(new java.awt.Dimension(657, 26));
        etiquetaEstado.setPreferredSize(new java.awt.Dimension(getHeight() - etiquetaDocbroker.getHeight() - etiquetaRepositorio.getHeight(), etiquetaEstado.getWidth()));
        etiquetaEstado.setPreferredSize(new java.awt.Dimension(panelEstado.getHeight() - etiquetaDocbroker.getHeight() - etiquetaRepositorio.getHeight(), etiquetaEstado.getWidth()));
        etiquetaEstado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                etiquetaEstadoMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                etiquetaEstadoMousePressed(evt);
            }
        });

        botonRefrescarArbol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/actualizar-atr.png"))); // NOI18N
        botonRefrescarArbol.setText("Refrescar Arbol");
        botonRefrescarArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRefrescarArbolActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEstadoLayout = new javax.swing.GroupLayout(panelEstado);
        panelEstado.setLayout(panelEstadoLayout);
        panelEstadoLayout.setHorizontalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEstadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonRefrescarArbol, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(etiquetaEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEstadoLayout.setVerticalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEstadoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(etiquetaEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(botonRefrescarArbol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        panelDocu.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelDocu.setPreferredSize(new java.awt.Dimension(330, 30));

        etiquetaDocbroker.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaDocbroker.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        etiquetaDocbroker.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        etiquetaDocbroker.setMaximumSize(new java.awt.Dimension(209, 26));
        etiquetaDocbroker.setMinimumSize(new java.awt.Dimension(209, 26));
        etiquetaDocbroker.setPreferredSize(new java.awt.Dimension(209, 26));

        etiquetaRepositorio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaRepositorio.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        etiquetaRepositorio.setMaximumSize(new java.awt.Dimension(116, 26));
        etiquetaRepositorio.setPreferredSize(new java.awt.Dimension(116, 26));

        javax.swing.GroupLayout panelDocuLayout = new javax.swing.GroupLayout(panelDocu);
        panelDocu.setLayout(panelDocuLayout);
        panelDocuLayout.setHorizontalGroup(
            panelDocuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDocuLayout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(etiquetaRepositorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(39, 39, 39))
            .addGroup(panelDocuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelDocuLayout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(etiquetaDocbroker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(155, 155, 155)))
        );
        panelDocuLayout.setVerticalGroup(
            panelDocuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDocuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(etiquetaRepositorio, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelDocuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelDocuLayout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(etiquetaDocbroker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)))
        );

        divisorArbol.setDividerLocation(200);
        divisorArbol.setAutoscrolls(true);
        divisorArbol.setContinuousLayout(true);
        divisorArbol.setOneTouchExpandable(true);

        scrollArbol.setPreferredSize(new java.awt.Dimension(400, 362));

        arbolRepo.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("raiz")));
        arbolRepo.setAutoscrolls(true);
        arbolRepo.setRowHeight(18);
        arbolRepo.setShowsRootHandles(true);
        arbolRepo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                arbolRepoMousePressed(evt);
            }
        });
        arbolRepo.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                arbolRepoValueChanged(evt);
            }
        });
        scrollArbol.setViewportView(arbolRepo);

        divisorArbol.setLeftComponent(scrollArbol);

        scrollFicheros.setPreferredSize(new java.awt.Dimension(720, 609));

        divisor.setDividerSize(3);
        divisor.setForeground(new java.awt.Color(0, 0, 255));
        divisor.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        scrollGrid.setAutoscrolls(true);
        scrollGrid.setMinimumSize(new java.awt.Dimension(720, 100));
        scrollGrid.setPreferredSize(new java.awt.Dimension(720, 200));
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

        divisor.setLeftComponent(scrollGrid);

        scrollAtributos.setMinimumSize(new java.awt.Dimension(720, 200));
        scrollAtributos.setPreferredSize(new java.awt.Dimension(720, 390));

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
        tablaAtributos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaAtributos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaAtributosMousePressed(evt);
            }
        });
        scrollAtributos.setViewportView(tablaAtributos);

        divisor.setRightComponent(scrollAtributos);

        scrollFicheros.setViewportView(divisor);

        divisorArbol.setRightComponent(scrollFicheros);

        javax.swing.GroupLayout panelDocumentosLayout = new javax.swing.GroupLayout(panelDocumentos);
        panelDocumentos.setLayout(panelDocumentosLayout);
        panelDocumentosLayout.setHorizontalGroup(
            panelDocumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDocumentosLayout.createSequentialGroup()
                .addComponent(panelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 695, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(panelDocu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(divisorArbol, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelDocumentosLayout.setVerticalGroup(
            panelDocumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDocumentosLayout.createSequentialGroup()
                .addComponent(divisorArbol, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDocumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDocu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelEstado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        botonActivarCripto.setBorder(null);
        botonActivarCripto.setBorderPainted(false);
        botonActivarCripto.setContentAreaFilled(false);
        botonActivarCripto.setFocusPainted(false);
        botonActivarCripto.setFocusable(false);
        botonActivarCripto.setIconTextGap(1);
        botonActivarCripto.setRequestFocusEnabled(false);
        botonActivarCripto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActivarCriptoActionPerformed(evt);
            }
        });

        textoNumRegistros.setForeground(new java.awt.Color(0, 0, 102));
        textoNumRegistros.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        textoNumRegistros.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textoNumRegistros.setText("0");
        textoNumRegistros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoNumRegistrosActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Nº de registros");

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
                            .addGroup(panelEntradaLayout.createSequentialGroup()
                                .addComponent(textoIdDocumentum, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
                                .addComponent(botonActivarCripto, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textoCarpeta))
                        .addGap(35, 35, 35)
                        .addComponent(botonBuscar))
                    .addComponent(textoRutaDocumentum))
                .addGap(18, 18, 18)
                .addGroup(panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botonConectar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(textoNumRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelEntradaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {botonBuscar, botonSalir});

        panelEntradaLayout.setVerticalGroup(
            panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEntradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEntradaLayout.createSequentialGroup()
                        .addGroup(panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textoIdDocumentum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonActivarCripto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textoRutaDocumentum, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textoNumRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(botonArribaDir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelEntradaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel4, textoNumRegistros});

        textoRutaDocumentum.getAccessibleContext().setAccessibleName("textoRutaDocumentum");
        textoIdDocumentum.getAccessibleContext().setAccessibleName("textoIdDocumentum");
        textoCarpeta.getAccessibleContext().setAccessibleName("textoCarpeta");
        botonArribaDir.getAccessibleContext().setAccessibleName("botonSubir");

        menuOpciones.setMnemonic('O');
        menuOpciones.setText("Opciones");
        menuOpciones.setToolTipText("");

        opcionBuscar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/buscar_peq.png"))); // NOI18N
        opcionBuscar.setText("Buscar");
        opcionBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionBuscarActionPerformed(evt);
            }
        });
        menuOpciones.add(opcionBuscar);

        opcionConectar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionConectar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/conexion_peq.png"))); // NOI18N
        opcionConectar.setText("Conectar");
        opcionConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionConectarActionPerformed(evt);
            }
        });
        menuOpciones.add(opcionConectar);
        menuOpciones.add(jSeparator2);

        checkArbolSiNo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        checkArbolSiNo.setSelected(true);
        checkArbolSiNo.setText("Ocultar árbol de navegación");
        checkArbolSiNo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/no-arbol.png"))); // NOI18N
        checkArbolSiNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkArbolSiNoActionPerformed(evt);
            }
        });
        menuOpciones.add(checkArbolSiNo);
        menuOpciones.add(jSeparator1);

        opcionSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        opcionSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        opcionSalir.setMnemonic('S');
        opcionSalir.setText("Salir");
        opcionSalir.setToolTipText("Volver a la pantalla anterior");
        opcionSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionSalirActionPerformed(evt);
            }
        });
        menuOpciones.add(opcionSalir);

        menuDocumentum.add(menuOpciones);

        menuApariencia.setMnemonic('A');
        menuApariencia.setText("Apariencia");

        opcionRBMetal.setSelected(true);
        opcionRBMetal.setText("Metal");
        opcionRBMetal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBMetalActionPerformed(evt);
            }
        });
        menuApariencia.add(opcionRBMetal);

        opcionRBNimbus.setSelected(true);
        opcionRBNimbus.setText("Nimbus");
        opcionRBNimbus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBNimbusActionPerformed(evt);
            }
        });
        menuApariencia.add(opcionRBNimbus);

        opcionRBWindows.setSelected(true);
        opcionRBWindows.setText("Windows");
        opcionRBWindows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBWindowsActionPerformed(evt);
            }
        });
        menuApariencia.add(opcionRBWindows);

        opcionRBWindowsClassic.setSelected(true);
        opcionRBWindowsClassic.setText("Windows Classic");
        opcionRBWindowsClassic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBWindowsClassicActionPerformed(evt);
            }
        });
        menuApariencia.add(opcionRBWindowsClassic);

        opcionRBPorDefecto.setSelected(true);
        opcionRBPorDefecto.setText("Propia del Sistema Operativo");
        opcionRBPorDefecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRBPorDefectoActionPerformed(evt);
            }
        });
        menuApariencia.add(opcionRBPorDefecto);

        menuDocumentum.add(menuApariencia);

        menuUtilidades.setText("Utilidades");

        opcionDql.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionDql.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/dql-nuevo.png"))); // NOI18N
        opcionDql.setText("Editor de DQL");
        opcionDql.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionDqlActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionDql);

        opcionAPI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionAPI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/API.png"))); // NOI18N
        opcionAPI.setText("Ejecutar comando API");
        opcionAPI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionAPIActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionAPI);

        opcionEjecutarSql.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/sql-nativo.png"))); // NOI18N
        opcionEjecutarSql.setText("Ejecutar SQL nativa");
        opcionEjecutarSql.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionEjecutarSqlActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionEjecutarSql);
        menuUtilidades.add(Separador1);

        opcionCrearCarpeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/carpeta_azul.png"))); // NOI18N
        opcionCrearCarpeta.setText("Crear carpeta en Repositorio");
        opcionCrearCarpeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCrearCarpetaActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionCrearCarpeta);

        opcionExportarCarpeta.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionExportarCarpeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/carpeta-exportar-peq.png"))); // NOI18N
        opcionExportarCarpeta.setText("Exportar Carpeta de Documentum");
        opcionExportarCarpeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionExportarCarpetaActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionExportarCarpeta);

        opcionImportarADocumentum.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionImportarADocumentum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/document-import.png"))); // NOI18N
        opcionImportarADocumentum.setText("Importar a Documentum");
        opcionImportarADocumentum.setToolTipText("");
        opcionImportarADocumentum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionImportarADocumentumActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionImportarADocumentum);

        opcionRelations.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/relation-peq.png"))); // NOI18N
        opcionRelations.setText("Relations de Documentos");
        opcionRelations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionRelationsActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionRelations);
        menuUtilidades.add(Separador3);

        opcionTipos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/tipos.png"))); // NOI18N
        opcionTipos.setText("Tipos Documentales");
        opcionTipos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionTiposActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionTipos);

        opcionGrupos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/grupo.png"))); // NOI18N
        opcionGrupos.setText("Grupos y roles");
        opcionGrupos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionGruposActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionGrupos);

        opcionStorage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/storage.png"))); // NOI18N
        opcionStorage.setText("Almacenamiento del repositorio");
        opcionStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionStorageActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionStorage);

        opcionSesiones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/repoSesiones_peq.gif"))); // NOI18N
        opcionSesiones.setText("Sesiones");
        opcionSesiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionSesionesActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionSesiones);

        opcionJobs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/jobs.png"))); // NOI18N
        opcionJobs.setText("Jobs");
        opcionJobs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionJobsActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionJobs);

        opcionIndexador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/idx.png"))); // NOI18N
        opcionIndexador.setText("Indexador");
        opcionIndexador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionIndexadorActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionIndexador);

        opcionEstadisticasRepos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/estadisticas.png"))); // NOI18N
        opcionEstadisticasRepos.setText("Estadísticas de Repositorios");
        opcionEstadisticasRepos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionEstadisticasReposActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionEstadisticasRepos);

        opcionInfoRepo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/information.png"))); // NOI18N
        opcionInfoRepo.setText("Información del Repositorio");
        opcionInfoRepo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionInfoRepoActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionInfoRepo);
        menuUtilidades.add(Separador4);

        opcionLeerLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/doc.png"))); // NOI18N
        opcionLeerLog.setText("Leer fichero Log");
        opcionLeerLog.setActionCommand("LeerFicheroLog");
        opcionLeerLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionLeerLogActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionLeerLog);

        opcionEjecutarComandoSSOO.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionEjecutarComandoSSOO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/terminal_1.png"))); // NOI18N
        opcionEjecutarComandoSSOO.setText("Ejecutar comando de Sistema");
        opcionEjecutarComandoSSOO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionEjecutarComandoSSOOActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionEjecutarComandoSSOO);

        opcionPasswordLDAP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/cambiar_password.png"))); // NOI18N
        opcionPasswordLDAP.setText("Cambiar Password LDAP");
        opcionPasswordLDAP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionPasswordLDAPActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionPasswordLDAP);

        opcionCripto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        opcionCripto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/encryption.png"))); // NOI18N
        opcionCripto.setText("Encriptar / Desencriptar");
        opcionCripto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCriptoActionPerformed(evt);
            }
        });
        menuUtilidades.add(opcionCripto);

        menuDocumentum.add(menuUtilidades);

        menuAcercade.setText("Acerca de");

        opcionManual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/manual.png"))); // NOI18N
        opcionManual.setText("Manual de la Aplicación");
        opcionManual.setToolTipText("Manual de la Aplicación");
        opcionManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionManualActionPerformed(evt);
            }
        });
        menuAcercade.add(opcionManual);

        opcionAcercade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/documentum_logo_mini.gif"))); // NOI18N
        opcionAcercade.setText("Acerca de ...");
        opcionAcercade.setToolTipText("Acerca de DocumentumDFCs");
        opcionAcercade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionAcercadeActionPerformed(evt);
            }
        });
        menuAcercade.add(opcionAcercade);

        menuDocumentum.add(menuAcercade);

        setJMenuBar(menuDocumentum);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelDocumentos, javax.swing.GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDocumentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opcionExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionExportarActionPerformed
        exportarDeDocumentum();
    }//GEN-LAST:event_opcionExportarActionPerformed

    private void tablaDocumentosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDocumentosMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            componente = "tablaDocumentos";
            botonderecho = true;
            popupmenu(evt);
        }

//        if (tablaDocumentos.getSelectedRow() < 0) {
//            return;
//        }
//        if (evt.getClickCount() == 2 && evt.getButton() == java.awt.event.MouseEvent.BUTTON1 && tablaDocumentos.getModel().getRowCount() > 0) {
//            //     Utilidades.escribeLog("Doble click : " + tablaDocumentos.getModel().getValueAt(tablaDocumentos.getSelectedRow(), 0).toString());
//            cargarAtributos(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString());
//            utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
//            textoRutaDocumentum.setText(utilDocum.dameAtributo(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString(), "r_folder_path").toString().replace("[", "").replace("]", ""));
//            if (tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 2).toString().equals("dm_folder")
//                    || tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString().startsWith("0b")) {
//                textoRutaDocumentum.setText(textoRutaDocumentum.getText() + "/" + tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString());
//                buscarEnDocumentum();
//            } else if (tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 2).toString().equals("dm_cabinet")) {
//                textoRutaDocumentum.setText("/" + tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString());
//                buscarEnDocumentum();
//            } else if (tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString().startsWith("09")
//                    || !utilDocum.dameAtributo(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString(), "a_storage_type").isEmpty()) {
//                abrirFicheroDeDocumentum();
//            } else {
//                Utilidades.copiarTextoPortapapeles(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString());
//            }
//        } else if (tablaDocumentos.getModel().getRowCount() > 0) {
//            cargarAtributos(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString());
//            utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
//            if (!utilDocum.dameAtributo(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString(), "r_folder_path").toString().replace("[", "").replace("]", "").isEmpty()) {
//                textoRutaDocumentum.setText(utilDocum.dameAtributo(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString(), "r_folder_path").toString().replace("[", "").replace("]", ""));
//            }
//        } else if (evt.getClickCount() == 1 && evt.getButton() == java.awt.event.MouseEvent.BUTTON3 && tablaDocumentos.getModel().getRowCount() > 0) {
//            botonderecho = true;
//            componente = "tablaDocumentos";
//            popupmenu(evt);
//        }

    }//GEN-LAST:event_tablaDocumentosMousePressed

    private void tablaDocumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDocumentosMouseClicked
        if (tablaDocumentos.getSelectedRow() < 0) {
            return;
        }

        if (evt.getClickCount() == 2 && evt.getButton() == java.awt.event.MouseEvent.BUTTON1 && tablaDocumentos.getModel().getRowCount() > 0) {
            //     Utilidades.escribeLog("Doble click : " + tablaDocumentos.getModel().getValueAt(tablaDocumentos.getSelectedRow(), 0).toString());
            cargarAtributos(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString());
            utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
            textoRutaDocumentum.setText(utilDocum.dameAtributo(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString(), "r_folder_path").toString().replace("[", "").replace("]", ""));
            if (tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 2).toString().equals("dm_folder")
                    || tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString().startsWith("0b")) {
                textoRutaDocumentum.setText(textoRutaDocumentum.getText() + "/" + tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString());
                buscarEnDocumentum();
            } else if (tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 2).toString().equals("dm_cabinet")) {
                textoRutaDocumentum.setText("/" + tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString());
                buscarEnDocumentum();
            } else if (tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString().startsWith("09")
                    || !utilDocum.dameAtributo(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString(), "a_storage_type").isEmpty()) {
                abrirFicheroDeDocumentum();
            } else {
                Utilidades.copiarTextoPortapapeles(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString());
            }
        } else if (tablaDocumentos.getModel().getRowCount() > 0) {
            cargarAtributos(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString());
            utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
            if (!utilDocum.dameAtributo(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString(), "r_folder_path").toString().replace("[", "").replace("]", "").isEmpty()) {
                textoRutaDocumentum.setText(utilDocum.dameAtributo(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString(), "r_folder_path").toString().replace("[", "").replace("]", ""));
            }
        } else if (evt.getClickCount() == 1 && evt.getButton() == java.awt.event.MouseEvent.BUTTON3 && tablaDocumentos.getModel().getRowCount() > 0) {
            botonderecho = true;
            componente = "tablaDocumentos";
            popupmenu(evt);
        }
    }//GEN-LAST:event_tablaDocumentosMouseClicked

    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarActionPerformed
        buscarEnDocumentum();
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

        if (componente.equals("etiquetaEstado")) {
            Utilidades.copiarTextoPortapapeles(etiquetaEstado.getText());
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
        conexionDocumentum();
        limpiarPantalla();
    }//GEN-LAST:event_botonConectarActionPerformed

    private void opcionConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionConectarActionPerformed
        conexionDocumentum();
        limpiarPantalla();
    }//GEN-LAST:event_opcionConectarActionPerformed

    private void opcionBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionBuscarActionPerformed
        buscarEnDocumentum();
        System.gc();
    }//GEN-LAST:event_opcionBuscarActionPerformed

    private void etiquetaEstadoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaEstadoMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "etiquetaEstado";
            popupmenu(evt);
        }
    }//GEN-LAST:event_etiquetaEstadoMousePressed

    private void etiquetaEstadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaEstadoMouseEntered
        if (!etiquetaEstado.getText().isEmpty()) {
            etiquetaEstado.setToolTipText("<html>" + etiquetaEstado.getText());
        }
    }//GEN-LAST:event_etiquetaEstadoMouseEntered

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
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
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
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
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
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
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
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
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
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            Utilidades.escribeLog("Error al cambiar Aspecto por Defecto - " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionRBPorDefectoActionPerformed

    private void opcionAbrirDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionAbrirDocumentoActionPerformed
        abrirFicheroDeDocumentum();
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

    private void opcionAcercadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionAcercadeActionPerformed
        mostrarAcercade();
    }//GEN-LAST:event_opcionAcercadeActionPerformed

    private void opcionDqlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionDqlActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaDqlconTabs pantallaDql = new PantallaDqlconTabs(this, true);
            pantallaDql.setTitle("Consultas a Documentum por DQL  -  " + etiquetaDocbroker.getText() + " / " + etiquetaRepositorio.getText());
            pantallaDql.setVisible(true);
//            PantallaDql pantallaDql = new PantallaDql(this, true);
//            pantallaDql.setTitle("Consultas a Documentum por DQL  -  " + EtiquetaDocbroker.getText() + " / " + EtiquetaRepositorio.getText());
//            pantallaDql.setVisible(true);            

        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionDqlActionPerformed

    private void opcionActualizarAtributoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionActualizarAtributoActionPerformed
        actualizarAtributo();
    }//GEN-LAST:event_opcionActualizarAtributoActionPerformed

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
            borradoLogicoDocumento();
        }
    }//GEN-LAST:event_opcionBorradoLogicoActionPerformed

    private void opcionImportarADocumentumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionImportarADocumentumActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaImportar pantallaimportar = new PantallaImportar(this, true);
            pantallaimportar.setLocationRelativeTo(this);
            if (!textoRutaDocumentum.getText().isEmpty()) {
                pantallaimportar.setRutadcm(textoRutaDocumentum.getText());
            }
            pantallaimportar.setVisible(true);
        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionImportarADocumentumActionPerformed

    private void opcionExportarDocumentosExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionExportarDocumentosExcelActionPerformed
        exportarDocumentosExcel();
    }//GEN-LAST:event_opcionExportarDocumentosExcelActionPerformed

    private void opcionExportarAtributosExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionExportarAtributosExcelActionPerformed
        exportarAtributosExcel();
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
        log.cargarFichero(dirbase + util.separador() + "DocumentumDFCs-" + dia + "-" + mes + "-" + anio + ".log");
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
                buscarEnDocumentum();
            }
        }
    }//GEN-LAST:event_botonArribaDirActionPerformed

    private void opcionDumpAtributosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionDumpAtributosActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaAtributos dump = new PantallaAtributos(this, true);
            dump.setTitle("Volcado de Atributos de Objetos de Documentum   -   " + etiquetaRepositorio.getText() + " / " + etiquetaDocbroker.getText());
            String idDocumentum = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
            dump.setIdDocumentum(idDocumentum);
            dump.cargarAtributos(idDocumentum);
            dump.setVisible(true);
            System.gc();
        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
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
            pedirDatos.setAccion("ExportarCarpeta");
            pedirDatos.setIcono("carpeta.png");
            pedirDatos.asignarIconos();
            pedirDatos.setVisible(true);
            String carpeta = pedirDatos.getCarpeta();
            String ruta = pedirDatos.getRuta();
            if (carpeta != null && !carpeta.isEmpty() && ruta != null && !ruta.isEmpty()) {
                exportarCarpeta(carpeta, ruta);
            }
        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
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
            @Override
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
                    IDfSession lsesion = null;
                    while ((s1 = br.readLine()) != null) {
                        String[] result = s1.split(";");
                        String servidor = result[0];
                        String repositorio = result[1];
                        String usuario = result[2];
                        String clave = result[3];
                        String puerto = result[4];
                        barradocum.setLabelMensa(servidor + "  <-->  " + repositorio);
                        //   System.out.println(repositorio + "-" + servidor + "-" + puerto + "-" + usuario + "-" + clave);
                        lsesion = utilDocum.conectarDocumentum(usuario, clave, repositorio, servidor, puerto);
                        IDfCollection col = utilDocum.ejecutarDql("select count(*) from dmr_content where r_object_id in (select i_contents_id from  dm_sysobject where A_STORAGE_TYPE='filestore_01' and (R_CREATION_DATE > DATE('" + fechainicial + "','DD/MM/YYYY')) and (R_CREATION_DATE < DATE('" + fechafinal + "','DD/MM/YYYY')) )", lsesion);
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
                        } catch (DfException | NumberFormatException Ex) {
                            System.out.println(Ex.getMessage());
                        }

                        col = utilDocum.ejecutarDql("select sum(full_content_size)  from dmr_content where r_object_id in (select i_contents_id from  dm_sysobject where A_STORAGE_TYPE='filestore_01' and (R_CREATION_DATE > DATE('" + fechainicial + "','DD/MM/YYYY')) and (R_CREATION_DATE < DATE('" + fechafinal + "','DD/MM/YYYY')) )", lsesion);
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
                        } catch (DfException | NumberFormatException Ex) {
                            System.out.println(Ex.getMessage());
                        }
                        lNumeroLineas++;
                    }
                    try {
                        if (lsesion != null && lsesion.isConnected()) {
                            lsesion.disconnect();
                        }
                    } catch (DfException Ex) {
                        System.out.println(Ex.getMessage());
                    }
                    if (datos.length > 0) {
                        String[] cabecera = {"Servidor", "Repositorio", "Nº de Ficheros", "Tamaño de los Ficheros"};
                        DefaultTableModel modeloLotes = new DefaultTableModel() {
                            @Override
                            public boolean isCellEditable(int fila, int columna) {
                                return false;
                            }
                        };

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
                        etiquetaEstado.setText("Generado el fichero " + fichero);
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
                            String res = correo.enviar();
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
            IDfSession lsesion = utilDocum.conectarDocumentum();
            PantallaApi pantallaApi = new PantallaApi(this, true);
            pantallaApi.setTitle("Comandos de API  -  " + etiquetaDocbroker.getText() + " / " + etiquetaRepositorio.getText());
            pantallaApi.setSesion(lsesion);
            pantallaApi.setVisible(true);
        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
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
        IDfSession lsesion = utilDocum.conectarDocumentum();
        if (lsesion == null) {
            Utilidades.escribeLog("No se pudo obtener sesión de Documentum (checkout)");
            return;
        }
        String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
        try {
            String resultado = utilDocum.checkoutDoc(r_object_id, lsesion);
            if (resultado.contains("Error al hacer checkout de")) {
                etiquetaEstado.setText(resultado);
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

            if (lsesion.isConnected()) {
                lsesion.disconnect();
            }
        } catch (DfException ex) {
            Utilidades.escribeLog("No se pudo hacer check out de " + r_object_id + " - Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionCheckoutActionPerformed

    private void opcionCheckinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCheckinActionPerformed
        IDfSession lsesion = utilDocum.conectarDocumentum();
        if (lsesion == null) {
            Utilidades.escribeLog("No se pudo obtener sesión de Documentum (checkin)");
            return;
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
                utilDocum.checkinDoc(r_object_id, lsesion, fichero, version, descripcion, indexar);
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
            if (lsesion.isConnected()) {
                lsesion.disconnect();
            }
        } catch (DfException ex) {
            Utilidades.escribeLog("No se pudo hacer check in de " + r_object_id + " - Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionCheckinActionPerformed

    private void opcionCancelCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCancelCheckoutActionPerformed
        IDfSession lsesion = utilDocum.conectarDocumentum();
        if (lsesion == null) {
            Utilidades.escribeLog("No se pudo obtener sesión de Documentum (CancelCheckout)");
            return;
        }
        String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
        try {
            utilDocum.cancelCheckout(r_object_id, lsesion);
            int fila = tablaDocumentos.getSelectedRow();
            int columna = 5;
            java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/vacio.gif");
            ImageIcon iconoCheckin = new ImageIcon(imgURL);
            tablaDocumentos.getModel().setValueAt(iconoCheckin, fila, columna);
            // tablaDocumentos.getModel().setValueAt(" ", fila, columna);
            opcionCancelCheckout.setVisible(false);
            opcionCheckin.setVisible(false);
            opcionCheckout.setVisible(true);
            if (lsesion.isConnected()) {
                lsesion.disconnect();
            }
        } catch (DfException ex) {
            Utilidades.escribeLog("No se pudo hacer cancel check out de " + r_object_id + " - Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_opcionCancelCheckoutActionPerformed

    private void textoRutaDocumentumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoRutaDocumentumKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            buscarEnDocumentum();
        }
    }//GEN-LAST:event_textoRutaDocumentumKeyPressed

    private void textoCarpetaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoCarpetaKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            buscarEnDocumentum();
        }
    }//GEN-LAST:event_textoCarpetaKeyPressed

    private void textoIdDocumentumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoIdDocumentumKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            buscarEnDocumentum();
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
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
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
            IDfSession lsesion = utilDocum.conectarDocumentum();
            pantallaTareasIndexador.setSesion(lsesion);
            pantallaTareasIndexador.cargarTabla();
            pantallaTareasIndexador.setVisible(true);
//            try {
//                if (lsesion.isConnected()) {
//                    lsesion.disconnect();
//                }
//            } catch (DfException ex) {
//
//            }

        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
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
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionJobsActionPerformed

    private void opcionCrearCarpetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCrearCarpetaActionPerformed
        crearCarpetaEnRepo();
    }//GEN-LAST:event_opcionCrearCarpetaActionPerformed

    private void crearCarpetaEnRepo() {
        PantallaPedirDatos pedir = new PantallaPedirDatos(this, true);
        pedir.setAccion("CrearCarpeta");
        pedir.setIcono("carpeta_azul.png");
        pedir.asignarIconos();
        pedir.setTitle("Crear carpeta en el Repositorio");
        pedir.setEtiquetaCarpeta("Nombre de la carpeta");
        pedir.setEtiquetaRuta("Ruta de origen de la carpeta");
        pedir.botonNavegar.setVisible(false);
        pedir.textoRutaSO.setSize(pedir.textoCarpeta.getSize());
        if (!textoRutaDocumentum.getText().isEmpty()) {
            pedir.textoRutaSO.setText(textoRutaDocumentum.getText());
        }
        pedir.setVisible(true);
        String carpeta = pedir.getCarpeta();
        String ruta = pedir.getRuta();
        String rutacompleta = "";
        if (carpeta != null && !carpeta.isEmpty() && ruta != null && !ruta.isEmpty()) {
            if (ruta.equals("/")) {
                rutacompleta = ruta + carpeta;
            } else {
                rutacompleta = ruta + "/" + carpeta;
            }
            IDfFolder folder = utilDocum.crearCarpeta(utilDocum.conectarDocumentum(), rutacompleta);
            if (folder != null) {
                String id = "";
                try {
                    id = folder.getObjectId().toString();
                } catch (DfException ex) {

                }
                etiquetaEstado.setText("Creada carpeta " + carpeta + " con el id '" + id + "' en la ruta " + ruta);
                if (!checkArbolSiNo.isSelected()) {
                    DefaultMutableTreeNode nodo = dameNodoDeRuta(ruta);
                    if (nodo != null) {
                        DefaultMutableTreeNode nodohijo = new DefaultMutableTreeNode(carpeta);
                        nodo.add(nodohijo);
//                    irANodo(rutacompleta);
                        DefaultTreeModel model = (DefaultTreeModel) arbolRepo.getModel();
                        model.reload(nodo);
                    }
                }
            } else {
                etiquetaEstado.setText("No se pudo crear la carpeta " + carpeta + " en la ruta " + ruta);
            }
        }
    }

    private void opcionBorrarObjetosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionBorrarObjetosActionPerformed
        final int[] seleccion = tablaDocumentos.getSelectedRows();
        if (seleccion.length == 0) {
            etiquetaEstado.setText("Ningún objeto seleccionado");
            return;
        }
        PantallaConfirmaDialogo confirma = new PantallaConfirmaDialogo(this, true);
        confirma.setTitle("Borrado de objetos y su posible contenido en Documentum");
        final Boolean multiple;
        if (seleccion.length == 1) {
            String ruta = textoRutaDocumentum.getText() + "/" + tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString();
            String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
            if (utilDocum.existeCarpeta(ruta)) {
                String nombreDir = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString();
                confirma.etiqueta.setText("¿Desea realmente borrar la Carpeta " + nombreDir + " y su contenido?");
            } else {
                confirma.etiqueta.setText("¿Desea realmente borrar el objecto con r_object_id " + r_object_id + "?");
            }
            multiple = false;
        } else {
            confirma.etiqueta.setText("¿Desea realmente borrar estos objectos y su posible contenido?");
            multiple = true;
        }

        confirma.etiqueta.setOpaque(false);
        confirma.etiqueta.setBorder(BorderFactory.createEmptyBorder());
        confirma.etiqueta.setBackground(new Color(0, 0, 0, 0));
        confirma.repaint();
        confirma.setVisible(true);
        Boolean resultado = confirma.respuesta();
        if (resultado) {
            new Thread() {
                @Override
                public void run() {
                    try {
//                        IDfSession lsesion = utilDocum.conectarDocumentum();
                        if (multiple) {
                            barradocum = new PantallaBarra(PantallaDocumentum.this, false);
                            barradocum.setTitle("Borrando objetos y su posible contenido...");
                            barradocum.barra.setIndeterminate(true);
                            barradocum.botonParar.setVisible(false);
                            barradocum.setLabelMensa("");
                            barradocum.barra.setOpaque(true);
                            barradocum.barra.setStringPainted(false);
                            barradocum.validate();
                            barradocum.setVisible(true);
                            for (int n = 0; n < seleccion.length; n++) {
                                int pos = seleccion[n];
                                String ruta = textoRutaDocumentum.getText() + "/" + tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(pos), 0).toString();
                                String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(pos), 1).toString();
                                if (utilDocum.existeCarpeta(ruta)) {
                                    barradocum.setLabelMensa("Carpeta  -->  " + tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(pos), 0).toString());
                                    barradocum.validate();
                                    IDfFolder carpeta = (IDfFolder) gsesion.getObjectByPath(ruta);

                                    if (utilDocum.borrarRuta(carpeta)) {
                                        etiquetaEstado.setText("Borrada la carpeta " + tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(pos), 0).toString());
                                        if (!checkArbolSiNo.isSelected()) {
                                            DefaultMutableTreeNode nodo = dameNodoDeRuta(ruta);
                                            DefaultTreeModel modeloArbol = (DefaultTreeModel) arbolRepo.getModel();
                                            DefaultMutableTreeNode nodopadre = (DefaultMutableTreeNode) nodo.getParent();
                                            modeloArbol.removeNodeFromParent(nodo);
                                            expandOrCollapsToLevel(arbolRepo, new TreePath(nodopadre.getParent()), 1, true);
                                        }
                                    }
                                } else {
                                    barradocum.setLabelMensa("Objeto  -->  " + r_object_id);
                                    if (utilDocum.borrarDocumento(r_object_id)) {
                                        etiquetaEstado.setText("Borrado el objeto con r_object_id " + r_object_id);
                                    }
                                }
                            }
                            barradocum.dispose();
                        } else {
                            String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
                            String ruta = textoRutaDocumentum.getText() + "/" + tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString();
                            ruta = ruta.replace("//", "/");
                            if (utilDocum.existeCarpeta(ruta)) {
                                IDfFolder carpeta = (IDfFolder) gsesion.getObjectByPath(ruta);
                                barradocum = new PantallaBarra(PantallaDocumentum.this, false);
                                barradocum.setTitle("Borrando objetos y su posible contenido...");
                                barradocum.barra.setIndeterminate(true);
                                barradocum.setDefaultCloseOperation(1);
                                barradocum.botonParar.setVisible(false);
                                barradocum.setLabelMensa("Borrando " + ruta + " ...");
                                barradocum.barra.setOpaque(true);
                                barradocum.barra.setStringPainted(false);
                                barradocum.validate();
                                barradocum.setVisible(true);
                                utilDocum.borrarRuta(carpeta);
                                if (!checkArbolSiNo.isSelected()) {
                                    DefaultMutableTreeNode nodo = dameNodoDeRuta(ruta);
                                    DefaultTreeModel modeloArbol = (DefaultTreeModel) arbolRepo.getModel();
                                    DefaultMutableTreeNode nodopadre = (DefaultMutableTreeNode) nodo.getParent();
                                    modeloArbol.removeNodeFromParent(nodo);
                                    expandOrCollapsToLevel(arbolRepo, new TreePath(nodopadre.getPath()), 1, true);
                                }
                                barradocum.dispose();
                            } else {
                                if (utilDocum.borrarDocumento(r_object_id)) {
                                    etiquetaEstado.setText("Borrado el objeto con r_object_id " + r_object_id);
                                }
                            }
                        }
//                        if (lsesion.isConnected()) {
//                            lsesion.disconnect();
//                        }
                        buscarEnDocumentum();
                    } catch (DfException ex) {
                        System.out.println("Error al borrar Objeto - Error: " + ex.getMessage());
                    }
                }
            }.start();
            System.gc();

        }
    }//GEN-LAST:event_opcionBorrarObjetosActionPerformed

    private void opcionPropiedadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionPropiedadesActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaPropiedades propiedades = new PantallaPropiedades(this, true);
            String id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
            propiedades.setR_object_id(id);
            propiedades.consultarValores(id);
            propiedades.setLocationRelativeTo(this);
            propiedades.setVisible(true);
        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionPropiedadesActionPerformed

    private void opcionCambiarTipoDocumentalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCambiarTipoDocumentalActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaCambiarTipo pantallacambiartipo = new PantallaCambiarTipo(this, true);
            String id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();

            pantallacambiartipo.setR_object_id(id);
            pantallacambiartipo.setTipoDocumentalActual(utilDocum.dameTipoDocumental(id));
            pantallacambiartipo.setTitle("Cambiar tipo Documental - r_object_id: " + id);
            pantallacambiartipo.cargarComboTipos();
            pantallacambiartipo.setVisible(true);

            if (pantallacambiartipo.getAccion().equalsIgnoreCase("OK")) {
                buscarEnDocumentum();
            }

        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionCambiarTipoDocumentalActionPerformed

    private void opcionRelationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRelationsActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaRelations pantallarelations = new PantallaRelations(this, true);
            String id = "";
            if (tablaDocumentos.getSelectedRow() > 0) {
                tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
            }
            pantallarelations.setTitle("Relations de Documentos - r_object_id: " + id);
            pantallarelations.setId(id);
            pantallarelations.cargarDatos(id);
            pantallarelations.setVisible(true);

        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionRelationsActionPerformed

    private void opcionRelationsPopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionRelationsPopupActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaRelations pantallarelations = new PantallaRelations(this, true);
            String id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
            pantallarelations.setTitle("Relations de Documentos - r_object_id: " + id);
            pantallarelations.cargarDatos(id);
            if (!pantallarelations.isPrimeravez()) {
                pantallarelations.setVisible(true);
            }
        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionRelationsPopupActionPerformed

    private void opcionTiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionTiposActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaArbolTipos pantallatipos = new PantallaArbolTipos(this, true);
            pantallatipos.setTitle("Tipos Documentales");
            pantallatipos.setVisible(true);
        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionTiposActionPerformed

    private void opcionCriptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCriptoActionPerformed
        PantallaDesencriptar pantallacripto = new PantallaDesencriptar(this, true);
        pantallacripto.setVisible(true);
    }//GEN-LAST:event_opcionCriptoActionPerformed

    private void botonActivarCriptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActivarCriptoActionPerformed
        if (opcionCripto.isVisible()) {
            opcionCripto.setVisible(false);
        } else {
            opcionCripto.setVisible(true);
        }
    }//GEN-LAST:event_botonActivarCriptoActionPerformed

    private void opcionStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionStorageActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaStorage pantallastorage = new PantallaStorage(this, true);
            pantallastorage.setTitle("Almacenamiento en el repositorio " + repositorio);
            pantallastorage.cargarStorage();
            pantallastorage.setVisible(true);

        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionStorageActionPerformed

    private void opcionInfoRepoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionInfoRepoActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaInfoRepositorio pantallaInfoRepo = new PantallaInfoRepositorio(this, true);
            pantallaInfoRepo.setTitle("Información del repositorio " + repositorio);
            IDfSession lsesion = utilDocum.conectarDocumentum(usuario, clave, repositorio, docbroker, puerto);
            pantallaInfoRepo.setGsesion(lsesion);
            pantallaInfoRepo.setRepositorio(repositorio);
            pantallaInfoRepo.inicializar();
            pantallaInfoRepo.setVisible(true);

        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionInfoRepoActionPerformed

    private void opcionSesionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionSesionesActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaSesiones pantallasesiones = new PantallaSesiones(this, true);
            pantallasesiones.setTitle("Sesiones del repositorio " + repositorio);
            IDfSession lsesion = utilDocum.conectarDocumentum(usuario, clave, repositorio, docbroker, puerto);
            pantallasesiones.setGsesion(lsesion);
            pantallasesiones.setRepo(repositorio);
            pantallasesiones.inicializar();
            pantallasesiones.setVisible(true);

        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionSesionesActionPerformed

    private void opcionGruposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionGruposActionPerformed
        if (botonBuscar.isEnabled()) {
            PantallaArbolGrupos pantallagrupos = new PantallaArbolGrupos(this, true);
            pantallagrupos.setTitle("Grupos y Roles del repositorio " + repositorio);
//            IDfSession lsesion = utilDocum.conectarDocumentum(usuario, clave, repositorio, docbroker, puerto);
//            pantallagrupos.setGsesion(lsesion);
//            pantallagrupos.setRepo(repositorio);
//            pantallagrupos.inicializar();
//            pantallagrupos.cargarArbol(); 
            pantallagrupos.setVisible(true);
        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionGruposActionPerformed

    private void textoNumRegistrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoNumRegistrosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoNumRegistrosActionPerformed

    private void opcionEjecutarSqlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionEjecutarSqlActionPerformed
        if (botonBuscar.isEnabled()) {
            String gestorBBDD = utilDocum.dameGestorBbddDocumentum();
            PantallaSqlconTabs pantallaSql = new PantallaSqlconTabs(this, true, gestorBBDD);
            pantallaSql.setTitle("Consultas a Documentum por SQL(" + gestorBBDD + ") nativa  -  " + etiquetaDocbroker.getText() + " / " + etiquetaRepositorio.getText());
            pantallaSql.setGestorBBDD(gestorBBDD);
            pantallaSql.setVisible(true);
//            PantallaDql pantallaDql = new PantallaDql(this, true);
//            pantallaDql.setTitle("Consultas a Documentum por DQL  -  " + EtiquetaDocbroker.getText() + " / " + EtiquetaRepositorio.getText());
//            pantallaDql.setVisible(true);            

        } else {
            etiquetaEstado.setText("Debe seleccionar antes una conexión.");
            botonConectar.requestFocus();
        }
    }//GEN-LAST:event_opcionEjecutarSqlActionPerformed

    private void arbolRepoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arbolRepoMousePressed
        if (evt.getClickCount() == 2 && evt.getButton() == java.awt.event.MouseEvent.BUTTON1 && !arbolRepo.isSelectionEmpty()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) arbolRepo.getLastSelectedPathComponent();
            if (node == null) // Nothing is selected.
            {
                return;
            }
            Object[] rutas = node.getPath();
            String rutadir = "/";
            for (int i = 0; i < rutas.length; i++) {
                rutadir += rutas[i];
                if (i + 1 < rutas.length) {
                    rutadir += "/";
                }
            }

            if (rutadir.equals("/" + repositorio)) {
                textoRutaDocumentum.setText("/");
            } else {
                rutadir = rutadir.replaceFirst("/" + repositorio, "");
                textoRutaDocumentum.setText(rutadir);
            }

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
//                    etiquetaEstado.setText("Recuperando carpetas de " + textoRutaDocumentum.getText() + " ...");
                    buscarEnDocumentum();
//                    actualizarNodoActual();
                }
            });
        } else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
//            if (!arbolRepo.isSelectionEmpty()) {
//                DefaultMutableTreeNode node = (DefaultMutableTreeNode) arbolRepo.getLastSelectedPathComponent();
//                Object[] rutas = node.getPath();
//                String rutadir = "/";
//                for (int i = 0; i < rutas.length; i++) {
//                    rutadir += rutas[i];
//                    if (i + 1 < rutas.length) {
//                        rutadir += "/";
//                    }
//                }
//
//                if (rutadir.equals("/" + repositorio)) {
//                } else {
//                    rutadir = rutadir.replaceFirst("/" + repositorio, "");
//                }
//
//                int valores = arbolRepo.getModel().getChildCount(node);
//                if (valores < 100) {
//                    for (int num = 0; num < valores; num++) {
//                        DefaultMutableTreeNode nodobuscar = (DefaultMutableTreeNode) arbolRepo.getModel().getChild(node, num);
//                        String rutarefresco = rutadir + "/" + nodobuscar.getUserObject().toString();
//                        irANodo(rutarefresco);
//                    arbolRepo.collapsePath(new TreePath(nodobuscar.getPath()));
//                    }
//                    irANodo(rutadir);
//                }
//            }
        }
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            botonderecho = true;
            componente = "arbolRepo";
            popupmenu(evt);
        }
    }//GEN-LAST:event_arbolRepoMousePressed

    private void arbolRepoValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_arbolRepoValueChanged
        actualizarNodoActual();
    }//GEN-LAST:event_arbolRepoValueChanged

    private void botonRefrescarArbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRefrescarArbolActionPerformed
        if (conectado) {
            modeloLotes = new DefaultTableModel() {
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
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        tablaDocumentos.setModel(modeloLotes);
                    }
                });
            } catch (Exception ex) {
            }
            cargarAtributos("");
            cargarDocumentos("", "");
            cargarArbol();
            textoRutaDocumentum.setText("");
        }
    }//GEN-LAST:event_botonRefrescarArbolActionPerformed

    private void opcionColapsarNodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionColapsarNodoActionPerformed
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) arbolRepo.getLastSelectedPathComponent();
        if (nodo == null || nodo.isRoot()) {
            return;
        }
        arbolRepo.collapsePath(new TreePath(nodo.getPath()));

//        renombrarNodo(nodo, "Directorio");
    }//GEN-LAST:event_opcionColapsarNodoActionPerformed

    private void opcionCrearCarpetaDesdeArbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCrearCarpetaDesdeArbolActionPerformed
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) arbolRepo.getLastSelectedPathComponent();
        if (nodo == null) {
            return;
        }
        crearCarpetaEnRepo();
    }//GEN-LAST:event_opcionCrearCarpetaDesdeArbolActionPerformed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        if (checkArbolSiNo.isSelected()) {
            divisorArbol.getLeftComponent().setMinimumSize(new Dimension());
            divisorArbol.setDividerLocation(0.0d);
//            divisorArbol.setDividerLocation(1.0);
//            divisorArbol.
        }
    }//GEN-LAST:event_formWindowStateChanged

    private void checkArbolSiNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkArbolSiNoActionPerformed

        if (checkArbolSiNo.isSelected()) {
            botonRefrescarArbol.setEnabled(false);
            divisorArbol.getLeftComponent().setMinimumSize(new Dimension());
            divisorArbol.setDividerLocation(1);
            arbolRepo.setModel(null);

        } else {
            divisorArbol.setDividerLocation(200);
            if (conectado) {
                botonRefrescarArbol.setEnabled(true);
                cargarCabinetes();
                cargarArbol();
            }
        }

    }//GEN-LAST:event_checkArbolSiNoActionPerformed

    private void actualizarNodoActual() {
        if (!arbolRepo.isSelectionEmpty()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) arbolRepo.getLastSelectedPathComponent();
            if (node == null) // Nothing is selected.
            {
                return;
            }

            Object nodeInfo = node.getUserObject();
            String nombre = nodeInfo.toString();

            Object[] rutas = node.getPath();
            String rutadir = "/";
            for (int i = 0; i < rutas.length; i++) {
                rutadir += rutas[i];
                if (i + 1 < rutas.length) {
                    rutadir += "/";
                }
            }

            if (rutadir.equals("/" + repositorio)) {
                textoRutaDocumentum.setText("/");
            } else {
                rutadir = rutadir.replaceFirst("/" + repositorio, "");
                textoRutaDocumentum.setText(rutadir);
            }

            if (esDirPadre(rutadir) && node.isLeaf()) {
                dameCarpetas(nombre, node);
                arbolRepo.getCellRenderer();
                expandOrCollapsToLevel(arbolRepo, new TreePath(node.getPath()), 1, true);
                arbolRepo.expandPath(new TreePath(node.getPath()));
            }
        }
    }

    public void cargarArbol() {
        new Thread() {
            @Override
            public void run() {
                arbolRepo.setModel(null);
                arbolRepo.setCellRenderer(new RendererArbol());
                arbolRepo.setRootVisible(true);
                raiz.removeAllChildren();
                arbolRepo.setModel(modelo);
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelo.getRoot();
                root.setUserObject(repositorio);
                modelo.nodeChanged(root);
                dameCarpetas(" ", raiz);
                expandOrCollapsToLevel(arbolRepo, new TreePath(((DefaultMutableTreeNode) arbolRepo.getModel().getRoot())), 1, true);
                for (Enumeration e = root.children(); e.hasMoreElements();) {
                    DefaultMutableTreeNode n = (DefaultMutableTreeNode) e.nextElement();
                    barradocum.setLabelMensa("Obteniendo datos del Cabinet  " + n.toString());
                    dameCarpetas(n.toString(), n);
                    expandOrCollapsToLevel(arbolRepo, new TreePath(n.getPath()), 1, true);
                    arbolRepo.collapsePath(new TreePath(n.getPath()));
                }

            }
        }.start();
    }

    private void cargarCabinetes() {
        cabinetes.clear();
        String dql = "select object_name from dm_cabinet order by object_name";
        try {
            IDfQuery q = new DfQuery();
            q.setDQL(dql);
            IDfCollection col = q.execute(gsesion, DfQuery.EXEC_QUERY);
            while (col.next()) {
                IDfTypedObject r = col.getTypedObject();
                String cabinet = r.getValueAt(0).asString();
                if (barradocum.isVisible()) {
                    barradocum.setLabelMensa("Consultado contenido de " + cabinet);
                }
                Boolean tiene = utilDocum.tieneCarpetas2(cabinet, gsesion);
                CabinetConCarpeta cabin = new CabinetConCarpeta();
                cabin.setNombrecabinet(cabinet);
                cabin.setTienecapetas(tiene);
                cabinetes.add(cabin);
            }
            col.close();
        } catch (DfException ex) {
            Utilidades.escribeLog("dameCarpetas. Error al ejecutar DQL: " + ex.getMessage());
        }
    }

    private Boolean cabinetTieneCarpetas(String cabinet) {
        int n = 0;
        while (n < cabinetes.size()) {
            if (cabinetes.get(n).getNombrecabinet().equals(cabinet)) {
                return cabinetes.get(n).getTienecapetas();
            }
            n++;
        }

        return false;
    }

    private void dameCarpetas(String directorio, DefaultMutableTreeNode nodo) {
        int numreg = Integer.parseInt(textoNumRegistros.getText());

        Object[] rutas = nodo.getPath();
        String rutadir = "/";
        for (int i = 0; i < rutas.length; i++) {
            rutadir += rutas[i];
            if (i + 1 < rutas.length) {
                rutadir += "/";
            }
        }
        rutadir = rutadir.replaceFirst("/" + repositorio, "");

        String dql = "SELECT object_name FROM dm_folder WHERE  folder('" + rutadir + "') order by object_name ";
        rutadir = rutadir + "/";

        if (directorio.equals(" ")) {
            dql = "select object_name from dm_cabinet order by object_name";
            rutadir = "/";
        }
//        if (numreg > 0) {
//            dql = dql + " enable (return_top " + numreg + ")";
//        } else {
//            etiquetaEstado.setText("Sin limite de grupos a consultar");
//        }

        try {
            IDfQuery q = new DfQuery();
            if (barradocum.isVisible()) {
                barradocum.setLabelMensa("Recuperando información de " + rutadir);
            }
            q.setDQL(dql);
            IDfCollection col = q.execute(gsesion, DfQuery.EXEC_QUERY);
            while (col.next()) {
                IDfTypedObject r = col.getTypedObject();
                String hijo = r.getValueAt(0).asString();
                DefaultMutableTreeNode nodohijo = new DefaultMutableTreeNode(hijo);
                nodo.add(nodohijo);
//                if (esDirPadre(hijo) && !directorio.equals(" ")) {
//                    dameCarpetas(hijo, nodohijo);
//                }
            }
            col.close();
        } catch (DfException ex) {
            Utilidades.escribeLog("dameCarpetas. Error al ejecutar DQL: " + ex.getMessage());
        }
    }

    public void expandOrCollapsToLevel(JTree tree, TreePath treePath, int level, boolean expand) {
        try {
            expandOrCollapsePath(tree, treePath, level, 0, expand);
        } catch (Exception e) {
            // e.printStackTrace();

        }
    }

    public void recorrerNodos(TreeNode nodo) {
        //    System.out.println(nodo.toString());
        if (nodo.getChildCount() >= 0) {
            for (Enumeration e = nodo.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                recorrerNodos(n);
            }
        }
    }

    public void expandOrCollapsePath(JTree tree, TreePath treePath, int level, int currentLevel, boolean expand) {
//      System.err.println("Exp level "+currentLevel+", exp="+expand);
        if (expand && level <= currentLevel && level > 0) {
            return;
        }

        TreeNode treeNode = (TreeNode) treePath.getLastPathComponent();
        TreeModel treeModel = tree.getModel();
        if (treeModel.getChildCount(treeNode) >= 0) {
            for (int i = 0; i < treeModel.getChildCount(treeNode); i++) {
                TreeNode n = (TreeNode) treeModel.getChild(treeNode, i);
                TreePath path = treePath.pathByAddingChild(n);
                expandOrCollapsePath(tree, path, level, currentLevel + 1, expand);
            }
            if (!expand && currentLevel < level) {
                return;
            }
        }
        if (expand) {
            tree.expandPath(treePath);
//         System.err.println("Path expanded at level "+currentLevel+"-"+treePath);
        } else {
            tree.collapsePath(treePath);
//         System.err.println("Path collapsed at level "+currentLevel+"-"+treePath);
        }
    }

    public class RendererArbol extends DefaultTreeCellRenderer {

        ImageIcon carpeta;
        ImageIcon carpeta_abierta;
        ImageIcon carpeta_cerrada;
        ImageIcon cabinet_abierto;
        ImageIcon cabinet_cerrado;
        ImageIcon cabinet;
        ImageIcon carpeta_gris;

        public RendererArbol() {
            carpeta = new ImageIcon(PantallaArbolDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/carpeta_azul.png"));
            carpeta_abierta = new ImageIcon(PantallaArbolDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/carpeta_azul_abierta.png"));
            carpeta_cerrada = new ImageIcon(PantallaArbolDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/carpeta-cerrada.png"));
            cabinet_abierto = new ImageIcon(PantallaArbolDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/cabinet-abierto-azul.png"));
            cabinet_cerrado = new ImageIcon(PantallaArbolDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/cabinet-cerrado-azul.png"));
            cabinet = new ImageIcon(PantallaArbolDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/cabinet-cerrado-rojo.png"));
            carpeta_gris = new ImageIcon(PantallaArbolDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/carpeta_gris.png"));
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            String nombre = value.toString();
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;

            Object[] rutas = nodo.getPath();
            String rutadir = "/";
            for (int i = 0; i < rutas.length; i++) {
                rutadir += rutas[i];
                if (i + 1 < rutas.length) {
                    rutadir += "/";
                }
            }
            rutadir = rutadir.replaceFirst("/" + repositorio, "");
            String esCabinet = "false";
            if (!nodo.isRoot()) {
                DefaultMutableTreeNode nodopadre = (DefaultMutableTreeNode) nodo.getParent();
                if (nodopadre.getUserObject().toString().equals(" ") || nodopadre.getUserObject().toString().equals(repositorio)) {
                    esCabinet = "true";
                    setIcon(cabinet);
                }
            }

            if (expanded) {
                if (esCabinet.equals("true")) {
                    setIcon(cabinet_abierto);
                } else {
                    setIcon(carpeta_abierta);
                }
            } else {
                if (nodo.getChildCount() > 0) {
                    switch (esCabinet) {
                        case "true":
                            setIcon(cabinet_cerrado);
                            break;
                        default:
                            setIcon(carpeta);
                    }
                } else {
                    if (esCabinet.equals("true")) {
                        setIcon(cabinet);
                    } else {
                        if (leaf) {
                            setIcon(carpeta_cerrada);
                        } else {
                            setIcon(carpeta_gris);
                        }
                    }
                }
            }

            return this;
        }

    }

    private void irANodo(String ruta) {
        DefaultMutableTreeNode nodoraiz = (DefaultMutableTreeNode) arbolRepo.getModel().getRoot();
        DefaultMutableTreeNode nodoactual = nodoraiz;
        DefaultMutableTreeNode nodobuscar = nodoactual;
        if (ruta.equals("/") || ruta.equals("/" + repositorio)) {
            TreePath rutanodos = new TreePath(nodobuscar.getPath());
            arbolRepo.setSelectionPath(rutanodos);
            arbolRepo.scrollPathToVisible(rutanodos);
            expandOrCollapsToLevel(arbolRepo, rutanodos, 1, true);
            return;
        }
        String[] path = ruta.split("/");

        for (int n = 1; n < path.length; n++) {
            int valores = arbolRepo.getModel().getChildCount(nodoactual);
            Boolean encontrado = false;
            for (int num = 0; num < valores && encontrado == false; num++) {
                nodobuscar = (DefaultMutableTreeNode) arbolRepo.getModel().getChild(nodoactual, num);
                if (nodobuscar.getUserObject().toString().equals(path[n])) {
                    TreePath rutanodos = new TreePath(nodobuscar.getPath());
                    arbolRepo.setSelectionPath(rutanodos);
                    arbolRepo.scrollPathToVisible(rutanodos);
                    encontrado = true;
                }
            }
            nodoactual = nodobuscar;
        }
    }

    private DefaultMutableTreeNode dameNodoDeRuta(String ruta) {
        DefaultMutableTreeNode nodoraiz = (DefaultMutableTreeNode) arbolRepo.getModel().getRoot();
        DefaultMutableTreeNode nodoactual = nodoraiz;
        DefaultMutableTreeNode nodobuscar = nodoactual;
        if (ruta.equals("/") || ruta.equals("/" + repositorio)) {
            TreePath rutanodos = new TreePath(nodobuscar.getPath());
            arbolRepo.setSelectionPath(rutanodos);
            arbolRepo.scrollPathToVisible(rutanodos);
            expandOrCollapsToLevel(arbolRepo, rutanodos, 1, true);
            return nodoraiz;
        }
        String[] path = ruta.split("/");

        for (int n = 1; n < path.length; n++) {
            int valores = arbolRepo.getModel().getChildCount(nodoactual);
            Boolean encontrado = false;
            for (int num = 0; num < valores && !encontrado; num++) {
                nodobuscar = (DefaultMutableTreeNode) arbolRepo.getModel().getChild(nodoactual, num);
                if (nodobuscar.getUserObject().toString().equals(path[n])) {
                    encontrado = true;
                }
            }
            nodoactual = nodobuscar;
        }
        return nodobuscar;
    }

    public Boolean esDirPadre(String carpeta) {
        String ruta = carpeta;
//        IDfFolder folder = null;
//        folder = gsesion.getFolderByPath(carpeta);
        String dql = "SELECT count(*) FROM  dm_sysobject WHERE folder('" + carpeta + "')";
        try {
//            folder = gsesion.getFolderByPath(ruta);
//            return folder.isReference();

            String resultado = execQuery(dql, gsesion);
            int valor = Integer.parseInt(resultado);
            return valor > 0;
        } catch (NumberFormatException ex) {
            Utilidades.escribeLog("Error al consultar esDirPadre - Carpeta: " + carpeta + " - Error: " + ex.getMessage());
        }
//        } catch (DfException ex) {
//            java.util.logging.Logger.getLogger(PantallaDocumentum.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return false;
    }

    public String execQuery(String query, IDfSession sesion) {
        //    Utilidades.escribeLog("execQuery: " + query + NEWLINE);
        String retVal = "";
        IDfCollection col = null;
        try {
            IDfQuery q = new DfQuery();
            q.setDQL(query);
            col = q.execute(sesion, DfQuery.EXEC_QUERY);
            if (col.next()) {
                IDfTypedObject r = col.getTypedObject();
                retVal = r.getValueAt(0).asString();
            }
        } catch (DfException ex) {
            Utilidades.escribeLog("execQuery.error al ejecutar DQL: " + ex.getMessage());
        }
        try {
            col.close();
        } catch (DfException e) {
            Utilidades.escribeLog("execQuery.error General: " + e.getMessage());
        }
        return retVal;
    }

    public void mostrarAcercade() {
        if (conectado) {
            versiondfcs = utilDocum.dameVersionDFC();
        }
        Acercade about = new Acercade(this, true);
        about.setDirdfc(dirdfc);
        if (conectado) {
            about.setDocbroker(docbroker);
            about.setRepositorio(repositorio);
            about.setUsuario(usuario);
            about.setVersiondocumentum(versiondocumentum);
            about.setIdrepositorio(utilDocum.dameIdRepositorio());
        }
        about.setLocationRelativeTo(this);
        about.setVisible(true);
    }

    public static void main(String args[]) {
        // Impide que se abrán más de una instancia de la aplicación
        try {
            Socket clientSocket = new Socket("localhost", ConexionUnica.PORT);
            Utilidades util = new Utilidades();
            util.mensaje(ventanapadre, "DocumentumDFCs", "\n  Ya existe una instancia de la aplicación DocumentumDFCs en ejecución.");
            System.exit(0);
        } catch (Exception e) {
            ConexionUnica sds = new ConexionUnica();
            sds.start();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
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
    private javax.swing.JPopupMenu.Separator Separador1;
    private javax.swing.JPopupMenu.Separator Separador3;
    private javax.swing.JPopupMenu.Separator Separador4;
    private javax.swing.JTree arbolRepo;
    private javax.swing.JButton botonActivarCripto;
    private javax.swing.JButton botonArribaDir;
    private javax.swing.JButton botonBuscar;
    private javax.swing.JButton botonConectar;
    private javax.swing.JButton botonRefrescarArbol;
    private javax.swing.JButton botonSalir;
    private javax.swing.JCheckBoxMenuItem checkArbolSiNo;
    private javax.swing.JSplitPane divisor;
    private javax.swing.JSplitPane divisorArbol;
    public javax.swing.JLabel etiquetaDocbroker;
    public javax.swing.JLabel etiquetaEstado;
    public javax.swing.JLabel etiquetaRepositorio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenu menuAcercade;
    private javax.swing.JMenu menuApariencia;
    private javax.swing.JMenuBar menuDocumentum;
    private javax.swing.JMenu menuOpciones;
    private javax.swing.JMenu menuUtilidades;
    private javax.swing.JMenuItem opcionAPI;
    private javax.swing.JMenuItem opcionAbrirDocumento;
    private javax.swing.JMenuItem opcionAcercade;
    private javax.swing.JMenuItem opcionActualizarAtributo;
    private javax.swing.JMenuItem opcionBorradoLogico;
    private javax.swing.JMenuItem opcionBorrarObjetos;
    private javax.swing.JMenuItem opcionBuscar;
    private javax.swing.JMenuItem opcionCambiarTipoDocumental;
    private javax.swing.JMenuItem opcionCancelCheckout;
    private javax.swing.JMenuItem opcionCheckin;
    private javax.swing.JMenuItem opcionCheckout;
    private javax.swing.JMenuItem opcionColapsarNodo;
    private javax.swing.JMenuItem opcionConectar;
    private javax.swing.JMenuItem opcionConvertir;
    private javax.swing.JMenuItem opcionCopiar;
    private javax.swing.JMenuItem opcionCopiarAtributo;
    private javax.swing.JMenuItem opcionCopiarIDDocumentum;
    private javax.swing.JMenuItem opcionCopiarNombre;
    private javax.swing.JMenuItem opcionCopiarValor;
    private javax.swing.JMenuItem opcionCrearCarpeta;
    private javax.swing.JMenuItem opcionCrearCarpetaDesdeArbol;
    private javax.swing.JMenuItem opcionCripto;
    private javax.swing.JMenuItem opcionDql;
    private javax.swing.JMenuItem opcionDumpAtributos;
    private javax.swing.JMenuItem opcionEjecutarComandoSSOO;
    private javax.swing.JMenuItem opcionEjecutarSql;
    private javax.swing.JMenuItem opcionEstadisticasRepos;
    private javax.swing.JMenuItem opcionExportar;
    private javax.swing.JMenuItem opcionExportarAtributosExcel;
    private javax.swing.JMenuItem opcionExportarCarpeta;
    private javax.swing.JMenuItem opcionExportarDocumentosExcel;
    private javax.swing.JMenuItem opcionGrupos;
    private javax.swing.JMenuItem opcionImportarADocumentum;
    private javax.swing.JMenuItem opcionIndexador;
    private javax.swing.JMenuItem opcionInfoRepo;
    private javax.swing.JMenuItem opcionJobs;
    private javax.swing.JMenuItem opcionLeerLog;
    private javax.swing.JMenuItem opcionManual;
    private javax.swing.JMenuItem opcionPasswordLDAP;
    private javax.swing.JMenuItem opcionPegar;
    private javax.swing.JMenuItem opcionPropiedades;
    private javax.swing.JRadioButtonMenuItem opcionRBMetal;
    private javax.swing.JRadioButtonMenuItem opcionRBNimbus;
    private javax.swing.JRadioButtonMenuItem opcionRBPorDefecto;
    private javax.swing.JRadioButtonMenuItem opcionRBWindows;
    private javax.swing.JRadioButtonMenuItem opcionRBWindowsClassic;
    private javax.swing.JMenuItem opcionRelations;
    private javax.swing.JMenuItem opcionRelationsPopup;
    private javax.swing.JMenuItem opcionRenditions;
    private javax.swing.JMenuItem opcionSalir;
    private javax.swing.JMenuItem opcionSesiones;
    private javax.swing.JMenuItem opcionStorage;
    private javax.swing.JMenuItem opcionTipos;
    private javax.swing.JPanel panelDocu;
    private javax.swing.JPanel panelDocumentos;
    private javax.swing.JPanel panelEntrada;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JPopupMenu popupArbol;
    private javax.swing.JPopupMenu popupAtributos;
    private javax.swing.JPopupMenu popupDocumentos;
    private javax.swing.JPopupMenu popupEditar;
    private javax.swing.JScrollPane scrollArbol;
    private javax.swing.JScrollPane scrollAtributos;
    private javax.swing.JScrollPane scrollFicheros;
    private javax.swing.JScrollPane scrollGrid;
    private javax.swing.JPopupMenu.Separator separador;
    private javax.swing.JTable tablaAtributos;
    private javax.swing.JTable tablaDocumentos;
    private javax.swing.JTextField textoCarpeta;
    private javax.swing.JTextField textoIdDocumentum;
    private javax.swing.JFormattedTextField textoNumRegistros;
    private javax.swing.JTextField textoRutaDocumentum;
    // End of variables declaration//GEN-END:variables

    private void cargarAtributos(String pr_object_id) {
        final String r_object_id = pr_object_id;
        String mensajeborrado = "";
        Color colormensaje = Color.BLACK;

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
            Object[][] datosatri = new Object[0][2];
            Object[] cabeceraatri = {"Nombre del Atributo", "Valor del Atributo"};
            modeloLotes = new DefaultTableModel(datosatri, cabeceraatri) {
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
            tablaAtributos.setModel(modeloLotes);
            etiquetaEstado.setText("");
            return;
        }

        atributos = utilDocum.dameTodosAtributos(r_object_id, gsesion);
        if (atributos.size() <= 0) {
            Object[][] datosatri = new Object[0][2];
            Object[] cabeceraatri = {"Nombre del Atributo", "Valor del Atributo"};
            modeloLotes = new DefaultTableModel(datosatri, cabeceraatri) {
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
            tablaAtributos.setModel(modeloLotes);
            if (!utilDocum.dameError().isEmpty()) {
                if (utilDocum.dameError().contains("Bad ID given: 0000000000000000")) {
                    etiquetaEstado.setText("No se encontró el ID de Documentum " + r_object_id);
                } else {
                    etiquetaEstado.setText(utilDocum.dameError());
                }
            } else {
                etiquetaEstado.setText("No se encontró el ID de Documentum " + r_object_id);
            }
            etiquetaEstado.validate();
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
            etiquetaEstado.setText("No se encontró el ID de Documentum " + r_object_id);
        }
        tablaAtributos.setModel(modeloLotes);
        TableColumn columna = tablaAtributos.getColumnModel().getColumn(0);
        columna.setPreferredWidth(250);
        columna.setMaxWidth(250);
        columna.sizeWidthToFit();
        tablaAtributos.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        if (!mensajeborrado.isEmpty()) {
            etiquetaEstado.setText(mensajeborrado);
            etiquetaEstado.setForeground(colormensaje);
            etiquetaEstado.validate();
        }
        pintarTablaAtributos();
        tablaAtributos.getTableHeader().setFont(tablaAtributos.getTableHeader().getFont().deriveFont(Font.BOLD, tablaDocumentos.getTableHeader().getFont().getSize2D()));
        //tablaAtributos.getTableHeader().setFont(tablaAtributos.getTableHeader().getFont().deriveFont(tablaAtributos.getTableHeader().getFont().getSize2D() + 1));
        tablaAtributos.getTableHeader().setForeground(new Color(0, 0, 153));
        System.gc();
    }

    private void pintarTablaAtributos() {
        tablaAtributos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                boolean oddRow = (row % 2 == 0);
                if (oddRow) {
                    setBackground(Color.WHITE);
                } else {
                    setBackground(new Color(245, 245, 245)); // gris claro
                }

                if (isSelected) {
                    setBackground(new Color(175, 205, 235)); // azul claro selección
                }
                return this;
            }
        });
    }

    private void cargarDocumentos(String pcarpeta, String ptipo) {
        final String carpeta = pcarpeta;
        final String tipo = ptipo;

        new Thread() {
            @Override
            public void run() {
                modeloLotes = (DefaultTableModel) tablaDocumentos.getModel();
                try {
                    modeloLotes.setRowCount(0);
                    modeloLotes.fireTableDataChanged();
                } catch (Exception ex) {
                }
                if (carpeta.isEmpty()) {
                    etiquetaEstado.setText("");
                    return;
                }
                barradocum = new PantallaBarra(PantallaDocumentum.this, false);
                barradocum.setTitle("Consultando en Documentum ...");
                barradocum.barra.setIndeterminate(true);
                barradocum.botonParar.setVisible(true);
                barradocum.setLabelMensa("");
                barradocum.barra.setOpaque(true);
                barradocum.barra.setStringPainted(false);
                barradocum.validate();
                barradocum.setVisible(true);
                barradocum.setDefaultCloseOperation(HIDE_ON_CLOSE);

                utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
                ArrayList<AtributosDocumentum> documentos = new ArrayList<AtributosDocumentum>();
                utilDocum.setVentanapadre(PantallaDocumentum.this);
                if (tipo.toLowerCase().equals("carpeta")) {
                    documentos = utilDocum.listarFicheros(carpeta);
                } else if (tipo.toLowerCase().equals("ruta")) {
                    int numreg = Integer.parseInt(textoNumRegistros.getText());
                    documentos = utilDocum.listarFicherosRuta(carpeta, numreg);
                } else if (tipo.toLowerCase().equals("id")) {
                    documentos = utilDocum.listarFicherosId(carpeta);
                }

                if (documentos.size() <= 0) {
                    Object[][] datos = new Object[0][6];
                    Object[] cabecera = {"Nombre ", "ID Documentum", "Tipo Documental", "Fecha Creación", "Usuario", "Check Out"};
                    modeloLotes = new DefaultTableModel(datos, cabecera) {
                        @Override
                        public boolean isCellEditable(int fila, int columna) {
                            return false;
                        }
                    };
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            tablaDocumentos.setModel(modeloLotes);
                        }
                    });
                    if (!utilDocum.dameError().isEmpty()) {
                        etiquetaEstado.setText(utilDocum.dameError());
                    } else {
                        etiquetaEstado.setText("No se encontraron Documentos en la carpeta " + carpeta);
                    }
                    barradocum.dispose();
                    return;
                }
                try {
                    Object[][] datos = new Object[documentos.size()][6];
                    Object[] cabecera = {"Nombre ", "ID Documentum", "Tipo Documental", "Fecha Creación", "Usuario", "Check Out"};
                    java.net.URL imgURL = PantallaDocumentum.class
                            .getClassLoader().getResource("es/documentum/imagenes/vacio.gif");
                    ImageIcon iconoCheckin = new ImageIcon(imgURL);
                    imgURL
                            = PantallaDocumentum.class
                                    .getClassLoader().getResource("es/documentum/imagenes/bloqueado.gif");
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
                modeloLotes.setColumnCount(6);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        tablaDocumentos.setModel(modeloLotes);
                    }
                });

                if (tipo.toLowerCase().equals("id")) {
                    etiquetaEstado.setText("Encontrado(s) " + documentos.size() + " documento(s) con id " + carpeta);
                } else {
                    etiquetaEstado.setText("Encontrado(s) " + documentos.size() + " documento(s) en la carpeta " + carpeta);
                }
                barradocum.dispose();
                if (tablaDocumentos.getModel().getRowCount() > 0) {
                    tablaDocumentos.setRowSelectionInterval(0, 0);
                }
                pintarTablaDocumentos();
                mostrarAtributos();
                if (primeraDocumentos) {
                    tablaDocumentos.getTableHeader().setFont(tablaDocumentos.getTableHeader().getFont().deriveFont(Font.BOLD, tablaDocumentos.getTableHeader().getFont().getSize2D() + 1));
                    primeraDocumentos = false;
                }
                tablaDocumentos.getTableHeader().setForeground(new Color(0, 0, 153));
//         tablaDocumentos.getTableHeader().setFont(tablaDocumentos.getTableHeader().getFont().deriveFont(tablaDocumentos.getTableHeader().getFont().getSize2D() + 1));
                //        tablaDocumentos.getTableHeader().setFont(tablaDocumentos.getTableHeader().getFont().deriveFont(tablaDocumentos.getTableHeader().getFont().getSize2D() + 1));
                TableColumn columna = tablaDocumentos.getColumnModel().getColumn(0);
                columna.setPreferredWidth(170);
                columna.setMinWidth(170);
                columna.sizeWidthToFit();
                if (tablaDocumentos.getColumnModel().getColumnCount() > 5) {
                    TableColumn columnaUsu = tablaDocumentos.getColumnModel().getColumn(4);
                    columnaUsu.setPreferredWidth(60);
                    columnaUsu.setMinWidth(60);
                    columnaUsu.sizeWidthToFit();
                    TableColumn columnaIco = tablaDocumentos.getColumnModel().getColumn(5);
                    columnaIco.setPreferredWidth(25);
                    columnaIco.setMinWidth(25);
                    columnaIco.sizeWidthToFit();
                }
            }
        }.start();
        System.gc();
    }

    private void pintarTablaDocumentos() {
        tablaDocumentos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                boolean oddRow = (row % 2 == 0);
                if (oddRow) {
                    setBackground(Color.WHITE);
                } else {
                    setBackground(new Color(245, 245, 245)); // gris claro
                }

                if (isSelected) {
                    setBackground(new Color(175, 205, 235)); // azul claro selección
                }
                return this;
            }
        });
    }

    private void mostrarAtributos() {
        if (tablaDocumentos.getModel().getRowCount() > 0) {
            cargarAtributos(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString());
        }
        utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
//        textoRutaDocumentum.setText(utilDocum.dameAtributo(tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString(), "r_folder_path").toString().replace("[", "").replace("]", ""));

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

//        imgURL
//                = PantallaDocumentum.class
//                        .getClassLoader().getResource("es/documentum/imagenes/seleccionado.png");
//        UIManager.put("CheckBoxMenuItem.checkIcon", new ImageIcon(imgURL));
//        imgURL
//                = PantallaDocumentum.class
//                        .getClassLoader().getResource("es/documentum/imagenes/seleccionado.png");
//        UIManager.put("CheckBoxMenuItem[Enabled+Selected].checkIcon", new ImageIcon(imgURL));
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

            if (evt.getSource().getClass().getName().equals("javax.swing.JTree")) {
                popupArbol.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }

    private void salir() {
        Utilidades.escribeLog("Salimos del programa DocumentumDFCs\n");
        this.dispose();
        System.exit(0);
    }

    private void inicializar() {
        asignarIconos();
        botonBuscar.setEnabled(false);
        opcionBuscar.setEnabled(false);
        botonConectar.setBackground(colornoconex);
        modeloLotes = new DefaultTableModel() {
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
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    tablaDocumentos.setModel(modeloLotes);
                }
            });
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
                    .getName() + " - " + ex.getMessage());
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
        MiProperties prop = util.leerPropeties(dirdfc + "dfc.properties");
        prop.setProperty("dfc.data.dir", dirdfc);
        prop.setProperty("dfc.security.keystore.file", dirdfc + "dfc.keystore");
        util.escribirProperties(dirdfc + "dfc.properties", prop);

        util.sacarArchivoJar("/es/documentum/manual/DocumentumDFCs.pdf", dirdfc + "manual" + util.separador() + "DocumentumDFCs.pdf");
        util.sacarArchivoJar("/es/documentum/lib/cryptojce.jar", dirdfc + "lib" + util.separador() + "cryptojce.jar");
        util.sacarArchivoJar("/es/documentum/lib/cryptojcommon.jar", dirdfc + "lib" + util.separador() + "cryptojcommon.jar");
        util.sacarArchivoJar("/es/documentum/lib/jcmFIPS.jar", dirdfc + "lib" + util.separador() + "jcmFIPS.jar");
        util.borrarFichero(dirdfc + "export", "*");

        try {
            ClassPathUpdater.add(dirdfc);
            ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "cryptojce.jar");
            ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "cryptojcommon.jar");
            ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "jcmFIPS.jar");
        } catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            Utilidades.escribeLog("Error al actualizar el Classpath  - Error: " + ex.getMessage());
        }

        opcionRBMetalActionPerformed(null);

        versiondfcs = utilDocum.dameVersionDFC();

        botonArribaDir.setEnabled(false);
        opcionDql.setVisible(false);
        opcionAPI.setVisible(false);
        Separador1.setVisible(false);
        opcionCrearCarpeta.setVisible(false);
        opcionExportarCarpeta.setVisible(false);
        opcionImportarADocumentum.setVisible(false);
        opcionPasswordLDAP.setVisible(false);
        Separador3.setVisible(false);
        opcionEstadisticasRepos.setVisible(false);
        opcionIndexador.setVisible(false);
        opcionJobs.setVisible(false);
        opcionStorage.setVisible(false);
        opcionTipos.setVisible(false);
        opcionRelations.setVisible(false);
        textoRutaDocumentum.setEnabled(false);
        textoCarpeta.setEnabled(false);
        textoIdDocumentum.setEnabled(false);
        opcionCripto.setVisible(false);
        opcionInfoRepo.setVisible(false);
        opcionSesiones.setVisible(false);
        opcionGrupos.setVisible(false);
        opcionEjecutarSql.setVisible(false);
        botonRefrescarArbol.setEnabled(false);
        arbolRepo.setRootVisible(false);
        if (checkArbolSiNo.isSelected()) {
            botonRefrescarArbol.setEnabled(false);
            divisorArbol.setDividerLocation(1);
        } else {
            botonRefrescarArbol.setEnabled(true);
            divisorArbol.setDividerLocation(200);
        }
    }

    private void conexionDocumentum() {
        PantallaConexion pantallaconectar = new PantallaConexion(this, true);
        etiquetaDocbroker.setText(PantallaConexion.getDocbroker());
        etiquetaRepositorio.setText(PantallaConexion.getRepositorio());
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
            conectado = false;
            etiquetaRepositorio.setBackground(null);
            etiquetaDocbroker.setText("");
            etiquetaRepositorio.setText("");
            opcionDql.setVisible(false);
            opcionAPI.setVisible(false);
            Separador1.setVisible(false);
            opcionCrearCarpeta.setVisible(false);
            opcionExportarCarpeta.setVisible(false);
            opcionImportarADocumentum.setVisible(false);
            opcionPasswordLDAP.setVisible(false);
            Separador3.setVisible(false);
            opcionEstadisticasRepos.setVisible(false);
            opcionIndexador.setVisible(false);
            opcionJobs.setVisible(false);
            opcionStorage.setVisible(false);
            opcionTipos.setVisible(false);
            opcionRelations.setVisible(false);
            textoRutaDocumentum.setEnabled(false);
            textoCarpeta.setEnabled(false);
            textoIdDocumentum.setEnabled(false);
            opcionInfoRepo.setVisible(false);
            opcionSesiones.setVisible(false);
            opcionGrupos.setVisible(false);
            opcionEjecutarSql.setVisible(false);
            botonRefrescarArbol.setEnabled(false);
            arbolRepo.setModel(null);
        } else {
            gsesion = pantallaconectar.getIdsesion();
            if (!utilDocum.dameFTIndex(pantallaconectar.getIdsesion()).isEmpty()) {
                opcionIndexador.setEnabled(true);
            } else {
                opcionIndexador.setEnabled(false);
            }
            new Thread() {
                @Override
                public void run() {
                    // Cerramos todas la ventanas que no sean la principal de la aplicacion 
                    // al realizar una nueva conexion
                    Window[] children = getWindows();
                    for (Window win : children) {
                        if (win instanceof JDialog || win instanceof JFrame) {
                            if (!win.getName().contains("PantallaDocumentum")) {
                                win.dispose();
                            }
                        }
                    }
                    barradocum = new PantallaBarra(PantallaDocumentum.this, false);
                    if (!checkArbolSiNo.isSelected()) {
                        barradocum.setTitle("Consultando Cabinet y Carpetas de " + repositorio);
                    } else {
                        barradocum.setTitle("Consultando el Repositorio " + repositorio);
                    }
                    barradocum.barra.setIndeterminate(true);
                    barradocum.botonParar.setVisible(false);
                    if (!checkArbolSiNo.isSelected()) {
                        barradocum.setLabelMensa("Recuperando información de Cabinet de " + repositorio);
                    } else {
                        barradocum.setLabelMensa("Recuperando información del repositorio " + repositorio);
                    }
                    barradocum.barra.setOpaque(true);
                    barradocum.barra.setStringPainted(false);
                    barradocum.validate();
                    barradocum.setVisible(true);
                    cargarAtributos("");
                    cargarDocumentos("", "");
                    arbolRepo.setModel(null);
                    conexionActiva();
                    barradocum.dispose();
                }
            }.start();
        }
        panelEstado.repaint();

    }

    private void conexionActiva() {
        if (checkArbolSiNo.isSelected()) {
            botonRefrescarArbol.setEnabled(false);
            divisorArbol.getLeftComponent().setMinimumSize(new Dimension());
            divisorArbol.setDividerLocation(0.0d);
        } else {
            botonRefrescarArbol.setEnabled(true);
            divisorArbol.setDividerLocation(200);
            cargarCabinetes();
            cargarArbol();
        }

        botonBuscar.setEnabled(true);
        opcionBuscar.setEnabled(true);
        botonConectar.setBackground(colorconex);
        conectado = true;
        etiquetaRepositorio.setOpaque(true);
        etiquetaRepositorio.setBackground(colorconex);
        opcionDql.setVisible(true);
        opcionAPI.setVisible(true);
        Separador1.setVisible(true);
        opcionCrearCarpeta.setVisible(true);
        opcionExportarCarpeta.setVisible(true);
        opcionImportarADocumentum.setVisible(true);
        // opcionPasswordLDAP.setVisible(true);
        Separador3.setVisible(true);
        if (repositorio.startsWith("P_A") || repositorio.startsWith("I_A") || repositorio.startsWith("D_A") || repositorio.startsWith("SAP")) {
            opcionEstadisticasRepos.setVisible(true);
        }
        opcionIndexador.setVisible(true);
        opcionJobs.setVisible(true);
        opcionStorage.setVisible(true);
        opcionTipos.setVisible(true);
        opcionRelations.setVisible(true);
        textoRutaDocumentum.setEnabled(true);
        textoCarpeta.setEnabled(true);
        textoIdDocumentum.setEnabled(true);
        utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
        esadmin = utilDocum.esUsuarioAdmin(usuario);
        idrepositorio = utilDocum.dameIdRepositorio();
        if (esadmin) {
            opcionActualizarAtributo.setEnabled(true);
            opcionBorrarObjetos.setVisible(true);
            opcionBorradoLogico.setEnabled(true);
            etiquetaDocbroker.setOpaque(true);
            etiquetaDocbroker.setBackground(coloradmin);
        } else {
            opcionActualizarAtributo.setEnabled(false);
            opcionBorrarObjetos.setVisible(false);
            opcionBorradoLogico.setEnabled(false);
            etiquetaDocbroker.setOpaque(false);
            etiquetaDocbroker.setBackground(null);
        }
        if (utilDocum.hayADTS()) {
            opcionConvertir.setEnabled(true);
        } else {
            opcionConvertir.setEnabled(false);
        }

        opcionInfoRepo.setVisible(true);
        opcionSesiones.setVisible(true);
        opcionGrupos.setVisible(true);
        if (utilDocum.dameNumeroVersionDocumentum() >= 7 && esadmin) {
            opcionEjecutarSql.setVisible(true);
        } else {
            opcionEjecutarSql.setVisible(false);
        }
    }

    public void actualizarTablaDocumentos(String ruta) {
        if (!ruta.isEmpty()) {
            if (!utilDocum.existeCarpeta(ruta)) {
                etiquetaEstado.setText("No existe la ruta " + ruta + " en Documentum");
                return;
            }
            cargarAtributos("");
            etiquetaEstado.setText("Buscando contenido de la ruta de Documentum " + ruta);
            panelEstado.revalidate();

            final String carpeta = ruta;

            new Thread() {
                @Override
                public void run() {
//                    DefaultTableModel modeloLotes = (DefaultTableModel) tablaDocumentos.getModel();
                    try {
                        modeloLotes.setRowCount(0);
                        modeloLotes.fireTableDataChanged();
                    } catch (Exception ex) {
                    }
                    if (carpeta.isEmpty()) {
                        etiquetaEstado.setText("");
                        return;
                    }

                    utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
                    ArrayList<AtributosDocumentum> documentos = new ArrayList<AtributosDocumentum>();
                    utilDocum.setVentanapadre(PantallaDocumentum.this);
                    int numreg = Integer.parseInt(textoNumRegistros.getText());
                    documentos = utilDocum.listarFicherosRuta(carpeta, numreg);

                    if (documentos.size() <= 0) {
                        Object[][] datos = new Object[0][7];
                        Object[] cabecera = {"Nombre ", "ID Documentum (r_object_id)", "Tipo Documental", "Fecha Creación", "Usuario", "Check Out"};
                        modeloLotes = new DefaultTableModel(datos, cabecera) {
                            @Override
                            public boolean isCellEditable(int fila, int columna) {
                                return false;
                            }
                        };
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                tablaDocumentos.setModel(modeloLotes);
                            }
                        });

                        if (!utilDocum.dameError().isEmpty()) {
                            etiquetaEstado.setText(utilDocum.dameError());
                        } else {
                            etiquetaEstado.setText("No se encontraron Documentos en la carpeta " + carpeta);
                        }
                        return;
                    }
                    try {
                        Object[][] datos = new Object[documentos.size()][7];
                        Object[] cabecera = {"Nombre ", "ID Documentum (r_object_id)", "Tipo Documental", "Fecha Creación", "Usuario", "Check Out"};
                        java.net.URL imgURL = PantallaDocumentum.class
                                .getClassLoader().getResource("es/documentum/imagenes/vacio.gif");
                        ImageIcon iconoCheckin = new ImageIcon(imgURL);
                        imgURL
                                = PantallaDocumentum.class
                                        .getClassLoader().getResource("es/documentum/imagenes/bloqueado.gif");
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
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            tablaDocumentos.setModel(modeloLotes);
                        }
                    });
                    TableColumn columna = tablaDocumentos.getColumnModel().getColumn(0);
                    columna.setPreferredWidth(125);
                    columna.setMinWidth(125);
                    columna.sizeWidthToFit();

                    tablaDocumentos.doLayout();
                    etiquetaEstado.setText("Encontrado(s) " + documentos.size() + " documento(s) en la carpeta " + carpeta);
                    tablaDocumentos.setRowSelectionInterval(0, 0);
                    mostrarAtributos();
                }
            }.start();
            System.gc();

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
    }

    public void buscarEnDocumentum() {
        if (!textoIdDocumentum.getText().isEmpty()) {
            if (textoIdDocumentum.getText().length() == 16) {
                etiquetaEstado.setText("Buscando atributos en Documentum para el ID de Documentum " + textoIdDocumentum.getText());
                panelEstado.revalidate();
                cargarAtributos(textoIdDocumentum.getText());
                utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
                cargarDocumentos(textoIdDocumentum.getText(), "id");
                textoRutaDocumentum.setText(utilDocum.dameAtributo(textoIdDocumentum.getText(), "r_folder_path").toString().replace("[", "").replace("]", ""));
                textoCarpeta.setText("");
            } else {
                etiquetaEstado.setText("El ID de Documentum (r_object_id) tiene que tener 16 caracteres.");
            }
        } else if (!textoCarpeta.getText().isEmpty()) {
            cargarAtributos("");
            textoRutaDocumentum.setText("");
            etiquetaEstado.setText("Buscando contenido de la carpeta de Documentum " + textoCarpeta.getText());
            panelEstado.revalidate();
            utilDocum.setVentanapadre(this);
            cargarDocumentos(textoCarpeta.getText(), "carpeta");
        } else if (!textoRutaDocumentum.getText().isEmpty()) {
            String rutaDocum = textoRutaDocumentum.getText();
            while (rutaDocum.substring(rutaDocum.length() - 1).equals("/") && !rutaDocum.equals("/")) {
                textoRutaDocumentum.setText(rutaDocum.substring(0, rutaDocum.length() - 1));
                rutaDocum = textoRutaDocumentum.getText();
            }

            if (!checkArbolSiNo.isSelected()) {
                irANodo(rutaDocum);
            }
            if (rutaDocum.equals("/")) {
                textoRutaDocumentum.setText("/");
            }

            if (!utilDocum.existeCarpeta(rutaDocum)) {
                etiquetaEstado.setText("No existe la ruta " + rutaDocum + " en Documentum");
                return;
            }
            cargarAtributos("");
            //  textoRutaDocumentum.setText("");
            etiquetaEstado.setText("Buscando contenido de la ruta de Documentum " + textoCarpeta.getText());
            panelEstado.revalidate();
            cargarDocumentos(rutaDocum, "ruta");
            String ruta = rutaDocum;
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
        etiquetaEstado.validate();
        if (barradocum.isActive()) {
            barradocum.dispose();
        }
    }

    private void exportarDeDocumentum() {
        if (tablaDocumentos.getModel().getRowCount() > 0) {
            String nombre = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
            if (!nombre.startsWith("09") && utilDocum.dameAtributo(nombre, "a_storage_type").isEmpty()) {
                etiquetaEstado.setText("Sólo aplicable a Documentos");
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
                String resultado = utilDocum.guardarFichero(nombre, directorio);
                etiquetaEstado.setText(resultado);
            } else {
                Utilidades.escribeLog("No se ha seleccionado directorio de salida ");
            }
        }
    }

    private void abrirFicheroDeDocumentum() {
        if (tablaDocumentos.getModel().getRowCount() > 0) {
            String nombre = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
            if (!nombre.startsWith("09")) { //&& utilDocum.dameAtributo(nombre, "a_storage_type").isEmpty()) {
                etiquetaEstado.setText("Sólo aplicable a Documentos");
                return;
            }
            String directorio = dirdfc + "export";
            utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
            String resultado = utilDocum.guardarFichero(nombre, directorio);
            etiquetaEstado.setText(resultado);
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

    private void actualizarAtributo() {
        utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
        // actualizarAtributo(int tipo, String r_object_id, String nombre, String valor)
        String nombre = tablaAtributos.getModel().getValueAt(tablaAtributos.convertRowIndexToModel(tablaAtributos.getSelectedRow()), 0).toString();
        /*       
        if (!nombre.equals("a_content_type") && !nombre.equals("subject") && !nombre.equals("title") && !nombre.equals("acl_name")
                && !nombre.equals("object_name") && !nombre.startsWith("map_atr")) {
            etiquetaEstado.setText("Atributo " + nombre + " no modificable");
            return;
        }
         */
        String valor;
        if (tablaAtributos.getModel().getValueAt(tablaAtributos.convertRowIndexToModel(tablaAtributos.getSelectedRow()), 1) == null) {
            valor = null;
        } else {
            valor = tablaAtributos.getModel().getValueAt(tablaAtributos.convertRowIndexToModel(tablaAtributos.getSelectedRow()), 1).toString();
        }

        if (tablaDocumentos.getSelectedRow() < 0) {
            etiquetaEstado.setForeground(Color.BLUE);
            etiquetaEstado.setText("Debe tener seleccionado el Documento a modificar");
            return;
        }

        String r_object_id = tablaDocumentos.getModel().getValueAt(tablaAtributos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
        int tipo = buscarTipo(nombre);
        int pos = -1;
        if (nombre.contains("[") && nombre.contains("]")) {
            pos = Integer.parseInt(nombre.substring(nombre.indexOf("[") + 1, nombre.indexOf("]")));
            nombre = nombre.substring(0, nombre.indexOf("["));
        }
        String resultado = utilDocum.actualizarAtributo(tipo, r_object_id, nombre, valor, pos);
        etiquetaEstado.setText(resultado);
        if (!resultado.isEmpty()) {
        }
    }

    private void borrarDocumento() {
        utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
        String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();

        if (!r_object_id.startsWith("09")) {
            etiquetaEstado.setText("Solo aplicable a Documentos");
            return;
        }
        utilDocum.borrarDocumento(r_object_id);
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

    private void borrarDocumento(String r_object_id) {
        this.utilDocum = new UtilidadesDocumentum(this.dirdfc + "dfc.properties");
        if (!r_object_id.startsWith("09")) {
            this.etiquetaEstado.setText("Solo aplicable a Documentos");
            return;
        }
        this.utilDocum.borrarDocumento(r_object_id);
    }

    private void borradoLogicoDocumento() {
        utilDocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
        String r_object_id = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString();
        if (!r_object_id.startsWith("09")) {
            etiquetaEstado.setText("Sólo aplicable a Documentos");
            return;
        }
        String nombre = "map_atr_borrado_logico";
        int tipo = buscarTipo(nombre);
        String resultado = utilDocum.actualizarAtributo(tipo, r_object_id, nombre, "1", -1);
        if (utilDocum.dameError().isEmpty()) {
            if (tablaDocumentos.getSelectedRow() >= 0) {
                cargarAtributos(r_object_id);
            } else {
                cargarAtributos("");
            }
        }
    }

    private int buscarTipo(String atributo) {
        int valor = 0;

        for (int n = 0; n < atributos.size(); n++) {
            if (atributos.get(n).getNombre().equals(atributo)) {
                valor = atributos.get(n).getTipo();
            }
        }

        return valor;
    }

    private void exportarDocumentosExcel() {
        if (tablaDocumentos.getModel().getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Seleccionar directorio y nombre de fichero");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String fichero = chooser.getSelectedFile().toString();
                if (!fichero.toLowerCase().endsWith(".xlsx")) {
                    fichero = fichero + ".xlsx";
                }
                String titulo_excel = "Repositorio " + repositorio;
                util.exportarAExcel(tablaDocumentos, fichero, titulo_excel);
                //util.exportaExcel(tablaDocumentos, fichero);
            } else {
                Utilidades.escribeLog("No se ha seleccionado el fichero de salida ");
            }
        }
        System.gc();
    }

    private void exportarAtributosExcel() {
        if (tablaAtributos.getModel().getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Seleccionar directorio y nombre de fichero");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String fichero = chooser.getSelectedFile().toString();
                if (!fichero.toLowerCase().endsWith(".xlsx")) {
                    fichero = fichero + ".xlsx";
                }
                //   util.exportaExcel(tablaAtributos, fichero);
                String nom = tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString().isEmpty() ? tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 1).toString() : tablaDocumentos.getModel().getValueAt(tablaDocumentos.convertRowIndexToModel(tablaDocumentos.getSelectedRow()), 0).toString();
                String titulo_excel = "Atributos de " + nom;
                util.exportarAExcel(tablaAtributos, fichero, titulo_excel);
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
        String dirdfcproperties = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();

        try {
            ClassPathUpdater.add(dirdfcproperties);
            ClassPathUpdater.add(dirdfcproperties + "lib" + util.separador() + "jcmFIPS.jar");
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            Utilidades.escribeLog("Error al exportarCarpeta - Error: " + ex.getMessage());
        }

        IDfSession lsesion = utilDocum.conectarDocumentum();

        IDfFolder carpeta = null;
        try {
            carpeta = lsesion.getFolderByPath(rutaRepositorio);
            if (lsesion.isConnected()) {
                lsesion.disconnect();
            }
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
            @Override
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
                IDfSession lsesion = null;
                final StringBuilder bufAbsPath = new StringBuilder(32);
                try {
                    // Create the destination folder on the filesystem. This routine will create it 
                    // even if it is nested a few levels down.
                    utilDocum.createFolderPath(ruta);
                    String rutaSistemaFicheros = ruta;
                    Utilidades util = new Utilidades();
                    String dirdfc = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();

                    try {
                        ClassPathUpdater.add(dirdfc);
                        ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "jcmFIPS.jar");
                    } catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                        Utilidades.escribeLog("Error al actualizar el Classpath  - Error: " + ex.getMessage());
                    }

                    lsesion = utilDocum.conectarDocumentum();
                    // This is a deep export, so get all of the folders in the specified location
                    // and then export the contents of each folder 
                    String qualification = "select r_object_id,object_name from dm_folder where FOLDER(ID('"
                            + idCarpeta + "'), DESCEND)";
                    IDfCollection colDQL = utilDocum.execQuery(lsesion, qualification);

                    IDfFolder myFolder = (IDfFolder) lsesion.getObject(idCarpeta);

                    bufAbsPath.append(rutaSistemaFicheros).append(util.separador()).append(myFolder.getObjectName());
                    rutaSistemaFicheros = bufAbsPath.toString();
                    utilDocum.doExport(lsesion, myFolder, rutaSistemaFicheros, barradocum);

                    String docbaseRootFolderPath = myFolder.getFolderPath(0);

                    while (colDQL.next() && barradocum.PARAR == false) {
                        // Get the r_object_id of each folder
                        barradocum.validate();
                        IDfAttr attr = colDQL.getAttr(0);
                        String objectId = colDQL.getString(attr.getName());
                        // Get the docbase folder object
                        myFolder = (IDfFolder) lsesion.getObject(new DfId(objectId));
                        // Build the absolute filesystem folder path be appending the docbase path to the 
                        // file system root.
                        StringBuilder bufAbsFolderPath = new StringBuilder(32);
                        bufAbsFolderPath.append(rutaSistemaFicheros);

                        String path0 = null;
                        for (int ctrPaths = 0; ctrPaths < myFolder.getFolderPathCount(); ctrPaths++) {
                            String tmpPath = myFolder.getFolderPath(ctrPaths);
                            if (tmpPath.startsWith(docbaseRootFolderPath)) {
                                path0 = tmpPath;
                                break;
                            }
                        }
                        if (path0 != null) {
                            String subFolderPath = myFolder.getFolderPath(0).substring(docbaseRootFolderPath.length() + 1, path0.length());
                            bufAbsFolderPath.append(util.separador());
                            bufAbsFolderPath.append(subFolderPath.replace("/", util.separador()));
                            String absFolderPath = bufAbsFolderPath.toString();
                            barradocum.setLabelMensa(absFolderPath);
                            // Export this docbase folder using the Export Operation
                            utilDocum.doVirtualDocExport(lsesion, myFolder, absFolderPath);
                            utilDocum.doExport(lsesion, myFolder, absFolderPath, barradocum);
                        }
                    }
                    colDQL.close();
                    if (lsesion.isConnected()) {
                        lsesion.disconnect();
                    }
                } catch (DfException ex) {

                } finally {
                    barradocum.setVisible(false);
                    barradocum.dispose();
                    Utilidades.escribeLog("Fin de la exportación de datos de la carpeta " + carpeta);

                    etiquetaEstado.setText("Finalizada la exportación de datos de la carpeta " + carpeta);
                    System.gc();
                }
            }
        }.start();
//        barradocum.setVisible(false);
//        barradocum.dispose();
    }

    private void limpiarPantalla() {
        try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    Object[][] datos = new Object[0][6];
                    Object[] cabecera = {"Nombre ", "ID Documentum", "Tipo Documental", "Fecha Creación", "Usuario", "Check Out"};
                    modeloLotes = new DefaultTableModel(datos, cabecera) {
                        @Override
                        public boolean isCellEditable(int fila, int columna) {
                            return false;
                        }
                    };
                    tablaDocumentos.setModel(modeloLotes);
                    Object[][] datosatri = new Object[0][2];
                    Object[] cabeceraatri = {"Nombre del Atributo", "Valor del Atributo"};
                    modeloLotes = new DefaultTableModel(datosatri, cabeceraatri) {
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
                    tablaAtributos.setModel(modeloLotes);
                }
            });

        } catch (Exception ex) {
        }
        textoIdDocumentum.setText("");
        textoCarpeta.setText("");
        textoRutaDocumentum.setText("");
        etiquetaEstado.setText("");
    }

}

class CabinetConCarpeta implements Serializable {

    private String nombrecabinet;
    private Boolean tienecapetas;

    public String getNombrecabinet() {
        return nombrecabinet;
    }

    public void setNombrecabinet(String nombrecabinet) {
        this.nombrecabinet = nombrecabinet;
    }

    public Boolean getTienecapetas() {
        return tienecapetas;
    }

    public void setTienecapetas(Boolean tienecapetas) {
        this.tienecapetas = tienecapetas;
    }

    public CabinetConCarpeta() {
    }

}
