# HeapSort(힙 정렬)
## References

https://st-lab.tistory.com/225
<br>
https://www.programiz.com/dsa/heap-sort

## Description

### 시간 복잡도 및 공간 복잡도
- 시간 복잡도: O(nlog(n))
- 공간 복잡도: O(1)

### 장점
- 최악의 경우에도 O(nlog(n))의 시간복잡도를 가진다.
- 힙의 특성상 <u>부분 정렬</u>을 할 때 효율이 좋다.

### 단점
- 일반적인 O(nlog(n)) 정렬 알고리즘에 비해 성능은 약간 떨어지는 편이다.
- 한 번 최대힙을 만들면서 불안정 정렬 상태에서 최대값만을 가지고 정렬하기 때문에 안정 정렬이 아니다.

## 노트 필기

![알고리즈무-10](https://user-images.githubusercontent.com/79316402/222917341-90f7f852-5c74-4304-b2ed-01eaee4ab1f8.jpg)

![알고리즈무-11](https://user-images.githubusercontent.com/79316402/222917347-a1ba94ab-733e-40f0-a158-12cd2879b202.jpg)

![알고리즈무-12](https://user-images.githubusercontent.com/79316402/222917355-8f1f5a79-27ae-4729-913f-fbe14b692a02.jpg)

![알고리즈무-13 2](https://user-images.githubusercontent.com/79316402/222917359-3e881a55-f2b0-4bda-b1c7-0051b4395afc.jpg)

## Code

- 재귀적 방법
```java
public class HeapSort {
    // 부모 인덱스를 읽는 함수
    private static int getParent(int child) {
        return (child - 1) / 2;
    }

    // 두 인덱스의 원소를 교환하는 함수
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void heapSort(int[] arr) {
        int size = arr.length;

        /*
         * 부모노드와 heaify과정에서 음수가 발생하면 잘못된 참조가 발생하기 때문에
         * 원소가 1개이거나 0개일 경우는 정렬 할 필요가 없으므로 바로 함수를 종료한다.
         */
        if (size < 2) {
            return;
        }

        // 가장 마지막 노드의 부모 노드 인덱스
        int parentIdx = getParent(size - 1);

        // max heap 만들기
        for (int i = parentIdx; i >= 0; i--) {
            // 부모노드(i값)을 1씩 줄이면서 heap 조건을 만족시키도록 재구성한다.
            heapify(arr, i, size - 1);
        }

        for (int i = size - 1; i > 0; i--) {
            /*
             *  root인 0번째 인덱스와 i번째 인덱스의 값을 교환해준 뒤
             *  0 ~ (i-1) 까지의 부분트리에 대해 max heap을 만족하도록
             *  재구성한다.
             */
            swap(arr, 0, i);
            heapify(arr, 0, i - 1);
        }
    }

    // 힙을 만드느 함수
    private static void heapify(int[] arr, int parentIdx, int lastIdx) {
        /*
         * 현재 트리에서 부모 노드의 자식노드 인덱스를 각각 구해준다.
         * 현재 부모 인덱스를 가장 큰 값을 갖고있다고 가정한다.
         */
        int leftChildIdx = 2 * parentIdx + 1;
        int rightChildIdx = 2 * parentIdx + 2;
        int largestIdx = parentIdx;

        /*
         *  left child node와 비교
         *
         *  자식노드 인덱스가 끝의 원소 인덱스를 넘어가지 않으면서
         *  현재 가장 큰 인덱스보다 왼쪽 자식노드의 값이 더 클경우
         *  가장 큰 인덱스를 가리키는 largestIdx를 왼쪽 자식노드인덱스로 바꿔준다.
         *
         */
        if (leftChildIdx < lastIdx && arr[largestIdx] < arr[leftChildIdx]) {
            largestIdx = leftChildIdx;
        }

        /*
         *  right child node와 비교
         *
         *  자식노드 인덱스가 끝의 원소 인덱스를 넘어가지 않으면서
         *  현재 가장 큰 인덱스보다 오른쪽 자식노드의 값이 더 클경우
         *  가장 큰 인덱스를 가리키는 largestIdx를 오른쪽 자식노드인덱스로 바꿔준다.
         *
         */
        if (rightChildIdx < lastIdx && arr[largestIdx] < arr[rightChildIdx]) {
            largestIdx = rightChildIdx;
        }

        /*
         * largestIdx와 부모노드가 같지 않다는 것은
         * 위 자식노드 비교 과정에서 현재 부모노드보다 큰 노드가 존재한다는 뜻이다.
         * 그럴 경우 해당 자식 노드와 부모노드를 교환해주고,
         * 교환된 자식노드를 부모노드로 삼은 서브트리를 검사하도록 재귀 호출 한다.
         */
        if (parentIdx != largestIdx) {
            swap(arr, largestIdx, parentIdx);
            heapify(arr, largestIdx, lastIdx); // 재귀 호출이 발생
        }
    }
}
```

- ✅ 추천! 재귀적 방법 성능 개선 버전(for문 활용) 
- 이전 코드의 마지막 if문을 보면 heapify의 재귀 호출이 발생한다.
  - 정렬해야 하는 원소가 많아진다.
  - 재귀가 일어날 때마다 부모노드와 자식노드가 교환된다면 최악의 경우 트리의 깊이 만큼의 재귀 호출이 발생할 수 있다는 의미이고, stackoverflow가 발생할 수 있으며 메모리를 많이 차지한다는 뜻이다.
- 반복문 형식으로 구현하면 문제를 해결할 수 있다.
  - 2 * parentIdx + 1은 왼쪽 자식 인덱스를 의미하고, 이 값이 lastIdx, 즉 마지막 인덱스를 넘지 않을 때까지 반복한다.
  - 왼쪽 자식 인덱스를 선택한 이유는 마지막 부모 인덱스가 왼쪽 자식만 가지고 있는 경우, 왼쪽 자식노드와 비교 및 교환이 불가능하기 때문이다.

```java
public class HeapSort {
 
	/*
	 * 중간 코드 생략 (위에 있는 코드와 같다.)
	 */
	
	private static void heapify(int[] arr, int parentIdx, int lastIdx) {
		
	    int leftChildIdx;
	    int rightChildIdx;
	    int largestIdx;
 
	    /*
	     * 현재 부모 인덱스의 자식 노드 인덱스가 
	     * 마지막 인덱스를 넘지 않을 때 까지 반복한다.
	     * 
	     * 이 때 왼쪽 자식 노드를 기준으로 해야 한다.
	     * 오른쪽 자식 노드를 기준으로 범위를 검사하게 되면
	     * 마지막 부모 인덱스가 왼쪽 자식만 갖고 있을 경우
	     * 왼쪽 자식노드와는 비교 및 교환을 할 수 없기 때문이다. 
	     */
	    while((parentIdx * 2) + 1 <= lastIdx) {
	        leftChildIdx = (parentIdx * 2) + 1;
	        rightChildIdx = (parentIdx * 2) + 2;
	        largestIdx = parentIdx;
 
	        /*
	         * left child node와 비교 
	         * (범위는 while문에서 검사했으므로 별도 검사 필요 없음)
	         */
	        if (arr[leftChildIdx] > arr[largestIdx]) {
	            largestIdx = leftChildIdx;
	        }
 
	        /*
	         * right child node와 비교 
	         * right child node는 범위를 검사해주어야한다. 
	         */
	        if (rightChildIdx <= lastIdx && arr[rightChildIdx] > arr[largestIdx]) {
	            largestIdx = rightChildIdx;
	        }
 
	        /*
	         * 교환이 발생했을 경우 두 원소를 교체 한 후
	         * 교환이 된 자식노드를 부모 노드가 되도록 교체한다. 
	         */
	        if (largestIdx != parentIdx) {
	            swap(arr, parentIdx, largestIdx);
	            parentIdx = largestIdx;
	        } 
	        else {
	            return;
	        }
	    }
	}
}
```

- ✅ Popular! Heap Sort 알고리즘(재귀 방식 + O(nlog(n)))
- for 문에서 i가 n / 2 - 1인 이유는 배열을 힙으로 만들어줄 때, 부모노드와 자식노드 간의 대소 관계를 비교해야 한다. 힙 정렬 알고리즘은 힙을 최대 힙으로 구성하는 것이 목적이기 때문에 배열의 뒷부분은 자식 노드를 갖지 못하므로, 뒷부분을 제외한 나머지 부분에서 힙을 구성하기 위해 i 값이 n / 2 - 1로 설정되는 것이다.
```java
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

```
