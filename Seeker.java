
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
    private double oldSpeed;
    private Flag flag;

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
        oldSpeed = Math.sqrt(Math.pow(this.getSpeedX(), 2) + Math.pow(this.getSpeedY(), 2));

        this.flag=null;
    }

    @Override
    protected void manageException(Field field) {
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
    private void goSomewhere(int destinationX, int destinationY){



//            if (flagPos[1] - this.getY() == 0) {
//
//                this.setY(this.getY() + 1, this.id);
//            }
        double angleOfSpeed = Math.atan2(destinationX - this.getX() , destinationY - this.getY());

//            if (angleOfSpeed == 0) {
//
//                angleOfSpeed = 0.01;
//            }
        this.setSpeedX(Math.sin(angleOfSpeed) * oldSpeed, this.id);
        this.setSpeedY(Math.cos(angleOfSpeed) * oldSpeed, this.id);

    }


    @Override
    public void play(Field field) {


        //TODO: grab flag to base
        int[] flagPos = new int[2];
        int[] mybase = new int[2];
        String teamColour = "";

        if (this.getTeam().equals("reds")) {
            flagPos = field.getFlag1Position();
            mybase = field.getBase2Position();

            teamColour = "reds";


        } else if (this.getTeam().equals("blues")) {
            flagPos = field.getFlag2Position();
            mybase = field.getBase1Position();
            teamColour = "blues";
        }
//====================================================================
        //If I have not carried the flag

        //only when null and pickup false

        if (flag != null) {
            //go to the flag
            goSomewhere(mybase[0], mybase[1]);
            System.out.println("here 0");
            flag.speedX = this.getSpeedX();
            flag.speedY = this.getSpeedY();

            if (winGame(field, teamColour)) {
                System.out.println("hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                this.setSpeedX(0, this.id);
                this.setSpeedY(0, this.id);
                flag.speedY = 0;
                flag.speedX = 0;
                this.setX(mybase[0], this.id);
                this.setY(mybase[1], this.id);
            }
        }
        //if I have carried the flag
        else {
            System.out.println("*****************************************************");

            if (pickUpFlag(field)) {

                if (this.getTeam().equals("reds")) {
                    this.flag = (Flag) field.flag1;

                } else {
                    this.flag = (Flag) field.flag2;
                }
                this.setSpeedX(0, this.id);
                this.setSpeedY(0, this.id);
            } else {
                goSomewhere(flagPos[0], flagPos[1]);
            }


//are carrying glag, not win game yet
//            else {//if(this.getSpeedX()==0 && this.getSpeedY()==0 ||field.flag1.x==this.getX() && field.flag1.y==this.getY())  {
//                //then we need to stop momentarily at the base
//                System.out.println("here 1");
//
//                }
//
//
//            }
//            else {
//                System.out.println("here 2");
//                //then we need to go back to base
//                //====================================EXECUTING AT SOME WRONG TIMES (THOUGH AT ALL RIGHT TIMES)==============
//                this.setSpeedX(0, this.id);
//                this.setSpeedY(0, this.id);
//                this.
//
//
//            }
        }
    }



    @Override
    public void update(Field field) {

    }
}