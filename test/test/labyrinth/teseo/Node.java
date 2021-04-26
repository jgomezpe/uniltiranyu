package test.labyrinth.teseo;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public Node parent; // The parent of the current node
    public List<Node> children; // The children of the current node
    public int x;
    public int y;
    public int TimesVisited;
    public int numWalls;

    public static int maxNrOfChildren; 

    public Node (int x, int y){
        //this.info=info; 
    	this.x = x;
    	this.y = y;
    	this.TimesVisited = 0;
    	this.numWalls = -1;
        children  = new ArrayList<Node>(maxNrOfChildren);
    }

    public void setChild(Node childNode, int position)
    {
        if(position>=maxNrOfChildren-1){
            // {todo: Throw some error}
        }

        else {
            System.out.println("this.children="+this.children);
            if(this.children.get(position)!=null){
                // {todo: There is alerady a child node on this position; throw some error;}
            } else{
                childNode.parent=this;
                this.children.set(position, childNode);
            }
        }
    }
    
    public void addChild(Node Child) {
    	this.children.add(Child);
    	this.TimesVisited++;
    }
    
    public void printTimesVisited() {
    	System.out.println("timesv:"+Integer.toString(this.TimesVisited));
    }
    public int getTimesVisited() {
    	return this.TimesVisited;
    }
    
    public void setNumWalls(int walls) {
    	this.numWalls = walls;
    }
    
    public int getNumWalls() {
    	return this.numWalls;
    }
    
    public void visitNode() {
    	this.TimesVisited++;
    }
	public List<Node> getNeighbours() {
		return children;
	}
}