package sort.heapsort;

public class HeapSort {

    public void sort(int[] arr) {
        int n = arr.length;

        // Heap 구조로 만들기
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Heap 구조를 이용하여 정렬하기
        for (int i = n - 1; i > 0; i--) {
            // 현재 루트 노드인 arr[0]를 가장 마지막 노드와 교환
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // heapify을 통해 다시 heap 구조로 만들어주기
            heapify(arr, i, 0);
        }
    }

    void heapify(int[] arr, int n, int i) {
        int largest = i; // 루트 노드
        int l = 2 * i + 1; // 왼쪽 자식 노드
        int r = 2 * i + 2; // 오른쪽 자식 노드

        // 왼쪽 자식 노드가 루트 노드보다 크다면
        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }

        // 오른쪽 자식 노드가 루트 노드보다 크다면
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }

        // largest가 i가 아니라면 루트 노드와 자식 노드의 위치를 바꿔준 후
        // 바뀐 위치를 기준으로 heapify을 다시 호출
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }
}