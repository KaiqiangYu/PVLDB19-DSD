package hku.algo.cds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;

import hku.util.Combination;
import hku.util.DataReader;
import hku.util.KCore;

public class TDCDS {

	private int[][] graph;
	private int graph_size;
	private long[] V_Est;
	private int motif_size;
	private int[][] motif;
	private long deposit;
	private long[] Corenum;
	private long[] index;
	private long[] record_index;
	private ArrayList<Integer> list;
	private int motif_type;
	private int motif_d;
	Map<Integer,Integer>[] motif_graph;
	private int delete[];
	int mark[];
	int new_array[];
	int new_map[];
	long min_core=0L;
	long combinate[];
	long current_max=0;
	long current_core=0;
	int insert[];
	//private int motif_count;
	
	public TDCDS(int[][] graph, int graph_size,int[][] motif,int motif_type,int motif_d) {
		this.graph=graph;
		this.motif=motif;
		this.motif_type=motif_type;
		//System.out.println(motif_type);
		this.graph_size=graph_size;
		V_Est=new long[graph_size];
		Corenum=new long[graph_size];
		index=new long[graph_size];
		motif_size=motif.length;
		record_index=new long[graph_size];
		Arrays.fill(record_index, -1);
		Arrays.fill(Corenum, -1);
		Arrays.fill(index, 0);
		this.motif_d=motif_d;
		
		mark=new int[graph_size];
		new_array=new int[graph_size];
		new_map=new int[graph_size];
		delete=new int[graph_size];
		insert=new int[graph_size];
		Arrays.fill(insert,0);
		Arrays.fill(delete,0);
		Arrays.fill(mark, 0);		
		Arrays.fill(new_array, 0);		
		Arrays.fill(new_map, 0);
	}
	
	
	
	public void EstimateByCore(long Combination[]) {
		KCore kcore=new KCore(graph);
		combinate=Combination;
		int temp[]=kcore.decompose();
//		System.out.println(kcore.obtainMaxCore()+"  (((");
		//kcore.distribute();
		for(int i=0;i<graph_size;++i) {
			V_Est[i]=Combination[temp[i]];
		}
	}
	
	public void Estimate(long arr[]) {
		int max=0;
		this.combinate=arr;
		motif_graph=new Map[graph_size];
		//int motif_graph[][]=new int[graph_size][];
		
		KList kk=new KList(graph,motif_size);
		kk.ListFast();
		V_Est=kk.getMotifDegree();
		

		
	}
	
