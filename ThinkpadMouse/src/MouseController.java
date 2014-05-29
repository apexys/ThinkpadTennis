import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.*;


public class MouseController {
	
	Robot Rb;
	
	int delayMS = 32;
	
	Dimension screenSize;
	
	FloatingPoint sensorCenter;
	FloatingPoint sensorMinimum;
	FloatingPoint scaling;
	FloatingPoint screenMiddle;
	
	FloatingPoint history;
	
	float smoothSpeed = 0.15f;
	
	public MouseController() throws AWTException, InterruptedException{
		Rb = new Robot();
		
		history = new FloatingPoint(0f, 0f);
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		lastPosition = readGyro();
		
		screenMiddle = new FloatingPoint(((float)screenSize.width) / 2f, ((float)screenSize.height) / 2f);

		Rb.mouseMove((int) screenMiddle.x, (int) screenMiddle.y);
		
		System.out.println("Robot initialized");
		
		calibrate();
		recalculate();
		
		System.out.print("sensorCenter: ");
		System.out.println(sensorCenter.toString());
		
		System.out.print("sensorMinimum: ");
		System.out.println(sensorMinimum.toString());
		
		System.out.print("scaling: ");
		System.out.println(scaling.toString());
		
		System.out.print("screenMiddle: ");
		System.out.println(screenMiddle.toString());
		
		
		while(true){
			recalculate();
			if(!Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)){
				System.out.println("Enable Capslock to start mouse control");
				while(!Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)){
					Thread.sleep(250);
				}
			}
			System.out.println("Mouse control enabled");
			System.out.println("");
			while(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)){
				controlMouse();
				Thread.sleep(delayMS);
			}
			System.out.println("Mouse control disabled");
		}
	}
	
	public void calibrate(){
		try{
		FloatingPoint temp;
		System.out.println("Set Thinkpad down and press any key");
		System.in.read();
		temp = measureForAWhile();
		sensorCenter = new FloatingPoint(temp.x, temp.y);
		
		System.out.println("Tilt Thinkpad LEFT and BACK and press any key");
		System.in.read();
		temp = measureForAWhile();
		sensorMinimum = new FloatingPoint(temp.x, temp.y);
		
		}catch(Exception ex){
			System.out.println("Calibration FAILED!");
			ex.printStackTrace();
		}
	}
	
	public void recalculate(){
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		lastPosition = readGyro();
		screenMiddle = new FloatingPoint(((float)screenSize.width) / 2f, ((float)screenSize.height) / 2f);
		
		scaling = new FloatingPoint(0f, 0f);
		scaling.x = screenMiddle.x / (sensorCenter.x - sensorMinimum.x);
		scaling.y = screenMiddle.y / (sensorCenter.y - sensorMinimum.y);
	}
	
	public FloatingPoint measureForAWhile(){
		FloatingPoint result = new FloatingPoint(0f, 0f);
		for(int i = 0; i < 100; i++){
			FloatingPoint temp = readGyro();
			result.x = result.x + temp.x;
			result.y = result.y + temp.y;
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		result.x = result.x / 100f;
		result.y = result.y / 100f;
		return result;
	}
	
	public FloatingPoint smoothPoint(FloatingPoint input){
		FloatingPoint smooth = new FloatingPoint(history.x * (1 - smoothSpeed)  + input.x * smoothSpeed, history.y * (1 - smoothSpeed)  + input.y * smoothSpeed);
		history.x = smooth.x;
		history.y = smooth.y;
		return smooth;
	}
	
	public FloatingPoint rectifyPoint(FloatingPoint input){
		FloatingPoint temp =  new FloatingPoint(
				(input.x - sensorMinimum.x) * scaling.x,
				(input.y - sensorMinimum.y) * scaling.y
				);
		//System.out.println(temp);
		return temp;
	}
	
	public FloatingPoint readGyro(){
		try{
		String[] parts = readFileAsString("/sys/devices/platform/hdaps/position").replace("(", "").replace(")","").split(",");
		//System.out.println("Parts: "+ parts[0] + "    " + parts[1]);
		int X = Integer.parseInt(parts[0].trim());
		int Y = Integer.parseInt(parts[1].trim());
		return new FloatingPoint(X,Y);
		//System.out.println("Position: " + String.valueOf(X) + "," + String.valueOf(Y));
		}catch(Exception ex){
			return new FloatingPoint(0f,0f);
		}
		
	}
	
	FloatingPoint lastPosition;
	public void controlMouse(){
		try{
		FloatingPoint position = smoothPoint(rectifyPoint(readGyro()));
		Rb.mouseMove((int) position.x,(int) position.y);
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}
	}
	
	
	private String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
	
	public static void main(String[] args) {
		try{
			MouseController mc = new MouseController();
		}catch(Exception ex){
			;
		}
	}

}
