public class AVLNode<T> extends BinaryNode<T> {
    int height;

    public AVLNode(){
        height = -1;
    }
    public AVLNode(T data){
        super(data);
        height = 0;
    }
    public AVLNode(T data, AVLNode<T> leftNode, AVLNode<T> rightNode){
        super(data, leftNode, rightNode);
        height = 1 + Math.max(leftNode.getHeight(), rightNode.getHeight());
    }
    public void setLeftChild(AVLNode newLeftChild){
        super.setLeftChild(newLeftChild);
        height = getHeight();
    }
    public void setRightChild(AVLNode newRightChild){
        super.setRightChild(newRightChild);
        height = getHeight();
    }
    public int getHeight(){
        return height = super.getHeight();
    }


}
