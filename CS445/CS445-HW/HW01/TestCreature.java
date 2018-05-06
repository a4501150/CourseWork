/**
 * @author Jinyang Li
 *
 */
public class TestCreature {

	public static int THING_COUNT,CREATURE_COUNT;
	
	public TestCreature() {
		
		CREATURE_COUNT  = 5;
		THING_COUNT		= 5;
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new TestCreature(); //create instance of program
		
		Thing[] things = new Thing[THING_COUNT];
		Creature[] creatures = new Creature [CREATURE_COUNT];
		
		
		for (int i=0; i<5; i++ ) {
			things[i] = new Thing("Random Thing #" + i);
		}
		
		creatures[0] = new Tiger("White");
		creatures[1] = new Ant("Big");
		creatures[2] = new Fly("Yellow");
		creatures[3] = new Bat("Black");
		creatures[4] = new Fly("Red");
		
		//combine two arrays
		Thing[] newThings = concat(things, creatures);
		
		//output things created
		System.out.println("Things: \n");
		for( int i = 0; i<5; i++) {
			
			System.out.println(newThings[i].toString());
			
		}
		
		System.out.println("\nCreatures: \n");
		for(int i = 5; i<10; i++) {
			
			System.out.println(newThings[i].toString());
			
		}
		
//		These sections are for initial test only.
//		//section 1 test move()
//		System.out.println("\nSection 1 test move();");
//		for ( Thing crs : newThings)
//			if(crs instanceof Creature)
//				((Creature) crs).move();
//		
//		//section extra test whatDidYouEat() before eat():
//		System.out.println("\nSection extra test whatDidYouEat() before eat()");		
//		for ( Thing crs : newThings)
//			if(crs instanceof Creature)
//				((Creature) crs).whatDidYouEat();
//		
//		//section 2a test eat() : things
//		System.out.println("\nSection 2a test eat() : things");
//		for ( Thing crs : newThings)
//			if(crs instanceof Creature)
//				((Creature) crs).eat(new Thing("Potato Chips"));
//		
//		//section 2b test eat() : creatures
//		System.out.println("\nSection 2b test eat() : creatures;");	
//		for ( Thing crs : newThings)
//			if(crs instanceof Creature)
//				((Creature) crs).eat(new Ant("Eatenable Ant"));
//		
//		//section 3 test whatDidYouEat() after eat():
//		System.out.println("\nSection 3 test whatDidYouEat() after eat(): creatures");		
//		for ( Thing crs : newThings)
//			if(crs instanceof Creature)
//				((Creature) crs).whatDidYouEat();
//		
//		//section 4 test A creature only remembers the last thing it ate.:
//		System.out.println("\nsection 4 test A creature only remembers the last thing it ate.:");		
//		((Creature) newThings[8]).eat(new Tiger("eatMe"));
//		((Creature) newThings[8]).eat(new Ant("eatMe"));
//		((Creature) newThings[8]).eat(new Fly("eatMe"));
//		((Creature) newThings[8]).whatDidYouEat();
//		
	}
	
	public static Thing[] concat(Thing[] a, Thing[] b) {
		   int aLen = a.length;
		   int bLen = b.length;
		   Thing[] c= new Thing[aLen+bLen];
		   System.arraycopy(a, 0, c, 0, aLen);
		   System.arraycopy(b, 0, c, aLen, bLen);
		   return c;
		}

}
