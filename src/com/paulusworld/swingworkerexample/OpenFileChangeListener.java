/**
 * 
 */
package com.paulusworld.swingworkerexample;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;

/**
 * @author paulus
 *
 */
public class OpenFileChangeListener implements PropertyChangeListener {

	private JList list;
	
	public OpenFileChangeListener(JList list) {
		this.list = list;
	}
	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName() == "progress") {
			list.setModel((ListModel) evt.getNewValue());
			list.updateUI();
		}

	}

}
