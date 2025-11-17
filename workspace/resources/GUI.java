
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
	private JLayeredPane screenLayers;
	private JLayeredPane draggablePane;
	private JPanel deckPanel ;
	private JPanel reveal;
	private JLayeredPane pile1  = new JLayeredPane();;
	private JPanel completed;
	private Point originalLocation;

	Card clicked1;
	Card clicked2;
	int num = 0;

   	public GUI(Solitaire game){
	   this.game= game;
	   for(Card card: game.getDeck()){
	card.addMouseListener(this);
	card.addMouseMotionListener(this);
	  }
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
	  	deckPanel = new JPanel();
        deckPanel.setSize(100,150);
		deckPanel.setLayout(new FlowLayout());
		deckPanel.setLocation(20,20);
		deckPanel.setBackground(Color.YELLOW);
		JLabel deckLabel = new JLabel("Deck");
		deckPanel.add(deckLabel);
		this.add(deckPanel);

		//Revealed card and where cards will be dragged from
		reveal = new JPanel();
		reveal.setLayout(new FlowLayout());
        reveal.setSize(200,150);
		reveal.setLocation(125,20);
		reveal.setBackground(Color.CYAN);
		JLabel revealL = new JLabel("Revealed Cards");
		reveal.add(revealL);
		this.add(reveal);

		// 7 piles for solitare
		pile1.setLocation(20, 175);
		this.add(pile1);

		/*JLayeredPane pile2 = new JLayeredPane();
        pile2.setSize(100,375);
		pile2.setLocation(120, 175);
		pile2.setBackground(Color.PINK);
		
		this.add(pile2);

		JLayeredPane pile3 = new JLayeredPane();
        pile3.setSize(100,375);
		pile3.setLocation(220, 175);
		pile3.setBackground(Color.PINK);
	
		this.add(pile3);*/

		//Where suits will be set A-K
		completed = new JPanel();
		completed.setLayout(new FlowLayout());
        completed.setSize(new Dimension(400,150));
		completed.setLocation(370, 20);
		completed.setBackground(Color.ORANGE);
		JLabel completedL = new JLabel("4 completion piles");
		this.add(completed);

		


		screenLayers = getLayeredPane();

// Created test to see stacked cards as revealed cards
		Stack<Card> deck = new Stack<>();
		Card c1 = new Card(5, Card.Suit.Spades);
		c1.addMouseListener(this);
		Clicker click = new Clicker();
	   for(Card c: game.getDeck()){
		c.addMouseListener(click);
	   }

		game.setup();
		int xOffset = 0;
		int yOffset = 130;
		int newOffset = 130;
		//Card c: row


		for(Stack<Card> row: game.columns){
			Object[] rowOb;
			rowOb = row.toArray();
			Card c;
			for(int i = rowOb.length-1; i >= 0; i--){
				c = (Card) rowOb[i];
				//System.out.println("Adding card: " + c.toString());
				c.setLocation(xOffset, yOffset);
				c.setSize(new Dimension(80, 113)); 
				 if (i==rowOb.length-1){
				 	c.show();
				 } 
				 else {
				  	c.hide();
				 }
				pile1.add(c);
				yOffset -= 20; 
			}
			newOffset -= 20;
			yOffset = newOffset;
			xOffset += 110;
		}
		pile1.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.BLUE));
		pile1.setSize(750, 350);

		Card f = game.getDeck().peek();
		f.setPreferredSize(new Dimension(90,120));
		f.hide();
		deckPanel.add(f); 
		
		/*Stack<Card> dk = new Stack<>();
		Card card = new Card(2, Card.Suit.Diamonds);
		card.addMouseListener(this);
		card.addMouseMotionListener(this);
		Card card2 = new Card(2, Card.Suit.Spades);
		card2.addMouseListener(this);
		card2.addMouseMotionListener(this);
		Card card3 = new Card(2, Card.Suit.Hearts);
		card3.addMouseListener(this);
		card3.addMouseMotionListener(this);
		dk.add(card);
		dk.add(card2);
		dk.add(card3);
		reveal.add(drawPile(dk));*/
		screenLayers = getLayeredPane();
        this.setVisible(true);
    }

