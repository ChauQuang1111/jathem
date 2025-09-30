
// Unique Binary Search Trees 
import java.util.*;

// Định nghĩa class TreeNode (giống LeetCode)
class TreeNode {
  int val;
  TreeNode left, right;

  TreeNode() {
  }

  TreeNode(int val) {
    this.val = val;
  }

  TreeNode(int val, TreeNode left, TreeNode right) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

class Solution {
  // dp[conf] sẽ lưu tất cả cây có thể sinh ra từ trạng thái "conf"
  // (memoization để tránh tính lại)
  private ArrayList<TreeNode>[] dp;

  // Hàm chính: sinh tất cả BST với giá trị 1..n
  @SuppressWarnings("unchecked")
  public List<TreeNode> generateTrees(int n) {
    // Tạo mảng dp có 256 phần tử (2^8, giả sử n <= 8)
    this.dp = (ArrayList<TreeNode>[]) new ArrayList[256];
    // Bắt đầu với trạng thái conf = 0 (chưa chọn số nào)
    return gen(n, (char) 0);
  }

  // Hàm đệ quy gen: sinh tất cả cây từ tập giá trị chưa bị "loại bỏ" (conf)
  private ArrayList<TreeNode> gen(int n, char conf) {
    // Nếu trạng thái này đã tính → trả về luôn
    ArrayList<TreeNode> res = dp[conf];
    if (res != null)
      return res;

    res = new ArrayList<>();

    // Duyệt qua tất cả các số i (0..n-1) tương ứng giá trị (i+1)
    for (char i = 0; i < n; i++) {
      // Nếu bit i chưa bật trong conf → tức là giá trị i+1 chưa bị loại
      if ((conf & (1 << i)) == 0) {
        // Tính cây con bên trái:
        // bật tất cả bit từ i trở lên (chỉ còn các số < i)
        ArrayList<TreeNode> lefts = gen(n, (char) (conf | ('ÿ' << i) & 'ÿ'));

        // Tính cây con bên phải:
        // bật tất cả bit từ 0..i (chỉ còn các số > i)
        ArrayList<TreeNode> rights = gen(n, (char) (conf | ('ÿ' >> (7 - i)) & 'ÿ'));

        // Ghép mọi cây trái và cây phải
        for (TreeNode left : lefts) {
          for (TreeNode right : rights) {
            TreeNode root = new TreeNode(i + 1, left, right);
            res.add(root);
          }
        }
      }
    }

    // Nếu không sinh được cây nào → nghĩa là tập rỗng → add null
    if (res.isEmpty())
      res.add(null);

    // Lưu vào dp
    dp[conf] = res;
    return res;
  }

  // Hàm tiện ích: in cây theo dạng Preorder (root-left-right)
  private static void printTree(TreeNode root) {
    if (root == null) {
      System.out.print("null ");
      return;
    }
    System.out.print(root.val + " ");
    printTree(root.left);
    printTree(root.right);
  }

  static Scanner sc = new Scanner(System.in);

