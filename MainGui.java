/**
@author Parth Mittal (2015069)
@author Rishabh Garg (2015076)
*/
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

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
	private static boolean sinceFlag;  // true for since button, false for range
	private static boolean sortByDateFlag;  // true for by date button, false for relevance
	private static JTextField findByField;
	private static JTextField sinceYrField;
	private static JTextField rangeYrFieldi;
	private static JTextField rangeYrFieldf; 
	private static JTextField publField; 
	private static JTextField authField;
	private static String findByInput;
	private static int sinceYrVal;
	private static int startYrVal;
	private static int endYrVal;
	private static int publVal;
	private static int i;
	private static String authVal;
	private static QueryEngine queryEngine;
	private static DefaultTableModel tabModel;
	private static JTable resultTable ;
	private static JScrollPane tablePane ;

	public static JPanel getMainPanel()
	{
		mainPanel = new JPanel(new GridBagLayout());

		// mainPanel.setBackground(Color.RED);

		namePanel = new JPanel(new GridBagLayout());
		
		JLabel nameLabel = new JLabel("DBLP Query Engine");
		nameLabel.setFont(new Font("Serif", Font.BOLD, 36));
//		nameLabel.setFont(nameLabel.getFont().deriveFont(32f));
		namePanel.add(nameLabel); 
		
		// Debug
//		namePanel.setBackground(Color.BLUE);
		
		Border raisedetched = BorderFactory.createLineBorder(Color.BLUE, 3);
		namePanel.setBorder(raisedetched);
		
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
		// querySelectPanel.setBackground(Color.PINK);
		
		// TODO: Add a disabled first button
		String[] queryList = {"Select a query", "Query 1", "Query 2", "Query 3"};
		querybox = new JComboBox<String>(queryList);
		querySelectPanel.add(querybox);
		querySelectPanel.add(Box.createVerticalStrut(200));
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
	        		tabModel.setRowCount(0);
		    		mainQuerySelectPanel.validate();
		    		mainQuerySelectPanel.repaint();
				}
		        else
    		        updateQueryPanel(selectedQuery);
			}
		});
		
		// Debug
		// mainQuerySelectPanel.setBackground(Color.GREEN);
		
		c.weightx = 0.3;
		c.weighty = 1.0;
		c.ipadx = 200;
		c.ipady = 300;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
//		c.gridheight = 5;
		mainQuerySelectPanel.add(querySelectPanel);
		Border raisedetched2 = BorderFactory.createLineBorder(Color.BLUE, 3);
		mainQuerySelectPanel.setBorder(raisedetched2);
		mainPanel.add(mainQuerySelectPanel, c);
		
		resultDisplayPanel = new JPanel(new GridBagLayout());
        
        final ArrayList<ArrayList<String>> rowData = new ArrayList<ArrayList<String>>();
		Object columnNames[] = { "SNo.", "Authors", "Title", "Pages", "Year", "Volume", "Journal/Booktitle", "URL"};
		tabModel = new DefaultTableModel(0,8);
		tabModel.setColumnIdentifiers(columnNames);
		tabModel.setRowCount(0);
		resultTable = new JTable(tabModel);
		resultTable.setPreferredScrollableViewportSize(new Dimension(500, 400));
		tablePane = new JScrollPane(resultTable);
		tablePane.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		resultDisplayPanel.add(tablePane);

