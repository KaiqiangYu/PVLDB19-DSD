package hku.algo.pdsenumeration;

import java.util.Map;
import java.util.Map.Entry;

import hku.algo.cds.KList;
import hku.util.DataReader;

public class EnumCstar {
	
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
		
		private Map<String, int[]> Statistic=null;
		
	public EnumCstar(int[][] Graph, int graph_size) {
		
		this.Motif=Motif;
		this.Graph=Graph;
		this.graph_size=graph_size;
		this.motif_type=motif_type;
	}
	
	
	public int Enumerate() {
		//find all core instance
		KList k=new KList(Graph, 3);
		k.ListRecord();
		
		//for each core instance, enumerate all matches
		motif_degree=new int[graph_size];
		int[] temp_array;
		int i=0,count=0,j=0;
		for(Entry<String, int[]> entry : k.Statistic.entrySet()) {
			temp_array=entry.getValue();
			i=0;
			count=0;
			for(;i<3;++i) {
				for(j=0;j<Graph[temp_array[i]].length;++j) {
					if(Graph[temp_array[i]][j]!=temp_array[0]&&
							Graph[temp_array[i]][j]!=temp_array[1]&&
							Graph[temp_array[i]][j]!=temp_array[2]) {
						++count;
						motif_degree[Graph[temp_array[i]][j]]++;
					}
				}
			}
			for(i=0;i<3;++i) {
				motif_degree[temp_array[i]]+=count;
			}
		}
		return count;
	}
	
	public int Enumerate_One() {
		//find all core instance
		KList k=new KList(Graph, 3);
		k.ListRecord();
		
		//for each core instance, enumerate all matches
		motif_degree=new int[graph_size];
		int[] temp_array;
		int i=0,count=0,j=0;boolean my;
		for(Entry<String, int[]> entry : k.Statistic.entrySet()) {
			temp_array=entry.getValue();
			i=0;
			count=0;
			my=false;
			if(temp_array[0]!=0&&temp_array[1]!=0&&temp_array[2]!=0)
				my=true;
			for(;i<3;++i) {
				if(!my){
					for(j=0;j<Graph[temp_array[i]].length;++j) {
						if(Graph[temp_array[i]][j]!=temp_array[0]&&
								Graph[temp_array[i]][j]!=temp_array[1]&&
								Graph[temp_array[i]][j]!=temp_array[2]) {
							++count;
							motif_degree[Graph[temp_array[i]][j]]++;
						}
					}
				}else{
					for(j=0;j<Graph[temp_array[i]].length;++j) {
						if(Graph[temp_array[i]][j]==0) {
							++count;
							motif_degree[Graph[temp_array[i]][j]]++;
						}
					}
				}
				
			}
			for(i=0;i<3;++i) {
				motif_degree[temp_array[i]]+=count;
			}
		}
		return count;
	}
	
	public static void main(String[] args) {
		 DataReader a=new DataReader("./datasets/yeast.txt","./motif/edge.txt");
         //DataReader a=new DataReader("./motif/3triangle.txt","./motif/3triangle.txt");
         int Graph[][]=a.readGraph();
         EnumCstar ec=new EnumCstar(Graph,a.graph_size);
         ec.Enumerate();
         int count=0;
         for(int i=0;i<a.graph_size;++i) {
        	 	//System.out.println(ec.motif_degree[i]);
        	 	count+=ec.motif_degree[i];
        	 	
         }
         count=count/4;
 	 	 System.out.println(count);
	}
	

}