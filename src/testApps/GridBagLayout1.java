/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testApps;

/**
 *
 * @author priyathamanisetty
 */

    import java.awt.GridBagConstraints;
    import java.awt.GridBagLayout;
    import javax.swing.BoxLayout;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
    import javax.swing.JPanel;
    import javax.swing.JScrollPane;
    import javax.swing.JTabbedPane;
    import javax.swing.JTextField;

    public class GridBagLayout1 extends JFrame
    {
        public static void main(String[] args)
        {
            GridBagLayout1 tp1 = new GridBagLayout1();
            tp1.go();
        }

        public void go()
        {
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            // create a panel with some labels on it
            JPanel innerFirst = new JPanel();
            innerFirst.setLayout(new BoxLayout(innerFirst, BoxLayout.PAGE_AXIS));
            innerFirst.add(new JLabel("one"));
            innerFirst.add(new JLabel("two"));
            innerFirst.add(new JLabel("three"));
            innerFirst.add(new JLabel("four"));

            // put that panel in a scroll pane
            JScrollPane firstSP = new JScrollPane(innerFirst);

            // make another panel and put our scrolled panel in it
            JPanel outerFirst = new JPanel(); 
            outerFirst.setLayout(new BoxLayout(outerFirst, BoxLayout.PAGE_AXIS));
            outerFirst.add(firstSP); 

            // create a GridBagLayout panel with some text fields on it
            JPanel innerSecond = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = .25;
            gbc.anchor = GridBagConstraints.LINE_START;
            innerSecond.add(new JTextField(8), gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            innerSecond.add(new JTextField(10), gbc);
            gbc.gridx =0;
            gbc.gridy = 2;
            innerSecond.add(new JTextField(12), gbc);

            // put that panel in a scroll pane
            JScrollPane secondSP = new JScrollPane(innerSecond);

            // make another panel and put our second scrolled panel in it
            JPanel outerSecond = new JPanel(); 
            outerSecond.setLayout(new BoxLayout(outerSecond, BoxLayout.LINE_AXIS));
            outerSecond.add(secondSP); 

            JPanel innerThird = new JPanel(new GridBagLayout());
            GridBagConstraints gbc3 = new GridBagConstraints();
            gbc3.anchor = GridBagConstraints.LINE_END;
            gbc.weightx = .25;
            gbc3.gridx = 0;
            gbc3.gridy = 0;
            innerThird.add(new JLabel("1st label"), gbc3);
            gbc3.gridy = 1;
            innerThird.add(new JLabel("second label"), gbc3);
            gbc3.gridy = 2;
            innerThird.add(new JLabel("IIIrd label"), gbc3);

            gbc3.anchor = GridBagConstraints.LINE_START;
            gbc3.gridx = 1;
            gbc3.gridy = 0;
            innerThird.add(new JTextField(8), gbc3);
            gbc3.gridy = 1;
            innerThird.add(new JTextField(12), gbc3);
            gbc3.gridy = 2;
            innerThird.add(new JTextField(14), gbc3);

            JScrollPane thirdSP = new JScrollPane(innerThird);
            JPanel outerThird = new JPanel();
            outerThird.setLayout(new BoxLayout(outerThird, BoxLayout.LINE_AXIS));
            outerThird.add(thirdSP);

            // put the scrolled panes onto a tabbed pane
            JTabbedPane tp = new JTabbedPane();
            tp.add("text fields", outerSecond);
            tp.add("labels", outerFirst);
            tp.add("mixed", outerThird);

            // add the tabbed pane to the frame
            this.add(tp);

            // pack it and ship it.
            pack();
            setVisible(true);
        }
    }
