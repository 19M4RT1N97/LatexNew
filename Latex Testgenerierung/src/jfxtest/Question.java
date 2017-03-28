/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxtest;

import java.util.LinkedList;

/**
 *
 * @author Martin
 */
public class Question {

    String name;
    String schwierigkeit;
    String thema;
    LinkedList<String> textvorantworten = new LinkedList<>();
    LinkedList<String> textnachantworten = new LinkedList<>();
    LinkedList<String> antworten = new LinkedList<>();

    public Question(String[] text) {
        boolean antwort = true;
        this.name = text[1];
        this.thema = text[2];
        this.schwierigkeit = text[3];
        boolean choices = false;
        if (hasCheckboxes(text)) {
            for (int index = 4; index < text.length; index++) {
                if (text[index].contains("\\begin{checkboxes}")) {
                    choices = true;
                } else if (text[index].contains("\\end{checkboxes}")) {
                    choices = false;
                    antwort = false;
                } else if (choices) {
                    this.antworten.add(text[index]);
                } else if (antwort) {
                    this.textvorantworten.add(text[index]);
                } else if (!antwort) {
                    this.textnachantworten.add(text[index]);
                }
            }
        } else{
            for (int index = 4; index < text.length; index++) {
                if (text[index].contains("\\CheckBox")||text[index].contains("% @correct")) {
                    this.antworten.add(text[index]);
                    antwort=false;
                } else if (antwort) {
                    this.textvorantworten.add(text[index]);
                } else if (!antwort) {
                    this.textnachantworten.add(text[index]);
                }
            }
        }
    }

    @Override
    public String toString() {
        return name + ";" + schwierigkeit + ";" + thema;
    }

    private boolean hasCheckboxes(String[] s) {
        boolean has = false;
        for (int index = 4; index < s.length; index++) {
            if (s[index].contains("\\begin{checkboxes}")) {
                has = true;
                break;
            }
        }
        return has;
    }
}
