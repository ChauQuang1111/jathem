
// 70. Climbing Stairs
import java.util.Scanner;

public class b2 {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int n = sc.nextInt();

        int ways = climbStairs(n);
        sc.close();
        System.out.println(n + " là: " + ways);
    }

    public static int climbStairs(int n) {
        // Nếu n = 1 hoặc n = 2 thì có đúng n cách
        if (n <= 2)
            return n;

        // first: số cách đến bậc (i-2)
        // second: số cách đến bậc (i-1)
        int first = 1, second = 2, result = 0;

        // Tính từ bậc 3 đến bậc n
        for (int i = 3; i <= n; i++) {
            result = first + second; // dp[i] = dp[i-1] + dp[i-2]
            first = second; // dịch first lên 1 bậc (i-2 -> i-1)
            second = result; // dịch second lên 1 bậc (i-1 -> i)
        }

        // second lúc này chính là số cách đến bậc n
        return second;
    }

}

// Rồi👍mình sẽ giải thích thuật toán của code Java này+thêm chú thích chi tiết
// cho bạn.

// ---

// ##💡Ý tưởng

// Đây là cách viết**tối ưu bộ nhớ**(so với dùng mảng`dp`).

// *Vẫn dựa vào công thức**Fibonacci**:

// ```dp[i]=dp[i-1]+dp[i-2]```*Thay vì lưu cả mảng`dp`,ta chỉ cần lưu**2 biến**:

// *`first`=số cách để leo đến bậc(i-2)*`second`=số cách để leo đến bậc(i-1)*Mỗi
// lần tính bước tiếp theo thì cập nhật lại`first`và`second`.

// ---

// ##📝Code với chú thích

// ```java
// class Solution {
// public int climbStairs(int n) {
// // Nếu n = 1 hoặc n = 2 thì có đúng n cách
// if (n <= 2)
// return n;

// // first: số cách đến bậc (i-2)
// // second: số cách đến bậc (i-1)
// int first = 1, second = 2, result = 0;

// // Tính từ bậc 3 đến bậc n
// for (int i = 3; i <= n; i++) {
// result = first + second; // dp[i] = dp[i-1] + dp[i-2]
// first = second; // dịch first lên 1 bậc (i-2 -> i-1)
// second = result; // dịch second lên 1 bậc (i-1 -> i)
// }

// // second lúc này chính là số cách đến bậc n
// return second;
// }
// }```

// ---

// ##🔎

// Ví dụ
// chạy với`n=5`

// *
// Ban đầu:`first=1`(bậc 1),`second=2`(bậc 2)

// *
// Vòng lặp:

// *i=3→result=1+2=3→
// cập nhật:`first=2`,`second=3`*i=4→result=2+3=5→
// cập nhật:`first=3`,`second=5`*i=5→result=3+5=8→
// cập nhật:`first=5`,`second=8`

// 👉
// Kết quả:`second=8`→có**8 cách**leo 5 bậc.

// ---

// ⚡
// Thuật toán
// này chỉ dùng**O(1) bộ nhớ** thay vì O(n), nhưng độ phức tạp thời gian vẫn là
// **O(n)**.

// Bạn có muốn mình viết thêm **phiên bản đệ quy có memoization** để so sánh
// không?
// Ok 👍 mình giải thích kỹ đoạn này cho bạn, vì đây chính là “trick” để không
// cần dùng mảng `dp[]`.

// ---

// ### 1. Ý tưởng

// Ở mỗi bước lặp, ta cần giữ 2 giá trị trước đó để tính số cách đến bậc hiện
// tại:

// ```
// dp[i] = dp[i-1] + dp[i-2]
// ```

// Thay vì lưu nguyên cả mảng, ta dùng 2 biến:

// * `first` = số cách

// đến bậc (i-2)
// * `second` = số cách

// đến bậc (i-1)
// * `result` = số cách đến bậc i

// ---

// ### 2. Tại sao phải cập nhật?

// Khi đã tính xong `result` (tức là `dp[i]`), để tiếp tục vòng lặp cho `i+1`,
// ta phải **dời cửa sổ** về phía trước:

