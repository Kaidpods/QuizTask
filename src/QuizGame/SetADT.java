package QuizGame;

public interface SetADT {

    /**
     * Add an object to the set
     *
     * @param o Object to add
     */
    public void add(Object o);

    /**
     * Remove an object from the set
     *
     * @param o Object to remove
     */
    public void remove (Object o);

    /**
     * Set the set to the intersection of itself and s
     *
     * @param s Object to compare for Intersection
     */
    public void intersection (SetADT s);

    /**
     * Set the set to the difference between itself and s
     *
     * @param s Object to compare for difference
     */
    public void difference (SetADT s);

    /**
     * Checks to see if the set is empty
     *
     * @return True if set is empty, else return false
     */
    public boolean isEmpty ();

    /**
     * Returns the number of items within the set
     *
     * @return Number of items
     */
    public int size();
}
