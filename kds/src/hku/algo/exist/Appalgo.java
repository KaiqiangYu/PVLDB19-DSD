package hku.algo.exist;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import hku.algo.pdscoredecompose.DiamondMotif;
import hku.algo.pdscoredecompose.GeneralMotif;
import hku.algo.pdscoredecompose.StarMotif;
import hku.util.Combination;

/**
 * Approximation algorithm
 * @author yukaiqiang
 * @date Nov,24 2017
 */
public class Appalgo {
	
	/** data structure used to save vertex's motif-degree*/
	private int Motif_degree[]=null;
	/** data structure used to save all matching */
	private Map<String,int[]> Motif_Record=null;
	/** the number of vertices in the given motif*/
	private int motif_size=0;
	/** the number of vertices in the given graph*/
	private int graph_size=0;
	private int[][] Graph;
	private int[][] Motif;
	private int motif_type=1;
	private int motif_d;
	
	/**
	 * 
	 * @param Motif_degree vertex's motif-degree
	 * @param Motif_Record the data structure used to save all matchings
	 * @param motif_size the number of vertices in the given motif
	 * @param graph_size the number of vertices in the given graph
	 */
	public Appalgo(int[] Motif_degree,Map<String,int[]> Motif_Record,
			int motif_size,int graph_size,int[][] Graph,int[][] Motif) {
		this.Motif_degree=Motif_degree;
		this.Motif_Record=Motif_Record;
		this.motif_size=motif_size;
		this.graph_size=graph_size;
		this.Graph=Graph;
		this.Motif=Motif;
		motif_d=Motif.length;
	}
	
	public Appalgo(int[] Motif_degree,Map<String,int[]> Motif_Record,
			int motif_size,int graph_size,int[][] Graph,int[][] Motif,int motif_type) {
		this.Motif_degree=Motif_degree;
		this.Motif_Record=Motif_Record;
		this.motif_size=motif_size;
		this.graph_size=graph_size;
		this.Graph=Graph;
		this.Motif=Motif;
		this.motif_type=motif_type;
		motif_d=Motif.length;
		
	}
	
	
	public Appalgo(
			int motif_size,int graph_size,int[][] Graph,int[][] Motif,int motif_type,int m) {
		this.Motif_degree=Motif_degree;
		this.Motif_Record=Motif_Record;
		this.motif_size=motif_size;
		this.graph_size=graph_size;
		this.Graph=Graph;
		this.Motif=Motif;
		this.motif_type=motif_type;
		motif_d=m;
	}
	
public double[] Approximate(int index,int [][] combination) {
		
		//GeneralMotif g=new GeneralMotif(Graph, Motif, graph_size, motif_size, motif_type, Motif_Record, Motif_degree);
		
		
		StarMotif g=new StarMotif(Graph, index, combination, graph_size);
		double res1[][]=g.Decompose();
		double max=0;int index_v=0,num=0;
		for(int i=0;i<res1.length;++i) {
			if(max<res1[i][2]) {
				max=res1[i][2];
				index_v=i;
			}
		}
		System.out.println(graph_size-index_v);
		double[] res={max,(graph_size-index_v),res1[index_v][2]};
		return res;
		
}
public double[] Approximate(int [][] combination) {
	
	//GeneralMotif g=new GeneralMotif(Graph, Motif, graph_size, motif_size, motif_type, Motif_Record, Motif_degree);
	
	
	DiamondMotif g=new DiamondMotif(Graph, combination, graph_size);
	double res1[][]=g.Decompose();
	double max=0;int index_v=0,num=0;
	for(int i=0;i<res1.length;++i) {
		if(max<res1[i][2]) {
			max=res1[i][2];
			index_v=i;
		}
	}
	System.out.println(graph_size-index_v);
	double[] res={max,(graph_size-index_v),res1[index_v][2]};
	return res;
	
}

