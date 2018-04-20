import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
public class LocateClick extends JFrame implements MouseListener, MouseMotionListener
{
  static int red;
  static int blue;
  static int green;
  static int x1;
  static int y1;
  
  public LocateClick()
  {
    addMouseListener(this);
    addMouseMotionListener(this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public void mousePressed(MouseEvent e)
  {
    x1 = e.getX();
    y1 = e.getY();
  }
  
  public static void Locator(String filename){
	  
	ImageUtils image=new ImageUtils();
	Color[][] original = image.loadImage(filename);
	BufferedImage im=image.convertToBufferedFrom2D(original);
	
    LocateClick picture=new LocateClick();
    picture.setContentPane(new JLabel(new ImageIcon(im)));
    picture.setSize(picture.getPreferredSize());
    picture.pack();
    while(x1==0) {
    picture.setVisible(true);
    }
    picture.setVisible(false);
    red=original[x1][y1].getRed();
    blue=original[x1][y1].getBlue();
    green=original[x1][y1].getGreen();
  }
  
  public void getXY(String fileName) {
	  
	  Locator(fileName);
	  ImageUtils image = new ImageUtils();
	  Color[][] picture= image.loadImage(fileName);
	  int x= x1;
	  int y= y1;
	  System.out.println("x: " + x + " y: " + y);
	  x1=0;
	  y1=0;
	  
  }
  
//Overrides for MouseListener
  	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}