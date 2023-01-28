package hku.algo.maxflow;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

/**
 * compute the Min-Cut, according to the flow networks.
 * 
 * In this class, we used various exiting algorithms. 
 * EK:
 * Preflow: 
 * 
 * @author yukaiqiang
 * @date Nov,23 2017
 */
public class FindMinCut {
	
	/** data structure used to record flow network*/
	public Map<Integer, double[]>[] FlowNetwork=null;
	
	/** array used to save path*/
	public int[] parent=null;
	/** source vertex */
	private int s=0;
	/** sink vertex */
	private int t=0;
	
	/**
	 * 
	 * @param FlowNetwork data structure used to record flow network
	 * @param s source vertex
	 * @param t sink vertex
	 */
	public FindMinCut(Map<Integer,double[]>[] FlowNetwork,int s,int t) {
		this.FlowNetwork=FlowNetwork;
		this.s=s;
		this.t=t;
	}
	
	/**
	 * EK algorithm to solve the max-flow problem
	 * the min-cut(S-T) is saved in the array 'parent[]'
	 * S: parent[i]>-1
	 * T: parent[i]=-1
	 * @return the value of max flow
	 */
	public double EdmondsKarp() {
		parent=new int[FlowNetwork.length];
		double result=augmentPath(parent);
		double sum=0;
		double temp[];
		
		while(result!=-1) {
			int cur=t;
			double fre=0;
			while(cur!=s) {
				
				temp=FlowNetwork[parent[cur]].get(cur);
				//System.out.println(temp[0]+" "+result);
				fre=temp[0];
				temp[0]=temp[0]-result;
				temp=FlowNetwork[cur].get(parent[cur]);
				temp[0]=temp[0]+result;
				cur=parent[cur];
				
			}
			//temp=FlowNetwork[523].get(0);
			//double temp_temp=temp[0];
			//temp=FlowNetwork[0].get(523);
			//System.out.println((fre-0)+" "+result+" "+temp_temp+" "+temp[0]);
			sum+=result;
			//System.out.println("*****"+result);
			result=augmentPath(parent);
			//System.out.println(result);
		}
		
		return sum;
	}
	
	/**
	 * use BFS algorithm to find the augment path. 
	 * @param parent the array to save the path
	 * @return the minimum capacity of each edge in the path
	 */
	private double augmentPath(int[] parent) {
		double maxflow=Integer.MAX_VALUE;
		Arrays.fill(parent, -1);
		Queue<Integer> queue=new LinkedList<Integer>();
		queue.add(s);
		parent[s]=s;
		double temp[];
		
		while(!queue.isEmpty()) {
			int p=queue.poll();
			if(p==t) {
				while(p!=s) {
					//System.out.println(000);
					temp=FlowNetwork[parent[p]].get(p);
					if(maxflow>temp[0])
						maxflow=temp[0];
					p=parent[p];
				}
				break;
			}
			
			for(Entry<Integer, double[]> entry: FlowNetwork[p].entrySet()) {
				temp=entry.getValue();
				if(parent[entry.getKey()]==-1&&temp[0]>0) {
					parent[entry.getKey()]=p;
					queue.add(entry.getKey());
					//System.out.println(entry.getKey()+" "+p);
				}
			}
		}
		
		if(parent[t]==-1) {
			//get the min-cut
			return -1;
		}
		return maxflow;
	}
	
	/**
	 * 
	 * @return the parent
	 */
	public int[] getparent() {
		return parent;
	}
	
}
