package hku.algo.exist;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Queue;

import hku.algo.prune.Component;
import hku.algo.prune.DensestCore;

public class DynamicExactalgo {
	
	private Queue<Component> queue=null;
	
	private DensestCore Core=null;
	
	private int motif_size;
	
	public DynamicExactalgo(Queue<Component> queue,DensestCore Core,int motif_size) {
		this.queue=queue;
		this.Core=Core;
		this.motif_size=motif_size;
	}
	
	public MDS DynamicExact(){
		Component C,index;
		index=queue.iterator().next();
		double low_bound=0;
		Iterator it=queue.iterator();
		while(it.hasNext()) {
			C=(Component) it.next();
			if(low_bound<C.densest) {
				low_bound=C.densest;
				index=C;
			}
		}		
		if(Math.ceil(low_bound)<Math.ceil(Core.densest)) {
			low_bound=Core.densest;
		}
		
		double up_bound=Core.kmax;
		
		Exactalgo exact;
		
		MDS mds=new MDS(null,index.motif_num,index.graph_size,low_bound);

		while(!queue.isEmpty()) {
			C=queue.remove();
		
			exact=new Exactalgo(C.motif_list, motif_size, C.graph_size, C.motif_degree);
			long test=0;
			for(int mm=0;mm<C.motif_degree.length;++mm) {
				test+=C.motif_degree[mm];
			}

				exact=new Exactalgo(C.motif_list, motif_size, C.Graph.length, C.motif_degree);
				
				//System.out.println((test/motif_size)+"   "+C.motif_num);
				int res[]=exact.Exact(Math.ceil(low_bound), Math.ceil(up_bound)+1, C.motif_num);
				//compute the bound
				long motif_num=0; int vertex_num=0;
				for(Entry<String,int[]> entry:C.motif_list.entrySet()) {
					int temp[]=entry.getValue();
					int i=0;					
					for(;i<temp.length-1;++i) {
						if(res[temp[i]]==-1)
							break;
					}
					if(i==temp.length-1) {
						motif_num+=temp[i];
					}
						
				}
				for(int i=0;i<C.graph_size;++i) {
					if(res[i]!=-1)
						vertex_num++;
					//System.out.print(res[i]+" ");
				}
				if(vertex_num==0)
					vertex_num=C.graph_size;
				low_bound=(double)motif_num/(vertex_num*1.0);
				if(low_bound>mds.densest) {
					mds.densest=low_bound;
					mds.motif_num=motif_num;
					mds.s_t_result=res;
					mds.vertex_num=vertex_num;
					mds.core=C;
				}
			}
//		}
		
		return mds;
	}

}
