package cs430;

public class CUPC {
	public int[] vertext;
	public int[] previous;
	public int size;

	public CUPC(int size, int[] Initial_Size) {
		this.vertext = new int[size];
		this.previous = this.vertext;
		this.size = size;
		this.vertext[size - 1] = Initial_Size[size - 1];

	}

	public void setVertext(int[] thisWater) {
		vertext = thisWater;
	}

	public void setPrevious(int[] previousWater) {
		previous = previousWater;

	}
}

