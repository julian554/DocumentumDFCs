package es.documentum.utilidades;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;

public class CambiarColorTexto extends DefaultStyledDocument {

    Utilidades util = new Utilidades();
    private static final long serialVersionUID = 1L;
    private final Style _defaultStyle;
    private final Style _cwStyle;
    private final Style _comillaStyle;
    private String _tipoSQL;

    public String getTipoSQL() {
        return _tipoSQL;
    }

    public void setTipoSQL(String _tipoSQL) {
        this._tipoSQL = _tipoSQL;
    }

    public CambiarColorTexto(Style defaultStyle, Style cwStyle, Style comillaStyle, String tipoSQL) {
        _defaultStyle = defaultStyle;
        _cwStyle = cwStyle;
        _tipoSQL = tipoSQL;
        _comillaStyle = comillaStyle;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offset, str, a);
        refreshDocument();
    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        super.remove(offs, len);
        refreshDocument();
    }

    private synchronized void refreshDocument() throws BadLocationException {
        String text = getText(0, getLength());
        final List<HiliteWord> list = processWords(text);
        setCharacterAttributes(0, text.length(), _defaultStyle, true);
        for (HiliteWord word : list) {
            int p0 = word._position;
            setCharacterAttributes(p0, word._word.length(), _cwStyle, true);

        }
        final List<HiliteWord> listaComillaSimple = palabrasEntreCaracteres(text, "'", "'");
        for (HiliteWord word : listaComillaSimple) {
            int p0 = word._position;
            setCharacterAttributes(p0, word._word.length(), _comillaStyle, true);
        }
        final List<HiliteWord> listaComillaDoble = palabrasEntreCaracteres(text, "\"", "\"");
        for (HiliteWord word : listaComillaDoble) {
            int p0 = word._position;
            setCharacterAttributes(p0, word._word.length(), _comillaStyle, true);
        }
    }

    private List<HiliteWord> processWords(String content) {
        content += " ";
        List<HiliteWord> hiliteWords = new ArrayList<>();
        int lastWhitespacePosition = 0;
        String word = "";
        char[] data = content.toCharArray();

        for (int index = 0; index < data.length; index++) {
            char ch = data[index];
            if (!(Character.isLetter(ch) || Character.isDigit(ch) || ch == '_')) {
                lastWhitespacePosition = index;
                if (word.length() > 0) {
                    if (util.esPalabraReservadaSQL(word, _tipoSQL)) {
                        hiliteWords.add(new HiliteWord(word, (lastWhitespacePosition - word.length())));
                    }
                    word = "";
                }
            } else {
                word += ch;
            }
        }
        return hiliteWords;
    }

    private List<HiliteWord> palabrasEntreCaracteres(final String str, final String open, final String close) {
        List<HiliteWord> hiliteWords = new ArrayList<>();
        if (str == null || isEmpty(open) || isEmpty(close)) {
            return hiliteWords;
        }
        final int strLen = str.length();
        if (strLen == 0) {
            return hiliteWords;
        }
        final int closeLen = close.length();
        final int openLen = open.length();
        int pos = 0;
        while (pos < strLen - closeLen) {
            int start = str.indexOf(open, pos);
            if (start < 0) {
                break;
            }
            start += openLen;
            final int end = str.indexOf(close, start);
            if (end < 0) {
                break;
            }

            hiliteWords.add(new HiliteWord(str.substring(start - 1, end + 1), start - 1));
            pos = end + closeLen;
        }
        return hiliteWords;
    }

    private boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

}

class HiliteWord {

    int _position;
    String _word;

    public HiliteWord(String word, int position) {
        _position = position;
        _word = word;
    }
}
