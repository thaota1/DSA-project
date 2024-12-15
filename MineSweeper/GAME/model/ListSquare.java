package model;

import java.util.Random;
 
public class ListSquare{
  private final int NUM_ROWS = 15;
  private final int NUM_COLUMNS = 20;
  private final int NUM_MINES = NUM_ROWS * NUM_COLUMNS / 10;
 
  private Square[][] arrSquare;
 
  public ListSquare() {
    //if (iList == 1){
      arrSquare = new Square[NUM_ROWS][NUM_COLUMNS];
      for (int i = 0; i < arrSquare.length; i++) {
        for (int j = 0; j < arrSquare[0].length; j++) {
          arrSquare[i][j] = new Square();
        }
      }
 
      layMines();
      setMines();
    // }else{
    //   arrSquare = new Square[NUM_ROWS][NUM_COLUMNS];
    //   for (int i = 0; i < arrSquare.length; i++) {
    //     for (int j = 0; j < arrSquare[0].length; j++) {
    //       arrSquare[i][j] = new Square();
    //     }
    //   }
    // }        
  }



  //Dat min ngau nhien vao ma tran cell
  private void layMines(){
      for (int i = 0; i < NUM_MINES; i++) {
      	int x = genRan(NUM_ROWS);
      	int y = genRan(NUM_COLUMNS);

      	// neu cell co min roi thi dat ngau nhien cell khac
      	while (arrSquare[x][y].isHasMine()) {
        		x = genRan(NUM_ROWS);
        		y = genRan(NUM_COLUMNS);
     	 }
      	arrSquare[x][y].setHasMine(true);
      }
    }

  //Set so luong min xung quanh cac cell cua ma tran cell
  private void setMines(){
     for (int i = 0; i < arrSquare.length; i++) {
      	for (int j = 0; j < arrSquare[0].length; j++) {
        		int count = this.countMines(i, j);        		
        		arrSquare[i][j].setNumMineAround(count);
     	 }
    }
  }  

  //Dem min xung quanh cell[row][col] 
  private int countMines(int row, int col){
    int count = 0;
    //Xet 3 dong tuong doi so voi row cua cell hien tai
    for (int m = -1; m <= 1; m++) {
        if (row + m < 0) { m++; }
        if (row + m > NUM_ROWS - 1) { break; }
        //Xet 3 cot tuong doi so voi col cua cell hien tai
        for (int n = -1; n <= 1; n++) {
            if (col + n < 0) { n++; }
            if (col + n > NUM_COLUMNS - 1) { break; }
            //Neu cell co min va khong phai la cell dang xet thi dem
            if (!(m == 0 && n == 0) && arrSquare[row + m][col + n].isHasMine()) {count++;}
        }
    }
    return count;
  }
   
  private int genRan(int range) {
    Random rd = new Random();
    return rd.nextInt(range);
  }
 
  public Square[][] getArrSquare() {
    return arrSquare;
  }  

  //add method
  public int getRows() {
    return this.NUM_ROWS;
  }

  //add method
  public int getCols(){
    return this.NUM_COLUMNS;
  }

  //use for UNDO
  public Square getSquare(int row, int col){
    return this.arrSquare[row][col];
  }

  //NEW CODE HERE
  //open cell khong co min, overgame neu opened cell co min - return true
  public boolean play(int x, int y) {
    Square square = this.arrSquare[x][y];

    if (!square.isOpen()){ 
      square.setOpen(true);

      //overgame neu cell chon co min - tra ve true
      if (square.isHasMine()) 
        return false;
      
      //neu cac cell xung quanh khong co min thi goi ham de qui play(x,y) cho tung cell
      if (square.getNumMineAround() == 0) 
        openSquareAround(x,y);     
    }    
    return true;
  }

  //open cells xung quanh khong co min
  private void openSquareAround(int x, int y){
    for (int m = -1; m <= 1; m++) {
      if (x + m < 0) { m++; }
      if (x + m > NUM_ROWS - 1) { break; }
      for (int n = -1; n <= 1; n++) {
        if (y + n < 0) { n++; }
        if (y + n > NUM_COLUMNS - 1) { break; }
        play(x + m, y + n);
      }
    }
  }
 
  //Dat flag cho cell chua open
  public void target(int x, int y) {
    Square square = this.arrSquare[x][y];

    if (!square.isOpen()) {
      if (!square.isTarget()) {
        square.setTarget(true);
      } else {
        square.setTarget(false);
      }
    }
  }
 
  //Open all cell cua ma tran 
  public void showAllSquares() {
    for (int i = 0; i < arrSquare.length; i++) {
      for (int j = 0; j < arrSquare[0].length; j++) {
        arrSquare[i][j].setOpen(true);
      }
    }
  }

  //so min cua game
  public int getMines(){
    return this.NUM_MINES;
  }

  // methods called from ManageSquare - updateBoard()
  public boolean SquareOpen(int i, int j){
    return this.arrSquare[i][j].isOpen();
  }

  public boolean SquareTarget(int i, int j){
    return this.arrSquare[i][j].isTarget();
  }

  public boolean SquareMine(int i, int j){
    return this.arrSquare[i][j].isHasMine();
  }

  public int ArroundMines(int i, int j){
    return this.arrSquare[i][j].getNumMineAround();
  }
  
  

}