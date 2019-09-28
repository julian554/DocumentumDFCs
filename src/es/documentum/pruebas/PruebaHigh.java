package es.documentum.pruebas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class PruebaHigh {

    public static void main(String[] args) {
        final JTextArea textarea = new JTextArea(25, 80);
        textarea.setText("I'm trying to make a word search engine ... method.");
        Highlighter highlighter = new DefaultHighlighter();
        final DefaultHighlightPainter painter = new DefaultHighlightPainter(Color.pink);
        textarea.setHighlighter(highlighter);

        final JTextField highlight = new JTextField(80);
        highlight.getDocument().addDocumentListener(new DocumentListener() {

            private void updateHighlights() {
                highlight(textarea, highlight.getText(), painter);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateHighlights();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateHighlights();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateHighlights();
            }
        });

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(highlight, BorderLayout.NORTH);
        frame.add(textarea, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(600, 400));
        frame.pack();
        frame.setVisible(true);
    }

    public static void highlight(JTextArea textarea, String textToHighlight,
            HighlightPainter painter) {
        String text = textarea.getText();
        Highlighter highlighter = textarea.getHighlighter();
        highlighter.removeAllHighlights();

        if (!textToHighlight.isEmpty()) {
            Matcher m = compileWildcard(textToHighlight).matcher(text);
            while (m.find()) {
                try {
                    highlighter.addHighlight(m.start(), m.end(), painter);
                } catch (BadLocationException e) {
                    throw new IllegalStateException(e);
                    /* cannot happen */
                }
                textarea.setCaretPosition(m.end());
            }
        }
    }

    public static Pattern compileWildcard(String wildcard) {
        StringBuilder sb = new StringBuilder("\\b");
        /* word boundary */
 /* the following replaceAll is just for performance */
        for (char c : wildcard.replaceAll("\\*+", "*").toCharArray()) {
            if (c == '*') {
                sb.append("\\S*");
                /*- arbitrary non-space characters */
            } else {
                sb.append(Pattern.quote(String.valueOf(c)));
            }
        }
        sb.append("\\b");
        /* word boundary */
        return Pattern.compile(sb.toString());
    }

}
