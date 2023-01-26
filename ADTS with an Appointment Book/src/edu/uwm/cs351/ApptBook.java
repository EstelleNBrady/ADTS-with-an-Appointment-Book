// This is an assignment for students to complete after reading Chapter 3 of
// "Data Structures and Other Objects Using Java" by Michael Main.
package edu.uwm.cs351;
import junit.framework.TestCase;
//Estelle Brady
//CS 351 - section 401
//collaborated with TA Yash, Lucus Patron and Julien Moreno
//used a YouTube video to help with insert method -https://www.youtube.com/watch?v=Hj4GAi8CNPM
//used Activity 2 to get help with ensureCapacity

/******************************************************************************
 * This class is a homework assignment;
 * An ApptBook ("book" for short) is a sequence of Appointment objects in sorted order.
 * The book can have a special "current element," which is specified and 
 * accessed through four methods that are available in the this class 
 * (start, getCurrent, advance and isCurrent).
 * <p>
 * Notes:
 * <ol>
 * <li> The capacity of a book can change after it's created, but
 *   the maximum capacity is limited by the amount of free memory on the 
 *   machine. The constructor, insert, insertAll, and clone
 *   methods will result in an
 *   {@link java.lang.OutOfMemoryError} when free memory is exhausted.
 * <li>
 *   A book's capacity cannot exceed the maximum integer 2,147,483,647
 *   ({@link Integer#MAX_VALUE}). Any attempt to create a larger capacity
 *   results in a failure due to an arithmetic overflow. 
 * </ol>
 * <b>NB</b>: Neither of these conditions require any work for the implementors (students).
 *
 *
 ******************************************************************************/
public class ApptBook implements Cloneable {

	/** Static Constants */
	private static final int INITIAL_CAPACITY = 1;

	/** Fields */
	
	// TODO: You need 'data', 'manyItems' and 'currentIndex' fields.
	// Don't initialize them here, but rather in the constructor(s).
	
	// Invariant of the ApptBook class:
	//   1. The number of elements in the books is in the instance variable 
	//      manyItems.
	//   2. For an empty book (with no elements), we do not care what is 
	//      stored in any of data; for a non-empty book, the elements of the
	//      book are stored in data[0] through data[manyItems-1] and we
	//      don't care what's in the rest of data.
	//   3. None of the elements are null and they are ordered according to their
	//      natural ordering (Comparable); duplicates *are* allowed.
	//   4. If there is a current element, then it lies in data[currentIndex];
	//      if there is no current element, then currentIndex equals manyItems.
	
	//'data' - array, 'manyItems' -used and 'currentIndex' -where the pointer is
	
	private Appointment[] data; //array of the elements
	private int manyItems; //used items
	private int currentIndex; //what index we are at

	private static boolean doReport = true; // change only in invariant tests
	private boolean report(String error) {
		if (doReport) {
			System.out.println("Invariant error: " + error);
		}
		return false;
	}
/**
 * this method is an extra test within certain methods to check certain exceptions
 *checks whether it is null, if there are at least as many items as in manyItems
 *is any elements are null
 *if they are in natural order
 *if currentIndex is never negative or more than numbers in book
 * @return report or true
 */
	private boolean wellFormed() {
		// Check the invariant.
		
		// 1. data array is never null
		if(this.data == null)
			return report("an element in data is null");
		// 2. The data array has at least as many items in it as manyItems
		//    claims the book has
		if(data.length < manyItems)
			return report("the array is shorter than the amount of items");

		// 3. None of the elements are null and all are in natural order
		for(int i=0; i<manyItems; i++)
			if(data[i] == null)
				return report("An element in the array is null");
		for(int i=0; i+1<manyItems; i++)
			if(data[i].compareTo(data[i+1])>0)
				return report("This is out of order");
		// 4. currentIndex is never negative and never more than the number of
		//    items in the book.
		// TODO	
		if(currentIndex < 0 || currentIndex > manyItems)
			return report("currentIndex cannot be less than 0 & less than manyItems");
		// If no problems discovered, return true
		return true;
	}

	/**
	 * Initialize an empty book with 
	 * an initial capacity of INITIAL_CAPACITY. The {@link #insert(Appointment)} method works
	 * efficiently (without needing more memory) until this capacity is reached.
	 * @postcondition
	 *   This book  is empty and has an initial capacity of INITIAL_CAPACITY
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for initial array.
	 **/  
	// This is only for testing the invariant.  Do not change!
	private ApptBook(boolean testInvariant) {
		
		
	}
	/**
	 * sets data to the initial capacity
	 */
	public ApptBook( )
	{
		this.data = new Appointment[INITIAL_CAPACITY];
		assert wellFormed() : "invariant failed at end of constructor";
	}

