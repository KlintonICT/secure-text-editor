/*******************************************************************
*	Member 1: Vipawan Jarukitpipat		ID: 6088044		Section: 1 *
*	Member 2: Klinton Chhun				ID: 6088111		Section: 1 *
********************************************************************/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class SecureTextEditor extends WindowAdapter implements ActionListener{
	private JFrame frame;					// layout
	private JTextArea textArea;				// text for user writing
	private JPanel statusBar;				// bottom author
	private JScrollPane scrollableTextArea;	// make text area scrollable
	private String title = "*Untitled"; 	// title of frame
	private JMenuBar menuBar; 				// menu on layout
	private JMenu file, edit, view;			// list of menu
	private JButton about;					// about us button
	private JCheckBoxMenuItem wordWrap;		// for choosing to whether to enable horizontal scroll view or not
	private JMenuItem newText, open, save, encpVige, encpAes, print, exit, cut, copy, 
	paste, delete, selectAll, timeNDate, format, zoomIn, zoomOut, zoomDefault;
	private String originalText = "";		// track original text of normal text after saving
	private String originalEncText = "";	// track original text of encryption after saving
	private boolean isSaved = false;		// check whether file is save
	
	public SecureTextEditor() {
		// Create a frame
		frame = new JFrame(frameTitle(title));
		AddImageIcon();
		CreateTextArea();
		CreateMenuBar();
		CreateStatusBar();
		System.out.println(textArea.getText());
		frame.getContentPane().add(scrollableTextArea);
		frame.setSize(1000, 600);
		frame.addWindowListener(this); // add listener to window  
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void AddImageIcon() {
		// Create Icon for title bar
		ImageIcon icon = new ImageIcon("C:\\Users\\chhun\\OneDrive\\Documents\\Java\\Secured Text Editor\\src\\secure.png");
		frame.setIconImage(icon.getImage());
	}
	
	public void CreateTextArea() {
		// Create text area for user to write
		textArea = new JTextArea();
		textArea.setFont(textArea.getFont().deriveFont(20f));
		// Make the text area is scrollable
		scrollableTextArea = new JScrollPane(textArea); 
		scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JScrollBar barVer = scrollableTextArea.getVerticalScrollBar();
		JScrollBar barHor = scrollableTextArea.getHorizontalScrollBar();
		barVer.setPreferredSize(new Dimension(25, 0)); // change dimension of scroll bar
		barHor.setPreferredSize(new Dimension(0, 25));
	}
	public void CreateStatusBar() {
		// Add status bar for text editor
		statusBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
		statusBar.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY),
				new EmptyBorder(5, 5, 5, 5))); // create space for status bar
		JLabel status = new JLabel();
		statusBar.add(status);
		frame.setLayout(new BorderLayout());
		frame.add(statusBar, BorderLayout.SOUTH);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				status.setText("© Built by Ampere & Klinton"); // add status bar content
				status.setFont(status.getFont().deriveFont(15f));
			}
		});
	}
	public void CreateMenuBar() {
		// Create menu bar 
		menuBar = new JMenuBar();
		// Create menu list
		file = new JMenu("File");
		edit = new JMenu("Edit");
		view = new JMenu("View");
		format = new JMenu("Format");
		about = new JButton("About");
		about.addActionListener(this);
		file.setFont(file.getFont().deriveFont(18f)); // change font size
		edit.setFont(edit.getFont().deriveFont(18f));
		view.setFont(view.getFont().deriveFont(18f));
		format.setFont(format.getFont().deriveFont(18f));
		about.setFont(about.getFont().deriveFont(18f));
		about.setBorderPainted(false);
		about.setOpaque(false);
		about.setContentAreaFilled(false);
		about.setFocusPainted(false);
		// Create items for file menu
		newText = new JMenuItem("<html><p style='margin-left: 20px;'>New</p></html>") {
			private static final long serialVersionUID = 1L;
			@Override
			public Dimension getPreferredSize() { // change the width dimension for each item menu
				Dimension d = super.getPreferredSize();
				d.width = Math.max(d.width, 250); 
		        return d;
			}
		};
		open = new JMenuItem("<html><p style='margin-left: 20px;'>Open...</p></html>");
		save = new JMenuItem("<html><p style='margin-left: 20px;'>Save As Normal Text</p></html>");
		encpVige = new JMenuItem("<html><p style='margin-left: 20px;'>Enc By Vignere Cipher</p></html>");
		encpAes = new JMenuItem("<html><p style='margin-left: 20px;'>Enc By AES</p></html>");
		exit = new JMenuItem("<html><p style='margin-left: 20px;'>Exit</p></html>");
		print = new JMenuItem("<html><p style='margin-left: 20px;'>Print...</p></html>");
		newText.setFont(newText.getFont().deriveFont(18f));
		open.setFont(open.getFont().deriveFont(18f));
		save.setFont(save.getFont().deriveFont(18f));
		encpVige.setFont(encpVige.getFont().deriveFont(18f));
		encpAes.setFont(encpAes.getFont().deriveFont(18f));
		exit.setFont(exit.getFont().deriveFont(18f));
		print.setFont(print.getFont().deriveFont(18f));
		newText.addActionListener(this);
		open.addActionListener(this); // add the items into event action listener to handle action when user click
		save.addActionListener(this);
		encpVige.addActionListener(this);
		encpAes.addActionListener(this);
		print.addActionListener(this);
		exit.addActionListener(this);
		// Create items for edit menu
		cut = new JMenuItem("<html><p style='margin-left: 20px;'>Cut</p></html>") {
			private static final long serialVersionUID = 1L;
			@Override
			public Dimension getPreferredSize() { // change the width dimension for each item menu
				Dimension d = super.getPreferredSize();
				d.width = Math.max(d.width, 200); 
		        return d;
			}
		};
		copy = new JMenuItem("<html><p style='margin-left: 20px;'>Copy</p></html>");
		paste = new JMenuItem("<html><p style='margin-left: 20px;'>Paste</p></html>");
		delete = new JMenuItem("<html><p style='margin-left: 20px;'>Delete</p></html>");
		selectAll = new JMenuItem("<html><p style='margin-left: 20px;'>Select All</p></html>");
		timeNDate = new JMenuItem("<html><p style='margin-left: 20px;'>Time/Date</p></html>");
		cut.setFont(cut.getFont().deriveFont(18f));
		copy.setFont(copy.getFont().deriveFont(18f));
		paste.setFont(paste.getFont().deriveFont(18f));
		delete.setFont(delete.getFont().deriveFont(18f));
		selectAll.setFont(selectAll.getFont().deriveFont(18f));
		timeNDate.setFont(timeNDate.getFont().deriveFont(18f));
		cut.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		delete.addActionListener(this);
		selectAll.addActionListener(this);
		timeNDate.addActionListener(this);
		// create items fro format menu
		wordWrap = new JCheckBoxMenuItem("<html><p style='margin-left: 20px;'>Word Wrap</p></html>") {
			private static final long serialVersionUID = 1L;
			@Override
			public Dimension getPreferredSize() { // change the width dimension for each item menu
				Dimension d = super.getPreferredSize();
				d.width = Math.max(d.width, 200); 
		        return d;
			}
		};  
		wordWrap.setFont(wordWrap.getFont().deriveFont(18f));
		wordWrap.addActionListener(this);
		// create items for view menu
		zoomIn = new JMenuItem("<html><p style='margin-left: 20px;'>Zoom In</p></html>") {
			private static final long serialVersionUID = 1L;
			@Override
			public Dimension getPreferredSize() { // change the width dimension for each item menu
				Dimension d = super.getPreferredSize();
				d.width = Math.max(d.width, 250); 
		        return d;
			}
		};
		zoomOut = new JMenuItem("<html><p style='margin-left: 20px;'>Zoom Out</p></html>");
		zoomDefault = new JMenuItem("<html><p style='margin-left: 20px;'>Restore Default Zoom</p></html>");
		zoomIn.setFont(zoomIn.getFont().deriveFont(18f));
		zoomOut.setFont(zoomOut.getFont().deriveFont(18f));
		zoomDefault.setFont(zoomDefault.getFont().deriveFont(18f));
		zoomIn.addActionListener(this);
		zoomOut.addActionListener(this);
		zoomDefault.addActionListener(this);
		// Create Separator for item
		JSeparator sep1 = new JSeparator() {
			private static final long serialVersionUID = 1L;
			@Override
			public Dimension getMaximumSize() {
				return new Dimension(200, 25);
			}
		};
		JSeparator sep2 = new JSeparator() {
			private static final long serialVersionUID = 1L;
			@Override
			public Dimension getMaximumSize() {
				return new Dimension(200, 25);
			}
		};
		JSeparator sep3 = new JSeparator() {
			private static final long serialVersionUID = 1L;
			@Override
			public Dimension getMaximumSize() {
				return new Dimension(180, 25);
			}
		};
		// Add all items to its menu list
		file.add(newText); file.add(open); file.add(save); file.add(encpVige); file.add(encpAes);
		file.add(sep1); file.add(print); file.add(sep2); file.add(exit);
		edit.add(cut); edit.add(copy); edit.add(paste); edit.add(delete); 
		edit.add(sep3); edit.add(selectAll); edit.add(timeNDate);
		format.add(wordWrap);
		view.add(zoomIn); view.add(zoomOut); view.add(zoomDefault);
		// Add menu lists to menu bar
		menuBar.add(file); menuBar.add(edit); menuBar.add(format); menuBar.add(view); menuBar.add(about); 
		frame.setJMenuBar(menuBar);
	}
	
	public String frameTitle(String title) { return title + " - Secure Text Editor"; }
	
	// Handle all the button press
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == newText) {
			if(isSaved && originalText.equals(textArea.getText())) textArea.setText("");
			else {
				if(!originalText.equals(textArea.getText()) && !textArea.getText().isEmpty()) {
					int confirm = JOptionPane.showConfirmDialog(frame, "Do you want to use new text area without saving?");
					if( confirm==JOptionPane.YES_OPTION ) textArea.setText("");
				}else textArea.setText("");
			}
		}
		else if(event.getSource() == open) openFile(); 
		else if(event.getSource() == save) saveFile(textArea.getText(), ".txt");
		else if(event.getSource() == encpVige) encryptText(textArea.getText(), ".encv"); 
		else if(event.getSource() == encpAes) encryptText(textArea.getText(), ".enca");
		else if(event.getSource() == print) {
			try { textArea.print(); }
			catch(Exception error) { error.printStackTrace(); }
		}
		else if(event.getSource() == cut) textArea.cut();
		else if(event.getSource() == copy) textArea.copy();
		else if(event.getSource() == paste) textArea.paste();
		else if(event.getSource() == delete) {
			textArea.setText(textArea.getText().replace(textArea.getSelectedText(),""));
		}
		else if(event.getSource() == selectAll) textArea.selectAll();
		else if(event.getSource() == timeNDate){
			DateFormat dateFormat = new SimpleDateFormat("hh:mm a dd/mm/yyyy");
			Date date = new Date();
			textArea.setText(textArea.getText() + dateFormat.format(date));
		}
		else if(event.getSource() == wordWrap) {
			if(wordWrap.isSelected()) {
				textArea.setLineWrap(true);
		        textArea.setWrapStyleWord(true);
		        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			}
			else {
				textArea.setLineWrap(false);
		        textArea.setWrapStyleWord(false);
		        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			}
		}
		else if(event.getSource() == zoomIn) {
			float increaseFontSize = textArea.getFont().getSize() + 2;
			textArea.setFont(textArea.getFont().deriveFont(increaseFontSize));
		}else if(event.getSource() == zoomOut) {
			float decreaseFontSize = textArea.getFont().getSize() >= 8 ?
					(textArea.getFont().getSize() - 2) : textArea.getFont().getSize();
			textArea.setFont(textArea.getFont().deriveFont(decreaseFontSize));
		}else if(event.getSource() == zoomDefault) {
			textArea.setFont(textArea.getFont().deriveFont(20f));
		}
		else if(event.getSource() == about ) JOptionPane.showMessageDialog(frame,aboutUs(), "About Us",JOptionPane.PLAIN_MESSAGE);
		else if(event.getSource() == exit) {
			if(isSaved && originalText.equals(textArea.getText())) System.exit(0);
			else {
				if(!originalText.equals(textArea.getText()) && !textArea.getText().isEmpty()) {
					int confirm = JOptionPane.showConfirmDialog(frame, "Do you want to quit without saving?");
					if( confirm==JOptionPane.YES_OPTION ) System.exit(0);
				}else System.exit(0);
			}
		}
	}
	
	public JLabel aboutUs() {
		JLabel aboutUs = new JLabel("<html>"
				+ "<center>"
				+ "<h2>ITCS461 Computer and Communication Security</h2>"
				+ "<h2>Project 1: Secure Text Editor</h2>"
				+ "<h3>Built by</h3>"
				+ "<h3>Vipawan Jarukitpipat &nbsp&nbsp ID: 6088044 &nbsp&nbsp Section: 1</h3>"
				+ "<h3>Klinton Chhun &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp ID: 6088111 &nbsp&nbsp Section: 1</h3>"
				+ "</center>"
				+ "</html>");
		aboutUs.setHorizontalAlignment(SwingConstants.CENTER);
		return aboutUs;
	}
	
	// Open file from user directory
	public void openFile() {
		FileDialog openFile = new FileDialog(frame, "Open", FileDialog.LOAD);
		openFile.setVisible(true);
		String filePath = openFile.getDirectory() + openFile.getFile(); // Get file path from user
		BufferedReader bf = null; // Reader file content by using buffer reader
		StringBuffer newTextArea = new StringBuffer(); // store content from file by using StringBuffer
		try {
			bf = new BufferedReader(new FileReader(filePath));
			String line;
			while((line = bf.readLine()) != null ) { // read the file line by line
				newTextArea.append(line + "\n");
			}
		}catch(Exception error) { error.printStackTrace(); }
		// Remove end of line content
		String content = newTextArea.toString().trim(); 
		// Add content to text area
		String[] fileType = openFile.getFile().split("\\.");
		if (fileType[1].equals("txt")) textArea.setText(content);
		else textArea.setText(decryptText(content, fileType[1]));
		isSaved = true;
		originalText = textArea.getText();
		// Update title frame
		frame.setTitle(frameTitle(openFile.getFile()));
	}
	
	// Handle save file
	public void saveFile(String textA, String fileType) {
		FileDialog saveFile = new FileDialog(frame, "Save As", FileDialog.SAVE);
		saveFile.setVisible(true);
		// Save file name
		saveFile.setFile(saveFile.getFile());
		// save text area
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(saveFile.getDirectory()+"\\"+saveFile.getFile()+fileType));
			bf.write(textA);
			bf.close();
		}catch(Exception error) { error.printStackTrace(); }
		// set frame title
		if( saveFile.getFile() != null ) {
			if(fileType.equals(".txt")) originalText = textA;
			else originalText = originalEncText;
			isSaved = true;
			frame.setTitle(frameTitle(saveFile.getFile() + fileType));
		}
	}
	
	public void encryptText(String textA, String fileType) {
		originalEncText = textA;
		String key = "";
		// This will have pop up dialog for user to input their key
		if(fileType.equals(".encv")) {
			key = JOptionPane.showInputDialog(frame, "Encrypt file with Vigenere Cipher\nEnter your key to encrypt:");
			if(key != null) saveFile(new VigenereCipher(textA, key).encrypt(), fileType);
		}else if(fileType.equals(".enca")) {
			key = JOptionPane.showInputDialog(frame, "Encrypt file with Advanced Ecryption Standard\nEnter your key to encrypt:");
			if(key != null) saveFile(new AES(textA, key).encrypt(), fileType);
		}
	}
	
	public String decryptText(String textA, String fileType) {
		String plainText = "", key = "";
		if(fileType.equals("encv")) {
			key = JOptionPane.showInputDialog(frame, "Decrypt Vigenere Cipher\nEnter your key to decrypt:");
			plainText = new VigenereCipher(textA, key).decrypt();
		}else if(fileType.equals("enca")) {
			key = JOptionPane.showInputDialog(frame, "Decrypt Advanced Encryption Standard\nEnter your key to decrypt:");
			plainText = new AES(textA, key).decrypt();
		}
		return plainText;
	}
	
	// handle window close event
	public void windowClosing(WindowEvent event) {    
		if(isSaved && originalText.equals(textArea.getText())) frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		else {
			if(!originalText.equals(textArea.getText()) && !textArea.getText().isEmpty()) {
				int confirm = JOptionPane.showConfirmDialog(frame, "Do you want to quit without saving?");
				if( confirm==JOptionPane.YES_OPTION ) frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}else frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
	public static void main(String[] args) {
		new SecureTextEditor();
	}
}