	private boolean Computecore(Long k_l,Long k_u) {
		Long k=k_l;
		boolean over=false;
		boolean abc;
		int num=0;
		for(int i=0;i<list.size();++i){
			int temp=list.get(i);
			if(index[temp]>=k_l){
				num++;				
			}
		}
		if(num<combinate.length&&combinate[num]<k_l)return false;
		while(k<=k_u) {
			
			while(true) {
				abc=true;
				for(int i=0;i<list.size();++i) {
					int temp=list.get(i);
					if(index[temp]<k) {
						abc=false;
						list.remove(i);
						i--;
						V_Est[temp]=k-1;
						if(index[temp]>0) {
							Map<Integer,Long> map=Generate(temp,mark,new_array,new_map);
							if(!map.isEmpty())
								for(Entry<Integer, Long> entry : map.entrySet()) {
									int temp_key=entry.getKey();
									long temp_value=entry.getValue();
									index[temp_key]-=temp_value;
								}
						}
						
						mark[temp]=-1;
					}
				}
				if(abc)
					break;
			}
			
			for(int i=0;i<list.size();++i) {
				int temp=list.get(i);
				Corenum[temp]=k;	
				over=true;
			}			
			if(list.size()==0)
				break;
			k=k+1;
		}
		deposit=k-1;
		//System.out.println("K_L :"+k_l+" K_U: "+k_u);
		return over;
	}
	
	
	private boolean ComputecoreBash(Long k_l,Long k_u) {
		
		Long k=k_l;
		boolean over=false;
		boolean abc;
		int num=0;

		
		while(k<=k_u) {
			//System.out.println(k+" "+k_u+" "+current_max);
			while(true) {
				
				ArrayList<Integer> list_delete=new ArrayList<Integer>();
				abc=true;
				for(int i=0;i<list.size();++i) {
					
					int temp=list.get(i);
					//System.out.println(k);
					if(index[temp]<k) {
						list.remove(i);
						//i--;
						delete[temp]=1;
						V_Est[temp]=k-1;
						if(index[temp]!=0) {
							list_delete.add(temp);
						}
							
							//System.out.println(index[temp]);
							mark[temp]=-1;
						}
//						
						
				}
				
				Map<Integer,Long> map=Generate(delete,list_delete,mark,new_array,new_map);
				if(!map.isEmpty())
					for(Entry<Integer, Long> entry : map.entrySet()) {
						int temp_key=entry.getKey();
						long temp_value=entry.getValue();
						index[temp_key]-=temp_value;
						//System.out.println(temp_value);
				}
				for(int i=0;i<list.size();++i) {
					int temp=list.get(i);
					if(index[temp]<k) {
						abc=false;
					}
				}
				if(abc)
					break;
			}
			if(list.size()>0) {
				long kkkk=0XFFFFFF;
				for(int i=0;i<list.size();++i) {
					
					int temp=list.get(i);
					if(index[temp]<kkkk) {
						kkkk=index[temp];
					}
				}
				if(k<kkkk)
					k=kkkk;
			}
			
//			for(int i=0;i<list.size();++i) {
//				int temp=list.get(i);
//				Corenum[temp]=k;	
////				over=true;
//			}
			if(k>=current_max)
				over=true;
			if(list.size()==0)
				break;
			current_core=k;
			
			k=k+1;
			
			
		}
		deposit=k-1;
		//System.out.println("K_L :"+k_l+" K_U: "+k_u+" "+k);
		return over;
	}
	
	public void GetResults() {
		for(int i=0;i<V_Est.length;++i) {
			if(V_Est[i]==deposit) {
				System.out.println(i);
			}
		}
	}
	
	public void GetDensity() {
		ArrayList<Integer> a=new ArrayList<Integer>();
		for(int i=0;i<V_Est.length;++i) {
			if(V_Est[i]==deposit) {
				a.add(i);
			}
		}
		long count_ver=0;
		int[] aa1=new int[graph_size];
		int[] aa2=new int[graph_size];
		int[] aa3=new int[graph_size];
		int[] aa4=new int[graph_size];
		Arrays.fill(aa1, 0);
		Arrays.fill(aa2, -1);
		Arrays.fill(aa3, 0);
		Arrays.fill(aa4, 0);
		for(int i=0;i<V_Est.length;++i) {
			if(V_Est[i]==deposit) {
				count_ver++;
				aa2[i]=0;
				aa3[i]=1;
			}
		}
				
		Map<Integer,Long> map=Generate(aa3,a,aa2,aa4,aa1);
		long count_num=0;
		if(!map.isEmpty())
			for(Entry<Integer, Long> entry : map.entrySet()) {
				long temp_value=entry.getValue();
				count_num+=temp_value;			
		}
		count_num/=motif_size;
		System.out.println("clique number:"+ count_num);
		System.out.println("vertex number:"+ count_ver);
		System.out.println("Density:" +count_num*1.0/count_ver);
		
		
	}
	
