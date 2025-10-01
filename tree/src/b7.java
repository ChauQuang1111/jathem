
// Range Sum Query Mutable
import java.util.*;

class b7 {

    /**
     * NumArray using Fenwick Tree (Binary Indexed Tree)
     * - update: O(log n)
     * - sumRange: O(log n)
     */
    public static class NumArray {
        public int[] nums; // lưu giá trị gốc
        public int[] bit; // mảng Fenwick (1-based)
        public int n;

        // Constructor: khởi tạo NumArray và Fenwick Tree từ mảng ban đầu
        public NumArray(int[] nums) {
            this.n = nums.length;
            this.nums = new int[n];
            this.bit = new int[n + 1]; // 1-based indexing cho Fenwick Tree

            // copy dữ liệu vào nums và khởi tạo bit bằng cách add từng phần tử
            for (int i = 0; i < n; i++) {
                this.nums[i] = nums[i];
                add(i, nums[i]); // thêm nums[i] vào BIT tại vị trí i
            }
        }

        /**
         * Cập nhật nums[index] = val
         * 
         * @param index vị trí cần cập nhật (0-based)
         * @param val   giá trị mới
         */
        public void update(int index, int val) {
            if (index < 0 || index >= n)
                return;
            int delta = val - nums[index]; // hiệu chỉnh cần thêm vào BIT
            nums[index] = val; // cập nhật mảng gốc
            add(index, delta); // cập nhật Fenwick Tree
        }

        /**
         * Tính tổng từ left -> right (bao gồm cả hai đầu)
         * 
         * @param left  chỉ số trái (0-based)
         * @param right chỉ số phải (0-based)
         * @return tổng các phần tử trong đoạn [left, right]
         */
        public int sumRange(int left, int right) {
            if (left < 0)
                left = 0;
            if (right >= n)
                right = n - 1;
            if (left > right)
                return 0;
            return prefixSum(right) - prefixSum(left - 1);
        }

        // -------- Fenwick Tree helper methods --------

        // Thêm delta vào vị trí index (0-based). Fenwick dùng index 1-based.
        private void add(int index, int delta) {
            index++; // chuyển sang 1-based
            while (index <= n) {
                bit[index] += delta;
                index += index & -index; // đi tới node cha
            }
        }

        // Tính tổng prefix từ 0 -> index (0-based). Nếu index < 0 trả 0.
        public int prefixSum(int index) {
            if (index < 0)
                return 0;
            int sum = 0;
            index++; // chuyển sang 1-based
            while (index > 0) {
                sum += bit[index];
                index -= index & -index; // chuyển tới node thấp hơn
            }
            return sum;
        }

        static Scanner sc = new Scanner(System.in);

        public static void main(String[] args) {

            // Đọc n
            int n = sc.nextInt();
            int[] arr = new int[n];
            // Đọc mảng nums
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            // Khởi tạo NumArray
            NumArray numArray = new NumArray(arr);

            // Đọc số truy vấn q
            int q = sc.nextInt();
            sc.nextLine(); // đọc newline còn dư

            // Xử lý từng truy vấn
            for (int i = 0; i < q; i++) {
                String line = sc.nextLine().trim();
                if (line.length() == 0) {
                    i--;
                    continue;
                }
                String[] parts = line.split("\\s+");
                String cmd = parts[0];

                if (cmd.equalsIgnoreCase("update")) {
                    // format: update index val
                    int idx = Integer.parseInt(parts[1]);
                    int val = Integer.parseInt(parts[2]);
                    numArray.update(idx, val);
                    // In ra null tương tự LeetCode (tùy chọn)
                    System.out.println("null");
                } else if (cmd.equalsIgnoreCase("sum")) {
                    // format: sum left right
                    int left = Integer.parseInt(parts[1]);
                    int right = Integer.parseInt(parts[2]);
                    int res = numArray.sumRange(left, right);
                    System.out.println(res);
                } else {
                    // nếu lệnh không hợp lệ
                    System.out.println("Unknown command: " + cmd);
                }
            }

            sc.close();
        }
    }

}

// ---

// ## 📌 Đề bài

// Bạn được cho một **mảng số nguyên `nums`**, và cần hỗ trợ 2 loại thao tác:

// 1. **update(index, val)**

// * Cập nhật giá trị của phần tử tại vị trí `index` thành `val`.

// 2. **sumRange(left, right)**

// * Trả về tổng các phần tử trong khoảng `[left, right]`.

// 👉 Khác với bài **303. Range Sum Query – Immutable** (không cho update), ở
// bài này mảng có thể thay đổi nên phải có cấu trúc dữ liệu hiệu quả để hỗ trợ
// cả update lẫn sum.

// ---

// ## 📌 Ví dụ

