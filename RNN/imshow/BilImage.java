/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package soleo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author The code after adding sca and its hybrid
 *//*
 * To change this licens header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author NALINI_DISHA_SHIVANI
 */
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.table.*;
import java.io.*;
import java.util.*;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
//import com.sun.image.codec.jpeg.*;
import javax.swing.filechooser.FileFilter;




import java.util.Hashtable;
import java.util.Enumeration;
import java.nio.*;
import java.nio.channels.*;

//import java.util.Arrays;


/**************************** Main Class For This Image Processing Project ******************/////////////

class BilImage extends JFrame implements ActionListener
{

	JMenu zoom;
	JMenu sign;
	JMenu mnClass;
	JMenu mnHelp;
	JMenuItem miOpen;
	JMenuItem miSave;
        JMenuItem miExit;
	JMenuItem fuzzy;
	JMenuItem miAbout;
	JMenuItem miContrast;
	JMenuItem mifcc;
        JMenuItem mnen;
	static JMenuItem miRegion;
	JMenu mnEnhance;
	JMenuItem zin,zout;
	JMenuItem miFcm;
	JTextArea ta;
	 RegionGrow rw;

   	JButton bok,bclose;
        static boolean chek=false;
	AboutDialog dialog = null;
	Font f;
	Color co;
	public static String name;
	String fname;
	String ImageName;
	BufferedImage img;
	Container con = getContentPane();
	Container ContentPane = getContentPane();
	DemoPanel pane;
      static Fcm fcm;
	static float zoomFactor;

	static {
		try{
			File file=new File("C:\\BilImage");
			if(!file.exists())
				file.mkdir();
			System.out.println(file.getAbsolutePath());
			//PrintStream ps=new PrintStream(file.getAbsoluteFile()+"\\LogFile.txt");
		}catch (Exception e) {
			System.out.println("Error in creating log file."+e);
		}
	}

	public static void main(String s[])
	{

		BilImage frame = new BilImage();
		frame.setTitle("Similarity and Dissimilarity For Fuzzy Soft Classifier: SMIC Tool");
		frame.setVisible(true);
		frame.setLocation(100,100);
		frame.setSize(800, 550);
		frame.show();


	}

	BilImage()
	{

		JMenuBar mb = new JMenuBar();
		JMenu mnfile = new JMenu("File");
		mnClass=new JMenu("Classifier");
		sign =new JMenu("Signature files");
		sign.setEnabled(false);
		fuzzy=new JMenuItem("Table for fuzzy c mean");
		mnEnhance = new JMenu("Tools");
		miOpen = new JMenuItem("Open");
		miSave = new JMenuItem("Save");
		miExit = new JMenuItem("Exit");
		miAbout = new JMenuItem("About");
		miContrast = new JMenuItem("Enhancement");
		mifcc = new JMenuItem("FCC");
		miRegion = new JMenuItem("Region Grow");
                mnen = new JMenuItem("Enhanced FCM");
                mnHelp = new JMenu("Help");
		zoom = new JMenu("Zoom");
		zin=new JMenuItem("Zoom In");
		zout=new JMenuItem("Zoom Out");
		miFcm=new JMenuItem("Fuzzy C-Means");

		mnEnhance.setEnabled(false);
		mnClass.setEnabled(false);
		mnEnhance.add(zoom);
		zoom.add(zin);
		zoom.add(zout);

		sign.add(fuzzy);

		mnfile.add(miOpen);
		mnfile.add(miSave);
		mnfile.addSeparator();
		mnfile.add(miExit);
		mnEnhance.add(miContrast);
		mnEnhance.add(mifcc);
		mnEnhance.add(miRegion);
		mnClass.add(miFcm);
            	mnHelp.add(miAbout);
		mb.add(mnfile);
		mb.add(mnEnhance);
		mb.add(sign);
		mb.add(mnClass);
             mb.add(mnHelp);

		setJMenuBar(mb);

		con.setBackground(Color.PINK);
		f = new Font("Palatino Linotype", Font.ITALIC, 21);

		miOpen.addActionListener(this);
		miSave.addActionListener(this);
		miExit.addActionListener(this);
		sign.addActionListener(this);
		fuzzy.addActionListener(this);
		miAbout.addActionListener(this);
		miContrast.addActionListener(this);
		mifcc.addActionListener(this);
		miRegion.addActionListener(this);
		zoom.addActionListener(this);
		zin.addActionListener(this);
		zout.addActionListener(this);
		mnClass.addActionListener(this);
		miFcm.addActionListener(this);
                mnen.addActionListener(this);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				System.exit(0);
			}
		});

	}

	JButton okBut;
	JTextField jtBand, jtRow, jtCol;
	JFrame cross;
	public static File imgFile;

	public static int p,q,r;
	public static int tab=0;

	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		if(source==fuzzy)
		{
		    DemoPanel pane=new DemoPanel();
		    imagetable6 Table6;
			System.out.println("Table Opened");
			int Noclicks=pane.retclicks();
			Table6=new imagetable6();
			Table6=new imagetable6(Noclicks);
			Table6.setSize(570,350);
			Table6.setLocation(350,40);
			Table6.setVisible(true);
			System.out.println( " No of clicks is" + Noclicks);
			tab=1;

		}

		if (source == miOpen)
		{
			JFileChooser chooser = new JFileChooser();

			int r5 = chooser.showOpenDialog(this);
			if (r5 == chooser.APPROVE_OPTION)
			{
				File file = chooser.getSelectedFile();
				imgFile=file;
                              name = file.getName();
				ReadImageData Rid1 = new ReadImageData(file);
				this.ImageName = name;
				picture Picture1 = new picture();
				picture.setimage(name);
				p=q=r=1;
				img = Picture1.getpicdata(p, q, r);
				zoomFactor=1;
				DemoPanel pane = new DemoPanel(img);
				ContentPane.removeAll();
				ContentPane.add(new JScrollPane(pane));
				mnEnhance.setEnabled(true);
				mnClass.setEnabled(true);
				sign.setEnabled(true);
                                System.gc();
				validate();
			}

		}

		if (source == miSave)
		{
			System.out.println("Clicked");

		}

		if (source == miExit)
		{
			System.exit(0);
		}

		if (source == miAbout)
		{
			JPanel panel=new JPanel();
			ta = new JTextArea();
			ta.setBounds(100, 100, 375, 100);
			ta.setFont(f);
			ta.setForeground(Color.RED);
			con.removeAll();
			panel.add(ta);
			con.add(panel,"Center");
			ta.setBackground(Color.PINK);
			panel.setBackground(Color.PINK);
			ta.setText("\n\n\tRemote Sensing is a technique of sensing the objects\n\twithout touching & seeing them.\n\n\tThis project helps us in gethering information from \n\tthese type of techniques.");

			System.out.println("Help Clicked");
		}
                if (source == mnen)
		{



                }

		if (source == miContrast)
		{

			       Enhancement  enhance1=new Enhancement();
                   enhance1.enhances(p,q,r);
	               img=enhance1.getpicdata();
			   pane=new DemoPanel(img);
			   pane.repaint();
                     ContentPane.removeAll();
		         ContentPane.add(new JScrollPane(pane));
			   validate();

		}

		if(source == miRegion)
              {
		 System.out.println("Region Frame Opened");
                 chek=true;
	    	rw=new RegionGrow();
	    	rw.setSize(800,850);
	    	rw.show();


        }
		if (source == mifcc)
		{
			if (dialog == null)
				dialog = new AboutDialog(this);

                        dialog.setSize(500, 150);
			dialog.setVisible(true);

			p = dialog.show_band1();
			q = dialog.show_band2();
			r = dialog.show_band3();
			picture Picture1 = new picture();
			img = Picture1.getpicdata(p, q, r);
			pane = new DemoPanel(img);
			pane.repaint();
			ContentPane.removeAll();
			ContentPane.add(new JScrollPane(pane));
			validate();
			System.out.println("p : "+p+"q : "+q+"r : "+r);

		}

		if(source==zin)
		{
	         System.out.println("zoom in");
	         String in=JOptionPane.showInputDialog(null,"Enter the Enlargement factor for zoom ");
	         int f= Integer.parseInt(in);
	         ZoomIn zoompane = new ZoomIn(img,f);
                 img=zoompane.retchangepic();
			 pane=new DemoPanel(img);
			 pane.repaint();
                 ContentPane.removeAll();
		 ContentPane.add(new JScrollPane(pane));
		     zoomFactor=zoomFactor*f;
			 validate();

	    }

	    if(source==zout)
	    {
	    	 System.out.println("zoom out");
	    	 String in=JOptionPane.showInputDialog(null,"Enter the shrinking factor for zoom out");
	         int f= Integer.parseInt(in);
	         ZoomOut zoompane=new ZoomOut(img,f);
             img=zoompane.retchangepic();
			 pane=new DemoPanel(img);
			 pane.repaint();
                 ContentPane.removeAll();
		 ContentPane.add(new JScrollPane(pane));
		 zoomFactor=zoomFactor/f;
			 validate();


	    }

	    if(source==miFcm)
	    {

	    	System.out.println("FCM Frame Opened");
	    	fcm=new Fcm();
	    	fcm.setSize(950,600);
                fcm.show();

	    }


	}

}



///////////*********************** Class Read Image Data To Read The Header File of Image. ***********///////////////



class ReadImageData
{
	public static File picfile;
	public static int Bands;
	private static int Rows;
	private static int Columns;

	public ReadImageData()
	{

	}

	public ReadImageData(File file)
	{
		String name = file.getName();
		this.picfile = file;
	}

	public void read(File Name)//read image file name
	{
		int index = 0;
		int b1 = 0;
		int row = 0;
		int col = 0;
		int x = 0;
		int c[];
		InputStream f1;
		char p[] = new char[42];
		String ban = new String();
		String row1 = new String();
		String col1 = new String();
		this.picfile = Name;
		String s[] = new String[30];
		try
		{
			f1 = new FileInputStream(Name + ".hdr");

			c = new int[11];
			for (int i = 0; i < 42; i = i + 1)
			{
				p[i] = (char)f1.read();
			}
			f1.close();
		}
		catch (Exception e)
		{
		}

		for (int i = 6; i < 13; i = i + 1)
		{
			if (p[i] == '\n') break;
			if (p[i] == ' ') p[i] = '0';
			ban = ban + p[i];
		}
		b1 = Integer.parseInt(ban);
		this.Bands = b1;
		System.out.println(b1);
		for (int i = 20; i < 27; i = i + 1)
		{
			if (p[i] == '\n') break;
			if (p[i] == ' ') p[i] = '0';
			row1 = row1 + p[i];
		}
		row = Integer.parseInt(row1);
		System.out.println(row);
		this.Rows = row;
		for (int i = 35; i < 41; i = i + 1)
		{
			if (p[i] == '\n') break;
			if (p[i] == ' ') p[i] = '0';
			col1 = col1 + p[i];
		}
		col = Integer.parseInt(col1);
		this.Columns = col;
		System.gc();
	}

	public int bands()//return bands
	{
		return Bands;
	}

	public int rows()//return rows
	{
		return Rows;
	}

	public int columns()//return columns
	{
		return Columns;
	}

	public File imagefile()//return imagefilename
	{
		return picfile;
	}
}




///////////////**************** Picture Class To Read & Display the Image ************////////////////////


class picture extends JPanel
{
	public static BufferedImage img;
	private int Width;
	private int Height;
	private int Bands;
	private ReadImageData ImgData1;
	static private File ImageFile;
	static int[][][] a;
	int array_size;
	static String ImageName;
	int x_pass,y_pass,y_pass2,num_of_slice,origin_x=0,origin_y=0;
	public static int []maxBand;
	public static int []minBand;
	public static byte pix_rgb[][];
	public static int shiftFlag=0;

        public static double sidband1[];
        public static double sidband2[];
        public static double sidband3[];
        public static double sidband4[];
         public static double sidband5[];
        public static double sidband6[];
        public static double sidband7[];
        public static double sidband8[];

	public static void setimage(String nam)
	{
		ImageFile = new File(nam);
		ImageName = nam;
	}


	public BufferedImage getpicdata(int Color1, int Color2, int Color3)///return image
	{
		System.gc();
		ImgData1 = new ReadImageData();
		ImageFile = ImgData1.imagefile();

		ImgData1.read(ImageFile);
		Bands = ImgData1.bands();
		Width = ImgData1.columns();
		Height = ImgData1.rows();
		int p = Color1;
		int q = Color2;
		int r = Color3;
                sidband1 = new double[Width*Height];
		sidband2 = new double[Width*Height];
		sidband3 = new double[Width*Height];
		sidband4 = new double[Width*Height];
                sidband5 = new double[Width*Height];
		sidband6 = new double[Width*Height];
		sidband7 = new double[Width*Height];
		sidband8 = new double[Width*Height];
		System.out.println("Bands : " + Bands);
		System.out.println("Width : " + Width);
		System.out.println("Height : " + Height);
		System.gc();
		int i=0, x, j=0, l, k;
		int index = 0;

		int pix[];
		int pix1[];
		int pix2[];
		int pix3[];

		int pix_2[];
		int pix1_2[];
		int pix2_2[];
		int pix3_2[];


		int p1, p2;
		int z,g;
		char temp[] = new char[10];
		char temp2[] = new char[100];
		int myFlag = 0;

		int maxRGB = 0, tempMax = 0;
		byte temp_max[];


		try
		{
			InputStream MyHdr = new FileInputStream(ImageFile + ".hdr");
			for (k = 0; k < 71; k++)
				temp2[k] = (char)MyHdr.read();
			for (k = 71; k < 78; k++)
			{
				temp[k - 71] = (char)MyHdr.read();
			}
		}
		catch (Exception e) { myFlag = 1; }
		String myTemp = (new String(temp)).trim();
		System.out.println("Read Data : " + myTemp);
		try
		{
			String s = myTemp.substring(1);
			shiftFlag = Integer.parseInt (s);
		}
		catch(Exception e){ myFlag = 1;}

		System.out.println("My Shift : " + shiftFlag);
		if(myFlag == 1)
			shiftFlag = 8;

		long fsize, fsize2;

		MappedByteBuffer mBuf;
		int tempWidth;

		try
		{

			FileInputStream f1 = new FileInputStream(ImageFile);            //reading pixel values for each band
			FileChannel fchan = f1.getChannel();
			FileOutputStream fout[] = new FileOutputStream[Bands];
			FileChannel fchanOut[] = new FileChannel[Bands];



			fsize = fchan.size();

			myFlag = 0;
			try
			{
				FileInputStream chk = new FileInputStream("Data" + ImageName +  "0");
			}
			catch(FileNotFoundException e)
			{
				myFlag = 1;
			}
			if(myFlag == 1)
			{

				for(k=0; k<Bands; k++)
				{

					fout[k] = new FileOutputStream("Data" + ImageName +  k);

					fchanOut[k] = fout[k].getChannel();

				}

				int pos=0;
				index=1;
				System.out.println("My Database Creation Started");



				tempWidth = Width;


				if (shiftFlag != 8)
					tempWidth = Width * 2;


				index=1;
				for(i=0;i<Height*Bands;i++)
				{
					mBuf=fchan.map(FileChannel.MapMode.READ_ONLY,pos,tempWidth);
					pos=pos+tempWidth;
					fchanOut[index-1].write(mBuf);

					index=index+1;
					if(index==(Bands+1))
						index=1;
				}

				f1.close();
				fchan.close();
				for(i=0;i<Bands;i++)
				{
					fout[i].close();
					fchanOut[i].close();
				}

			}
			System.gc();
		}
		catch (Exception e) { System.out.println("Before Database creation : " + e); }

		System.out.println("My Database Created");



		img = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_ARGB);

		WritableRaster raster = img.getRaster();


		FileInputStream fin_band[]=new FileInputStream[Bands];
 		FileChannel fchan_in[]=new FileChannel[Bands];

 		index = 0;
 		double myTempCalc;

 		int slices, y_pass, y_origin;
 		int x_win=Width,y_win=600,y_win2,flag1=0;
 		int uneven_flag = 1;

 		if((Height % y_win) == 0)
 		{
 			slices = (int)(Height/y_win);
 			uneven_flag = 0;
 		}
 		else
 			slices = (int)(Height/y_win)+1;

 		fsize2=((Height-(slices-1)*y_win)*x_win);
 		y_win2 = (Height-(slices-1)*y_win);
		if(Height>y_win || Width>x_win)
		{
		 	fsize=(y_win*x_win);
		 	flag1=1;
		 	y_pass=y_win;

		}
	 	else
		{
		 	fsize=(Height*Width);
		 	System.out.println("Fsize : " + fsize);
		 	flag1=0;
		 	y_pass=Width;
		}


		int band_num[] = new int[Bands];
		String input;



		byte pix_byte[],pix_rgb2[][];



 		try
 		{
 			for(k=0; k<Bands; k++)
 			{
 				fin_band[k] = new FileInputStream("Data" + ImageName +  k);
 				fchan_in[k] = fin_band[k].getChannel();

 			}


 			if(shiftFlag == 8)
 			{
	 			pix = new int [(int)fsize];
				pix1 = new int [(int)fsize];
				pix2 = new int [(int)fsize];
				pix3 = new int [(int)fsize];

				pix_2 = new int [(int)fsize2];
			 	pix1_2 = new int [(int)fsize2];
				pix2_2 = new int [(int)fsize2];
				pix3_2 = new int [(int)fsize2];

	 			pix_rgb=new byte[Bands][(int)fsize];
				pix_rgb2=new byte[Bands][(int)fsize2];
			}
			else
			{
				pix = new int [(int)fsize];
				pix1 = new int [(int)fsize];
				pix2 = new int [(int)fsize];
				pix3 = new int [(int)fsize];

				pix_2 = new int [(int)fsize2];
			 	pix1_2 = new int [(int)fsize2];
				pix2_2 = new int [(int)fsize2];
				pix3_2 = new int [(int)fsize2];

	 			pix_rgb=new byte[Bands][(int)fsize*2];
				pix_rgb2=new byte[Bands][(int)fsize2*2];

			}


			byte pix_line[];
			byte pix_line2[];
			int abc=0;
			if(shiftFlag == 8)
			{
				pix_line = new byte[x_win];
				pix_line2 = new byte[x_win];
				abc = x_win;
			}
			else
			{
				pix_line = new byte[x_win * 2];
				pix_line2 = new byte[x_win * 2];
				abc = x_win * 2;
			}
			int line_start,line_start2=0;
			int myMax = 0;
			int myMin=0;
			long count;

			int tempCount;
			if(shiftFlag == 8)
				count = fsize;
			else
				count = fsize * 2;


			long counter=0;
			long start_pos = 0;
			maxBand=new int[Bands];
			minBand=new int[Bands];
			int maxBand2[]=new int[Bands];
		   	int minBand2[]=new int[Bands];
		   	int myMin2=0;
		   	int myMax2=0;
		   	line_start = 0;
		   	int first_time=1;
		   		System.out.println("Calculating Maximum and Minimum1");
		   	for(i=0; i<Bands; i++)
		   	{
		   		maxBand[i] = -1;
		   		minBand[i] = 65535;
		   	}
		   		System.out.println("Calculating Maximum and Minimum2");

		   	//Calculation of Maximum and Minimum
		   	FileInputStream f1 = new FileInputStream(ImageFile);
			FileChannel fchan = f1.getChannel();

		   	if(shiftFlag == 8)
				tempWidth = Width;
			else
				tempWidth = Width * 2;
			System.out.println("Calculating Maximum and Minimum");
		   	for(j=0; j<Height; j++)
		   	{

		   		for(i=0; i<Bands; i++)
		   		{
		   			mBuf = fchan.map(FileChannel.MapMode.READ_ONLY,line_start,tempWidth);
					mBuf.get(pix_line);
					for(k=0; k<tempWidth; k++)
						pix_rgb[i][k] = pix_line[k];
					line_start += tempWidth;

					for(k=0; k<Width; k++)
					{
						if(shiftFlag != 8)
						{
							p1 = pix_rgb[i][2*k] << 24;
							p2 = (pix_rgb[i][2*k+1] <<24 ) >>> 8;
							tempCount = p1 | p2;
							tempCount = tempCount >>> 16;
						}
						else
							tempCount = (pix_rgb[i][k] << 24) >>> 24;

						if(tempCount>maxBand[i])
							maxBand[i]=tempCount;

						if(tempCount<minBand[i])
						   minBand[i]=tempCount;
					}
		   		}
		   		if(j%500 == 0)
		   		{
		   			System.out.println("j : " + j);
		   			System.gc();
		   		}
		   	}



		myMax = maxBand[0];
		for(i=1; i<Bands; i++)
			if(myMax < maxBand[i])
				myMax = maxBand[i];

		for(i=0;i<Bands;i++)
			 System.out.println("the maximum value for band"+ (i+1 )+ " " + maxBand[i]);

		for(i=0;i<Bands;i++)
			System.out.println("the minimum value for band"+ (i+1) + " " + minBand[i]);


			// when size of image is smaller than window size set by x_win & y_win
			if(flag1==0)
			{

				for(i=0; i<Bands; i++)
				{
					if(shiftFlag == 8)
						mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,0,fsize);
					else
						mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,0,fsize*2);
					mBuf.get(pix_rgb[i]);
				}
				System.gc();

 				//convert raw bytes into arrays.
 				if(shiftFlag == 8)
 				{
 					System.out.println("This is an 8 bit image");
					for(i=0;i<fsize;i++)
					{
						pix[i]=255;
						if(Bands!=1)
						{
							if(pix_rgb[p-1][i]>=0)
								pix1[i]= pix_rgb[p-1][i];
							else
							  	pix1[i]= 256+pix_rgb[p-1][i];

							if(pix_rgb[q-1][i]>=0)
							  	pix2[i]= pix_rgb[q-1][i];
							else
							  	pix2[i]= 256+pix_rgb[q-1][i];

							if(pix_rgb[r-1][i]>=0)
							  	pix3[i]= pix_rgb[r-1][i];
							else
							  	pix3[i]= 256+pix_rgb[r-1][i];
						}

						else
						{
							if(pix_rgb[0][i]>=0)
							{
								pix1[i]= pix_rgb[0][i];
								pix2[i]=pix1[i];
								pix3[i]=pix1[i];
							}
							else
							{
								pix1[i]= 256+pix_rgb[0][i];
								pix2[i]=pix1[i];
								pix3[i]=pix1[i];
							}
						 }
	    	      	}//close for
				}
				else
				{

					for(i=0;i<fsize;i++)
					{
						pix[i]=255;
						if(Bands!=1)
						{
							//for pix1
							if(pix_rgb[p-1][2*i]<0)
								z=256+pix_rgb[p-1][2*i];
							else
								z=pix_rgb[p-1][2*i];

							if(pix_rgb[p-1][2*i+1]<0)
								g=256+pix_rgb[p-1][2*i+1];
							else
								g=pix_rgb[p-1][2*i+1];

							p1 = z << 8;
							p2 = g;
							pix1[i] = p1 | p2;

							myTempCalc = ((double)pix1[i] / (double)myMax) * 255.0;
							pix1[i] = (int)myTempCalc;

							// for pix2
							if(pix_rgb[q-1][2*i]<0)
								z=256+pix_rgb[q-1][2*i];
							else
								z=pix_rgb[q-1][2*i];

							if(pix_rgb[q-1][2*i+1]<0)
								g=256+pix_rgb[q-1][2*i+1];
							else
								g=pix_rgb[q-1][2*i+1];

							p1 = z << 8;
							p2 = g;
							pix2[i] = p1 | p2;

							myTempCalc = ((double)pix2[i] / (double)myMax) * 255.0;
							pix2[i] = (int)myTempCalc;

							// for pix3

							if(pix_rgb[r-1][2*i]<0)
								z=256+pix_rgb[r-1][2*i];
							else
								z=pix_rgb[r-1][2*i];

							if(pix_rgb[r-1][2*i+1]<0)
								g=256+pix_rgb[r-1][2*i+1];
							else
								g=pix_rgb[r-1][2*i+1];

							p1 = z << 8;
							p2 = g;
							pix3[i] = p1 | p2;

							myTempCalc = ((double)pix3[i] / (double)myMax) * 255.0;
							pix3[i] = (int)myTempCalc;

						}

						else
						{

						}

	    	      	}//close for
					System.out.println("Image Complete");
				}


				raster.setSamples(0,0,Width,Height,0,pix1);
				raster.setSamples(0,0,Width,Height,1,pix2);
				raster.setSamples(0,0,Width,Height,2,pix3);
				raster.setSamples(0,0,Width,Height,3,pix);


			}//close if
			else if(flag1==1) // when size of image is greater than window size set by x_win & y_win
			{

				System.out.println("the maximum value in bigger image is:"+ myMax);

				System.out.println("Maximum Value : " + myMax);

				if(shiftFlag==8)
				{
					line_start=0;
					for(int v=0;v<slices;v++)
					{
						System.out.println("enter v :"+v);

						if(v!=(slices-1))
						{

				  		  for( i=0;i<Bands;i++)
						  {
							if(Bands==1 && i!=0)
							{
								continue;
							}

							//line_start=line_start2;
							line_start=v*y_win*Width;
							System.out.println("line start i :"+line_start+" "+i);
							int k_index=0;
							// read the files line by line i.e x_win bytes in one go
							// and repeat unpo y_win i.e. size of window
							for(j=0;j<y_win;j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win);
								mBuf.get(pix_line);// store each line in separate buffer
								// i.e pix_line[]
								line_start=line_start+Width;

								// increment line_start by Width to point to next line
								// and leave rest of the pixels
								for(k=0;k<x_win;k++,k_index++)
								{
								// transfer one line data into pix_rgb[][]
								pix_rgb[i][k_index]=pix_line[k];

								}
							}//close for

							System.out.println("i :"+i);


				    	}//close for

						//line_start2=line_start;
							System.out.println("enter ");
							System.out.println("fsize :"+fsize);

						if(Bands!=1)
						{
							for( i=0;i<fsize;i++)
							{
				    		    pix[i]=255;
				    		    if(pix_rgb[p-1][i]>=0)
							    pix1[i]= pix_rgb[p-1][i];
								else
								pix1[i]= 256+pix_rgb[p-1][i];

								if(pix_rgb[q-1][i]>=0)
								pix2[i]= pix_rgb[q-1][i];
								else
								pix2[i]= 256+pix_rgb[q-1][i];

								if(pix_rgb[r-1][i]>=0)
								pix3[i]= pix_rgb[r-1][i];
								else
								pix3[i]= 256+pix_rgb[r-1][i];

							}

							raster.setSamples(0,origin_y,x_win,y_win,0,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,1,pix2);
							raster.setSamples(0,origin_y,x_win,y_win,2,pix3);
				 			raster.setSamples(0,origin_y,x_win,y_win,3,pix);
				 			System.out.println("slice displayed");
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

					    }
						else
						{
							  for( i=0;i<fsize;i++)
							  {
							      pix[i]=255;
							      if(pix_rgb[0][i]>=0)
							      pix1[i]= pix_rgb[0][i];
							      else
							  	  pix1[i]= 256+pix_rgb[0][i];

							  }
							  pix2=pix3=pix1;

							raster.setSamples(0,origin_y,x_win,y_win,0,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,1,pix2);
							raster.setSamples(0,origin_y,x_win,y_win,2,pix3);
						 	raster.setSamples(0,origin_y,x_win,y_win,3,pix);

							System.out.println("slice displayed");
						 	origin_x=0;
						 	origin_y=origin_y+y_win;

						}
						System.gc();

					}//close if(v!=num_of_slice-1)

					else if(v==(slices-1))
					{
						System.out.println("v origin_y line_start  :"+v+" "+origin_y+" "+line_start);
						for( i=0;i<Bands;i++)
						{
							if(Bands==1 && i!=0)
							{
								continue;
							}
							line_start=v*y_win*Width;
							System.out.println("line start2 i :"+line_start+" "+i);
							int k_index=0;
							// read the files line by line i.e x_win bytes in one go
							// and repeat unpo y_win i.e. size of window

						    for(j=0;j<(Height-(slices-1)*y_win);j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win);
								mBuf.get(pix_line);// store each line in separate buffer
								// i.e pix_line[]
								line_start=line_start+Width;
								// increment line_start by Width to point to next line
								// and leave rest of the pixels
								for(k=0;k<x_win;k++,k_index++)
								{
									// transfer one line data into pix_rgb[][]
									pix_rgb2[i][k_index]=pix_line[k];

								}

							}//close for
							System.out.println("i :"+i);
						}//close for

						System.out.println("enter ");
						System.out.println("fsize2 :"+fsize2);

						if(Bands!=1)
						{
							for( i=0;i<fsize2;i++)
							{
								pix_2[i]=255;

							    if(pix_rgb2[p-1][i]>=0)
							    pix1_2[i]= pix_rgb2[p-1][i];
							    else
							    pix1_2[i]= 256+pix_rgb2[p-1][i];

							    if(pix_rgb2[q-1][i]>=0)
							    pix2_2[i]= pix_rgb2[q-1][i];
								else
								pix2_2[i]= 256+pix_rgb2[q-1][i];

								if(pix_rgb2[r-1][i]>=0)
								pix3_2[i]= pix_rgb2[r-1][i];
								else
								pix3_2[i]= 256+pix_rgb2[r-1][i];
							}
						    y_pass2=(Height-origin_y);//(num_of_slice-1));
							raster.setSamples(0,origin_y,x_win,y_pass2,0,pix1_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,1,pix2_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,2,pix3_2);
				 			raster.setSamples(0,origin_y,x_win,y_pass2,3,pix_2);

						}
						else
						{
							for( i=0;i<fsize2;i++)
							{
								pix_2[i]=255;

								if(pix_rgb2[0][i]>=0)
							    pix1_2[i]= pix_rgb2[0][i];
							    else
							    pix1_2[i]= 256+pix_rgb2[0][i];
							}
							pix3_2=pix2_2=pix1_2;

						    y_pass2=(Height-origin_y);//(num_of_slice-1));
							raster.setSamples(0,origin_y,x_win,y_pass2,0,pix1_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,1,pix2_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,2,pix3_2);
				 			raster.setSamples(0,origin_y,x_win,y_pass2,3,pix_2);

						}

						System.gc();

					}//close if(v==num_of_slice-1)


				}//close for "v"

			}// close else if
			else
			{

			  /* the code for 16 bit bigger image */
				line_start=0;
			  	System.out.println("Total number of slices : " + slices);
				for(int v=0;v<slices;v++)
				{
					System.out.println("\nSlice Number v :"+v);

					if(v!=(slices-1))
					{

				  		for( i=0;i<Bands;i++)
						{
							if(Bands==1 && i!=0)
							{
								continue;
							}

							line_start=v*y_win*Width*2;
							System.out.println("line start i :"+line_start+" "+i);
							System.out.println("\n");
							int k_index=0;
							// read the files line by line i.e x_win bytes in one go
							// and repeat unpo y_win i.e. size of windowx
							for(j=0;j<y_win;j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win*2);
								mBuf.get(pix_line);// store each line in separate buffer
								for(k=0;k<x_win*2;k++,k_index++)
								{
									pix_rgb[i][k_index]=pix_line[k];

								}

								line_start=line_start+(Width*2);

							}//close for



				    	}//close for


							System.out.println("enter ");
							System.out.println("fsize :"+fsize);
							double TempCalc;
							int myChkVal;

						System.out.println("Check value : p : " + p + ", q : " + q + ", r : " + r);
						if(Bands!=1)
						{
							line_start = 0;
							for( i=0;i<fsize;i++)
							{
				    		    pix[i]=255;

				    		    //for pix1
								if(pix_rgb[p-1][2*i]<0)
									z=256+pix_rgb[p-1][2*i];
								else
									z=pix_rgb[p-1][2*i];

								if(pix_rgb[p-1][2*i+1]<0)
									g=256+pix_rgb[p-1][2*i+1];
								else
									g=pix_rgb[p-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix1[i] = p1 | p2;

								myTempCalc = ((double)pix1[i] / (double)myMax) * 255.0;
								pix1[i] = (int)myTempCalc;

							// for pix2
								if(pix_rgb[q-1][2*i]<0)
									z=256+pix_rgb[q-1][2*i];
								else
									z=pix_rgb[q-1][2*i];

								if(pix_rgb[q-1][2*i+1]<0)
									g=256+pix_rgb[q-1][2*i+1];
								else
									g=pix_rgb[q-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix2[i] = p1 | p2;

								myTempCalc = ((double)pix2[i] / (double)myMax) * 255.0;
								pix2[i] = (int)myTempCalc;

							// for pix3

								if(pix_rgb[r-1][2*i]<0)
									z=256+pix_rgb[r-1][2*i];
								else
									z=pix_rgb[r-1][2*i];

								if(pix_rgb[r-1][2*i+1]<0)
									g=256+pix_rgb[r-1][2*i+1];
								else
									g=pix_rgb[r-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix3[i] = p1 | p2;

								myTempCalc = ((double)pix3[i] / (double)myMax) * 255.0;
								pix3[i] = (int)myTempCalc;

				    		    line_start=line_start+Width*2;
				    		}

							raster.setSamples(0,origin_y,x_win,y_win,0,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,1,pix2);
							raster.setSamples(0,origin_y,x_win,y_win,2,pix3);
				 			raster.setSamples(0,origin_y,x_win,y_win,3,pix);
				 			System.out.println("Main Slice : " + v);
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

					    }
						else
						{
							System.out.println("slice displayed2");
						 	origin_x=0;
						 	origin_y=origin_y+y_win;

						}
						System.gc();

					}//close if(v!=num_of_slice-1)

					else if(v==(slices-1))
					{
						System.out.println("Last slice");
				  		for( i=0;i<Bands;i++)
						{
							if(Bands==1 && i!=0)
							{
								continue;
							}

							line_start=v*y_win*Width*2;
							System.out.println("line start i :"+line_start+" "+i);
							System.out.println("\n");
							int k_index=0;

							for(j=0;j<(Height-(slices-1)*y_win);j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win*2);
								mBuf.get(pix_line);// store each line in separate buffer
								for(k=0;k<x_win*2;k++,k_index++)
								{
									pix_rgb2[i][k_index]=pix_line[k];

								}

								line_start=line_start+(Width*2);

							}//close for



				    	}//close for


							System.out.println("enter ");
							System.out.println("fsize :"+fsize);
							double TempCalc;
							int myChkVal;

						System.out.println("Check value : p : " + p + ", q : " + q + ", r : " + r);
						if(Bands!=1)
						{
							line_start = 0;
							for( i=0;i<fsize2;i++)
							{

				    		    pix_2[i]=255;

				    		    //for pix1
								if(pix_rgb2[p-1][2*i]<0)
									z=256+pix_rgb2[p-1][2*i];
								else
									z=pix_rgb2[p-1][2*i];

								if(pix_rgb2[p-1][2*i+1]<0)
									g=256+pix_rgb2[p-1][2*i+1];
								else
									g=pix_rgb2[p-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix1_2[i] = p1 | p2;

								myTempCalc = ((double)pix1_2[i] / (double)myMax) * 255.0;
								pix1_2[i] = (int)myTempCalc;

								// for pix2
								if(pix_rgb2[q-1][2*i]<0)
									z=256+pix_rgb2[q-1][2*i];
								else
									z=pix_rgb2[q-1][2*i];

								if(pix_rgb2[q-1][2*i+1]<0)
									g=256+pix_rgb2[q-1][2*i+1];
								else
									g=pix_rgb2[q-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix2_2[i] = p1 | p2;

								myTempCalc = ((double)pix2_2[i] / (double)myMax) * 255.0;
								pix2_2[i] = (int)myTempCalc;

								// for pix3

								if(pix_rgb2[r-1][2*i]<0)
									z=256+pix_rgb2[r-1][2*i];
								else
									z=pix_rgb2[r-1][2*i];

								if(pix_rgb2[r-1][2*i+1]<0)
									g=256+pix_rgb2[r-1][2*i+1];
								else
									g=pix_rgb2[r-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix3_2[i] = p1 | p2;

								myTempCalc = ((double)pix3_2[i] / (double)myMax) * 255.0;
								pix3_2[i] = (int)myTempCalc;

					    		    line_start=line_start+Width*2;
				    		}

				    		System.out.println("Displayed Image");

							raster.setSamples(0,origin_y,x_win,y_win2,0,pix1_2);
							raster.setSamples(0,origin_y,x_win,y_win2,1,pix2_2);
							raster.setSamples(0,origin_y,x_win,y_win2,2,pix3_2);
				 			raster.setSamples(0,origin_y,x_win,y_win2,3,pix_2);
				 			System.out.println("slice displayed1");
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

					    }
						else
						{

							System.out.println("slice displayed2");
						 	origin_x=0;
						 	origin_y=origin_y+y_win;

						}
						System.gc();




					}//close if(v==num_of_slice-1)

			  }	//end of slice v==-1


			}


			for( i=0;i<Bands;i++)
			{
				fin_band[i].close();
				fchan_in[i].close();
			}
		}
 }

            catch(Exception e)
            {
            	System.out.println(e);
            }

 			System.out.println("Hi");

 		return img;
 		}

	public int[] minArray()
	{
		return minBand;
	}

	public int[] maxArray()
	{
		return maxBand;
	}


}


