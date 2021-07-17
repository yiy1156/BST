package project6;
import java.util.*;
/**
 * An implementation of a binary search tree. The elements are ordered using their natural ordering.
 * This implementation provides guaranteed O(H) (H is the height of this tree which could be 
 * as low as logN for balanced trees, but could be as large as N for unbalanced trees) time cost 
 * for the basic operations (add, remove and contains).
 * This class implements many of the methods provided by the Java framework's TreeSet class. .

 * @author Yi Yang
 */
public class BST<E extends Comparable <E>> extends Object implements Collection<E>, Iterable<E>, Cloneable{
	//creates root
	private Node<E> root;
	/**
	 * Node Class
	 * 
	 * @author Yi Yang
	 *
	 */
	private static class Node<E>  {
	    private E data;
	    private Node<E> left, right;
	    /**Constructs a node
	     * 
	     * @param data
	     */
		public Node(E data)
		{
			this.data = data;
		}
		public int getCount() {
			return 1+(left==null ? 0 : left.getCount()) + (right==null?0 : right.getCount());
		}
	}
    /**Constructs a new, empty tree, sorted according to the natural 
     * ordering of its elements. 
     * All elements inserted into the tree must implement the Comparable interface.
     * 
     */
    public BST() {
    	root=null;
    }
    /**
     * Constructs a new tree containing the elements in the specified collection, sorted according to the natural ordering of its elements. 
     * All elements inserted into the tree must implement the Comparable interface.
     * This operation should be O(N logN) where N is the number of elements in the collection. 
     * This implies, that the tree that is constructed has to have the high that is 
     * approximately logN, not N.
     * @param collection collection whose elements will comprise the new tree
     * @throws NullPointerException if the specified collection is null
     */
    public BST(E[] collection) throws NullPointerException{
    	if (collection == null)
    		throw new NullPointerException("Null collection");
    	root=null;
    }
    
    
    /**
     * Adds the specified element to this set if it is not already present. 
     * More formally, adds the specified element e to this tree if the set contains no element 
     * e2 such that Objects.equals(e, e2). 
     * If this set already contains the element, 
     * the call leaves the set unchanged and returns false.
     * @param e - element to be added to this set
     * @return true if this set did not already contain the specified element
     * @throws NullPointerException - if the specified element is null and this set uses natural 
     * ordering, or its comparator does not permit null elements
     */
    public boolean add(E data) throws NullPointerException{
    	if (data==null) {
    		throw new NullPointerException("null data");
    	}
    	int oldSize = size();
        root  =  recAdd( data, root );
        if (oldSize == size())
            return false;
        return true;
    }
    /**
     * Adds the specified element to this set if it is not already present. Recursively
     * @param data 
     * @param n 
     * @return Node<E>
     */
    private Node<E> recAdd ( E data, Node<E> n ) {
    	if ( n == null ) {
            return new Node<E>(data);
        }
    	if ( n.data == data ) {
            return root;
        }
    	else if ( data.compareTo(n.data) == -1) { // go left
    		n.right =  recAdd ( data, n.right );   
        }
    	else {
            n.left = recAdd ( data, n.left);
        }
    	return n;
    }
    /**
     * Adds all of the elements in the specified collection to this tree.
     * @param collection - collection containing elements to be added to this set
     * @return true if this set changed as a result of the call
     * @throws NullPointerException - 
     * if the specified collection is null or if any element of the collection is null
     */
    public boolean addAll(Collection<? extends E> collection) throws NullPointerException{
    	if (collection==null)
    		throw new NullPointerException("null collection");
    	boolean success = false;
    	for (E data : collection) 
    		if (this.add(data))
    			success=true;
    	return success;
    }
    //creates new boolean variable found
    private boolean found;
    /**
     * Removes the specified element from this tree if it is present. 
     * More formally, removes an element e such that Objects.equals(o, e), 
     * if this tree contains such an element. Returns true if this tree contained the element 
     * (or equivalently, if this tree changed as a result of the call). 
     * (This tree will not contain the element once the call returns.)
     * @param o - object to be removed from this set, if present
     * @return true if this set contained the specified element
     * @throws ClassCastException - if the specified object cannot be compared with the elements currently in this tree
     * NullPointerException - if the specified element is null
     */
    public boolean remove(Object o) throws ClassCastException, NullPointerException
    {
    	if (o==null) 
    		throw new NullPointerException("null object");
    	//if ()
    	//	throw new ClassCastException("Wrong comaprable class");
        found = false;
        root = recRemove(o, root);
        return found;
    }
    /**
     * Removes the specified element from this tree if it is present recursively
     * @param item
     * @param node
     * @return Node<E> node removed
     */
    @SuppressWarnings("unchecked")
	private Node<E> recRemove(Object item, Node<E> node)
    {
        if (node == null)
            found = false;
        else if ( node.data.compareTo((E) item)==1)
            node.left = recRemove(item, node.left);
        else if (node.data.compareTo((E) item)==-1)
            node.right = recRemove(item, node.right );
        else {
            node = removeNode(node);
            found = true;
        }
        return node;
    }
    /**
     * Removes a single instance of the specified element from this collection, if it is present
     * @param node
     * @return removed node
     */
    private Node<E> removeNode(Node<E> node) {
    	 E data;

         if (node.left == null)
             return node.right ;
         else if (node.right  == null)
             return node.left;

         else {
             data = getPredecessor(node.left);
             node.data = data;
             node.left = recRemove(data, node.left);
             return node;
         }
    }
    /**
     * Find the predecessor of a given node
     * @param subtree
     * @return the predecessor
     */
    private E getPredecessor(Node<E> subtree)
    {
        Node<E> temp = subtree;
        while (temp.right  != null)
            temp = temp.right ;
        return temp.data;
    }
    /**
     * Removes all of the elements from this set. 
     * The set will be empty after this call returns.
     * 
     */
    public void clear() {
		root = null;
		
		
	}
    /** 
     * Returns true if this set contains the specified element. More formally, 
     * returns true if and only if this set contains an element e such that Objects.equals(o, e).
     * @param o - object to be checked for containment in this set
     * @return true if this set contains the specified element
     * @throws ClassCastException - if the specified object cannot be compared with the elements currently 
     * in the set
     * NullPointerException - if the specified element is null and this set uses natural ordering, 
     * or its comparator does not permit null elements
     * 
     */
    @SuppressWarnings("unchecked")
	public boolean contains (Object o ) throws ClassCastException, NullPointerException{
    	if (o==null)
    		throw new NullPointerException("null object");
    	//class cast check
        if (root == null)
            return false;
        Node<E> cur = root;

        do {
            E curVal = cur.data;

            if (o == curVal)  return true;
            if (curVal.compareTo((E) o)==1 ) 
            	cur = cur.left;
            else 
            	cur = cur.right;

        } while (cur != null) ;

        return false;


    }
    /**
     * Returns true if this collection contains all of the elements in the specified collection. 
     * This implementation iterates over the specified collection, checking each element returned by
     * the iterator in turn to see if it's contained in this tree. 
     * If all elements are so contained true is returned, otherwise false.
     * @param c - collection to be checked for containment in this tree
     * @return true if this tree contains all of the elements in the specified collection
     * @throws NullPointerException - if the specified collection contains one or more null elements 
     * and this collection does not permit null elements, 
     * or if the specified collection is null.
     */
    public boolean containsAll(Collection<?> c) throws NullPointerException{
    	if (c==null)
    		throw new NullPointerException("null collection");
    	for (Object data : c) 
    		if (!contains(data))
    			return false;
    	return true;
    }
    /** 
     * Returns the number of elements in this tree.
     *@return number of elements in this collection.
     */
    public int size () {
        return size(root);
    }
    /** 
     * Returns the number of elements in this collection.
     * @param n - Node
     *@return number of elements in this collection.
     */
    private int size(Node<E> n) {
        if (n == null ) 
        	return 0;
        return 1 + size(n.left) + size(n.right);
    }
    /**
     * Returns true if this set contains no elements.
     * @return true if collection has no elements
     */
    public boolean isEmpty() {
		return (root == null);
	}
    /**
     * Returns the height of this tree. 
     * The height of a leaf is 1. The height of the tree is the height of its root node.
     * @return the height of this tree or zero if the tree is empty
     */
    public int height() {
    	return height(root);
    }
    /**
     * Returns the height of this tree. 
     * @param node
     * @return the height of this tree or zero if the tree is empty
     */
    private int height(Node<E> node) {
    	if (node==null)
    		return 0;
    	else {
    		int leftHeight = height(node.left);
    		int rightHeight = height(node.right);
    		if (leftHeight>rightHeight)
    			return (leftHeight+1);
    		else
    			return (rightHeight+1);
    	}
    }
    /**
     * Returns an iterator over the elements in this tree in ascending order.
     * @return an iterator over the elements in this set in ascending order
     */
	public Iterator<E> iterator() {
    	return new MyIterator();
    }
	/**
	 * Nested MyIterator Class that implements the iterator
	 * @author Yi Yang
	 *
	 */
    private class MyIterator implements Iterator<E> {
    	Stack<Node<E>> stack = new Stack<Node<E>>();
    	/**
    	 * Default constructor
    	 */
    	public MyIterator() {
           while (root != null) {
        	   stack.push(root);
        	   root = root.left;
           }
        }
    	/**
    	 * Determines if there is a next element
    	 */
    	@Override
        public boolean hasNext(){
           return !stack.isEmpty();
        }
    	/**
    	 * returns the next element
    	 */
    	@Override
        public E next() {
        	Node<E> n = stack.pop();
        	Node<E> r = n.right;
        	while (r!= null) {
        		stack.push(r);
        		r=r.left;
        	}
        	return n.data;
        }
        
    }
    /**
     * Returns an iterator over the elements in this tree in order of the preorder traversal.
     * @return an iterator over the elements in this tree in order of the preorder traversal
     */
    public Iterator<E> preorderIterator() {
    	return new MyPreorderIterator();
    }
    /**
     * Nested MyPreorderIterator Class that implements the iterator
	 * @author Yi Yang
     *
     */
    private class MyPreorderIterator implements Iterator<E> {
    	Stack<Node<E>> stack = new Stack<Node<E>>();
    	/**
    	 * Default constructor
    	 */
    	public MyPreorderIterator() {
    		if (root !=null) 
    			stack.push(root);
    	}
    	/**
    	 * Determines if there is a next element
    	 */
    	@Override
    	public boolean hasNext() {
    		return !stack.isEmpty();
    	}
    	/**
    	 * returns the next element
    	 */
    	@Override
    	public E next() {
    		Node<E> current = stack.peek();
    		if (current.left != null) {
    			stack.push(current.left);
    		}
    		else {
    			Node<E> temp = stack.pop();
    			while (temp.right == null) {
    				if (stack.isEmpty())
    					return current.data;
    				temp=stack.pop();
    			}
    			stack.push(temp.right);
    		}
    		return current.data;
    	}
    }
    /**
     * Returns an iterator over the elements in this tree in order of the postorder traversal.
     * @return an iterator over the elements in this tree in order of the postorder traversal
     */
	
