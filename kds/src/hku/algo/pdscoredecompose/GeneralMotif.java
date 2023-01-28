package hku.algo.pdscoredecompose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import hku.algo.findgeneralpattern.FindMotif;
import hku.algo.pdsenumeration.EnumBasket;
import hku.algo.pdsenumeration.EnumCstar;
import hku.algo.pdsenumeration.EnumThTri;
import hku.algo.pdsenumeration.EnumTwoTri;
import hku.util.Log;

/**
 * 
 * @author yukaiqiang
 *
 */
public class GeneralMotif {

	/** adjacent list of the given graph */
	private int[][] Graph = null;
	/** adjacent matrix of the given motif */
	private int[][] Motif = null;
	/** the number of vertex in the given graph */
	private int graph_size;
	/** the number of vertex in the given motif */
	private int motif_size;
	/** the degree for each vertex in the motif */
	private int[] degree;
	/** data structure to record the information of sharing motifs */
	private Map<Integer, Integer> Share[] = null;
	private int motif_type=1;
	
	private Map<String,int[]> motif_list=null;
	
	private int[] motif_degree;
	private int motif_d;

	/**
	 * 
	 * @param Graph
	 *            adjacent list of the given graph
	 * @param Motif
	 *            adjacent matrix of the given motif
	 * @param graph_size
	 *            the number of vertex in the given graph
	 * @param motif_size
	 *            the number of vertex in the given motif
	 */
	public GeneralMotif(int[][] Graph, int[][] Motif, int graph_size, int motif_size,int motif_type,
			Map<String,int[]> motif_list,int[] motif_degree) {
		this.motif_list=motif_list;
		this.Graph = Graph;
		this.Motif = Motif;
		this.graph_size = graph_size;
		this.motif_size = motif_size;
		this.motif_type=motif_type;
		this.motif_degree=motif_degree;
		this.motif_d=motif_size;
		degree = new int[motif_size];
		for (int i = 0; i < motif_size; ++i) {
			int count = 0;
			for (int j = 0; j < motif_size; ++j) {
				if (Motif[i][j] > 0)
					count++;
			}
			degree[i] = count;
		}

		Share = new Map[graph_size];
		for (int i = 0; i < graph_size; ++i) {
			Share[i] = new HashMap<Integer, Integer>();
		}

	}
	
	public GeneralMotif(int[][] Graph, int[][] Motif, int graph_size, int motif_size,int motif_type,
			Map<String,int[]> motif_list,int[] motif_degree,int m) {
		this.motif_list=motif_list;
		this.Graph = Graph;
		this.Motif = Motif;
		this.graph_size = graph_size;
		this.motif_size = motif_size;
		this.motif_type=motif_type;
		this.motif_degree=motif_degree;
		this.motif_d=m;
		degree = new int[motif_size];
		for (int i = 0; i < motif_size; ++i) {
			int count = 0;
			for (int j = 0; j < motif_size; ++j) {
				if (Motif[i][j] > 0)
					count++;
			}
			degree[i] = count;
		}

		Share = new Map[graph_size];
		for (int i = 0; i < graph_size; ++i) {
			Share[i] = new HashMap<Integer, Integer>();
		}

	}
	
