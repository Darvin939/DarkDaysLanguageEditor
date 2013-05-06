package darvin939.DDLangEditor;

import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.ListModel;

public class ClearTask extends Thread {
	private JEditorPane edValues;
	private static long currtime;
	private static String[] values;
	private static int selectionIndex;
	private static String selectionValue;
	private static String[] valuesModifed;
	private HashMap<Object, String> theChosen;
	private JList<Object> listKeys;
	private static String[] keys;
	EditorForm main;

	public ClearTask(EditorForm main) {
		this.main = main;
		this.edValues = main.edValues;
		this.theChosen = main.theChosen;
		this.listKeys = main.listKeys;
	}

	public void run() {
		try {
			while (true) {
				if (currtime < ((System.currentTimeMillis() / 1000) - 2)) {
					if (edValues.getText().isEmpty() && selectionValue != null) {
						edValues.setText(values[selectionIndex]);
						EditorForm.setPreview(edValues.getText());
						edValues.setCaretPosition(edValues.getText().length());
						valuesModifed[selectionIndex] = edValues.getText();
						theChosen.remove(keys[selectionIndex]);
						main.setLanguage(keys[selectionIndex], edValues.getText());
						ListModel<Object> jList1Model = new DefaultComboBoxModel<Object>(keys);
						listKeys.setModel(jList1Model);
					}
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setParametrs(long l, String[] v, String[] k, int si, String sv, String[] vm) {
		currtime = l;
		values = v;
		keys = k;
		selectionIndex = si;
		selectionValue = sv;
		valuesModifed = vm;
	}
}
