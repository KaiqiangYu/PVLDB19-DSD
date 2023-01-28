# DSD: Densest Subgraph Discovery

This repository implements the densest subgraph discovery algorithm proposed in our VLDB 2019 paper.
<pre>
Yixiang Fang, Kaiqiang Yu, Reynold Cheng, Laks V.S. Lakshmanan, Xuemin Lin.
<a href="http://www.vldb.org/pvldb/vol12/p1719-fang.pdf">Efficient Algorithms for Densest Subgraph Discovery.</a>
Proc. VLDB Endow. 12(11), (2019)
</pre>

## Index  
```shell
.
|- README.md                                                    
|- kds
    |- datasets                     // Two example graphs
        |- graph.txt
        |- test.txt 			 
    |- motif
        |- ...                      // Edges/cliques/patterns			
    |- result
        |- ...                      // Experimental results			
    |- src/hku                      // Source code
        |- util                     // Graph processing procedures (e.g., core decomposition)
        |- algo                     // Implementation of our algorithms
            |- cds                  // algorithms for Clique-based Density Subgraph (CDS) Discovery
                |- CDSdecomposite.java      // (k,\phi)-core decomposition
                |- KList.java               // list all edges/k-cliques
                |- TDCDS.java               // CoreApp for CDS
                |- ExactTest.java           // Running examples for CoreExact and Exact
                |- AppTest.java             // Running examples for CoreApp, IncApp and PeelApp
            |- exist
                |- Appalgo.java             // IncApp and CoreApp for CDS and PDS
                |- DynamicExactalgo.java    // CoreExact for CDS and PDS
                |- Exact.java               // Exact for CDS and PDS
                |- ...
            |- maxflow                      // Max-flow solver
            |- findgeneralpattern           // list general patterns
            |- pdscoredecompose             // k-pattern core decomposition
            |- pdsenumeration               // list some special patterns 
            |- prune                        // Pruning techniques
```


## Source code & Algorithms info
### Programming Language: `Java`

### Algorithms 
- Exact algorithm: `CoreExact` (Algorithm 4, *Proposed*), `Exact` (Algorithm 1, Baseline)
- Approximation algorithm: `CoreApp` (Algorithm 6, *Proposed*), `IncApp`(Algorithm 5, *Proposed*), `PeelApp` (Algorithm 2, Baseline)


## Input Graph Format
The input graph  should follow the following format.

 Example.txt

    3
    0 1 2
    1 0
    2 0

(1) The first line includes one non-neigtive integer, e.g., 3, which denotes the number of vertices. For example, the example  graph has three vertices {0, 1, 2}.

(2) The following lines represent an adjacent list of the input graph. To illustrate, consider the second line 0 1 2. The vertex with id 0 is adjacent with two vertices 1 and 2.
 
## Running Example
We provide examples in `ExactTest.java` and `AppTest.java` (under the folder `./kds/src/hku/algo/cds`) for running exact algorithms (`CoreExact` and `Exact`) and approximation algorithms (`CoreApp`, `IncApp` and `PeelApp`), respectively.
