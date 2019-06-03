package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;

import org.junit.BeforeClass;
import org.junit.Test;

public class AStarTest {

	// Small graph use for tests
	private static Graph graph;

	// List of nodes
	private static Node[] nodes;

	// List of arcs in the graph, a2b is the arc from node A (0) to B (1).
	@SuppressWarnings("unused")
	private static Arc a2b, a2c, b2d, b2e, b2f, c2a, c2b, c2f, e2c, e2d, e2f, f2e;

	@BeforeClass
	public static void initAll() throws IOException {

		// Define roads
		RoadInformation RoadInfo = new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null);

		// Create nodes
		nodes = new Node[6];
		for (int i = 0; i < nodes.length; ++i) {
			nodes[i] = new Node(i, null);
		}

		// Add arcs...
		a2b = Node.linkNodes(nodes[0], nodes[1], 7, RoadInfo, null);
		a2c = Node.linkNodes(nodes[0], nodes[2], 8, RoadInfo, null);
		b2d = Node.linkNodes(nodes[1], nodes[3], 4, RoadInfo, null);
		b2e = Node.linkNodes(nodes[1], nodes[4], 1, RoadInfo, null);
		b2f = Node.linkNodes(nodes[1], nodes[5], 5, RoadInfo, null);
		c2a = Node.linkNodes(nodes[2], nodes[0], 7, RoadInfo, null);
		c2b = Node.linkNodes(nodes[2], nodes[1], 2, RoadInfo, null);
		c2f = Node.linkNodes(nodes[2], nodes[5], 2, RoadInfo, null);
		e2c = Node.linkNodes(nodes[4], nodes[2], 2, RoadInfo, null);
		e2d = Node.linkNodes(nodes[4], nodes[3], 2, RoadInfo, null);
		e2f = Node.linkNodes(nodes[4], nodes[5], 3, RoadInfo, null);
		f2e = Node.linkNodes(nodes[5], nodes[4], 3, RoadInfo, null);

