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
public class Question {
    String name;
    String schwierigkeit;
    String thema;
    String[] text;

    public Question(String[] text) {
        this.text = new String[text.length-3];
        this.name = text[1];
        this.thema = text[2];
        this.schwierigkeit = text[3];
        for(int index=3;index<text.length;index++){
         this.text[index-3] = text[index];   
        }
        
    }

    @Override
    public String toString() {
        return  name + ";" + schwierigkeit + ";" + thema;
    }
}