    public Iterator<E> postorderIterator() {
    	return new  MyPostorderIterator();
    }
    /**
     * Nested MyPreorderIterator Class that implements the iterator
	 * @author Yi Yang
     *
     */
    private class MyPostorderIterator implements Iterator<E> {
    	Stack<Node<E>> stack = new Stack<Node<E>>();
    	/**
    	 * find the next leaf
    	 * @param cur-given a node
    	 */
    	private void findLeaf(Node<E> cur) {
    		while (cur != null) {
    			stack.push(cur);
    			if (cur.left != null) {
    				cur=cur.left;
    			}
    			else {
    				cur = cur.right;
    			}
    		}
    	}
    	/**
    	 * Default constructor
    	 */
    	public MyPostorderIterator() {
    		findLeaf(root);
    	}
    	/**
    	 * Determines if there is a next element
    	 */
    	@Override
    	public boolean hasNext() {
    		return !stack.isEmpty();
    	}
    	/**
    	 * returns the next element
    	 */
    	@Override
    	public E next() {
    		if (!hasNext()) {
    			throw new NoSuchElementException("All nodes visited");
    		}
    		Node<E> temp = stack.pop();
    		if (!stack.isEmpty()) {
    			Node<E> top = stack.peek();
    			if (temp == top.left) {
    				findLeaf(top.right);
    			}
    		}
    		return temp.data;
    	}
    }
    /**
     * Returns the element at the specified position in this tree. 
     * The order of the indexed elements is the same as provided by this tree's iterator. 
     * The indexing is zero based 
     * (i.e., the smallest element in this tree is at index 0 and the largest one is at index size()-1).
     * @param index - index of the element to return
     * @return the element at the specified position in this tree
     * @throws IndexOutOfBoundsException - if the index is out of range 
     * (index < 0 || index >= size())
     */
    public E get(int index) throws IndexOutOfBoundsException{
        if (index<0 || index>size()) {
        	throw new IndexOutOfBoundsException("Invalid index");
        }
        return get(root, index);
    }
    /**
     * Returns the element at the specified position in this tree. 
     * @param root
     * @param index
     * @return the element at the index
     */
    private E get(Node<E> root, int index) {
    	if (root == null) { 
    		return null;
    	}
    	int leftCount = root.left == null ? 0:root.left.getCount();
    	if (index<=leftCount) {
    		return get(root.left, index);
    	}
    	else if (index == leftCount+1) {
    		return root.data;
    	}
    	else {
    		return get(root.right, index-leftCount-1);
    	}
    }
    /**
     * Returns a collection whose elements range from fromElement, inclusive, to toElement, inclusive. 
     * The returned collection/list is backed by this tree, so changes in the returned list are reflected 
     * in this tree, and vice-versa (i.e., the two structures share elements. 
     * The returned collection should be organized according to the natural ordering of the elements (i.e., it should be sorted).
     * @param fromElement  - low endpoint (inclusive) of the returned collection
     * @param toElement - high endpoint (inclusive) of the returned collection
     * @return a collection containing a portion of this tree whose elements range from fromElement, 
     * inclusive, to toElement, inclusive
     * @throws NullPointerException  if fromElement or toElement is null
     * @throws IllegalArgumentException if fromElement is greater than toElement
     */
    public ArrayList<E> getRange(E fromElement, E toElement) throws NullPointerException, IllegalArgumentException {
    	if (fromElement==null || toElement==null)
    		throw new NullPointerException("null element");
    	if (fromElement.compareTo(toElement)>0)
    		throw new IllegalArgumentException("illegal element values");
    	//TODO
    	ArrayList<E> list = new ArrayList<E>();
    	return list;
    }
    /**
     * Returns the least element in this tree greater than or equal to the given element, 
     * or null if there is no such element.
     * @param e the value to match
     * @return the least element greater than or equal to e, or null if there is no such element
     * @throws ClassCastException - if the specified element cannot be compared with the elements currently in the set
     * @throws NullPointerException - if the specified element is null
     */

