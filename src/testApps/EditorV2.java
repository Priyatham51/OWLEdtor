/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testApps;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;

/**
 *
 * @author priyathamanisetty
 */
public class EditorV2 extends JPanel {
    
    
    
    JPanel rightSide;
    
    JPanel classDisp;
    JPanel propDisp;
    
    
    JTabbedPane tabbedPane; 

    
    
    
    
    public EditorV2() {
        
        rightSide = new JPanel();
        rightSide.setLayout(new GridLayout());
        //rightSide.setBackground(Color.BLUE);
        fillRightSidePanel();
        
        propDisp = new JPanel();
        propDisp.setSize(600, 200);
        
        classDisp = new JPanel();
        classDisp.setSize(600,200);
        
        
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Class", classDisp);
        tabbedPane.addTab("Prop", propDisp);
        
        
        
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx =150;
        gbc.ipady = 300;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(tabbedPane,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 300;
        gbc.ipady = 300;

        //gbc.fill = GridBagConstraints.BOTH;
        
        this.add(rightSide,gbc);
        
        
    }

    private void fillRightSidePanel() {
        
        
        JCheckBox jcb = new JCheckBox();
        jcb.setText("CheckBox1");
        
        JCheckBox jcb1 = new JCheckBox();
        jcb1.setText("CheckBox2");
        
        JCheckBox jcb2 = new JCheckBox();
        jcb2.setText("CheckBox2");
        
        JCheckBox jcb3 = new JCheckBox();
        jcb3.setText("CheckBox3");
        
        JCheckBox jcb4 = new JCheckBox();
        jcb4.setText("CheckBox4");
        
        
        JCheckBox jcb5 = new JCheckBox();
        jcb5.setText("CheckBox5");
        
         JCheckBox jcb6 = new JCheckBox();
        jcb6.setText("CheckBox6");
        
        JCheckBox jcb7 = new JCheckBox();
        jcb7.setText("CheckBox7");
        
        JCheckBox jcb8 = new JCheckBox();
        jcb8.setText("CheckBox8");
        
        JCheckBox jcb9 = new JCheckBox();
        jcb9.setText("CheckBox9");
        
        JCheckBox jcb10 = new JCheckBox();
        jcb10.setText("CheckBox10");
        
        
        JCheckBox jcb11 = new JCheckBox();
        jcb11.setText("CheckBox11");
        
        
        
       JPanel checkBoxPanel = new JPanel();
//        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
//        checkBoxPanel.setBackground(Color.red);
//        checkBoxPanel.setMinimumSize(new Dimension(10, 10));
//        checkBoxPanel.setPreferredSize(new Dimension(10, 10));
       // checkBoxPanel.setSize(100, 30);
        //checkBoxPanel.setPreferredSize(new Dimension(30,100));
//        checkBoxPanel.add(jcb);
//        checkBoxPanel.add(jcb1);
//        checkBoxPanel.add(jcb2);
//        checkBoxPanel.add(jcb3);
//        checkBoxPanel.add(jcb4);
//        checkBoxPanel.add(jcb5);
//        checkBoxPanel.add(jcb6);
//        checkBoxPanel.add(jcb7);
//        checkBoxPanel.add(jcb8);
//        checkBoxPanel.add(jcb9);
//        checkBoxPanel.add(jcb10);
//        checkBoxPanel.add(jcb11);
        
//        checkBoxPanel.add(new JTextArea(""
//            + "Twas brillig and the slithy toves\n"
//            + "Did gyre and gimble in the wabe;\n"
//            + "All mimsy were the borogoves,\n"
//            + "And the mome raths outgrabe."));
//        
        
        
        List<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
        
        String labels[] = {"A", "B", "C", "D", "E", "F"};
    int numberCheckboxes = 6;
    for (int i = 0; i < numberCheckboxes; i++) {
        JCheckBox checkbox = new JCheckBox(labels[i]);
        checkboxes.add(checkbox); //for further use you add it to the list
    }
    
    
    
    
        
        //rightSide.add(checkboxes);
        
       JList newlist = new JList(labels);
       //newlist.setCellRenderer(new CheckListRenderer());
       
        JScrollPane jsp = new JScrollPane(newlist);
        
       
        //jsp.setMinimumSize(new Dimension(100, 100));
        jsp.setPreferredSize(new Dimension(300, 100));
        checkBoxPanel.add(jsp);
        rightSide.add(checkBoxPanel);
       
        
        //System.out.println(checkBoxPanel.getSize().toString());

    }
    
    class CheckListItem
{
   private String  label;
   private boolean isSelected = false;

   public CheckListItem(String label)
   {
      this.label = label;
   }

   public boolean isSelected()
   {
      return isSelected;
   }

   public void setSelected(boolean isSelected)
   {
      this.isSelected = isSelected;
   }

   public String toString()
   {
      return label;
   }
}
    
    class CheckListRenderer extends JCheckBox
   implements ListCellRenderer
{
        @Override
   public Component getListCellRendererComponent(
         JList list, Object value, int index,
         boolean isSelected, boolean hasFocus)
   {
      setEnabled(list.isEnabled());
      setSelected(((CheckListItem)value).isSelected());
      setFont(list.getFont());
      setBackground(list.getBackground());
      setForeground(list.getForeground());
      setText(value.toString());
      return this;
   }
}

    
    
    
    
}
