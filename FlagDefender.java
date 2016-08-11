import java.util.ArrayList;

public class FlagDefender extends Player{
  
  private static int player1 = 0;
  private static int player2 = 0;
//  private static boolean gone = false;
  Player chasingPlayer;
  Player playerWithFlag;
  ArrayList<Entity> chasingPlayerTeam; 
  ArrayList<Entity> opposingPlayerTeam; 
  
  public FlagDefender(Field f, int side, String name, int number, String team, char symbol, double x, double y){
    
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = Math.random()*10-2;
    this.speedY = Math.random()*10-2;
  }
    
    @Override
  public  void update(Field field){
  }
    
    
    @Override
  public void play(Field field){
      
      boolean gone = false;
      
      chasingPlayer = (Player)field.getTeam1().get(player1);
      playerWithFlag = (Player)field.getTeam2().get(player2);
      
      chasingPlayerTeam = field.getTeam1();
      opposingPlayerTeam = field.getTeam2();
      
      if(field.pickUpFlag((Player)field.getTeam1().get(player1))){
        chasingPlayer = (Player)field.getTeam2().get(player1);
        playerWithFlag = (Player)field.getTeam1().get(player2);
        
        chasingPlayerTeam = field.getTeam2();
        opposingPlayerTeam = field.getTeam1();
      }
      
      if(field.pickUpFlag(playerWithFlag)){
        
        if(chasingPlayer instanceof FlagDefender){
          
          for(int i=0; i<chasingPlayerTeam.size(); i++){
            if(goingToCatch((Player)chasingPlayerTeam.get(i), playerWithFlag)){
              player1++;
              gone = true;
              break;
            }
          }
          if(gone == false){
            
            double xOfOpponent = playerWithFlag.getX();
            double yOfOpponent = playerWithFlag.getY();
            
            double xOfChaser = chasingPlayer.x;
            double yOfChaser = chasingPlayer.y;
    
            double xDifference = xOfOpponent - xOfChaser;
            double yDifference = yOfOpponent - yOfChaser;
      
            double xSpeed = Math.abs(Math.cos(Math.atan(yDifference / xDifference))) * MAX_SPEED;
            double ySpeed = Math.abs(Math.sin(Math.atan(yDifference / xDifference))) * MAX_SPEED;
    
            if(xOfOpponent < chasingPlayer.x){
              chasingPlayer.speedX = -xSpeed;
            }
            if(xOfOpponent > chasingPlayer.x){
              chasingPlayer.speedX = xSpeed;
            }
      
            if(yOfOpponent < chasingPlayer.y){
              chasingPlayer.speedY = -ySpeed;
            }
            if(yOfOpponent > chasingPlayer.y){
              chasingPlayer.speedY = ySpeed;
            }
            
            chasingPlayer.setSpeedX(chasingPlayer.speedX, this.id);
            chasingPlayer.setSpeedY(chasingPlayer.speedY, this.id);
            
            if(chasingPlayer.catchOpponent(playerWithFlag, field)){
            chasingPlayer.setSpeedX(playerWithFlag.getSpeedX(), this.id);
            chasingPlayer.setSpeedY(playerWithFlag.getSpeedY(), this.id);
            }
            player1++;
            
            
          }
        }
        else{
          player1++;
      }
      
      if(chasingPlayerTeam.size() == player1){
        player1 = 0;
      }
        
      }
      else{
        player2++;
      }
      
      if(opposingPlayerTeam.size() == player2){
        player2 = 0;
      }
      
    }
    
    
    @Override
  protected void manageException(Field field){
    
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