	public Long EMcore(Long k_l,Long k_u) {
		
		
		long min=0XFFFFFF;
		int again=0;
		//System.out.println(list.size());
		ArrayList<Integer> aaa=new ArrayList<Integer>();
		for(int i=0;i<list.size();++i) {
			int temp=list.get(i);
			insert[temp]=1;
			mark[temp]=0;
			aaa.add(temp);
			delete[temp]=0;
//			if(record_index[temp]==-1) {
//				mark[temp]=0;
//				aaa.add(temp);
//				insert[temp]=1;
//			}
			
		}
	
		Map<Integer,Long> a=Generate(insert,aaa,mark,new_array,new_map);
		//System.out.println(list.size()+" "+a.entrySet().size()+" "+aaa.size());
		for(Entry<Integer,Long> entry : a.entrySet()) {
			int node=entry.getKey();
			Long degree=entry.getValue();
			
			//if(record_index[node]==-1){
				record_index[node]=degree;
			//}else
				//record_index[node]+=degree;
		}
		
		
//		for(int i=0;i<list.size();++i) {
//			int temp=list.get(i);
//			
//			if(record_index[temp]==-1) {
//				mark[temp]=0;
//				Map<Integer,Long> a=Generate(temp,mark,new_array,new_map);
//				int count=0;
//////				
//				for(Entry<Integer,Long> entry : a.entrySet()) {
//					int node=entry.getKey();
//					Long degree=entry.getValue();
//					count+=degree;
//					record_index[node]+=degree;
//				}
//				record_index[temp]=count/(motif_size-1);
//				if(record_index[temp]>=min_core)
//					again++;
//			}
//			
//			
//			
//		}
		k_l=record_index[list.get(0)];
		k_u=record_index[list.get(0)];
		for(int i=0;i<list.size();++i) {
			int temp=list.get(i);
			if(k_l>record_index[list.get(i)])
				k_l=record_index[list.get(i)];
			if(k_u<record_index[list.get(i)])
				k_u=record_index[list.get(i)];
			
		}
		
		k_l=current_core>k_l?current_core:k_l;
		//System.out.println("****");
		boolean del=false;
		for(int i=0;i<list.size();++i) {
			int temp=list.get(i);
			
			//System.out.println(temp+"  "+record_index[temp]);
			
			index[temp]=record_index[temp];
			//System.out.println(index[temp]);
			if(min>index[temp]&&index[temp]!=0) {
				min=index[temp];	
				del=true;
			}
					
		}
//		if(!del)
//			min=0;
//		//System.out.println("&&&&");
//		if(again==0&&k_l>min_core)
//			return -1L;
		if(min_core<min)
			min_core=min;
//		for(int i=0;i<graph_size;++i) {
//			index[i]=record
//		}
	//System.out.println("k_u "+k_u+" current_max:"+current_max+" "+current_core);
		boolean over=ComputecoreBash(k_l-1,k_u);
		Long core=-1L;
		if(over) {
			core=k_l;
		}
		return core;
	}
	
	public void TDAlg() {
		//long s1=System.currentTimeMillis();
		//Estimate();
		//EstimateApp(index,mmm);
		//EstimateByMotif(com);
		
		//long s2=System.currentTimeMillis();
		//System.out.println("finish"+" "+(s2-s1));
		Arrays.fill(index, 0);
		Arrays.fill(mark, -1);
		Long k_u=0L;
		for(Long a:V_Est) {
			if(a>k_u)
				k_u=a;
		}
		
//		while(true) {
//			int count_node=0;
//			int next=0;
//			for(int i=0;i<graph_size;++i) {
//				if(k_u<=V_Est[i]) {
//					V_Est[i]=k_u;
//					count_node++;
//				}else if(next<V_Est[i]){
//					next=V_Est[i];
//				}
//					
//			}
//			long count_size=1;
//			//long del=1;
//			for(int i=1;i<=motif_size;++i) {
//				count_size=(count_node)*count_size;
//				//del=del*(i+1);
//			}
//			count_size=count_size/motif_type;
//			if(count_size>k_u)
//				break;
//			k_u=next;
//			
//		}
		
		//k_u=204;
		Long core=-1L;
		Long k_l=k_u;
		Long max=0L;
//		int count=0;
		int iterate=100;
//		int countwhile=0;
		int arr[]=new int[graph_size];
		Arrays.fill(arr, -1);
		max=0L;
		for(int i=0;i<graph_size;++i) {
			if(max<V_Est[i]) {
				max=V_Est[i];
			}
		}
		k_u=max;
		do {	
//			countwhile++;
			k_u=k_l;
			max=0L;
			do {
				max=0L;
			for(int i=0;i<graph_size;++i) {
				if(max<V_Est[i]&&arr[i]==-1) {
					max=V_Est[i];
				}
			}
			list=new ArrayList<Integer>();
//			if(countwhile==1)
			
			k_l=max;
			for(int i=0;i<graph_size;++i) {
				if(V_Est[i]>=k_l) {
					arr[i]=0;
					//System.out.println("***"+i+" "+arr[i]);
					Corenum[i]=0;
					list.add(i);
					if(record_index[i]!=-1)
						mark[i]=0;
				}
			}
			//System.out.println(max);
			}while(list.size()<iterate&&list.size()<graph_size);
			iterate=list.size();
			iterate=iterate*2;;
			current_max=0;
			for(int i=0;i<graph_size;++i) {
				if(V_Est[i]>current_max&&V_Est[i]<max)
					current_max=V_Est[i];
			}
			//System.out.println("()"+list.size()+" "+current_max+" "+k_u+" "+max);
			//current_max=600;
			core=EMcore(k_l,k_u);
			
//			k_u=k_l-1;
//			k_l=k_u;
//			for(int i=0;i<graph_size;++i) {
//				if(max<V_Est[i]&&record_index[i]==-1) {
//					max=V_Est[i];
//				}
//			}
			
			
		}while(core==-1);
		System.out.println("max-core: "+deposit);
	}
	
	

	
	
