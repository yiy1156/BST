# BST
An implementation of Binary Search Tree
This project implements a binary search tree class based on a given specification. The specification is similar to the one for
TreeSet class provided by Java libraries.

Nodes
The program should provide and use a nested class1
that provides nodes for your list. The details of the implementation of that class are
up to you, but this class should be private and static:
private static class Node <E>
  
Iterator
The BST<E> class implements Iterable<E> interface. This means that its iterator() method needs to return an instance of
a class that implements the Iterator<E> interface. The iterator() method should return an iterator instance that accesses the
values in the tree according to the inorder traversal of the binary search tree. The two additional methods preorderIterator() and
postOrederIterator() need to return iterators that access the values in the tree according to the preorder and postorder traversals,
respectively.