  // Hàm main: nhập n từ Scanner và in ra các cây
  public static void main(String[] args) {

    System.out.print("Nhập n: ");
    int n = sc.nextInt();

    Solution sol = new Solution();
    List<TreeNode> trees = sol.generateTrees(n);

    System.out.println("Có tất cả " + trees.size() + " cây BST:");
    int idx = 1;
    for (TreeNode t : trees) {
      System.out.print("Cây " + idx++ + ": ");
      printTree(t);
      System.out.println();
    }
  }
}

// À, đây là **LeetCode 95. Unique Binary Search Trees II** 👍.
// Đề: Cho số nguyên `n`, bạn cần sinh ra **tất cả các cấu trúc cây nhị phân tìm
// kiếm (BST)** có thể chứa các giá trị từ `1` đến `n`.

// ---

// ## Ý tưởng chính

// * **BST** có đặc tính:

// * Mọi node bên trái < root.
// * Mọi node bên phải > root.

// * Để sinh tất cả BST:

// * Ta thử chọn **mỗi số i (1..n)** làm **gốc (root)**.
// * Khi chọn `i` làm gốc:

// * Các số từ `1..i-1` phải nằm ở **cây con bên trái**.
// * Các số từ `i+1..n` phải nằm ở **cây con bên phải**.
// * Vấn đề còn lại: sinh **tất cả BST có thể** từ dãy số `[1..i-1]` và
// `[i+1..n]`.
// * Sau đó, **ghép** từng cây bên trái với từng cây bên phải → tạo ra nhiều BST
// khác nhau.

// * Đây là một dạng **đệ quy + chia để trị**.

// * Nếu đoạn `[l..r]` rỗng → trả về danh sách có `null` (nghĩa là không có cây
// con).

// ---

// ## Thuật toán (đệ quy)

// ```text
// function generate(l, r):
// if l > r:
// return [null] // không có cây

// result = []
// for i từ l đến r:
// leftTrees = generate(l, i-1) // tất cả cây con trái
// rightTrees = generate(i+1, r) // tất cả cây con phải

// // ghép từng left với từng right
// for mỗi cây L trong leftTrees:
// for mỗi cây R trong rightTrees:
// root = new TreeNode(i)
// root.left = L
// root.right = R
// result.add(root)

// return result
// ```

// Cuối cùng, gọi `generate(1, n)` để lấy kết quả.

// ---

// ## Ví dụ: n = 3

// Các số là `{1,2,3}`.

// 1. **Chọn 1 làm root**:

// * Left = ∅ → [null]
// * Right = cây từ {2,3} → có 2 cấu hình
// → sinh ra 2 cây.

// 2. **Chọn 2 làm root**:

// * Left = {1} → chỉ 1 cây
// * Right = {3} → chỉ 1 cây
// → sinh ra 1 cây.

// 3. **Chọn 3 làm root**:

// * Left = cây từ {1,2} → có 2 cấu hình
// * Right = ∅ → [null]
// → sinh ra 2 cây.

// Tổng cộng: 5 cây BST khác nhau. ✅

// ---

// ## Độ phức tạp

// * Số lượng cây sinh ra chính là **Catalan number**:
// ( C_n = \frac{1}{n+1} \binom{2n}{n} ).
// * Thời gian: **O(C_n * n)** (do mỗi lần chọn root và ghép).

// ---

// 👉 Bạn có muốn mình viết luôn code **Java** hoàn chỉnh cho bài này không?
// À, đoạn code bạn đưa là một cách giải khá **dị** cho LeetCode 95 🙂.
// Thay vì dùng đệ quy `build(start, end)` bình thường, tác giả encode trạng
// thái bằng **bitmask** để biểu diễn những số đã được dùng rồi, sau đó sinh
// cây. Mình phân tích chi tiết nhé:

// ---

// ## 1. Ý tưởng chung

// * Với `n` số từ `1..n`, thay vì cắt đoạn `[start..end]`, code này dùng một
// **bitmask `conf`** để đánh dấu những số nào đã được “loại bỏ” (tức là đã
// không thể dùng vì đã ở nhánh khác).
// * `gen(n, conf)` sẽ trả về tất cả cây có thể sinh ra từ những số **chưa bị
// đánh dấu trong `conf`**.
// * Mỗi lần chọn một số `i` làm root:

// * Xây cây trái từ những số nhỏ hơn `i` còn lại.
// * Xây cây phải từ những số lớn hơn `i` còn lại.
// * Kết quả là toàn bộ BST có thể.

// ---

// ## 2. Giải thích từng đoạn code

// ```java
// dp = (ArrayList<TreeNode>[]) new ArrayList[256];
// ```

// * `dp` là mảng để memo (lưu kết quả đã tính).
// * Vì `conf` là kiểu `char` (16 bit, nhưng trong code này có vẻ tác giả giả
// định n ≤ 8 để vừa 1 byte), nên số trạng thái là 2^n ≤ 256.

// ---

// ```java
// return gen(n, '\0');
// ```

// * Bắt đầu với `conf = 0` (chưa loại bỏ số nào).

// ---

// ```java
// ArrayList<TreeNode> res = dp[conf];
// if(res != null) return res;
// res = new ArrayList<>();
// ```

// * Nếu đã tính trạng thái này rồi thì trả về luôn (memoization).
// * Ngược lại, khởi tạo list mới.

// ---

// ```java
// for(char i=0; i<n; i++) if((conf & (1 << i)) == 0) {
// ```

// * Duyệt qua từng số `i` (0-based, tức là giá trị node = i+1).
// * Nếu bit thứ `i` chưa bật trong `conf` → nghĩa là số này còn khả dụng để
// chọn làm root.

// ---

// ```java
// ArrayList<TreeNode> lefts = gen(n, (char) (conf | ('ÿ' << i) & 'ÿ')),
// rights = gen(n, (char) (conf | ('ÿ' >> (7-i)) & 'ÿ'));
// ```

// * Đây là đoạn **hack** hơi khó đọc:

// * `'ÿ'` trong Java là ký tự `ÿ` (mã Unicode 255, tức là `0xFF` =
// `11111111b`).
// * `'ÿ' << i`: tạo mask với tất cả bit từ `i` trở lên là `1`.
// * `'ÿ' >> (7-i)`: tạo mask với tất cả bit từ `0..i` là `1`.
// * `(conf | mask) & 'ÿ'`: bật thêm các bit tương ứng.

// 👉 Ý nghĩa:

// * Khi chọn `i` làm root:

// * **Left** = gọi `gen` nhưng bật tất cả bit từ `i` trở lên → chỉ giữ lại các
// số nhỏ hơn `i`.
// * **Right** = gọi `gen` nhưng bật tất cả bit từ `0..i` → chỉ giữ lại các số
// lớn hơn `i`.

// ---

// ```java
// for(TreeNode left : lefts) for(TreeNode right : rights)
// res.add(new TreeNode(i+1, left, right));
// ```

// * Kết hợp mọi cây trái và cây phải → tạo node root = i+1.

// ---

// ```java
// if(res.isEmpty()) res.add(null);
// dp[conf] = res;
// return res;
// ```

// * Nếu không sinh được cây nào (tức không còn số nào) thì thêm `null` (trường
// hợp cây rỗng).
// * Lưu kết quả vào `dp[conf]` để tránh tính lại.

// ---

// ## 3. Tóm gọn thuật toán

// * Sử dụng **bitmask `conf`** để biểu diễn tập các số đã bị loại khỏi
// consideration.
// * Mỗi bước chọn một số `i` chưa bị loại → làm root.
// * Sinh cây trái từ các số nhỏ hơn `i` (ẩn bằng mask).
// * Sinh cây phải từ các số lớn hơn `i`.
// * Ghép tất cả lại.
// * Dùng `dp` để cache kết quả của mỗi trạng thái `conf`.

// ---

// ## 4. Nhận xét

// * Đây là cách làm khá **tricky** và khó đọc, nhưng vẫn đúng.
// * Phức tạp hơn so với cách đệ quy `[start..end]`, nhưng ý tưởng là tương tự:
// chọn root, chia trái/phải, ghép.
// * Ưu điểm: tận dụng memoization trên bitmask.
// * Nhược điểm: code rối, khó hiểu, khó mở rộng cho `n > 8`.

// ---

// 👉 Bạn có muốn mình viết lại version **rõ ràng và dễ hiểu hơn** (dùng
// interval `[start..end]`) và so sánh hiệu suất với cách bitmask này không?
