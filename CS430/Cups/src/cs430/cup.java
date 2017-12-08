package cs430;

public class cup {

	int volume;
	int water;
	
	public cup() {
		
		this.volume = 0;
		this.water = 0;
		
	}
	
	public cup(int volume) {
		
		this.volume = volume;
		this.water = 0;
		
	}
	
	public void pour_out(cup target) {
		
		//target 杯子倒满
		if(this.water > target.volume) {
			
		this.water = this.water - target.volume;
		target.water = target.volume;
		
		} 
		
		//target 杯子比较大，当前杯子全部倒过去
		else if ( this.water <= target.volume - target.water ) {
			
			target.water += this.water;
			this.water = 0;
			
		}
		
	}
	
	public void pour_in(int amount) {
		
		this.water += amount;
		
	}
	
	public void fillful() {
		
		this.water = this.volume;
		
	}
	
	@Override
	public boolean equals(Object other) {
		cup target = (cup)other;
		if (target.water == this.water)
			return true;
		return false;
		
	}
	
	
	
}
