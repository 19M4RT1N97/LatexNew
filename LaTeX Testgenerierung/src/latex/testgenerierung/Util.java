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
    static ArrayList head = new ArrayList();
    static ArrayList dif1 = new ArrayList();
    static ArrayList dif2 = new ArrayList();
    static ArrayList dif3 = new ArrayList();

    public static void FragenAnzahl(String jtf) {
        //Textfeld auslesen und in Variable speichern
        //Variable enthällt anzahl der erwünschten Fragen
        fragenanzahl = Integer.parseInt(jtf);
    }

    public static void DiffFragenanzahl(String jtfdif1, String jtfdif2, String jtfdif3) {
        //Aus den Textfeldern des Schwierigkeitentabs werden die Inhalte herausgeholt und im Setanzahl gespeichert.
        setAnzahlDif(0, jtfdif1);
        setAnzahlDif(1, jtfdif2);
        setAnzahlDif(2, jtfdif3);

    }

    public static void setAnzahlDif(int index, String jtf) {
        //Fals das Übergebene Textfeld leer ist wird eine 0 gespeichert, fals nicht wird es in einem Int[] gespeichert.
        if (!jtf.equals("")) {
            anzahldif[index] = Integer.parseInt(jtf);
        } else {
            anzahldif[index] = 0;
        }
    }

    public static void ThemenRead(String jtfthema1, String jtfthema2) {
        // Die beiden Felder des Thementabs werden eingelesen und gespeichert
        //!!ACHTUNG!! THEMA2 DARF LEER SEIN
        thema1 = jtfthema1;
        if (!jtfthema2.equals("")) {
            thema2 = jtfthema2;
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
    public static void readFile(String path) {
        BufferedReader br = null;
        try {
            //Dokument mit File Chooser einlesen

            FileReader fr = new FileReader(new File(path));

            br = new BufferedReader(fr);
            boolean questions = false;
            //Arraylist Erstellen und mit 10 Elementen Befüllen (OutOfBounce vorbeugung)
            String fullline = "";
            int difficulty = 0;
            boolean lineadd = true;
            while (br.ready()) {
                String line = br.readLine();
                if (line.contains("\\begin{questions}")) {
                    fullline += line + " ";
                    head.add(fullline+" | ");
                    fullline = "";
                    lineadd=false;
                }
                if (line.contains("\\end{document}")) {
                    fullline += line + " ";
                    LineSave(difficulty, fullline+" | ");
                    fullline = "";
                    lineadd = false;
                } else if (line.contains("\\end{minipage}")) {

                    fullline += line + " ";
                    LineSave(difficulty, fullline);
                    fullline = "";
                    lineadd = false;
                }

                //Difficulty attribut auslesen und Festlegen
                if (line.contains("% @difficulty")) {
                    String[] split = line.split("=");
                    difficulty = Integer.parseInt(split[1]);
                }
                if (lineadd) {
                    fullline += line + " ; ";
                } else {
                    lineadd = true;
                }
            }
            System.out.println("" + head.toString());
            System.out.println("" + dif1.toString());
            System.out.println("" + dif2.toString());

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

    public static void LineSave(int difficulty, String fullline) {
        //durch die Difficulty wird der Ensprechende Array zur Speicherung gewählt
        switch (difficulty) {
            case 1:
                dif1.add(fullline);
                break;
            case 2:
                dif2.add(fullline);
                break;
            case 3:
                dif3.add(fullline);
                break;
        }
    }

    public static void FileSave(DefaultTableModel dm, String absolutepath) {

        BufferedWriter bw = null;
        try {

            bw = new BufferedWriter(new FileWriter(new File(absolutepath)));

            //GetCoords holt den Index (Position inerhalb der Frageliste) und der Schwierigkeit (Welche Frageliste)
            int[][] coords = Docreader.GetCoords(dm);
            
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
                }
            }
            
            bw.close();

        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static DefaultTableModel fillTable(DefaultTableModel dm, JTextField diff) {
        //Switch auf Schwierigkeit -1 um das 0te Element zu erhalten
        int schwierigkeit = Integer.parseInt(diff.getText());
        DefaultTableModel dmnew = null;
        switch (schwierigkeit - 1) {
            case 0:
                dmnew = dofillTable(dm, diff, dif1);
                break;
            case 1:
                dofillTable(dm, diff, dif2);
                break;
            case 2:
                dofillTable(dm, diff, dif3);
                break;
        }
        return dmnew;
    }

    public static DefaultTableModel fillTable(DefaultTableModel dm) {
        //table wird gefüllt 
        DefaultTableModel dmnew = null;
        for (int index = 0; index < anzahldif.length; index++) {
            if (anzahldif[index] != 0) {
                switch (index) {
                    case 0:
                        dmnew = dofillTable(dm, anzahldif[index], dif1);
                        break;
                    case 1:
                        dmnew = dofillTable(dm, anzahldif[index], dif2);
                        break;
                    case 2:
                        dmnew = dofillTable(dm, anzahldif[index], dif3);
                        break;
                }
            }
        }
        return dmnew;
    }

    public static DefaultTableModel dofillTable(DefaultTableModel dm, int difanzahl, ArrayList dif) {
        //Auslesen der Ausgewählten Fragen jedoch leeres Textfeld
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

        }
        return dm;
    }

    public static DefaultTableModel dofillTable(DefaultTableModel dm, JTextField diff, ArrayList dif) {
        //Auslesen der Ausgewählten Fragen jedoch gefülltes Textfeld

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

        return dm;
    }

    public static DefaultTableModel TableLoeschen(DefaultTableModel dm, int row) {
        //löschen der ausgewählten Zeile in der Table

        if (row > -1) {
            dm.removeRow(row);
        }
        return dm;

    }

    public static void BacktoMultiSingel() {
        //Falls Zurückbutton gedrückt Textfelder der Schwierigkeit geleert
        for (int i = 0; i < anzahldif.length; i++) {
            anzahldif[i] = 0;
        }
    }
    
    public static void createLaTexDoc(String path) throws IOException
    {
        
        
        FileWriter writer = new FileWriter(path);
        String header= head.toString();
        String[] headerArray=header.split(";");
        
        for(int i=0; i<headerArray.length;i++)
        {
            writer.write(headerArray[i].toString());
        }
        
    }
}
