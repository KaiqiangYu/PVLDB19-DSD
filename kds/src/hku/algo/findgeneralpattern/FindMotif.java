package hku.algo.findgeneralpattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author yukaiqiang
 * @date Nov, 20 2017
 * this algorithm is used to find all motifs from the given graph.
 */
public class FindMotif {
	
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
	
	//data structures this algorithm needs
	/** represent the deep of searching(from 0 to motif_size-1). */
	private int d=0;
	/** index in each layer. */
	private int[] H=null;
	/** mark whether the vertex in the given graph has been used before */
	private int [] F=null;
	/** matrix that we perform 'DFS' algorithm on it. */
	private int[][] M=null;
	/** save the records of motif, in order to union */
	public Map<String, int[]> Statistic=null;
	/***/
	private int[] Motif_degree=null;
	
	private int[] motif_degree;
	
	private int motif_clique=0;
	/**
	 * 
	 * @param Motif adjacency matrix of given motif.
	 * @param Graph adjacency matrix of given original graph.
	 */
	public FindMotif(int[][] Motif, int[][] Graph) {
		this.Motif=Motif;
		this.Graph=Graph;
	}
	
	/**
	 * 
	 * @param Motif
	 * @param Graph
	 * @param graph_size
	 */
	public FindMotif(int[][] Motif, int[][] Graph, int graph_size,int motif_type) {
		this.Motif=Motif;
		this.Graph=Graph;
		this.graph_size=graph_size;
		this.motif_type=motif_type;
	}
	
	public FindMotif(int[][] Motif, int[][] Graph, int graph_size,int motif_type,int motif_clique) {
		this.Motif=Motif;
		this.Graph=Graph;
		this.graph_size=graph_size;
		this.motif_type=motif_type;
		this.motif_clique=motif_clique;
	}
	
	/**
	 * Initialize the data structure we need.
	 * In order to initialize the array M[motif_size][graph_size], we need to 
	 * computer the degree of each vertex in the given motif and given graph.
	 * The value of the element in M[i][j] is either '1' or '0'.
	 * '1'-----(i,j) may be a possible matching. 
	 * '0'-----(i,j) can not become a matching.
	 * Here, i corresponds to the index of vertex in the motif and j corresponds to 
	 * the index of vertex in the given graph. 
	 */
	private void Initialize() {
		motif_size=Motif[0].length;
		Motif_degree=new int[graph_size];
		
		//computer the degree of each vertex
		motif_degree=new int[motif_size];
		int graph_degree[]=new int[graph_size];
		int i,j,count;
		
		for(i=0;i<motif_size;++i) {
			count=0;
			for(j=0;j<motif_size;++j) {
				if(Motif[i][j]>0)
					++count;
			}
			motif_degree[i]=count;
		}
		
		for(i=0;i<graph_size;++i) {		
			graph_degree[i]=Graph[i].length;
			//System.out.println(graph_degree[i]);
		}
		
		//initialize the data structure M[][];
		M=new int[motif_size][graph_size];
		
		for(i=0;i<motif_size;++i) {
			for(j=0;j<graph_size;++j) {			
				if(motif_degree[i]<=graph_degree[j])
					M[i][j]=1;
				else
					M[i][j]=0;			
			}
		}
		
		//initialize d, H[], F[]
		d=0;H=new int[motif_size];
		for(i=0;i<motif_size;++i)
			H[i]=-1;
		F=new int[graph_size];
		for(i=0;i<graph_size;++i)
			F[i]=0;
		
	}
	
