package com.paulusworld.swingworkerexample;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * Main application class.
 * 
 * @author Paul Lyon <pmlyon@gmail.com>
 *
 */
public class SwingWorkerExampleWindow {

	/**
	 * Application frame.
	 */
	private JFrame frame;
	
	/**
	 * File to read data from.
	 */
	private File openFile;
	
	/**
	 * JFileChooser for providing an interface for the user to select and open the data file.
	 */
	private JFileChooser fileChooser = new JFileChooser();
	
	/**
	 * SwingWorker object for handling data processing on a different thread.
	 */
	private OpenFileTask task;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					
					SwingWorkerExampleWindow window = new SwingWorkerExampleWindow();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
				
			}
			
		});
		
	}

	/**
	 * Create the application.
	 */
	public SwingWorkerExampleWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		// Setting up the application window.
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create a split pane for resizable panes.
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		// Text pane to display select list item.
		JTextPane textPane = new JTextPane();
		splitPane.setRightComponent(textPane);
		
		// Scroll pane to allow each item to be seen by the user
		// if the number of items "overflows" the current view.
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		// The list control that will contain each line of the 
		// selected file as an item.
		JList<String> list = new JList<String>();

		// Adding a selection listener to the list so we can update the text pane
		// when the user selects different items.
		LineSelectionListener selectionListener = new LineSelectionListener(list, textPane);
		list.addListSelectionListener(selectionListener);
		// Finally adding the list to the scroll pane.
		scrollPane.setViewportView(list);
		
		// Creating the main panel for the application window and setting its layout.
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		// Text portion of the status bar.
		JLabel lblStatus = new JLabel("");
		panel.add(lblStatus, BorderLayout.WEST);
		
		// Progress bar that will live in the status bar.
		JProgressBar progressBar = new JProgressBar();
		panel.add(progressBar, BorderLayout.EAST);
		
		// Setting up a menu bar to make the example feel like a real application.
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		// The only "menu" to hold an open menu item.
		JMenu mnOpenFile = new JMenu("File");
		menuBar.add(mnOpenFile);
		
		// The open menu item.
		JMenuItem mntmOpenFile = new JMenuItem("Open File");
		
		// Add an action listener so we know when a user clicks on "Open File"
		mntmOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Setting up the file chooser to default to the user's home directory.
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				// Setting the title of the open file dialog.
				fileChooser.setDialogTitle("Open file");
				// Filter out all other files leaving only .txt files.
				fileChooser.setFileFilter(new FileNameExtensionFilter("Text file", "txt"));
				
				// Save the user action from the file chooser.
				int userAction = fileChooser.showOpenDialog((Component) e.getSource());
				
				// If the user approved of the selected file, then we work with that.
				if(userAction == JFileChooser.APPROVE_OPTION) {
					
					// Get the selected file.
					openFile = fileChooser.getSelectedFile();
					// Create a new SwingWorker object to handle the parsing of the file in the background.
					// The parameters are specified by us when we created the OpenFileTask class.
					task = new OpenFileTask(openFile, list);
					//OpenFileChangeListener changeListener = new OpenFileChangeListener(list);
					//task.addPropertyChangeListener(changeListener);					
					task.execute();
				}
			}
		});
		
		// Add the "Open File" menu item to the "File" menu.
		mnOpenFile.add(mntmOpenFile);
		
	}

}
