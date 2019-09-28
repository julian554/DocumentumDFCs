package es.documentum.pantalla;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfSession;
import es.documentum.utilidades.Utilidades;
import static es.documentum.utilidades.Utilidades.escribeLog;
import es.documentum.utilidades.UtilidadesDocumentum;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class PantallaInfoRepositorio extends javax.swing.JFrame {

    String repositorio = "";
    IDfSession gsesion = null;
    UtilidadesDocumentum utilDocum = new UtilidadesDocumentum();
    Utilidades util = new Utilidades();
    String componente = "";
    Boolean botonderecho = false;
    public static PantallaDocumentum ventanapadre = null;

    public String getRepositorio() {
        return repositorio;
    }

    public void setRepositorio(String repositorio) {
        this.repositorio = repositorio;
    }

    public IDfSession getGsesion() {
        return gsesion;
    }

    public void setGsesion(IDfSession gsesion) {
        this.gsesion = gsesion;
    }

    public PantallaInfoRepositorio(PantallaDocumentum parent, boolean modal) {
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

    }

    public void inicializar() {
        cargarDatos(gsesion);
    }

    protected static Image getLogo() {
        //   java.net.URL imgURL = PantallaDocumentum.class.getClassLoader().getResource("es/documentum/imagenes/documentum_logo_mini.gif");
        java.net.URL imgURL = PantallaInfoRepositorio.class.getClassLoader().getResource("es/documentum/imagenes/information.png");

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
        jScrollPane1 = new javax.swing.JScrollPane();
        textoInfo = new javax.swing.JTextPane();
        botonAceptar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        Menu = new javax.swing.JMenu();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ejecutar Comandos SSH en remoto");
        setAlwaysOnTop(true);
        setMinimumSize(new java.awt.Dimension(1074, 775));
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setName("VentanaLeerFichero"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1074, 775));

        jScrollPane1.setMinimumSize(new java.awt.Dimension(700, 500));

        textoInfo.setEditable(false);
        textoInfo.setBackground(new java.awt.Color(245, 245, 245));
        textoInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                textoInfoMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(textoInfo);

        botonAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/documentum/imagenes/salir_peq.png"))); // NOI18N
        botonAceptar.setMnemonic('A');
        botonAceptar.setText("Cerrar");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1076, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(botonAceptar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opcionCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_opcionCerrarActionPerformed


    private void opcionCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCopiarActionPerformed
        if (componente.equals("textoInfo")) {
            if (textoInfo.getSelectedText() == null) {
                Utilidades.copiarTextoPortapapeles(textoInfo.getText());
            } else {
                Utilidades.copiarTextoPortapapeles(textoInfo.getSelectedText());
            }
        }
    }//GEN-LAST:event_opcionCopiarActionPerformed

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

    private void textoInfoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textoInfoMousePressed
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            componente = "textoInfo";
            botonderecho = true;
            popupmenu(evt);
        }
    }//GEN-LAST:event_textoInfoMousePressed

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        dispose();
    }//GEN-LAST:event_botonAceptarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

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
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PantallaInfoRepositorio frame = new PantallaInfoRepositorio(ventanapadre, true);
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
    private javax.swing.JMenu Menu;
    public static javax.swing.JButton botonAceptar;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu opcionApariencia;
    private javax.swing.JMenuItem opcionCerrar;
    private javax.swing.JMenuItem opcionCopiar;
    private javax.swing.JRadioButtonMenuItem opcionRBMetal;
    private javax.swing.JRadioButtonMenuItem opcionRBNimbus;
    private javax.swing.JRadioButtonMenuItem opcionRBPorDefecto;
    private javax.swing.JRadioButtonMenuItem opcionRBWindows;
    private javax.swing.JRadioButtonMenuItem opcionRBWindowsClassic;
    private javax.swing.JPopupMenu popupEditar;
    private javax.swing.JTextPane textoInfo;
    // End of variables declaration//GEN-END:variables

    private class HTMLListener implements HyperlinkListener {

        public void hyperlinkUpdate(HyperlinkEvent e) {

            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
//                    textoInfo.setPage(e.getURL());
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void cargarDatos(IDfSession gsesion) {
        StringBuilder cadenaHTML = new StringBuilder();
        String dql = "";
        try {
            String id = gsesion.getDocbaseId();
            String idrepositorio = id + " (" + Integer.toHexString(Integer.parseInt(id)) + ")";
            dql = "select object_name, r_object_id, web_server_loc, r_server_version from dm_server_config order by r_object_id";
            IDfCollection col = utilDocum.ejecutarDql(dql, gsesion);
            textoInfo.setContentType("text/html");
            textoInfo.addHyperlinkListener(new HTMLListener());

            cadenaHTML.append("<br>");
            while (col.next()) {
                String repo = col.getTypedObject().getString("object_name");
                String version = col.getTypedObject().getString("r_server_version");
                String servidor = col.getTypedObject().getString("web_server_loc");
                String r_object_id = col.getTypedObject().getString("r_object_id");
                String dormancyStatus = utilDocum.getDormancyStatusFromServerMap(gsesion, repo);
                String mensajeActivo = "";
                if (dormancyStatus != null && dormancyStatus.equalsIgnoreCase("Active")) {
                    mensajeActivo = "Activo";
                } else {
                    mensajeActivo = "Parado";
                }

                cadenaHTML.append("<TABLE BORDER CELLSPACING=0 WIDTH=\"100%\"><tr bgcolor=#f5f5f5><th>REPOSITORIO</th><th>R_OBJECT_ID</th>"
                        + "<th>R_DOCBASE_ID</th><th>DOCBROKER</th><th>VERSION</th><th>ESTADO</th>");
                cadenaHTML.append("<tr bgcolor=\"white\"><td style=\"text-align:center\"><font color=\"black\" size=4> " + repo + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + r_object_id + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + idrepositorio + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + servidor + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + version + " </font></td");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + mensajeActivo + " </font></td></tr></TABLE><br>");

                cadenaHTML.append("</TABLE><br>");

            }

            if (col != null) {
                col.close();
            }
        } catch (Exception ex) {

        }
        cadenaHTML.append("</TABLE><br>");

        try {
            dql = "select object_name, r_object_id from dm_jms_config order by r_object_id";
            IDfCollection col = utilDocum.ejecutarDql(dql, gsesion);
            textoInfo.setContentType("text/html");
            textoInfo.addHyperlinkListener(new HTMLListener());
            while (col.next()) {
                String jmsname = col.getTypedObject().getString("object_name");
                String r_object_id = col.getTypedObject().getString("r_object_id");

                //  dql = "select app_server_name, app_server_uri from dm_server_config_r where r_object_id='" + r_object_id + "'";
                dql = "select r_object_id,server_config_id, servlet_name as app_server_name, base_uri as app_server_uri from dm_sysprocess_config_r  where r_object_id='" + r_object_id + "'";
                IDfCollection col_lineas = utilDocum.ejecutarDql(dql, gsesion);

                dql = "select sc.object_name as nombre from dm_server_config sc, dm_sysprocess_config_r r where sc.r_object_id=r.server_config_id  and r.r_object_id='" + r_object_id + "'";
                IDfCollection col_conf = utilDocum.ejecutarDql(dql, gsesion);
                String nombre_conf = "";

                while (col_conf.next()) {
                    String nombre = col_conf.getTypedObject().getString("nombre");
                    if (nombre_conf.isEmpty()) {
                        nombre_conf = nombre;
                    } else {
                        nombre_conf = nombre_conf + ", " + nombre;
                    }
                }

                if (col_lineas != null) {
                    cadenaHTML.append("<TABLE BORDER CELLSPACING=0 WIDTH=\"100%\">" + "<tr bgcolor=\"white\"><th><font color=\"black\" size=5>JAVA METHOD SERVER</font></th><th> " + jmsname + " </th><th>Content Server: " + nombre_conf + "</th></tr></TABLE>");
                    cadenaHTML.append("<TABLE BORDER CELLSPACING=0 WIDTH=\"100%\">" + "<tr bgcolor=#f5f5f5><th>ID</th><th>NOMBRE</th><th>URL</th></tr>");

//                                        cadenaHTML.append("<TABLE BORDER CELLSPACING=0 WIDTH=\"100%\"><CAPTION ALIGN=top><b>JAVA METHOD SERVER  -==-  " + jmsname +"  -==-  "+nombre_conf+ "</b></CAPTION>"
//                            + "<tr bgcolor=#f5f5f5><th>NOMBRE</th><th>URL</th></tr>");
                }

                while (col_lineas.next()) {
                    String nombre_servlet = col_lineas.getTypedObject().getString("app_server_name");
                    String uri_servlet = col_lineas.getTypedObject().getString("app_server_uri");
                    cadenaHTML.append("<tr bgcolor=\"white\"><td  width=\"30%\"><font color=\"black\" size=4>" + r_object_id + "</font></td><td>"+nombre_servlet+"</td>");
                    //   cadenaHTML.append("<td><font color=\"blue\" size=4>" + uri_servlet + "</font></td></tr>");
                    cadenaHTML.append("<td><fontsize=4><a href=\"" + uri_servlet + "\">" + uri_servlet + "</a></font></td></tr>");
                }
                cadenaHTML.append("</TABLE><br><br>");
                if (col_lineas != null) {
                    col_lineas.close();
                }
                if (col_conf != null) {
                    col_conf.close();
                }

            }
        } catch (Exception ex) {

        }

        try {
            dql = "SELECT s.object_name as nombre,r_is_public,server_major_version,server_minor_version,dormancy_status,acs_base_url,c.object_name as srv,s.r_object_id "
                    + " FROM dm_acs_config_sp s, dm_acs_config_r r, dm_server_config c where s.r_object_id=r.r_object_id "
                    + " and s.svr_config_id=c.r_object_id and acs_base_url !=' '";
            
            /*
            The current dormancy status of the ACS server. Valid values are:
                * Dormant
                * Active
            */
            IDfCollection col = utilDocum.ejecutarDql(dql, gsesion);
            cadenaHTML.append("<TABLE BORDER CELLSPACING=0 WIDTH=\"100%\"><CAPTION ALIGN=top><b>ACS</b></CAPTION>"
                    + "<tr bgcolor=#f5f5f5><th>NOMBRE</th><th>ID</th><th>REPOSITORIO</th><th>VERSION</th><th>ESTADO</th><th>URL</th></tr>");
            while (col.next()) {
                String nombre_acs = col.getTypedObject().getString("nombre");
                String acs_publico = col.getTypedObject().getString("r_is_public");
                String acs_servidor = col.getTypedObject().getString("srv");
                String acs_v_mayor = col.getTypedObject().getString("server_major_version");
                String acs_v_menor = col.getTypedObject().getString("server_minor_version");
                String estado = col.getTypedObject().getString("dormancy_status");
                estado = estado.trim().equalsIgnoreCase("ACTIVE") ? "Activo" : "No Activo";
                String acs_url = col.getTypedObject().getString("acs_base_url");
                String id= col.getTypedObject().getString("r_object_id");

                cadenaHTML.append("<tr bgcolor=\"white\"><td><font color=\"black\" size=4> " + nombre_acs + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + id + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + acs_servidor + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + acs_v_mayor + "." + acs_v_menor + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + estado + " </font></td>");
                if (estado.equals("No Activo")) {
                    cadenaHTML.append("<td></td></tr>");;
                } else {
                    cadenaHTML.append("<td><fontsize=4><a href=\"" + acs_url + "\">" + acs_url + "</a></font></td></tr>");
                }
//                cadenaHTML.append("<td><font color=\"blue\" size=4> " + acs_url + " </font></td></tr>");
            }
            if (col != null) {
                col.close();
            }
        } catch (Exception ex) {
        }
        cadenaHTML.append("</TABLE><br><br>");
        try {

            dql = "select a.object_name as agent_name, c.object_name as object_name, a.force_inactive as force_inactive,"
                    + " b.ft_engine_id as connected_server_id  from dm_ftindex_agent_config a, dm_fulltext_index b, "
                    + " dm_ftengine_config c  where a.index_name = b.index_name and b.ft_engine_id = c.r_object_id";
            IDfCollection col = utilDocum.ejecutarDql(dql, gsesion);
            cadenaHTML.append("<TABLE BORDER CELLSPACING=0 WIDTH=\"100%\"><CAPTION ALIGN=top><b>INDEXADOR</b></CAPTION>"
                    + "<tr bgcolor=#f5f5f5><th>NOMBRE DEL AGENTE</th><th>NOMBRE DEL OBJETO</th><th>ID</th><th>URL</th></tr>");

            while (col.next()) {
                String agent_name = col.getTypedObject().getString("agent_name");
                String object_name = col.getTypedObject().getString("object_name");
                String force_inactive = col.getTypedObject().getString("force_inactive");
                String connected_server_id = col.getTypedObject().getString("connected_server_id");
                String dsearch_qrserver_host = "";
                String dsearch_qrserver_port = "";
                String dsearch_qrserver_protocol = "";

                dql = "select param_name,param_value from dm_ftengine_config_r  where r_object_id='" + connected_server_id + "' and param_name in ('dsearch_qrserver_host','dsearch_qrserver_port','dsearch_qrserver_protocol')";
                IDfCollection col_lineas = utilDocum.ejecutarDql(dql, gsesion);
                while (col_lineas.next()) {
                    String parametro = col_lineas.getTypedObject().getString("param_name");
                    if (parametro.equalsIgnoreCase("dsearch_qrserver_host")) {
                        dsearch_qrserver_host = col_lineas.getTypedObject().getString("param_value");;
                    }
                    if (parametro.equalsIgnoreCase("dsearch_qrserver_port")) {
                        dsearch_qrserver_port = col_lineas.getTypedObject().getString("param_value");;
                    }
                    if (parametro.equalsIgnoreCase("dsearch_qrserver_protocol")) {
                        dsearch_qrserver_protocol = col_lineas.getTypedObject().getString("param_value");;
                    }
                }
                String url = dsearch_qrserver_protocol + "://" + dsearch_qrserver_host + ":" + dsearch_qrserver_port + "/dsearchadmin";
                col_lineas.close();
                cadenaHTML.append("<tr bgcolor=\"white\"><td style=\"text-align:center\"><font color=\"black\" size=4> " + agent_name + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + object_name + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + connected_server_id + " </font></td>");
                cadenaHTML.append("<td><fontsize=4><a href=\"" + url + "\">" + url + "</a></font></td></tr>");

            }

            if (col != null) {
                col.close();
            }
        } catch (Exception ex) {
        }

        cadenaHTML.append("</TABLE><br><br>");

        try {
            dql = "select r_object_id,object_name,ldap_host,port_number,ssl_mode,ssl_port,a_application_type,import_mode,first_time_sync from dm_ldap_config";
            IDfCollection col = utilDocum.ejecutarDql(dql, gsesion);

            cadenaHTML.append("<TABLE BORDER CELLSPACING=0 WIDTH=\"100%\"><CAPTION ALIGN=top><b>SINCRONIZACION CON LDAP</b></CAPTION>"
                    + "<tr bgcolor=#f5f5f5><th>NOMBRE DEL OBJETO</th><th>ID</th><th>ESTADO</th><th>SERVIDOR</th><th>PUERTO</th>"
                    + "<th>TIPO DE DIRECTORIO</th><th>MODO DE IMPORTACION</th></tr>");

            while (col.next()) {
                String r_object_id = col.getTypedObject().getString("r_object_id");
                String estado = utilDocum.esActivoLDAP(r_object_id, gsesion);  //:"NO";
                String nombre = col.getTypedObject().getString("object_name");
                String servidor = col.getTypedObject().getString("ldap_host");
                String puerto = col.getTypedObject().getString("port_number");
                String puerto_ssl = col.getTypedObject().getString("ssl_port");
                puerto = (puerto_ssl.isEmpty() || puerto_ssl.equals("0")) ? puerto : puerto_ssl;
                String a_application_type = col.getTypedObject().getString("a_application_type");
                String tipo = utilDocum.descripcionTipoLDAP(a_application_type);
                String import_mode = col.getTypedObject().getString("import_mode");
                String modo = utilDocum.descripcionImportacionLDAP(import_mode);

                cadenaHTML.append("<tr bgcolor=\"white\"><td style=\"text-align:center\"><font color=\"black\" size=4> " + nombre + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + r_object_id + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + estado + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + servidor + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + puerto + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + tipo + " </font></td>");
                cadenaHTML.append("<td style=\"text-align:center\"><font color=\"black\" size=4> " + modo + " </font></td></tr>");
            }

            if (col != null) {
                col.close();
            }

            textoInfo.setText(cadenaHTML.toString());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        cadenaHTML.append("</TABLE><br>");
    }

    private void popupmenu(MouseEvent evt) {
        if (evt.isPopupTrigger() || botonderecho) {
            botonderecho = false;
            if (evt.getSource().getClass().getName().equals("javax.swing.JTextPane")) {
                popupEditar.show(evt.getComponent(), evt.getX(), evt.getY());

            }

        }
    }

}
