              package resources;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.Collections;
import java.util.List;
import java.awt.Component;
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
				deck.add(new Card(i, s));
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

	public Card reveal3(){
		Card c = null;
		for (int i = 0; i < 3; i++){
		c = deck.remove();
		revealed.add(c);
	}
		System.out.println("Card " + c);
		System.out.println(revealed);
		return c;
	}

	
	public boolean checkMove(Card current, Card m, boolean type){
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
   

   public Container getContainer(Card c){
	
   }

	public Queue<Card> getDeck(){
		return deck;
	}
	//the part of your program that's in charge of game rules goes here.
	public boolean checkPress(int x, int y)
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
