import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Ranges;
import sweeper.Game;
public class JavaSweeper extends JFrame
{

    private Game game;
    private JPanel panel;
    private JLabel label;

    private final int COLS = 15;
    private final int ROWS = 15;
    private final int BOMBS = 20;
    private final int IMAGE_SIZE = 60;
    public static void main(String[] args) {
        new JavaSweeper();
    }
    private JavaSweeper()
    {
        game = new Game(COLS,ROWS,BOMBS);
        game.start();
        Ranges.setSize(new Coord(COLS,ROWS));
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }
    private void initFrame()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Sweeper");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }
    private void initLabel(){
        label = new JLabel("Welcome!");
        add(label,BorderLayout.SOUTH);
    }
    private void initPanel()
    {
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(Coord coord: Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image, coord.x*IMAGE_SIZE, coord.y*IMAGE_SIZE, this);
                }

            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX()/IMAGE_SIZE;
                int y = e.getY()/IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE,Ranges.getSize().y * IMAGE_SIZE));
        add (panel);
    }

    private String getMessage() {
        switch(game.getState()){
            case PLAYED: return "Think twice!";
            case BOMBED: return "You lose!";
            case WINNER: return "You win!";
            default: return "Welcome!";
        }
    }

    private  Image getImage(String name){
        String filename = "img/"+name.toLowerCase()+".jpg";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
    private void setImages(){
        for(Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }



}