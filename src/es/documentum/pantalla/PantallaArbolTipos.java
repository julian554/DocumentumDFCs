package es.documentum.pantalla;

import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;
import es.documentum.utilidades.ClassPathUpdater;
import es.documentum.utilidades.TablaSinEditarCol;
import es.documentum.utilidades.Utilidades;
import es.documentum.utilidades.UtilidadesDocumentum;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class PantallaArbolTipos extends javax.swing.JFrame {

    public static PantallaDocumentum ventanapadre = null;
    Utilidades util = new Utilidades();
    UtilidadesDocumentum utildocum = new UtilidadesDocumentum();
    IDfSession gsesion = sesionDocumentum();
    private static final String NEWLINE = "\r\n";
    DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("dm_sysobject");
    DefaultTreeModel modelo = new DefaultTreeModel(raiz);
    Boolean mistipos = false;
    String tiposPadre = "";
    Boolean botonderecho = false;
    ArrayList busqueda = new ArrayList();

    public PantallaArbolTipos(PantallaDocumentum parent, boolean modal) {
        initComponents();
        try {
            setIconImage(new ImageIcon(getLogo()).getImage());
        } catch (NullPointerException e) {
            Utilidades.escribeLog("\nError cargando el Logo " + e.getMessage() + "\n");
        }
        cargarArbol();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MenuOpciones = new javax.swing.JPopupMenu();
        OpcionAtributos = new javax.swing.JMenuItem();
        OpcionListado = new javax.swing.JMenuItem();
        ScrollArbol = new javax.swing.JScrollPane();
        arbolTipos = new javax.swing.JTree();
        BotonCerrar = new javax.swing.JButton();
        CheckTiposPropios = new javax.swing.JCheckBox();
        TextoTipo = new javax.swing.JTextField();
        BotonBuscar = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaInfo = new javax.swing.JTable();
        BotonBuscarSiguiente = new javax.swing.JToggleButton();
        MenuPrincipal = new javax.swing.JMenuBar();
        opcionOpciones = new javax.swing.JMenu();
        opcionCerrar = new javax.swing.JMenuItem();

        OpcionAtributos.setText("Ver Atributos");
        OpcionAtributos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpcionAtributosActionPerformed(evt);
            }
        });
        MenuOpciones.add(OpcionAtributos);

        OpcionListado.setText("Exportar Arbol a XML");
        OpcionListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpcionListadoActionPerformed(evt);
            }
        });
        MenuOpciones.add(OpcionListado);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tipos Documentales");
        setMinimumSize(new java.awt.Dimension(909, 651));

        arbolTipos.setAutoscrolls(true);
        arbolTipos.setRowHeight(18);
        arbolTipos.setShowsRootHandles(true);
        arbolTipos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                arbolTiposMousePressed(evt);
            }
        });
        arbolTipos.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                arbolTiposValueChanged(evt);
            }
        });
        ScrollArbol.setViewportView(arbolTipos);

        BotonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        BotonCerrar.setText("Cerrar");
        BotonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCerrarActionPerformed(evt);
            }
        });

        CheckTiposPropios.setText("Tipos Documentales propios");
        CheckTiposPropios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckTiposPropiosActionPerformed(evt);
            }
        });

        BotonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/buscar_peq.png"))); // NOI18N
        BotonBuscar.setText("Buscar");
        BotonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBuscarActionPerformed(evt);
            }
        });

        tablaInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Propiedad", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaInfo.setShowHorizontalLines(false);
        tablaInfo.setShowVerticalLines(false);
        jScrollPane1.setViewportView(tablaInfo);

        BotonBuscarSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/siguiente.png"))); // NOI18N
        BotonBuscarSiguiente.setText("Buscar Siguiente");
        BotonBuscarSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBuscarSiguienteActionPerformed(evt);
            }
        });

        opcionOpciones.setMnemonic('O');
        opcionOpciones.setText("Opciones");
        opcionOpciones.setName("opcionOpciones"); // NOI18N

        opcionCerrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        opcionCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        opcionCerrar.setText("Cerrar");
        opcionCerrar.setToolTipText("Cerrar la Aplicación");
        opcionCerrar.setActionCommand("");
        opcionCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCerrarActionPerformed(evt);
            }
        });
        opcionOpciones.add(opcionCerrar);

        MenuPrincipal.add(opcionOpciones);

        setJMenuBar(MenuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(TextoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotonBuscarSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(CheckTiposPropios, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BotonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ScrollArbol, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollArbol, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonCerrar)
                    .addComponent(CheckTiposPropios)
                    .addComponent(TextoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonBuscar)
                    .addComponent(BotonBuscarSiguiente))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_BotonCerrarActionPerformed

    private void arbolTiposMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arbolTiposMousePressed
        if (!arbolTipos.isSelectionEmpty()) {
            if (evt.getButton() == MouseEvent.BUTTON3) {
                botonderecho = true;
                popupmenu(evt);
            } else {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) arbolTipos.getLastSelectedPathComponent();
                if (node == null) // Nothing is selected.
                {
                    return;
                }

                Object nodeInfo = node.getUserObject();
                String nombre = nodeInfo.toString();

                if (esTipoPadre(nombre)) {
                    //            popupDirectorio.show((Component) evt.getSource(), evt.getX(), evt.getY());
                } else {
                    //           popupFichero.show((Component) evt.getSource(), evt.getX(), evt.getY());
                }
                System.out.println(nombre + " --> " + (esTipoPadre(nombre) ? "Nodo padre" : "Hijo"));
                CargarInfoNodo(nombre);
            }
        }
    }//GEN-LAST:event_arbolTiposMousePressed

    private void popupmenu(MouseEvent evt) {
        if (evt.isPopupTrigger() || botonderecho) {
            MenuOpciones.show(evt.getComponent(), evt.getX(), evt.getY());
            OpcionAtributos.setEnabled(true);
            botonderecho = false;
        }

    }

    private void opcionCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_opcionCerrarActionPerformed

    private void CheckTiposPropiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckTiposPropiosActionPerformed
        if (CheckTiposPropios.isSelected()) {
            mistipos = true;
        } else {
            mistipos = false;
        }
        cargarArbol();

    }//GEN-LAST:event_CheckTiposPropiosActionPerformed

    private void BotonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBuscarActionPerformed
        busqueda.clear();
        TreePath rutanodo = buscarNodo(raiz, TextoTipo.getText());
        arbolTipos.setSelectionPath(rutanodo);
        arbolTipos.scrollPathToVisible(rutanodo);
        BotonBuscarSiguiente.setVisible(true);
    }//GEN-LAST:event_BotonBuscarActionPerformed

    private void OpcionAtributosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpcionAtributosActionPerformed
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) arbolTipos.getLastSelectedPathComponent();
        if (node != null) // Nothing is selected.
        {
            PantallaTipoAtributos pantallatipoatributos = new PantallaTipoAtributos(this, true);
            String id = "";
            Object nodeInfo = node.getUserObject();
            String nombre = nodeInfo.toString();
            id = utildocum.DameRobjectidDeTipo(nombre, gsesion);

            pantallatipoatributos.setTitle("Relations de Documentos - r_object_id: " + id);
            pantallatipoatributos.setR_object_id(id);
            pantallatipoatributos.CargarTablas(id);

            pantallatipoatributos.setVisible(true);
        }
    }//GEN-LAST:event_OpcionAtributosActionPerformed

    private void OpcionListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpcionListadoActionPerformed
        // recorrerArbol();

        TreeModel modelo = arbolTipos.getModel();
        try {
            String res = util.ArboltoXml(modelo);
            //    System.out.println(res);
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Seleccionar directorio y nombre de fichero");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String fichero = chooser.getSelectedFile().toString();
                if (!fichero.toLowerCase().endsWith(".xml")) {
                    fichero = fichero + ".xml";
                }
                util.borrarFichero(fichero);
                util.crearFichero(fichero, "xml");
                util.escribeFichero(fichero, res);
            }
        } catch (Exception ex) {

        }

    }//GEN-LAST:event_OpcionListadoActionPerformed

    private void BotonBuscarSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBuscarSiguienteActionPerformed
        TreePath rutanodo = buscarNodoSiguiente(TextoTipo.getText());
        if (rutanodo == null) {
            busqueda.clear();
            rutanodo = buscarNodo(raiz, TextoTipo.getText());
        }
        arbolTipos.setSelectionPath(rutanodo);
        arbolTipos.scrollPathToVisible(rutanodo);
    }//GEN-LAST:event_BotonBuscarSiguienteActionPerformed

    private void arbolTiposValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_arbolTiposValueChanged
        if (!arbolTipos.isSelectionEmpty()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) arbolTipos.getLastSelectedPathComponent();
            if (node == null) // Nothing is selected.
            {
                return;
            }

            Object nodeInfo = node.getUserObject();
            String nombre = nodeInfo.toString();

            if (esTipoPadre(nombre)) {
                //            popupDirectorio.show((Component) evt.getSource(), evt.getX(), evt.getY());
            } else {
                //           popupFichero.show((Component) evt.getSource(), evt.getX(), evt.getY());
            }
            System.out.println(nombre + " --> " + (esTipoPadre(nombre) ? "Nodo padre" : "Hijo"));
            CargarInfoNodo(nombre);
        }

    }//GEN-LAST:event_arbolTiposValueChanged

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
            java.util.logging.Logger.getLogger(PantallaArbolTipos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaArbolTipos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaArbolTipos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaArbolTipos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaArbolTipos(ventanapadre, true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton BotonBuscar;
    private javax.swing.JToggleButton BotonBuscarSiguiente;
    private javax.swing.JButton BotonCerrar;
    private javax.swing.JCheckBox CheckTiposPropios;
    private javax.swing.JPopupMenu MenuOpciones;
    private javax.swing.JMenuBar MenuPrincipal;
    private javax.swing.JMenuItem OpcionAtributos;
    private javax.swing.JMenuItem OpcionListado;
    private javax.swing.JScrollPane ScrollArbol;
    private javax.swing.JTextField TextoTipo;
    private javax.swing.JTree arbolTipos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem opcionCerrar;
    private javax.swing.JMenu opcionOpciones;
    private javax.swing.JTable tablaInfo;
    // End of variables declaration//GEN-END:variables

    public Boolean esTipoPadre(String tipo) {
        String dql = "select name from dm_type where super_name ='" + tipo + "'";
        try {
            String resultado = execQuery(dql, gsesion);
            return !resultado.isEmpty();
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al consultar esTipoPadre - Tipo: " + tipo + " - Error: " + ex.getMessage());
        }
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
            Utilidades.escribeLog("execQuery.error al ejecutar DQL: " + ex.getMessage() + NEWLINE);
        }
        try {
            col.close();
        } catch (DfException e) {
            Utilidades.escribeLog("execQuery.error General: " + e.getMessage() + NEWLINE);
        }
        return retVal;
    }

    private void cargarArbol() {
        BotonBuscarSiguiente.setVisible(false);
        arbolTipos.setModel(null);
        raiz.removeAllChildren();
        arbolTipos.setCellRenderer(new RendererArbol());
        arbolTipos.setModel(modelo);
        DameTipos("dm_sysobject", raiz);
        DefaultTreeCellRenderer render = (DefaultTreeCellRenderer) arbolTipos.getCellRenderer();
        if (mistipos) {
            expandOrCollapsToLevel(arbolTipos, new TreePath(((DefaultMutableTreeNode) arbolTipos.getModel().getRoot())), 2, true);
        } else {
            expandOrCollapsToLevel(arbolTipos, new TreePath(((DefaultMutableTreeNode) arbolTipos.getModel().getRoot())), 1, true);
        }
    }

    private void DameTipos(String tipo, DefaultMutableTreeNode nodo) {
        String dql = "select name,super_name from dm_type where super_name='" + tipo + "' and super_name<>' ' order by 2,1";
        IDfCollection col = null;
        List<String> tipos = new ArrayList<String>();
        try {
            IDfQuery q = new DfQuery();
            q.setDQL(dql);
            col = q.execute(gsesion, DfQuery.EXEC_QUERY);
            String padre = tipo;
            while (col.next()) {
                //     System.out.print(padre + " --> ");
                IDfTypedObject r = col.getTypedObject();
                String hijo = r.getValueAt(0).asString();
                if (mistipos && esTipoGeneral(hijo)) {
                    continue;
                }
                DefaultMutableTreeNode nodohijo = new DefaultMutableTreeNode(hijo);
                nodo.add(nodohijo);

                if (esTipoPadre(hijo)) {
                    //     System.out.println(hijo);
                    DameTipos(hijo, nodohijo);
                } else {
                    //     System.out.println(hijo);
                }
            }
//            System.out.println();
        } catch (DfException ex) {
            Utilidades.escribeLog("DameTipos. Error al ejecutar DQL: " + ex.getMessage() + NEWLINE);
        }

    }

    private Boolean esTipoGeneral(String tipo) {
        Boolean resultado = false;
        switch (tipo.substring(0, 3)) {
            case "dm_":
            case "dmc":
            case "dmi":
            case "web":
            case "cts":
            case "dmr":
            case "dam":
            case "scs":
            case "xdm":
            case "xcp":
            case "bam":
            case "bpm":
                resultado = true;
        }

        if (tipo.equalsIgnoreCase("dm_document") || tipo.equalsIgnoreCase("dm_sysobject")) {
            resultado = false;
        }

        return resultado;
    }

    private void CargarInfoNodo(String nombre) {
        String r_object_id = utildocum.DameRobjectidDeTipo(nombre, gsesion);
        String super_tipo = utildocum.DameSuperTipo(nombre, gsesion);
        tiposPadre = "";
        //  tiposPadre = DamePadres(nombre);
        tiposPadre = utildocum.DameTiposPadre(nombre, gsesion);
        String filestore = utildocum.DameFilestoreDeTipo(nombre, gsesion);
        ArrayList<String> hijos = utildocum.DameTiposHijos(nombre, gsesion);
        int numhijos = hijos.size();
        int cont = 4 + numhijos;
        TablaSinEditarCol modeloLotes = new TablaSinEditarCol();

        String[] cabecera = {"Propiedad", "Valor"};
        String[][] datos = new String[cont][2];
        datos[0][0] = "Nombre";
        datos[0][1] = nombre;
        datos[1][0] = "r_object_id";
        datos[1][1] = r_object_id;
        datos[2][0] = "Super Tipo";
        datos[2][1] = tiposPadre;
        datos[3][0] = "Filestore";
        datos[3][1] = filestore;

        for (int i = 0; i < hijos.size(); i++) {
            datos[i + 4][0] = "Tipo hijo";
            datos[i + 4][1] = hijos.get(i);
        }

        modeloLotes = new TablaSinEditarCol(datos, cabecera);
        tablaInfo.setModel(modeloLotes);
        TableColumn columna = tablaInfo.getColumnModel().getColumn(0);
        columna.setPreferredWidth(150);
        columna.setMaxWidth(150);
        columna.sizeWidthToFit();
        pintarTabla();

    }

    private void pintarTabla() {
        tablaInfo.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
//                boolean oddRow = (row % 2 == 0);
//                if (oddRow) {
//                    setBackground(new Color(245, 245, 245)); // gris claro
//                } else {
//                    setBackground(Color.WHITE);
//                }
                setOpaque(true);
                setForeground(Color.BLACK);
                setBackground(new Color(245, 245, 245)); // gris claro
                setBackground(BotonBuscar.getBackground());
                String nombre = (String) table.getValueAt(row, 0);
                if (nombre.equals("Super Tipo")) {
                    //   setOpaque(true);
                    setForeground(Color.BLUE);
                }
                if (nombre.equals("Nombre")) {
                    setFont(getFont().deriveFont(Font.BOLD));
                }

                return this;
            }
        });
    }


    /* 
    private String DamePadres(String nombre) {
        String supertipo = "";
        try {
            String dql = "Select super_name from dm_type where name='" + nombre + "'";
            IDfCollection col = utildocum.ejecutarDql(dql, gsesion);
            if (col != null) {
                while (col.next()) {
                    IDfTypedObject r = col.getTypedObject();
                    supertipo = r.getValueAt(0).asString();
                }
            }

            if (!supertipo.isEmpty()) {
                if (tiposPadre.isEmpty()) {
                    tiposPadre = supertipo;
                } else {
                    tiposPadre = tiposPadre + "," + supertipo;
                }
                if (esPadre(supertipo)) {
                    DamePadres(supertipo);
                }
            }

        } catch (DfException ex) {
        }
        return tiposPadre;
    }
     */
    public void expandOrCollapsToLevel(JTree tree, TreePath treePath, int level, boolean expand) {
        try {
            expandOrCollapsePath(tree, treePath, level, 0, expand);
        } catch (Exception e) {
            // e.printStackTrace();

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
        ImageIcon documento;

        public RendererArbol() {
            carpeta = new ImageIcon(PantallaArbolTipos.class.getClassLoader().getResource("es/documentum/imagenes/carpeta_azul.png"));
            carpeta_abierta = new ImageIcon(PantallaArbolTipos.class.getClassLoader().getResource("es/documentum/imagenes/carpeta_azul_abierta.png"));
            documento = new ImageIcon(PantallaArbolTipos.class.getClassLoader().getResource("es/documentum/imagenes/texto.png"));
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            String nombre = value.toString();
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
            String dir = infoNodo(nodo, "tamanio");

            if (expanded) {
                setIcon(carpeta_abierta);
            } else if (leaf && row > 0) {
                String extension = value.toString().substring(nombre.lastIndexOf(".") > 0 ? nombre.lastIndexOf(".") + 1 : nombre.length(), nombre.length()).toLowerCase();
                //     System.out.println(extension);
                if (dir.equals("dir")) {
                    setIcon(carpeta);
                } else {
                    setIcon(documento);
                }

            } else {
                setIcon(carpeta);
            }
            return this;
        }

    }

    private TreePath buscarNodo(DefaultMutableTreeNode root, String s) {
        @SuppressWarnings("unchecked")
        Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
        // Busca nombre esacto
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            System.out.println(node.toString());
            if (node.toString().equalsIgnoreCase(s) && !util.buscarEnLista(busqueda, node.toString())) {
                busqueda.add(node.toString());
                return new TreePath(node.getPath());
            }
        }
        // Si no encuentra nombre busca que empiece por
        e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            System.out.println(node.toString());
            if (node.toString().toLowerCase().startsWith(s.toLowerCase()) && !util.buscarEnLista(busqueda, node.toString())) {
                busqueda.add(node.toString());
                return new TreePath(node.getPath());
            }
        }
        return null;
    }

    private TreePath buscarNodoSiguiente(String s) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) arbolTipos.getLastSelectedPathComponent();

        if (node != null) {
            return buscarNodo(raiz, s);
        } else {
            busqueda.clear();
            return buscarNodo(raiz, s);
        }
    }

    public String infoNodo(DefaultMutableTreeNode nodo, String dato) {
        String informacion = "";
        if (nodo == null) {
            return informacion;
        }
        Object nodeInfo = nodo.getUserObject();
        String nombrefichero = nodeInfo.toString();
        String rutafichero = nodeInfo.toString();

        DefaultMutableTreeNode nodopadre = nodo;
        while (!nodopadre.isRoot()) {
            nodopadre = (DefaultMutableTreeNode) nodopadre.getParent();
            nodeInfo = nodopadre.getUserObject();
            if (nodeInfo.toString().equals("/")) {
                rutafichero = nodeInfo.toString() + rutafichero;
            } else {
                rutafichero = nodeInfo.toString() + "/" + rutafichero;
            }
        }
        return informacion;
    }

    public void actualizarNodo(DefaultMutableTreeNode nodo, Boolean mensaje) {
        String rutanodo = "";
        Object[] paths = nodo.getPath();
        for (int i = 0; i < paths.length; i++) {
            rutanodo += paths[i];
            if (i + 1 < paths.length) {
                if (!rutanodo.equals("/")) {
                    rutanodo += "/";
                }
            }
        }
        if (mensaje) {

        }
        DefaultMutableTreeNode nodopadre = (DefaultMutableTreeNode) nodo.getParent();
        String nombre = nodo.toString();
        int nivel = nodo.getDepth();

        expandOrCollapsToLevel(arbolTipos, new TreePath(nodo.getPath()), nivel, true);

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

    public void recorrerArbol() {
        TreeNode raiz = (TreeNode) arbolTipos.getModel().getRoot();
        System.out.println("\nRecorrer Arbol\n");
        imprimirNodos(raiz);

    }

    public void imprimirNodos(TreeNode nodo) {
        System.out.println(nodo.toString());
        if (nodo.getChildCount() >= 0) {
            for (Enumeration e = nodo.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                imprimirNodos(n);
            }
        }
    }

    protected static Image getLogo() {
        java.net.URL imgURL = PantallaArbolTipos.class.getClassLoader().getResource("es/documentum/imagenes/tipos.png");

        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            return null;
        }
    }
}
