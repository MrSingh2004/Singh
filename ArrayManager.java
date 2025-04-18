import java.util.ArrayList;

public class ArrayManager {
        private int[] data;
        private ArrayList<Integer> freeFrameList;
        private int frameSize;


        public ArrayManager(int numFrames, int frameSize) {
        this.frameSize = frameSize;
        int totalSize = numFrames * frameSize;

        data = new int[totalSize];

        freeFrameList = new ArrayList<>();

        for (int i = 0; i < numFrames; i++) {
        freeFrameList.add(i);
        }
        }



        public Array createArray(int size) throws OutOfMemoryException {
                // Your code here

                return null;
        }

        public Array aliasArray(Array a) {
                PagedArray pa = (PagedArray) a;

                // Your code here

                return null;
        }

        public void deleteArray(Array a) {
                PagedArray pa = (PagedArray) a;
                //
                // Your code here
        }

        public void resizeArray(Array a, int newSize) throws OutOfMemoryException {
                PagedArray pa = (PagedArray) a;
      //
                // Your code here
        }

        public void printMemory() {
                // Your code here
        }

        private class PagedArray implements Array {
                private int[] pageTable;
                private int length;

                public PagedArray(int pageTable[], int length) {
                        this.pageTable = pageTable;
                        this.length = length;
                }

                public int getValue(int index) throws SegmentationViolationException {
                        if (index < 0 || index >= length) throw new SegmentationViolationException();
                        return 0;
                }

                public void setValue(int index, int val) throws SegmentationViolationException {
                        if (index < 0 || index >= length) throw new SegmentationViolationException();
                }

                public String toString() {
                        // code here

                        return "";
                }

                public int[] getPageTable() {

                        return pageTable;
                }

                public void setPageTable(int[] pageTable) {
                        this.pageTable = pageTable;
                }

                public int length() {

                        return length;
                }

                public void setLength(int length) {
                        this.length = length;
                }
        }
}
