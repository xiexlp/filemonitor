package js.com.file.concat;

import java.util.ArrayList;
import java.util.List;

public class TestSplit {
	
	
	public static void main(String[] args) {
		
		int size=216;
		int num=3;
		
		int every= size/num;
		System.out.println("every:"+every);
		
		List<Pair> pairs = new ArrayList<Pair>();
		
		for(int i=0;i<num;i++){
			Pair pair  = new Pair();
			if(i!=num-1){
				pair.setBeginIndex(i*every);
				pair.setEndIndex(i*every+every-1);
				pair.setSize(every);
			}else{
				pair.setBeginIndex(i*every);
				pair.setEndIndex(size-1);
				pair.setSize(size-1-i*every+1);
			}
			pairs.add(pair);
		}
		
		for(Pair pair:pairs){
			System.out.println("begin:"+pair.getBeginIndex()+" end:"+pair.getEndIndex()+" size:"+pair.getSize());
		}
	}
	
	
	public static List<Pair> Split(int size,int num) {
//		int size=216;
//		int num=3;
		
		//平均分配
		int every= size/num;
		
		System.out.println("every:"+every);
		
		List<Pair> pairs = new ArrayList<Pair>();
		
		for(int i=0;i<num;i++){
			Pair pair  = new Pair();
			if(i!=num-1){
				pair.setBeginIndex(i*every);
				pair.setEndIndex(i*every+every-1);
				pair.setSize(every);
			}else{
				pair.setBeginIndex(i*every);
				pair.setEndIndex(size-1);
				pair.setSize(size-1-i*every+1);
			}
			pairs.add(pair);
		}
		
		for(Pair pair:pairs){
			System.out.println("begin:"+pair.getBeginIndex()+" end:"+pair.getEndIndex()+" size:"+pair.getSize());
		}
		return pairs;
	}
	
	
	
	
	

}
