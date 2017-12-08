/**
 * 
 */
package midtem;

/**
 * @author lijinyang
 *
 */
public class Fraction {

	/**
	 * 
	 */
	
	int numerator, denominator;
	
	public Fraction(int numerator, int denominator) {
		
		this.numerator = numerator;
		this.denominator = denominator;
		
	}
	
	public Fraction add( Fraction b) {
		
		int n, d;
		n = this.numerator * b.denominator + this.denominator * b.numerator;
		d = this.denominator * b.denominator;
		
		return new Fraction(n, d);
	}
	
	public static Fraction multiplication(Fraction a, Fraction b){
		
		int n, d;
		n = a.numerator * b.numerator;
		d = a.denominator * b.denominator;
		
		return new Fraction(n, d);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Fraction a = new Fraction (1,2);
		Fraction b = new Fraction(3,4);
		
		Fraction c = a.add(b);
		
		System.out.println(c.numerator +"/"+c.denominator );
		
	}

}
