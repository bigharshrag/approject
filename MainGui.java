import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainGui {

	private static JPanel mainPanel;
	private static JPanel namePanel;
	private static JPanel mainQuerySelectPanel;
	private static JPanel querySelectPanel;
	private static JPanel resultDisplayPanel;
	private static JComboBox<String> querybox; 
	// private static JRadioButton rangeYrButton;
	private static JButton submitButton;
	private static JButton resetButton;
	private static GridBagConstraints c = new GridBagConstraints();
	private static boolean authorFlag;  // true for author button, false for title
	private static JTextField findByField;
	private static JTextField sinceYrField;
	private static JTextField rangeYrFieldi;
	private static JTextField rangeYrFieldf; 
	private static JTextField publField; 
	private static String findByInput;
	private static int sinceYrVal;
	private static int startYrVal;
	private static int endYrVal;
	private static int publVal;
	
	public static JPanel getMainPanel()
	{
		mainPanel = new JPanel(new GridBagLayout());

		mainPanel.setBackground(Color.RED);

		namePanel = new JPanel(new GridBagLayout());
		
		JLabel nameLabel = new JLabel("DBLP Query Engine");
		nameLabel.setFont(new Font("Serif", Font.BOLD, 36));
//		nameLabel.setFont(nameLabel.getFont().deriveFont(32f));
		namePanel.add(nameLabel); 
		
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

		mainQuerySelectPanel = new JPanel(new GridBagLayout());
		querySelectPanel = new JPanel();
		querySelectPanel.setLayout(new BoxLayout(querySelectPanel, BoxLayout.Y_AXIS));
		querySelectPanel.setBackground(Color.PINK);
		
		// TODO: Add a disabled first button
		String[] queryList = {"Select a query", "Query 1", "Query 2", "Query 3"};
		querybox = new JComboBox<String>(queryList);
		querySelectPanel.add(querybox);
		querySelectPanel.add(Box.createVerticalStrut(25));
		querybox.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
				JComboBox<String> cb = (JComboBox<String>)event.getSource();
		        String selectedQuery = (String)cb.getSelectedItem();
		        if(selectedQuery.equals("Select a query"))
				{
	        		querySelectPanel.removeAll();
	        		querySelectPanel.add(querybox);
	        		querySelectPanel.add(Box.createVerticalStrut(25));
		    		mainQuerySelectPanel.validate();
		    		mainQuerySelectPanel.repaint();
				}
		        else
    		        updateQueryPanel(selectedQuery);
			}
		});
		
		// Debug
		mainQuerySelectPanel.setBackground(Color.GREEN);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.3;
		c.weighty = 1.0;
		c.ipadx = 200;
		c.ipady = 500;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
