package FTC_2019_2020_Season;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import java.util.Locale;

@Autonomous(name="Blue Autonomus", group="Auto")
public class Blue_Autonomus extends LinearOpMode {
 
 

 
     /* Declare OpMode members. */
     RobotHardware robot = new RobotHardware();
     private ElapsedTime runtime = new ElapsedTime();
     
    ColorSensor sensorColor0;
    ColorSensor sensorColor1;

  
 
     @Override
     public void runOpMode() {
      
     /*
     * Initialize the drive system variables.
     * The init() method of the hardware class does all the work here
     */
     robot.init(hardwareMap);
     
       // get a reference to the color sensor.
        sensorColor0 = hardwareMap.get(ColorSensor.class, "sensorColor0");
        sensorColor1 = hardwareMap.get(ColorSensor.class, "sensorColor1");
        // get a reference to the distance sensor that shares the same name.
       // sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_color_distance");

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues0[] = {0F, 0F, 0F};
        float hsvValues1[] = {0F, 0F, 0F};
        // values is a reference to the hsvValues array.
       

        // sometimes it helps to multiply the raw RGB values with a scale factor
        // to amplify/attentuate the measured values.
        final double SCALE_FACTOR = 255;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);


        char skystoneLocation = 'r';
        boolean position = false;


        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        telemetry.addData("Mode", "calibrating2...");
        telemetry.update();

