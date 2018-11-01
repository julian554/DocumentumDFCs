package es.documentum.pantalla;

import es.documentum.utilidades.Utilidades;
import static es.documentum.utilidades.Utilidades.escribeLog;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PantallaRelations extends javax.swing.JFrame {

    Utilidades util = new Utilidades();
    static String tipo = "FRAME";
    private boolean botonderecho;
    private String componente;
    private boolean primeravez = true;
    public static PantallaDocumentum ventanapadre = null;
    private ArrayList<Map<String, String>> listarelaciones = new ArrayList<Map<String, String>>();
    private int poslista = 0;
    private int numrela = 1;
    private String id = "";

    public ArrayList<Map<String, String>> getListarelaciones() {
        return listarelaciones;
    }

    public void setListarelaciones(ArrayList<Map<String, String>> listarelaciones) {
        this.listarelaciones = listarelaciones;
    }

    public int getPoslista() {
        return poslista;
    }

    public void setPoslista(int poslista) {
        this.poslista = poslista;
    }

    public int getNumrela() {
        return numrela;
    }

    public void setNumrela(int numrela) {
        this.numrela = numrela;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPrimeravez() {
        return primeravez;
    }

    public void setPrimeravez(boolean primeravez) {
        this.primeravez = primeravez;
    }

    public void setTextoServidor(String servidor) {
        TextoId.setText(servidor);
    }

    public PantallaRelations(PantallaDocumentum parent, boolean modal) {
        ventanapadre = parent;
        initComponents();
        setLocationRelativeTo(null);
        //  setVisible(true);
        repaint();
        try {
            setIconImage(new ImageIcon(getLogo()).getImage());
        } catch (NullPointerException e) {
            Utilidades.escribeLog("\nError cargando el Logo " + e.getMessage() + "\n");
        }
        opcionRBMetalActionPerformed(null);
        botonAnterior.setEnabled(false);
        botonSiguiente.setEnabled(false);
    }

    protected static Image getLogo() {
        //   java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/documentum_logo_mini.gif");
        java.net.URL imgURL = PantallaRelations.class.getClassLoader().getResource("es/documentum/imagenes/relation.png");

        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            return null;
        }
    }

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {
        this.requestFocus();
        this.toFront();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupEditar = new javax.swing.JPopupMenu();
        opcionCopiar = new javax.swing.JMenuItem();
        opcionPegar = new javax.swing.JMenuItem();
        opcionCopiarTodo = new javax.swing.JMenuItem();
        botonCerrar = new javax.swing.JButton();
        botonBuscar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ListaHijo = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        TextoId = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        ListaPadre = new javax.swing.JList<>();
        etiquetaTipoRelation = new javax.swing.JLabel();
        etiquetaIdRelation = new javax.swing.JLabel();
        etiquetaNombreRelation = new javax.swing.JLabel();
        etiquetaDestino = new javax.swing.JLabel();
        etiquetaOrigen = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        botonAnterior = new javax.swing.JButton();
        botonSiguiente = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        Menu = new javax.swing.JMenu();
        opcionCerrar = new javax.swing.JMenuItem();
        opcionApariencia = new javax.swing.JMenu();
        opcionRBMetal = new javax.swing.JRadioButtonMenuItem();
        opcionRBNimbus = new javax.swing.JRadioButtonMenuItem();
        opcionRBWindows = new javax.swing.JRadioButtonMenuItem();
        opcionRBWindowsClassic = new javax.swing.JRadioButtonMenuItem();
        opcionRBPorDefecto = new javax.swing.JRadioButtonMenuItem();

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

        opcionCopiarTodo.setText("Copiar Todo");
        opcionCopiarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCopiarTodoActionPerformed(evt);
            }
        });
        popupEditar.add(opcionCopiarTodo);

        setTitle("Relations de un documento");
        setAlwaysOnTop(true);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setName("VentanaLeerFichero"); // NOI18N
        setResizable(false);

        botonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        botonCerrar.setText("Cerrar");
        botonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarActionPerformed(evt);
            }
        });

        botonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/buscar_peq.png"))); // NOI18N
        botonBuscar.setText(" Buscar");
        botonBuscar.setToolTipText("Ejecutar comando");
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });

        ListaHijo.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        ListaHijo.setForeground(new java.awt.Color(0, 51, 102));
        ListaHijo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ListaHijo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ListaHijoMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(ListaHijo);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("r_object_id");

        TextoId.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        TextoId.setForeground(new java.awt.Color(0, 51, 153));
        TextoId.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                TextoIdInputMethodTextChanged(evt);
            }
        });

        ListaPadre.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        ListaPadre.setForeground(new java.awt.Color(0, 51, 102));
        ListaPadre.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ListaPadre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ListaPadreMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(ListaPadre);

        etiquetaTipoRelation.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        etiquetaTipoRelation.setForeground(new java.awt.Color(0, 0, 102));

        etiquetaIdRelation.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        etiquetaIdRelation.setText("r_object_id de la relation : ");

        etiquetaNombreRelation.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        etiquetaNombreRelation.setText("Nombre de la relation : ");

        etiquetaDestino.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        etiquetaOrigen.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Tipo de relation : ");

        botonAnterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/anterior.png"))); // NOI18N
        botonAnterior.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnteriorActionPerformed(evt);
            }
        });

        botonSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/siguiente.png"))); // NOI18N
        botonSiguiente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSiguienteActionPerformed(evt);
            }
        });

        Menu.setMnemonic('A');
        Menu.setText("Archivo");

        opcionCerrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        opcionCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        opcionCerrar.setText("Cerrar");
        opcionCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCerrarActionPerformed(evt);
            }
        });
        Menu.add(opcionCerrar);

        jMenuBar1.add(Menu);

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

        jMenuBar1.add(opcionApariencia);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextoId, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(etiquetaNombreRelation, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(etiquetaIdRelation, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(etiquetaDestino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(114, 114, 114)
                        .addComponent(botonAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3)
                    .addComponent(etiquetaOrigen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiquetaTipoRelation, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(etiquetaTipoRelation, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1)))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(etiquetaNombreRelation, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(etiquetaIdRelation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22)
                .addComponent(etiquetaOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiquetaDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAnterior)
                    .addComponent(botonSiguiente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonCerrar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opcionCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCerrarActionPerformed
        botonCerrarActionPerformed(evt);
    }//GEN-LAST:event_opcionCerrarActionPerformed

    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_botonCerrarActionPerformed

    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarActionPerformed
        String id = TextoId.getText();
        if (!id.isEmpty() && id.length() == 16 && id.startsWith("09")) {
            LimpiarTexto();
            CargarDatos(id);
        }
    }//GEN-LAST:event_botonBuscarActionPerformed


    private void opcionCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarActionPerformed
        if (componente.equals("ListaHijo")) {
            if (ListaHijo.getModel().getElementAt(ListaHijo.getSelectedIndex()) != null) {
                Utilidades.copiarTextoPortapapeles(ListaHijo.getModel().getElementAt(ListaHijo.getSelectedIndex()));
            }
        }

        if (componente.equals("ListaPadre")) {
            if (ListaPadre.getModel().getElementAt(ListaPadre.getSelectedIndex()) != null) {
                Utilidades.copiarTextoPortapapeles(ListaPadre.getModel().getElementAt(ListaPadre.getSelectedIndex()));
            }
        }

        if (componente.equals("TextoId")) {
            if (TextoId.getSelectedText() == null) {
                Utilidades.copiarTextoPortapapeles(TextoId.getText());
            } else {
                Utilidades.copiarTextoPortapapeles(TextoId.getSelectedText());
            }
        }
    }//GEN-LAST:event_opcionCopiarActionPerformed

    private void opcionPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionPegarActionPerformed
        if (componente.equals("TextoId")) {
            TextoId.setText(Utilidades.pegarTextoPortapapeles());
        }
    }//GEN-LAST:event_opcionPegarActionPerformed

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

    private void ListaHijoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaHijoMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3 && ListaHijo.getModel().getSize() > 0) {
            botonderecho = true;
            componente = "ListaHijo";
            popupmenu(evt);
        }
    }//GEN-LAST:event_ListaHijoMousePressed

    private void TextoIdInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_TextoIdInputMethodTextChanged
