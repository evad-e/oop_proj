package chess.pieces;

import chess.Board;

public class King extends Piece {

    private boolean has_moved;

    public King(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board);
        has_moved = false;

    }
    public void setHasMoved(boolean has_moved)
    {
        this.has_moved = has_moved;
    }
    
    public boolean getHasMoved()
    {
        return has_moved;
    }
    @Override
    public boolean canMove(int destination_x, int destination_y)
    {
        Piece testPiece = board.getPiece(destination_x, destination_y);
        int i;
    // Remember: a king can move one square up, right, left, or down, or
    // diagonally, but he can never put himself in danger of an oposing 
    // piece attacking him on the next turn. He cannot attack his own pieces.
        
    //check if a same-color piece is on the destination
    if(Math.abs( destination_x-this.getX()) == 1|| Math.abs( destination_y-this.getY())  == 1 ){;
        if(testPiece!=null){
            if(testPiece.isWhite()&&this.isWhite()){
                return false;
            }else if(testPiece.isBlack()&&this.isBlack()){
                return false;
            }
        }

       
        
        
    }else if(destination_y==this.getY()&&destination_x-this.getX()==Math.abs(2)){
        if(destination_x-this.getX()>0){
           
        }      
    }else{
        return false;
    }
   

    //moving north white perspective()
    if(destination_y > this.getY()){
        testPiece = board.getPiece(this.getX(), this.getY()+1);
        if(testPiece!=null){
            return false;
        }
    }
    //moving east white perspective()
    else if(destination_x>this.getX()){
        testPiece = board.getPiece(this.getX()+1, this.getY());
        if(testPiece!=null){
            return false;
        }
    }
     //moving south white perspective()
    else if(destination_x<this.getY()){
        testPiece = board.getPiece(this.getX(), this.getY()-1);
        if(testPiece!=null){
            return false;
        }
    }
     //moving west white perspective()
    else if(destination_x<this.getX()){
        testPiece = board.getPiece(this.getX()-1, this.getY());
        if(testPiece!=null){
            return false;
        }
    }
    //moving northeast white perspective()
    else if(destination_y>this.getY() && destination_x>this.getX()){
        testPiece = board.getPiece(this.getX()+1, this.getY()+1);
        if(testPiece!=null){
            return false;
        }
    }
    //moving southeast white perspective()
    else if(destination_y<this.getY() && destination_x>this.getX()){
        testPiece = board.getPiece(this.getX()+1, this.getY()-1);
        if(testPiece!=null){
            return false;
        }
    }
    //moving southwest white perspective()
    else if(destination_y<this.getY() && destination_x<this.getX()){
        testPiece = board.getPiece(this.getX()-1, this.getY()-1);
        if(testPiece!=null){
            return false;
        }
    }
    //moving northwest white perspective()
    else if(destination_y<this.getY() && destination_x<this.getX()){
        testPiece = board.getPiece(this.getX()-1, this.getY()+1);
        if(testPiece!=null){
            return false;
        }
    }
           
        return true;
    }
    public boolean canCastle(int destination_x, int destination_y){
        int i;
        
        
        if(destination_x-this.getX()>0){
           for(i=this.getX();i<destination_x;i++){
              if(this.isWhite()){
                  
              }
           }
        } 
        return true;
    }

}
