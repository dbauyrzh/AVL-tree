import javax.swing.*;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {

    public AVLTree(){
        super();
    }
    public AVLTree(T rootEntry){
        setRootNode(new AVLNode(rootEntry));
    }

   public T add(T newEntry) {
        if(getRootNode() == null) {
            setRootNode(new AVLNode<>(newEntry));
            return null;
        } else
            return addE((AVLNode<T>) getRootNode(), newEntry);
    }

    private T addE(AVLNode<T> rootNode, T entry){
        T temp = null;
        if(entry.compareTo(rootNode.getData()) == 0) {
            temp = rootNode.getData();
            rootNode.setData(entry);
        }
        else if(entry.compareTo(rootNode.getData()) == 1){
            if(rootNode.hasRightChild()){
                addE((AVLNode<T>) rootNode.getRightChild(), entry);
            } else rootNode.setRightChild((BinaryNode<T>) new AVLNode<>(entry));
            rootNode.setRightChild((BinaryNode<T>) rebalance((AVLNode) rootNode.getRightChild()));
        }
        else if(entry.compareTo(rootNode.getData()) == -1){
            if(rootNode.hasLeftChild()){
                addE((AVLNode<T>) rootNode.getLeftChild(), entry);
            } else rootNode.setLeftChild((BinaryNode<T>) new AVLNode<>(entry));
            rootNode.setLeftChild((BinaryNode<T>) rebalance((AVLNode) rootNode.getLeftChild()));
        }
        if(rootNode.getData().compareTo(getRootData()) == 0){
            setRootNode(rebalance(rootNode));
        }
        return temp;
    }

    private AVLNode rebalance(AVLNode<T> node){
        if(node == null) return null;
        if(getHeightDif(node) > 1){
            if(getHeightDif( (AVLNode) node.getLeftChild()) >= 0){
                return rotateRight(node);
            }
            else{
                return rotateLeftRight(node);
            }
        }
        else if(getHeightDif(node) < -1){
            if(getHeightDif((AVLNode)node.getRightChild()) <= 0){
                return rotateLeft(node);
            }
            else{
                return rotateRightLeft(node);
            }
        }
        return node;
    }

    private int getHeightDif(AVLNode<T> node){
        if(node.hasRightChild() && node.hasLeftChild()){
            return node.getLeftChild().getHeight() - node.getRightChild().getHeight();
        }
        else if(node.hasLeftChild()){
            return 1 + node.getLeftChild().getHeight();
        }
        else if(node.hasRightChild()){
            return -1 - node.getRightChild().getHeight();
        }
        else return 0;
    }

    public T remove(T entry) {
        if(entry == null) return null;
        if(!hasNode(entry)) return null;
        setRootNode((BinaryNode<T>) removeE((AVLNode<T>) getRootNode(),entry));
        return entry;
    }

    private AVLNode removeE(AVLNode<T> rootNode, T entry){
        if(entry.compareTo(rootNode.getData()) < 0){
            rootNode.setLeftChild((BinaryNode<T>) removeE((AVLNode<T>) rootNode.getLeftChild(), entry));
            rootNode.setLeftChild((BinaryNode<T>) rebalance((AVLNode) rootNode.getLeftChild()));
        }
        else if(entry.compareTo(rootNode.getData()) > 0){
            rootNode.setRightChild((BinaryNode<T>) removeE((AVLNode<T>) rootNode.getRightChild(), entry));
            rootNode.setRightChild((BinaryNode<T>) rebalance((AVLNode) rootNode.getRightChild()));
        }
        else{
            if(rootNode.hasRightChild() && rootNode.hasLeftChild()){
                T largestData = findMax((AVLNode<T>) rootNode.getLeftChild());
                rootNode.setData(largestData);
                rootNode.setLeftChild((BinaryNode<T>) removeE((AVLNode<T>) rootNode.getLeftChild(), largestData));
                rootNode.setLeftChild((BinaryNode<T>) rebalance((AVLNode<T>) rootNode.getLeftChild()));
            }
            else if(rootNode.hasLeftChild()){
                rootNode = (AVLNode<T>) rootNode.getLeftChild();
            }
            else {
                rootNode = (AVLNode<T>) rootNode.getRightChild();
            }
        }
        return rootNode;
    }

    private T findMax(AVLNode<T> node){
        while(node.hasRightChild()){
            node = (AVLNode<T>) node.getRightChild();
        }
        return node.getData();
    }
    private boolean hasNode(T entry){
        return hasNodeE((AVLNode<T>) getRootNode(), entry);
    }
    private boolean hasNodeE(AVLNode<T> rootNode, T entry){
        if(rootNode == null) return false;
        if(entry.compareTo(rootNode.getData()) < 0) {
                return hasNodeE((AVLNode<T>) rootNode.getLeftChild(), entry);
        }
        if(entry.compareTo(rootNode.getData()) > 0) {
                return hasNodeE((AVLNode<T>) rootNode.getRightChild(), entry);
        }
        return true;
    }


    private AVLNode rotateRight(AVLNode node) {
        AVLNode leftChild = (AVLNode) node.getLeftChild();
        node.setLeftChild((AVLNode) leftChild.getRightChild());
        leftChild.setRightChild(node);
        return leftChild;
    }
    private AVLNode rotateLeft(AVLNode node) {
        AVLNode rightChild = (AVLNode) node.getRightChild();
        node.setRightChild((AVLNode) rightChild.getLeftChild());
        rightChild.setLeftChild(node);
        return rightChild;
    }
    private AVLNode rotateLeftRight(AVLNode node) {
        AVLNode leftChild = (AVLNode) node.getLeftChild();
        node.setLeftChild(rotateLeft(leftChild));
        return rotateRight(node);
    }
    private AVLNode rotateRightLeft(AVLNode node) {
        AVLNode rightChild = (AVLNode) node.getRightChild();
        node.setRightChild(rotateRight(rightChild));
        return rotateLeft(node);
    }
}
