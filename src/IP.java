import javaxt.io.Image;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Scanner;
import javax.swing.JPanel;


public class IP {

	public static void main(String[] args) {
	 
		
	ImageUtils test1 = new ImageUtils(); //create obj for ImageUtils class
	
	Scanner userInput= new Scanner(System.in); //set-up new scanner
	
	try {
	System.out.println("Please enter the file path of the desired image to process:");
	String filePath = userInput.nextLine(); //get img file path
	
	Image image= new Image(filePath); //create new image
	
	//begin check if square by getting img dimensions
	int height = image.getHeight();
	int width = image.getWidth();
	int heightC = (int) (height * .5);
	int widthC = (int) (width * .5);
	
	image.resize(1500, 900, true); //make img fit GUI
	image.rotate(); //automatically rotate the image based on the image metadata 
	
	System.out.println("Enter new file path for final image to be saved to: ");
	String filePath2= userInput.nextLine(); 
	
	LocateClick testing = new LocateClick();
	
	/*testing.getXY(filePath);
	testing.getXY(filePath);
	testing.getXY(filePath);*/
	
	//image.addText("text", 0, 250, "Tahoma", 100, 0, 175, 175);
	
	image.saveAs(filePath2); //save square img
	
	Color[][] imageNew= test1.loadImage(filePath2); //load square img for GUI
	test1.addImage(imageNew, filePath); //add square image to GUI
	test1.display(); //disp GUI
	
	System.out.println("Each image option is stacked and progress.");
	System.out.println("Ex: If black and white is first selected, then flip, the final image will be black and white AND flipped.");
	System.out.println("Note: Checkerboard images will not be saved, and will not be used in image progression");
	
	int menu= 1;
	while (menu==1) {
		System.out.println("Select an option to apply: ");
		System.out.println("1) Black and White");
		System.out.println("2) Flip Image");
		System.out.println("3) Rotate CW");
		System.out.println("4) Rotate CCW");
		System.out.println("5) Crop to 1st Quadrant");
		System.out.println("6) Checkerboard overlay");
		System.out.println("7) Add black text to the top right corner");
		System.out.println("-To exit the program, close the image display window.");
		
		int opt= userInput.nextInt();
		
	if (opt==1) {
		image.desaturate();
		image.saveAs(filePath2); //save square img
		imageNew= test1.loadImage(filePath2);
		test1.addImage(imageNew, "BW"); //add square image to GUI
		test1.display(); //disp GUI
	} else if (opt==2) {
		image.flip();
		image.saveAs(filePath2); //save square img
		imageNew= test1.loadImage(filePath2);
		test1.addImage(imageNew, "Flipped"); //add square image to GUI
		test1.display(); //disp GUI
	} else if (opt==3) {
		image.rotateClockwise();
		image.saveAs(filePath2); //save square img
		imageNew= test1.loadImage(filePath2);
		test1.addImage(imageNew, "CW"); //add square image to GUI
		test1.display(); //disp GUI
	} else if (opt==4) {
		image.rotateCounterClockwise();
		image.saveAs(filePath2); //save square img
		imageNew= test1.loadImage(filePath2);
		test1.addImage(imageNew, "CCW"); //add square image to GUI
		test1.display(); //disp GUI
	} else if (opt==5) {
		image.crop(0, 0, widthC, heightC);
		image.saveAs(filePath2); //save square img
		imageNew= test1.loadImage(filePath2);
		test1.addImage(imageNew, "cropQ1"); //add square image to GUI
		test1.display(); //disp GUI
	}  else if (opt==6) {
		imageNew= fullChkr(imageNew);
		test1.addImage(imageNew, "Chkr");
		test1.display(); //disp GUI
	} else if (opt==7) {
		System.out.println("Enter the text to place on the image: ");
		String text= userInput.next();
		image.addText(text, 0, 80, "Tahoma", 100, 0, 0, 0);
		image.saveAs(filePath2); //save square img
		imageNew= test1.loadImage(filePath2);
		test1.addImage(imageNew, "added text"); //add square image to GUI
		test1.display(); //disp GUI
	} else if (opt==0) {
		menu=0;
	}
	}
	//image.trim(); //remove added black space needed to disp
	image.saveAs(filePath2); //saves again after trim
	userInput.close();
	} catch (Exception e) {
		System.out.println("A fatal error occured! \nCheck all entries. \nAll file paths must include file extension. \nOnly single strings can be used when adding text to images.");
	}
	}
	
	/*public static void saveNew(String filePath, String filePath2, Color[][] imageNew) {
		
		Image image= new Image(filePath);
		ImageUtils test1 = new ImageUtils();
		image.saveAs(filePath2); //save square img
		imageNew= test1.loadImage(filePath2);
		test1.addImage(imageNew, "cropQ1"); //add square image to GUI
		test1.display(); //disp GUI
	}*/
	
	 public static Color [] [] fullChkr (Color [] [] img){
		 
		 Scanner userInput= new Scanner(System.in); //set-up new scanner
		 
		 System.out.println("Please enter the RED value for the checkerboard: ");
		 int red= userInput.nextInt();
		 System.out.println("Please enter the GREEN value for the checkerboard: ");
		 int green= userInput.nextInt();
		 System.out.println("Please enter the BLUE value for the checkerboard: ");
		 int blue= userInput.nextInt();
		 
         Color[] [] tmp=ImageUtils.cloneArray(img);
         
         int row1= tmp.length/5;
         int row2= row1*2;
         int row3= row1*3;
         int row4= row1*4;
         
         int height= img[0].length;
         
         int col1= height/5;
         int col2= col1*2;
         int col3= col1*3;
         int col4= col1*4;
         
         for(int row=0; row< tmp.length; row++) {

                 for(int col=0; col<tmp[row].length; col++) {
                 		
                     if ( ( ( (row<row1 ) || (row>row2 && row<row3 ) || (row>row4)) && ( (col<col1) || (col>col2 && col<col3) || (col>col4) ) ) || ( ( (row>row1 && row<row2) || (row>row3 && row<row4))&& ( ( (col>col1) && col<col2 ) || (col>col3 && col<col4) ) ) )  {

                         Color pixel=tmp[row][col];

                         tmp[row][col]=new Color (red, blue, green);
                  
                     }
                }
         }
         userInput.close();
         return tmp;
 }      
}
