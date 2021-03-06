package chess.pieces;
import chess.Board;

public class King extends Piece {

    private boolean has_moved;
    private boolean isFirstMove;
    private boolean isCastleMove;
    private boolean hasQSideCastlingRights;
    private boolean hasKSideCastlingRights;

    public King(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board, 'k', "\u2654", "\u265A");
        has_moved = false;
        isFirstMove = false;
        isCastleMove = false;
        hasQSideCastlingRights = true;
        hasKSideCastlingRights = true;
    }

    public void setHasMoved(boolean has_moved)
    {
        this.has_moved = has_moved;
    }
    
    public boolean getHasMoved()
    {
        return has_moved;
    }
    public boolean getIsFirstMove() {
        return isFirstMove;
    }

    public void setIsFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    public boolean getIsCastleMove() {
        return isCastleMove;
    }

    public void setIsCastleMove(boolean castleMove) {
        isCastleMove = castleMove;
    }

    public boolean getHasQSideCastlingRights() {
        return hasQSideCastlingRights;
    }

    public void setHasQSideCastlingRights(boolean hasQSideCastlingRights) {
        this.hasQSideCastlingRights = hasQSideCastlingRights;
    }

    public boolean getHasKSideCastlingRights() {
        return hasKSideCastlingRights;
    }

    public void setHasKSideCastlingRights(boolean hasKSideCastlingRights) {
        this.hasKSideCastlingRights = hasKSideCastlingRights;
    }

    @Override
    public boolean canMove(int destination_x, int destination_y) {
        Piece testPiece;
        testPiece = board.getPiece(destination_x, destination_y);

        if(Math.abs(destination_x-this.getX()) > 1 || Math.abs(destination_y-this.getY())  > 1 ){
            if(destination_y == this.getY()) {
                if (destination_x - this.getX() == 2) {
                   return  board.getIsWhitePerspective() ? canCastleKingSide() : canCastleQueenSide();
                } else if (destination_x - this.getX() == -2) {
                    return board.getIsWhitePerspective() ? canCastleQueenSide() : canCastleKingSide();
                }
            }
            return false;
        }else{
            if(testPiece!=null && testPiece.isWhite() == this.isWhite()){
                return false;
            }

            //Kings can't attack each other
            int xPos = this.isWhite() ? board.blackKing.getX() : board.whiteKing.getX();
            int yPos = this.isWhite() ? board.blackKing.getY() : board.whiteKing.getY();

            if( (destination_x == xPos + 1 && destination_y == yPos) || (destination_x == xPos - 1 && destination_y == yPos )
                    || (destination_x == xPos && destination_y == yPos + 1) || (destination_x == xPos && destination_y == yPos - 1)
                    || (destination_x == xPos + 1 && destination_y == yPos + 1) || (destination_x == xPos - 1 && destination_y == yPos + 1)
                    || (destination_x == xPos - 1 && destination_y == yPos - 1)  || (destination_x == xPos + 1 && destination_y == yPos - 1) ){
                return false;
            }

            if(isUnderAttack(destination_x, destination_y)) {
                return false;
            }
        }

        return canMoveChecked(destination_x, destination_y);

    }


    public boolean canCastleKingSide(){
//        Piece kingRook = board.getIsWhitePerspective() ? board.getPiece(7, this.getY()) : board.getPiece(0, this.getY());
//        if (this.has_moved || this.hasKSideCastlingRights == false || kingRook==null || ( kingRook.getClass().equals(Rook.class) && ((Rook)kingRook).getHasMoved() )) {
        if (this.hasKSideCastlingRights == false) {
            return false;
        } else {
            if(board.getIsWhitePerspective()) {
                for (int i = this.getX(); i <= this.getX() + 2; i++) {
                    if (isUnderAttack(i, this.getY()) || (i + 1 < 7 && board.getPiece(i + 1, this.getY()) != null)) {
                        return false;
                    }
                }
            }else{
                for (int i = this.getX(); i >= this.getX() - 2; i--) {
                    if (isUnderAttack(i, this.getY()) || (i - 1 > 0 && board.getPiece(i - 1, this.getY()) != null)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public boolean canCastleQueenSide(){
        //Piece queenRook =  board.getIsWhitePerspective() ? board.getPiece(0, this.getY()) : board.getPiece(7, this.getY());
        //if (this.has_moved || this.hasQSideCastlingRights == false || queenRook==null || ( queenRook.getClass().equals(Rook.class) && ((Rook)queenRook).getHasMoved() )) {
        if (this.hasQSideCastlingRights == false) {
             return false;
        } else {
            if(board.getIsWhitePerspective()) {
                for (int i = this.getX(); i >= this.getX() - 2; i--) {
                    if (isUnderAttack(i, this.getY()) || (board.getPiece(i - 1, this.getY()) != null)) {
                        return false;
                    }
                }
            }else{
                for (int i = this.getX(); i <= this.getX() + 2; i++) {
                    if (isUnderAttack(i, this.getY()) || (board.getPiece(i + 1, this.getY()) != null)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isUnderAttack(int x, int y){

        if(this.isWhite()){
            for(Piece piece: board.Black_Pieces){
                if(piece.canMove(x,y) || (piece.getClass().equals(Pawn.class) && canPawnAttack(x,y, piece))  ){
                    return true;
                }
            }
        }
        else if(this.isBlack()){
            for(Piece piece: board.White_Pieces){
                if(piece.canMove(x,y) || (piece.getClass().equals(Pawn.class) && canPawnAttack(x,y, piece)) ){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canPawnAttack (int x, int y, Piece pawn){

        if (pawn.getX()+1 == x || pawn.getX()-1 == x) {
            if (board.getIsWhitePerspective() ) {
                if ( (pawn.isWhite() && pawn.getY()-1 == y) || (pawn.isBlack() && pawn.getY()+1 == y) ) {
                    return true;
                }
            }else{
                if ( (pawn.isWhite() && pawn.getY()+1 == y) || (pawn.isBlack() && pawn.getY()-1 == y) ) {
                    return true;
                }
            }
        }
        return false;
    }



    public boolean isCheck(){
        return isUnderAttack(this.getX(), this.getY());
    }
    public boolean isCheckmate(){
        
        boolean isWhitesTurn = board.turnCounter % 2 != 1;
        if(this.isCheck() && !isWhitesTurn){
            for (Piece piece : board.Black_Pieces) {
                if(!piece.availableMoves(piece.getX(), piece.getY()).isEmpty()){
                    return false;
                }
            }
            return true;
        }else if(this.isCheck() && isWhitesTurn){
            for (Piece piece : board.White_Pieces) {
                if(!piece.availableMoves(piece.getX(), piece.getY()).isEmpty()){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public boolean isDraw(){
        //stalemate
        boolean isWhitesTurn = board.turnCounter % 2 != 1;
        if(this.isWhite() == isWhitesTurn && !this.isCheck()){
            if(isWhitesTurn){
                for (Piece piece : board.White_Pieces) {
                    if(!piece.availableMoves(piece.getX(), piece.getY()).isEmpty()){
                        return false;
                    }
                }
                return true;
            }else{
                for (Piece piece : board.Black_Pieces) {
                    if(!piece.availableMoves(piece.getX(), piece.getY()).isEmpty()){
                        return false;
                    }
                }
                return true;
            }
        }
        return board.halfMoveCounter>=100;
    }
}
