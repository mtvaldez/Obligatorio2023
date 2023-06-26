package tads.linkedlist;

public interface MyList<T> {

    void add(T value);

    T get(int position);

    boolean contains(T value);

    void remove(T value);

    int size();
    int indexOf(T value);
    MyLinkedListImpl clone();
    void addAll(T[] list);

}
