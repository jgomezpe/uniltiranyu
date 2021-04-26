/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.games.fourinrow;

import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.fourinrow.FourInRow;

/**
 *
 * @author Jonatan
 */
public class MyFourInRowAgentProgram implements AgentProgram {
    protected String color;
    public MyFourInRowAgentProgram( String color ){
        this.color = color;        
    }
    
    //método para evaluar las acciones propias cercanas
    private int evaluateHeuristic(int row, int col, Percept p) {
        
        int usablePositions = 0;
        int enemyPositions = 0;
        
        boolean spaceBeteween = false;
        boolean used = false;
        boolean interrumped = false;
        int tempHeuristic = 0;
        
        int n = Integer.parseInt((String)p.get(FourInRow.SIZE));
        
        //-------------------------------------------------------------horizontales----------------------------------------------------------------
        
        //evalua si la distancia entre col y el borde en n es mayor que 4 en iterator1 y en iterator2 de col a 0
        int iterator1 = n - col;
        int iterator2 = col;
        if(iterator1>4) {iterator1=4;}
        if(iterator2>4) {iterator2=4;}
        
        //de la posición actual a la derecha
        for (int k = 1; k < iterator1; k++) {
            if (!interrumped && p.get(row+":"+(col+k)).equals((String)FourInRow.SPACE)) {
                tempHeuristic++;
                usablePositions++;
                spaceBeteween = true;
            }
            else if (!spaceBeteween && !interrumped && p.get(row+":"+(col+k)).equals((String)this.color)) {
                tempHeuristic+=2;
                usablePositions++;
                used = true;
            }
            else {
                interrumped = true;
                if(!spaceBeteween && !used){
                    enemyPositions++;
                }
            }
        }
        
        //se restauran los condicionales
        spaceBeteween = false;
        used = false;
        interrumped = false;
        
        //de la posición actual a la izquierda
        for (int k = 1; k <= iterator2; k++) {
            if (!interrumped && p.get(row+":"+(col-k)).equals((String)FourInRow.SPACE)) {
                tempHeuristic++;
                usablePositions++;
                spaceBeteween = true;
            }
            else if (!spaceBeteween && !interrumped && p.get(row+":"+(col-k)).equals((String)this.color)) {
                tempHeuristic+=2;
                usablePositions++;
                used = true;
            }
            else {
                interrumped = true;
                if(!spaceBeteween && !used){
                    enemyPositions++;
                }
            }
        }
        
        //si no hay 4 espacios minimos usables no suma las fichas alrededor
        if(usablePositions<4){
            tempHeuristic = 0;
        }
        
        //si gana con una jugada en esta posición asigna la heuristica como 50, considerando que el maximo de otras maneras es 24
        if(tempHeuristic-usablePositions >= 3){
            tempHeuristic=50;
        }
        
        //si el enemigo gana usando esta posición asigna la heuristica como 30, considerando que solo es superado si se puede ganar en otra posición
        if (enemyPositions>=3){
            tempHeuristic=30;
        }
        
        //-------------------------------------------------------------verticales----------------------------------------------------------------
        
        //se restauran los condicionales y se crea una nueva heuristica temporal
        spaceBeteween = false;
        used = false;
        interrumped = false;
        usablePositions = 0;
        enemyPositions = 0;
        int tempHeuristicV = 0;
        
        //evalua si la distancia entre row y el borde en n es mayor que 4 en iterator1 y en iterator2 de row a 0
        iterator1 = n - row;
        iterator2 = row;
        if(iterator1>4) {iterator1=4;}
        if(iterator2>4) {iterator2=4;}
        
        //de la posición actual hacia abajo
        for (int k = 1; k < iterator1; k++) {
            if (!interrumped && p.get((row+k)+":"+col).equals((String)FourInRow.SPACE)) {
                tempHeuristicV++;
                usablePositions++;
                spaceBeteween = true;
            }
            else if (!spaceBeteween && !interrumped && p.get((row+k)+":"+col).equals((String)this.color)) {
                tempHeuristicV+=2;
                usablePositions++;
                used = true;
            }
            else {
                interrumped = true;
                if(!spaceBeteween && !used){
                    enemyPositions++;
                }
            }
        }
        
        //se restauran los condicionales
        spaceBeteween = false;
        used = false;
        interrumped = false;
        
        //de la posición actual hacia arriba
        for (int k = 1; k <= iterator2; k++) {
            if (!interrumped && p.get((row-k)+":"+col).equals((String)FourInRow.SPACE)) {
                tempHeuristicV++;
                usablePositions++;
                spaceBeteween = true;
            }
            else if (!spaceBeteween && !interrumped && p.get((row-k)+":"+col).equals((String)this.color)) {
                tempHeuristicV+=2;
                usablePositions++;
                used = true;
            }
            else {
                interrumped = true;
                if(!spaceBeteween && !used){
                    enemyPositions++;
                }
            }
        }
        
        //si no hay 4 espacios minimos usables no suma las fichas alrededor
        if(usablePositions<4){
            tempHeuristicV = 0;
        }
        
        //si gana con una jugada en esta posición asigna la heuristica como 50, considerando que el maximo de otras maneras es 24
        if(tempHeuristicV-usablePositions >= 3){
            tempHeuristicV=50;
        }
        
        //si el contrincante gana usando esta posición asigna la heuristica como 30, considerando que solo es superado si se puede ganar en otra posición
        if (enemyPositions>=3){
            tempHeuristicV=30;
        }
        
        //-------------------------------------------------------------Diagonales----------------------------------------------------------------
        int tempHeuristicD = diagonals(row, col, p);
        
        
        //La heuristica del nodo es la suma de las temporales
        return tempHeuristic+tempHeuristicV + tempHeuristicD;
    }
    
