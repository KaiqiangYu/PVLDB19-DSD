package hku.algo.prune;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class InvalidEdgePruning {
	
	private Map<String,int[]> motif_list=null;
	
	private int[][] Graph=null;
	
	private int graph_size;
	
	public InvalidEdgePruning(Map<String,int[]> motif_list,
			int[][] Graph,int graph_size) {
		this.motif_list=motif_list;
		this.Graph=Graph;
		this.graph_size=graph_size;
	}
	
	public int Prune() {
		int count=0;
//		System.out.println("********");
//		for(int i=0;i<graph_size;++i) {
//			System.out.print(i+"  ");
//			for(int j=0;j<Graph[i].length;++j) {
//				System.out.print(Graph[i][j]+" ");
//			}
//			System.out.println();
//		}
		Map<Integer,Integer> neig[]=new Map[graph_size];
		for(int i=0;i<graph_size;++i) {
			neig[i]=new HashMap<Integer,Integer>();
		}
		for(Entry<String,int[]> entry:motif_list.entrySet()) {
			int temp[]=entry.getValue();
			for(int i=0;i<temp.length-1;++i) {
				for(int j=0;j<temp.length-1;++j) {
					if(i!=j) {
						if(!neig[temp[i]].containsKey(temp[j])) {
							neig[temp[i]].put(temp[j], 0);
						}
					}
				}
			}
		}
		for(int i=0;i<graph_size;++i) {
			int temp[]=new int[Graph[i].length];
			int size=0;
			for(int j=0;j<Graph[i].length;++j) {
				if(neig[i].containsKey(Graph[i][j])) {
					temp[size]=Graph[i][j];
					size++;
				}
			}
			if(size<Graph[i].length) {
				int array[]=new int[size];
				for(int j=0;j<size;++j)
					array[j]=temp[j];
				count+=(Graph[i].length-size);
				Graph[i]=array;
				
			}
		}
		return count/2;
	}

}
