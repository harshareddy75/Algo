package org.dcb;

import java.util.BitSet;

/**
 * Given an integer n, find the next biggest integer with the same number of 1-bits on. For example, given the number 6
 * (0110 in binary), return 9 (1001).
 *
 * Assumptions
 * For negative numbers, I am not dealing with the first bit (1's complement and 2's complement)
 * 1) If the number is -9, the next biggest integer is -6
 * 2) If the number is -3 (0xb 11), the next biggest number with the same number of 1s is 3 (0xb 11)
 *
 * I have not dealt with integer overflows here
 *
 * Solution:
 * For positive integers:
 * Find the first 1 from the right. If there is a 0 next to it -> swap 1 and 0
 * If the first 1 from the right is followed by more 1's, find the first 0 after the 1. Flip that 0 to a 1. The rest of
 * the 1s shift to the right most position available (this technically covers the previous case, but for simplicity I
 * have coded it separately)
 *
 * For negative integers:
 * Do the opposite
 *
 * For 0 -> Return 0
 *
 * For n being the number of digits in the binary representation of the number, the solution creates a bitset of size n
 * and runs several loops of max size n.
 *
 * Time Complexity - O(n)
 * Space Complexity - O(n)
 *
 */

public class NextBiggestBinary {
    int findNextBiggestBinary(int num) {
        if (num == 0) {
            return 0;
        } else if (num > 0) {
            BitSet bitSet = findBinarySet(num);
            findNextBiggestPositiveBinary(bitSet);
            return convertToNum(bitSet);
        } else {
            BitSet bitSet = findBinarySet(Math.abs(num));
            findNextBiggestNegativeBinary(bitSet);
            int tempNum = convertToNum(bitSet);
            return tempNum == Math.abs(num) ? tempNum : tempNum*-1 ;
        }
    }

    private int convertToNum(BitSet bitSet) {
        return (int)bitSet.toLongArray()[0];
    }

    private BitSet findBinarySet(int num) {
        return BitSet.valueOf(new long[]{num});
    }

    private void findNextBiggestPositiveBinary(BitSet bitSet) {
        int firstTruePos = 0;
        int firstFalseAfterTrue = 0;
        boolean firstTrueFound = false;
        boolean firstFalseAfterTrueFound = false;
        int countOnes = 0;

        for (int i=0;i<bitSet.length();++i) {
            if (!firstTrueFound) {
                if (bitSet.get(i)) {
                    firstTrueFound = true;
                    firstTruePos = i;
                    countOnes++;
                }
            } else {
                if (!bitSet.get(i)) {
                    firstFalseAfterTrue = i;
                    firstFalseAfterTrueFound = true;
                    break;
                } else {
                    countOnes++;
                }
            }
        }
        if (!firstFalseAfterTrueFound) {
            firstFalseAfterTrue = bitSet.length();
        }
        if (countOnes == 1) {
            bitSet.flip(firstTruePos);
            bitSet.flip(firstFalseAfterTrue);
        } else {
            bitSet.set(0, 0+countOnes-1);
            bitSet.clear(countOnes-1, firstFalseAfterTrue);
            bitSet.set(firstFalseAfterTrue);
        }
    }

    private void findNextBiggestNegativeBinary(BitSet bitSet) {
        int firstZeroPos = 0;
        boolean firstZeroFound = false;
        int firstOneAfterZeroPos = 0;
        int countOnes = 0;
        for (int i=0;i<bitSet.length();++i) {
            if (!firstZeroFound) {
                if (!bitSet.get(i)) {
                    firstZeroFound = true;
                    firstZeroPos = i;
                } else {
                    countOnes++;
                }
            } else {
                if (bitSet.get(i)) {
                    countOnes++;
                    firstOneAfterZeroPos = i;
                    break;
                }
            }
        }
        if (!firstZeroFound) {
            return;
        }
        if (firstZeroPos + 1 == firstOneAfterZeroPos) {
            bitSet.flip(firstZeroPos);
            bitSet.flip(firstOneAfterZeroPos);
            return;
        }
        bitSet.flip(firstOneAfterZeroPos);
        bitSet.set(firstOneAfterZeroPos-countOnes, firstOneAfterZeroPos);
        bitSet.clear(0, firstOneAfterZeroPos-countOnes);
    }
}
