package hku.algo.exist;

import hku.algo.prune.Component;

public class MDS {
	
	public int[] s_t_result=null;
	public long motif_num;
	public int vertex_num;
	public double densest;
	public Component core;
	
	public MDS(int[] s_t_result,long motif_num,int vertex_num,double densest) {
		this.s_t_result=s_t_result;
		this.motif_num=motif_num;
		this.vertex_num=vertex_num;
		this.densest=densest;
	}

}
