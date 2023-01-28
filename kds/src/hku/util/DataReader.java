package hku.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * read the information of given graph and given motif 
 * @author yukaiqiang
 * @date Nov,16 2017
 * Node are named from (0,1,2,...)
 */
public class DataReader {
	
	/** URL of the given graph */
	private String Graph_File=null;
	/** URL of the given motif */
	private String Motif_File=null;
	/** different types of motifs. */
	public int Motif_Type=0;
	/** count motif */
	public int Motif_Count=1;
	/** the number of vertex in the given graph*/
	public int graph_size=0;
	
	public int Graph[][]=null;
	public int Motif[][]=null;
	
	
	
	//private int edge=0;
	
	
	/**
	 * 
	 * @param Graph_File URL of the given graph
	 * @param Motif_File URL of the given motif
	 */
	public DataReader(String Graph_File, String Motif_File ) {
		this.Graph_File=Graph_File;
		this.Motif_File=Motif_File;
	}
	
	/**
	 * Read the information of given graph from the file.
	 * 
	 * File Format:
	 * 3
	 * 0 1 2
	 * 1 0
	 * 2 1
	 * The first line of the file is the number of vertices (e.g. 3)
	 * The next few lines give the information about adjacency List of
	 * given graph (e.g. The second line '0 1 2' represents two different edges 
	 * such as 0-1,0-2)
	 * 
	 * @return adjacency List of the given graph 
	 */
	public int[][] readGraph(){
		int Graph[][]=null;
		long count_edge=0;
		try {
			BufferedReader stdin=new BufferedReader(new FileReader(Graph_File));
			String line=null;
			int vertex=0;
			
			line=stdin.readLine();
			String s[]=line.split(" ");
			int graph_size=Integer.parseInt(s[0]);
			
			//String s[];
			//int graph_size=7414866;
			
			Graph=new int[graph_size][];
			this.graph_size=graph_size;
			while((line = stdin.readLine()) != null){
				s = line.split(" ");
				//System.out.println(s[0]);
				vertex = Integer.parseInt(s[0]);
				Graph[vertex] = new int[s.length - 1];
				count_edge+=s.length-1;
				for(int i = 1;i < s.length;i ++){
					Graph[vertex][i - 1] = Integer.parseInt(s[i]);
				}
			}
			System.out.println("###"+count_edge/2);
			stdin.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.Graph=Graph;
		return Graph;
	}
	
	public int[][] RereadGraph(String aaa){
		int Graph[][]=null;
		try {
			BufferedReader stdin=new BufferedReader(new FileReader(Graph_File));
			String line=null;
			int vertex=0;
			long count_edge=0;
			line=stdin.readLine();
			String s[]=line.split(" ");
			int graph_size=Integer.parseInt(s[0]);
			Graph=new int[graph_size][];
			this.graph_size=graph_size;
			while((line = stdin.readLine()) != null){
				s = line.split(" ");
				//System.out.println(s[0]);
				vertex = Integer.parseInt(s[0]);
				count_edge=count_edge+s.length-1;
				Graph[vertex] = new int[s.length - 1];
				for(int i = 1;i < s.length;i ++){
					Graph[vertex][i - 1] = Integer.parseInt(s[i]);
				}
			}
			stdin.close();
			
			BufferedWriter stdout =new BufferedWriter(new FileWriter(aaa,false));
			stdout.write(graph_size+"\n");
			stdout.flush();
			for(int i=0;i<graph_size;++i) {
				stdout.write(i+"");
				int temp_array[]=Graph[i];
				Arrays.sort(temp_array);
				for(int j=0;j<Graph[i].length;++j) {
					stdout.write(" "+Graph[i][j]);
				}
				stdout.write("\n");
				stdout.flush();
			}
			stdout.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.Graph=Graph;
		return Graph;
	}
	
	/**
	 * Read the information of given motif from file.
	 * 
	 * File Format
	 * 4 0 1
	 * 0 1
	 * 1 2
	 * 2 3
	 * 3 0
	 * There are three integers in the first line of the file.
	 * '4' the number of vertices
	 * '0' illustrate different types of the motifs
	 * '1' used to count the motifs
	 * @return The adjacency matrix of the given motif.
	 */
	public int[][] readMotif(){
		int Motif[][]=null;
		int tempx,tempy;
		try {
			BufferedReader stdin=new BufferedReader(new FileReader(Motif_File));
			String line=stdin.readLine();
			String s[]=line.split(" ");
			
			int motif_size=Integer.parseInt(s[0]);
			Motif_Type=Integer.parseInt(s[1]);
			Motif_Count=Integer.parseInt(s[2]);
			
			Motif=new int[motif_size][motif_size];
			while((line=stdin.readLine())!=null) {
				s=line.split(" ");
				tempx=Integer.parseInt(s[0]);
				tempy=Integer.parseInt(s[1]);
				Motif[tempx][tempy]=1;
				Motif[tempy][tempx]=1;
			}
			
			stdin.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.Motif=Motif;
		return Motif;
	}

	/**
	 * @return the motif_Type
	 */
	public int getMotif_Type() {
		return Motif_Type;
	}

	/**
	 * @return the motif_Count
	 */
	public int getMotif_Count() {
		return Motif_Count;
	}
	
	public int gentgraph_size() {
		return graph_size;
	}

	//this class has been tested as follows and all methods in the class perform well
	public static void main(String[] args) {
//		System.out.println("!!!test!!!");
		DataReader a=new DataReader("./datasets/yeast.txt", "test.txt");
		int temp[][]=a.RereadGraph("./datasets/yeast11.txt");
//		for(int i=0;i<a.gentgraph_size();++i) {
//			for(int j=0;j<temp[i].length;++j) {
//				System.out.print(temp[i][j]+"\t");
//			}
//			System.out.println();
//		}
//		
//		temp=a.readMotif();
//		for(int i=0;i<temp.length;++i) {
//			for(int j=0;j<temp.length;++j) {
//				System.out.print(temp[i][j]+"\t");
//			}
//			System.out.println();
//		}
	}

}
