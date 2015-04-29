package Graphs;

import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AdjacentList<V, E> extends Position<V> {

	private class Path implements Comparable {

		private Vertex<V> vertex;
		private int cost;
		private LinkedList<Vertex<V>> list;

		public Path(Vertex<V> v, int cost) {
			this.vertex = v;
			this.cost = cost;
			this.list = new LinkedList<Vertex<V>>();
		}

		public int compareTo(Object obj) {
			@SuppressWarnings("unchecked")
			Path path = (Path) obj;
			if (this.cost > path.cost) {
				return 1;
			} else if (this.cost < path.cost) {
				return -1;
			}

			return 0;
		}

	}

	@SuppressWarnings("hiding")
	public class Vertex<V> extends Position<V> {
		// attributes of inner class Vertex
		private V element;
		private List<Edge<E>> outDegree;
		private Position<Vertex<V>> pos;

		public Vertex(V element) {
			// super(element);
			this.element = element;
			this.outDegree = new LinkedList<Edge<E>>();
		}

		/** Returns the Vertex Element */
		public V getElement() {
			return this.element;
		}

		public void setVertex(V o) {
			setElement(o);
		}

		protected int degree() {
			return outDegree.size();
		}

		/** Stores the position of the vertex within the graphs vertexList */
		public void setPosition(Position<Vertex<V>> p) {
			pos = p;
		}

		/** return the current position of the vertex in the graphs vertex list */
		public Position<Vertex<V>> getPosition() {
			return pos;
		}

		/** Returns a reference to the outgoing egdes in the graph */
		public LinkedList<Edge<E>> getOutgoing() {
			return (LinkedList<Edge<E>>) outDegree;
		}

		/** Returns the incident edges on this vertex */
		public Iterable<Edge<E>> incidentEdges() {
			return outDegree;
		}

		/** Inserts and edge into the list container */
		public Position<Edge<E>> insertIncidence(Edge<E> edge) {
			outDegree.add(edge);
			int index = outDegree.indexOf(edge);
			return (Position<Edge<E>>) outDegree.get(index);
		}

		/** Removes an edge from the list and associated vertices */
		public void removeIncidence(Position<Edge<E>> position) {
			outDegree.remove(position);
		}

		public Position<Vertex<V>> location() {
			return pos;
		}

		@Override
		public V element() {
			return this.element;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Vertex<V> other = (Vertex<V>) obj;

			if (element == null) {
				if (other.element != null)
					return false;
			} else if (!element.equals(other.element))
				return false;
			return true;
		}

	}// end of inner Vertex class

	// Start of Inner Edge class
	public class Edge<E> extends Position<V> {
		// Attributes of inner Class Edges
		private E element;
		private LinkedList<Vertex<V>>[] endpoints;
		private Position<Edge<E>> position;
		// store edges from and to
		private Position<Edge<E>>[] incident;

		/**
		 * Construct Inner Edge instance from destTo to destFrom and store them
		 * in a linked list
		 */
		public Edge(Vertex<V> destFrom, Vertex<V> destTo, E weight) {
			this.element = weight;
			endpoints = new LinkedList[2];
			int index = 0;
			int track = 0;
			while (index < endpoints.length) {
				endpoints[index] = new LinkedList<Vertex<V>>();
				++index;
			}
			endpoints[0].add(destFrom);
			endpoints[1].add(destTo);
			incident = (Position<Edge<E>>[]) new Position[2];
		}

		/** Returns the associated element for given edge */
		public E getElement() {
			return this.element;
		}

		public LinkedList<Vertex<V>>[] endVertice() {
			return endpoints;
		}

		/** Returns the position of the edge in the and its endvertices */
		public Position<Edge<E>>[] incidence() {
			return incident;
		}

		/** Returns a reference to the endpoint LinkedList */
		public LinkedList<Vertex<V>>[] getEndpoints() {
			return endpoints;
		}

		/** Stores the Position of this Edge wothin the graphs vertex list */
		public void setPosition(Position<Edge<E>> p) {
			position = p;
		}

		/** Return the Position of this edge within the graphs vertex List */
		public Position<Edge<E>> getPosition() {
			return position;
		}

		/** Sets the Position of the edges and its vertices */
		public void setIncident(Position<Edge<E>> edge1, Position<Edge<E>> edge2) {
			this.incident[0] = edge1;
			this.incident[1] = edge2;
		}

		/** Returns the position of the edges */
		public Position<Edge<E>> location() {
			return position;
		}

		public int compareTo(Edge<E> other) {
			if (this.getElement().equals(other.getElement()))
				return 1;
			else
				return 0;
		}

		/** A string representation of the edges */
		public String toString() {
			return "FROM -->" + endpoints[0].getFirst().element()
					+ " TO -->" + endpoints[1].getFirst().element()
					+ "  WEIGHT ==> " + getElement() + "\n";
		}
	}

	// end of egdes class
	// attributes of AdjacentList
	private LinkedList<Vertex<V>> vertices;
	private LinkedList<Edge<E>> edges;
	private Map<V, Vertex<V>> adjlist;

	/** Constructs an empty graph */
	public AdjacentList() {
		this.vertices = new LinkedList<Vertex<V>>();
		this.edges = new LinkedList<Edge<E>>();
		this.adjlist = new HashMap<V, Vertex<V>>();
	}

	/** Returns the number of vertices or vertex */
	public int numVertices() {
		return this.vertices.size();
	}

	/** Returns the number of Edges in the Graph list */
	public int numEdges() {
		return this.edges.size();
	}

	/** Returns the vertice as an Iterable Collection */
	public Iterable<Vertex<V>> vertices() {
		return this.vertices;
	}

	/** Return the Edges as an Iterable Coolection */
	public Iterable<Edge<E>> edges() {
		return this.edges;
	}

	/** Returns an Iterable collection of edges incident on a vertex */
	public Iterable<Edge<E>> incidentEdges(Vertex<V> vertex) {
		Vertex<V> v = checkVertex(vertex);
		return vertex.incidentEdges();
	}

	/** Returns an iterable collection of edges fro which vertex v is the origin */
	public Iterator<Edge<E>> outgoingEdges(Vertex<V> v) {
		Vertex<V> valid = validate(v);
		return valid.getOutgoing().iterator();
	}

	private Vertex<V> checkVertex(Vertex<V> v) {
		if (v == null || !(v instanceof Vertex))
			throw new IllegalArgumentException("Vertex is invalid!");

		return (Vertex<V>) v;
	}

	private Edge<E> checkEdge(Edge<E> edge) {
		if (edge == null || !(edge instanceof Edge))
			throw new IllegalArgumentException("Vertex is invalid!");

		return (Edge<E>) edge;
	}

	private Vertex<V> validate(Vertex<V> v) {
		if (v == null)
			throw new IllegalArgumentException("There Is No Vertex Object!");
		return v;
	}

	protected Iterator<Edge<E>> getEdge(Vertex<V> from, Vertex<V> to) {
		Vertex<V> origin = validate(from);
		 return  outgoingEdges(origin); // will be null if there is no
	}

	/** Create a Vertex Object */
	public V createVertex(V vertex) {
		Vertex<Object> vert = new Vertex<Object>(vertex);
		// System.out.println((E)vert.getElement());
		return (V) vert.getElement();
	}

	@SuppressWarnings("unchecked")
	private Vertex<V> createVertex2(V vertex) {
		Vertex<Object> vert = new Vertex<Object>(vertex);
		return (Vertex<V>) vert;
	}

	/**
	 * Inserts and return a new vertex with the given element
	 * 
	 * @throws InvalidKeyException
	 */
	public Vertex<V> insertVertex(V element) throws InvalidKeyException {
		// note the vertex object creates a new Hashmap
		Vertex<V> vertex = new Vertex<V>(element);
		if (adjlist.containsKey(element)) {
			return vertex;
		}
		vertices.add(vertex);
		adjlist.put(element, vertex);
		return vertex;
	}

	protected V replace(Vertex<V> vertex, V toSet) throws InvalidKeyException {
		V temp = vertex.element();
		Vertex<V> vert = checkVertex(vertex);
		Vertex<V> rep = createVertex2(toSet);
		Vertex<V> vv = checkVertex(rep);
		int count = 0;
		int save = 0;
		while (count < vertices.size()) {
			if (vertices.get(count).getElement().equals(temp)) {
				vertices.set(count, vv);
				save = count;
			}
			count++;
		}
		if (adjlist.containsValue(toSet))
			throw new InvalidKeyException(toSet + "already exist!!");

		adjlist.put(temp, vv); // i had to do this use the existing key to
		// insert the new vertex;
		adjlist.put(toSet, vv);
		return vv.getElement();
	}

	/**
	 * Replace the Vertex values with the identifier specified
	 * 
	 * @throws InvalidKeyException
	 */
	public V replaceVertex(V v, V o) throws InvalidKeyException {
		return replace(getVertex(v), o);
	}

	/** Replace an Edge by the specified element */
	@SuppressWarnings("unchecked")
	public E replace(Edge<E> edge, E o) {
		E temp = edge.getElement();
		Edge<E> check = checkEdge(edge);
		Position<Edge<E>>[] points = check.incidence();
		int index = 0;
		while (index < edges.size()) {
			if (edges.contains(check)) {
				points[0].setElement((Edge<E>) o);
				// points[1].setElement((Edge<E>) o);
			}
			index++;
		}
		return temp;
	}

	protected V removeVertex(Vertex<V> v) {
		Vertex<V> vertex = checkVertex(v);

		adjlist.remove(v.element());
		vertices.remove(vertex);
		return vertex.element();
	}

	protected void removeConnection(Vertex<V> from, Vertex<V> to) {
		Iterator<Edge<E>> itinc = incidentEdges(to).iterator();
		while (itinc.hasNext()) {
			Edge<E> edge = (Edge<E>) itinc.next();
			if (edge != null)
				removeEdge(edge);
		}
	}

	/** Removes a vertex and all of it incident edges */

	public void removeConnection(V from, V to) {
		removeConnection(getVertex(from), getVertex(to));
	}

	/** Removes a vertex */
	public V removeVertex(V v) {
		return removeVertex(getVertex(v));
	}

	/**
	 * Reomoves an egdes from the graph PLEASE NOTE THAT REMOVING A VERTEX ALSO
	 * REMOVES THE EDGE SO THERE WAS NO POINT OF RMOVING EDGE SEPERATELY
	 */
	public E removeEdge(Edge<E> edge) {
		LinkedList<Vertex<V>>[] end = edge.endVertice();
		Position<Edge<E>>[] inc = edge.incidence();
		end[0].remove(inc[0]);
		end[1].remove(inc[1]);
		@SuppressWarnings("unchecked")
		E toret = (E) edge.element();
		edges.remove(edge); // edges are contained in a linked list object
		return toret;
	}

	/** Returns the vertex of the assoaciated vertex key */
	public Vertex<V> getVertex(V vertex) {
		return adjlist.get(vertex);
	}

	/** Returns true iff the Vertex is in the graph */
	public boolean isVertex(V vertex) {
		return adjlist.containsKey(vertex);
	}

	protected int degree(Vertex<V> vertex) {
		Vertex<V> v = checkVertex(vertex);
		return v.degree();
	}

	protected int degree(V v) {
		return degree(getVertex(v));
	}

	/**
	 * Inserts and returns a new Edge between from and to , storing given
	 * elements
	 */
	protected Edge<E> insertEdges(Vertex<V> from, Vertex<V> to, E element) {
		// check the validity of the incoming edges
		// int index = 0;
		Vertex<V> destfrom = checkVertex(from);
		Vertex<V> destto = checkVertex(to);
		Edge<E> edge = new Edge<E>(destfrom, destto, element);
		edges.addLast(edge);// add the edge into a edge cointainer
		Position<Edge<E>> p1 = destfrom.insertIncidence(edge);
		Position<Edge<E>> p2 = destto.insertIncidence(edge);
		edge.setIncident(p1, p2);
		@SuppressWarnings("unchecked")
		Position<Edge<E>> get = (Position<Edge<E>>) edges.getLast();
		edge.setPosition(get);
		return edge;
	}

	/** Inserts edges with given vertex elements */
	public Edge<E> insertEdge(V from, V to, E weight) {
		return insertEdges(getVertex(from), getVertex(to), weight);
	}

	protected boolean Adjacent(Vertex<V> from, Vertex<V> to) {
		Iterable<Edge<E>> toSearch;
		// check to determine which vertex has the most outgoing links
		if (degree(from) < degree(to)) {
			toSearch = incidentEdges(from);
		} else {
			toSearch = incidentEdges(to);
		}
		for (Edge<E> e : toSearch) {
			LinkedList<Vertex<V>>[] check = e.endVertice();
			if ((check[0].getFirst().equals(from))
					&& (check[1].getFirst().equals(to))) {
				if ((check[0].getFirst().equals(to))
						&& (check[1].getFirst().equals(from))) {
					return true;
				}
				return true;
			}
		}
		return false;
	}

	/** Return the other end vertex of an incident edge */
	public Vertex<V> opposite(Vertex<V> vertex, Edge<E> edge) {
		checkVertex(vertex);
		Edge<E> check = checkEdge(edge);
		LinkedList<Vertex<V>>[] vertices = check.endVertice();
		if (vertex.equals(vertices[0].getFirst())) {
			return vertices[1].getFirst();
		} else if (vertex.equals(vertices[1].getFirst())) {
			return vertices[0].getFirst();
		} else {
			throw new IllegalArgumentException(
					"No such Vertex Exist in the Graph!!");
		}

	}

	/** Returns true iff the given Vertices are adjacent to one another */
	public boolean isAdjacent(V from, V to) {
		return Adjacent(getVertex(from), getVertex(to));
	}

	/** This method list all the connections a Vertex has */
	public void Connections(V v) {
		for (Vertex<V> vertex : vertices()) {
			if (vertex.element().equals(v)) {
				System.out.println("\nEdges Connected to " + vertex.element()
						+ ":");
				for (Edge<E> edge : incidentEdges(vertex)) {
					System.out.println(edge);
				}
			}
		}
	}

	/** Shows the edges in this graph */
	public void showEdges() {
		Iterator<Edge<E>> it = edges.iterator();
		if (it.hasNext() == false) {
			System.out.println("NO MORE CONNECTIONS!");
		}
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	public void showAll() {
		Iterator<Vertex<V>> it = vertices.iterator();
		while (it.hasNext()) {
			System.out.println("Vertex  : " + " ==> "
					+ it.next().getElement().toString());
		}
	}

	public void shortestPath(V from, V to) {
		PriorityQueue<Path> vq = new PriorityQueue<Path>();
		Vertex<V> v1 = getVertex(from);
		Vertex<V> v2 = getVertex(to);
		// do a check for vertex if null with getvertex method
		Path startPath = new Path(getVertex(from), 0);

		startPath.list = new LinkedList<Vertex<V>>();

		// startPath.list = new LinkedList
		int bestCost = 0;
		vq.add(startPath);
		LinkedList<Vertex<V>> travelled = new LinkedList<Vertex<V>>();
		while (!vq.isEmpty() && adjlist.size() > travelled.size()) {
			Path current = vq.poll();
			Vertex<V> cur = current.vertex;
			if (cur.equals(getVertex(to))) {
				//System.out.println("here 1");
				Vertex<V> start = this.getVertex(from);
				Iterator<Edge<E>> it = getEdge(v1 , v2);
				for (Vertex<V> v : current.list) {
						while(it.hasNext()){
							System.out.println(it.next().toString());
						}
					start = v;
				}
				System.out.println("The shortest path cost is ===>" + bestCost);
				
			}
			if (!travelled.contains(cur)) {
				// System.out.println("here 2");
				travelled.add(cur);
				for (Edge<E> edges : cur.getOutgoing()) {
					// System.out.println("here 3");
					LinkedList<Vertex<V>>[] dest = edges.endVertice();
					int i = 0;
					while (i < dest.length) {
						//System.out.println("the endpoints:  "
								//+ dest[1].getFirst().element());
						Vertex<V> destination = dest[1].getFirst();
						i++; //gets all destination
						Double ss = (Double) edges.getElement();
						//Double s = Double.parseDouble(ss);
						//System.out.println("wieght: " + s);

						int cost = (int) (current.cost + ss);
						bestCost = cost;
						@SuppressWarnings("unchecked")
						LinkedList<Vertex<V>> clone = (LinkedList<Vertex<V>>) current.list
								.clone();
						 clone.add(destination);

						Path n = new Path(destination, cost);
						 n.list = clone;
						 vq.add(n);
						i++;
					}
					//System.out.println("the current cost is : :" + bestCost);
				}//end of second for
			}//end of second if
		}//end of main while loop
		//System.out.println("The shortest path cost is ===>" + bestCost);
	}//end of function shortest path

}
