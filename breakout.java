import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.Thread.State;

import javax.swing.JPanel;
//import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;




class begin extends JPanel{
	private static final long serialVersionUID = 1L;
    public void paintComponent(Graphics g) {
    	
        Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
        					RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(32)); // 32 pixel thick stroke
        g2.setColor(Color.PINK); // make it blue
        g2.drawLine(0, (int)getHeight()/2, getWidth(), getHeight());  // draw line 
        g2.setColor(new Color(113,191,234));
        g2.drawLine(getWidth(), 0, 0,getHeight());
        
        String name = "Ziyi Chen";
        String number = "20516568";
        g2.setColor(Color.BLACK);
	    g2.drawString(name, getWidth()/2-15, getHeight() - 40);
	    g2.setColor(Color.BLACK);
	    g2.drawString(number, getWidth()/2-15, getHeight() - 20);
	    String how = "Using mouse to operate the paddle and click the mouse to launch the ball";
	    String how2 = "If use keyboard, press the button left or right to control the paddle and press space to launch";
	    g2.setColor(Color.BLACK);
	    g2.drawString(how, 10, getHeight()/2-20);
	    g2.setColor(Color.BLACK);
	    g2.drawString(how2, 10, getHeight()/2);
    }
}


public class breakout extends JPanel{
	private static final long serialVersionUID = 1L;
	private static int screenwidth = 500;
	private static int screenheight = 700;
	private int xpos = 0;
	private int ypos = 0;
	private int bxpos = 375;
	private int bypos = 648;
	private int score =  0 ;
	private int life = 3;
	public static int ballspeed = 3;
	private static boolean level = false;
	//private int ballx = 0;
	//private int bally = 0;
    private boolean keyleft = false;
    private boolean keyright = false;
    private Ball ball = new Ball();
    private static boolean keyboard = true;
    block[][] Block = new block[11][8];
    
    
//    ActionListener time = new ActionListener() {
//		@Override
//		public void actionPerformed(ActionEvent event) {
//			repaint();
//		}
//	};
//	
//    Timer timer = new Timer(1000000, time);
    
    public class block {
		int x;
		int y;
		int w;
		int h;
		int l;
		Color c;
		boolean exist;
		block(){
			this.x = 0;
			this.y = 0;
			this.w = 0;
			//this.h = 20;
			this.exist = true;
		}
	}
    
