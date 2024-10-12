package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import java.awt.Font;
/**
 * Classe Vista per 
 * @author Gabriel Carmona Palop y Diego Martínez León
 * @version 1.0
 */
public class Vista extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDirectorio;
	private JButton btnDirectorio;
	private JTextField txtPalabra;
	private JButton btnPalabra;
	private JTextArea txtAreaPrincipal;
	private File directori;
	private static JCheckBox chkMayusculas;
	private static JCheckBox chkAccents;
	private JButton btnReemplazar;
	private JTextField txtRemplazar;

	/**
	 * Metode Principal per a executar el programa 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = new Vista();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Constructor de Vista
	 */
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 707, 469);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(150, 152, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				JLabel lblNewLabel_1 = new JLabel("            AEV - 1 Diego Martinez i Gabriel Carmona");
				lblNewLabel_1.setBackground(new Color(191, 191, 251));
				lblNewLabel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
				lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 17));
				lblNewLabel_1.setBounds(10, 365, 481, 40);
				contentPane.add(lblNewLabel_1);

		txtDirectorio = new JTextField();
		txtDirectorio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtDirectorio.setBorder(new LineBorder(new Color(171, 173, 179)));
		txtDirectorio.setBackground(new Color(177, 180, 248));
		txtDirectorio.setBounds(501, 28, 180, 40);
		contentPane.add(txtDirectorio);
		txtDirectorio.setColumns(10);

		btnDirectorio = new JButton("Buscar");
		btnDirectorio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtDirectorio.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "NO S'HA POGUT REALITZAR LA BUSQUEDA DEL DIRECTORI , TEXT BUIT",
							"ERROR EN BUSCAR", JOptionPane.WARNING_MESSAGE);
				} else {
					directori = new File(txtDirectorio.getText());
					if (!directori.exists()) {
						JOptionPane.showMessageDialog(null, "DIRECTORI NO EXISTENT", "ERROR AMB EL DIRECTORI",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					txtAreaPrincipal.setText(
							mostraArbreDades(directori, "info", 0, txtPalabra.getText(), txtRemplazar.getText()));
				}
			}

		});
		btnDirectorio.setBounds(501, 79, 180, 40);
		contentPane.add(btnDirectorio);

		txtPalabra = new JTextField();
		txtPalabra.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPalabra.setBorder(new LineBorder(new Color(171, 173, 179)));
		txtPalabra.setBackground(new Color(177, 180, 248));
		txtPalabra.setBounds(501, 147, 180, 40);
		contentPane.add(txtPalabra);
		txtPalabra.setColumns(10);

		btnPalabra = new JButton("Buscar coincidencies");
		btnPalabra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPalabra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAreaPrincipal.setText("");
				if (txtDirectorio.getText().equals("") && !txtPalabra.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "NO S'HA POGUT REALITZAR LA BUSQUEDA DEL DIRECTORI , TEXT BUIT",
							"ERROR EN BUSCAR", JOptionPane.WARNING_MESSAGE);
				} else {
					directori = new File(txtDirectorio.getText());
					if (!directori.exists()) {
						JOptionPane.showMessageDialog(null, "DIRECTORI NO EXISTENT", "ERROR AMB EL DIRECTORI",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					txtAreaPrincipal.setText(mostraArbreDades(directori, "coincidencies", 0, txtPalabra.getText(),
							txtRemplazar.getText()));
				}
			}
		});
		btnPalabra.setBounds(501, 250, 180, 40);
		contentPane.add(btnPalabra);

		chkMayusculas = new JCheckBox("Respectar mayuscules");
		chkMayusculas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chkMayusculas.setBackground(new Color(150, 152, 245));
		chkMayusculas.setBounds(501, 194, 168, 23);
		contentPane.add(chkMayusculas);

		chkAccents = new JCheckBox("Respectar accents");
		chkAccents.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chkAccents.setBackground(new Color(150, 152, 245));
		chkAccents.setBounds(501, 220, 168, 23);
		contentPane.add(chkAccents);

		JLabel lblNewLabel = new JLabel("Buscar palabra:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(501, 130, 130, 14);
		contentPane.add(lblNewLabel);

		JLabel lblBuscarDirectorio = new JLabel("Buscar directori:");
		lblBuscarDirectorio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBuscarDirectorio.setBounds(501, 11, 130, 14);
		contentPane.add(lblBuscarDirectorio);

		btnReemplazar = new JButton("Remplaçar coincidencies");
		btnReemplazar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReemplazar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAreaPrincipal.setText("");
				if (txtDirectorio.getText().equals("") || txtPalabra.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "NO S'HA POGUT REALITZAR EL REMPLAÇ  , TEXT BUIT",
							"ERROR EN BUSCAR", JOptionPane.WARNING_MESSAGE);
				} else {
					directori = new File(txtDirectorio.getText());
					if (!directori.exists()) {
						JOptionPane.showMessageDialog(null, "DIRECTORI NO EXISTENT", "ERROR AMB EL DIRECTORI",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					txtAreaPrincipal.setText(
							mostraArbreDades(directori, "Remplazos", 0, txtPalabra.getText(), txtRemplazar.getText()));
				}
			}
		});
		btnReemplazar.setBounds(501, 365, 180, 40);
		contentPane.add(btnReemplazar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 13, 481, 341);
		contentPane.add(scrollPane);

		txtAreaPrincipal = new JTextArea();
		txtAreaPrincipal.setEditable(false);
		txtAreaPrincipal.setBackground(new Color(210, 213, 251));
		scrollPane.setViewportView(txtAreaPrincipal);
		txtAreaPrincipal.setFont(new Font("Monospaced", Font.BOLD, 14));

		txtRemplazar = new JTextField();
		txtRemplazar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtRemplazar.setBorder(new LineBorder(new Color(171, 173, 179)));
		txtRemplazar.setBackground(new Color(177, 180, 248));
		txtRemplazar.setColumns(10);
		txtRemplazar.setBounds(501, 314, 180, 40);
		contentPane.add(txtRemplazar);

		JLabel lblRemplazar = new JLabel("Palabra a remplazar:");
		lblRemplazar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRemplazar.setBounds(501, 297, 130, 14);
		contentPane.add(lblRemplazar);

	}

	/**
	 * Mètode que mostra l'estructura de directoris dins del directori especificat , i la seua informació .
	 * @param directori : Directori especificat
	 * @param opcio : Determina el tipus d'informació a mostrar 
	 * @param subnivell : Comptador que s'utilitza per a identificar el nivell en el qual es troba cada arxiu o directori
	 * @param paraula : Paraula que s'utilitzara en el cas de buscar coincidències o reemplaçar 
	 * @param stringARemplazar : Paraula que s'utilitzara per a reemplaçar
	 * @return String amb l'estructura de directoris del directori especificat en forma d'arbre amb la seua informació desitjada 
	 */
	private static String mostraArbreDades(File directori, String opcio, int subnivell, String paraula,
			String stringARemplazar) {

		String mostrar = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		subnivell++;

		if (subnivell == 1) {
			mostrar += ("" + directori.getName());
		}

		for (File arxiu : directori.listFiles()) {
			if (arxiu.isDirectory()) {
				if (arxiu.listFiles().length != 0) {
					mostrar += ("\n" + posaEspais(subnivell, arxiu) + arxiu.getName());
					mostrar += mostraArbreDades(arxiu, opcio, subnivell, paraula, stringARemplazar);
				}
			} else {
				mostrar += ("\n" + posaEspais(subnivell, arxiu) + arxiu.getName() + " "
						+ (opcio == "info" ? posarInfoArxius(arxiu, sdf)
								: posarInfoArxiusB(arxiu, paraula, opcio, stringARemplazar)));
			}
		}
		return mostrar;
	}

	/**
	 * Mètode que afig espai segons el subnivell en el qual es trobe l'arxiu i el tipus d'arxiu (directori/fitxer)
	 * @param subnivell : Ubicació d'archiu
	 * @param arxiuActual : Arxiu que se li afegiran els espais segons el seu subnivell
	 * @return String amb els caràcters necessaris per a l'arxiu en funció de la ubicació i el tipus de l'arxiu
	 */
	public static String posaEspais(int subnivell, File arxiuActual) {
		String resultat = "|";

		for (int i = 1; i <= subnivell; i++) {
			if (i == subnivell && arxiuActual.isDirectory()) {
				resultat += "-- \\";
			} else if (i == subnivell) {
				resultat += "-- ";
			} else {
				resultat += "   |";
			}
		}
		return resultat;
	}

	/**
	 * Mètode que mostra la informació de cada fitxer amb ultima modificació realitzada i la seua grandària
	 * @param arxiu : Arxiu del qual mostrar la informació
	 * @param sdf : Arreplega l'hora i data de la ultima modificació
	 * @return String amb la informació de l'arxiu especificat
	 */
	public static String posarInfoArxius(File arxiu, SimpleDateFormat sdf) {

		return "(" + String.format("%.1f", (arxiu.length() / 1024.0)) + " KB - " + sdf.format(arxiu.lastModified())
				+ ")";
	}

	/**
	 * Mètode que mostra tant les coincidències com els reemplaçaments efectuats
	 * @param arxiu : Arxiu del qual mostrar la informació
	 * @param stringABuscar : String amb la qual mostrar coincidències i reemplaçar
	 * @param opcio : String que determina el tipus d'acció a que realitzar
	 * @param stringARemplazar : String per a reemplaçar el string a buscar
	 * @return String amb les coincidències o els reemplaçaments efectuats 
	 */
	public static String posarInfoArxiusB(File arxiu, String stringABuscar, String opcio, String stringARemplazar) {
		String mostra = "";
		if (opcio.equals("coincidencies")) {

			if (!esArxiuDeTextPla(arxiu)) {
				mostra = "(Coincidencies: 0)";

			} else if (arxiu.getName().endsWith(".pdf")) {
				try {
					PDDocument document = PDDocument.load(arxiu);
					PDFTextStripper pdfStripper = new PDFTextStripper();
					String texto = pdfStripper.getText(document);
					mostra = "(Coincidencies: " + (Vista.buscardorCoincidencies(texto, stringABuscar)) + ")";
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				mostra = "(Coincidencies: " + (Vista.buscardorCoincidencies(Vista.llegirArxius(arxiu), stringABuscar))
						+ ")";
			}
			return String.format(mostra);

		} else {
			
			String textComplet = Vista.llegirArxius(arxiu);

	        String textFormatejat = textComplet;
	        if (!chkMayusculas.isSelected()) {
	        	textFormatejat = textFormatejat.toLowerCase();
	            stringABuscar = stringABuscar.toLowerCase();
	        }

	        if (!chkAccents.isSelected()) {
	        	textFormatejat = Normalizer.normalize(textFormatejat, Normalizer.Form.NFD);
	        	textFormatejat = textFormatejat.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	            stringABuscar = Normalizer.normalize(stringABuscar, Normalizer.Form.NFD);
	            stringABuscar = stringABuscar.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	        }

	        if (!esArxiuDeTextPla(arxiu)) {
	            return "(Remplazos: 0)";
	        } else {
	            int coincidencies = 0;
	            String textModificat = "";
	            int indexDeCoincidencia = textFormatejat.indexOf(stringABuscar);
	            int indexUltimaCoincidencia = 0;

	            while (indexDeCoincidencia != -1) {
	            	coincidencies++;
	                textModificat += textComplet.substring(indexUltimaCoincidencia, indexDeCoincidencia) + stringARemplazar;
	                indexUltimaCoincidencia = indexDeCoincidencia + stringABuscar.length();
	                indexDeCoincidencia = textFormatejat.indexOf(stringABuscar, indexUltimaCoincidencia);
	            }

	            textModificat += textComplet.substring(indexUltimaCoincidencia);

	            if (!textModificat.equals(textComplet)) {
	                Vista.crearArchiuModificat(textModificat, arxiu);
	            }

	            return "(Remplazos: " + coincidencies + ")";
	        }
	    }    
	}

	/**
	 * Metode que llig l'arxiu i bolca el seu contingut en un string
	 * @param arxiu : Archiu del qual llegir el contingut
	 * @return String amb el text complet de l'archiu
	 */
	public static String llegirArxius(File arxiu) {
		String lineas = "";
		try {
			FileReader fileReader = new FileReader(arxiu, StandardCharsets.UTF_8);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String linea = bufferedReader.readLine();
			while (linea != null) {
				lineas += linea + "\n";
				linea = bufferedReader.readLine();
			}
			bufferedReader.close();
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineas;
	}

	// Preguntar si la palabra a buscar puede estar vacia o no
	/**
	 * Mètode que busca les coincidències d'un string que indiques sobre un string que tinga el text complet
	 * @param textComplet : Text en el qual buscar les coincidències
	 * @param cadenaATrobar : String amb la cadena exacta per a buscar en el text complet
	 * @return String amb el numero de coincidències trobades
	 */
	public static String buscardorCoincidencies(String textComplet, String cadenaATrobar) {

		if (!chkMayusculas.isSelected()) {
			textComplet = textComplet.toLowerCase();
			cadenaATrobar = cadenaATrobar.toLowerCase();
		}

		if (!chkAccents.isSelected()) {
			textComplet = Normalizer.normalize(textComplet, Normalizer.Form.NFD);
			textComplet = textComplet.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
			cadenaATrobar = Normalizer.normalize(cadenaATrobar, Normalizer.Form.NFD);
			cadenaATrobar = cadenaATrobar.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		}

		int contador = 0;
		for (int i = 0; i <= textComplet.length() - 1; i++) {
			boolean trobat = true;
			for (int j = 0; j < cadenaATrobar.length(); j++) {
				if (textComplet.charAt(i + j) != cadenaATrobar.charAt(j)) {
					trobat = false;
					break;
				}
			}
			if (trobat) {
				contador++;
			}
		}
		return Integer.toString(contador);
	}

	/**
	 * Determina si l'arxiu especificat en un arxiu de valguda
	 * @param arxiu :  Arxiu a comprovar
	 * @return boolean que retorna si l'arxiu és valgut o no
	 */
	public static boolean esArxiuDeTextPla(File arxiu) {

		String[] extensions = { ".txt", ".csv", ".log", ".md", ".json", ".xml", ".tsv", ".html", ".htm", ".ini",
				".properties", ".yaml", ".yml", ".sql", ".batch", ".sh", ".po", ".diff", ".patch" , ".pdf" };

		String nombreArchiu = arxiu.getName();

		for (String extensio : extensions) {
			if (nombreArchiu.toLowerCase().endsWith(extensio)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Crea un arxiu amb el text modificat 
	 * @param text : String amb el text modificat
	 * @param arxiu : Arxiu original per a obtindre la ruta de l'arxiu
	 */
	public static void crearArchiuModificat(String text, File arxiu) {
		File pare = arxiu.getParentFile();
		String rutaArxiu = pare.getAbsolutePath();
		File nouArxiu = new File(rutaArxiu + "/MOD_" + arxiu.getName());
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(nouArxiu))) {
			bufferedWriter.write(text);
			bufferedWriter.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
	
	