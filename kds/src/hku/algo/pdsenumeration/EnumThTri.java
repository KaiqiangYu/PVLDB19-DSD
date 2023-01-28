package hku.algo.pdsenumeration;


import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import hku.algo.cds.KList;
import hku.util.DataReader;

public class EnumThTri {
	
	//parameters we need
		/** adjacency matrix of given motif. */
		private int[][] Motif=null;
		/** adjacency matrix of given original graph. */
		private int[][] Graph=null;
		/** the number of vertex in the given motif. */
		private int motif_size=0;
		/** the number of vertex in the given graph. */
		private int graph_size=0;
		private int motif_type=1;
		public int motif_degree[];
		
		public Map<String, int[]> Statistic=null;
		
	public EnumThTri(int[][] Graph, int graph_size) {
		
		this.Motif=Motif;
		this.Graph=Graph;
		this.graph_size=graph_size;
		this.motif_type=motif_type;
	}
	
	
	public int Enumerate() {
		motif_degree=new int[graph_size];
		int i=0,j=0,size=Graph.length;
		int temp_a,temp_i=0,temp_j=0,count=0,sum=0;
		int[] temp_array=new int[8000];
		int temp_degree=0;
		for(;i<size;++i) {
			for(j=0;j<Graph[i].length;++j) {
				if(i<Graph[i][j]) {
					temp_a=Graph[i][j];
					count=0;
					for(temp_i=0;temp_i<Graph[i].length;++temp_i) {
						temp_j=0;
						while(temp_j<Graph[temp_a].length&&Graph[i][temp_i]!=Graph[temp_a][temp_j]) {
							++temp_j;
						}
						if(temp_j<Graph[temp_a].length&&Graph[i][temp_i]==Graph[temp_a][temp_j]) {
							temp_array[count]=Graph[i][temp_i];
							++count;
							++temp_j;
						}
					}
					if(count>=3) {
						temp_degree=((count-1)*count*(count-2))/6;
						
						sum+=temp_degree;
						motif_degree[i]+=temp_degree;
						motif_degree[Graph[i][j]]+=temp_degree;
						for(temp_i=0;temp_i<count;++temp_i) {
							motif_degree[temp_array[temp_i]]+=((count-2)*(count-1)/2);
						}
					}
					
				}
			}
		}
		return sum;
	}
	
public int Enumerate_One() {
		
		
		motif_degree=new int[graph_size];
		int i=0,j=0,size=Graph.length;
		int temp_a,temp_i=0,temp_j=0,count=0,sum=0;
		int[] temp_array=new int[8000];
		int temp_degree=0;
		for(;i<size;++i) {
			for(j=0;j<Graph[i].length;++j) {
				if(i<Graph[i][j]) {
					temp_a=Graph[i][j];
					if(i==0) {
						temp_a=Graph[i][j];
						count=0;
						for(temp_i=0;temp_i<Graph[i].length;++temp_i) {
							temp_j=0;
							while(temp_j<Graph[temp_a].length&&Graph[i][temp_i]!=Graph[temp_a][temp_j]) {
								++temp_j;
							}
							if(temp_j<Graph[temp_a].length&&Graph[i][temp_i]==Graph[temp_a][temp_j]) {
								temp_array[count]=Graph[i][temp_i];
								++count;
								++temp_j;
							}
						}
						if(count>=3) {
							temp_degree=((count-1)*count*(count-2))/6;
							
							sum+=temp_degree;
							motif_degree[i]+=temp_degree;
							//System.out.println(temp_degree+"kkk"+motif_degree[0]);
							motif_degree[Graph[i][j]]+=temp_degree;
							for(temp_i=0;temp_i<count;++temp_i) {
								motif_degree[temp_array[temp_i]]+=((count-2)*(count-1)/2);
							}
						}
					}else{ 
						boolean aa_i=false,aa_j=false;
						for(temp_i=0;temp_i<Graph[i].length;++temp_i){
							if(Graph[i][temp_i]==0){
								aa_i=true;
								break;
							}
						}
						if(aa_i){
							for(temp_i=0;temp_i<Graph[temp_a].length;++temp_i){
								if(Graph[temp_a][temp_i]==0){
									aa_j=true;
									break;
								}
							}
						}else{
							continue;
						}
						
						if(aa_i&&aa_j){
						temp_a=Graph[i][j];
						count=0;
						for(temp_i=0;temp_i<Graph[i].length;++temp_i) {
							temp_j=0;
							while(temp_j<Graph[temp_a].length&&Graph[i][temp_i]!=Graph[temp_a][temp_j]) {
								++temp_j;
							}
							if(temp_j<Graph[temp_a].length&&Graph[i][temp_i]==Graph[temp_a][temp_j]) {
								temp_array[count]=Graph[i][temp_i];
								++count;
								++temp_j;
							}
						}
						if(count>=3) {
							temp_degree=((count-2)*(count-1)/2);
							//System.out.println(temp_degree+"bbb"+motif_degree[0]+" "+i+" "+Graph[i][j]);
							sum+=temp_degree;
							motif_degree[i]+=temp_degree;
							motif_degree[Graph[i][j]]+=temp_degree;
							motif_degree[0]+=(temp_degree-count+2);
							for(temp_i=0;temp_i<count;++temp_i) {
								motif_degree[temp_array[temp_i]]+=(count-2);
							}
						}
					}}
					
					
				}
			}
		}
		return sum;
	}
	
	public static void main(String[] args) {
		 DataReader a=new DataReader("./datasets/yeast.txt","./motif/edge.txt");
         //DataReader a=new DataReader("./motif/3triangle.txt","./motif/3triangle.txt");
         int Graph[][]=a.readGraph();
         EnumThTri ec=new EnumThTri(Graph,a.graph_size);
         int aaa=ec.Enumerate();
         System.out.println(aaa);
         int count=0;
         for(int i=0;i<a.graph_size;++i) {
        	 	//System.out.println(ec.motif_degree[i]);
        	 	count+=ec.motif_degree[i];
        	 	
         }
         count=count/5;
 	 	 System.out.println(count);
	}
	

}
