
// 23. Merge k Sorted Lists
import java.util.*;

public class b8 {
    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    // Hàm chính gộp k danh sách đã sắp xếp
    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0)
            return null; // Nếu không có danh sách nào thì trả về null

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        // 1. Tìm giá trị nhỏ nhất và lớn nhất trong tất cả các danh sách
        for (ListNode nodeRow : lists) {
            for (ListNode head = nodeRow; head != null; head = head.next) {
                min = Math.min(min, head.val);
                max = Math.max(max, head.val);
            }
        }

        // Dịch min về 0 để tránh số âm trong mảng
        min = Math.abs(min);
        int freq[] = new int[max + min + 1];

        // 2. Đếm tần suất xuất hiện của từng giá trị
        for (ListNode nodeRow : lists) {
            for (ListNode head = nodeRow; head != null; head = head.next) {
                freq[head.val + min]++;
            }
        }

        // 3. Xây dựng danh sách kết quả từ mảng tần suất
        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy;

        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 0) {
                int val = i - min; // Trả về giá trị ban đầu
                while (freq[i]-- > 0) { // Với mỗi lần xuất hiện, tạo node mới
                    temp.next = new ListNode(val);
                    temp = temp.next;
                }
            }
        }

        return dummy.next; // Trả về danh sách đã gộp
    }

    // Hàm main để nhập dữ liệu bằng Scanner
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Nhập số lượng danh sách k
        int k = sc.nextInt();
        ListNode[] lists = new ListNode[k];

        // Nhập từng danh sách
        for (int i = 0; i < k; i++) {
            int n = sc.nextInt(); // số phần tử trong danh sách thứ i
            ListNode dummy = new ListNode(-1);
            ListNode tail = dummy;

            for (int j = 0; j < n; j++) {
                int val = sc.nextInt();
                tail.next = new ListNode(val);
                tail = tail.next;
            }

            lists[i] = dummy.next; // gán danh sách đã nhập vào mảng
        }

        // Gọi hàm mergeKLists
        ListNode result = mergeKLists(lists);

        // In kết quả
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}

// Rồi 👍 mình sẽ giải thích chi tiết thuật toán mà đoạn code bạn đưa ra đang
// làm nhé.

// ---

// ## 📌 Ý tưởng chính của code

// Code này **không dùng heap hay merge trực tiếp** mà sử dụng **Counting Sort**
// (đếm tần suất).
// Cách làm như sau:

// 1. **Tìm min và max value trong tất cả các linked list**

// * Duyệt qua tất cả `lists` để xác định giá trị nhỏ nhất (`min`) và lớn nhất
// (`max`).
// * Mục đích: biết khoảng giá trị các node để tạo mảng `freq`.

// 2. **Tạo mảng tần suất `freq`**

// * `freq[val + min]` lưu số lần xuất hiện của `val`.
// * Lý do có `+min`: để dịch giá trị về chỉ số dương (nếu có số âm).

// 3. **Duyệt lại toàn bộ danh sách và tăng tần suất**

// * Mỗi lần gặp một node có giá trị `head.val`, tăng `freq[head.val + min]++`.

// 4. **Xây dựng lại linked list đã sắp xếp**

// * Duyệt qua `freq` từ nhỏ đến lớn.
// * Với mỗi `i` (ứng với giá trị `i - min`), nếu `freq[i] > 0`, tạo bấy nhiêu
// node nối vào kết quả.

// ---

// ## 📌 Chú thích code

// ```java
// if(lists.length == 0) return null;

// int max = Integer.MIN_VALUE;
// int min = Integer.MAX_VALUE;

// // 1. Tìm min và max
// for (ListNode nodeRow : lists) {
// for(ListNode head = nodeRow; head != null; head = head.next) {
// min = Math.min(min, head.val);
// max = Math.max(max, head.val);
// }
// }

// min = Math.abs(min); // dịch giá trị để tránh index âm
// int freq[] = new int[max + min + 1];

// // 2. Đếm tần suất
// for (ListNode nodeRow : lists) {
// for(ListNode head = nodeRow; head != null; head = head.next) {
// freq[head.val + min]++;
// }
// }

// // 3. Tạo lại linked list từ freq
// ListNode dummy = new ListNode(-1);
// ListNode temp = dummy;

