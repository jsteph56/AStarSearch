import java.util.*;

public class AStarSearch {
   Node board[][] = new Node[15][15];
   Node startNode;
   Node endNode;
   Node currNode = this.startNode;
   PriorityQueue<Node> openList = new PriorityQueue<>();
   HashMap<Node, Node> closedList = new HashMap<>();
   
   public static void main(String args[]) {
        AStarSearch run = new AStarSearch();
        System.out.println(run.toString());
        run.getBeginAndEnd();
   }
   
   public AStarSearch () {
        Random rand = new Random();  

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int type = rand.nextInt(10);

                if (type == 0) {
                    board[i][j] = new Node(i, j, 1);
                } else {
                    board[i][j] = new Node(i, j, 0);
                }
            }
        }


   }

   public void getStartAndEndPos() {
        Scanner scnr = new Scanner(System.in);
        int startRow = 0;
        int startCol = 0;
        int endRow = 0;
        int endCol = 0;

        System.out.println("Input row to start at (0 - 14): ");
        System.out.println("CHOSEN NODE MUST NOT BE UNPATHABLE, I.E SELECT A POSITION THAT DISPLAYS A 0");
        startRow = scnr.nextInt();
        System.out.println("Input column to start at (0 - 14): ");
        System.out.println("CHOSEN NODE MUST NOT BE UNPATHABLE, I.E SELECT A POSITION THAT DISPLAYS A 0");
        startCol = scnr.nextInt();
        this.startNode = this.board[startRow][startCol];
        
        System.out.println("Input row to end at (0 - 14): ");
        System.out.println("CHOSEN NODE MUST NOT BE UNPATHABLE, I.E SELECT A POSITION THAT DISPLAYS A 0");
        endRow = scnr.nextInt();
        System.out.println("Input col to end at (0 - 14): ");
        System.out.println("CHOSEN NODE MUST NOT BE UNPATHABLE, I.E SELECT A POSITION THAT DISPLAYS A 0");
        endCol = scnr.nextInt();
        this.endNode = this.board[endRow][endCol];
        
        
        checkPathable();
        this.startNode.setG(0);
        this.startNode.setH(findH(this.startNode));
        this.startNode.setF();
        this.openList.add(this.startNode);
        
        scnr.close();
        searching();
   }

   public void searching() {
        this.currNode = this.startNode;
        
        while(!this.openList.isEmpty()) {
            ArrayList<Node> currNeighbors;

            this.currNode = openList.poll();
            closedList.put(this.currNode, this.currNode.getParent());

            if (currNode.equals(this.endNode)) {
                System.out.println("Path found!");
                generatePath();
                break;
            }

            currNeighbors = generateNeighbors(currNode);
            updateNeighborValues(currNeighbors);
        }
        System.out.println("Path could not be found!");
   }

   public int findH(Node o) {
        return (Math.abs(o.getRow() - this.endNode.getRow()) + Math.abs(o.getCol() - this.endNode.getCol())) * 10;
    }

    public int findG(Node o) {
        Node parent = o.getParent();
        int g = 0;

        if (parent.getCol() > o.getCol() ||parent.getCol() < o.getCol()) {
            if (parent.getRow() < o.getRow() || parent.getRow() > o.getRow()) {
                g = parent.getG() + 14;
            } else {
                g = parent.getG() + 10;
            }
        }

        return g;
    }

    public void generatePath() {
        ArrayList<Node> path = new ArrayList<>();
        Node temp = this.endNode;

        while (!temp.equals(this.startNode)) {
            path.add(temp);
            //System.out.println(temp.toString());
            temp = temp.getParent();
        }
        path.add(this.startNode);

        System.out.println("Available path: ");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(path.get(i).toString());
            //System.out.print(", Node's F: " + path.get(i).getF());

            if (!path.get(i).equals(this.endNode)) {
                System.out.print(" -> ");
            }
        }
        System.exit(0);
    }

    public ArrayList<Node> generateNeighbors(Node o) {
        ArrayList<Node> neighbors = new ArrayList<>();
        Node neigh1, neigh2, neigh3, neigh4, neigh5, neigh6, neigh7, neigh8;

        if (o.getCol() == 0) {
            if(o.getRow() == 14) {
                neigh1 = this.board[o.getRow() - 1][o.getCol()];
                neigh2 = this.board[o.getRow() - 1][o.getCol() + 1];
                neigh3 = this.board[o.getRow()][o.getCol() + 1];

                neighbors.add(neigh1);
                neighbors.add(neigh2);
                neighbors.add(neigh3);
            } else if (o.getRow() == 0) {
                neigh1 = this.board[o.getRow() + 1][o.getCol()];
                neigh2 = this.board[o.getRow() + 1][o.getCol() + 1];
                neigh3 = this.board[o.getRow()][o.getCol() + 1];
                
                neighbors.add(neigh1);
                neighbors.add(neigh2);
                neighbors.add(neigh3);
            } else {
                neigh1 = this.board[o.getRow() + 1][o.getCol()];
                neigh2 = this.board[o.getRow() + 1][o.getCol() + 1];
                neigh3 = this.board[o.getRow() - 1][o.getCol()];
                neigh4 = this.board[o.getRow() - 1][o.getCol() + 1];
                neigh5 = this.board[o.getRow()][o.getCol() + 1];

                neighbors.add(neigh1);
                neighbors.add(neigh2);
                neighbors.add(neigh3);
                neighbors.add(neigh4);
                neighbors.add(neigh5);
            }
        } else if (o.getCol() == 14) {
            if(o.getRow() == 14) {
                neigh1 = this.board[o.getRow() - 1][o.getCol()];
                neigh2 = this.board[o.getRow() - 1][o.getCol() - 1];
                neigh3 = this.board[o.getRow()][o.getCol() - 1];

                neighbors.add(neigh1);
                neighbors.add(neigh2);
                neighbors.add(neigh3);
            } else if (o.getRow() == 0) {
                neigh1 = this.board[o.getRow() + 1][o.getCol()];
                neigh2 = this.board[o.getRow() + 1][o.getCol() - 1];
                neigh3 = this.board[o.getRow()][o.getCol() - 1];

                neighbors.add(neigh1);
                neighbors.add(neigh2);
                neighbors.add(neigh3);
            } else {
                neigh1 = this.board[o.getRow() + 1][o.getCol()];
                neigh2 = this.board[o.getRow() + 1][o.getCol() - 1];
                neigh3 = this.board[o.getRow() - 1][o.getCol()];
                neigh4 = this.board[o.getRow() - 1][o.getCol() - 1];
                neigh5 = this.board[o.getRow()][o.getCol() - 1];

                neighbors.add(neigh1);
                neighbors.add(neigh2);
                neighbors.add(neigh3);
                neighbors.add(neigh4);
                neighbors.add(neigh5);
            }
        } else if (o.getRow() == 0) {
            neigh1 = this.board[o.getRow() + 1][o.getCol()];
            neigh2 = this.board[o.getRow() + 1][o.getCol() + 1];
            neigh3 = this.board[o.getRow() + 1][o.getCol() - 1];
            neigh4 = this.board[o.getRow()][o.getCol() + 1];
            neigh5 = this.board[o.getRow()][o.getCol() - 1];

            neighbors.add(neigh1);
            neighbors.add(neigh2);
            neighbors.add(neigh3);
            neighbors.add(neigh4);
            neighbors.add(neigh5);
        } else if (o.getRow() == 14) {
            neigh1 = this.board[o.getRow() - 1][o.getCol()];
            neigh2 = this.board[o.getRow() - 1][o.getCol() + 1];
            neigh3 = this.board[o.getRow() - 1][o.getCol() - 1];
            neigh4 = this.board[o.getRow()][o.getCol() + 1];
            neigh5 = this.board[o.getRow()][o.getCol() - 1];

            neighbors.add(neigh1);
            neighbors.add(neigh2);
            neighbors.add(neigh3);
            neighbors.add(neigh4);
            neighbors.add(neigh5);
        } else {
            neigh1 = this.board[o.getRow() + 1][o.getCol()];
            neigh2 = this.board[o.getRow() + 1][o.getCol() + 1];
            neigh3 = this.board[o.getRow() + 1][o.getCol() - 1];
            neigh4 = this.board[o.getRow() - 1][o.getCol()];
            neigh5 = this.board[o.getRow() - 1][o.getCol() + 1];
            neigh6 = this.board[o.getRow() - 1][o.getCol() - 1];
            neigh7 = this.board[o.getRow()][o.getCol() + 1];
            neigh8 = this.board[o.getRow()][o.getCol() - 1];

            neighbors.add(neigh1);
            neighbors.add(neigh2);
            neighbors.add(neigh3);
            neighbors.add(neigh4);
            neighbors.add(neigh5);
            neighbors.add(neigh6);
            neighbors.add(neigh6);
            neighbors.add(neigh7);
            neighbors.add(neigh8);
        }

        for (int i = 0; i < neighbors.size(); i++) {
            if (openList.contains(neighbors.get(i))) {
                neighbors.remove(i);
            }
        }

        return neighbors;
    }

    public void updateNeighborValues(ArrayList<Node> list) {
        Node temp;
        
        for (int i = 0; i < list.size(); i++) {
            temp = list.get(i);

            if (temp.getType() != 1 && !closedList.containsKey(temp)) {
                temp.setParent(this.currNode);
                temp.setG(findG(temp));
                temp.setH(findH(temp));
                temp.setF();

                openList.add(temp);
            }
        }
    }

    public void checkPathable() {
        Scanner scnr = new Scanner(System.in);
        int row;
        int column;

        if (this.startNode.getType() == 1) {
            System.out.println("Given StartNode was unpathable.");
            System.out.println("Please select a new starting row (0 - 14): ");
            row = scnr.nextInt();
            System.out.println("Please select a new starting column (0 - 14): ");
            column = scnr.nextInt();
            
            this.startNode = this.board[row][column];
            checkPathable();
        }

        if (this.endNode.getType() == 1) {
            System.out.println("Given EndNode was unpathable.");
            System.out.println("Please select a new ending row (0 - 14): ");
            row = scnr.nextInt();
            System.out.println("Please select a new ending column (0 - 14): ");
            column = scnr.nextInt();

            this.endNode = this.board[row][column];
            checkPathable();
        }

        scnr.close();
    }

   @Override
   public String toString() {
        String s = "";
    
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                s += " " + board[i][j].getType();
            }
            s += "\r\n";
        }

        return s;
   }
}
