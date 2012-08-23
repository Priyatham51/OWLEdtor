/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uca.cs.owleditor.popup;

import com.uca.cs.owleditor.OOMIOwlModel;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

/**
 *
 * @author priyathamanisetty
 */
public class ZZZPopupTester {

    
    JPanel testpanel;
    JFrame testFrame;
    
    
     OOMIOwlModel oomiModel;
    OWLDataFactory df; 
    OWLOntology onto ;
    
    
    
    
    
    
    public static void main(String[] args) {
         
        try
        {
             
        
           OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

            // Ontologies cna have an IRI, which is used to identify the ontology.  You should
            // think of the ontology IRI as the "name" of the ontology.  This IRI frequently
            // resembles a Web address (i.e. http://...), but it is important to realise that
            // the ontology IRI might not necessarily be resolvable.  In other words, we
            // can't necessarily get a document from the URL corresponding to the ontology
            // IRI, which represents the ontology.
            // In order to have a concrete representation of an ontology (e.g. an RDF/XML
            // file), we MAP the ontology IRI to a PHYSICAL URI.  We do this using an IRIMapper

            // Let's create an ontology and name it "http://www.co-ode.org/ontologies/testont.owl"
            // We need to set up a mapping which points to a concrete file where the ontology will
            // be stored. (It's good practice to do this even if we don't intend to save the ontology).
            IRI ontologyIRI = IRI.create("http://www.co-ode.org/ontologies/testont.owl");
            // Create the document IRI for our ontology
            
            File file = new File("/Users/priyathamanisetty/Desktop/Sematic Web reading/MyOnt.owl");
            IRI documentIRI = IRI.create(file.toURI());
            // Set up a mapping, which maps the ontology to the document IRI
            SimpleIRIMapper mapper = new SimpleIRIMapper(ontologyIRI, documentIRI);
            manager.addIRIMapper(mapper);

            // Now create the ontology - we use the ontology IRI (not the physical URI)
            OWLOntology ontology = manager.createOntology(ontologyIRI);
            // Now we want to specify that A is a subclass of B.  To do this, we add a subclass
            // axiom.  A subclass axiom is simply an object that specifies that one class is a
            // subclass of another class.
            // We need a data factory to create various object from.  Each manager has a reference
            // to a data factory that we can use.
            OWLDataFactory factory = manager.getOWLDataFactory();
            
            
           // setup();
            
            
        }
        catch (OWLException e) {
            e.printStackTrace();
        }
        
        
        
        
    }
    
    
     private void setup() {
        oomiModel = new OOMIOwlModel();
        
        
    }
    
}
