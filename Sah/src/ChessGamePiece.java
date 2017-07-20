import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;

//clase abstracte pentru reprezentarea pe tabla de joc a unei piese 
//clasele care o mostenesc sunt: Rook, Bishop, Knight, Queen, King and Pawn.
//metodele contin informatii despre celulele din jur
public abstract class ChessGamePiece
{
    private boolean             skipMoveGeneration;

    private int                 pieceColor;

    private ImageIcon           pieceImage;
//lista pentru miscarile posibile a unei piese,reinoita la fiecare miscare sau selectie a piesei
    protected ArrayList<String> possibleMoves;
//linia  piesei
    protected int               pieceRow;
//coloana piesei
    protected int               pieceColumn;
//piesa neagra
    static final int            BLACK      = 0;
//piesa alba
    static final int            WHITE      = 1;
//piesa fara culoare
    static final int            UNASSIGNED = -1;


    // crerea unui obiect gamepiece si setarea piesei,randului,liniei si culoarea ei
    public ChessGamePiece(
        ChessGameBoard board,
        int row,
        int col,
        int pieceColor )
    {
        skipMoveGeneration = false;
        this.pieceColor = pieceColor;
        pieceImage = createImageByPieceType();
        pieceRow = row;
        pieceColumn = col;
        if ( board.getCell( row, col ) != null )
        {
            board.getCell( row, col ).setPieceOnSquare( this );
        }
        possibleMoves = calculatePossibleMoves( board ); 
    }


    // piece speciale, cu mutari diferite de restul pieselor,linie, coloana si culoare
    public ChessGamePiece(
        ChessGameBoard board,
        int row,
        int col,
        int pieceColor,
        boolean skipMoveGeneration )
    {
        this.skipMoveGeneration = skipMoveGeneration;
        this.pieceColor = pieceColor;
        pieceImage = this.createImageByPieceType();
        pieceRow = row;
        pieceColumn = col;
        if ( board.getCell( row, col ) != null )
        {
            board.getCell(row, col).setPieceOnSquare( this );
        }
        if ( !this.skipMoveGeneration )
        {
            possibleMoves = calculatePossibleMoves( board );
        }
    }


