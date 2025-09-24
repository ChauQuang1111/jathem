
// Word Search
import java.util.*;

class b3 {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int m = sc.nextInt();

        int n = sc.nextInt();
        sc.nextLine(); // đọc bỏ newline

        char[][] board = new char[m][n];
        for (int i = 0; i < m; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < n; j++) {
                board[i][j] = line.charAt(j);
            }
        }

        String word = sc.nextLine();

        boolean exist = exist(board, word);
        System.out.println(exist);
        sc.close();
    }

    // Giải thuật kiểm tra từ có tồn tại trên board hay không

    public static boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        if (word.length() == 0)
            return true;

        // 1) Đếm tần suất ký tự trên board
        int[] cnt = new int[128];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                cnt[board[i][j]]++;

        // 2) Đếm tần suất ký tự trong word
        int[] need = new int[128];
        for (int k = 0; k < word.length(); k++)
            need[word.charAt(k)]++;

        // 3) Kiểm tra khả năng tồn tại (nếu cần nhiều ký tự hơn board → false)
        for (int ch = 0; ch < 128; ch++)
            if (need[ch] > 0 && need[ch] > cnt[ch])
                return false;

        // 4) Chọn hướng bắt đầu từ ký tự ít xuất hiện hơn để tối ưu
        char first = word.charAt(0), last = word.charAt(word.length() - 1);
        if (cnt[first] > cnt[last]) {
            word = new StringBuilder(word).reverse().toString();
        }

        // 5) Thử DFS từ mọi ô
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, word, 0, i, j))
                    return true;
            }
        }
        return false;
    }

    // DFS + backtracking
    public static boolean dfs(char[][] b, String w, int k, int r, int c) {
        // Base case: đã tìm đủ ký tự
        if (k == w.length())
            return true;
        // Ra ngoài biên hoặc ký tự không khớp
        if (r < 0 || c < 0 || r >= b.length || c >= b[0].length)
            return false;
        if (b[r][c] != w.charAt(k))
            return false;

        char saved = b[r][c];
        b[r][c] = '\0'; // đánh dấu visited

        // Thử 4 hướng: xuống, lên, phải, trái
        boolean found = dfs(b, w, k + 1, r + 1, c) ||
                dfs(b, w, k + 1, r - 1, c) ||
                dfs(b, w, k + 1, r, c + 1) ||
                dfs(b, w, k + 1, r, c - 1);

        b[r][c] = saved; // phục hồi ô
        return found;
    }
}
// Được, mình sẽ giải thích thuật toán **Word Search** trong đoạn code bạn đưa
// theo từng bước và ý tưởng **optimization**.

// ---

// ### **1️⃣ Kiểm tra khả năng tồn tại từ (Feasibility Check)**

// ```java
// int[] cnt = new int[128];
// for (int i = 0; i < m; i++)
// for (int j = 0; j < n; j++)
// cnt[board[i][j]]++;
// ```

// * Tạo mảng `cnt` để đếm tần suất mỗi ký tự trên **bảng**.
// * Mục đích: nếu từ `word` cần một ký tự nhiều hơn số ký tự có trên bảng thì
// chắc chắn **không tồn tại** → trả về `false` sớm.

// ```java
// int[] need = new int[128];
// for (int k = 0; k < word.length(); k++) need[word.charAt(k)]++;
// for (int ch = 0; ch < 128; ch++)
// if (need[ch] > 0 && need[ch] > cnt[ch]) return false;
// ```

// * Tạo mảng `need` đếm tần suất ký tự trong `word`.
// * So sánh với `cnt`, nếu có ký tự `word` cần mà bảng không đủ → kết thúc sớm.

// ---

// ### **2️⃣ Tối ưu hướng tìm kiếm (Rarity Heuristic)**

// ```java
// char first = word.charAt(0), last = word.charAt(word.length() - 1);
// if (cnt[first] > cnt[last]) {
// word = new StringBuilder(word).reverse().toString();
// }
// ```

// * Chọn **đầu hoặc cuối từ ít xuất hiện hơn** để bắt đầu DFS.
// * Lý do: ký tự **hiếm** xuất hiện càng ít thì khả năng thất bại sớm càng cao
// → giảm số lượng đường đi DFS cần thử.
// * Đây là một **heuristic** để tối ưu DFS.

// ---

// ### **3️⃣ Backtracking với DFS**

// ```java
// for (int i = 0; i < m; i++) {
// for (int j = 0; j < n; j++) {
// if (dfs(board, word, 0, i, j)) return true;
// }
// }
// ```

// * Thử **DFS từ từng ô** trong bảng.
// * Nếu DFS trả về `true` từ bất kỳ ô nào → từ `word` tồn tại.

// ---

// ### **4️⃣ DFS và đánh dấu ô đã đi (Visited)**

// ```java
// private boolean dfs(char[][] b, String w, int k, int r, int c) {
// if (k == w.length()) return true; // đã tìm đủ từ
// if (r < 0 || c < 0 || r >= b.length || c >= b[0].length) return false; // ra
// ngoài biên
// if (b[r][c] != w.charAt(k)) return false; // ký tự không khớp

