import java.util.ArrayList;
import java.util.Objects;

public class HexGame {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    private int size;
    private DisjointSet blueGame;
    private DisjointSet redGame;

    private int TOP_EDGE = 122;
    private int RIGHT_EDGE = 125;
    private int LEFT_EDGE = 124;
    private int BOTTOM_EDGE = 123;

    private String[] grid;
    public HexGame(int sideLength){
        this.size = sideLength * sideLength;
        grid = new String[size+4];

        for (int i=0; i < grid.length; i++){
            grid[i]="0";
        }
        this.blueGame = new DisjointSet(size+4);
        this.redGame = new DisjointSet(size+4);
    }

    public boolean playBlue(int position, boolean displayNeighbors){
        if (displayNeighbors){
            System.out.println(displayNeighbors(position, "blue"));
        }
        if (isOccupied(position)){
            return false;
        }
        else {
            grid[position-1] = "B";

            ArrayList<Integer> neighbors = getNeighborsBlue(position);
            for (int item: neighbors){
                if (Objects.equals(grid[item-1], "B")){
                    blueGame.union(position-1, item-1);
                }else if (item > 121){
                    blueGame.union(position-1, item-1);
                }
            }
        }
        if (blueGame.find(LEFT_EDGE-1)== blueGame.find(RIGHT_EDGE-1)){
            System.out.print("Blue wins with move at position " + position + "!!");
            return true;
        }
        return false;
    }

    public boolean playRed(int position, boolean displayNeighbors){
        if (displayNeighbors){
            System.out.println(displayNeighbors(position, "red"));
        }
        if (isOccupied(position)){
            return false;
        }
        else {
            grid[position-1] = "R";
            ArrayList<Integer> neighbors = getNeighborsRed(position);

            for (int item: neighbors){
                if (Objects.equals(grid[item-1], "R")){
                    redGame.union(position-1, item-1);
                }else if (item > 121){
                    redGame.union(position-1, item-1);
                }
            }
        }
        if (redGame.find(TOP_EDGE-1)== redGame.find(BOTTOM_EDGE-1)){
            System.out.print("Red wins with move at position " + position + "!!");
            return true;
        }
        return false;
    }

    private String displayNeighbors(int position, String color){
        String neighbors = "";
        ArrayList<Integer> colorNeighbors;

        if (Objects.equals(color, "red")){
            colorNeighbors = getNeighborsRed(position);
        }
        else if (Objects.equals(color, "blue")){
            colorNeighbors = getNeighborsBlue(position);
        }
        else return "Error with displayNeighbors";

        neighbors += "Cell "+position+": "+ colorNeighbors;

        return neighbors;
    }

    public Color[] getGrid(){
        Color[] coloredGrid = new Color[grid.length];
        int index = 0;
        for (String hex : grid ){
            Color coloredHex = new Color(hex);
            coloredGrid[index] = coloredHex;
            index++;
        }
        return coloredGrid;
    }

    public static class Color{
        private String color;
        private Color(String identifier){
            if (identifier == "B"){
                this.color = ANSI_BLUE + "B" + ANSI_RESET;
            } else if (identifier == "R"){
                this.color = ANSI_RED + "R" + ANSI_RESET;
            } else {
                this.color = "0";
            }
        }
        public String getColor(){
            return this.color;
        }
    }

    public int getSize(){
        return this.size;
    }

    private boolean isOccupied(int position){
        return !Objects.equals(grid[position - 1], "0");
    }

    private ArrayList<Integer> getNeighborsRed(int position){
        ArrayList<Integer> neighbors = new ArrayList<>();
        int column = position % 11;
        int row = Math.ceilDiv(position, 11);

        if (column != 0){
            neighbors.add(position+1);
        }
        if (column != 1){
            neighbors.add(position-1);
        }
        if (row != 1){
            if (column != 0) {
                neighbors.add(position - 10);
            }
            neighbors.add(position -11);
        } else {
            neighbors.add(122);
        }
        if (row != 11){
            if (column != 1) {
                neighbors.add(position + 10);
            }
            neighbors.add(position + 11);
        } else {
            neighbors.add(123);
        }
        return neighbors;
    }

    private ArrayList<Integer> getNeighborsBlue(int position){
        ArrayList<Integer> neighbors = new ArrayList<>();
        int column = position % 11;
        int row = Math.ceilDiv(position, 11);

        if (column != 0){
            neighbors.add(position+1);
        } else {neighbors.add(125);}
        if (column != 1){
            neighbors.add(position-1);
        }else {neighbors.add(124);}
        if (row != 1){
            neighbors.add(position -11);
            if (column!= 0){
                neighbors.add(position - 10);
            }
        }
        if (row != 11){
            neighbors.add(position + 11);
            if (column != 1){
                neighbors.add(position + 10);
            }
        }
        return neighbors;
    }
}
