/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testApps;
/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import uk.ac.manchester.owl.owlapi.tutorial.LabelExtractor;

/** <p>
 * Simple example. Read an ontology, and display the class hierarchy. May use a
 * reasoner to calculate the hierarchy.
 * </p>
 * Author: Sean Bechhofer<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 17-03-2007<br>
 * <br> */
public class SimpleHierarchyExample {
    private static int INDENT = 4;
    private OWLReasonerFactory reasonerFactory;
    private OWLOntology ontology;
    private PrintStream out;

    public SimpleHierarchyExample(OWLOntologyManager manager,
            OWLReasonerFactory reasonerFactory) throws OWLException,
            MalformedURLException {
        this.reasonerFactory = reasonerFactory;
        out = System.out;
    }

    /** Print the class hierarchy for the given ontology from this class down,
     * assuming this class is at the given level. Makes no attempt to deal
     * sensibly with multiple inheritance. */
    public void printHierarchy(OWLOntology ontology, OWLClass clazz) throws OWLException {
        OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontology);
        this.ontology = ontology;
        printHierarchy(reasoner, clazz, 0);
        /* Now print out any unsatisfiable classes */
        for (OWLClass cl : ontology.getClassesInSignature()) {
            if (!reasoner.isSatisfiable(cl)) {
                out.println("XXX: " + labelFor(cl));
            }
        }
        reasoner.dispose();
    }

    private String labelFor(OWLClass clazz) {
        /*
         * Use a visitor to extract label annotations
         */
        LabelExtractor le = new LabelExtractor();
        Set<OWLAnnotation> annotations = clazz.getAnnotations(ontology);
        for (OWLAnnotation anno : annotations) {
            anno.accept(le);
        }
        /* Print out the label if there is one. If not, just use the class URI */
        if (le.getResult() != null) {
            return le.getResult().toString();
        } else {
            return clazz.getIRI().toString();
        }
    }

    /** Print the class hierarchy from this class down, assuming this class is at
     * the given level. Makes no attempt to deal sensibly with multiple
     * inheritance. */
    public void printHierarchy(OWLReasoner reasoner, OWLClass clazz, int level)
            throws OWLException {
        /*
         * Only print satisfiable classes -- otherwise we end up with bottom
         * everywhere
         */
        if (reasoner.isSatisfiable(clazz)) {
            for (int i = 0; i < level * INDENT; i++) {
                out.print(" ");
            }
            out.println(labelFor(clazz));
            /* Find the children and recurse */
            for (OWLClass child : reasoner.getSubClasses(clazz, true).getFlattened()) {
                if (!child.equals(clazz)) {
                    printHierarchy(reasoner, child, level + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            IRI classIRI = null;
            String reasonerFactoryClassName = "r";
            // We first need to obtain a copy of an
            // OWLOntologyManager, which, as the name
            // suggests, manages a set of ontologies.
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            // We load an ontology from the URI specified
            // on the command line
           // System.out.println(args[0]);
            File file = new File("C:\\Users\\panise\\Documents\\College\\SemanticWebReading\\SampleOntologyfiles\\camera.owl");
            IRI documentIRI = IRI.create(file.toURI());
            // Now load the ontology.
            OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
            // Report information about the ontology
            System.out.println("Ontology Loaded...");
            System.out.println("Document IRI: " + documentIRI);
            System.out.println("Ontology : " + ontology.getOntologyID());
            System.out.println("Format      : " + manager.getOntologyFormat(ontology));
            // / Create a new SimpleHierarchy object with the given reasoner.
            SimpleHierarchyExample simpleHierarchy = new SimpleHierarchyExample(manager,
                    (OWLReasonerFactory) Class.forName(reasonerFactoryClassName)
                            .newInstance());
            // Get Thing
            if (classIRI == null) {
                classIRI = OWLRDFVocabulary.OWL_THING.getIRI();
            }
            OWLClass clazz = manager.getOWLDataFactory().getOWLClass(classIRI);
            System.out.println("Class       : " + classIRI);
            // Print the hierarchy below thing
            simpleHierarchy.printHierarchy(ontology, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}