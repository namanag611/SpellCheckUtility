package com.swabunga.spell.examples;

import java.io.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import com.swabunga.spell.event.*;
import com.test.run.GetAllTextofWebpage;
import com.google.common.io.Files;
import com.swabunga.spell.engine.*;

/** This class shows an example of how to use the spell checking capability.
 *
 * @author Jason Height (jheight@chariot.net.au)
 */
public class SpellCheckExample implements SpellCheckListener {

  private static String dictFile = "dict/english.0";
  private SpellChecker spellCheck = null;
  private static final String FILENAME = "D:\\filename.txt";
  BufferedWriter bw = null;
  FileWriter fw = null;
  File file = new File(FILENAME);
  ArrayList<String> al=new ArrayList<String>();
 

  public SpellCheckExample(String text) {
    try {
      SpellDictionary dictionary = new SpellDictionary(new File(dictFile));

      spellCheck = new SpellChecker(dictionary);
      spellCheck.addSpellCheckListener(this);
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      boolean run=true;
      while (run) {
	System.out.print("Enter text to spell check: ");
	String line = text;
	//String line=in.readLine();
	if (line.length() <=0)
	  break;
		spellCheck.checkSpelling( new StringWordTokenizer(line) );
		run=false;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void spellingError(SpellCheckEvent event) {
	 
	  try {
			fw = new FileWriter(file.getAbsoluteFile(),true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			bw = new BufferedWriter(fw);  
    List suggestions = event.getSuggestions();
    if (suggestions.size() > 0) {
      //System.out.println("MISSPELT WORD: "+event.getInvalidWord());
     
      if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
      
      //-----------------------
      try{
      	
		bw.write("MISSPELT WORD: "+event.getInvalidWord());
		bw.newLine();
      }catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
      //--------------------------
      for (Iterator suggestedWord=suggestions.iterator(); suggestedWord.hasNext();) {
        //System.out.println("Suggested Word: ="+suggestedWord.next());
        
      }
    }
    //Null actions
    try {
    if (bw != null)
		bw.close();
    

	if (fw != null)
		fw.close();
	
    }catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    
  }

 // public static void main(String[] args) {
  // new SpellCheckExample();
  //}
}
