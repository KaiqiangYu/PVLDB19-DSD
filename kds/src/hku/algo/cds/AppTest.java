package hku.algo.cds;



import java.util.Date;
import hku.algo.exist.Appalgo;
import hku.algo.findgeneralpattern.FindMotif;

import hku.util.Combination;
import hku.util.DataReader;
import hku.util.KCore;
import hku.util.Log;

public class AppTest {
	static String dataset_doc = "./datasets/";
	static String motif_doc = "./motif/";
	static String[] datasets_url = { "graph"};
	static String[] motif_url = { "edge"};
	static int[] motif_d = { 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4 };
	static String result = "./result/AppEfficiency.txt";
	static DataReader g_data[] = new DataReader[14];
	static DataReader m_data[] = new DataReader[14];
	public static void main(String[] args) {

				
		try {
			for (int i = 0; i < datasets_url.length; ++i) {
				g_data[i] = new DataReader(dataset_doc + datasets_url[i] + ".txt", null);
				g_data[i].readGraph();
			}
			for (int j = 0; j < motif_url.length; ++j) {
				m_data[j] = new DataReader(null, motif_doc + motif_url[j] + ".txt");

				m_data[j].readMotif();
			}
			DataReader a = new DataReader(null, null);
			Log stdout = new Log();

						
				for (int i = 0; i < 1; ++i) {
							stdout.write(datasets_url[i] + "\n");
							Date date = new Date();
							String time = date.toLocaleString();
							System.out.println(time + " " + datasets_url[i]);
							a.Graph = g_data[i].Graph;
							a.graph_size = g_data[i].gentgraph_size();
							
							int max_degree123=0;
							for(int j=0;j<a.graph_size;++j) {
								if(max_degree123<a.Graph[j].length) {
									max_degree123=a.Graph[j].length;
								}
							}
							System.out.println(max_degree123+" ***"+a.graph_size);
							double aaa=System.currentTimeMillis();
							KCore k123=new KCore(a.Graph);
							k123.decompose();
							int max123=k123.obtainMaxCore();
							double aaa2=System.currentTimeMillis();
							
							stdout.write("**************AppComparison***************\n");
							System.out.println("**************App***************" + "dtasets_index: " + i);
							for (int j = 0; j < 1; ++j) {
								stdout.write("  " + motif_url[j] + "\n");
								for(int ki=0;ki<3;++ki) {
									if(ki==0) {
										System.out.println("******CoreApp******");
										date = new Date();
										time = date.toLocaleString();
										System.out.println(time + " " + motif_url[j] + " motif_index: " + j);
										a.Graph = g_data[i].Graph;
										a.graph_size = g_data[i].gentgraph_size();
										a.Motif = m_data[j].Motif;
										a.Motif_Count = m_data[j].Motif_Count;
										a.Motif_Type = m_data[j].Motif_Type;
										int Graph[][] = a.Graph;
										int Motif[][] = a.Motif;
										long start=0,over=0;
										
											long arr[]=Combination.combination(a.Motif.length-1, max123,1);
											start=System.currentTimeMillis();
											hku.algo.cds.TDCDS b=new hku.algo.cds.TDCDS(a.Graph,a.graph_size,Motif,a.Motif_Count,2);
											b.EstimateByCore(arr);
											b.TDAlg();
											
											//b.GetResults();// return the dense subgraph
											b.GetDensity();// return its density
											
											over=System.currentTimeMillis();
										
										stdout.write("TopDown:\n");
										stdout.write("    " + "Time:\n");
										stdout.write("      " + "time: " + (over-start) + "\n");
									}else if(ki==1) {
										System.out.println("******IncApp******");
										date = new Date();
										time = date.toLocaleString();
										System.out.println(time + " " + motif_url[j] + " motif_index: " + j);
										a.Graph = g_data[i].Graph;
										a.graph_size = g_data[i].gentgraph_size();
										a.Motif = m_data[j].Motif;
										a.Motif_Count = m_data[j].Motif_Count;
										a.Motif_Type = m_data[j].Motif_Type;
										int Graph[][] = a.Graph;
										int Motif[][] = a.Motif;
										
										for(int yui=0;yui<a.graph_size;++yui) {
											for(int yuj=0;yuj<Graph[yui].length;++yuj) {
												if(Graph[yui][yuj]==yui) {
													System.out.println("error");
												}
											}
										}
										

										long start_time = System.currentTimeMillis();
										FindMotif b = new FindMotif(Motif, Graph, a.gentgraph_size(), a.getMotif_Count(),
												a.getMotif_Type());
										
										long match_time = System.currentTimeMillis();

										Appalgo c = new Appalgo(b.getMotif_degree(), b.getStatistic(), a.Motif.length, a.gentgraph_size(),
												Graph, Motif, a.getMotif_Count());
										
										double res[] = null;
										res = c.ApproximateClique();
										
										
										long stop_time = System.currentTimeMillis();
										stdout.write("Inc:\n");
										stdout.write("    " + "Info:\n");
										stdout.write("      " + "vertex: " + a.gentgraph_size() + "\n");
										// stdout.write(" "+"motif: "+r_n+"\n");
										stdout.write("    " + "app_densest:" + res[0] + "\n");
										stdout.write("      " + "vertex: " + res[1] + "\n");
										stdout.write("      " + "motif: " + res[2] + "\n");
										stdout.write("    " + "time: \n");
										stdout.write("      " + "matching: " + (match_time - start_time) + "ms\n");
										stdout.write("      " + "total: " + (stop_time - start_time) + "ms\n");
										// stdout.flush();
									}else if(ki==2) {
										System.out.println("******PeelApp******");
										date = new Date();
										time = date.toLocaleString();
										System.out.println(time + " " + motif_url[j] + " motif_index: " + j);
										a.Graph = g_data[i].Graph;
										a.graph_size = g_data[i].gentgraph_size();
										a.Motif = m_data[j].Motif;
										a.Motif_Count = m_data[j].Motif_Count;
										a.Motif_Type = m_data[j].Motif_Type;
										int Graph[][] = a.Graph;
										int Motif[][] = a.Motif;
										

										long start_time = System.currentTimeMillis();
										FindMotif b = new FindMotif(Motif, Graph, a.gentgraph_size(), a.getMotif_Count(),
												a.getMotif_Type());
										
										long match_time = System.currentTimeMillis();

										Appalgo c = new Appalgo(b.getMotif_degree(), b.getStatistic(), a.Motif.length, a.gentgraph_size(),
												Graph, Motif, a.getMotif_Count());
										
										double res[] = null;
										res = c.ApproximateCliquePeel();
										
										
										long stop_time = System.currentTimeMillis();
									}
//									
									stdout.write("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");

									stdout.write("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
									}
								}
								
								

						}
	
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
   
	 
	
	

}
