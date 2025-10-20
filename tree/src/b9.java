
// 24. Swap Nodes in Pairs
import java.util.*;

public class b9 {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Nhập số phần tử trong danh sách
        int n = sc.nextInt();

        // Tạo danh sách liên kết từ đầu vào
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int i = 0; i < n; i++) {
            int val = sc.nextInt();
            curr.next = new ListNode(val);
            curr = curr.next;
        }

        // Gọi hàm hoán đổi
        ListNode head = dummy.next;
        ListNode swapped = swapPairs(head);

        // In kết quả theo format LeetCode: [2,1,4,3]
        printListAsArray(swapped);
    }

    // Hàm hoán đổi các cặp node liền kề trong danh sách liên kết
    public static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (head != null && head.next != null) {
            ListNode first = head;
            ListNode second = head.next;

            prev.next = second;
            first.next = second.next;
            second.next = first;

            prev = first;
            head = first.next;
        }

        return dummy.next;
    }

    // In danh sách liên kết theo dạng mảng: [2,1,4,3]
    public static void printListAsArray(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        System.out.println(list.toString().replace(" ", ""));
    }

    // Định nghĩa node
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }
}

// Rất hay — đây là một bài LeetCode kinh điển: **“24. Swap Nodes in Pairs”** 💡
// Cùng giải thích **đề bài** thật dễ hiểu nhé 👇

// ---

// ## 🧩 **Đề bài: 24. Swap Nodes in Pairs**

// Cho **một danh sách liên kết (linked list)**, bạn cần **hoán đổi vị trí của
// các nút theo cặp** liền kề và **trả về đầu danh sách mới** sau khi hoán đổi.

// ---

// ### 🔹 **Ví dụ:**

// **Input:**

// ```
// head = [1, 2, 3, 4]
// ```

// **Output:**

// ```
// [2, 1, 4, 3]
// ```

// **Giải thích:**

// * Cặp (1,2) → hoán đổi thành (2,1)
// * Cặp (3,4) → hoán đổi thành (4,3)
// * Kết quả: `[2, 1, 4, 3]`

// ---

// ### 🔹 **Trường hợp đặc biệt:**

// 1️⃣ Nếu danh sách chỉ có **1 phần tử**, thì **không thể hoán đổi**, kết quả
// giữ nguyên.
// → Ví dụ: `[1]` → `[1]`

// 2️⃣ Nếu danh sách rỗng (`head = None`), kết quả cũng là `None`.

// ---

// ### 🔹 **Yêu cầu:**

// * **Không được chỉ thay đổi giá trị trong node.**
// * Bạn phải **thay đổi con trỏ (pointer)** giữa các node để thực sự hoán đổi
// vị trí.

// ---

// ### 🔹 **Minh họa trực quan:**

// Trước khi hoán đổi:

// ```
// 1 -> 2 -> 3 -> 4 -> null
// ```

// Sau khi hoán đổi:

// ```
// 2 -> 1 -> 4 -> 3 -> null
// ```

// ---

// ### 🔹 **Tóm tắt ý tưởng thuật toán:**

// * Duyệt qua danh sách theo **từng cặp node**.
// * Mỗi cặp (A, B):

// * Hoán đổi con trỏ để B trỏ về A.
// * Giữ con trỏ đến node tiếp theo để tiếp tục duyệt.
// * Cập nhật lại con trỏ đầu (`head`) khi xử lý cặp đầu tiên.

// ---

// Nếu bạn muốn, mình có thể viết **code minh họa bằng Java hoặc Python** (kèm
// chú thích rõ ràng) cho bài này nhé.
// 👉 Bạn muốn xem bằng **Java** hay **Python**?
// Rất hay — ta cùng **phân tích kỹ thuật toán trong code này nhé 👇**

// ---

// ## 🎯 **Mục tiêu bài toán**

// Bạn có một **danh sách liên kết đơn (Linked List)**, ví dụ:

// ```
// 1 → 2 → 3 → 4
// ```

// Bạn cần **đổi chỗ từng cặp node liền kề**, kết quả là:

// ```
// 2 → 1 → 4 → 3
// ```

// Nếu danh sách có số phần tử lẻ (ví dụ `1→2→3`), thì node cuối (`3`) giữ
// nguyên.

// ---

// ## 🧩 **Ý tưởng thuật toán**

// Bài này không được phép chỉ đổi giá trị (`val`) của node, mà phải đổi **liên
// kết (next)**.
// Ta sẽ hoán đổi *hai node liên tiếp* mỗi lần duyệt.

// ---

// ## 🔁 **Các bước chi tiết**

// Giả sử danh sách:

// ```
// 1 → 2 → 3 → 4
// ```

// ---

// ### **1️⃣ Tạo node giả (dummy)**

// ```java
// ListNode dummy = new ListNode(0);
// dummy.next = head;
// ListNode prev = dummy;
// ```

// → `dummy` giúp xử lý dễ dàng hơn khi `head` bị đổi chỗ.
// Bây giờ danh sách là:

// ```
// dummy → 1 → 2 → 3 → 4
// ```

// ---

// ### **2️⃣ Duyệt từng cặp để hoán đổi**

// Vòng `while` chạy khi vẫn còn ít nhất 2 node:

// ```java
// while (head != null && head.next != null) {
// ListNode first = head;
// ListNode second = head.next;
// ```

// Ví dụ lúc đầu:

// ```
// first = 1
// second = 2
// ```

// ---

// ### **3️⃣ Tiến hành đổi chỗ**

// #### Trước khi đổi:

// ```
// prev → 1 → 2 → 3 → 4
// ```

// #### Sau khi đổi:

// * `prev.next = second;`
// → `prev → 2`
// * `first.next = second.next;`
// → `1 → 3`
// * `second.next = first;`
// → `2 → 1`

// Kết quả sau hoán đổi:

// ```
// dummy → 2 → 1 → 3 → 4
// ```

// ---

// ### **4️⃣ Cập nhật con trỏ cho lần tiếp theo**

// ```java
// prev = first;
// head = first.next;
// ```

// * `prev` giờ trỏ vào node `1` (node sau cùng của cặp vừa đổi).
// * `head` chuyển sang `3` (cặp kế tiếp bắt đầu tại 3).

// Lặp lại quá trình cho cặp (3, 4).

// ---

// ### **5️⃣ Kết thúc**

// Khi duyệt xong, ta trả về danh sách mới:

// ```java
// return dummy.next;
// ```

// `dummy.next` trỏ đến node đầu tiên của danh sách đã hoán đổi.

// ---

// ## ⏱️ **Độ phức tạp**

// * **Thời gian:** `O(n)` — duyệt qua từng node 1 lần
// * **Không gian:** `O(1)` — chỉ dùng vài biến tạm (`dummy`, `prev`, `first`,
// `second`)

// ---

// ## ✅ **Ví dụ minh họa**

// **Input:**

// ```
// 1 → 2 → 3 → 4
// ```

// **Quá trình:**

// ```
// → (1,2) → 2 1
// → (3,4) → 4 3
// ```

// **Output:**

// ```
// 2 → 1 → 4 → 3
// ```

// ---

// Bạn có muốn mình viết thêm **phiên bản Python tương đương** với giải thích
// tương tự không?
