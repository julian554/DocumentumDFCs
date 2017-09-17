package es.documentum.utilidades;

import java.io.Serializable;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class TablaSinEditarCol extends DefaultTableModel implements Serializable {

    private static Vector newVector(int size) {
        Vector v = new Vector(size);
        v.setSize(size);
        return v;
    }

    public TablaSinEditarCol() {
        this(0, 0);
    }

    public TablaSinEditarCol(int numfilas, int numcolumnas) {
        this(newVector(numcolumnas), numfilas);
    }

    public TablaSinEditarCol(Vector columnas, int numfilas) {
        setDataVector(newVector(numfilas), columnas);
    }

    public TablaSinEditarCol(String[][] campos, String[] cabeceras) {
        setDataVector(campos, cabeceras);
    }

    public TablaSinEditarCol(Vector<Vector<String>> campos, Vector<String> cabeceras) {
        setDataVector(campos, cabeceras);
    }

    public TablaSinEditarCol(Object[][] campos, Object[] cabeceras) {
        setDataVector(campos, cabeceras);
    }

    @Override
    public boolean isCellEditable(int fila, int columna) {
<<<<<<< HEAD
        if (columna == 0 || columna == 1 || columna == 2 || columna == 3 || columna == 4 || columna == 5
            || columna == 6 || columna == 7 || columna == 8 || columna == 9 || columna == 10 || columna == 11) {
=======
        if (columna == 0 || columna == 1 || columna == 2 || columna == 3 || columna == 4 || columna == 5) {
>>>>>>> origin/master
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Class getColumnClass(int c) {
<<<<<<< HEAD
        Class clase = "".getClass();
        if (c < 0) {
            return clase;
        }
        try {
            if (getValueAt(0, c) == null) {
                return clase;
            }
        } catch (Exception ex) {
        }
        if (c == 5) {
            return ImageIcon.class;
        }
        
        try {
            clase = getValueAt(0, c).getClass();
        } catch (Exception ex) {
        }
        return clase;
=======
        if (c < 0) {
            return "".getClass();
        }
        if (getValueAt(0, c) == null) {
            return "".getClass();
        }
        if (c==5){
            return ImageIcon.class;
        }
        return getValueAt(0, c).getClass();
>>>>>>> origin/master
    }

}
