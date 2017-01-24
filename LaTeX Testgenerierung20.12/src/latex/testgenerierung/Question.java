/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latex.testgenerierung;

import java.util.LinkedList;

/**
 *
 * @author Martin
 */
public class Question {

    String name;
    String schwierigkeit;
    String thema;
    LinkedList<String> textvorantworten=new LinkedList<>();
    LinkedList<String> textnachantworten=new LinkedList<>();
    LinkedList<String> antworten=new LinkedList<>();

    public Question(String[] text) {
        boolean antwort = false;
        this.name = text[1];
        this.thema = text[2];
        this.schwierigkeit = text[3];
        for (int index = 4; index < text.length; index++) {
            if (text[index].contains("\\choice") || text[index].contains("% @correct")) {
                this.antworten.add(text[index]);
                antwort = true;
            } else if (!antwort) {
                this.textvorantworten.add(text[index]);
            } else if (antwort) {
                this.textnachantworten.add(text[index]);
            }
            
        }
    }

    @Override
    public String toString() {
        return name + ";" + schwierigkeit + ";" + thema;
    }
}
