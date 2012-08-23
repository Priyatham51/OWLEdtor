/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testApps;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;


// Represents items in the list that can be selected

public class NewClass
{
   public static void main(String args[])
   {
      JFrame frame = new JFrame();
     frame.setLayout(new BoxLayout(frame,BoxLayout.Y_AXIS));
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      
      
      // Create a list containing CheckListItem's

      JList list = new JList(new CheckListItem[] {
            new CheckListItem("apple"),
            new CheckListItem("orange"),
            new CheckListItem("mango"),
            new CheckListItem("paw paw"),
            new CheckListItem("banana")});

      // Use a CheckListRenderer (see below)
      // to renderer list cells

      list.setCellRenderer(new CheckListRenderer());
      list.setSelectionMode(
         ListSelectionModel.SINGLE_SELECTION);

      
      
      // Add a mouse listener to handle changing selection

      list.addMouseListener(new MouseAdapter()
      {
         public void mouseClicked(MouseEvent event)
         {
            JList list = (JList) event.getSource();

            // Get index of item clicked

            int index = list.locationToIndex(event.getPoint());
            CheckListItem item = (CheckListItem)
               list.getModel().getElementAt(index);

            // Toggle selected state

            item.setSelected(! item.isSelected());

            // Repaint cell

            list.repaint(list.getCellBounds(index, index));
         }
      });   

      JButton addBtn = new JButton("Add");
      addBtn.addActionListener(new ActionListener(){

           @Override
           public void actionPerformed(ActionEvent e) {
           // int selected[] =  list.getSelectedIndices(); 
           }
          
      });
      //frame.getContentPane().add(new JScrollPane(list));
     // frame.getContentPane().add(addBtn);
      frame.add(list);
      frame.add(addBtn);
      
      
      frame.pack();
      frame.setVisible(true);
   }
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

// Handles rendering cells in the list using a check box

class CheckListRenderer extends JCheckBox
   implements ListCellRenderer
{
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