	/**
	 * This method implements the approximation algorithm. The main idea is to 
	 * remove each vertex whose motif-degree is smallest in each step.
	 */
	public double[] ApproximateInc() {
		
		GeneralMotif g=new GeneralMotif(Graph, Motif, graph_size, motif_size, motif_type, Motif_Record, Motif_degree,motif_d);
		double res1[][]=g.DecomposeInc();
		double max=0;int index_v=0,num=0;
		double maxc=0;
		for(int i=0;i<res1.length;++i) {
			if(maxc<res1[i][1]) {
				maxc=res1[i][1];
			}
		}
		
		for(int i=0;i<res1.length;++i) {
			if(max<res1[i][2]) {
				max=res1[i][2];
				index_v=i;
			}
		}
		System.out.println(graph_size-index_v+"  "+maxc);
		double[] res={max,(graph_size-index_v),res1[index_v][3]};
		return res;
//		int res_node[]=new int[graph_size];
//		int res_num=graph_size,res_count=0;
//		int temp_array[];
//		
//		for(int i=0;i<graph_size;++i) {
//			res_count+=Motif_degree[i];
//			res_node[i]=Motif_degree[i];
//		}
//		res_count=res_count/motif_size;
//		
//		double res_density=(res_count*1.0)/res_num;
//		double fin_v=graph_size;
//		double fin_m=res_count;
//		while(true) {
//			
//			int temp=Integer.MAX_VALUE;
//			int index=-1;
//			//find the minimum motif-degree vertex
//			for(int i=0;i<graph_size;++i) {
//				if(Motif_degree[i]!=-1&&Motif_degree[i]<temp) {
//					index=i;
//					temp=Motif_degree[i];
//				}
//			}	
//			
//			//condition
//			if(index==-1)
//				break;
//			
//			//delete this vertex and update			
//			for(Entry<String, int[]> entry : Motif_Record.entrySet()) {
//				temp_array=entry.getValue();
//				if(temp_array[motif_size]==0)
//					continue;
//				else {
//					int i;
//					for(i=0;i<motif_size;++i) {
//						if(temp_array[i]==index)
//							break;
//					}
//					if(i<motif_size) {
//						res_count-=temp_array[motif_size];
//						for(i=0;i<motif_size;++i)
//							Motif_degree[temp_array[i]]-=temp_array[motif_size];
//						temp_array[motif_size]=0;
//					}
//				}
//			}
//			Motif_degree[index]=-1;
//			res_num--;
//			//System.out.println(res_density<(0.1*res_count)/res_num);
//			//record the result
//			if(res_num!=0&&res_density<(1.0*res_count)/res_num) {
//				res_density=(1.0*res_count)/res_num;
//				//System.out.println(res_count+" "+res_num);
//				fin_v=res_num;
//				fin_m=res_count;
//				for(int i=0;i<graph_size;++i)
//					res_node[i]=Motif_degree[i];
//			}
//			
//		}
		
	}
	
	
public double[] Approximate() {
		
		GeneralMotif g=new GeneralMotif(Graph, Motif, graph_size, motif_size, motif_type, Motif_Record, Motif_degree);
		double res1[][]=g.Decompose();
		double max=0;int index_v=0,num=0;
		double maxc=0;
		for(int i=0;i<res1.length;++i) {
			if(maxc<res1[i][1]) {
				maxc=res1[i][1];
			}
		}
		
		for(int i=0;i<res1.length;++i) {
			if(max<res1[i][2]) {
				max=res1[i][2];
				index_v=i;
			}
		}
		System.out.println(graph_size-index_v+"  "+maxc);
		double[] res={max,(graph_size-index_v),res1[index_v][3]};
		return res;
//		int res_node[]=new int[graph_size];
//		int res_num=graph_size,res_count=0;
//		int temp_array[];
//		
//		for(int i=0;i<graph_size;++i) {
//			res_count+=Motif_degree[i];
//			res_node[i]=Motif_degree[i];
//		}
//		res_count=res_count/motif_size;
//		
//		double res_density=(res_count*1.0)/res_num;
//		double fin_v=graph_size;
//		double fin_m=res_count;
//		while(true) {
//			
//			int temp=Integer.MAX_VALUE;
//			int index=-1;
//			//find the minimum motif-degree vertex
//			for(int i=0;i<graph_size;++i) {
//				if(Motif_degree[i]!=-1&&Motif_degree[i]<temp) {
//					index=i;
//					temp=Motif_degree[i];
//				}
//			}	
//			
//			//condition
//			if(index==-1)
//				break;
//			
//			//delete this vertex and update			
//			for(Entry<String, int[]> entry : Motif_Record.entrySet()) {
//				temp_array=entry.getValue();
//				if(temp_array[motif_size]==0)
//					continue;
//				else {
//					int i;
//					for(i=0;i<motif_size;++i) {
//						if(temp_array[i]==index)
//							break;
//					}
//					if(i<motif_size) {
//						res_count-=temp_array[motif_size];
//						for(i=0;i<motif_size;++i)
//							Motif_degree[temp_array[i]]-=temp_array[motif_size];
//						temp_array[motif_size]=0;
//					}
//				}
//			}
//			Motif_degree[index]=-1;
//			res_num--;
//			//System.out.println(res_density<(0.1*res_count)/res_num);
//			//record the result
//			if(res_num!=0&&res_density<(1.0*res_count)/res_num) {
//				res_density=(1.0*res_count)/res_num;
//				//System.out.println(res_count+" "+res_num);
//				fin_v=res_num;
//				fin_m=res_count;
//				for(int i=0;i<graph_size;++i)
//					res_node[i]=Motif_degree[i];
//			}
//			
//		}
		
	}

public double[] ApproximateClique() {
	hku.algo.cds.CDSdecompose g=new hku.algo.cds.CDSdecompose(Graph, Motif, graph_size, motif_size, motif_type, Motif_Record, null);
	double res1[][]=g.Decompose();
	double max=0;int index_v=0,num=0;
	double maxc=0;
	for(int i=0;i<res1.length;++i) {
		//System.out.println(res1[i][1]);
		
		if(maxc<res1[i][1]) {
			maxc=res1[i][1];
			index_v=i;
		}
	}
	
	/** return dense subgraph/ uncommented if necessary.
	System.out.println("dense subgraph");
	for(int i=index_v;i<res1.length;++i) {
		System.out.println(res1[i][0]);
	}
	*/
	//System.out.println(index_v);
	System.out.println("max_core:"+maxc+" vertex number:"+(graph_size-index_v+1)+"  clique number:"+res1[index_v-1][3]+" density:"+res1[index_v-1][2]);
	double[] res={maxc,(graph_size-index_v),res1[index_v-1][3]};
	return res;
}


public double[] ApproximateCliquePeel() {
	hku.algo.cds.CDSdecompose g=new hku.algo.cds.CDSdecompose(Graph, Motif, graph_size, motif_size, motif_type, Motif_Record, null);
	double res1[][]=g.Decompose();
	double max=0;int index_v=0,num=0;
	double maxc=0;
	
	
	for(int i=0;i<res1.length;++i) {
		if(max<res1[i][2]) {
			max=res1[i][2];
			index_v=i;
		}
	}
	/** return dense subgraph/ uncommented if necessary.
	System.out.println("dense subgraph");
	for(int i=index_v;i<res1.length;++i) {
		System.out.println(res1[i][0]);
	}
	*/
	System.out.println("vertex number:"+(graph_size-index_v)+" clique number:"+res1[index_v][3]+" density:"+max);
	double[] res={max,(graph_size-index_v),res1[index_v][3]};
	return res;
}

}
