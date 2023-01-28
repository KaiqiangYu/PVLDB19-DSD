package hku.algo.cds;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import hku.util.DataReader;
import hku.util.KCore;

public class KList {
    
    private int[][] graph;
    private int k;
    private int[] order;
    private static int graph_size;
    private int degree[];
    private int label[];
    public int motif_num=0;
    public long[] motif_degree;
    private int[][] GenGraph;
    public Map<String, int[]> Statistic=new HashMap<String, int[]>();
    
    public ArrayList<String> Adjlist[];
    
    
    public KList(int[][] graph,int k) {
            this.GenGraph=graph;
            this.k=k;
            
            graph_size=graph.length;
            this.graph=new int[graph_size][];
            order=new int[graph_size];
            degree=new int[graph_size];
            label=new int[graph_size];
            Arrays.fill(label, k);
            motif_degree=new long[graph_size];
            Arrays.fill(motif_degree, 0);
    }
    
    public void getListingOrder() {
            KCore b=new KCore(GenGraph);
            b.decompose();
            
            int temp_arr[]=b.obtainReverseCoreArr();
//          for(int i=0;i<graph_size;++i) {
//                  System.out.println(temp_arr[i]);
//          }
            for(int i=0;i<graph_size;++i) {
                    order[temp_arr[i]]=i+1;
            }
            
    }
    
    public void GenerateDAG() {
            
            int count;
            for(int i=0;i<graph_size;++i) {
                    count=0;
                    for(int j=0;j<GenGraph[i].length;++j) {
                            if(order[i]<order[GenGraph[i][j]]) {
                                    count++;
                            }
                    }
                    int arr[]=new int[count];
                    degree[i]=count;
                    count=0;
                    for(int j=0;j<GenGraph[i].length;++j) {
                            if(order[i]<order[GenGraph[i][j]]) {
                                    arr[count]=GenGraph[i][j];
                                    count++;
                            }
                    }
                    graph[i]=arr;
            }
            
    }
    
    public void ListingRecord(int k,ArrayList<Integer> c,ArrayList<Integer> arr) {

            if(k==2) {
//                  System.out.println(">>>");
                    String a="";
                    for(int m=0;m<c.size();++m) {
                            a+=c.get(m)+" ";
                    }
                    int multi=0;
                    for(int i=0;i<arr.size();++i) {
                            int temp=arr.get(i);
                            for(int j=0;j<degree[temp];++j) {
//                                  System.out.println(a+temp+" "+graph[temp][j]);
                                    a=a+temp+" "+graph[temp][j];
                                    multi++;
                                    motif_num++;
                                    motif_degree[graph[temp][j]]++;
                                    motif_degree[temp]++;
                                    int temp_arr[]=new int[this.k+1];
                                    for(int m=0;m<c.size();++m) {
                                            temp_arr[m]=c.get(m);
                                    }
                                    temp_arr[this.k-2]=temp;
                                    temp_arr[this.k-1]=graph[temp][j];
                                    temp_arr[this.k]=1;
                                    //System.out.println(temp_arr[k]+" "+k);
                                    this.Statistic.put(a, temp_arr);
                            }
                    }
                    for(int m=0;m<c.size();++m) {
                            int temp=c.get(m);
                            motif_degree[temp]+=multi;
                    }
                    
            }else {
                    
                    for(int i=0;i<arr.size();++i) {
                            int temp=arr.get(i);
                            //int count=0;
                            
                            ArrayList<Integer> arr_n=new ArrayList<Integer>();
                            for(int j=0;j<graph[temp].length;++j) {
                                    //System.out.println("****"+graph[temp][j]+" "+" "+label[graph[temp][j]]+" "+k);
                                    if(label[graph[temp][j]]==k) {
                                            label[graph[temp][j]]=k-1;
                                            
                                            //count++;
                                            arr_n.add(graph[temp][j]);
                                    }                                               
                            }
                            
                            for(int j=0;j<arr_n.size();++j) {
                                    //int count=0;
                                    int arr_temp=arr_n.get(j);
                                    int index=0;
                                    for(int m=graph[arr_temp].length-1;m>index;--m) {
                                            if(label[graph[arr_temp][m]]==k-1) {
                                                    while(index<m&&label[graph[arr_temp][index]]==k-1) {
                                                            index++;
                                                    }
                                                    if(label[graph[arr_temp][index]]!=k-1) {
                                                            int temp1=graph[arr_temp][m];
                                                            graph[arr_temp][m]=graph[arr_temp][index];
                                                            graph[arr_temp][index]=temp1;
                                                            
                                                    }
                                            }
                                    }
                                    if(graph[arr_temp].length!=0&&label[graph[arr_temp][index]]==k-1)
                                            index++;
                                    degree[arr_temp]=index;
                            }
                            
                            c.add(arr.get(i));
                            ListingRecord(k-1,c,arr_n);
                            c.remove(arr.get(i));
                            for(int j=0;j<arr_n.size();++j) {
                                    int arr_temp=arr_n.get(j);
                                    label[arr_temp]=k;
                            }
                    }
            }
            
    }
    
    
    