class DialogDemo
{

}


///////////*************  DemoPanel Class To take Clicks On The Displayed Image **********************//////////////////////



class DemoPanel extends JPanel implements MouseMotionListener
{
	static BufferedImage Imag;
	private Dimension ViewSize;
	private picture Picture1;

	int x;
	int y;
	int width;
	int height;
	int bands;
	int maxindex, minindex;
	private DialogDemo message;
	public static int Noofclicks;

	public static int flag1=1;
	int click_x[]=new int[10000];
	int click_y[]=new int[10000];
	private calculate c1;
	ReadImageData imgdata;
    public static int Bands1;
   	int Width1;
   	int Height1;
	MappedByteBuffer mBuf;
	static double val1[][];
	public static int clicks=0;
	public static double xratios[]= new double[ReadImageData.Bands];
        public static  double yratio []=new double[DemoPanel.xratios.length];
	public static double  rowdata[]=new double[new ReadImageData().bands()];


	public DemoPanel()
	{
	}

	public BufferedImage getpixdata()
	{
		return Imag;
	}

	int return_x()
	{
		if (x >= 0)
			return x;
		else
			return -1;

	}

	int return_y()
	{
		if (y >= 0)
			return y;
		else
			return -1;
	}
	public int retclicks()
    {
    	return Noofclicks;
    }


	public DemoPanel (BufferedImage Img)
	{
	  System.gc();
	  this.Imag=Img;
	  int Width=Math.min(256,Imag.getWidth());
	  final int Height=Math.min(256,Imag.getHeight());
	  ViewSize=new Dimension(Width,Height);
	  setPreferredSize(new Dimension(Imag.getWidth(),Imag.getHeight()));

	  imgdata=new ReadImageData();
      Bands1=imgdata.bands();
  	  Width1=imgdata.columns();
  	  Height1=imgdata.rows();
  	  FileInputStream fin_band[]=new FileInputStream[Bands1];
      final FileChannel fchan_in[]=new FileChannel[Bands1];

	  try
	  {
	 	 for(int k=0; k<Bands1; k++)
 	  	 {
 			fin_band[k] = new FileInputStream("Data" + BilImage.name +  k);
 			fchan_in[k] = fin_band[k].getChannel();

 	     }
 	  }
 	  catch(Exception e)
 	  {
 		System.out.println("file Open Error in click : "+e);
 	  }
 	  val1=new double[Bands1][100];

	  addMouseListener(new MouseAdapter()
	   {
	       int val,i;
	       long start,len;
	       int arr[]=new int[Bands1];
	       double sum;

		   public void mouseClicked(MouseEvent evt)

		   {
                       if(BilImage.chek==true)
                       {
			   if(RegionGrow.ra1.isSelected()==true)
			   {
				   System.gc();
				   flag1=0;
		           	if(flag1!=0)
		           		flag1=JOptionPane.showConfirmDialog(null,"Do you want to save the Signature File");

		           	if(flag1==0)
		           	{
		                       int width=Imag.getWidth();
								int height=Imag.getHeight();
								int x=evt.getX();
								int y=evt.getY();

								if(evt.getClickCount()>0)
								{
								if(y > (height) || x > (width) || y < 0 || x < 0)
								{
								  clicks=0;
								  Noofclicks=0;
								  JOptionPane.showMessageDialog(null,"You clicked outside the image","Warning Message",JOptionPane.INFORMATION_MESSAGE);
								  flag1=1;
								}
								else                         ///getting all x,y coordinates clicked
								{
								 click_x[clicks]=x;
								 click_y[clicks]=y;

								 System.out.print("x : "+x);
								 System.out.println(" y : "+y);

								 x=(int)(x/BilImage.zoomFactor);
								 y=(int)(y/BilImage.zoomFactor);
								 System.out.print("After x : "+x);
								 System.out.println(" After y : "+y);
								 clicks=Noofclicks;
								 System.out.println("clicks : "+(Noofclicks+1));
								 try
								 {
									 byte b,b1;
									 	int temp,tempCount;
									 	int p1,p2;
									 	if(picture.shiftFlag==8)
									 	{
									 		for(i=0;i<Bands1;i++)
									 		{
									 			start=y*Width1+x;
									 			len=1;
									 			mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,start,len);
									 			b=mBuf.get();
									 			tempCount = (b<< 24) >>> 24;
									 		    val1[i][clicks]=tempCount;
									 			System.out.println("val on band : "+(i+1)+" = "+(double)tempCount);
									 		}

									 	}
									 	else
									 	{
									 		for(i=0;i<Bands1;i++)
									 		{
									 			start=(y*Width1+x)*2;
									 			len=2;
									 			mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,start,len);
									 			b=mBuf.get();
									 			b1=mBuf.get();
									 			p1 = b << 24;
												p2 = (b1 <<24 ) >>> 8;
												tempCount = p1 | p2;
												tempCount = tempCount >>> 16;
												val1[i][clicks]=tempCount;
									 			System.out.println("val on band : "+(i+1)+" = "+(double)tempCount);
									 		}

									 	}
								 		for(int z=0;z<Bands1;z++)
								 			xratios[z]=val1[z][clicks];

								 		RegionGrow.count=0;
										RegionGrow.count1=0;
							RegionGrow.windowmaker(x,y,Width1,Height1);
							RegionGrow. Bandratiocal();

								 	clicks++;
								 	Noofclicks++;

								}
								catch(Exception e)
								{
									System.out.println("Click error : "+e);
									e.printStackTrace();
								}
							   }
							  }
						  }
		           	flag1=0;
		             }
                       }
                       else if(BilImage.chek==false)
                       {

            	System.gc();
               if(flag1!=0)
            		flag1=JOptionPane.showConfirmDialog(null,"Do you want to save the clicks");

            	if(flag1==0)
            	{
                        int width=Imag.getWidth();
						int height=Imag.getHeight();
						int x=evt.getX();
						int y=evt.getY();
						if(BilImage.tab==0)
						{
		    				DemoPanel pane=new DemoPanel();
		   					imagetable6 Table6;
							System.out.println("Table Opened");
							int Noclicks=pane.retclicks();
							Table6=new imagetable6();
							Table6=new imagetable6(Noclicks);
							Table6.setSize(550,300);
							Table6.setVisible(true);
							BilImage.tab=1;
						}
						if(evt.getClickCount()>0)
						{
						if(y > (height) || x > (width) || y < 0 || x < 0)
						{
						  clicks=0;
						  Noofclicks=0;
						  if(message==null)
						  message=new DialogDemo();
						  JOptionPane.showMessageDialog(null,"You clicked outside the image","Warnning Message",JOptionPane.INFORMATION_MESSAGE);
						  flag1=1;
						}
						else                         ///getting all x,y coordinates clicked
						{
						 click_x[clicks]=x;
						 click_y[clicks]=y;

						 System.out.print("x : "+x);
						 System.out.println(" y : "+y);

						 x=(int)(x/BilImage.zoomFactor);
						 y=(int)(y/BilImage.zoomFactor);

						 clicks=Noofclicks;
						 System.out.println("clicks : "+(Noofclicks+1));
						 try
						 {
						 	byte b,b1;
						 	int temp,tempCount;
						 	int p1,p2;
						 	if(picture.shiftFlag==8)
						 	{
						 		for(i=0;i<Bands1;i++)
						 		{
						 			start=y*Width1+x;
						 			len=1;
						 			mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,start,len);
						 			b=mBuf.get();
						 			tempCount = (b<< 24) >>> 24;
						 		    val1[i][clicks]=tempCount;
						 			System.out.println("val on band : "+(i+1)+" = "+(double)tempCount);
						 		}
						 	}
						 	else
						 	{
						 		for(i=0;i<Bands1;i++)
						 		{
						 			start=(y*Width1+x)*2;
						 			len=2;
						 			mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,start,len);
						 			b=mBuf.get();
						 			b1=mBuf.get();
						 			p1 = b << 24;
									p2 = (b1 <<24 ) >>> 8;
									tempCount = p1 | p2;
									tempCount = tempCount >>> 16;
									val1[i][clicks]=tempCount;
						 			System.out.println("val on band : "+(i+1)+" = "+(double)tempCount);
						 		}
						 	}

						 	clicks++;
						 	Noofclicks++;

						}
						catch(Exception e)
						{
							System.out.println("Click error : "+e);
						}
					   }
					  }
				  }
              }
                       }


	     });

		addMouseMotionListener(this);
    }


          public void mouseDragged(MouseEvent evt){}
		  public void mouseMoved(MouseEvent evt)
          {
               // show coordinates
                 x = evt.getX();
                 y = evt.getY();

	           int width=Imag.getWidth();
			   int height=Imag.getHeight();
			   if(x>width)
			   		setToolTipText("");
			   else
			   		if(y>height)
			   			setToolTipText("");
			   		else
			   		{
	           			setToolTipText("R" + y + ",C" + x);
                 		repaint();
              		}
           }


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(Imag, 0, 0, this);
		g2.dispose();
	}


}

////////////////////**************** About Dialog Class **********************///////////////


class AboutDialog extends JDialog implements ActionListener
{
	static int Band1 = 4;
	static int Band2 = 4;
	static int Band3 = 4;
	private ReadImageData ImgData2;
	Container contentPane = getContentPane();
	JLabel pict, pict1, pict2;
	JTextField f1, f2, f3;
	JButton b1, b2;
	JComboBox list, list1, list2;
	int x, y, z;
	private boolean ok;

	public int show_band1()
	{
		return Band1;
	}

	public int show_band2()
	{
		return Band2;
	}

	public int show_band3()
	{
		return Band3;
	}

	public AboutDialog(JFrame parent)
	{
		super(parent, "Bands Chooser", true);
		ImgData2 = new ReadImageData();
		int i = ImgData2.bands();
		String str[] = new String[i];
		GridBagLayout g1;
		GridBagConstraints gbc;
		int k = i;

		for (int j = 0; j < i; j++)
		{
			str[j] = Integer.toString(k);
			k--;
		}

		b1 = new JButton("OK");
		b2 = new JButton("Cancel");
		list = new JComboBox(str);
		list1 = new JComboBox(str);
		list2 = new JComboBox(str);
		pict = new JLabel("Red");
		pict1 = new JLabel("Green");
		pict2 = new JLabel("Blue");

		f1 = new JTextField(5);
		f1.setBackground(Color.red);
		f1.setEnabled(false);
		f2 = new JTextField(5);
		f2.setBackground(Color.green);
		f2.setEnabled(false);
		f3 = new JTextField(5);
		f3.setBackground(Color.blue);
		f3.setEnabled(false);


		g1 = new GridBagLayout();
		contentPane.setLayout(g1);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 0;
		gbc.gridy = 0;
		g1.setConstraints(pict, gbc);
		contentPane.add(pict);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 2;
		gbc.gridy = 0;

		g1.setConstraints(f1, gbc);
		contentPane.add(f1);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 4;
		gbc.gridy = 0;

		g1.setConstraints(list, gbc);
		contentPane.add(list);

		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 6;//4
		gbc.gridy = 0;
		g1.setConstraints(pict1, gbc);
		contentPane.add(pict1);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 8;//6
		gbc.gridy = 0;

		g1.setConstraints(f2, gbc);
		contentPane.add(f2);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 10;
		gbc.gridy = 0;

		g1.setConstraints(list1, gbc);
		contentPane.add(list1);

		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 12;//8
		gbc.gridy = 0;
		g1.setConstraints(pict2, gbc);
		contentPane.add(pict2);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 14;//10
		gbc.gridy = 0;


		g1.setConstraints(f3, gbc);
		contentPane.add(f3);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 16;//12
		gbc.gridy = 0;

		g1.setConstraints(list2, gbc);
		contentPane.add(list2);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 2;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 3;
		gbc.gridy = 2;
		g1.setConstraints(b1, gbc);
		contentPane.add(b1);
		b1.addActionListener(this);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 2;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 6;
		gbc.gridy = 2;
		g1.setConstraints(b2, gbc);
		contentPane.add(b2);
		b2.addActionListener(this);
		dispose();
        }

	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();
		if (source == b1)//
		{
			String s = (String)list.getSelectedItem();
			String s1 = (String)list1.getSelectedItem();
			String s2 = (String)list2.getSelectedItem();
			int x = Integer.parseInt(s);
			this.Band1 = x;
			int y = Integer.parseInt(s1);
			this.Band2 = y;
			int z = Integer.parseInt(s2);
			this.Band3 = z;
			dispose();
		}
		else
		{
			dispose();
		}
	}
}


///////////////******************* Zoom In Class For Enlargement of The Image****************///////////////


class ZoomIn
 {
    private BufferedImage picture;
	private static int newsize=1;
	public ZoomIn ()
	{
	}
	public ZoomIn (BufferedImage Imag,int Enlargefactor)
	{
	  System.gc();
	  int w=Imag.getWidth()*Enlargefactor;
	  int h=Imag.getHeight()*Enlargefactor;
	  BufferedImage EnlargedImag=new BufferedImage(w,h,Imag.getType());
	  for(int y=0;y < h;y++)
	  	for(int x=0;x < w;x++)
	     EnlargedImag.setRGB(x,y,Imag.getRGB(x/Enlargefactor,y/Enlargefactor));
	  System.out.println(EnlargedImag.getWidth());
	  this.picture=EnlargedImag;
	  this.newsize=Enlargefactor;
    }

    public int retnewsize()
    {
     	return newsize;
    }
    public BufferedImage retchangepic()
	{
	    return picture;
    }
}

/***************** Shrinking The Image by Pixel Skipping***************************************/



 class ZoomOut
 {
    private BufferedImage picture;
	public ZoomOut(BufferedImage Imag,int Enlargefactor)
	{
	  System.gc();
	  int w=Imag.getWidth()/Enlargefactor;
	  int h=Imag.getHeight()/Enlargefactor;
	  BufferedImage EnlargedImag=new BufferedImage(w,h,Imag.getType());
	  for(int y=0;y < h;y++)
	  	for(int x=0;x < w;x++)
	     EnlargedImag.setRGB(x,y,Imag.getRGB(x*Enlargefactor,y*Enlargefactor));
	  this.picture=EnlargedImag;
     }
	 public BufferedImage retchangepic()
	 {
	    return picture;
     }

}


 //////////////////********** Calculate Class For Calculating Error Margin *************///////////////


class calculate
{

	static Vector pixelValue=new Vector();   				//vector for capture pixel value

	private DemoPanel demo;
	private BufferedImage image;
	private ReadImageData imgdat11;
    static int Bands;
    static int Width;
    static int Height;


    Double pixelArray[];
    static double pixel_Array[];		//arrary for geting vector values
    static int[]xy=new int[2];                  //for tranfering x,y coordinate for accuracy assesment


	int bands;
	private ZoomIn zoom1;
	private static int newsize=1;
	private ReadImageData imgdat;
	static double bandsdata[]=new double[Bands];
	static int[][][] pixvalue=new int[Bands][Width][Height];
	public calculate()
	{
	}
	public calculate(int a[][][])
	{
	  imgdat=new ReadImageData();
	  this.Bands=imgdat.bands();
      this.Width=imgdat.columns();
      this.Height=imgdat.rows();
      this.pixvalue=a;
	  double bandsdat[]=new double[Bands];
	  this.bandsdata=bandsdat;
	  for(int i=0;i<Bands;i++)
	    bandsdata[i]=0.00000000000;
	}

	public  calculate(int x,int y)
    {
	  imgdat11=new ReadImageData();
	  int bands=imgdat11.bands();
	  this.Bands=imgdat11.bands();
	  zoom1=new ZoomIn();
	  this.newsize=zoom1.retnewsize();
	  int newx=(int)(x/newsize);
	  int newy=(int)(y/newsize);
	  double banddata[]=new double[bands];
      xy[0]=newx;
      xy[1]=newy;

	  for(int i=0;i<Bands;i++)
	    banddata[i]=pixvalue[i][newy][newx];			//for tranfring value to acuracy class


	// code for storing pixel values

	try
	{


	   for(int i=0;i<Bands;i++)
	   {
	    	Double temp=new Double(banddata[i]);
	   		pixelValue.addElement(temp);                     			        //adding elements to vector array
	   }

       int l=pixelValue.size();
       pixelArray=new Double[l];
       pixel_Array=new double[l];
       pixelValue.copyInto(pixelArray);

       for(int m=0;m<l;m++)
       {

       	pixel_Array[m]=pixelArray[m].doubleValue();     //converting double object to primitive double**/
       	System.out.println(pixel_Array[m]);
       }

	}
	catch(Exception e)
	{
		System.out.println("UMA TYAGI"+  e);
	}
		for(int d=0;d<Bands;d++)
	   		this.bandsdata[d]=banddata[d]+this.bandsdata[d];
  }


  public double[] retbandsdata()// return the total of pixel values of coordinates clicked
  {
    return bandsdata;
  }


 public int[][][] retpix()      /** return the values of pixvalues matrix **/
  {
    return pixvalue;
  }

 public int[]retxy()            // to return x,y coordinate
  {
    return xy;
  }



  public double[] ret_pixelArray()			// function to return pixel values
  {
  	return pixel_Array;
  }

  public void remove()                          // function to remove vector elements
  {
    int i=pixelValue.size();

    for(int j=0;j<i;j++)
    {
    	Double t=new Double(pixel_Array[j]);

    	pixelValue.removeElement(t);

    }
  }


  /************* Function For Calculating Error Function *********************************************/

  public  double erf(double z)
  {
        double t = 1.0 / (1.0 + 0.5 * Math.abs(z));

        // use Horner's method
        double ans = 1 - t * Math.exp( -z*z   -   1.26551223 +
                                            t * ( 1.00002368 +
                                            t * ( 0.37409196 +
                                            t * ( 0.09678418 +
                                            t * (-0.18628806 +
                                            t * ( 0.27886807 +
                                            t * (-1.13520398 +
                                            t * ( 1.48851587 +
                                            t * (-0.82215223 +
                                            t * ( 0.17087277))))))))));
        if (z >= 0) return  ans;
        else        return -ans;
    }

}




//////////***************** Class For Enhancing The Apperance of The Image. ***************////////////////


class Enhancement
 {
  private ReadImageData imgdata;
  private calculate cal;
  public File fi;
  BufferedImage img;
  int [][][] pixeldata;
  double [][][] pixeldata1;
  int Bands;
  int Height;
  int Width;
  picture pict=new picture();

  public Enhancement()
   {}

  public Enhancement(int i,int j,int k)
   {
     pict=new picture();
     img=pict.getpicdata(i,j,k);
   }

 public void enhances(int col1,int col2,int col3)
  {int tRow=0;
  	try{
  		tRow=new ReadImageData().rows();
  	System.out.println("tRow="+tRow);
  }
  catch(Exception e)
  {
  	System.out.println("Ex:"+e);
  }
  if(true)
  {
   cal=new calculate();
   imgdata=new ReadImageData();
   double min=0;
   double max=0;
   int p=col1;
   int q=col2;
   int r=col3;
   int index=0;
   Bands=imgdata.bands();
   Width=imgdata.columns();
   Height=imgdata.rows();
   int pix[] = new int [(Width)*(Height)];
   int pix1[] = new int [(Width)*(Height)];
   int pix2[] = new int [(Width)*(Height)];
   int pix3[] = new int [(Width)*(Height)];
   if(Bands!=1)
   pixeldata1=new double[Bands][Height][Width];
   else
   pixeldata1=new double[3][Height][Width];
   pixeldata=new int[Bands][Height][Width];
   pixeldata=cal.retpix();
   MappedByteBuffer mBuf;
   long fsize=Height*Width;
   FileChannel fchan_in[]=new FileChannel[Bands];
   byte [][]pix_rgb=new byte[Bands][(int)fsize];
   byte [][]pix_rgb1=new byte[Bands][(int)fsize*2];
   FileInputStream fin_band[]=new FileInputStream[Bands];

 		try
 		{

 			for(int i=0;i<Bands;i++)
 			{
 				fin_band[i] = new FileInputStream("Data" + BilImage.name +  i);
 				fchan_in[i] = fin_band[i].getChannel();
 			}

 		}
 		catch(Exception e)
 		{
 			System.out.println("Ex:"+e);
 		}
		int temp,tempCount,p1,p2;;
		int i=0,j,k,x,l;
		int r1=(BilImage.p)-1;
		int g1=(BilImage.q)-1;
		int b1=(BilImage.r)-1;
 		int b[][]=new int[Bands*Height][Width];
 		int a[][][];
 		if(Bands!=1)
 		 a=new int [Bands][Height][Width*2];
 		else
 		 a=new int [3][Height][Width*2];
		try
		{
		     if(Bands!=1)
		     {
			 if(picture.shiftFlag==8)
			 {


				mBuf=fchan_in[r1].map(FileChannel.MapMode.READ_ONLY,0,fsize);
				mBuf.get(pix_rgb[i]);
				for(j=0;j<Height;j++)
					for(k=0;k<Width;k++)
					{
						temp=(int)pix_rgb[i][j*(Width)+k];
						if(temp<0)
							a[0][j][k]=255+(int)pix_rgb[i][j*(Width)+k];
						else
							a[0][j][k]=(int)pix_rgb[i][j*(Width)+k];
					}


				mBuf=fchan_in[g1].map(FileChannel.MapMode.READ_ONLY,0,fsize);
				mBuf.get(pix_rgb[i]);
				for(j=0;j<Height;j++)
					for(k=0;k<Width;k++)
					{
						temp=(int)pix_rgb[i][j*(Width)+k];
						if(temp<0)
							a[1][j][k]=255+(int)pix_rgb[i][j*(Width)+k];
						else
							a[1][j][k]=(int)pix_rgb[i][j*(Width)+k];
					}



				mBuf=fchan_in[b1].map(FileChannel.MapMode.READ_ONLY,0,fsize);
				mBuf.get(pix_rgb[i]);
				for(j=0;j<Height;j++)
					for(k=0;k<Width;k++)
					{
						temp=(int)pix_rgb[i][j*(Width)+k];
						if(temp<0)
							a[2][j][k]=255+(int)pix_rgb[i][j*(Width)+k];
						else
							a[2][j][k]=(int)pix_rgb[i][j*(Width)+k];
					}
				System.gc();

			}
			else
			{	System.out.println("bigin");
				System.gc();
				mBuf=fchan_in[r1].map(FileChannel.MapMode.READ_ONLY,0,fsize*2);
				mBuf.get(pix_rgb1[0]);
				for(j=0;j<Height-1;j++)
					for(k=0;k<Width*2;k++)
					{
						p1 = pix_rgb1[0][2*(j*Width+k)] << 24;
						p2 = (pix_rgb1[0][2*(j*Width+k)+1] <<24 ) >>> 8;
						tempCount = p1 | p2;
						tempCount = tempCount >>> 16;
						a[0][j][k]=tempCount;
					}

				mBuf=fchan_in[g1].map(FileChannel.MapMode.READ_ONLY,0,fsize*2);
				mBuf.get(pix_rgb1[0]);
				for(j=0;j<Height-1;j++)
					for(k=0;k<Width*2;k++)
					{
						p1 = pix_rgb1[0][2*(j*Width+k)] << 24;
						p2 = (pix_rgb1[0][2*(j*Width+k)+1] <<24 ) >>> 8;
						tempCount = p1 | p2;
						tempCount = tempCount >>> 16;
						a[1][j][k]=tempCount;
					}

				mBuf=fchan_in[b1].map(FileChannel.MapMode.READ_ONLY,0,fsize*2);
				mBuf.get(pix_rgb1[0]);
				for(j=0;j<Height-1;j++)
					for(k=0;k<Width*2;k++)
					{
						p1 = pix_rgb1[0][2*(j*Width+k)] << 24;
						p2 = (pix_rgb1[0][2*(j*Width+k)+1] <<24 ) >>> 8;
						tempCount = p1 | p2;
						tempCount = tempCount >>> 16;
						a[2][j][k]=tempCount;
					}
				System.out.println("end");
			}
		}
		else
		{
		    FileInputStream fin=new FileInputStream("Data"+BilImage.name+"0");///change
		    FileChannel fchan_in1[]=new FileChannel[3];
            fchan_in1[0]=fin.getChannel();
            fchan_in1[1]=fin.getChannel();
            fchan_in1[2]=fin.getChannel();

            i=0;
            mBuf=null;
            try
            {
			mBuf=fchan_in1[0].map(FileChannel.MapMode.READ_ONLY,0,fsize);

			mBuf.get(pix_rgb[i]);

			for(j=0;j<Height;j++)
				for(k=0;k<Width;k++)
				{
					temp=(int)pix_rgb[i][j*(Width)+k];
					if(temp<0)
						a[0][j][k]=255+(int)pix_rgb[i][j*(Width)+k];
					else
						a[0][j][k]=(int)pix_rgb[i][j*(Width)+k];
				}
				max=pict.maxBand[0];
				min=pict.minBand[0];
				for(int i1=0;i1<3;i1++)
					for(j=0;j<Height;j++)
     	   				for( k=0;k<Width;k++)
        	   				pixeldata1[i1][j][k]=((a[0][j][k]-min)/(max-min))*255;
           	}
			catch(Exception e)
			{
				System.out.println("1 Band Enhance Error : "+e);
			}

			System.gc();

			}

	}
	catch(Exception e)
	{
		System.out.println("Exception1 : "+e);
	}

// Finding max & min of displaying Bands.

	if(Bands!=1)
	{
	 for(i=0;i<3;i++)
	 {
	 	if(i==0)
	 	{
	 		max=pict.maxBand[r1];
	 		min=pict.minBand[r1];
	 	}
	 	if(i==1)
	 	{
	 		max=pict.maxBand[g1];
	 		min=pict.minBand[g1];
	 	}
	 	if(i==2)
	 	{
	 		max=pict.maxBand[b1];
	 		min=pict.minBand[b1];
	 	}

	 	for(j=0;j<Height-1;j++)
     	   for( k=0;k<Width;k++)
        	   pixeldata1[i][j][k]=((a[i][j][k]-min)/(max-min))*255;
	 }
	}

	for(i=0;i<3;i++)
	{
		min=pixeldata1[i][0][0];
		max=pixeldata1[i][0][0];

		for(j=0;j<Height-1;j++)
		{
			for(k=0;k<Width;k++)
			{
				if(min>pixeldata1[i][j][k])
					min=pixeldata1[i][j][k];

				if(max<pixeldata1[i][j][k])
					max=pixeldata1[i][j][k];
			}
		}

	}

    for(i=0;i<Height;i++)
    {
	    for ( j=0;j<Width;j++)
	    {
			 pix[index]=255;
		     pix1[index]= (int)pixeldata1[0][i][j];
			 pix2[index]= (int)pixeldata1[1][i][j];
			 pix3[index]= (int)pixeldata1[2][i][j];
			 index++;
		}
    }

	img=new BufferedImage(Width,Height,BufferedImage.TYPE_INT_ARGB);
	WritableRaster raster=img.getRaster();
	raster.setSamples(0,0,Width,Height,0,pix1);
	raster.setSamples(0,0,Width,Height,1,pix2);
	raster.setSamples(0,0,Width,Height,2,pix3);
	raster.setSamples(0,0,Width,Height,3,pix);
    }

	System.out.println("Image Enhanced.");

  }


  public BufferedImage getpicdata()
  {
	  return img;
  }
}


 /* ***********************   Class For Saving Image Clicks     **********************************/


class imagetable6 extends JFrame implements ActionListener
{
  static Vector pixels=new Vector();
  static public double c[][];
  Double[]pixelsarray;
  double[]pixels_array;
  int len;
  static int row=0;
  int preclicks=0,saveclicks;
  static double cw;
  int[][]pixelstore;
  private ReadImageData imgdata8=new ReadImageData();
  int bands=imgdata8.bands();
  static Vector varienceValue=new Vector();  //for storing variances
  Double varienceArray[];
  static double varience_Array[];
  static Vector avarageValue=new Vector();   //for storing avarage vlues
  Double avarageArray[];
  static double avarage_Array[];
  static double old_avg[][];
  double pixel[][];
  double[][] varm=new double[bands][bands];
  double[] av=new double[bands];                                               		/**array to store final values of co-varience matrix**/
  double p_array[];
  static int state=2;
  int cls=0;
  int row1=0;
  int flag; 			                            /**for storing the returned values from ret_matrixArray of calculate class**/
  static DefaultTableModel model;
  private JTable table;
  JMenuItem openitem;
  JMenuBar menubar;
  int click;
  static File tablefile;
  TableDialog  tablemessage;
  tabledata td=new tabledata();
  DemoPanel pane=new DemoPanel();
  static int Bands;
  String columnname[]=new String[24];
  static double rowdat[]=new double[Bands];
  static ArrayList a1=new ArrayList();
  static ArrayList a2=new ArrayList();
  ArrayList a3=new ArrayList();
  static ArrayList a4=new ArrayList();
  calculate c1=new calculate();
  private addfiledata afd;

  public imagetable6()
  {
  }

  static int rownum=0;
  static Object data[]=new Object[20];
  static Object rowdata[]=new Object[20];
  static Object cname[]=new Object[20];  ;
  int siz;
  static int tablerows;


  JButton b=new JButton("Add");
  JButton b1=new JButton("Cal.Stat.");
  JButton b2=new JButton("Save Sig.");

  JTextField t1=new JTextField(2);
  JRadioButton  box1=new JRadioButton("D Matrix",false);
  JRadioButton  box2=new JRadioButton("V-C Matrix",true);
  JRadioButton  box3=new JRadioButton("D.V-C Matrix",false);

