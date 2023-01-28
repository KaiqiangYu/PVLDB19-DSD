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
    |- datasets/			 
    |- motif/			
    |- result/			
    |- src/                       
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
