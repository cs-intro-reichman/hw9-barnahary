/**
 * Represents a managed memory space. The memory space manages a list of allocated 
 * memory blocks, and a list free memory blocks. The methods "malloc" and "free" are 
 * used, respectively, for creating new blocks and recycling existing blocks.
 */
public class MemorySpace {
    
    // A list of the memory blocks that are presently allocated
    private LinkedList allocatedList;

    // A list of memory blocks that are presently free
    private LinkedList freeList;

    /**
     * Constructs a new managed memory space of a given maximal size.
     * 
     * @param maxSize
     *            the size of the memory space to be managed
     */
    public MemorySpace(int maxSize) {
        // initiallizes an empty list of allocated blocks.
        allocatedList = new LinkedList();
        // Initializes a free list containing a single block which represents
        // the entire memory. The base address of this single initial block is
        // zero, and its length is the given memory size.
        freeList = new LinkedList();
        freeList.addLast(new MemoryBlock(0, maxSize));
    }

    /**
     * Allocates a memory block of a requested length (in words). Returns the
     * base address of the allocated block, or -1 if unable to allocate.
     */
    public int malloc(int length) {        
        Node current = freeList.getFirst();
        while (current != null) {
            MemoryBlock freeBlock = current.block;
            if (freeBlock.length >= length) {
                int allocatedAddress = freeBlock.baseAddress;
                MemoryBlock allocatedBlock = new MemoryBlock(allocatedAddress, length);
                allocatedList.addLast(allocatedBlock);
                
                if (freeBlock.length == length) {
                    freeList.remove(current);
                } else {
                    freeBlock.baseAddress += length;
                    freeBlock.length -= length;
                }
                return allocatedAddress;
            }
            current = current.next;
        }
        return -1;
    }

    /**
     * Frees the memory block whose base address equals the given address.
     */
    public void free(int address) {
        Node current = allocatedList.getFirst();
        while (current != null) {
            if (current.block.baseAddress == address) {
                freeList.addLast(current.block);
                allocatedList.remove(current);
                return;
            }
            current = current.next;
        }
        throw new IllegalArgumentException("Address not found in allocated list.");
    }
    
    /**
     * A textual representation of the free list and the allocated list of this memory space.
     */
    public String toString() {
        return freeList.toString() + "\n" + allocatedList.toString();        
    }
    
    /**
     * Performs defragmentation of this memory space.
     */
    public void defrag() {
        Node current = freeList.getFirst();
        while (current != null && current.next != null) {
            MemoryBlock block1 = current.block;
            MemoryBlock block2 = current.next.block;
            if (block1.baseAddress + block1.length == block2.baseAddress) {
                block1.length += block2.length;
                freeList.remove(current.next);
            } else {
                current = current.next;
            }
        }
    }
}
