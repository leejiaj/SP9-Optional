/**
 * @author Leejia James
 *
 * Implementing O(n) and O(log n) algorithms for computing
 * Fibonacci Number
 * 
 * Observations
 * --------------
 * O(n) algorithm
 * n			Running Time
 * --------------------------
 * 1000			1 msec
 * 10000		26 msec
 * 100000		503 msec
 *
 * O(log n) algorithm
 * n			Running Time
 * --------------------------
 * 1000			0 msec
 * 10000		5 msec
 * 100000		38 msec
 *
 * Ver 1.0: 2018/11/3
 */
package lxj171130;

import java.math.BigInteger;

public class Fibonacci {

	public static void main(String[] args) {
		int n = 1000;
		int choice = 1;
		if(args.length > 0) { n = Integer.parseInt(args[0]); }
		if(args.length > 1) { choice = Integer.parseInt(args[1]); }
		BigInteger result = BigInteger.ZERO;
		Timer timer = new Timer();
		switch(choice) {
		case 1: // O(n) algorithm
		    result = fibonacciLinear(n);
		    break;
		case 2: // O(log n) algorithm
		    result = fibonacciLogn(n);
		    break;
		}
		timer.end();

		System.out.println("Choice: " + choice + "\n" + timer);
		System.out.println(result);
	}
	

	/**
	 * Linear time (O(n)) algorithm to compute Fibonacci number
	 * Method used: Dynamic Programming
	 * @param n
	 * @return Fibonacci number corresponding to number n
	 */
	public static BigInteger fibonacciLinear(int n){
		if(n == 0) {
			return BigInteger.ZERO;
		}
		BigInteger[] fib = new BigInteger[n+1];
		fib[0] = BigInteger.ZERO;
		fib[1] = BigInteger.ONE;
		for(int i=2; i<=n; i++) {
			fib[i] = fib[i-1].add(fib[i-2]);
		}
		return fib[n];
	}
	
	/**
	 * O(log n) algorithm to compute Fibonacci number
	 * Method used: Divide and Conquer method to calculate power
	 * @param n
	 * @return Fibonacci number corresponding to number n
	 */
	public static BigInteger fibonacciLogn(int n) {
		if(n == 0) {
			return BigInteger.ZERO;
		}
		BigInteger[][] A = {{BigInteger.ONE,BigInteger.ONE},{BigInteger.ONE,BigInteger.ZERO}};
		power(A, n-1);
		return A[0][0];
	}
	
	/**
	 * Divide and conquer method to calculate power
	 * @param A
	 * @param n
	 */
	private static void power(BigInteger[][] A, int n) {
		BigInteger[][] temp = {{BigInteger.ONE,BigInteger.ONE},{BigInteger.ONE,BigInteger.ZERO}};
		if(n == 0 || n == 1) {
			return;
		}
		power(A , n/2);		
		matrixMult(A, A);
		if(n % 2 == 1) {
			matrixMult(A, temp);
		}
	}

	/**
	 * Method to multiply matrices A and A2
	 * @param A
	 * @param A2
	 * @return result matrix after multiplication
	 */
	private static BigInteger[][] matrixMult(BigInteger[][] A, BigInteger[][] A2) {
		BigInteger t0, t1, t2, t3;
		t0 = A[0][0].multiply(A2[0][0]).add(A[0][1].multiply(A2[1][0]));
		t1 = A[0][0].multiply(A2[0][1]).add(A[0][1].multiply(A2[1][1]));
		t2 = A[1][0].multiply(A2[0][0]).add(A[1][1].multiply(A2[1][0]));
		t3 = A[1][0].multiply(A2[0][1]).add(A[1][1].multiply(A2[1][1]));
		A[0][0] = t0;
		A[0][1] = t1;
		A[1][0] = t2;
		A[1][1] = t3;
		return A;
	}
	
	
	/** Timer class for roughly calculating running time of programs
	 *  @author rbk
	 *  Usage:  Timer timer = new Timer();
	 *          timer.start();
	 *          timer.end();
	 *          System.out.println(timer);  // output statistics
	 */
	public static class Timer {
	    long startTime, endTime, elapsedTime, memAvailable, memUsed;
	    boolean ready;

	    public Timer() {
	        startTime = System.currentTimeMillis();
	        ready = false;
	    }

	    public void start() {
	        startTime = System.currentTimeMillis();
	        ready = false;
	    }

	    public Timer end() {
	        endTime = System.currentTimeMillis();
	        elapsedTime = endTime-startTime;
	        memAvailable = Runtime.getRuntime().totalMemory();
	        memUsed = memAvailable - Runtime.getRuntime().freeMemory();
	        ready = true;
	        return this;
	    }

	    public long duration() { if(!ready) { end(); }  return elapsedTime; }

	    public long memory()   { if(!ready) { end(); }  return memUsed; }

	    public String toString() {
	        if(!ready) { end(); }
	        return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
	    }
	}

}

