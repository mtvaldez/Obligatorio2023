package tads.BinarySearchTree;

import tads.exceptions.NodeAlreadyExists;
import tads.exceptions.NodeNotFound;
import tads.linkedlist.MyLinkedListImpl;

public class MyBSTImpl implements MyBST {

    private NodeBST root;


    public MyBSTImpl(NodeBST root) {
        this.root = root;
    }
    @Override
    public NodeBST getRoot() {
        return root;
    }
    public void setRoot(NodeBST root) {
        this.root = root;
    }


    private NodeBST findParent(Object key, NodeBST miniRoot) {

        NodeBST parent = null;


        if (root.getKey().equals(key)) {  // El parent es la raiz
            return null;
        }


        if (miniRoot.getKey().compareTo(key) > 0) {  //  Si la key de la mini root es mayor a la key buscada, el parent esta a la izquierda
            if (miniRoot.getLeftChild().getKey().equals(key)) {
                parent = miniRoot;
            } else {
                return findParent(key, miniRoot.getLeftChild());
            }
        }


        if (miniRoot.getKey().compareTo(key) < 0) {  //  Si la key de la mini root es menor a la key buscada, el parent esta a la derecha
            if (miniRoot.getRightChild().getKey().equals(key)) {
                parent = miniRoot;
            } else {
                return findParent(key, miniRoot.getRightChild());
            }
        }


        return parent;

    }
    @Override
    public NodeBST findNode(Object key, NodeBST miniRoot) {

        if (miniRoot == null) {
            return null;
        }
        if (miniRoot.getKey().compareTo(key) == 0) {     //  El compareTo devuelve 0 si son iguales
            return miniRoot;
        }
        if (miniRoot.getKey().compareTo(key) > 0) {    //  El compareTo devuelve 1 si el primero es mayor
            return findNode(key, miniRoot.getLeftChild());
        } else {    //  El caso que queda es si compareTo devuelve -1, queriendo decir que el primero es menor
            return findNode(key, miniRoot.getRightChild());
        }

    }

    private void replaceNode(NodeBST oldNode, NodeBST newNode) {

        if (oldNode.equals(root)) {
            root = newNode;
            return;
        }

        NodeBST parentDelOld = findParent(oldNode.getKey(), root);
        if (parentDelOld.getLeftChild() != null && parentDelOld.getLeftChild().getKey().equals(oldNode.getKey())) {
            parentDelOld.setLeftChild(newNode);
        } else {
            parentDelOld.setRightChild(newNode);
        }
    }

    @Override
    public Object find(Comparable key) throws NodeNotFound {

        NodeBST foundNode = findNode(key, root);

        if (foundNode != null) {
            System.out.println("Found node with key: " + foundNode.getKey() + " and data: " + foundNode.getData());
            return foundNode.getData();
        } else {
            throw new NodeNotFound("goda re cornuda 2 punto tu vija");
        }

    }


    public NodeBST findMaxLeftTree(NodeBST miniRoot) {

        MyLinkedListImpl<NodeBST> maxCandidates = inOrder();

        NodeBST max = maxCandidates.get(0);

        for (int i = 0; i < inOrder().size(); i++) {
            if (maxCandidates.get(i).getKey().equals(miniRoot.getKey())) {
                break;
            }
            if (maxCandidates.get(i).getKey().compareTo(max.getKey()) > 0) {
                max = maxCandidates.get(i);
            }
        }

        return max;

    }
    public NodeBST findMinRightTree(NodeBST miniRoot) {

        MyLinkedListImpl<NodeBST> minCandidates = inOrder();

        NodeBST min = minCandidates.get(minCandidates.size()-1);

        for (int i = inOrder().size()-1; i > 0; i--) {

            if (minCandidates.get(i).getKey().equals(miniRoot.getKey())){
                break;
            }

            if (minCandidates.get(i).getKey().compareTo(min.getKey()) < 0) {
                min = minCandidates.get(i);
            }

        }
        return min;

    }

