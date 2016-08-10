import java.util.Random;

/*
* COMP 1006 Assignment 5 Problem 3
* Esra'a Saleh 101014397 9/8/2016
* Description: This class is for making a Seeker player who goes towards the opposing team's flag, then stops there.
* Compilation and usage: use Dr. Java to compile this program along with Player, Entity, and Field and then run
* CaptureTheFlag
* Examples: not applicable, not a program
* */
public class Seeker extends Player {
    public Seeker(Field f, int side, String name, int number, String team, char symbol, double x, double y) {
        super(f, side, name, number, team, symbol, x, y);
        this.speedX = Math.random()*MAX_SPEED-2;
        this.speedY = Math.random()*MAX_SPEED-2;
        Random randBool=new Random();

        if(randBool.nextBoolean()){
            this.speedX=-this.speedX;
        }
        if(randBool.nextBoolean()){
            this.speedY=-this.speedY;
        }
    }

    @Override
    protected void handleBoundsException(Field field) {
        if(this.getX()+this.getSprite().getWidth()>field.maxX) {
            this.setX(field.maxX-this.getSprite().getWidth(), this.id);


        }
        else if(this.getX()<field.minX){
            this.setX(field.minX, this.id);


        }
        if(this.getY()+this.getSprite().getHeight()>field.maxY){
            this.setY(field.maxY-this.getSprite().getHeight(), this.id);


        }else if(this.getY()< field.minY){
            this.setY(field.minY,this.id);


        }


    }

    @Override
    public void play(Field field) {

            if (pickUpFlag(field) == false) {

                //using pythagorean theorem
                double oldSpeed = Math.sqrt(Math.pow(this.getSpeedX(), 2) + Math.pow(this.getSpeedY(), 2));
                int[] flagPos = new int[2];
                if (this.team.equals("reds")) {
                    flagPos = field.getFlag1Position();


                } else if (this.team.equals("blues")) {
                    flagPos = field.getFlag2Position();

                }

                if (flagPos[1] - this.getY() == 0) {

                    this.setY(this.getY() + 1, this.id);
                }
                double angleOfSpeed = Math.atan2(flagPos[0] - this.getX() , flagPos[1] - this.getY());

                if (angleOfSpeed == 0) {

                    angleOfSpeed = 0.01;
                }
                this.setSpeedX(Math.sin(angleOfSpeed) * oldSpeed, this.id);
                this.setSpeedY(Math.cos(angleOfSpeed) * oldSpeed, this.id);



            } else if(pickUpFlag(field) == true){
             
                this.setSpeedX(0, this.id);
                this.setSpeedY(0, this.id);
            }

    }

    @Override
    public void update(Field field) {

    }
}
