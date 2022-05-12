package view;


import controller.ClickController;
import model.*;
import save_write.Save_Write;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的<br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色<br>
     * chessListener：棋盘监听棋子的行动<br>
     * chessboard: 表示8 * 8的棋盘<br>
     * currentColor: 当前行棋方
     */
    private ArrayList<ChessComponent[][]> store =new ArrayList<>();

    private ChessComponent[][] chessComponents = new ChessComponent[8][8];
    private ChessColor currentColor = ChessColor.BLACK;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private int CHESS_SIZE;

    public void setCHESS_SIZE(int width) {
        this.CHESS_SIZE = width / 8;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(0,0));
        chessComponent.MoreInformation(calculatePoint(0,0), ChessColor.BLACK, clickController, CHESS_SIZE);
        initOnBoard(0,0,chessComponent);
        // FIXME: Initialize chessboard for testing only.
    }

    /**
     * @param width
     * @param height
     * @param path
     */
    public Chessboard(int width, int height,String path) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

//        initiateEmptyChessboard();
//        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(0,0));
//        chessComponent.MoreInformation(calculatePoint(0,0), ChessColor.BLACK, clickController, CHESS_SIZE);
//        initOnBoard(0,0,chessComponent);
//        // FIXME: Initialize chessboard for testing only.

        Save_Write a=new Save_Write();
        if(!a.convertToChessboard(a.readFileByFileReader(path))){
            if (!a.convertToChessboard(a.readFileByFileReader("resource/save1.txt"))){
                a.writeFileByFileWriter("resource/save1.txt", new ArrayList<String>(){{add("RNBQKBNRPPPPPPPP________________________________pppppppprnbqkbnrw");}});
                a.convertToChessboard(a.readFileByFileReader("resource/save1.txt"));
            }
        }
        store=a.getStore();
        chessComponents=store.get(store.size()-1);

//        for (int row = 0; row < 8; row++) {
//            for (int col = 0; col < 8; col++) {
//                System.out.print(chessComponents[row][col].toChar());
//            }
//        }

//        initOnBoard(0,0,chessComponent);
//        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(0,0));
//        chessComponent.MoreInformation(calculatePoint(0,0),ChessColor.BLACK, clickController, CHESS_SIZE);
//        initOnBoard(0,0,chessComponent);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
//                System.out.print(chessComponents[row][col].getChessColor().getName());
                initOnBoard(row,col,chessComponents[row][col]);
            }
        }
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
    }


    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }
//换人
    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
    }

    private void initOnBoard(int row, int col,ChessComponent chessComponent) {
        chessComponent.MoreInformation(calculatePoint(row, col),chessComponent.getChessColor(), clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


//    private void initBishopOnBoard(int row, int col, ChessColor color) {
//        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
//        chessComponent.setVisible(true);
//        putChessOnBoard(chessComponent);
//    }
//    private void initKingOnBoard(int row, int col, ChessColor color) {
//        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
//        chessComponent.setVisible(true);
//        putChessOnBoard(chessComponent);
//    }
//    private void initKnightOnBoard(int row, int col, ChessColor color) {
//        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
//        chessComponent.setVisible(true);
//        putChessOnBoard(chessComponent);
//    }
//    private void initPawnOnBoard(int row, int col, ChessColor color) {
//        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
//        chessComponent.setVisible(true);
//        putChessOnBoard(chessComponent);
//    }
//    private void initQueenOnBoard(int row, int col, ChessColor color) {
//        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
//        chessComponent.setVisible(true);
//        putChessOnBoard(chessComponent);
//    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
    }
}
