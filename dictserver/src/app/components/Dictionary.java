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
            System.out.printf("%nDictionary loaded Successfully");
            displayDictionary();
            inputStream.close();
        }catch(FileNotFoundException e){
            System.out.println("empty file path, creating empty dictionary file: " + fileName);
        }
        this.outputFile = fileName;
    }

    public synchronized void saveDictionary(){
        System.out.println("Saving dictionary...");
        try{
            PrintWriter outputStream = new PrintWriter(new FileOutputStream(this.outputFile));
            String outputString = "";
            for(String word : wordList.keySet()){
                String meanings = wordList.get(word).getMeanings();
                outputString += word + "=" + meanings + "\n";
            }
            outputStream.printf(outputString);
            outputStream.close();
            System.out.println("Successfully saved dictionary");
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

    public String displayDictionary(){
        String displayString = "\n=========================\n";
        ArrayList<String> words = new ArrayList<String>(wordList.keySet());
        Collections.sort(words);
        for(String word : words){
            String meanings = wordList.get(word).getMeanings()+"\n";
            displayString += word+": "+meanings;
        }
        displayString += "\n=========================\n";
        System.out.printf(displayString);
        return displayString;
    }

    public String searchWord(String word){
        Word returnedWord = null;
        if((returnedWord = this.wordList.get(word)) != null){
            response = String.format("%s\nMeanings: \n%s", word, returnedWord.getMeanings());
            System.out.printf(response + "\n");
            return response;
        }
        response = String.format("Error! \"%s\" does not exist!", word);
        System.out.printf(response + "\n");
        return response;
    }

    public synchronized String addWord(String word, String meaningsString){
        if(meaningsString == null || meaningsString.length()==0){
            response = String.format("Error! Add meanings to %s!", word);
            System.out.printf(response + "\n");
            return response;
        }
        if(this.wordList.get(word) != null){
            response = String.format("Error! \"%s\" already exists! To add new meaning, use \"Update Word\".", word);
            System.out.printf(response + "\n");
            return response;
        }
        ArrayList<String> meanings = processMeanings(meaningsString);
        Word newWord = new Word(word, meanings);
        wordList.put(word, newWord);
        response = String.format("Added word \"%s\" successfully!",word);
        System.out.printf(response+"\n");
        return response;
    }

    public synchronized String removeWord(String word){
        if(this.wordList.get(word) == null){
            response = String.format("\"%s\" does not exist. No action performed");
            System.out.println(response);
            return response;
        }
        String meanings = this.wordList.get(word).getMeanings();
        this.wordList.remove(word);
        response = String.format("Successfully removed word %s%nMeanings: %s", word, meanings);
        System.out.printf(response+"\n");
        return response;
    }

    public synchronized String updateMeaning(String word, String meaningsString){
        Word searchWord;
        if((searchWord=wordList.get(word))!=null){
            if(meaningsString == null || meaningsString.length() == 0){
                response = String.format("Error! Add meaning to \"%s\"!", word);
                System.out.printf(response + "\n");
                return response;
            }else{
                searchWord.setMeanings(processMeanings(meaningsString));
                response = String.format("Updated meaning successfully!\n%s\nMeanings: %s", word, meaningsString);
                System.out.printf(response + "\n");
                return response;
            }
        }
        response = String.format("Error word \"%s\" not found!", word);
        System.out.printf(response + "\n");
        return response;
    }

}
