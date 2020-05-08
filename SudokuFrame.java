import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {
	
	private JButton check_btn;
	private JTextArea sudo_puzzle_area;
	private JTextArea solved_area;
	private JCheckBox auto_check;
	Sudoku sd;
	
	
	public SudokuFrame() {
		
		super("Sudoku Solver");
		setupTextAreas();
		setupCheckers();
		addListeners();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	private void setupCheckers() {
		JPanel checkers_wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
		check_btn = new JButton("Check");
		auto_check = new JCheckBox("Auto Check",true);

		checkers_wrapper.add(check_btn);
		checkers_wrapper.add(auto_check);
		add(checkers_wrapper,BorderLayout.SOUTH);
	}


	private void setupTextAreas() {
		setLayout(new BorderLayout(4,4));
		sudo_puzzle_area = new JTextArea(15,20);
		sudo_puzzle_area.setBorder(new TitledBorder("Puzzle"));
		add(sudo_puzzle_area,BorderLayout.CENTER);
		
		solved_area = new JTextArea(15,20);
		solved_area.setBorder(new TitledBorder("Solution"));
		add(solved_area,BorderLayout.EAST);
	}
	
	private void addListeners() {
		sudo_puzzle_area.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(auto_check.isSelected()) solve();				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(auto_check.isSelected()) solve();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(auto_check.isSelected()) solve();
				
			}
		});		
		check_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				solve();
				
			}
		});
	}
	void solve() {
		try {
			String message="";
			sd = new Sudoku(sudo_puzzle_area.getText());
			int sol_count = sd.solve();
			message += sd.getSolutionText()+"\n";
			message+="solutions: "+sol_count+"\n";
			message+="elapsed: " + sd.getElapsed()+"ms\n";
			solved_area.setText(message);
		}catch(Exception e) {
			solved_area.setText("Parsing Problem");
		}
	}




	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

}