  public imagetable6(int n)
  {

    setTitle("Table For Fuzzy C-Mean's Signature File");
	setSize(500,400);

	addWindowListener(new WindowAdapter()
	{
	  public void WindowClosing(WindowEvent e)
	  {
	    System.exit(0);
	  }
	});

    JMenu fmenu=new JMenu("File");
	fmenu.setMnemonic(KeyEvent.VK_F);
	openitem=new JMenuItem("Open Sig.File");
	openitem.setMnemonic(KeyEvent.VK_O);
	openitem.addActionListener(this);
	fmenu.add(openitem);
	menubar= new JMenuBar();
	menubar.add(fmenu);
	setJMenuBar(menubar);
	afd=new addfiledata();
	this.tablerows=afd.retnewsize();
	System.out.println(tablerows);
	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	b.addActionListener(this);
	b1.addActionListener(this);
      b2.addActionListener(this);


    box1.addActionListener(this);
    box2.addActionListener(this);
    box3.addActionListener(this);

    ButtonGroup bg=new ButtonGroup();
    bg.add(box1);
    bg.add(box2);
    bg.add(box3);


	int bands;
	model=new DefaultTableModel(10000,0);
	data[0]= new String("Class");
	this.Bands=imgdata8.bands();
	bands=imgdata8.bands();
    model.addColumn(data[0]);

	for(int i=1;i<=Bands;i++)
	{
	 	data[i]=new String("Band")+ new Integer(i);
	 	model.addColumn(data[i]);
	}

	table=new JTable(model);
	Container contentpane=getContentPane();
	p1.setSize(100,150);
	p1.add(new JScrollPane(table),"Center");
	contentpane.add(p1,"Center");
	p2.add(b);
	p2.add(b2);
    p2.add(b1);
    p2.add(box1);
    p2.add(t1);
    p2.add(box2);
    p2.add(box3);
    t1.setText("2");
	contentpane.add(p2,"South");
  }



    public  Object[] retclassname()
     {

          return cname;
     }


   public double[][]retoldavarage()
    {
     return old_avg;
    }

    public double[] retvarianceArray()			// function to return varience values
    {
  	return varience_Array;
    }

    public double[] retavarageArray()			// function to return avarage values
    {
  	return avarage_Array;
    }

  public void remove()                          // function to remove vector elements
   {
    int i=varienceValue.size();
    for(int j=0;j<i;j++)
       {
        Double t=new Double(varience_Array[j]);

        varienceValue.removeElement(t);

        }
  }


  public void remove1()                          // function to remove vector elements
  {
    int i=avarageValue.size();
    for(int j=0;j<i;j++)
       {
        Double t=new Double(avarage_Array[j]);

        avarageValue.removeElement(t);

        }
  }
 double pixeldata[];

 public void helpaccuracy(double[]pix)          //Function For Calculating Stat Again For Subpixel Acc assesment
     {
                          int bands=imgdata8.bands();


                         int pixlength=pix.length;
                         pixeldata=new double[pixlength];
                         int l=pixlength/bands;
                         double[][] pixel=new double[l][bands];

                         this.pixeldata=pix;
//**********here is the logic to devlop the varience-covariance matrix**************

 			    double[][]varm=new double[bands][bands];
                      double[]av=new double[bands];
                      int k11=0;
                      for(int i=0;i<l;i++)
                         for(int j=0;j<bands;j++)
                               {
                               pixel[i][j]=pixeldata[k11];
                               k11=k11+1;
                               }




                        for(int i=0;i<pixlength;i++)
                             pixeldata[i]=0;


int p1=pixel.length;
int p2=p1-1;

for(int i=0;i<pixel[0].length;i++)
           av[i]=0;
      for(int i=0;i<pixel[0].length;i++)
          for(int j=0;i<pixel[0].length;i++)
                 varm[i][j]=0;

       for(int j=0;j<pixel[0].length;j++)
         for(int i=0;i<pixel.length;i++)
             av[j]=av[j]+pixel[i][j];

        for(int i=0;i<pixel[0].length;i++)
              av[i]=av[i]/p1;


       for(int k=0;k<pixel[0].length;k++)
         for(int j=0;j<pixel[0].length;j++)
           for(int i=0;i<pixel.length;i++)
               varm[k][j]=varm[k][j]+((pixel[i][k]-av[k])*(pixel[i][j]-av[j]));

           for(int i=0;i<pixel[0].length;i++)
                   for(int j=0;j<pixel[0].length;j++)
               varm[i][j]=varm[i][j]/(p2*p1);
           System.out.println("avarage is:");
       for(int i=0;i<pixel[0].length;i++)
           {
           System.out.print(av[i]);
           System.out.print("      ");
           }
       System.out.println("  ");



     if(state==2)
        {
         for(int i=0;i<bands;i++)
             for(int j=0;j<bands;j++)
               {
                if(i==j)
                   varm[i][j]=cw;
                 else
                   varm[i][j]=0;
               }
        }

      if(state==3)
        {
         for(int i=0;i<bands;i++)
             for(int j=0;j<bands;j++)
               {
                if(i!=j)
                 varm[i][j]=0;
               }
        }



       System.out.println("varience is:");
       for(int i=0;i<pixel[0].length;i++)
       {
        System.out.println("  ");

        System.out.println("  ");

         for(int j=0;j<pixel[0].length;j++)
            {
            System.out.print(varm[i][j]);
            System.out.print("  ");
            }
        }

         c1.remove();   // for removing the vector elements




     for(int i=0;i<pixel[0].length;i++)
          {
         for(int j=0;j<pixel[0].length;j++)
	      {
	      Double temp=new Double(varm[i][j]);
	      varienceValue.addElement(temp);
             			        //adding elements to vector array
	      }
           }


         for(int i=0;i<pixel[0].length;i++)
           {
             Double temp=new Double(av[i]);
             avarageValue.addElement(temp);
            }


          int l1=varienceValue.size();
          varienceArray=new Double[l1];
          varience_Array=new double[l1];
          varienceValue.copyInto(varienceArray);

         for(int m=0;m<l1;m++)
           {

       	varience_Array[m]=varienceArray[m].doubleValue();     //converting double object to primitive double**/

           }

          int l2=avarageValue.size();
          avarageArray=new Double[l2];
          avarage_Array=new double[l2];
          avarageValue.copyInto(avarageArray);

	     for(int m=0;m<l2;m++)
           {

       	avarage_Array[m]=avarageArray[m].doubleValue();     //converting double object to primitive double**/

           }

           int l3=l2/bands;

           old_avg=new double[l3][bands];
           int k12=0;
		   for(int i=0;i<l3;i++)
		       for(int j=0;j<bands;j++)
		            {
		            old_avg[i][j]=avarage_Array[k12];
		            k12=k12+1;
                        }

          c1.remove();

               for(int i=0;i<pixel[0].length;i++)
                   for(int j=0;j<pixel[0].length;j++)   //for removing previous values
                      varm[i][j]=0;

              for(int i=0;i<pixel.length;i++)
                   for(int j=0;j<pixel[0].length;j++)
                         pixel[i][j]=0;

  }

 public double[][] retmat()
 {
	 return c;

 }

  int empty1=0;// // for storing avarage in table

  public void actionPerformed(ActionEvent event)
  {
    Object source=event.getSource();

       if(source==box1)
           {
            state=1;
           }

       if(source==box2)
           {
            state=2;
           }



       if(source==box3)
           {
            state=3;
           }


      if (source==b)
	  {
	  	int i,j;
	  	int col=1;
	  	double sum;
	  	System.out.println("preclicks="+preclicks);
	  	System.out.println("Noofclicks="+DemoPanel.Noofclicks);
		for( j=preclicks;j<DemoPanel.Noofclicks;j++)
			for(i=0;i<new ReadImageData().bands();i++)
				System.out.println("Click="+j+" Band= "+i+" Value= "+DemoPanel.val1[i][j]);

	    for(i=0;i<new ReadImageData().bands();i++)
		{
			sum=0;
			for( j=preclicks;j<DemoPanel.Noofclicks;j++)
				sum+=DemoPanel.val1[i][j];
			System.out.println("Sum of clicks of Band "+(i+1)+" : "+sum);
			model.setValueAt((double)sum/(DemoPanel.Noofclicks-preclicks),row,col++);

		}
		saveclicks=preclicks;
		preclicks=DemoPanel.clicks;
		DemoPanel.clicks=0;
		row++;

      }

      else if (source==b2)


	  {
        /*************************** new image storing in files *************************/
          int temp,temp1,temp2;
          byte b1,b2;
          int tt;
          File file=null;
		  JFileChooser chooser=new JFileChooser();
          int r=chooser.showSaveDialog(this);
          if(r==JFileChooser.APPROVE_OPTION)
	      {
	       	file = chooser.getSelectedFile();

    		 try
     		 {
  				String name=file.toString();
   				System.out.println(name);
   				File ff=new File(name);
	            FileOutputStream fout=new FileOutputStream(name);
				for( int j2=saveclicks;j2<DemoPanel.Noofclicks;j2++)
	            {
	                  for(int k1=0;k1<new ReadImageData().bands();k1++)
		              {

                            temp= (int)DemoPanel.val1[k1][j2];
                            temp1=temp>>>8;
                            fout.write(temp1);
                            fout.write(temp);
                      }
                }
                DemoPanel.clicks=0;
                DemoPanel.Noofclicks=0;
                preclicks=0;
                saveclicks=0;

	      		fout.close();
     		 }
     		 catch(IOException e)
             {
                System.out.println("Exception is :"+ e);
             }
		  }

	  }


	  else if (source==b1)
      {

		     if(flag==0)
		     {
		  		len=len-1;
		  		flag=1;
		   	 }
	         int bands=imgdata8.bands();
	         int l=len/bands;
	         double[][] pixel=new double[bands][l];
		   	 c= new double[bands][bands];
			 double a[][]=new double[bands][l];
			 double b[]=new double[bands];

			 double sum;

			 System.out.println("Image Bands : "+bands);
			 System.out.println("No of Clicks : "+l);
			 for(int j=0;j<l;j++)
			 	for(int i=0;i<bands;i++)
			 		a[i][j]=pixels_array[j*bands+i];

			for(int i=0;i<bands;i++)
			{
				sum=0;
				for(int j=0;j<l;j++)
					sum+=a[i][j];
				b[i]=sum/l;
			}

			for(int i=0;i<bands;i++)
			{
				for(int j=0;j<bands;j++)
				{
					sum=0;
					for(int k=0;k<l;k++)
					{
						sum=sum+((a[j][k]-b[j])*(a[i][k]-b[i]));
					}
					c[i][j]=sum/(l-1);
				}
			}


			if(state==1)
			{
				System.out.println("The Unit Matrix is =");
				for(int i=0;i<bands;i++)
				{
					System.out.println();
					for(int j=0;j<bands;j++)
					{
						if(i==j)
							System.out.print("\t"+t1.getText());
						else
							System.out.print("\t"+0);
					}
				}
			}
			if(state==2)
			{
				System.out.println("Varience Co-Varience Matrix is =");
				for(int i=0;i<bands;i++)
				{
					System.out.println();
					for(int j=0;j<bands;j++)
					{
						System.out.print("\t"+c[i][j]);
					}
				}
			}
			if(state==3)
			{
				System.out.println("Diagonal Varience Co-Varience Matrix is =");
				for(int i=0;i<bands;i++)
				{
					System.out.println();
					for(int j=0;j<bands;j++)
					{
						if(i==j)
							System.out.print("\t"+c[i][j]);
						else
							System.out.print("\t"+0);
					}
				}
			}
			System.out.println();
			for(int i=0;i<bands;i++)
				 model.setValueAt(b[i],row,i+1);
				row++;

      }

	////////////////// To open a Signature File. ///////////////////////////

	 else if(source==openitem)
	 {

		int b,c1;
        JFileChooser chooser=new JFileChooser();
        int r=chooser.showOpenDialog(this);


        if(r==JFileChooser.APPROVE_OPTION)
		{
			 File file = chooser.getSelectedFile();
		     String name=file.getName();

			 System.gc();
			 validate();

             try
    		 {
                  FileInputStream f1=new FileInputStream(file);




                  try
                  {
                   		int c=1;
						while(c!=-1)
						{

					        b=f1.read();
					        if(b==-1)
					         break;
                            b=b<<8;
                            c1=f1.read();
                            Double temp=new Double(b|c1);
                            pixels.addElement(temp);


                        }

                   }
                   catch(Exception e)
                   {
                   		System.out.println("Hellow Uma"+e);
                   }
				   try
				   {
	                   f1.close();
					   System.gc();
				   }
				   catch(IOException e)
				   {
				   		System.out.println("Hellow Uma"+e);
				   }
             }
             catch(Exception e)
             {
             	System.out.println("Hellow Tyagi"+e);
             }

             len=pixels.size();
             pixelsarray=new Double[len];
             pixels.copyInto(pixelsarray);
             //len=len-1;
             pixels_array=new double[len];

	         for(int m=0;m<len;m++)
	         {

	       		pixels_array[m]=pixelsarray[m].doubleValue();     //converting double object to primitive double**/
	       		System.out.println(pixels_array[m]);
	         }


	      	for(int j=0;j<len;j++)
	        {
	       		 Double t=new Double(pixels_array[j]);               // for removing pixel values
	        	 pixels.removeElement(t);
	        }

	    }
	    flag=0;

     }

  }
}




/*********************************** Class AddFileData **********************************************/


 class addfiledata
{
       ArrayList a4=new ArrayList();
	   ArrayList a5=new ArrayList();
	   ArrayList a6=new ArrayList();
	   ArrayList a7=new ArrayList();
	   ArrayList a8=new ArrayList();
	   static File tablefile;
	   static int tablesize;
	   static ArrayList filedata=new ArrayList();
	   public addfiledata()
	   {
	   }
	   public addfiledata(File file)
	   {
	     this.tablefile=file;
         try
		  {
		    BufferedReader in=new BufferedReader(new FileReader(file));
			String s;
		    while((s=in.readLine())!=null)
		     {
    		    StringTokenizer t=new StringTokenizer(s,"\t");
			    a4.add(a4.size(),t.nextToken());
				a5.add(a5.size(),t.nextToken());
				a6.add(a6.size(),t.nextToken());
				a7.add(a7.size(),t.nextToken());
				a8.add(a8.size(),t.nextToken());
		    }
			in.close();
		} catch(IOException e)
			 {
				System.out.println("ERROR" +e);
				System.exit(1);
             }
			 this.tablesize=a4.size();
			 for(int i=1;i<a4.size();i++)
			 {
			   filedata.add(filedata.size(),a4.get(i));
			   filedata.add(filedata.size(),a5.get(i));
			   filedata.add(filedata.size(),a6.get(i));
			   filedata.add(filedata.size(),a7.get(i));
			   filedata.add(filedata.size(),a8.get(i));
              }
    }
	public int retnewsize()
	{
	  return tablesize;
    }
	public ArrayList retfiledata()
	{
	   return filedata;
    }
}

 /********************************** Table Message *********************************************/



class TableDialog extends JFrame
{
		 JFrame frame;
		 public TableDialog(){}
         public TableDialog(boolean val) {
         this.frame = frame;
         JOptionPane.showMessageDialog(frame,"You should enter the Name of place.");
		 }
}


/****************** This Class Contains The Data to be added to The Table**********************/


class  tabledata
{
    private calculate c1=new calculate();
    static int Bands;
	static double rowdata1[]=new double[Bands];
	private ReadImageData imgdata9=new ReadImageData();
    static double rowdata2[]=new double[Bands];
	static double temp[]=new double[20];

	public tabledata()
	{
	this.Bands=imgdata9.bands();
	double rowdata3[]=new double[Bands];
	this.rowdata2=rowdata3;
	this.rowdata1=rowdata2;
    }

	public double[] rettabledata()
	{
	   this.rowdata1=c1.retbandsdata();
	   for(int i=0;i<Bands;i++)
	   {
	    this.rowdata2[i]=this.rowdata1[i]-this.temp[i];
		this.temp[i]=this.rowdata1[i];
       }
       return rowdata2;
    }
}



///////////////************** Class for Classing The Pixels Of Image ****************///////////////////



class Fcm extends JFrame implements ActionListener
{
  static Vector pixels=new Vector();
  static double x[];
  static double xnew[][];
  float m,del,lem,neo,beta,gama;
  double sub[][];
  double subT[][];
  public double c[][][];

  double meu[][];

  double meun[][];
  double meunm[][];
  double prob[];
  Double[]pixelsarray;
  double[]pixels_array;
  int len;
  int row=0;
  static int count=0;
  int preclicks=0,saveclicks;
  static double cw;
  int[][]pixelstore;
  private ReadImageData imgdata8=new ReadImageData();
  int bands=imgdata8.bands();
  static Vector varienceValue=new Vector();  //for storing variances
  Double varienceArray[];
  static double varience_Array[];
  static Vector avarageValue=new Vector();   //for storing avarage vlues
  Double avarageArray[];
  static double avarage_Array[];
  static double old_avg[][];
  double pixel[][];
  double[][] varm=new double[bands][bands];
  double[] av=new double[bands];                                               		/**array to store final values of co-varience matrix**/
  double p_array[];
  static int state=2;
  static int typ=1;
  static int st=2;
  static int classi=1;
  static int advclassi=0;
  public static double ma=0.0,mi=99.0;
  public static double minin=999;
  static int sa;
  static int context=0;
  int cls=0;
  int row1=0;
  int flag;			                            /**for storing the returned values from ret_matrixArray of calculate class**/
  private DefaultTableModel model;
  private JTable table;
  public static double xratios[]= new double[ReadImageData.Bands];
        public static  double yratio []=new double[DemoPanel.xratios.length];
  JMenuItem openitem;
  JMenuItem openkitem[]=new JMenuItem[24];
  JMenuItem openk2item[]=new JMenuItem[24];

  JMenu sel_distance=null;
  JMenu sel_2distance=null;

   static String distances[] = {"Euclidean","V-C","D.V-C","Manhattan","ChessBoard","Mean-Absolute-Difference","Canberra","BrayCurtis","Cosine","Correlation","Normalized-Squared-Euclidean","Median-Absolute-Difference","Sid-Sam-Tan","Sid-Sam-Sin","Sid","Tan","Sin","Sid-Plus-Tan","Sid-Plus-Sin","Sca","Sca-Tan","Sca-Sin","Sid-Sca-Tan","Sid-Sca-Sin"};
   double mv_distances[]={2.1, 2.0, 3.0, 1.01, 1.16, 1.1, 1.05, 1.35, 1.03, 1.7, 2.2, 1.8,1.0};
   static String classifier_chk[]={"Fuzzy C-Means","Noise Clustering Without Entropy","Noise Clustering With Entropy","FCM With Entropy","Possibilistic C-Means","Modified Possibilistic C-Means","Improved Possibilistic C-Means","Maximum Likelihood Classifier","Fuzzy C-Means S","Possibilistic C-Means"};
   static String adv_classifier_chk[]={"FLICM","ADFLICM","ADPLICM","PLICM"};
  JMenuBar menubar;
  int click;
  static File tablefile;
  TableDialog  tablemessage;
  tabledata td=new tabledata();
  DemoPanel pane=new DemoPanel();
  static int Bands;
  String columnname[]=new String[24];
  static double rowdat[]=new double[Bands];
  static ArrayList a1=new ArrayList();
  static ArrayList a2=new ArrayList();
  ArrayList a3=new ArrayList();
  static ArrayList a4=new ArrayList();
  calculate c1=new calculate();
  private addfiledata afd;

  static int rownum=0;
  static Object data[]=new Object[300];
  static Object rowdata[]=new Object[20];
  static Object cname[]=new Object[20];
  double mean[][];
  double mul[][];
   double [][] takenvalues=new double[24][bands];


  int siz;
  static int tablerows;

  JButton btClasi=new JButton("Classify");
  Font f=new Font("Arial",Font.BOLD,18);
  JButton btsyn=new JButton("Synthetic Image");
   Font fo=new Font("Arial",Font.BOLD,18);


  JRadioButton  box1=new JRadioButton("Type I  ",true);
  JRadioButton  box2=new JRadioButton("Type II",false);
   JRadioButton  box3=new JRadioButton("Hard Classifier",false);
  // JCheckBox box4=new JCheckBox();
   JLabel box5=new JLabel("0<Threshold<1");
   JTextField t=new JTextField("0",2);



        JRadioButton fcm=new JRadioButton("Fuzzy C-Means",true);
        JRadioButton fcms=new JRadioButton("Fuzzy C-Means_S",false);
        JRadioButton flicm=new JRadioButton("FLICM",false);
        JRadioButton adflicm=new JRadioButton("ADFLICM",false);
                JRadioButton plicm=new JRadioButton("PLICM",false);
        JRadioButton adplicm=new JRadioButton("ADPLICM",false);
  	JRadioButton  noiseOut=new JRadioButton("Noise Clustering Without Entropy",false);
  	JRadioButton  noiseWith=new JRadioButton("Noise Clustering With Entropy",false);
  	JRadioButton  entropy=new JRadioButton("FCM With Entropy",false);
  	JRadioButton  pcm=new JRadioButton("Possibilistic C-Means",false);
        JRadioButton pcms=new JRadioButton("Possibilistic C-Means_S",false);
  	JRadioButton mpcm=new JRadioButton("Modified Possibilistic C-Means",false);
  	JRadioButton  ipcm=new JRadioButton("Improved Possibilistic C-Means",false);
  	JRadioButton  mlc=new JRadioButton("Maximum Likelihood Classifier",false);

  JRadioButton con=new JRadioButton("Smoothing Prior");
  JRadioButton dis=new JRadioButton("Discontinuity Adaptive Prior");
  JRadioButton h1=new JRadioButton("H1",false);
  JRadioButton h2=new JRadioButton("H2",true);
  JRadioButton h3=new JRadioButton("H3",false);
  JRadioButton h4=new JRadioButton("H4",false);
  JLabel l1=new JLabel("m >1   ");
  JLabel l2=new JLabel("Select A Classifier(BASE) --");
  JLabel ll2=new JLabel("Select the Advanced Classifier");
  JLabel ll21=new JLabel("(BASE classifier selection is needed.)");
  JLabel sp=new JLabel("");
  JLabel l3=new JLabel("Select One Contextual --");
  JLabel l4=new JLabel((char)948+">0   ");
  JLabel l5=new JLabel("1>="+(char)955+">=0   ");
  JLabel l6=new JLabel((char)957+">0   ");
  JLabel l7=new JLabel((char)946+">0   ");
  JLabel l8=new JLabel("1>="+(char)947+">=0   ");
  JLabel l9=new JLabel("Select A Type --");
  JLabel l10=new JLabel();
  JLabel l11=new JLabel();

  JLabel lbl=new JLabel("Select in case of Adaptive prior --");


  JTextField tfm=new JTextField(2);
  JTextField tfdel=new JTextField(2);
  JTextField tflem=new JTextField(2);
  JTextField tfneo=new JTextField(2);
  JTextField tfbeta=new JTextField(2);
  JTextField tfgama=new JTextField(2);



    ButtonGroup bg=new ButtonGroup();
    ButtonGroup bg1=new ButtonGroup();
	ButtonGroup bg2=new ButtonGroup();
	ButtonGroup bg3=new ButtonGroup();
        ButtonGroup bg4= new ButtonGroup();

  static int b;
  static int h;
  static int w;
  int flago=1;
  int j1=0;
  int val=0;
  double temp=0.0,energy=0.0,energy1=0.0,energy2=0.0;
  double temp1=0.0,temp2=0.0,temp3=0.0;
  double div=0.0;
  double dsq[][];
  int node=0;
  double dsqs[][];
  double maxsid=-1;
  int fl=0;
  double max[]=new double[20];
  double min[]=new double[20];
  double minsid=65535;
  double dsq_temp[][];
  double m1,m2;
  double mix_m;
  int flag_second=1;
  String txt;
  JCheckBox cb;
  JCheckBox box4;
  JTextField meu_mix;


  public Fcm()
  {
for(int i=0;i<20;i++)
  {
      max[i]=-1;
      min[i]=65535;
  }

    setTitle("Classifier For Fuzzy C-Means");
	setSize(1000,1000);
	addWindowListener(new WindowAdapter()
	{
	  public void WindowClosing(WindowEvent e)
          {
	    System.exit(0);
	  }
	});

    JMenu fmenu=new JMenu("File");
	fmenu.setMnemonic(KeyEvent.VK_F);
	openitem=new JMenuItem("Open Sig.File");
	openitem.setMnemonic(KeyEvent.VK_O);
	openitem.addActionListener(this);
	fmenu.add(openitem);

	/*Distance options*/
	        JMenu distance1=new JMenu("Distance-1");
	        sel_distance=new JMenu("Euclidean");
	        JMenu distance2=new JMenu("Distance-2");
	        sel_2distance=new JMenu("Manhattan");
			 box4=new JCheckBox("Save Dist");

	         cb= new JCheckBox();
	        JLabel meu_lb=new JLabel(" 1>="+(char)955+ ">=0" + "  for mixture: ");
	        JLabel t1_lb=new JLabel("              ");
	        JLabel t2_lb=new JLabel("    ");
	        JLabel t3_lb=new JLabel();
	         meu_mix=new JTextField("");

	        for(int i=0;i<24;i++)
	        {
	            openkitem[i]=new JMenuItem(distances[i]);
	            distance1.add(openkitem[i]);
	            openkitem[i].addActionListener(this);
	        }
	        for(int i=0;i<24;i++)
	        {
	            openk2item[i]=new JMenuItem(distances[i]);
	            distance2.add(openk2item[i]);
	            openk2item[i].addActionListener(this);
	        }



	        menubar= new JMenuBar();
	        menubar.add(fmenu);
	        menubar.add(distance1);
	        menubar.add(sel_distance);
	        menubar.add(distance2);
	        menubar.add(sel_2distance);


	        menubar.add(t2_lb);

	        menubar.add(cb);
	        menubar.add(meu_lb);
	        menubar.add(box4);

	        menubar.add(meu_mix);
	        menubar.add(t1_lb);

	setJMenuBar(menubar);
	afd=new addfiledata();
	this.tablerows=afd.retnewsize();

	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	JPanel p3=new JPanel();
	JPanel p4=new JPanel();
	JPanel p5=new JPanel();
	JPanel p6=new JPanel();
	JPanel p7=new JPanel();
	JPanel p8=new JPanel();
	JPanel p9=new JPanel();
	JPanel p10=new JPanel();
        JPanel p11=new JPanel();



    bg.add(box1);
    bg.add(box2);
	bg.add(box3);
	//bg.add(box4);
	//bg.add(box5);
	//bg.add(t);
	bg1.add(fcm);
	bg1.add(noiseOut);
	bg1.add(noiseWith);
	bg1.add(entropy);
	bg1.add(pcm);
    bg1.add(mpcm);
    bg1.add(ipcm);
    bg1.add(mlc);
     bg1.add(fcms);
     bg1.add(pcms);
    bg4.add(flicm);
    bg4.add(adflicm);

    bg4.add(plicm);
    bg4.add(adplicm);
   // bg1.add(box4);

	bg2.add(con);
	bg2.add(dis);

	bg3.add(h1);
	bg3.add(h2);
	bg3.add(h3);
	bg3.add(h4);

	box1.addActionListener(this);
    box2.addActionListener(this);
	box3.addActionListener(this);
    h1.addActionListener(this);
	h2.addActionListener(this);
	h3.addActionListener(this);
	h4.addActionListener(this);
	fcm.addActionListener(this);
        fcms.addActionListener(this);

        pcms.addActionListener(this);
    noiseOut.addActionListener(this);
    noiseWith.addActionListener(this);
    entropy.addActionListener(this);
    pcm.addActionListener(this);
    mpcm.addActionListener(this);
    ipcm.addActionListener(this);
    mlc.addActionListener(this);
        flicm.addActionListener(this);
        adflicm.addActionListener(this);

        plicm.addActionListener(this);
        adplicm.addActionListener(this);
   // box4.addItemListener(this);

        btClasi.addActionListener(this);
        btsyn.addActionListener(this);
        con.addActionListener(this);
	dis.addActionListener(this);

	int bands;
	model=new DefaultTableModel(40,0);
	data[0]= new String("Class");
	this.Bands=imgdata8.bands();
	bands=imgdata8.bands();
    model.addColumn(data[0]);
	for(int i=1;i<=Bands;i++)
	{
	 data[i]=new String("Band")+ new Integer(i);
	 model.addColumn(data[i]);
	}
	table=new JTable(model);
	Container contentpane=getContentPane();
	p1.setSize(1000,1000);

	p1.add(new JScrollPane(table),"Center");
	contentpane.add(p1,"West");

    p2.add(tfm);
    p2.add(l1);
    p2.add(tfdel);
    p2.add(l4);
    p2.add(tflem);
    p2.add(l5);
    p2.add(tfneo);
    p2.add(l6);
    p2.add(tfbeta);
    p2.add(l7);
    p2.add(tfgama);
    p2.add(l8);
    p2.add(btClasi);
    p2.add(btsyn);


    p3.add(l2);
    p3.add(sp);
    p3.add(fcm,"Center");
    p3.add(fcms,"Center");
    p3.add(noiseOut,"Center");
    p3.add(noiseWith);
    p3.add(sp);
    p11.add(ll2);
    p11.add(ll21);
    p11.add(sp);
    p11.add(flicm);
    p11.add(adflicm);
    p11.add(plicm);
    p11.add(adplicm);
    p3.add(entropy);
    p3.add(pcm);
    p3.add(pcms);
    p3.add(mpcm);
    p3.add(ipcm);
    p3.add(mlc);
   // p3.add(box4);

    p4.add(l11);

    p4.add(l3);
    p4.add(con);
    p4.add(dis);

    p4.add(lbl);
    p4.add(h1,"East");
    p4.add(h2,"East");
    p4.add(h3,"East");
    p4.add(h4,"East");


	p7.add(l9);
    p9.add(box1,"West");
    p9.add(l10);
    p8.add(box2);
    p8.add(box3);
   // p8.add(box4);
	p8.add(box5);
	p8.add(t);

    p7.add(p9);


    tfm.setText("3.0");
    tfdel.setText("2.5");
    tflem.setText("0.7");
    tfneo.setText("2.0");
    tfbeta.setText("3.0");
    tfgama.setText("0.3");

    p3.setSize(200,200);
	p2.setLayout(new FlowLayout());

	p3.setLayout(new GridLayout(12,100));

        p4.setLayout(new GridLayout(10,1));
	p5.setLayout(new GridLayout(3,1));
	p6.setLayout(new GridLayout(2,1));
	p7.setLayout(new GridLayout(2,1));
	p8.setLayout(new GridLayout(2,1));
	p9.setLayout(new GridLayout(1,3));
	p10.setLayout(new GridLayout(4,1));
        p11.setLayout(new GridLayout(10,50));

        p5.add(p3,"North");
	p5.add(p4,"Center");
        p5.add(p11,"South");

	p5.add(p6,"South");
	p6.add(p7);
	p6.add(p8);

	contentpane.add(p2,"South");
	contentpane.add(p5,"East");

	l1.setFont(f);
	l4.setFont(f);
	l5.setFont(f);
	l6.setFont(f);
	l7.setFont(f);
	l8.setFont(f);


 	bands=new ReadImageData().bands();
    c= new double[10][bands][bands];

  }






public double determinant(double a[][],int n)
{
	double det = 0;
	if(n==1)
	det=a[0][0];
	else if(n==2)
	det=a[0][0]*a[1][1]-a[1][0]*a[0][1];
	else
	{
		det=0;
		for(int j1=0;j1<n;j1++)
		{
			double[][] m = new double[n-1][];
			for(int k=0;k<(n-1);k++)
			{
				m[k]=new double[n-1];
			}
			for(int i=1;i<n;i++)
			{
				int j2=0;
				for(int j=0;j<n;j++)
				{
					if(j==j1)
						continue;

					m[i-1][j2]=a[i][j];
					j2++;
				}
			}
			det +=Math.pow(-1.0,1.0+j1+1.0)*a[0][j1]*determinant(m,n-1);
		}
	}
	return det;


}