    //genereaza si intoarce o lista cu posibile miscari, locatie a unei piese
    protected abstract ArrayList<String> calculatePossibleMoves(
        ChessGameBoard board );

//calculeaza si intoarce miscarile din josul piesei 
    protected ArrayList<String> calculateSouthMoves(
        ChessGameBoard board,
        int numMoves )
    {
        ArrayList<String> moves = new ArrayList<String>();
        int count = 0;
        if ( isPieceOnScreen() )
        {
            for ( int i = pieceRow + 1; i < 8 && count < numMoves; i++ )
            {
                if ( ( board.getCell( i, pieceColumn ).getPieceOnSquare()
                    == null || isEnemy( board, i, pieceColumn ) ) )
                {
                    moves.add( i + "," + pieceColumn );
                    count++;
                    if ( isEnemy( board, i, pieceColumn ) )
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
        }
        return moves;
    }


    // calculeaza si intoarce miscarile din susul piesei 
    protected ArrayList<String> calculateNorthMoves(
        ChessGameBoard board,
        int numMoves )
    {
        ArrayList<String> moves = new ArrayList<String>();
        int count = 0;
        if ( isPieceOnScreen() )
        {
            for ( int i = pieceRow - 1; i >= 0 && count < numMoves; i-- )
            {
                if ( ( board.getCell( i, pieceColumn ).getPieceOnSquare()
                    == null || isEnemy( board, i, pieceColumn ) ) )
                {
                    moves.add( i + "," + pieceColumn );
                    count++;

                    if ( isEnemy( board, i, pieceColumn ) )
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
        }
        return moves;
    }


    // calculeaza si intoarce miscarile din dreapta piesei 
    protected ArrayList<String> calculateEastMoves(
        ChessGameBoard board,
        int numMoves )
    {
        ArrayList<String> moves = new ArrayList<String>();
        int count = 0;
        if ( isPieceOnScreen() )
        {
            for ( int i = pieceColumn + 1; i < 8 && count < numMoves; i++ )
            {
                if ( ( board.getCell( pieceRow, i ).getPieceOnSquare()
                    == null || isEnemy( board, pieceRow, i ) ) )
                {
                    moves.add( pieceRow + "," + i );
                    count++;
                    if ( isEnemy( board, pieceRow, i ) )
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
        }
        return moves;
    }


// calculeaza si intoarce miscarile din stanga piesei 
    protected ArrayList<String> calculateWestMoves(
        ChessGameBoard board,
        int numMoves )
    {
        ArrayList<String> moves = new ArrayList<String>();
        int count = 0;
        if ( isPieceOnScreen() )
        {
            for ( int i = pieceColumn - 1; i >= 0 && count < numMoves; i-- )
            {
                if ( ( board.getCell(pieceRow, i ).getPieceOnSquare()
                    == null || isEnemy( board, pieceRow, i ) ) )
                {
                    moves.add( pieceRow + "," + i );
                    count++;
                    if ( isEnemy( board, pieceRow, i ) )
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
        }
        return moves;
    }


// calculeaza si intoarce miscarile diagonalei din stanga in susul piesei 
    protected ArrayList<String> calculateNorthWestMoves(
        ChessGameBoard board,
        int numMoves )
    {
        ArrayList<String> moves = new ArrayList<String>();
        int count = 0;
        if ( isPieceOnScreen() )
        {
            for ( int i = 1; i < 8 && count < numMoves; i++ )
            {
                if ( isOnScreen( pieceRow - i, pieceColumn - i )
                    && ( board.getCell( pieceRow - i,
                        pieceColumn - i ).getPieceOnSquare() == null ) )
                {
                    moves.add( ( pieceRow - i ) + "," + ( pieceColumn - i ) );
                    count++;
                }
                else if ( isEnemy( board, pieceRow - i, pieceColumn - i ) )
                {
                    moves.add( ( pieceRow - i ) + "," + ( pieceColumn - i ) );
                    count++;
                    break;
                }
                else
                {
                    break;
                }
            }
        }
        return moves;
    }


// calculeaza si intoarce miscarile diagonalei din dreapta in susul piesei 
    protected ArrayList<String> calculateNorthEastMoves(
        ChessGameBoard board,
        int numMoves )
    {
        ArrayList<String> moves = new ArrayList<String>();
        int count = 0;
        if ( isPieceOnScreen() )
        {
            for ( int i = 1; i < 8 && count < numMoves; i++ )
            {
                if ( isOnScreen( pieceRow - i, pieceColumn + i )
                    && ( board.getCell( pieceRow - i,
                        pieceColumn + i).getPieceOnSquare() == null ) )
                {
                    moves.add( ( pieceRow - i ) + "," + ( pieceColumn + i ) );
                    count++;
                }
                else if ( isEnemy( board, pieceRow - i, pieceColumn + i ) )
                {
                    moves.add( ( pieceRow - i ) + "," + ( pieceColumn + i ) );
                    count++;
                    break;
                }
                else
                {
                    break;
                }
            }
        }
        return moves;
    }


    // calculeaza si intoarce miscarile diagonalei din stanga in josul piesei 
    protected ArrayList<String> calculateSouthWestMoves(
        ChessGameBoard board,
        int numMoves )
    {
        ArrayList<String> moves = new ArrayList<String>();
        int count = 0;
        if ( isPieceOnScreen() )
        {
            for ( int i = 1; i < 8 && count < numMoves; i++ )
            {
                if ( isOnScreen( pieceRow + i, pieceColumn - i )
                    && ( board.getCell( pieceRow + i,
                        pieceColumn - i ).getPieceOnSquare() == null ) )
                {
                    moves.add( ( pieceRow + i ) + "," + ( pieceColumn - i ) );
                    count++;
                }
                else if ( isEnemy( board, pieceRow + i, pieceColumn - i ) )
                {
                    moves.add( ( pieceRow + i ) + "," + ( pieceColumn - i ) );
                    count++;
                    break;
                }
                else
                {
                    break;
                }
            }
        }
        return moves;
    }


   // calculeaza si intoarce miscarile diagonalei din dreapta in josul piesei 
    protected ArrayList<String> calculateSouthEastMoves(
        ChessGameBoard board,
        int numMoves )
    {
        ArrayList<String> moves = new ArrayList<String>();
        int count = 0;
        if ( isPieceOnScreen() )
        {
            for ( int i = 1; i < 8 && count < numMoves; i++ )
            {
                if ( isOnScreen( pieceRow + i, pieceColumn + i )
                    && ( board.getCell( pieceRow + i,
                        pieceColumn + i ).getPieceOnSquare() == null ) )
                {
                    moves.add( ( pieceRow + i ) + "," + ( pieceColumn + i ) );
                    count++;
                }
                else if ( isEnemy( board, pieceRow + i, pieceColumn + i ) )
                {
                    moves.add( ( pieceRow + i ) + "," + ( pieceColumn + i ) );
                    count++;
                    break;
                }
                else
                {
                    break;
                }
            }
        }
        return moves;
    }

//creeaza imaginea culorii piesei
    public abstract ImageIcon createImageByPieceType();
    public ImageIcon getImage()
    {
        return pieceImage;
    }


    // returneaza culoarea piesei,0-negru,1-alb,-1-nici o culoare
    public int getColorOfPiece()
    {
        return pieceColor;
    }

    // verifica daca locatia dorita are granite,linie, coloana,true=valida si false=nevalida
    public boolean isOnScreen( int row, int col )
    {
        if ( row >= 0 && row <= 7 && col >= 0 && col <= 7 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean isPieceOnScreen()
    {
        return isOnScreen( pieceRow, pieceColumn );
    }

//noua pozitie a piese,pe linie si coloana cu true sau false
    public boolean move( ChessGameBoard board, int row, int col )
    {
        if ( canMove( board, row, col ) )
        {
            String moveLog = this.toString() + " -> ";
            board.clearCell( pieceRow, pieceColumn );
            if ( isEnemy( board, row, col ) )
            {
                ChessGraveyard graveyard;
                ChessGameEngine gameEngine =
                    ( (ChessPanel)board.getParent() ).getGameEngine();
                if ( gameEngine.getCurrentPlayer() == 1 )
                {
                    graveyard =
                        ( (ChessPanel)board.getParent() ).getGraveyard( 2 );
                }
                else
                {
                    graveyard =
                        ( (ChessPanel)board.getParent() ).getGraveyard( 1 );
                }
                graveyard.addPiece(
                    board.getCell( row, col ).getPieceOnSquare() );
            }
            setPieceLocation( row, col );
            moveLog += " (" + row + ", " + col + ")";
            ( (ChessPanel)board.getParent() ).getGameLog().addToLog( moveLog );
            board.getCell( row, col ).setPieceOnSquare( this );
            if ( !skipMoveGeneration )
            {
                updatePossibleMoves( board );
            }
            return true;
        }
        else
        {
            return false;
        }
    }

//determina daca piesa poate fi mutata undeva si daca regele e pus in sah de mutarea respectiva
    public boolean canMove( ChessGameBoard board, int row, int col )
    {
        updatePossibleMoves( board );
        if ( possibleMoves.indexOf( row + "," + col ) > -1 )
        {
            return testMoveForKingSafety( board, row, col );
        }
        return false;
    }
    private boolean testMoveForKingSafety(
        ChessGameBoard board,
        int row,
        int col )
    {
        updatePossibleMoves( board );
        ChessGamePiece oldPieceOnOtherSquare =
            board.getCell( row, col ).getPieceOnSquare();
        ChessGameEngine engine =
            ( (ChessPanel)board.getParent() ).getGameEngine();
        int oldRow = pieceRow;
        int oldColumn = pieceColumn;
        board.clearCell( pieceRow, pieceColumn );
        this.setPieceLocation( row, col ); // mutare la noua locatie
        board.getCell( row, col ).setPieceOnSquare( this );
        boolean retVal = !engine.isKingInCheck( true ); // dacaregele e inca in sah 
        this.setPieceLocation( oldRow, oldColumn ); // mutare inapoi 
        board.getCell( oldRow, oldColumn ).setPieceOnSquare( this );
        board.clearCell( row, col ); // mutarea piesei unde a fost inainte
        board.getCell( row, col ).setPieceOnSquare( oldPieceOnOtherSquare );
        return retVal;
    }
    protected void updatePossibleMoves( ChessGameBoard board )
    {
        possibleMoves = calculatePossibleMoves( board );
    }


    //locatia interna a piesei
    public void setPieceLocation( int row, int col )
    {
        pieceRow = row;
        pieceColumn = col;
    }


    // locatia pe linie
    public int getRow()
    {
        return pieceRow;
    }


    // locatia pe coloana
    public int getColumn()
    {
        return pieceColumn;
    }


    // afiseaza mutarile legale pe tabla
    public void showLegalMoves( ChessGameBoard board )
    {
        updatePossibleMoves( board );
        if ( isPieceOnScreen() )
        {
            for ( String locStr : possibleMoves )
            {
                String[] currCoords = locStr.split( "," );
                int row = Integer.parseInt( currCoords[0] );
                int col = Integer.parseInt( currCoords[1] );
                if ( canMove( board, row, col ) ) // doar mutari legale
                {
                    if ( isEnemy( board, row, col ) )
                    {
                        board.getCell( row, col ).setBackground(
                            Color.YELLOW );
                    }
                    else
                    {
                        board.getCell( row, col ).setBackground( Color.PINK );
                    }
                }
            }
        }
    }


    // determina daca piesa are mutari legale sau ba
    public boolean hasLegalMoves( ChessGameBoard board )
    {
        updatePossibleMoves( board );
        if ( isPieceOnScreen() )
        {
            for ( String locStr : possibleMoves )
            {
                String[] currCoords = locStr.split( "," );
                int row = Integer.parseInt( currCoords[0] );
                int col = Integer.parseInt( currCoords[1] );
                if ( canMove( board, row, col ) ) //mutari legale doar
                {
                    return true;
                }
            }
            return false;
        }
        return false;
    }


    //determina daca celula are oo piesa inamica
    public boolean isEnemy( ChessGameBoard board, int row, int col )
    {
        if ( row > 7 || col > 7 || row < 0 || col < 0 )
        {
            return false;
        }
        ChessGamePiece enemyPiece =
            board.getCell( row, col ).getPieceOnSquare() == null
                ? null
                : board.getCell( row, col ).getPieceOnSquare();
        if ( enemyPiece == null
            || this.getColorOfPiece() == ChessGamePiece.UNASSIGNED
            || enemyPiece.getColorOfPiece() == ChessGamePiece.UNASSIGNED )
        {
            return false;
        }
        if ( this.getColorOfPiece() == ChessGamePiece.WHITE )
        {
            if ( enemyPiece.getColorOfPiece() == ChessGamePiece.BLACK )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            if ( enemyPiece.getColorOfPiece() == ChessGamePiece.WHITE )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }


    // lista cu piesele care pot ataca o anumita piesa
    public ArrayList<ChessGamePiece> getCurrentAttackers( ChessGameBoard board )
    {
        ArrayList<ChessGamePiece> attackers = new ArrayList<ChessGamePiece>();
        int enemyColor =
            ( this.getColorOfPiece() == ChessGamePiece.BLACK )
                ? ChessGamePiece.WHITE
                : ChessGamePiece.BLACK;
        this.updatePossibleMoves( board );
        for ( int i = 0; i < board.getCells().length; i++ )
        {
            for ( int j = 0; j < board.getCells()[0].length; j++ )
            {
                ChessGamePiece currPiece =
                    board.getCell( i, j ).getPieceOnSquare();

                if ( currPiece != null
                    && currPiece.getColorOfPiece() == enemyColor )
                {
                    currPiece.updatePossibleMoves( board );
                    if ( currPiece.canMove( board, pieceRow, pieceColumn ) )
                    {
                        attackers.add( currPiece );
                    }
                }
            }
        }
        return attackers;
    }

    @Override
    public String toString()
    {
        return this.getClass().toString().substring( 6 ) + " @ (" + pieceRow
            + ", " + pieceColumn + ")";
    }

}
