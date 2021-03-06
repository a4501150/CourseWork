package cs430;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Manycups extends CUPC {

	public Manycups(int size, int[] Initial_Size) {
		super(size, Initial_Size);
	}

	final static String IMPOSSIBLE = "It's impossible to find t";
	final static String GOTYOU = "Yes!we got t";
	static CUPC Initial_Water;
	
	static Queue<CUPC> q = new ArrayDeque<CUPC>();
	static Stack<CUPC> allVertext = new Stack<CUPC>();

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		System.out.println("please entry the cup size u from samll to big ,need splide by ,  ");
		String a = in.nextLine();
		System.out.println("please entry the target u want");
		int t = in.nextInt();
		String[] ss = a.split(",");
		int size = ss.length;
		int[] cups = new int[size];
		for (int i = 0; i < size; i++) {
			cups[i] = Integer.parseInt(ss[i]);
		}
		//CUPC Initial_Water = new CUPC(size, cups);
		Initial_Water = new CUPC(size, cups);
	    boolean l = Find_target( t, cups);
	    if(l)
	    	System.out.print(GOTYOU);
	    else
	    	System.out.print(IMPOSSIBLE);
	    

	}
	// 我们去测试当前结果是否已经在前面出现过
	public static boolean test_now(Stack<CUPC> allVertext, CUPC initial_Water) {
		if (allVertext.contains(initial_Water))
			return true;
		return false;
	}

	// 我们去测试是否已经找到了我们想要的target
	public static boolean test_target(CUPC initial_Water, int size, int t) {
		for (int i = 0; i < size; i++) {
			if (initial_Water.vertext[i] == t)
				return true;
		}
		return false;
	}

	// if我们找到了target print－parth
	public static void print_path(CUPC tem, int size) {
		for (int i = 0; i < size; i++) {
			for (int tmp : tem.previous) {
				System.out.print(i + " ");
			}
			System.out.print("\n");
		}
	}

	// wooooo 终极大boss
	public static boolean Find_target( int t, int[] cups) {
		
		boolean k = true;
		for (int i = 0; i < cups.length; i++) {
			if (cups[i] == t) {
				Initial_Water.vertext[i] = t;
				Initial_Water.vertext[cups.length - 1] = cups[cups.length - 1] - t;
				print_path(Initial_Water, 1);
				k = true;
			} else
				k = false;
		}
		if (t > cups[cups.length-1])
			k = false;
		else {
			k = BFS(t, cups);
		}
		
		return k;
	}

	public static boolean BFS(int t, int[] cups) {
		
		
		
		boolean m = false;
		boolean y = false;
		boolean flag = false;
		int size = cups.length;
//		Queue<CUPC> q = new ArrayDeque<CUPC>();
//		Stack<CUPC> allVertext = new Stack<CUPC>();
		for (int i = 0; i < size; i++) {
			if (Initial_Water.vertext[i] == 0)
				continue;
			
			for (int j = 0; j < size; j++) {
				
				if (Initial_Water.vertext[j] == cups[j] && j==i)
					continue;
				
				else if (Initial_Water.vertext[j] == 0) {
					
					if(Initial_Water.vertext[i] <= cups[j]){				
					Initial_Water.vertext[j] = Initial_Water.vertext[i];
					Initial_Water.vertext[i] = 0;
					} else if (Initial_Water.vertext[i] > cups[j]) {
						Initial_Water.vertext[i] -= Initial_Water.vertext[i] - cups[j];
						Initial_Water.vertext[j] = cups[j];
					}
					
				} else if (Initial_Water.vertext[j] != 0) {
					
					if( cups[j] - Initial_Water.vertext[j] >= Initial_Water.vertext[i]) {
						Initial_Water.vertext[j] += Initial_Water.vertext[i];
						Initial_Water.vertext[i] = 0;
					} else {
						Initial_Water.vertext[i] -= cups[j] - Initial_Water.vertext[j];
						Initial_Water.vertext[j] = cups[j];
					}	
				}

				
				
				
				if (test_now(allVertext, Initial_Water))
					continue;
				
				
				allVertext.add(Initial_Water);
				
				y = test_target(Initial_Water, size, t);
				if (y) {
					
					print_path(Initial_Water, q.size());
					break;
				}
				
				q.add(Initial_Water);
				
				
			}
			
			//最外面的for
			if (y) {
				m = true;
				break;
			}

			else if (q.isEmpty()) {
				m = false;
				break;
			} 
			
			else {
//				Initial_Water = q.remove();
//				m = BFS(t, cups);
				flag = true;
				break;
			}
		}
		if(flag==true) {
			System.out.println("xxxxcalled");
		Initial_Water = q.remove();
		m = BFS(t, cups);
		}
		return m;
	}
}