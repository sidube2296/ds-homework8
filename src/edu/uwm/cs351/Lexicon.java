package edu.uwm.cs351;
import java.util.function.Consumer;

/**
 * Set of strings, sorted lexicographically.
 */
public class Lexicon {
	
	private static class Node {
		String string;
		Node left, right;
		Node (String s) { string = s; }
	}
	
	private Node root;
	private int manyNodes;
	
	private static Consumer<String> reporter = (s) -> System.out.println("Invariant error: "+ s);
	
	/**
	 * Used to report an error found when checking the invariant.
	 * By providing a string, this will help debugging the class if the invariant should fail.
	 * @param error string to print to report the exact error found
	 * @return false always
	 */
	private boolean report(String error) {
		reporter.accept(error);
		return false;
	}

	private int reportNeg(String error) {
		report(error);
		return -1;
	}

	/**
	 * Count all the nodes in this subtree, 
	 * while checking that all the keys are all in the range (lo,hi),
	 * and that the keys are arranged in BST form.
	 * If a problem is found, -1 is returned and exactly one problem is reported.
	 * <p>
	 * @param n the root of the subtree to check
	 * @param lo if non-null then all strings in the subtree rooted
	 * 				at n must be [lexicographically] greater than this parameter
	 * @param hi if non-null then all strings in the subtree rooted
	 * 				at n must be [lexicographically] less than this parameter
	 * @return number of nodes in the subtree, or -1 is there is a problem.
	 */
	private int checkInRange(Node n, String lo, String hi)
	{
		// TODO: Implement this method
		/* Check that all strings in the subtree are in the parameter range,
		 * and none of them are null.
		 * Report any errors.  If there is an error return a negative number.
		 * (Write "return reportNeg(...);" when detecting a problem.)
		 * Otherwise return the number of nodes in the subtree.
		 * Note that the bounds in recursive calls may be different.
		 */
	    if (n == null) return 0;

	    if (n.string == null) return reportNeg("String in node is null");

	    if (lo != null && n.string.compareTo(lo) <= 0) {
	        return reportNeg("String" + n.string + "falls below the lower bound " + lo + ".");
	    }
	    if (hi != null && n.string.compareTo(hi) >= 0) {
	        return reportNeg("String" + n.string + "falls above the upper bound " + hi + ".");
	    }
	    
	    if (checkInRange(n.left, lo, n.string) < 0)  return -1;

	    if (checkInRange(n.right, n.string, hi) < 0) return -1;

	    return 1 + (checkInRange(n.left, lo, n.string)) + (checkInRange(n.right, n.string, hi));
	}
	
	/**
	 * Check the invariant.  
	 * Returns false if any problem is found. 
	 * @return whether invariant is currently true.
	 * If false is returned then exactly one problem has been reported.
	 */
	private boolean wellFormed() {
		// TODO: most of the work is done by checkInRange
		// If all checks pass, the invariant holds
		return checkInRange(root, null, null) < 0 ? false : 
		(checkInRange(root, null, null) != manyNodes ? 
		report("Mismatch in node count: expected " + manyNodes + " but found " + checkInRange(root, null, null)) : 
		true);
	}

	private Lexicon(boolean unused) { } // do not modify, used by Spy
	
	/**
	 * Creates an empty lexicon.
	 */
	public Lexicon() {
		// TODO: Implement the constructor (BEFORE the assertion!)
		root = null;
		manyNodes = 0;
		assert wellFormed() : "invariant false at end of constructor";
	}
	

	/** Gets the size of this lexicon.
	 * @return the count of strings in this lexicon
	 */
	public int size() {
		assert wellFormed() : "invariant false at start of size()";
		// TODO: Implement this method
		return manyNodes;
	}
	
	/**
	 * Gets the [lexicographically] least string in the lexicon.
	 * @return the least string or null if empty
	 */
	public String getMin() {
		assert wellFormed() : "invariant false at start of getMin()";
		// TODO: Implement this method
	    if (root == null) return null;
	    Node current = root;
	    while (current.left != null)
	        current = current.left;
	    return current.string;
	}
	