	/**
	 * Initialize an empty book with a specified initial capacity
	 * The {@link #insert(Appointment)} method works
	 * efficiently (without needing more memory) until this capacity is reached.
	 * @param initialCapacity
	 *   the initial capacity of this book, must not be negative
	 * @exception IllegalArgumentException
	 *   Indicates that name, bpm or initialCapacity are invalid
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for an array with this many elements.
	 *   new Appointment[initialCapacity].
	 **/   
	public ApptBook(int initialCapacity)
	{
		if(initialCapacity < 0)
			throw new IllegalArgumentException("initial Capacity cannot be negative");
		if(initialCapacity > Integer.MAX_VALUE)
			throw new OutOfMemoryError("Memory has gone over the limit");
		this.data = new Appointment[initialCapacity];	
		// TODO: Implemented by student.
		assert wellFormed() : "invariant failed at end of constructor";
	}

	/**
	 * Determine the number of elements in this book.
	 * @return
	 *   the number of elements in this book -manyItems
	 **/ 
	public int size( )
	{	
		assert wellFormed() : "invariant failed at start of size";
		return manyItems;
	}

	/**
	 * Set the current element at the front of this book., so we must sent currentIndex to 0
	 * @postcondition
	 *   The front element of this book is now the current element (but 
	 *   if this book has no elements at all, then there is no current 
	 *   element).
	 **/ 
	public void start( )
	{
	
		assert wellFormed() : "invariant failed at start of start";
		currentIndex = 0;
		assert wellFormed() : "invariant failed at end of start";
	}

	/**
	 * Accessor method to determine whether this book has a specified 
	 * current element that can be retrieved with the 
	 * getCurrent method. This is used by comparing currentIndex and manyItems
	 * @return
	 *   true (there is a current element) or false (there is no current element at the moment)
	 **/
	public boolean isCurrent( )
	{
		assert wellFormed() : "invariant failed at start of isCurrent";
		System.out.print("CurrentIndex :" +currentIndex +" manyItems " + manyItems);
		if(currentIndex == manyItems)
			return false;
		return true;
	}

	/**
	 * Accessor method to get the current element of this book. 
	 * @precondition
	 *   isCurrent() returns true.
	 * @return
	 *   the current element of this book
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   getCurrent may not be called.
	 **/
	public Appointment getCurrent( )
	{
		assert wellFormed() : "invariant failed at start of getCurrent";
		if(this.isCurrent() == false)
			throw new IllegalStateException("There is no current element");
		return this.data[currentIndex];
		// TODO: Implemented by student.
		// Don't change "this" object!
	}

	/**
	 * Move forward, so that the current element will be the next element in
	 * this book.
	 * @precondition
	 *   isCurrent() returns true. 
	 * @postcondition
	 *   If the current element was already the end element of this book 
	 *   (with nothing after it), then there is no longer any current element. 
	 *   Otherwise, the new element is the element immediately after the 
	 *   original current element.
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   advance may not be called.
	 **/
	public void advance( )
	{
		assert wellFormed() : "invariant failed at start of advance";
		//if there is a current element, then you can move the current Index forwards
		if(this.isCurrent() == true)
			++currentIndex;
		else
			throw new IllegalStateException("there is no element");
		assert wellFormed() : "invariant failed at end of advance";
		// TODO: Implemented by student.
	
	}

	/**
	 * Remove the current element from this book.
	 * @precondition
	 *   isCurrent() returns true.
	 * @postcondition
	 *   The current element has been removed from this book, and the 
	 *   following element (if there is one) is now the new current element. 
	 *   If there was no following element, then there is now no current 
	 *   element.
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   removeCurrent may not be called. 
	 **/
	public void removeCurrent( )
	{
		assert wellFormed() : "invariant failed at start of removeCurrent";
		if(this.isCurrent() ==false)
			throw new IllegalStateException("this is not in the book");
		//takes the current index, replaces it with the next index, shifts all to the left 
		//after this forloop, we will get rid of the null element by --manyItems
		for(int i = currentIndex; i<manyItems-1; i++) {
					data[i]=data[i+1];
				}
		--manyItems;
			assert wellFormed() : "invariant failed at end of removeCurrent";		
			}	
			
	
	
	/**
	 * Set the current element to the first element that is equal
	 * or greater than the guide.
	 * @param guide element to compare against, must not be null.
	 */
	//Modified by TA Yash
	public void setCurrent(Appointment guide) {
		if(guide == null)
			throw new IllegalArgumentException("cannot be null in setCurrent");
		//this sets the currentIndex to 0
		start();
		//if this element exists in that index and if the element in index 0
		//is before the guide element, then it will advance
		while(isCurrent() && getCurrent().compareTo(guide)< 0) {
			advance();	
		}
//		int tempIndex = 0;
//		while(tempIndex < manyItems && data[tempIndex].compareTo(element)<= 0) {
//			tempIndex++;
//		if(data[tempIndex].compareTo(guide)>=0) {
//			this.currentIndex = tempIndex;
//		}
//		if(data[manyItems].compareTo(guide)>=0) {
//			this.currentIndex = manyItems;
//		}else {
//			
//			
//		}

		// (Binary search would be much faster for a large book.
		// but don't worry about efficiency for this method yet.)
	}

