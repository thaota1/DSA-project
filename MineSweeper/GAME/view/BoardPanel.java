package view;
import control.*;
import model.*;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;
 
public class BoardPanel extends JPanel{   
  private Label[][] lbSquare;
  private int numSquareClosed;

  private ManageSquare manageSquare;
  private int numRows, numCols;
 
  public BoardPanel(ManageSquare manageSquare) {
    this.manageSquare = manageSquare;
    ListSquare listSquare = this.manageSquare.getListSquare();
    //numRows = listSquare.getArrSquare().length;
    //numCols = listSquare.getArrSquare()[0].length;
    numRows = listSquare.getRows();
    numCols = listSquare.getCols();

    initComp();
    addComp();
    addEvent();
  }
 
  private void initComp() {  
    setLayout(new GridLayout(numRows, numCols));
  }
 
  private void addComp() {
    Border border = BorderFactory.createLineBorder(Color.gray, 1);
    lbSquare = new Label[this.numRows][this.numCols];
    for (int i = 0; i < lbSquare.length; i++) {
      for (int j = 0; j < lbSquare[0].length; j++) {
        lbSquare[i][j] = new Label();
        lbSquare[i][j].setOpaque(true);
        lbSquare[i][j].setBackground(new Color(242, 242, 242));
        lbSquare[i][j].setBorder(border);
        lbSquare[i][j].setHorizontalAlignment(JLabel.CENTER);
        lbSquare[i][j].setVerticalAlignment(JLabel.CENTER);
        add(lbSquare[i][j]);
      }
    }
  }
 
  private void addEvent() {
    for (int i = 0; i < lbSquare.length; i++) {
      for (int j = 0; j < lbSquare[0].length; j++) {
        lbSquare[i][j].x = i;
        lbSquare[i][j].y = j;
        lbSquare[i][j].addMouseListener(new MouseAdapter() {
          @Override
          public void mouseReleased(MouseEvent e) {
            Label label = (Label) e.getComponent();
            if (e.getButton() == MouseEvent.BUTTON1) {
              manageSquare.play(label.x, label.y);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
              manageSquare.target(label.x, label.y);
            }
          }
        });
      }
    }
  } 
 
  //Cap nhat hien thi 
  public void updateBoard() {
    Font font = new Font("VNI", Font.PLAIN, 20);
    numSquareClosed = 0;
    //Square[][] ArrSquare = this.manageSquare.getListSquare().getArrSquare();

    //for (int i = 0; i < ArrSquare.length; i++) {
      //for (int j = 0; j < ArrSquare[0].length; j++) {
    for (int i = 0; i < this.numRows; i++) {
      for (int j = 0; j < this.numCols; j++) {          
        lbSquare[i][j].setFont(font);
        //neu cell chua open
        //if (!ArrSquare[i][j].isOpen()) {
        if (!manageSquare.itSquareOpen(i,j)) {
          lbSquare[i][j].setBackground(new Color(242, 242, 242));
          lbSquare[i][j].setForeground(Color.black);
          numSquareClosed++;
          //neu cell chua dat flag
          //if (!ArrSquare[i][j].isTarget()) {
          if (!manageSquare.itSquareTarget(i,j)) {
            lbSquare[i][j].setText("");
          } else {
            lbSquare[i][j].setText("\uD83D\uDEA9"); // ki tu 'flag'
          }
        //neu cell da open  
        } else {
          //neu cell co min
          //if (ArrSquare[i][j].isHasMine()) {
          if (manageSquare.itSquareMine(i,j)) {
            lbSquare[i][j].setText("\uD83D\uDCA3"); // ki tu 'bomb'
          //neu cell khong co min  
          } else {
            //int numMineAround = ArrSquare[i][j].getNumMineAround();
            int numMineAround = manageSquare.itArroundMines(i,j);
            //neu xung quanh cell khong co min
            if (numMineAround == 0) {
              lbSquare[i][j].setText("");
            //thong bao tong so min xung quanh cell  
            } else {
              lbSquare[i][j].setText(numMineAround + "");
              // hien thi mau cho ky so - tuy theo so luong min xung quanh
              setColorForNumber(lbSquare[i][j],numMineAround);
            }
          }
          //set mau nen cua Label la mau trang
          //lbSquare[i][j].setBackground(Color.white);

          //set mau nen cua Label da open la mau xam trang
          lbSquare[i][j].setBackground(Color.lightGray);
        }
      }
    }
  }
  
  //Xet mau cho ky so - tuy thuoc so luong min xung quanh
  private void setColorForNumber(Label label, int numMineAround){
    switch (numMineAround) {
      case 1:
        label.setForeground(new Color(255, 255, 0));
        break;
      case 2:
        label.setForeground(new Color(255, 0, 0));
        break;
      case 3:
        label.setForeground(new Color(0, 204, 0));
        break;
      case 4:
        label.setForeground(new Color(102, 0, 255));
        break;
      case 5:
        label.setForeground(new Color(128, 128, 128));
        break;
      case 6:
        label.setForeground(new Color(255, 0, 0));
        break;
      case 7:
        label.setForeground(new Color(0, 204, 0));
        break;
      case 8:
        label.setForeground(new Color(102, 0, 255));
        break;
      }
  }
  // tạo class con để mở rộng thuộc tính cho lớp JLabel
  // giúp lưu được chỉ số hàng, cột của label đó ở trong GridLayout
  // vì ko thể truyền giá trị i, j vào bên trong phương thức addMouseListener
  private class Label extends JLabel {
    private int x;
    private int y;
  }
   
  public int getNumSquareClosed() {
    return numSquareClosed;
  }

  //use for UNDO
  public void setNumSquareClosed(int bufferSquareClosed) {
    numSquareClosed = bufferSquareClosed;
  }
}