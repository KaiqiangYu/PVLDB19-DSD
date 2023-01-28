package hku.algo.pdscoredecompose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * core-decompose algorithm for Diamond-motif
 * @author yukaiqiang
 * @date Nov,28,2017
 */
public class DiamondMotif {
	
	/** adjacent list of the given graph*/
	private int[][] Graph=null;
	/** Combination*/
	private int[][] Combination=null;
	/** the number of vertices in the given graph*/
	private int graph_size;
	
	/**
	 * 
	 * @param Graph adjacent list of the given graph
	 * @param graph_size the number of vertex in the given graph
	 * @param Combination Combination
	 */
	public DiamondMotif(int[][] Graph,int[][] Combination,int graph_size) {
		this.Graph=Graph;
		this.Combination=Combination;
		this.graph_size=graph_size;
	}
	
	/**
	 * Decompose the Diamond-motif core
	 * 
	 * NOTE: the time complexity of the traditional k-core decompose algorithm is linear.
	 * So, we also try to ensure our algorithm is also a method with 'linear' time complexity.
	 * 
	 * @return double[graph_size+1][3]. double[a][0]=node_index,
	 *  double[a][1]=motif_number,double[a][2]=motif_density(after pruning this vertex)
	 *  a=0: original graph
	 *  a=1,2,3,...,graph_size: each vertex we removing 
	 */
	public double[][] Decompose() {
		double[][] result=new double[graph_size+1][3];
		int count=0;
		int Delete[]=new int[graph_size];
		Arrays.fill(Delete, 0);
		
		//compute the motif-degree for each vertices in the given graph
		int motif_degree[] =new int[graph_size];
		Map<Integer,Integer> TwoHob=new HashMap<Integer,Integer>();
		Map<Integer,Integer> de=new HashMap<Integer,Integer>();
		for(int i=0;i<graph_size;++i) {
			TwoHob.clear();
			for(int j=0;j<Graph[i].length;++j) {
				int neig=Graph[i][j];
				for(int k=0;k<Graph[neig].length;++k) {
					if(Graph[neig][k]!=i) {
						if(TwoHob.containsKey(Graph[neig][k])) {
							int temp=TwoHob.get(Graph[neig][k]);
//							TwoHob.replace(Graph[neig][k], (Integer)temp, (Integer)(temp+1));
							TwoHob.remove(Graph[neig][k]);
							TwoHob.put(Graph[neig][k], (Integer)(temp+1));
						}else {
							TwoHob.put(Graph[neig][k], (Integer)1);
						}
					}				
				}
			}
			
			int num=0;
			for(Entry<Integer, Integer> entry: TwoHob.entrySet()) {
				num+=Combination[0][entry.getValue()];
			}
			motif_degree[i]=num;
		}
		
		
		//initialize the data structure
		int max=0;long motif_num=0;
		for(int i=0;i<graph_size;++i) {
			motif_num+=motif_degree[i];
			//System.out.println(motif_degree[i]);
			if(max<motif_degree[i])
				max=motif_degree[i];
		}
		max=max+1;
		System.out.println(max+"*****"+graph_size);
//		ArrayList node_list[]=new ArrayList[max];
//		for(int i=0;i<max;++i)
//			node_list[i]=new ArrayList<Integer>();
//		for(int i=0;i<graph_size;++i){
//			node_list[motif_degree[i]].add(i);
//		}
		
		motif_num=motif_num/4;
		
		
		//data structure used to save the result
		result[0][2]=motif_num/(double)graph_size;
//		
//		int index_min=0,update_min=Integer.MAX_VALUE;
		
		int bin[]=new int[max+1];
		Arrays.fill(bin, 0);
		for(int i=1;i<graph_size+1;++i) {
			bin[motif_degree[i-1]]+=1;
		}
		
		int start=1;
		for(int d=0;d<=max;++d) {
			int num=bin[d];
			bin[d]=start;
			start+=num;
		}
		
		int pos[]=new int[graph_size*2];
		int vert[]=new int[graph_size*2];
		for(int v=1;v<graph_size+1;++v) {
			pos[v]=bin[motif_degree[v-1]];
			vert[pos[v]]=v;
			bin[motif_degree[v-1]]+=1;
		}
		
		for(int d=max;d>=1;d--) {
			bin[d]=bin[d-1];
		}
		bin[0]=1;
		
		for(int i=1;i<=graph_size;++i) {
			de.clear();
			//get the vertex with minimum motif-degree.
//			if(update_min<index_min) {
//				index_min=update_min;
//			}else {
//				for(;index_min<max;++index_min) {
//					if(!node_list[index_min].isEmpty())
//						break;
//				}
//			}
//			update_min=Integer.MAX_VALUE;
//			int index=(Integer)node_list[index_min].remove(0);
			
			int index=vert[i]-1;
			//System.out.println(index);
			//record the result
			
			if(index<0||Delete[index]==1)
				continue;
			
			count++;
			result[count][0]=index;
			result[count][1]=motif_degree[index];
			motif_num-=motif_degree[index];
			
			if(graph_size-count>0) {
				result[count][2]=motif_num/(double)(graph_size-count);
			}else {
				result[count][2]=0;
			}
			Delete[index]=1;
			
			TwoHob.clear();
			for(int j=0;j<Graph[index].length;++j) {
				int neig=Graph[index][j];
				if(Delete[neig]==0&&index!=Graph[index][j]) {
					for(int k=0;k<Graph[neig].length;++k) {
						if(Delete[Graph[neig][k]]==0&&neig!=Graph[neig][k]) {
							if(TwoHob.containsKey(Graph[neig][k])) {
								int temp=TwoHob.get(Graph[neig][k]);
								//TwoHob.replace(Graph[neig][k], (Integer)temp, (Integer)(temp+1));
								TwoHob.remove((Integer)Graph[neig][k]);
								TwoHob.put(Graph[neig][k], (Integer)(temp+1));
							}else {
								TwoHob.put(Graph[neig][k], (Integer)1);
							}
						}				
					}
				}
				
			}
			
			//update
			
			
			for(int j=0;j<Graph[index].length;++j) {
				int neig=Graph[index][j];
				if(Delete[neig]==0&&index!=Graph[index][j]) {
					int num=0;
					for(int k=0;k<Graph[neig].length;++k) {
						if(Delete[Graph[neig][k]]==0&&neig!=Graph[neig][k])
							num+=(TwoHob.get(Graph[neig][k])-1);
					}
					de.put(neig, num);
//					if(motif_degree[neig]>motif_degree[index]) {
//						int du=motif_degree[neig];
//						int pu=pos[neig+1];
//						int pw=bin[du];
//						int w=vert[pw];
//						if((neig+1)!=w) {
//							pos[neig+1]=pw;
//							vert[pu]=w;
//							pos[w]=pu;
//							vert[pw]=neig+1;							
//						}
//						bin[du]+=1;
//						
//					}
//					motif_degree[neig]-=num;
//					node_list[motif_degree[neig]].remove((Integer)neig);
//					motif_degree[neig]=motif_degree[neig]-num;
//					node_list[motif_degree[neig]].add(neig);
//					
//					if(update_min>motif_degree[neig]) {
//						update_min=motif_degree[neig];
//					}
				}			
				
			}
			
			for(Entry<Integer, Integer> entry: TwoHob.entrySet()) {
				int temp_node=entry.getKey();
//				node_list[motif_degree[temp_node]].remove((Integer)temp_node);
				
//				node_list[motif_degree[temp_node]].add((Integer)temp_node);
//				
//				if(update_min>motif_degree[temp_node])
//					update_min=motif_degree[temp_node];
				if(de.containsKey(temp_node)) {
					int temp=de.get(temp_node);
					de.remove((Integer)temp_node);
					
					de.put((Integer)temp_node, (Integer)(temp+Combination[0][entry.getValue()]));
				}else {
					de.put((Integer)temp_node, (Integer)(Combination[0][entry.getValue()]));
				}
				
				//motif_degree[temp_node]=motif_degree[temp_node]-Combination[0][entry.getValue()];
			}
			
			for(Entry<Integer,Integer> entry:de.entrySet()) {
				int temp_node=entry.getKey();
				if(motif_degree[temp_node]>motif_degree[index]) {
					int du=motif_degree[temp_node];
					int pu=pos[temp_node+1];
					int pw=bin[du];
					int w=vert[pw];
					if((temp_node+1)!=w) {
						pos[temp_node+1]=pw;
						vert[pu]=w;
						pos[w]=pu;
						vert[pw]=temp_node+1;							
					}
					bin[du]+=1;					
				}
				motif_degree[temp_node]=motif_degree[temp_node]-entry.getValue();
				if(motif_degree[temp_node]<0)
					motif_degree[temp_node]=0;
			}
			
			motif_degree[index]=0;
		}
		
		
		return result;
	}
	

}