	/**
	 * 
	 * @param i
	 * @param mark
	 * @return
	 */
	private Map<Integer,Integer> Update(int i,int mark[]) {
		
		Map<Integer,Integer> result=new HashMap<Integer,Integer>();
		int temp[];int j=0;boolean flag=false;
		for(Entry<String,int[]> entry:motif_list.entrySet()) {
			temp=entry.getValue();
			flag=false;
			for(j=0;j<temp.length-1;++j) {
				if(temp[j]==i)
					flag=true;
				if(mark[temp[j]]!=0)
					break;
			}
			if(flag&&j==temp.length-1) {
				
				for(j=0;j<temp.length-1;++j) {
					if(temp[j]!=i) {
						if (result.containsKey(temp[j])) {
							Integer temp_temp = (Integer) result.get(temp[j]);
//							result.replace(temp[j], (Integer)temp_temp, (Integer)(temp[temp.length-1] + temp_temp));
							result.remove(temp[j]);
							result.put(temp[j], (Integer)(temp[temp.length-1] + temp_temp) );
						} else {
							result.put(temp[j], temp[temp.length-1] );
						}
					}
				}
				
			}
		}
		
//		Map<String,int[]> conbination=SearchConbination(i, mark);
//		
//		for(Entry<String,int[]> entry:conbination.entrySet()) {
//			int temp=CountMotif(entry.getValue());
//			int array[]=entry.getValue();
//			for(int j=0;j<array.length;++j) {
//				if(array[j]!=i) {
//					if (result.containsKey(array[j])) {
//						Integer temp_temp = (Integer) result.get(array[j]);
//						result.replace(array[j], temp_temp, temp + temp_temp);
//					} else {
//						result.put(array[j], temp);
//					}
//				}
//			}
//		}
		
		return result;

	}
	
	
//	private Map<Integer,Integer> Update(int i,int mark[]) {
//		int H[] = new int[motif_size];
//		ArrayList record[] = new ArrayList[motif_size];
//		int D[] = new int[graph_size];
//		int F[] = new int[motif_size];
//		Arrays.fill(F, 0);
//
//		int motif_num = 0;
//		for (int j = 0; j < motif_size; ++j)
//			record[j] = new ArrayList<Integer>();
//		Arrays.fill(D, 0);
//		int deep = 0;
//		H[0] = i;
//		
//		Map<Integer,Integer> result=new HashMap<Integer,Integer>();
//		
//		while (deep >= 0) {
//			if (deep == motif_size - 1) {
//				deep--;
//				int temp = CountMotif(H);
//				if(temp>0) {
//				
//					for (int m = 1; m < motif_size; ++m) {
//						if (result.containsKey(H[m])) {
//							Integer temp_temp = (Integer) result.get(H[m]);
//							result.replace(H[m], temp_temp, temp + temp_temp);
//						} else {
//							result.put(H[m], temp);
//						}
//					}
//				}
//				
//				
//				continue;
//			}
//			int index = H[deep];
//			// mark that we have visited this vertex
//			record[deep].add(index);
//			D[index] = 1;
//			// find a feasible vertex
//			int j = F[deep];
//			for (; j < Graph[index].length; ++j) {
//				if (D[Graph[index][j]] == 0 && mark[Graph[index][j]]==0)
//					break;
//			}
//			
//			if (j < Graph[index].length) {
//				F[deep] = j;
//				H[deep + 1] = Graph[index][j];
//				D[Graph[index][j]] = 1;
//				record[deep + 1].add(Graph[index][j]);
//				deep++;
//			} else {
//				// for(int m=deep+1;m<motif_size;++m)
//				// F[m]=0;
//				F[deep + 1] = 0;
//				F[deep]=0;
//				for (int m = 0; m < record[deep + 1].size(); ++m) {
//					int temp = (int) record[deep + 1].get(m);
//					D[temp] = 0;
//				}
//				record[deep + 1].clear();
//				deep--;
//
//			}
//
//		}
//		return result;
//
//	}
	
	/**
	 * 
	 * @param index
	 * @param mark
	 * @return
	 */
//	public Map<String,int[]> SearchConbination(int index,int mark[]){
//		Map<String,int[]> Conbination=new HashMap<String,int[]>();
//		Queue<Integer> queue[]=new LinkedList[motif_size];
//		for(int i=0;i<motif_size;++i)
//			queue[i]=new LinkedList<Integer>();
//		queue[0].add(index);
//		int deep=0,temp;
//		int H[]=new int[motif_size];
//		int M[]=new int[graph_size];
//		Arrays.fill(M, 0);
//		Arrays.fill(H, -1);
//		while(true) {
//			if(H[deep]!=-1&&deep!=motif_size-1) {
//				M[H[deep]]=0;
//				//System.out.println("in "+deep+" free "+H[deep]);
//			}
//			if(queue[deep].isEmpty()) {
//				//M[H[deep]]=0;
//				H[deep]=-1;
//				deep--;
//				if(deep==0)
//					break;
//				continue;
//			}
//			temp=queue[deep].poll();
//			H[deep]=temp;
//			M[temp]=1;
//			//System.out.println("in "+deep+" look "+H[deep]);
//			for(int i=0;i<Graph[temp].length;++i) {
//				if((mark==null||mark[Graph[temp][i]]==0)&&M[Graph[temp][i]]==0)
//					queue[deep+1].add(Graph[temp][i]);
//			}
//			deep++;
//			if(deep==motif_size-1) {
//				while(!queue[deep].isEmpty()) {
//					H[deep]=queue[deep].poll();					
//					int sort[]=new int[motif_size];
//					for(int i=0;i<motif_size;++i)
//						sort[i]=H[i];
//					Arrays.sort(sort);
//					String s="";
//					for(int i=0;i<motif_size;++i)
//						s+=sort[i]+"*";
//					if(!Conbination.containsKey(s)) {
//						Conbination.put(s, sort);
//					}
//					//System.out.println(s);
//				}
//			}
//			
//		}
//		return Conbination;
//	}