	public Map<Integer,Long> Generate(int index,int mark[],int array[],int map_s[]){
		LinkedList temp_list=new LinkedList<Integer>();
		temp_list.add(index);
		array[index]=1;
		Queue queue=new LinkedList();
		queue.add(index);
		int d=1;
				
		while(!queue.isEmpty()&&d+1<=2) {
			int temp=(Integer) queue.poll();
			d=array[temp];
			for(int i=0;i<graph[temp].length;++i) {
				if(array[graph[temp][i]]==0&&d+1<=2&&mark[graph[temp][i]]==0) {
					queue.add(graph[temp][i]);
					array[graph[temp][i]]=d+1;
					temp_list.add(graph[temp][i]);
				}
			}
		}
		
		int count=temp_list.size();
		int map_array[]=new int[count];
		int num=0;
		int new_graph[][]=new int[count][];
		for(int i=0;i<count;++i) {
			int node=(Integer) temp_list.get(i);
			map_array[i]=node;
			map_s[node]=num;
			
				int temp_count=0;
				for(int j=0;j<graph[node].length;++j) {
					if(array[graph[node][j]]!=0&&graph[node][j]!=node) {
						temp_count++;
					}
				}
				new_graph[num]=new int[temp_count];
				temp_count=0;
				for(int j=0;j<graph[node].length;++j) {
					if(array[graph[node][j]]!=0&&graph[node][j]!=node) {
						new_graph[num][temp_count]=graph[node][j];
						temp_count++;
					}
				}
				num++;
		}
		for(int i=0;i<count;++i) {
			int update=(Integer) temp_list.get(i);
			array[update]=0;
		}
		

		
		for(int i=0;i<count;++i) {
			for(int j=0;j<new_graph[i].length;++j) {
				new_graph[i][j]=map_s[new_graph[i][j]];
			}
		}
		
		KList f=new KList(new_graph,motif_size);
		f.ListOne(0);
		long[] t_a=f.getMotifDegree();
		//System.out.println(t_a[0]);
		Map<Integer, Long> result=new HashMap<Integer,Long>();
		for(int i=1;i<t_a.length;++i) {
			if(t_a[i]>0)
				result.put(map_array[i], t_a[i]);
		}
		return result;
	}
	
	
	
