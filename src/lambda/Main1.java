package lambda;

public class Main1 {
	MyType sabiranje = (int a, int b) -> a + b;

	public int operacija(int a, int b, MyType myType){
		return myType.mojaOperacija(a, b);
	}
	
	public static void main(String[] args) {
		Main1 m = new Main1();
	    System.out.println("Result: " + m.operacija(3, 5, m.sabiranje)); 
	}

}
