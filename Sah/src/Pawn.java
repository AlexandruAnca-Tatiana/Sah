import javax.swing.ImageIcon;
import java.util.ArrayList;


public class Pawn
    extends ChessGamePiece
{
    private boolean notMoved;


 
    public Pawn( ChessGameBoard board, int row, int col, int color )
    {
        super( board, row, col, color, true );
        notMoved = true;
        possibleMoves = calculatePossibleMoves( board );
    }


  
    @Override
    public boolean move( ChessGameBoard board, int row, int col )
    {
        if ( super.move( board, row, col ) )
        {
            notMoved = false;
            possibleMoves = calculatePossibleMoves( board );
            if ( ( getColorOfPiece() == ChessGamePiece.BLACK && row == 7 )
                || ( getColorOfPiece() == ChessGamePiece.WHITE && row == 0 ) )
            { 
                board.getCell( row, col ).setPieceOnSquare( new Queen(
                    board,
                    row,
                    col,
                    getColorOfPiece() ) );
            }
            return true;
        }
        return false;
    }


  
    @Override
    protected ArrayList<String> calculatePossibleMoves( ChessGameBoard board )
    {
        ArrayList<String> moves = new ArrayList<String>();
        if ( isPieceOnScreen() )
        {
            int currRow =
                getColorOfPiece() == ChessGamePiece.WHITE
                    ? ( pieceRow - 1 )
                    : ( pieceRow + 1 );
            int count = 1;
            int maxIter = notMoved ? 2 : 1;
           
            while ( count <= maxIter )
            { 
                if ( isOnScreen( currRow, pieceColumn )
                    && board.getCell( currRow,
                        pieceColumn ).getPieceOnSquare() == null )
                {
                    moves.add( currRow + "," + pieceColumn );
                }
                else
                {
                    break;
                }
                currRow =
                    ( getColorOfPiece() == ChessGamePiece.WHITE )
                        ? ( currRow - 1 )
                        : ( currRow + 1 );
                count++;
            }
            
            if ( getColorOfPiece() == ChessGamePiece.WHITE )
            {

                if ( isEnemy( board, pieceRow - 1, pieceColumn - 1 ) )
                {
                    moves.add( ( pieceRow - 1 ) + "," + ( pieceColumn - 1 ) );
                }
                if ( isEnemy( board, pieceRow - 1, pieceColumn + 1 ) )
                {
                    moves.add( ( pieceRow - 1 ) + "," + ( pieceColumn + 1 ) );

                }
            }
            else
            {
                if ( isEnemy( board, pieceRow + 1, pieceColumn - 1 ) )
                {
                    moves.add( ( pieceRow + 1 ) + "," + ( pieceColumn - 1 ) );
                }

                if ( isEnemy( board, pieceRow + 1, pieceColumn + 1 ) )
                {
                    moves.add( ( pieceRow + 1 ) + "," + ( pieceColumn + 1 ) );
                }
            }
        }
        return moves;
    }


   
    @Override
    public ImageIcon createImageByPieceType()
    {
        if ( getColorOfPiece() == ChessGamePiece.WHITE )
        {
            return new ImageIcon( "chessImages/WhitePawn.gif" );
        }
        else if ( getColorOfPiece() == ChessGamePiece.BLACK )
        {
            return new ImageIcon( "chessImages/BlackPawn.gif" );
        }
        else
        {
            return new ImageIcon( "chessImages/default-Unassigned.gif" );
        }
    }
}
