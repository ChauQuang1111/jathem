// 101. Symmetric Tree

import java.util.*;

public class b10 {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int n = sc.nextInt();

        System.out.println("Nhập các giá trị node theo thứ tự level order:");
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            int val = sc.nextInt();
            arr[i] = val; // không có null ở đây để đơn giản
        }

        // Xây cây từ mảng level order
        TreeNode root = buildTree(arr);

        // Kiểm tra đối xứng

        boolean result = isSymmetric(root);

        System.out.println(result ? "Cây đối xứng" : "Cây không đối xứng");
    }

    // Xây cây nhị phân từ mảng level order
    public static TreeNode buildTree(Integer[] arr) {
        if (arr.length == 0 || arr[0] == null)
            return null;

        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;

        while (!q.isEmpty() && i < arr.length) {
            TreeNode node = q.poll();

            // thêm nhánh trái
            if (i < arr.length && arr[i] != null) {
                node.left = new TreeNode(arr[i]);
                q.add(node.left);
            }
            i++;

            // thêm nhánh phải
            if (i < arr.length && arr[i] != null) {
                node.right = new TreeNode(arr[i]);
                q.add(node.right);
            }
            i++;
        }
        return root;
    }

    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        return isMirror(root.left, root.right);
    }

    // Hàm kiểm tra 2 nhánh có là ảnh gương của nhau không
    public static boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null)
            return true; // cả 2 đều rỗng → đối xứng
        if (t1 == null || t2 == null)
            return false; // 1 bên null → không đối xứng

        // kiểm tra giá trị và 2 cặp nhánh con
        return (t1.val == t2.val)
                && isMirror(t1.left, t2.right)
                && isMirror(t1.right, t2.left);
    }
}

// Tất nhiên 👇 — mình sẽ **giải thích chi tiết thuật toán “Symmetric Tree”**
// (Bài LeetCode 101) kèm ví dụ minh họa rõ ràng.

// ---

// ## 🧩 Đề bài

// Cho gốc của một **cây nhị phân**, hãy kiểm tra xem **cây có đối xứng
// (symmetrical)** quanh trục dọc đi qua gốc hay không.

// Nói cách khác, cây đó có phải là **ảnh gương của chính nó** không?

// ---

// ## 💡 Ý tưởng chính

// Cây đối xứng khi:

// * Hai nhánh trái và phải của gốc là **ảnh gương** của nhau.
// * Nghĩa là:

// * Giá trị nút trái và phải **bằng nhau**
// * Nhánh **trái của cây bên trái** phải đối xứng với **nhánh phải của cây bên
// phải**,
// * và ngược lại.

// ---

// ## 🔁 Thuật toán đệ quy

// Ta viết một hàm phụ `isMirror(TreeNode t1, TreeNode t2)` để kiểm tra xem hai
// cây có là ảnh gương của nhau hay không.

// ### Bước kiểm tra:

// 1. **Cả hai cùng null** → ✅ Đối xứng
// (vì cả hai đều không có gì)

// 2. **Một trong hai null** → ❌ Không đối xứng
// (một bên có nhánh, bên kia không có)

// 3. **Giá trị không bằng nhau** → ❌ Không đối xứng

// 4. Nếu các điều kiện trên đều qua, ta tiếp tục kiểm tra **2 cặp con tương
// ứng**:

// * `t1.left` ↔ `t2.right`
// * `t1.right` ↔ `t2.left`

// Nếu tất cả các cặp đều đúng → cây đối xứng.

// ---

// ## 🧠 Độ phức tạp

// * **Thời gian:** `O(n)` — ta duyệt tất cả các nút trong cây đúng một lần.
// * **Không gian:** `O(h)` — do đệ quy (h là chiều cao cây).

// ---

// ## 🧮 Ví dụ minh họa

// ### Input:

// ```
// 1
// / \
// 2 2
// / \ / \
// 3 4 4 3
// ```

// ### So sánh từng cặp:

// 1. `1 ↔ 1` ✅
// 2. `2(left) ↔ 2(right)` ✅
// 3. So sánh:

// * `3 ↔ 3` ✅
// * `4 ↔ 4` ✅

// Mọi cặp tương ứng đều bằng nhau → **cây đối xứng** ✅

// ---

// ### Trường hợp **không đối xứng:**

// ```
// 1
// / \
// 2 2
// \ \
// 3 3
// ```

// So sánh:

// * `1 ↔ 1` ✅
// * `2(left) ↔ 2(right)` ✅
// * Nhưng:

// * `left.left = null`
// * `right.right = 3`
// → ❌ Không đối xứng

// ---

// ## ✅ Tóm tắt

// | Điều kiện kiểm tra | Kết luận | | |
// | -------------------------- |
// ---------------------------------------------------- | ----------- |
// -------------- |
// | `t1 == null && t2 == null` | Đối xứng | | |
// | `t1 == null | | t2 == null` | Không đối xứng |
// | `t1.val != t2.val` | Không đối xứng | | |
// | Còn lại | So sánh `t1.left ↔ t2.right` và `t1.right ↔ t2.left` | | |

// ---

// Bạn có muốn mình vẽ **sơ đồ minh họa dạng ảnh gương** để trực quan hóa quá
// trình đệ quy không?
