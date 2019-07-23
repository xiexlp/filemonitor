package js.com.file.concat.transform;

import java.util.HashSet;
import java.util.Set;

public class StringTest {
	
	
	public static void main(String[] args) {
		String name = "aaa.txt";
		String type=getType(name);
		System.out.println(type);
		
		
		
		String types="java,bat,xml";
		splitTypes(types);
		System.out.println("------------");
		splitTypeSet(types);
	}
	
	
	
	public static String getType(String fname){
		int index = fname.lastIndexOf(".");
		String type = fname.substring(index+1);
		return type;
	}
	
	
	public static String[] splitTypes(String types){
		String[] typeArray= types.split("\\,");
		int l = typeArray.length;
		
		for(String type:typeArray){
			System.out.println(type);
		}
		return typeArray;
	}
	
	public static Set<String> splitTypeSet(String types){
		Set<String> typeSet = new HashSet<String>();
		String[] typeArray= types.split("\\,");
		int l = typeArray.length;
		
		for(String type:typeArray){
			System.out.println(type);
			typeSet.add(type);
		}
		return typeSet;
	}
	

}