	public int Match_V2(){
		Initialize();
		Statistic=new HashMap<String,int[]>();
		int v_size=graph_size;
		int motif[][]=Motif;
		
		

		int m=0,n=0;
		int temp_M[][]=new int [motif_size][v_size];
		int temp_M2[][]=new int [motif_size][v_size];
		int count=0;
		
		int max=0;
		
		d=0;H[0]=-1;
		for(int i=0;i<v_size;++i)
			F[i]=0;
		for(int i=0;i<motif_size;++i){
			for(int j=0;j<v_size;++j){
					temp_M[i][j]=M[i][j];
					//temp_M[i][j]=1;
					
			}
		}
		
		//test the temp_M
		/**
		System.out.println("*******test for the temp_M*******");
		for(int i=0;i<motif_size;++i){
			for(int j=0;j<v_size;++j){
				System.out.print(temp_M[i][j]);
			}
			System.out.println();
		}*/
		
		int status=2;
		
		while(true){
			
			//step2
			if(status==2){
				for(m=0;m<v_size;++m){
					if(temp_M[d][m]==1&&F[m]==0){
						break;
					}
				}
				
				if(m==v_size){
					//step7
					if(d==0){
						//terminate
						break;
					}
					F[n]=0;d=d-1;
					//Assignment(2,d);
					
				
					
					n=H[d];
					//go to step5
					status=5;
					//continue;
				}else{
					//continue the step2
					status=3;
					
					if(d==0){
						
						n=H[0];
						
					}else{
						n=-1;
					}
				}
			}
			
			
			
			//step3
			if(status==3){
				do{
					n=n+1;
				}while((temp_M[d][n]==0||F[n]==1));
				
				
				
				if(d==0){
					for(int i=0;i<motif_size;++i)
						for(int j=0;j<v_size;++j)
							temp_M[i][j]=M[i][j];
					
					for(int i=1;i<motif_size;++i){
						if(motif[0][i]==1){
							int array[]=new int[v_size];
							Arrays.fill(array, 0);
							
							for(int j=0;j<Graph[n].length;++j)
								array[Graph[n][j]]=M[i][Graph[n][j]];
							temp_M[i]=array;
						}
						
					}
					for(int i=0;i<motif_size;++i)
						for(int j=0;j<v_size;++j)
							temp_M2[i][j]=temp_M[i][j];
					
					
					
				}else {
					H[d]=n;
					if(d+1<motif.length)
						for(int i=0;i<v_size;++i)
							temp_M[d+1][i]=temp_M2[d+1][i];
					
					if(d+1<motif.length){
						int array[]=new int[v_size];
						Arrays.fill(array, 0);
						int my_count=0;
						for(int i=1;i<d+1;++i) {
							if(motif[i][d+1]==1) {
								my_count++;
								for(int j=0;j<Graph[H[i]].length;++j)
									array[Graph[H[i]][j]]++;
							}
						}
						for(int i=0;i<v_size;++i) {
							if(array[i]==my_count) {
								array[i]=temp_M2[d+1][i];
							}else {
								array[i]=0;
							}
						}
						
						temp_M[d+1]=array;
						
						
					}	
					
//					if(d+1<motif.length&&motif[d][d+1]==1){
//						int array[]=new int[v_size];
//						Arrays.fill(array, 0);						
//						for(int j=0;j<Graph[n].length;++j)
//							array[Graph[n][j]]=temp_M2[d+1][Graph[n][j]];
//						temp_M[d+1]=array;
//					}					
				}
				status=4;
				
			}
			
			//step4
			if(status==4){
				if(d<motif_size-1){
					//go to step6
					H[d]=n;
					F[n]=1;
					d=d+1;
					//go to step2
					status=2;
				}else{
					//output
					H[d]=n;
					//F[n]=1;
					
						
					 {
						int[] temp_H=new int[motif_size+1];
						for(int i=0;i<motif_size;++i)
							temp_H[i]=H[i];
						Arrays.sort(temp_H,0,motif_size);
						String recordKey="";
						for(int i=0;i<motif_size;++i) {
							recordKey+=(temp_H[i]+"*");
						}
						if(Statistic.containsKey(recordKey)) {
							status=5;
							continue;
						}
					}
					 
					int temp_count=0;
					if(motif_clique==0) {
						
						temp_count=CountMotif(H);
					}else {
						
						temp_count=CountMotifOne(H);
					}
//					for(int i=0;i<H.length;++i)
//						System.out.println(H[i]);
//					System.out.println(temp_count+" "+count);
//					System.out.println("***");
					if(temp_count!=0) {
						//temp_count=1;
						count+=temp_count;
						//Assignment(2,d);
						int[] temp_H=new int[motif_size+1];
						for(int i=0;i<motif_size;++i)
							temp_H[i]=H[i];
						Arrays.sort(temp_H,0,motif_size);
						temp_H[motif_size]=temp_count;
						String recordKey="";
						for(int i=0;i<motif_size;++i) {
							recordKey+=(temp_H[i]+"*");
							Motif_degree[H[i]]+=(temp_count);
						}				
						Statistic.put(recordKey, temp_H);
					}
					
				
					status=5;
				}
			}
			
			
			//step5
			if(status==5){
				for(m=n+1;m<v_size;++m){
					if(temp_M[d][m]==1&&F[m]==0){
						break;
					}
				}
				if(m>=v_size){
					//step7
					if(d==0){
						//terminate
						break;
					}
					d=d-1;
					//Assignment(2,d);
					n=H[d];
					
					
					Arrays.fill(F, 0);
					/**
					 * debug 4/16/2018
					 * remark
					 */
					for(int i=0;i<d;++i)
						F[H[i]]=1;
					/*****************/
					
					
					//F[n]=1;
					//go to step5
					status=5;
				}else{
					//Assignment(2,d);
					//step3
					status=3;
				}
			}
			
		}
		//System.out.println(count);
		return count;
	}
	
	
	private int CountMotifOne(int[] H) {
		int i,j,tempx,tempy;boolean flag=false;
		for(i=0;i<motif_size;++i) {
			for(j=0;j<motif_size;++j) {
				tempx=H[i];tempy=H[j];
				flag=false;
				if(Motif[i][j]==1) {
					for(int k=0;k<Graph[tempx].length;++k) {
						if(tempy==Graph[tempx][k]) {
							flag=true;
							break;
						}
					}
				}else {
					flag=true;
				}
				if(!flag) {
					break;
				}	
				
			}
			if(!flag) {
				break;
			}
		}
		if(flag)
			return 1;
		else
			return 0;
	}

