              package resources;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import java.util.Collections;
import java.util.List;
import java.awt.Component;
import java.awt.*;
import java.util.*;

public class Solitaire {
	ArrayList<Stack <Card>> columns;
	Queue<Card> deck;
	Stack<Card> revealed;
	
	public Solitaire(){
		//deck.add
		
		columns = new ArrayList();
        deck = new LinkedList<>();
		revealed = new Stack();

		for(Card.Suit s: Card.Suit.values()){
			for(int i = 1; i < 14; i++){
				Card newC = new Card(i,s);
				deck.add(newC);
			}
		}
	}
	
	 public void setup(){
		//Card[] deck = deck.toArray();
		//ArrayList<Card> deck = Arrays.asList(deck);
		Collections.shuffle((List<?>) deck);
		int j = 6;
		for(int i = 0; i<8; i++){
			Stack<Card> row = new Stack<>();
			for(int z = j; z>=0; z--){
				row.push(deck.remove());
				//System.out.println("row " + row);
			}
			columns.add(row);
			j--;
		}		
		reveal3();
	}

	public Stack getRevealed (){
		return revealed;
		
	}

	 public void resetRevealed() {

		while (revealed.isEmpty() == false) {
	        	Card c = revealed.pop();
				c.hide();
	        	deck.add(c);
	        }
		revealed.clear();
	    }

	public void reveal3(){
		Card c = null;
		for (int i = 0; i < 3; i++){
		c = deck.remove();
		revealed.add(c);
		//System.out.println(c);
	}
	    
	} 

   //public Container getContainer(Card c){
	
  // }

	public Queue<Card> getDeck(){
		return deck;
	}

	//the part of your program that's in charge of game rules goes here.

	public boolean checkRelease(Card current, Card m, boolean type){
		if(type){
			if((m.suit.equals(current.suit))&&(m.value == (current.value-1))){
				return true;
			}
		} else {
			if((m.suit.isRed && !current.suit.isRed)&&(m.value == (current.value+1))){
				return true;
			}
		}
		return false;
	} 

	public JLayeredPane checkPress(Card c, JLayeredPane pile)
	{
		if (pile != null && c.isReversed == false)
		{
			JLayeredPane pane = new JLayeredPane();

			
			pane.setSize(150,120);
			//draggablePane.add(c);
			pane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.PINK));
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
				pane.add(temp1);

			}
			return pane;
		}
		return null;
	}

	public boolean checkClick(int x, int y)
	{
		return true;
	}

	public boolean checkWin()
	{
		for (Stack<Card> s: columns)
		{
			if (s.size() == 13)
			{
				return true;
			}
		}
		return false;
	}

   
}
