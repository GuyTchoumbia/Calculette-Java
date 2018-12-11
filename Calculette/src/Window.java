import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Window extends JFrame {
	
	private JLabel display;
	OperatorButtonListener obl = new OperatorButtonListener();
	DigitButtonListener dbl = new DigitButtonListener();
	ResetButtonListener rbl = new ResetButtonListener();
	private String operator = "";
	private String result = "";
	private String firstNum = "";	

	public Window() {	//fenetre
		this.setSize(250, 250);
		this.setTitle("Calculette");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		//4 panels, 1 main attached to the content pane containing the 3 others so we may place them as we wish: 1 for the label, 1 for the digits and one for the operators
		JPanel mainPanel = new JPanel();
		
		JPanel labelPanel = new JPanel();
		
		JPanel digitPanel = new JPanel();
		GridLayout digitGrid = new GridLayout(4,2);
		digitGrid.setHgap(2);
		digitGrid.setVgap(2);
		digitPanel.setLayout(digitGrid);
		
		JPanel operatorPanel = new JPanel();
		GridLayout operatorGrid = new GridLayout(6,1);
		operatorGrid.setHgap(2);
		operatorGrid.setVgap(2);
		operatorPanel.setLayout(operatorGrid);
		
		JPanel resetPanel = new JPanel();
				
		JLabel display = new JLabel("0");
		display.setPreferredSize(new Dimension(180, 30));
		display.setBackground(Color.gray);
		display.setBorder(BorderFactory.createEtchedBorder());	
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(display);		
		
		OperatorButtonListener obl = new OperatorButtonListener();
		
		final class MyButton extends JButton{	
			
			public MyButton(String text, int size, JPanel panel){		
				this.setText(text);
				this.setSize(size, size);
				panel.add(this);
				if (panel == digitPanel) 
					this.addActionListener(dbl);
				else if (panel ==operatorPanel)
					this.addActionListener(obl);
			}
		}
		/* boutons
		 * d'abord les numeriques
		 */		
		MyButton[] digits = new MyButton[10];
		for (int i = 9; i>=0; i--) {
			digits[i] = new MyButton(String.valueOf(i), 40, digitPanel) ;
		}
		// les signes
		String[] operators = {"+", "-", "X", "/", ".", "="};		
		
		MyButton[] operatorButtons = new MyButton[operators.length];
		for (int i = 0; i<operators.length; i++) {
			operatorButtons[i] = new MyButton(operators[i], 30, operatorPanel); 
		}
		
		// le reset		
		JButton resetButton = new MyButton("C", 50, resetPanel); 		
		
		// l'affichage	
		this.getContentPane().add(mainPanel);		
		mainPanel.add(labelPanel, BorderLayout.NORTH);
		mainPanel.add(digitPanel, BorderLayout.CENTER);
		mainPanel.add(operatorPanel, BorderLayout.WEST);
		mainPanel.add(resetPanel, BorderLayout.SOUTH);
		this.setVisible(true);		
		
	}	
	
	public void setDisplay(String s) {
		display.setText(s);
	}	
	
			

	final class OperatorButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (result != "" && firstNum != "" && operator != "")
				calc();
			operator = ((JButton) e.getSource()).getText();	
			System.out.println(result+operator+firstNum);			 
		}
	}	
		
	
	public class DigitButtonListener implements ActionListener {
	
		public void actionPerformed(ActionEvent e) {
			String input =  ((JButton) e.getSource()).getText();
			if (operator != "") {
				firstNum += input;				
			}
			else if (operator == "") {
				result += input;				
			}
			System.out.println(result+operator+firstNum);
		}	
	}
	
	public class ResetButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			operator ="";
			result = "";
			firstNum = "";			
		}
		
	}
	
	private void calc() {
		float nResult = Float.valueOf(result);
		float nFirstNum = Float.valueOf(firstNum);
		switch (operator) {
			case "+" : 
				nResult = nResult + nFirstNum;
				break;
			case "-" :
				nResult = nResult - nFirstNum;
				break;
			case "X" : 
				nResult = nResult * nFirstNum;
				break;
			case "/" :
				try {
					nResult = nResult / nFirstNum;
					break;
				}
				catch (ArithmeticException e) {
					result = "error";				
				}
			}
		result = String.valueOf(nResult);
		firstNum = "";
		System.out.println(result);
	}
}
