package test;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Arrays;

/**
 * 快排
 * https://www.bilibili.com/video/BV117411x72U?from=search&seid=2224544263023075531
 */
public class QuickSortTest extends TestCase {
    @Test
    public void test() {
        int[] num = {3, 45, 78, 64, 52, 11, 64, 55, 99, 11, 18};
        quickSort(num, 0, num.length - 1);
        System.out.println(Arrays.toString(num));
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        int i = left;
        int j = right;
        int key = nums[left];
        while (i < j) {
            // 先j左移
            while (nums[j] >= key && i < j) {
                j--;
            }
            // 后i右移, 这个顺序不能变
            while (nums[i] <= key && i < j) {
                i++;
            }
            if (i < j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        nums[left] = nums[i];
        nums[i] = key;
        quickSort(nums, left, i - 1);
        quickSort(nums, i + 1, right);
    }
}
