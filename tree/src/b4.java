
// 100. Same Tree
import java.util.*;

class b4 {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Nhập số phần tử cây p
        int n1 = sc.nextInt();
        int[] arr1 = new int[n1];
        for (int i = 0; i < n1; i++)
            arr1[i] = sc.nextInt();
        TreeNode p = buildTree(arr1);

        // Nhập số phần tử cây q
        int n2 = sc.nextInt();
        int[] arr2 = new int[n2];
        for (int i = 0; i < n2; i++)
            arr2[i] = sc.nextInt();
        TreeNode q = buildTree(arr2);

        // Gọi hàm so sánh
        System.out.println(isSameTree(p, q));

        sc.close();
    }

    // Hàm so sánh 2 cây bằng BFS
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(p);
        queue.add(q);

        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();

            // Nếu cả 2 null → bỏ qua
            if (node1 == null && node2 == null)
                continue;

            // Nếu 1 null, 1 không → khác nhau
            if (node1 == null || node2 == null)
                return false;

            // Nếu giá trị khác nhau → khác
            if (node1.val != node2.val)
                return false;

            // Thêm các node con vào queue để so sánh tiếp
            queue.add(node1.left);
            queue.add(node2.left);

            queue.add(node1.right);
            queue.add(node2.right);
        }
        return true;
    }

    // Hàm dựng cây nhị phân từ mảng level-order (dùng -1 là null)
    public static TreeNode buildTree(int[] arr) {
        if (arr.length == 0 || arr[0] == -1)
            return null;

        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while (!queue.isEmpty() && i < arr.length) {
            TreeNode curr = queue.poll();

            // Xử lý con trái
            if (arr[i] != -1) {
                curr.left = new TreeNode(arr[i]);
                queue.add(curr.left);
            }
            i++;

            if (i >= arr.length)
                break;

            // Xử lý con phải
            if (arr[i] != -1) {
                curr.right = new TreeNode(arr[i]);
                queue.add(curr.right);
            }
            i++;
        }
        return root;
    }

    // Định nghĩa node cây nhị phân
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
            left = right = null;
        }
    }
}
