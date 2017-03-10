/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latex.testgenerierung;

/**
 *
 * @author Martin
 */
public class HeadAtt {
    String name;
    String Thema;
    String Schwierigkeit;
    
    public HeadAtt(String[] splitter){
        String[] splittname= splitter[1].split("=");
        String[] splittthema= splitter[2].split("=");
        String[] splittschwer= splitter[3].split("=");
        this.name = splittname[1];
        this.Thema=splittthema[1];
        this.Schwierigkeit=splittschwer[1];
    }
}
