package hku.algo.exist;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Construct the flow network
 * @author yukaiqiang
 * @date Nov,24 2017
 */
public class FlowNetwork {
	
	/** data structure used to record all matchings*/
	private Map<String,int[]> Motif_Record=null;
	/** the number of vertices in the given motif*/
	private int motif_size=0;
	/** the number of vertices in the given graph*/
	private int graph_size=0;
	/** data structure used to save the flow network*/
	private Map<Integer,double[]> FlowNetwork[]=null;
	
	private Map<Integer,double[]> FlowNetwork1[]=null;
	/** data structure used to save the vertex's motif-degree*/
	private int[] Motif_degree=null;
	
	/**
	 * 
	 * @param map data structure used to record all matchings.
	 * @param motif_size the number of vertices in the given motif
	 * @param graph_size the number of vertices in the given graph
	 * @param Motif_degree the number of participating motifs per vertex
	 */
	public FlowNetwork(Map<String,int[]> map,int motif_size,
			int graph_size,int[] Motif_degree) {
		this.Motif_Record=map;
		this.motif_size=motif_size;
		this.graph_size=graph_size;
		this.Motif_degree=Motif_degree;
	}
	
	/**
	 * Construct the flow network, according to the given information
	 * @param alph the initial motif density
	 * @return Flow network
	 */
	@SuppressWarnings("unchecked")
	public  Map<Integer,double[]>[] Construct(double alph) {
		int a=Motif_Record.size();
		int i=0;double weight=0;
		int temp_array[]=null;
		FlowNetwork=new Map[a+graph_size+2];
		for(i=0;i<a+graph_size+2;++i)
			FlowNetwork[i]=new HashMap<Integer,double[]>();
		i=graph_size;
		for(Entry<String, int[]> entry : Motif_Record.entrySet()) {
			temp_array=entry.getValue();
			weight=temp_array[motif_size]*(motif_size-1);
			for(a=0;a<motif_size;++a) {
				double temp1[]= {(double)temp_array[motif_size],(double)temp_array[motif_size]};
				double temp2[]= {weight,weight};
				FlowNetwork[i].put(temp_array[a],temp2 );
				FlowNetwork[temp_array[a]].put(i, temp1);
			}
			++i;
		}
		int soure=Motif_Record.size()+graph_size;
		int tink=Motif_Record.size()+graph_size+1;
		for(i=0;i<graph_size;++i) {
			double temp1[]= {0.0,0.0};
			FlowNetwork[i].put(soure,temp1);
			double temp2[]= {alph*(motif_size),alph*(motif_size)};
//			System.out.println(alph);
			FlowNetwork[i].put(tink,temp2);
			double temp3[]= {(double) Motif_degree[i],(double) Motif_degree[i]};
			FlowNetwork[soure].put(i, temp3);
			double temp4[]= {0,0};
			FlowNetwork[tink].put(i,temp4);
			//System.out.println("&&&&&&&"+alph*motif_size);
		}
		
//		FlowNetwork1=new Map[a+graph_size+2];
//		for(i=0;i<a+graph_size+2;++i)
//			FlowNetwork1[i]=new HashMap<Integer,double[]>();
		
		
//		System.out.println("**************");
//		for(i=0;i<FlowNetwork.length;++i){
//			System.out.print(i);
//			for(Entry<Integer, double[]> entry : FlowNetwork[i].entrySet()){
//				double temp[]=entry.getValue();
//				int ab=entry.getKey();
//				System.out.print("	("+ab+" , "+temp[0]+")");
//			}
//			System.out.println();
//		}
//		System.out.println("**************");
		
		return FlowNetwork;
	}
	
	public void copyNetwork(){
		for(int i=0;i<FlowNetwork.length;++i){
			for(Entry<Integer, double[]> entry : FlowNetwork[i].entrySet()){
				int a=entry.getKey();
				double b[]=entry.getValue();
				double c[]=new double[2];
				c[0]=b[0];c[1]=b[1];
				FlowNetwork1[i].put(a, c);
			}
		}
	}
	
	/**
	 * Update the capacity of some edges
	 * @param alph the motif density we 'guess' in each step of binary search.
	 * @return Flow network
	 */
	public Map<Integer,double[]>[] Update(double alph) {
		int tink=graph_size+Motif_Record.size()+1;
		double[] temp_array;
		for(int i=0;i<=tink;++i)
			for(Entry<Integer, double[]> entry : FlowNetwork[i].entrySet()) {
				temp_array=entry.getValue();
				temp_array[0]=temp_array[1];
			}
		for(int i=0;i<graph_size;++i) {
			temp_array=FlowNetwork[i].get(tink);
			temp_array[0]=alph*motif_size;
			temp_array[1]=alph*motif_size;
		}
//		System.out.println("**************");
//		for(int i=0;i<FlowNetwork.length;++i){
//			System.out.print(i);
//			for(Entry<Integer, double[]> entry : FlowNetwork[i].entrySet()){
//				double temp[]=entry.getValue();
//				int ab=entry.getKey();
//				System.out.print("	("+ab+" , "+temp[0]+")");
//			}
//			System.out.println();
//		}
//		System.out.println("**************");
		return FlowNetwork;
	}
	
	
	public Map<Integer,double[]>[] Update1(double alph) {
		int tink=graph_size+Motif_Record.size()+1;
		double[] temp_array;
//		for(int i=0;i<=tink;++i)
//			for(Entry<Integer, double[]> entry : FlowNetwork[i].entrySet()) {
//				temp_array=entry.getValue();
//				temp_array[0]=temp_array[1];
//			}
		for(int i=0;i<graph_size;++i) {
			temp_array=FlowNetwork1[i].get(tink);
			temp_array[0]=alph*motif_size-temp_array[0];
			temp_array[1]=alph*motif_size;
		}
//		System.out.println("**************");
//		for(int i=0;i<FlowNetwork.length;++i){
//			System.out.print(i);
//			for(Entry<Integer, double[]> entry : FlowNetwork[i].entrySet()){
//				double temp[]=entry.getValue();
//				int ab=entry.getKey();
//				System.out.print("	("+ab+" , "+temp[0]+")");
//			}
//			System.out.println();
//		}
//		System.out.println("**************");
		return FlowNetwork1;
	}
	

}