	/**
	 * Compute the vertex's motif-degree
	 * 
	 * @return the number of motifs for each vertex in the given graph
	 */
//	public int[] ComputeMD() {
//		// initialize the array
//		int motif_degree[] = new int[graph_size];
//		Arrays.fill(motif_degree, 0);
//
//		for(int i=0;i<graph_size;++i) {
//			Map<String,int[]> conbination=SearchConbination(i, null);
//			int num=0;
//			for(Entry<String,int[]> entry:conbination.entrySet()) {
//				num+=CountMotif(entry.getValue());
//				//if(i==0)
//					//System.out.println(entry.getKey());
//			}
//			motif_degree[i]=num;
//			
//		}
//		return motif_degree;
//	}
	

	/**
	 * Compute the number of motifs for the given combination
	 * 
	 * @param H
	 *            the combination of the vertex
	 * @return the number of motif for this combination
	 */
	private int CountMotif(int[] H) {
		int count = 0;
		int F[][] = new int[motif_size][motif_size];
		for (int i = 0; i < motif_size; ++i) {
			for (int j = 0; j < motif_size; ++j) {
				if (degree[i] <= Graph[H[j]].length)
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
	 * Construct the adjacent list to record the information of the sharing motifs.
	 */
	private void Construct(int H[], int num) {
		for (int i = 1; i < motif_size; ++i) {
			if (Share[H[0]].containsKey(H[i])) {
				Integer temp = (Integer) Share[H[0]].get(H[i]);
//				Share[H[0]].replace(H[i], temp, temp + num);
				Share[H[0]].remove(H[i]);
				Share[H[0]].put(H[i], (Integer)(temp + num) );
			} else {
				Share[H[0]].put(H[i], num);
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public double[][] Decompose() {
		
//		for(int i=0;i<graph_size;++i) {
//			System.out.println(motif_degree[i]);
//		}
		//Log.write("33333");
		int max = 0;
		int motif_num = 0;
		int count = 0;
		int mark[]=new int[graph_size];
		Arrays.fill(mark, 0);
		Map<Integer,Integer> map;
		
		int array_index[]=new int[graph_size];
		Arrays.fill(array_index, 0);
		int array_degree[]=new int[graph_size];
		Log.write("2222\n");
		int new_array[]=new int[graph_size];
		Arrays.fill(new_array, 0);
		int new_map[]=new int[graph_size];
		Arrays.fill(new_map, 0);
		for(int mm=0;mm<graph_size;mm++) {
			array_degree[mm]=com(mm,array_index,new_array,new_map);
			//Generate(mm,array_index,new_array,new_map);
		}
		Log.write("11111\n");
		motif_degree=array_degree;

		
		
		for (int i = 0; i < graph_size; ++i) {
			if (max < motif_degree[i])
				max = motif_degree[i];
			motif_num += motif_degree[i];
		}
		max=max+1;
		motif_num = motif_num / motif_size;
		System.out.println(motif_num);

		ArrayList node_list[] = new ArrayList[max];
		for (int i = 0; i < max; ++i)
			node_list[i] = new ArrayList<Integer>();
		for (int i = 0; i < graph_size; ++i)
			node_list[motif_degree[i]].add(i);

		// data structure used to save the result
		double[][] result = new double[graph_size+1][5];
		result[0][2] = motif_num / (double) graph_size;
		result[0][3] =motif_num;
		//System.out.println(111);
	//	int index_min = 0, update_min = Integer.MAX_VALUE;
		
		
		/****
		 * debug 
		 */
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
		
		int pos[]=new int[graph_size+1];
		int vert[]=new int[graph_size+1];
		for(int v=1;v<graph_size+1;++v) {
			pos[v]=bin[motif_degree[v-1]];
			vert[pos[v]]=v;
			bin[motif_degree[v-1]]+=1;
		}
		
		for(int d=max;d>=1;d--) {
			bin[d]=bin[d-1];
		}
		bin[0]=1;
		int core_number=0;
		for (int i = 1; i < graph_size+1; ++i) {
			
			
			int index=vert[i]-1;
			

			// get the vertex with minimum motif-degree
//			if (update_min < index_min) {
//				index_min = update_min;
//			} else {
//				for (; index_min < max; ++index_min) {
//					if (!node_list[index_min].isEmpty())
//						break;
//				}
//			}
//			update_min = Integer.MAX_VALUE;
//			int index = (Integer) node_list[index_min].remove(0);

			// record the result
			count++;
			result[count][0] = index;
			result[count][1] = motif_degree[index];
			motif_num -= motif_degree[index];
			if(motif_num<0) motif_num=0;
			result[count][3]=motif_num;
//			if(core_number>motif_degree[index]){
//				System.out.println((graph_size-count)+"  sefe");
//				return result;
//			}
//			if(core_number<motif_degree[index])
//				core_number=motif_degree[index];
				
//			if(core_number>motif_degree[index]) {
//				result[count][4]=core_number;
//			}else {
//				core_number=motif_degree
//			}
			if(core_number>motif_degree[index])
				return result;
			core_number=motif_degree[index];
			if (graph_size - count > 0) {
				result[count][2] = motif_num / (double) (graph_size - count);
			} else {
				result[count][2] = 0;
			}

			// update
			if(motif_degree[index]>0) {
				//map=Update(index,mark);
//				System.out.println("****0"+" "+index);
//				for(Entry<Integer, Integer> entry : map.entrySet()) {
//					System.out.println(entry.getKey()+" "+entry.getValue());
//				}
//				System.out.println("*****1"+" "+mark[37]);
				//System.out.println(111);
				map=Generate(index,mark,new_array,new_map);
				//System.out.println(222);
//				for(Entry<Integer, Integer> entry : map.entrySet()) {
//					System.out.println(entry.getKey()+" "+entry.getValue());
//				}
//				System.out.println("*****2");
				if(!map.isEmpty())
				for(Entry<Integer, Integer> entry : map.entrySet()) {
					int temp_key=entry.getKey();
					int temp_value=entry.getValue();
					//node_list[motif_degree[temp_key]].remove((Integer)temp_key);			
					if(motif_degree[temp_key]>motif_degree[index]) {
						int du=motif_degree[temp_key];
						int pu=pos[temp_key+1];
						int pw=bin[du];
						int w=vert[pw];
						if((temp_key+1)!=w) {
							pos[temp_key+1]=pw;
							vert[pu]=w;
							pos[w]=pu;
							vert[pw]=temp_key+1;							
						}
						bin[du]+=1;
						motif_degree[temp_key]-=temp_value;
					}
					
//					if(update_min>motif_degree[temp_key]) {
//						update_min=motif_degree[temp_key];
//					}
//					node_list[motif_degree[temp_key]].add(temp_key);
				}
			}
			motif_degree[index]=0;
			//node_list[index_min].remove((Integer)index);
			mark[index]=1;
			
			

		}
		return result;

	}
	
public double[][] DecomposeInc() {
		
//		for(int i=0;i<graph_size;++i) {
//			System.out.println(motif_degree[i]);
//		}
		//Log.write("33333");
		int max = 0;
		long motif_num = 0;
		int count = 0;
		int mark[]=new int[graph_size];
		Arrays.fill(mark, 0);
		Map<Integer,Integer> map;
		
		int array_index[]=new int[graph_size];
		Arrays.fill(array_index, 0);
		int array_degree[]=new int[graph_size];
		Log.write("2222\n");
		int new_array[]=new int[graph_size];
		Arrays.fill(new_array, 0);
		int new_map[]=new int[graph_size];
		Arrays.fill(new_map, 0);
		//for(int mm=0;mm<graph_size;mm++) {
			//array_degree[mm]=com(mm,array_index,new_array,new_map);
			//Generate(mm,array_index,new_array,new_map);
		//}
		Log.write("11111\n");
		array_degree=motif_degree;

		
		
		for (int i = 0; i < graph_size; ++i) {
			if (max < motif_degree[i])
				max = motif_degree[i];
			motif_num += motif_degree[i];
		}
		max=max+1;
		motif_num = motif_num / motif_size;
		System.out.println(motif_num);

		//ArrayList node_list[] = new ArrayList[max];
		//for (int i = 0; i < max; ++i)
			//node_list[i] = new ArrayList<Integer>();
		//for (int i = 0; i < graph_size; ++i)
			//node_list[motif_degree[i]].add(i);

		// data structure used to save the result
		double[][] result = new double[graph_size+1][5];
		result[0][2] = motif_num / (double) graph_size;
		result[0][3] =motif_num;
		System.out.println(111);
	//	int index_min = 0, update_min = Integer.MAX_VALUE;
		
		
		/****
		 * debug 
		 */
		long bin[]=new long[max+1];
		Arrays.fill(bin, 0);
		for(int i=1;i<graph_size+1;++i) {
			bin[motif_degree[i-1]]+=1;
		}
		
		int start=1;
		for(int d=0;d<=max;++d) {
			long num=bin[d];
			bin[d]=start;
			start+=num;
		}
		
		long pos[]=new long[graph_size+1];
		long vert[]=new long[graph_size+1];
		for(int v=1;v<graph_size+1;++v) {
			pos[v]=bin[motif_degree[v-1]];
			vert[(int)pos[v]]=v;
			bin[motif_degree[v-1]]+=1;
		}
		
		for(int d=max;d>=1;d--) {
			bin[d]=bin[d-1];
		}
		bin[0]=1;
		int core_number=0;
		for (int i = 1; i < graph_size+1; ++i) {
			
//			if(i%1000==0)
				//System.out.println("&&&&:"+i);
			int index=(int) (vert[i]-1);
			

			// get the vertex with minimum motif-degree
//			if (update_min < index_min) {
//				index_min = update_min;
//			} else {
//				for (; index_min < max; ++index_min) {
//					if (!node_list[index_min].isEmpty())
//						break;
//				}
//			}
//			update_min = Integer.MAX_VALUE;
//			int index = (Integer) node_list[index_min].remove(0);

			// record the result
			count++;
			result[count][0] = index;
			result[count][1] = motif_degree[index];
			motif_num -= motif_degree[index];
			if(motif_num<0) motif_num=0;
			result[count][3]=motif_num;
			if(core_number>motif_degree[index]){
				System.out.println((graph_size-count)+"  sefe");
				return result;
			}
			if(core_number<motif_degree[index])
				core_number=motif_degree[index];
				
//			if(core_number>motif_degree[index]) {
//				result[count][4]=core_number;
//			}else {
//				core_number=motif_degree
//			}
			if(core_number>motif_degree[index])
				return result;
			core_number=motif_degree[index];
			if (graph_size - count > 0) {
				result[count][2] = motif_num / (double) (graph_size - count);
			} else {
				result[count][2] = 0;
			}

			// update
			if(motif_degree[index]>0) {
				//map=Update(index,mark);
//				System.out.println("****0"+" "+index);
//				for(Entry<Integer, Integer> entry : map.entrySet()) {
//					System.out.println(entry.getKey()+" "+entry.getValue());
//				}
//				System.out.println("*****1"+" "+mark[37]);
				//System.out.println(111);
				map=Generate(index,mark,new_array,new_map);
				//System.out.println(222);
//				for(Entry<Integer, Integer> entry : map.entrySet()) {
//					System.out.println(entry.getKey()+" "+entry.getValue());
//				}
//				System.out.println("*****2");
				if(!map.isEmpty())
				for(Entry<Integer, Integer> entry : map.entrySet()) {
					int temp_key=entry.getKey();
					int temp_value=entry.getValue();
					//node_list[motif_degree[temp_key]].remove((Integer)temp_key);			
					if(motif_degree[temp_key]>motif_degree[index]) {
						int du=motif_degree[temp_key];
						long pu=pos[temp_key+1];
						long pw=bin[du];
						long w=vert[(int) pw];
						if((temp_key+1)!=w) {
							pos[temp_key+1]=pw;
							vert[(int) pu]=w;
							pos[(int) w]=pu;
							vert[(int) pw]=temp_key+1;							
						}
						bin[du]+=1;
						motif_degree[temp_key]-=temp_value;
					}
					
//					if(update_min>motif_degree[temp_key]) {
//						update_min=motif_degree[temp_key];
//					}
//					node_list[motif_degree[temp_key]].add(temp_key);
				}
			}
			motif_degree[index]=0;
			//node_list[index_min].remove((Integer)index);
			mark[index]=1;
			
			

		}
		return result;

	}
	
	public Map<Integer,Integer> Generate(int index,int mark[],int array[],int map_s[]){
		LinkedList temp_list=new LinkedList<Integer>();
		temp_list.add(index);
		array[index]=1;
		Queue queue=new LinkedList();
		queue.add(index);
		int d=1;
		while(!queue.isEmpty()&&d+1<=3) {
			int temp=(Integer) queue.poll();
			d=array[temp];
			for(int i=0;i<Graph[temp].length;++i) {
				if(array[Graph[temp][i]]==0&&d+1<=motif_size&&mark[Graph[temp][i]]==0) {
					queue.add(Graph[temp][i]);
					array[Graph[temp][i]]=d+1;
					temp_list.add(Graph[temp][i]);
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
				for(int j=0;j<Graph[node].length;++j) {
					if(array[Graph[node][j]]!=0&&Graph[node][j]!=node) {
						temp_count++;
					}
				}
				new_graph[num]=new int[temp_count];
				temp_count=0;
				for(int j=0;j<Graph[node].length;++j) {
					if(array[Graph[node][j]]!=0&&Graph[node][j]!=node) {
						new_graph[num][temp_count]=Graph[node][j];
						temp_count++;
					}
				}
				num++;
		}
		for(int i=0;i<count;++i) {
			int update=(Integer) temp_list.get(i);
			array[update]=0;
		}
		
//		System.out.println();
//		for(int i=0;i<count;++i) {
//			for(int j=0;j<new_graph[i].length;++j) {
//				System.out.print(new_graph[i][j]+" ");
//			}
//			System.out.println();
//		}
		
		for(int i=0;i<count;++i) {
			for(int j=0;j<new_graph[i].length;++j) {
				new_graph[i][j]=map_s[new_graph[i][j]];
			}
		}
		//EnumCstar f=new EnumCstar(new_graph,count);
		//EnumTwoTri f=new EnumTwoTri(new_graph,count);
		//EnumThTri f=new EnumThTri(new_graph,count);
		EnumBasket f=new EnumBasket(new_graph,count);
		f.Enumerate_One();
		int[] t_a=f.motif_degree;
		//FindMotif f=new FindMotif( Motif,new_graph,count,motif_type);
		//f.Match_V3();
		//int[] t_a=f.getMotif_degree();
		Map<Integer,Integer> result=new HashMap<Integer,Integer>();
		for(int i=1;i<t_a.length;++i) {
			if(t_a[i]>0)
				result.put(map_array[i], t_a[i]);
		}
		return result;
	}
	
	
	public int com(int index,int mark[],int array[],int map_s[]){
		LinkedList temp_list=new LinkedList<Integer>();
		temp_list.add(index);
		//int[] array=new int[graph_size];
		//Arrays.fill(array, 0);
		array[index]=1;
		Queue queue=new LinkedList();
		queue.add(index);
		int d=1;
		while(!queue.isEmpty()&&d+1<=motif_size) {
			int temp=(Integer) queue.poll();
			d=array[temp];
			for(int i=0;i<Graph[temp].length;++i) {
				if(array[Graph[temp][i]]==0&&d+1<=motif_size) {
					queue.add(Graph[temp][i]);
					array[Graph[temp][i]]=d+1;
					temp_list.add(Graph[temp][i]);
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
				for(int j=0;j<Graph[node].length;++j) {
					if(array[Graph[node][j]]!=0&&Graph[node][j]!=node) {
						temp_count++;
					}
				}
				new_graph[num]=new int[temp_count];
				temp_count=0;
				for(int j=0;j<Graph[node].length;++j) {
					if(array[Graph[node][j]]!=0&&Graph[node][j]!=node) {
						new_graph[num][temp_count]=Graph[node][j];
						
						temp_count++;
					}
				}
				num++;
		}
		for(int i=0;i<count;++i) {
			int update=(Integer) temp_list.get(i);
			array[update]=0;
		}
		
//		System.out.println();
//		for(int i=0;i<count;++i) {
//			for(int j=0;j<new_graph[i].length;++j) {
//				System.out.print(new_graph[i][j]+" ");
//			}
//			System.out.println();
//		}
		//System.out.println("******");
		for(int i=0;i<count;++i) {
			//System.out.println();
			for(int j=0;j<new_graph[i].length;++j) {
				new_graph[i][j]=map_s[new_graph[i][j]];
				//System.out.print(new_graph[i][j]+" ");
			}
		}
		//System.out.println();
		//FindMotif f=new FindMotif( Motif,new_graph,count,motif_type);
		//int mmm=f.Match_V3();
		//EnumCstar f=new EnumCstar(new_graph,count);
		//EnumTwoTri f=new EnumTwoTri(new_graph,count);
		//EnumThTri f=new EnumThTri(new_graph,count);
		EnumBasket f=new EnumBasket(new_graph,count);
		int mmm=f.Enumerate_One();
		//int[] t_a=f.motif_degree;
		//System.out.println(mmm);
		//Log.write("111112");
		return mmm;
	}


}



//debug: replace DFS by BFS
//public int[] ComputeMD() {
//	// initialize the array
//	int motif_degree[] = new int[graph_size];
//	Arrays.fill(motif_degree, 0);
//
//	int H[] = new int[motif_size];
//	ArrayList record[] = new ArrayList[motif_size];
//	int D[] = new int[graph_size];
//	int F[] = new int[motif_size];
//	
//
//	for (int i = 0; i < graph_size; ++i) {
//		Arrays.fill(F, 0);
//		int motif_num = 0;
//		for (int j = 0; j < motif_size; ++j)
//			record[j] = new ArrayList<Integer>();
//		Arrays.fill(D, 0);
//		int deep = 0;
//		H[0] = i;
//		record[0].add(0);
//		while (deep >= 0) {
//			if (deep == motif_size - 1) {
//				deep--;
//				if(H[0]==0) {
//					System.out.println();
//					for(int mm=0;mm<motif_size;++mm)
//						System.out.print(H[mm]);
//					System.out.println();
//				}
//				int temp = CountMotif(H);
//				//System.out.println(temp);
//				motif_num += temp;
//				// Construct(H, temp);
//				continue;
//			}
//
//			int index = H[deep];
//			// mark that we have visited this vertex
//			
//			D[index] = 1;
//			// find a feasible vertex
//			int j = F[deep];
//			for (; j < Graph[index].length; ++j) {
//				if (D[Graph[index][j]] == 0)
//					break;
//			}
//			
//			if(H[0]==0) {
//				System.out.println("&&&&&");
//				for(int mm=0;mm<motif_size;++mm)
//				System.out.print(H[mm]+" ");
//				System.out.println();
//				System.out.println("(()())");
//			}
//			
//			if (j < Graph[index].length) {
//				F[deep] = j;
//				H[deep + 1] = Graph[index][j];
//				D[Graph[index][j]] = 1;
//				record[deep + 1].add(Graph[index][j]);
//				deep++;
//			} else {
//				F[deep + 1] = 0;
//				F[deep]=0;
//				for (int m = 0; m < record[deep + 1].size(); ++m) {
//					int temp = (int) record[deep + 1].get(m);
//					D[temp] = 0;
//				}
//				record[deep + 1].clear();
//				deep--;
//			}
//
//		}
//		motif_degree[i] = motif_num;
//
//	}
//	return motif_degree;
//}