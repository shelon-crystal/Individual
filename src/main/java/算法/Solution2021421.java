package 算法;

import java.util.HashMap;
import java.util.Map;

/**
 * @author maxcs
 */
public class Solution2021421 {

    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++ ){
            // 如果存在同key的元素
            if (map.containsKey(s.charAt(i))){
                // 更新起始点，前提这个元素位置index + 1要在start之后
                start = Math.max(map.get(s.charAt(i)) + 1, start);
            }
            map.put(s.charAt(i),i);
            // 比较之前的最大值和当前遍历到的长度（当前位置 - 起始位置 + 1）
            max = Math.max(max, i - start + 1);
        }
        return max;
    }
}
