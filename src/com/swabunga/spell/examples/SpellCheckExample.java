package com.swabunga.spell.examples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.swabunga.spell.engine.SpellDictionary;
import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;

/**
 * This class shows an example of how to use the spell checking capability.
 *
 * @author Jason Height (jheight@chariot.net.au)
 */
public class SpellCheckExample implements SpellCheckListener {

	String dir = System.getProperty("user.dir");
	String file_path = dir + "\\spell\\";

	private static String dictFile = "dict/english.0";
	private SpellChecker spellCheck = null;
	BufferedWriter bw = null;
	FileWriter fw = null;
	File file;

	public SpellCheckExample(String text, int i) {

		String FILENAME = file_path + i + ".txt";
		file = new File(FILENAME);
		try {
			SpellDictionary dictionary = new SpellDictionary(new File(dictFile));

			spellCheck = new SpellChecker(dictionary);
			spellCheck.addSpellCheckListener(this);
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			boolean run = true;
			while (run) {
				// System.out.print("Enter text to spell check: ");
				String line = text;
				// String line = in.readLine();
				if (line.length() <= 0)
					break;
				spellCheck.checkSpelling(new StringWordTokenizer(line));
				run = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void spellingError(SpellCheckEvent event) {

		try {
			fw = new FileWriter(file.getAbsoluteFile(), true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bw = new BufferedWriter(fw);
		List suggestions = event.getSuggestions();
		if (suggestions.size() > 0) {
			// System.out.println("MISSPELT WORD: " + event.getInvalidWord());

			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// -----------------------
			try {

				bw.write("MISSPELT WORD: " + event.getInvalidWord());
				bw.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// --------------------------
			for (Iterator suggestedWord = suggestions.iterator(); suggestedWord.hasNext();) {
				// System.out.println("Suggested Word: =" +
				// suggestedWord.next());
				String message = "Suggested Word: =" + suggestedWord.next();
			}
		}
		// Null actions
		try {
			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// public static void main(String[] args) {
	/// new SpellCheckExample();
	// }
}
