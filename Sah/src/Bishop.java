
import javax.swing.ImageIcon;
import java.util.ArrayList;
public class Bishop extends ChessGamePiece
{
    //fac un obicet nou de tip Bishop, cu locatie(row si line),si culoare
    public Bishop( ChessGameBoard board, int row, int col, int color )
    {
        super( board, row, col, color );
    }
    
    //calcularea tuturor mutarilor piesei
    @Override
    protected ArrayList<String> calculatePossibleMoves( ChessGameBoard board )
    {
        ArrayList<String> northEastMoves = calculateNorthEastMoves( board, 8 );
        ArrayList<String> northWestMoves = calculateNorthWestMoves( board, 8 );
        ArrayList<String> southEastMoves = calculateSouthEastMoves( board, 8 );
        ArrayList<String> southWestMoves = calculateSouthWestMoves( board, 8 );

        ArrayList<String> allMoves = new ArrayList<String>();
        allMoves.addAll( northEastMoves );
        allMoves.addAll( northWestMoves );
        allMoves.addAll( southEastMoves );
        allMoves.addAll( southWestMoves );
        return allMoves;
    }

//creeaza o icoana depinzand de culoarea piesei
    @Override
    public ImageIcon createImageByPieceType()
    {
        if ( getColorOfPiece() == ChessGamePiece.WHITE )
        {
            return new ImageIcon( "chessImages/WhiteBishop.gif" );
        }
        else if ( getColorOfPiece() == ChessGamePiece.BLACK )
        {
            return new ImageIcon( "chessImages/BlackBishop.gif" );
        }
        else
        {
            return new ImageIcon( "chessImages/default-Unassigned.gif" );
        }
    }
}