// Input:

// ```
// ["NumArray", "sumRange", "update", "sumRange"]
// [[[1,3,5]], [0,2], [1,2], [0,2]]
// ```

// Giải thích:

// * `NumArray([1,3,5])` → khởi tạo mảng `[1,3,5]`.
// * `sumRange(0,2)` → 1+3+5 = 9.
// * `update(1,2)` → mảng thành `[1,2,5]`.
// * `sumRange(0,2)` → 1+2+5 = 8.

// Output:

// ```
// [null, 9, null, 8]
// ```

// ---

// ## 📌 Ràng buộc

// * `1 <= nums.length <= 3 * 10^4`
// * `-100 <= nums[i] <= 100`
// * `0 <= index < nums.length`
// * Có thể có **10^4 thao tác** update hoặc sumRange.

// 👉 Nếu dùng cách cộng dồn đơn giản (mỗi lần sumRange duyệt từ `left → right`)
// → O(n) cho mỗi query → quá chậm.

// ---

// ## 📌 Hướng tiếp cận

// Ta cần cấu trúc dữ liệu hỗ trợ:

// * **Update 1 phần tử** nhanh.
// * **Tính tổng trên đoạn [L, R]** nhanh.

// Các cách:

// 1. **Fenwick Tree (Binary Indexed Tree – BIT)**

// * Update: O(log n).
// * Query prefix sum: O(log n).
// * sumRange(L, R) = prefix(R) - prefix(L-1).

// 2. **Segment Tree**

// * Update: O(log n).
// * Query sumRange: O(log n).

// 👉 Cả 2 đều phù hợp. BIT cài ngắn gọn hơn, Segment Tree dễ mở rộng cho các
// loại query khác.

// ---

// 📌 Tóm lại:

// * Đây là bài toán điển hình về **cấu trúc dữ liệu cho Range Query + Update**.
// * Giải bằng **Fenwick Tree hoặc Segment Tree** để đạt hiệu quả.

// ---

// Bạn muốn mình viết code **Fenwick Tree (ngắn gọn)** hay **Segment Tree (dễ
// hiểu hơn)** cho bài này?

// ---

// ### **Đề bài**

// Bạn có một mảng số nguyên `nums`. Yêu cầu hỗ trợ hai loại thao tác:

// 1. `update(index, val)` → Cập nhật giá trị tại `nums[index] = val`.
// 2. `sumRange(left, right)` → Tính tổng phần tử từ `nums[left]` đến
// `nums[right]` (bao gồm cả 2 đầu).

// Mục tiêu: Cả hai thao tác phải được thực hiện **nhanh hơn O(n)** (tốt nhất là
// O(log n)).

// ---

// ### **Giải pháp cơ bản (Brute force)**

// * `update`: O(1) (chỉ cần thay số).
// * `sumRange`: O(n) (tính lại tổng từ left → right).
// ❌ Nhưng nếu có **nhiều truy vấn**, cách này sẽ quá chậm.

// ---

// ### **Giải pháp tối ưu**

// Ta cần một cấu trúc dữ liệu cho phép:

// * Cập nhật nhanh.
// * Tính tổng nhanh.

// Hai cách phổ biến:

// #### **1. Fenwick Tree (Binary Indexed Tree - BIT)**

// * Lưu mảng phụ `bit[]`, trong đó mỗi phần tử quản lý tổng một đoạn con.
// * `update`: O(log n).
// * `sumRange`: O(log n).

// #### **2. Segment Tree**

// * Chia mảng thành các đoạn [left, right], mỗi node quản lý tổng của đoạn đó.
// * `update`: O(log n).
// * `sumRange`: O(log n).

// ---

// ### **Ý tưởng Segment Tree (trực quan hơn)**

// 1. Xây dựng cây phân đoạn từ mảng `nums`.

// * Node gốc quản lý toàn bộ mảng.
// * Node con trái quản lý nửa trái, node con phải quản lý nửa phải.
// * Mỗi node lưu tổng đoạn nó quản lý.
// 2. Khi `update(index, val)` → đi từ gốc xuống lá, cập nhật lại tổng trên
// đường đi.
// 3. Khi `sumRange(left, right)` → duyệt cây, lấy tổng từ các đoạn phù hợp.

// ---

// ### **Độ phức tạp**

// * **Xây dựng cây**: O(n).
// * **update**: O(log n).
// * **sumRange**: O(log n).

// ---

// 👉 Tóm lại: Để giải bài này ta thường dùng **Segment Tree** hoặc **Fenwick
// Tree** để đảm bảo update và query đều nhanh.

// ---

// Bạn có muốn mình viết luôn code mẫu bằng **Segment Tree** để bạn dễ hiểu hơn
// không?