private void update() {

		pile1.removeAll();
		int xOffset = 0;
		int yOffset = 130;
		int newOffset = 130;
		//Card c: row


		for(Stack<Card> row: game.columns){
			Object[] rowOb;
			rowOb = row.toArray();
			Card c;
			for(int i = rowOb.length-1; i >= 0; i--){
				c = (Card) rowOb[i];
				//System.out.println("Adding card: " + c.toString());
				c.setLocation(xOffset, yOffset);
				c.setSize(new Dimension(80, 113)); 
				//  if (i==rowOb.length-1){
				//  	c.show();
				//  } 
				//  else {
				//   	c.hide();
				//  }	
				c.show();		
				 System.out.println("trying to add "+c);			 
				pile1.add(c);
				yOffset -= 20; 
			}
			newOffset -= 20;
			yOffset = newOffset;
			xOffset += 110;
		}

}

// Creates a pile of flipped cards all next to each other so people can see the next card in the deck
	public JLayeredPane drawPile(Stack<Card> stackIn) {
    	Object cards[];
    	cards = stackIn.toArray(); 
		int yOffset = 130;
		int xOffset = 0;
		JLayeredPane piled = new JLayeredPane();
    	for (int i = 0; i < cards.length; i++){
			Card card = (Card) cards[i];
			card.setLocation(0, yOffset);
			card.setSize(90,120);

			piled.add(card);
			yOffset -= 20;
		}
		piled.setPreferredSize(new Dimension(190,120));
		return piled;

	}

	@Override
	//JACK
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		/*private JLayeredPane screenLayers;
		screenLayers = getLayeredPane();
		JLayeredPane draggablePane;
		screenLayers.add(draggablePane, JLayeredPane.DRAG_LAYER);
		
		*/

		//TODO: If you have time come back to this
	// 	if (draggablePane!= null) {
    //         Point pos = getLocationOnScreen();
    //         pos.x = arg0.getLocationOnScreen().x - 50;
    //         pos.y = arg0.getLocationOnScreen().y - 50;
    //         draggablePane.setLocation(pos);   		}
	// repaint();
		
	}

	@Override

	//ALEX
	//Pre-condition: none
	//Post-condition:Mouse moves is tracked
	public void mouseMoved(MouseEvent arg0) {
		double screenx = arg0.getX();
		double screeny = arg0.getY();		
	}
	
	@Override
	//ALEX
	//Pre-condition: Deck is not empty
	//Post-condition: 3 cards are revealed from deck and once 6 are in frame it resets
	public void mouseClicked(MouseEvent arg0){
		//if statment for what mouseClicked is doing
		//deckPanel.contains(arg0.getPoint())
		Point t = arg0.getPoint();
		if(deckPanel.contains(t)){
			reveal.setLayout(null);
			Stack<Card> revealed;
			Point p = SwingUtilities.convertPoint((Component)arg0.getSource(), arg0.getPoint(), deckPanel);	
			
			if(deckPanel.contains(p) && game.getDeck().peek() != null && reveal.getComponentCount() <= 6){
				Card c = (Card)deckPanel.getComponentAt(p);
				revealed = game.getRevealed();
				Object [] cards = revealed.toArray();
				for (int i=0; i<cards.length; i++){
						Card d = (Card)cards[i];
						d.show();
						d.setSize(90, 120);
						d.setLocation(5+i*20, 25);
						reveal.add(d,0);
				}
				Card f = game.getDeck().peek();
				f.hide();
				f.setLocation(5, 25);
				f.setSize(90,120);
				deckPanel.add(f);
				game.reveal3();
				revealed = game.getRevealed();
				repaint();
				
			} else if (deckPanel.contains(p) && game.getDeck().peek() != null){
				//reset revealed cards back to deck
						game.resetRevealed();
						game.reveal3();
						revealed = game.getRevealed();
						Object [] cards = revealed.toArray();
						
						
						for (Component comp : reveal.getComponents()) {
							if (comp instanceof Card){
								comp.setPreferredSize(new Dimension(90,100));
								reveal.remove(comp); 
							}
						}
				for (int i=0; i<cards.length; i++){
						Card d = (Card)cards[i];
						d.show();
						d.setSize(90, 120);
						d.setLocation(5+i*20, 25);
						reveal.add(d,0);
				}
				Card f = game.getDeck().peek();
				f.hide();
				f.setLocation(5, 25);
				f.setSize(90,120);
				deckPanel.add(f);
					
					// Prepare next 3 cards for next click
					game.reveal3();
					revealed = game.getRevealed();
					repaint();
		
					}
					
			}
	}


		
		// TODO Auto-generated method stub
	
	

	@Override
	//
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	//JACK

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		/*originalLocation = arg0.getPoint();
		Card c = (Card)arg0.getSource();
		Point pos = getLocationOnScreen();
		JLayeredPane pile = null;

		if (((JPanel)arg0.getSource()).getParent() instanceof JLayeredPane)
		{
			pile = (JLayeredPane) ((JPanel)arg0.getSource()).getParent();
		}
		
		

		if (arg0.getSource() instanceof Card && pile != null)
		{
			if (game.checkPress(c, pile) != null)
			{
				pile = (JLayeredPane) ((JPanel)arg0.getSource()).getParent();
				draggablePane = game.checkPress(c, pile);
				pos.x = arg0.getLocationOnScreen().x - 50;
	        	pos.y = arg0.getLocationOnScreen().y - 50;
	        	draggablePane.setLocation(pos);
				screenLayers.add(draggablePane, JLayeredPane.DRAG_LAYER);
			}
			else{
				return;
			}
			/*draggablePane = new JLayeredPane();
			
			
			draggablePane.setSize(150,120);
			//draggablePane.add(c);
			draggablePane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.PINK));
			c.setLocation(0, 0);
			//the pile where the card is at
			
			//the layer the card is at
			int cardPos = pile.getPosition(c);
			//add every card under to the pile
			for (int i = 0; i <= cardPos; i++)
			{
				Stack<Card> temp = new Stack<>();
				Card temp1 = (Card) pile.getComponent(0);
				temp1.setLocation(20 * i, 0);
				draggablePane.add(temp1);

			}
			

			pos.x = arg0.getLocationOnScreen().x - 50;
	        pos.y = arg0.getLocationOnScreen().y - 50;
	        draggablePane.setLocation(pos);
			screenLayers.add(draggablePane, JLayeredPane.DRAG_LAYER);*/
			//System.out.println(draggablePane.toString());
			
		//}
		
		//repaint();
	}

	@Override
	//ABBY
	public void mouseReleased(MouseEvent arg0) {
/*
		Card current = (Card)arg0.getSource();
		Card m = null;
		Point p = arg0.getLocationOnScreen();
		boolean type;
		if (pile1 != null && pile1.getBounds().contains(p)) {
            Component[] components = pile1.getComponents();
			for (Component comp : components) {
        		if (comp.getBounds().contains(p)) {
					m = (Card)comp;
				}
        }
			type = false;
			if(game.checkRelease(current, m, type)){
				current.setLocation(m.getLocation());
			}
    } else if(completed != null && completed.getBounds().contains(p)){
			Component[] components = completed.getComponents();
			for (Component comp : components) {
        		if (comp.getBounds().contains(p)) {
					m = (Card)comp;
				}
			type = true;
			if(game.checkRelease(current, m, type)){
				current.setLocation(m.getLocation());
			}
        } 
		}else{
			current.setLocation(originalLocation);
		}
		/*if (screenLayers != null)
		{
			screenLayers.remove(draggablePane);
			repaint();
	 }	*/
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	class Clicker implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			//System.out.println(ep.toString);
			System.out.println(deckPanel.getBounds());
			if (pile1.contains(e.getPoint()) || completed.contains(e.getPoint()) || reveal.contains(e.getPoint())){
				boolean type = false;
				boolean moved = false;
				if(clicked1== null){
					clicked1 = (Card)e.getComponent();
					System.out.print(clicked1.toString());
					clicked1.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
					return;
				}
				else if(clicked2 == null){
					clicked2 = (Card)e.getComponent();
					System.out.print(clicked2.toString());
					if (clicked1.isReversed){
						clicked1 = null;
						clicked2 = null;
						repaint();
						return;
					}
					System.out.println("before check release");
					game.checkRelease(clicked1, clicked2);
					System.out.println("after check release");
					update();
					clicked1.setBorder(null);
					clicked1 = null;
					clicked2 = null;
					repaint();	
				}
		}				
			
		}
						// Object [] cards = pile1.getComponents();
						// for(Object comp: cards){
						// 	if(((Component) comp).getLocation() == clicked2.getLocation()){
						// 		type = false;
						// 	}
						// }
						// Object [] cards2 = completed.getComponents();
						// for(Object comp: cards2){
						// 	if(((Component) comp).getLocation() == clicked2.getLocation()){
						// 		type = true;
						// 	}
						// }
						// if(game.checkRelease(clicked1, clicked2, type)){
						// 		clicked1.setLocation(clicked2.getX(), clicked2.getY());
						// 		moved = true;
						// 	}
						// }
					
			

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			//throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			//throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			//throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		//	throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
		}

	}
}
