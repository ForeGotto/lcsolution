public class BeautyOfCode {
    public static int lowestOne(int n) {
        int result = 0;
        while (n > 0) {
            n >>>= 1;
            result += n;
        }
        return result;
    }

    public static int oneCountInBinary(int n) {
        int result = 0;
        while (n != 0) {
            n &= (n - 1);
            result++;
        }
        return result;
    }

    public static int lowestOneFast(int n) {
        return n - oneCountInBinary(n);
    }
}