//        if (!TextoId.getText().isEmpty()) {
//            setId(TextoId.getText());
//        }

    }//GEN-LAST:event_TextoIdInputMethodTextChanged

    private void opcionCopiarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarTodoActionPerformed
        String textocompleto = "";
        if (componente.equals("ListaHijo")) {
            for (int i = 0; i < ListaHijo.getModel().getSize(); i++) {
                textocompleto = textocompleto + ListaHijo.getModel().getElementAt(i) + "\n";
            }
        }

        if (componente.equals("ListaPadre")) {
            for (int i = 0; i < ListaPadre.getModel().getSize(); i++) {
                textocompleto = textocompleto + ListaPadre.getModel().getElementAt(i) + "\n";
            }
        }

        Utilidades.copiarTextoPortapapeles(textocompleto);
    }//GEN-LAST:event_opcionCopiarTodoActionPerformed

    private void ListaPadreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaPadreMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3 && ListaPadre.getModel().getSize() > 0) {
            botonderecho = true;
            componente = "ListaPadre";
            popupmenu(evt);
        }
    }//GEN-LAST:event_ListaPadreMousePressed

    private void botonSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSiguienteActionPerformed
        if (poslista + 1 < numrela) {
            poslista = poslista + 1;
        } else {
            poslista = 0;
        }
        CargarHijo(listarelaciones.get(poslista));
    }//GEN-LAST:event_botonSiguienteActionPerformed

    private void botonAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnteriorActionPerformed
        if (poslista > 0) {
            poslista = poslista - 1;
        } else {
            poslista = numrela - 1;
        }
        CargarHijo(listarelaciones.get(poslista));
    }//GEN-LAST:event_botonAnteriorActionPerformed
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

            if (evt.getSource() == ListaHijo) {
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
                opcionPegar.setEnabled(false);
                opcionPegar.setVisible(false);
            }

            if (evt.getSource() == ListaPadre) {
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
                opcionPegar.setEnabled(false);
                opcionPegar.setVisible(false);
            }

            if (evt.getSource() == TextoId) {
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
                opcionPegar.setEnabled(true);
                opcionPegar.setVisible(true);
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            escribeLog("Error al establecer el estilo de la ventana. Error: " + ex.getMessage());
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PantallaRelations frame = new PantallaRelations(ventanapadre, true);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });

            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ListaHijo;
    private javax.swing.JList<String> ListaPadre;
    private javax.swing.JMenu Menu;
    private javax.swing.JTextField TextoId;
    private javax.swing.JButton botonAnterior;
    private javax.swing.JButton botonBuscar;
    public static javax.swing.JButton botonCerrar;
    private javax.swing.JButton botonSiguiente;
    private javax.swing.JLabel etiquetaDestino;
    private javax.swing.JLabel etiquetaIdRelation;
    private javax.swing.JLabel etiquetaNombreRelation;
    private javax.swing.JLabel etiquetaOrigen;
    private javax.swing.JLabel etiquetaTipoRelation;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenu opcionApariencia;
    private javax.swing.JMenuItem opcionCerrar;
    private javax.swing.JMenuItem opcionCopiar;
    private javax.swing.JMenuItem opcionCopiarTodo;
    private javax.swing.JMenuItem opcionPegar;
    private javax.swing.JRadioButtonMenuItem opcionRBMetal;
    private javax.swing.JRadioButtonMenuItem opcionRBNimbus;
    private javax.swing.JRadioButtonMenuItem opcionRBPorDefecto;
    private javax.swing.JRadioButtonMenuItem opcionRBWindows;
    private javax.swing.JRadioButtonMenuItem opcionRBWindowsClassic;
    private javax.swing.JPopupMenu popupEditar;
    // End of variables declaration//GEN-END:variables

    public void LimpiarTexto() {
        DefaultListModel Modelo = new DefaultListModel();
        ListaHijo.setModel(Modelo);
        ListaPadre.setModel(Modelo);
        etiquetaOrigen.setText("r_object_id:");
        etiquetaDestino.setText("r_object_id:");
        etiquetaTipoRelation.setText("");
        etiquetaNombreRelation.setText("Nombre de la relation: ");
        etiquetaIdRelation.setText("r_object_id de la relation: ");
        setTitle("Relations de un Documento");
    }

    public void CargarDatos(String r_object_id) {
        if (!r_object_id.isEmpty()) {
            LimpiarTexto();
            TextoId.setText(r_object_id);
            setId(r_object_id);
            Map<String, String> relaciones = new HashMap<>();
            setListarelaciones(ventanapadre.utilDocum.DameRelation(r_object_id, ventanapadre.utilDocum.conectarDocumentum()));

            if (getListarelaciones().isEmpty()) {
                PantallaMensaje mensaje = new PantallaMensaje(this, botonderecho);
                mensaje.setMensaje("El documento con r_object_id " + r_object_id + " no tiene Relations");
                mensaje.setTitle("Relations de Documentos");
                mensaje.setVisible(true);
                if (isPrimeravez()) {
                    this.dispose();
                }
                return;
            }
            setPrimeravez(false);

            setNumrela(getListarelaciones().size());

            if (getNumrela() > 1) {
                botonAnterior.setEnabled(true);
                botonSiguiente.setEnabled(true);
            } else {
                botonAnterior.setEnabled(false);
                botonSiguiente.setEnabled(false);
            }

            relaciones = getListarelaciones().get(poslista);

            System.out.println(relaciones.get("Tipo"));
            etiquetaTipoRelation.setText(relaciones.get("Tipo"));
            System.out.println(relaciones.get("NombreRelacion"));
            etiquetaNombreRelation.setText("Nombre de la relation: " + relaciones.get("NombreRelacion"));
            System.out.println(relaciones.get("IdRelacion"));
            etiquetaIdRelation.setText("r_object_id de la relation: " + relaciones.get("IdRelacion"));

            ListaPadre.removeAll();
            DefaultListModel ModeloPadre = new DefaultListModel();
            ListaPadre.setModel(ModeloPadre);

            try {
                ArrayList listavalores = ventanapadre.utilDocum.DameAtributo(relaciones.get("IdPadre"), "object_name");
                String nombrePadre = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
                listavalores = ventanapadre.utilDocum.DameAtributo(relaciones.get("IdPadre"), "r_object_type");
                String tipoPadre = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
                listavalores = ventanapadre.utilDocum.DameAtributo(relaciones.get("IdPadre"), "a_storage_type");
                String filestorePadre = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
                listavalores = ventanapadre.utilDocum.DameAtributo(relaciones.get("IdPadre"), "r_content_size");
                String tamPadre = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
                listavalores = ventanapadre.utilDocum.DameAtributo(relaciones.get("IdPadre"), "i_partition");
                String iPartitionPadre = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
                ModeloPadre.add(0, "Nombre del Documento: " + nombrePadre);
                ModeloPadre.add(1, "Tipo documental: " + tipoPadre);
                ModeloPadre.add(2, "Tamaño del documento: " + (tamPadre.isEmpty() ? "" : util.humanReadableByteCount(Long.parseLong(tamPadre), true)));
                ModeloPadre.add(3, "Filestore: " + filestorePadre);
                ModeloPadre.add(4, "Ruta en Documentum: " + relaciones.get("RutaPadreDocumentum"));
                ModeloPadre.add(5, "Ruta en SSOO: " + relaciones.get("RutaPadreSSOO"));
                if (!iPartitionPadre.isEmpty()) {
                    ModeloPadre.add(6, "i_partition: " + iPartitionPadre);
                }
            } catch (NumberFormatException ex) {
                Utilidades.escribeLog("Error al cargar listaPadre - Error " + ex.getMessage());
            }

//            System.out.println(relaciones.get("IdPadre"));
//            System.out.println(relaciones.get("RutaPadreDocumentum"));
//            System.out.println(relaciones.get("RutaPadreSSOO"));
            CargarHijo(relaciones);

        }
    }

    private void CargarHijo(Map<String, String> relaciones) {
        ListaHijo.removeAll();
        DefaultListModel ModeloHijo = new DefaultListModel();
        ListaHijo.setModel(ModeloHijo);
        etiquetaTipoRelation.setText(relaciones.get("Tipo"));
        try {
            etiquetaDestino.setText("r_object_id: " + relaciones.get("IdHijo"));
            etiquetaOrigen.setText("r_object_id: " + relaciones.get("IdPadre"));
            etiquetaNombreRelation.setText("Nombre de la relation: " + relaciones.get("NombreRelacion"));
            etiquetaIdRelation.setText("r_object_id de la relation: " + relaciones.get("IdRelacion"));
            ArrayList listavalores = ventanapadre.utilDocum.DameAtributo(relaciones.get("IdHijo"), "object_name");
            String nombreHijo = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
            listavalores = ventanapadre.utilDocum.DameAtributo(relaciones.get("IdHijo"), "r_object_type");
            String tipoHijo = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
            listavalores = ventanapadre.utilDocum.DameAtributo(relaciones.get("IdHijo"), "a_storage_type");
            String filestoreHijo = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
            listavalores = ventanapadre.utilDocum.DameAtributo(relaciones.get("IdHijo"), "r_content_size");
            String tamHijo = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
            listavalores = ventanapadre.utilDocum.DameAtributo(relaciones.get("IdHijo"), "i_partition");
            String iPartitionHijo = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
            ModeloHijo.add(0, "Nombre del Documento: " + nombreHijo);
            ModeloHijo.add(1, "Tipo documental: " + tipoHijo);
            ModeloHijo.add(2, "Tamaño del documento: " + (tamHijo.isEmpty() ? "" : util.humanReadableByteCount(Long.parseLong(tamHijo), true)));
            ModeloHijo.add(3, "Filestore: " + filestoreHijo);
            ModeloHijo.add(4, "Ruta en Documentum: " + relaciones.get("RutaHijoDocumentum"));
            ModeloHijo.add(5, "Ruta en SSOO: " + relaciones.get("RutaHijoSSOO"));
            if (!iPartitionHijo.isEmpty()) {
                ModeloHijo.add(6, "i_partition: " + iPartitionHijo);
            }
        } catch (NumberFormatException ex) {
            Utilidades.escribeLog("Error al cargar ListaHijo - Error " + ex.getMessage());
        }

//        System.out.println(relaciones.get("IdHijo"));
//        System.out.println(relaciones.get("RutaHijoDocumentum"));
//        System.out.println(relaciones.get("RutaHijoSSOO"));
    }
}
