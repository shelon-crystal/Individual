package 算法;

/**
 * @author maxcs
 * 计算两个单链表组成的数字之和，链表中的数字倒序排列
 * 423->3 2 4
 * 24-> 7 2
 *
 */
public class Solution20210420 {

    /**
     *  定义单链表
     */
    public static class ListNode{
        int val;
        ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // pre用于指向头指针，以免遍历cur的时候丢失
        ListNode pre = new ListNode();
        // 用于遍历各个节点
        ListNode cur = pre;
        // 用于存储进位
        int carry = 0;
        while (l1 != null || l2 !=null){
            // 分别从两个链表取位，如果位为零，说明两个数位数不一样，则补零进行计算
            int value1 = l1 == null ? 0 :l1.val;
            int value2 = l2 == null ? 0 :l2.val;
            // 计算同位相加
            int sum = value1 + value2 + carry;
            // 计算进位
            carry = sum / 10;
            // 对其取余，保证当前位正确，如16%10=6
            sum = sum % 10;
            // 连接当前节点
            cur.next = new ListNode(sum);

            // 移动新链表指针
            cur = cur.next;

            // 移动两表指针，用于下次取位
            if (l1 != null){
                l1 = l1.next;
            }
            if (l2 != null){
                l2 = l2.next;
            }
        }
        // 最高位有进位
        if (carry == 1){
            cur.next = new ListNode(1);
        }
        // 返回头指针
        return pre.next;
    }

    public static void main(String[] args) {
        // 计算423 + 27
        // 链表1
        ListNode listNode1 = new ListNode(3);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(4);
        listNode1.next = listNode2;
        listNode2.next = listNode3;

        // 链表2
        ListNode listNode5 = new ListNode(9);
        ListNode listNode6 = new ListNode(2);
        listNode5.next = listNode6;

        // 计算
        ListNode resultNode = addTwoNumbers(listNode1,listNode5);
        // 遍历
        while (resultNode != null){
            System.out.println(resultNode.val);
            resultNode = resultNode.next;
        }


    }
}