        while (!isStopRequested() && !robot.imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }


     
     // Wait for the game to start (driver presses PLAY)
        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.addData("imu calib status", robot.imu.getCalibrationStatus().toString());
        telemetry.update();
        waitForStart();
        
 
            if (opModeIsActive()) {
              ///////////////////////////////////////////////////////////////
            /////////////////////   Move Forward    3 in  //////////////////second per inch 0.032
            //////////////////////////////////////////////////////////// 
             robot.forward();
             runtime.reset();
             while(opModeIsActive() && (runtime.seconds() <= .09)){
             }
            
                  ////////////////////////////////////////////////////////////
            /////////////////////   Reverse Movement  //////////////////second per inch 0.032
            ////////////////////////////////////////////////////////////
             robot.backward();
             runtime.reset();
             while(opModeIsActive() && (runtime.seconds() <= .001)){
             } robot.stop();
              
              sleep(500);
             
             while (opModeIsActive() && robot.ssScrew.getCurrentPosition() < 390){
                 robot.ssScrew.setPower(0.5);
             }
             
              robot.ssScrew.setPower(0);
             
             robot.ssTilt.setPosition(0.85);
             sleep(250);
             
            ///////////////////////////////////////////////////////////////
            /////////////////////   Move Forward    24 in  //////////////////second per inch 0.032
            //////////////////////////////////////////////////////////// .768 - .876
             robot.forward();
             runtime.reset();
             while(opModeIsActive() && (runtime.seconds() <= .740)){
             }
            
            ////////////////////////////////////////////////////////////
            /////////////////////   Reverse Movement  //////////////////second per inch 0.032
            ////////////////////////////////////////////////////////////
             robot.backward();
             runtime.reset();
             while(opModeIsActive() && (runtime.seconds() <= .001)){
             } robot.stop();
              
              sleep(1000);
              
             while (position != true){
            Color.RGBToHSV((int) (sensorColor0.red() * SCALE_FACTOR),
                    (int) (sensorColor0.green() * SCALE_FACTOR),
                    (int) (sensorColor0.blue() * SCALE_FACTOR),
                    hsvValues0);
            Color.RGBToHSV((int) (sensorColor1.red() * SCALE_FACTOR),
                    (int) (sensorColor1.green() * SCALE_FACTOR),
                    (int) (sensorColor1.blue() * SCALE_FACTOR),
                    hsvValues1);
                    
            telemetry.addData("Hue for color sensor 0", hsvValues0[0]);
            telemetry.addData("----First color Sensor ends here" , "----");
            telemetry.addData("Hue for sensor 1", hsvValues1[0]);
            telemetry.addData("----Second color Sensor ends here" , "----");
            telemetry.update();
            
            
            if (hsvValues1[0] > 105 && hsvValues0[0] < 105){
                telemetry.addData("Skystone is Left " , "yeet");
                    robot.left();
                    sleep(186);
                    robot.right();
                    sleep(1);
                    robot.stop();
                    skystoneLocation = 'l';
                    position = true;
            }   else if (hsvValues0[0] > 105 && hsvValues1[0] < 105){ 
                    telemetry.addData("Skystone is Middle " , "yeet");
                    robot.left();
                    sleep(218);//128
                    robot.right();
                    sleep(1);
                    robot.stop();
                    skystoneLocation = 'm';
                    position = true;
            }   else if(hsvValues0[0] > 105 && hsvValues1[0] > 105){ /// fail safe
                    robot.forward();
                    runtime.reset();
                    while(opModeIsActive() && (runtime.seconds() <= 0.032)){
                    }robot.backward();
                    runtime.reset();
                    while(opModeIsActive() && (runtime.seconds() <= 0.001)){
                    }robot.stop();
            } else if (hsvValues0[0] > 105 && hsvValues1[0] > 105){  /// fail safe
                    robot.forward();
                    runtime.reset();
                    while(opModeIsActive() && (runtime.seconds() <= 0.032)){
                    } robot.backward();
                    runtime.reset();
                    while(opModeIsActive() && (runtime.seconds() <= 0.001)){
                    }robot.stop();
            } else {
                    telemetry.addData("Skystone is Right " , "yeet");
                    robot.left();
                    sleep(624);//384+8inches
                    robot.right();
                    sleep(1);
                    robot.stop();
                    skystoneLocation = 'r';
                    position = true;
            }
            
            sleep(1000);
            
/////////////////////////////////////////////////////////////////
///////////////////////  LEFT /////////////////////////////////
//////////////////////////////////////////////////////////
            if (skystoneLocation == 'l'){
                
                telemetry.addData("Skystone is Left" , "!");
                telemetry.update();
                
            // Back Up From Blocks
            robot.backward();
            sleep(72);
            robot.stop();
            sleep(100);
            
               /// -380 for extension  encoder
             while (opModeIsActive() && robot.ssExtend.getCurrentPosition() > -230){
                 robot.ssExtend.setPower(-0.5);
             } 
             robot.ssExtend.setPower(0);
                
                /// screw to 0 encoder
             while (opModeIsActive() && robot.ssScrew.getCurrentPosition() > 0){
                 robot.ssScrew.setPower(-0.5);
             } robot.ssScrew.setPower(0);
                
               sleep(150); 
                
                 robot.ssClaw.setPosition(1);
                 sleep(500);
                 
                 ///pull toward robot
                 while (opModeIsActive() && robot.ssExtend.getCurrentPosition() < -50){
                 robot.ssExtend.setPower(0.5);
             } 
             robot.ssExtend.setPower(0);
                 sleep(500);
                
              

            
            // Spin To Foundation ////// - = right
            robot.resetAngle();
            sleep(100);
            robot.rotate(75, 0.25);
            sleep(100);
            robot.spinLeft();
            sleep(1);
            robot.stop();
            sleep(100);
            
            // Move To Foundation
            robot.forward();
            sleep(2450);
            robot.backward();
            sleep(1);
            robot.stop();
            sleep(100);

            // Spin Towards Blocks
            robot.resetAngle();
            sleep(100);
            robot.rotate(-80, 0.25);
            sleep(100);
            robot.spinLeft();
            sleep(1);
            robot.stop();
            sleep(100);
            
            // drop  block
            while (opModeIsActive() && robot.ssScrew.getCurrentPosition() < 390){
                 robot.ssScrew.setPower(0.5);
             }robot.ssScrew.setPower(0);
              
              
              
                /// -430 for extension  encoder
             while (opModeIsActive() && robot.ssExtend.getCurrentPosition() > -430){
                 robot.ssExtend.setPower(-0.5);
             }robot.ssExtend.setPower(0);
              
                // drop  block
              robot.ssClaw.setPosition(0.6);
               sleep(500); 
               
               robot.ssTilt.setPosition(0);
             sleep(250);
              
               /// 0 for extension  encoder
             while (opModeIsActive() && robot.ssExtend.getCurrentPosition() < 0){
                 robot.ssExtend.setPower(0.5);
             }robot.ssExtend.setPower(0);
              
               
            //Spin In Order To Grab
            robot.resetAngle();
            sleep(100);
            robot.rotate(75, 0.25);
            sleep(100);
            robot.spinRight();
            sleep(1);
            robot.stop();
            sleep(100);
             
            //Move To Foundation
            robot.right();
            sleep(600);
            robot.left();
            sleep(1);
            robot.stop();
            
            //Grab Foundation
            robot.hook0.setPosition(1);
            robot.hook1.setPosition(-1);
            sleep(2000);
            
            //Move Foundation Left
            robot.left(0.25);
            sleep(1500);
            
            //Ensure Grip On Foundation
            robot.hook0.setPosition(1);
            robot.hook1.setPosition(-1);
            sleep(2000);
            
            //Spin To Score
            robot.spinLeft(1);
            sleep(900);         
          
            //Move Foundation To Wall
            robot.right();
            sleep(1100);
           
            //Release Foundation
            robot.hook0.setPosition(-0.6);
            robot.hook1.setPosition(0.6);
            
          robot.left();
          sleep(500); //was 1900
           
          robot.resetAngle();
          sleep(100);
          robot.rotate(80, 0.3);
          sleep(100);
          
          robot.spinRight();
          sleep(1);
          
             robot.forward();
             sleep(30);
             robot.backward();
             sleep(1);
             robot.stop();
             
             while (opModeIsActive() && robot.ssScrew.getCurrentPosition() > 0){
                 robot.ssScrew.setPower(-0.5);
             }robot.ssScrew.setPower(0);
             
             robot.ssTilt.setPosition(0.5);
             sleep(250);
             
            robot.forward();
          sleep(670);
           robot.stop();
            }
////////////////////////////////////////////////////////////////          
////////////////////////// MIDDLE //////////////////////////////            
////////////////////////////////////////////////////////////////           
            if (skystoneLocation == 'm'){
                
                telemetry.addData("Skystone is Middle" , "!");
                telemetry.update();
                
            // Back Up From Blocks
            robot.backward();
            sleep(72);
            robot.stop();
            sleep(100);
            
             /// -380 for extension  encoder
             while (opModeIsActive() && robot.ssExtend.getCurrentPosition() > -230){
                 robot.ssExtend.setPower(-0.5);
             } 
             robot.ssExtend.setPower(0);
                
                /// screw to 0 encoder
             while (opModeIsActive() && robot.ssScrew.getCurrentPosition() > 0){
                 robot.ssScrew.setPower(-0.5);
             } robot.ssScrew.setPower(0);
                
               sleep(150); 
                
                 robot.ssClaw.setPosition(1);
                 sleep(500);
                 
                 ///pull toward robot
                 while (opModeIsActive() && robot.ssExtend.getCurrentPosition() < -50){
                 robot.ssExtend.setPower(0.5);
             } 
             robot.ssExtend.setPower(0);
                 sleep(500);
                
            
            // Spin To Foundation ////// - = right
            robot.resetAngle();
            sleep(100);
            robot.rotate(75, 0.25);
            sleep(100);
            robot.spinLeft();
            sleep(1);
            robot.stop();
            sleep(100);
            
            // Move To Foundation
            robot.forward();
            sleep(2210);//Was 2450
            robot.backward();
            sleep(1);
            robot.stop();
            sleep(100);

            // Spin Towards Blocks
            robot.resetAngle();
            sleep(100);
            robot.rotate(-80, 0.25);
            sleep(100);
            robot.spinLeft();
            sleep(1);
            robot.stop();
            sleep(100);
            
           
             // drop  block
            while (opModeIsActive() && robot.ssScrew.getCurrentPosition() < 390){
                 robot.ssScrew.setPower(0.5);
             }robot.ssScrew.setPower(0);
              
              
              
                /// -430 for extension  encoder
             while (opModeIsActive() && robot.ssExtend.getCurrentPosition() > -430){
                 robot.ssExtend.setPower(-0.5);
             }robot.ssExtend.setPower(0);
              
                // drop  block
              robot.ssClaw.setPosition(0.6);
              sleep(500); 
               
              robot.ssTilt.setPosition(0);
              sleep(250);
              
               /// 0 for extension  encoder
             while (opModeIsActive() && robot.ssExtend.getCurrentPosition() < 0){
                 robot.ssExtend.setPower(0.5);
             }robot.ssExtend.setPower(0); 
               
            //Spin In Order To Grab
            robot.resetAngle();
            sleep(100);
            robot.rotate(75, 0.25);
            sleep(100);
            robot.spinRight();
            sleep(1);
            robot.stop();
            sleep(100);
             
            //Move To Foundation
            robot.right();
            sleep(600);
            robot.left();
            sleep(1);
            robot.stop();
            
            //Grab Foundation
            robot.hook0.setPosition(1);
            robot.hook1.setPosition(-1);
            sleep(2000);
            
            //Move Foundation Left
            robot.left(0.25);
            sleep(1500);
            
            //Ensure Grip On Foundation
            robot.hook0.setPosition(1);
            robot.hook1.setPosition(-1);
            sleep(2000);
            
            //Spin To Score
            robot.spinLeft(1);
            sleep(900);         
          
            //Move Foundation To Wall
            robot.right();
            sleep(1100);
           
            //Release Foundation
            robot.hook0.setPosition(-0.6);
            robot.hook1.setPosition(0.6);
            
          robot.left();
          sleep(500); //was 1900
           
          robot.resetAngle();
          sleep(100);
          robot.rotate(80, 0.3);
          sleep(100);
          
          robot.spinRight();
          sleep(1);
          
          robot.forward();
             sleep(30);
             robot.backward();
             sleep(1);
             robot.stop();
             
          
           while (opModeIsActive() && robot.ssScrew.getCurrentPosition() > 0){
                 robot.ssScrew.setPower(-0.5);
             }robot.ssScrew.setPower(0);
             
             robot.ssTilt.setPosition(0.5);
             sleep(250);
             
            robot.forward();
          sleep(670);
           robot.stop();
            
            
            
            }
//////////////////////////////////////////////////////////////////////////////
///////////////////// RIGHT /////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////
             if (skystoneLocation == 'r'){
                 
              telemetry.addData("Skystone is Right" , "!");
                telemetry.update();
                
            // Back Up From Blocks
            robot.backward();
            sleep(72);
            robot.stop();
            sleep(100);
            
            /// -380 for extension  encoder
             while (opModeIsActive() && robot.ssExtend.getCurrentPosition() > -230){
                 robot.ssExtend.setPower(-0.5);
             } 
             robot.ssExtend.setPower(0);
                
                /// screw to 0 encoder
             while (opModeIsActive() && robot.ssScrew.getCurrentPosition() > 0){
                 robot.ssScrew.setPower(-0.5);
             } robot.ssScrew.setPower(0);
                
               sleep(150); 
                
                 robot.ssClaw.setPosition(1);
                 sleep(500);
                 
                 ///pull toward robot
                 while (opModeIsActive() && robot.ssExtend.getCurrentPosition() < -80){
                 robot.ssExtend.setPower(0.5);
             } 
             robot.ssExtend.setPower(0);
                 sleep(500);
            
            // Spin To Foundation ////// - = right
            robot.resetAngle();
            sleep(100);
            robot.rotate(75, 0.25);
            sleep(100);
            robot.spinLeft();
            sleep(1);
            robot.stop();
            sleep(100);
            
            // Move To Foundation
            robot.forward();
            sleep(2270);//2450-8*30
            robot.backward();
            sleep(1);
            robot.stop();
            sleep(100);

            // Spin Towards Blocks
            robot.resetAngle();
            sleep(100);
            robot.rotate(-80, 0.25);
            sleep(100);
            robot.spinLeft();
            sleep(1);
            robot.stop();
            sleep(100);
            
             // drop  block
            while (opModeIsActive() && robot.ssScrew.getCurrentPosition() < 390){
                 robot.ssScrew.setPower(0.5);
             }robot.ssScrew.setPower(0);
              
              
              
                /// -430 for extension  encoder
             while (opModeIsActive() && robot.ssExtend.getCurrentPosition() > -430){
                 robot.ssExtend.setPower(-0.5);
             }robot.ssExtend.setPower(0);
              
                // drop  block
              robot.ssClaw.setPosition(0.6);
               sleep(500); 
               
               robot.ssTilt.setPosition(0);
             sleep(250);
              
               /// 0 for extension  encoder
             while (opModeIsActive() && robot.ssExtend.getCurrentPosition() < 0){
                 robot.ssExtend.setPower(0.5);
             }robot.ssExtend.setPower(0); 
               
            //Spin In Order To Grab
            robot.resetAngle();
            sleep(100);
            robot.rotate(75, 0.25);
            sleep(100);
            robot.spinRight();
            sleep(1);
            robot.stop();
            sleep(100);
             
            //Move To Foundation
            robot.right();
            sleep(600);
            robot.left();
            sleep(1);
            robot.stop();
            
            //Grab Foundation
            robot.hook0.setPosition(1);
            robot.hook1.setPosition(-1);
            sleep(2000);
            
            //Move Foundation Left
            robot.left(0.25);
            sleep(1500);
            
            //Ensure Grip On Foundation
            robot.hook0.setPosition(1);
            robot.hook1.setPosition(-1);
             sleep(2000);
            
            //Spin To Score
            robot.spinLeft(1);
            sleep(900);         
          
            //Move Foundation To Wall
            robot.right();
            sleep(1100);
           
            //Release Foundation
            robot.hook0.setPosition(-0.6);
            robot.hook1.setPosition(0.6);
            
          robot.left();
          sleep(500); //was 1900
           
          robot.resetAngle();
          sleep(100);
          robot.rotate(80, 0.3);
          sleep(100);
          
          robot.spinRight();
          sleep(1);
          
          robot.forward();
             sleep(30);
             robot.backward();
             sleep(1);
             robot.stop();
             
          
           while (opModeIsActive() && robot.ssScrew.getCurrentPosition() > 0){
                 robot.ssScrew.setPower(-0.5);
             }robot.ssScrew.setPower(0);
             
             robot.ssTilt.setPosition(0.5);
             sleep(250);
             
            robot.forward();
          sleep(670);
           robot.stop();
            
           }
            telemetry.update();
            

            }
         }
     }
  }

    
