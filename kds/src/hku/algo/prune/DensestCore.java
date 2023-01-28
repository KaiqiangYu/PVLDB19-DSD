package hku.algo.prune;

/**
 * Record the information of the located core
 * @author yukaiqiang
 * @date Nov 29,2017
 */
public class DensestCore {
	
	public int[][] Graph=null;
	
	public int graph_size;
	
	public int kcore=0;
	
	public int delete_vetex=0;
	
	public int delete_motif=0;
	
	public double densest;
	
	public int kmax;
	
	/**
	 * 
	 * @param Graph
	 * @param graph_size
	 * @param kcore
	 * @param delete_vetex
	 * @param delete_motif
	 */
	public DensestCore(int[][] Graph,int graph_size,int kcore,
			int delete_vetex,int delete_motif,double densest,int kmax) {
		this.Graph=Graph;
		this.graph_size=graph_size;
		this.kcore=kcore;
		this.delete_motif=delete_motif;
		this.delete_vetex=delete_vetex;
		this.densest=densest;
		this.kmax=kmax;
	}
	

}