	/**
	 * Checks if the given string is in the lexicon.
	 * @param str the string to search for (maybe null)
	 * @return true if str is in the lexicon, false otherwise
	 */
	public boolean contains(String str) {
		assert wellFormed() : "invariant false at start of contains()";
		// TODO: Implement this method
	    if (str == null) return false;
	    Node c = root;
	    while (c != null) {
	        if (str.compareTo(c.string) == 0) return true;
	        else if (str.compareTo(c.string) < 0) c = c.left;
	        else c = c.right;
	    }
	    return false;
	  }
	
	/**
	 * Gets the next [lexicographically] greater string than the given string.
	 * @param str the string of which to find the next greatest
	 * @return the next string greater than str
	 * @throws NullPointerException if str is null
	 */
	public String getNext(String str) {
		assert wellFormed() : "invariant false at start of getNext()";
		// TODO: Implement this method using a loop!
		//			Recursion is *NOT* allowed for this method.
	    if (str == null) throw new NullPointerException("Cannot search for next of null.");
	    Node c = root;
	    Node s = null;
	    while (c != null) {
	        if (str.compareTo(c.string) < 0) {
	            s = c;
	            c = c.left; 
	        } else c = c.right;
	    }
	    return (s != null) ? s.string : null;
	}
	
	/**
	 * Accept into the consumer all strings in this lexicon.
	 * @param consumer the consumer to accept the strings
	 * @throws NullPointerException if consumer is null
	 */
	public void consumeAll(Consumer<String> consumer) {
		// We don't assert the invariant, because we do nothing other than
		// call another public method.
		consumeAllWithPrefix(consumer,"");
	}
	
	/**
	 * Accept into the consumer all strings that start with the given prefix.
	 * @param consumer the consumer to accept the strings
	 * @param prefix the prefix to find all strings starting with
	 * @throws NullPointerException if consumer or prefix is null
	 */
	public void consumeAllWithPrefix(Consumer<String> consumer, String prefix) {
		// TODO: Implement this to call the helper after preliminary checks (if any)
	}
	
	private void consumeAllHelper(Consumer<String> consumer, String prefix, Node n) {
		// TODO: Implement this (recursive!) helper method.
	}
	
	/// Mutators
	
	/**
	 * Add a new string to the lexicon. If it already exists, do nothing and return false.
	 * @param str the string to add (must not be null)
	 * @return true if str was added, false otherwise
	 * @throws NullPointerException if str is null
	 */
	public boolean add(String str) {
		assert wellFormed() : "invariant false at start of add()";
		boolean result = false;
		// TODO: Implement this method
		if (str == null) throw new NullPointerException("String is null.");
		if (root == null) {
			root = new Node(str);
			manyNodes++;
			result = true;
		} else {
			Node c = root;
			while (true) {
				if (str.compareTo(c.string) == 0) break;
				else if (str.compareTo(c.string) < 0) {
					if (c.left == null) {
						c.left = new Node(str);
						manyNodes++;
						result = true;
						break;
					}
					c = c.left;
				} else {
					if (c.right == null) {
						c.right = new Node(str);
						manyNodes++;
						result = true;
						break;
					}
					c = c.right;
				}
			}
		}
		assert wellFormed() : "invariant false at end of add()";
		return result;
	}
	
	// TODO: Optional: you may wish to define a helper method or two.

