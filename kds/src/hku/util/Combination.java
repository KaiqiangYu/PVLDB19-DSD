package hku.util;

import java.util.Arrays;

/**
 * Compute the combination
 * @author yukaiqiang
 * @date Nov,26 2017
 */
public class Combination {
	
	/**
	 * Compute the combination
	 * @param star_num the number of tail-vertices in the star-motif
	 * @param max_degree the max degree in the given graph
	 * @return the array result[i] is used to save the result of C(star_num,i)
	 */
	public static int[] combination(int star_num,int max_degree){
		int result[]=new int[max_degree+1];
		Arrays.fill(result,0);
		result[star_num]=1;
		for(int i=star_num+1;i<=max_degree;i++) {
			result[i]=(int) ((result[i-1]/((i-star_num)*1.0))*i);
		}
		return result;
	}
	public static long[] combination(int star_num,int max_degree,int aa){
		long result[]=new long[max_degree+1];
		Arrays.fill(result,0);
		result[star_num]=1;
		for(int i=star_num+1;i<=max_degree;i++) {
			result[i]=(long) ((result[i-1]/((i-star_num)*1.0))*i);
		}
		return result;
	}
	public static void main(String[] args) {
		int a[]=combination(3, 10);
		for(int i:a) {
			System.out.println(i);
		}
	}

}