	/**
	 * Change the current capacity of this book as needed so that
	 * the capacity is at least as big as the parameter.
	 * This code must work correctly and efficiently if the minimum
	 * capacity is (1) smaller or equal to the current capacity (do nothing)
	 * (2) at most double the current capacity (double the capacity)
	 * or (3) more than double the current capacity (new capacity is the
	 * minimum passed).
	 * @param minimumCapacity
	 *   the new capacity for this book
	 * @postcondition
	 *   This book's capacity has been changed to at least minimumCapacity.
	 *   If the capacity was already at or greater than minimumCapacity,
	 *   then the capacity is left unchanged.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for: new array of minimumCapacity elements.
	 **/
	//helped by activity 2
	private void ensureCapacity(int minimumCapacity)
	{
		if(minimumCapacity <= data.length) return;
			int c1 = data.length*2;
		if(c1 <minimumCapacity) 
			c1 = minimumCapacity;
		Appointment [] temp = new Appointment[c1];
		for(int i = 0; i<manyItems; ++i)
			temp[i] = data[i];
		data = temp;
	}

	/**
	 * Add a new element to this book, in order.  If an equal appointment is already
	 * in the book, it is inserted after the last of these. 
	 * If the new element would take this book beyond its current capacity,
	 * then the capacity is increased before adding the new element.
	 * The current element (if any) is not affected.
	 * @param element
	 *   the new element that is being added, must not be null
	 * @postcondition
	 *   A new copy of the element has been added to this book. The current
	 *   element (whether or not is exists) is not changed.
	 * @exception IllegalArgumentException
	 *   indicates the parameter is null
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for increasing the book's capacity.
	 **/
	//youtube video assisted in implementing the insert method. - link up top
	public void insert(Appointment element) {
		assert wellFormed() : "invariant failed at start of insert";
		if(element == null)
			throw new IllegalArgumentException("The element is null in insert");
		if(currentIndex > data.length) {
			throw new OutOfMemoryError("not enough memory");
		}
		ensureCapacity(manyItems+1);
		
		//set a temp index because we cannot influence currentIndex
		//iterate through the array to see if any of these values are equal or less than the incoming element
		//breaks out when these are true
		int tempIndex = 0;
		while(tempIndex < manyItems && data[tempIndex].compareTo(element)<= 0) {
			tempIndex++;
		}
		
		//with the given index through the while loop, it will break if we get the element that comes after element
		//it then shifts the left of them to the right
		for(int i = manyItems; i>tempIndex; i--) {
			data[i] = data[i-1];
		}
		//if the currentindex is not less than tempindex, then we can increment current index
		data[tempIndex] = element;	manyItems++;	
		if(!(currentIndex<tempIndex)) currentIndex++;
		
		assert wellFormed() : "invariant failed at end of insert";	
	}

	/**
	 * Place all the appointments of another book (which may be the
	 * same book as this!) into this book in order as in {@link #insert}.
	 * The elements should added one by one from the start.
	 * The elements are probably not going to be placed in a single block.
	 * @param addend
	 *   a book whose contents will be placed into this book
	 * @precondition
	 *   The parameter, addend, is not null. 
	 * @postcondition
	 *   The elements from addend have been placed into
	 *   this book. The current el;ement (if any) is
	 *   unchanged.
	 * @exception NullPointerException
	 *   Indicates that addend is null. 
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory to increase the size of this book.
	 **/
	
	public void insertAll(ApptBook addend)
	{
		assert wellFormed() : "invariant failed at start of insertAll";
		// TODO: Implemented by student.
		// Watch out for the this==addend case!
		// Cloning the addend is an easy way to avoid problems.
		if(addend ==null)
			throw new NullPointerException("this addend is null!");
		if(manyItems ==Integer.MAX_VALUE)
			throw new OutOfMemoryError("insert all is out of memory");
		//cloning the addend to manipulate a - not the original
		ApptBook a = addend.clone();
		//need enough room to add data's info into a
		ensureCapacity(manyItems + addend.manyItems);
		
		//iterates through each index of data and individually puts each one in a
		for(int i = 0; a.manyItems>i; ++i) {
			insert(a.data[i]);
		}
			
		assert wellFormed() : "invariant failed at end of insertAll";
		
		assert addend.wellFormed() : "invariant of addend broken in insertAll";
	}

