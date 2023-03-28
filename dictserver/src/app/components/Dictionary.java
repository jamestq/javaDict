package app.components;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class Dictionary {

    private static String DEFAULT_OUTPUT = "dictionary.data";
    
    private LinkedHashMap<String, Word> wordList;
    private String outputFile = DEFAULT_OUTPUT; 

    public Dictionary(){
        this.wordList = new LinkedHashMap<>();
    }

    public void loadDictionary(String fileName){
        try{
            Scanner inputStream = new Scanner(new FileInputStream(fileName));
            loadWords(inputStream);
            this.outputFile = fileName;
            System.out.printf("%nDictionary loaded Successfully");
            displayDictionary();
            inputStream.close();
        }catch(FileNotFoundException e){
            System.out.println("invalid file path, creating empty default file: " + DEFAULT_OUTPUT);
        }
    }

    public void loadWords(Scanner inputStream){
        String currentLine;
        while(inputStream.hasNextLine()){
            currentLine = inputStream.nextLine();
            processToken(currentLine);
        }
    }

    public void displayDictionary(){
        System.out.printf("%n%n=========================%n%n");
        ArrayList<String> words = new ArrayList<String>(wordList.keySet());
        Collections.sort(words);
        for(String word : words){
            System.out.print(word + ": ");
            System.out.println(wordList.get(word).getMeanings());
        }
        System.out.printf("%n=========================%n%n");
    }

    public void processToken(String currentLine){
        for(int i=0; i<currentLine.length(); i++){
            if(currentLine.charAt(i) == '='){
                String word = currentLine.substring(0, i);
                ArrayList<String> meanings = processMeanings(currentLine.substring(i+1));
                Word newWord = new Word(word, meanings);
                wordList.put(word, newWord);
            }
        }
    }

    public ArrayList<String> processMeanings(String meaningsString){
        ArrayList<String> meanings = new ArrayList<>();
        int meaningStartId = 0;
        for(int i=0; i<meaningsString.length();i++){
            if(meaningsString.charAt(i) == ','){
                meanings.add(meaningsString.substring(meaningStartId,i).trim());
                meaningStartId = i+1;
            }
        }
        return meanings;
    }

    public void searchWord(String word){
        Word returnedWord = null;
        if((returnedWord = this.wordList.get(word)) != null){
            System.out.printf("Found word for %s!%n", word);
            System.out.printf("Meanings: %s%n", returnedWord.getMeanings());
        }else{
            System.out.printf("Error! \"%s\" does not exist!%n", word);
        }
    }

    public void addWord(String word, String meaningsString){
        if(meaningsString == null || meaningsString.length()==0){
            System.out.println("Error! Add meanings!");
            return;
        }
        if(this.wordList.get(word) != null){
            System.out.println("Error! Word already exists! To add new meaning, use Update.");
            return;
        }
        ArrayList<String> meanings = processMeanings(meaningsString);
        Word newWord = new Word(word, meanings);
        wordList.put(word, newWord);
        System.out.printf("Added word %s successfully!%n", word);
    }

    public void removeWord(String word){
        if(this.wordList.get(word) == null){
            System.out.println("This word does not exist. No action performed");
            return;
        }
        String meanings = this.wordList.get(word).getMeanings();
        this.wordList.remove(word);
        System.out.println("Successfully removed word");
        System.out.printf("%s : %s", word, meanings);
    }

    public void updateMeaning(String word, String meaningsString){
        Word searchWord;
        if((searchWord=wordList.get(word))!=null){
            if(meaningsString == null || meaningsString.length() == 0){
                System.out.println("Error! Add meaning!");
                return;
            }else{
                searchWord.setMeanings(processMeanings(meaningsString));
                System.out.println("Updated meaning successfully!");
            }
        }else{
            System.out.printf("Error word \"%s\" not found!", word);
        }
    }

}
