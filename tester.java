/**
 * Author Richmond Frimpong
 * A samll tester program for the Graph implementation
 */
package Graphs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Graphs.AdjacentList.Vertex;

/**
 * @author RichMond
 * 
 */
public class tester {

	public static <E, V> void main(String[] args) throws IOException, InvalidKeyException {
		Scanner sc = new Scanner(System.in);
		String curOS = System.getProperty("os.name");
		System.out.println(curOS);
		//String PATHLINUX = "/home/richie/Desktop/Sharedwindows/AGraph/bin/Graphs/airports";
		//String PATHWINDOWS = "C:/Users/RichMond/workspace/AGraph/src/Graphs/airports";
		File file = new File("C:/Users/RichMond/workspace/AGraph/src/Graphs/airports");
		//if(os.equals(curOS)){
			 //file = new File(PATHLINUX);
		//}
		//else if(os.equals(curOS)){
			 //file = new File(PATHWINDOWS);
		//}
		//else{
			//System.out.println("Exiting.............");
			//System.exit(0);
		//}
		AdjacentList<String, String> g = new AdjacentList<String, String>();
		List<String> list = new ArrayList<String>();
		
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		int index = 0 ;
		String read = "";
		while((read = br.readLine())!= null)
		{
			list.add(read);
		}
		//System.out.println(list);
		br.close();
		String vertex[] = new String[list.size()];
		list.toArray(vertex);
		//System.out.println(vertex[1]);
		while(index < vertex.length-1){
			g.insertVertex(g.createVertex(vertex[index]));
			//g.insertVertex(vertex[index]);
			//System.out.println(vertex[index]);
			index++;
		}
		g.insertEdge(vertex[4], vertex[6],"1000" );
		g.insertEdge(vertex[5], vertex[6],"2000" );
		g.insertEdge(vertex[10], vertex[8],"3000" );
		g.insertEdge(vertex[22], vertex[1],"89000" );
		g.insertEdge(vertex[40], vertex[0] ,"56000" );
		g.insertEdge(vertex[0], vertex[15],"5000" );
		g.insertEdge(vertex[15], vertex[23],"15000" );
		g.insertEdge(vertex[40], vertex[26],"2000" );
		g.insertEdge(vertex[40], vertex[23],"1000" );
		g.insertEdge(vertex[26], vertex[0] ,"23000" );
		g.insertEdge(vertex[17], vertex[16],"156000" );
		g.insertEdge(vertex[35], vertex[9],"107777" );
		g.insertEdge(vertex[28], vertex[36],"14546" );
		g.insertEdge(vertex[19], vertex[18],"189566" );
		//g.replace(vertex[40], "555");
		g.removeConnection(vertex[4], vertex[6]);
		g.showAll();
		g.showEdges();
		System.out.println("Adjacent");
		//g.Connections2(vertex[40]);
		//g.shortestPath(vertex[40], vertex[23]);
	}
}
