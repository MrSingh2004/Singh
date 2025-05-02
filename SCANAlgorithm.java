import java.util.ArrayList;
import java.util.Collections;

    
    public class SCANAlgorithm extends ScheduleAlgorithmBase {

    public SCANAlgorithm(int initPosition, int maxCylinders, int direction, ArrayList<Integer> q) {
        super(initPosition, maxCylinders, direction, q);
    }

    public String getName() {
        return "SCAN";
    }

    public void calcSequence() {
        ArrayList<Integer> lower = new ArrayList<>();
        ArrayList<Integer> higher = new ArrayList<>();

        for (Integer r : referenceQueue) {
           
            if (r < position)
                lower.add(r);
            else
                higher.add(r);
    }

        Collections.sort(lower, Collections.reverseOrder());
        Collections.sort(higher);

        if (direction == ScheduleAlgorithm.RIGHT) {
            
            for (Integer r : higher)
                seekToSector(r);
            
            if (position != maxCylinders - 1)
                seekToSector(maxCylinders - 1);
            
            
            for (Integer r : lower)
                seekToSector(r);
        
        } else {
            
            for (Integer r : lower)
                seekToSector(r);
            
            
            if (position != 0)
                seekToSector(0);
            
            
            for (Integer r : higher)
                seekToSector(r);
        }
    }
}
