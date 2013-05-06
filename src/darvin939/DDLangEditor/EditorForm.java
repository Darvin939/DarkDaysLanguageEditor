package darvin939.DDLangEditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.ScrollPaneConstants;

public class EditorForm extends JFrame {
	private static final long serialVersionUID = 6565919783055801022L;
	private JPanel contentPane;
	private JTextField tfDirectory;
	public JList<Object> listKeys;
	private JComboBox<Object> cmbLang;
	private JButton btnChoice;
	public String[] languages = new String[] { "English", "Russian" };
	private String[] ruchars = { "а", "б", "в", "г", "д", "е", "Є", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "€" };
	private JButton btnExit;
	private JButton btnCreate;
	public JEditorPane edValues;
	protected String directory = "";
	public String[] keys;
	public String[] values;
	public String[] valuesModifed;
	public HashMap<Object, String> theChosen = new HashMap<Object, String>();
	private HashMap<String, Boolean> isLangModified = new HashMap<String, Boolean>();
	protected int selectionIndex;
	protected String edValue;
	boolean adjust = true;
	protected String selectionValue;
	protected File choosenFile;
	private String language = "";
	private String saveFormat = "ddlang";
	private JTextField txtKey_1;
	private JTextField txtKey;
	protected String lastDir = "";
	private static JTextPane edPreview;
	protected static long currtime;
	private JScrollPane scrollPane_2;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditorForm frame = new EditorForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EditorForm() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditorForm.class.getResource("icon.png")));
		setTitle("DarkDays Language Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		setLocation(dim.width / 2 - 417, dim.height / 2 - 185);
		setSize(824, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfDirectory = new JTextField();
		tfDirectory.setFocusable(false);
		tfDirectory.setBounds(10, 9, 696, 26);
		contentPane.add(tfDirectory);
		tfDirectory.setColumns(10);

		btnCreate = new JButton("Create");
		btnCreate.setEnabled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					HashMap<String, String> map = new HashMap<String, String>();
					for (int i = 0; i < keys.length; i++) {
						map.put(keys[i], valuesModifed[i]);
					}
					CipherUtil ciph = new CipherUtil();
					ciph.writeMap(choosenFile.getParent(), language.isEmpty() ? choosenFile.getName().toLowerCase() : language.toLowerCase() + "." + saveFormat, map);
					dispose();
				} catch (Exception local) {
					local.printStackTrace();
				}
			}
		});
		btnCreate.setBounds(716, 144, 90, 30);
		contentPane.add(btnCreate);

		listKeys = new JList<Object>();
		listKeys.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionListener listSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				adjust = event.getValueIsAdjusting();
				if (!adjust) {
					try {
						JList<?> list = (JList<?>) event.getSource();
						list.setCellRenderer(new ListRenderer());
						selectionIndex = list.getSelectedIndex();
						selectionValue = values[list.getSelectedIndex()];
						edValues.setText(valuesModifed[list.getSelectedIndex()]);
						edValues.setEditable(true);
						setPreview(valuesModifed[list.getSelectedIndex()]);
						edValue = values[list.getSelectedIndex()];
					} catch (Exception e) {
					}
				}
			}
		};
		listKeys.addListSelectionListener(listSelectionListener);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 292, 245);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(listKeys);

		cmbLang = new JComboBox<Object>();
		cmbLang.setEnabled(false);
		cmbLang.setModel(new DefaultComboBoxModel<Object>(languages));
		cmbLang.setBounds(716, 111, 90, 22);
		contentPane.add(cmbLang);

		JSeparator separator = new JSeparator();
		separator.setBounds(716, 98, 90, 2);
		contentPane.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(716, 42, 90, 2);
		contentPane.add(separator_1);

		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setBounds(716, 57, 90, 30);
		contentPane.add(btnExit);

		btnChoice = new JButton("Browse");
		btnChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fileopen = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("DarkDays Lang File *." + saveFormat, saveFormat);
					fileopen.setFileFilter(filter);
					fileopen.setAcceptAllFileFilterUsed(false);
					if (lastDir.isEmpty())
						fileopen.setCurrentDirectory(new File((System.getProperty("user.home") + "/Desktop/Games/Minecraft/Workspase/Workspase Server/plugins/DarkDays/locales").replace("\\", "/")));
					else
						fileopen.setCurrentDirectory(new File(lastDir.replace("\\", "/")));
					int ret = fileopen.showDialog(null, "ќткрыть");
					if (ret == JFileChooser.APPROVE_OPTION) {
						File file = fileopen.getSelectedFile();
						lastDir = file.getParent();
						tfDirectory.setText(file.getAbsolutePath());
						directory = file.getAbsolutePath();
						btnCreate.setEnabled(true);
						CipherUtil ciph = new CipherUtil();
						choosenFile = file;
						Map<String, String> map = ciph.readMap(file.getParent(), file.getName());
						keys = new String[map.size()];
						values = new String[map.size()];
						valuesModifed = new String[map.size()];
						int index = 0;
						for (Entry<String, String> set : map.entrySet()) {
							keys[index] = set.getKey();
							values[index] = set.getValue();
							valuesModifed[index] = set.getValue();
							setLanguage(set.getKey(), set.getValue());
							index++;
						}
						theChosen.clear();
						edValues.setText("");
						ListModel<Object> jList1Model = new DefaultComboBoxModel<Object>(keys);
						listKeys.setModel(jList1Model);
						if (keys.length != 0)
							listKeys.setSelectedIndex(0);
					}
				} catch (Exception local) {
					local.printStackTrace();
				}
			}
		});
		btnChoice.setBounds(716, 11, 90, 23);
		contentPane.add(btnChoice);

		txtKey_1 = new JTextField();
		txtKey_1.setForeground(new Color(0, 0, 0));
		txtKey_1.setText("key");
		txtKey_1.setEditable(false);
		txtKey_1.setBackground(new Color(192, 192, 192));
		txtKey_1.setBounds(10, 308, 30, 26);
		contentPane.add(txtKey_1);
		txtKey_1.setColumns(10);

		JLabel lblNewLabel = new JLabel("There are Russian letters");
		lblNewLabel.setBounds(42, 314, 145, 14);
		contentPane.add(lblNewLabel);

		txtKey = new JTextField();
		txtKey.setFocusable(false);
		txtKey.setForeground(new Color(0, 128, 0));
		txtKey.setText("key");
		txtKey.setBounds(199, 308, 30, 26);
		contentPane.add(txtKey);
		txtKey.setColumns(10);

		JLabel lblChanged = new JLabel("Changed");
		lblChanged.setBounds(231, 314, 71, 14);
		contentPane.add(lblChanged);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(0, 300, 818, 2);
		contentPane.add(separator_2);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Preview", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(314, 164, 392, 123);
		contentPane.add(panel);
		panel.setLayout(null);

		edPreview = new JTextPane();
		edPreview.setFocusable(false);
		edPreview.setBackground(Color.WHITE);
		edPreview.setDisabledTextColor(Color.BLACK);
		//edPreview.setBounds(10, 17, 372, 95);
		//panel.add(edPreview);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 17, 372, 95);
		scrollPane_2.setViewportView(edPreview);
		panel.add(scrollPane_2);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Editor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(314, 44, 392, 123);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		edValues = new JEditorPane();
		//edValues.setBounds(10, 17, 372, 95);
		//panel_1.add(edValues);
		edValues.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				int SI = selectionIndex;
				setPreview(edValues.getText());
				if (selectionValue != null) {
					String text = edValues.getText();
					if (!text.equalsIgnoreCase(edValue)) {
						theChosen.put(keys[selectionIndex], "chosen");
						valuesModifed[selectionIndex] = text;
						setLanguage(keys[selectionIndex], text);
					}
					if (text.equalsIgnoreCase(edValue)) {
						theChosen.remove(keys[selectionIndex]);
						valuesModifed[selectionIndex] = values[selectionIndex];
						setLanguage(keys[selectionIndex], text);
					}
					ClearTask.setParametrs(System.currentTimeMillis() / 1000, values, keys, selectionIndex, selectionValue, valuesModifed);
					ListModel<Object> jList1Model = new DefaultComboBoxModel<Object>(keys);
					listKeys.setModel(jList1Model);
					selectionIndex = SI;
				}
			}
		});
		edValues.setEditable(false);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(10, 17, 372, 95);
		scrollPane_1.setViewportView(edValues);
		panel_1.add(scrollPane_1);
		new ClearTask(this).start();
	}

	protected static void setPreview(String s) {
		edPreview.setText("");
		s = s.replaceAll("COMMASPACE", ", ");
		
		edPreview.setText(s);
		//appendToPane(edPreview, s, Color.BLACK);
	}

	public void appendToPane(JTextPane tp, String msg, Color c) {
		System.out.println(msg.length());
		final int constant = 51;
		while (msg.length() > constant) {
			StyleContext sc = StyleContext.getDefaultStyleContext();
			AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
			aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
			aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
			int len = tp.getDocument().getLength();
			tp.setCaretPosition(len);
			tp.setCharacterAttributes(aset, false);
			tp.replaceSelection(msg.substring(0, constant) + "\n");
			msg = msg.substring(constant, msg.length());
		}
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}

	public void setLanguage(String key, String value) {
		for (String chr : ruchars) {
			if (value.toLowerCase().contains(chr)) {
				isLangModified.put(key, true);
				break;
			} else {
				isLangModified.put(key, false);
			}
		}
		int trueCount = 0;
		for (Entry<String, Boolean> set : isLangModified.entrySet()) {
			if (!set.getValue())
				trueCount++;
		}
		if (trueCount == isLangModified.size()) {
			language = "English";
			cmbLang.setSelectedItem("English");
		} else {
			language = "Russian";
			cmbLang.setSelectedItem("Russian");
		}
	}

	private class ListRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = -9107034088630397209L;

		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (theChosen.containsKey(value)) {
				setForeground(new Color(0, 128, 0));
			} else {
				setForeground(Color.black);
			}

			for (Entry<String, Boolean> set : isLangModified.entrySet()) {
				if (set.getKey().equals(value) && set.getValue()) {
					setBackground(new Color(192, 192, 192));
				}
			}
			return (this);
		}
	}
}
