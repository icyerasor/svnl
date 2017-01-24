

public class Test {
	public final static int BASE    = 10000;
	public final static int MODIFY  = 10001;
	public final static int ADD     = 10010;
	public final static int DELETE  = 10100;
	public final static int REPLACE = 11000;
	
	public static int getVisibility(int actions, int action) {
		int diff = action-BASE;
		int cmp = 2*diff;
		
		int result = actions;
		if(actions%diff != 0) {
			result = result-(actions%diff);
		}
		
		result = result-diff;
		
		if(result%cmp == 0) {
			return 1;
		}
		return 0;
	}
	
	public static void main(String[] args) {
		System.out.println(getVisibility(ADD, ADD));
		System.out.println(getVisibility(MODIFY+ADD, ADD));
		System.out.println(getVisibility(REPLACE+MODIFY, MODIFY));
		System.out.println(getVisibility(REPLACE+MODIFY, REPLACE));
		System.out.println(getVisibility(REPLACE+MODIFY, ADD));
	}
}
