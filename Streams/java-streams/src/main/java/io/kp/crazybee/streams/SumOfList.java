package io.kp.crazybee.streams;

import java.util.List;
import java.util.stream.IntStream;

public class SumOfList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> integers = List.of(1, 2, 3, 4, 5);
		System.out.println(integers.stream().reduce(0, (a, b) -> a + b));
		System.out.println(integers.stream().reduce(0, Integer::sum));
		
		// Product of range
		int prod = IntStream.range(1, 6).reduce((num1,num2) -> num1 * num2).orElse(-1);
		System.out.println("Product is "+prod);
	}

}
