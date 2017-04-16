/**
 * QuickSelect: Selection algorithm to find the kth smallest element in an unordered list
 * Average time: O(n), Worst time: O(n^2)
 */
public class QuickSelect {

	public static int recursiveQuickSelect(int[] array, int index){
		return recursive(array, 0, array.length - 1, index);
	}
	private static int recursive(int[] array, int left, int right, int index){
		if(left == right) return array[left];
		int pivot = randomPivot(left, right);
		int pivotIndex = partition(array,left, right, pivot);
		if (index == pivotIndex) {
			return array[index];
		} else if (index < pivotIndex) {
			return recursive(array, left, pivotIndex - 1, index);
		} else {
			return recursive(array, pivotIndex + 1, right, index);
		}
	}
	// Group a list into two part, left side < pivotIndex and right side >= pivotIndex
	private static int partition(int[] array, int left, int right, int pivotIndex){
		int value = array[pivotIndex];
		swap(array, pivotIndex, right);
		int partitionPoint = left;
		for(int i = left; i < right; i++){
			if(array[i] < value){
				swap(array, partitionPoint, i);
				partitionPoint++;
			}
		}
		swap(array,right,partitionPoint);
		return partitionPoint;
	}

	private static void swap(int[] array, int left, int right){
		int temp = array[left];
		array[left] = array[right];
		array[right] = temp;
	}
	// Choose a random pivot given left and right
	private static int randomPivot(int left, int right) {
		return left + (int) Math.floor(Math.random() * (right - left + 1));
	}

	public static void main(String[] args){
		int[] array = {0,9,8,7,6,43,5,4,3,2,21,54,76,87,14,47,78};
		for(int i = 0; i < array.length; i++) {
			System.out.println(recursiveQuickSelect(array, i));
		}
	}
}
