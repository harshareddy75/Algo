package org.dcb;

public class Main {

    public static void main(String[] args) {
        NextBiggestBinary nextBiggestBinary =  new NextBiggestBinary();
        for (int i=-10; i<=10; ++i) {
            System.out.println("The next number for " + i + ": " + nextBiggestBinary.findNextBiggestBinary(i));
        }
    }
}
