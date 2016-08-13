import java.util.Random;

/*
 * COMP 1006 Assignment 5 Problem 3
 * Esra'a Saleh 101014397 9/8/2016
 * Description: This class is for making a Seeker player who goes towards the opposing team's flag, then stops there.
 * Compilation and usage: use Dr. Java to compile this program along with Player, Entity, and Field and then run
 * CaptureTheFlag
 * Examples: not applicable, not a program
 * */
public class SeekerUpdated extends Player {
    private double oldSpeed;
    private Flag flag;
    protected static int tracker1 = 0;
    protected static int tracker2 = 0;
    boolean ifInJail =false;
    //boolean variable for the seeker who is seeking to recognize that he is still seeking
    private boolean iAmSeeking;
    //static variable for other seekers to know if someone went for the flag
    protected static boolean redIsSeekingFlag =false;
    protected static boolean blueIsSeekingFlag =false;

    public SeekerUpdated(Field f, int side, String name, int number, String team, char symbol, double x, double y) {
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
        iAmSeeking=false;

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

        double angleOfSpeed = Math.atan2(destinationX - this.getX() , destinationY - this.getY());
        this.setSpeedX(Math.sin(angleOfSpeed) * oldSpeed, this.id);
        this.setSpeedY(Math.cos(angleOfSpeed) * oldSpeed, this.id);
    }


    @Override
    public void play(Field field) {
        boolean someOneIsSeekingFlag=false;
        int[] flagPos = new int[2];
        int[] mybase = new int[2];
        String teamColour = "";

        if (this.getTeam().equals("reds")) {
            flagPos = field.getFlag1Position();
            mybase = field.getBase2Position();

            teamColour = "reds";
            someOneIsSeekingFlag= redIsSeekingFlag;


        } else if (this.getTeam().equals("blues")) {
            flagPos = field.getFlag2Position();
            mybase = field.getBase1Position();
            teamColour = "blues";
            someOneIsSeekingFlag= blueIsSeekingFlag;
        }
        //second time for same seeking player will not go to flag
        //if no one is seeking the flag or this seeker is the one seeking

       if(!someOneIsSeekingFlag || (this.iAmSeeking) ){
            double targetX, targetY, rotation;
           //problem here
           if (this.getTeam().equals("reds")) {redIsSeekingFlag=true;}else{blueIsSeekingFlag=true;}
            someOneIsSeekingFlag=true;

           this.iAmSeeking=true;
            //TODO: when there is more than one seeker, let it know if another seeker went to grab the flag
            //TODO: if yes: roam randomly inside territory
            //TODO: if no: go for the flag

//====================================================================
            //If I have not carried the flag

            //only when null and pickup false

            if (flag != null) {
                //go to the flag
                goSomewhere(mybase[0], mybase[1]);
                // System.out.println("here 0");
                flag.speedX = this.getSpeedX();
                flag.speedY = this.getSpeedY();

                if (winGame(field, teamColour)) {
        /*  System.out.println("hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");*/
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
      /*System.out.println("*****************************************************");*/
                //in case flag is null
                //check if you can pick it up
                if (pickUpFlag(field)) {

                    if (this.getTeam().equals("reds")) {
                        this.flag = (Flag) field.flag1;

                    } else {
                        this.flag = (Flag) field.flag2;
                    }
                    this.setSpeedX(0, this.id);
                    this.setSpeedY(0, this.id);
                }
                //if you can't pick it up, go towards it
                else {
                    goSomewhere(flagPos[0], flagPos[1]);
                }
            }




//LOGIC TO PUT ACCEPT THAT THIS PLAYER HAS BEEN CAUGHT AND GO TO JAIL OPPONENTS JAIL STARTS HERE (RALTON):
            if ((field.getTeam.get(this) == 1) && (!field.team2.isEmpty())) {

                if (this.hasBeenCaught((Player) field.team2.get(tracker1), field)) {
                    //drop the flag

                    if(flag!=null) {
                        flag.speedX = 0;
                        flag.speedY = 0;

                    }
                    flag=null;
                    targetX = field.getJail2Position()[0] - this.getX();
                    targetY = field.getJail2Position()[1] - this.getY();
                    rotation = Math.atan2(targetY, targetX) * 180 / Math.PI;

                    this.speedX = this.MAX_SPEED * (90 - Math.abs(rotation)) / 90;
                    if (rotation < 0) {
                        this.speedY = -(this.MAX_SPEED) + (Math.abs(this.speedX));
                    } else {
                        this.speedY = this.MAX_SPEED - Math.abs(this.speedX);
                    }
                } else {
                    tracker1 += 1;
                    if (field.team2.size() == tracker1) {
                        tracker1 = 0;
                    }


                }

                if (this.hasBeenCaught((Player) field.team2.get(tracker1), field) && inJail2(this, field)) {
                    ifInJail = true;

                    someOneIsSeekingFlag=false;
                    blueIsSeekingFlag=false;
                    this.iAmSeeking=false;
                }

                if (ifInJail) {
                    this.setSpeedX(0, this.id);
                    this.setSpeedY(0, this.id);

                    return;
                } else {
                    return;
                }

            }

            if ((field.getTeam.get(this) == 2) && (!field.team1.isEmpty())) {

                if (this.hasBeenCaught((Player) field.team1.get(tracker2), field)) {
                    if(flag!=null) {
                        flag.speedX = 0;
                        flag.speedY = 0;

                    }
                    flag=null;
                    targetX = field.getJail1Position()[0] - this.getX();
                    targetY = field.getJail1Position()[1] - this.getY();
                    rotation = Math.atan2(targetY, targetX) * 180 / Math.PI;

                    this.speedX = this.MAX_SPEED * (90 - Math.abs(rotation)) / 90;
                    if (rotation < 0) {
                        this.speedY = -(this.MAX_SPEED) + (Math.abs(this.speedX));
                    } else {
                        this.speedY = this.MAX_SPEED - Math.abs(this.speedX);
                    }
                } else {
                    tracker2 += 1;
                    if (field.team1.size() == tracker2) {
                        tracker2 = 0;
                    }
                }
            }

            if (this.hasBeenCaught((Player) field.team1.get(tracker2), field) && inJail1(this, field)) {
                ifInJail = true;
                someOneIsSeekingFlag=false;
                redIsSeekingFlag=false;
                this.iAmSeeking=false;
            }

            if (ifInJail) {
                this.setSpeedX(0, this.id);
                this.setSpeedY(0, this.id);


                return;
            } else {
                return;
            }

        }

//LOGIC ENDS HERE (RALTON)
        else if(someOneIsSeekingFlag){
            //roam in territory

           if((this.y <= field.minY)||(this.y >= field.maxY - sprite.getHeight())){
               this.speedY *= -1;
           }
           if((this.x <= field.minX)||(this.x + this.speedX >= (field.maxX/2) - sprite.getWidth()/2)){
               this.speedX *= -1;
           }
       }
       //update booleans


    }

    @Override
    public void update(Field field) {

    }
}