    private int diagonals (int row, int col, Percept p) {
        
        
        int usablePositions = 0;
        int enemyPositions = 0;
        
        boolean spaceBeteween = false;
        boolean used = false;
        boolean interrumped = false;
        int tempHeuristic = 0;
        
        int n = Integer.parseInt((String)p.get(FourInRow.SIZE));
        
        //evalua las distancias de las diagonales y toma la menor de estas
        int temp1=row;
        int temp2=col;
        int upLeft = ((temp1 > temp2) ? temp2 : temp1);
        temp2=n-col;
        int upRight = ((temp1 > temp2) ? temp2 : temp1);
        temp1=n-row;
        int downRight = ((temp1 > temp2) ? temp2 : temp1);
        temp2=col;
        int downLeft = ((temp1 > temp2) ? temp2 : temp1);

        //-------------------------------------------------------------Diagonal arriba izquierda----------------------------------------------------------------
        
        //de la posición actual hacia arriba a la izquierda
        for (int k = 1; k < upLeft && k < 4; k++) {
            if (!interrumped && p.get((row-k)+":"+(col-k)).equals((String)FourInRow.SPACE)) {
                tempHeuristic++;
                usablePositions++;
                spaceBeteween = true;
            }
            else if (!spaceBeteween && !interrumped && p.get((row-k)+":"+(col-k)).equals((String)this.color)) {
                tempHeuristic+=2;
                usablePositions++;
                used = true;
            }
            else {
                interrumped = true;
                if(!spaceBeteween && !used){
                    enemyPositions++;
                }
            }
        }
        
        //se restauran los condicionales
        spaceBeteween = false;
        used = false;
        interrumped = false;
        
        //de la posición actual hacia abajo a la derecha
        for (int k = 1; k < downRight && k < 4; k++) {
            if (!interrumped && p.get((row+k)+":"+(col+k)).equals((String)FourInRow.SPACE)) {
                tempHeuristic++;
                usablePositions++;
                spaceBeteween = true;
            }
            else if (!spaceBeteween && !interrumped && p.get((row+k)+":"+(col+k)).equals((String)this.color)) {
                tempHeuristic+=2;
                usablePositions++;
                used = true;
            }
            else {
                interrumped = true;
                if(!spaceBeteween && !used){
                    enemyPositions++;
                }
            }
        }
        
        //si no hay 4 espacios minimos usables no suma las fichas alrededor
        if(usablePositions<4){
            tempHeuristic = 0;
        }
        
        //si gana con una jugada en esta posición asigna la heuristica como 50, considerando que el maximo de otras maneras es 24
        if(tempHeuristic-usablePositions >= 3){
            tempHeuristic=50;
        }
        
        //si el enemigo gana usando esta posición asigna la heuristica como 30, considerando que solo es superado si se puede ganar en otra posición
        if (enemyPositions>=3){
            tempHeuristic=30;
        }
        
        //-------------------------------------------------------------Diagonal arriba derecha----------------------------------------------------------------
        
        //se restauran los condicionales y se crea una nueva heuristica temporal
        spaceBeteween = false;
        used = false;
        interrumped = false;
        usablePositions = 0;
        enemyPositions = 0;
        int tempHeuristic2 = 0;
        
        //de la posición actual hacia arriba a la izquierda
        for (int k = 1; k < upRight && k < 4; k++) {
            if (!interrumped && p.get((row-k)+":"+(col+k)).equals((String)FourInRow.SPACE)) {
                tempHeuristic2++;
                usablePositions++;
                spaceBeteween = true;
            }
            else if (!spaceBeteween && !interrumped && p.get((row-k)+":"+(col+k)).equals((String)this.color)) {
                tempHeuristic2+=2;
                usablePositions++;
                used = true;
            }
            else {
                interrumped = true;
                if(!spaceBeteween && !used){
                    enemyPositions++;
                }
            }
        }
        
        //se restauran los condicionales
        spaceBeteween = false;
        used = false;
        interrumped = false;
        
        //de la posición actual hacia abajo a la izquierda
        for (int k = 1; k < downLeft && k < 4; k++) {
            if (!interrumped && p.get((row+k)+":"+(col-k)).equals((String)FourInRow.SPACE)) {
                tempHeuristic2++;
                usablePositions++;
                spaceBeteween = true;
            }
            else if (!spaceBeteween && !interrumped && p.get((row+k)+":"+(col-k)).equals((String)this.color)) {
                tempHeuristic2+=2;
                usablePositions++;
                used = true;
            }
            else {
                interrumped = true;
                if(!spaceBeteween && !used){
                    enemyPositions++;
                }
            }
        }
        
        //si no hay 4 espacios minimos usables no suma las fichas alrededor
        if(usablePositions<4){
            tempHeuristic2 = 0;
        }
        
        //si gana con una jugada en esta posición asigna la heuristica como 50, considerando que el maximo de otras maneras es 24
        if(tempHeuristic-usablePositions >= 3){
            tempHeuristic2=50;
        }
        
        //si el enemigo gana usando esta posición asigna la heuristica como 30, considerando que solo es superado si se puede ganar en otra posición
        if (enemyPositions>=3){
            tempHeuristic2=30;
        }

        return tempHeuristic + tempHeuristic2;
        
        
    }
    
    
    
    
    @Override
    public Action compute(Percept p) {        
        long time = (long)(200 * Math.random());
        try{
           Thread.sleep(time);
        }catch(Exception e){}
        
        if( p.get(FourInRow.TURN).equals(color) ){
            int n = Integer.parseInt((String)p.get(FourInRow.SIZE));
            
            //posición del nodo sobre el que se jugará
            int row = -1;
            int col = -1;
            int maxHeuristic = -1;
            
            //recorre las columnas buscando el primer espacio en blanco en cada una
            for (int j = 0; j < n; j++) {
                for (int i = n-1; i >= 0; i--) {
                    if (p.get(i+":"+j).equals((String)FourInRow.SPACE)) {
                        int temp = evaluateHeuristic(i, j, p);
                        if (temp > maxHeuristic) {
                            maxHeuristic = temp;
                            row = i;
                            col = j;
                        }
                        break;
                    }
                }
            }
            
            if(row!=-1){
            return new Action( row+":"+col+":"+color );
            }
            return new Action(FourInRow.PASS);
            
        }
        return new Action(FourInRow.PASS);
    }

    @Override
    public void init() {
    }
    
}