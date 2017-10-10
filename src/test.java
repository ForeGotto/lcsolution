public class test {
    public static void main(String[] args) {
//        System.out.println(-11%10);
//        System.out.println((new Easy()).isPalindrome(1111));
//        Thread thread = new Thread(() -> aa());
//        Thread thread1 = new Thread(() -> bb());
//
//
//        thread.toString();
//
//        thread1.start();
//
//        thread.start();

//        int[] arr = new int[] {-3,0,-1};
//        System.out.println((new Easy()).searchInsert(arr, 2));
//        System.out.println((new Easy()).maxSubArray(arr));

//        System.out.println((new Easy()).climbStairs(10));

//        StringBuilder dd = null;
//        System.out.println(null == null);
        int n = 8;
        System.out.println(BeautyOfCode.lowestOneFast(n));
        System.out.println(BeautyOfCode.lowestOne(n));
        System.out.println(Integer.toString(84*148, 14));
    }

    public static int anInt = 0;
    public static synchronized void aa() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(++anInt);
    }

    public static synchronized void bb() {
        System.out.println(anInt);
    }
}