	/**
	 * Generate a copy of this book.
	 * @return
	 *   The return value is a copy of this book. Subsequent changes to the
	 *   copy will not affect the original, nor vice versa.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for creating the clone.
	 **/ 
	public ApptBook clone( ) { 
		assert wellFormed() : "invariant failed at start of clone";
		ApptBook answer;
	
		try
		{
			answer = (ApptBook) super.clone( );
		}
		catch (CloneNotSupportedException e)
		{  // This excetion should not occur. But if it does, it would probably
			// indicate a programming error that made super.clone unavailable.
			// The most common error would be forgetting the "Implements Cloneable"
			// clause at the start of this class.
			throw new RuntimeException
			("This class does not implement Cloneable");
		}
	
		// all that is needed is to clone the data array.
		// (Exercise: Why is this needed?)
		answer.data = data.clone( );
	
		assert wellFormed() : "invariant failed at end of clone";
		assert answer.wellFormed() : "invariant on answer failed at end of clone";
		return answer;
	}

	// don't change this nested class:
	public static class TestInvariantChecker extends TestCase {
		Time now = new Time();
		Appointment e1 = new Appointment(new Period(now,Duration.HOUR),"1: think");
		Appointment e2 = new Appointment(new Period(now,Duration.DAY),"2: current");
		Appointment e3 = new Appointment(new Period(now.add(Duration.HOUR),Duration.HOUR),"3: eat");
		Appointment e4 = new Appointment(new Period(now.add(Duration.HOUR.scale(2)),Duration.HOUR.scale(8)),"4: sleep");
		Appointment e5 = new Appointment(new Period(now.add(Duration.DAY),Duration.DAY),"5: tomorrow");
		ApptBook hs;

		protected void setUp() {
			hs = new ApptBook(false);
			ApptBook.doReport = false;
		}

		public void test0() {
			assertFalse(hs.wellFormed());
		}
		
		public void test1() {
			hs.data = new Appointment[0];
			hs.manyItems = -1;
			assertFalse(hs.wellFormed());
			hs.manyItems = 1;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.manyItems = 0;
			assertTrue(hs.wellFormed());
		}
		
		public void test2() {
			hs.data = new Appointment[1];
			hs.manyItems = 1;
			assertFalse(hs.wellFormed());
			hs.manyItems = 2;
			assertFalse(hs.wellFormed());
			hs.data[0] = e1;
			
			doReport = true;
			hs.manyItems = 0;
			assertTrue(hs.wellFormed());
			hs.manyItems = 1;
			hs.data[0] = e1;
			assertTrue(hs.wellFormed());
		}

		public void test3() {
			hs.data = new Appointment[3];
			hs.manyItems = 2;
			hs.data[0] = e2;
			hs.data[1] = e1;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.data[0] = e1;
			assertTrue(hs.wellFormed());
			hs.data[1] = e2;
			assertTrue(hs.wellFormed());
		}
		
		public void test4() {
			hs.data = new Appointment[10];
			hs.manyItems = 3;
			hs.data[0] = e2;
			hs.data[1] = e4;
			hs.data[2] = e3;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.data[2] = e5;
			assertTrue(hs.wellFormed());
			hs.data[0] = e4;
			assertTrue(hs.wellFormed());
			hs.data[1] = e5;
			assertTrue(hs.wellFormed());
		}
		
		public void test5() {
			hs.data = new Appointment[10];
			hs.manyItems = 4;
			hs.data[0] = e1;
			hs.data[1] = e3;
			hs.data[2] = e2;
			hs.data[3] = e5;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.data[2] = e4;
			assertTrue(hs.wellFormed());
			hs.data[2] = e5;
			assertTrue(hs.wellFormed());
			hs.data[2] = e3;
			assertTrue(hs.wellFormed());
		}
		
		public void test6() {
			hs.data = new Appointment[3];
			hs.manyItems = -2;
			assertFalse(hs.wellFormed());
			hs.manyItems = -1;
			assertFalse(hs.wellFormed());
			hs.manyItems = 1;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.manyItems = 0;
			assertTrue(hs.wellFormed());
		}

		public void test7() {
			hs.data = new Appointment[3];
			hs.data[0] = e1;
			hs.data[1] = e2;
			hs.data[2] = e4;
			hs.manyItems = 4;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.manyItems = 3;
			assertTrue(hs.wellFormed());
		}

		public void test8() {
			hs.data = new Appointment[3];
			hs.data[0] = e2;
			hs.data[1] = e3;
			hs.data[2] = e1;
			hs.manyItems = 2;
			hs.currentIndex = -1;
			assertFalse(hs.wellFormed());
			hs.currentIndex = 3;
			assertFalse(hs.wellFormed());
			
			doReport = true;
			hs.currentIndex = 0;
			assertTrue(hs.wellFormed());
			hs.currentIndex = 1;
			assertTrue(hs.wellFormed());
			hs.currentIndex = 2;
			assertTrue(hs.wellFormed());
		}
	}
}