    public void ListingAdj(int k,ArrayList<Integer> c,ArrayList<Integer> arr) {
    		//System.out.println(k);
        if(k==2) {
                //System.out.println(">>>");
                String a="";
                for(int m=0;m<c.size();++m) {
                        a+=c.get(m)+" ";
                }
                int multi=0;
                for(int i=0;i<arr.size();++i) {
                        int temp=arr.get(i);
                        for(int j=0;j<degree[temp];++j) {
//                              System.out.println(a+temp+" "+graph[temp][j]);
                                String cc=a+temp+" "+graph[temp][j];
                                multi++;
                                motif_num++;
                                motif_degree[graph[temp][j]]++;
                                motif_degree[temp]++;
                                int temp_arr[]=new int[this.k+1];
                                for(int m=0;m<c.size();++m) {
                                        temp_arr[m]=c.get(m);
                                }
                                temp_arr[this.k-2]=temp;
                                temp_arr[this.k-1]=graph[temp][j];
                                temp_arr[this.k]=1;
                                //System.out.println(a+" "+temp_arr.length);
                                this.Statistic.put(cc, temp_arr);
                                for(int m=0;m<this.k;++m) {
                                		Adjlist[temp_arr[m]].add(cc);
                                }
                        }
                }
                for(int m=0;m<c.size();++m) {
                        int temp=c.get(m);
                        motif_degree[temp]+=multi;
                }
                
        }else {
                
                for(int i=0;i<arr.size();++i) {
                        int temp=arr.get(i);
                        //int count=0;
                        
                        ArrayList<Integer> arr_n=new ArrayList<Integer>();
                        for(int j=0;j<graph[temp].length;++j) {
                                //System.out.println("****"+graph[temp][j]+" "+" "+label[graph[temp][j]]+" "+k);
                                if(label[graph[temp][j]]==k) {
                                        label[graph[temp][j]]=k-1;
                                        
                                        //count++;
                                        arr_n.add(graph[temp][j]);
                                }                                               
                        }
                        
                        for(int j=0;j<arr_n.size();++j) {
                                //int count=0;
                                int arr_temp=arr_n.get(j);
                                int index=0;
                                for(int m=graph[arr_temp].length-1;m>index;--m) {
                                        if(label[graph[arr_temp][m]]==k-1) {
                                                while(index<m&&label[graph[arr_temp][index]]==k-1) {
                                                        index++;
                                                }
                                                if(label[graph[arr_temp][index]]!=k-1) {
                                                        int temp1=graph[arr_temp][m];
                                                        graph[arr_temp][m]=graph[arr_temp][index];
                                                        graph[arr_temp][index]=temp1;
                                                        
                                                }
                                        }
                                }
                                if(graph[arr_temp].length!=0&&label[graph[arr_temp][index]]==k-1)
                                        index++;
                                degree[arr_temp]=index;
                        }
                        
                        c.add(arr.get(i));
                        ListingAdj(k-1,c,arr_n);
                        c.remove(arr.get(i));
                        for(int j=0;j<arr_n.size();++j) {
                                int arr_temp=arr_n.get(j);
                                label[arr_temp]=k;
                        }
                }
        }
        
}
    
    
    public void Listing(int k,ArrayList<Integer> c,ArrayList<Integer> arr) {

            if(k==2) {
                    
                    String a="";
                    for(int m=0;m<c.size();++m) {
                            a+=c.get(m)+" ";
                    }
                    int multi=0;
                    for(int i=0;i<arr.size();++i) {
                            int temp=arr.get(i);
                            for(int j=0;j<degree[temp];++j) {
//                                  System.out.println(a+temp+" "+graph[temp][j]);
                                    multi++;
                                    motif_num++;
                                    motif_degree[graph[temp][j]]++;
                                    motif_degree[temp]++;
                            }
                    }
                    for(int m=0;m<c.size();++m) {
                            int temp=c.get(m);
//                          System.out.println(c.get(m)+"####"+);
                            motif_degree[temp]+=multi;
                    }
                    
            }else {
                    
                    for(int i=0;i<arr.size();++i) {
                            int temp=arr.get(i);
                            //int count=0;
                            
                            ArrayList<Integer> arr_n=new ArrayList<Integer>();
                            for(int j=0;j<graph[temp].length;++j) {
                                    //System.out.println("****"+graph[temp][j]+" "+" "+label[graph[temp][j]]+" "+k);
                                    if(label[graph[temp][j]]==k) {
                                            label[graph[temp][j]]=k-1;
                                            
                                            //count++;
                                            arr_n.add(graph[temp][j]);
                                    }                                               
                            }
                            
                            for(int j=0;j<arr_n.size();++j) {
                                    //int count=0;
                                    int arr_temp=arr_n.get(j);
                                    int index=0;
                                    for(int m=graph[arr_temp].length-1;m>index;--m) {
                                            if(label[graph[arr_temp][m]]==k-1) {
                                                    while(index<m&&label[graph[arr_temp][index]]==k-1) {
                                                            index++;
                                                    }
                                                    if(label[graph[arr_temp][index]]!=k-1) {
                                                            int temp1=graph[arr_temp][m];
                                                            graph[arr_temp][m]=graph[arr_temp][index];
                                                            graph[arr_temp][index]=temp1;
                                                            
                                                    }
                                            }
                                    }
                                    if(graph[arr_temp].length!=0&&label[graph[arr_temp][index]]==k-1)
                                            index++;
                                    degree[arr_temp]=index;
                            }
                            
                            c.add(arr.get(i));
                            Listing(k-1,c,arr_n);
                            c.remove(arr.get(i));
                            for(int j=0;j<arr_n.size();++j) {
                                    int arr_temp=arr_n.get(j);
                                    label[arr_temp]=k;
                            }
                    }
            }
            
    }
    
    
    
