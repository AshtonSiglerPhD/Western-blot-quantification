import java.util.*;
import java.math.*;

/**
 * 
 * @author Ashton
 *
 */
public class AnalysisLogic {

    private ArrayList<Integer> rawValues;
    private List<Double> relativeValues;
    
    public AnalysisLogic() {
        
        rawValues = new ArrayList<Integer>();
        relativeValues = new ArrayList<Double>();
    }
    
    public List<Integer> getRawValues() {
        return this.rawValues;
                
    }
    
    public List<Double> getRelativeValues() {
        return this.relativeValues;
    }
    
    public void fillWithTestData() {
        for(int i = 0; i < 108; i++) {
            addRawValue((int) (Math.random()*100));
        }
    }
    
    public void addRawValue(int x) {
        rawValues.add(x);
    }
    
    public void flipRawValues() {
        
    }
    
    public void undoLastRawValue() {
        rawValues.remove((rawValues.size() - 1));
        rawValues.trimToSize();
    }
    
    public void clearRelativeValues() {
        relativeValues = new ArrayList<Double>();
    }
    
    public void processRawValues() {
        
        
        
        int size = rawValues.size();
        int maximum = -1;
        
        for(int i = 0; i < size; i++) {
            int x = rawValues.get(i);
            if (x > maximum) {
                maximum = x;
            }
        }
        
        for(int i = 0; i < size; i++) {
            double newVal = ( (double)rawValues.get(i)/maximum);
            relativeValues.add(newVal);
        }
        
    }
    
    public static void main(String args[]) {
        //System.out.println((double)1/2);
        AnalysisLogic x = new AnalysisLogic();
//        for(int i = 0; i < 20; i++) {
//            x.addRawValue(i);
//        }
        x.fillWithTestData();
//        x.rawValues.add(3,22);
//        x.rawValues.add(3,40);
        x.processRawValues();
        System.out.println(x.rawValues);
        System.out.println(x.relativeValues);
        
        x.undoLastRawValue();
        x.clearRelativeValues();
        x.processRawValues();
        
        System.out.println(x.rawValues);
        System.out.println(x.relativeValues);
    }
}
