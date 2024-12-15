package view;
import control.*;
 
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

 
public class MainFrame extends JFrame{ 
  private final String TITLE = "MineSweeper";
  public final int FRAME_WIDTH = 730;
  public final int FRAME_HEIGHT = 600;
  private BoardPanel boardPanel;
  private ControlPanel controlPanel;

  ManageSquare manageSquare;
 
  public MainFrame() {
    manageSquare = new ManageSquare();

    initComp();
    addComp();
    addEvent();
  }
 
  public void initComp() {
    setTitle(TITLE);
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    setLayout(null);
    try {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
 
  public void addComp() {
    boardPanel = new BoardPanel(this.manageSquare);
    boardPanel.setBounds(10, 60, 700, 500);
    add(boardPanel);    
    manageSquare.setBoardPanel(boardPanel);
 
    controlPanel = new ControlPanel(this.manageSquare);
    controlPanel.setBounds(10, 0, 700, 60);
    add(controlPanel);
    manageSquare.setControlPanel(controlPanel);

  }
 
  public void addEvent() {
    WindowListener wd = new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        int kq = JOptionPane.showConfirmDialog(MainFrame.this, "Do you want to exit game?",
         "Information", JOptionPane.YES_NO_OPTION);
        if (kq == JOptionPane.YES_OPTION) {
          dispose();
        }
      }
    };
    addWindowListener(wd);
  }

}