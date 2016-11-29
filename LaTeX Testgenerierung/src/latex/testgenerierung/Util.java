/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latex.testgenerierung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Martin
 */
public class Util {

    static int fragenanzahl;
    static int[] anzahldif = new int[10];
    static String thema1;
    static String thema2;
    static ArrayList frage = new ArrayList();
    static ArrayList header = new ArrayList();
    static ArrayList footer = new ArrayList();
    static ArrayList result = new ArrayList();
    static ArrayList description = new ArrayList();
    static ArrayList dif1 = new ArrayList();
    static ArrayList dif2 = new ArrayList();
    static ArrayList dif3 = new ArrayList();
    static ArrayList dif4 = new ArrayList();
    static ArrayList dif5 = new ArrayList();
    static ArrayList dif6 = new ArrayList();
    static ArrayList dif7 = new ArrayList();
    static ArrayList dif8 = new ArrayList();
    static ArrayList dif9 = new ArrayList();
    static ArrayList dif10 = new ArrayList();

    public static void Zurueck(JTabbedPane jp) {
        //Einen Tab Zurück
        int index = jp.getSelectedIndex();
        jp.setSelectedIndex(index - 1);

    }

    public static void FragenAnzahl(JTextField jtf) {
        //Textfeld auslesen und in Variable speichern
        //Variable enthällt anzahl der erwünschten Fragen
        fragenanzahl = Integer.parseInt(jtf.getText());
    }

    public static void DiffFragenanzahl(JTextField jtfdif1, JTextField jtfdif2, JTextField jtfdif3, JTextField jtfdif4,
            JTextField jtfdif5, JTextField jtfdif6, JTextField jtfdif7, JTextField jtfdif8, JTextField jtfdif9, JTextField jtfdif10) {
        //Aus den Textfeldern des Schwierigkeitentabs werden die Inhalte herausgeholt und im Setanzahl gespeichert.
        setAnzahlDif(0, jtfdif1);
        setAnzahlDif(1, jtfdif2);
        setAnzahlDif(2, jtfdif3);
        setAnzahlDif(3, jtfdif4);
        setAnzahlDif(4, jtfdif5);
        setAnzahlDif(5, jtfdif6);
        setAnzahlDif(6, jtfdif7);
        setAnzahlDif(7, jtfdif8);
        setAnzahlDif(8, jtfdif9);
        setAnzahlDif(9, jtfdif10);

    }

    public static void setAnzahlDif(int index, JTextField jtf) {
        //Fals das Übergebene Textfeld leer ist wird eine 0 gespeichert, fals nicht wird es in einem Int[] gespeichert.
        if (!jtf.getText().isEmpty()) {
            anzahldif[index] = Integer.parseInt(jtf.getText());
        } else {
            anzahldif[index] = 0;
        }
    }

    public static void ThemenRead(JTextField jtfthema1, JTextField jtfthema2) {
        // Die beiden Felder des Thementabs werden eingelesen und gespeichert
        //!!ACHTUNG!! THEMA2 DARF LEER SEIN
        thema1 = jtfthema1.getText();
        if (!jtfthema2.getText().isEmpty()) {
            thema2 = jtfthema2.getText();
        }
    }

    public static boolean SumDif() {
        //Anzahl der Fragen und Anzahl der Schwierigkeiten müssen übereinstimmen
        int sum = 0;
        for (int i = 0; i < anzahldif.length; i++) {
            sum += anzahldif[i];
        }
        if (sum == fragenanzahl) {
            return true;
        } else {
            return false;
        }
    }

