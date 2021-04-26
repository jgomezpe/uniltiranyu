/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.games.squares;

import speco.array.Array;
import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.squares.Squares;

import java.util.*; 


public class cajita implements AgentProgram {
	
	
	protected String color;
    public cajita( String color ){
        this.color = color;        
    }
    public static int jugada =0;
    public static int rayas =0;
    public static int afectados =0;
    public static boolean flag= true;
    public static Hashtable<String, Integer> cuadro =  new Hashtable<String, Integer>();
    public static Hashtable<String, Integer> cuadroAux =  new Hashtable<String, Integer>();
    public static Hashtable<String, Integer> jugadas =  new Hashtable<String, Integer>();

    @Override
    public Action compute(Percept p) {
        long time = (long)(200 * Math.random());
        try{
           Thread.sleep(time);
        }catch(Exception e){}
        if( p.get(Squares.TURN).equals(color) ){
        	jugada = jugada+1;
            int size = Integer.parseInt((String)p.get(Squares.SIZE));
            int i = 0;
            int j = 0;
            if (jugada ==1 || cuadro.size()==0) {
            	rayas =0;
            	String llave ="";
            	for(int aux = 0; aux<size; aux++) {
            		llave = "0,"+aux;
            		cuadro.put(llave,1);
            		rayas = rayas+2;
            	}
            	for(int aux = 0; aux<size; aux++) {
            		llave = (size-1)+","+aux;
            		cuadro.put(llave,1);
            		rayas = rayas+2;
            	}
            	for(int aux = 0; aux<size; aux++) {
            		llave = aux+",0";
            		if(cuadro.containsKey(llave)) {
            			int l = cuadro.get(llave);
            			cuadro.remove(llave);
            			l = l+1;
            			cuadro.put(llave,l);
            		}else {
            			cuadro.put(llave,1);
            		}
            		rayas = rayas+2;
            	}
            	for(int aux = 0; aux<size; aux++) {
            		llave = aux+","+(size-1);
            		if(cuadro.containsKey(llave)) {
            			int l = cuadro.get(llave);
            			cuadro.remove(llave);
            			l = l+1;
            			cuadro.put(llave,l);
            		}else {
            			cuadro.put(llave,1);
            		}
            		rayas = rayas+2;
            	}
            }
            if(cuadro.size()==(size*size)) {
            	rayas= size*size*2;
            }
            if(cuadro.size()==((size*size)-size)) {
            	//System.out.println("**-------------** "+cuadro.size()+"**-------------** "+rayas);
            	//rayas= size*size*2;
            	for (int x = 0; x < size; x++) {
        			for (int y = 0; y < size; y++) {
        				if(!(cuadro.containsKey((String)(y+","+x)))) {
        					
        					//System.out.println("**  ** "+y+"**  ** "+x);
                    	}
					}
				}
            }
            Array<String> v = new Array<String>();
            while(v.size()==0 && rayas<(size*size*2)){
            	i = (int)(size*Math.random());
            	j = (int)(size*Math.random());
            	int l = 0;
            	int laux = 0;
            	if(cuadro.containsKey((String)(i+","+j))) {
            		l = cuadro.get((String)(i+","+j));
            	}
            	if(l<2) {
            		if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE)) {
            			if(j!=0) {
            				int l2 = 0;
            				if(cuadro.containsKey((String)(i+","+(j-1)))) {
                        		l2 = cuadro.get((String)(i+","+(j-1)));
                        	}
            				if(l2<2) {
            					v.add(Squares.LEFT);
            				}
            			}
            		}else {
            			laux++;
            		}
					if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE)){
						if(i!=0) {
							int l2 = 0;
							if(cuadro.containsKey((String)((i-1)+","+j))) {
                        		l2 = cuadro.get((String)((i-1)+","+j));
                        	}
							if(l2<2) {
            					v.add(Squares.TOP);
            				}
            			}
            		}else {
            			laux++;
            		}
					if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE)){
						if(i!=(size-1)) {
							int l2 = 0;
							if(cuadro.containsKey((String)((i+1)+","+j))) {
                        		l2 = cuadro.get((String)((i+1)+","+j));
                        	}
							if(l2<2) {
            					v.add(Squares.BOTTOM);
            				}
            			}
            		}else {
            			laux++;
            		}
					if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE)){
						if(j!=(size-1)) {
							int l2 = 0;
							if(cuadro.containsKey((String)(i+","+(j+1)))) {
                        		l2 = cuadro.get((String)(i+","+(j+1)));
                        	}
							if(l2<2) {
            					v.add(Squares.RIGHT);
            				}
            			}
            		}
					else {
            			laux++;
            		}
					if(l<laux) {
						v = new Array<String>();
						if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.TRUE)) {
							if(j!=0) {
								int l2 = 0;
								int l3 = 0;
	            				int iaux=i;
	            				int jaux=j-1;
	            				if(((String)p.get(iaux+":"+jaux+":"+Squares.LEFT)).equals(Squares.TRUE))
	    		                    l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.TOP)).equals(Squares.TRUE))
	    							l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.BOTTOM)).equals(Squares.TRUE))
	    							l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.RIGHT)).equals(Squares.TRUE))
	    							l3++;
	    						String llave =iaux+","+jaux;	            				
	            				if(cuadro.containsKey(llave)) {
	                        		l2 = cuadro.get(llave);
	                        		if(l2<l3) {
            		        			cuadro.remove(llave);
            		        			cuadro.put(llave,l3);
            		        			rayas=rayas+(l3-l2);
	                        		}
	                        	}else {
	                        		if(0<l3) {
	                        			cuadro.put(llave,l3);
	                        			rayas=rayas+(l3-l2);
	                        		}
	                        	}
	            			}
						}
						if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.TRUE)){
							if(i!=0) {
								int l2 = 0;
								int l3 = 0;
	            				int iaux=i-1;
	            				int jaux=j;
	            				if(((String)p.get(iaux+":"+jaux+":"+Squares.LEFT)).equals(Squares.TRUE))
	    		                    l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.TOP)).equals(Squares.TRUE))
	    							l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.BOTTOM)).equals(Squares.TRUE))
	    							l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.RIGHT)).equals(Squares.TRUE))
	    							l3++;
	    						
	    						String llave =iaux+","+jaux;	            				
	            				if(cuadro.containsKey(llave)) {
	                        		l2 = cuadro.get(llave);
	                        		if(l2<l3) {
            		        			cuadro.remove(llave);
            		        			cuadro.put(llave,l3);
            		        			rayas=rayas+(l3-l2);
	                        		}
	                        	}else {
	                        		if(0<l3) {
	                        			cuadro.put(llave,l3);
	                        			rayas=rayas+(l3-l2);
	                        		}
	                        	}
	            			}
						}
						if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.TRUE)){
							if(i!=(size-1)) {
								int l2 = 0;
								int l3 = 0;
	            				int iaux=i+1;
	            				int jaux=j;
	            				if(((String)p.get(iaux+":"+jaux+":"+Squares.LEFT)).equals(Squares.TRUE))
	    		                    l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.TOP)).equals(Squares.TRUE))
	    							l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.BOTTOM)).equals(Squares.TRUE))
	    							l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.RIGHT)).equals(Squares.TRUE))
	    							l3++;
	    						String llave =iaux+","+jaux;	            				
	            				if(cuadro.containsKey(llave)) {
	                        		l2 = cuadro.get(llave);
	                        		if(l2<l3) {
            		        			cuadro.remove(llave);
            		        			cuadro.put(llave,l3);
            		        			rayas=rayas+(l3-l2);
	                        		}
	                        	}else {
	                        		if(0<l3) {
	                        			cuadro.put(llave,l3);
	                        			rayas=rayas+(l3-l2);
	                        		}
	                        	}
	            			}
						}
						if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.TRUE)){
							if(j!=(size-1)) {
								int l2 = 0;
								int l3 = 0;
	            				int iaux=i;
	            				int jaux=j+1;
	            				if(((String)p.get(iaux+":"+jaux+":"+Squares.LEFT)).equals(Squares.TRUE))
	    		                    l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.TOP)).equals(Squares.TRUE))
	    							l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.BOTTOM)).equals(Squares.TRUE))
	    							l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.RIGHT)).equals(Squares.TRUE))
	    							l3++;
	    						String llave =iaux+","+jaux;	            				
	            				if(cuadro.containsKey(llave)) {
	                        		l2 = cuadro.get(llave);
	                        		if(l2<l3) {
            		        			cuadro.remove(llave);
            		        			cuadro.put(llave,l3);
            		        			rayas=rayas+(l3-l2);
	                        		}
	                        	}else {
	                        		if(0<l3) {
	                        			cuadro.put(llave,l3);
	                        			rayas=rayas+(l3-l2);
	                        		}
	                        	}
	            			}
						}
						String llave =i+","+j; 
						if(cuadro.containsKey(llave)) {                    		
		        			cuadro.remove(llave);
		        			cuadro.put(llave,laux);                    		
                    	}else {                    		
                			cuadro.put(llave,laux);                    		
                    	}
						rayas=rayas+(laux-l);
					}
            	}              
            }
            
            
            if(rayas>((size*size*2)-1)) {
            	if(flag) {
            		//System.out.println("**************************************************** "+rayas);
            		cuadro =  new Hashtable<String, Integer>();
            		for (int x = 0; x < size; x++) {
            			for (int y = 0; y < size; y++) {
								int l3 = 0;
	            				int iaux=y;
	            				int jaux=x;
	            				
	            				if(((String)p.get(iaux+":"+jaux+":"+Squares.LEFT)).equals(Squares.TRUE))
	    		                    l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.TOP)).equals(Squares.TRUE))
	    							l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.BOTTOM)).equals(Squares.TRUE))
	    							l3++;
	    						if(((String)p.get(iaux+":"+jaux+":"+Squares.RIGHT)).equals(Squares.TRUE))
	    							l3++;
	    						rayas=rayas+(l3);
                    			cuadro.put((String)(y+","+x),l3);
            				
            				
            				
            				
						}
					}
            	}
            	
            	
            	
            	
            	/////////////////////////////////////////////////////////////////////////////////////////////////////
            	/////////////////////////////////////////////////////////////////////////////////////////////////////
            	/////////////////////////////////////////////////////////////////////////////////////////////////////
            	if(flag) {
            		cuadroAux=(Hashtable) cuadro.clone();
            		//System.out.println("**********************  Tama�o  ************* "+cuadroAux.size());
            		while(cuadroAux.size()>0){
            			v = new Array<String>();
            			//System.out.println("**********************  Tama�o  ************* "+cuadroAux.size());
            			afectados =0;
            			while(v.size()==0 && cuadroAux.size()>0){
                            
            				String [] coordenadas = cuadroAux.keySet().toArray(new String[cuadroAux.size()]);
    		        		int sizeKeys = coordenadas.length;
    		        		int key = (int)(sizeKeys*Math.random());
    		        		String llave2 = coordenadas[key];
    		        		String [] componentes = llave2.split(",");
    		        		i = Integer.parseInt(componentes[0]);
    		        		j = Integer.parseInt(componentes[1]);
                            
                            
                            if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE))
                              v.add(Squares.LEFT);
                            if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE))
                              v.add(Squares.TOP);
                            if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE))
                              v.add(Squares.BOTTOM);
                            if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE))
                              v.add(Squares.RIGHT);
                            
                            if(v.size()==0) {
    							cuadroAux.remove(llave2);
    						}
                            
                          }
            			//System.out.println("**********************  Tama�o V ************* "+v.size()+" **********************  Tama�o aux ************* "+cuadroAux.size());
            			if(v.size()!=0) {
							
						
	                		String move = v.get((int)(Math.random()*v.size()));
	                		
	                		evaluar(p, i, j, move);
	                		if((Squares.LEFT).equals(move)) {
	                			evaluar(p, i, (j-1), Squares.RIGHT);
	                		}
	                		if((Squares.TOP).equals(move)) {
	                			evaluar(p, (i-1), j, Squares.BOTTOM);
	                		}
	                		if((Squares.BOTTOM).equals(move)) {
	                			evaluar(p, (i+1), j, Squares.TOP);
	                		}
	                		if((Squares.RIGHT).equals(move)) {
	                			evaluar(p, i, (j+1), Squares.LEFT);
	                		}
	                		
	                		jugadas.put((String)(i+","+j+","+move),afectados);
            			}
                		
            		}
            		
            		
            		
            		flag=false; 
            		
            	}
            	
            	
            	
            	/////////////////////////////////////////////////////////////////////////////////////////////////////
            	/////////////////////////////////////////////////////////////////////////////////////////////////////
            	/////////////////////////////////////////////////////////////////////////////////////////////////////
            	/////////////////////////////////////////////////////////////////////////////////////////////////////
            	
            	
            	while(v.size()==0 ) {
            		v = new Array<String>();
            		if(cuadro.size()!=0) {
            			
            			if(jugadas.size()>0) {
            				///////
            				///////
            				String [] coordenadas = jugadas.keySet().toArray(new String[jugadas.size()]);
            				int [] valorAfectacion = new int[coordenadas.length];
            				String acceso ="";
            				int indiceMin = 0;
            				int valorMin =0;
            				for (int k =0; k<coordenadas.length; k++) {
            					acceso = coordenadas[k];
            					valorAfectacion[k]= jugadas.get(acceso);
            				}
            				valorMin = valorAfectacion[0];
            				for (int k =1; k<valorAfectacion.length; k++) {
            					if(valorAfectacion[k]<valorMin) {
            						valorMin = valorAfectacion[k];
            						indiceMin =k;
            					}
            				}
            				//System.out.println("**********************  valor minimo   ************* "+valorMin);
    		        		String llave2 = coordenadas[indiceMin];
    		            	String movimiento="";
    		        		String [] componentes = llave2.split(",");
    		        		i = Integer.parseInt(componentes[0]);
    		        		j = Integer.parseInt(componentes[1]);
    		        		movimiento = componentes[2];
    		        		
    		        		if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE) && (Squares.LEFT).equals(movimiento)) {
    		                    v.add(Squares.LEFT);
    		        		}
    						if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE) && (Squares.TOP).equals(movimiento)) {
    						  v.add(Squares.TOP);
    						}
    						if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE) && (Squares.BOTTOM).equals(movimiento)) {
    						  v.add(Squares.BOTTOM);
    						}
    						if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE) && (Squares.RIGHT).equals(movimiento)) {
    						  v.add(Squares.RIGHT);
    						}
    						
    						
    						if(v.size()==0) {
    							v = new Array<String>();
    						}
    						jugadas.remove(llave2);
            				///////
            				///////
            			}else {
            				String [] coordenadas = cuadro.keySet().toArray(new String[cuadro.size()]);
    		        		int sizeKeys = coordenadas.length;
    		        		int key = (int)(sizeKeys*Math.random());
    		        		String llave2 = coordenadas[key];
    		        		int l = 0;
    		            	int laux = 0;
    		            	if(cuadro.containsKey(llave2)) {
    		            		l = cuadro.get(llave2);
    		            	}
    		        		String [] componentes = llave2.split(",");
    		        		i = Integer.parseInt(componentes[0]);
    		        		j = Integer.parseInt(componentes[1]);
    		        		if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE)) {
    		                    v.add(Squares.LEFT);
    		        		}else {
    		        			laux++;
    		        		}
    						if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE)) {
    						  v.add(Squares.TOP);
    						}else {
    		        			laux++;
    		        		}
    						if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE)) {
    						  v.add(Squares.BOTTOM);
    						}else {
    		        			laux++;
    		        		}
    						if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE)) {
    						  v.add(Squares.RIGHT);
    						}else {
    		        			laux++;
    		        		}
    						if(l<laux ) {
    							if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.TRUE)) {
    								if(j!=0) {
    									int l2 = 0;
    									int l3 = 0;
    		            				int iaux=i;
    		            				int jaux=j-1;
    		            				if(((String)p.get(iaux+":"+jaux+":"+Squares.LEFT)).equals(Squares.TRUE))
    		    		                    l3++;
    		    						if(((String)p.get(iaux+":"+jaux+":"+Squares.TOP)).equals(Squares.TRUE))
    		    							l3++;
    		    						if(((String)p.get(iaux+":"+jaux+":"+Squares.BOTTOM)).equals(Squares.TRUE))
    		    							l3++;
    		    						if(((String)p.get(iaux+":"+jaux+":"+Squares.RIGHT)).equals(Squares.TRUE))
    		    							l3++;
    		    						String llave =iaux+","+jaux;	            				
    		            				if(cuadro.containsKey(llave)) {
    		                        		l2 = cuadro.get(llave);
    		                        		if(l2<l3) {
    	            		        			cuadro.remove(llave);
    	            		        			if(l3<4) {
    	            		        				cuadro.put(llave,l3);
    	            		        			}
    		                        		}
    		                        	}else {
    		                        		if(0<l3 && l3<4) {
    		                        			cuadro.put(llave,l3);
    		                        		}
    		                        	}
    		            			}
    							}
    							if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.TRUE)){
    								if(i!=0) {
    									int l2 = 0;
    									int l3 = 0;
    		            				int iaux=i-1;
    		            				int jaux=j;
    		            				if(((String)p.get(iaux+":"+jaux+":"+Squares.LEFT)).equals(Squares.TRUE))
    		    		                    l3++;
    		    						if(((String)p.get(iaux+":"+jaux+":"+Squares.TOP)).equals(Squares.TRUE))
    		    							l3++;
    		    						if(((String)p.get(iaux+":"+jaux+":"+Squares.BOTTOM)).equals(Squares.TRUE))
    		    							l3++;
    		    						if(((String)p.get(iaux+":"+jaux+":"+Squares.RIGHT)).equals(Squares.TRUE))
    		    							l3++;
    		    						String llave =iaux+","+jaux;	            				
    		            				if(cuadro.containsKey(llave)) {
    		                        		l2 = cuadro.get(llave);
    		                        		if(l2<l3) {
    	            		        			cuadro.remove(llave);
    	            		        			if(l3<4) {
    	            		        				cuadro.put(llave,l3);
    	            		        			}
    		                        		}
    		                        	}else {
    		                        		if(0<l3 && l3<4) {
    		                        			cuadro.put(llave,l3);
    		                        		}
    		                        	}
    		            			}
    							}
    							if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.TRUE)){
    								if(i!=(size-1)) {
    									int l2 = 0;
    									int l3 = 0;
    		            				int iaux=i+1;
    		            				int jaux=j;
    		            				if(((String)p.get(iaux+":"+jaux+":"+Squares.LEFT)).equals(Squares.TRUE))
    		    		                    l3++;
    		    						if(((String)p.get(iaux+":"+jaux+":"+Squares.TOP)).equals(Squares.TRUE))
    		    							l3++;
    		    						if(((String)p.get(iaux+":"+jaux+":"+Squares.BOTTOM)).equals(Squares.TRUE))
    		    							l3++;
    		    						if(((String)p.get(iaux+":"+jaux+":"+Squares.RIGHT)).equals(Squares.TRUE))
    		    							l3++;
    		    						String llave =iaux+","+jaux;	            				
    		            				if(cuadro.containsKey(llave)) {
    		                        		l2 = cuadro.get(llave);
    		                        		if(l2<l3) {
    	            		        			cuadro.remove(llave);
    	            		        			if(l3<4) {
    	            		        				cuadro.put(llave,l3);
    	            		        			}
    		                        		}
    		                        	}else {
    		                        		if(0<l3 && l3<4) {
    		                        			cuadro.put(llave,l3);
    		                        		}
    		                        	}
    		            			}
    							}
    							if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.TRUE)){
    								if(j!=(size-1)) {
    									int l2 = 0;
    									int l3 = 0;
    		            				int iaux=i;
    		            				int jaux=j+1;
    		            				if(((String)p.get(iaux+":"+jaux+":"+Squares.LEFT)).equals(Squares.TRUE))
    		    		                    l3++;
    		    						if(((String)p.get(iaux+":"+jaux+":"+Squares.TOP)).equals(Squares.TRUE))
    		    							l3++;
    		    						if(((String)p.get(iaux+":"+jaux+":"+Squares.BOTTOM)).equals(Squares.TRUE))
    		    							l3++;
    		    						if(((String)p.get(iaux+":"+jaux+":"+Squares.RIGHT)).equals(Squares.TRUE))
    		    							l3++;
    		    						String llave =iaux+","+jaux;	            				
    		            				if(cuadro.containsKey(llave)) {
    		                        		l2 = cuadro.get(llave);
    		                        		if(l2<l3) {
    	            		        			cuadro.remove(llave);
    	            		        			if(l3<4) {
    	            		        				cuadro.put(llave,l3);
    	            		        			}
    		                        		}
    		                        	}else {
    		                        		if(0<l3 && l3<4) {
    		                        			cuadro.put(llave,l3);
    		                        		}
    		                        	}
    		            			}
    							}
    							
    							String llave =i+","+j; 
    							if(cuadro.containsKey(llave)) {                    		
    			        			cuadro.remove(llave);
    			        			if(laux<3) {
        		        				cuadro.put(llave,laux);
        		        			}
    			        			                    		
    	                    	}else if(laux<3) {                    		
    	                			cuadro.put(llave,laux);                    		
    	                    	}
    			            	
    						}
    						if(v.size()==0) {
    							v = new Array<String>();
    							cuadro.remove(llave2);
    						}
            			}
            			
		        		
            		}
            	}
        	}
            try{
            	String move = v.get((int)(Math.random()*v.size()));
            	rayas = rayas+2;
            	int l = 0;
            	String llave =i+","+j; 
            	if(cuadro.containsKey(llave)) {
        			l = cuadro.get(llave);
        			cuadro.remove(llave);
        			l = l+1;
        			if(l<3 || flag) {
        				cuadro.put(llave,l);
        			}
        		}else if(flag) {
        			cuadro.put(llave,1);
        		}
            	if((Squares.LEFT).equals(move) && j!=0) {
            		l = 0;
                	llave =i+","+(j-1); 
                	if(cuadro.containsKey(llave)) {
            			l = cuadro.get(llave);
            			cuadro.remove(llave);
            			l = l+1;
            			if(l<3 || flag) {
            				cuadro.put(llave,l);
            			}
            		}else if(flag) {
            			cuadro.put(llave,1);
            		}
            	}
				if((Squares.TOP).equals(move)&& i!=0) {
            		l = 0;
                	llave =(i-1)+","+j; 
                	if(cuadro.containsKey(llave)) {
            			l = cuadro.get(llave);
            			cuadro.remove(llave);
            			l = l+1;
            			if(l<3 || flag) {
            				cuadro.put(llave,l);
            			}
            		}else if(flag) {
            			cuadro.put(llave,1);
            		}
				}
				if((Squares.BOTTOM).equals(move)&& i!=(size-1)) {
            		l = 0;
                	llave =(i+1)+","+j; 
                	if(cuadro.containsKey(llave)) {
            			l = cuadro.get(llave);
            			cuadro.remove(llave);
            			l = l+1;
            			if(l<3 || flag) {
            				cuadro.put(llave,l);
            			}
            		}else if(flag) {
            			cuadro.put(llave,1);
            		}
				}
				if((Squares.RIGHT).equals(move)&& j!=(size-1)) {
            		l = 0;
                	llave =i+","+(j+1); 
                	if(cuadro.containsKey(llave)) {
            			l = cuadro.get(llave);
            			cuadro.remove(llave);
            			l = l+1;
            			if(l<3 || flag) {
            				cuadro.put(llave,l);
            			}
            		}else if(flag) {
            			cuadro.put(llave,1);
            		}
				}
                return new Action( i+":"+j+":"+move);
            }catch(Exception e){}
        }
        return new Action(Squares.PASS);
    }

    @Override
    public void init() {
    }
    
    public void evaluar(Percept p, int i, int j, String move) {
    	String llave =i+","+j;	 
    	int l =0;
    	if(cuadroAux.containsKey(llave)) {
    		l= cuadroAux.get(llave);
    		l= l+1;
    		cuadroAux.remove(llave);
    		cuadroAux.put(llave,l);
    		if(l>2) {
    			cuadroAux.remove(llave);
    			afectados = afectados+1;
    			if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE) && !(Squares.LEFT).equals(move)) {
    				evaluar(p, i, (j-1), Squares.RIGHT);
    			}
    			if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE) && !(Squares.TOP).equals(move)) {
    				evaluar(p, (i-1), j, Squares.BOTTOM);
				}
				if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE) && !(Squares.BOTTOM).equals(move)) {
					evaluar(p, (i+1), j, Squares.TOP);
				}
				if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE) && !(Squares.RIGHT).equals(move)) {
					evaluar(p, i, (j+1), Squares.LEFT);
				}
    		}
    	}
    	
    }
}
