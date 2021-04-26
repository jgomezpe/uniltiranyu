package test.games.fourinrow;

public class FourInRowSlot{
    private boolean isFilled;
    private boolean isRed;
    private boolean isHighlighted;
    
    /**
     * Creates a new Connect4Slot, initially unfilled.
     * 
     * Your agent will not need to use this method.
     */
    public FourInRowSlot()
    {
        this.isFilled = false;
        this.isRed = false;
    }
    /**
     * Copies the given slot.
     * 
     * Your agent will not need to use this method.
     * 
     * @param slot the slot to copy.
     */
    public FourInRowSlot(FourInRowSlot slot)
    {
        this.isFilled = slot.getIsFilled();
        this.isRed = slot.getIsRed();
    }
    /**
     * Checks if the slot is currently filled.
     * 
     * Your agent WILL need to use this method.
     * 
     * @return true if filled, false if not.
     */
    public boolean getIsFilled()
    {
        return isFilled;
    }
    /**
     * If the slot is filled, checks if the token in the slot is red.
     * 
     * If the slot is not filled, this will still return false; so, this should only
     * be checked after checking getIsFilled().
     * 
     * Your agent WILL need to use this method.
     * 
     * @return true if the token in the slot is red, false if it is yellow.
     */
    public boolean getIsRed()
    {
        return isRed;
    }
    /**
     * If the slot is currently empty, adds a red token to it.
     * 
     * Your agent WILL need to use this method.
     */
    public void addRed()
    {
        if (!isFilled)
        {
            this.isFilled = true;
            this.isRed = true;
        }
    }
    /**
     * If the slot is currently empty, adds a yellow token to it.
     * 
     * Your agent WILL need to use this method.
     */
    public void addYellow()
    {
        if (!isFilled)
        {
            this.isFilled = true;
            this.isRed = false;
        }
    }
    
    /**
     * Checks if the slot should be highlighted because it is part of a winning move.
     * 
     * Your agent will not need to use this method.
     * 
     * @return true if the slot is highlighted, false if not.
     */
    public boolean getIsHighlighted()
    {
        return isHighlighted;
    }
    /**
     * Highlights the slot.
     * 
     * Your agent will not need to use this method.
     */
    public void highlight()
    {
        this.isHighlighted = true;
    }
    /**
     * Clears the slot.
     * 
     * Your agent will not need to use this method.
     */
    public void clear()
    {
        this.isFilled = false;
        this.isRed = false;
        this.isHighlighted = false;
    }
}