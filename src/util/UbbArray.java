package util;

import java.util.NoSuchElementException;

/**
 * This class represents a resizable array
 *
 * @param <T>
 */
public class UbbArray<T>
{
    /**
     * Default initial capacity.
     */
    private static Integer DEFAULT_CAPACITY = 1;

    private Object[] elements;
    private Integer size;
    private Integer capacity;

    public UbbArray() {
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
        this.elements = new Object[this.capacity];
    }

    /**
     * Inserts a new element at the end of the array
     *
     * @param obj The object to be inserted
     */
    public void add(T obj) {
        this.ensureCapacity();
        this.elements[this.size++] = obj;
    }

    /**
     *
     * @param index The position of the wanted element
     * @return Object The element at the given index
     * @throws IndexOutOfBoundsException If the index is out of range
     */
    @SuppressWarnings("unchecked")
    public T getAt(Integer index) {
        this.checkIndex(index);
        return (T) this.elements[index];
    }

    /**
     *
     * @return Integer The number of the elements from within the array
     */
    public Integer getSize() {
        return this.size;
    }

    /**
     * Checks if there is any available space and resize the internal array if case
     */
    private void ensureCapacity() {
        if (this.size.equals(this.capacity)) {
            this.resize();
        }
    }

    /**
     * Removes an element from a given position
     *
     * @param index The position of element to be removed
     * @return Object The removed element
     * @throws IndexOutOfBoundsException If index is out of range
     */
    public T removeAt(Integer index) {
        this.checkIndex(index);
        @SuppressWarnings("unchecked")
        T obj = (T) this.elements[index];
        for (int i = index; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.size--;
        return obj;
    }

    /**
     * Search for an element and returns it's position
     *
     * @param obj The element to be found
     * @return Integer the position of the element within the array
     * @throws NoSuchElementException If the element is not found
     */
    public Integer find(T obj) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(obj)) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Check is the array contains a certain element
     *
     * @param obj The element to be checked
     * @return true if the element is found
     */
    public boolean has(T obj) {
        try {
            this.find(obj);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    /**
     *
     * @param index The position of wichi the element will be inserted
     * @param obj The object to be inserted
     * @throws IndexOutOfBoundsException If the index is out of range
     */
    public void set(Integer index, T obj) {
        this.checkIndex(index);
        this.elements[index] = obj;
    }

    /**
     * Allocates a new space for the elements
     */
    private void resize() {
        this.capacity = this.capacity * 2 + 1;
        Object[] temp = new Object[this.capacity];
        for (Integer i = 0; i < this.size; i++) {
            temp[i] = this.elements[i];
        }
        this.elements = temp;
    }

    /**
     * Checks if the given index is within correct range
     *
     * @param index the index to be checked
     * @throws IndexOutOfBoundsException If index is out of range
     */
    private void checkIndex(Integer index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
