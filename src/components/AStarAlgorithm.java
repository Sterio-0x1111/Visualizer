/**
 * @version 0.1
 * @date 02.06.2024
 */
package components;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.List;

public class AStarAlgorithm extends PApplet {

    final int cols = 50, rows = 50;
    Node[][] grid = new Node[cols][rows];
    List<Node> openSet = new ArrayList<>();
    List<Node> closedSet = new ArrayList<>();
    Node start;
    Node end;
    float w, h;

    public void settings() {
	size(800, 800);
    }

    public void setup() {
	w = width / cols;
	h = height / rows;

	for (int i = 0; i < cols; i++) {
	    for (int j = 0; j < rows; j++) {
		grid[i][j] = new Node(i, j);
	    }
	}

	start = grid[0][0];
	end = grid[cols - 1][rows - 1];
	openSet.add(start);

	for (Node[] array : grid) {
	    for (Node n : array) {
		n.addNeighbors(grid, cols, rows);
	    }
	}
    }

    public void draw() {
	background(0);
	Node current = null;

	if (!openSet.isEmpty()) {
	    int lowestIndex = 0;
	    for (int i = 0; i < openSet.size(); i++) {
		if (openSet.get(i).f < openSet.get(lowestIndex).f) {
		    lowestIndex = i;
		}
	    }

	    current = openSet.get(lowestIndex);
	    if (current == end) {
		noLoop();
		System.out.println("Done!");
	    }

	    openSet.remove(current);
	    closedSet.add(current);

	    for (Node neighbor : current.neighbors) {
		if (!closedSet.contains(neighbor) && !neighbor.wall) {
		    float tempG = current.g + 1;
		    boolean newPath = false;
		    if (openSet.contains(neighbor)) {
			if (tempG < neighbor.g) {
			    neighbor.g = tempG;
			    newPath = true;
			}
		    } else {
			neighbor.g = tempG;
			newPath = true;
			openSet.add(neighbor);
		    }
		    if (newPath) {
			neighbor.h = heuristic(neighbor, end);
			neighbor.f = neighbor.g + neighbor.h;
			neighbor.previous = current;
		    }
		}
	    }
	} else {
	    noLoop();
	    System.out.println("No solution");
	    return;
	}

	for (Node[] array : grid) {
	    for (Node n : array) {
		n.show(color(255), w, h);
		if (closedSet.contains(n)) {
		    n.show(color(255, 0, 0), w, h);
		}
		if (openSet.contains(n)) {
		    n.show(color(0, 255, 0), w, h);
		}
	    }
	}

	if (current != null) {
	    while (current.previous != null) {
		current.showPath(color(0, 0, 255), w, h);
		current = current.previous;
	    }
	}
    }

    public float heuristic(Node a, Node b) {
	return dist(a.i, a.j, b.i, b.j);
    }

    public class Node {
	int i, j;
	float f, g, h;
	ArrayList<Node> neighbors;
	Node previous;
	boolean wall = false;

	Node(int i, int j) {
	    this.i = i;
	    this.j = j;
	    neighbors = new ArrayList<>();
	    wall = (random(1) < 0.3);
	}

	void addNeighbors(Node[][] grid, int cols, int rows) {
	    if (i < cols - 1)
		neighbors.add(grid[i + 1][j]);
	    if (i > 0)
		neighbors.add(grid[i - 1][j]);
	    if (j < rows - 1)
		neighbors.add(grid[i][j + 1]);
	    if (j > 0)
		neighbors.add(grid[i][j - 1]);
	}

	void show(int color, float w, float h) {
	    fill(color);
	    if (wall)
		fill(0);
	    noStroke();
	    rect(i * w, j * h, w - 1, h - 1);
	}

	void showPath(int color, float w, float h) {
	    fill(color);
	    noStroke();
	    ellipse(i * w + w / 2, j * h + h / 2, w / 2, h / 2);
	}
    }
}