package es.documentum.pantalla;

import es.documentum.Beans.Pistas;
import es.documentum.utilidades.Utilidades;
import es.documentum.utilidades.UtilidadesDocumentum;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class PantallaAyudaApi extends javax.swing.JFrame {

    private ArrayList<Pistas> listaPistasApi;
    private ArrayList<Pistas> listaFiltroPistasApi;
    private int posicionX = 200;
    private int posicionY = 400;
    private int ancho = 720;
    private int alto = 300;
    private String texto="";
    public static PantallaApi ventanapadre = null;
    UtilidadesDocumentum utildocum = new UtilidadesDocumentum();

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
        textoApi.setText(texto);
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }



    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public PantallaAyudaApi(PantallaApi parent, boolean modal) {
        ventanapadre = parent;
        initComponents();
        listaPistasApi = utildocum.leerDatosAyuda("ayuda-api.xml");
        listaFiltroPistasApi = listaPistasApi;
        cargarTablaAyuda();
//        setBounds(posicionX, posicionY, ancho, alto);
        //setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
     * this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelAyudaApi = new javax.swing.JPanel();
        panelTabla = new javax.swing.JScrollPane();
        tablaAyuda = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        textoApi = new javax.swing.JTextPane();
        botonCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(720, 300));
        setName("apiAyuda"); // NOI18N
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        panelAyudaApi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tablaAyuda.setAutoCreateRowSorter(true);
        tablaAyuda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tablaAyuda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaAyuda.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaAyuda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaAyudaMousePressed(evt);
            }
        });
        panelTabla.setViewportView(tablaAyuda);
        tablaAyuda.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        textoApi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textoApiKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(textoApi);

        botonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/si.png"))); // NOI18N
        botonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAyudaApiLayout = new javax.swing.GroupLayout(panelAyudaApi);
        panelAyudaApi.setLayout(panelAyudaApiLayout);
        panelAyudaApiLayout.setHorizontalGroup(
            panelAyudaApiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAyudaApiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAyudaApiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 694, Short.MAX_VALUE)
                    .addGroup(panelAyudaApiLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelAyudaApiLayout.setVerticalGroup(
            panelAyudaApiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAyudaApiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAyudaApiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCerrar))
                .addGap(8, 8, 8)
                .addComponent(panelTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAyudaApi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAyudaApi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textoApiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoApiKeyReleased
        MostrarAyuda();
        listaFiltroPistasApi = buscarEnDatosAyuda(textoApi.getText());
        cargarTablaAyuda();
    }//GEN-LAST:event_textoApiKeyReleased

    private void tablaAyudaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaAyudaMousePressed
        if (tablaAyuda.getSelectedRow() < 0) {
            return;
        }

        if (evt.getClickCount() == 2 && evt.getButton() == java.awt.event.MouseEvent.BUTTON1 && tablaAyuda.getModel().getRowCount() > 0) {
            LabelIcon valor = (LabelIcon) tablaAyuda.getModel().getValueAt(tablaAyuda.convertRowIndexToModel(tablaAyuda.getSelectedRow()), 0);

            textoApi.setText(valor.getLabel().trim());
            textoApi.requestFocus();
            ventanapadre.setTextoAyudaApi(valor.getLabel().trim());
        }
    }//GEN-LAST:event_tablaAyudaMousePressed

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
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(PantallaAyudaApi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(PantallaAyudaApi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(PantallaAyudaApi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(PantallaAyudaApi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaAyudaApi(ventanapadre, true);

            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCerrar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelAyudaApi;
    private javax.swing.JScrollPane panelTabla;
    private javax.swing.JTable tablaAyuda;
    private javax.swing.JTextPane textoApi;
    // End of variables declaration//GEN-END:variables

    private void MostrarAyuda() {
//        etiquetaAyuda.setText(textoApi.getText());

    }



    public void cargarTablaAyuda() {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }

                @Override
                public Class<?> getColumnClass(int column) {
                    switch (column) {
                        case 0:
                            return LabelIcon.class;
                        default:
                            return Object.class;
                    }
                }
            };
            tablaAyuda.setDefaultRenderer(LabelIcon.class, new LabelIconRenderer());
            tablaAyuda.setModel(modelo);

//            String[][] filas = new String[pistas.size()][3];
            Object[][] filas = new Object[listaFiltroPistasApi.size()][2];
            Object[] cabecera = new Object[2];
            for (int n = 0; n < listaFiltroPistasApi.size(); n++) {
                Pistas mipista = listaFiltroPistasApi.get(n);

//                System.out.println("-> TIPO: " + mipista.getTipo());
//                System.out.println("-> TEXTO: " + mipista.getTexto());
//                System.out.println("-> SINTAXIS: " + mipista.getSintaxis());
                String tipo = mipista.getTipo();

                //dmAPISet
                java.net.URL imgURL = PantallaAyudaApi.class.getClassLoader().getResource("es/documentum/imagenes/set.png");

                if (tipo.equalsIgnoreCase("dmAPIExec")) {
                    imgURL = PantallaAyudaApi.class.getClassLoader().getResource("es/documentum/imagenes/exec.png");
                }
                if (tipo.equalsIgnoreCase("dmAPIGet")) {
                    imgURL = PantallaAyudaApi.class.getClassLoader().getResource("es/documentum/imagenes/get.png");
                }

                ImageIcon icono = new ImageIcon(imgURL);

                String textoComando = mipista.getSintaxis().substring(0, (mipista.getSintaxis().indexOf(",") < 0 ? mipista.getSintaxis().length() : mipista.getSintaxis().indexOf(",")));
                filas[n][0] = new LabelIcon(icono, "  " + textoComando);
                filas[n][1] = mipista.getTexto();;

            }
            cabecera[0] = "Comando";
            cabecera[1] = "Ayuda comando";
            modelo = new DefaultTableModel(filas, cabecera) {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }

                @Override
                public Class<?> getColumnClass(int column) {
                    switch (column) {
                        case 0:
                            return LabelIcon.class;
                        default:
                            return Object.class;
                    }
                }
            };
            tablaAyuda.setModel(modelo);
        } catch (Exception ex) {
            System.out.println("Error - " + ex.getMessage());
        }

        panelTabla.setBounds(textoApi.getX(), textoApi.getY() + 75, textoApi.getWidth(), 400);
        panelTabla.setLayout(new ScrollPaneLayout());
//        panelTabla.setSize(textoApi.getWidth(), 400);
        tablaAyuda.setShowGrid(false);
        tablaAyuda.setAutoResizeMode(4);
        TableColumn columna = tablaAyuda.getColumnModel().getColumn(0);
        columna.setPreferredWidth(130);
        columna.setMinWidth(130);
        columna.sizeWidthToFit();
        TableColumn columna1 = tablaAyuda.getColumnModel().getColumn(1);
        columna1.setPreferredWidth(tablaAyuda.getWidth() - 240);
        columna1.setMinWidth(tablaAyuda.getWidth() - 240);
        columna1.sizeWidthToFit();
        tablaAyuda.setSelectionMode(0);
        tablaAyuda.setColumnSelectionAllowed(false);
        tablaAyuda.getTableHeader().setFont(new Font(tablaAyuda.getTableHeader().getFont().getFontName(), Font.BOLD, tablaAyuda.getFont().getSize()));
        pintarTabla(tablaAyuda);
    }

    public ArrayList<Pistas> buscarEnDatosAyuda(String cadena) {
        ArrayList<Pistas> listaPistas = new ArrayList<>();
        for (int n = 0; n < listaPistasApi.size(); n++) {
            if (listaPistasApi.get(n).getSintaxis().contains(cadena.toLowerCase())) {
                listaPistas.add(listaPistasApi.get(n));
            }
        }
        return listaPistas;
    }

    private void pintarTabla(JTable tabla) {
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                boolean oddRow = (row % 2 == 0);
                if (oddRow) {
                    setBackground(Color.WHITE);
                } else {
                    setBackground(new Color(235, 235, 235)); // gris claro
                }

                if (isSelected) {
                    setBackground(new Color(175, 205, 235)); // azul claro selección
                }
                return this;
            }
        });
    }

    private static class LabelIcon {

        Icon icon;

        public Icon getIcon() {
            return icon;
        }

        public void setIcon(Icon icon) {
            this.icon = icon;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
        String label;

        public LabelIcon(Icon icon, String label) {
            this.icon = icon;
            this.label = label;
        }
    }

    private static class LabelIconRenderer extends DefaultTableCellRenderer {

        public LabelIconRenderer() {
            setHorizontalTextPosition(JLabel.RIGHT);
            setVerticalTextPosition(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            JLabel r = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, col);
            setIcon(((LabelIcon) value).icon);
            setText(((LabelIcon) value).label);
            return r;
        }
    }

}