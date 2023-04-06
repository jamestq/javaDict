/**
 * Name: Uy Thinh Quang
 * Surname: Quang
 * StudentID: 1025981
 */
package app.components;

import java.util.ArrayList;

public class Word {
    
    private String word;
    private ArrayList<String> meanings;

    public Word(String word, ArrayList<String> meanings){
        this.word = word;
        this.meanings = meanings;
    }

    public String getWord(){
        return this.word;
    }

    public String getMeanings(){
        String meaningString = "";
        for(String meaning : this.meanings){
            meaningString += meaning + ",";
        }
        return meaningString;
    }

    public void setMeanings(ArrayList<String> meanings){
        this.meanings = meanings;
    }
}