	private int CountMotif(int[] H) {
		int count = 0;
		int F[][] = new int[motif_size][motif_size];
		for (int i = 0; i < motif_size; ++i) {
			for (int j = 0; j < motif_size; ++j) {
				if (motif_degree[i] <= Graph[H[j]].length)
					F[i][j] = 1;
				else
					F[i][j] = 0;
			}
		}
		
		int m, n, d = 0;

		int M[] = new int[motif_size];
		int D[] = new int[motif_size];
		Arrays.fill(D, -1);
		Arrays.fill(M, 0);
		while (D[0] < motif_size) {
			//System.out.println(d);
			n = D[d];
			
			for (m = n + 1; m < motif_size; ++m) {
				if (F[d][m] == 1 && M[m] == 0)
					break;
			}
			if (m == motif_size) {
				if (d == 0)
					break;
				D[d] = -1;
				if (n >= 0)
					M[n] = 0;
				d = d - 1;
			} else {
				D[d] = m;
				M[m] = 1;
				if (n >= 0)
					M[n] = 0;
				if (d < motif_size)
					d = d + 1;
			}
			if (d == motif_size) {
					
				boolean flag = false;
				for (int i = 0; i < motif_size; ++i) {
					for (int j = 0; j < motif_size; ++j) {
						flag = false;
						if (Motif[i][j] == 1) {
							int tempx = H[D[i]];
							int tempy = H[D[j]];
							for (int k = 0; k < Graph[tempx].length; ++k) {
								if (Graph[tempx][k] == tempy) {
									flag = true;
									break;
								}
							}
						} else {
							flag = true;
						}
						if (!flag)
							break;
					}
					if (!flag)
						break;
				}
				if (flag) {
					count++;
				}
				d--;
			}
		}
		//System.out.println(count);

		return count/motif_type;
	}
	
	
	/**
	 * This method is used to find all motif-matchings. 
	 * @return the number of matchings
	 */
	public int Match() {
		Initialize();
		Statistic=new HashMap<String,int[]>();
		int n,m;
		int i,j,tempx,tempy,count=0;
		boolean flag=true;
		while(H[0]<graph_size) {
			
			n=H[d];
			for(m=n+1;m<graph_size;++m) {
				if(M[d][m]==1&&F[m]==0)
					break;
			}
			
			if(m==graph_size) {
				if(d==0) {
					break;
				}
				H[d]=-1;
				if(n>=0)
					F[n]=0;
				d=d-1;
				
			}else {
				H[d]=m;F[m]=1;
				if(n>=0)
					F[n]=0;
				if(d<motif_size)
					d=d+1;
			}
			
			//debug Nov,27,2017
//			if(d==motif_size) {
//				flag=true;
//				for(i=0;i<motif_size;++i) {
//					for(j=0;j<motif_size;++j) {
//						tempx=H[i];tempy=H[j];
//						if(Motif[i][j]==1) 
//							flag=false;
//						else
//							flag=true;
//						for(int k=0;k<Graph[tempx].length;++k) {
//							if(tempy==Graph[tempx][k]) {
//								flag=!flag;
//								break;
//							}
//						}
//						if(!flag) {
//							break;
//						}	
//						
//					}
//					if(!flag) {
//						break;
//					}
//				}
			if(d==motif_size) {
				
				for(i=0;i<motif_size;++i) {
					for(j=0;j<motif_size;++j) {
						tempx=H[i];tempy=H[j];
						flag=false;
						if(Motif[i][j]==1) {
							for(int k=0;k<Graph[tempx].length;++k) {
								if(tempy==Graph[tempx][k]) {
									flag=true;
									break;
								}
							}
						}else {
							flag=true;
						}
						if(!flag) {
							break;
						}	
						
					}
					if(!flag) {
						break;
					}
				}
				
				if(flag) {
					++count;
					int[] temp_H=new int[motif_size+1];
					for(i=0;i<motif_size;++i)
						temp_H[i]=H[i];
					Arrays.sort(temp_H,0,motif_size);
					temp_H[motif_size]=1;
					String recordKey="";
					for(i=0;i<motif_size;++i) {
						recordKey+=(temp_H[i]+"*");
						Motif_degree[H[i]]++;
					}
					//System.out.println(recordKey);
					if(Statistic.containsKey(recordKey)) {
						temp_H=(int[]) Statistic.get(recordKey);
						temp_H[motif_size]++;
					}else {
						Statistic.put(recordKey, temp_H);
					}
//					for(i=0;i<motif_size;++i)
//						System.out.print(H[i]+" ");
//					System.out.println();
					
				}
				
				d=d-1;
			}
			
		}
		//System.out.println(count);
//		if(count%motif_type>0)
//			System.out.println("erro:"+count+" "+motif_type);
		count=count/motif_type;
		for(Entry<String, int[]> entry : Statistic.entrySet()) {
			entry.getValue()[motif_size]=entry.getValue()[motif_size]/motif_type;
		}
		for(int k=0;k<graph_size;++k)
			Motif_degree[k]=Motif_degree[k]/motif_type;
		
		return count;
		
	}
	
