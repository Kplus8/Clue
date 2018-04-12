package clueGame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {

	public DetectiveNotes() {

		JPanel people = new JPanel();
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		
		
		setSize(300, 300);
		setTitle("Detective Notes");
		add(people);
	}

}