	public breakout(){	
		this.setBackground(new Color(192,192,192));
		this.setFocusable(true);
		MouseAdapter m = new mouse();
		this.addMouseListener(m);  //click
		this.addMouseMotionListener(m); //move mouse
		this.addKeyListener(new keyboard()); //keyboard
		//this.addComponentListener(new CL());
		
		for(int i = 1; i < 11 ;++i){
			for (int j = 1 ; j < 8 ; ++j){
				this.Block[i][j] = new block();
				this.Block[i][j].l = i;
			}
		}
		JCheckBox check = new JCheckBox("Level");
		//check.setLocation(x, y);
		ActionListener actionListener = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        boolean enable = check.isSelected();
		        if(enable){
		        	level = true;
		        }
		        else {level = false;}
		    }
		};
		check.addActionListener(actionListener);
		if(!keyboard){this.add(check);}
		
		
	}
	
	public class Ball implements Runnable{
		int x = bxpos+38;
	    int y = bypos-15;
		boolean xadd = false;
		boolean yadd = false;
		boolean dead = true;
		public void draw(Graphics2D g2){
			g2.setColor(Color.BLACK);
			g2.fillOval(x, y, 10, 10);
			if(dead){
				x = bxpos+(getWidth()/6)/2;
				y = bypos-15;
				repaint();
			}
		}
		public void run() {
			dead = false;
		    xadd = false;
			yadd = false;
			double betx = (Block[1][2].x - Block[1][1].x -Block[1][1].w)/2;
			while(!dead)
	        {
				
				if(score == 7000 && !level){
					dead = true;
					life=0;
					break;
				}
				if(score == 38500 && level){
					dead = true;
					life=0;
					break;
				}
				
				for(int i = 1; i < 11 ;++i){
					boolean kk = false;
					for (int j = 1 ; j < 8 ; ++j){
						if(x > Block[i][j].x && x-10 < (Block[i][j].w + Block[i][j].x) && 
								(y+10 == Block[i][j].y || y == Block[i][j].y+20) && Block[i][j].exist){
							if(level && Block[i][j].l != 1){
								Block[i][j].l -= 1;
								yadd = !yadd;
								score += 100;
								//System.out.println(1);
								kk = true;
								break;
							}
							Block[i][j].exist = false;
							yadd = !yadd;
							score += 100;
							//System.out.println(1);
							kk = true;
							break;
						}
						else if((x+10 == Block[i][j].x || x == (Block[i][j].w + Block[i][j].x))&&
								y > Block[i][j].y && y-10 < Block[i][j].y+20 && Block[i][j].exist){
							if(level && Block[i][j].l != 1){
								Block[i][j].l -= 1;
								yadd = !yadd;
								score += 100;
								//System.out.println(1);
								kk = true;
								break;
							}
							Block[i][j].exist = false;
							xadd = !xadd;
							score += 100;
							//System.out.println(2);
							kk = true;
							break;
						}
						else if (((x+10 == Block[i][j].x && y+10 == Block[i][j].y) ||
								 (y == Block[i][j].y+20 && x+10 == Block[i][j].x)||
								 (x == (Block[i][j].x+Block[i][j].w) && y == Block[i][j].y+20)||
								 (x == (Block[i][j].x+Block[i][j].w) && y+20 == Block[i][j].y)) && Block[i][j].exist){
							if(level && Block[i][j].l != 1){
								Block[i][j].l -= 1;
								yadd = !yadd;
								score += 100;
								//System.out.println(1);
								kk = true;
								break;
							}
							Block[i][j].exist = false;
							yadd = !yadd;
							xadd = !xadd;
							score += 100;
							//System.out.println(3);
							kk = true;
							break;
						}
//						if (!Block[i][j].exist) {
//							
//						} else {
//						if(x > Block[i][j].x && x-10 < (Block[i][j].w + Block[i][j].x + betx) && 
//								(y+25 == Block[i][j].y || y == Block[i][j].y+35) && Block[i][j].exist){
//							Block[i][j].exist = false;
//							yadd = !yadd;
//							score += 100;
//							System.out.println(1);
//							break;
//						}
//						else if((x+10 == Block[i][j].x || x == (Block[i][j].w + Block[i][j].x + betx))&&
//								y > Block[i][j].y && y < Block[i][j].y+35 && Block[i][j].exist){
//							Block[i][j].exist = false;
//							xadd = !xadd;
//							score += 100;
//							System.out.println(2);
//							break;
//						}
//						else if ((x+10 == Block[i][j].x && y+10 == Block[i][j].y) ||
//								 (y == Block[i][j].y+35 && x+10 == Block[i][j].x)||
//								 (x == (Block[i][j].x+Block[i][j].w + betx) && y == Block[i][j].y+35)||
//								 (x == (Block[i][j].x+Block[i][j].w+betx) && y+10 == Block[i][j].y) && Block[i][j].exist){
//							Block[i][j].exist = false;
//							yadd = !yadd;
//							xadd = !xadd;
//							score += 100;
//							System.out.println(3);
//							break;
//						}
//						}
					}
					if(kk)break;
				}
				if (y==getHeight()-10 && (x< bxpos || x > bxpos+getWidth()/6)){
					dead = true;
					life -= 1;
				}
				if (y > bypos-5 && x > bxpos && x < bxpos+getWidth()/6){
					y -= 1;
					x += 1;
					yadd=!yadd;
					continue;
				}
	            if(yadd){y+=1;}
	            else{y-=1;}
	            
	            if(y>=getHeight()-10||y<=0){yadd=!yadd;}
	            
	            if(xadd){x+=1;}
	            else {x-=1;}
	            
	            if(x>=getWidth()-10||x<=0){xadd=!xadd;}
	            
	            repaint();
	            try   
	            {   
	            Thread.currentThread();
				Thread.sleep(ballspeed);  
	            }   
	            catch(Exception e){} 
	            
	        }
		}
	}
	public class mouse extends MouseAdapter { //mouse adapter
		public void mouseMoved(MouseEvent e) {
			xpos = e.getX();
			ypos = e.getY();
			if(xpos >= getWidth() - 80){
				xpos = getWidth() - 80;
			}
			repaint();
		}
		Thread thread = new Thread(ball);
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				//System.out.println("a  "+ thread.getState());
				
				if(!thread.isAlive()){
					thread = new Thread(ball);
					if(life == 0){
						for(int i = 1; i < 11 ;++i){
							for (int j = 1 ; j < 8 ; ++j){
								Block[i][j].exist = true;
								if(level) Block[i][j].l = i;
							}
						}
						score = 0;
						life = 3;
					}
				}
				State r = thread.getState();
				//System.out.println("r  "+ r);
				if(r.toString().equals("NEW")){
				thread.start();
				//System.out.println("b  "+ thread.getState());

				}
			}
		}
	}
	
	
	public class keyboard extends KeyAdapter { //keyboard adapter
		Thread thread = new Thread(ball);
		public void keyPressed(KeyEvent e) {//key press
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_LEFT) {
				keyleft = true;
			} else if (key == KeyEvent.VK_RIGHT) {
				keyright = true;
			} else if (key == KeyEvent.VK_SPACE) {
				if(keyboard){
				if(!thread.isAlive()){
					thread = new Thread(ball);
					if(life == 0){
						for(int i = 1; i < 11 ;++i){
							for (int j = 1 ; j < 8 ; ++j){
								Block[i][j].exist = true;
							}
						}
						score = 0;
						life = 3;
					}
				}
				State r = thread.getState();
				//System.out.println("r  "+ r);
				if(r.toString().equals("NEW")){
				thread.start();
				}          
			}
			repaint();
			}
		}

		//public void keyReleased(KeyEvent e) {//key release
		//	int key = e.getKeyCode();
		//	if (key == KeyEvent.VK_LEFT) {
		//		keyleft = false;
		//	} else if (key == KeyEvent.VK_RIGHT) {
		//		keyright = false;
		//	}
		//}
	}
	
	
	public void paintComponent(Graphics g) {
		super.setDoubleBuffered(true);
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
        					RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(10)); // 32 pixel thick stroke
        g2.setColor(Color.BLACK); // make it blue
        
        if(keyboard){
        	if (keyleft && !keyright){
        		bxpos -= 50;
        		keyleft = !keyleft;
        		bypos = getHeight()-30;
        		if(bxpos <= 0){bxpos = 0;}
        	}
        	else if (!keyleft && keyright){
        		bxpos += 50;
        		keyright = !keyright;
        		bypos = getHeight()-30;
        		if(bxpos >= getWidth()-80){bxpos = getWidth()-80;}
        	}
        }
       else{
    	   bxpos = xpos;
           bypos = getHeight()-30;
        }
        g2.setColor(Color.black);
        g2.drawLine(bxpos, bypos, bxpos+getWidth()/6, bypos);  // draw paddle 
        
        String label = "Score: " + score;
        g2.setColor(Color.BLACK);
	    g2.drawString(label, 10, 20);
        
	    ball.draw(g2);
	    
	    for(int i = 1; i < 11 ;++i){
	    	int width = (getWidth()/9);
	    	int high = 30;
	    	Color c = null;
	    	if(i == 1){ c = new Color(84,0,84);}
	    	else if(i == 2){ c = new Color(250,5,0);}
	    	else if(i == 3){ c = new Color(255,180,0);}
	    	else if(i == 4){ c = new Color(255,245,0);}
	    	else if(i == 5){ c = new Color(180,250,0);}
	    	else if(i == 6){ c = new Color(40,180,140);}
	    	else if(i == 7){ c = new Color(0,135,255);}
	    	else if(i == 8){ c = new Color(105,5,220);}
	    	else if(i == 9){ c = new Color(207,0,207);}
	    	else if(i == 10){ c = new Color(245,0,245);}
			for (int j = 1 ; j < 8 ; ++j){
				if(level){
					int k = Block[i][j].l;
					if(k == 1){ c = new Color(84,0,84);}
	    		else if(k == 2){ c = new Color(250,5,0);}
	    		else if(k == 3){ c = new Color(255,180,0);}
	    		else if(k == 4){ c = new Color(255,245,0);}
	    		else if(k == 5){ c = new Color(180,250,0);}
	    		else if(k == 6){ c = new Color(40,180,140);}
	    		else if(k == 7){ c = new Color(0,135,255);}
	    		else if(k == 8){ c = new Color(105,5,220);}
	    		else if(k == 9){ c = new Color(207,0,207);}
	    		else if(k == 10){ c = new Color(245,0,245);}
				}
				Block[i][j].x = j*width;
				Block[i][j].y = 20 + i*high;
				Block[i][j].w = width - 5;
				if (Block[i][j].exist){
					g2.setColor(c);
					g2.fillRoundRect(Block[i][j].x,Block[i][j].y,Block[i][j].w,20,5,5);
				}
			}
		}
	    
	    
	    //int s = bxpos + 80;
	    if (life == 0){
	    String label2 = "YOU LOSE, PLEASE TRY AGAIN!!!";
	    Font font =new Font("Times new Roman",Font.BOLD,20);
		g2.setFont(font);
        g2.setColor(Color.RED);
	    g2.drawString(label2, getWidth()/2-180, getHeight()-300);
	    }
	    for (int i = 0; i < life ;++i){
	    	int k = 30;
	    	g2.setColor(new Color(217,217,25));
			g2.fillOval(getWidth()-20-(i*15), 10, 10, 10);
	    }
	    //g2.setColor(Color.BLACK);
	    //g2.fillOval(bxpos+38,bypos-15,10,10);

	}
	
	
	public static void main(String args[]) {
		JFrame beginF = new JFrame("Breakout Game!");
		JPanel beginP = new begin();
		beginP.setLayout(null);
		beginF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton startM = new JButton("Play with Mouse");
		startM.setBounds(50, 50, 200, 50);
		Font font =new Font("Times new Roman",Font.BOLD,20);
		startM.setFont(font);
		
		JButton startK = new JButton("Play with Keyboard");
		startK.setBounds(50, 150, 200, 50);
		startK.setFont(font);
		
		beginP.add(startM);
		beginP.add(startK);
		beginF.add(beginP);
		beginF.setSize(screenwidth,screenheight);
		beginF.setVisible(true);
		
		
		//System.out.println(breakout.ballspeed);
		for(int i = 0;i<args.length-1;++i){
			if(args[i] == "speed"){
				int v = Integer.valueOf(args[i+1]);
				ballspeed = v;
				//System.out.println(v);
			}
		}
		//System.out.println(breakout.ballspeed);
		if(args.length>1){
			int v = Integer.valueOf(args[1]);
			ballspeed = v;
		}
		
		startM.addMouseListener(new MouseAdapter() { 
            public void mouseClicked(MouseEvent e) {
            	keyboard = false;
            	beginF.dispose();
            	JFrame frame = new JFrame("Breakout Game!");
            	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	frame.setLocationByPlatform(true);
            	frame.setSize(screenwidth,screenheight);
            	frame.setContentPane(new breakout());
            	frame.setVisible(true);
            	}
            });
		startK.addMouseListener(new MouseAdapter() { 
            public void mouseClicked(MouseEvent e) {
            	beginF.dispose();
            	JFrame frame = new JFrame("Breakout Game!");
            	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	frame.setLocationByPlatform(true);
            	frame.setSize(screenwidth,screenheight);
            	frame.setContentPane(new breakout());
            	frame.setVisible(true);
            	}
            });
		
		
	}

}
