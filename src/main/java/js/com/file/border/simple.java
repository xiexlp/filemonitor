package js.com.file.border;
 
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
 
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
 
public class simple extends JFrame {
	public simple() {
		initUI();
	}
 
	private void initUI() {
		Border emptyPanl, llineBorder, etchedBorder, raisedBorder, loweredBevelBorder, emptyBorder;
		emptyPanl = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		llineBorder = BorderFactory.createLineBorder(Color.red);
		etchedBorder = BorderFactory.createEtchedBorder();
		raisedBorder = BorderFactory.createRaisedBevelBorder();
		loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
		emptyBorder = BorderFactory.createEmptyBorder();
                //(2)
		JPanel simpleBorder = new JPanel();
		simpleBorder.setBorder(emptyPanl);
		simpleBorder.setLayout(new BoxLayout(simpleBorder, BoxLayout.Y_AXIS));
		addCompForBorder(llineBorder,"line border",simpleBorder);
		addCompForBorder(etchedBorder,"etche border",simpleBorder);
		addCompForBorder(loweredBevelBorder,"lowere border",simpleBorder);
		addCompForBorder(emptyBorder,"empty border",simpleBorder);
		
		JTabbedPane jTabbedPane = new JTabbedPane();
		jTabbedPane.setToolTipText("simple");
		jTabbedPane.addTab("simple", simpleBorder);
		this.add(jTabbedPane);
		//this.getContentPane().add(jTabbedPane);
	}
	
	private void addCompForBorder(Border border,String lable,Container container) {
            //(1)
            JPanel comp = new JPanel(false);
            JLabel label = new JLabel(lable, JLabel.CENTER);
            comp.setLayout(new GridLayout(1, 1));
            comp.add(label);
            comp.setBorder(border);
 
	    container.add(Box.createRigidArea(new Dimension(0, 10)));
	    container.add(comp);
	}
 
	public static void main(String[] args) {
                //(3)
                JFrame frame = new simple();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(400,400));
		frame.pack();
		frame.setVisible(true);
	}
}
