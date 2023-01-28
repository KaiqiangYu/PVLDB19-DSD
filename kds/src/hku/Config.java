package hku;

/**
 * @author yukaiqiang
 * @date Nov 16, 2017
 */
public class Config {
	public static double kwFreq = 0.01;//consider all the words globally
	public static int topKw = 20;//consider the keywords of each user locally
	
	//stem file paths
	public static String stemFile = "./stemmer.lowercase.txt";
	public static String stopFile = "./stopword.txt";
	
	//motif file paths
	public static String motifFile="./motif.txt";
	
	//dataset file paths
	
	
	
	
	
	//query parameters
	public static int k = 6;//the degree constraint
	
	//the # of queryId examples
	public static int qIdNum = 300;
	
	//save parameters
	public static int ccsSizeThreshold = 50;//community size
	
	//log path
	public static String logFilePath = "./result/Efficiency.txt";
	
	//public static String logFilePath=""
	//CODICIL parameter
	public static int clusterK = 2500;//the number of clusters
	
	//public static int Motif_TYPE=1;
	
}
