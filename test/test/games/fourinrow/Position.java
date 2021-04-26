package test.games.fourinrow;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.BitSet;

public class Position {
	public int WIDTH = 7;
	public int HEIGHT = 7;
	public BitSet[] jugador = new BitSet[WIDTH];
	public BitSet[] noVacio = new BitSet[WIDTH];
	// private BitSet[] bottom = new BitSet[WIDTH];
	public int[] techo = new int[WIDTH];
	public int MIN_SCORE;
	public int MAX_SCORE;

	int moves;
	BitSet mBitSet = new BitSet(WIDTH);

	public static BitSet sum(BitSet a, BitSet b) {
		BitSet temp1 = (BitSet) a.clone();
		temp1.xor(b);
		BitSet temp2 = (BitSet) a.clone();
		temp2.and(b);
		BitSet carrys = new BitSet(a.size() + 1);
		for (int i = 0; i < a.size(); i++) {
			carrys.set(i + 1, (temp1.get(i) && carrys.get(i)) || temp2.get(i));
		}
		BitSet sumSet = (BitSet) temp1.clone();
		sumSet.xor(carrys);
		return sumSet;
	}

	Position(int tamano) {
		jugador = new BitSet[tamano];
		noVacio = new BitSet[tamano];
		techo = new int[tamano];
		// bottom = new BitSet[tamano];
		WIDTH = tamano;
		HEIGHT = tamano;
		for (int i = 0; i < tamano; i++) {
			jugador[i] = new BitSet(tamano);
			noVacio[i] = new BitSet(tamano);
			// bottom[i] = new BitSet(tamano);
		}
		// for (int i= 0; i < tamano;i++) {
		// bottom[i].set(0);
		// }

		MIN_SCORE = -(tamano * tamano) / 2 + 3;
		MAX_SCORE = (tamano * tamano + 1) / 2 - 3;
	}

	boolean canPlay(int columna) {
		return !noVacio[columna].get(HEIGHT - 1);
	}

	void play(int columna) {
		for (int i = 0; i < WIDTH; i++) {
			jugador[i].xor(noVacio[i]);
		}
		noVacio[columna].set(techo[columna]);
		techo[columna]++;
		moves++;
	}

	void dPlay(int columna) {
		//System.out.println("entre");
		// for (BitSet map:jugador
		// ) {
		// 	System.out.println(map.toString());
		// }
		// System.out.println("///////////////////////");
		// for (BitSet map:noVacio
		// ) {
		// 	System.out.println(map.toString());
		// }
		--moves;
		noVacio[columna].clear(--techo[columna]);
		for (int i = 0; i < jugador.length; i++) {
			jugador[i].xor(noVacio[i]);
		}


		// System.out.println("sali");
		// for (BitSet map:jugador
		// ) {
		// 	System.out.println(map.toString());
		// }
	}

	ArrayDeque<Integer> movimientoForzado() {
		BitSet[] temp = Arrays.copyOf(jugador, jugador.length);
		for (int i = 0; i < WIDTH; i++) {
			temp[i].xor(noVacio[i]);
		}
		int count = 0;
		int pos = 0;
		for (int i = 0; i < WIDTH; i++) {
			if (!canPlay(i)){
				continue;
			}
			if (isWinningMove(i, temp)) {
				count++;
				pos = i;
			}
		}
		ArrayDeque<Integer> posibles = new ArrayDeque<>();
		if (count >= 1) {
			if (count == 1) {
				posibles.push(pos);
			}
			for (int i = 0; i < WIDTH; i++) {
				temp[i].xor(noVacio[i]);
			}
			return posibles;
		}
		for (int i = 0; i < WIDTH; i++) {
			if (techo[i]+2>=HEIGHT){
				continue;
			}
			techo[i]++;
			
			if (!isWinningMove(i, temp)) {
				posibles.push(i);
			}
			techo[i]--;
		}
		for (int i = 0; i < WIDTH; i++) {
			temp[i].xor(noVacio[i]);
		}
		return posibles;
	}

	public boolean isWinningMove(int columna) {
		if (techo[columna] >= 3 && jugador[columna].get(techo[columna] - 1) && jugador[columna].get(techo[columna] - 2)
				&& jugador[columna].get(techo[columna] - 3)) {
			return true;
		}
		for (int dy = -1; dy <= 1; dy++) {
			int nb = 0;
			for (int dx = -1; dx <= 1; dx += 2) {
				for (int x = columna + dx, y = techo[columna] + dx * dy; x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT
						&& jugador[x].get(y); x += dx, y += dx * dy, nb++)
					;
			}
			if (nb >= 3) {
				return true;
			}
		}
		return false;
	}

	public boolean isWinningMove(int columna, BitSet[] otro) {
		if (techo[columna] >= 3 && otro[columna].get(techo[columna] - 1) && otro[columna].get(techo[columna] - 2)
				&& otro[columna].get(techo[columna] - 3)) {
			return true;
		}
		for (int dy = -1; dy <= 1; dy++) {
			int nb = 0;
			for (int dx = -1; dx <= 1; dx += 2) {
				for (int x = columna + dx, y = techo[columna] + dx * dy; x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT
						&& otro[x].get(y); x += dx, y += dx * dy, nb++)
					;
			}
			if (nb >= 3) {
				return true;
			}
		}
		return false;
	}

	int nbMoves() {
		return moves;
	}

	public BitSet[] key() {
		BitSet[] nKey = new BitSet[WIDTH];
		BitSet[] bottom = new BitSet[WIDTH];

		for (int i = 0; i < bottom.length; i++) {
			bottom[i]= new BitSet(WIDTH);
			bottom[i].set(0);
		}
		for (int i = 0; i < WIDTH; i++) {
			nKey[i] = sum(sum(this.jugador[i], this.noVacio[i]), bottom[i]);
		}
		return nKey;
	}

	public boolean canWinNext() {
		for (int i = 0; i < WIDTH; i++) {
			if (isWinningMove(i)) {
				return true;
			}
		}
		return false;
	}

}