      public void distance_mixture(Object source)
      {
        double meu_mix_db=0;
	   	if(flag_second==0)
       meu_mix_db=Double.parseDouble(meu_mix.getText());

	   if (source==btClasi || source==btsyn)
       {
       		b= new ReadImageData().bands();
    		h= new ReadImageData().rows();
    		w= new ReadImageData().columns();

			meu=new double[count+1][h*w];
                        meun=new double[b][h*w];
                        meunm=new double[count+1][h*w];
			dsq=new double[count][h*w];
                        xnew=new double[b][h*w];
                        dsqs=new double[count][h*w];
 		    x=new double[b];
 		    sub=new double[b][1];
 		    subT=new double[1][b];
  		    FileInputStream fin[]=new FileInputStream[b];
  		    FileChannel fchan[]=new FileChannel[b];
  		    mean=new double[count][b];
  		    mul=new double[1][b];
  		    MappedByteBuffer mBuf[]=new MappedByteBuffer[b];
  		    double c1[][][]=new double[count][b][b];
                     double rese[][]=new double[count][h*w];
                     double remax=-5, remin=65355;

  		   if(source==btClasi)
                   {
                   System.out.println("Selected BASE Classifier is : "+classifier_chk[classi-1]);
                   if(advclassi!=0)
                   System.out.println("Selected ADVANCED Classifier is : "+adv_classifier_chk[advclassi-1]);
                   System.out.println("Distance selected:"+txt);
                   }
                   else if(source==btsyn)
                   {
                       count=1;
                       classi=12;
                       txt="dead";
                   }

  		    m=Float.parseFloat(tfm.getText());
  		    del=Float.parseFloat(tfdel.getText());
  		    lem=Float.parseFloat(tflem.getText());
  		    neo=Float.parseFloat(tfneo.getText());
  		    beta=Float.parseFloat(tfbeta.getText());
  		    gama=Float.parseFloat(tfgama.getText());

  		    try
  		    {
  		      for(int i=0;i<b;i++)
  		      {
  		      	 fin[i]=new FileInputStream("Data" + BilImage.name + i);
  		      	 fchan[i]= fin[i].getChannel();
  		      	 if(picture.shiftFlag!=8)
  		      	 mBuf[i]=fchan[i].map(FileChannel.MapMode.READ_ONLY,0,(h*w*2));
  		      	 else
  		      	 mBuf[i]=fchan[i].map(FileChannel.MapMode.READ_ONLY,0,(h*w));
  		      }
  			}
  			catch(Exception e)
  			{
  				System.out.println("file Error" +e);
  			}


  		    // calculating mean vector Array.

  		   for(int j=0;j<count;j++)
              for(int i=0;i<b;i++)
              {
                 if(source==btClasi)
                 {
                     mean[j][i]=(Double)(model.getValueAt(j,i+1));
                  }
              }
             if(txt.compareTo("Euclidean")==0)
             {
             	for(int i=0;i<count;i++)
             	    for(int j=0;j<b;j++)
             	      for(int k=0;k<b;k++)
             	        if(j==k)
             	         c1[i][j][k]=1;

             	         else
             	         c1[i][j][k]=0;
             }

            if(txt.compareTo("D.V-C")==0)
             {
             	for(int i=0;i<count;i++)
             	    for(int j=0;j<b;j++)
             	      for(int k=0;k<b;k++)
             	        if(j==k)
             	         c1[i][j][k]=c[i][j][k];
             	        else
             	         c1[i][j][k]=0;
             }

             if(txt.compareTo("V-C")==0)
             {
             	for(int i=0;i<count;i++)
             	    for(int j=0;j<b;j++)
             	      for(int k=0;k<b;k++)
             	         c1[i][j][k]=c[i][j][k];

             }

			              if(txt.compareTo("Manhattan")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }

  if(txt.compareTo("Sin")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }

 if(txt.compareTo("Tan")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }

 if(txt.compareTo("Sid")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }
 if(txt.compareTo("Sca")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }
if(txt.compareTo("Sca-Tan")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }
if(txt.compareTo("Sca-Sin")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }
if(txt.compareTo("Sid-Sca-Tan")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }
if(txt.compareTo("Sid-Sca-Sin")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }
 if(txt.compareTo("Sid-Plus-Sin")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }

 if(txt.compareTo("Sid-Sam-Sin")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }
 if(txt.compareTo("Sid-Sam-Tan")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }

 if(txt.compareTo("Sid-Plus-Tan")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }


			              if(txt.compareTo("ChessBoard")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }

			              if(txt.compareTo("Mean-Absolute-Difference")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }

			              if(txt.compareTo("Canberra")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }



			             if(txt.compareTo("BrayCurtis")==0)
			             {
			             	for(int i=0;i<count;i++)
			             	    for(int j=0;j<b;j++)
			             	      for(int k=0;k<b;k++)
			             	        if(j==k)
			             	         c1[i][j][k]=1;

			             	         else
			             	         c1[i][j][k]=0;
             }


			              if(txt.compareTo("Cosine")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }



			              if(txt.compareTo("Correlation")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }




			              if(txt.compareTo("Normalized-Squared-Euclidean")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }





			              if(txt.compareTo("Median-Absolute-Difference")==0)
			              {
			              	for(int i=0;i<count;i++)
			              	    for(int j=0;j<b;j++)
			              	      for(int k=0;k<b;k++)
			              	        if(j==k)
			              	         c1[i][j][k]=1;

			              	         else
			              	         c1[i][j][k]=0;
             }



          //Calculating distance vector.

           try
			{

			  for(int l=0;l<count;l++)
			  {
			  	for(int i=0;i<b;i++)
			  		if(picture.shiftFlag!=8)
  		      	 		mBuf[i]=fchan[i].map(FileChannel.MapMode.READ_ONLY,0,(h*w*2));
  		      	    else
  		      	 		mBuf[i]=fchan[i].map(FileChannel.MapMode.READ_ONLY,0,(h*w));

		    	for(int i=0;i<h*w;i++)
	  		    {
	  		    	for(int j=0;j<b;j++)
	  		    	{
	  		    		   if(picture.shiftFlag!=8)
	  		    		   {
	    				        byte b5=mBuf[j].get();
						 		byte b1=mBuf[j].get();
						 		int  p1 = b5 << 24;
								int  p2 = (b1 <<24 ) >>> 8;
								int	tempCount = p1 | p2;
								tempCount = tempCount >>> 16;
								x[j]=(double)tempCount;
                                                             //   System.out.println("\nByte="+b1);
					}
							else
							{
							  	x[j] = (mBuf[j].get() << 24) >>> 24;
                                                                      //     System.out.println("\nByte="+mBuf[j].get());

							}
                                        //   System.out.println("\nx[]="+x[j]);
                                        xnew[j][i]=x[j];

                                        }


             //...............Different Measures of Similarity and Dissimilarity..............



                   //...........EUCLIDEAN............................

                   if(txt.compareTo("Euclidean")==0)
					                       {

											         for(int k=0;k<b;k++)
												     sub[k][0]= x[k]-mean[l][k];

													 for(int k=0;k<b;k++)
													 subT[0][k]=sub[k][0];

													 for(int j=0;j<b;j++)
													 {
													   mul[0][j]=0;
													 for(int k=0;k<b;k++)
													 mul[0][j]+=subT[0][k]*c1[l][k][j];
													 }

													 dsq[l][i]=0;
													 for(int k=0;k<b;k++)
													 dsq[l][i]+=mul[0][k]*sub[k][0];



					                                    /****Mixing second Distance*/
					                                    if(flag_second==0)
					                                     dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
					                                    /*End Mixing*/


					                                    }

					      //...........V-C............................

					                         else if(txt.compareTo("V-C")==0)
					 					                         {

					 											         for(int k=0;k<b;k++)
					 												     sub[k][0]= x[k]-mean[l][k];

					 													 for(int k=0;k<b;k++)
					 													 subT[0][k]=sub[k][0];

					 													 for(int j=0;j<b;j++)
					 													 {
					 													   mul[0][j]=0;
					 													 for(int k=0;k<b;k++)
					 													 mul[0][j]+=subT[0][k]*c1[l][k][j];
					 													 }

					 													 dsq[l][i]=0;
					 													 for(int k=0;k<b;k++)
					 													 dsq[l][i]+=mul[0][k]*sub[k][0];



					 					                                    /****Mixing second Distance*/
					 					                                    if(flag_second==0)
					 					                                     dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
					 					                                    /*End Mixing*/

                                                                   }
//....................SID...........
                    //if the distance selected is sid
                    else if(txt.compareTo("Sid")==0)
                    {
                        int var =i;
                        double p=0.0;
                        double [] ps=new double[b];



                        //p is for sum of the bands
			 for(int s=0;s<b;s++)
                         {
                             p =p+takenvalues[l][s];//the values for the classes are stored in the p,where takenvalues [][] has the values for the classes
                         }
                         /*for the general purpose the array is taken so that the images for any number of bands can be easily read and be worked on*/
                         for(int s=0;s<b;s++)
                              ps[s]=takenvalues[l][s]/p;
                        //ps is the array for each band it refers for ps[1]/p and so on which helps in reading the values separately for each band

                      double q=0.0;
                      for(int s=0;s<b;s++)
                       q =q+x[s];//q is the sum for the picture pixels values taken form the image

                      double [] qs=new double[b];//the array for the qs
                      for(int s=0;s<b;s++)
                      {
                          qs[s]=x[s]/q;//qs[] is used for the similar reasons as ps[] was used
                      }



                 if(q==0.0)
                {//for the 0th value the the 0 is filled in the qs[] array
                    for(int s=0;s<b;s++)
                         qs[s]=0.0;
                }
		//a_b[] and b_a[] are taken to take the values for sid separately
                   double a_b[]=new double[b];
                   double b_a[]=new double[b];
                   double fac[]=new double[b];
                   double fact[]=new double[b];

                    for(int s=0;s<b;s++)
                    {
                        fac[s]=ps[s]/qs[s];//to execute the formula p1/q1 and the value for this is stored in the array fromat as mentioned earlier
                        fact[s]=qs[s]/ps[s];//for the formula q1/p1 and so on

                    }


                       for(int s=0;s<b;s++)
                       {
                           if(qs[s]==0.0)
                           {
                               //the value as 0 is stored to avoid NaN or infinity exception
                               a_b[s]=0.0;
                               b_a[s]=0.0;
                           }
                           else
                           {
                               //the values are calculated and stored in the array
                               a_b[s]=ps[s]*Math.log10(fac[s]);
                               b_a[s]=qs[s]*Math.log10(fact[s]);
                           }
                       }


                      double sida_b=0.0,sidb_a=0.0;
                      for(int s=0;s<b;s++)
                      {
                          sida_b=sida_b+a_b[s];
                          sidb_a=sidb_a+b_a[s];

                      }
                      /*this if else ladder is made for the calculation for the min value */
                        if(sida_b<sidb_a)
                        {
                            if(minsid>sida_b)
                                minsid=sida_b;
                        }

                         if(sida_b>sidb_a)
                        {
                            if(minsid>sidb_a)
                                minsid=sidb_a;
                        }

                     double sid=sida_b+sidb_a;
                     if(i==(h*w-1))//for the last pixel
                     {
                         if(minsid<0)
                            fl=1;
                     }

                        dsq[l][var]=sid;
              //   System.out.println("\nSid="+sid);
                        //offset calculation
                        if(fl==1)
                        for(int d=0;d<count;d++)
                        {
                            for(int r=0;r<h*w;r++)
                            {
                                dsq[d][r]=dsq[d][r]-minsid;
                            }
                        }

                     }
                       //Spectral correlation angle it is simply acos(values from pcc+1)/2,the most sensitive
                    else if(txt.compareTo("Sca")==0)
                    {


                        double sx = 0.0;
                        double sy = 0.0,r=0.0;
                        double sxx = 0.0;
                        double syy = 0.0;
                        double sxy = 0.0;

                         int n = b;

       for(int ih = 0; ih < n; ++ih)//n is no of bands
    {
      double xs = x[ih];//x array has unknown values i.e. si acc to formula
      double y = takenvalues[l][ih];//takenvalues has the known values i.e.sj according to formula

      sx += xs;//sx is the summation of the x[]
      sy += y;//sy is the summation of the y[]
      sxx += xs * xs;//summation of the square of each and every element si^2
      syy += y * y;//summation of the square of y elements sj^2
      sxy += xs * y;//summation of sisj
    }




// covariation
    double cov = sxy / n - sx * sy / n / n;

    // standard error of x
    double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);

    // standard error of y
    double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);

    // correlation is just a normalized covariation
    //test cases to avoid NaN
    if(sigmax==0.0)
        r=0.0;
    else if(sigmay==0.0)
        r=0.0;
    else
    r=cov / sigmax / sigmay;

                        dsq[l][i] = Math.acos((r+1)/2);


                      }
          //combination of sca with tan simple appyling tan function on thetha
                    else if(txt.compareTo("Sca-Tan")==0)
                    {

                        double sx = 0.0,r=0.0;
    double sy = 0.0;
    double sxx = 0.0;
    double syy = 0.0;
    double sxy = 0.0;

    int n = b;

       for(int ih = 0; ih < n; ++ih)//n is no of bands
    {
      double xs = x[ih];
      double y = takenvalues[l][ih];

      sx += xs;
      sy += y;
      sxx += xs * xs;
      syy += y * y;
      sxy += xs * y;
    }




// covariation
    double cov = sxy / n - sx * sy / n / n;

    // standard error of x
    double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);

    // standard error of y
    double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);

    // correlation is just a normalized covariation
    if(sigmax==0.0)
        r=0.0;
    else if(sigmay==0.0)
        r=0.0;
    else
    r=cov / sigmax / sigmay;

                        dsq[l][i] = (Math.acos((r+1)/2)*180)/Math.PI;
                         double tan_value_sca=Math.tan(dsq[l][i]*Math.PI/180);
                            dsq[l][i]=tan_value_sca;

                      }


             //combination of the sca with sin applying sin on the thetha

                    else if(txt.compareTo("Sca-Sin")==0)
                    {

                        double sx = 0.0,r=0.0;
                        double sy = 0.0;
                       double sxx = 0.0;
                       double syy = 0.0;
                       double sxy = 0.0;

    int n = b;

       for(int ih = 0; ih < n; ++ih)//n is no of bands
    {
      double xs = x[ih];
      double y = takenvalues[l][ih];

      sx += xs;
      sy += y;
      sxx += xs * xs;
      syy += y * y;
      sxy += xs * y;
    }




// covariation
    double cov = sxy / n - sx * sy / n / n;


    double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);

    double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);

    if(sigmax==0.0)
        r=0.0;
    else if(sigmay==0.0)
        r=0.0;
    else
    r=cov / sigmax / sigmay;

                        dsq[l][i] = (Math.acos((r+1)/2)*180)/Math.PI;
                         double sin_value_sca=Math.sin(dsq[l][i]*Math.PI/180);
                            dsq[l][i]=sin_value_sca;
                      }


                    else if(txt.compareTo("Sid-Sca-Tan")==0)
                    {

                        double sx = 0.0,r=0.0;
    double sy = 0.0;
    double sxx = 0.0;
    double syy = 0.0;
    double sxy = 0.0;

    int n = b;

       for(int ih = 0; ih < n; ++ih)//n is no of bands
    {
      double xs = x[ih];
      double y = takenvalues[l][ih];

      sx += xs;
      sy += y;
      sxx += xs * xs;
      syy += y * y;
      sxy += xs * y;
    }



    double cov = sxy / n - sx * sy / n / n;
    double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);
    double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);
    if(sigmax==0.0)
        r=0.0;
    else if(sigmay==0.0)
        r=0.0;
    else
    r=cov / sigmax / sigmay;

                        dsq[l][i] = (Math.acos((r+1)/2)*180)/Math.PI;
                         double tan_value_sca=Math.tan(dsq[l][i]*Math.PI/180);
                            dsq[l][i]=tan_value_sca;

                                                      int var =i;
                                                double p=0.0;
                                                 double [] ps=new double[b];  //   System.out.println("\nBands:"+Bands);

			 for(int s=0;s<b;s++)
                         {   p =p+takenvalues[l][s];
                            }
                         for(int s=0;s<b;s++)
                              ps[s]=takenvalues[l][s]/p;


                        double q=0.0;
                      for(int s=0;s<b;s++)
                       q =q+x[s];

                      double [] qs=new double[b];
                      for(int s=0;s<b;s++)
                      {
                          qs[s]=x[s]/q;
                      }



        if(q==0.0)
        {
           for(int s=0;s<b;s++)
            qs[s]=0.0;
        }

                 double a_b[]=new double[b];
                 double b_a[]=new double[b];
                   double fac[]=new double[b];
                   double fact[]=new double[b];

                    for(int s=0;s<b;s++)
                    {
                        fac[s]=ps[s]/qs[s];
                        fact[s]=qs[s]/ps[s];

                    }


                       for(int s=0;s<b;s++)
                       {
                           if(qs[s]==0.0)
                           {
                               a_b[s]=0.0;
                               b_a[s]=0.0;
                           }
                           else
                           {
                               a_b[s]=ps[s]*Math.log10(fac[s]);
                                b_a[s]=qs[s]*Math.log10(fact[s]);
                           }
                       }


                      double sida_b=0.0,sidb_a=0.0;
                      for(int s=0;s<b;s++)
                      {
                          sida_b=sida_b+a_b[s];
                          sidb_a=sidb_a+b_a[s];

                      }

                      double sid=sida_b+sidb_a;


                       dsq[l][i]=dsq[l][i]*sid;

                      }


                    else if(txt.compareTo("Sid-Sca-Sin")==0)
                    {

                        double sx = 0.0,r=0.0;
    double sy = 0.0;
    double sxx = 0.0;
    double syy = 0.0;
    double sxy = 0.0;

    int n = b;

       for(int ih = 0; ih < n; ++ih)//n is no of bands
    {
      double xs = x[ih];
      double y = takenvalues[l][ih];

      sx += xs;
      sy += y;
      sxx += xs * xs;
      syy += y * y;
      sxy += xs * y;
    }



    double cov = sxy / n - sx * sy / n / n;
    double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);
    double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);
    if(sigmax==0.0)
        r=0.0;
    else if(sigmay==0.0)
        r=0.0;
    else
    r=cov / sigmax / sigmay;

                        dsq[l][i] = (Math.acos((r+1)/2)*180)/Math.PI;
                         double sin_value_sca=Math.sin(dsq[l][i]*Math.PI/180);
                            dsq[l][i]=sin_value_sca;

                                                      int var =i;
                                                double p=0.0;
                                                 double [] ps=new double[b];

			 for(int s=0;s<b;s++)
                         {   p =p+takenvalues[l][s];
                            }
                         for(int s=0;s<b;s++)
                              ps[s]=takenvalues[l][s]/p;


                        double q=0.0;
                      for(int s=0;s<b;s++)
                       q =q+x[s];

                      double [] qs=new double[b];
                      for(int s=0;s<b;s++)
                      {
                          qs[s]=x[s]/q;
                      }



        if(q==0.0)
        {
           for(int s=0;s<b;s++)
            qs[s]=0.0;
        }

                 double a_b[]=new double[b];
                 double b_a[]=new double[b];
                   double fac[]=new double[b];
                   double fact[]=new double[b];

                    for(int s=0;s<b;s++)
                    {
                        fac[s]=ps[s]/qs[s];
                        fact[s]=qs[s]/ps[s];

                    }


                       for(int s=0;s<b;s++)
                       {
                           if(qs[s]==0.0)
                           {
                               a_b[s]=0.0;
                               b_a[s]=0.0;
                           }
                           else
                           {
                               a_b[s]=ps[s]*Math.log10(fac[s]);
                                b_a[s]=qs[s]*Math.log10(fact[s]);
                           }
                       }


                      double sida_b=0.0,sidb_a=0.0;
                      for(int s=0;s<b;s++)
                      {
                          sida_b=sida_b+a_b[s];
                          sidb_a=sidb_a+b_a[s];

                      }

                      double sid=sida_b+sidb_a;


                       dsq[l][i]=dsq[l][i]*sid;

                      }



           else if(txt.compareTo("Sid-Sam-Sin")==0)
				            {
                        	//sin
                                                double angle,value;
                            for(int j=0;j<b;j++)//b is no of bands
			    {
                                sub[j][0]=0;//sub is 2d array
                                    for(int k=0;k<b;k++)
                                        sub[j][0]+= (x[k]*mean[l][k]);  //filling
                            }

                            for(int j=0;j<b;j++)
                            {
                                mul[0][j]=0;
                                subT[0][j]=0;
                                for(int k=0;k<b;k++)
                                {
                                    mul[0][j]+=(x[k]*x[k]);//mul square ti find its magnitude
                                    subT[0][j]+=(mean[l][k]*mean[l][k]);//mean is pre defined squaring of sub is stored in subT
                                 /*  if(k==b-1)
                                    {
                                        mul[0][j]=Math.sqrt(mul[0][j]);//to find magnitude
                                        subT[0][j]=Math.sqrt(subT[0][j]);//to find magnitude
                                    }*/
                                }
                            }
                        //    double value_arr[]=null;
                        //    double angle_arr[]=null;
                         //   double tan_arr[]=null;

                            dsq[l][i]=0;
                            for(int k=0;k<b;k++)
                            {
                               // System.out.println("welcome to sin");
                                if(mul[0][k]==0)
                                {
                                    dsq[l][i]=0.0;
                                }
                                else
                               dsq[l][i]=(sub[k][0])/((Math.sqrt(mul[0][k])*Math.sqrt(subT[0][k])));

                                value= dsq[l][i];
                                angle = (Math.acos(value)*180)/Math.PI;
                              double sin_value=Math.sin(angle*Math.PI/180);

                              dsq[l][i]=sin_value;

                            }              //sid
                                                int var =i;
                                                double p=0.0;
                                                 double [] ps=new double[b];  //   System.out.println("\nBands:"+Bands);

			 for(int s=0;s<b;s++)
                         {   p =p+takenvalues[l][s];
                            }
                         for(int s=0;s<b;s++)
                              ps[s]=takenvalues[l][s]/p;


                        double q=0.0;
                      for(int s=0;s<b;s++)
                       q =q+x[s];

                      double [] qs=new double[b];
                      for(int s=0;s<b;s++)
                      {
                          qs[s]=x[s]/q;
                      }



        if(q==0.0)
        {
           for(int s=0;s<b;s++)
            qs[s]=0.0;
        }

                 double a_b[]=new double[b];
                 double b_a[]=new double[b];
                   double fac[]=new double[b];
                   double fact[]=new double[b];

                    for(int s=0;s<b;s++)
                    {
                        fac[s]=ps[s]/qs[s];
                        fact[s]=qs[s]/ps[s];

                    }


                       for(int s=0;s<b;s++)
                       {
                           if(qs[s]==0.0)
                           {
                               a_b[s]=0.0;
                               b_a[s]=0.0;
                           }
                           else
                           {
                               a_b[s]=ps[s]*Math.log10(fac[s]);
                                b_a[s]=qs[s]*Math.log10(fact[s]);
                           }
                       }


                      double sida_b=0.0,sidb_a=0.0;
                      for(int s=0;s<b;s++)
                      {
                          sida_b=sida_b+a_b[s];
                          sidb_a=sidb_a+b_a[s];

                      }

                      double sid=sida_b+sidb_a;


                       dsq[l][var]=dsq[l][var]*sid;
                 //    System.out.println("\nthe dsq at the value l is"+dsq[l][var]);



                   }


               else if(txt.compareTo("Sid-Plus-Sin")==0)
				            {
                        	//sin
                                                double angle,value;
                            for(int j=0;j<b;j++)//b is no of bands
			    {
                                sub[j][0]=0;//sub is 2d array
                                    for(int k=0;k<b;k++)
                                        sub[j][0]+= (x[k]*mean[l][k]);  //filling
                            }

                            for(int j=0;j<b;j++)
                            {
                                mul[0][j]=0;
                                subT[0][j]=0;
                                for(int k=0;k<b;k++)
                                {
                                    mul[0][j]+=(x[k]*x[k]);//mul square ti find its magnitude
                                    subT[0][j]+=(mean[l][k]*mean[l][k]);//mean is pre defined squaring of sub is stored in subT
                                 /*  if(k==b-1)
                                    {
                                        mul[0][j]=Math.sqrt(mul[0][j]);//to find magnitude
                                        subT[0][j]=Math.sqrt(subT[0][j]);//to find magnitude
                                    }*/
                                }
                            }
                        //    double value_arr[]=null;
                        //    double angle_arr[]=null;
                         //   double tan_arr[]=null;

                            dsq[l][i]=0;
                            for(int k=0;k<b;k++)
                            {
                               // System.out.println("welcome to sin");
                                if(mul[0][k]==0)
                                {
                                    dsq[l][i]=0.0;
                                }
                                else
                                dsq[l][i]=(sub[k][0])/((Math.sqrt(mul[0][k])*Math.sqrt(subT[0][k])));

                                value= dsq[l][i];
                                angle = (Math.acos(value)*180)/Math.PI;
                              double sin_value=Math.sin(angle*Math.PI/180);

                              dsq[l][i]=sin_value;

                            }              //sid
                                                int var =i;
                                                double p=0.0;
                                                 double [] ps=new double[b];  //   System.out.println("\nBands:"+Bands);

			 for(int s=0;s<b;s++)
                         {   p =p+takenvalues[l][s];
                            }
                         for(int s=0;s<b;s++)
                              ps[s]=takenvalues[l][s]/p;


                        double q=0.0;
                      for(int s=0;s<b;s++)
                       q =q+x[s];

                      double [] qs=new double[b];
                      for(int s=0;s<b;s++)
                      {
                          qs[s]=x[s]/q;
                      }



        if(q==0.0)
        {
           for(int s=0;s<b;s++)
            qs[s]=0.0;
        }

                 double a_b[]=new double[b];
                 double b_a[]=new double[b];
                   double fac[]=new double[b];
                   double fact[]=new double[b];

                    for(int s=0;s<b;s++)
                    {
                        fac[s]=ps[s]/qs[s];
                        fact[s]=qs[s]/ps[s];

                    }


                       for(int s=0;s<b;s++)
                       {
                           if(qs[s]==0.0)
                           {
                               a_b[s]=0.0;
                               b_a[s]=0.0;
                           }
                           else
                           {
                               a_b[s]=ps[s]*Math.log10(fac[s]);
                                b_a[s]=qs[s]*Math.log10(fact[s]);
                           }
                       }


                      double sida_b=0.0,sidb_a=0.0;
                      for(int s=0;s<b;s++)
                      {
                          sida_b=sida_b+a_b[s];
                          sidb_a=sidb_a+b_a[s];

                      }

                      double sid=sida_b+sidb_a;


                       dsq[l][var]=dsq[l][var]+sid;
                 //    System.out.println("\nthe dsq at the value l is"+dsq[l][var]);



                   }


else if(txt.compareTo("Sid-Sam-Tan")==0)
				            {
                        	           //sid

                                                int var =i;
                                                double p=0.0;
                                                 double [] ps=new double[b];

			 for(int s=0;s<b;s++)
                         {   p =p+takenvalues[l][s];
                            }
                         for(int s=0;s<b;s++)
                              ps[s]=takenvalues[l][s]/p;


                        double q=0.0;
                      for(int s=0;s<b;s++)
                       q =q+x[s];

                      double [] qs=new double[b];
                      for(int s=0;s<b;s++)
                      {
                          qs[s]=x[s]/q;
                      }



        if(q==0.0)
        {
           for(int s=0;s<b;s++)
            qs[s]=0.0;
        }
		    //    System.out.println("\nBands:"+Bands);

                 double a_b[]=new double[b];
                 double b_a[]=new double[b];
                   double fac[]=new double[b];
                   double fact[]=new double[b];

                    for(int s=0;s<b;s++)
                    {
                        fac[s]=ps[s]/qs[s];
                        fact[s]=qs[s]/ps[s];

                    }

                         //   System.out.println("\nhere:"+Bands);

                       for(int s=0;s<b;s++)
                       {
                           if(qs[s]==0.0)
                           {
                               a_b[s]=0.0;
                               b_a[s]=0.0;
                           }
                           else
                           {
                               a_b[s]=ps[s]*Math.log10(fac[s]);
                                b_a[s]=qs[s]*Math.log10(fact[s]);
                           }
                       }


                      double sida_b=0.0,sidb_a=0.0;
                      for(int s=0;s<b;s++)
                      {
                          sida_b=sida_b+a_b[s];
                          sidb_a=sidb_a+b_a[s];

                      }

                      double sid=sida_b+sidb_a;

                       dsqs[l][var]=sid;
                      // System.out.println("\ndsqs:"+Bands);

                   double value,angle;
                      //      System.out.println("selection:::::::::::::::::::"+txt);

                            for(int j=0;j<b;j++)//b is no of bands
			    {
                                sub[j][0]=0;//sub is 2d array
                                    for(int k=0;k<b;k++)
                                        sub[j][0]+= (x[k]*mean[l][k]);  //filling
                            }

                            for(int j=0;j<b;j++)
                            {
                                mul[0][j]=0;
                                subT[0][j]=0;
                                for(int k=0;k<b;k++)
                                {
                                    mul[0][j]+=(x[k]*x[k]);//mul square ti find its magnitude
                                    subT[0][j]+=(mean[l][k]*mean[l][k]);//mean is pre defined squaring of sub is stored in subT
                                  /*  if(k==b-1)
                                    {
                                        mul[0][j]=Math.sqrt(mul[0][j]);//to find magnitude
                                        subT[0][j]=Math.sqrt(subT[0][j]);//to find magnitude
                                    }*/
                                }
                            }

                            dsq[l][i]=0;
                            for(int k=0;k<b;k++)
                            {
                                if(mul[0][k]==0)
                                {
                                    dsq[l][i]=0.0;
                                }
                                else
                               dsq[l][i]=(sub[k][0])/((Math.sqrt(mul[0][k])*Math.sqrt(subT[0][k])));

                                value= dsq[l][i];
                                angle = (Math.acos(value)*180)/Math.PI;

                                if(max[l]<angle)
                                    max[l]=angle;
                                if(min[l]>angle)
                                    min[l]=angle;


                                dsq[l][i]=angle;



		            }
                               if(i==h*w-1)
                                {

                                    for(int ase=0;ase<(h*w);ase++)
                                    {
                                        dsq[l][ase]=((dsq[l][ase]-min[l])/(max[l]-min[l]));
                                        dsq[l][ase]=Math.tan(dsq[l][ase]);
                                        dsq[l][ase]=dsq[l][ase]*dsqs[l][ase];
                                    }



                                }




                   }

else if(txt.compareTo("Sid-Plus-Tan")==0)
				            {
                        	           //sid

                                                int var =i;
                                                double p=0.0;
                                                 double [] ps=new double[b];

			 for(int s=0;s<b;s++)
                         {   p =p+takenvalues[l][s];
                            }
                         for(int s=0;s<b;s++)
                              ps[s]=takenvalues[l][s]/p;


                        double q=0.0;
                      for(int s=0;s<b;s++)
                       q =q+x[s];

                      double [] qs=new double[b];
                      for(int s=0;s<b;s++)
                      {
                          qs[s]=x[s]/q;
                      }



        if(q==0.0)
        {
           for(int s=0;s<b;s++)
            qs[s]=0.0;
        }
		    //    System.out.println("\nBands:"+Bands);

                 double a_b[]=new double[b];
                 double b_a[]=new double[b];
                   double fac[]=new double[b];
                   double fact[]=new double[b];

                    for(int s=0;s<b;s++)
                    {
                        fac[s]=ps[s]/qs[s];
                        fact[s]=qs[s]/ps[s];

                    }

                         //   System.out.println("\nhere:"+Bands);

                       for(int s=0;s<b;s++)
                       {
                           if(qs[s]==0.0)
                           {
                               a_b[s]=0.0;
                               b_a[s]=0.0;
                           }
                           else
                           {
                               a_b[s]=ps[s]*Math.log10(fac[s]);
                                b_a[s]=qs[s]*Math.log10(fact[s]);
                           }
                       }


                      double sida_b=0.0,sidb_a=0.0;
                      for(int s=0;s<b;s++)
                      {
                          sida_b=sida_b+a_b[s];
                          sidb_a=sidb_a+b_a[s];

                      }

                      double sid=sida_b+sidb_a;

                       dsqs[l][var]=sid;
                      // System.out.println("\ndsqs:"+Bands);

                   double value,angle;
                      //      System.out.println("selection:::::::::::::::::::"+txt);

                            for(int j=0;j<b;j++)//b is no of bands
			    {
                                sub[j][0]=0;//sub is 2d array
                                    for(int k=0;k<b;k++)
                                        sub[j][0]+= (x[k]*mean[l][k]);  //filling
                            }

                            for(int j=0;j<b;j++)
                            {
                                mul[0][j]=0;
                                subT[0][j]=0;
                                for(int k=0;k<b;k++)
                                {
                                    mul[0][j]+=(x[k]*x[k]);//mul square ti find its magnitude
                                    subT[0][j]+=(mean[l][k]*mean[l][k]);//mean is pre defined squaring of sub is stored in subT
                                  /*  if(k==b-1)
                                    {
                                        mul[0][j]=Math.sqrt(mul[0][j]);//to find magnitude
                                        subT[0][j]=Math.sqrt(subT[0][j]);//to find magnitude
                                    }*/
                                }
                            }

                            dsq[l][i]=0;
                            for(int k=0;k<b;k++)
                            {
                                if(mul[0][k]==0)
                                {
                                    dsq[l][i]=0.0;
                                }
                                else
                              dsq[l][i]=(sub[k][0])/((Math.sqrt(mul[0][k])*Math.sqrt(subT[0][k])));

                                value= dsq[l][i];
                                angle = (Math.acos(value)*180)/Math.PI;

                                if(max[l]<angle)
                                    max[l]=angle;
                                if(min[l]>angle)
                                    min[l]=angle;


                                dsq[l][i]=angle;



		            }
                               if(i==h*w-1)
                                {

                                    for(int ase=0;ase<(h*w);ase++)
                                    {
                                        dsq[l][ase]=((dsq[l][ase]-min[l])/(max[l]-min[l]));
                                        dsq[l][ase]=Math.tan(dsq[l][ase]);
                                        dsq[l][ase]=dsq[l][ase]+dsqs[l][ase];
                                    }



                                }




                   }

