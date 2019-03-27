package es.documentum.pruebas;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class ImportarFicheroExcel {

    // private static final String FILE_NAME = "D:\\Adif\\Documentum\\USUARIOS_LDAP_6M_NO_CONNECT.xls";
    private static final String FILE_NAME = "C:\\Users\\E274399\\Desktop\\attr-mio.xlsx";

    public static void main(String[] args) {
        ArrayList libro = importarExcelEnArrayList(FILE_NAME);
//        try {
//            Workbook workbook = WorkbookFactory.create(new File(FILE_NAME));
//            Sheet datatypeSheet = workbook.getSheetAt(0);
//            Iterator<Row> iterator = datatypeSheet.iterator();
//
//            while (iterator.hasNext()) {
//                Row currentRow = iterator.next();
//                Iterator<Cell> cellIterator = currentRow.iterator();
//                ArrayList celdas = new ArrayList();
//                while (cellIterator.hasNext()) {
//                    Cell currentCell = cellIterator.next();
//                    String dato = "";
//                    //getCellTypeEnum shown as deprecated for version 3.15
//                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
//                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
//                        dato = currentCell.getStringCellValue();
////                        System.out.print(dato + "--");
//                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
//                        dato = currentCell.getNumericCellValue() + "";
////                        System.out.print(dato + "--");
//                    }
//
//                    celdas.add(dato);
//                }
//                libro.add(celdas);
////                System.out.println();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidFormatException ex) {
//            Logger.getLogger(ImportarFicheroExcel.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (EncryptedDocumentException ex) {
//            Logger.getLogger(ImportarFicheroExcel.class.getName()).log(Level.SEVERE, null, ex);
//        }

        for (int x = 0; x < libro.size(); x++) {
            ArrayList celdas = (ArrayList) libro.get(x);
            for (int j = 0; j < celdas.size(); j++) {
                System.out.print(celdas.get(j));
                if (j + 1 < celdas.size()) {
                    System.out.print(" -- ");
                }
            }
            System.out.println();
        }

    }

    public static ArrayList<String> importarExcelEnArrayList(String fichero) {
        ArrayList libro = new ArrayList();
        try {
            Workbook workbook = WorkbookFactory.create(new File(fichero));
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                ArrayList celdas = new ArrayList();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    String dato = "";
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    CellType valorCelda = currentCell.getCellTypeEnum();

                    switch (valorCelda) {
                        case STRING:
                            dato = currentCell.getStringCellValue();
                            break;
                        case NUMERIC:
                            dato = currentCell.getNumericCellValue() + "";
                            break;
                        case BOOLEAN:
                            dato = currentCell.getBooleanCellValue() ? "true" : "false";
                            break;
                        default:
                            dato = currentCell.getStringCellValue();
                    }
                    celdas.add(dato);
                }
                libro.add(celdas);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error - " + e.getMessage());
        } catch (InvalidFormatException ex) {
            System.out.println("Error - " + ex.getMessage());
        } catch (EncryptedDocumentException ex) {
            System.out.println("Error - " + ex.getMessage());
        }
        return libro;
    }

}