	/**
	 * Add all strings in the array into this lexicon from the range [lo,hi).
	 * The elements are added recursively from the middle, so that
	 * if the array was sorted, the tree will be balanced.
	 * All the tree mutations are done by add.
	 * Return number of strings actually added; some might not be added
	 * if they are duplicates.
	 * @param array source
	 * @param lo index lower bound
	 * @param hi index upper bound
	 * @return number of strings added
	 * @throws NullPointerException if array is null
	 */
	public int addAll(String[] array, int lo, int hi) {
		assert wellFormed() : "invariant false at start of addAll()";
		// TODO: Implement this method (be efficient!)
		
		// NB: As long as you never touch any fields directly (or call private methods)
		// you shouldn't *need* to check the invariant. We will anyway.
		assert wellFormed() : "invariant false at end of addAll()";
		return -1;
	}
	
	/**
	 * Copy all the strings from lexicon (in sorted order) into the array starting
	 * at the given index.  Return the next index for (later) elements.
	 * This is a helper method for {@link #toArray(String[])}.
	 * @param array destination of copy
	 * @param root the subtree whose elements should be copied
	 * @param index the index to place the next element
	 * @return the next spot in the array to use after this subtree is done
	 */
	private int copyInto(String[] array, Node root, int index) {
		// TODO: Implement this method
		return -1;
	}
	
	/**
	 * Return an array of all the strings in this lexicon (in order).
	 * @param array to use unless null or too small
	 * @return array copied into
	 */
	public String[] toArray(String[] array) {
		assert wellFormed() : "invariant false at the start of toArray()";
		// TODO: Implement this method using copyInto after some preliminaries
		return array;
	}
	
	/**
	 * Used for testing the invariant.  Do not change this code.
	 */
	public static class Spy {
		/**
		 * Return the sink for invariant error messages
		 * @return current reporter
		 */
		public Consumer<String> getReporter() {
			return reporter;
		}

		/**
		 * Change the sink for invariant error messages.
		 * @param r where to send invariant error messages.
		 */
		public void setReporter(Consumer<String> r) {
			reporter = r;
		}
		/**
		 * A public version of the data structure's internal node class.
		 * This class is only used for testing.
		 */
		public static class Node extends Lexicon.Node {
			// Even if Eclipse suggests it: do not add any fields to this class!
			/**
			 * Create a node with null data and null next fields.
			 */
			public Node() {
				this(null, null, null);
			}
			/**
			 * Create a node with the given values
			 * @param s data for new node, may be null
			 * @param l left for new node, may be null
			 * @param r right for new node, may be null
			 */
			public Node(String s, Node l, Node r) {
				super(null);
				this.string = s;
				this.left = l;
				this.right = r;
			}
			
			/**
			 * Change the data in the node.
			 * @param s new string to use
			 */
			public void setString(String s) {
				this.string = s;
			}
			
			/**
			 * Change a node by setting the "left" field.
			 * @param n new left field, may be null.
			 */
			public void setLeft(Node n) {
				this.left = n;
			}
			
			/**
			 * Change a node by setting the "right" field.
			 * @param n new right field, may be null.
			 */
			public void setRight(Node n) {
				this.right = n;
			}
		}

		/**
		 * Create a debugging instance of the ADT
		 * with a particular data structure.
		 * @param r root
		 * @param m many nodes
		 * @return a new instance of a BallSeq with the given data structure
		 */
		public Lexicon newInstance(Node r, int m) {
			Lexicon result = new Lexicon(false);
			result.root = r;
			result.manyNodes = m;
			return result;
		}

		/**
		 * Return whether debugging instance meets the 
		 * requirements on the invariant.
		 * @param lx instance of to use, must not be null
		 * @return whether it passes the check
		 */
		public boolean wellFormed(Lexicon lx) {
			return lx.wellFormed();
		}
		
		/**
		 * Return the result of the helper method checkInRange
		 * @param n node to check for
		 * @param lo lower bound
		 * @param hi upper bound
		 * @return result of running checkInRange on a debugging instance of Lexicon
		 */
		public int checkInRange(Node n, String lo, String hi) {
			Lexicon lx = new Lexicon(false);
			lx.root = null;
			lx.manyNodes = -1;
			return lx.checkInRange(n,lo,hi);
		}
	}
}