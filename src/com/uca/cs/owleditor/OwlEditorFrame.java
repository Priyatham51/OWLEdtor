
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uca.cs.owleditor;

/**
 *
 * @author priyathamanisetty
 */




import java.awt.*;
import java.awt.color.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.image.renderable.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;


import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyCreationIOException;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import com.uca.cs.owleditor.utils.ui.OwlEditorFileFilter;
import com.uca.cs.owleditor.OOMIOwlModel;



public class OwlEditorFrame extends JFrame /*implements ActionListener, WindowListener*/ {
    
    
    public static final int FRAME_WIDTH = 1300;
    public static final int FRAME_HEIGHT = 700;
    
    
    
    
    File workingOntologyFile;
    File openFile = null;
    
    
    
    IDEPanel panel;
    OOMIOwlModel oomiOwlModel;
    
    /*
     * Menu Fileds Decleration
     */
    
    JMenuBar jMenuBar = new JMenuBar();
    
    
    JMenu jMenuFile = new JMenu();
        JMenuItem jMenuFileNew = new JMenuItem();
        JMenuItem jMenuFileOpen = new JMenuItem();
        JMenuItem jMenuFileSave = new JMenuItem();
        JMenuItem jMenuFileSaveAs = new JMenuItem();
        JMenuItem jMenuFileExit = new JMenuItem();
        
    JMenu jMenuView = new JMenu();
        JMenuItem jMenuViewRDFSource = new JMenuItem();
        JMenuItem jMenuViewAbstractSyntaxSource = new JMenuItem();
        
    JMenu jMenuAbout = new JMenu();
        JMenuItem jMenuAboutEditor = new JMenuItem();
        
        
    
  
    
    public OwlEditorFrame(){
       
       oomiOwlModel = new OOMIOwlModel();
        
       init();
        
    }
    
    
    protected void init(){
     
        this.setTitle("OOMI OWL Editor");
        
        
        // Setting the File menu
        jMenuFile.setText("File");
        jMenuFile.setMnemonic('F');
        
        
        jMenuFileNew.setText("New Ontology");
        jMenuFileNew.setMnemonic('n');
        jMenuFileNew.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                jMenuFileNew_actionPerformed( e );
            }
        });
        
        jMenuFileOpen.setText("Open Ontology");
        jMenuFileOpen.setMnemonic('o');
        jMenuFileOpen.addActionListener(new ActionListener(){
            public void actionPerformed( ActionEvent e ) {
                jMenuFileOpen_actionPerformed( e );
            }
        });
        
        jMenuFileSave.setText("Save");
        jMenuFileSave.setMnemonic('s');
        
//        jMenuFileSaveAs.setText("Save As"); // Save As needs to added
//        jMenuFileSaveAs.setMnemonic(c);
        
        jMenuFileExit.setText("Exit");
        jMenuFileExit.setMnemonic('x');
        
        // Setting the View Menu
        
        jMenuView.setText("View");
        jMenuView.setMnemonic('v');
        
        jMenuViewRDFSource.setText("Source RDF/XML");
        jMenuViewRDFSource.setMnemonic('r');
       
        jMenuViewAbstractSyntaxSource.setText("Source Abstract Syntax");
        
        //Setting the About View
        jMenuAbout.setText("About");
        jMenuAboutEditor.setText("About OOMI Editor");
        
        
        //Add all the Components to the Menus
        //Components of "FILE" menu
        jMenuFile.add(jMenuFileNew);
        jMenuFile.add(jMenuFileOpen);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFileSave);
        
        //jMenuFile.add(jMenuFileSaveAs); no implemented in this version
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFileExit);
        
        //compentes of "View" menu
        jMenuView.add(jMenuViewRDFSource);
        jMenuView.add(jMenuViewAbstractSyntaxSource);
        
        //Components of "About" menu
        jMenuAbout.add(jMenuAboutEditor);
        jMenuAboutEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				
				JPanel msgPanel = new JPanel();
				msgPanel.setLayout(new GridLayout(2, 1));
				msgPanel.add(new JLabel("OOMI Research Group"));
				msgPanel.add(new JLabel("University of Central Arkansas,Conway"));
                                
				JOptionPane.showMessageDialog(null, msgPanel);
			}
		});
        
        //add the menus to the menuBar
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuView);
        jMenuBar.add(jMenuAbout);
        
        JFrame owlEditorFrame = new JFrame("OOMI Owl Editor");
        
        owlEditorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        owlEditorFrame.setPreferredSize(new Dimension(600,600));
        owlEditorFrame.setSize(new Dimension(1000,700));

        
        owlEditorFrame.setJMenuBar(jMenuBar);
        
        panel = new IDEPanel(owlEditorFrame,oomiOwlModel);
        oomiOwlModel.setIDEPanelHandler(panel);
        owlEditorFrame.getContentPane().add(panel);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        owlEditorFrame.setLocation((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2);
        owlEditorFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        owlEditorFrame.setVisible(true);

        owlEditorFrame.setVisible(true);
        
    }
    
    
    /***
	 * Generic load file method using which user can load
	 * standard OWL Ontology (.owl, .rdf)
	 * This method invokes
	 * LoadOWLFile() depending on extension of file selected by user
	 */
    
    public void loadFile(){
        
        JFileChooser ontologyFileChooser = new JFileChooser();
        
        FileFilter[] filters = OwlEditorFileFilter.getAllFilters();
        
        //add the FileFilters to FileChooser
        for(int i=0; i<filters.length; i++)
        ontologyFileChooser.addChoosableFileFilter(filters[i]);
        
        int returnVal;
        
        /*
         * if workingOntologyFile is not null set File chooser to current Directory
         * 
         */
        if(openFile != null){
            ontologyFileChooser.setCurrentDirectory(workingOntologyFile);
        }
        
        
        openFile = null;
        
        if(workingOntologyFile == null){
            
            returnVal = ontologyFileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                    openFile = ontologyFileChooser.getSelectedFile();
            } else { // save cancelled    
                return;
            }	
        }
        
        workingOntologyFile = openFile;
        loadOWLFile();
        
        
        
        
        
    }
    
    /**
	 * Simple method to load a single OWL file written in RDF/XML
	 * or in Abstract Syntax format
	 */
	private void loadOWLFile() {
		if (workingOntologyFile != null) {
			String filePath = workingOntologyFile.toURI().toString();
			String filepath = workingOntologyFile.toString();
                        panel.updateTextArea(filepath);                               
//			loader.loadURIInModel(filePath, filePath);
//			// add ontology to fileOntMap
//			this.fileOntMap.put(workingOntologyFile.toURI(), workingOntologyFile);
//			
		}
	}
    
    
    /**
     * File | New action performed
     *
     * @todo Write this method to create a new project with an empty dataStore
     */
    public void jMenuFileNew_actionPerformed( ActionEvent e ) {
        try {
            
            
        } catch(java.lang.UnsupportedOperationException uoe) {
            
        }
    }
    
    private void jMenuFileOpen_actionPerformed(ActionEvent e) {
                try {    
                      loadFile();
                      oomiOwlModel.setWorkingOntology(workingOntologyFile);
                    } 
                catch(java.lang.UnsupportedOperationException uoe) 
                    {
                        
                    }
            }
    
    
    
    
   
    
    
}
