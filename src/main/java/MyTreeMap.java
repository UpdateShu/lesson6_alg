import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MyTreeMap<K extends Comparable<K>, V> {
    public int size = 0;
    private Node root;
    private Node lastNode = null;

    private class Node {

        K key;
        V value;
        Node left;
        Node right;
        int level;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.level = 0;
            size++;
        }

        public int getHeight() {
            if (left == null) {
                if (right == null) {
                    return 0;
                }
                else {
                    return right.getHeight() + 1;
                }
            }
            else {
                if (right == null) {
                    return left.getHeight() + 1;
                }
                else {
                    return Math.max(right.getHeight(), left.getHeight()) + 1;
                }
            }
        }

        public boolean isBalanced() {
            boolean leftIsBalanced = left != null ? left.isBalanced() : true;
            boolean rightIsBalanced = right != null ? right.isBalanced() : true;
            if (leftIsBalanced && rightIsBalanced) {
                int leftHeight = left != null ? left.getHeight() : 0;
                int rightHeight = right != null ? right.getHeight() : 0;
                return Math.abs(rightHeight - leftHeight) <= 1;
            }
            else
                return false;
        }
    }

    public int getHeight() {
        if (root == null)
            return 0;
        return root.getHeight();
    }

    public int getLevel() {
        if (root == null)
            return 0;
        if (root.left != null) {
            if (root.right != null) {
                return Math.max(root.left.level, root.right.level) + 1;
            }
            else {
                return root.left.level + 1;
            }
        }
        else {
            if (root.right != null) {
                return root.right.level + 1;
            }
        }
        return root.level;
    }

    public int lastLevel() {
        return lastNode != null ? lastNode.level : 0;
    }

    public boolean isBalanced() {
        if (root == null)
            return true;
        return root.isBalanced();
    }

    public boolean isEmpty() {
        return root == null;
    }

    private void checkKeyNotNull(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key null");
        }
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    public V get(K key) {
        checkKeyNotNull(key);
        return get(root, key);
    }

    private V get(Node node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node.value;
        } else if (cmp < 0) {
            return get(node.left, key);
        } else {
            return get(node.right, key);
        }
    }

    public void put(K key, V value) {
        checkKeyNotNull(key);
        if (value == null) {
            // delete(key)
            return;
        }
        root = put(root, key, value);
    }

    private Node put(Node node, K key, V value) {

        if (node == null) {
            lastNode = new Node(key, value);
            return lastNode;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
        } else if (cmp < 0) {
            node.left = put(node.left, key, value);
            node.left.level = node.level + 1;
        } else {
            node.right = put(node.right, key, value);
            node.right.level = node.level + 1;
        }
        return node;
    }

    public K minKey() {
        return min(root).key;
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        deleteMin(root);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        return node;
    }

    public void deleteLastNode() {
        if (lastNode == null)
            return;
        delete(lastNode.key);
    }

    public void delete(K key) {
        checkKeyNotNull(key);
        root = delete(root, key);
    }

    private Node delete(Node node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            size--;
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            Node temp = node;
            node = min(node.right);
            node.right = deleteMin(temp.right);
            node.left = temp.left;
        }
        return node;
    }

    @Override
    public String toString() {
        if (root == null)
            return "Дерево не содержит узлов";
        StringBuilder str = new StringBuilder();
        List<String> formatNodes = new ArrayList<>();
        addFormatNode(root, formatNodes, true);
        for (String formatNode : formatNodes)
            str.append(formatNode + "\n");
        return str.toString();
    }

    void addFormatNode(Node node, List<String> formatNodes, boolean isRight) {
        StringBuilder formatStr = new StringBuilder(isRight ? "\\>" : "/>");
        if (node == null) {
            return;
        }
        for (int i = 0; i < node.level; i++)
            formatStr.insert(0, "     ");
        formatStr.append("[" + node.key.toString() + "] " + node.value + " (" + node.getHeight() + ")");
        formatNodes.add(formatStr.toString());
        addFormatNode(node.right, formatNodes, true);
        addFormatNode(node.left, formatNodes, false);
    }
}