    public void Listing(int k,ArrayList<Integer> c,ArrayList<Integer> arr,int map) {

            if(k==2) {
                    boolean onenode=false;
                    String a="";
                    for(int m=0;m<c.size();++m) {
                            a+=c.get(m)+" ";
                            if(c.get(m)==map) {
                                    onenode=true;
                            }
                    }
                    int multi=0;
                    for(int i=0;i<arr.size();++i) {
                            int temp=arr.get(i);
                            for(int j=0;j<degree[temp];++j) {
//                                  System.out.println(a+temp+" "+graph[temp][j]);
                                    if(onenode||temp==map||graph[temp][j]==map) {
                                            multi++;
                                            motif_num++;
                                            motif_degree[graph[temp][j]]++;
                                            motif_degree[temp]++;
                                    }
                                    
                            }
                    }
                    for(int m=0;m<c.size();++m) {
                            int temp=c.get(m);
                            motif_degree[temp]+=multi;
                    }
                    
            }else {
                    
                    for(int i=0;i<arr.size();++i) {
                            int temp=arr.get(i);
                            //int count=0;
                            
                            ArrayList<Integer> arr_n=new ArrayList<Integer>();
                            for(int j=0;j<graph[temp].length;++j) {
                                    //System.out.println("****"+graph[temp][j]+" "+" "+label[graph[temp][j]]+" "+k);
                                    if(label[graph[temp][j]]==k) {
                                            label[graph[temp][j]]=k-1;
                                            
                                            //count++;
                                            arr_n.add(graph[temp][j]);
                                    }                                               
                            }
                            
                            for(int j=0;j<arr_n.size();++j) {
                                    //int count=0;
                                    int arr_temp=arr_n.get(j);
                                    int index=0;
                                    for(int m=graph[arr_temp].length-1;m>index;--m) {
                                            if(label[graph[arr_temp][m]]==k-1) {
                                                    while(index<m&&label[graph[arr_temp][index]]==k-1) {
                                                            index++;
                                                    }
                                                    if(label[graph[arr_temp][index]]!=k-1) {
                                                            int temp1=graph[arr_temp][m];
                                                            graph[arr_temp][m]=graph[arr_temp][index];
                                                            graph[arr_temp][index]=temp1;
                                                            
                                                    }
                                            }
                                    }
                                    if(graph[arr_temp].length!=0&&label[graph[arr_temp][index]]==k-1)
                                            index++;
                                    degree[arr_temp]=index;
                            }
                            
                            c.add(arr.get(i));
                            Listing(k-1,c,arr_n,map);
                            c.remove(arr.get(i));
                            for(int j=0;j<arr_n.size();++j) {
                                    int arr_temp=arr_n.get(j);
                                    label[arr_temp]=k;
                            }
                    }
            }
            
    }
    
    
    public void Listing(int k,ArrayList<Integer> c,ArrayList<Integer> arr,int map[]) {

            if(k==2) {
                    boolean onenode=false;
                    String a="";
                    for(int m=0;m<c.size();++m) {
                            a+=c.get(m)+" ";
                            if(map[c.get(m)]==1) {
                                    onenode=true;
                            }
                    }
                    int multi=0;
                    for(int i=0;i<arr.size();++i) {
                            int temp=arr.get(i);
                            for(int j=0;j<degree[temp];++j) {
//                                  System.out.println(a+temp+" "+graph[temp][j]);
                                    if(onenode||map[temp]==1||map[graph[temp][j]]==1) {
                                            multi++;
                                            motif_num++;
                                            motif_degree[graph[temp][j]]++;
                                            motif_degree[temp]++;
                                    }
                                    
                            }
                    }
                    for(int m=0;m<c.size();++m) {
                            int temp=c.get(m);
                            motif_degree[temp]+=multi;
                    }
                    
            }else {
                    
                    for(int i=0;i<arr.size();++i) {
                            int temp=arr.get(i);
                            //int count=0;
                            
                            ArrayList<Integer> arr_n=new ArrayList<Integer>();
                            for(int j=0;j<graph[temp].length;++j) {
                                    //System.out.println("****"+graph[temp][j]+" "+" "+label[graph[temp][j]]+" "+k);
                                    if(label[graph[temp][j]]==k) {
                                            label[graph[temp][j]]=k-1;
                                            
                                            //count++;
                                            arr_n.add(graph[temp][j]);
                                    }                                               
                            }
                            
                            for(int j=0;j<arr_n.size();++j) {
                                    //int count=0;
                                    int arr_temp=arr_n.get(j);
                                    int index=0;
                                    for(int m=graph[arr_temp].length-1;m>index;--m) {
                                            if(label[graph[arr_temp][m]]==k-1) {
                                                    while(index<m&&label[graph[arr_temp][index]]==k-1) {
                                                            index++;
                                                    }
                                                    if(label[graph[arr_temp][index]]!=k-1) {
                                                            int temp1=graph[arr_temp][m];
                                                            graph[arr_temp][m]=graph[arr_temp][index];
                                                            graph[arr_temp][index]=temp1;
                                                            
                                                    }
                                            }
                                    }
                                    if(graph[arr_temp].length!=0&&label[graph[arr_temp][index]]==k-1)
                                            index++;
                                    degree[arr_temp]=index;
                            }
                            
                            c.add(arr.get(i));
                            Listing(k-1,c,arr_n,map);
                            c.remove(arr.get(i));
                            for(int j=0;j<arr_n.size();++j) {
                                    int arr_temp=arr_n.get(j);
                                    label[arr_temp]=k;
                            }
                    }
            }
            
    }
    
