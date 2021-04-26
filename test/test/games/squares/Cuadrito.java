package test.games.squares;

import speco.array.Array;
import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.squares.Squares;

import java.util.*;

/**
 *
 * @author Andrés Aranguren, David Rico
 */
public class Cuadrito implements AgentProgram {
    protected String color;
    protected HashMap<Integer, ArrayList<String>> Memory = new HashMap<>();

    public Cuadrito( String color ){
        this.color = color;
    }

    @Override
    public Action compute(Percept p) {
        long time = (long)(200 * Math.random());
        try{
            Thread.sleep(time);
        }catch(Exception e){}
        if( p.get(Squares.TURN).equals(color) ){
            int size = Integer.parseInt((String)p.get(Squares.SIZE));
            int i = 0;
            int j = 0;
            String[] o = null;
            Array<String> v = new Array<String>();
            while(v.size()==0){
                for(i = 0; i < size; i++){
                    for(j = 0; j < size; j++){
                        String value_cell = "";

                        if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE))
                            value_cell += "l";
                        if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE))
                            value_cell += "t";
                        if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE))
                            value_cell += "b";
                        if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE))
                            value_cell += "r";

                        if(value_cell.length() == 4){
                            if(Memory.containsKey(1)){
                                ArrayList aux = Memory.get(1);
                                aux.add(Integer.toString(i)+":"+Integer.toString(j)+":"+value_cell);
                                Memory.replace(1, aux);
                            }else{
                                ArrayList aux = new ArrayList();
                                aux.add(Integer.toString(i)+":"+Integer.toString(j)+":"+value_cell);
                                Memory.put(1, aux);
                            }
                        }else if(value_cell.length() == 3){
                            if(Memory.containsKey(0)){
                                ArrayList aux = Memory.get(0);
                                aux.add(Integer.toString(i)+":"+Integer.toString(j)+":"+value_cell);
                                Memory.replace(0, aux);
                            }else{
                                ArrayList aux = new ArrayList();
                                aux.add(Integer.toString(i)+":"+Integer.toString(j)+":"+value_cell);
                                Memory.put(0, aux);
                            }
                        }else if(value_cell.length() == 2){
                            if(Memory.containsKey(-1)){
                                ArrayList aux = Memory.get(-1);
                                aux.add(Integer.toString(i)+":"+Integer.toString(j)+":"+value_cell);
                                Memory.replace(-1, aux);
                            }else{
                                ArrayList aux = new ArrayList();
                                aux.add(Integer.toString(i)+":"+Integer.toString(j)+":"+value_cell);
                                Memory.put(-1, aux);
                            }
                        }else if(value_cell.length() == 1){
                            if(Memory.containsKey(2)){
                                ArrayList aux = Memory.get(2);
                                aux.add(Integer.toString(i)+":"+Integer.toString(j)+":"+value_cell);
                                Memory.replace(2, aux);
                            }else{
                                ArrayList aux = new ArrayList();
                                aux.add(Integer.toString(i)+":"+Integer.toString(j)+":"+value_cell);
                                Memory.put(2, aux);
                            }
                        }
                    }
                }

                int key_max = Collections.max(Memory.keySet());
                String temp = Memory.get(key_max).get(0);
                o = temp.split(":");
                for(int index = 0; index < o[2].length(); index++){
                    if(o[2].charAt(index) == 'l')v.add(Squares.LEFT);
                    if(o[2].charAt(index) == 't')v.add(Squares.TOP);
                    if(o[2].charAt(index) == 'b')v.add(Squares.BOTTOM);
                    if(o[2].charAt(index) == 'r')v.add(Squares.RIGHT);
                }
            }
            Memory.clear();
            try{
                String move = v.get((int)(Math.random()*v.size()));
                return new Action( o[0]+":"+o[1]+":"+move);
            }catch(Exception e){}
        }
        return new Action(Squares.PASS);
    }

    @Override
    public void init() {

    }
}