    public E ceiling(E e) throws ClassCastException, NullPointerException{
    	if (e==null)
    		throw new NullPointerException("null element");
        return ceiling(root, e);
    }
    /**
     * Returns the least element in this tree greater than or equal to the given element,
     * @param node
     * @param e
     * @return ceiling
     */
    private E ceiling (Node<E> node, E e) {
    	if (node==null) {
    		return null;
    	}
    		
    	if (node.data==e) {
    		return node.data;
    	}
    		
    	if (node.data.compareTo(e)<0) {
    		return ceiling(node.right, e);
    	}
    	E ceiling = ceiling(node.left, e);
    	return (ceiling.compareTo(e)>=0) ? ceiling : node.data;
    }
    /**
     * Returns the greatest element in this set less than or equal to the given element, 
     * or null if there is no such element.
     * This operation should be O(H).
     * @param e the value to match
     * @return the greatest element less than or equal to e, or null if there is no such element
     * @throws ClassCastException - 
     * if the specified element cannot be compared with the elements currently in the set
     * @throws NullPointerException - if the specified element is null
     */
    public E floor(E e) throws ClassCastException, NullPointerException {
    	if (e==null)
    		throw new NullPointerException("null element");
    	return floor(root, e);
    } 
    /**
     * Returns the greatest element in this set less than or equal to the given element
     * @param node
     * @param e
     * @return floor
     */
    private E floor (Node<E> node, E e) {
    	if (node==null) {
    		return null;
    	}
    	if (node.data==e) {
    		return node.data;
    	}
    	if (node.data.compareTo(e)>0) {
    		return floor(node.left, e);
    	}	
    	E floorVal = floor(root.right, e);
    	return (floorVal.compareTo(e)<=0) ? floorVal : node.data;
    }
    /**
     * Returns the first (lowest) element currently in this tree.
     * @return the first (lowest) element currently in this set.
     * @throws NoSuchElementException - if this set is empty
     */
    public E first() throws NoSuchElementException {
    	if (size()==0)
    		throw new NoSuchElementException("Empty set");
    	Node<E> current = root;
    	while (current.left!=null) {
    		current = current.left;
    	}
    	return current.data;
    }
    /**
     * Returns the last (highest) element currently in this set.
     * @return the last (highest) element currently in this set.
     * @throws NoSuchElementException - if this set is empty
     */
    public E last() throws NoSuchElementException{
    	if (size()==0)
    		throw new NoSuchElementException("Empty set");
    	Node<E> current = root;
    	while (current.right!=null) {
    		current = current.right;
    	}
    	return current.data;
    }
    /**
     * Returns the greatest element in this set strictly less than the given element, 
     * or null if there is no such element.
     * @param e the value to match
     * @return the greatest element less than e, or null if there is no such element
     * @throws ClassCastException - if the specified element cannot be compared with the elements currently in the set
     * @throws NullPointerException - if the specified element is null
     */ 
    public E lower(E e) throws ClassCastException, NullPointerException{
    	if (e==null)
    		throw new NullPointerException("null element");
    	return lower(root, e);
    }
    /**
     * Returns the greatest element in this set strictly less than the given element
     * @param node
     * @param e
     * @return lower value
     */
    private E lower (Node<E> node, E e) {
    	if (node==null) {
    		return null;
    	}
    	if (node.data==e) {
    		return node.data;
    	}
    	if (node.data.compareTo(e)>0) {
    		return lower(node.left, e);
    	}	
    	E lowerVal = lower(root.right, e);
    	return (lowerVal.compareTo(e)<0) ? lowerVal : node.data;
    }
    /**
     * Returns the least element in this tree strictly 
     * greater than the given element, or null if there is no such element.
     * @param e the value to match
     * @return the least element greater than e, or null if there is no such element
     * @throws ClassCastException - if the specified element cannot be compared with the elements currently in the set
     * @throws NullPointerException - if the specified element is null
     */
    
