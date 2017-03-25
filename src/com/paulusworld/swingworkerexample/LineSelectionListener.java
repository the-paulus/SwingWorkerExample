/**
 * 
 */
package com.paulusworld.swingworkerexample;

import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * LineSelectionListener handles displaying the selected item in the JList in 
 * a TextPane.
 * 
 * @author Paul Lyon <pmlyon@gmail.com>
 *
 */
public class LineSelectionListener implements ListSelectionListener {

	/**
	 * The JTextPane to display the item in.
	 */
	private JTextPane textPane;
	
	/**
	 * The JList containing the lines of text file.
	 */
	private JList<String> list;
	
	/**
	 * 
	 */
	public LineSelectionListener(JList<String> list, JTextPane textPane) {
		
		this.textPane = textPane;
		this.list = list;
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		textPane.setText(list.getSelectedValue());
		
	}

}
