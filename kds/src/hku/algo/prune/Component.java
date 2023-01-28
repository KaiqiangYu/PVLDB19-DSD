package hku.algo.prune;

import java.util.Map;

public class Component {
	
	public int[][] Graph;
	
	public int graph_size;
	
	public Map<String,int[]> motif_list;
	
	public long motif_num;
	
	public double densest;
	
	public int[] motif_degree;
	
	public Component(int[][] Graph,int graph_size,
			Map<String,int[]> motif_list,long motif_num,double densest,int [] motif_degree) {
		this.Graph=Graph;
		this.graph_size=graph_size;
		this.motif_list=motif_list;
		this.motif_num=motif_num;
		this.densest=densest;
		this.motif_degree=motif_degree;
	}

}