//		c.gridheight = 5;
		mainQuerySelectPanel.add(querySelectPanel);
		mainPanel.add(mainQuerySelectPanel, c);
		

		resultDisplayPanel = new JPanel(new GridBagLayout());
		JButton button3 = new JButton("result display");
		resultDisplayPanel.add(button3);
		// Debug
		resultDisplayPanel.setBackground(Color.YELLOW);
		c.fill = GridBagConstraints.VERTICAL;
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
	
	public static void updateQueryPanel(String queryChoice)
	{
		if(queryChoice.equals("Query 1"))
			onQuery1();
		else if(queryChoice.equals("Query 2"))
			onQuery2();
		else if(queryChoice.equals("Query 3"))
			onQuery3();			
	}


	public static void onQuery1()
	{
		querySelectPanel.removeAll();
		querybox.setSelectedIndex(1);
		querySelectPanel.add(querybox);
		querySelectPanel.add(Box.createVerticalStrut(25));
		
		JRadioButton authorButton = new JRadioButton("Find by Author Name");
	    JRadioButton titleButton = new JRadioButton("Find by Title Tags");
	    ButtonGroup bG = new ButtonGroup();
	    bG.add(authorButton);
	    bG.add(titleButton);
	    findByField = new JTextField(40);
		JLabel findLabel = new JLabel("Enter author/Title");

		querySelectPanel.add(authorButton);
		querySelectPanel.add(Box.createVerticalStrut(5));
		querySelectPanel.add(titleButton);
		querySelectPanel.add(Box.createVerticalStrut(10));
		querySelectPanel.add(findLabel);
		querySelectPanel.add(Box.createVerticalStrut(5));
		querySelectPanel.add(findByField);
		querySelectPanel.add(Box.createVerticalStrut(25));
		
		authorButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
				authorFlag = true;
			}
		});
		
		titleButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
				authorFlag = false;
			}
		});

		findByField.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e) 
		    {
			    findByInput = findByField.getText();
		    }
	    });

		JRadioButton sinceYrButton = new JRadioButton("Since Year");
	    sinceYrField = new JTextField(40);
	    JRadioButton rangeYrButton = new JRadioButton("Custom Range");
	    rangeYrFieldi = new JTextField(10);
	    rangeYrFieldf = new JTextField(10);
	    ButtonGroup bG2 = new ButtonGroup();
	    bG2.add(sinceYrButton);
	    bG2.add(rangeYrButton);
		querySelectPanel.add(sinceYrButton);
		querySelectPanel.add(Box.createVerticalStrut(5));
		querySelectPanel.add(sinceYrField);
		querySelectPanel.add(Box.createVerticalStrut(5));
		querySelectPanel.add(rangeYrButton);
		querySelectPanel.add(Box.createVerticalStrut(5));
		querySelectPanel.add(rangeYrFieldi);
		querySelectPanel.add(rangeYrFieldf);
		querySelectPanel.add(Box.createVerticalStrut(10));

		findByField.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e) 
		    {
			    findByInput = findByField.getText();
		    }
	    });

	    sinceYrField.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e) 
		    {
			    sinceYrVal = Integer.parseInt(sinceYrField.getText());
		    }
	    });

	    rangeYrFieldi.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e) 
		    {
			    sinceYrVal = Integer.parseInt(rangeYrFieldi.getText());
		    }
	    });

	    rangeYrFieldf.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e) 
		    {
			    endYrVal = Integer.parseInt(rangeYrFieldf.getText());
		    }
	    });

		sinceYrButton.addItemListener(new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED){
                        sinceYrField.setEnabled(true);
                        sinceYrField.setText("");
                    }
                    else if(e.getStateChange() == ItemEvent.DESELECTED){
                        sinceYrField.setEnabled(false);
                        sinceYrField.setText("Disabled");
                    }

                }
            });

		rangeYrButton.addItemListener(new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED){
                        rangeYrFieldi.setEnabled(true);
                        rangeYrFieldf.setEnabled(true);
                        rangeYrFieldi.setText("");
                        rangeYrFieldf.setText("");
                    }
                    else if(e.getStateChange() == ItemEvent.DESELECTED){
                        rangeYrFieldi.setEnabled(false);
                        rangeYrFieldf.setEnabled(false);
                        rangeYrFieldi.setText("Disabled");
                        rangeYrFieldf.setText("Disabled");
                    }

                }
            });
		
		submitButton = new JButton("Submit");
		querySelectPanel.add(submitButton);
		submitButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
				System.out.println(findByInput);
				System.out.println(sinceYrVal);
				System.out.println(startYrVal);
				System.out.println(endYrVal);
				// if(temp == null)
				// 	System.out.println("Hello");
			}
		});

		resetButton = new JButton("Reset");
		querySelectPanel.add(resetButton);
		resetButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
        		querybox.setSelectedIndex(0);
			}
		});
		mainQuerySelectPanel.validate();
		mainQuerySelectPanel.repaint();
	}

	public static void onQuery2()
	{	
		querySelectPanel.removeAll();
		querybox.setSelectedIndex(2);
		querySelectPanel.add(Box.createVerticalStrut(25));
		querySelectPanel.add(querybox);

		JLabel publLabel = new JLabel("Enter no of publications");
		publField = new JTextField(40);

		querySelectPanel.add(publLabel);
		querySelectPanel.add(Box.createVerticalStrut(5));
		querySelectPanel.add(publField);
		publField.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e) 
		    {
			    publVal = Integer.parseInt(publField.getText());
		    }
	    });

		submitButton = new JButton("Submit");
		querySelectPanel.add(submitButton);

		resetButton = new JButton("Reset");
		querySelectPanel.add(resetButton);
		resetButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
        		querybox.setSelectedIndex(0);
			}
		});

		mainQuerySelectPanel.validate();
		mainQuerySelectPanel.repaint();
	}

	public static void onQuery3()
	{
		querySelectPanel.removeAll();
		querybox.setSelectedIndex(3);
		querySelectPanel.add(Box.createVerticalStrut(25));
		querySelectPanel.add(querybox);

		submitButton = new JButton("Submit");
		querySelectPanel.add(submitButton);

		
		resetButton = new JButton("Reset");
		querySelectPanel.add(resetButton);
		resetButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
        		querybox.setSelectedIndex(0);
			}
		});

		mainQuerySelectPanel.validate();
		mainQuerySelectPanel.repaint();
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