//............................. SIN .............................
                         else if(txt.compareTo("Sin")==0)
                         {

                            double angle,value;
                            for(int j=0;j<b;j++)//b is no of bands
			    {
                                sub[j][0]=0;//sub is 2d array
                                    for(int k=0;k<b;k++)
                                        sub[j][0]+= (x[k]*mean[l][k]);  //filling where sub matrix is added with the value of the sid
                            }

                            for(int j=0;j<b;j++)
                            {
                                mul[0][j]=0;
                                subT[0][j]=0;
                                for(int k=0;k<b;k++)
                                {
                                    mul[0][j]+=(x[k]*x[k]);//mul square to find its magnitude
                                    subT[0][j]+=(mean[l][k]*mean[l][k]);//mean is pre defined squaring of sub is stored in subT
                                 /*  if(k==b-1)
                                    {
                                        mul[0][j]=Math.sqrt(mul[0][j]);//to find magnitude
                                        subT[0][j]=Math.sqrt(subT[0][j]);//to find magnitude
                                    }*/
                                }
                            }


                            dsq[l][i]=0.0;//the main matrix in which the values are supposed to be filled
                            for(int k=0;k<b;k++)
                            {

                                if(mul[0][k]==0)
                                {
                                    dsq[l][i]=0.0;//if value is passed as 0.0 then the dsq element is supposed to be fileed with 0 to avoid NaN excception
                                }
                                else //the values are filled in the dsq matrix
                              dsq[l][i]=(sub[k][0])/((Math.sqrt(mul[0][k])*Math.sqrt(subT[0][k])));

                                value= dsq[l][i];
                                angle = (Math.acos(value)*180)/Math.PI;	//it refers to tetha where theta is the cos inverse of the value,the values are supppoes to be radian
                              double sin_value=Math.sin(angle*Math.PI/180);//this is the intergral part the sin for the value is stored in the sin_value

                              dsq[l][i]=sin_value;//the final value is stored in the dsq [][]

		            }
                          //  System.out.println("\ndsq for sin="+dsq[l][i]+"\nb="+b);
              }

   //............................. TAN.............................
                         //if the distance selected is tan
                         //............................. TAN.............................
                         else if(txt.compareTo("Tan")==0)				            {
                        	 //int counter = 0;

                                 double value,angle;
                            //System.out.println("cos:::::::::::::::::::"+txt);

                            for(int j=0;j<b;j++)//b is no of bands
			    {
                                sub[j][0]=0;//sub is 2d array
                                    for(int k=0;k<b;k++)
                                        sub[j][0]+= (x[k]*mean[l][k]);  //filling
                            }

                            for(int j=0;j<b;j++)
                            {
                                mul[0][j]=0;
                                subT[0][j]=0;
                                for(int k=0;k<b;k++)
                                {
                                    mul[0][j]+=(x[k]*x[k]);//mul square ti find its magnitude
                                    subT[0][j]+=(mean[l][k]*mean[l][k]);//mean is pre defined squaring of sub is stored in subT
                                /*    if(k==b-1)
                                    {
                                        mul[0][j]=Math.sqrt(mul[0][j]);//to find magnitude
                                        subT[0][j]=Math.sqrt(subT[0][j]);//to find magnitude
                                    }*/
                                }
                            }

                            dsq[l][i]=0;
                            for(int k=0;k<b;k++)
                            {
                                if(mul[0][k]==0)
                                {
                                    dsq[l][i]=0.0;
                                }
                                else
                             dsq[l][i]=(sub[k][0])/((Math.sqrt(mul[0][k])*Math.sqrt(subT[0][k])));

                                value= dsq[l][i];
                                angle = (Math.acos(value)*180)/Math.PI;
                            //    System.out.println("\nAngle="+angle);
                              if(max[l]<angle)
                                    max[l]=angle;
                                if(min[l]>angle)
                                    min[l]=angle;


                                dsq[l][i]=angle;


                              /*  if(k==b-1)
                                {
                                    dsq[l][i]=1-dsq[l][i];

				 }*/

		            }
                               if(i==h*w-1)
                                {

                                    for(int ase=0;ase<(h*w);ase++)
                                    {
                                        dsq[l][ase]=((dsq[l][ase]-min[l])/(max[l]-min[l]));
                                        dsq[l][ase]=Math.tan(dsq[l][ase]);
                                    }



                                }

	    }




                         //...........D.V-C............................


					                              else if(txt.compareTo("D.V-C")==0)
						 					 	  {

						 					 		 for(int k=0;k<b;k++)
						 					 		 sub[k][0]= x[k]-mean[l][k];

						 					 		 for(int k=0;k<b;k++)
						 					 		 subT[0][k]=sub[k][0];

						 					 		 for(int j=0;j<b;j++)
						 					 		 {
						 					 		  mul[0][j]=0;
						 					 		  for(int k=0;k<b;k++)
						 					 		  mul[0][j]+=subT[0][k]*c1[l][k][j];
						 					 		 }

						 					 		 dsq[l][i]=0;
						 					 		 for(int k=0;k<b;k++)
						 					 	     dsq[l][i]+=mul[0][k]*sub[k][0];



						 					 		/****Mixing second Distance*/
						 					 		 if(flag_second==0)
						 					 		 dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
						 					 		/*End Mixing*/


					                                }


                  //...........MANHATTAN............................


                     else if(txt.compareTo("Manhattan")==0)
	                 {
						                       for(int k=0;k<b;k++)
						 	    	         	sub[k][0]= Math.abs(x[k]-mean[l][k]);



						 					for(int j=0;j<b;j++)
						 					{
						 						mul[0][j]=0;
						 						for(int k=0;k<b;k++)
						 							mul[0][j]+=sub[k][0];
						 					}

						 					dsq[l][i]=0;
						 					for(int k=0;k<b;k++)
						 						dsq[l][i]+=mul[0][k];

                                          /****Mixing second Distance*/
										  	 if(flag_second==0)
										  	 dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
					                                    /*End Mixing*/
						 }
                                       //...........CHESSBOARD............................


						 else if(txt.compareTo("ChessBoard")==0)
	                    {
						                               for(int k=0;k<b;k++)
						 								{
						 								  sub[k][0]= Math.abs(x[k]-mean[l][k]);

						 								}

						 											dsq[l][i]=0;
						 											double max=0.0;
						 											for(int k=0;k<b;k++)
						 												{
						 													if(sub[k][0]>max)
						 													max=sub[k][0];
						 													dsq[l][i]= max;
													                    }

                                                       /****Mixing second Distance*/
													    if(flag_second==0)
													   	 dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
													     /*End Mixing*/
				               }



            //...........MEAN-ABSOLUTE-DIFFERENCE............................

				    else if(txt.compareTo("Mean-Absolute-Difference")==0)
				   	                    {
				   						           for(int k=0;k<b;k++)
												   {
													 sub[k][0]= Math.abs(x[k]-mean[l][k]);

													}


                                                      dsq[l][i]=0;
                                                      double sum=0.0;
													  for(int k=0;k<b;k++)
                                                      {
														sum+=sub[k][0];
														dsq[l][i]= (sum/b);
													  }
				                                                          /****Mixing second Distance*/
				   													     if(flag_second==0)
				   													   	  dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
				   													     /*End Mixing*/
                                        }



               //...........CANBERRA............................


                   else if(txt.compareTo("Canberra")==0)
				  				   	    {

										        for(int k=0;k<b;k++)
										 	    {
										 		  sub[k][0]= (Math.abs(x[k]-mean[l][k]))/((Math.abs(x[k]))+(Math.abs(mean[l][k])));

										 		}


										 						for(int j=0;j<b;j++)
										 							{
										 							  mul[0][j]=0;
										 							  for(int k=0;k<b;k++)
										 									mul[0][j]+=sub[k][0];
										 							}

										 						dsq[l][i]=0;
										 						for(int k=0;k<b;k++)
										 						dsq[l][i]+=mul[0][k];



				  				                       /****Mixing second Distance*/
				  				                         if(flag_second==0)
				  				   						 dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
				  				   						/*End Mixing*/
                                        }



                   //...........BRAYCURTIS............................


                    else if(txt.compareTo("BrayCurtis")==0)
				     {

							    for(int k=0;k<b;k++)
							  	 sub[k][0]= Math.abs(x[k]+mean[l][k]);


							 					for(int j=0;j<b;j++)
							 					{
							 						mul[0][j]=0;
							 						for(int k=0;k<b;k++)
							 							mul[0][j]+=sub[k][0];
							 					}

							 					dsq[l][i]=0;
							 					for(int k=0;k<b;k++)
						                dsq[l][i]+=((Math.abs(x[k]-mean[l][k]))/mul[0][k]);


				   				  				     /****Mixing second Distance*/
				   				  				       if(flag_second==0)
				   				  				   	dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
				   				  				   	/*End Mixing*/
                                        }



                     //...........COSINE............................


                         else if(txt.compareTo("Cosine")==0)
				  				            {

									                  for(int j=0;j<b;j++)
											  	       {
											  	          sub[j][0]=0;
										                  for(int k=0;k<b;k++)
													sub[j][0]+= (x[k]*mean[l][k]);
														 }

                                                                                                                 for(int j=0;j<b;j++)
														 {
															mul[0][j]=0;
															subT[0][j]=0;
														 for(int k=0;k<b;k++)
														 {
															mul[0][j]+=(x[k]*x[k]);
															subT[0][j]+=(mean[l][k]*mean[l][k]);
															if(k==b-1)
															{
															 mul[0][j]=Math.sqrt(mul[0][j]);
															subT[0][j]=Math.sqrt(subT[0][j]);
															}
							 					                   }
													      }


																dsq[l][i]=0;
																for(int k=0;k<b;k++)
																 {
																  dsq[l][i]=(sub[k][0])/(mul[0][k]*subT[0][k]);
																	 if(k==b-1)
																	 {
																		dsq[l][i]=1-dsq[l][i];
																		}
																	}


				  				   				  				     /****Mixing second Distance*/
				  				   				  				      if(flag_second==0)
				  				   				  				      dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
				  				   				  				   	/*End Mixing*/

		                                         }



               //...........CORRELATION............................


              else if(txt.compareTo("Correlation")==0)
			  					 {

			                                  double sum=0.0;
			  								for(int k=0;k<b;k++)
			  								  sum+=x[k];



			  						        double add=0.0;
			  								for(int k=0;k<b;k++)
			  								   add+=mean[l][k];


			  						    for(int j=0;j<b;j++)
			  							 {
			  								sub[j][0]=0;
			  								for(int k=0;k<b;k++)
			  							    sub[j][0]+= (x[k]-(sum/b))*(mean[l][k]-((add)/b));
			  							 }

			  				             for(int j=0;j<b;j++)
			  							 {
			  								mul[0][j]=0;
			  								subT[0][j]=0;
			  								for(int k=0;k<b;k++)
			  								{
			  								 mul[0][j]+=((x[k]-((sum)/b))*(x[k]-((sum)/b)));
			  								 subT[0][j]+=((mean[l][k]-((add)/b))*(mean[l][k]-((add)/b)));
			  								 if(k==b-1)
			  								 {
			  								  mul[0][j]=Math.sqrt(mul[0][j]);
			  								 subT[0][j]=Math.sqrt(subT[0][j]);
			  								  }
			  						         }
			  								}


			  									dsq[l][i]=0;
			  									for(int k=0;k<b;k++)
			  									{
			  									 dsq[l][i]=(sub[k][0])/(mul[0][k]*subT[0][k]);
			  									 if(k==b-1)
			  									 {
			  									  dsq[l][i]=1-dsq[l][i];
			  									 }
			  								    }

			  								  				    /****Mixing second Distance*/
			  								  				   	 if(flag_second==0)
			  								  				   	 dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
			  								  				   	/*End Mixing*/
			  				}




			//...........NORMALIZED-SQUARED-EUCLIDEAN............................


			  	else if(txt.compareTo("Normalized-Squared-Euclidean")==0)
									 {

				                                double sum=0.0;
												for(int k=0;k<b;k++)
												  sum+=x[k];



										        double add=0.0;
												for(int k=0;k<b;k++)
												   add+=mean[l][k];


								             for(int j=0;j<b;j++)
											 {
												mul[0][j]=0;
												for(int k=0;k<b;k++)
												{
												 mul[0][j]+=((x[k]-(sum/b)-mean[l][k]+(add/b))*(x[k]-(sum/b)-mean[l][k]+(add/b)));
										         }
												}

                                           for(int j=0;j<b;j++)
										    {
										   	 subT[0][j]=0;
										   	for(int k=0;k<b;k++)
										   	subT[0][j]+= Math.abs((x[k]-(sum/b))*(x[k]-(sum/b)));
										   	 }



													dsq[l][i]=0;
													for(int k=0;k<b;k++)
													{

													 dsq[l][i]=(mul[0][k]/(subT[0][k]*2));

												    }

												  				    /****Mixing second Distance*/
												  				   	 if(flag_second==0)
												  				   	 dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
												  				   	/*End Mixing*/
								}




				//...........MEDIAN-ABSOLUTE-DIFFERENCE............................


					else if(txt.compareTo("Median-Absolute-Difference")==0)
			          {
						    for(int k=0;k<b;k++)
						    {
						       sub[k][0]= Math.abs(x[k]-mean[l][k]);

						     }

									double h = 0.0;
									for(int k=0;k<b;k++)
									{
										for(int u=k+1;u<b;u++)
										{
										if(sub[k][0]>sub[u][0])
										{
										  h=sub[k][0];
									      sub[k][0]=sub[u][0];
									      sub[u][0]=h;
										}

									}
								}
												    dsq[l][i]=0;
								                    double sum=0.0;
								                    double avgnw=0.0;
                                              if(b%2==0)
                                               {
											   sum=(sub[b/2][0]+sub[(b/2)+1][0])/2;
										       dsq[l][i]= (sum);
                   							  }
                                             else
                                             {

											   avgnw=(sub[((b+1)/2)][0]);
										       dsq[l][i]= (avgnw);

										    }

												  /****Mixing second Distance*/
												   	if(flag_second==0)
												   	dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
												   	/*End Mixing*/
                      }


		      }
	   			System.gc();

	   		  }

                          if(source==btClasi)
	   		   System.out.println("DSquare i,j is Calculated");
                          else if(source==btsyn)
                          System.out.println("Synthetic Image Calculations");



	   		//  System.out.println("status"+ st);
	   		}
	  		catch(Exception e)
	  		{
	  			 System.out.println("dSquare i,j Error: "+ e);
	  		}

                if(flag_second==1&&cb.isSelected())
                        return;


	///********* Calculating Meu Array by Distance vector for Fuzzy C-Means *************////

			if(classi==1)
			{

		  		try
		  		{
                                        double min=65535,max=-1;
		  			for(int j=0;j<count;j++)
		  			{
		  				for(int i=0;i<h*w;i++)
		  				{
		  					div=0.0;
		  					for(int k=0;k<count;k++)
		  					{
								//if((dsq[k][i])>= 0.0)
								//{
		  						div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));
							   //}
		  					}

		  					meu[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);

                                                        if(min>meu[j][i])
                                                            min=meu[j][i];

                                                        if(max<meu[j][i])
                                                            max=meu[j][i];

						   //}

		  				}

				   }


	System.gc();

				 System.out.println("Meu has been Computed for Fuzzy C-Means.....:P");
				 System.out.println("\nMaxmeu"+max);
			  	   		  System.out.println("\nMinmeu"+ min);

		  		}
		  		catch(Exception e)
		  		{

		  			System.out.println("FCM Meu Calculation Error : "+e);
		  		}
	   		}

	   		/******************Calculating meu Array for Noise Clustering without Entropy *************/

	   		else
	   		{

	   			if(classi==2)
	   			{
		   			try
		  		    {

		  			    flago=0;
		  			  	for(int j=0;j<count;j++)
		  				{
		  				for(int i=0;i<h*w;i++)
		  				{
		  					div=0.0;
		  					for(int k=0;k<count;k++)
		  					{
		  						div=div+ Math.pow((dsq[j][i]/dsq[k][i]),(1/(m-1)));
		  					}

		  					meu[j][i]=1/(div+Math.pow((dsq[j][i]/del),(1/(m-1))));
		  				    }
		  					System.gc();
		  				}
		  				for(int i=0;i<h*w;i++)
		  			  	{
		  			  	 double div1=0.0;
		  			  	 for(int j=0;j<count;j++)
		  			  	  {
		  			  	  	div1=div1+Math.pow((del/dsq[j][i]),(1/(m-1)));
		  			  	  }
		  			  	meu[count][i]=1/(div1+1.00);
		  			  	}
		  				System.out.println("Meu Computed.");
		  			}
		  			catch(Exception e)
		  			{
		  				System.out.println("Meu Calculation Error : "+e);
		  			}


		   			System.out.println("Meu has been Computed for Noise Clustering without Entropy.");
	   		    }


	   			else
	   			{

	   				/////////*********Calculating meu Array for Noise Clustering with Entropy *************/

	   				if(classi==3)
	   				{
	   					try
  						{

  			            flago=0;
  						for(int j=0;j<count;j++)
						{
			  				for(int i=0;i<h*w;i++)
			  				{
			  					div=0.0;
			  					for(int k=0;k<count;k++)
			  					{
			  						div=div+ Math.exp(-(dsq[k][i]/neo));
			  					}

			  					meu[j][i]=Math.exp(-(dsq[j][i]/neo))/(div+Math.exp(-del/neo));
			  				}
			  				System.gc();
			  			}

			  			for(int i=0;i<h*w;i++)
			  			{
			  			   double div1=0.0;
			  			   for(int j=0;j<count;j++)
			  			   {
			  			  	  div1=div1+Math.exp(-(dsq[j][i]/neo));
			  			   }
			  			   meu[count][i]=Math.exp(-(del/neo))/(div1+Math.exp(-(del/neo)));
			  			}
			  			System.out.println("Meu Computed.");
				  		}
				  		catch(Exception e)
				  		{
				  			System.out.println("Meu Calculation Error : "+e);
				  		}


	   					System.out.println("Meu has been Computed for Noise Clustering with Entropy.");

	   				}



	   				/////////*********Calculating meu Array for FCM with Entropy *************/

	   				else
	   				{
	   					if(classi==4)
	   					{
	   						try
					  		{

					  			for(int j=0;j<count;j++)
					  			{
					  				for(int i=0;i<h*w;i++)
					  				{
					  					div=0.0;
					  					for(int k=0;k<count;k++)
					  					{
					  						div=div+Math.exp(-(dsq[k][i])/neo);
					  					}

					  					meu[j][i]=(Math.exp(-(dsq[j][i])/neo)/div);

					  				}
					  				System.gc();
					  			}
					  			System.out.println("Meu Computed.");
					  		}
					  		catch(Exception e)
					  		{
					  			System.out.println("Meu Calculation Error : "+e);
					  		}

		   					System.out.println("Meu has been Computed for FCM with Entropy.");
	   					}

	   					/////////******** Calculating Meu Array For Possibilistic C-Means *********/////////

	   					else
	   					{
                                                          double maxp=-1,minp=65535;


                                                  if(classi==5)
                                                  {
					  		try
					  		{
					  			for(int j=0;j<count;j++)
					  			{
					  				for(int i=0;i<h*w;i++)
					  				{
					  					div=0.0;
					  					for(int k=0;k<count;k++)
					  					{
                                                                                    	div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));
					  					}

                                                                                meu[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);
                                            				}
					  				System.gc();
					  			}

                           		  			double val1=0.0;
					  			double val2=0.0;
					  			double []arrayEta=new double[count];


					  			for(int i=0;i<count;i++)
					  			{
					  				val1=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meu[i][j])>= 0.0)        //To overcome from undefined meu in case of Cosine,Correlation and Normalized-Squared-euclidean distances
										{
						  				val1=val1+Math.pow(meu[i][j],m);
									}
						  			}

						  			val2=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meu[i][j])>= 0.0)
										{
                                                                              val2=val2+(Math.pow(meu[i][j],m)*dsq[i][j]);
									        }
						  			}

						  			arrayEta[i]=val2/val1;
					  			}

					  			for(int k=0;k<count;k++)
					  			{
					  				System.out.println("Eta value for class "+(k+1)+" : "+arrayEta[k]);

					  			}

					  			for(int i=0;i<count;i++)
					  			{
					  				for(int j=0;j<h*w;j++)
					  				{
                                                                           	meu[i][j]=1/(1+(Math.pow((dsq[i][j]/arrayEta[i]),(1/(m-1)))));
                                                        		}
					  			}

					  			System.out.println("Meu has been Computed for Possibilistic C-Means.");
					  		}
					  		catch(Exception e)
					  		{
					  			System.out.println("PCM Meu Calculation Error : "+e);
					  		}
                                                  }
                                            else
                                                  {
                                                      if(classi==6)

                                                      		//Modified Possiblistic Cmeans
                                                      {
                                                      try
					  		{
					  			for(int j=0;j<count;j++)
					  			{
					  				for(int i=0;i<h*w;i++)
					  				{
					  					div=0.0;
					  					for(int k=0;k<count;k++)
					  					{
					  						div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));
					  					}

					  					meu[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);

					  				}
					  				System.gc();
					  			}


					  			double val1=0.0;
					  			double val2=0.0;
					  			double []arrayEta=new double[count];


					  			for(int i=0;i<count;i++)
					  			{
					  				val1=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meu[i][j])>= 0.0)        //To overcome from undefined meu in case of Cosine,Correlation and Normalized-Squared-euclidean distances
										{
						  				val1=val1+Math.pow(meu[i][j],m);
									}
						  			}

						  			val2=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meu[i][j])>= 0.0)
										{
						  				val2=val2+(Math.pow(meu[i][j],m)*dsq[i][j]);
									}
						  			}

						  			arrayEta[i]=val2/val1;
					  			}

					  			for(int k=0;k<count;k++)
					  			{
					  				System.out.println("Eta value for class "+(k+1)+" : "+arrayEta[k]);

					  			}

					  			for(int i=0;i<count;i++)
					  			{
					  				for(int j=0;j<h*w;j++)
					  				{
					  					meu[i][j]=Math.exp(-dsq[i][j]/arrayEta[i]);
					  				}
					  			}


					  			System.out.println("Meu has been Computed for Modified Possibilistic C-Means.");
					  		}
					  		catch(Exception e)
					  		{
					  			System.out.println("MPCM Meu Calculation Error : "+e);
					  		}

                                                      }
                            else
                            {							//Improved possiblistic c means
							if(classi==7)
							{
                          	 try
					  		{
					  			for(int j=0;j<count;j++)
					  			{
					  				for(int i=0;i<h*w;i++)
					  				{
					  					div=0.0;
					  					for(int k=0;k<count;k++)
					  					{
					  						div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));
					  					}

					  					meu[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);

					  				}
					  				System.gc();
					  			}



					  			double val1=0.0;
					  			double val2=0.0,sum=0.0;
					  			double []arrayEta=new double[count];


					  			for(int i=0;i<count;i++)
					  			{
					  				val1=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meu[i][j])>= 0.0)        //To overcome from undefined meu in case of Cosine,Correlation and Normalized-Squared-euclidean distances
										{
						  				val1=val1+Math.pow(meu[i][j],m);
									}
						  			}

						  			val2=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meu[i][j])>= 0.0)
										{
						  				val2=val2+(Math.pow(meu[i][j],m)*dsq[i][j]);
									}
						  			}

						  			arrayEta[i]=val2/val1;
					  			}

					  			for(int k=0;k<count;k++)
					  			{
					  				System.out.println("Eta value for class "+(k+1)+" : "+arrayEta[k]);

					  			}
                                                                int i,j,k;
                                                                for(i=0;i<count;i++)
                                                                {
                                                                	for(k=0;k<h*w;k++)
                                                                        {
                                                                                sum=0.0;
                                                                                for(j=0;j<count;j++)
                                                                                {
                                                                                   sum+=Math.pow(((arrayEta[i]*(1-Math.exp(-dsq[i][k]/arrayEta[i])))/(arrayEta[j]*(1-Math.exp(-dsq[j][k]/arrayEta[j])))),(2/(m-1)));
                                                                                }
                                                                                meu[i][k]=1/sum;
                                                                        		//System.out.println("meu:ipcm: "+meu[i][k]);


                                                                        }
                                                                }


					  			System.out.println("Meu has been Computed for Improved Possibilistic C-Means.");
					  		}
					  		catch(Exception e)
					  		{
					  			System.out.println("IPCM Meu Calculation Error : "+e);
					  		}

                             }
                                 else
                                        {
                                            	if(classi==9)
				{
                               {

                                    double fac=0.0,fa=0.0;
                                     double aa=0;int ws=0;
                                     double sec=0.0;int d=0;
                                     int xs,xe,ys,ye,xw,yw;
                                     int lim=0;
                                       if(h<w)
                                           sec=h;
                                       else
                                           sec=w;

                       while(aa<=0.0)
                       {
                                   String input = JOptionPane.showInputDialog("Please Enter The Value Of a (1 < a < infinity) ");
			 aa = Double.parseDouble(input);
                      }
                      while(ws<=0 || (ws%2==0))
                       {
        String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
			 ws = Integer.parseInt(inpt);
                      }
                       xw=ws;
                       yw=ws;
                       System.out.println("\nWindow Size="+ws);

                                    try
		  		{
                                        double min=65535,max=-1;
		  			for(int j=0;j<count;j++)
		  			{
                                          	   for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {

                                                for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                {
                                                     fac=0.0;
                                                     lim=0;

                                                     if(ws!=1)
                                                     {
                                                           if((x1-(xw/2))<0)
                                                                      xs=0;
                                                                       else
                                                                  xs=x1-(xw/2);

                                                              if((x1+(xw/2))>=w)
                                                                      xe=w-1;
                                                                   else
                                                                    xe=x1+(xw/2);

                                                                  if((y1-(yw/2))<0)
                                                                          ys=0;
                                                                             else
                                                                            ys=y1-(yw/2);

                                                                        if((y1+(yw/2))>=h)
                                                                          ye=h-1;
                                                                                 else
                                                                          ye=y1+(yw/2);
                                                     }
                                                     else
                                                     {
                                                             xe=0;xs=0;
                                                             ye=0;ys=0;
                                                             aa=0;
                                                             }


                                           	    for(int sd=ys;sd<=ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<=xe;gd++)
                                                              {
                                                                  if(sd!=x1 || gd!=y1)
                                                                  {
                                                                      d=((sd)*w)+(gd);
                                                                      if(d<(h*w))
                                                                      {
                                                                             fac+=dsq[j][d];
                                                                      lim++;
                                                                      }
                                                                     }
                                                                  }

                                                                }

                                                   fac=fac*(aa/(lim));



						 int i=((y1)*w)+(x1);
                                     	              div=0.0;
		  					for(int k=0;k<count;k++)
		  					{
                                                          fa=0.0;
                                                       lim=0;

                                                       if(ws!=1)
                                                       {
                                                           if((x1-(xw/2))<0)
                                                                      xs=0;
                                                                       else
                                                                  xs=x1-(xw/2);

                                                              if((x1+(xw/2))>=w)
                                                                      xe=w-1;
                                                                   else
                                                                    xe=x1+(xw/2);

                                                                  if((y1-(yw/2))<0)
                                                                          ys=0;
                                                                             else
                                                                            ys=y1-(xw/2);

                                                                        if((y1+(yw/2))>=h)
                                                                          ye=h-1;
                                                                                 else
                                                                          ye=y1+(yw/2);
                                                       }
                                                        else
                                                     {
                                                             xe=0;xs=0;
                                                             ye=0;ys=0;
                                                             }




                                           	    for(int sd=ys;sd<=ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<=xe;gd++)
                                                              {
                                                                  if(sd!=x1 || gd!=y1)
                                                                  {

                                                                      d=((sd)*w)+(gd);
                                                                      if(d<(h*w))
                                                                      {
                                                                      fa+=dsq[k][d];
                                                                      lim++;
                                                                      }

                                                                    }
                                                                  }

                                                                }
                                                          fa=fa*(aa/(lim));

								div=div+Math.pow((1/(fa+dsq[k][i])),(1/(m-1)));
							}

		  					meu[j][i]=(Math.pow((1/(fac+dsq[j][i])),(1/(m-1)))/div);

                                                        if(min>meu[j][i])
                                                            min=meu[j][i];

                                                        if(max<meu[j][i])
                                                            max=meu[j][i];

						   }

		  				}

				   }

	System.gc();

				  System.out.println("Classification is Completed for Fuzzy C-Means S.");
                                       System.out.println("\nMaxmeu"+max);
			  	   		  System.out.println("\nMinmeu"+ min);

		  		}
		  		catch(Exception e)
		  		{

		  		  System.out.println("Classification Fuzzy C-Means S Error  ");
                                      }
                                }
                                                }


                                                else
                                                {
                                                      if(classi==10)
                                                  {   double fac=0.0,fa=0.0;
                                     double aa=0;int ws=0;
                                     double sec=0.0;int d=0;
                                     int xs,xe,ys,ye,xw,yw;
                                     int lim=0;
                                       if(h<w)
                                           sec=h;
                                       else
                                           sec=w;

                       while(aa<=0.0)
                       {
                                   String input = JOptionPane.showInputDialog("Please Enter The Value Of a (1 < a < infinity) ");
			 aa = Double.parseDouble(input);
                      }
                      while(ws<=0 || (ws%2==0))
                       {
        String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
			 ws = Integer.parseInt(inpt);
                      }
                       xw=ws;
                       yw=ws;
                       System.out.println("\nWindow Size="+ws);

					  		try
					  		{
                                                            for(int j=0;j<count;j++)
		  			{
                                          	   for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {

                                                for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                {
                                                     fac=0.0;
                                                     lim=0;

                                                     if(ws!=0)
                                                     {
                                                             if((x1-(xw/2))<0)
                                                                      xs=0;
                                                                       else
                                                                  xs=x1-(xw/2);

                                                              if((x1+(xw/2))>=w)
                                                                      xe=w-1;
                                                                   else
                                                                    xe=x1+(xw/2);

                                                                  if((y1-(yw/2))<0)
                                                                          ys=0;
                                                                             else
                                                                            ys=y1-(yw/2);

                                                                        if((y1+(yw/2))>=h)
                                                                          ye=h-1;
                                                                                 else
                                                                          ye=y1+(yw/2);
                                                     }
                                                      else
                                                     {
                                                             xe=0;xs=0;
                                                             ye=0;ys=0;
                                                             aa=0;
                                                             }



                                           	    for(int sd=ys;sd<=ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<=xe;gd++)
                                                              {
                                                                  if(sd!=x1 || gd!=y1)
                                                                  {
                                                                      d=((sd)*w)+(gd);
                                                                      if(d<(h*w))
                                                                      {     fac+=dsq[j][d];
                                                                      lim++;
                                                                  }
                                                                    }
                                                                  }

                                                                }
                                                   fac=fac*(aa/(lim));



						 int i=((y1)*w)+(x1);
                                     	              div=0.0;
		  					for(int k=0;k<count;k++)
		  					{
                                                          fa=0.0;
                                                       lim=0;

                                                       if(ws!=1)
                                                       {
                                                             if((x1-(xw/2))<0)
                                                                      xs=0;
                                                                       else
                                                                  xs=x1-(xw/2);

                                                              if((x1+(xw/2))>=w)
                                                                      xe=w-1;
                                                                   else
                                                                    xe=x1+(xw/2);

                                                                  if((y1-(yw/2))<0)
                                                                          ys=0;
                                                                             else
                                                                            ys=y1-(yw/2);

                                                                        if((y1+(yw/2))>=h)
                                                                          ye=h-1;
                                                                                 else
                                                                          ye=y1+(yw/2);
                                                       }
                                                       else
                                                     {
                                                             xe=0;xs=0;
                                                             ye=0;ys=0;
                                                             aa=0;
                                                             }




                                           	    for(int sd=ys;sd<=ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<=xe;gd++)
                                                              {
                                                                  if(sd!=x1 || gd!=y1)
                                                                  {
                                                                      d=((sd)*w)+(gd);
                                                                      if(d<(h*w))
                                                                      {  fa+=dsq[k][d];
                                                                      lim++;
                                                                      }

                                                                    }
                                                                  }

                                                                }
                                                          fa=fa*(aa/(lim));

								div=div+Math.pow((1/(fa+dsq[k][i])),(1/(m-1)));
							}

		  					meu[j][i]=(Math.pow((1/(fac+dsq[j][i])),(1/(m-1)))/div);



		  				}

				   }
                                        }

	System.gc();


                           		  			double val1=0.0;
					  			double val2=0.0;
					  			double []arrayEta=new double[count];


					  			for(int i=0;i<count;i++)
					  			{
					  				val1=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meu[i][j])>= 0.0)        //To overcome from undefined meu in case of Cosine,Correlation and Normalized-Squared-euclidean distances
										{
						  				val1=val1+Math.pow(meu[i][j],m);
									}
						  			}

						  			val2=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meu[i][j])>= 0.0)
										{
                                                                              val2=val2+(Math.pow(meu[i][j],m)*dsq[i][j]);
									        }
						  			}

						  			arrayEta[i]=val2/val1;
					  			}

					  			for(int k=0;k<count;k++)
					  			{
					  				System.out.println("Eta value for class "+(k+1)+" : "+arrayEta[k]);

					  			}

					  			for(int j=0;j<count;j++)
					  			{

                                          	   for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {

                                                for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                {
                                                     fac=0.0;
                                                     lim=0;

                                                     if(ws!=1)
                                                     {

		  			   if((x1-(xw/2))<0)
                                                                      xs=0;
                                                                       else
                                                                  xs=x1-(xw/2);

                                                              if((x1+(xw/2))>=w)
                                                                      xe=w-1;
                                                                   else
                                                                    xe=x1+(xw/2);

                                                                  if((y1-(yw/2))<0)
                                                                          ys=0;
                                                                             else
                                                                            ys=y1-(yw/2);

                                                                        if((y1+(yw/2))>=h)
                                                                          ye=h-1;
                                                                                 else
                                                                          ye=y1+(yw/2);
                                                     }
                                                      else
                                                     {
                                                             xe=0;xs=0;
                                                             ye=0;ys=0;
                                                             aa=0;
                                                             }




                                           	    for(int sd=ys;sd<=ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<=xe;gd++)
                                                              {
                                                                  if(sd!=x1 || gd!=y1)
                                                                  {
                                                                      d=((sd)*w)+(gd);
                                                                      if(d<(h*w))
                                                                      {     fac+=dsq[j][d];
                                                                      lim++;
                                                                      }

                                                                    }
                                                                  }

                                                                }
                                                   fac=fac*(aa/(lim));
                                               			 int i=((y1)*w)+(x1);
                                     	                   	meu[j][i]=1/(1+(Math.pow(((dsq[j][i]+fac)/arrayEta[j]),(1/(m-1)))));
                                                                             //   System.out.println("\nMeu:"+meu[i][j]);


					  				}
                                                        }
					  			}

					  			System.out.println("Meu has been Computed for Possibilistic C-Means_S.");
					  		}

					  		catch(Exception e)
					  		{
					  			System.out.println("PCM_S Meu Calculation Error : "+e);
					  		}

                                                  }
                                             else
                                                {
                                                    if(classi==12)
				              {



                                    {

                                    double fac=0.0;
                                    double mx[]=new double[b];
                                    double mn[]=new double[b];
                                     double aa=0;int ws=0;
                                     int sec=0;int d=0,lim=0;
                                     int xs,xe,ys,ye,xw,yw;
                                       if(h<w)
                                           sec=h;
                                       else
                                           sec=w;

                       while(aa<=0.0)
                       {
                                   String input = JOptionPane.showInputDialog("Please Enter The Value Of a (1 < a < infinity) ");
			 aa = Double.parseDouble(input);
                      }
                       div=0.0;

                      while(ws<=0 || (ws%2==0))
                       {
        String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
			 ws = Integer.parseInt(inpt);
                      }

                      xw=ws;
                      yw=ws;
                       System.out.println("\nWindow Size="+ws);
                                    try
		  		{
                                        double min=65535,max=-1;
		  			for(int j=0;j<b;j++)
		  			{
                                          	   for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {

                                                for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                {
                                                     fac=0.0;
                                                     lim=0;

                                                     if(ws!=1)
                                                     {
                                                           if((x1-(xw/2))<0)
                                                                      xs=0;
                                                                       else
                                                                  xs=x1-(xw/2);

                                                              if((x1+(xw/2))>=w)
                                                                      xe=w-1;
                                                                   else
                                                                    xe=x1+(xw/2);

                                                                  if((y1-(yw/2))<0)
                                                                          ys=0;
                                                                             else
                                                                            ys=y1-(yw/2);

                                                                        if((y1+(yw/2))>=h)
                                                                          ye=h-1;
                                                                                 else
                                                                          ye=y1+(yw/2);
                                                     }
                                                      else
                                                     {
                                                             xe=0;xs=0;
                                                             ye=0;ys=0;
                                                             aa=0;
                                                             }




                                           	    for(int sd=ys;sd<=ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<=xe;gd++)
                                                              {
                                                                  if(sd!=x1 || gd!=y1)
                                                                  {
                                                                      d=((sd)*w)+(gd);
                                                                      if(d<(h*w))
                                                                      {    fac+=xnew[j][d];
                                                                      lim++;
                                                                      }

                                                                    }
                                                                  }

                                                                }

                                                   fac=fac*(aa/(lim));



                                                      int i=((y1)*w)+(x1);

						      div=xnew[j][i]+fac;

                                                      meun[j][i]=div/(1+aa);

                                                      if(mn[j]>meun[j][i])
                                                          mn[j]=meun[j][i];


                                                      if(mx[j]<meun[j][i])
                                                          mx[j]=meun[j][i];
                                               //       System.out.println("\nmeu="+meu[j][i]+"\tdiv="+div+"\tx="+xnew[j][i]+"\tfac="+fac);


				   }

	System.gc();


		  		}
                                        }

                                }
		  		catch(Exception e)
		  		{

		  		  System.out.println("Synthetic Error  ");
                                      }

                                System.out.println("Synthetic Image Formation Has Been Completed.");
                                          }

                                                }


                             else
                             {
                                                                                try
								 			{
										double[][] dis=new double[count][h*w];
							                  	int b = Bands;
								        	prob = new double[count];

										double[] det = new double[count];
										double[] max = new double[count];
										double[] min = new double[count];


										for(int j=0;j<count;j++)
										{
																	max[j]=-1;
																	min[j]=1000000000;
																}

																for(int j=0;j<count;j++)
																{
																	for(int i=0;i<h*w;i++)
																	{
																		if(dsq[j][i]>=max[j])
																		{
																			max[j]=dsq[j][i];
																		}
																		if(dsq[j][i]<=min[j])
																		{
																			min[j]=dsq[j][i];
																		}
																	}
																}
																if((txt.compareTo("Normalized-Squared-Euclidean")==0)||(txt.compareTo("Canberra")==0)||(txt.compareTo("Correlation")==0))
																{
															    	for(int j=0;j<count;j++)
																	{
															    		for(int i=0;i<h*w;i++)
																		{
																		dis[j][i]=dsq[j][i];
																		}
																	}
																}
																else
																{
																	for(int j=0;j<count;j++)
																	{
	    	       														for(int i=0;i<h*w;i++)
	    																{
																		     dis[j][i]=8*((dsq[j][i]-min[j])/(max[j]-min[j]));
																		}
	    															}
																}

																//finding determinant of variance covariance matrix
																System.out.println("Determinants are:-");
																for(int i=0;i<count;i++)
																{
																	det[i]=determinant(c1[i],b);
																	System.out.println(det[i]);
																}
																System.out.println("Bands"+b);

																for(int i=0;i<h*w;i++)
																{

																	double sum=0.0;
																	for(int j=0;j<count;j++)
																	{
																		prob[j]=1/Math.pow(2*Math.PI,(b/2));
																		prob[j]=prob[j]*(1/Math.pow(det[j],0.5));
																		prob[j]=prob[j]*Math.pow(Math.E,-0.5*dis[j][i]);
																		sum+=prob[j];
																		//System.out.println(dsq[j][i]);
																	}

																	for(int j=0;j<count;j++)
																	{
																		prob[j]=1/Math.pow(2*Math.PI,(b/2));


                                                                                                                                                prob[j]=prob[j]*(1/Math.pow(det[j],0.5));
																		prob[j]=prob[j]*Math.pow(Math.E,-0.5*dis[j][i]);
																		meu[j][i]=prob[j]/sum;
																		//System.out.println(meu[j][i]);
																    }
																}


								 					  			System.out.println("Meu has been Computed for Maximum Likelihood Classifier.");
								 					  		}
								 					  		catch(Exception e)
								 					  		{
								 					  			System.out.println("MLC Meu Calculation Error : "+e);
								 					  		}


							 }
                                                    }
                             }

                                                  }

	   					}
	   				}
                                                }
	   			}
	   		}
                        }



                        if(advclassi==1)
				              {
                                    {

                                    double fac=0.0;
                                    double maxa[]=new double[count];
                                    double mina[]=new double[count];
                                    double [][] dsw=new double[count][h*w];
                                     int ws=0;
                                     double lim=0.0;int d=0,sec=0;
                                     int xs,xe,ys,ye,xw,yw;
                                       if(h<w)
                                           sec=h;
                                       else
                                           sec=w;

                       div=0.0;

                      while(ws<=0 || (ws%2==0))
                       {
        String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
			 ws = Integer.parseInt(inpt);
                      }

                      double gval[][]=new double[count][h*w];
                      xw=ws;
                      yw=ws;
                       System.out.println("\nWindow Size="+ws);

                      /* try
                       {
                           for(int j=0;j<count;j++)
		  			{
                                            for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {
                                                      for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                        {
                                                              int i=((y1)*w)+(x1);
                                                      gval[j][i]=0.0;

                                                	div=0.0;
		  					for(int k=0;k<count;k++)
		  					{
								div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));
							}

		  					meunm[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);



		  				}
                                                   }

				   }
                       }
                       catch(Exception e)
                       {
                       }*/

                                    try
		  		{
                                       for(int j=0;j<count;j++)
		  			{
                                            mina[j]=65535;
                                        maxa[j]=-2;

                                            for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {
                                                for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                {

                                                     fac=0.0;


                                                        if(ws!=1)
                                                        {
                                                                if((x1-(xw/2))<0)
                                                                      xs=0;
                                                                       else
                                                                  xs=x1-(xw/2);

                                                              if((x1+(xw/2))>=w)
                                                                      xe=w-1;
                                                                   else
                                                                    xe=x1+(xw/2);

                                                                  if((y1-(yw/2))<0)
                                                                          ys=0;
                                                                             else
                                                                            ys=y1-(yw/2);

                                                                        if((y1+(yw/2))>=h)
                                                                          ye=h-1;
                                                                                 else
                                                                          ye=y1+(yw/2);


                                                        }
                                                        else
                                                        {
                                                            xs=0;ys=0;ye=0;xe=0;
                                                        }

                                                      int i=((y1)*w)+(x1);


                                     	            for(int sd=ys;sd<=ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<=xe;gd++)
                                                              {
                                                                      d=((sd)*w)+(gd);
                                                                     if(sd!=y1 || gd!=x1)
                                                                        {
                                                                                                              for(int k=0;k<b;k++)
												     sub[k][0]= xnew[k][d]-xnew[k][i];

													 for(int k=0;k<b;k++)
													 subT[0][k]=sub[k][0];

													 for(int jh=0;jh<b;jh++)
													 {

                                                                                                         mul[0][jh]=0;
													 for(int k=0;k<b;k++)
													 mul[0][jh]+=subT[0][k]*c1[j][k][jh];

													 }

													 dsw[j][d]=0;
													 for(int k=0;k<b;k++)
													 dsw[j][d]+=mul[0][k]*sub[k][0];

                                                                     gval[j][i]+=((1/(1+dsw[j][d]))*(Math.pow((1-meu[j][d]),m))*(dsq[j][d]));
                                                                        }

                                                                  }

                                                                }



                                                }

	                                 System.gc();
                                 }
                               }
                                }
		  		catch(Exception e)
		  		{
                         	  System.out.println("FLICM Error  ");
                                      }

                                    try
                                    {
                                       for(int j=0;j<count;j++)
		  			{
		  				for(int i=0;i<h*w;i++)
		  				{
		  					div=0.0;
		  					for(int k=0;k<count;k++)
		  					{
							div=div+Math.pow((1/(dsq[k][i]+gval[k][i])),(1/(m-1)));
					        	}

		  					meunm[j][i]=(Math.pow((1/(dsq[j][i]+gval[j][i])),(1/(m-1)))/div);


		  				}

				   }



                                    }
                                    catch(Exception e)
                                    {

                                    }
                                          }
                                   System.out.println("FLICM Classification has been Completed.");

                                                }

                                                            else  if(advclassi==2)
				              {
                                    {

                                    double maxa[]=new double[count];
                                    double mina[]=new double[count];
                                     int ws=0;
                                     double lim=0.0;int d=0,sec=0;
                                     int xs,xe,ys,ye,xw,yw;
                                       if(h<w)
                                           sec=h;
                                       else
                                           sec=w;

                       div=0.0;

                      while(ws<=0 || (ws%2==0) )
                       {
        String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
			 ws = Integer.parseInt(inpt);
                      }

                      double sval=0.0;
                      xw=ws;
                      yw=ws;
                       System.out.println("\nWindow Size="+ws);


                                    try
		  		{
                                       for(int j=0;j<count;j++)
		  			{
                                            mina[j]=65535;
                                        maxa[j]=-2;

                                            for(int y1=0;y1<h;y1++)
                                            {
                                                for(int x1=0;x1<w;x1++)
                                                {
                                                       if(ws!=1)
                                                        {
                                                               if((x1-(xw/2))<0)
                                                                      xs=0;
                                                                       else
                                                                  xs=x1-(xw/2);

                                                              if((x1+(xw/2))>=w)
                                                                      xe=w-1;
                                                                   else
                                                                    xe=x1+(xw/2);

                                                                  if((y1-(yw/2))<0)
                                                                          ys=0;
                                                                             else
                                                                            ys=y1-(yw/2);

                                                                        if((y1+(yw/2))>=h)
                                                                          ye=h-1;
                                                                                 else
                                                                          ye=y1+(yw/2);
     }
                                                        else
                                                        {
                                                            xs=0;ys=0;ye=0;xe=0;
                                                        }

                                                      int i=((y1)*w)+(x1);
                                                      int dij=1,num=1;
                                                      double heat=0.0;
                                                      double fact[]=new double[count];



                                                      for(int jy=0;jy<count;jy++)
                                                                     {

                                                              heat=0.0;
                                                    for(int sd=ys;sd<=ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<=xe;gd++)
                                                              {num++;
                                                                          d=((sd)*w)+(gd);
                                                                     if(sd!=y1 || gd!=x1)
                                                                        {
                                                                            if((Math.abs(gd-x1))>(Math.abs(sd-y1)))
                                                                                dij=(Math.abs(gd-x1));

                                                                            else if((Math.abs(gd-x1))<(Math.abs(sd-y1)))
                                                                                dij=(Math.abs(sd-y1));

                                                                            else
                                                                                  dij=(Math.abs(sd-y1));


                                                                           sval=(meu[jy][d]*meu[jy][i])/(dij*dij);
                                                                          heat+=(1-sval)*(dsq[jy][d]);
                                                                        }

                                                                     }
                                                                  }
                                                                  fact[jy]=heat;
                                                                }
                                                      num=num/count;

                                                    div=0.0;
                                                    for(int k=0;k<count;k++)
		  					{
							div=div+Math.pow((1/(dsq[k][i]+(fact[k]/num))),(1/(m-1)));
					        	}
                            			meunm[j][i]=(Math.pow((1/(dsq[j][i]+(fact[j]/num))),(1/(m-1)))/div);
                                      }
                                         System.gc();
                                 }
                               }
                                }
		  		catch(Exception e)
		  		{
                         	  System.out.println("ADFLICM Error  ");
                                      }

                                          }
                                   System.out.println("ADFLICM Classification has been Completed.");

                                                }


                                               else  if(advclassi==3)
				              {

                                    double maxa[]=new double[count];
                                    double mina[]=new double[count];
                                     int ws=0;
                                     double lim=0.0;int d=0,sec=0;
                                     int xs,xe,ys,ye,xw,yw;
                                       if(h<w)
                                           sec=h;
                                       else
                                           sec=w;

                       div=0.0;

                      while(ws<=0 || (ws%2==0) )
                       {
        String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
			 ws = Integer.parseInt(inpt);
                      }

                      double sval=0.0;
                      xw=ws;
                      yw=ws;
                       System.out.println("\nWindow Size="+ws);


                                   try
		  		{//try
                                       for(int j=0;j<count;j++)
		  			{
                                            mina[j]=65535;
                                        maxa[j]=-2;

                                            for(int y1=0;y1<h;y1++)
                                            {
                                                for(int x1=0;x1<w;x1++)
                                                {



                                                        if(ws!=1)
                                                        {
                                                               if((x1-(xw/2))<0)
                                                                      xs=0;
                                                                       else
                                                                  xs=x1-(xw/2);

                                                              if((x1+(xw/2))>=w)
                                                                      xe=w-1;
                                                                   else
                                                                    xe=x1+(xw/2);

                                                                  if((y1-(yw/2))<0)
                                                                          ys=0;
                                                                             else
                                                                            ys=y1-(yw/2);

                                                                        if((y1+(yw/2))>=h)
                                                                          ye=h-1;
                                                                                 else
                                                                          ye=y1+(yw/2);


                                                        }
                                                        else
                                                        {
                                                            xs=0;ys=0;ye=0;xe=0;
                                                        }

                                                      int i=((y1)*w)+(x1);
                                                      int dij=1,num=1;
                                                      double heat=0.0;
                                                      double fact[]=new double[count];



                                                      for(int jy=0;jy<count;jy++)
                                                                     {

                                                              heat=0.0;
                                                    for(int sd=ys;sd<=ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<=xe;gd++)
                                                              {num++;
                                                                          d=((sd)*w)+(gd);
                                                                     if(sd!=y1 || gd!=x1)
                                                                        {
                                                                            if((Math.abs(gd-x1))>(Math.abs(sd-y1)))
                                                                                dij=(Math.abs(gd-x1));

                                                                            else if((Math.abs(gd-x1))<(Math.abs(sd-y1)))
                                                                                dij=(Math.abs(sd-y1));

                                                                            else
                                                                                  dij=(Math.abs(sd-y1));


                                                                           sval=(meu[jy][d]*meu[jy][i])/(dij*dij);
                                                                          heat+=(1-sval)*(dsq[jy][d]);
                                                                        }

                                                                     }
                                                                  }
                                                                  fact[jy]=heat;
                                                                }
                                                      num=num/count;

                                                    div=0.0;
                                                    for(int k=0;k<count;k++)
		  					{
							div=div+Math.pow((1/(dsq[k][i]+(fact[k]/num))),(1/(m-1)));
					        	}
                            			meunm[j][i]=(Math.pow((1/(dsq[j][i]+(fact[j]/num))),(1/(m-1)))/div);
                                      }
                                  //       System.gc();
                                 }
                               }
                                       double val1=0.0;
					  			double val2=0.0;
					  			double []arrayEta=new double[count];


					  			for(int i=0;i<count;i++)
					  			{
					  				val1=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meunm[i][j])>= 0.0)        //To overcome from undefined meu in case of Cosine,Correlation and Normalized-Squared-euclidean distances
										{
						  				val1=val1+Math.pow(meunm[i][j],m);
									}
						  			}

						  			val2=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meunm[i][j])>= 0.0)
										{
                                                                              val2=val2+(Math.pow(meunm[i][j],m)*dsq[i][j]);
									        }
						  			}

						  			arrayEta[i]=val2/val1;
					  			}

					  			for(int k=0;k<count;k++)
					  			{
					  				System.out.println("Eta value for class "+(k+1)+" : "+arrayEta[k]);

					  			}

                                                                for(int j=0;j<count;j++)
		  			{
                                            mina[j]=65535;
                                        maxa[j]=-2;

                                            for(int y1=0;y1<h;y1++)
                                            {
                                                for(int x1=0;x1<w;x1++)
                                                {



                                                        if(ws!=1)
                                                        {
                                                                if((x1-(xw/2))<0)
                                                                      xs=0;
                                                                       else
                                                                  xs=x1-(xw/2);

                                                              if((x1+(xw/2))>=w)
                                                                      xe=w-1;
                                                                   else
                                                                    xe=x1+(xw/2);

                                                                  if((y1-(yw/2))<0)
                                                                          ys=0;
                                                                             else
                                                                            ys=y1-(yw/2);

                                                                        if((y1+(yw/2))>=h)
                                                                          ye=h-1;
                                                                                 else
                                                                          ye=y1+(yw/2);


                                                        }
                                                        else
                                                        {
                                                            xs=0;ys=0;ye=0;xe=0;
                                                        }

                                                      int i=((y1)*w)+(x1);
                                                      int dij=1,num=1;
                                                      double heat=0.0;
                                                      double fact[]=new double[count];



                                                      for(int jy=0;jy<count;jy++)
                                                                     {

                                                              heat=0.0;
                                                    for(int sd=ys;sd<=ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<=xe;gd++)
                                                              {num++;
                                                                          d=((sd)*w)+(gd);
                                                                     if(sd!=y1 || gd!=x1)
                                                                        {
                                                                            if((Math.abs(gd-x1))>(Math.abs(sd-y1)))
                                                                                dij=(Math.abs(gd-x1));

                                                                            else if((Math.abs(gd-x1))<(Math.abs(sd-y1)))
                                                                                dij=(Math.abs(sd-y1));

                                                                            else
                                                                                  dij=(Math.abs(sd-y1));


                                                                           sval=(meu[jy][d]*meu[jy][i])/(dij*dij);
                                                                          heat+=(1-sval)*(dsq[jy][d]);
                                                                        }

                                                                     }
                                                                  }
                                                                  fact[jy]=heat;
                                                                }
                                                      num=num/count;

                                                    div=0.0;

                                                    meunm[j][i]=1/(1+(Math.pow(((dsq[j][i]+(fact[j]/num))/arrayEta[j]),(1/(m-1)))));

                                      }
                                  //       System.gc();
                                 }
                               }


                                }
		  	catch(Exception e)
		  		{
                         	  System.out.println("ADPLICM Error  ");
                                      }
                                              System.out.println("ADPLICM Classification has been Completed.");

                                              }


                                               else  if(advclassi==4)

				              {
                                    {

                                    double fac=0.0;
                                    double maxa[]=new double[count];
                                    double mina[]=new double[count];
                                    double [][] dsw=new double[count][h*w];
                                     int ws=0;
                                     double lim=0.0;int d=0,sec=0;
                                     int xs,xe,ys,ye,xw,yw;
                                       if(h<w)
                                           sec=h;
                                       else
                                           sec=w;

                       div=0.0;

                      while(ws<=0 || (ws%2==0))
                       {
        String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
			 ws = Integer.parseInt(inpt);
                      }

                      double gval[][]=new double[count][h*w];
                      xw=ws;
                      yw=ws;
                       System.out.println("\nWindow Size="+ws);


                                    try
		  		{
                                       for(int j=0;j<count;j++)
		  			{
                                            mina[j]=65535;
                                        maxa[j]=-2;

                                            for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {
                                                for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                {

                                                     fac=0.0;


                                                        if(ws!=1)
                                                        {
                                                                 if((x1-(xw/2))<0)
                                                                      xs=0;
                                                                       else
                                                                  xs=x1-(xw/2);

                                                              if((x1+(xw/2))>=w)
                                                                      xe=w-1;
                                                                   else
                                                                    xe=x1+(xw/2);

                                                                  if((y1-(yw/2))<0)
                                                                          ys=0;
                                                                             else
                                                                            ys=y1-(yw/2);

                                                                        if((y1+(yw/2))>=h)
                                                                          ye=h-1;
                                                                                 else
                                                                          ye=y1+(yw/2);


                                                        }
                                                        else
                                                        {
                                                            xs=0;ys=0;ye=0;xe=0;
                                                        }

                                                      int i=((y1)*w)+(x1);


                                     	            for(int sd=ys;sd<=ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<=xe;gd++)
                                                              {
                                                                      d=((sd)*w)+(gd);
                                                                     if(sd!=y1 || gd!=x1)
                                                                        {
                                                                                                              for(int k=0;k<b;k++)
												     sub[k][0]= xnew[k][d]-xnew[k][i];

													 for(int k=0;k<b;k++)
													 subT[0][k]=sub[k][0];

													 for(int jh=0;jh<b;jh++)
													 {

                                                                                                         mul[0][jh]=0;
													 for(int k=0;k<b;k++)
													 mul[0][jh]+=subT[0][k]*c1[j][k][jh];

													 }

													 dsw[j][d]=0;
													 for(int k=0;k<b;k++)
													 dsw[j][d]+=mul[0][k]*sub[k][0];

                                                                     gval[j][i]+=((1/(1+dsw[j][d]))*(Math.pow((1-meu[j][d]),m))*(dsq[j][d]));
                                                                        }

                                                                  }

                                                                }



                                                }

	                                 System.gc();
                                 }
                               }
                                }
		  		catch(Exception e)
		  		{
                         	  System.out.println("PLICM Error  ");
                                      }

                                    try
                                    {
                                       for(int j=0;j<count;j++)
		  			{
		  				for(int i=0;i<h*w;i++)
		  				{
		  					div=0.0;
		  					for(int k=0;k<count;k++)
		  					{
							div=div+Math.pow((1/(dsq[k][i]+gval[k][i])),(1/(m-1)));
					        	}

		  					meunm[j][i]=(Math.pow((1/(dsq[j][i]+gval[j][i])),(1/(m-1)))/div);


		  				}

				   }

			double val1=0.0;
					  			double val2=0.0;
					  			double []arrayEta=new double[count];


					  			for(int i=0;i<count;i++)
					  			{
					  				val1=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meunm[i][j])>= 0.0)        //To overcome from undefined meu in case of Cosine,Correlation and Normalized-Squared-euclidean distances
										{
						  				val1=val1+Math.pow(meunm[i][j],m);
									}
						  			}

						  			val2=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meunm[i][j])>= 0.0)
										{
                                                                              val2=val2+(Math.pow(meunm[i][j],m)*dsq[i][j]);
									        }
						  			}

						  			arrayEta[i]=val2/val1;
					  			}

					  			for(int k=0;k<count;k++)
					  			{
					  				System.out.println("Eta value for class "+(k+1)+" : "+arrayEta[k]);

					  			}

					  			for(int i=0;i<count;i++)
					  			{
					  				for(int j=0;j<h*w;j++)
					  				{
                                                                           	meunm[i][j]=1/(1+(Math.pow(((dsq[i][j]+gval[i][j])/arrayEta[i]),(1/(m-1)))));
                                                                             //   System.out.println("\nMeu:"+meu[i][j]);


					  				}
					  			}


                                    }
                                    catch(Exception e)
                                    {

                                    }
                                          }
                                   System.out.println("PLICM Classification has been Completed.");

                                                }
	 /////////////********** Minimization Of Energy ***********////////////////

	 	/*	if(context!=0)
		 	{
				int count2,counter=0;
				int iteration=0,k=0,N;
		 		double E1,E2,energyDiff;
		 		double t,tend,p;
		 		Fcm fcm1=new Fcm();
		 		count2=count;
				if(classi==2||classi==3)
					count2=count+1;
				double meu1[][]=new double[count2][h*w];
				double deviation[][]=new double[count2][h*w];

				String tempra=JOptionPane.showInputDialog(null,"Enter initial temprature");
				t=Double.parseDouble(tempra);

				tempra=JOptionPane.showInputDialog(null,"Enter Final Value Of Temprature(Small Float Value).");
				tend=Double.parseDouble(tempra);

				tempra=JOptionPane.showInputDialog(null,"Enter Total No. Of Iterations On Temprature(N Value).");
				N=Integer.parseInt(tempra);

				tempra=JOptionPane.showInputDialog(null,"Enter Float Value Of Range 0.001 ( p Value).");
				p=Double.parseDouble(tempra);


				while(t>tend)		//while current temprature is greater than minimum temprature limit.
				{
					while((N>iteration)&&((h*w*p)>counter))
					{
						for(int i=0;i<count2;i++)
						{
							for(int j=0;j<h*w;j++)
							{
								deviation[i][j]=(Math.sqrt(t)*meu[i][j])/count;
							}
						}

						for(int i=0;i<count2;i++)
						{
							for(int j=0;j<h*w;j++)
							{
								meu1[i][j]=fcm1.normalRandom(meu[i][j],deviation[i][j]);
							}
						}

						E1=contextualEnergy(meu);
						E2=contextualEnergy(meu1);

						energyDiff=E2-E1;
						System.out.println("Diff= "+Math.exp(energyDiff/t));
						if((energyDiff>0)||(Math.exp(energyDiff/t)>=Math.random()))
						{
							for(int i=0;i<count2;i++)
							{
								for(int j=0;j<h*w;j++)
								{
									meu[i][j]=meu1[i][j];
								}
							}
							counter++;
							System.out.println("Meu i,j updated");
						}
						iteration++;
					}

					///////// Update Temprature //////////////

					k++;
					t=((Math.log(1+k)/Math.log(2+k))*t);
					iteration=0;
					counter=0;
				}
			}	*/



	 /////////////********** Classification Of Boundary Pixels ***********////////////////

	 		if(context!=0)
		 	{
				int count2,counter1,counter2,r1,c2;
				int iteration=0,k=0,N;
		 		double E1,E2,energyDiff;
		 		double t,tend;
		 		double sum=0.0;
		// 		Fcm fcm1=new Fcm();
		 		count2=count;
				if(classi==2||classi==3)
					count2=count+1;
				double meu1[][]=new double[count2][h*w];
				double deviation[][]=new double[count2][h*w];
				double ft[][]=new double[count2][h*w];
				double ft2[][]=new double[count2][h*w];
				int arr[]=new int[count2];

				String tempra=JOptionPane.showInputDialog(null,"Enter initial temprature");
				t=Double.parseDouble(tempra);

				tempra=JOptionPane.showInputDialog(null,"Enter Final Value Of Temprature(Small Float Value).");
				tend=Double.parseDouble(tempra);

				tempra=JOptionPane.showInputDialog(null,"Enter Total No. Of Iterations On Temprature(N Value).");
				N=Integer.parseInt(tempra);
				try
				{
				BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Enter row no. to check pixel value");
				r1=Integer.parseInt(br.readLine());
				System.out.println("Enter column no. to check pixel value");
				c2=Integer.parseInt(br.readLine());

				while(t>tend)		//while current temprature is greater than minimum temprature limit.
				{
					while(N>iteration)
					{
						for(int i1=0;i1<count2;i1++)
						System.out.println("Value of Meu(i,j) at pixel "+r1+","+c2+" class "+(i1+1)+" : "+meu[i1][r1*w+c2]);

						ft=BilImage.fcm.smoothing(meu,meu,count2,r1,c2);

					    double n1;
						int n2;
                        for(int i=0;i<count2;i++)
						{
							for(int j=0;j<h*w;j++)
							{
								boolean b=true;
							 	n1=Math.random();
								n1=n1*977;
								n2=(int)n1%count2;

								meu1[i][j]=meu[n2][j];

							}
						}
							ft2=BilImage.fcm.smoothing(meu1,meu,count2,r1,c2);

						energyDiff=0.0;
						counter1=0;
						counter2=0;
						for(int i=0;i<count2;i++)
						{
							for(int j=0;j<h*w;j++)
							{
								energyDiff=ft[i][j]-ft2[i][j];

								if(energyDiff>0)
								{
									meu[i][j]=meu1[i][j];
									counter1++;
								}
								else
								{
							/*		if(Math.exp(energyDiff/t)>=Math.random())
									{
									//	for(int j=0;j<count2;j++)
										{
											meu[i][j]=meu1[i][j];
										}
										counter2++;
									}	*/
								}
						  }
						}
						iteration++;
						System.out.println("Iteration "+iteration+" completed.");
						System.out.println("If updates "+counter1+" pixels");
						System.out.println("Else updates "+counter2+" pixels");
					}

					///////// Update Temprature //////////////

					k++;
					System.out.println("Current temprature value : "+t);
					t=((Math.log(1+k)/Math.log(2+k))*t);
					System.out.println("Temprature value after updation : "+t);
					iteration=0;
				}
				}
				catch(Exception e)
				{
					System.out.println("Temprature Exception : "+e);
				}
			}



      /******************** Saving the Meu matrix./****************************/








 		if(typ==1)
			{
				File file=null;
			JFileChooser chooser=new JFileChooser();
            int r=chooser.showSaveDialog(this);
			if(r==JFileChooser.APPROVE_OPTION)
			{
			   file = chooser.getSelectedFile();
    		   try
     			{
     				int count1;
      				String name=file.toString();
					OutputStream fout=null;
					if(flago==0)
				    count1=count+1 ;
				    else
				    count1=count;

        // Type-II...............................

				    if(typ==2)
					{
						for(int j=0;j<count;j++)
						for(int i=0;i<h*w;i++)
					    meu[j][i]= ((3*meu[j][i])-1)/2;
				    }




				if(sa==1)//if save distance selected
					{

						System.out.println("In sa");


						double finalm[][]=new double[count][h*w];// array



						for(int i=0;i<h*w;i++)
						for(int j=0;j<count;j++)
						finalm[j][i]=dsq[j][i];// saving the distance corresponding to each pixel and class


						for( int j2=0;j2<count1;j2++)
						 {

							fout=new FileOutputStream(name + j2);

							 for(int k1=0;k1<h*w;k1++)
								 fout.write(((int)finalm[j2][k1]));


							  System.out.println(name+j2);
							  FileWriter out=new FileWriter(name+j2+".hdr");
							  out.write("BANDS:      "+1+"\n");
							  out.write("ROWS:    "+h+"\n");
							  out.write("COLS:    "+w+"\n");
							  out.close();

						 }
													fout.close();


					}


					else if(sa!=1)
					{int bt=0;

						if(advclassi==0 && classi!=12)
                                                {
                                                        System.out.println("\nBASE CLASSIFICATION");

                                                    for( int j2=0;j2<count1;j2++)
		            	{
		            		fout=new FileOutputStream(name + j2);

		            	      for(int k1=0;k1<h*w;k1++)
                    	          fout.write((int)((meu[j2][k1])*255.0));

							System.out.println(name+j2);
                                                    	FileWriter out=new FileWriter(name+j2+".hdr");
							out.write("BANDS:      "+1+"\n");
							out.write("ROWS:    "+h+"\n");
							out.write("COLS:    "+w+"\n");
							out.close();

						}

                                                }
                                                else if(advclassi==1 || advclassi==2 || advclassi==3 || advclassi==4)
                                                {
                                                        System.out.println("\nADVANCED CLASSIFICATION");

                                                    for( int j2=0;j2<count1;j2++)
		            	{
		            		fout=new FileOutputStream(name + j2);

		            	      for(int k1=0;k1<h*w;k1++)
                    	          fout.write((int)((meunm[j2][k1])*255.0));

							System.out.println(name+j2);
                                                    	FileWriter out=new FileWriter(name+j2+".hdr");
							out.write("BANDS:      "+1+"\n");
							out.write("ROWS:    "+h+"\n");
							out.write("COLS:    "+w+"\n");
							out.close();

						}

                                                }

                                                else if(classi==12)
                                                {
                                                    if(b<=8)
                                                        bt=8;
                                                    else if(b>8 && b<=16)
                                                        bt=16;
                                                    else
                                                        bt=0;
                                                                   fout=new FileOutputStream(name+"syn");

                                                                            for(int y1=0;y1<h;y1++)
                                                                            {
                                                                                  for( int j2=0;j2<b;j2++)
		            	                                                  {

                                                                         for(int x1=0;x1<w;x1++)
                                                                               {
                                                                                     int ind=((y1)*w)+(x1);

                                                                                       fout.write((((int)(meun[j2][ind]))));
                                                                                   }

                                                                             }
                                                                         }
                                                      System.out.println(name+"syn");

                                                        System.out.println("\nSYNTHETIC IMAGE FORMATION");
							FileWriter out=new FileWriter(name+"syn.hdr");
                                                        out.write("BANDS:      "+b+"\n");
							out.write("ROWS:    "+h+"\n");
							out.write("COLS:    "+w+"\n");
                                                        out.write("INTERLEAVING:   BIL"+"\n");
                                                        out.write("DATATYPE: U"+bt+"\n");
							out.close();

                                                }
                                                fout.close();

					}

				}
				catch(IOException e)
                {
                    System.out.println("Exception is :"+ e);
                }
			}

			con.setSelected(false);
			flago=1;
		    System.out.println("Process Completed");

	 }





		if(typ==3)
		{

		     File file=null;

		     double f1[]=new double[h*w];
		     double m1[][]=new double[count][h*w];
			//double ki[][]=new double[count+1][h*w];

			String min=t.getText();
			double mini=Double.parseDouble(min);
			System.out.println("hard"+mini);



		 		JFileChooser chooser=new JFileChooser();
				 int r=chooser.showSaveDialog(this);
				if(r==JFileChooser.APPROVE_OPTION)
				{
				  file = chooser.getSelectedFile();
					 try
					 {
						int count1;
						String name=file.toString();
						OutputStream fout=null;
						if(flago==0)
						count1=count+1 ;
						else
		 				count1=count;




						System.out.println("count is"+count); //total number of classes

						 double max=0.0, temp=0.0;int c12,gz=1;

						 int c11=255/count; //label calculate



						 int l=1; int k2f=0,k2=0;


						System.out.println("label is"+c11);


						for(int k3=0;k3<h*w;k3++)
						{
							for(int k4=0;k4<count;k4++)
								{

                                    m1[k4][k3]=meu[k4][k3]; //making copy of meu array
								}
						}


						 for(int k1=0;k1<h*w;k1++) // total no. of pixels
						  {

					                 k2=0; k2f=0;max=meu[0][k1];

							  for(int j=k2+1;j<count;j++)
						 		 {

						 		  if(m1[k2][k1]<m1[j][k1])/*Performing sorting such that after loop finishes
						 		  							the max value for the given pixel is stored at m1[0][k1]*/
									 {

										temp=m1[k2][k1];
										m1[k2][k1]=m1[j][k1];
										m1[j][k1]=temp;
										max=m1[k2][k1];
									 }
								}
						 		for(int j=0;j<count;j++)
						 		{
									if(m1[k2][k1]==meu[j][k1]) //Searching for same value which is stored in m1[0][k1], meu array
									{
										k2f=j;//class corresponding to max value is stored

									}
								}



							if(max<mini)
																			f1[k1]=0;
																			else
																			f1[k1]=c11*(k2f+1);


   						 		//gz=k2f+1;

						 		// c12=c11*gz;


								//f1[k1]=c12; // final array for storing value of class with max value

							}









					if(sa==1)// if save distance selected
						{

								System.out.println("In sa");


				        		double finalm[][]=new double[count][h*w];// array


								for(int i=0;i<h*w;i++)
									for(int j=0;j<count;j++)
									    finalm[j][i]=dsq[j][i];



								for( int j2=0;j2<count1;j2++)
						           {

						            	fout=new FileOutputStream(name + j2);


						                  for(int k1=0;k1<h*w;k1++)
				                              fout.write(((int)finalm[j2][k1]));

										System.out.println(name+j2);
										FileWriter out=new FileWriter(name+j2+".hdr");
										out.write("BANDS:      "+1+"\n");
										out.write("ROWS:    "+h+"\n");
										out.write("COLS:    "+w+"\n");
										out.close();

									}
									fout.close();


								}




					else if(sa!=1)
					{
							fout=new FileOutputStream(name );

						for(int i=0;i<count;i++)
							{

								for(int k19=0;k19<h*w;k19++)
								{

						          	fout.write((((int)f1[k19])));// writing final array

								}

							}

							System.out.println(name);
							FileWriter out=new FileWriter(name+".hdr");

							out.write("BANDS:      "+1+"\n");
							out.write("ROWS:    "+h+"\n");
							out.write("COLS:    "+w+"\n");
							out.close();




							fout.close();
					}
				}
						catch(IOException e)
						 {
						     System.out.println("Exception is :"+ e);
						 }
					}

				con.setSelected(false);
				flago=1;
		 		 System.out.println("Fcm Completed");
			 }







}






		         // Type-II...............................










    /****************** Code for Open the Signature File *****************************/

	   if(source==openitem)
	   {
            int by,cy;
            JFileChooser chooser=new JFileChooser();
            int r=chooser.showOpenDialog(this);
            if(r==JFileChooser.APPROVE_OPTION)
		    {
				File file = chooser.getSelectedFile();
				String name=file.getName();

				System.gc();
				validate();

                try
    		    {
                    InputStream f1=new FileInputStream(file);            //reading pixel values for each band
                    try
                  {
                   		int cd=1;
						while(cd!=-1)
						{

					        by=f1.read();
					        if(by==-1)
					         break;
                            by=by<<8;
                            cy=f1.read();
                            Double temp=new Double(by|cy);
                            pixels.addElement(temp);


                        }

                   }
                     catch(IOException e)
                     {
                        System.out.println("File Reading Error: "+e);
                     }
				     try
				     {
			              f1.close();
						  System.gc();
					 }
					 catch(IOException e)
					 {
					 	System.out.println("File Closing Error : "+e);
					 }

                }
                catch(FileNotFoundException e)
                {
                	System.out.println("File Error : "+e);
                }

				len=pixels.size();
				pixelsarray=new Double[len];
				pixels.copyInto(pixelsarray);
				pixels_array=new double[len];

		        for(int m=0;m<len;m++)
		        {

		       		pixels_array[m]=pixelsarray[m].doubleValue();     //converting double object to primitive double**/
		       		System.out.println(pixels_array[m]);
		        }


		        for(int j=0;j<len;j++)
		        {
		        	Double t=new Double(pixels_array[j]);               // for removing pixel values

		         	pixels.removeElement(t);

		        }

			 }


	        /////////////////// After Opening The File. //////////////////////////

	        len=len-1;
	        int bands=imgdata8.bands();

            int l=len/bands;
            double[][] pixel=new double[bands][l];

			double a[][]=new double[bands][l];
			double b[]=new double[bands];

			double sum;

			System.out.println("Image Bands : "+bands);
			System.out.println("No of Clicks : "+l);
			for(int j=0;j<l;j++)
			 	for(int i=0;i<bands;i++)
			 		a[i][j]=pixels_array[j*bands+i];

			for(int i=0;i<bands;i++)
			{
				sum=0;
				for(int j=0;j<l;j++)
					sum+=a[i][j];
				b[i]=sum/l;
			}

			for(int i=0;i<bands;i++)
			{
				for(int j=0;j<bands;j++)
				{
					sum=0;
					for(int k=0;k<l;k++)
					{
						sum=sum+((a[j][k]-b[j])*(a[i][k]-b[i]));
					}
					c[count][i][j]=sum/(l-1);
				}
			}

			System.out.println("Varience Co-Varience Matrix is =");
			for(int i=0;i<bands;i++)
			{
				System.out.println();
				for(int j=0;j<bands;j++)
				{
					System.out.print("\t"+c[count][i][j]);
				}
			}

			System.out.println();
			for(int i=0;i<bands;i++)
                        {
				 model.setValueAt(b[i],row,i+1);
                                 takenvalues[count][i]=b[i];
                                }
			row++;
			count++;
			System.out.println("Number of Classes : "+count);

       }
  }