    /*
    Lest das LaTeX Dokument mit hilfe eines BufferedReader und speichert die einzelnen Fragen in eine Arraylist
    
     */
    public static void readFile() {
        BufferedReader br = null;
        try {
            //Dokument mit File Chooser einlesen
//            JFileChooser jfc = new JFileChooser();
//            jfc.showOpenDialog(jfc);
//             FileReader fr = new FileReader(new File(jfc.getSelectedFile().getAbsolutePath()));
            FileReader fr = new FileReader(new File("Testsammlung.tex"));
            br = new BufferedReader(fr);
            boolean questions = false;
            //Arraylist Erstellen und mit 10 Elementen Befüllen (OutOfBounce vorbeugung)

            int difficulty = 0;

            while (br.ready()) {
                String line = br.readLine();
                //header Attribut auslesen
                if (line.contains("% @header")) {
                    frage.clear();
                }
                //footer Attribut auslesen
                if (line.contains("% @footer")) {
                    header.add(frage.clone());
                    frage.clear();
                }
                //result Attribut auslesen
                if (line.contains("% @result")) {
                    footer.add(frage.clone());
                    frage.clear();
                }
                //Description Attribut auslesen
                if (line.contains("% @description")) {
                    result.add(frage.clone());
                    frage.clear();
                }
                //Prüfung Frage ende also speichern oder nicht
                if (line.contains("% @name") && questions) {
                    ArraySave(difficulty, line);
                    //vor 1. Frage Liste von unnötigen Items clearen
                } else if (line.contains("% @name") && difficulty == 0) {
                    description.add(frage.clone());
                    frage.clear();
                    frage.add(line);
                    questions = true;

                } else {
                    //Line in Arraylist speichern
                    frage.add(line);
                }
                //Difficulty attribut auslesen und Festlegen
                if (line.contains("% @difficulty")) {
                    String[] split = line.split("=");
                    difficulty = Integer.parseInt(split[1]) - 1;
                }

            }

            ArraySave(difficulty, "");
            System.out.println("" + dif1.toString());
            System.out.println("" + dif2.toString());
            System.out.println("" + header.toString());
            System.out.println("" + footer.toString());
            System.out.println("" + result.toString());
            System.out.println("" + description.toString());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void ArraySave(int difficulty, String line) {
        //durch die Difficulty wird der Ensprechende Array zur Speicherung gewählt
        switch (difficulty) {
            case 0:
                Docreader.DocSave(frage, dif1);
                frage.add(line);
            case 1:
                Docreader.DocSave(frage, dif2);
                frage.add(line);
            case 2:
                Docreader.DocSave(frage, dif3);
                frage.add(line);
            case 3:
                Docreader.DocSave(frage, dif4);
                frage.add(line);
            case 4:
                Docreader.DocSave(frage, dif5);
                frage.add(line);
            case 5:
                Docreader.DocSave(frage, dif6);
                frage.add(line);
            case 6:
                Docreader.DocSave(frage, dif7);
                frage.add(line);
            case 7:
                Docreader.DocSave(frage, dif8);
                frage.add(line);
            case 8:
                Docreader.DocSave(frage, dif9);
                frage.add(line);
            case 9:
                Docreader.DocSave(frage, dif10);
                frage.add(line);
        }
    }

    public static void FileSave(JTable jt) {

        BufferedWriter bw = null;
        try {
            JFileChooser jfc = new JFileChooser();
            jfc.showSaveDialog(jfc);
            bw = new BufferedWriter(new FileWriter(new File(jfc.getSelectedFile().getAbsolutePath())));
            DefaultTableModel dm = (DefaultTableModel) jt.getModel();
            //GetCoords holt den Index (Position inerhalb der Frageliste) und der Schwierigkeit (Welche Frageliste)
            int[][] coords = Docreader.GetCoords(dm);
            Docreader.DocOutStandart(header, bw);
            Docreader.DocOutStandart(description, bw);
            Docreader.DocOutStandart(result, bw);
            for (int index = 0; index < coords.length; index++) {
                int schwer = coords[index][1];
                switch (schwer - 1) {
                    case 0:
                        Docreader.DocOut(dif1, bw, coords[index][0]);
                        break;
                    case 1:
                        Docreader.DocOut(dif2, bw, coords[index][0]);
                        break;
                    case 2:
                        Docreader.DocOut(dif3, bw, coords[index][0]);
                        break;
                    case 3:
                        Docreader.DocOut(dif4, bw, coords[index][0]);
                        break;
                    case 4:
                        Docreader.DocOut(dif5, bw, coords[index][0]);
                        break;
                    case 5:
                        Docreader.DocOut(dif6, bw, coords[index][0]);
                        break;
                    case 6:
                        Docreader.DocOut(dif7, bw, coords[index][0]);
                        break;
                    case 7:
                        Docreader.DocOut(dif8, bw, coords[index][0]);
                        break;
                    case 8:
                        Docreader.DocOut(dif9, bw, coords[index][0]);
                        break;
                    case 9:
                        Docreader.DocOut(dif10, bw, coords[index][0]);
                        break;
                }
            }
            Docreader.DocOutStandart(footer, bw);
            bw.close();

        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void fillTable(JTable jt, JTextField diff) {
        //Switch auf Schwierigkeit -1 um das 0te Element zu erhalten
        int schwierigkeit = Integer.parseInt(diff.getText());
        switch (schwierigkeit - 1) {
            case 0:
                dofillTable(jt, diff, dif1);
                break;
            case 1:
                dofillTable(jt, diff, dif2);
                break;
            case 2:
                dofillTable(jt, diff, dif3);
                break;
            case 3:
                dofillTable(jt, diff, dif4);
                break;
            case 4:
                dofillTable(jt, diff, dif5);
                break;
            case 5:
                dofillTable(jt, diff, dif6);
                break;
            case 6:
                dofillTable(jt, diff, dif7);
                break;
            case 7:
                dofillTable(jt, diff, dif8);
                break;
            case 8:
                dofillTable(jt, diff, dif9);
                break;
            case 9:
                dofillTable(jt, diff, dif10);
                break;
        }
    }

    public static void fillTable(JTable jt) {
        //table wird gefüllt 
        for (int index = 0; index < anzahldif.length; index++) {
            if (anzahldif[index] != 0) {
                switch (index) {
                    case 0:
                        dofillTable(jt, anzahldif[index], dif1);
                        break;
                    case 1:
                        dofillTable(jt, anzahldif[index], dif2);
                        break;
                    case 2:
                        dofillTable(jt, anzahldif[index], dif3);
                        break;
                    case 3:
                        dofillTable(jt, anzahldif[index], dif4);
                        break;
                    case 4:
                        dofillTable(jt, anzahldif[index], dif5);
                        break;
                    case 5:
                        dofillTable(jt, anzahldif[index], dif6);
                        break;
                    case 6:
                        dofillTable(jt, anzahldif[index], dif7);
                        break;
                    case 7:
                        dofillTable(jt, anzahldif[index], dif8);
                        break;
                    case 8:
                        dofillTable(jt, anzahldif[index], dif9);
                        break;
                    case 9:
                        dofillTable(jt, anzahldif[index], dif10);
                        break;
                }
            }
        }
    }

    public static void dofillTable(JTable jt, int difanzahl, ArrayList dif) {
        //Auslesen der Ausgewählten Fragen jedoch leeres Textfeld
        DefaultTableModel dm = (DefaultTableModel) jt.getModel();
        int counter = 0;
        String[] line = null;

        for (int index = 0; index < dif.size(); index++) {
            ArrayList temp = (ArrayList) dif.get(index);
            line = new String[5];

            for (int i = 0; i < temp.size(); i++) {
                if (temp.size() > 4) {
                    if (temp.get(i).toString().contains("% @name")) {
                        line[i] = index + "";
                        String[] split = temp.get(i).toString().split("=");
                        line[i + 1] = split[1];
                    } else if (temp.get(i).toString().contains("% @topic")) {
                        String[] split = temp.get(i).toString().split("=");
                        line[i + 1] = split[1];
                    } else if (temp.get(i).toString().contains("% @difficulty")) {
                        String[] split = temp.get(i).toString().split("=");
                        line[i + 1] = split[1];
                    } else if (temp.get(i).toString().contains("% @size")) {
                        String[] split = temp.get(i).toString().split("=");
                        line[i + 1] = split[1];
                    } else {
                        break;
                    }
                }

            }
            if (thema2 != null) {
                if (temp.size() > 4 && (line[2].contains(thema1) || line[2].contains(thema2))) {
                    dm.addRow(line);
                    counter++;
                    if (counter == difanzahl) {
                        break;
                    }
                    line = null;
                } else {
                    line = null;
                }
            } else if (temp.size() > 4 && line[2].contains(thema1)) {
                dm.addRow(line);
                counter++;
                if (counter == difanzahl) {
                    break;
                }
                line = null;
            } else {
                line = null;
            }

            jt.setModel(dm);
        }
    }

    public static void dofillTable(JTable jt, JTextField diff, ArrayList dif) {
        //Auslesen der Ausgewählten Fragen jedoch gefülltes Textfeld
        DefaultTableModel dm = (DefaultTableModel) jt.getModel();

        String[] line = null;

        for (int index = 0; index < dif.size(); index++) {
            ArrayList temp = (ArrayList) dif.get(index);
            line = new String[5];

            for (int i = 0; i < temp.size(); i++) {

                if (temp.size() > 4) {
                    if (temp.get(i).toString().contains("% @name")) {
                        line[i] = index + "";
                        String[] split = temp.get(i).toString().split("=");
                        line[i + 1] = split[1];
                    } else if (temp.get(i).toString().contains("% @topic")) {
                        String[] split = temp.get(i).toString().split("=");
                        line[i + 1] = split[1];
                    } else if (temp.get(i).toString().contains("% @difficulty")) {
                        String[] split = temp.get(i).toString().split("=");
                        line[i + 1] = split[1];
                    } else if (temp.get(i).toString().contains("% @size")) {
                        String[] split = temp.get(i).toString().split("=");
                        line[i + 1] = split[1];
                    } else {
                        break;
                    }
                }
            }
            if (temp.size() > 4) {
                dm.addRow(line);
                line = null;
            }
        }

        jt.setModel(dm);
    }

    public static void TableLoeschen(JTable jt) {
        //löschen der ausgewählten Zeile in der Table
        if (jt.getSelectedRow() != -1) {
            DefaultTableModel dm = (DefaultTableModel) jt.getModel();
            dm.removeRow(jt.getSelectedRow());
            jt.setModel(dm);
        }
    }

    public static void BacktoAnzahl(JTextField tm1, JTextField tm2) {
        //Falls Zurückbutton gedrückt Themenfelder Geleehrt
        tm1.setText("");
        tm2.setText("");
        thema1 = "";
        thema2 = "";
    }

    public static void BacktoMultiSingel(JTextField schwer1, JTextField schwer2, JTextField schwer3, JTextField schwer4, JTextField schwer5,
            JTextField schwer6, JTextField schwer7, JTextField schwer8, JTextField schwer9, JTextField schwer10) {
        //Falls Zurückbutton gedrückt Textfelder der Schwierigkeit geleert
        schwer1.setText("");
        schwer2.setText("");
        schwer3.setText("");
        schwer4.setText("");
        schwer5.setText("");
        schwer6.setText("");
        schwer7.setText("");
        schwer8.setText("");
        schwer9.setText("");
        schwer10.setText("");
        for (int i = 0; i < anzahldif.length; i++) {
            anzahldif[i] = 0;
        }
    }
}
