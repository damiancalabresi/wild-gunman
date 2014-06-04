package ar.com.badami.wildgunman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import ar.com.badami.framework.FileIO;

public class Settings {
	public static String SETTINGS_FILE = ".wild-gunman";

	// Se setean los valores default
	public static boolean soundEnabled = true;

	public static void load(FileIO files) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(files.readFile(SETTINGS_FILE)));
			soundEnabled = Boolean.parseBoolean(in.readLine());
		} catch (IOException e) {
			// :( It's ok we have defaults
		} catch (NumberFormatException e) {
			// :/ It's ok, defaults save our day
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	public static void save(FileIO files) {
		// A partir del SDK 8 el metodo Context.getExternalFilesDir() nos
		// devuelve un directorio para la aplicacion asi no se accede a la raiz
		// de la memoria SD
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(files.writeFile(SETTINGS_FILE)));
			out.write(Boolean.toString(soundEnabled));
		} catch (IOException e) {
			// Si hay algun error al escribir se le debería avisar al usuario de
			// alguna forma
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}

}
