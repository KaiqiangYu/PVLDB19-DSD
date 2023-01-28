package hku.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author fangyixiang
 * @date Jul 24, 2015
 * We assume that nodes' IDs are 1, 2, ..., n
 */
public class KCore {
	private int graph[][] = null;
	private int n = -1;
	private int deg[] = null;
	private int coreReverseFang[] = null; //2015-9-17, an array sorted by coreness in descend order
	
	public KCore(int graph[][]){
		this.graph = graph;
		this.n = graph.length;
		this.coreReverseFang = new int[n];// //2015-9-17, initialize this array
	}
	
	public int[] decompose(){
		long startT = System.currentTimeMillis();
		//System.out.println("KCore n:" + n + " time1:" + (System.currentTimeMillis() - startT));
		
		deg = new int[n];
		
		//step 1: obtain the degree and the maximum degree
		int md = -1; // the maximum degree in the graph
		for(int i = 0;i < n;i ++){
			
			deg[i] = graph[i].length;
			if(deg[i] > md){
				md = deg[i];
			}
		}
		//System.out.println("KCore time2:" + (System.currentTimeMillis() - startT));
//		System.out.println("md:" + md);
//		for(int i = 1;i <= n;i ++)   System.out.print("deg[" + i + "]=" + deg[i] + " ");
//		System.out.println();
		
		//step 2: fill the bin
		int bin[] = new int[md+1];
		for(int i = 0;i < n;i ++){
			bin[deg[i]] += 1;
		}
//		System.out.println("KCore time3:" + (System.currentTimeMillis() - startT));
//		for(int i = 0;i <= md;i ++)   System.out.print("bin[" + i + "]=" + bin[i] + " ");
//		System.out.println();
		
		//step 3: update the bin
		int start = 0;
		for(int d = 0; d <= md;d ++){
			int num = bin[d];
			bin[d] = start;
			start += num;
		}
//		System.out.println("KCore time4:" + (System.currentTimeMillis() - startT));
//		for(int i = 0;i <= md;i ++)   System.out.print("bin[" + i + "]=" + bin[i] + " ");
//		System.out.println();
		
		//step 4: find the position
		int pos[] = new int[n+1];
		int vert[] = new int[n+1];
		for(int v = 0; v < n;v ++){
			pos[v] = bin[deg[v]];
			vert[pos[v]] = v;
			bin[deg[v]] += 1;
		}
//		System.out.println("KCore time5:" + (System.currentTimeMillis() - startT));
//		for(int i = 1;i <= n;i ++)   System.out.print("pos[" + i + "]=" + pos[i] + " ");
//		System.out.println();
//		for(int i = 1;i <= n;i ++)   System.out.print("ver[" + i + "]=" + vert[i] + " ");
//		System.out.println();
		
		for(int d = md; d >= 1; d--){
			bin[d] = bin[d - 1];
		}
		//System.out.println(bin.length);
		if(bin.length!=0)
		bin[0] = 1;
//		System.out.println("KCore time6:" + (System.currentTimeMillis() - startT));
		
		//step 5: decompose
		for(int i = 0;i <n;i ++){
			int v = vert[i];
			for(int j = 0;j < graph[v].length;j ++){
				int u = graph[v][j];
				if(deg[u] > deg[v]){
					int du = deg[u];   int pu = pos[u];
					int pw = bin[du];  int w = vert[pw];
					if(u != w){
						pos[u] = pw;   vert[pu] = w;
						pos[w] = pu;   vert[pw] = u;
					}
					bin[du] += 1;
					deg[u] -= 1;
				}
			}
			
			//System.out.println("deg[" + v + "]=" + deg[v]);
			coreReverseFang[n - i-1] = v;
		}
//		System.out.println("KCore time7:" + (System.currentTimeMillis() - startT));
		return deg;
	}
	
	//obtain the max core
	public int obtainMaxCore(){
		int max = - 1;
		for(int i = 1;i < deg.length;i ++){
			if(deg[i] > max){
				max = deg[i];
			}
		}
		return max;
	}

	public int[] obtainReverseCoreArr(){
		return coreReverseFang;
	}
	
	public String distribute() {
		int iteration=1;
		int size=5;
		int count=0;
		//int iteration=0;
		//System.out.println("***"+deg.length);
		String sss="";
		do {
			count=0;
			for(int i=0;i<deg.length;++i) {
				if(deg[i]<size*iteration&&deg[i]>=size*(iteration-1)) {
					count++;
				}
			}
			if(count!=0){
				sss+="core_number ["+(size*(iteration-1))+" "+(size*iteration)+")"+"\tnum: "+count+"\tratio:"+(count*1.0/deg.length)+"\n";
				System.out.println("core_number ["+(size*(iteration-1))+" "+(size*iteration)+")"+"\tnum: "+count+"\tratio:"+(count*1.0/deg.length));
			}
			
			iteration++;		
		}while(count!=0);
		
		int max = - 1;
		for(int i = 1;i < deg.length;i ++){
			if(deg[i] > max){
				max = deg[i];
			}
		}
		for(int i = 1;i < deg.length;i ++){
			if(deg[i] == max){
				count++;
			}
		}
		sss+="maxCore:"+max+"\t\tnum: "+count+"\t\tratio:"+(count*1.0/deg.length)+"\n";
		System.out.println("maxCore:"+max+"\t\tnum: "+count+"\t\tratio:"+(count*1.0/deg.length));
		return sss;
		
	}
	
	public static void main(String[] args) {
//		int graph[][] = new int[11][];
//		int a1[] = {2, 3, 4, 6}; graph[1] = a1;
//		int a2[] = {1, 3, 4, 6}; graph[2] = a2; 
//		int a3[] = {1, 2, 4, 5}; graph[3] = a3;
//		int a4[] = {1, 2, 3, 5}; graph[4] = a4;
//		int a5[] = {3, 4};       graph[5] = a5;
//		int a6[] = {1, 2, 7};    graph[6] = a6;
//		int a7[] = {6};          graph[7] = a7;
//		int a8[] = {9};          graph[8] = a8;
//		int a9[] = {8};          graph[9] = a9;
//		int a10[] = {};          graph[10] = a10;
		
		
		int Graph[][]=null;
		try {
			//BufferedReader stdin=new BufferedReader(new FileReader("./info/Email-Enron.txt"));
			///Users/yukaiqiang/eclipse-workspace/mds/datasets/Email-Eu-core.txt
			BufferedReader stdin=new BufferedReader(new FileReader("./datasets/CA-HepPh.txt"));
			String line=null;
			int vertex=0;
			
			line=stdin.readLine();
			String s[]=line.split(" ");
			int graph_size=Integer.parseInt(s[0]);
			Graph=new int[graph_size][];
			//this.graph_size=graph_size;
			while((line = stdin.readLine()) != null){
				s = line.split(" ");
				//System.out.println(s[0]);
				vertex = Integer.parseInt(s[0]);
				Graph[vertex] = new int[s.length - 1];
				for(int i = 1;i < s.length;i ++){
					Graph[vertex][i - 1] = Integer.parseInt(s[i]);
				}
			}
			stdin.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long s=System.currentTimeMillis();
		KCore kcore = new KCore(Graph);
		int core[] = kcore.decompose();
		int maxCore = kcore.obtainMaxCore();
		//kcore.distribute();
		//for(int i = 1;i < core.length;i ++)   System.out.print("cor[" + i + "]=" + core[i] + " ");
		System.out.println();
		System.out.println("maxCore:" + maxCore);
		long en=System.currentTimeMillis();
		System.out.println(en-s);
	}

}