    public void ListFast() {
            getListingOrder();
            GenerateDAG();
            ArrayList<Integer> c=new ArrayList<Integer>();
            ArrayList<Integer> arr=new ArrayList<Integer>();                
            for(int i=0;i<graph_size;++i) {
                    arr.add(i);
            }
            Listing(k, c, arr);
    }
    
    
    public void ListRecord() {
            getListingOrder();
            GenerateDAG();
            ArrayList<Integer> c=new ArrayList<Integer>();
            ArrayList<Integer> arr=new ArrayList<Integer>();                
            for(int i=0;i<graph_size;++i) {
                    arr.add(i);
            }
            ListingRecord(k, c, arr);
    }
    
    public void ListAdj() {
        getListingOrder();
        GenerateDAG();
        ArrayList<Integer> c=new ArrayList<Integer>();
        ArrayList<Integer> arr=new ArrayList<Integer>();                
        for(int i=0;i<graph_size;++i) {
                arr.add(i);
        }
        Adjlist=new ArrayList[graph_size];
        for(int i=0;i<graph_size;++i) {
        		Adjlist[i]=new ArrayList<String>();
        }
        ListingAdj(k, c, arr);
}
    
    
    public void ListOne(int a) {
            getListingOrder();
            GenerateDAG();
            
            ArrayList<Integer> c=new ArrayList<Integer>();
            ArrayList<Integer> arr=new ArrayList<Integer>();                
            for(int i=0;i<graph_size;++i) {
                    arr.add(i);
            }
            Listing(k, c, arr,a);
    }
    