//................DISTANCE MENU BAR......................


public void actionPerformed(ActionEvent event)
  {

  Object source=event.getSource();
  for(int i=0;i<24;i++)
       {

         if(source==openkitem[i])
         sel_distance.setText(distances[i]);

         if(source==openk2item[i])
          sel_2distance.setText(distances[i]);

       }


//.................TYPE-1 and TYPE-2 Options....................


               if(source==box1)
			   	{
			   	 typ=1;

			   	  }

			   	 	 if(source==box2)
			   	 	  {

			   	 	   typ=2;

                    }
                    if(source==box3)
                    {
						typ=3;
						t.setEnabled(true);
					}
                    {

       if(source==fcm)
           {
            classi=1;
           }

       if(source==noiseOut)
           {
            classi=2;
           }

       if(source==noiseWith)
           {
            classi=3;
           }

       if(source==entropy)
           {
            classi=4;
           }

       if(source==pcm)
           {
            classi=5;
           }
       if(source==mpcm)
       {
           classi=6;
       }
        if(source==ipcm)
       {
           classi=7;
       }
       if(source==mlc)
	    {
           classi=8;
       }
        if(source==fcms)
	    {

           classi=9;
       }

        if(source==pcms)
	    {

           classi=10;
       }
        }


                   // if(source==bg4)
                    {
                         if(source==flicm)
                        {
                          advclassi=1;
                          }
                        if(source==adflicm)
                         {
                                advclassi=2;
                             }
                          if(source==adplicm)
                        {
                          advclassi=3;
                          }
                        if(source==plicm)
                         {
                                advclassi=4;
                             }


                    }

         if(source==btsyn)
	    {
           classi=12;
       }
       if(source==con)
           {
            context=1;
           }

       if(source==dis)
           {
            context=2;
           }
       if(source==h1)
          {
          	st=1;
          }
       if(source==h2)
          {
          	st=2;
          }
       if(source==h3)
          {
          	st=3;
          }
       if(source==h4)
          {
          	st=4;
          }
 if(box4.isSelected())
          sa=1;

        txt=sel_distance.getText();
      HashMap<String , Double> mymap=new HashMap<String , Double>();
      String tmpp=tfm.getText();
      if(tmpp.compareTo("")==0&&source==btClasi) {

      for(int i=0; i<24; i++)
          mymap.put(distances[i], mv_distances[i]);
      m1 = mymap.get(txt);
      m2=m1;
      String tmp=sel_2distance.getText();
      if(tmp.compareTo("Manhattan")==0)
          m2 = m1;
      else if(cb.isSelected())
          m2 = mymap.get(sel_2distance.getText());
      String st=meu_mix.getText();
      if(st.compareTo("")==0)
          meu_mix.setText("1");



      mix_m = Double.parseDouble(meu_mix.getText());
      mix_m = m1*mix_m + (1.0-mix_m)*m2;

      tfm.setText(mix_m+"");
      }
      distance_mixture(source);

   /* Second Distance Mixing starts--------------------------------------------------*/

      if(cb.isSelected()&&source==btClasi)
    {
        flag_second=0;
       txt=sel_2distance.getText();
       dsq_temp =new double[count][h*w];
       for(int l1=0;l1<count;l1++)
           for(int i1=0;i1<h*w;i1++)
               dsq_temp[l1][i1]=dsq[l1][i1];
       distance_mixture(source);
    }
      flag_second=1;


    }

    /*public void itemStateChanged(ItemEvent ie)
    {
		JCheckBox so=(JCheckBox)ie.getItem();
		if(so==box4)
		{

			sa=1;
		}
	}*/

	   ///////////***********Calculating Contextual for Classifier*************//////////////

  public double contextualEnergy(double [][]meu2)
  {
                double []xr=new double [10];

  			energy1=0.0;
	   		energy2=0.0;
	   		temp=0.0;
	   		temp1=0.0;
	   		temp2=0.0;
	   		temp3=0.0;

	   		if(context==1)
	   		{
	   			/////////////// Calculation Of Second Part Of Formula //////////////

	   			for(int i=0;i<h*w;i++)
	   			{
	   				for(int j=0;j<count;j++)
	   				{
	   					for(int k=0;k<8;k++)
	   					{

	   						if(k==0)
	   						{
	   							j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

	   						if(k==1)
	   						{
	   								j1=(i-w)+1;
	   							if((i+1)%w==0)
	   								j1=i-w;
	   							if(j1<0);
	   								j1=i;
	   						}

	   						if(k==2)
	   						{
	   							j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==3)
	   						{
	   							j1=i+w+1;
	   							if(j1>=h*w)
	   								j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==4)
	   						{
	   							j1=i+w;
	   							if(j1>=h*w)
	   								j1=i;
	   						}

	   						if(k==5)
	   						{
	   							j1=(i+w)-1;
	   							if(j1>=h*w)
	   								j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==6)
	   						{
	   							j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==7)
	   						{
	   							j1=(i-w)-1;
	   							if(i%w==0)
	   								j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}
	   						try{

	   						temp=meu2[j][i]-meu2[j][j1];
	   					}catch(Exception e)
	   					{
	   						System.out.println("k= "+k);
	   						System.out.println(e);
	   					}
	   						temp=temp*temp*beta;
	   						energy1=energy1+temp;
	   					}
	   				}
	   			}

	   			energy1=energy1*lem;


				////////////////// FCM With Contextual ////////////////


				if(classi==1)
				{
		   			for(int i=0;i<h*w;i++)
		   			{



		   				for(int j=0;j<count;j++)
		   				{
		   					energy2=energy2+((Math.pow(meu2[j][i],m))*dsq[j][i]);
		   				}
		   			}

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);
		   			System.out.println("Classification with Contextual is Completed for Fuzzy C-Means.");
	   			}

                                	if(classi==9)
				{


		   			/*for(int i=1;i<h*w-1;i++)
		   			{
                                            for(int j=1;j<h*w-1;j++)
                                            {
                                                ar[0]=dsq[i-1][j-1];
                                                ar[1]=dsq[i-1][j];
                                                ar[2]=dsq[i-1][j+1];
                                                ar[3]=dsq[i][j-1];
                                                ar[4]=dsq[i][j+1];
                                                ar[5]=dsq[i+1][j-1];
                                                ar[6]=dsq[i+1][j];
                                                ar[7]=dsq[i+1][j+1];

                                            }

		   			}*/
		   			System.out.println("Classification with Contextual is Completed for Fuzzy C-Means S.");
	   			}
	////////// Noise clustering Without Entropy & With  Contextual ////////////////


		   		if(classi==2)
		   		{
		   			for(int i=0;i<h*w;i++)
		   			{
		   				temp1=temp1+(Math.pow(meu2[count][i],m));
		   			}
		   			temp1=temp1*del;

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp2=temp2+(meu2[j][i]*dsq[j][i]);
		   				}
		   			}
		   			energy2=temp1+temp2;

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);
		   			System.out.println("Classification with Contextual is completed for Noise clustering Without Entropy.");
		   		}


	   			////////// Noise clustering With Entropy &  Contextual ////////////////


		   		if(classi==3)
		   		{
		   			for(int i=0;i<h*w;i++)
		   			{
		   				temp1=temp1+(Math.pow(meu2[count][i],m));
		   			}
		   			temp1=temp1*del;

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp2=temp2+(meu2[j][i]*dsq[j][i]);
		   				}
		   			}

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count+1;j++)
		   				{
		   					temp3=temp3+(meu2[j][i]*(Math.log10(meu2[j][i])));
		   				}
		   			}
	   				temp3=temp3*neo;

	   				energy2=temp1+temp2+temp3;

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);
		   			System.out.println("Classification of Noise Clustering With Entropy is Completed.");
		   		}


		   		////////// Fuzzy C-Means With Entropy & Contextual ////////////////

		   		if(classi==4)
		   		{

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp1=temp1+(meu2[j][i]*dsq[j][i]);
		   				}
		   			}

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp2=temp2+(meu2[j][i]*(Math.log10(meu2[j][i])));
		   				}
		   			}
	   				temp2=temp2*neo;

	   				energy2=temp1+temp2;

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);
		   			System.out.println("Classification of FCM With Entropy is Completed.");
		   		}


	   		}


	////////////*************** Classifier With Discontinuity Adaptive Prior ***********/////////


	   		else
	   		{

		   		/////////// Computing Second Part Of Formula ///////////////////

		   		double eta=0.0;
		   		temp=0.0;temp1=0.0;temp2=0.0;temp3=0.0;

		   		for(int i=0;i<h*w;i++)
		   		{
		   			for(int j=0;j<count;j++)
		   			{
		   				for(int k=0;k<8;k++)
		   				{

	   						if(k==0)
	   						{
	   							j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

	   						if(k==1)
	   						{
	   								j1=(i-w)+1;
	   							if((i+1)%w==0)
	   								j1=i-w;
	   							if(j1<0);
	   								j1=i;
	   						}

	   						if(k==2)
	   						{
	   							j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==3)
	   						{
	   							j1=i+w+1;
	   							if(j1>=h*w)
	   								j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==4)
	   						{
	   							j1=i+w;
	   							if(j1>=h*w)
	   								j1=i;
	   						}

	   						if(k==5)
	   						{
	   							j1=(i+w)-1;
	   							if(j1>=h*w)
	   								j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==6)
	   						{
	   							j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==7)
	   						{
	   							j1=(i-w)-1;
	   							if(i%w==0)
	   								j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

	   						eta=Math.abs(meu2[j][i]-meu2[j][j1]);

	   						energy1=energy1+((gama*eta)-((gama*gama)*(Math.log(1+(eta/gama)))));


		   				}
		   			}
		   		}

		   		energy1=energy1*lem;


		   		////////// FCM With Discontinuity Adaptive Prior////////////////

		   		if(classi==1)
		   		{
		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					energy2=energy2+((Math.pow(meu2[j][i],m))*dsq[j][i]);
		   				}
		   			}

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);

		   			System.out.println("Classification with Discontinuity Adaptive Prior is Completed for Fuzzy C-Means.");
		   		}

                                	if(classi==9)
				{
                                    System.out.println("in classi9");


		   			/*for(int i=1;i<h*w-1;i++)
		   			{
                                            for(int j=1;j<h*w-1;j++)
                                            {
                                                ar[0]=dsq[i-1][j-1];
                                                ar[1]=dsq[i-1][j];
                                                ar[2]=dsq[i-1][j+1];
                                                ar[3]=dsq[i][j-1];
                                                ar[4]=dsq[i][j+1];
                                                ar[5]=dsq[i+1][j-1];
                                                ar[6]=dsq[i+1][j];
                                                ar[7]=dsq[i+1][j+1];

                                            }

		   			}*/
		   			System.out.println("Classification with Contextual is Completed for Fuzzy C-Means S.");
	   			}

		   		////////// Noise clustering Without Entropy & With Discontinuity Adaptive Prior////////////////

		   		if(classi==2)
		   		{
		   			for(int i=0;i<h*w;i++)
		   			{
		   				temp1=temp1+(Math.pow(meu2[count][i],m));
		   			}
		   			temp1=temp1*del;

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp2=temp2+(meu2[j][i]*dsq[j][i]);
		   				}
		   			}
		   			energy2=temp1+temp2;

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);

		   			System.out.println("Contextual of Noise clustering Without Entropy & With Discontinuity Adaptive Prior is completed.");
		   		}



		   		////////// Noise clustering With Entropy & Discontinuity Adaptive Prior////////////////

		   		if(classi==3)
		   		{
		   			for(int i=0;i<h*w;i++)
		   			{
		   				temp1=temp1+(Math.pow(meu2[count][i],m));
		   			}
		   			temp1=temp1*del;

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp2=temp2+(meu2[j][i]*dsq[j][i]);
		   				}
		   			}

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count+1;j++)
		   				{
		   					temp3=temp3+(meu2[j][i]*(Math.log10(meu2[j][i])));
		   				}
		   			}
	   				temp3=temp3*neo;

	   				energy2=temp1+temp2+temp3;

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);

		   			System.out.println("Classification of Noise Clustering With Entropy & Discontinuity Adaptive Prior is Completed.");
		   		}



		   		////////// Fuzzy C-Means With Entropy & Discontinuity Adaptive Prior////////////////

		   		if(classi==4)
		   		{
		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp1=temp1+(meu2[j][i]*dsq[j][i]);
		   				}
		   			}

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp2=temp2+(meu2[j][i]*(Math.log10(meu2[j][i])));
		   				}
		   			}
	   				temp2=temp2*neo;

	   				energy2=temp1+temp2;

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);

		   			System.out.println("Classification of FCM With Entropy & Discontinuity Adaptive Prior is Completed.");
		   		}


	    	}
	    	return energy;
  }


  ///////////************* Normal Random Function ***************//////////////////////

  public double normalRandom(double mean,double stdDev)
  {

  	double val;

  	Random rand=new Random();
  	val=stdDev*rand.nextGaussian()+mean;
  	return val;

  }


  ////////////////************************* Smoothing Of Boundry Pixels *******************////////////////

  public double[][] smoothing(double [][]meu3,double [][]meu4,int count1,int r1,int c1)
  {

  	double temp4=0.0,temp5=0.0;
  	double eta=0.0;
  	double sum=0.0;
  	ReadImageData obj=new ReadImageData();
  	h=obj.rows();
  	w=obj.columns();
  	beta=Float.parseFloat(tfbeta.getText());
  	gama=Float.parseFloat(tfgama.getText());
  	lem=Float.parseFloat(tflem.getText());

  	double fl[][]=new double[count1][h*w];
  	double fp[][]=new double[count1][h*w];
  	double ft1[][]=new double[count1][h*w];

  	for(int i=0;i<count1;i++)
  	{
  		for(int j=0;j<h*w;j++)
  		{
  			fl[i][j]=meu3[i][j];

  		}
  	}

	for(int i1=0;i1<count1;i1++)
	System.out.println("Value of FL at pixel "+r1+","+c1+" class "+(i1+1)+" : gama : "+gama+" : "+fl[i1][r1*w+c1]);




  	/////////////////// For Smoothing Prior /////////////////////////

  	if(context==1)
  	{

		for(int j=0;j<count1;j++)
   		{
   			for(int i=0;i<h*w;i++)
   			{
   				temp5=0.0;

   				for(int k=0;k<8;k++)
   				{

	   						if(k==0)
	   						{
	   							j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

	   						if(k==1)
	   						{
	   								j1=(i-w)+1;
	   							if((i+1)%w==0)
	   								j1=i-w;
	   							if(j1<0);
	   								j1=i;
	   							if(j1>=h*w)
	   								j1=i;
	   						}

	   						if(k==2)
	   						{
	   							j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==3)
	   						{
	   							j1=i+w+1;
	   							if(j1>=h*w)
	   								j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==4)
	   						{
	   							j1=i+w;
	   							if(j1>=h*w)
	   								j1=i;
	   						}

	   						if(k==5)
	   						{
	   							j1=(i+w)-1;
	   							if(j1>=h*w)
	   								j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==6)
	   						{
	   							j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==7)
	   						{
	   							j1=(i-w)-1;
	   							if(i%w==0)
	   								j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

					temp4=Math.abs((meu3[j][i]-meu4[j][j1]));
					temp5=temp5+(temp4);
   				}

   				fp[j][i]=beta*temp5/8;
   			}
   		}
   		System.out.println("beta"+ beta);

  	}

  	///////////////// For Discontinuity Adaptive Prior /////////////////

  	else
  	{	System.out.println("Dis. adaptive prior"+beta+" gama "+gama);
  	    System.out.println("status"+st);
  		for(int j=0;j<count1;j++)
   		{
   			for(int i=0;i<h*w;i++)
   			{
   				temp4=0.0;

   				for(int k=0;k<8;k++)
   				{

	   						if(k==0)
	   						{
	   							j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

	   						if(k==1)
	   						{
	   								j1=(i-w)+1;
	   							if((i+1)%w==0)
	   								j1=i-w;
	   							if(j1<0);
	   								j1=i;
	   						}

	   						if(k==2)
	   						{
	   							j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==3)
	   						{
	   							j1=i+w+1;
	   							if(j1>=h*w)
	   								j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==4)
	   						{
	   							j1=i+w;
	   							if(j1>=h*w)
	   								j1=i;
	   						}

	   						if(k==5)
	   						{
	   							j1=(i+w)-1;
	   							if(j1>=h*w)
	   								j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==6)
	   						{
	   							j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==7)
	   						{
	   							j1=(i-w)-1;
	   							if(i%w==0)
	   								j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

					        eta=Math.abs(meu3[j][i]-meu4[j][j1]);
					        if(st==4)
	   						{

	   						temp4=temp4+((gama*eta)-((gama*gama)*(Math.log(1+(eta/gama)))));
	   					    }
	   						if(st==3)
	   						{

	   						temp4=temp4+(gama*(Math.log(1+((eta*eta)/gama))));
	   					    }
	   						if(st==2)
	   						{

	   						temp4=temp4+(-(gama/(1+(eta*eta)/gama)));
	   				    	}
	   						if(st==1)
	   						{

	   						temp4=temp4+(Math.pow((- gama*2.7182818284),(-(eta*eta)/gama)));
	   					    }

   				}

   				fp[j][i]=temp4;
   			}
   		}

  	}

	for(int i1=0;i1<count1;i1++)
	System.out.println("Value of FP at pixel "+r1+","+c1+" class "+(i1+1)+" : "+fp[i1][r1*w+c1]);


  	for(int j=0;j<count1;j++)
  	{
  		for(int i=0;i<h*w;i++)
  		{

  			ft1[j][i]=((1-lem)*fl[j][i])+(lem*fp[j][i]);
  		}
  	}

	for(int i1=0;i1<count1;i1++)
	System.out.println("Value of FT at pixel "+r1+","+c1+" class "+(i1+1)+" : "+ft1[i1][r1*w+c1]);

  	return ft1;

  }
}

///// CLASS TO CALCULATE THE CORRELATION COEFFICIENT//////

class Corelation
{


public static double Correlation(double[] xs, double[] ys) {

    double sx = 0.0;
    double sy = 0.0;
    double sxx = 0.0;
    double syy = 0.0;
    double sxy = 0.0;

    int n = xs.length;

    System.out.println("the n in pcc is"+n);
    for(int i = 0; i < n; ++i)//n is no of bands
    {
      double x = xs[i];
      double y = ys[i];

      sx += x;
      sy += y;
      sxx += x * x;
      syy += y * y;
      sxy += x * y;
    }

   /* System.out.println("The value of sx="+sx);
    System.out.println("The value of sy="+sy);
    System.out.println("The value of sxx="+sxx);
    System.out.println("The value of syy="+syy);
    System.out.println("The value of sxy="+sxy);*/


// covariation
    double cov = sxy / n - sx * sy / n / n;
   // System.out.println("cov"+cov);
    // standard error of x
    double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);
    //System.out.println("sigmax="+sigmax);
    // standard error of y
    double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);
//System.out.println("sigmay"+sigmay);
//System.out.println("the division"+cov / sigmax/ sigmay);
    // correlation is just a normalized covariation
    return cov / sigmax / sigmay;
  }
}
class ScaD
{
    public static double ScaDistance(double []xs,double []ys)
    {


    double si=0.0;
    double sj=0.0;
    double sij=0.0;
    double nsij=0.0;
    double r_num=0.0;
    double si2=0.0;
    double sj2=0.0;
    double s_i2=0.0;
    double s_j2=0.0;
    double nsi2=0.0;
     double  nsj2=0.0;
    double res=0.0;
     double r_dem=0.0;
     double sca1=0.0;
     double sca2=0.0;
    int n = xs.length;


    for(int i = 0; i < n; ++i)//n is no of bands
    {
      double x = xs[i];
      double y = ys[i];
      si+=x;
      sj+=y;
      sij+=x*y;
      si2+=x*x;
      sj2+=y*y;
 }

    nsij=n*sij;
    r_num=nsij-(si*sj);

    s_i2=si*si;
    s_j2=sj*sj;
    nsi2=n*s_i2;
    nsj2=n*s_j2;
   // System.out.println("r_num="+r_num);
   r_dem=Math.sqrt((nsi2-s_i2)*(nsj2-s_j2));
  // System.out.println("r_dem="+r_dem);
   if(r_dem==0.0)
   {
       r_dem=1.0;
   }
   if(r_num==0.0)
   {
       r_num=1.0;

   }
  // res=r_num/r_dem;
  res=Corelation.Correlation(xs,ys);

   sca1=(res+1)/2;
   System.out.println("sca1="+sca1);
   sca2=(Math.acos(sca1));//System.out.println("sca2="+sca2);
 // System.out.println("\nvalue="+sca2);
    return sca2;
    }
}

class CosCorelation
{


public static double CosCorrelation(double[] xs, double[] ys) {

    double sx = 0.0;
    double sy = 0.0;
    double sxx = 0.0;
    double syy = 0.0;
    double sxy = 0.0;

    int n = xs.length;
    double the_value=0.0;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

      sx += x;
      sy += y;
      sxx += x * x;
      syy += y * y;
      sxy += x * y;
    }


// covariation
    double cov = sxy ;
    // standard error of x
    double sigmax = Math.sqrt(sxx);
    // standard error of y
    double sigmay = Math.sqrt(syy);

    the_value=cov/sigmax/sigmay;

    //the_value=cov/sigmax/sigmay;
    // correlation is just a normalized covariation
    //System.out.println("the cos="+the_value);
    return the_value;
   // return cov / sigmax / sigmay;      //cos theta

  }
}

