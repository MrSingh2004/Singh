import java.util.ArrayList;
import java.util.Collections;


    public class CSCANAlgorithm extends ScheduleAlgorithmBase {

    public CSCANAlgorithm(int initPosition, int maxCylinders, int direction, ArrayList<Integer> q) {
        super(initPosition, maxCylinders, direction, q);
    }

    public String getName() {
        return "C-SCAN";
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

        Collections.sort(lower);
        Collections.sort(higher);

        if (direction == ScheduleAlgorithm.RIGHT) {
            for (Integer r : higher)
                seekToSector(r);

            if (position != maxCylinders - 1)
                seekToSector(maxCylinders - 1);

            seekToSector(0);

            for (Integer r : lower)
                seekToSector(r);
        } else {
            for (int i = lower.size() - 1; i >= 0; i--)
                seekToSector(lower.get(i));

            if (position != 0)
                seekToSector(0);

            seekToSector(maxCylinders - 1);

            for (int i = higher.size() - 1; i >= 0; i--)
                seekToSector(higher.get(i));
        }
    }
}
