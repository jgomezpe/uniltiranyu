package test.labyrinth.teseo;
import java.util.*;

import uniltiranyu.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;

/**
 *
 * @author Jonatan
 */
public class TeseoSimple extends SimpleTeseoAgentProgram {

	
	//where i've been
    public Stack<Stack<int[]>> wib;
    //where i'm trying
    public Stack<int[]> wit;
    //mind map
    public LinkedList<int[]> mindmap;
    //position
    public int[] position;
    //where I'm looking
    public int view;
	// am I returning to a place where I had to make a decision?
    public boolean returning;
    public TeseoSimple(){
    	this.wib = new Stack<Stack<int[]>>();
    	this.wit = new Stack<int[]>();
    	this.mindmap = new LinkedList<int[]>();
    	this.position = new int[2];
    	//I'll always start counting at [0,0]
    	this.position[0] = 0;
    	this.position[1] = 0;
    	this.view = 0;
    	//0 => up;1 => right;2 => down; 3 => left;
    	this.returning = false;
    }
    
    //have I been at?
    public boolean haveIBeenAt(int[] positionTocheck) {
    	for(int i = 0; i<this.mindmap.size();i++) {
    		if(Arrays.equals(this.mindmap.get(i),positionTocheck)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    //where to look
    public int[] look(int v) {
    	v = v%4;
    	if(v == 0) {
    		int[] way = {0,-1};
    		//System.out.println("that's up");
    		return way;
    	}else if(v == 1) {
    		int[] way = {1,0};
    		//System.out.println("that's right");
    		return way;
    	}else if(v == 2) {
    		int[] way = {0,1};
    		//System.out.println("that's down");
    		return way;
    	}else{
    		int[] way = {-1,0};
    		//System.out.println("that's left");
    		return way;
    	}
    }
    
    //where to view
    
    public int whereToview(int[] a) {
    	System.out.println("moving to:["+a[0]+","+a[1]+"]");
    	if(a.length != 2)return -1;
    	int[] f = {0,-1};
    	if(Arrays.equals(a,f)) return 0;
    	int[] d = {1,0};
    	if(Arrays.equals(a,d)) return 1;
    	int[] b = {0,1};
    	if(Arrays.equals(a,b)) return 2;
    	int[] i = {-1,0};
    	if(Arrays.equals(a,i)) return 3;
    	return -1;
    }
    
    public void printStack() {
    	if(this.returning)System.out.println("returning!");
    	System.out.println("My position:["+this.position[0]+","+this.position[1]+"]");
    	System.out.println("where I'm looking:"+this.view);
    	System.out.println("where i'm trying: ");
    	System.out.print("[");
    	for(int i = 0;i<this.wit.size();i++) {
    		System.out.print("("+this.wit.get(i)[0] + ","+this.wit.get(i)[1]+")");
    	}
    	System.out.println("]");
    	//this.showPositions();
    	System.out.println("Saved stacks");
    	for(int i = 0; i<this.wib.size();i++) {
    		System.out.print("[");
    		for(int j = 0;j<this.wib.get(i).size();j++) {
    			System.out.print("("+this.wib.get(i).get(j)[0]+","+this.wib.get(i).get(j)[1]+")");
    		}
    		System.out.println("]");
    	}
    	System.out.println();
    }
    
    public int backTracking(int d) {
    	System.out.println("Backtracked: "+ d);
    	if(d == 0)return 0;
    	if(d == 1)return 3;
    	if(d == 3)return 1;
    	
    	return 2;
    }
    
    //Is there somewhere to move?
    public boolean someWhereToGo(boolean PF, boolean PD, boolean PA, boolean PI) {
    	if(!PF && !haveIBeenAt(this.addArrays(this.position, this.look(0+this.view)))) {
        	return true;
        }
        if(!PD && !haveIBeenAt(this.addArrays(this.position, this.look(1+this.view)))) {
        	return true;
        }
        if(!PA && !haveIBeenAt(this.addArrays(this.position, this.look(2+this.view)))) {
        	return true;
        }
        if(!PI && !haveIBeenAt(this.addArrays(this.position, this.look(3+this.view)))) {
        	return true;
        }
    	return false;
    }
    
    public int[] addArrays(int[] a, int[] b) {
    	
    	if(a.length != b.length) {
    		int[] solution = {};
    		return solution;
    	}else{
    		int[] solution = new int[a.length];
    		for(int i = 0; i<a.length;i++) {
    			solution[i] = a[i] + b[i];
    		}
    		return solution;
    	}
    }
    
    public int[] subArrays(int[] a, int[] b) {
    	
    	if(a.length != b.length) {
    		int[] solution = {};
    		return solution;
    	}else{
    		int[] solution = new int[a.length];
    		for(int i = 0; i<a.length;i++) {
    			solution[i] = a[i] - b[i];
    		}
    		return solution;
    	}
    }
    
    public void showPositions() {
    	System.out.println("Positions visited");
    	for(int i = 0;i<this.mindmap.size();i++) {
    		System.out.println("["+this.mindmap.get(i)[0]+","+this.mindmap.get(i)[1]+"]");
    	}
    	System.out.println("----------------------------------");
    }
    
    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean FAIL) {
    	
        if (MT || FAIL) return -1;
        
        //System.out.println(this.view+ " Returning: " + this.returning + " Position: ["+ this.position[0] + "," +this.position[1]+ "]");
        //System.out.println("view: "+ this.view+ " Stack size:" + this.wit.size());
        //this.printStack();
        
        if(this.returning) {
        	System.out.println(someWhereToGo( PF, PD, PA, PI));
        	
        	if(this.wit.isEmpty() && !someWhereToGo( PF, PD, PA, PI)) {
        		System.out.println("wib.size()=>"+ this.wib.size());
        		while(this.wib.get(this.wib.size()-1).size()==0) {
        			this.wib.remove(this.wib.size()-1);
        		}
        		while(this.wib.get(this.wib.size()-1).size()>0) {
        			this.wit.push(this.wib.get(this.wib.size()-1).pop());
        		};
        		//this.wit = this.wib.pop();
        		this.wib.remove(this.wib.size()-1);
        		System.out.println("Poped!");
        		this.printStack();
        		if(!this.wit.isEmpty()&&Arrays.equals(this.wit.peek(),this.position)) {
        			this.wit.pop();
        		}
        		this.printStack();
        		int going = this.whereToview(this.subArrays(this.wit.pop(),this.position));
        		
        		this.position=this.addArrays(this.position, this.look((going)%4));
            	int olView = this.view;
            	this.view = (going)%4;
            	System.out.println("Moving to:"+going+"-"+olView+"="+(going-olView)%4);
            	return (going-olView)%4;
        	}else if(/*this.wit.isEmpty() && */someWhereToGo( PF, PD, PA, PI)){
        		System.out.println("Got here");
        		this.returning = false;
        		this.printStack();
        	}else {
        		
        		if(Arrays.equals(this.wit.peek(),this.position)) {
        			this.wit.pop();
        		}
        		this.printStack();
        		int going = this.whereToview(this.subArrays(this.wit.pop(),this.position));
        		
        		this.position=this.addArrays(this.position, this.look((going)%4));
        		int olView = this.view;
            	this.view = (going)%4;
            	System.out.println("Moving to:"+going+"-"+olView+"="+(going-olView)%4);
            	return (going-olView)%4;
        	}
        	
        }
        
        int count = 0;
        this.mindmap.push(this.position);
        
        if(!PF && !haveIBeenAt(this.addArrays(this.position, this.look(0+this.view)))) {
        	count++;
        }
        if(!PD && !haveIBeenAt(this.addArrays(this.position, this.look(1+this.view)))) {
        	count++;
        }
        if(!PA && !haveIBeenAt(this.addArrays(this.position, this.look(2+this.view)))) {
        	count++;
        }
        if(!PI && !haveIBeenAt(this.addArrays(this.position, this.look(3+this.view)))) {
        	count++;
        }
        
        //System.out.println("count:"  + count);
        //this.showPositions();
        
        //Is there somewhere to move?
        
        if(count == 0) {//No, I must return
        	//this.wit.push(this.position);
        	//this.wit.pop();
            this.printStack();
        	this.position=this.addArrays(this.position, this.look(2+this.view));
        	this.view = (this.view+2)%4;
        	this.returning = true;
        	return 2;
        }else if(count == 1) {//just one way, I'll go there
        	this.wit.push(this.position);
            this.printStack();
            if (MT) {
            	return -1;
            }
            if (!PI && !haveIBeenAt(this.addArrays(this.position, this.look(3+this.view)))) {
            	this.position=this.addArrays(this.position, this.look(3+this.view));
            	this.view = (this.view + 3)%4;
            	return 3;
            }
            if (!PF && !haveIBeenAt(this.addArrays(this.position, this.look(0+this.view)))) {
            	this.position=this.addArrays(this.position, this.look(0+this.view));
            	this.view = (this.view + 0)%4;
            	return 0;
            }
            if (!PD && !haveIBeenAt(this.addArrays(this.position, this.look(1+this.view)))) {
            	this.position=this.addArrays(this.position, this.look(1+this.view));
            	this.view = (this.view + 1)%4;
            	return 1;
            }
            this.position=this.addArrays(this.position, this.look(2+this.view));
        	this.view = (this.view + 2)%4;
            return 2;
        }else if(count>1){
        	this.wib.push(new Stack<int[]>());
        	System.out.println("Pushed wit with size:"+ this.wit.size());
        	while(this.wit.size()>0) {
        		this.wib.get(this.wib.size()-1).push(this.wit.pop());
        	}
        	this.wit.clear();
        	this.wit.push(this.position);
            this.printStack();
            if (MT) {
            	return -1;
            }
            if (!PI && !haveIBeenAt(this.addArrays(this.position, this.look(3+this.view)))) {
            	this.position=this.addArrays(this.position, this.look(3+this.view));
            	this.view = (this.view + 3)%4;
            	return 3;
            }
            if (!PF && !haveIBeenAt(this.addArrays(this.position, this.look(0+this.view)))) {
            	this.position=this.addArrays(this.position, this.look(0+this.view));
            	this.view = (this.view + 0)%4;
            	return 0;
            }
            if (!PD && !haveIBeenAt(this.addArrays(this.position, this.look(1+this.view)))) {
            	this.position=this.addArrays(this.position, this.look(1+this.view));
            	this.view = (this.view + 1)%4;
            	return 1;
            }
            this.position=this.addArrays(this.position, this.look(2+this.view));
        	this.view = (this.view + 2)%4;
            return 2;
        	
        }
        
        return -1;
    }
    
}