    @Override
    public Boolean contains(Comparable key) {

        NodeBST nodoBuscado = findNode(key, root);

        if (nodoBuscado != null) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void insert(Comparable key, Object data) throws NodeAlreadyExists {

            if ( root == null ) {
                root = new NodeBST(key, data);
                return;
            }

            if (contains(key)){
                throw new NodeAlreadyExists("Nodo ya existe");
            }

            NodeBST newNode = new NodeBST(key, data);

            NodeBST miniRoot = root;

            while (true) {

                if (miniRoot.getKey().compareTo(key) > 0) {  //  Si la key de la mini root es mayor a la key buscada, el parent esta a la izquierda
                    if (miniRoot.getLeftChild() == null) {
                        miniRoot.setLeftChild(newNode);
                        break;
                    } else {
                        miniRoot = miniRoot.getLeftChild();
                    }
                }

                if (miniRoot.getKey().compareTo(key) < 0) {  //  Si la key de la mini root es menor a la key buscada, el parent esta a la derecha
                    if (miniRoot.getRightChild() == null) {
                        miniRoot.setRightChild(newNode);
                        break;
                    } else {
                        miniRoot = miniRoot.getRightChild();
                    }
                }

            }

    }


    @Override
    public void delete(Comparable key) throws NodeNotFound {

        NodeBST aBorrar = findNode(key, root);

        if (aBorrar == null) {
            throw new NodeNotFound("Nodo buscado no existe");
        }


        if (aBorrar.getLeftChild() != null && aBorrar.getRightChild() != null) {

            NodeBST maxLeftTree = findMaxLeftTree(aBorrar);

            delete(maxLeftTree.getKey());

            aBorrar.setKey(maxLeftTree.getKey());

            aBorrar.setData(maxLeftTree.getData());

        } else if (aBorrar.getLeftChild() != null && aBorrar.getRightChild() == null) {

            NodeBST leftChild = aBorrar.getLeftChild();
            if (aBorrar == root)
                root = leftChild;
            else
                replaceNode(aBorrar, leftChild);

        } else if (aBorrar.getLeftChild() == null && aBorrar.getRightChild() != null) {

            NodeBST rightChild = aBorrar.getRightChild();
            if (aBorrar == root)
                root = rightChild;
            else
                replaceNode(aBorrar, rightChild);

        }else if (aBorrar.getLeftChild() == null && aBorrar.getRightChild() == null && aBorrar.equals(root)) {

            root = null;

        }else if (aBorrar.getLeftChild() == null && aBorrar.getRightChild() == null){

            NodeBST parent = findParent(aBorrar.getKey(), root);

            if (parent.getLeftChild() != null && parent.getLeftChild().getKey().equals(aBorrar.getKey())){
                parent.setLeftChild(null);
            } else{
                parent.setRightChild(null);
            }

        }

    }

    @Override
    public void size() {
        System.out.println("El arbol tiene " + sizeAux(root) + " nodos.");
    }
    @Override
    public int sized(){
        return sizeAux(root);
    }
    private int sizeAux(NodeBST palo){
        if (palo == null){
            return 0;
        } else {
            return 1 + sizeAux(palo.getLeftChild()) + sizeAux(palo.getRightChild());
        }
    }   // Cuenta los nodos del arbol y devuelve el numero.
    @Override
    public MyBSTImpl clone(){
        MyBSTImpl clon = new MyBSTImpl(null);
        clon.root = cloneAux(root);
        return clon;
    }
    public NodeBST cloneAux(NodeBST palo){
        if (palo == null){
            return null;
        } else {
            NodeBST clon = new NodeBST(palo.getKey(), palo.getData());
            clon.setLeftChild(cloneAux(palo.getLeftChild()));
            clon.setRightChild(cloneAux(palo.getRightChild()));
            return clon;
        }
    }   // Clona el arbol y devuelve el clon.

    @Override
    public NodeBST getMax() {
        NodeBST nodoMax = root;
        while (nodoMax.getRightChild() != null){
            nodoMax = nodoMax.getRightChild();
        }
        return nodoMax.getRightChild();
    }
    @Override
    public MyLinkedListImpl preOrder() {

        MyLinkedListImpl<NodeBST> listaReLoca = new MyLinkedListImpl<>();

        preOrderAux(this.root, listaReLoca);

        return listaReLoca;
    }
    private void preOrderAux(NodeBST dairyco, MyLinkedListImpl listaReLoca) {

        if (dairyco == null)
            return;

        listaReLoca.add(dairyco);

        // Recursively traverse the left subtree
        preOrderAux(dairyco.getLeftChild(), listaReLoca);

        // Recursively traverse the right subtree
        preOrderAux(dairyco.getRightChild(), listaReLoca);

    }


    @Override
    public MyLinkedListImpl<NodeBST> postOrder() {

        MyLinkedListImpl<NodeBST> listaEstupefaciente = new MyLinkedListImpl<>();

        postOrderAux(this.root, listaEstupefaciente);

        return listaEstupefaciente;
    }
    private void postOrderAux(NodeBST dairyco, MyLinkedListImpl listaEstupefaciente) {

        if (dairyco == null)
            return;

        // Recursively traverse the left subtree
        postOrderAux(dairyco.getLeftChild(), listaEstupefaciente);

        // Recursively traverse the right subtree
        postOrderAux(dairyco.getRightChild(), listaEstupefaciente);

        listaEstupefaciente.add(dairyco);

    }


    @Override
    public MyLinkedListImpl inOrder() {

        MyLinkedListImpl<NodeBST> listaAnalistica = new MyLinkedListImpl<>();

        inOrderAux(this.root, listaAnalistica);

        return listaAnalistica;
    }
    private void inOrderAux(NodeBST dairyco, MyLinkedListImpl listation) {

        if (dairyco == null)
            return;

        // Recursively traverse the left subtree
        inOrderAux(dairyco.getLeftChild(), listation);

        listation.add(dairyco);

        // Recursively traverse the right subtree
        inOrderAux(dairyco.getRightChild(), listation);

    }

}
