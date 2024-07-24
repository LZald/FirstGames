public class MineGrid {

	private int[][] mineBoard;
	private int mines;
	
	public MineGrid(int h, int w, int mines) {
		mineBoard = new int[h][w];
		this.mines = mines;
		introduceMines();
		shuffleMines();
	}
	
	public int getNumber(int f, int c) {
		return mineBoard[f][c];
	}
	
	public void introduceMines() {
		int num = mines;
		for (int i = 0; i < mineBoard.length; i++) {
			for (int j = 0; j < mineBoard[0].length; j++) {
				if (num>0) {
					mineBoard[i][j]=9;
					num--;
				}else {
					mineBoard[i][j]=0;
				}
			}
		}
	}
	
	public void shuffleMines() {
		int a, b, tmp;
		for (int i = 0; i < mineBoard.length; i++) {
			for (int j = 0; j < mineBoard[0].length; j++) {
				a = (int)(Math.random()*mineBoard.length);
				b = (int)(Math.random()*mineBoard[0].length);
				tmp = mineBoard[i][j];
				mineBoard[i][j]=mineBoard[a][b];
				mineBoard[a][b]=tmp;
			}
		}
	}
	
	public void setNumbers() {
		for (int i = 0; i < mineBoard.length; i++) {
			for (int j = 0; j < mineBoard[0].length; j++) {
				setNumber(i,j);
			}
		}
	}
	
	public void setNumber(int i, int j) {
		if (mineBoard[i][j]==9) {
			return;
		}
		for (int a = -1; a < 2; a++) {
			for (int b = -1; b < 2; b++) {
				if((i+a>=0)&&(j+b>=0)&&(i+a<mineBoard.length)&&(j+b<mineBoard[0].length)) {
					if (mineBoard[i+a][j+b]==9) {
						mineBoard[i][j]++;
					}
				}
			}
		}
	}
	
	public void reset() {
		introduceMines();
		shuffleMines();
	}

	public void setToBlanc(int f, int c) {
		int ri, rj, tmp;
		if (mines>mineBoard.length*mineBoard[0].length-9) {
			if(mineBoard[f][c]==9) {
				do {
					ri = (int)(Math.random()*mineBoard.length);
					rj = (int)(Math.random()*mineBoard[0].length);
				} while (mineBoard[ri][rj]==9);
				tmp = mineBoard[ri][rj];
				mineBoard[ri][rj]=mineBoard[f][c];
				mineBoard[f][c]=tmp;
			}
			return;
		}
		for (int a = -1; a < 2; a++) {
			for (int b = -1; b < 2; b++) {
				if((f+a>=0)&&(c+b>=0)&&(f+a<mineBoard.length)&&(c+b<mineBoard[0].length)) {
					if(mineBoard[f+a][c+b]==9) {
						do {
							ri = (int)(Math.random()*mineBoard.length);
							rj = (int)(Math.random()*mineBoard[0].length);
						} while ((mineBoard[ri][rj]==9)||
								((ri<f+2)&&(ri>f-2)&&(rj<c+2)&&(rj>c-2)));
						tmp = mineBoard[ri][rj];
						mineBoard[ri][rj]=mineBoard[f+a][c+b];
						mineBoard[f+a][c+b]=tmp;
					}
				}
			}
		}
	}
}

