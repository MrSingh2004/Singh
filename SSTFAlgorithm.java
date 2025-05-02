import java.util.ArrayList;

    
    public class SSTFAlgorithm extends ScheduleAlgorithmBase {

    public SSTFAlgorithm(int initPosition, int maxCylinders, int direction, ArrayList<Integer> q) {
        super(initPosition, maxCylinders, direction, q);
    }

    public String getName() {
        return "SSTF";
    }

    public void calcSequence() {
        ArrayList<Integer> remaining = new ArrayList<>(referenceQueue);

        while (!remaining.isEmpty()) {
            int closest = remaining.get(0);
            int shortest = Math.abs(position - closest);

            for (int i = 1; i < remaining.size(); i++) {
                int current = remaining.get(i);
                int dist = Math.abs(position - current);
                if (dist < shortest) {
                    shortest = dist;
                    closest = current;
                }
            }

            seekToSector(closest);
            remaining.remove(Integer.valueOf(closest));
        }
    }
}

