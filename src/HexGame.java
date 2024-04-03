import java.util.ArrayList;
import java.util.Objects;

public class HexGame {
    private String[] grid;
    public HexGame(int sideLength){
        this.size = sideLength * sideLength;
        grid = new String[size+4];

        for (int i=0; i < grid.length; i++){
            grid[i]="0";
        }

        this.blueGame = new DisjointSet(size+4);
        for (int i=0; i< this.size; i += sideLength){
            blueGame.union(124-1, i);
            blueGame.union(123-1, i+sideLength-1);
        }
        this.redGame = new DisjointSet(size+4);
        for (int i=0; i< sideLength; i ++){
            redGame.union(122-1, i);
            redGame.union(125-1, i+110);
        }
    }
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    private int size;
    private DisjointSet blueGame;
    private DisjointSet redGame;

    //Edges: Top: 122; Right: 123; Left: 124; Bottom: 125;
    private int TOP_EDGE = size+1;
    private int RIGHT_EDGE = size+2;
    private int LEFT_EDGE = size+3;
    private int BOTTOM_EDGE = size+4;
    public boolean playBlue(int position, boolean displayNeighbors){
        if (displayNeighbors){
            System.out.println(displayNeighbors(position, "blue"));
        }
        if (isOccupied(position)){
            return false;
        }
        else {
            grid[position-1] = "B";
        }
        //fix this function
        if (blueGame.find(LEFT_EDGE)== blueGame.find(RIGHT_EDGE)){
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
        }
        if (redGame.find(TOP_EDGE)== redGame.find(BOTTOM_EDGE)){
            return true;
        }
        return false;
    }

    private String displayNeighbors(int position, String color){
        String neighbors = "";
        ArrayList<Integer> colorNeighbors = new ArrayList<>();

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
