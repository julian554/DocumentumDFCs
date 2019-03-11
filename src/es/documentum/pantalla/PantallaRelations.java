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
        textoId.setText(servidor);
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
        listaHijo = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        textoId = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaPadre = new javax.swing.JList<>();
        etiquetaTipoRelation = new javax.swing.JLabel();
        etiquetaIdRelation = new javax.swing.JLabel();
        etiquetaNombreRelation = new javax.swing.JLabel();
        etiquetaDestino = new javax.swing.JLabel();
        etiquetaOrigen = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        botonAnterior = new javax.swing.JButton();
        botonSiguiente = new javax.swing.JButton();
        menuPrincipal = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        opcionCerrar = new javax.swing.JMenuItem();
        opcionApariencia = new javax.swing.JMenu();
        opcionRBMetal = new javax.swing.JRadioButtonMenuItem();
        opcionRBNimbus = new javax.swing.JRadioButtonMenuItem();
        opcionRBWindows = new javax.swing.JRadioButtonMenuItem();
        opcionRBWindowsClassic = new javax.swing.JRadioButtonMenuItem();
        opcionRBPorDefecto = new javax.swing.JRadioButtonMenuItem();

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

        opcionCopiarTodo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/copiar-todo.png"))); // NOI18N
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

        listaHijo.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        listaHijo.setForeground(new java.awt.Color(0, 51, 102));
        listaHijo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaHijo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listaHijoMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(listaHijo);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("r_object_id");

        textoId.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        textoId.setForeground(new java.awt.Color(0, 51, 153));
        textoId.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                textoIdInputMethodTextChanged(evt);
            }
        });

        listaPadre.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        listaPadre.setForeground(new java.awt.Color(0, 51, 102));
        listaPadre.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaPadre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listaPadreMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(listaPadre);

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

        menuArchivo.setMnemonic('A');
        menuArchivo.setText("Archivo");

        opcionCerrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        opcionCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        opcionCerrar.setText("Cerrar");
        opcionCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCerrarActionPerformed(evt);
            }
        });
        menuArchivo.add(opcionCerrar);

        menuPrincipal.add(menuArchivo);

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

        menuPrincipal.add(opcionApariencia);

        setJMenuBar(menuPrincipal);

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
                        .addComponent(textoId, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(textoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        String id = textoId.getText();
        if (!id.isEmpty() && id.length() == 16 && id.startsWith("09")) {
            limpiarTexto();
            cargarDatos(id);
        }
    }//GEN-LAST:event_botonBuscarActionPerformed


    private void opcionCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarActionPerformed
        if (componente.equals("ListaHijo")) {
            if (listaHijo.getModel().getElementAt(listaHijo.getSelectedIndex()) != null) {
                Utilidades.copiarTextoPortapapeles(listaHijo.getModel().getElementAt(listaHijo.getSelectedIndex()));
            }
        }

        if (componente.equals("ListaPadre")) {
            if (listaPadre.getModel().getElementAt(listaPadre.getSelectedIndex()) != null) {
                Utilidades.copiarTextoPortapapeles(listaPadre.getModel().getElementAt(listaPadre.getSelectedIndex()));
            }
        }

        if (componente.equals("TextoId")) {
            if (textoId.getSelectedText() == null) {
                Utilidades.copiarTextoPortapapeles(textoId.getText());
            } else {
                Utilidades.copiarTextoPortapapeles(textoId.getSelectedText());
            }
        }
    }//GEN-LAST:event_opcionCopiarActionPerformed

    private void opcionPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionPegarActionPerformed
        if (componente.equals("TextoId")) {
            textoId.setText(Utilidades.pegarTextoPortapapeles());
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

    private void listaHijoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaHijoMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3 && listaHijo.getModel().getSize() > 0) {
            botonderecho = true;
            componente = "ListaHijo";
            popupmenu(evt);
        }
    }//GEN-LAST:event_listaHijoMousePressed

    private void textoIdInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_textoIdInputMethodTextChanged