//		showMoreEntries(table, rowData, 0);
		
		JButton nextButton = new JButton("Next");
		nextButton.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
		resultDisplayPanel.add(nextButton);
		nextButton.addActionListener(new ActionListener() 
		{
		 public void actionPerformed(ActionEvent e) {
//			 showMoreEntries(table, rowData, pageNumber++);
		 	}
		});


		// Debug
		// resultDisplayPanel.setBackground(Color.YELLOW);
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.ipadx = 15;
		c.ipady = 50;
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

		//Reset values
		sinceYrVal = 0;
		startYrVal = 0;
		endYrVal = 2020;
		sortByDateFlag = true;
		sinceFlag = false;

		
		JRadioButton authorButton = new JRadioButton("Find by Author Name");
	    JRadioButton titleButton = new JRadioButton("Find by Title Tags");
	    ButtonGroup bG = new ButtonGroup();
	    bG.add(authorButton);
	    bG.add(titleButton);
	    findByField = new JTextField(100);
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
			    // findByInput = findByField.getText();
		    }
	    });

		JRadioButton sinceYrButton = new JRadioButton("Since Year");
	    sinceYrField = new JTextField(40);
	    JRadioButton rangeYrButton = new JRadioButton("Custom Range");
	    ButtonGroup bG2 = new ButtonGroup();
	    bG2.add(sinceYrButton);
	    bG2.add(rangeYrButton);

	    JPanel rangePanel = new JPanel();
		rangePanel.setLayout(new BoxLayout(rangePanel, BoxLayout.X_AXIS));
	    rangeYrFieldi = new JTextField(10);
	    rangeYrFieldf = new JTextField(10);
	    JLabel dashlabel = new JLabel(" -- ");

		querySelectPanel.add(sinceYrButton);
		querySelectPanel.add(Box.createVerticalStrut(5));
		querySelectPanel.add(sinceYrField);
		querySelectPanel.add(Box.createVerticalStrut(5));
		querySelectPanel.add(rangeYrButton);
		querySelectPanel.add(Box.createVerticalStrut(5));
		rangePanel.add(rangeYrFieldi);
		rangePanel.add(dashlabel);
		rangePanel.add(rangeYrFieldf);
		querySelectPanel.add(rangePanel);
		querySelectPanel.add(Box.createVerticalStrut(10));

	    sinceYrField.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e) 
		    {
			    // sinceYrVal = Integer.parseInt(sinceYrField.getText());
		    }
	    });

	    rangeYrFieldi.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e) 
		    {
			    // startYrVal = Integer.parseInt(rangeYrFieldi.getText());
		    }
	    });

	    rangeYrFieldf.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e) 
		    {
			    // endYrVal = Integer.parseInt(rangeYrFieldf.getText());
		    }
	    });

		sinceYrButton.addItemListener(new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED){
                    	sinceFlag = true;
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
                        sinceFlag = false;
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

		JRadioButton dateButton = new JRadioButton("Sort By date");
	    JRadioButton relevButton = new JRadioButton("Sort by relevance");
	    ButtonGroup bG3 = new ButtonGroup();
	    bG3.add(dateButton);
	    bG3.add(relevButton);
		querySelectPanel.add(dateButton);
		querySelectPanel.add(Box.createVerticalStrut(5));
		querySelectPanel.add(relevButton);
		querySelectPanel.add(Box.createVerticalStrut(5));

	    dateButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
				sortByDateFlag = true;
			}
		});
		
		relevButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
				sortByDateFlag = false;
			}
		});
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
				
				
				try{
					sinceYrVal = Integer.parseInt(sinceYrField.getText());
				}
				catch(NumberFormatException e) {
					sinceYrVal = 0;
					if(sinceFlag == true)
					{
						JFrame errorFrame = new JFrame();
						JOptionPane.showMessageDialog(errorFrame, "Invalid input. Need an integer", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				try{
					startYrVal = Integer.parseInt(rangeYrFieldi.getText());
				}
				catch(NumberFormatException e) {
					startYrVal = 0;
					if(sinceFlag == false)
					{
						JFrame errorFrame = new JFrame();
						JOptionPane.showMessageDialog(errorFrame, "Invalid input. Need an integer for starting year", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				try{
					endYrVal = Integer.parseInt(rangeYrFieldf.getText());
				}
				catch(NumberFormatException e) {
					endYrVal = 2020;
					if(sinceFlag == false)
					{
						JFrame errorFrame = new JFrame();
						JOptionPane.showMessageDialog(errorFrame, "Invalid input. Need an integer for ending year", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			
				findByInput = findByField.getText();
				if(findByInput == "")
				{
					JFrame errorFrame = new JFrame();
					JOptionPane.showMessageDialog(errorFrame, "Invalid input. Empty data", "Error", JOptionPane.ERROR_MESSAGE);
					querybox.setSelectedIndex(0);
				}

				if (sortByDateFlag == true )
					queryEngine.setSortByDate(true);
				else
					queryEngine.setSortByRelevance(true);

				if(sinceFlag == true)
				{
					queryEngine.setSinceYear(sinceYrVal);
					queryEngine.setToYear(2020);
				}
				else
				{
					queryEngine.setSinceYear(startYrVal);
					queryEngine.setToYear(endYrVal);
				}
				
				System.out.println(findByInput);
				System.out.println(sinceYrVal);
				System.out.println(startYrVal);
				System.out.println(endYrVal);

				ArrayList<Publication> ans = null;
				tabModel.setRowCount(0);
				if(authorFlag == true)
				{	
					// 1a
		        	int ikcount = 1;
					try {
						ans = queryEngine.query1a(findByInput);
					} catch (IOException | SAXException | ParserConfigurationException e) {
						e.printStackTrace();
					}
					if( ans.size() == 0)
					{
						tabModel.addRow(new Object[]{"No results Found!"});
					}
					else{
				        for (Publication x : ans) {
				    		Object tempPrint[] = new Object[]{ikcount,x.getAuthors(),x.getTitle(),x.getPages(),x.getYear(),x.getVolume(),x.getJournal(),x.getUrl()}; 
				        	tabModel.addRow(tempPrint);
				        	ikcount++;
				        }						
					}
				}
				else
				{
		        	int ikcount = 1;
		        	String splitVals[] = findByInput.split(" ");
		        	ArrayList<String> keywords = new ArrayList<>();
		        	for(String words : splitVals){
		        		keywords.add(words);
		        	}

					try {
						ans = queryEngine.query1b(keywords);
					} catch (IOException | SAXException | ParserConfigurationException e) {
						e.printStackTrace();
					}
					if( ans.size() == 0)
					{
						tabModel.addRow(new Object[]{"No results Found!"});
					}
					else{
						for (Publication x : ans) {
				    		Object tempPrint[] = new Object[]{ikcount,x.getAuthors(),x.getTitle(),x.getPages(),x.getYear(),x.getVolume(),x.getJournal(),x.getUrl()}; 
				        	tabModel.addRow(tempPrint);
				        	ikcount++;
				        }
				    }
				}
			}
		});

		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
        		querybox.setSelectedIndex(0);
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(submitButton);
		buttonPanel.add(resetButton);
		querySelectPanel.add(buttonPanel);

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
			    // publVal = Integer.parseInt(publField.getText());
		    }
	    });

		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
				ArrayList<Person> ans2 = null;

				try{
				    publVal = Integer.parseInt(publField.getText());
				}
				catch(NumberFormatException e) {
						JFrame errorFrame = new JFrame();
						JOptionPane.showMessageDialog(errorFrame, "Invalid input. Need an integer", "Error", JOptionPane.ERROR_MESSAGE);
					}
				

	        	tabModel.setRowCount(0);
	        	int ikcount = 1;
				try {
					ans2 = queryEngine.query2(publVal);
				} catch (ParserConfigurationException | SAXException | IOException e) {
					e.printStackTrace();
				}
				if( ans2.size() == 0)
				{
					tabModel.addRow(new Object[]{"No results Found!"});
				}
				else{
			        for (Person x : ans2) {
			        	System.out.println(x.getNames().get(0));
			        	tabModel.addRow(new Object[]{ikcount, x.getNames().get(0)});
			        	ikcount++;
			        }
			    }
		         System.out.println(ans2.size());
			}
		});

		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
        		querybox.setSelectedIndex(0);
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(submitButton);
		buttonPanel.add(resetButton);
		querySelectPanel.add(buttonPanel);

		mainQuerySelectPanel.validate();
		mainQuerySelectPanel.repaint();
	}

	public static void onQuery3()
	{
		querySelectPanel.removeAll();
		querybox.setSelectedIndex(3);
		querySelectPanel.add(Box.createVerticalStrut(25));
		querySelectPanel.add(querybox);
	
		JLabel authLabel = new JLabel("Authors :");
		authField = new JTextField(60);
		querySelectPanel.add(Box.createVerticalStrut(5));
		querySelectPanel.add(authField);

	    JLabel publLabel = new JLabel("Enter Year");
		publField = new JTextField(40);

		querySelectPanel.add(publLabel);
		querySelectPanel.add(Box.createVerticalStrut(5));
		querySelectPanel.add(publField);
		

		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
				authVal = authField.getText();
				try{
					publVal = Integer.parseInt(publField.getText());
				}
				catch(NumberFormatException e) {
					publVal = 0;
				}

				int ans = -1;
	        	tabModel.setRowCount(0);
				try {
					ans = queryEngine.query3(authVal, publVal);
				} catch (ParserConfigurationException | SAXException | IOException e) {
					e.printStackTrace();
				}
				tabModel.addRow(new Object[]{"No of publications predicted", ans});

			}
		});
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent	event)
			{
        		querybox.setSelectedIndex(0);
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(submitButton);
		buttonPanel.add(resetButton);
		querySelectPanel.add(buttonPanel);

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

	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException
	{
		EventQueue.invokeLater(new Runnable() {
            public void run() {
             	try {
				 	queryEngine = new QueryEngine("dblp.xml");
				 } catch (ParserConfigurationException e) {
				 	e.printStackTrace();
				 } catch (SAXException e) {
				 	e.printStackTrace();
				 } catch (IOException e) {
				 	e.printStackTrace();
				 }
                System.out.println("Done parsing authors list");
            	displayGUI();
            }
        });
	}

}