		// Initialize the graph
		graph = new Graph("ID", "", Arrays.asList(nodes), null);

	}

	@Test
	public void testDoRun() {
		System.out.println("#####-----Test de validité avec oracle sur un exemple simple-----#####");
		/* Tableau contenant les arcs*/
		//Arc[] arcs = new Arc[] { a2b, a2c, b2d, b2e, b2f, c2a, c2b, c2f, e2c, e2d, e2f, f2e };

		for (int i=0;  i < nodes.length; ++i) {

			/* Affichage du point de départ */
			System.out.print("x"+(nodes[i].getId()+1) + ":");

			for (int j=0;  j < nodes.length; ++j) {

				if(nodes[i]==nodes[j]) {
					System.out.print("     -    ");
				}
				else{

					ArcInspector arcInspectorDijkstra = new ArcInspectorFactory().getAllFilters().get(0);
					ShortestPathData data = new ShortestPathData(graph, nodes[i],nodes[j], arcInspectorDijkstra);

					BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
					DijkstraAlgorithm D = new DijkstraAlgorithm(data);

					/* Récupération des solutions de Bellman et Dijkstra pour comparer */
					ShortestPathSolution solution = D.run();
					ShortestPathSolution expected = B.run();

					/* Pas de chemin trouvé */
					if (solution.getPath() == null) {
						assertEquals(expected.getPath(), solution.getPath());
						System.out.print("(infini)  ");
					}
					/* Un plus court chemin trouvé */
					else {

						/* Calcul du coût de la solution */
						float costSolution = solution.getPath().getLength();
						float costExpected = expected.getPath().getLength();
						assertEquals(costExpected, costSolution, 0);

						/* On récupère l'avant dernier sommet du chemin de la solution (=sommet père de la destination) */
						List<Arc> arcs = solution.getPath().getArcs();
						Node originOfLastArc = arcs.get(arcs.size()-1).getOrigin();

						/* Affiche le couple (coût, sommet père du Dest) */
						System.out.print("("+costSolution+ ", x" + (originOfLastArc.getId()+1) + ") ");
					}
					
				}

			}

			/* Retour à la ligne */ 
			System.out.println();

		}
		System.out.println();
	}

	@Test
	public void testDoScenarioDistanceIDF() throws Exception {
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/ile-de-france.mapgr";
		//String mapName = "/Users/rafael/Desktop/Maps/ile-de-france.mapgr"; //test en local
		
		AStarTestWithMap test = new  AStarTestWithMap();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Ile de France -------------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName, 1,origine,destination);    
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 301615;
		destination = 89900;
		test.testScenario(mapName, 1,origine,destination);    	
	
		
		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : N'existe pas ----------");
		System.out.println("----- Destination : Existe ------------");
		origine = -1;
		destination = 89900;
		test.testScenario(mapName, 1,origine,destination);    	

		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : Existe ----------------");
		System.out.println("----- Destination : N'existe pas ------");
		origine = 301615;
		destination = -1;
		test.testScenario(mapName, 1,origine,destination);    	
		
		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : N'existe pas ----------");
		System.out.println("----- Destination : N'existe pas ------");
		origine = -1;
		destination = -2;
		test.testScenario(mapName, 1,origine,destination);    	
	}

	
	@Test
	public void testDoScenarioTempsIDF() throws Exception {
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/ile-de-france.mapgr";
		//String mapName = "/Users/rafael/Desktop/Maps/ile-de-france.mapgr";

		AStarTestWithMap test = new  AStarTestWithMap();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Ile de France -------------------------######");
		System.out.println("#####----- Mode : TEMPS ----------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName, 0,origine,destination);    
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 301615;
		destination = 89900;
		test.testScenario(mapName, 0,origine,destination);    	
	
		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : N'existe pas ----------");
		System.out.println("----- Destination : Existe ------------");
		origine = -1;
		destination = 89900;
		test.testScenario(mapName, 0,origine,destination);    	

		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : Existe ----------------");
		System.out.println("----- Destination : N'existe pas ------");
		origine = 301615;
		destination = -1;
		test.testScenario(mapName, 0,origine,destination);    	
		
		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : N'existe pas ----------");
		System.out.println("----- Destination : N'existe pas ------");
		origine = -1;
		destination = -2;
		test.testScenario(mapName, 0,origine,destination);    	
	}

	@Test
	public void testDoScenarioDistanceINSA() throws Exception {

		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		//String mapName = "/Users/rafael/Desktop/Maps/insa.mapgr";

		AStarTestWithMap test = new  AStarTestWithMap();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : INSA ----------------------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 257 ;
		destination = 257;
		test.testScenario(mapName, 1,origine,destination);    
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 885;
		destination = 257;
		test.testScenario(mapName, 1,origine,destination);    	
	
		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : N'existe pas ----------");
		System.out.println("----- Destination : Existe ------------");
		origine = -1;
		destination = 257;
		test.testScenario(mapName, 1,origine,destination);    	

		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : Existe ----------------");
		System.out.println("----- Destination : N'existe pas ------");
		origine = 885;
		destination = -1;
		test.testScenario(mapName, 1,origine,destination);    	
		
		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : N'existe pas ----------");
		System.out.println("----- Destination : N'existe pas ------");
		origine = -1;
		destination = -2;
		test.testScenario(mapName, 1,origine,destination);   
	}

	@Test
	public void testDoScenarioTempsINSA() throws Exception {
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		//String mapName = "/Users/rafael/Desktop/Maps/insa.mapgr";

		AStarTestWithMap test = new  AStarTestWithMap();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : INSA ----------------------------------######");
		System.out.println("#####----- Mode : TEMPS ----------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 257 ;
		destination = 257;
		test.testScenario(mapName, 0,origine,destination);    
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 885;
		destination = 257;
		test.testScenario(mapName, 0,origine,destination);    	
	
		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : N'existe pas ----------");
		System.out.println("----- Destination : Existe ------------");
		origine = -1;
		destination = 257;
		test.testScenario(mapName, 0,origine,destination);    	

		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : Existe ----------------");
		System.out.println("----- Destination : N'existe pas ------");
		origine = 885;
		destination = -1;
		test.testScenario(mapName, 0,origine,destination);    	
		
		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : N'existe pas ----------");
		System.out.println("----- Destination : N'existe pas ------");
		origine = -1;
		destination = -2;
		test.testScenario(mapName, 0,origine,destination);   
	}
	
	@Test
	public void testDoScenarioDistanceCarreDense() throws Exception {
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre-dense.mapgr";
		//String mapName = "/Users/rafael/Deskop/Maps/carre-dense.mapgr";


		AStarTestWithMap test = new  AStarTestWithMap();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : CARRE DENSE ---------------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 62670;
		destination = 201771;
		test.testScenario(mapName, 1,origine,destination);    		
	}

	@Test
	public void testDoScenarioTempsCarreDense() throws Exception {
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre-dense.mapgr";
		//String mapName = "/Users/rafael/Desktop/Maps/carre-dense.mapgr";

		AStarTestWithMap test = new  AStarTestWithMap();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : CARRE DENSE ---------------------------######");
		System.out.println("#####----- Mode : TEMPS -------------------------------######");
		System.out.println();

		System.out.println("----- Cas d'un chemin simple ------");
		origine = 62670;
		destination = 201771;
		test.testScenario(mapName, 0,origine,destination);    			
	}
	
	
	@Test
	public void testDoScenarioDistanceBretagne() throws Exception {
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr";
		//String mapName = "/Users/rafael/Desktop/Maps/bretagne.mapgr";

		AStarTestWithMap test = new  AStarTestWithMap();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Bretagne ----------------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------------------------######");
		System.out.println();
	
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 316635;
		destination = 254601;
		test.testScenario(mapName, 1,origine,destination);    	
	
		System.out.println("----- Cas de sommets non connexes ------");
		origine = 435720;
		destination = 201602;
		test.testScenario(mapName, 1,origine,destination);    	

	}
	
	@Test
	public void testDoScenarioTempsBretagne() throws Exception {
		String mapName = "C:/Users/Utilisateur/Desktop/3A MIC/Kimi/graphe/Maps/bretagne.mapgr";
		//String mapName = "/Users/rafael/Desktop/Maps/bretagne.mapgr";

		AStarTestWithMap test = new  AStarTestWithMap();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Bretagne ----------------------------######");
		System.out.println("#####----- Mode : TEMPS ----------------------------------######");
		System.out.println();
	
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 316635;
		destination = 254601;
		test.testScenario(mapName, 1,origine,destination);    	
	
		System.out.println("----- Cas de sommets non connexes ------");
		origine = 435720;
		destination = 201602;
		test.testScenario(mapName, 0,origine,destination);
	}

	@Test
	public void testDoScenarioMinTempsDistIDF() throws Exception {
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/ile-de-france.mapgr";
		//String mapName = "/Users/rafael/Desktop/Maps/ile-de-france.mapgr";
		
		AStarTestWithMap test = new  AStarTestWithMap();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité sans oracle sur une carte-----######");
		System.out.println("#####----- Carte : Ile de France -------------------------######");
		System.out.println();

		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName, 0,origine,destination);    
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 301615;
		destination = 89900;
		test.testScenario(mapName, 0,origine,destination);    	
	
		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : N'existe pas ----------");
		System.out.println("----- Destination : Existe ------------");
		origine = -1;
		destination = 89900;
		test.testScenario(mapName, 0,origine,destination);    	

		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : Existe ----------------");
		System.out.println("----- Destination : N'existe pas ------");
		origine = 301615;
		destination = -1;
		test.testScenario(mapName, 0,origine,destination);    	
		
		System.out.println("----- Cas de sommets inexistants ------");
		System.out.println("----- Origine : N'existe pas ----------");
		System.out.println("----- Destination : N'existe pas ------");
		origine = -1;
		destination = -2;
		test.testScenarioSansOracle(mapName,origine,destination);   
	}

	@Test
	public void testDoScenarioMinTempsDistCarreDense() throws Exception {
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre-dense.mapgr";
		//String mapName = "/Users/rafael/Desktop/Maps/carre-dense.mapgr";
		
		AStarTestWithMap test = new  AStarTestWithMap();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité sans oracle sur une carte-----######");
		System.out.println("#####----- Carte : CARRE DENSE ---------------------------######");
		System.out.println();

		System.out.println("----- Cas d'un chemin simple ------");
		origine = 318408;
		destination = 171943;
		test.testScenarioSansOracle(mapName,origine,destination);    
	}
	
	@Test
	public void testDoScenarioMinTempsDistBretagne() throws Exception {
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr";
		//String mapName = "/Users/rafael/Desktop/Maps/bretagne.mapgr";

		AStarTestWithMap test = new  AStarTestWithMap();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité sans oracle sur une carte-----######");
		System.out.println("#####----- Carte : Bretagne ----------------------------######");
		System.out.println();
	
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 316635;
		destination = 254601;
		test.testScenario(mapName, 1,origine,destination);    	
	
		System.out.println("----- Cas de sommets non connexes ------");
		origine = 435720;
		destination = 201602;
		test.testScenarioSansOracle(mapName,origine,destination);    
	}
}