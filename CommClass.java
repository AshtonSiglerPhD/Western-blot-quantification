import javax.swing.*;
/**
 * CommClass is responsible for communication between the GUI and 
 * AnalysisLogic. Prevents GUI from becoming overly conflated with the logic.
 * @author Ashton
 *
 */
public class CommClass {
    
    public AnalysisLogic logicObject;
    private boolean colorFlip = false;
    //public MainGUITwo GUIObject;
    
    public CommClass() {
        logicObject = new AnalysisLogic();
    }
    
    public void flipColor() {
        if (!colorFlip) {
            colorFlip = true;
        } else {
            colorFlip = false;
        }
    }
    
    public boolean getColorFlip() {
        return this.colorFlip;
    }
    
    public void addRawFromGUI(int x) {
        logicObject.addRawValue(x);
    }

    public void processData() {
        //logicObject.fillWithTestData();
        //logicObject.fillWithTestData();
        logicObject.processRawValues();
        System.out.println(logicObject.getRawValues());
        System.out.println(logicObject.getRelativeValues());
    }
    
//    public static void main(String args[]) {
//        CommClass runner = new CommClass();
//        runner.logicObject.fillWithTestData();
//        SwingUtilities.invokeLater(new Runnable() {
//          public void run() {
//              MainGUITwo.createAndShowGUI();
//              System.out.println(MainGUITwo.hasBuffImage);
//              
//          }
//      });
//    }
}
