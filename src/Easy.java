import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.HashMap;
import java.util.LinkedList;

public class Easy {
    /**
     * @param x
     * @return
     * @math The input is assumed to be a 32-bit signed integer. Your function should return 0 when the reversed integer overflows.
     * it should be noticed that -11 % 10 == -1 in java
     * if you are not sure the rule of the language you are using, maybe you should do the same thing as I do here
     */
    public int reverse(int x) {
        int sign = x >= 0 ? 1 : -1;
        x *= sign;
        long i = 0;
        while (x > 0) {
            i *= 10;
            i += x % 10;
            x /= 10;
        }
        i *= sign;
        if (i > Integer.MAX_VALUE || i < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) i;
    }

    /**
     * @param x
     * @return
     * @math Determine whether an integer is a palindrome. Do this without extra space.
     */
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }

        //get the largest number which is both multiple of 10 and not larger than x
        //say: x:1001, l2r:1000; x:1, l2r:1;
        int l2r = 1;
        while (x / l2r >= 10) {
            l2r *= 10;
        }
        while (l2r > 0) {
            if (x / l2r != x % 10) {
                return false;
            }
            x = (x % l2r) / 10;
            l2r /= 100;
        }
        return true;
    }

    /**
     * @param nums
     * @param target
     * @return
     * @array
     * @hashmap Given an array of integers, return indices of the two numbers such that they add up to a specific target.
     * You may assume that each input would have exactly one solution, and you may not use the same element twice.
     */
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>(nums.length);
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                result[0] = map.get(target - nums[i]);
                result[1] = i;
                break;
            }
            map.put(nums[i], i);
        }
        return result;
    }

    /**
     * @param s
     * @return
     * @math
     * @string Given a roman numeral, convert it to an integer.
     * Input is guaranteed to be within the range from 1 to 3999.
     */
    public int romanToInt(String s) {
        HashMap<Character, Integer> map = new HashMap<>(7);
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int cursor = s.length() - 1;
        int result = 0;
        while (cursor > 0) {
            int cur = map.get(s.charAt(cursor));
            int left = map.get(s.charAt(cursor - 1));
            if (cur <= left) {
                result += cur;
                cursor--;
            } else {
                result += cur - left;
                cursor -= 2;
            }
        }
        if (cursor == 0) {
            result += map.get(s.charAt(0));
        }
        return result;
    }

    /**
     * @param strs
     * @return
     * @string Write a function to find the longest common prefix string amongst an array of strings.
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        int minLength = strs[0].length();
        for (int i = 1; i < strs.length; i++) {
            if (strs[i].length() < minLength) {
                minLength = strs[i].length();
            }
        }
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < minLength; i++) {
            char cur = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++)
                if (strs[j].charAt(i) != cur) {
                    return builder.toString();
                }
            builder.append(cur);
        }
        return builder.toString();
    }

    /**
     * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
     * The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
     *
     * @param s
     * @return
     * @string
     * @stack
     */
    public boolean isValid(String s) {
        if ((s.length() & 1) == 1 || s.length() == 0) {
            return false;
        }
        ArrayList<Character> stack = new ArrayList<>(s.length());
        int top = -1;
        for (char curChar : s.toCharArray()) {
            if (curChar == '(' || curChar == '{' || curChar == '[') {
                stack.add(curChar);
                top++;
            } else {
                if (top == -1) {
                    return false;
                }
                int diff = stack.remove(top--) - curChar;
                if (diff != -1 && diff != -2) {
                    return false;
                }
            }
        }
        if (top != -1) {
            return false;
        }
        return true;
    }

    /**
     * @param l1
     * @param l2
     * @return
     * @linkedList Merge two sorted linked lists and return it as a new list.
     * The new list should be made by splicing together the nodes of the first two lists.
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        ListNode result;
        if (l1.val < l2.val) {
            result = l1;
            l1 = l1.next;
        } else {
            result = l2;
            l2 = l2.next;
        }

        ListNode curNode = result;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curNode.next = l1;
                l1 = l1.next;
            } else {
                curNode.next = l2;
                l2 = l2.next;
            }
            curNode = curNode.next;
        }

        if (l1 == null) {
            curNode.next = l2;
        } else {
            curNode.next = l1;
        }

        return result;
    }

    /**
     * @param nums
     * @return
     * @array
     * @two-pointers Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
     * Do not allocate extra space for another array, you must do this in place with constant memory.
     * For example,
     * Given input array nums = [1,1,2],
     * Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
     * It doesn't matter what you leave beyond the new length.
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }
        int cursor = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[cursor] = nums[i];
                cursor++;
            }
        }//end of for loop
        return cursor;
    }

    /**
     * @param nums
     * @param val
     * @return
     * @array
     * @two-pointers Given an array and a value, remove all instances of that value in place and return the new length.
     * Do not allocate extra space for another array, you must do this in place with constant memory.
     * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
     */
    public int removeElement(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }
        int cursor = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[cursor] = nums[i];
                cursor++;
            }
        }
        return cursor;
    }

    /**
     * @param haystack
     * @param needle
     * @return
     * @two-pointers
     * @string Implement strStr().
     * Returns the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
     */
    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null || haystack.length() < needle.length()) {
            return -1;
        }
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            int cursor = 0;
            while (cursor < needle.length() && haystack.charAt(i + cursor) == needle.charAt(cursor)) {
                cursor++;
            }
            if (cursor == needle.length()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * I don't know why linear-search version is faster than binary-search version, maybe I should commit at midnight
     *
     * @param nums
     * @param target
     * @return
     * @array
     * @binary-search Given a sorted array and a target value, return the index if the target is found.
     * If not, return the index where it would be if it were inserted in order.
     * You may assume no duplicates in the array.
     * Here are few examples.
     * [1,3,5,6], 5 → 2
     */
    public int searchInsert(int[] nums, int target) {
        if (nums.length == 0 || target <= nums[0]) {
            return 0;
        }
        if (target > nums[nums.length - 1]) {
            return nums.length;
        }
        int l = 0, r = nums.length - 1, m;
        while (l <= r) {
            m = (l + r) / 2;
            if (nums[m] == target) {
                return m;
            }
            if (nums[m] < target) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        if (nums[l] < target) {
            return l + 1;
        } else {
            return l;
        }
    }

    /**
     * this problem sucks
     *
     * @param n
     * @return
     * @string The count-and-say sequence is the sequence of integers with the first five terms as following:
     * 1, 11, 21, 1211, 111221, 312211, 13112221
     * each element is the same as what you read off the previous one
     */
    public String countAndSay(int n) {
        String result = "1";
        if (n == 1) {
            return result;
        }
        StringBuilder builder = new StringBuilder();
        for (int j = 1; j < n; j++) {
            int count = 1;
            char cur = result.charAt(0);
            builder.delete(0, builder.length());
            for (int i = 1; i < result.length(); i++) {
                if (result.charAt(i) != cur) {
                    builder.append(count);
                    builder.append(cur);
                    cur = result.charAt(i);
                    count = 1;
                } else {
                    count++;
                }
            }//end of inner for loop
            builder.append(count);
            builder.append(cur);
            result = builder.toString();
        }//end of outer for loop
        return result;
    }

    /**
     * use dp and you get a solution with time complexity of O(N) and space complexity of O(1)
     *
     * @param nums
     * @return
     * @array
     * @divide-and-conquer
     * @dynamic-programming Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
     * For example, given the array [-2,1,-3,4,-1,2,1,-5,4],
     * the contiguous subarray [4,-1,2,1] has the largest sum = 6.
     */
    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            max = sum > max ? sum : max;
            sum = sum < 0 ? 0 : sum;
        }
        return max;
    }

    /**
     * @param s
     * @return
     * @string Given a string s consists of upper/lower-case alphabets and empty space characters ' ',
     * return the length of last word in the string.
     * If the last word does not exist, return 0.
     */
    public int lengthOfLastWord(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int r = s.length() - 1;
        while (r >= 0 && s.charAt(r) == ' ') {
            r--;
        }
        if (r < 0) {
            return 0;
        }
        int l = r;
        while (l >= 0 && s.charAt(l) != ' ') {
            l--;
        }
        return r - l;
    }

    /**
     * @param digits
     * @return
     * @array
     * @math Given a non-negative integer represented as a non-empty array of digits, plus one to the integer.
     * You may assume the integer do not contain any leading zero, except the number 0 itself.
     * The digits are stored such that the most significant digit is at the head of the list.
     */
    public int[] plusOne(int[] digits) {
        int carry = 1;
        for (int i = digits.length - 1; carry == 1 && i >= 0; i--) {
            digits[i] += carry;
            if (digits[i] < 10) {
                carry = 0;
            } else {
                carry = 1;
                digits[i] -= 10;
            }
        }
        if (carry == 1) {
            int[] newArray = new int[digits.length + 1];
            System.arraycopy(digits, 0, newArray, 1, digits.length);
            digits = newArray;
            digits[0] = 1;
        }
        return digits;
    }

    /**
     * @param a
     * @param b
     * @return
     * @math
     * @string Given two binary strings, return their sum (also a binary string).
     * For example,
     * a = "11"
     * b = "1"
     * Return "100".
     */
    public String addBinary(String a, String b) {
        if (a == null || a.length() == 0) {
            return b;
        }
        if (b == null || b.length() == 0) {
            return a;
        }
        int length = a.length() > b.length() ? a.length() : b.length();
        StringBuilder builder = new StringBuilder(length + 1);
        int carry = 0;
        for (int i = 0, aCursor = a.length() - 1, bCursor = b.length() - 1; i < length; i++, aCursor--, bCursor--) {
            int ac = (aCursor >= 0) ? a.charAt(aCursor) - '0' : 0;
            int bc = (bCursor >= 0) ? b.charAt(bCursor) - '0' : 0;
//            System.out.printf(carry+ " " +);
            builder.insert(0, ac ^ bc ^ carry);
            carry = (ac + bc + carry) > 1 ? 1 : 0;
        }
        if (carry == 1) {
            builder.insert(0, carry);
        }
        return builder.toString();
    }

    /**
     * Implement int sqrt(int x).
     * Compute and return the square root of x.
     *
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        long m = x / 2;
        while (m * m > x) {
            m = (m + x / m) / 2;
        }
        return (int) m;
    }

    public int mySqrt1(int x) {
        long start = 0, end = x;
        while (start + 1 < end) {

            long mid = start + (end - start) / 2;
            if (mid * mid == x) return (int) mid;
            if (mid * mid > (long) x) end = mid;
            else start = mid;
        }
        if (end * end > x) return (int) start;
        else return (int) end;
    }

    /**
     * @param n
     * @return
     * @dynamic-programing You are climbing a stair case. It takes n steps to reach to the top.
     * Each time you can either climb 1 or 2 steps.
     * In how many distinct ways can you climb to the top?
     * Note: Given n will be a positive integer.
     */
    public int climbStairs(int n) {
        int pre = 0;
        int cur = 1;
        for (int i = 1; i <= n; i++) {
            int tmp = cur;
            cur += pre;
            pre = tmp;
        }
        return cur;
    }

    /**
     * @param head
     * @return
     * @linkedlist Given a sorted linked list, delete all duplicates such that each element appear only once.
     * For example,
     * Given 1->1->2, return 1->2.
     * Given 1->1->2->3->3, return 1->2->3.
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode pivot = head;
        if (pivot == null) {
            return null;
        }
        ListNode cur = head.next;
        while (cur != null) {
            if (pivot.val != cur.val) {
                pivot.next = cur;
                pivot = pivot.next;
            }
            cur = cur.next;
        }
        pivot.next = cur;
        return head;
    }

    /**
     * @array
     * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
     * Note:
     * You may assume that nums1 has enough space (size that is greater or equal to m + n)
     * to hold additional elements from nums2.
     * The number of elements initialized in nums1 and nums2 are m and n respectively.
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (n == 0) {
            return;
        }
        int i = m + n - 1;
        m--;
        n--;
        for (; m >= 0 && n >= 0; i--) {
            if (nums1[m] > nums2[n]) {
                nums1[i] = nums1[m];
                m--;
            } else {
                nums1[i] = nums2[n];
                n--;
            }
        }
        if (n >= 0) {
            for (; n >= 0; n--, i--) {
                nums1[i] = nums2[n];
            }
        }
    }

    /**
     * @binary-tree
     * @depth-first-search
     * Given two binary trees, write a function to check if they are equal or not.
     * Two binary trees are considered equal if they are structurally identical and
     * the nodes have the same value.
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if ((p == null && q != null) || (p != null && q == null)) {
            return false;
        }
        if ((p == null && q == null) ||
                (p.val == q.val && p.left == null && p.right == null &&
                        q.left == null && q.right == null)) {
            return true;
        }
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * @copied-from-solution
     * @tree
     * @depth-first-search
     * @breadth-first-search
     * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
     * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
     *     1
     *    / \
     *   2   2
     *  / \ / \
     * 3  4 4  3
     * But the following [1,2,2,null,3,null,3] is not:
     *  1
     * / \
     * 2   2
     * \   \
     * 3    3
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode t1 = q.poll();
            TreeNode t2 = q.poll();
            if (t1 == null && t2 == null) continue;
            if (t1 == null || t2 == null) return false;
            if (t1.val != t2.val) return false;
            q.add(t1.left);
            q.add(t2.right);
            q.add(t1.right);
            q.add(t2.left);
        }
        return true;
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int ld = maxDepth(root.left);
        int rd = maxDepth(root.right);
        return ld > rd ? ld + 1 : rd + 1;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }

}





