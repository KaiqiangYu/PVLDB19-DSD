package hku.algo.pdsenumeration;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import hku.algo.cds.KList;
import hku.util.DataReader;

public class EnumBasket {
	
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
		
	public EnumBasket(int[][] Graph, int graph_size) {
		
		this.Motif=Motif;
		this.Graph=Graph;
		this.graph_size=graph_size;
		this.motif_type=motif_type;
	}
	
	
	public int Enumerate() {
		KList k=new KList(Graph, 3);
		k.ListRecord();
		
		motif_degree=new int[graph_size];
		int i=0,j=0,size=Graph.length;
		int temp_a,temp_i=0,temp_j=0,count=0,sum=0;
		int[] temp_array=new int[8000];
		int[] temp_array2=new int[8000];
		int[] temp_array_edge1=new int[8000];
		int[] temp_array_edge2=new int[8000];
		int[] label=new int[graph_size];
		Arrays.fill(label, 0);
		int temp_degree=0;
		int[] temp_array1;
		int node_a,node_b,node_c;
		int temp_count=0;
		int count_edge1=0,count_edge2=0;
		int count_sum=0;
		for(i=0;i<graph_size;++i)
			Arrays.sort(Graph[i]);
		
		for(Entry<String, int[]> entry : k.Statistic.entrySet()) {
			temp_array1=entry.getValue();
			node_a=temp_array1[0];
			node_b=temp_array1[1];
			node_c=temp_array1[2];
			
			count=0;temp_count=0;count_edge1=0;count_edge2=0;count_sum=0;
			temp_j=0;
			for(temp_i=0;temp_i<Graph[node_a].length;++temp_i) {		
				while(temp_j<Graph[node_b].length&&Graph[node_a][temp_i]>Graph[node_b][temp_j]) {
					++temp_j;
				}
				if(temp_j<Graph[node_b].length&&Graph[node_a][temp_i]==Graph[node_b][temp_j]&&Graph[node_a][temp_i]!=node_a&&Graph[node_a][temp_i]!=node_b&&Graph[node_a][temp_i]!=node_c) {
					temp_array[count]=Graph[node_a][temp_i];
					++count;
					++temp_j;
				}
			}
			temp_j=0;
			for(temp_i=0;temp_i<count;++temp_i) {		
				while(temp_j<Graph[node_c].length&&temp_array[temp_i]>Graph[node_c][temp_j]) {
					++temp_j;
				}
				if(temp_j<Graph[node_c].length&&temp_array[temp_i]==Graph[node_c][temp_j]&&Graph[node_c][temp_j]!=node_a&&Graph[node_c][temp_j]!=node_b&&Graph[node_c][temp_j]!=node_c) {
					temp_array2[temp_count]=temp_array[temp_i];
					label[temp_array[temp_i]]=1;
					++temp_count;
					++temp_j;
				}
			}
			
			temp_j=0;
			for(temp_i=0;temp_i<Graph[node_a].length;++temp_i) {		
				while(temp_j<Graph[node_c].length&&Graph[node_a][temp_i]>Graph[node_c][temp_j]) {
					++temp_j;
				}
				if(temp_j<Graph[node_c].length&&Graph[node_a][temp_i]==Graph[node_c][temp_j]&&Graph[node_a][temp_i]!=node_a&&Graph[node_a][temp_i]!=node_b&&Graph[node_a][temp_i]!=node_c) {
					temp_array_edge1[count_edge1]=Graph[node_a][temp_i];
					++count_edge1;
					++temp_j;
				}
			}
			
			temp_j=0;
			for(temp_i=0;temp_i<Graph[node_b].length;++temp_i) {		
				while(temp_j<Graph[node_c].length&&Graph[node_b][temp_i]>Graph[node_c][temp_j]) {
					++temp_j;
				}
				if(temp_j<Graph[node_c].length&&Graph[node_b][temp_i]==Graph[node_c][temp_j]&&Graph[node_b][temp_i]!=node_a&&Graph[node_b][temp_i]!=node_b&&Graph[node_b][temp_i]!=node_c) {
					temp_array_edge2[count_edge2]=Graph[node_b][temp_i];
					//System.out.println("****"+Graph[node_b][temp_i]);
					++count_edge2;
					++temp_j;
				}
			}
			
			for(temp_i=0;temp_i<count;++temp_i) {
				if(label[temp_array[temp_i]]!=1) {
					motif_degree[temp_array[temp_i]]+=temp_count;
					count_sum++;
				}
			}
			for(temp_i=0;temp_i<count_edge1;++temp_i) {
				if(label[temp_array_edge1[temp_i]]!=1) {
					motif_degree[temp_array_edge1[temp_i]]+=temp_count;
					count_sum++;
				}
			}
			for(temp_i=0;temp_i<count_edge2;++temp_i) {
				if(label[temp_array_edge2[temp_i]]!=1) {
					motif_degree[temp_array_edge2[temp_i]]+=temp_count;
					count_sum++;
				}
			}
			for(temp_i=0;temp_i<temp_count;++temp_i) {
				motif_degree[temp_array2[temp_i]]+=(6*(temp_count-1)+count_sum);
				label[temp_array2[temp_i]]=0;
			}
			motif_degree[node_a]+=(temp_count*count_sum+((temp_count)*(temp_count-1)*3));
			motif_degree[node_b]+=(temp_count*count_sum+((temp_count)*(temp_count-1)*3));
			motif_degree[node_c]+=(temp_count*count_sum+((temp_count)*(temp_count-1)*3));
			sum+=(temp_count*count_sum+((temp_count)*(temp_count-1)*3));
			//System.out.println(temp_count+" "+count+" "+count_edge1+" "+count_edge2+" "+count_sum+" "+(6*(temp_count-1)+count_sum)+" "+(temp_count*count_sum+((temp_count)*(temp_count-1)*3)));
			//System.out.println(node_a+" "+node_b+" "+node_c);
			//System.out.println(temp_array2[0]+" "+temp_array2[1]);
			//System.out.println(temp_array[0]+" "+temp_array[1]);
			//System.out.println(temp_array_edge1[0]+" "+temp_array_edge1[1]);
			//System.out.println(temp_array_edge2[0]+" "+temp_array_edge2[1]);
			//System.out.println(motif_degree[0]+" "+motif_degree[1]+" "+motif_degree[2]+" "+motif_degree[3]+" "+motif_degree[4]);
			//System.out.println();
			//for(temp_i=0;temp_i<)
		}
		for(i=0;i<graph_size;++i)
			motif_degree[i]/=2;
		
		
		return sum/2;
	}
	
public int Enumerate_One() {
		
		
	KList k=new KList(Graph, 3);
	k.ListRecord();
	
	motif_degree=new int[graph_size];
	int i=0,j=0,size=Graph.length;
	int temp_a,temp_i=0,temp_j=0,count=0,sum=0;
	int[] temp_array=new int[8000];
	int[] temp_array2=new int[8000];
	int[] temp_array_edge1=new int[8000];
	int[] temp_array_edge2=new int[8000];
	int[] label=new int[graph_size];
	Arrays.fill(label, 0);
	int temp_degree=0;
	int[] temp_array1;
	int node_a,node_b,node_c;
	int temp_count=0;
	int count_edge1=0,count_edge2=0;
	int count_sum=0;
	for(i=0;i<graph_size;++i)
		Arrays.sort(Graph[i]);
	
	for(Entry<String, int[]> entry : k.Statistic.entrySet()) {
		temp_array1=entry.getValue();
		node_a=temp_array1[0];
		node_b=temp_array1[1];
		node_c=temp_array1[2];
		
		count=0;temp_count=0;count_edge1=0;count_edge2=0;count_sum=0;
		temp_j=0;
		
		
		
		
		for(temp_i=0;temp_i<Graph[node_a].length;++temp_i) {		
			while(temp_j<Graph[node_b].length&&Graph[node_a][temp_i]>Graph[node_b][temp_j]) {
				++temp_j;
			}
			if(temp_j<Graph[node_b].length&&Graph[node_a][temp_i]==Graph[node_b][temp_j]&&Graph[node_a][temp_i]!=node_a&&Graph[node_a][temp_i]!=node_b&&Graph[node_a][temp_i]!=node_c) {
				temp_array[count]=Graph[node_a][temp_i];
				++count;
				++temp_j;
			}
		}
		temp_j=0;
		for(temp_i=0;temp_i<count;++temp_i) {		
			while(temp_j<Graph[node_c].length&&temp_array[temp_i]>Graph[node_c][temp_j]) {
				++temp_j;
			}
			if(temp_j<Graph[node_c].length&&temp_array[temp_i]==Graph[node_c][temp_j]&&Graph[node_c][temp_j]!=node_a&&Graph[node_c][temp_j]!=node_b&&Graph[node_c][temp_j]!=node_c) {
				temp_array2[temp_count]=temp_array[temp_i];
				label[temp_array[temp_i]]=1;
				++temp_count;
				++temp_j;
			}
		}
		
		temp_j=0;
		for(temp_i=0;temp_i<Graph[node_a].length;++temp_i) {		
			while(temp_j<Graph[node_c].length&&Graph[node_a][temp_i]>Graph[node_c][temp_j]) {
				++temp_j;
			}
			if(temp_j<Graph[node_c].length&&Graph[node_a][temp_i]==Graph[node_c][temp_j]&&Graph[node_a][temp_i]!=node_a&&Graph[node_a][temp_i]!=node_b&&Graph[node_a][temp_i]!=node_c) {
				temp_array_edge1[count_edge1]=Graph[node_a][temp_i];
				++count_edge1;
				++temp_j;
			}
		}
		
		temp_j=0;
		for(temp_i=0;temp_i<Graph[node_b].length;++temp_i) {		
			while(temp_j<Graph[node_c].length&&Graph[node_b][temp_i]>Graph[node_c][temp_j]) {
				++temp_j;
			}
			if(temp_j<Graph[node_c].length&&Graph[node_b][temp_i]==Graph[node_c][temp_j]&&Graph[node_b][temp_i]!=node_a&&Graph[node_b][temp_i]!=node_b&&Graph[node_b][temp_i]!=node_c) {
				temp_array_edge2[count_edge2]=Graph[node_b][temp_i];
				//System.out.println("****"+Graph[node_b][temp_i]);
				++count_edge2;
				++temp_j;
			}
		}
		
		if(node_a==0||node_b==0||node_c==0) {
			for(temp_i=0;temp_i<count;++temp_i) {
				if(label[temp_array[temp_i]]!=1) {
					motif_degree[temp_array[temp_i]]+=temp_count;
					count_sum++;
				}
			}
			for(temp_i=0;temp_i<count_edge1;++temp_i) {
				if(label[temp_array_edge1[temp_i]]!=1) {
					motif_degree[temp_array_edge1[temp_i]]+=temp_count;
					count_sum++;
				}
			}
			for(temp_i=0;temp_i<count_edge2;++temp_i) {
				if(label[temp_array_edge2[temp_i]]!=1) {
					motif_degree[temp_array_edge2[temp_i]]+=temp_count;
					count_sum++;
				}
			}
			
			for(temp_i=0;temp_i<temp_count;++temp_i) {
				motif_degree[temp_array2[temp_i]]+=(6*(temp_count-1)+count_sum);
				label[temp_array2[temp_i]]=0;
			}
			motif_degree[node_a]+=(temp_count*count_sum+((temp_count)*(temp_count-1)*3));
			motif_degree[node_b]+=(temp_count*count_sum+((temp_count)*(temp_count-1)*3));
			motif_degree[node_c]+=(temp_count*count_sum+((temp_count)*(temp_count-1)*3));
			sum+=(temp_count*count_sum+((temp_count)*(temp_count-1)*3));
		}else if(label[0]==1) {
			for(temp_i=0;temp_i<count;++temp_i) {
				if(label[temp_array[temp_i]]!=1) {
					motif_degree[temp_array[temp_i]]+=1;
					count_sum++;
				}
			}
			for(temp_i=0;temp_i<count_edge1;++temp_i) {
				if(label[temp_array_edge1[temp_i]]!=1) {
					motif_degree[temp_array_edge1[temp_i]]+=1;
					count_sum++;
				}
			}
			for(temp_i=0;temp_i<count_edge2;++temp_i) {
				if(label[temp_array_edge2[temp_i]]!=1) {
					motif_degree[temp_array_edge2[temp_i]]+=1;
					count_sum++;
				}
			}
			motif_degree[temp_array2[0]]+=(6*(temp_count-1)+count_sum);
			label[temp_array2[0]]=0;
			for(temp_i=1;temp_i<temp_count;++temp_i) {
				motif_degree[temp_array2[temp_i]]+=(6);
				label[temp_array2[temp_i]]=0;
			}
			motif_degree[node_a]+=(count_sum+((temp_count-1)*6));
			motif_degree[node_b]+=(count_sum+((temp_count-1)*6));
			motif_degree[node_c]+=(count_sum+((temp_count-1)*6));
			sum+=(count_sum+((temp_count-1)*6));
		}else if((count!=0&&temp_array[0]==0)||(count_edge1!=0&&temp_array_edge1[0]==0)||(count_edge2!=0&&temp_array_edge2[0]==0)) {
//			if((count!=0&&temp_array[0]==0)) {
//				
//			}else if((count_edge1!=0&&temp_array_edge1[0]==0)) {
//				
//			}else if((count_edge2!=0&&temp_array_edge2[0]==0)) {
//				
//			}
			motif_degree[0]+=temp_count;
			
			for(temp_i=0;temp_i<temp_count;++temp_i) {
				motif_degree[temp_array2[temp_i]]+=1;
				label[temp_array2[temp_i]]=0;
			}
			motif_degree[node_a]+=temp_count;
			motif_degree[node_b]+=temp_count;
			motif_degree[node_c]+=temp_count;
			sum+=temp_count;
			
		}
		
		
		//System.out.println(temp_count+" "+count+" "+count_edge1+" "+count_edge2+" "+count_sum+" "+(6*(temp_count-1)+count_sum)+" "+(temp_count*count_sum+((temp_count)*(temp_count-1)*3)));
		//System.out.println(node_a+" "+node_b+" "+node_c);
		//System.out.println(temp_array2[0]+" "+temp_array2[1]);
		//System.out.println(temp_array[0]+" "+temp_array[1]);
		//System.out.println(temp_array_edge1[0]+" "+temp_array_edge1[1]);
		//System.out.println(temp_array_edge2[0]+" "+temp_array_edge2[1]);
		//System.out.println(motif_degree[0]+" "+motif_degree[1]+" "+motif_degree[2]+" "+motif_degree[3]+" "+motif_degree[4]);
		//System.out.println();
		//for(temp_i=0;temp_i<)
	}
	for(i=0;i<graph_size;++i)
		motif_degree[i]/=2;
	
	
	return sum/2;
	}
	
	public static void main(String[] args) {
		 DataReader a=new DataReader("./datasets/Adjnoun.txt","./motif/edge.txt");
         //DataReader a=new DataReader("./motif/3triangle.txt","./motif/3triangle.txt");
         int Graph[][]=a.readGraph();
         EnumBasket ec=new EnumBasket(Graph,a.graph_size);
         int aaa=ec.Enumerate_One();
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
