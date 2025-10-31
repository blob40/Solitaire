// Alex Krouse
// 10/24/2025
//creating solitare as a playable game

package resources;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;


public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

	Solitaire game;
	private JLayeredPane screenLayers = getLayeredPane();
	private JLayeredPane draggablePane;
   	public GUI(Solitaire game){
	   this.game= game;
        //Create and set up the window.
       setTitle("Solitaire");
       setSize(900,700);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



       // this supplies the background
       try {
		System.out.println(getClass().toString());
		Image blackImg = ImageIO.read(getClass().getResource("background.jpg"));
		setContentPane(new ImagePanel(blackImg));

       }catch(IOException e) {
    	   e.printStackTrace();
       }
       
       /*******
        * This is just a test to make sure images are being read correctly on your machine. Please replace
        * once you have confirmed that the card shows up properly. The code below should allow you to play the solitare
        * game once it's fully created.
        */
       //Card card = new Card(2, Card.Suit.Diamonds);
      // System.out.println(card);
       //this.add(card);    


	   //Setting up deck where cards will be flipped down
	  	JPanel deckPanel = new JPanel();
        deckPanel.setSize(100,150);
		deckPanel.setLayout(new FlowLayout());
		deckPanel.setLocation(20,20);
		deckPanel.setBackground(Color.YELLOW);
		JLabel deckLabel = new JLabel("Deck");
		deckPanel.add(deckLabel);
		this.add(deckPanel);

		//Revealed card and where cards will be dragged from
		JPanel reveal = new JPanel();
		reveal.setLayout(new FlowLayout());
        reveal.setSize(200,150);
		reveal.setLocation(125,20);
		reveal.setBackground(Color.CYAN);
		JLabel revealL = new JLabel("Revealed Cards");
		reveal.add(revealL);
		this.add(reveal);

		// 7 piles for solitare
		JPanel pile1 = new JPanel();
		pile1.setLayout(new FlowLayout());
        pile1.setSize(750,375);
		pile1.setLocation(20, 175);
		pile1.setBackground(Color.PINK);
		JLabel pileL1 = new JLabel("7 Piles");
		pile1.add(pileL1);
		this.add(pile1);

		//Where suits will be set A-K
		JPanel completed = new JPanel();
		completed.setLayout(new FlowLayout());
        completed.setSize(400,150);
		completed.setLocation(370, 20);
		completed.setBackground(Color.ORANGE);
		JLabel completedL = new JLabel("4 completion piles");
		completed.add(completedL);
		this.add(completed);

// Created test to see stacked cards as revealed cards
		Stack<Card> deck = new Stack<>();
		deck.push(new Card(5, Card.Suit.Spades));
		deck.push(new Card(2, Card.Suit.Spades));
		reveal.add(drawPile(deck));
	 /* 	for(int i = 1; i<=52; i++){
			Suit current;
			if(i<14){
				suit = Card.Suit.Spades;
			}
			else if(i<27){
				suit = Card.Suit.Clubs;
			}
			else if(i<40){
				suit = Card.Suit.Hearts;
			}
			else {
				suit = Card.Suit.Diamonds;
			}
			if(i==1||i==14||i==27||i==40){
			 deck.push(new Card(1, suit));
			}
			else if(i==2||i==15||i==28||i==41){
				deck.push(new Card(2, suit));
			}
			else if(i==11||i==24||i==37||i==50){
				deck.push(new Card(11, suit));
			}
			else if(i==12||i==25||i==38||i==51){
				deck.push(new Card(12, suit));
			}
			else if(i==13||i==26||i==39||i==52){
				deck.push(new Card(13, suit));
			   }
			}
		*/
	
		Card si = new Card(5, Card.Suit.Spades);
		si.setPreferredSize(new Dimension(90,120));
		si.hide();
		reveal.add(si);


	//Repile button
       JButton reDraw = new JButton();
	   reDraw.setPreferredSize(new Dimension(90,120));
	   reDraw.setBackground(Color.yellow);
	   deckPanel.add(reDraw);
       reDraw.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
	   				 System.out.println("Clicked");
		
					 repaint();
			}
				
			});


		this.addMouseMotionListener(this);
		this.addMouseListener(this);


        this.setVisible(true);
    }


// Creates a pile of flipped cards all next to each other so people can see the next card in the deck
	 public JLayeredPane drawPile(Stack<Card> stackIn) {
    Object cards[];
    cards = stackIn.toArray(); 
	int change = 5;
	JLayeredPane piled = new JLayeredPane();
    for (int i = 0; i < cards.length; i++){
		Card card = (Card) cards[i];
        card.setLocation(change, 0);
		card.setSize(90,120);
		piled.add(card);
		change += 20;
	}
	piled.setPreferredSize(new Dimension(190,120));
	return piled;

}

	@Override
	//JACK
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (draggablePane!= null) {
            Point pos = getLocationOnScreen();
            pos.x = arg0.getLocationOnScreen().x - 50;
            pos.y = arg0.getLocationOnScreen().y - 50;
            draggablePane.setLocation(pos);   		}
	repaint();
		
	}

	@Override

	//ALEX
	public void mouseMoved(MouseEvent arg0) {
		double screenx = arg0.getX();
		double screeny = arg0.getY();

		// System.out.println("screen x " + screenx + "screen y " + screeny);
		// TODO Auto-generated method stub
		
	}


	@Override
	//
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//JACK
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int x = arg0.getX();
		int y = arg0.getY();

		if (game.checkPress(x, y) && arg0.getSource() instanceof Card)
		{
			draggablePane = new JLayeredPane();
			Point pos = getLocationOnScreen();
			draggablePane.setSize(80,100);
			((JPanel)arg0.getSource()).setLocation(0, 0);
			draggablePane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.PINK));
			draggablePane.add((Card)arg0.getSource());
			pos.x = arg0.getLocationOnScreen().x - 50;
	        pos.y = arg0.getLocationOnScreen().y - 50;
	        draggablePane.setLocation(pos);
		}
		screenLayers.add(draggablePane, JLayeredPane.DRAG_LAYER);
		repaint();
	}

	@Override
	//ABBY
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	//ABBY if needed
	public void createBoard(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
