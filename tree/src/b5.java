
// 19. Remove Nth Node From End of List
import java.util.*;

// Định nghĩa node của Linked List

public class b5 {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int nNodes = sc.nextInt();
        int[] arr = new int[nNodes];
        for (int i = 0; i < nNodes; i++) {
            arr[i] = sc.nextInt();
        }

        int n = sc.nextInt();

        // Xây dựng linked list từ mảng
        ListNode head = buildLinkedList(arr);

        // Gọi hàm xóa node
        head = removeNthFromEnd(head, n);

        // In danh sách kết quả dạng [1,2,3,5]
        printLinkedList(head);

        sc.close();
    }

    // Hàm xây dựng linked list từ mảng
    public static ListNode buildLinkedList(int[] arr) {
        if (arr.length == 0)
            return null;

        ListNode head = new ListNode(arr[0]); // node đầu tiên
        ListNode curr = head;

        // Nối các node còn lại
        for (int i = 1; i < arr.length; i++) {
            curr.next = new ListNode(arr[i]);
            curr = curr.next;
        }
        return head;
    }

    // Hàm in linked list dạng [1,2,3,5]
    public static void printLinkedList(ListNode head) {
        List<Integer> list = new ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            list.add(curr.val);
            curr = curr.next;
        }
        System.out.println(list);
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        // Tạo dummy node (giúp xử lý trường hợp xóa node đầu tiên)
        ListNode res = new ListNode(0, head);
        ListNode dummy = res;

        // B1: cho con trỏ head chạy trước n bước
        for (int i = 0; i < n; i++) {
            head = head.next;
        }

        // B2: di chuyển song song head và dummy
        // Khi head đến cuối thì dummy sẽ đứng ngay trước node cần xóa
        while (head != null) {
            head = head.next;
            dummy = dummy.next;
        }

        // B3: bỏ qua node cần xóa
        dummy.next = dummy.next.next;

        // Trả về head mới (bỏ qua dummy)
        return res.next;
    }
}

// Ok 👍 mình sẽ giải thích chi tiết thuật toán bạn viết trong Java nhé.

// ---

// ### Ý tưởng bài toán

// Ta cần **xóa node thứ n từ cuối danh sách liên kết**. Thay vì duyệt 2 lần (1
// lần đếm độ dài, 1 lần tìm vị trí), ta dùng **2 con trỏ (two pointers)** để
// làm trong 1 lần duyệt.

// ---

// ### Code và giải thích từng bước

// ```java
// class Solution {
// public ListNode removeNthFromEnd(ListNode head, int n) {
// // Tạo dummy node để tránh case xóa head
// ListNode res = new ListNode(0, head);
// ListNode dummy = res;

// // B1: cho con trỏ head chạy trước n bước
// // Sau vòng for, khoảng cách giữa head và dummy là n
// for (int i = 0; i < n; i++) {
// head = head.next;
// }

// // B2: di chuyển song song head và dummy
// // Khi head chạy đến cuối (null), dummy sẽ đứng ngay trước node cần xóa
// while (head != null) {
// head = head.next;
// dummy = dummy.next;
// }

// // B3: xóa node thứ n từ cuối bằng cách bỏ qua dummy.next
// dummy.next = dummy.next.next;

// // Trả về danh sách mới, bỏ qua dummy node
// return res.next;
// }
// }
// ```

// ---

// ### Ví dụ minh họa

// Input: `head = [1,2,3,4,5], n = 2`
// Mục tiêu: xóa node có giá trị `4`.

// 1. Sau khi chạy `for`, con trỏ `head` đứng ở node `3` (cách `dummy` **2
// bước**).
// 2. Tiếp tục `while` cho đến khi `head = null`:

// * `dummy` di chuyển từ node `0(dummy)` → `1` → `2` → `3`.
// 3. Lúc này `dummy` đang ở node `3`, ngay trước node `4` cần xóa.
// 4. Cập nhật `dummy.next = dummy.next.next` → bỏ qua node `4`.

// Kết quả: `[1,2,3,5]`.

// ---

// 👉 Thuật toán này chỉ duyệt **1 lần qua list** → độ phức tạp **O(L)** với `L`
// là số node.
// 👉 Dùng dummy node để tránh xử lý riêng trường hợp xóa node đầu tiên.

// ---

// Bạn có muốn mình vẽ sơ đồ **con trỏ di chuyển (head và dummy)** qua từng bước
// để dễ hình dung hơn không?
