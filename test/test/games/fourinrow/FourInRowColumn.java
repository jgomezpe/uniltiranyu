package test.games.fourinrow;

public class FourInRowColumn{
	
    private FourInRowSlot[] slots;
    
    /**
     * Creates a new Connect4Column with a given height.
     * 
     * Your agent will not need to use this method.
     * 
     * @param the height of the column.
     */
    public FourInRowColumn(int height)
    {
        slots = new FourInRowSlot[height];
        for (int i = 0; i < height; i++)
        {
            slots[i] = new FourInRowSlot();
        }
    }
    /**
     * Creates a copy of the given Connect4Column.
     * 
     * Your agent will not need to use this method.
     * 
     * @param column the column to copy.
     */
    public FourInRowColumn(FourInRowColumn column)
    {
        this.slots = new FourInRowSlot[column.getRowCount()];
        for (int i = 0; i < column.getRowCount(); i++)
        {
            slots[i] = new FourInRowSlot(column.getSlot(i));
        }
    }
    /**
     * Returns a single Connect4Slot from the column.
     * 
     * Your agent WILL need to use this method.
     * 
     * @param i the Connect4Slot to retrieve.
     * @return the Connect4Slot at that index.
     */
    public FourInRowSlot getSlot(int i)
    {
        if (i < slots.length && i >= 0)
        {
            return slots[i];
        }
        else
        {
            return null;
        }
    }
    /**
     * Checks if the column is full.
     * 
     * Your agent WILL need to use this method.
     * 
     * @return true if the column is full, false otherwise.
     */
    public boolean getIsFull()
    {
        for (FourInRowSlot slot : slots)
        {
            if (!slot.getIsFilled())
            {
                return false;
            }
        }
        return true;
    }
    /**
     * Returns the number of rows in the column.
     * 
     * Your agent WILL need to use this method.
     * 
     * @return the number of rows in the column.
     */
    public int getRowCount()
    {
        return slots.length;
    }
}
