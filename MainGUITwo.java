import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.imageio.*;
import java.io.*;


/**
 * MainGUITwo is the second iteration of a program that can semi-quantitatively
 * analyze images taken from Western Blot membranes. This class handles the main
 * logic associated with creating and maintaining the GUI portion of the program
 * 
 * @author Ashton Sigler
 * @version 1.1a.05.29.18
 *
 */
@SuppressWarnings("serial")
public class MainGUITwo extends JFrame {
    private CommClass commClass;
    private JFrame mainFrame;
    private ImagePanel imagePanel;
    private ButtonPanel buttonPanel;
    //private JLabel image;
    //private ImageIcon imageIcon;
    private static BufferedImage buffImage;
    public static BufferedImage imageTwo;
    public static boolean hasBuffImage = false;
    
    public MainGUITwo() {
        commClass = new CommClass();
        mainFrame = new JFrame("Western Blot Analysis 1.1a");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        
        imagePanel = new ImagePanel();
        buttonPanel = new ButtonPanel();
        
        mainFrame.setJMenuBar(createMenuBar());
        //mainFrame.add(imagePanel, BorderLayout.WEST);
        mainFrame.getContentPane().add(imagePanel, BorderLayout.WEST);
        mainFrame.getContentPane().add(buttonPanel, BorderLayout.EAST);
        
        mainFrame.pack();
        mainFrame.setVisible(true);
        
    }
    
    public void replaceImagePanel() {
        mainFrame.getContentPane().remove(imagePanel);
        invalidate();
        imagePanel = new ImagePanel();
        mainFrame.getContentPane().add(imagePanel, BorderLayout.WEST);
        revalidate();
        mainFrame.pack();
        
    }
    private class ButtonPanel extends JPanel {
        private JButton reset,/*file,*/exit, process;
        
        public ButtonPanel() {
            exit = new JButton();
            exit.setText("Exit Program");
            exit.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae) {
                    System.exit(0);
                }
            });
            
            reset = new JButton();
            reset.setText("Reset");
            reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    hasBuffImage = false;
                    replaceImagePanel();
                    System.out.println("Resetting");
                }
            });
            
            process = new JButton();
            process.setText("Process Data");
            process.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    commClass.processData();
                }
            });
            
            add(process);
            add(reset);
            add(exit);
        }
        
        
    }
    
    //@SuppressWarnings("serial")
    private class ImagePanel extends JPanel {
        int x,y,x2,y2;
        
        private Dimension defaultPreferredSize;
        public ImagePanel() {
            defaultPreferredSize = new Dimension(800,500);
            if (!hasBuffImage) {
                setPreferredSize(defaultPreferredSize);
                System.out.println("Default PreferredSize");
            } else {
                setPreferredSize(new Dimension(
                                  buffImage.getWidth(), buffImage.getHeight()));
                System.out.println("Non-default PreferredSize");
            }
            x = y = x2 = y2 = 0;
            
            addMouseListener(new MyMouseListener());
        }
        
        public void setStartPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public void setEndPoint(int x, int y) {
            x2 = x;
            y2 = y;
        }
        
        public void resizePanel() {
            setSize(new Dimension(
                    buffImage.getWidth(), buffImage.getHeight()));
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (hasBuffImage) {
                this.resizePanel();
                System.out.println("found image, attempting to draw");
                System.out.println(buffImage.getWidth());
                System.out.println(buffImage.getHeight());
                System.out.println(this.getSize());
                g.drawImage(buffImage, 0, 0, null);
                //mainFrame.pack();
            } else {
                
                //this.setBackground(Color.BLACK);
            }
            g.setColor(Color.RED);
            drawRect(g,x,y,x2,y2);
        }
        
        public void drawRect(Graphics g, int x, int y, int x2, int y2) {
            int px = Math.min(x,x2);
            int py = Math.min(y, y2);
            int pw = Math.abs(x-x2);
            int ph = Math.abs(y-y2);
            g.drawRect(px, py, pw, ph);
        }
        
        class MyMouseListener extends MouseAdapter {
            public void mousePressed(MouseEvent e) {
                setStartPoint(e.getX(), e.getY());
            }
            
            public void mouseDragged(MouseEvent e) {
                setEndPoint(e.getX(), e.getY());
                repaint();
            }
            
            public void mouseReleased(MouseEvent e) {
                setEndPoint(e.getX(), e.getY());
                repaint();
            }
        }
        
        
    }
    
    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu jMenu;
        JMenuItem jmi, jexit;
        menuBar = new JMenuBar();
        //image = new JLabel("");
        
        jMenu = new JMenu("File");
        jmi = new JMenuItem("Open");
        jmi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser();
                int result = fc.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        buffImage = ImageIO.read(file);
                        hasBuffImage = true;
                        replaceImagePanel();
                        System.out.println(hasBuffImage);
                        System.out.println(buffImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        
        jexit = new JMenuItem("Exit");
        jexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        
        jMenu.add(jmi);
        jMenu.add(jexit);
        menuBar.add(jMenu);
        
        return menuBar;
    }
    
    public static void createAndShowGUI() {
        /*MainGUITwo gui =*/ new MainGUITwo();
    }
    
    public static void main(String args[]) {
        //final MainGUITwo mainGUI;
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
                System.out.println(hasBuffImage);
                
            }
        });
    }
    
}