// * Lúc này `second` (bậc i-1) sẽ trở thành `first` (bậc (i+1)-2).
// * Lúc này `result` (bậc i) sẽ trở thành `second` (bậc (i+1)-1).

// 👉 Vì thế ta có:

// ```java
// first = second; // dịch first từ (i-2) -> (i-1)
// second = result; // dịch second từ (i-1) -> i
// ```

// ---

// ### 3. Ví dụ minh họa với n = 5

// Bắt đầu:

// ```
// first = 1 (bậc 1)
// second = 2 (bậc 2)
// ```

// * i = 3:
// `result = first + second = 1 + 2 = 3`
// Cập nhật:
// `first = 2, second = 3`

// * i = 4:
// `result = 2 + 3 = 5`
// Cập nhật:
// `first = 3, second = 5`

// * i = 5:
// `result = 3 + 5 = 8`
// Cập nhật:
// `first = 5, second = 8`

// Kết thúc vòng lặp:
// 👉 `second = 8` chính là số cách leo lên bậc thứ 5.

// ---

// ✅ Nói ngắn gọn:

// * `first` và `second` luôn giữ **2 giá trị gần nhất**.
// * Mỗi lần tính xong `result`, ta **dời cửa sổ** bằng cách gán lại `first` và
// `second`.

// ---

// Bạn có muốn mình vẽ **sơ đồ cửa sổ trượt** để thấy rõ hơn `first → second →
// result` di chuyển thế nào không?
// À, câu hỏi rất hay 👍.

// Bạn để ý trong code Java của bạn:

// ```java
// for (int i = 3; i <= n; i++) {
// result = first + second;
// first = second;
// second = result;
// }
// // second lúc này chính là số cách đến bậc n
// return second;
// ```

// ### 1. Tại sao không return `result`?

// * `result` chỉ được cập nhật **trong vòng lặp**.
// * Nếu `n = 1` hoặc `n = 2`, vòng lặp **không chạy**, khi đó `result` vẫn = 0
// → trả về sai.
// * Nhưng `second` đã được khởi tạo

// từ trước (`second = 2` khi n ≥ 2), và sau vòng lặp `second` luôn giữ đúng giá
// trị của bậc cuối cùng.

// ---

// ### 2. Ví dụ n = 3

// * Ban đầu: `first = 1, second = 2`
// * i = 3:
// `result = 1 + 2 = 3`
// `first = 2, second = 3`

// Kết thúc vòng lặp:

// * `result = 3`
// * `second = 3`

// → cả 2 đều đúng, return cái nào cũng được.

// ---

// ### 3. Ví dụ n = 2

// * Vòng lặp **không chạy**.
// * `result` vẫn = 0 ❌ (sai).
// * `second = 2` ✅ (đúng).

// ---

// ### ✅ Kết luận

// Ta return `second` vì:

// * Với n = 1,2 thì `second` đã chính xác ngay từ đầu.
// * Với n ≥ 3 thì sau vòng lặp, `second` luôn cập nhật đúng số cách ở bậc n.
// * Còn `result` chỉ dùng như biến tạm trong vòng lặp, không đảm bảo cho mọi
// trường hợp.

// ---

// 👉 Bạn có muốn mình viết lại code sao cho luôn **return result** mà vẫn

// đúng (kể cả n = 1, 2) để dễ hiểu hơn không?
// Đúng rồi 🚀, bạn để ý kỹ thì trong **vòng lặp**:

// ```java
// result = first + second;
// first = second;
// second = result;
// ```

// sau mỗi lần lặp, ta luôn gán `second = result`.
// 👉 Điều đó có nghĩa là:

// * Kết thúc **mỗi vòng lặp**, `result` và `second` luôn có cùng giá trị.

// ---

// ### Vậy tại sao lại return `second` chứ không return `result`?

// * Với `n ≥ 3`:
// `result == second` → return cái nào cũng được.

// * Nhưng với `n = 1` hoặc `n = 2`:

// * Vòng lặp **không chạy** → `result` vẫn = 0 ❌ (sai).
// * `second` đã được gán đúng

