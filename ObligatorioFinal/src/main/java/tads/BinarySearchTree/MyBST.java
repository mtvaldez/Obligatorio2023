package tads.BinarySearchTree;

import tads.exceptions.NodeAlreadyExists;
import tads.exceptions.NodeNotFound;
import tads.linkedlist.MyLinkedListImpl;

public interface MyBST<K extends Comparable<K>, T> {
    NodeBST getRoot();
    T find(K key) throws NodeNotFound;
    void insert (K key, T data) throws NodeAlreadyExists;
    void delete (K key) throws NodeNotFound;
    void size();
    MyLinkedListImpl inOrder();
    MyLinkedListImpl<NodeBST> postOrder();
    MyLinkedListImpl preOrder();
    Boolean contains(Comparable key);
    int sized();
    MyBSTImpl clone();
    NodeBST getMax();
    NodeBST findNode(Object key, NodeBST miniRoot);
}