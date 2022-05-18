package view;

//import controller.GameController;

import controller.GameController;
import save_write.Save_Write;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 * 调用了chessboard。java文件
 * 调用方法 new，setlocation
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    public JLabel gamer;
//    private GameController gameController;

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard();
        addLabel();
        addRankingButton();
        addLoadButton();
        addSaveButton();
        addGamer();
        addResetButton();
        addRepentButton();
        GameController.setChessGameFrame(this);

        //加入背景图片
        ImageIcon img = new ImageIcon("backGroundImages/MainFrame2/gameBackground2.jpg");//这是背景图片
        img.setImage(img.getImage().getScaledInstance(width, height,Image.SCALE_DEFAULT ));
        JLabel imgLabel = new JLabel(img);//将背景图放在标签里。
        getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));//注意这里是关键，将背景标签添加到jfram的LayeredPane面板里。
        imgLabel.setBounds(0,0,WIDTH,HEIGTH);//设置背景标签的位置
        Container cp=getContentPane();
        cp.setLayout(new BorderLayout());
        ((JPanel)cp).setOpaque(false);

        setVisible(true);
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, "resource/save1.txt");
        GameController.setChessboard(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    public void addGamer() {
        gamer = new JLabel("Current  "+GameController.getChessboard().getCurrentColors().get(GameController.getChessboard().getCurrentColors().size() - 1).toString());
        gamer.setLocation(HEIGTH, HEIGTH / 10);
        gamer.setSize(200, 60);
        gamer.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(gamer);
        gamer.repaint();
        JLabel gamer1=new JLabel("WHITE  "+GameController.getUser1());
        gamer1.setLocation(HEIGTH, HEIGTH / 10+40);
        gamer1.setSize(200, 60);
        gamer1.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(gamer1);
        JLabel gamer2=new JLabel("BLACK  "+GameController.getUser2());
        gamer2.setLocation(HEIGTH, HEIGTH / 10+80);
        gamer2.setSize(200, 60);
        gamer2.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(gamer2);
    }

    public void removeGamer() {
        remove(gamer);
    }

    /**
     * 添加排行榜
     */
    private void addRankingButton() {
        JButton button = new JButton("Ranking List");
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

//        button.addActionListener(e -> {
//            System.out.println("Click ranking list");
//            JFrame Rank=new JFrame("Ranking List");
//            GameController.getUserController().
//        });
    }
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            JFileChooser fileChooser = new JFileChooser(); // 创建一个文件选择器
            fileChooser.setDialogTitle("选择要打开的文件"); // 给它一个标题
            File currentDirectory = new File("resource"); // 默认目录。TODO：改成你电脑上的目录
            fileChooser.setCurrentDirectory(currentDirectory); // 设置文件选择器起始目录为默认目录
            int result = fileChooser.showOpenDialog(button); // 打开文件选择器
            File chosenFile = null;
            if (result == JFileChooser.APPROVE_OPTION) { // 如果最终确认选择而不是推出
                chosenFile = fileChooser.getSelectedFile(); // 获取选中的文件
            }
            try {
                String path = chosenFile.getAbsolutePath();
                remove(GameController.getChessboard());
                GameController.setChessboard(new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, path));
                GameController.getChessboard().setLocation(HEIGTH / 10, HEIGTH / 10);
                add(GameController.getChessboard());
                GameController.getChessboard().repaint();
            } catch (Exception a) {
                a.printStackTrace();
            }
        });
    }

    private void addResetButton() {
        JButton button = new JButton("Reset");
        button.setLocation(HEIGTH, HEIGTH / 10 + 390);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click reset");
            remove(GameController.getChessboard());
            Save_Write a = new Save_Write();
            a.writeFileByFileWriter("resource/save1.txt", new ArrayList<String>() {{
                add("RNBQKBNRPPPPPPPP________________________________pppppppprnbqkbnrw");
            }});
            a.convertToChessboard(a.readFileByFileReader("resource/save1.txt"));


            GameController.setChessboard(new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, "resource/save1.txt"));
            GameController.getChessboard().setLocation(HEIGTH / 10, HEIGTH / 10);
            add(GameController.getChessboard());
            GameController.getChessboard().repaint();
        });
    }

    public void addRepentButton() {
        JButton button = new JButton("Repent");
        button.setLocation(HEIGTH, HEIGTH / 10 + 450);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click repent");
            remove(GameController.getChessboard());
            GameController.getChessboard().repentChess();
            //todo:异常处理
            String path = "resource/save2.txt";
            Save_Write sW = new Save_Write();
            sW.setStore(GameController.getChessboard().getStore());
            sW.setCurrentColor(GameController.getChessboard().getCurrentColors());
            sW.writeFileByFileWriter(path, sW.convertToList(sW.getStore()));
            GameController.setChessboard(new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, "resource/save2.txt"));
            GameController.getChessboard().setLocation(HEIGTH / 10, HEIGTH / 10);
            add(GameController.getChessboard());
            GameController.getChessboard().repaint();
        });
    }

    /**
     * 增加保存按钮<br>
     */
    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 300);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            Save_Write sW = new Save_Write();
            sW.setStore(GameController.getChessboard().getStore());
            sW.setCurrentColor(GameController.getChessboard().getCurrentColors());
            sW.writeFileByFileWriter(path, sW.convertToList(sW.getStore()));
//todo：故障处理
        });
    }
}
