package model;

public class Square {
    private boolean isOpen;
    private boolean hasMine;
    private boolean isTarget;
    private int numMineAround;   
   
    public boolean isOpen() {
      return isOpen;
    }
    public void setOpen(boolean isOpen) {
      this.isOpen = isOpen;
    }    
    public boolean isHasMine() {
      return hasMine;
    }
    public void setHasMine(boolean hasMine) {
      this.hasMine = hasMine;
    }
    public int getNumMineAround() {
      return numMineAround;
    }
    public void setNumMineAround(int numMineAround) {
      this.numMineAround = numMineAround;
    }
    public boolean isTarget() {
      return isTarget;
    }
    public void setTarget(boolean isTarget) {
      this.isTarget = isTarget;
    }

    //use for UNDO
    public void assignment(Square square){
      this.isOpen = square.isOpen;
      this.hasMine = square.hasMine;
      this.isTarget = square.isTarget;
      this.numMineAround = square.numMineAround;
    }
  }