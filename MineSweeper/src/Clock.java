import java.util.TimerTask;

import javax.swing.JTextField;

class Clock extends TimerTask{

	private int time = 0;
	JTextField timePlayed;
	
	public Clock(JTextField timePlayed) {
		this.timePlayed = timePlayed;
	}
	
	public void run() 
    { 
        time++;
        timePlayed.setText(String.valueOf(time));
        if (time==999) {
        	cancel();
		}
    } 
}
