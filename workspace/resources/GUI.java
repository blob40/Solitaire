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
	private JPanel pile1;
	private JPanel completed;

   	public GUI(Solitaire game){
	   this.game= game;
	  // for(Card card: game.getDeck()){
	//	card.addMouseListener(this);
		//card.addMouseMotionListener(this);
	 //  }
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
		pile1 = new JPanel();
		pile1.setLayout(new FlowLayout());
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
		
		
		Card si = new Card(5, Card.Suit.Spades);
		si.addMouseListener(this);
		si.setPreferredSize(new Dimension(90,120));
		si.hide();
		deckPanel.add(si);


	//Repile button
       JButton reDraw = new JButton();
	   reDraw.setPreferredSize(new Dimension(90,120));
	   reDraw.setBackground(Color.yellow);
	   deckPanel.add(reDraw);
       reDraw.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					mouseClicked(null);
					repaint();
			}
				
			});


		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		game.setup();
		int xOffset = 0;
		int yOffset = 0;
		for(Stack<Card> row: game.columns){
			for(Card c: row){
				//System.out.println("Adding card: " + c.toString());
				c.setLocation(xOffset, yOffset);
				c.setSize(85, 118); 
				pile1.add(c); 
				yOffset += 20; 
			}
			yOffset = 0;
			xOffset +=20;
		}
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

		System.out.println("screen x " + screenx + "screen y " + screeny);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	//ALEX
	public void mouseClicked(MouseEvent arg0){
		if (arg0.getX() < 120 && arg0.getX() > 20 && arg0.getY() < 185 && arg0.getY() > 60){
			reveal.add(game.reveal3());
			repaint();
			if (game.getDeck() == null){
			//deckPanel.add(game.revealOne().hide());
			 game.getRevealed().clear();
			 reveal.removeAll();
			 repaint();
			}
			else {
				System.out.println("Clicked");
			}
		} 

		
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
		Card c = (Card)arg0.getSource();

		if (game.checkPress(x, y) && arg0.getSource() instanceof Card)
		{
			draggablePane = new JLayeredPane();
			Point pos = getLocationOnScreen();
			

			draggablePane.setSize(150,120);
			draggablePane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.PINK));
			c.setLocation(0, 0);
			JLayeredPane pile = ((JPanel)arg0.getSource()).getParent();
			int cardPos = pile.getPosition(c);
			/*for (int i = cardPos; pile.getPosition(c); i > 0; i--)
			{
				Card temp = pile.getComponent(i);
				temp.setLocation(0, 10 * cardPos - i);
				draggablePane.add(temp, cardPos - i);
			}*/
			draggablePane.add(c);

			pos.x = arg0.getLocationOnScreen().x - 50;
	        pos.y = arg0.getLocationOnScreen().y - 50;
	        draggablePane.setLocation(pos);
			screenLayers.add(draggablePane, JLayeredPane.DRAG_LAYER);
		}
		repaint();
	}

	@Override
	//ABBY
	public void mouseReleased(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		Card current = (Card)arg0.getSource();
		Card m;
		Point p = arg0.getLocation();
		boolean type;
		if (pile1.contains(p)) {
            m = (Card)pile1.findComponentAt(p);
			type = false;
			if(game.checkMove(current, m, type)){
				current.setLocation(m.getLocation());
			}
        } else if (completed.contains(p)){
            m = (Card)completed.findComponentAt(p);
			type = true;
			if(game.checkMove(current, m, type)){
				current.setLocation(m.getLocation());
			}
        }
		screenLayers.remove(draggablePane);
		repaint();
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