// for (int i = 0; i < freq.length; i++) {
// if (freq[i] > 0) {
// int val = i - min; // khôi phục lại giá trị thật
// while (freq[i]-- > 0) {
// temp.next = new ListNode(val);
// temp = temp.next;
// }
// }
// }

// return dummy.next;
// ```

// ---

// ## 📌 Độ phức tạp

// * Duyệt toàn bộ node 2 lần → `O(N)` với `N` = tổng số node.
// * Xây dựng kết quả bằng mảng `freq` kích thước `max - min + 1`.

// 👉 **Độ phức tạp thời gian:**

// * Trung bình tốt: `O(N + R)` với `R = max - min`.
// * Nếu giá trị node rất lớn (ví dụ `[-10^9, 10^9]`) thì `freq` cực kỳ to → tốn
// bộ nhớ.

// 👉 **Độ phức tạp không gian:** `O(max - min)` (có thể rất lớn, dễ bị Memory
// Limit Exceeded).

// ---

// ## 📌 Nhận xét

// * Ưu điểm: ý tưởng độc đáo, dễ hiểu, độ phức tạp tuyến tính với dải giá trị
// nhỏ.
// * Nhược điểm: nếu giá trị node nằm trong khoảng rộng (big range), mảng `freq`
// sẽ rất lớn → **không thực tế cho input lớn**.
// * Vì thế LeetCode thường khuyến khích dùng **PriorityQueue** hoặc **Divide &
// Conquer** thay vì counting sort.

// ---

// 👉 Bạn có muốn mình viết một **ví dụ cụ thể** (lists =
// [[1,4,5],[1,3,4],[2,6]]) để mô phỏng cách mảng `freq` được tạo và sau đó
// linked list được xây dựng lại không?

// Ok mình giải thích bài **LeetCode 23 – Merge k Sorted Lists** nhé 👇

// ---

// ### 📝 Đề bài

// Bạn được cho một mảng gồm `k` **danh sách liên kết đơn** (linked list), mỗi
// danh sách đã được **sắp xếp tăng dần**.
// Nhiệm vụ: **gộp tất cả `k` danh sách** này thành **một danh sách liên kết duy
// nhất, đã được sắp xếp tăng dần**.

// ---

// ### 📌 Ví dụ

// **Input:**

// ```
// lists = [[1,4,5],[1,3,4],[2,6]]
// ```

// **Diễn giải:**

// * Danh sách 1: `1 -> 4 -> 5`
// * Danh sách 2: `1 -> 3 -> 4`
// * Danh sách 3: `2 -> 6`

// **Output:**

// ```
// 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6
// ```

// ---

// ### 📌 Ý tưởng giải

// Có nhiều cách giải khác nhau:

// #### 🔹 Cách 1: Gom tất cả phần tử → sắp xếp → tạo lại linked list

// * Duyệt tất cả các node trong `k` danh sách.
// * Đưa giá trị vào một mảng.
// * Sort mảng.
// * Tạo lại linked list theo thứ tự.
// * **Độ phức tạp:** `O(N log N)` (với `N` = tổng số node).

// ---

// #### 🔹 Cách 2: Dùng **min-heap (priority queue)**

// * Ý tưởng giống **merge k mảng đã sắp xếp**.
// * Dùng `priority queue` (min-heap) để luôn lấy ra node nhỏ nhất hiện tại
// trong các danh sách.
// * Gắn node đó vào kết quả, rồi đưa node tiếp theo của danh sách đó vào heap.
// * **Độ phức tạp:** `O(N log k)`

// * `N`: tổng số node.
// * `k`: số danh sách.

// 👉 Đây là cách tối ưu thường dùng.

// ---

// #### 🔹 Cách 3: Chia để trị (Divide & Conquer)

// * Tương tự như merge sort.
// * Ghép đôi danh sách: merge 2 list một lúc → lặp lại cho đến khi còn 1 list.
// * **Độ phức tạp:** `O(N log k)`.

// ---

// ### 📌 Điểm mấu chốt

// * Bài này là **mở rộng của Merge 2 Sorted Lists (LeetCode 21)**.
// * Dùng heap hoặc chia để trị là cách tốt nhất để đạt hiệu suất cao.

// ---

// Bạn có muốn mình viết luôn **code Java bằng PriorityQueue** để thấy rõ cách
// triển khai không?
