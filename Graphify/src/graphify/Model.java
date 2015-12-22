package graphify;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Jack
 */
public class Model {
    public static HashMap<Integer, Integer> connectionCache = new HashMap<>();
    public static Queue<Vertex> queue;
    public static Stack<Vertex> stack;
    public static String sim = null;
    public static HashMap<Integer, Integer> distTo;
    public static HashMap<Integer, Integer> visited;
    public static HashMap<Integer, Integer> color;
    public static HashMap<Integer, Integer> fcolors;
    public static HashMap<Integer, Integer> greedyresult;
    public static HashSet<Integer> _colors2;
    public static HashSet<Integer> randomKeys;
    public static ArrayList<Edge> glowMap;
    public static ArrayList<Integer> cutV;
    public static ArrayList<Vertex> failed;
    public static Color[] vertexColors;
    public static int _selectedNode = -1;
    public static final int ARR_SIZE = 8;
    public static int _SIZE_OF_NODE = 20;
    public static int id = 0;
    public static int Edgeid = 0;
    public static int weight = 0;
    public static Integer maxColors = 0;
    public static int _source = -1;
    public static int _dest = -1;
    public static String currentProject = null;
    public static boolean changesMade = false;
    public static boolean algCalled = false;
    public static double dotOffset = 0.0;
    public static Algorithms alg;
    public static int pChangeTime = 300;
    public static int time = 0;
    public static int toolType = -1;
    public static HashMap<Integer, Vertex> vertices;
    public static ArrayList<Edge> edges;
    public static GraphifyGUI GG;
    public static Queue<Vertex> suggestQueue;
    public static HashMap<Integer, HashSet<Integer>> nodes;
    public static HashMap<Integer, Integer> setShortestPath = new HashMap<>();
    public static Map<Integer, Integer> set;
    public static ArrayList<Integer> conn;
    public static HashSet<Vertex> bconn;
    //for dijkstra
    public static HashSet<Vertex> uSNodes; // unsettled
    public static HashSet<Vertex> sNodes; // settled
    public static HashMap<Vertex, Integer> dist; // distance

    public static GraphifyGUI graph;

    public static final int TOOL_NONE = -1;
    public static final int TOOL_VERTEX = 0;
    public static final int TOOL_BIDIRECTIONAL = 1;
    public static final int TOOL_DIRECTIONAL = 2;
}
