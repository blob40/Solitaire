              package resources;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import java.util.List;
import java.awt.*;
import java.util.*;

public class Solitaire {
	ArrayList<Stack <Card>> columns;
	Queue<Card> deck;
	Stack<Card> revealed;
	ArrayList<Stack<Card>> finalPiles;
	
	public Solitaire(){
		//deck.add
		
		columns = new ArrayList();
        deck = new LinkedList<>();
		revealed = new Stack();
		finalPiles = new ArrayList<>();

		for(Card.Suit s: Card.Suit.values()){
			for(int i = 1; i < 14; i++){
				Card newC = new Card(i,s);
				deck.add(newC);
				newC.hide();
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
				if(z==0){
					row.peek().show();
				}
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
			c = deck.poll();
			revealed.add(c);
			System.out.println(revealed);
		}
	    
	} 

   //public Container getContainer(Card c){
	
  // }

	public Queue<Card> getDeck(){
		return deck;
	}

	//the part of your program that's in charge of game rules goes here.

	public void checkRelease(Card current, Card m){
		//System.out.println("Check Realease started");
		//moving from one column to another column
		boolean type = false;
		Stack<Card> temp = new Stack<>();
		
		// Find if m is in columns
		for(Stack<Card> s: columns){
			for(Card c: s){
				if(c.equals(m)){
					temp = s;
					type = true;
				}
			}
		}
		
		Stack<Card> finalPile = null;
		// Find if m is in final piles
		for(Stack<Card> t: finalPiles){
			for(Card b: t){
				if(b.equals(m)){
					type = false;
					finalPile = t;
				}
			}
		}

		Stack<Card> toMove = null;
		
		// Find source stack
		if (revealed != null && !revealed.isEmpty()) {
			System.out.println(revealed.get(0));
			Card lastRevealed = revealed.get(revealed.size() - 1);
			if (current.equals(lastRevealed)) {
				Stack<Card> tempR = new Stack<>();
				tempR.add(lastRevealed);
				toMove = tempR;
			}
		}

		outerloop:
		for (Stack<Card> s : columns) {
			for (Card c : s) {
				if (c == current && !c.isReversed) {
					toMove = s;
					break outerloop;
				}
			}
		}
		
		// Move to columns
		if (type && toMove != null && m.suit.isRed != current.suit.isRed && m.value == current.value+1){
			//System.out.println("is legal move");
			Stack<Card> backwards = new Stack();
			System.out.println("To move " + toMove);
			while(!toMove.isEmpty() && toMove.peek()!= current){
				backwards.push(toMove.pop());
			}
			if(!toMove.isEmpty()){
				backwards.push(toMove.pop());
			}
			while(!backwards.isEmpty()){
				temp.push(backwards.pop());
			}
			
			if(toMove != null && !toMove.isEmpty())
				toMove.peek().show();
		}
		
		// Move to final piles - case where m exists (moving onto existing card)
		if (!type && finalPile != null && toMove != null && current.value == m.value + 1 && current.suit == m.suit) {
			System.out.println("Legal move to final pile (onto existing card)");
			Card cardToMove = toMove.pop();
			finalPile.push(cardToMove);
			// If card came from revealed pile, remove it from there too
			if (revealed != null && !revealed.isEmpty() && revealed.contains(cardToMove)) {
				revealed.remove(cardToMove);
			}
			if(toMove != null && !toMove.isEmpty())
				toMove.peek().show();
		}
		
		// Move to final piles - case where m is null (moving to empty pile with Ace)
		if (m == null && toMove != null && current.value == 1) {
			// Find an empty final pile
			for (Stack<Card> pile : finalPiles) {
				if (pile.isEmpty()) {
					System.out.println("Legal move to empty final pile (Ace)");
					Card cardToMove = toMove.pop();
					pile.push(cardToMove);
					// If card came from revealed pile, remove it from there too
					if (revealed != null && !revealed.isEmpty() && revealed.contains(cardToMove)) {
						revealed.remove(cardToMove);
					}
					if(toMove != null && !toMove.isEmpty())
						toMove.peek().show();
					break;
				}
			}
		}

		System.out.print(columns);
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
