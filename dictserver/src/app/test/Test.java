package app.test;

import app.components.Dictionary;

public class Test{
    public static void main(String[] args){
        Dictionary newDict = new Dictionary();
        newDict.loadDictionary("words.data");
        newDict.searchWord("go");
        newDict.addWord("go", "to move, to express movement, to be in motion");
        newDict.displayDictionary();
        newDict.searchWord("go");
        newDict.addWord("go", "to not move, to not do anything");
        newDict.addWord("go", "");
        newDict.removeWord("go");
        newDict.displayDictionary();
        newDict.removeWord("go");
        newDict.addWord("go", "to move, to express movement, to be in motion");
        newDict.updateMeaning("go", "to not move, to not do anything");
    }
}