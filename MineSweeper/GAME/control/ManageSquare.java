package control;
import model.*;
import view.*;

public class ManageSquare {
  //use for UNDO
   int bufferSquareClosed;
   ListSquare bufferSquare;

   ListSquare listSquare;
   BoardPanel boardPanel;
   ControlPanel controlPanel;

  public ManageSquare(){
     listSquare = new ListSquare();

     //use for UNDO
     bufferSquare = new ListSquare();

    // System.out.println("matrix list:");
    // this.printList(this.listSquare.getArrSquare());
    // System.out.println("matrix buffer:");
    // this.printList(this.bufferSquare.getArrSquare());
  }	

  public ListSquare getListSquare(){
    return this.listSquare;
  }

  public void setBoardPanel(BoardPanel boardPanel){
    this.boardPanel = boardPanel;
  }

  public void setControlPanel(ControlPanel controlPanel){
    this.controlPanel = controlPanel;
  }

  //use for UNDO
  private void backupList(){
    int rows =  listSquare.getRows();
    int cols =  listSquare.getCols();
    for(int i=0; i<rows; i++)
      for (int j=0; j<cols; j++){
        // Square square = listSquare.getSquare(i, j);
        // bufferSquare.getSquare(i, j).assignment(square);
        Square lSquare = listSquare.getSquare(i, j);
        Square bSquare = bufferSquare.getSquare(i, j);
        bSquare.assignment(lSquare);
        //gan(lSquare,bSquare);

      }          
  }

  // private void gan(Square lSquare, Square bSquare){
  //   bSquare.setOpen(lSquare.isOpen());
  //   bSquare.setHasMine(lSquare.isHasMine());
  //   bSquare.setTarget(lSquare.isTarget());
  //   bSquare.setNumMineAround(lSquare.getNumMineAround());
  // }

  //use for UNDO
  private void restoreList(){
    int rows =  listSquare.getRows();
    int cols =  listSquare.getCols();
    for(int i=0; i<rows; i++)
      for (int j=0; j<cols; j++){
        Square square = bufferSquare.getSquare(i, j);
        listSquare.getSquare(i, j).assignment(square);
      }          
  }
   
  //from MainFrame
  public void play(int x, int y) {
    //use for UNDO
    this.backupList();
    bufferSquareClosed  = boardPanel.getNumSquareClosed();

    boolean conti = listSquare.play(x, y);   
    if (!conti) { listSquare.showAllSquares(); }
    boardPanel.updateBoard(); 
      
    // cập nhật số ô chưa mở vào controlPanel
    int numSquareClosed = boardPanel.getNumSquareClosed();
    controlPanel.updateStatus(numSquareClosed);
  }
  
  //from MainFrame
  public void target(int x, int y) {
    //use for UNDO
    this.backupList();

    listSquare.target(x, y);

    // System.out.println("list:");
    // this.printList(this.listSquare.getArrSquare());  
    
    // System.out.println("buffer:");
    // this.printList(this.bufferSquare.getArrSquare());
  
    boardPanel.updateBoard();
  }
  
  //from MainFrame
  public void restart() {
    listSquare = new ListSquare();
    bufferSquare = new ListSquare();

    boardPanel.updateBoard();
  }  

  // methods called from updateBoard()
  public boolean itSquareOpen(int i, int j){
    return this.listSquare.SquareOpen(i,j);
  }

  public boolean itSquareTarget(int i, int j){
    return this.listSquare.SquareTarget(i,j);
  }

  public boolean itSquareMine(int i, int j){
    return this.listSquare.SquareMine(i,j);
  }

  public int itArroundMines(int i, int j){
    return this.listSquare.ArroundMines(i,j);
  }
  

  //use for UNDO
  public void undo(){
    this.restoreList();
    boardPanel.setNumSquareClosed(this.bufferSquareClosed); 
    boardPanel.updateBoard();

    controlPanel.updateStatus(this.bufferSquareClosed);
  }

  // private void printList(Square[][] aSquares){
  //   int flag = 0;
  //   for(int i=0; i<aSquares.length; i++){
  //     for(int j=0; j<aSquares[0].length; j++){
  //       boolean bflag = aSquares[i][j].isTarget();
  //       if(bflag)
  //         flag = 1;
  //       else
  //         flag = 0;
  //       System.out.print("  " + flag);
  //     }
  //     System.out.println(); 
  //   }    
  // }
}
