package test.games.squares;

import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.squares.Squares;
/**
  *
  *
  *@author Equipo 6 - cin>>UNombre;
  *Jherson Adrian Medina Correa
  *Manuel David Medrano Monroy
  *Nicolas Eduardo Pardo Arias
  *
  */
public class squaresAgentHomework implements AgentProgram{
	public class triple<X,Y,Z,W>{
		private X a;
		private Y b;
		private Z c;
		private W v;
		private triple(X a, Y b, Z c, W v){
			this.a = a;
			this.b = b;
			this.c = c;
			this.v = v;
		}
		public X first(){return a;}
		public Y second(){return b;}
		public Z third(){return c;}
		public W fourth(){return v;}
		public void setFirst(X a){this.a = a;}
		public void setSecond(Y b){this.b = b;}
		public void setThird(Z c){this.c = c;}
		public void setFourth(W v){this.v = v;}
	}
	protected String color;
	protected int[][] dp = new int[1010][1010];
	public squaresAgentHomework(String color){
		this.color = color;
	}
	
	public triple go(boolean player, int d, int alpha, int beta,int n){
		triple<Integer,Integer,Integer,Integer> ans = new triple(0,0,0,0);	
		int mxI = 0;
		int res = 1000000010;
		int[][] add = new int[n+10][n+10];
		boolean ff = player;
		int cnt = 0;
		while(true && d<2){
			boolean f = false;
			for(int i=0; i<n; i++){
				for(int j=0; j<n; j++){
					if(dp[i][j] == 15) continue;
					int aux = dp[i][j];
					if(aux == 7){
						dp[i][j] += 8;
						dp[i][j-1]++;
						add[i][j] += 8;
						add[i][j-1]++;
						cnt++;
						if(dp[i][j-1]==15)cnt++;
					}
					if(aux == 11){
						dp[i][j] += 4;
						dp[i-1][j] += 2;
						add[i][j]+=4;
						add[i-1][j]+=2;
						cnt++;
						if(dp[i-1][j]==15)cnt++;
					}
					if(aux == 13){
						dp[i][j] += 2;
						dp[i+1][j] += 4;
						add[i][j] += 2;
						add[i+1][j] += 4;
						cnt++;
						if(dp[i+1][j]==15)cnt++;
					}
					if(aux == 14){
						dp[i][j]++;
						dp[i][j+1]+=8;
						add[i][j]++;
						add[i][j+1]+=8;
						cnt++;
						if(dp[i][j+1]==15)cnt++;
					}
				}
			}
			for(int i=0; i<n; i++){
				for(int j=0; j<n; j++){
					int r = dp[i][j];
					f |= ((r == 7) |( r == 11) | (r == 13) | (r == 14));
				}
			}
			if(!f)break;
		}
		if( d == 0 ){
			if(ff)ans.setFourth(Integer.valueOf(cnt));
			return ans;
		}
		if( ff ){
			res *= -1;
			for(int i=0; i<n; i++){
				for(int j=0; j<n; j++){
					for(int bit=0; bit<4; bit++){
						if(((dp[i][j]>>bit)&1) == 1)continue;
						if(mxI == 21)break;
						mxI++;
						System.out.println(i+" "+j+" "+dp[i][j]+" "+bit);
						dp[i][j] += (1<<bit);
						if(bit == 0)dp[i][j+1]+=(1<<(Math.abs(bit-3)));
						if(bit == 1)dp[i+1][j]+=(1<<(Math.abs(bit-3)));
						if(bit == 2)dp[i-1][j]+=(1<<(Math.abs(bit-3)));
						if(bit == 3)dp[i][j-1]+=(1<<(Math.abs(bit-3)));
						triple<Integer,Integer,Integer,Integer> retu = go(false,d-1,alpha,beta,n);
						dp[i][j] -= (1<<bit);
						if(bit == 0)dp[i][j+1]-=(1<<(Math.abs(bit-3)));
						if(bit == 1)dp[i+1][j]-=(1<<(Math.abs(bit-3)));
						if(bit == 2)dp[i-1][j]-=(1<<(Math.abs(bit-3)));
						if(bit == 3)dp[i][j-1]-=(1<<(Math.abs(bit-3)));
						int val = retu.fourth();
						if(res < val){
							res = val;
							ans.setFirst(Integer.valueOf(i));
							ans.setSecond(Integer.valueOf(j));
							ans.setThird(Integer.valueOf(bit));
							ans.setFourth(Integer.valueOf(res));

						}
						alpha = Math.max(alpha,val);
						if(beta<=alpha)break;
					}
					if(mxI == 21) break;
					if(beta<=alpha)break;
				}
				if(mxI == 21)break;
				if(beta<=alpha)break;
			}
		}else{
			for(int i=0; i<n; i++){
				for(int j=0; j<n; j++){
					for(int bit=0; bit<4; bit++){
						if(((dp[i][j]>>bit)&1) == 1)continue;
						if(mxI==21) break;
						mxI++;
						dp[i][j] += (1<<bit);
						System.out.println(i+" "+j+" "+dp[i][j]+" "+bit);
						if(bit == 0)dp[i][j+1]+=(1<<(Math.abs(bit-3)));
						if(bit == 1)dp[i+1][j]+=(1<<(Math.abs(bit-3)));
						if(bit == 2)dp[i-1][j]+=(1<<(Math.abs(bit-3)));
						if(bit == 3)dp[i][j-1]+=(1<<(Math.abs(bit-3)));
						triple<Integer,Integer,Integer,Integer> retu = go(true,d-1,alpha,beta,n);
						dp[i][j] -= (1<<bit);
						if(bit == 0)dp[i][j+1]-=(1<<(Math.abs(bit-3)));
						if(bit == 1)dp[i+1][j]-=(1<<(Math.abs(bit-3)));
						if(bit == 2)dp[i-1][j]-=(1<<(Math.abs(bit-3)));
						if(bit == 3)dp[i][j-1]-=(1<<(Math.abs(bit-3)));
						int val = retu.fourth();
						if(res > val){
							res = val;
							ans.setFirst(Integer.valueOf(i));
							ans.setSecond(Integer.valueOf(j));
							ans.setThird(Integer.valueOf(bit));
							ans.setFourth(Integer.valueOf(res));

						}
						alpha = Math.min(alpha,val);
						if(beta<=alpha)break;
					}
					if(mxI==21) break;
					if(beta<=alpha)break;
				}
				if(mxI==21)break;
				if(beta<=alpha)break;
			}

		}
		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
				dp[i][j] -= add[i][j];
		return ans;
	}
	@Override
	public Action compute( Percept p){
		long time = (long)(200*Math.random());
		try{
			Thread.sleep(time);
		}catch(Exception e){}
		int n = Integer.parseInt((String)p.get(Squares.SIZE));
		if(p.get(Squares.TURN).equals(color)){
			System.out.println(color);
			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++) dp[i][j] = 0;
			for(int i=0; i<n; i++){
				for(int j=0; j<n; j++){
					if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.TRUE))
						dp[i][j] += (1<<3);
					if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.TRUE))
						dp[i][j] += (1<<2);
					if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.TRUE))
						dp[i][j] += (1<<1);
					if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.TRUE))
						dp[i][j] += (1<<0);
					System.out.print(dp[i][j]+" ");
				}
				System.out.print('\n');
			}
			boolean c = true;
			System.out.println(c);
			int oo = 1000000010;
			triple<Integer,Integer,Integer,Integer> ans = go(c,2,-oo,oo,n);	
			String move="";
			try{
				if(ans.third() == 0) move = Squares.RIGHT;
				if(ans.third() == 1) move = Squares.BOTTOM;
				if(ans.third() == 2) move = Squares.TOP;
				if(ans.third() == 3) move = Squares.LEFT;
				return new Action(ans.first()+":"+ans.second()+":"+move);
			}catch(Exception e){}
		}	
		return new Action(Squares.PASS);
	}
	
	@Override
	public void init(){
	}
}
