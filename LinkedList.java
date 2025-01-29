public class LinkedList {
    
    private Node first; // pointer to the first element of this list
    private Node last;  // pointer to the last element of this list
    private int size;   // number of elements in this list
    
    public LinkedList () {
        first = null;
        last = null;
        size = 0;
    }
    
    public Node getFirst() {
        return this.first;
    }

    public Node getLast() {
        return this.last;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index must be between 0 and size");
        }
        Node current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
    
    public void add(int index, MemoryBlock block) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index must be between 0 and size");
        }
        Node newNode = new Node(block);
        if (index == 0) {
            addFirst(block);
        } else if (index == size) {
            addLast(block);
        } else {
            Node prev = getNode(index - 1);
            newNode.next = prev.next;
            prev.next = newNode;
            size++;
        }
    }

    public void addLast(MemoryBlock block) {
        Node newNode = new Node(block);
        if (size == 0) {
            first = last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }
    
    public void addFirst(MemoryBlock block) {
        Node newNode = new Node(block);
        if (size == 0) {
            first = last = newNode;
        } else {
            newNode.next = first;
            first = newNode;
        }
        size++;
    }

    public void remove(MemoryBlock block) {
		if (block == null) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}
		int index = indexOf(block);
		if (index == -1) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}
		remove(index);
	}
	
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}
		remove(getNode(index));
	}

    public void remove(Node node) {
		if (node == null) {
			throw new NullPointerException("Null node cannot be removed!");
		}
		if (size == 0) {
			return;
		}
		if (node == first) {
			first = first.next;
			if (size == 1) {
				last = null;
			}
		} else {
			Node prev = first;
			while (prev.next != null && prev.next != node) {
				prev = prev.next;
			}
			if (prev.next == null) {
				return; // אם לא נמצא
			}
			prev.next = node.next;
			if (node == last) {
				last = prev;
			}
		}
		size--;
	}

    public void remove(MemoryBlock block) {
        int index = indexOf(block);
        if (index == -1) {
            throw new IllegalArgumentException("Memory block not found in list");
        }
        remove(index);
    }    

    public ListIterator iterator(){
        return new ListIterator(first);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node current = first;
        while (current != null) {
            sb.append(current.block.toString()).append(" -> ");
            current = current.next;
        }
        sb.append("null");
        return sb.toString();
    }
}