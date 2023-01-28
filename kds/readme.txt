1. Summary: algorithms implemented in the current version

1.1 Approximation algorithms for EDS/CDS/PDS problem

1.1.a) Existing methods: PeelApp.
1.1.b) Proposed approaches: IncApp, CoreApp.

1.2 Exact algorithms for EDS/CDS/PDS problem

1.2.a) Existing methods: Exact (only for EDS and CDS problem).
1.2.b) Proposed approaches: CoreExact.


2. File architectures

./kds
 ./datasets			//Graph datasets employed in the experiments 
 ./motif			//Edges/cliques/patterns
 ./result			//Experimental results
 ./src/hku	
   ./algo			//All algorithms
     ./cds			//Algorithms for CDS problem
        ./CDSdecompose.java	//Decomposition procedures for obtaining k-clique-cores
        ./KList.java		//Enumeration procedures for listing all k-cliques
	./TDCDS.java		//CoreApp for CDS problem
	./Test.java		//Example of running CoreApp/IncApp/PeelApp

     ./exist			//Algorithms
        ./Appalgo.java		//IncApp-{ApproximateInc()} & PeelApp-{Approximate()}
	./Exactalgo.java	//Exact
        ./DynamicExactalgo.java //CoreExact
     ./topdown			//CoreApp for EDS and PDS problem
        ./TD***.java		//CoreApp for the specific patterns ***(Basket/star/edge/ThrTriangle/TwoTriangle) and the general patterns ***(motif)
     ./prune			//Pruning techniques
     ./pds***			//***(Decomposition/Enumeration) procedures for PDS problem
   ./test/Test.java		//Running examples
   ./util                       


3. Running Examples

Pls refer to ./kds/src/hku/test/Test.java for invoking desired algorithms.


4. Other issues

4.1) Data formats (undirected graph data).

All patterns and datasets used in our experiments should keep the following format.

Data_Format
***************************************
Number_of_vertices
Vertex_index Neighbor_index_1 …. Neighbor_index_n
***************************************

Example.txt
***************************************
3
0 1 2
1 0 2
2 0 1
***************************************

The file ‘Example.txt’ depicts a triangle with three vertices denoted as 0,1,2 and tree edges- (0,1),(0,2),(1,2).

4.2) Run PDS’s algorithm for CDS/EDS problem.

The results should be correct, but you may suffer much longer running time. This is because some procedures(decomposition/enumeration) are designed case by case. The same issues also occur when you invoke wrong functions for your desired problems.   

  
