/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latex.testgenerierung;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Martin
 */
public class Util {

    static LinkedList<Question> QuestionList = new LinkedList<>();

    static int fragenanzahl;
    static int[] anzahldif = new int[3];
    static LinkedList<String> thema = new LinkedList<>();
    static int single;
    static int multiple;
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

    public static LinkedList themenRead(){
        for(int index=0;index<dif1.size();index++){
         String difficulty1= dif1.get(index).toString();
         String[] dif1Array = difficulty1.split(";");
         HeadAtt ha = new HeadAtt(dif1Array);
         if(!thema.contains(ha.Thema)){
             thema.add(ha.Thema);
         }
        }
         for(int index=0;index<dif2.size();index++){
         String difficulty1= dif2.get(index).toString();
         String[] dif1Array = difficulty1.split(";");
         HeadAtt ha = new HeadAtt(dif1Array);
         if(!thema.contains(ha.Thema)){
             thema.add(ha.Thema);
         }
        }
          for(int index=0;index<dif3.size();index++){
         String difficulty1= dif3.get(index).toString();
         String[] dif1Array = difficulty1.split(";");
         HeadAtt ha = new HeadAtt(dif1Array);
         if(!thema.contains(ha.Thema)){
             thema.add(ha.Thema);
         }
        }
        return  thema;
    }
    
    public static int getThemencount(String thema){
        int  anzahl=0;
        for(int index=0;index<dif1.size();index++){
         String difficulty1= dif1.get(index).toString();
         String[] dif1Array = difficulty1.split(";");
         HeadAtt ha = new HeadAtt(dif1Array);
         if(thema.equals(ha.Thema)){
            anzahl++;     
         }
        }
         for(int index=0;index<dif2.size();index++){
         String difficulty2= dif2.get(index).toString();
         String[] dif2Array = difficulty2.split(";");
         HeadAtt ha = new HeadAtt(dif2Array);
         if(thema.equals(ha.Thema)){
            anzahl++;     
         }
        }
          for(int index=0;index<dif3.size();index++){
         String difficulty3= dif3.get(index).toString();
         String[] dif3Array = difficulty3.split(";");
         HeadAtt ha = new HeadAtt(dif3Array);
         if(thema.equals(ha.Thema)){
            anzahl++;     
         }
        }
        return anzahl;
    }
    public static int[] getDifMaxCount(String thema){
        int[]  anzahl=new int[3];
        for(int index=0;index<dif1.size();index++){
         String difficulty1= dif1.get(index).toString();
         String[] dif1Array = difficulty1.split(";");
         HeadAtt ha = new HeadAtt(dif1Array);
         if(thema.equals(ha.Thema)){
            anzahl[0]++;     
         }
        }
         for(int index=0;index<dif2.size();index++){
         String difficulty2= dif2.get(index).toString();
         String[] dif2Array = difficulty2.split(";");
         HeadAtt ha = new HeadAtt(dif2Array);
         if(thema.equals(ha.Thema)){
            anzahl[1]++;     
         }
        }
          for(int index=0;index<dif3.size();index++){
         String difficulty3= dif3.get(index).toString();
         String[] dif3Array = difficulty3.split(";");
         HeadAtt ha = new HeadAtt(dif3Array);
         if(thema.equals(ha.Thema)){
            anzahl[2]++;     
         }
        }
        return anzahl;
    }
    
    public static void setSinlgeMultiple(String multi, String si) {
        if (!si.equals("")) {
            single = Integer.parseInt(si);
        }
        if (!multi.equals("")) {
            multiple = Integer.parseInt(multi);
        }
    }

    public static String backtoTopic() {
        return "";
    }

