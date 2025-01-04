import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy {

    private int        calcHeuristic(Point start, Point end){
        return Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
    }

    public List<Point> computePath(Point start,
                                   Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        int g;              // Distance from start
        int h;              // Heuristic distance
        int f;              // Total distance. f = g + h

        List<Point> path = new LinkedList<Point>();
        Map<Point, CurNode> openList = new LinkedHashMap<Point, CurNode>();
//        List<CurNode> openList = new LinkedList<>();
        List<Point> closedList = new LinkedList<Point>();
        List<Point> neighbors = new LinkedList<Point>();
        CurNode currentNode;

        // Set distance values
        g = 0;                          // Initial distance from start is 0
        h = calcHeuristic(start, end);  // Initial Heuristic distance
        f = g + h;                      // Initail Total distance. f = g + h

        // Create initial node
        // Add initial node and start position to open list
        currentNode = new CurNode(g, h, f, null, start);
//        openList.add(currentNode);
        openList.put(start, currentNode);

        // Begin A*
        while(!openList.isEmpty()){     // Keep checking until all open nodes have been checked

            currentNode = openList
                    .values()
                    .stream()
                    .sorted(Comparator.comparingInt(CurNode::getF))
                    .collect(Collectors.toList()).iterator().next();
            openList.remove(currentNode.nodePosition);
//            currentNode = openList.stream().sorted(Comparator.comparing(CurNode::getF)).collect(Collectors.toList()).get(0);
//            openList.remove(currentNode);

            if (withinReach.test(currentNode.nodePosition, end)){       // Bipredicate returns true if end node is reached
                writePath(path, currentNode);
                break;
            }

            closedList.add(currentNode.nodePosition);   // If Node is not at Goal then add to searched/closed list

            // Add valid neighboring nodes
            neighbors = potentialNeighbors
                    .apply(currentNode.nodePosition)            // Returns stream of cardinal points around currentNode
                    .filter(canPassThrough)                     // Remove points that are Obstacles. ?And entities?
                    .filter(pt -> !closedList.contains(pt))     // Remove points in closed list
                    .collect(Collectors.toList());              // Add remaining points

            // Loop through each neighbor point and create a nodes with g, h, and f values
            for (Point neighbor : neighbors){

                if (openList.containsKey(neighbor)){
//                if (!openList
//                        .stream()
//                        .map(CurNode::getNodePosition)
//                        .filter(p -> p.equals(neighbor))
//                        .collect(Collectors.toList())
//                        .isEmpty()){
                    continue;
                }

                g = currentNode.g + 1;              // Increment Distance from start using current node's g value
                h = calcHeuristic(neighbor, end);   // Calculate new Heuristic distance from neighbor position
                f = g + h;                          // Calculate new Total distance for neighbor node

                // Create neighbor node
                // Add neighbor node to openList
                CurNode neighborNode = new CurNode(g, h, f, currentNode, neighbor);
                openList.put(neighbor, neighborNode);
//                openList.add(neighborNode);
            }
        }

        return path;
    }

    private void       writePath(List<Point> path, CurNode currentNode){
        if (currentNode.priorNode == null){               // Already at the goal. Prior node will be null.
            return;
        }

        path.add(0, currentNode.nodePosition);      // Reached Goal

        if (currentNode.priorNode.priorNode != null){     // Recursive writePath call till
            writePath(path, currentNode.priorNode);
        }
        return;
    }

//-------------------------------------------LOCAL CLASS------------------------------------------------------
    private class CurNode {
        private int g;              // Node's distance from start
        private int h;              // Node's Heuristic distance. Manhattan distance
        private int f;              // Node's Total distance. f = g + h
        private CurNode priorNode;  // Previously analyzed node
        private Point nodePosition; // Current node to be analyzed

        public CurNode(int g, int h, int f, CurNode priorNode, Point nodePosition){
            this.g = g;
            this.h = h;
            this.f = f;
            this.priorNode = priorNode;
            this.nodePosition = nodePosition;
        }

        public int getF() {
            return f;
        }

        public Point getNodePosition() {
            return nodePosition;
        }
    }
}

// 1.Choose/know starting and ending points of the path
// 2.Add start node to the open list and mark it as the current node
// 3.Analyze all valid adjacent nodes that are not on the closed list
//     a.Add to Open List if not already in it
//     b.Determine distance from start node (g value)
//         i.g = Distance of current node from start + distance from current node to adjacentnode
//     c.If the calculated g value is better than a previously calculated g value, replace the old gvalue with the new one and:
//         i.If necessary, estimate distance of adjacent node to the end point (h)
//             1.This is called the heuristic.  It can be Euclidean distance, Manhattandistance, etc.
//         ii.Add g and h to get an f value
//         iii.Mark the adjacent node’s prior vertex as the current node
// 4.Move the current node to the closed list
// 5.Choose a node from the open list with the smallest f value and make it the current node
// 6.Go to step 3
// 7.Repeat until a path to the end is found.
