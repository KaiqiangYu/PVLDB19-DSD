package hku.algo.prune;

import java.util.Arrays;

public class LocateCore {
	
	private int[][] Graph=null;
	//private int[][] Motif=null;
	private double[][] core=null;
	private int graph_size;
	
	public LocateCore(int[][] Graph,double[][] core,int graph_size) {
		this.Graph=Graph;
		this.core=core;
		this.graph_size=graph_size;
	}
	
	public DensestCore locate() {
		//find the low bound
		double max=core[0][2];
		double kmax=0;
		for(int i=1;i<graph_size;++i) {
			if(max<core[i][2])
				max=core[i][2];
			if(kmax<core[i][1])
				kmax=core[i][1];
		}
			
		int low_bound=(int) Math.ceil(max);
		//System.out.println(low_bound+"()");
		
		int index=1;
		int delete[]=new int[graph_size];
		Arrays.fill(delete, 0);
		for(;index<graph_size;++index) {
			if(core[index][1]>=low_bound)
				break;
			delete[(int) core[index][0]]=-1;
		}
		//System.out.println("*********"+index);
		int temp=0;
		for(int i=0;i<graph_size;++i) {
			if(delete[i]==0) {
				delete[i]=temp;
				temp++;
			}
		}
		
		double[][] new_core=new double[temp][2];
		
		int New_graph_size=temp;
		int New_Graph[][]=new int[New_graph_size][];
		temp=0;
		for(int i=index;i<graph_size;++i) {
			int m=(int)core[i][0];
			new_core[temp][0]=delete[m];
			new_core[temp][1]=core[i][1];
			temp++;
		}
		
		
		
		for(int i=0;i<graph_size;++i) {
			if(delete[i]!=-1) {
				int count=0;
				int array[]=new int[Graph[i].length];
				for(int j=0;j<Graph[i].length;++j) {
					if(delete[Graph[i][j]]!=-1) {
						array[count]=delete[Graph[i][j]];
						count++;
					}					
				}
				New_Graph[delete[i]]=new int[count];
				for(int j=0;j<count;++j) {
					New_Graph[delete[i]][j]=array[j];
				}
				
			}
		}
		int delete_motif=(int)(core[0][3]-core[index-1][3]);
		int motif_degree[]=new int[New_graph_size];
		
		DensestCore result=new DensestCore(New_Graph, New_graph_size, low_bound, index-1, delete_motif,core[index-1][2],(int)kmax);
		//System.out.println("^^^^^"+core[index-1][3]);
		return result;
	}
	

}