    public E higher(E e) throws ClassCastException, NullPointerException{
    	if (e==null)
    		throw new NullPointerException("null element");
        return higher(root, e);
    }
    /**
     * Returns the least element in this tree strictly 
     * @param node
     * @param e
     * @return higher value
     */
    private E higher (Node<E> node, E e) {
    	if (node==null) {
    		return null;
    	}
    		
    	if (node.data==e) {
    		return node.data;
    	}
    		
    	if (node.data.compareTo(e)<0) {
    		return higher(node.right, e);
    	}
    	E higherVal = higher(node.left, e);
    	return (higherVal.compareTo(e)>0) ? higherVal : node.data;
    }
	/**
	 * Returns a shallow copy of this tree instance 
	 * (i.e., the elements themselves are not cloned but the nodes are)
	 * @return a shallow copy of tree
	 */
    public BST<E> clone() {
    	BST<E> tree1 = new BST<E>();
    	BST<E> newTree = (BST<E>) tree1.clone();
    	return newTree;
    }
    /**
     * Compares the specified object with this tree for equality. Returns true if the given object is also a tree, 
     * the two trees have the same size, and every member of the given tree is contained in this tree.
     * @param obj - object to be compared for equality with this tree
     * @return true if equals
     */
    @Override
    public boolean equals(Object obj) {
		return equals(root, obj);
	 }
    /**
     * Compares the specified object with this tree for equality.
     * @param node
     * @param obj
     * @return true if equal
     */
    private boolean equals(Node<E> node, Object obj) {
    	@SuppressWarnings("unchecked")
		Node<E> object = (Node<E>) obj;
    	if (node==object) {
    		return true;
    	}
    	if (node == null || obj==null) {
    		return false;
    	}
    	return node.data.equals(obj) && node.left.data.equals(object.left.data) && 
    			node.right.data.equals(object.right.data);
    }
    
