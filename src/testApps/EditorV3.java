/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testApps;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author panise
 */
public class EditorV3 extends JPanel {
    
    JPanel classPanel;// = new JPanel();
    JPanel propPanel;// = new JPanel();
    
    JTabbedPane tabbedPane;

   
    
    public EditorV3() {
        
           classPanel = new JPanel();
           classPanel.setLayout(new BoxLayout(classPanel,BoxLayout.Y_AXIS));
           propPanel = new JPanel();
           
           
           tabbedPane = new JTabbedPane();
           
           fillClassPanel();
           
           
           tabbedPane.addTab("Class Editor", classPanel);
           tabbedPane.addTab("Prop Editor", propPanel);
           this.add(tabbedPane);
           
        
    }

    private void fillClassPanel() {
        JPanel domainPanel = new JPanel();
        domainPanel.setLayout(new BoxLayout(domainPanel, BoxLayout.Y_AXIS));
        JLabel domainLbl = new JLabel("Has Domian");
        String testString[] = {"A","B","c","d","e","f","g"};
        JList domainList = new JList(testString);
        int selected[] = {5,2};
        
        domainList.setSelectedIndices(selected);
        
        
        JScrollPane domainJsp = new JScrollPane(domainList);
        domainJsp.setPreferredSize(new Dimension(500,100));
        domainPanel.add(domainLbl);
        domainPanel.add(domainJsp);
        domainPanel.add(new JButton("Add"));
        
        
         
        JPanel inhertancePanel = new JPanel();
        inhertancePanel.setLayout(new BoxLayout(inhertancePanel, BoxLayout.Y_AXIS));
        JLabel superClass = new JLabel("Super Class");
        JLabel subClass = new JLabel("Sub Class");
        String testString2[] = {"A","B","c","d","e","f","g"};
        JList domainList2 = new JList(testString);
        
        JScrollPane domainJsp2 = new JScrollPane(domainList2);
        domainJsp2.setPreferredSize(new Dimension(500,100));
        
        inhertancePanel.add(superClass);
        inhertancePanel.add(domainJsp2);
        inhertancePanel.add(new JButton("Add"));
        
        
        classPanel.add(domainPanel);
      
        classPanel.add(inhertancePanel);
     
        
    }
    
    
    
    
    
    
}
