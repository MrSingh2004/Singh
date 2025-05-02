import java.util.ArrayList;
import java.util.Collections;


    public class LOOKAlgorithm extends ScheduleAlgorithmBase {

    public LOOKAlgorithm(int initPosition, int maxCylinders, int direction, ArrayList<Integer> q) {
        super(initPosition, maxCylinders, direction, q);
    }

    public String getName() {
        return "LOOK";
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
            
            for (Integer r : lower)
                seekToSector(r);
            
        } else {
            
            for (Integer r : lower)
                seekToSector(r);
            
            for (Integer r : higher)
                seekToSector(r);
        }
    }
}
