package utils;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.SQLException;

import javax.swing.JLabel;

public class InitialWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField passwordText;
	private final String explanationText="Questo gioco è ancora in versione alfa. Per il momento"
			+ " per giocare avrai bisogno di un database mySql nel computer, e dovrai inserire la tua "
			+ "password nel campo sottostante. Clicca invece il link in basso per scaricare un "
			+ "breve manuale. Se dopo aver inserito la password e cliccato su \"Vai al gioco\" "
			+ "non succede niente, digita \"Localhost:8080\" nel tuo browser";
	private JLabel msgLabel;


	/**
	 * Create the frame.
	 */
	public InitialWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500,650);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton enterPasswordBtn = new JButton("Inserisci password");
		enterPasswordBtn.setBounds(169, 442, 161, 29);
		enterPasswordBtn.addActionListener(e->{
			try {
				setPassword();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		contentPane.add(enterPasswordBtn);
		
		passwordText = new JTextField();
		passwordText.setBounds(169, 483, 161, 26);
		contentPane.add(passwordText);
		passwordText.setColumns(10);
		
		JTextArea msgArea = new JTextArea(explanationText);
		msgArea.setBackground(SystemColor.window);
		msgArea.setBounds(31, 69, 438, 132);
		msgArea. setLineWrap(true);
		msgArea.setEditable(false);
		
		contentPane.add(msgArea);
		
		JButton startGameBtn = new JButton("Vai al gioco");
		startGameBtn.setBounds(37, 213, 161, 29);
		startGameBtn.setBorderPainted(false);
        startGameBtn.setContentAreaFilled(false);
        startGameBtn.setFocusPainted(false);
        startGameBtn.setForeground(Color.BLUE);
        startGameBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startGameBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        startGameBtn.addActionListener(e->startGame());
        startGameBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startGameBtn.setForeground(Color.RED); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startGameBtn.setForeground(Color.BLUE); 
            }
        });
		contentPane.add(startGameBtn);
		
		JButton guideDownloadBtn = new JButton("Scarica manuale");
		guideDownloadBtn.setBounds(250, 213, 161, 29);
		 guideDownloadBtn.setBorderPainted(false);
         guideDownloadBtn.setContentAreaFilled(false);
         guideDownloadBtn.setFocusPainted(false);
         guideDownloadBtn.setForeground(Color.BLUE);
         guideDownloadBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
         guideDownloadBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
         guideDownloadBtn.addActionListener(e->downloadGuide());
         guideDownloadBtn.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseEntered(MouseEvent e) {
                 guideDownloadBtn.setForeground(Color.RED); 
             }

             @Override
             public void mouseExited(MouseEvent e) {
                 guideDownloadBtn.setForeground(Color.BLUE); 
             }
         });
		contentPane.add(guideDownloadBtn);
		
		msgLabel = new JLabel("");
		msgLabel.setBounds(169, 391, 230, 16);
		msgLabel.setForeground(Color.RED);
		contentPane.add(msgLabel);
	}
	private void startGame() {
		msgLabel.setText("");
		if(passwordText.getText().equals("")||passwordText.getText().equals(null)) {
			msgLabel.setText("Inserisci una password!");
			return;
		}
		 try {
	            // Specifica l'URL che vuoi aprire
	            String url = "localhost:8080";

	            // Controlla se il Desktop è supportato
	            if (Desktop.isDesktopSupported()) {
	                Desktop desktop = Desktop.getDesktop();

	                // Apri l'URL nel browser predefinito
	                desktop.browse(new URI(url));
	            } else {
	                System.out.println("Desktop non supportato.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	private void setPassword() throws IOException {
        msgLabel.setText("");
        String password = passwordText.getText();
        if (password.equals("") || password.equals(null)) {
            msgLabel.setText("Inserisci una password!");
            return;
        }
        DataBaseLoader.setPassword(password);
        if(!DataBaseLoader.canConnect()) {
            msgLabel.setText("Password errata");
            return;
        }
        
        msgLabel.setText("Password inserita correttamente");
        Main.startGame();
    }
	
	 private void downloadGuide() {
	        msgLabel.setText("");

	        String resourcePath = "/resources/despiteEverythingSpiegazione.pdf";

	        File downloadDir = FileSystemView.getFileSystemView().getDefaultDirectory();
	        File saveFile = new File(downloadDir, "despiteEverythingGuida.pdf");

	        try {
	            saveResourceToFile(resourcePath, saveFile.getAbsolutePath());
	            msgLabel.setText("Guida scaricata correttamente");
	        } catch (Exception ex) {
	            msgLabel.setText("Errore nel download, riprova");
	            ex.printStackTrace();
	        }
	    }

	   

	    public static void saveResourceToFile(String resourcePath, String savePath) throws IOException {
	        InputStream inputStream = InitialWindow.class.getResourceAsStream(resourcePath);

	        if (inputStream == null) {
	            throw new FileNotFoundException("Risorsa non trovata: " + resourcePath);
	        }

	        try (FileOutputStream outputStream = new FileOutputStream(savePath)) {
	            byte[] buffer = new byte[4096];
	            int bytesRead;
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
	        } finally {
	            inputStream.close();
	        }
	    }
}