	public Map<Integer,Long> Generate(int index[],ArrayList<Integer> list,int mark[],int array[],int map_s[]){
		LinkedList temp_list=new LinkedList<Integer>();
		Queue queue=new LinkedList();
		for(int i=0;i<list.size();++i) {
			temp_list.add(list.get(i));
			array[list.get(i)]=1;			
			queue.add(list.get(i));
		}
		
		int d=1;
				
		while(!queue.isEmpty()&&d+1<=2) {
			int temp=(Integer) queue.poll();
			d=array[temp];
			for(int i=0;i<graph[temp].length;++i) {
				if(array[graph[temp][i]]==0&&d+1<=2&&mark[graph[temp][i]]==0) {
					queue.add(graph[temp][i]);
					array[graph[temp][i]]=d+1;
					temp_list.add(graph[temp][i]);
				}
			}
		}
		
		int count=temp_list.size();
		int map_array[]=new int[count];
		int num=0;
		int new_graph[][]=new int[count][];
		for(int i=0;i<count;++i) {
			int node=(Integer) temp_list.get(i);
			map_array[i]=node;
			map_s[node]=num;
			
				int temp_count=0;
				for(int j=0;j<graph[node].length;++j) {
					if(array[graph[node][j]]!=0&&graph[node][j]!=node) {
						temp_count++;
					}
				}
				new_graph[num]=new int[temp_count];
				temp_count=0;
				for(int j=0;j<graph[node].length;++j) {
					if(array[graph[node][j]]!=0&&graph[node][j]!=node) {
						new_graph[num][temp_count]=graph[node][j];
						temp_count++;
					}
				}
				num++;
		}
		for(int i=0;i<count;++i) {
			int update=(Integer) temp_list.get(i);
			array[update]=0;
		}
		

		
		for(int i=0;i<count;++i) {
			for(int j=0;j<new_graph[i].length;++j) {
				new_graph[i][j]=map_s[new_graph[i][j]];
			}
		}
		int map_d[]=new int[new_graph.length];
		Arrays.fill(map_d, 0);
		for(int i=0;i<new_graph.length;++i)
			if(index[map_array[i]]==1){
				map_d[i]=1;
			}
		KList f=new KList(new_graph,motif_size);
		f.Listbash(map_d);
		//f.ListFast();
		long[] t_a=f.getMotifDegree();
		
		
		Map<Integer, Long> result=new HashMap<Integer,Long>();
		for(int i=0;i<t_a.length;++i) {
			//System.out.println(t_a[i])t_a[i]>0&&;
			if(t_a[i]>0)
				result.put(map_array[i], t_a[i]);
			//if(map_d[i]==1)
			//System.out.println(t_a[i]+" "+new_graph[i].length+" "+new_graph.length+" "+t_a.length+" "+map_array[i]+" "+i);
		}
		
		return result;
	}
	
	
	
	public static void main(String[] args) {
		
		int mmm[]=Combination.combination(3, 400);
		DataReader a=new DataReader("./datasets/com-amazon.txt","./motif/5clique.txt");
		int graph[][]=a.readGraph();
		int Motif[][]=a.readMotif();
		int index=2;
		int combination[][]=new int[3][];
		int max_degree=0;
		for(int k=0;k<a.graph_size;++k) {
			if(graph[k].length>max_degree)
				max_degree=graph[k].length;
		}
		
		combination[0]=Combination.combination(index, max_degree);
		combination[1]=Combination.combination(index-1, max_degree);
		if(index-2==0) {
			combination[2]=new int[max_degree];
			Arrays.fill(combination[2], 1);
			//Arrays.fill(combination[2], 1);
		}else {
			combination[2]=Combination.combination(index-2, max_degree);
		}

		long s=System.currentTimeMillis();
		TDCDS b=new TDCDS(graph,a.graph_size,Motif,a.Motif_Count,2);
		b.TDAlg();
		long en=System.currentTimeMillis();
		//System.out.println(en-s);
	}
	
	
	
	class node implements Comparable<node>{
		
		public int degree;
		public int share;
		public node(int degree,int share) {
			// TODO Auto-generated constructor stub
			this.degree=degree;
			this.share=share;
		}

		public int compareTo(node o) {
			// TODO Auto-generated method stub
			if(this.degree<o.degree)
				return -1;
			else if(this.degree>o.degree)
				return 1;
			else
				return 0;
		}
		
	}
}
