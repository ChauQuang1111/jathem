
// 94. Binary Tree Inorder Traversal
import java.util.*;

// Định nghĩa cấu trúc Node của cây nhị phân

public class b1 {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Nhập số lượng node trong cây
        int n = sc.nextInt();

        // Nhập giá trị các node (theo thứ tự level-order, -1 nghĩa là null)
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            arr[i] = (x == -1 ? null : x);
        }
        sc.close();
        // Xây dựng cây từ mảng level-order
        TreeNode root = buildTree(arr);

        // Gọi thuật toán Inorder Traversal
        ;
        List<Integer> result = inorderTraversal(root);

        // In kết quả
        System.out.println(result);
    }

    // Hàm xây dựng cây từ mảng level-order (dùng queue)
    public static TreeNode buildTree(Integer[] arr) {
        if (arr.length == 0 || arr[0] == null)
            return null;

        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while (!queue.isEmpty() && i < arr.length) {
            TreeNode current = queue.poll();

            // Gán con trái
            if (i < arr.length && arr[i] != null) {
                current.left = new TreeNode(arr[i]);
                queue.add(current.left);
            }
            i++;

            // Gán con phải
            if (i < arr.length && arr[i] != null) {
                current.right = new TreeNode(arr[i]);
                queue.add(current.right);
            }
            i++;
        }

        return root;
    }

    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result); // gọi hàm đệ quy để duyệt
        return result;
    }

    // Hàm đệ quy inorder
    public static void inorder(TreeNode node, List<Integer> result) {
        if (node == null)
            return; // Nếu node rỗng thì thoát
        inorder(node.left, result); // 1. duyệt cây con bên trái
        result.add(node.val); // 2. thăm node gốc
        inorder(node.right, result); // 3. duyệt cây con bên phải
    }
}

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int x) {
        val = x;
    }
}

// // Ok 👍 mình giải thích cho bạn **LeetCode 94. Binary Tree Inorder
// Traversal**

// // ---

// // ### **Đề bài:**

// // Cho một cây nhị phân (binary tree).
// // Yêu cầu: **trả về danh sách các giá trị của các node theo thứ tự duyệt
// trung thứ tự (inorder traversal).**

// // ---

// // ### **Inorder Traversal là gì?**

// // * Với **một node bất kỳ**, thứ tự duyệt là:

// // 1. **Duyệt trái** (left subtree)
// // 2. **Thăm node gốc** (root)
// // 3. **Duyệt phải** (right subtree)

// // ---

// // ### **Ví dụ 1:**

// // Input:

// // ```
// // 1
// // \
// // 2
// // /
// // 3
// // ```

// // Duyệt **Inorder**:

// // * Đi qua cây con trái của `1` → rỗng
// // * Thăm `1`
// // * Đi qua cây con phải (`2`) → trước tiên đi trái (`3`) rồi mới đến `2`

// // 👉 Kết quả = `[1,3,2]`

// // ---

// // ### **Ví dụ 2:**

// // Input: `root = []`
// // 👉 Output = `[]` (cây rỗng)

// // ---

// // ### **Ví dụ 3:**

// // Input: `root = [1]`
// // 👉 Output = `[1]`

// // ---

// // ### **Bản chất đề bài:**

// // * Đây là một bài cơ bản về duyệt cây nhị phân.
// // * Có 2 cách chính để làm:

// // 1. **Đệ quy (recursive)** → đơn giản, dễ viết
// // 2. **Dùng stack (iterative)** → tránh tràn stack nếu cây rất sâu

// // ---

// // Bạn có muốn mình viết luôn **code Java giải bài này** (cả cách đệ quy và
// không đệ quy) để dễ hiểu hơn không?
// Ok 👌 mình giải thích chi tiết **thuật toán Inorder Traversal** trong code
// Java bạn gửi:

// ---

// ### **Đề bài**

// Duyệt cây nhị phân theo thứ tự **Inorder** (Trái → Gốc → Phải) và trả về danh
// sách các giá trị theo thứ tự đó.

// ---

// ### **Thuật toán trong code**

// #### 1. Hàm `inorderTraversal(TreeNode root)`

// * Tạo một danh sách rỗng `result` để chứa kết quả.
// * Gọi hàm đệ quy `inorder(root, result)` để duyệt cây.
// * Trả về `result`.

// #### 2. Hàm đệ quy `inorder(TreeNode node, List<Integer> result)`

// * **Điều kiện dừng**: Nếu `node == null` thì return (cây rỗng).
// * Nếu không:

