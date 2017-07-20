import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.awt.event.MouseEvent;

public class ChessGameEngine
{
    private ChessGamePiece currentPiece;

    private boolean        firstClick;

    private int            currentPlayer;

    private ChessGameBoard board;

    private King           king1;

    private King           king2;

    public ChessGameEngine( ChessGameBoard board )
    {
        firstClick = true;
        currentPlayer = 1;
        this.board = board;
        this.king1 = (King)board.getCell( 7, 3 ).getPieceOnSquare();
        this.king2 = (King)board.getCell( 0, 3 ).getPieceOnSquare();
        ( (ChessPanel)board.getParent() ).getGameLog().clearLog();
        ( (ChessPanel)board.getParent() ).getGameLog().addToLog(
            "A new chess "
                + "game has been started. Player 1 (white) will play "
                + "against Player 2 (black). BEGIN!" );
    }


    public void reset()
    {
        firstClick = true;
        currentPlayer = 1;
        ( (ChessPanel)board.getParent() ).getGraveyard( 1 ).clearGraveyard();
        ( (ChessPanel)board.getParent() ).getGraveyard( 2 ).clearGraveyard();
        ( (ChessPanel)board.getParent() ).getGameBoard().initializeBoard();
        ( (ChessPanel)board.getParent() ).revalidate();
        this.king1 = (King)board.getCell( 7, 3 ).getPieceOnSquare();
        this.king2 = (King)board.getCell( 0, 3 ).getPieceOnSquare();
        ( (ChessPanel)board.getParent() ).getGameLog().clearLog();
        ( (ChessPanel)board.getParent() ).getGameLog().addToLog(
            "A new chess "
                + "game has been started. Player 1 (white) will play "
                + "against Player 2 (black). BEGIN!" );
    }
    //schimba randul jucatorilor
    private void nextTurn()
    {
        currentPlayer = ( currentPlayer == 1 ) ? 2 : 1;
        ( (ChessPanel)board.getParent() ).getGameLog().addToLog(
                "It is now Player " + currentPlayer + "'s turn." );
    }

    public int getCurrentPlayer()
    {
        return currentPlayer;
    }
    
    public boolean playerHasLegalMoves( int playerNum )
    {
        ArrayList<ChessGamePiece> pieces;
        if ( playerNum == 1 )
        {
            pieces = board.getAllWhitePieces();
        }
        else if ( playerNum == 2 )
        {
            pieces = board.getAllBlackPieces();
        }
        else
        {
            return false;
        }

        for ( ChessGamePiece currPiece : pieces )
        {
            if ( currPiece.hasLegalMoves( board ) )
            {
                return true;
            }
        }
        return false;
    }


   
    private boolean selectedPieceIsValid()
    {
        if ( currentPiece == null ) 
        {
            return false;
        }

        if ( currentPlayer == 2 )
        {
            if ( currentPiece.getColorOfPiece() == ChessGamePiece.BLACK )
            {
                return true;
            }
            return false;
        }
        else
      
        {
            if ( currentPiece.getColorOfPiece() == ChessGamePiece.WHITE )
            {
                return true;
            }
            return false;
        }
    }


    
    public boolean isKingInCheck( boolean checkCurrent )
    {
        if ( checkCurrent )
        {
            if ( currentPlayer == 1 )
            {
                return king1.isChecked( board );
            }
            return king2.isChecked( board );
        }
        else
        {
            if ( currentPlayer == 2 )
            {
                return king1.isChecked( board );
            }
            return king2.isChecked( board );
        }
    }


   
    private void askUserToPlayAgain( String endGameStr )
    {
        int resp =
            JOptionPane.showConfirmDialog( board.getParent(), endGameStr
                + " Do you want to play again?" );
        if ( resp == JOptionPane.YES_OPTION )
        {
            reset();
        }
        else
        {
            board.resetBoard( false );
            // System.exit(0);
        }
    }


   
    private void checkGameConditions()
    {
        int origPlayer = currentPlayer;
        for ( int i = 0; i < 2; i++ )
        {
            int gameLostRetVal = determineGameLost();
            if ( gameLostRetVal < 0 )
            {
                askUserToPlayAgain( "Game over - STALEMATE. You should both go"
                    + " cry in a corner!" );
                return;
            }
            else if ( gameLostRetVal > 0 )
            {
                askUserToPlayAgain( "Game over - CHECKMATE. " + "Player "
                    + gameLostRetVal + " loses and should go"
                    + " cry in a corner!" );
                return;
            }
            else if ( isKingInCheck( true ) )
            {
                JOptionPane.showMessageDialog(
                    board.getParent(),
                    "Be careful player " + currentPlayer + ", " +
                    "your king is in check! Your next move must get " +
                    "him out of check or you're screwed.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE );
            }
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            // check the next player's conditions as well.
        }
        currentPlayer = origPlayer;
        nextTurn();
    }


    
    public int determineGameLost()
    {
        if ( king1.isChecked( board ) && !playerHasLegalMoves( 1 ) ) 
        // loss
        {
            return 1;
        }
        if ( king2.isChecked( board ) && !playerHasLegalMoves( 2 ) ) 
        // loss
        {
            return 2;
        }
        if ( ( !king1.isChecked( board ) && !playerHasLegalMoves( 1 ) )
            || ( !king2.isChecked( board ) && !playerHasLegalMoves( 2 ) )
            || ( board.getAllWhitePieces().size() == 1 &&
                board.getAllBlackPieces().size() == 1 ) ) 
        {
            return -1;
        }
        return 0; 
    }


    
    public void determineActionFromSquareClick( MouseEvent e )
    {
        BoardSquare squareClicked = (BoardSquare)e.getSource();
        ChessGamePiece pieceOnSquare = squareClicked.getPieceOnSquare();

        board.clearColorsOnBoard();
        if ( firstClick )
        {
            currentPiece = squareClicked.getPieceOnSquare();
            if ( selectedPieceIsValid() )
            {
                currentPiece.showLegalMoves( board );
                squareClicked.setBackground( Color.GREEN );
                firstClick = false;
            }
            else
            {
                if ( currentPiece != null )
                {
                    JOptionPane.showMessageDialog(
                        squareClicked,
                        "You tried to pick up the other player's piece! "
                            + "Get some glasses and pick a valid square.",
                        "Illegal move",
                        JOptionPane.ERROR_MESSAGE );
                }
                else
                {
                    JOptionPane.showMessageDialog(
                        squareClicked,
                        "You tried to pick up an empty square! "
                            + "Get some glasses and pick a valid square.",
                        "Illegal move",
                        JOptionPane.ERROR_MESSAGE );
                }
            }
        }
        else
        {

            if ( pieceOnSquare == null ||
                !pieceOnSquare.equals( currentPiece ) ) 
            {
                boolean moveSuccessful =
                    currentPiece.move(
                        board,
                        squareClicked.getRow(),
                        squareClicked.getColumn() );
                if ( moveSuccessful )
                {
                    checkGameConditions();
                }
                else
                {
                    int row = squareClicked.getRow();
                    int col = squareClicked.getColumn();
                    JOptionPane.showMessageDialog(
                        squareClicked,
                        "The move to row " + ( row + 1 ) + " and column "
                            + ( col + 1 )
                            + " is either not valid or not legal "
                            + "for this piece. Choose another move location, "
                            + "and try using your brain this time!",
                        "Invalid move",
                        JOptionPane.ERROR_MESSAGE );
                }
                firstClick = true;
            }
            else
        
            {
                firstClick = true;
            }
        }
    }
}
