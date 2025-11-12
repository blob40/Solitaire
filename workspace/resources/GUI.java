
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
	private JLayeredPane pile1;
	private JPanel completed;

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
		JLayeredPane pile1 = new JLayeredPane();
        pile1.setSize(750,375);
		pile1.setLocation(20, 175);
		pile1.setBackground(Color.PINK);
		JLabel pileL1 = new JLabel("7 Piles");
		pile1.add(pileL1);
		this.add(pile1);

		//Where suits will be set A-K
		completed = new JPanel();
		completed.setLayout(new FlowLayout());
        completed.setSize(400,150);
		completed.setLocation(370, 20);
		completed.setBackground(Color.ORANGE);
		JLabel completedL = new JLabel("4 completion piles");
		completed.add(completedL);
		this.add(completed);

		screenLayers = getLayeredPane();

// Created test to see stacked cards as revealed cards
		Stack<Card> deck = new Stack<>();
		Card c1 = new Card(5, Card.Suit.Spades);
		c1.addMouseListener(this);
		


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
				System.out.println("Adding card: " + c.toString());
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
        this.setVisible(true);
    }

/*private void update() {

    columns.removeAll();
    topColumns.removeAll();
	ArrayList<Stack<Card>> allColumns = game.getColumns();

	for(Stack<Card> stack: allColumns) {
		topColumns.add(drawPile(stack, false)); 
	}

	columns.add(drawDeck(game.getDeck()));
	columns.add(drawPile(game.getPile(), true));
	columns.add(drawFinal(game.hearts, "hearts"));
	columns.add(drawFinal(game.spades, "spades"));
	columns.add(drawFinal(game.diamonds, "diamonds"));
	columns.add(drawFinal(game.clubs, "clubs"));
	System.out.println("updating");

    this.revalidate();
    this.repaint();

}
*/
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

		//System.out.println("screen x " + screenx + "screen y " + screeny);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	//ALEX
	public void mouseClicked(MouseEvent arg0){
		Stack<Card> revealed;
		Point p = SwingUtilities.convertPoint((Component)arg0.getSource(), arg0.getPoint(), deckPanel);	
		
		if(deckPanel.contains(p) && game.getDeck().peek() != null && reveal.getComponentCount() <= 6){
			Card c = (Card)deckPanel.getComponentAt(p);
			revealed = game.getRevealed();
			System.out.println(revealed);
			int y = 105;
			int count = 0;
			for (int i = 0; i<revealed.size(); i++){
					Card d = revealed.get(i);
					d.show();
					d.setSize(90, 120);
					d.setLocation(y - 20 * count, 25);
					reveal.add(d);
					Card f = game.getDeck().peek();
					f.hide();
					f.setLocation(5, 25);
					f.setSize(90,120);
					deckPanel.add(f);
					count++;
					repaint();
			}
			game.reveal3();
			revealed = game.getRevealed();
			repaint();
		} else if (deckPanel.contains(p) && game.getDeck().peek() != null){
			//reset revealed cards back to deck
					game.resetRevealed();
					 for (Component comp : reveal.getComponents()) {
						 if (comp instanceof Card){
							comp.setPreferredSize(new Dimension(90,120));
					    	reveal.remove(comp); 
						 }
					 }
					repaint();
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
			System.out.println(draggablePane.toString());
			
		}
		
		repaint();
	}

	@Override
	//ABBY
	public void mouseReleased(MouseEvent arg0) {

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
			//move card back
		}
		/*if (screenLayers != null)
		{
			screenLayers.remove(draggablePane);
			repaint();
	}
		*/
		
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
