package test;

import junit.framework.TestCase;

import org.junit.Test;

public class BoundTest extends TestCase {
    @Test
    public void test() {

    }

    /**
     * 边界值
     */
    public int searchInsert(int[] nums, int target) {
        int l = 0;
        int h = nums.length;
        int m = (l + h) / 2;
        while (l < h) {
            if (nums[m] == target) return m;
            if (nums[m] > target) {
                h = m;
            } else {
                l = m + 1;
            }
            m = (l + h) / 2;
        }
        return m;
    }
}