// từ đầu (1 hoặc 2) ✅.

// 👉 Do đó, **return `second` an toàn hơn** cho mọi `n`.
// Còn `result` chỉ là biến tạm dùng trong vòng lặp, nó chưa chắc đúng nếu vòng
// lặp không chạy.

// ---

// 🔎 Ví dụ n = 2:

// * Code skip vòng lặp, không cập nhật `result`.
// * `result = 0` ❌.
// * `second = 2` ✅ → đúng số cách.

// ---

// Bạn có muốn mình viết lại một phiên bản **chỉ dùng `result`** (không cần
// `second`) để thấy rõ hơn sự khác biệt không?
// Mình giải thích lại thật chậm để bạn dễ hình dung nhé 👍:

// Trong code của bạn:

// ```java
// if (n <= 2) return n;

// int first = 1, second = 2, result = 0;
// for (int i = 3; i <= n; i++) {
// result = first + second;
// first = second;
// second = result;
// }
// return second;
// ```

// ---

// ### 1. Trước khi vào vòng lặp

// * `first = 1` (số cách leo đến bậc 1).
// * `second = 2` (số cách leo đến bậc 2).
// * `result = 0` (chỉ là biến tạm, chưa có ý nghĩa).

// ---

// ### 2. Trong vòng lặp

// Mỗi vòng, ta tính:

// ```java
// result = first + second; // số cách leo đến bậc i
// first = second; // dịch first lên i-1
// second = result; // dịch second lên i
// ```

// 👉 Sau bước này:

// * `result` và `second` **đúng bằng nhau**.
// * Nhưng **result chỉ đúng trong vòng lặp**, còn nếu vòng lặp không chạy thì
// `result vẫn = 0`.

// ---

// ### 3. Sau khi vòng lặp kết thúc

// * `second` luôn lưu số cách leo đến bậc **n cuối cùng**.
// * `result` cũng lưu số cách đó, nhưng **chỉ đúng nếu vòng lặp đã chạy ít nhất
// 1 lần**.

// ---

// ### 4. Trường hợp đặc biệt

// * Nếu `n = 1` hoặc `n = 2`: vòng lặp **không chạy** → `result = 0` ❌.
// * Nhưng `second` đã được gán từ đầu (`2` nếu n=2) → đúng ✅.

// ---

// 👉 Vì vậy, **`return second` là an toàn cho mọi trường hợp** (cả khi `n ≤ 2`
// và khi `n > 2`).
// Còn `return result` sẽ bị sai khi `n ≤ 2`.

// ---

// Bạn có muốn mình viết lại phiên bản **return result** nhưng có điều kiện xử
// lý riêng cho `n = 1` và `n = 2` để bạn thấy rõ sự khác biệt không?
// À,

// câu hỏi hay👍.

// Trong bài**Climbing Stairs**:

// *Nếu**n=1**→
// chỉ có**1 cách**(bước 1 bậc).*Nếu**n=2**→có**2 cách**:

// 1. 1+1 2. 2

// 👉
// Vì vậy
// ta đặt:

// ```java
// int first = 1; // số cách để leo đến bậc 1
// int second = 2; // số cách để leo đến bậc 2
// ```

// Sau đó, từ**n=3
// trở đi**,
// công thức lặp:

// ```

// ways(n) = ways(n-1) + ways(n-2)
// ```

// (bởi vì để đến bậc n, ta có thể:

// * từ bậc n-1 đi thêm 1 bước, hoặc
// * từ bậc n-2 đi thêm 2 bước).

// ---

// ### Vì sao không chọn `first = 0, second = 1`?

// * Vì đây **không phải** dãy Fibonacci gốc bắt đầu từ 0,1.
// * Bài toán đã cho điều kiện cụ thể:

// * bậc 1 có 1 cách,
// * bậc 2 có 2 cách.

// 👉 Do đó ta khởi tạo **1 và 2** để phản ánh đúng số cách ở hai bậc đầu tiên.

// ---

// Bạn có muốn mình minh họa bằng ví dụ `n = 5` chạy từng vòng lặp để thấy rõ
// tại sao chọn `1,2` không?
