package hku.algo.pdscoredecompose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * core-decompose algorithm for star-motif
 * @author yukaiqiang
 * @date Nov,27 2017
 */
public class StarMotif {
	
	/** adjacent list of the given graph*/
	private int[][] Graph=null;
	/** the number of tail in the given star-motif*/
	private int star=0;
	/** Combination*/
	private int[][] Combination=null;
	/** the number of vertices in the given graph*/
	private int graph_size;
	
	/**
	 * 
	 * @param Graph adjacent list of the given graph
	 * @param star the number of tail in the given star-motif
	 * @param Combination Combination
	 */
	public StarMotif(int[][] Graph,int star,int[][] Combination,int graph_size) {
		this.Graph=Graph;
		this.star=star;
		this.Combination=Combination;
		this.graph_size=graph_size;
	}
	
	/**
	 * Decompose the star-motif core
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
		int degree[]=new int[graph_size];
		int motif_degree[]=new int[graph_size];
		int Delete[]=new int[graph_size];
		int max=0;
		long motif_num=0;
		int count=0;
		Arrays.fill(Delete, 0);
		
		//compute the motif-degree of each vertex
		for(int i=0;i<graph_size;++i) {
			int count2=0;
			for(int j=0;j<Graph[i].length;++j) {
				if(Graph[i][j]!=i)
					count2++;
			}
			//System.out.println(Graph[i].length);
			degree[i]=count2;
			motif_degree[i]=Combination[0][degree[i]];
		}		
		
		for(int i=0;i<graph_size;++i) {
			for(int j=0;j<Graph[i].length;++j) {
				if(Graph[i][j]!=i) {
					int temp=Graph[i][j];
					if(degree[temp]>0)
					motif_degree[i]+=Combination[1][degree[temp]-1];
				}
				
			}
		}
		
		
		//compute the total number of the motifs
		for(int i=0;i<graph_size;++i) {
			motif_num+=motif_degree[i];
			//System.out.println(motif_degree[i]);
			if(max<motif_degree[i]) {
				max=motif_degree[i];
			}
			//node_sort[i]=node_update[i]=new CoreNode(i,motif_degree[i]);
		}
		max=max+1;
		//System.out.println(max);
		//initialize the data structure
//		ArrayList node_list[]=new ArrayList[max];
//		for(int i=0;i<max;++i)
//			node_list[i]=new ArrayList<Integer>();
//		for(int i=0;i<graph_size;++i)
//			node_list[motif_degree[i]].add(i);
		
		motif_num=motif_num/(1+star);
		
		//data structure used to save the result
		double[][] result=new double[graph_size+1][3];
		result[0][2]=motif_num/(double)graph_size;
		
		System.out.println(motif_num);
		
		//int index_min=0,update_min=Integer.MAX_VALUE;
		
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
		Map<Integer,Integer> de=new HashMap<Integer,Integer>();
		
		for(int i=1;i<=graph_size;++i) {
			
			//get the vertex with minimum motif-degree. time complexity: O(d) 
//			if(update_min<index_min) {
//				index_min=update_min;
//			}else {
//				for(;index_min<max;++index_min) {
//					if(!node_list[index_min].isEmpty())
//						break;
//				}
//			}
//			update_min=Integer.MAX_VALUE;
//			int index=(Integer) node_list[index_min].remove(0);
			
			int index=vert[i]-1;
			if(index<0||Delete[index]==1)
				continue;
			//record the result
			count++;
			result[count][0]=index;
			result[count][1]=motif_degree[index];
			motif_num-=motif_degree[index];
			
			if(graph_size-count>0) {
				result[count][2]=motif_num/(double)(graph_size-count);
			}else {
				result[count][2]=0;
			}
			
			//update
			Delete[index]=1;
			motif_degree[index]=0;
			for(int j=0;j<Graph[index].length;++j) {
				int temp=Graph[index][j];
				if(Delete[temp]==0&&index!=Graph[index][j]) {
					//update this node
					int copy=motif_degree[temp];
					
					
					if(motif_degree[temp]>motif_degree[index]) {
						int du=motif_degree[temp];
						int pu=pos[temp+1];
						int pw=bin[du];
						int w=vert[pw];
						if((temp+1)!=w) {
							pos[temp+1]=pw;
							vert[pu]=w;
							pos[w]=pu;
							vert[pw]=temp+1;							
						}
						bin[du]+=1;					
					}
					if(degree[temp]-1>=0&&degree[index]-1>=0)
					motif_degree[temp]=motif_degree[temp]-
							Combination[1][degree[index]-1]-
							Combination[1][degree[temp]-1];
					
//					if(update_min>motif_degree[temp])
//						update_min=motif_degree[temp];
//					node_list[copy].remove((Integer)temp);
//					node_list[motif_degree[temp]].add((Integer)temp);
					
					
					//update neighbor
					for(int k=0;k<Graph[temp].length;++k) {
						int neighbor=Graph[temp][k];
						if(Delete[neighbor]==0&&Graph[temp][k]!=temp) {
							copy=motif_degree[neighbor];
							
							
							if(motif_degree[neighbor]>motif_degree[index]) {
								int du=motif_degree[neighbor];
								int pu=pos[neighbor+1];
								int pw=bin[du];
								int w=vert[pw];
								if((neighbor+1)!=w) {
									pos[neighbor+1]=pw;
									vert[pu]=w;
									pos[w]=pu;
									vert[pw]=neighbor+1;							
								}
								bin[du]+=1;					
							}
							if(degree[temp]-2>=0)
							motif_degree[neighbor]=motif_degree[neighbor]-
									Combination[2][degree[temp]-2];
							
//							if(update_min>motif_degree[neighbor])
//								update_min=motif_degree[neighbor];
//							node_list[copy].remove((Integer)neighbor);
//							node_list[motif_degree[neighbor]].add((Integer)neighbor);
							
						}
					}
				}
			}
			for(int j=0;j<Graph[index].length;++j)
				degree[Graph[index][j]]--;
			
			
			
			
		}
		
		return result;
		
	}
	
	public static void main(String[] args) {
		
	}
	
	/* this version is abolished because of the terrible time complexity of selecting minimum node.
	public void Decompose() {
		int degree[]=new int[graph_size];
		int motif_degree[]=new int[graph_size];
		int Delete[]=new int[graph_size];
		CoreNode[] node_sort=new CoreNode[graph_size];
		CoreNode[] node_update=new CoreNode[graph_size];
		long motif_num=0;
		int count=0;
		Arrays.fill(Delete, 0);
		
		//compute the motif-degree of each vertex
		for(int i=0;i<graph_size;++i) {
			degree[i]=Graph[i].length-1;
			motif_degree[i]=Combination[0][degree[i]];
		}		
		
		for(int i=0;i<graph_size;++i) {
			for(int j=1;j<Graph[i].length;++j) {
				motif_degree[i]+=Combination[1][degree[i]-1];
			}
		}
		//compute the total number of the motifs
		for(int i=0;i<graph_size;++i) {
			motif_num+=motif_degree[i];
			node_sort[i]=node_update[i]=new CoreNode(i,motif_degree[i]);
		}
		motif_num=motif_num/(1+star);
		
		//data structure used to save the result
		double[][] result=new double[3][graph_size+1];
		result[0][2]=motif_num/(double)graph_size;
		
		MinHeap minheap=new MinHeap(node_sort);
		CoreNode min_node=null;
		for(int i=0;i<graph_size;++i) {
			//get the vertex with minimum motif-degree. time complexity: O(logn) 
			min_node=(CoreNode) minheap.deleMin();
			
			//record the result
			count++;
			result[count][0]=min_node.index;
			result[count][1]=min_node.num;
			motif_num-=motif_degree[min_node.index];
			result[count][2]=motif_num/(double)graph_size;
			
			//update
			int index=min_node.index;
			Delete[index]=1;
			motif_degree[index]=0;
			for(int j=0;j<Graph[index].length;++j) {
				int temp=Graph[index][j];
				if(Delete[temp]==0) {
					//update this node
					motif_degree[temp]=motif_degree[temp]-
							Combination[1][degree[index]-1]-
							Combination[1][degree[temp]-1];
					
					//update the min heap
					node_update[temp].num=motif_degree[temp];
					
					//update neighbor
					for(int k=0;k<Graph[temp].length;++k) {
						int neighbor=Graph[temp][k];
						if(Delete[neighbor]==0) {
							motif_degree[neighbor]=motif_degree[neighbor]-
									Combination[2][degree[temp]-2];
							//update the min heap
							node_update[neighbor].num=motif_degree[neighbor];
						}
					}
				}
			}
		}
		
	}
	*/

}