    /**Returns a string representation of this tree. The string representation consists of a list of the tree's elements in the order they are returned by its iterator (inorder traversal), enclosed in square brackets ("[]"). Adjacent elements are separated by the characters ", " 
     * (comma and space). 
     * Elements are converted to strings as by String.valueOf(Object).
     * 
     * @return a string representation of this collection
     */
    @Override
    public String toString() {
    	return toString(root);
    }
    /**Returns a string representation of this collection.
     * 
     * @param root
     * @return a string representation of this collection.
     */
    private String toString(Node<E> root) {
    	StringBuilder builder = new StringBuilder();
    	if (root==null)
    		return "";
    	builder.append(toString(root.left));
    	builder.append(toString(root.right));
    	return builder.append(root.toString()).toString();
    }
    /**
     * This function returns an array containing all the elements returned by this tree's iterator, in the same order, stored in consecutive elements of the array, starting with index 0. 
     * The length of the returned array is equal to the number of elements returned by the iterator.
     * 
     * @return an array, whose runtime component type is Object, containing all of the elements in this tree
     */
    public Object[] toArray() {
		return toArray(root);
	}
    /**
     * Return an array presentation of tree
     * @param node
     * @return array
     */
    private Object[] toArray(Node<E> node) {
		List<E> list = new ArrayList<E>();
		addNodesToList(node, list);
		Object[] array = new Object[list.size()];
		for (int i=0; i<list.size(); ++i) {
			array[i] = list.get(i);
		}
		return array;
	}
    /**
     * adds nodes to a list
     * @param node
     * @param list
     */
    private void addNodesToList (Node<E> node, List<E> list) {
    	if (node == null) {
    		return;
    	}
    	addNodesToList(node.left, list);
    	list.add(node.data);
    	addNodesToList(node.right, list);
    	return;
    }
    /**
     * Produces tree like string representation of this tree.
     * @return tree like format of the tree
     */
    public String toStringTreeFormat() {
    	StringBuilder s = new StringBuilder();
    	preOrderPrint (root, 0, s);
    	return s.toString();
    }
    /**Uses preorder traversal to produce a treelike string representation of this BST
     * 
     * @param tree root of current subtree
     * @param level depth of current recursive call
     * @param output String representation of BST
     */
    private void preOrderPrint(Node<E> tree, int level, StringBuilder output) {
    	if (tree != null) {
    		String spaces = "\n";
    		if (level > 0) {
    			for (int i = 0; i < level - 1; i++)
    			spaces += " ";
    			spaces += "|--";
    			}
    			output.append(spaces);
    			output.append(tree.data);
    			preOrderPrint(tree.left, level + 1, output);
    			preOrderPrint(tree.right , level + 1, output);
    	}
    	else { 
    		String spaces = "\n";
    		if (level > 0) {
    		for (int i = 0; i < level - 1; i++)
    		spaces += " ";
    		spaces += "|--";
    		}
    		output.append(spaces);
    		output.append("null");
    	}
    }

    



	

    
    
    



    
    /**Returns the hash code data for this collection
     * 
     */
    public int hashCode() throws UnsupportedOperationException {
    	throw new UnsupportedOperationException("Unsupported");
    }
    

   
    /**Removes all of this collections elements that are also contained in the specified collection
     * @return true if all elements removed
     * 
     */
    public boolean removeAll(Collection<?> c) throws UnsupportedOperationException{
    	throw new UnsupportedOperationException("Unsupported");
    }


    /**Retains only the elements in this collection that are contained in the specified collection 
     * 
     */
    public boolean retainAll (Collection<?> c) throws UnsupportedOperationException {
    	throw new UnsupportedOperationException("Unsupported");
    }

    	

    /** Returns an array containing all of the elements in this collection; 
     * the runtime type of the returned array is that of the specified array
     * @return an array containing all of the elements in this collection
     */
    @SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		int size = size();
		if (a.length > size) {
			a[size] = null;
		}

		int i = 0;
		for (E e : this) {
			a[i] = (T) e;
			i++;
		}
		return a;
	}
}


