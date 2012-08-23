/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testApps;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author priyathamanisetty
 */
public class Tester {
    
    public static void main(String[] args) {
        
        EditorV3 ev2 = new EditorV3();
        ev2.setVisible(true);
        
        JFrame jf = new JFrame();
        jf.setLayout(new BorderLayout());
        
        jf.add(ev2, BorderLayout.LINE_START);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(1000, 600);
        jf.pack();
        jf.setResizable(false);
        jf.setVisible(true);
        
        
    }
}
