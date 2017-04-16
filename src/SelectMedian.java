/**
 *
 * @author Duc Nguyen
 * January 20, 2017
 * CSC 226 - Spring 2017
 * Find the median of an unsorted array using QuickSelect Algorithm
 */
public class SelectMedian {
	// Get the median from a small size array
	public static int selectMedian(int left, int right, int[] array) {
		for(int k = left+1; k < right; k++) {
			int h = left;
			int val = array[k];
			while((h >= left) && (array[h] > val)) {
				array[h+1] = array[h];
				h--;
			}
			array[h+1] = val;
		}
		return left + (int)Math.ceil((right-left)/2);
	}

	private static void swap(int[]array, int a, int b){
		int c = array[a];
		array[a] = array[b];
		array[b] = c;
	}
	/*
	I made an assumption that whatever calls LinearSelect will pass k in as k = (A.length + 1)/2
	LinearSelect will check if the length of A is odd, then add 1 to k so it is the median
	If A.length is even, it uses the floor value to get the median
	 */
	public static int LinearSelect(int[] A, int k){
		if (A == null) return -1;
		else if(A.length==1)return A[0];
		else{
//			if (A.length % 2 == 1){
//				return linearSelect(0,A.length-1,A,k + 1); // ceiling for array with odd length
//			}else{
				return linearSelect(0,A.length-1,A,k);
//			}

		}

	}
	private static int linearSelect(int left,int right, int[] A, int k){
		//return when left meets with right
		if (left>=right){
			return A[left];
		}
		int kth = sortToPivot(left,right,A,pickPivot(left,right,A));
		// get the kth smallest element that is also in midpoint, call pickPivot that return the pivot
		// so that it is used to sort the array around that pivot
		if (k <= kth){
			return linearSelect(left,kth-1,A,k); // choose the left side
		}else if(k == kth + 1){
			return A[kth]; // found it
		}else{
			return linearSelect(kth+1,right,A,k); // chose the right side
		}
	}
	private static int sortToPivot(int left, int right, int[] A, int pIndex){ // reorder the array according to the pivot but move the pivot to the end
		swap(A,pIndex,right); // move pivot to the rightmost side because we don't care about it
		int pivot=A[right];
		int tempRight=right-1;
		int tempLeft=left;
		while(tempLeft<=tempRight){
			while(tempLeft<=tempRight && A[tempLeft]<=pivot){
				tempLeft++;
			}
			while(tempLeft<=tempRight && A[tempRight]>=pivot){
				tempRight--;
			}
			if (tempLeft<tempRight){
				swap(A,tempLeft,tempRight);
			}
		}
		swap(A,tempLeft,right);
		return tempLeft;
	}

	private static int[] construct(int[] A ){
		// need to construct a new array because the base one cannot be used
		int[] array = new int[A.length-1];
		for(int i = 0; i < array.length; i++) {
			array[i] = A[i];
		}
		return array;
	}
	private static int pickPivot(int left, int right, int[] A){
		int[] array = construct(A);
		if (right-left <7) {    // less than 7 elements. Just sort
			return selectMedian(left, right, array);
		}
		int index = left;
		for(int k = left; k < right; k=k+7) {
			int rightBorder = k+6;
			if (rightBorder > right) {
				rightBorder = right; // if less than 7 elements just do less than that.
			}
			swap(array, selectMedian(k, rightBorder, array), index);
			index++;
		}
		int newRight = (left + (int)Math.ceil((right-left))/7);
		return pickPivot(left, newRight, array);
	}


    public static void main(String[] args) {
	    int[] A = {50, 54, 49, 49, 48, 49, 56, 52, 51, 52, 50, 59}; // array in Java is immmutable
//	    A = new int[] {3, 75, 12, 20};
//	    A = new int[] {1, 2, 4, 5, 3,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
//	    A = new int[] {75};
//	    A = null;
	    for (int k = 1; k < A.length + 1; k++) {
		    System.out.println("The median weight of index " + k + " is " + LinearSelect(A, (A.length + 1) / 2));
	    }
    }
}
