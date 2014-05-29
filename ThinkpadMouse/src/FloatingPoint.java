
public class FloatingPoint {
	public float x,y;
	public FloatingPoint(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return "(" + String.valueOf(x) + "," + String.valueOf(y)+ ")";
	}
	
	public float distanceTo(FloatingPoint point){
		return (float) Math.sqrt((point.x * point.x) + (point.y * point.y));
	}
}
