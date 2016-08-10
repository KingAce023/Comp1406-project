/* COMP 1406, Assignment 5, Seeker Player.                            *
 * Osepiribo West, 100977921, 7th August, 2016.                       *
 *                                                                    *
 * Description: A random walker will continue to walk in the playing  *
 *              field and bounce off the borders so that it remains   *
 *              in the playing field.                                 */


public class RandomWalker extends Player{
  
  public RandomWalker(Field f, int side, String name, int number, String team, char symbol, double x, double y){
   
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = Math.random()*10-2;
    this.speedY = Math.random()*10-2;
    
  }
  
  
  public  void update(Field field){
    
    /* Purpose:            Updates any changes made to a player's 
     *                     position by another player.
     * 
     * Input parameter(s): type and order respectively = Field.
     * 
     * Return type:        void.
     * 
     * Precondition:       input parameter field must be of class type
     *                     Field.
     * 
     * Postcondition:      None.
     * 
     * Side effect:        None.
     */
    
  }
  
  
  public void play(Field field){
    
    /* Purpose:            Sets the player's speed in the opposite
     *                     direction when it reaches the border of
     *                     the playing field so that it bounces off 
     *                     the playing field.
     * 
     * Input parameter(s): type and order respectively = Field.
     * 
     * Return type:        void.
     * 
     * Precondition:       input parameter field must be of class type
     *                     Field.
     * 
     * Postcondition:      None.
     * 
     * Side effect:        Changes the direction of player's speed when 
     *                     it gets to the border of the playing field
     */
    
    if(this.x + this.sprite.getWidth() >= field.maxX ||
       this.x <= field.minX){
      
     this.setSpeedX(-speedX, this.id);
     
    }
    
    if(this.y + this.sprite.getWidth() >= field.maxY ||
       this.y <= field.minY){
      
     this.setSpeedY(-speedY, this.id);
     
    }
    
  }
  
  
  @Override
  protected void manageException(Field field){
    
    /* Description:        Where it is created or declared: This method
     *                     is declared in class Player as an abstract method.
     * 
     *                     Where it is called: It is called in class Field 
     *                     in the update() method.
     * 
     *                     Why it is created: This method is created to handle
     *                     the EntityOutOfBoundsException in the update()
     *                     method located in class field. It updates the 
     *                     player's position if it moves out of the field.
     *                     The EntityOutOfBoundsException was not handled
     *                     in the update() method in this RandomWalker class for the
     *                     reason being that the update() method updates player's
     *                     position if another player causes a change in it's
     *                     position. E.g. a player from another team, catches this
     *                     player which makes the player go to jail, update() 
     *                     will update this player's position so that it goes to jail.
     *                     This manageException method updates player's position 
     *                     only when player goes out of the border.
     *
     * Purpose:            Sets the player's position to the border if
     *                     player eventually leaves the playing 
     *                     field (border).
     * 
     * Input parameter(s): type and order respectively = Field.
     * 
     * Return type:        void.
     * 
     * Precondition:       input parameter field must be of class type
     *                     Field.
     * 
     * Postcondition:      None.
     * 
     * Side effect:        Fixes the player's position by putting it back
     *                     in the field if it gets out.
     */
    
    if(this.x + this.sprite.getWidth() > field.maxX){
      this.setX(field.maxX - this.sprite.getWidth(), this.id);
      
    }
    
    if(this.x < field.minX){
      this.setX(field.minX, this.id);
      
    }
    
    if(this.y + this.sprite.getWidth() > field.maxY){
      this.setY(field.maxY - this.sprite.getWidth(), this.id);
      
    }
    
    if(this.y < field.minY){
      this.setY(field.minY, this.id);
      
    }
    
  }
  
  
  
}