// 1. Gọi đệ quy với cây con **trái** → `inorder(node.left, result)`.
// 2. Thêm giá trị của **node hiện tại** vào danh sách → `result.add(node.val)`.
// 3. Gọi đệ quy với cây con **phải** → `inorder(node.right, result)`.

// ---

// ### **Ví dụ minh họa**

// Cho cây nhị phân:

// ```
// 1
// \
// 2
// /
// 3
// ```

// * Bắt đầu từ root (1)
// → đi sang trái (null) → return
// → thêm `1` vào kết quả
// → đi sang phải (2)

// * Tại node (2):
// → đi sang trái (3)
// → tại node (3): trái (null) → thêm `3` → phải (null)
// → thêm `2`

// Kết quả cuối cùng: **\[1, 3, 2]**

// ---

// ### **Độ phức tạp**

// * ⏱ Thời gian: `O(n)` (mỗi node được thăm đúng 1 lần).
// * 🛠 Bộ nhớ: `O(h)` (h là chiều cao cây, do dùng ngăn xếp đệ quy).

// ---

// 👉 Nói ngắn gọn: thuật toán này **duyệt cây theo thứ tự trái → gốc → phải**,
// dùng đệ quy để xử lý.

// Bạn có muốn mình viết thêm **phiên bản iterative (dùng Stack, không cần đệ
// quy)** để so sánh không?
// Ok, mình vẽ sơ đồ luôn nhé 🚀

// Rồi 👍 mình giải thích chi tiết luôn đoạn hàm `buildTree` này:

// ---

// ###🎯**Mục đích**

// Hàm`buildTree(Integer[] arr)` được dùng để **xây dựng cây nhị phân** từ mảng
// `arr` theo **cách duyệt level-order (BFS)**.
// 👉 Tức là `arr` mô tả cây theo

// từng tầng (giống như input LeetCode).

// * Nếu phần tử trong mảng là `null` thì nghĩa là node đó **không tồn tại**.
// * Nếu là số nguyên thì tạo node mới.

// Ví dụ:

// ```
// arr = [1, null, 2, 3, null]
// ```

// sẽ ra cây:

// ```
// 1
// / \
// null 2
// /
// 3
// ```

// ---

// ### 📑 **Giải thích từng bước**

// ```java
// if (arr.length == 0 || arr[0] == null) return null;
// ```

// ➡️ Nếu mảng rỗng hoặc gốc là `null` thì không có cây nào cả → trả về `null`.

// ---

// ```java
// TreeNode root = new TreeNode(arr[0]);
// Queue<TreeNode> queue = new LinkedList<>();
// queue.add(root);
// ```

// ➡️ Tạo **gốc** từ `arr[0]`.
// ➡️ Sử dụng **queue (hàng đợi)** để xây dựng cây theo từng tầng (BFS).

// ---

// ```java
// int i = 1;
// while (!queue.isEmpty() && i < arr.length) {
// TreeNode current = queue.poll();
// ```

// ➡️ Duyệt qua từng node trong hàng đợi.
// ➡️ Mỗi lần lấy một node (`current`) ra, rồi gán **con trái** và **con phải**
// cho nó dựa vào `arr[i]` và `arr[i+1]`.

// ---

// ```java
// // Gán con trái
// if (i < arr.length && arr[i] != null) {
// current.left = new TreeNode(arr[i]);
// queue.add(current.left);
// }
// i++;
// ```

// ➡️ Nếu còn phần tử

// trong mảng (`i < arr.length`) và không phải `null`, thì tạo **node con
// trái**.
// ➡️ Sau đó cho con trái vào queue để xử lý tiếp.

// ---

// ```java
// // Gán con phải
// if (i < arr.length && arr[i] != null) {
// current.right = new TreeNode(arr[i]);
// queue.add(current.right);
// }
// i++;
// ```

// ➡️ Làm tương tự cho **con phải**.

// ---

// ```java
// return root;
// ```

// ➡️ Sau khi duyệt hết mảng → trả về **cây nhị phân gốc**.

// ---

// ### 🔑 Tóm gọn thuật toán

// 1. Tạo root từ `arr[0]`.
// 2. Dùng queue để duyệt BFS.
// 3. Với mỗi node lấy ra từ queue:

// * Gán con trái bằng `arr[i]` nếu có.
// * Gán con phải bằng `arr[i+1]` nếu có.
// 4. Tiếp tục cho đến hết mảng.

// ---

// 👉 Hàm này chính là cách "dịch" mảng kiểu LeetCode `[1,null,2,3]` thành một
// cây `TreeNode` thực sự trong Java.

// Bạn có muốn mình viết thêm ví dụ **input → output cây** minh họa chi tiết
// từng bước chạy không?