    public void Listbash(int a[]) {
            getListingOrder();
            GenerateDAG();
            
            ArrayList<Integer> c=new ArrayList<Integer>();
            ArrayList<Integer> arr=new ArrayList<Integer>();                
            for(int i=0;i<graph_size;++i) {
                    arr.add(i);
            }
            Listing(k, c, arr,a);
    }
    
    
    
    
    public int getMotifNum() {
            return this.motif_num;
    }
    public long[] getMotifDegree() {
            return this.motif_degree;
    }
    
	
	public static void main(String[] args) {
		
		DataReader a=new DataReader("./datasets/ca-con.txt","./motif/edge.txt");
		//DataReader a=new DataReader("./motif/3triangle.txt","./motif/3triangle.txt");
		int Graph[][]=a.readGraph();
		int Motif[][]=a.readMotif();
		
		KList k=new KList(Graph, 3);
//		k.getListingOrder();
//		k.GenerateDAG();
//		for(int i=0;i<graph_size;++i) {
//			System.out.print(k.order[i]+" ");
//		}
//		System.out.println();
//		System.out.println("*************");
//		for(int i=0;i<k.graph_size;++i) {
//			System.out.print(i+" ");
//			for(int j=0;j<k.graph[i].length;++j) {
//				System.out.print(k.graph[i][j]+" ");
//			}
//			System.out.println();
//		}
//		System.out.println("******");
//		System.out.println("*************");
//		ArrayList<Integer> c=new ArrayList<Integer>();
//		ArrayList<Integer> arr=new ArrayList<Integer>();
//		
//		for(int i=0;i<graph_size;++i) {
//			arr.add(i);
//		}
//		k.Listing(4, c, arr);
		//k.ListFast();
		//k.ListOne(33);
		//k.ListOne(1);
		int []aa= new int[a.graph_size];
		Arrays.fill(aa, 0);
		aa[33]=1;
		aa[855]=1;
		k.Listbash(aa);
		//System.out.println(k.getMotifDegree()[33]);
		
	}

}