//        if (!TextoId.getText().isEmpty()) {
//            setId(TextoId.getText());
//        }

    }//GEN-LAST:event_textoIdInputMethodTextChanged

    private void opcionCopiarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarTodoActionPerformed
        String textocompleto = "";
        if (componente.equals("ListaHijo")) {
            for (int i = 0; i < listaHijo.getModel().getSize(); i++) {
                textocompleto = textocompleto + listaHijo.getModel().getElementAt(i) + "\n";
            }
        }

        if (componente.equals("ListaPadre")) {
            for (int i = 0; i < listaPadre.getModel().getSize(); i++) {
                textocompleto = textocompleto + listaPadre.getModel().getElementAt(i) + "\n";
            }
        }

        Utilidades.copiarTextoPortapapeles(textocompleto);
    }//GEN-LAST:event_opcionCopiarTodoActionPerformed

    private void listaPadreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaPadreMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3 && listaPadre.getModel().getSize() > 0) {
            botonderecho = true;
            componente = "ListaPadre";
            popupmenu(evt);
        }
    }//GEN-LAST:event_listaPadreMousePressed

    private void botonSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSiguienteActionPerformed
        if (poslista + 1 < numrela) {
            poslista = poslista + 1;
        } else {
            poslista = 0;
        }
        cargarHijo(listarelaciones.get(poslista));
    }//GEN-LAST:event_botonSiguienteActionPerformed

    private void botonAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnteriorActionPerformed
        if (poslista > 0) {
            poslista = poslista - 1;
        } else {
            poslista = numrela - 1;
        }
        cargarHijo(listarelaciones.get(poslista));
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

            if (evt.getSource() == listaHijo) {
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
                opcionPegar.setEnabled(false);
                opcionPegar.setVisible(false);
            }

            if (evt.getSource() == listaPadre) {
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());
                opcionPegar.setEnabled(false);
                opcionPegar.setVisible(false);
            }

            if (evt.getSource() == textoId) {
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> listaHijo;
    private javax.swing.JList<String> listaPadre;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuBar menuPrincipal;
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
    private javax.swing.JTextField textoId;
    // End of variables declaration//GEN-END:variables

    public void limpiarTexto() {
        DefaultListModel Modelo = new DefaultListModel();
        listaHijo.setModel(Modelo);
        listaPadre.setModel(Modelo);
        etiquetaOrigen.setText("r_object_id:");
        etiquetaDestino.setText("r_object_id:");
        etiquetaTipoRelation.setText("");
        etiquetaNombreRelation.setText("Nombre de la relation: ");
        etiquetaIdRelation.setText("r_object_id de la relation: ");
        setTitle("Relations de un Documento");
    }

    public void cargarDatos(String r_object_id) {
        if (!r_object_id.isEmpty()) {
            limpiarTexto();
            textoId.setText(r_object_id);
            setId(r_object_id);
            Map<String, String> relaciones = new HashMap<>();
            setListarelaciones(ventanapadre.utilDocum.dameRelation(r_object_id, ventanapadre.utilDocum.conectarDocumentum()));

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

            listaPadre.removeAll();
            DefaultListModel ModeloPadre = new DefaultListModel();
            listaPadre.setModel(ModeloPadre);

            try {
                ArrayList listavalores = ventanapadre.utilDocum.dameAtributo(relaciones.get("IdPadre"), "object_name");
                String nombrePadre = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
                listavalores = ventanapadre.utilDocum.dameAtributo(relaciones.get("IdPadre"), "r_object_type");
                String tipoPadre = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
                listavalores = ventanapadre.utilDocum.dameAtributo(relaciones.get("IdPadre"), "a_storage_type");
                String filestorePadre = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
                listavalores = ventanapadre.utilDocum.dameAtributo(relaciones.get("IdPadre"), "r_content_size");
                String tamPadre = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
                listavalores = ventanapadre.utilDocum.dameAtributo(relaciones.get("IdPadre"), "i_partition");
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
            cargarHijo(relaciones);

        }
    }

    private void cargarHijo(Map<String, String> relaciones) {
        listaHijo.removeAll();
        DefaultListModel ModeloHijo = new DefaultListModel();
        listaHijo.setModel(ModeloHijo);
        etiquetaTipoRelation.setText(relaciones.get("Tipo"));
        try {
            etiquetaDestino.setText("r_object_id: " + relaciones.get("IdHijo"));
            etiquetaOrigen.setText("r_object_id: " + relaciones.get("IdPadre"));
            etiquetaNombreRelation.setText("Nombre de la relation: " + relaciones.get("NombreRelacion"));
            etiquetaIdRelation.setText("r_object_id de la relation: " + relaciones.get("IdRelacion"));
            ArrayList listavalores = ventanapadre.utilDocum.dameAtributo(relaciones.get("IdHijo"), "object_name");
            String nombreHijo = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
            listavalores = ventanapadre.utilDocum.dameAtributo(relaciones.get("IdHijo"), "r_object_type");
            String tipoHijo = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
            listavalores = ventanapadre.utilDocum.dameAtributo(relaciones.get("IdHijo"), "a_storage_type");
            String filestoreHijo = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
            listavalores = ventanapadre.utilDocum.dameAtributo(relaciones.get("IdHijo"), "r_content_size");
            String tamHijo = listavalores.isEmpty() ? "" : listavalores.get(0).toString();
            listavalores = ventanapadre.utilDocum.dameAtributo(relaciones.get("IdHijo"), "i_partition");
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