// char saved = b[r][c];
// b[r][c] = '\0'; // đánh dấu đã đi (visited)

// // Thử 4 hướng: xuống, lên, phải, trái
// if (dfs(b, w, k + 1, r + 1, c) ||
// dfs(b, w, k + 1, r - 1, c) ||
// dfs(b, w, k + 1, r, c + 1) ||
// dfs(b, w, k + 1, r, c - 1)) {
// b[r][c] = saved; // phục hồi ô
// return true;
// }

// b[r][c] = saved; // phục hồi ô sau khi thử xong
// return false;
// }
// ```

// * **Base case:**

// * `k == w.length()` → đã tìm đủ các ký tự → `true`.
// * Ký tự không khớp hoặc ra ngoài biên → `false`.
// * **Visited:**

// * Tạm thay ký tự ô bằng `'\0'` để tránh đi lại.
// * Sau khi DFS xong → phục hồi ký tự ban đầu.
// * **DFS 4 hướng:** thử xuống, lên, phải, trái.
// * Nếu bất kỳ hướng nào thành công → trả về `true`.

// ---

// ### **5️⃣ Ý tưởng tổng thể**

// 1. Kiểm tra sơ bộ xem từ `word` có khả năng tồn tại trên bảng hay không.
// 2. Tối ưu điểm xuất phát: bắt đầu từ ký tự ít xuất hiện hơn.
// 3. Sử dụng **DFS + backtracking** để thử tất cả đường đi hợp lệ, đánh dấu
// visited bằng cách tạm đổi ký tự.
// 4. Nếu tìm được toàn bộ từ → `true`, nếu thử hết các ô vẫn không tìm được →
// `false`.

// ---

// ### **Time & Space Complexity**

// * **Time:** O(M*N \* 4^L), với M*N là kích thước bảng, L là độ dài từ.
// * **Space:** O(L) cho stack DFS (chiều dài từ) + O(1) không dùng mảng visited
// vì đã ghi đè trực tiếp vào bảng.

// ---

// Nếu muốn, mình có thể viết **phiên bản Java hoàn chỉnh với Scanner** để nhập
// **board** và **word** thử nghiệm luôn.

// Bạn có muốn mình làm không?

// Ok, mình giải thích rõ đề **Word Search** (LeetCode 79) cho bạn nhé:

// ---

// ### Đề bài

// Bạn được cho một **ma trận ký tự** `board` (2D grid) và một **chuỗi từ cần
// tìm** `word`.

// Hãy kiểm tra xem có thể tìm thấy `word` trong `board` hay không.

// **Quy tắc di chuyển:**

// * Bắt đầu từ bất kỳ ô nào trong `board`.
// * Tại mỗi bước, có thể đi **lên, xuống, trái, phải** (4 hướng).
// * **Không được dùng lại một ô** đã được chọn trong cùng một lần tìm.

// 👉 Nếu tìm được thì trả về `true`, ngược lại trả về `false`.

// ---

// ### Ví dụ 1

// ```
// board = [
// ['A','B','C','E'],
// ['S','F','C','S'],
// ['A','D','E','E']
// ]
// word = "ABCCED"
// ```

// Lời giải:

// * Bắt đầu ở `board[0][0] = 'A'`.
// * Đi → `B`, → `C`, → `C`, ↓ `E`, ↓ `D`.
// * Ghép lại được `"ABCCED"` → **True**.

// ---

// ### Ví dụ 2

// ```
// board = [
// ['A','B','C','E'],
// ['S','F','C','S'],
// ['A','D','E','E']
// ]
// word = "SEE"
// ```

// Lời giải:

// * Bắt đầu ở `board[1][3] = 'S'`.
// * Xuống → `E`, xuống → `E`.
// * Ghép lại `"SEE"` → **True**.

// ---

// ### Ví dụ 3

// ```
// board = [
// ['A','B','C','E'],
// ['S','F','C','S'],
// ['A','D','E','E']
// ]
// word = "ABCB"
// ```

// Lời giải:

// * Không thể tìm ra `"ABCB"` mà không dùng lại một ô.
// * Kết quả → **False**.

// ---

// ### Ý tưởng giải thuật

// Đây là một bài **Backtracking (DFS trên grid)**:

// 1. Duyệt từng ô trong `board`.
// 2. Nếu ô đó = ký tự đầu tiên của `word` → bắt đầu tìm kiếm DFS.
// 3. Trong DFS:

// * Kiểm tra nếu ký tự hiện tại khớp.
// * Đánh dấu ô này là **đã dùng** (thường tạm đổi thành ký tự đặc biệt, ví dụ
// `'#'`).
// * Gọi đệ quy tìm ký tự tiếp theo theo 4 hướng.
// * Quay lui (backtrack): khôi phục ký tự cũ để các lần tìm khác không bị ảnh
// hưởng.
// 4. Nếu tại bất kỳ điểm nào tìm được đủ các ký tự trong `word` → trả về
// `true`.
// 5. Nếu duyệt hết mà không tìm được → trả về `false`.

// ---

// Bạn có muốn mình viết code minh họa luôn (Python hoặc Java) không?
