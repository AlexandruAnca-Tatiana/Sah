import javax.swing.ImageIcon;
import java.util.ArrayList;


public class Knight
    extends ChessGamePiece
{

  
    public Knight( ChessGameBoard board, int row, int col, int color )
    {
        super( board, row, col, color );
    }


    private ArrayList<String> calculateNorthMoves( ChessGameBoard board )
    {
        ArrayList<String> moves = new ArrayList<String>();
        for ( int i = 2; i >= -2; i -= 4 )
        {
            for ( int j = 1; j >= -1; j -= 2 )
            {
                if ( isOnScreen( pieceRow + i, pieceColumn + j )
                    && ( isEnemy( board, pieceRow + i, pieceColumn + j ) ||
                        board.getCell(
                        pieceRow + i,
                        pieceColumn + j )
                        .getPieceOnSquare() == null ) )
                {
                    moves.add( ( pieceRow + i ) + "," + ( pieceColumn + j ) );
                }
            }
        }

        return moves;
    }

    
    private ArrayList<String> calculateSouthMoves( ChessGameBoard board )
    {
        ArrayList<String> moves = new ArrayList<String>();
        for ( int i = 1; i >= -1; i -= 2 )
        {
            for ( int j = 2; j >= -2; j -= 4 )
            {
                if ( isOnScreen( pieceRow + i, pieceColumn + j )
                    && ( isEnemy( board, pieceRow + i, pieceColumn + j ) ||
                        board.getCell(
                        pieceRow + i,
                        pieceColumn + j )
                        .getPieceOnSquare() == null ) )
                {
                    moves.add( ( pieceRow + i ) + "," + ( pieceColumn + j ) );
                }
            }
        }
        return moves;
    }


    
    @Override
    protected ArrayList<String> calculatePossibleMoves( ChessGameBoard board )
    {
        ArrayList<String> moves = new ArrayList<String>();
        if ( isPieceOnScreen() )
        {
            moves.addAll( calculateNorthMoves( board ) );
            moves.addAll( calculateSouthMoves( board ) );
        }
        return moves;
    }


   
    @Override
    public ImageIcon createImageByPieceType()
    {
        if ( getColorOfPiece() == ChessGamePiece.WHITE )
        {
            return new ImageIcon( "chessImages/WhiteKnight.gif" );
        }
        else if ( getColorOfPiece() == ChessGamePiece.BLACK )
        {
            return new ImageIcon( "chessImages/BlackKnight.gif" );
        }
        else
        {
            return new ImageIcon( "chessImages/default-Unassigned.gif" );
        }
    }
}
