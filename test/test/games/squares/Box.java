package test.games.squares;

//import java.util.Array;
import speco.array.Array;

public class Box {
	
	public int[] coordinates = new int[2];
	public Array<Integer> connections;
	public int prevMov;
 	
    public Box (int x, int y){
    	this.coordinates[0] = x;
    	this.coordinates[1] = y;
    	this.prevMov = -1;
    	this.connections = new Array<Integer>();
    }
    
    public void addOption(int option){
    	this.connections.add(option);
    }
    
    public void deleteOptions() {
    	this.connections = new Array<Integer>();
    }
    
    public void printBox() {
	System.out.print("- box "+this.coordinates[0]+","+this.coordinates[1]+":");
    	for(Integer opt : this.connections) {
    		System.out.print("- "+opt+" ");
    	}
    }
    
    public int[] getCoordinates(){
    	return this.coordinates;
    }
    
    public Array<Integer> getConnections(){
    	return this.connections;
    }
        
}