	/**
	 * 
	 * @return all the records of matchings.
	 */
	public Map<String, int[]> getStatistic() {
		return Statistic;
	}
	
	public int[] getMotif_degree() {
		return Motif_degree;
	}
	
	public int Match_V3(){
		Initialize();
		Statistic=new HashMap<String,int[]>();
		int v_size=graph_size;
		int motif[][]=Motif;
		
		

		int m=0,n=0;
		int temp_M[][]=new int [motif_size][v_size];
		int temp_M2[][]=new int [motif_size][v_size];
		int count=0;
		
		int max=0;
		
		d=0;H[0]=-1;
		for(int i=0;i<v_size;++i)
			F[i]=0;
		for(int i=0;i<motif_size;++i){
			for(int j=0;j<v_size;++j){
					temp_M[i][j]=M[i][j];
					//temp_M[i][j]=1;
					
			}
		}
		
		//test the temp_M
		/**
		System.out.println("*******test for the temp_M*******");
		for(int i=0;i<motif_size;++i){
			for(int j=0;j<v_size;++j){
				System.out.print(temp_M[i][j]);
			}
			System.out.println();
		}*/
		
		int status=2;
		
		while(true){
			
			//step2
			if(status==2){
				for(m=0;m<v_size;++m){
					if(temp_M[d][m]==1&&F[m]==0){
						break;
					}
				}
				
				if(m==v_size){
					//step7
					if(d==0){
						//terminate
						break;
					}
					F[n]=0;d=d-1;
					//Assignment(2,d);
					
				
					
					n=H[d];
					//go to step5
					status=5;
					//continue;
				}else{
					//continue the step2
					status=3;
					
					if(d==0){
						
						n=H[0];
						
					}else{
						n=-1;
					}
				}
			}
			
			
			
			//step3
			if(status==3){
				do{
					n=n+1;
				}while(n<v_size&&(temp_M[d][n]==0||F[n]==1));
				
				if(d==0&&n==v_size)
					break;
				if(d>0&&n==v_size) {
					status=5;
					continue;
				}
				
				if(d==0){
					for(int i=0;i<motif_size;++i)
						for(int j=0;j<v_size;++j)
							temp_M[i][j]=M[i][j];
					
					for(int i=1;i<motif_size;++i){
						if(motif[0][i]==1){
							int array[]=new int[v_size];
							Arrays.fill(array, 0);
							
							for(int j=0;j<Graph[n].length;++j)
								array[Graph[n][j]]=M[i][Graph[n][j]];
							temp_M[i]=array;
						}
						
					}
					for(int i=0;i<motif_size;++i)
						for(int j=0;j<v_size;++j)
							temp_M2[i][j]=temp_M[i][j];
					
					if(n!=0) {
						int i=1;
						for(;i<motif.length;++i) {
							if(temp_M[i][0]==1) {
								break;
							}
						}
						if(i==motif.length) {
							status=3;
							continue;
						}
					}
					
				}else {
					H[d]=n;
					if(d+1<motif.length)
						for(int i=0;i<v_size;++i)
							temp_M[d+1][i]=temp_M2[d+1][i];
					
					if(d+1<motif.length){
						int array[]=new int[v_size];
						Arrays.fill(array, 0);
						int my_count=0;
						for(int i=1;i<d+1;++i) {
							if(motif[i][d+1]==1) {
								my_count++;
								for(int j=0;j<Graph[H[i]].length;++j)
									array[Graph[H[i]][j]]++;
							}
						}
						for(int i=0;i<v_size;++i) {
							if(array[i]==my_count) {
								array[i]=temp_M2[d+1][i];
							}else {
								array[i]=0;
							}
						}
						
						temp_M[d+1]=array;
						
						if(F[0]==0&&n!=0) {
							int i=d+1;
							for(;i<motif.length;++i) {
								if(temp_M[i][0]==1) {
									break;
								}
							}
							if(i==motif.length) {
								status=3;
								continue;
							}
						}
					}	
					
//					if(d+1<motif.length&&motif[d][d+1]==1){
//						int array[]=new int[v_size];
//						Arrays.fill(array, 0);						
//						for(int j=0;j<Graph[n].length;++j)
//							array[Graph[n][j]]=temp_M2[d+1][Graph[n][j]];
//						temp_M[d+1]=array;
//					}					
				}
				status=4;
				
			}
			
			//step4
			if(status==4){
				if(d<motif_size-1){
					//go to step6
					H[d]=n;
					F[n]=1;
					d=d+1;
					//go to step2
					status=2;
				}else{
					//output
					H[d]=n;
					//F[n]=1;
					if(F[0]==0&&n!=0) {
						status=5;
						continue;
					}else {
						int[] temp_H=new int[motif_size+1];
						for(int i=0;i<motif_size;++i)
							temp_H[i]=H[i];
						Arrays.sort(temp_H,0,motif_size);
						String recordKey="";
						for(int i=0;i<motif_size;++i) {
							recordKey+=(temp_H[i]+"*");
						}
						if(Statistic.containsKey(recordKey)) {
							status=5;
							continue;
						}
					}
					int temp_count=0;
					if(motif_clique==0) {
						
						temp_count=CountMotif(H);
					}else {
						
						temp_count=CountMotifOne(H);
					}
//					for(int i=0;i<H.length;++i)
//						System.out.println(H[i]);
//					System.out.println(temp_count+" "+count);
//					System.out.println("***");
					if(temp_count!=0) {
						//temp_count=1;
						count+=temp_count;
						//Assignment(2,d);
						int[] temp_H=new int[motif_size+1];
						for(int i=0;i<motif_size;++i)
							temp_H[i]=H[i];
						Arrays.sort(temp_H,0,motif_size);
						temp_H[motif_size]=temp_count;
						String recordKey="";
						for(int i=0;i<motif_size;++i) {
							recordKey+=(temp_H[i]+"*");
							Motif_degree[H[i]]+=(temp_count);
						}				
						Statistic.put(recordKey, temp_H);
					}
					
				
					status=5;
				}
			}
			
			
			//step5
			if(status==5){
				for(m=n+1;m<v_size;++m){
					if(temp_M[d][m]==1&&F[m]==0){
						break;
					}
				}
				if(m>=v_size){
					//step7
					if(d==0){
						//terminate
						break;
					}
					d=d-1;
					//Assignment(2,d);
					n=H[d];
					
					
					Arrays.fill(F, 0);
					/**
					 * debug 4/16/2018
					 * remark
					 */
					for(int i=0;i<d;++i)
						F[H[i]]=1;
					/*****************/
					
					
					//F[n]=1;
					//go to step5
					status=5;
				}else{
					//Assignment(2,d);
					//step3
					status=3;
				}
			}
			
		}
		//System.out.println(count);
		return count;
	}
	
	
	public int Match_V4(){
		Initialize();
		Statistic=new HashMap<String,int[]>();
		int v_size=graph_size;
		//int arr[][]=new int[v_size][v_size+1];
		int motif[][]=Motif;
		
//		int H[]=new int[motif_size];
//		int F[]=new int[v_size];
		

		int m=0,n=0;
		int temp_M[][]=new int [motif_size][v_size];
		int count=0;
		
		int max=0;
		
		d=0;H[0]=-1;
		for(int i=0;i<v_size;++i)
			F[i]=0;
		for(int i=0;i<motif_size;++i){
			for(int j=0;j<v_size;++j){
					temp_M[i][j]=M[i][j];
					
			}
		}
		
		//test the temp_M
		/**
		System.out.println("*******test for the temp_M*******");
		for(int i=0;i<motif_size;++i){
			for(int j=0;j<v_size;++j){
				System.out.print(temp_M[i][j]);
			}
			System.out.println();
		}*/
		
		int status=2;
		
		while(true){
			
			//step2
			if(status==2){
				for(m=0;m<v_size;++m){
					if(temp_M[d][m]==1&&F[m]==0){
						break;
					}
				}
				
				if(m==v_size){
					//step7
					if(d==0){
						//terminate
						break;
					}
					F[n]=0;d=d-1;
					//Assignment(2,d);
					n=H[d];
					//go to step5
					status=5;
					//continue;
				}else{
					//continue the step2
					status=3;
					
					if(d==0){
						
						n=H[0];
						
					}else{
						n=-1;
					}
				}
			}
			
			
			
			//step3
			if(status==3){
				do{
					n=n+1;
				}while(temp_M[d][n]==0||F[n]==1);
				if(d==0){
					for(int i=0;i<motif_size;++i)
						for(int j=0;j<v_size;++j)
							temp_M[i][j]=M[i][j];
					
					for(int i=1;i<motif_size;++i){
						if(motif[0][i]==1){
							int array[]=new int[v_size];
							Arrays.fill(array, 0);
							for(int j=0;j<Graph[n].length;++j)
								array[Graph[n][j]]=1;
							for(int j=0;j<v_size;++j){
								if(array[j]==0)
									temp_M[i][j]=0;
							}
						}
						
					}
				}
				status=4;
			}
			
			//step4
			if(status==4){
				if(d<motif_size-1){
					//go to step6
					H[d]=n;
					F[n]=1;
					d=d+1;
					//go to step2
					status=2;
				}else{
					//output
					H[d]=n;
					//F[n]=1;
					int temp_count=0;
					if(motif_clique==0) {
						if(H[0]!=0)
							break;
						temp_count=CountMotif(H);
					}else {
						if(H[0]!=0)
							break;
						temp_count=CountMotifOne(H);
					}
					if(temp_count!=0) {
						count+=temp_count;
						//Assignment(2,d);
						
					}
					
				
					status=5;
				}
			}
			
			
			//step5
			if(status==5){
				for(m=n+1;m<v_size;++m){
					if(temp_M[d][m]==1&&F[m]==0){
						break;
					}
				}
				if(m==v_size){
					//step7
					if(d==0){
						//terminate
						break;
					}
					d=d-1;
					//Assignment(2,d);
					n=H[d];
					for(int i=n+1;i<v_size;++i){
						F[i]=0;
					}
					//F[n]=1;
					//go to step5
					status=5;
				}else{
					//Assignment(2,d);
					//step3
					status=3;
				}
			}
			
		}
		//System.out.println(count);
		return count;
	}
	

}
