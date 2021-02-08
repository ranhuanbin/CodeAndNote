package com.test.apm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.test.apm.thread.ThreadActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.test).setOnClickListener(v -> sortArrayByParityII(new int[]{1, 1, 2, 3}));
        findViewById(R.id.gotoThreadActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ThreadActivity.class));
            }
        });
    }

    public int[] sortArrayByParityII(int[] A) {
        int n = A.length;
        int j = 1;
        for (int i = 0; i < n; i += 2) {
            if (A[i] % 2 == 1) {
                while (A[j] % 2 == 1) {
                    j += 2;
                }
                int tmp = A[j];
                A[j] = A[i];
                A[i] = tmp;
            }
        }
        return A;
    }

    public int findLengthOfLCIS(int[] nums) {
        int interval = 0;
        for (int i = 0, j = 1; j < nums.length; j++) {
            if (nums[j] > nums[j - 1]) {
                if ((j - i) > interval) {
                    interval = j - i;
                }
            } else {
                i = j;
            }
        }
        return interval;
    }

    public ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null) {//空表或者链表尾端
            return head;
        }
        ListNode listNode = reverseList1(head.next);
        head.next.next = head;
        head.next = null;
        return listNode;
    }

    public ListNode reverseList2(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    private class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
