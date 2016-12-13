package latex.testgenerierung;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Martin
 */
public class Docreader {

    public static void DocOutStandart(ArrayList al, BufferedWriter bw) {
        try {
            for (int i = 0; i < al.size(); i++) {
                String line = al.get(i).toString();
                String[] split = line.split(",");
                for (int splitindex = 0; splitindex < split.length; splitindex++) {

                    bw.write(split[splitindex]);
                    bw.newLine();

                }
            }
            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(Docreader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void DocOut(ArrayList al, BufferedWriter bw, int index) {
        //Durchgehen der Übergebenen Liste und Ausgeben
        try {

            String line = al.get(index).toString();
            String[] split = line.split(",");
            for (int splitindex = 0; splitindex < split.length; splitindex++) {

                bw.write(split[splitindex]);
                bw.newLine();

            }

            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(Docreader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int[][] GetCoords(DefaultTableModel dm) {
        //Difficulty und Position der Frage ermitteln
        int[][] fragencoords = new int[dm.getRowCount()][2];
        int counter = 0;
        for (int row = 0; row < dm.getRowCount(); row++) {
            for (int column = 0; column < dm.getColumnCount(); column++) {
                if (column == 0) {
                    fragencoords[counter][0] = Integer.parseInt((String) dm.getValueAt(row, column));
                } else if (column == 3) {
                    fragencoords[counter][1] = Integer.parseInt((String) dm.getValueAt(row, column));
                    counter++;
                }

            }
        }
        return fragencoords;
    }
}
