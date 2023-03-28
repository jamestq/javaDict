package app.components;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class Dictionary {

    private static String DEFAULT_OUTPUT = "dictionary.data";
    
    private LinkedHashMap<String, Word> wordList;
    private String outputFile = DEFAULT_OUTPUT; 
    String response = "";

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

    public void saveDictionary(){
        try{
            PrintWriter outputStream = new PrintWriter(new FileOutputStream(this.outputFile));
            String outputString = "";
            for(String word : wordList.keySet()){
                String meanings = String.join(wordList.get(word).getMeanings());
                outputString += word + "=" + meanings + "%n";
            }
            outputStream.printf(outputString);
            outputStream.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
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

    private void processToken(String currentLine){
        for(int i=0; i<currentLine.length(); i++){
            if(currentLine.charAt(i) == '='){
                String word = currentLine.substring(0, i);
                ArrayList<String> meanings = processMeanings(currentLine.substring(i+1));
                Word newWord = new Word(word, meanings);
                wordList.put(word, newWord);
            }
        }
    }

    private ArrayList<String> processMeanings(String meaningsString){
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

    public String searchWord(String word){
        Word returnedWord = null;
        if((returnedWord = this.wordList.get(word)) != null){
            response = String.format("Found word for %s!%nMeanings: %s%n", word, returnedWord.getMeanings());
            System.out.printf(response);
            return response;
        }
        response = String.format("Error! \"%s\" does not exist!%n", word);
        System.out.printf(response);
        return response;
    }

    public synchronized String addWord(String word, String meaningsString){
        if(meaningsString == null || meaningsString.length()==0){
            response = "Error! Add meanings!%n";
            System.out.printf(response);
            return response;
        }
        if(this.wordList.get(word) != null){
            response = "Error! Word already exists! To add new meaning, use Update.%n";
            System.out.printf(response);
            return response;
        }
        ArrayList<String> meanings = processMeanings(meaningsString);
        Word newWord = new Word(word, meanings);
        wordList.put(word, newWord);
        response = String.format("Added word %s successfully!%n",word);
        System.out.printf(response);
        return response;
    }

    public synchronized String removeWord(String word){
        if(this.wordList.get(word) == null){
            response = String.format("This word does not exist. No action performed%n");
            System.out.println(response);
            return response;
        }
        String meanings = this.wordList.get(word).getMeanings();
        this.wordList.remove(word);
        response = String.format("Successfully removed word%n%s : %s", word, meanings);
        System.out.printf(response);
        return response;
    }

    public synchronized String updateMeaning(String word, String meaningsString){
        Word searchWord;
        if((searchWord=wordList.get(word))!=null){
            if(meaningsString == null || meaningsString.length() == 0){
                response = "Error! Add meaning!%n";
                System.out.printf(response);
                return response;
            }else{
                searchWord.setMeanings(processMeanings(meaningsString));
                response = "Updated meaning successfully!%n";
                System.out.printf(response);
                return response;
            }
        }
        response = String.format("Error word \"%s\" not found!", word);
        System.out.printf(response);
        return response;
    }

}
