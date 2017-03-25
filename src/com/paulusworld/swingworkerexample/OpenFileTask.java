/**
 * 
 */
package com.paulusworld.swingworkerexample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.SwingWorker;

/**
 * The OpenFileTask class is what reads the text file on a separate thread in order to 
 * keep the application responsive.
 * <p>
 * When extending the SwingWorker class, we define what type of objects we want to return
 * from the doInBackground method (T) and the type of objects to publish immediate results 
 * to the <em>publish</em> and <em>process</em> methods (V).
 * 
 * SwingWorker<T,V>
 * 
 * @author Paul Lyon <pmlyon@gmail.com>
 *
 */
public class OpenFileTask extends SwingWorker<DefaultListModel<String>, Integer> {

	/**
	 * The file that is to be processed.
	 */
	private File txtFile;
	
	/**
	 * Data model used to hold the data read from the selected file. 
	 */
	private DefaultListModel<String> model = new DefaultListModel<String>();
	
	/**
	 * Reference to the list object that we want to update.
	 */
	private JList<String> list;
	
	/**
	 * Constructs a new OpenFileTask object. 
	 * 
	 * @param txtFile File that is to be processed.
	 * @param list The JList object that is to be updated.
	 */
	public OpenFileTask(File txtFile, JList<String> list) {
		
		this.txtFile = txtFile;
		this.list = list;
		
	}
	
	/**
	 * When the execute() method is called, the code in this function is executed 
	 * on a different thread "in the background."
	 */
	@Override
	protected DefaultListModel<String> doInBackground() throws Exception {
		
		String line;
		
		try {
			
			InputStream fis = new FileInputStream(txtFile);
			InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(isr);
			
			while((line = br.readLine()) != null) {
				model.addElement(line);
			}
			
			br.close();
			
		} catch(Exception e) {
			
		}
		
		return model;
	}

	/**
	 * Executed when the doInBackground function completes.
	 */
	public void done() {
		
		try {
			
			list.setModel((DefaultListModel<String>) get());
			
		} catch (InterruptedException | ExecutionException e) {
			
			e.printStackTrace();
			
		}
	}
	
	/**
	 * Updates the UI as the results are returned.
	 * @param chunks
	 */
	protected void process(final DefaultListModel<String> chunks) {
		
	}
}