    public static void BacktoMultiSingel() {
        //Falls Zurückbutton gedrückt Textfelder der Schwierigkeit geleert
        for (int i = 0; i < anzahldif.length; i++) {
            anzahldif[i] = 0;
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
                    head.add(fullline);
                    fullline = "";
                    lineadd = false;
                }
                if (line.contains("\\end{document}")) {
                    fullline += line + " ";
                    head.add(fullline);
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
            System.out.println("" + dif3.toString());
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

    public static DefaultTableModel fillTable(DefaultTableModel dm,String thema) {
        if (anzahldif[0] > 0) {
            ArrayList diftemp = (ArrayList) dif1.clone();
            for (int i = 0; i < anzahldif[0];) {
                int rindex = (int) round(Math.random() * (diftemp.size() - 1));
                if (!(rindex > diftemp.size())) {
                    String line = (String) diftemp.get(rindex);
                    String[] split = line.split(";");
                    HeadAtt ha = new HeadAtt(split);
                    Question q = new Question(split);
                    if (q.thema.contains(thema)) {
                        i++;
                        if (single == 0 && multiple == 0) {
                            dm.addRow(new Object[]{ha.name, ha.Thema, ha.Schwierigkeit});
                            QuestionList.addLast(q);
                            diftemp.remove(rindex);
                            i++;
                        } else if (single != 0 && isSingle(q)) {
                            dm.addRow(new Object[]{ha.name, ha.Thema, ha.Schwierigkeit});
                            QuestionList.addLast(q);
                            diftemp.remove(rindex);
                            single -= 1;
                            i++;
                        } else if (multiple != 0 && !isSingle(q)) {
                            dm.addRow(new Object[]{ha.name, ha.Thema, ha.Schwierigkeit});
                            QuestionList.addLast(q);
                            diftemp.remove(rindex);
                            multiple -= 1;
                            i++;
                        }
                    }
                }
            }
        }
        if (anzahldif[1] > 0) {
            ArrayList diftemp = (ArrayList) dif2.clone();
            for (int i = 0; i < anzahldif[1];) {
                int rindex = (int) round(Math.random() * (diftemp.size() - 1));
                if (!(rindex > diftemp.size())) {
                    String line = (String) diftemp.get(rindex);
                    String[] split = line.split(";");
                    HeadAtt ha = new HeadAtt(split);
                    Question q = new Question(split);
                    if (q.thema.contains(thema)) {
                        i++;
                        if (single == 0 && multiple == 0) {
                            dm.addRow(new Object[]{ha.name, ha.Thema, ha.Schwierigkeit});
                            QuestionList.addLast(q);
                            diftemp.remove(rindex);
                            i++;
                        } else if (single != 0 && isSingle(q)) {
                            dm.addRow(new Object[]{ha.name, ha.Thema, ha.Schwierigkeit});
                            QuestionList.addLast(q);
                            diftemp.remove(rindex);
                            single-=1;
                            i++;
                        } else if (multiple != 0 && !isSingle(q)) {
                            dm.addRow(new Object[]{ha.name, ha.Thema, ha.Schwierigkeit});
                            QuestionList.addLast(q);
                            diftemp.remove(rindex);
                            multiple-=1;
                            i++;
                        }
                    }
                }
            }
        }
        if (anzahldif[2] > 0) {
            ArrayList diftemp = (ArrayList) dif3.clone();
            for (int i = 0; i < anzahldif[2];) {
                int rindex = (int) round(Math.random() * (diftemp.size() - 1));
                if (!(rindex > diftemp.size())) {
                    String line = (String) diftemp.get(rindex);
                    String[] split = line.split(";");
                    HeadAtt ha = new HeadAtt(split);
                    Question q = new Question(split);
                    if (q.thema.contains(thema)) {
                        if (single == 0 && multiple == 0) {
                            dm.addRow(new Object[]{ha.name, ha.Thema, ha.Schwierigkeit});
                            QuestionList.addLast(q);
                            diftemp.remove(rindex);
                            i++;
                        } else if (single != 0 && isSingle(q)) {
                            dm.addRow(new Object[]{ha.name, ha.Thema, ha.Schwierigkeit});
                            QuestionList.addLast(q);
                            diftemp.remove(rindex);
                            single-=1;
                            i++;
                        } else if (multiple != 0 && !isSingle(q)) {
                            dm.addRow(new Object[]{ha.name, ha.Thema, ha.Schwierigkeit});
                            QuestionList.addLast(q);
                            diftemp.remove(rindex);
                            multiple-=1;
                            i++;
                        }
                    }
                }
            }
        }
        return dm;
    }

    public static DefaultTableModel fillTable(DefaultTableModel dm, int index) {

        if (index == 1) {

            for (int i = 0; i < dif1.size(); i++) {
                String line = (String) dif1.get(i);
                String[] split = line.split(";");
                HeadAtt ha = new HeadAtt(split);
                Question q = new Question(split);
                dm.addRow(new Object[]{ha.name, ha.Thema, ha.Schwierigkeit});
                QuestionList.addLast(q);
            }
        }
        if (index == 2) {
            for (int i = 0; i < dif2.size(); i++) {
                String line = (String) dif2.get(i);
                String[] split = line.split(";");
                HeadAtt ha = new HeadAtt(split);
                Question q = new Question(split);
                dm.addRow(new Object[]{ha.name, ha.Thema, ha.Schwierigkeit});
                QuestionList.addLast(q);
            }
        }
        if (index == 3) {
            for (int i = 0; i < dif3.size(); i++) {
                String line = (String) dif3.get(i);
                String[] split = line.split(";");
                HeadAtt ha = new HeadAtt(split);
                Question q = new Question(split);
                dm.addRow(new Object[]{ha.name, ha.Thema, ha.Schwierigkeit});
                QuestionList.addLast(q);

            }
        }
        return dm;
    }

    public static boolean isSingle(Question q) {
        Boolean issingle = false;
        for (String text : q.textvorantworten) {
            if (text.contains("$(=1)$")) {
                issingle = true;
            }
        }
        return issingle;
    }

    public static DefaultTableModel TableLoeschen(DefaultTableModel dm, int row) {
        //löschen der ausgewählten Zeile in der Table
        if (row > -1) {
            QuestionList.remove(row);
            dm.removeRow(row);
        }
        return dm;
    }
    public static void createLaTexDoc(String path) throws IOException {
        LinkedList<String> LinkedL;
        LinkedList<Question> Fragen = new LinkedList<Question>();
        LinkedL = new LinkedList<>();
        String[] text;
        Question q;
        try (FileWriter writer = new FileWriter(path)) {
            String header= head.get(0).toString();
            String[] headerArray=header.split(";");
            String end = head.get(1).toString();
            String[] endArray= end.split(";");
            
            for (String ha : headerArray) {
                
            
                writer.write(ha);
              writer.write(System.getProperty( "line.separator" ));
            }
//susi nochmal anschaun            
            for(int i=0;i<QuestionList.size();i++)
            {
                q= QuestionList.get(i);
                text= new String[q.text.length];
                text= q.text;
                for(int x=0;x<text.length;x++)
                {
                   
                    writer.write(System.getProperty( "line.separator" ));
                    writer.write(text[x]);

                }
            }
            
             
            for (String ea : endArray) {
              writer.write(ea);
              writer.write(System.getProperty( "line.separator" ));

            }
          
        }
    }
}
