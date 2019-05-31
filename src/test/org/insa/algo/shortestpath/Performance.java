package org.insa.algo.shortestpath;

import org.junit.Test;

public class Performance {
	@Test
	public void Compare() throws Exception {
		
		System.out.println("#####----- Test de validité sur une carte-----######");
		
		DijkstraTestWithMap testD = new  DijkstraTestWithMap();
		long startTime = System.nanoTime();    
		long endTime = System.nanoTime();
		long timeElapsedD = endTime - startTime;
		AStarTestWithMap testA = new  AStarTestWithMap();
		long timeElapsedA;
		System.out.println();
		
		
		System.out.println("#####----- Carte : HAUTE-GARONNE ---------------------------######");
		System.out.println();
		System.out.println("----- Cas d'un chemin simple ------");
		int origine = 48128;
		int destination = 132978;
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/haute-garonne.mapgr";
		String mapName = "/Users/rafael/Desktop/Maps/haute-garonne.mapgr";
		
		float moyenne = 0;
		for(int i =0;i<10;i++) {
			testD = new  DijkstraTestWithMap();
			startTime = System.nanoTime();
			testD.testScenarioSansOraclePerf(mapName,origine,destination);    
			endTime = System.nanoTime();
			timeElapsedD = endTime - startTime;
			testA = new  AStarTestWithMap();
			startTime = System.nanoTime();
			testA.testScenarioSansOraclePerf(mapName,origine,destination);
			endTime = System.nanoTime();
			timeElapsedA = endTime - startTime;
		    moyenne += ((float)timeElapsedD/(float)timeElapsedA*100-1);
		}
		System.out.println("AStar is " + moyenne/2 + "% faster than Dijkstra in HAUTE-GARONNE");
		System.out.println();
		System.out.println();
		
		
	    System.out.println("#####----- Carte : CARRE DENSE ---------------------------######");
		System.out.println();
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 0;
		destination = 20000;
		//mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre-dense.mapgr";
		mapName = "/Users/rafael/Desktop/Maps/carre-dense.mapgr";
		
		moyenne=0;
		for(int i =0;i<2;i++) {
			testD = new  DijkstraTestWithMap();
			startTime = System.nanoTime();
			testD.testScenarioSansOraclePerf(mapName,origine,destination);    
			endTime = System.nanoTime();
			timeElapsedD = endTime - startTime;
			testA = new  AStarTestWithMap();
			startTime = System.nanoTime();
			testA.testScenarioSansOraclePerf(mapName,origine,destination);
			endTime = System.nanoTime();
			timeElapsedA = endTime - startTime;
		    moyenne += ((float)timeElapsedD/(float)timeElapsedA*100-1);
		}
		System.out.println("AStar is " + moyenne/2 + "% faster than Dijkstra in CARRE DENSE");
		System.out.println();
		System.out.println();
	    
	    
	    System.out.println("#####----- Carte : GHADELOUPE ---------------------------######");
		System.out.println();
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 9922;
		destination = 30000;
		//mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/guadeloupe.mapgr";
		mapName = "/Users/rafael/Desktop/Maps/guadeloupe.mapgr";
		
		moyenne = 0;
		for(int i =0;i<10;i++) {
			testD = new  DijkstraTestWithMap();
			startTime = System.nanoTime();
			testD.testScenarioSansOraclePerf(mapName,origine,destination);    
			endTime = System.nanoTime();
			timeElapsedD = endTime - startTime;
			testA = new  AStarTestWithMap();
			startTime = System.nanoTime();
			testA.testScenarioSansOraclePerf(mapName,origine,destination);
			endTime = System.nanoTime();
			timeElapsedA = endTime - startTime;
		    moyenne += ((float)timeElapsedD/(float)timeElapsedA*100-1);
		}
		System.out.println("AStar is " + moyenne/2 + "% faster than Dijkstra in GUADELOUPE");
		System.out.println();
		System.out.println();
	}
	 
}