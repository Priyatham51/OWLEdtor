/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testApps;

import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 *
 * @author priyathamanisetty
 */
//java.lang.Enum;

//org.semanticweb.owlapi.vocab;


public class DataTypesTester {
    
    public enum Friends { PRIYATHAM, PRAVEEN, PULLAREDDY};
    //public OWL2DataType
    //public static final OWL2Datatype[] values(){
      
    public static void main(String[] args) {
        for(OWL2Datatype c : OWL2Datatype.values()){
          System.out.println(c);
        }
        
    }
    
}
