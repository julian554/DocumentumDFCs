package es.documentum.pruebas;

import es.documentum.utilidades.CambiarColorTexto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class EditorSQL extends JFrame {

    public EditorSQL() {
        StyleContext styleContext = new StyleContext();
        Style defaultStyle = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
        Style cwStyle = styleContext.addStyle("ConstantWidth", null);
        StyleConstants.setForeground(cwStyle, Color.BLUE);
        StyleConstants.setBold(cwStyle, true);
        Style comillaStyle = styleContext.addStyle("ConstantWidth", null);
        StyleConstants.setBold(comillaStyle, true);
        JTextPane pane = new JTextPane(new CambiarColorTexto(defaultStyle, cwStyle, comillaStyle, "oracle"));
//        final JTextArea pane = new JTextArea(new CambiarColorTexto(defaultStyle, cwStyle));
        pane.setFont(new Font("Courier New", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(pane);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(675, 400);
    }

    public static void main(String[] args) throws BadLocationException {
        EditorSQL app = new EditorSQL();
        app.setVisible(true);
    }
}
