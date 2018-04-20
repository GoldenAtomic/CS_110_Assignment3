import javaxt.io.Image;
import java.awt.Color;
import java.util.Scanner;

public class IP {

	public static ImageUtils test1; //global
	public static Image image; //global
	
	public static void main(String[] args) {
	
	int opt;
	test1 = new ImageUtils(); //create obj for ImageUtils class
	
	Scanner userInput= new Scanner(System.in); //set-up new scanner
	
	try {
	System.out.println("Please enter the file path of the desired image to process:");
	String filePath = userInput.nextLine(); //get img file path from user
	
	image= new Image(filePath); //create new image
	
	int height = image.getHeight(); //getting image height
	int width = image.getWidth(); //getting image width
	int heightC = (int) (height * .5); //getting 1/4 of height
	int widthC = (int) (width * .5); //getting 1/4 of width
	
	image.resize(1500, 900, true); //make img fit GUI
	image.rotate(); //automatically rotate the image based on the image metadata 
	
	System.out.println("Enter new file path for final image to be saved to: ");
	String filePath2= userInput.nextLine(); //get file path to save image to
	
	image.saveAs(filePath2); //save img to new file path
	
	Color[][] imageNew= test1.loadImage(filePath2); //load img for GUI
	test1.addImage(imageNew, filePath); //add image to GUI
	test1.display(); //disp GUI
	
	System.out.println("Each image option is stacked and progresses.");
	System.out.println("Ex: If black and white is first selected, then flip, the final image will be black and white AND flipped.");
	System.out.println("Note: Checkerboard images will not be saved, and will not be used in image progression");
	
	int menu= 1; //set menu variable
	
	while (menu==1) { //start for loop
		
		//menu
		System.out.println("Select an option to apply: ");
		System.out.println("1) Black and White");
		System.out.println("2) Flip Image");
		System.out.println("3) Rotate CW");
		System.out.println("4) Rotate CCW");
		System.out.println("5) Crop to 1st Quadrant");
		System.out.println("6) Checkerboard overlay");
		System.out.println("7) Add black text to the top right corner");
		System.out.println("-To exit the program, close the image display window.");
		
		opt= userInput.nextInt(); //take user option selection
		
	if (opt==1) { //B&W
		image.desaturate(); //de-saturate img
		saveDisp(filePath, filePath2, imageNew, "B&W");
		
	} else if (opt==2) {
		image.flip(); //flip image
		saveDisp(filePath, filePath2, imageNew, "Flip");
	} else if (opt==3) {
		image.rotateClockwise(); //rotate img CW
		saveDisp(filePath, filePath2, imageNew, "CW");
	} else if (opt==4) {
		image.rotateCounterClockwise(); //rotate CCW
		saveDisp(filePath, filePath2, imageNew, "CCW");
	} else if (opt==5) {
		image.crop(0, 0, widthC, heightC); //crop image to 1st Quadrant
		saveDisp(filePath, filePath2, imageNew, "Crop 1Q");
	} else if (opt==6) {
		System.out.println("Select the tpye of checkers you would like");
		System.out.println("1) Translucent");
		System.out.println("2) Solid");
		int type= userInput.nextInt();
		imageNew= Chkr(imageNew, type); //use created method fullChkr to add 5x5 checkerboard to image
		test1.addImage(imageNew, "Checker Board");
		test1.display();
	} else if (opt==7) {
		System.out.println("Enter the text to place on the image: ");
		String text= userInput.next(); //user enters string to add to image
		image.addText(text, 0, 80, "Tahoma", 100, 0, 0, 0); //set text attributes
		saveDisp(filePath, filePath2, imageNew, text);
	} else if (opt==0) {
		menu=0; //exit program
	}
	}
	
	image.saveAs(filePath2); //saves again 
	
	} catch (Exception e) {
		System.out.println("A fatal error occured! \nCheck all entries. \nAll file paths must include file extension. \nOnly single strings can be used when adding text to images.");
	}
	}
	
	 public static Color [] [] Chkr (Color [] [] img, int opt){
		 
		 Scanner userInput1= new Scanner(System.in); //set-up new scanner
		 
		 //get RGB values from user
		 System.out.println("Please enter the RED value for the checkerboard: ");
		 int red= userInput1.nextInt();
		 System.out.println("Please enter the GREEN value for the checkerboard: ");
		 int green= userInput1.nextInt();
		 System.out.println("Please enter the BLUE value for the checkerboard: ");
		 int blue= userInput1.nextInt();
		 
		 //create new temporary array
         Color[] [] tmp=ImageUtils.cloneArray(img);
         
         //get row values for image split in 5 rows
         int row1= tmp.length/5;
         int row2= row1*2;
         int row3= row1*3;
         int row4= row1*4;
         
         int height= img[0].length; //get height
         
         //get col values for image if 5 rows
         int col1= height/5;
         int col2= col1*2;
         int col3= col1*3;
         int col4= col1*4;
         
         //start to replace pixels
         for(int row=0; row< tmp.length; row++) {

                 for(int col=0; col<tmp[row].length; col++) {
                 		
                     if ( ( ( (row<row1 ) || (row>row2 && row<row3 ) || (row>row4)) && ( (col<col1) || (col>col2 && col<col3) || (col>col4) ) ) ||
                    		 ( ( (row>row1 && row<row2) || (row>row3 && row<row4))&& ( ( (col>col1) && col<col2 ) || (col>col3 && col<col4) ) ) )  {
                    	 
                    	 if (opt ==1) {
	                    	 Color pixel=tmp[row][col];
	                    	 int r=pixel.getRed();
	                    	 int b=pixel.getBlue();
	                    	 int g=pixel.getGreen();
	                    	 
	                         tmp[row][col]=new Color (Math.abs(r-red), Math.abs(b-blue), Math.abs(g-green));
                    
                    	 }else if (opt==2) {
                    		 
                    		 tmp[row][col]=new Color (red, blue, green);
                    		 
                    	 }
                     }
                }
         }
       return tmp;
 }      

 
	 public static void saveDisp(String filePath, String filePath2, Color [][] imageNew, String text) throws Exception {
		
	 	image.saveAs(filePath2);
		imageNew= test1.loadImage(filePath2);
		test1.addImage(imageNew, text); 
		test1.display();
	 }
}
