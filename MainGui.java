import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainGui {

	
	public static JPanel getMainPanel()
	{
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		mainPanel.setBackground(Color.RED);

		JPanel namePanel = new JPanel(new GridBagLayout());
		JButton button1 = new JButton("name");
		namePanel.add(button1);
		// Debug
		namePanel.setBackground(Color.BLUE);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 1.0;
//		c.ipadx = 100;
		c.ipady = 20;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
//		c.gridheight = 1;
		mainPanel.add(namePanel, c);


		JPanel querySelectPanel = new JPanel(new GridBagLayout());
		JButton button2 = new JButton("queryselect");
		querySelectPanel.add(button2);
		// Debug
		querySelectPanel.setBackground(Color.GREEN);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.3;
		c.weighty = 1.0;
		c.ipadx = 200;
		c.ipady = 500;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
//		c.gridheight = 5;
		mainPanel.add(querySelectPanel, c);
		

		JPanel resultDisplayPanel = new JPanel(new GridBagLayout());
		JButton button3 = new JButton("result display");
		resultDisplayPanel.add(button3);
		// Debug
		resultDisplayPanel.setBackground(Color.YELLOW);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.ipadx = 350;
		c.ipady = 500;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
//		c.gridheight = 5;
		mainPanel.add(resultDisplayPanel, c);

		
		return mainPanel;
	}
	
	public static void displayGUI()
	{
		JFrame mainFrame = new JFrame();
		
		JPanel mainPanel = getMainPanel();
		mainFrame.getContentPane().add(mainPanel);
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
//		mainFrame.setSize(700, 500);
		mainFrame.setTitle("DBLP Query Engine");
		mainFrame.pack();
	}

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() {
            public void run() {
            	displayGUI();
            }
        });
	}

}