class TanD
{


public static double TanDistance(double[] xs, double[] ys) {

    int n = xs.length;
    int b=n;
   double sub[][]=new double[b][1];
  double subT[][]=new double[1][b];
   double mul[][]=new double[1][b];
   double angle=0.0,dsq=0.0,value=0.0;



    for(int i = 0; i < n; ++i) {


                            for(int j=0;j<b;j++)//b is no of bands
			    {
                                sub[j][0]=0;//sub is 2d array
                                    for(int k=0;k<b;k++)
                                        sub[j][0]+= (xs[k]*ys[k]);  //filling where sub matrix is added with the value of the sid
                            }

                            for(int j=0;j<b;j++)
                            {
                                mul[0][j]=0;
                                subT[0][j]=0;
                                for(int k=0;k<b;k++)
                                {
                                    mul[0][j]+=(xs[k]*xs[k]);//mul square to find its magnitude
                                    subT[0][j]+=(ys[k]*ys[k]);//mean is pre defined squaring of sub is stored in subT
                             /*      if(k==b-1)
                                    {
                                        mul[0][j]=Math.sqrt(mul[0][j]);//to find magnitude
                                        subT[0][j]=Math.sqrt(subT[0][j]);//to find magnitude
                                    }*/
                                }
                            }


                            dsq=0.0;//the main matrix in which the values are supposed to be filled
                            for(int k=0;k<b;k++)
                            {

                                if(mul[0][k]==0)
                                {
                                    dsq=0.0;//if value is passed as 0.0 then the dsq element is supposed to be fileed with 0 to avoid NaN excception
                                }
                                else //the values are filled in the dsq matrix
                                dsq=(sub[k][0])/((Math.sqrt(mul[0][k])*Math.sqrt(subT[0][k])));

                                value= dsq;
                                angle = (Math.acos(value)*180)/Math.PI;	//it refers to tetha where theta is the cos inverse of the value,the values are supppoes to be radian
                              double tan_value=Math.tan(angle*Math.PI/180);//this is the intergral part the sin for the value is stored in the sin_value

                             dsq=tan_value;//the final value is stored in the dsq
                            //  dsq=angle;

		            }

    }


    return dsq;

  }
}



