/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uca.cs.owleditor.utils.ui;
import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 *File Filter class designed to create filters for Owl Editor. All the supported file types
 * are stored in an internal array to generate the filters. 
 * 
 * @author priyathamanisetty
 */
public class OwlEditorFileFilter extends FileFilter {
    
    private String description;
    private String[] extensions;
    
    public static String[][] supportedTypes = {
        {"OWL Files", "owl"},
        {"RDF Files", "rdf"},	
    };
    
     /**
     * Default file filter which contains all the file extensions
     * (*.swp, *.swo, *.owl, *.rdf, *.xml, ".txt)
     */
    public OwlEditorFileFilter() {
        String[] ext = new String[supportedTypes.length];
        for(int i = 0; i < ext.length; i++) {
            ext[i] = supportedTypes[i][1];
        }

        init("Ontology Files", ext);
    }
     
    /**
     * Default file filter which contains only ontology file extensions
     * (*.swo, *.owl, *.rdf, *.xml, ".txt)
     */
    public OwlEditorFileFilter(boolean onlyOntologyFiles) {
    	if (onlyOntologyFiles) {
	        String[] ext = new String[supportedTypes.length-1];
	        for(int i = 1; i < supportedTypes.length; i++) {
	            ext[i-1] = supportedTypes[i][1];
	        }
	
	        init("OWL Ontology Files", ext);
    	}
    }
    
    /**
     * A file filter that accepts only one type of extension. Extension should
     * not have '.' in it. Description is the string shown in the combo box of
     * the file chooser
     * 
     * @param desc
     * @param extension
     */
    public OwlEditorFileFilter(String desc, String extension) {
        init(desc, new String[] { extension });
    }

    /**
     * A file filter that accepts files whose extension matches the one element
     * in the given array.
     * 
     * @param desc
     * @param extension
     */
    public OwlEditorFileFilter(String desc, String[] extensions) {
        init(desc, extensions);
    }
    
    
    private void init(String desc, String[] ext) {    
        extensions = ext;
        
		StringBuffer strbuf = new StringBuffer(desc + " (");
		for(int i = 0; i < extensions.length; i++)
		{
			if(i > 0) { strbuf.append(", "); }
			strbuf.append("*." + extensions[i]);
		}
		strbuf.append(")");
		description = strbuf.toString();
    }
    
   
    /**
     * Return an array of file filters for all the supported file types.
     * 
     * @return
     */
    public static FileFilter[] getAllFilters() {
        OwlEditorFileFilter[] filters = new OwlEditorFileFilter[supportedTypes.length + 1];
        
        for(int i = 0; i < filters.length - 1; i++) {
            filters[i] = new OwlEditorFileFilter(supportedTypes[i][0], supportedTypes[i][1]);
        }
        filters[filters.length - 1] = new OwlEditorFileFilter();
        
        return filters;
    }
    
    @Override
    public boolean accept(File f) {
		
		if (f.isDirectory()) return true;
		
		String ext = getExtension(f);

        if (ext != null) {
            for(int i = 0; i < extensions.length; i++) {
                if(extensions[i].equals(ext))
                    return true;
            }
        }
		
        return false;
	}
    
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
    
    
    @Override
    public String getDescription() {
		return description;
	}
}
