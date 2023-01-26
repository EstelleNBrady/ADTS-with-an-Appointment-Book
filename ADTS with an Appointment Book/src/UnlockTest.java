import edu.uwm.cs.junit.LockedTestCase;
//Estelle Brady
//CS 351 - section 401
//collaborated with TA Yash, Lucus Patron and Julien Moreno
//used a YouTube video to help with insert method -https://www.youtube.com/watch?v=Hj4GAi8CNPM
//used Activity 2 to get help with ensureCapacity
public class UnlockTest {
	public static void main(String[] args){
		unlock("TestApptBook");
	}
	
	private static void unlock(String classname){
		System.out.println("Unlocking tests for " + classname + ".java");
		LockedTestCase.unlockAll(classname);
		System.out.format("All tests in %s.java are unlocked.%n"
				+ "You can run them against your program now.%n"
				+ "Remember to push %s.tst (refresh the project to show it).%n%n", classname, classname);
	}
}
