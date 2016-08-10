public class BaseDefenders extends Player{
  protected static int tracker = 0;
  protected double prevSpeedX;
  protected double prevSpeedY;
  protected double prevTrackersX;
  protected boolean opponentBase;
  
  
   public BaseDefenders(Field f, int side, String name, int number, String team, char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = Math.random()*10-5;
    this.speedY = Math.random()*10-5;
    this.prevSpeedX = this.speedX;
    this.prevSpeedY = this.speedY;
    
  }
   
    @Override
    public void play(Field field){
      double targetX, targetY, rotation;
      
      if((this.y <= field.minY)||(this.y >= field.maxY - sprite.getHeight())){
        this.speedY *= -1;
      }
      if((this.x <= field.minX)||(this.x + this.speedX >= (field.maxX/2) - (sprite.getWidth()/2))){
        this.speedX *= -1; 
      }
      
      if((field.getTeam.get(this)== 1)&&(!field.team2.isEmpty())){
        
//        for(int i = 0; i<field.getTeam2().size() - 1; i+=1){
//          if(field.getTeam2().get(i).getX() <= 405){
//            tracker = i;
//            opponentBase = true;
          
        
        
        if(field.getTeam2().get(tracker).getX() <= 405){
          targetX = field.team2.get(tracker).getX() - this.getX();
          targetY = field.team2.get(tracker).getY() - this.getY();
          rotation = Math.atan2(targetY, targetX)*180/Math.PI; 
          
          this.speedX = this.MAX_SPEED *(90 - Math.abs(rotation))/90;
          if(rotation < 0){
            this.speedY = -(this.MAX_SPEED) + (Math.abs(this.speedX));
          }
          else{
            this.speedY = this.MAX_SPEED - Math.abs(this.speedX);
          }
          
          if(this.catchOpponent((Player)field.team2.get(tracker),field)){
          this.speedX = field.team2.get(tracker).speedX;
          this.speedY = field.team2.get(tracker).speedY;
//          this.x = field.team2.get(tracker).x - 4;
          //this.y = field.team2.get(tracker).y - 4;
          }
          
        } else{
         tracker++; 
        }
        if(field.team2.size() == tracker){
         tracker=0; 
        }
      }
//        if((field.getTeam2().get(tracker).getX() >= 405)){
//          this.speedX = ;
//          this.speedY = 0;
//          opponentBase = false;
        }
       
      
    
    @Override
    public void update(Field field){}
    
    @Override
    protected void manageException(Field field){
      if(this.x + this.speedX <= field.minX){
        this.x = field.minX;
      }
      else if(this.y + this.speedY <= field.minY){
        this.y = field.minY;
      }
      else if(this.x + sprite.getWidth() + this.speedX >= field.maxX){
        this.x = field.maxX - sprite.getWidth();
      }
      else if(this.y + sprite.getHeight() + this.speedY >= field.maxY){
        this.y = field.maxY - sprite.getHeight();
      }
    }
    
}