package 算法;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 *  寻找两个正序数组合并后的中位数
 * @author maxcs
 */
public class Solution2021425 {
    static final int DIVIDE = 2;

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {

        // 判断是否其中一个非空
        if (nums1 == null){
            return solveNull(nums2);
        }else if (nums2 == null){
            return solveNull(nums1);
        }

        // 数组合并
        int [] merge = mergeArray(nums1,nums2);
        int mergeLength = merge.length;

        // 计算中位数
        if (mergeLength % DIVIDE == 0){
            double mid = merge[mergeLength/2] + merge[(mergeLength/2)-1];
            mid = mid / 2;
            return mid;
        }else {
            return merge[mergeLength/2];
        }
    }

    /**
     *  解决其中一个数组为空
     * @param nums
     * @return
     */
    public static double solveNull(int[] nums){
        int length = nums.length;

        if ((length % DIVIDE) == 0){
            double mid = nums[length/2] + nums[(length/2)-1];
            mid = mid / 2;
            return  mid;
        }
        else {
            return (double) nums[length / 2];
        }
    }


    /**
     *  数组合并
     * @param nums1
     * @param nums2
     * @return merge
     */
    public static int [] mergeArray(int[] nums1, int[] nums2){
        int numLength1 = nums1.length;
        int numLength2 = nums2.length;
        int[] merge = new int[numLength1 + numLength2];

        int num1Index = 0;
        int num2Index = 0;
        int mergeIndex = 0;

        while (num1Index < numLength1 && num2Index < numLength2){
            if (nums1[num1Index] < nums2[num2Index]){

                merge[mergeIndex++] = nums1[num1Index++];
            }else {

                merge[mergeIndex++] = nums2[num2Index++];
            }
        }

        while (num1Index < numLength1){
            merge[mergeIndex++] = nums1[num1Index++];
        }
        while (num2Index < numLength2){
            merge[mergeIndex++] = nums2[num2Index++];
        }
        return merge;
    }

    public static void main(String[] args) {
        int [] a = {3,5,7,10};
        int [] b = {1,6,8,11,15};

        double result = findMedianSortedArrays(a,b);
        System.out.println(result);
    }


    /**
     *  思路2：边界法
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;
        if (n1>n2) {
            return findMedianSortedArrays2(nums2, nums1);
        }
        int k = (n1 + n2 + 1)/2;
        int left = 0;
        int right = n1;
        while(left < right){
            int m1 = left +(right - left)/2;
            int m2 = k - m1;
            if (nums1[m1] < nums2[m2-1]) {
                left = m1 + 1;
            } else {
                right = m1;
            }
        }
        int m1 = left;
        int m2 = k - left;
        int c1 = Math.max(m1 <= 0 ? Integer.MIN_VALUE : nums1[m1-1],
                m2 <= 0 ? Integer.MIN_VALUE : nums2[m2-1]);
        if ((n1 + n2) % 2 == 1) {
            return c1;
        }
        int c2 = Math.min( m1 >= n1 ? Integer.MAX_VALUE :nums1[m1],
                m2 >= n2 ? Integer.MAX_VALUE : nums2[m2]);
        return (c1 + c2) * 0.5;

    }


    /**
     *  方法3
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays3(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        //因为数组是从索引0开始的，因此我们在这里必须+1，即索引(k+1)的数，才是第k个数。
        int left = (n + m + 1) / 2;
        int right = (n + m + 2) / 2;
        //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k
        return (getKth(nums1, 0, n - 1, nums2, 0, m - 1, left) + getKth(nums1, 0, n - 1, nums2, 0, m - 1, right)) * 0.5;
    }
    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        //因为索引和算数不同6-0=6，但是是有7个数的，因为end初始就是数组长度-1构成的。
        //最后len代表当前数组(也可能是经过递归排除后的数组)，符合当前条件的元素的个数
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        //让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是 len1
        //就是如果len1长度小于len2，把getKth()中参数互换位置，即原来的len2就变成了len1，即len1，永远比len2小
        if (len1 > len2) {
            return getKth(nums2, start2, end2, nums1, start1, end1, k);
        }
        //如果一个数组中没有了元素，那么即从剩余数组nums2的其实start2开始加k再-1.
        //因为k代表个数，而不是索引，那么从nums2后再找k个数，那个就是start2 + k-1索引处就行了。因为还包含nums2[start2]也是一个数。因为它在上次迭代时并没有被排除
        if (len1 == 0) {
            return nums2[start2 + k - 1];
        }

        //如果k=1，表明最接近中位数了，即两个数组中start索引处，谁的值小，中位数就是谁(start索引之前表示经过迭代已经被排出的不合格的元素，即数组没被抛弃的逻辑上的范围是nums[start]--->nums[end])。
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }

        //为了防止数组长度小于 k/2,每次比较都会从当前数组所生长度和k/2作比较，取其中的小的(如果取大的，数组就会越界)
        //然后素组如果len1小于k / 2，表示数组经过下一次遍历就会到末尾，然后后面就会在那个剩余的数组中寻找中位数
        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        //如果nums1[i] > nums2[j]，表示nums2数组中包含j索引，之前的元素，逻辑上全部淘汰，即下次从J+1开始。
        //而k则变为k - (j - start2 + 1)，即减去逻辑上排出的元素的个数(要加1，因为索引相减，相对于实际排除的时要少一个的)
        if (nums1[i] > nums2[j]) {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        }
        else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }

}