class SinD
{


public static double SinDistance(double[] xs, double[] ys) {

    int n = xs.length;
    int b=n;
   double sub[][]=new double[b][1];
  double subT[][]=new double[1][b];
   double mul[][]=new double[1][b];
   double angle=0.0,dsq=0.0,value=0.0;



    for(int i = 0; i < n; ++i) {


                            for(int j=0;j<b;j++)//b is no of bands
			    {
                                sub[j][0]=0;//sub is 2d array
                                    for(int k=0;k<b;k++)
                                        sub[j][0]+= (xs[k]*ys[k]);  //filling where sub matrix is added with the value of the sid
                            }

                            for(int j=0;j<b;j++)
                            {
                                mul[0][j]=0;
                                subT[0][j]=0;
                                for(int k=0;k<b;k++)
                                {
                                    mul[0][j]+=(xs[k]*xs[k]);//mul square to find its magnitude
                                    subT[0][j]+=(ys[k]*ys[k]);//mean is pre defined squaring of sub is stored in subT
                                  /* if(k==b-1)
                                    {
                                        mul[0][j]=Math.sqrt(mul[0][j]);//to find magnitude
                                        subT[0][j]=Math.sqrt(subT[0][j]);//to find magnitude
                                    }*/
                                }
                            }


                            dsq=0.0;//the main matrix in which the values are supposed to be filled
                            for(int k=0;k<b;k++)
                            {

                                if(mul[0][k]==0)
                                {
                                    dsq=0.0;//if value is passed as 0.0 then the dsq element is supposed to be fileed with 0 to avoid NaN excception
                                }
                                else //the values are filled in the dsq matrix
                                dsq=(sub[k][0])/((Math.sqrt(mul[0][k])*Math.sqrt(subT[0][k])));

                                value= dsq;
                                angle = (Math.acos(value)*180)/Math.PI;	//it refers to tetha where theta is the cos inverse of the value,the values are supppoes to be radian
                              double sin_value=Math.sin(angle*Math.PI/180);//this is the intergral part the sin for the value is stored in the sin_value

                              dsq=sin_value;//the final value is stored in the dsq [][]
//dsq=angle;
		            }

    }


    return dsq;

  }
}



class Sid
{


public static double SidDistance(double[] xs, double[] ys) {


    int n = xs.length;
    int b=n;
    double sid=0.0;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];


              double p=0.0;
                   double [] ps=new double[b];  //   System.out.println("\nBands:"+Bands);

			 for(int s=0;s<b;s++)
                         {   p =p+xs[s];
                            }
                         for(int s=0;s<b;s++)
                              ps[s]=xs[s]/p;


                        double q=0.0;
                      for(int s=0;s<b;s++)
                       q =q+ys[s];

                      double [] qs=new double[b];
                      for(int s=0;s<b;s++)
                      {
                          qs[s]=ys[s]/q;
                      }



        if(q==0.0)
        {
           for(int s=0;s<b;s++)
            qs[s]=0.0;
        }

                 double a_b[]=new double[b];
                 double b_a[]=new double[b];
                   double fac[]=new double[b];
                   double fact[]=new double[b];

                    for(int s=0;s<b;s++)
                    {
                        fac[s]=ps[s]/qs[s];
                        fact[s]=qs[s]/ps[s];

                    }


                       for(int s=0;s<b;s++)
                       {
                           if(qs[s]==0.0)
                           {
                               a_b[s]=0.0;
                               b_a[s]=0.0;
                           }
                           else
                           {
                               a_b[s]=ps[s]*Math.log10(fac[s]);
                                b_a[s]=qs[s]*Math.log10(fact[s]);
                           }
                       }


                      double sida_b=0.0,sidb_a=0.0;
                      for(int s=0;s<b;s++)
                      {
                          sida_b=sida_b+a_b[s];
                          sidb_a=sidb_a+b_a[s];

                      }

                       sid=sida_b+sidb_a;

                      }
 // System.out.println("\nSid:"+sid);
   return sid;

  }

}

class Euclidean
{


public static double EuclideanDistance(double[] xs, double[] ys) {

    double sxy = 0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

      sxy += (x-y)*(x-y);
    }
    double ans=Math.sqrt(sxy);

    return ans;

  }
}


class Manhattan
{


public static double ManhattanDistance(double[] xs, double[] ys) {

    double sxy = 0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

    if(x>=y)
      sxy +=(x-y);
    else
      sxy+=(y-x);
    }

    return sxy;

  }
}

class Chessboard
{


public static double ChessboardDistance(double[] xs, double[] ys) {

    double max=0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

    if(i==0)
    {if(x>=y)
      max=(x-y);
    else
      max=(y-x);

    }

    else{
    double temp;
    if(x>=y)
      temp=(x-y);
    else
      temp=(y-x);
    if(temp>max)
        max=temp;
    }
    }
    return max;

  }
}

class Braycurtis
{


public static double BraycurtisDistance(double[] xs, double[] ys) {

    double N = 0.0;
    double D = 0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

    if(x>=y)
      N +=(x-y);
    else
      N+=(y-x);
    D+=(x+y);
    }

    return N/D;

  }
}

class Canberra
{


public static double CanberraDistance(double[] xs, double[] ys) {

    double N = 0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

    N+=Math.abs(x-y)/(Math.abs(x)+Math.abs(y));
    }

    return N;

  }
}

class Mean
{


public static double MeanDistance(double[] xs, double[] ys) {

    double sxy = 0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

    if(x>=y)
      sxy +=(x-y);
    else
      sxy+=(y-x);
    }

    return sxy/n;

  }
}

class Median
{


public static double MedianDistance(double[] xs, double[] ys) {



    int n = xs.length;

    double sxy[]=new double[n];
    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

      sxy[i]=Math.abs(x-y);
    }

     Arrays.sort(sxy);
   if(n%2!=0)
       return sxy[n/2];
   else
   {
       return (sxy[n/2]+sxy[n/2-1])/2;
   }
  }
}
class NormalisedEuclidean{

    public static double mean(double xs[])
    {
        int len=xs.length;
        double mean=0;
        for(int i=0;i<len;i++)
        {
            mean+=xs[i];
        }
        return mean/len;
    }

    public static double NormalisedEuclideanDistance(double xs[],double ys[]){
        int bands=xs.length;
        double seedmean=mean(xs);
        double samplemean=mean(ys),meandiff;
        meandiff=seedmean-samplemean;
        double numerator=0,denominator=0;
        for(int i=0;i<bands;i++)
        {
            numerator+=Math.pow(meandiff+ys[i]-xs[i],2);
            denominator+=Math.pow(xs[i]-seedmean, 2)+Math.pow(ys[i]-samplemean,2);
        }
        return numerator/(bands*denominator);
    }
}

class RegionGrow extends JFrame implements ActionListener
{
    static JPanel Ofset;
	static JLabel l1;
	static JTextField t1;   // textfield for threshold
    static JButton b;
    static JLabel l2;
     static JLabel l3;
    static JLabel l4;
    static JLabel l5;
     static JLabel l6;
    static JLabel l7;
    static JLabel l8;
    static JLabel l9;
   static JRadioButton ra1;

    static JRadioButton ra2;
    static JCheckBox r3;
    static JCheckBox r4;
    static JCheckBox rE;
    static JCheckBox rM;
    static JCheckBox rCB;
    static JCheckBox rBC;
    static JCheckBox rC;
    static JCheckBox rMean;
    static JCheckBox rMedian;
    static JCheckBox rNE;
    static JCheckBox r7;
    static JCheckBox rSid;
    static JCheckBox rTan;
    static JCheckBox rSin;
    static JCheckBox rSca;
    JRadioButton r5;
    DemoPanel dm;
    JRadioButton r6;
    RegionGrow rw;
    static int wid=20;
    static int hei=20;
    static double thres=0.9;

    static JTextField tf1;
    static JTextField tf2;
    static JTextField tf3;

    public RegionGrow()
    {
             setTitle("Region Grow");
	setSize(700,700);
	addWindowListener(new WindowAdapter()
	{
	  public void WindowClosing(WindowEvent e)
          {
	    System.exit(0);
	  }
	});
           ButtonGroup group1 = new ButtonGroup();


   Ofset= new JPanel();

         b= new JButton("OK");
        l1=new JLabel("Length");
        l2= new JLabel("Height");
        l5= new JLabel("Correlation Threshold");
        l3=new JLabel("Px");
        l4= new JLabel("Px");
        l7=new JLabel("Region Grow");
        l8=new JLabel("Correlation Type");
         ra1=new JRadioButton("ON",false);
          ra1.setSelected(false);

	        ra2=new JRadioButton("OFF",true);
	        ra2.setSelected(true);
	        r3=new JCheckBox("PCC");
	        r4=new JCheckBox("CC");
                rE=new JCheckBox("E");
                rM=new JCheckBox("M");
                rCB=new JCheckBox("CB");
                rBC=new JCheckBox("BC");
                rC=new JCheckBox("C");
                rMean=new JCheckBox("MeD");
                rMedian=new JCheckBox("MiD");
                rNE=new JCheckBox("NE");
	        r7=new JCheckBox("Both");
                rSid=new JCheckBox("Sid");
                rTan=new JCheckBox("Tan");
                rSin=new JCheckBox("Sin");
                rSca=new JCheckBox("Sca");
        group1.add(ra1);
        group1.add(ra2);

        ra1.addActionListener(this);
        ra2.addActionListener(this);

        tf1= new JTextField("20");
         wid=20;
         tf2= new JTextField("20");
         hei=20;
         tf3= new JTextField("0.9");
         thres=90;

        l1.setLocation(55, 50);
        l1.setSize(75, 30);

        l2.setLocation(55, 100);
        l2.setSize(75, 30);

        l5.setLocation(25, 150);
        l5.setSize(120, 30);


        l3.setLocation(230, 50);
        l3.setSize(25, 30);

        l4.setLocation(230,100);
        l4.setSize(75, 30);


        l7.setLocation(55,195);
        l7.setSize(75, 30);

        l8.setLocation(55,230);
        l8.setSize(75, 30);

        tf1.setLocation(150,50);
        tf1.setSize(75, 30);

        tf2.setLocation(150,100);
        tf2.setSize(75, 30);

        tf3.setLocation(150,150);
        tf3.setSize(75, 30);


        b.setBounds(100,450,100,40);//x axis, y axis, width, height
        b.addActionListener(this);
        ra1.setLocation(150,195);
        ra1.setSize(50,30);
        ra2.setLocation(200,195);
        ra2.setSize(50,30);
        r3.setLocation(150,230);
        r3.setSize(50,30);
        r4.setLocation(200,230);
        r4.setSize(50,30);
        rE.setLocation(150,260);
        rE.setSize(50,30);
        rM.setLocation(200,260);
        rM.setSize(50,30);

        rCB.setLocation(150,290);
        rCB.setSize(50,30);
        rBC.setLocation(150,320);
        rBC.setSize(50,30);

        rSid.setLocation(150,350);
        rSid.setSize(50,30);

        rTan.setLocation(150,380);
        rTan.setSize(50,30);

        rSin.setLocation(200,380);
        rSin.setSize(50,30);

        rSca.setLocation(250,380);
        rSca.setSize(50,30);

        rC.setLocation(200,290);
        rC.setSize(50,30);
        rNE.setLocation(200,320);
        rNE.setSize(50,30);

        rMean.setLocation(250,290);
        rMean.setSize(50,30);
        rMedian.setLocation(250,320);
        rMedian.setSize(50,30);

        r7.setLocation(250,200);
        r7.setSize(100,90);


  Ofset.add( ra1);
        Ofset.add( ra2);
        Ofset.add( r3);
        Ofset.add( r4);
        Ofset.add( rE);
        Ofset.add( rM);
        Ofset.add( rCB);
        Ofset.add( rBC);
        Ofset.add( rC);
        Ofset.add( rMean);
        Ofset.add( rMedian);
        Ofset.add( rNE);
        Ofset.add( r7);
        Ofset.add(rSid);
        Ofset.add(rTan);

        Ofset.add(rSin);
        Ofset.add(rSca);
        Ofset.add(b);//adding button in JFrame
        Ofset.add(l1);
        Ofset.add(l3);

        Ofset.add(l2);

        Ofset.add(l4);
        Ofset.add(l5);
      //  Ofset.add(l6);
        Ofset.add(l7);
        Ofset.add(l8);


        Ofset.add(tf1);
        Ofset.add(tf2);
        Ofset.add(tf3);

Container contentpane=getContentPane();
	contentpane.add(Ofset,"Center");

        Ofset.setSize(400,500);//400 width and 500 height
        Ofset.setLayout(null);//using no layout managers
        Ofset.setVisible(true);

    }
  public static int ycod;
public static int xcod;
static int xw=RegionGrow.wid;
static double max=-1,min=65535;//half the Width of region grow window
  static int yw=RegionGrow.hei;     //half the Height of region grow window
  static double threshold=(RegionGrow.thres); // Threshold value for region grow samples


 public static  double yratio []=new double[DemoPanel.xratios.length];

static  int xs; //Cordinates For Starting and ending Points of Region Grow WIndow
static  int ys;
static  int xe;
static  int ye;

static int width1;
static int height1;
static int myflag;
static int shiftflag;

private static ReadImageData Data1;
static int band1;
static int samplecount;
static private File ImageFile;
static int count=0;
static int count1=0;
//public static double [][] res =new double[count][height1*width1];
static int turn=1;
static double maxS,minS;
static double maxSn,minSn;
static double maxT,minT;
static double maxE,minE;
static double maxM,minM;
static double maxCB,minCB;
static double maxBC,minBC;
static double maxC,minC;
static double maxMean,minMean;
static double maxMedian,minMedian;
static double maxNE,minNE;
static double maxSc,minSc;

public static void windowmaker(int xclickcor, int yclickcor, int width,int height)   //Function to calculate Window size
  {
    if((xclickcor-xw)<0)
        xs=1;
      else
        xs=xclickcor-xw;
    System.out.println("xstart :" +xs);

      if(xclickcor+xw>width)
          xe=width;
      else
          xe=xclickcor+xw;
      System.out.println("xend :" +xe);

      if((yclickcor-yw)<0)
        ys=1;
      else
        ys=yclickcor-yw;
      System.out.println("ystart :" +ys);

      if(yclickcor+yw>height)
          ye=height;
      else
          ye=yclickcor+yw;
      System.out.println("yend :" +ye);

}


  public static void Bandratiocal() throws FileNotFoundException
  {
	  ReadImageData ImgData1 = new ReadImageData();
		ImageFile = ImgData1.imagefile();
                int ch=0,th=0;
		ImgData1.read(ImageFile);
		//threshold=threshold/100;
		//threshold=threshold*Math.pow(100,DemoPanel.clicks);
         File file=null;
		  JFileChooser chooser=new JFileChooser();
         int r=chooser.showSaveDialog(chooser);
         if(r==JFileChooser.APPROVE_OPTION)
	      {
	       	file = chooser.getSelectedFile();
		try
		{
			String name=file.toString();
				System.out.println(name);
            FileOutputStream fout=new FileOutputStream(name);
		DemoPanel pane=new DemoPanel();
			imagetable6 Table6;
		System.out.println("Table Opened");
		int Noclicks=pane.retclicks();
		Table6=new imagetable6();
		Table6=new imagetable6(Noclicks);
		Table6.setSize(550,300);
		Table6.setVisible(true);
		xw=RegionGrow.wid;     //half the Width of region grow window
		yw=RegionGrow.hei;     //half the Height of region grow window
        threshold=(RegionGrow.thres); // Threshold value for region grow samples
        System.out.println("\nXW="+xw+"\nYW="+yw);
		System.out.println("Threshold: "+threshold);

       for(xcod=xs;xcod<=xe;xcod++)
      {
                for(ycod=ys;ycod<=ye;ycod++)
             {
                    if(xcod==xs&&ycod==ys)
                        turn=1;
                            try
                            {

                            	FileInputStream fin_band=new FileInputStream(ImageFile);
                            	 FileOutputStream fout_band[]=new FileOutputStream[band1];
                            	 FileChannel fchan_in = fin_band.getChannel();
                                 try
                           	  {
                           	 	 for(int k=0; k<band1; k++)
                            	  	 {
                            			fin_band = new FileInputStream("Data" + BilImage.name +  k);


                            	     }
                            	  }
                            	  catch(Exception e)
                            	  {
                            		System.out.println("file Open Error in click : "+e);
                            		e.printStackTrace();
                            	  }
					Data1 = new ReadImageData();
					width1 = Data1.columns();
					height1 = Data1.rows();
					band1= Data1.bands();


                                        	//System.out.println("Hell");
					MappedByteBuffer mBuf;
					int bvalues[] = new int[band1];
					long lstart;
					byte b4,b5;
					int p1,p2;
					int temp,tempCount;
					int z,nob=1;
                                     System.gc();
                                     if(picture.shiftFlag==8)
                                     {
                     for(int ii=0; ii<band1; ii++)
					{

						lstart = (ycod * width1*band1) + (ii * width1)+ xcod;
                                            //    if(lstart==30000)
                                              //System.out.println("\nXcod="+xcod+"\nYcod="+ycod+"\nlstart="+lstart);
						mBuf=fchan_in.map(FileChannel.MapMode.READ_ONLY,lstart,nob);

							b4 = mBuf.get();

							bvalues[ii] = (b4<<24) >>> 24;
					}
                                     }
                                     else
                                     {
                                    	 for(int ii=0; ii<band1; ii++)
                     					{
                                         	nob=2;
                     						lstart = ((ycod * width1*band1) + (ii * width1)+ xcod)*2;
                                                    //    System.out.println("\nXcod="+xcod+"\nYcod="+ycod+"\nlstart="+lstart+"\ni1="+ii);

                     						mBuf=fchan_in.map(FileChannel.MapMode.READ_ONLY,lstart,nob);

                     							b4=b5 = mBuf.get();
                     							p1 = b4<< 24;
            									p2 = (b5 <<24 ) >>> 8;
            									tempCount = p1 | p2;
            									tempCount = tempCount >>> 16;
                     							bvalues[ii] = tempCount;
                     					}
                                     }
                        for( z=0;z<band1;z++)
						yratio[z]=bvalues[z];
 				}
				catch(Exception e)
				{
					System.out.println(e);
					e.printStackTrace();
				}
                   if(rE.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxE=Euclidean.EuclideanDistance(DemoPanel.xratios,yratio);
                        minE=Euclidean.EuclideanDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)>maxE)
                           maxE=Euclidean.EuclideanDistance(DemoPanel.xratios,yratio);
                        if(Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)<minE)
                          minE=Euclidean.EuclideanDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }
                   if(rTan.isSelected()==true)
                {
                    if(turn==1)
                    {
                        maxT=TanD.TanDistance(DemoPanel.xratios,yratio);
                        minT=TanD.TanDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(TanD.TanDistance(DemoPanel.xratios,yratio)>maxT)
                           maxT=TanD.TanDistance(DemoPanel.xratios,yratio);
                        if(TanD.TanDistance(DemoPanel.xratios,yratio)<minT)
                          minT=TanD.TanDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }

                  if(rSca.isSelected()==true)
                   {
                        if(turn==1)
                    {
                        maxSc=ScaD.ScaDistance(DemoPanel.xratios,yratio);
                        minSc=ScaD.ScaDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(ScaD.ScaDistance(DemoPanel.xratios,yratio)>maxSc)
                           maxSc=ScaD.ScaDistance(DemoPanel.xratios,yratio);
                        if(ScaD.ScaDistance(DemoPanel.xratios,yratio)<minSc)
                          minSc=ScaD.ScaDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
              //   System.out.println("the maxsc is="+maxSc);
                // System.out.println("the min Sc i s="+minSc);

                   }

                   if(rSin.isSelected()==true)
                {
                    if(turn==1)
                    {
                        maxSn=SinD.SinDistance(DemoPanel.xratios,yratio);
                        minSn=SinD.SinDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(SinD.SinDistance(DemoPanel.xratios,yratio)>maxSn)
                           maxSn=SinD.SinDistance(DemoPanel.xratios,yratio);
                        if(SinD.SinDistance(DemoPanel.xratios,yratio)<minSn)
                          minSn=SinD.SinDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }



                    if(rSid.isSelected()==true)
                {
                    if(turn==1)
                    {
                        maxS=Sid.SidDistance(DemoPanel.xratios,yratio);
                        minS=Sid.SidDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Sid.SidDistance(DemoPanel.xratios,yratio)>maxS)
                           maxS=Sid.SidDistance(DemoPanel.xratios,yratio);
                        if(Sid.SidDistance(DemoPanel.xratios,yratio)<minS)
                          minS=Sid.SidDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }

                 if(rM.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxM=Manhattan.ManhattanDistance(DemoPanel.xratios,yratio);
                        minM=Manhattan.ManhattanDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)>maxM)
                           maxM=Manhattan.ManhattanDistance(DemoPanel.xratios,yratio);
                        if(Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)<minM)
                          minM=Manhattan.ManhattanDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }

                  if(rBC.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxBC=Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio);
                        minBC=Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)>maxBC)
                           maxBC=Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio);
                        if(Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)<minBC )
                          minBC=Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }

                 if(rCB.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxCB=Chessboard.ChessboardDistance(DemoPanel.xratios,yratio);
                        minCB=Chessboard.ChessboardDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)>maxCB)
                           maxCB=Chessboard.ChessboardDistance(DemoPanel.xratios,yratio);
                        if(Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)<minCB )
                          minCB=Chessboard.ChessboardDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }

                if(rC.isSelected()==true)
                {

                    if(turn==1)
                    {
                        maxC=Canberra.CanberraDistance(DemoPanel.xratios,yratio);
                        minC=Canberra.CanberraDistance(DemoPanel.xratios,yratio);

                    }

                    else
                    {
                       if(Canberra.CanberraDistance(DemoPanel.xratios,yratio)>maxC)
                           maxC=Canberra.CanberraDistance(DemoPanel.xratios,yratio);
                        if(Canberra.CanberraDistance(DemoPanel.xratios,yratio)<minC)
                          minC=Canberra.CanberraDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }

                 if(rMean.isSelected()==true)
                {

                    if(turn==1)
                    {
                        maxMean=Mean.MeanDistance(DemoPanel.xratios,yratio);
                        minMean=Mean.MeanDistance(DemoPanel.xratios,yratio);

                    }

                    else
                    {
                       if(Mean.MeanDistance(DemoPanel.xratios,yratio)>maxMean)
                           maxMean=Mean.MeanDistance(DemoPanel.xratios,yratio);
                        if(Mean.MeanDistance(DemoPanel.xratios,yratio)<minMean)
                          minMean=Mean.MeanDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }

                  if(rMedian.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxMedian=Median.MedianDistance(DemoPanel.xratios,yratio);
                        minMedian=Median.MedianDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Median.MedianDistance(DemoPanel.xratios,yratio)>maxMedian)
                           maxMedian=Median.MedianDistance(DemoPanel.xratios,yratio);
                        if(Median.MedianDistance(DemoPanel.xratios,yratio)<minMedian)
                          minMedian=Median.MedianDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }
                  if(rNE.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxNE=NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio);
                        minNE=NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)>maxNE)
                           maxNE=NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio);
                        if(NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)<minNE)
                          minNE=NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }
             }
      }
	/*  System.out.println("----");
      System.out.println(maxE+" "+minE);
      System.out.println(maxS+" "+minS);
	  System.out.println(maxM+" "+minM);
	  System.out.println(maxCB+" "+minCB);
	  System.out.println(maxBC+" "+minBC);
	  System.out.println(maxC+" "+minC);
	  System.out.println(maxMean+" "+minMean);
	  System.out.println(maxMedian+" "+minMedian);
	  System.out.println(maxNE+" "+minNE);
	  System.out.println("----");*/
      for(xcod=xs;xcod<=xe;xcod++)
      {

                for(ycod=ys;ycod<=ye;ycod++)
             {
                    count1++;

                            try
                            {

                            	FileInputStream fin_band=new FileInputStream(ImageFile);
                            	 FileOutputStream fout_band[]=new FileOutputStream[band1];
                            	 FileChannel fchan_in = fin_band.getChannel();
                                 try
                           	  {
                           	 	 for(int k=0; k<band1; k++)
                            	  	 {
                            			fin_band = new FileInputStream("Data" + BilImage.name +  k);


                            	     }
                            	  }
                            	  catch(Exception e)
                            	  {
                            		System.out.println("file Open Error in click : "+e);
                            		e.printStackTrace();
                            	  }
					Data1 = new ReadImageData();
					width1 = Data1.columns();
					height1 = Data1.rows();
					band1= Data1.bands();


                                        	//System.out.println("Hell");
					MappedByteBuffer mBuf;
					int bvalues[] = new int[band1];
					long lstart;
                                        double max=-1,min=65535;
					byte b4,b5;
					int p1,p2;
					int temp,tempCount;
					int z,nob=1;
                                     System.gc();
                                     if(picture.shiftFlag==8)
                                     {
                     for(int ii=0; ii<band1; ii++)
					{

						lstart = (ycod * width1*band1) + (ii * width1)+ xcod;
						mBuf=fchan_in.map(FileChannel.MapMode.READ_ONLY,lstart,nob);

							b4 = mBuf.get();

							bvalues[ii] = (b4<<24) >>> 24;
					}
                                     }
                                     else
                                     {
                                    	 for(int ii=0; ii<band1; ii++)
                     					{
                                         	nob=2;
                     						lstart = ((ycod * width1*band1) + (ii * width1)+ xcod)*2;
                     						mBuf=fchan_in.map(FileChannel.MapMode.READ_ONLY,lstart,nob);

                     							b4=b5 = mBuf.get();
                     							p1 = b4<< 24;
            									p2 = (b5 <<24 ) >>> 8;
            									tempCount = p1 | p2;
            									tempCount = tempCount >>> 16;
                     							bvalues[ii] = tempCount;
                     					}
                                     }
                        for( z=0;z<band1;z++)
						yratio[z]=bvalues[z];
 				}
				catch(Exception e)
				{
					System.out.println(e);
					e.printStackTrace();
				}

                             if(r3.isSelected()==true)
                            {
                            if(Corelation.Correlation(DemoPanel.xratios,yratio)>=threshold)

                            {
                            	count++;
                            	{
                            	  	int i,j;
                            	  	int col=1;
                            	    for(i=0;i<new ReadImageData().bands();i++)
                            		{
                            			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                            			j=(int)yratio[i]>>>8;
                           	 	        fout.write(j);
                            			fout.write((int)yratio[i]);
                            		}
                            		imagetable6.row++;

                                  }

                           }
                       }
                else if(r4.isSelected()==true)
                {
                	if(CosCorelation.CosCorrelation(DemoPanel.xratios,yratio)>=threshold)

                    {
                    	count++;
                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }
                else if( (rE.isSelected()==true) && (rM.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}


                else if( (rE.isSelected()==true) && (rM.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}

				else if( (rE.isSelected()==true) && (rSid.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rE.isSelected()==true) && (rBC.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rE.isSelected()==true) && (rC.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rE.isSelected()==true) && (rMean.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rE.isSelected()==true) && (rMedian.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rE.isSelected()==true) && (rNE.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rM.isSelected()==true) && (rCB.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                                else if( (rM.isSelected()==true) && (rCB.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}

				else if( (rM.isSelected()==true) && (rSid.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rM.isSelected()==true) && (rC.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rM.isSelected()==true) && (rMean.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rM.isSelected()==true) && (rMedian.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rM.isSelected()==true) && (rNE.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rCB.isSelected()==true) && (rBC.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rCB.isSelected()==true) && (rC.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                                else if( (rCB.isSelected()==true) && (rSid.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}

				else if( (rCB.isSelected()==true) && (rMean.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rCB.isSelected()==true) && (rMedian.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rCB.isSelected()==true) && (rNE.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rBC.isSelected()==true) && (rSid.isSelected()==true) )
				{
					if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}

                                else if( (rBC.isSelected()==true) && (rC.isSelected()==true) )
				{
					if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
					{
						if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}

				else if( (rBC.isSelected()==true) && (rMean.isSelected()==true) )
				{
					if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
					{
						if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rBC.isSelected()==true) && (rMedian.isSelected()==true) )
				{
					if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
					{
						if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rBC.isSelected()==true) && (rNE.isSelected()==true) )
				{
					if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rC.isSelected()==true) && (rSid.isSelected()==true) )
				{
					if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}

				else if( (rC.isSelected()==true) && (rMean.isSelected()==true) )
				{
					if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
					{
						if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}

				else if( (rC.isSelected()==true) && (rMedian.isSelected()==true) )
				{
					if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
					{
						if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rC.isSelected()==true) && (rNE.isSelected()==true) )
				{
					if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (rMean.isSelected()==true) && (rSid.isSelected()==true) )
				{
					if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                                else if( (rMean.isSelected()==true) && (rMedian.isSelected()==true) )
				{
					if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
					{
						if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}

				else if( (rMean.isSelected()==true) && (rNE.isSelected()==true) )
				{
					if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                                else if( (rMedian.isSelected()==true) && (rSid.isSelected()==true) )
				{
					if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}

				else if( (rMedian.isSelected()==true) && (rNE.isSelected()==true) )
				{
					if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}

                                else if( (rSid.isSelected()==true) && (rNE.isSelected()==true) )
				{
					if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}

                 else if(rE.isSelected()==true)
                {   //System.out.println("bhak");


                	if(((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE))<=threshold)

                    {
                    	count++;
                     /*   if(((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE))<min)
                                      min=(Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE);

                              if(((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE))>max )
                                   max=(Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE);*/



                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                    }
                  //      System.out.println("\nMaxE="+maxE+"\nMinE="+minE);
                    //System.out.println("\nMaxEo="+max+"\nMinEo="+min);

                }

                 else if(rM.isSelected()==true)
                {   //System.out.println("bhak");


                	if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }
                 else if(rSid.isSelected()==true)
                {      /* if(((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS))<min)
                           min=(Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS);

                              if(((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS))>max )
                                    max=(Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS);*/



                	if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                        if(yratio[i]==0.0)
                                        System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                     //   System.out.println("\nMaxSid="+max+"\nMinSid="+min);
                       // System.out.println("\nMAXS:"+maxS+"\nMINS="+minS);

                }

                   else if(rTan.isSelected()==true)
                {      /* if(((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS))<min)
                           min=(Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS);

                              if(((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS))>max )
                                    max=(Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS);*/



                	if((TanD.TanDistance(DemoPanel.xratios,yratio)-minT)/(maxT-minT)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                        if(yratio[i]==0.0)
                                        System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                     //   System.out.println("\nMaxSid="+max+"\nMinSid="+min);
                       // System.out.println("\nMAXS:"+maxS+"\nMINS="+minS);

                }
                   else if(rSin.isSelected()==true)
                {      /* if(((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS))<min)
                           min=(Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS);

                              if(((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS))>max )
                                    max=(Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS);*/



                	if((SinD.SinDistance(DemoPanel.xratios,yratio)-minSn)/(maxSn-minSn)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                        if(yratio[i]==0.0)
                                        System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                     //   System.out.println("\nMaxSid="+max+"\nMinSid="+min);
                       // System.out.println("\nMAXS:"+maxS+"\nMINS="+minS);

                }
                     else if(rSca.isSelected()==true)
                {
                	if(((ScaD.ScaDistance(DemoPanel.xratios,yratio)-minSc)/(maxSc-minSc))<=threshold)

                    {//System.out.println("\nDis::"+((ScaD.ScaDistance(DemoPanel.xratios,yratio))));
                    	count++;
                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }

                 else if(rCB.isSelected()==true)
                {

                	if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }

                 else if(rBC.isSelected()==true)
                {   //System.out.println("bhak");


                	if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }

                 else if(rC.isSelected()==true)
                {   //System.out.println("bhak");


                	if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }
                 else if(rMean.isSelected()==true)
                {   //System.out.println("bhak");


                	if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }
                 else if(rMedian.isSelected()==true)
                {   //System.out.println("bhak");


                	if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }

                 else if(rNE.isSelected()==true)
                {   //System.out.println("bhak");


                	if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }
                else
                {
                	if(Corelation.Correlation(DemoPanel.xratios,yratio)>=threshold)
                	{
                  if(CosCorelation.CosCorrelation(DemoPanel.xratios,yratio)>=threshold)

                    {
                    	count++;
                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }
                }
      }
      }

      System.out.println("Total number of pixels investigated:"+count1);
      System.out.println("Number of similar pixels:"+count);
      System.out.print("Training Samples calculated Succesfully..");
		}
		catch(Exception e)
		{
			 System.out.print(e);
			 e.printStackTrace();
		}
		}
  }

    @Override
    public void actionPerformed(ActionEvent e) {

	if (e.getSource() == b)
		{
                    int ch=0,th=0;

			wid=Integer.parseInt(tf1.getText());
			hei=Integer.parseInt(tf2.getText());
			thres=Double.parseDouble(tf3.getText());
                          if(r7.isSelected()==true)
                          th++;


                            if(rSid.isSelected()==true)
                          ch++;
                      if(rMedian.isSelected()==true)
                          ch++;
                      if(rMean.isSelected()==true)
                          ch++;

                      if(rNE.isSelected()==true)
                          ch++;
                      if(rC.isSelected()==true)
                          ch++;
                      if(rBC.isSelected()==true)
                          ch++;
                      if(rCB.isSelected()==true)
                          ch++;
                      if(rM.isSelected()==true)
                          ch++;
                      if(rE.isSelected()==true)
                          ch++;

                      if(r4.isSelected()==true)
                          ch++;
                      if(r3.isSelected()==true)
                          ch++;
                      if(rSca.isSelected()==true)
                          ch++;


                      if(ch>0 && th==1)
                      {
                            JOptionPane.showMessageDialog(null,"You clicked more than 2 options","Warning Message",JOptionPane.INFORMATION_MESSAGE);
			 }
                      if(th==0 && ch>=3)
                      {
                           JOptionPane.showMessageDialog(null,"You clicked more than 2 options","Warning Message",JOptionPane.INFORMATION_MESSAGE);

                      }

           System.out.println("Threshold value---: "+thres+"% Width:"+wid+" Height:"+hei);
         	   //Ofset.setVisible(false);
              dispose();




}     //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }}