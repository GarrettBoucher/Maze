
//Program:	Maze.java
//Course:	COSC470
//Description:	Program for finding the shortest path solution to a maze.  
//Author:	Garrett Boucher 
//Revised:	2/9/15
//Language:	Java
//IDE:		NetBeans 8.0.2

//*******************************************************************************
//*******************************************************************************

//Class:	Maze
//Description:	Reads a maze as a text file, and outputs the shortest solution path




public class Maze {
    
    public static int shortestPath;
    public static int[][]shortMaze;//the maze with the shortest path that will ultimately be printed
    public static int[][] maze;//the maze to be traversed
    public static int currentRow;
    public static int currentColumn;
    public static int spacesVisited;
    

     //*****************************************************************************************************************
	//Method:       main
	//Description:	Prompts the user to enter the name of a file containing a maze, then puts the maze in a two 
        //              dimensional int array. It then asks if the user wants to see the steps of the maze, and either
        //              calls traverseMazeAuto or solveMaze
        //              
	//Parameters:	none
	//Returns:	nothing
	//Throws:	nothing
	//Calls:		traverseMazeAuto, printMaze
    public static void main(String[] args) {
        TextFileClass textFile = new TextFileClass();
        textFile.getFileName("Specify the text file to be read"); 
        
        textFile.getFileContents();
        
        int rowsM;
        int columnsN;
        
        int numberOfMoves = -1;
        
        rowsM = Integer.parseInt(textFile.text[0]);
        columnsN = Integer.parseInt(textFile.text[1]);
        currentRow = Integer.parseInt(textFile.text[2]);
        currentColumn = Integer.parseInt(textFile.text[3]);
        
        
        maze = new int[rowsM][columnsN];
        shortMaze = new int[rowsM][columnsN];
        
        for (int i = 4; i < rowsM + 4; i++) {
            int digit;
            for (int j = 0; j < columnsN; j++) {
                digit = Integer.parseInt(textFile.text[i].substring(0+j, 1+j));
                maze[i-4][j]=digit;
            }  
        }
        
        
        System.out.println("");

        shortestPath = (rowsM * columnsN)+1;
        
        KeyboardInputClass keyboardInput = new KeyboardInputClass();
        String userInput="";
        userInput = keyboardInput.getString("", "Do you want to see the steps? (y/n): ");
        
        if(userInput.equals("y")){
            userInput = keyboardInput.getString("", "Automatically (y) or when ENTER pressed (n)?");
            if(userInput.equals("y")){
            traverseMazeAuto(currentRow, currentColumn, numberOfMoves);
            }else if(userInput.equals("n")){
                traverseMazeEnter(currentRow, currentColumn, numberOfMoves);
            }
        }else if(userInput.equals("n")){
            solveMaze(currentRow, currentColumn, numberOfMoves);
        }else {System.out.println("Please enter y or n");}

        
        printMaze(shortMaze, currentRow, currentColumn);
        System.out.println("Shortest path is " + shortestPath + " moves from start position");
        System.out.println(spacesVisited + " spaces were visited in total");
        
        
        
        
    }
      //*****************************************************************************************************************
	//Method:       loadArray
	//Description:	Loads shortMaze[][] with the contents of maze[][]
	//Parameters:	maze-   the 2 dimensional array holding the elements of the maze
	//Returns:	nothing
	//Throws:	nothing
	//Calls:		nothing
    public static void loadArray(int[][]maze){
         for (int row = 0; row < maze.length; row++) {
            for (int column = 0; column < maze[row].length; column++) {

                shortMaze[row][column] = maze[row][column];
                

            }

        }
    }
   
    
    //*****************************************************************************************************************
	//Method:       printMaze
	//Description:	Prints the current maze in a 2 dimensional array
	//Parameters:	maze-   the 2 dimensional array holding the elements of the maze
        //              currentRow-
        //              currentColumn
	//Returns:	nothing
	//Throws:	nothing
	//Calls:		nothing
    public static void printMaze(int[][] maze, int currentRow, int currentColumn) {
        for (int row = 0; row < maze.length; row++) {
            for (int column = 0; column < maze[row].length; column++) {

                if (checkAllDirections(maze, currentRow, currentColumn) == 3) {
                    maze[currentRow][currentColumn] = 3;
                }

                if (maze[row][column] == 0) {
                    System.out.print((char) 32);
                }
                if (maze[row][column] == 1) {
                    System.out.print((char) 219);
                }
                if (maze[row][column] == 2 || maze[row][column] == 3) {

                    System.out.print((char) 42);
                }

            }
            System.out.println("");

        }

    }
     //*****************************************************************************************************************
	//Method:       checkNorth
	//Description:	Checks the value of the element "north" of the current location in the 2 dimensional array
	//Parameters:	maze-   the 2 dimensional array holding the elements of the maze
        //              currentRow
        //              currentColumn
	//Returns:	int; either 3 representing an exit, or the value of the square to the north
	//Throws:	nothing
	//Calls:		nothing
    public static int checkNorth(int [][]maze, int currentRow, int currentColumn){
        if(currentRow == 0){
            return 3;
        }
        else return (maze[currentRow-1][currentColumn]);
    }
    
    //*****************************************************************************************************************
	//Method:       checkEast
	//Description:	Checks the value of the element "east" of the current location in the 2 dimensional array
	//Parameters:	maze-   the 2 dimensional array holding the elements of the maze
        //              currentRow
        //              currentColumn
	//Returns:	int; either 3 representing an exit, or the value of the square to the north
	//Throws:	nothing
	//Calls:		nothing
    public static int checkEast(int [][]maze, int currentRow, int currentColumn){
        if(currentColumn == maze[currentRow].length -1){
            return 3;
        }
        else return (maze[currentRow][currentColumn+1]);

    }
    
    //*****************************************************************************************************************
	//Method:       checkSouth
	//Description:	Checks the value of the element "south" of the current location in the 2 dimensional array
	//Parameters:	maze-   the 2 dimensional array holding the elements of the maze
        //              currentRow
        //              currentColumn
	//Returns:	int; either 3 representing an exit, or the value of the square to the north
	//Throws:	nothing
	//Calls:		nothing
    public static int checkSouth(int [][]maze, int currentRow, int currentColumn){
        if(currentRow == maze.length -1){
            return 3;
        }
        else return (maze[currentRow+1][currentColumn]);

    }
    
    //*****************************************************************************************************************
	//Method:       checkWest
	//Description:	Checks the value of the element "west" of the current location in the 2 dimensional array
	//Parameters:	maze-   the 2 dimensional array holding the elements of the maze
        //              currentRow
        //              currentColumn
	//Returns:	int; either 3 representing an exit, or the value of the square to the north
	//Throws:	nothing
	//Calls:		nothing
    public static int checkWest(int [][]maze, int currentRow, int currentColumn){
        if(currentColumn == 0 ){//&& maze[currentRow][currentColumn-1] < columnsN)
            return 3;
        }
        else return (maze[currentRow][currentColumn-1]);

    }
    
    //*****************************************************************************************************************
	//Method:       traverseMazeAuto
	//Description:	Recursively traverses the maze one square at a time. The algorithm progresses as follows:  
        //              First, it checks to see if the current position is in bounds, and returns if it is not.
        //              Then, it checks to see if the current position is a wall or has already been visited, and will 
        //                  return if so.
        //              The current position is then marked as visited.
        //              The maze is printed.
        //              The number of moves is incremented.
        //              The program checks if the number of moves is longer than shortestPath and returns if so.
        //              The program checks if the current location is an exit.  If the number of moves is less than
        //                   shortestPath, shortestPath is set to numberOfMoves.
        //                   The maze is printed.
        //                   loadArray is called to set shortMaze[][] equal to the current state of maze[][]
        //              traverseMazeAuto to the north is called; 
        //              traverseMazeAuto to the east is called; 
        //              traverseMazeAuto to the south is called; 
        //              traverseMazeAuto to the west is called;
        //              The current space is vacated (set to 0)
        //              
	//Parameters:	maze-   the 2 dimensional array holding the elements of the maze
        //              currentRow
        //              currentColumn
        //              numberOfMoves - the number of moves from the starting point, not including the starting point
        //
	//Returns:	nothing
	//Throws:	nothing
	//Calls:		printMaze, traverseMazeAuto, loadArray
    
public static void traverseMazeAuto(int currentRow, int currentColumn, int numberOfMoves){
     if (currentRow < 0 || currentRow > maze.length - 1 
                || currentColumn < 0 || currentColumn > maze[currentRow].length - 1) {
            return;//check bounds
        }
    if (maze[currentRow][currentColumn] == 1 || maze[currentRow][currentColumn] == 2) {
            return;//check if wall or already visited
        }
   
                maze[currentRow][currentColumn] = 2;
                spacesVisited++;
                printMaze(maze, currentRow, currentColumn);
                numberOfMoves++;
                
                
                
                
                if (numberOfMoves > shortestPath) {//don't keep going if it's already too long
                maze[currentRow][currentColumn] = 0;
                    System.out.println("^this path ended because it was already too long");
                    System.out.println("");
                return;
                 }
                
                System.out.println("");
                
                if(checkAllDirections(maze, currentRow, currentColumn) == 3){//check if exit
                    if(numberOfMoves <= shortestPath) {//Set new shortest path
                        shortestPath = numberOfMoves;
                        System.out.println("////////Here is a path that took " + numberOfMoves + " moves:");
                        printMaze(maze, currentRow, currentColumn);
                        System.out.println("");
                        loadArray(maze);
                       
                    }
                }
                
                 traverseMazeAuto(currentRow - 1, currentColumn, numberOfMoves);//north
                 traverseMazeAuto(currentRow, currentColumn + 1, numberOfMoves);//east
                 traverseMazeAuto(currentRow + 1, currentColumn, numberOfMoves);//south
                 traverseMazeAuto(currentRow, currentColumn - 1, numberOfMoves);//west
                 
                 printMaze(maze, currentRow, currentColumn);
                 System.out.println("");
                 maze[currentRow][currentColumn] = 0;
                               
}    
//*****************************************************************************************************************
	//Method:       traverseMazeEnter
	//Description:	This is the same as traverseMazeAuto, but it requires the user to hit ENTER at each print.
        //              Recursively traverses the maze one square at a time.  The algorithm progresses as follows:  
        //              First, it checks to see if the current position is in bounds, and returns if it is not.
        //              Then, it checks to see if the current position is a wall or has already been visited, and will 
        //                  return if so.
        //              The current position is then marked as visited.
        //              The maze is printed if ENTER is hit.
        //              The number of moves is incremented.
        //              The program checks if the number of moves is longer than shortestPath and returns if so.
        //              The program checks if the current location is an exit.  If the number of moves is less than
        //                   shortestPath, shortestPath is set to numberOfMoves.
        //                   The maze is printed.
        //                   loadArray is called to set shortMaze[][] equal to the current state of maze[][]
        //              traverseMazeAuto to the north is called; 
        //              traverseMazeAuto to the east is called; 
        //              traverseMazeAuto to the south is called; 
        //              traverseMazeAuto to the west is called;
        //              The current space is vacated (set to 0)
        //              
	//Parameters:	maze-   the 2 dimensional array holding the elements of the maze
        //              currentRow
        //              currentColumn
        //              numberOfMoves - the number of moves from the starting point, not including the starting point
        //
	//Returns:	nothing
	//Throws:	nothing
	//Calls:		printMaze, traverseMazeEnter, loadArray
public static void traverseMazeEnter(int currentRow, int currentColumn, int numberOfMoves){
     if (currentRow < 0 || currentRow > maze.length - 1 
                || currentColumn < 0 || currentColumn > maze[currentRow].length - 1) {
            return;//check bounds
        }
    if (maze[currentRow][currentColumn] == 1 || maze[currentRow][currentColumn] == 2) {
            return;//check if wall or already visited
        }
                KeyboardInputClass keyboardInput = new KeyboardInputClass();
                String userInput="";
                userInput = keyboardInput.getString("", "Hit ENTER ");
                if(userInput.equals("")){
                    maze[currentRow][currentColumn] = 2;
                    spacesVisited++;
                    printMaze(maze, currentRow, currentColumn);
                    numberOfMoves++;
                }
                
                
                
                if (numberOfMoves > shortestPath) {//don't keep going if it's already too long
                maze[currentRow][currentColumn] = 0;
                    System.out.println("^this path ended because it was already too long");
                    System.out.println("");
                return;
                 }
                
                System.out.println("");
                
                if(checkAllDirections(maze, currentRow, currentColumn) == 3){//check if exit
                    if(numberOfMoves <= shortestPath) {//Set new shortest path
                        shortestPath = numberOfMoves;
                        System.out.println("////////Here is a path that took " + numberOfMoves + " moves:");
                        printMaze(maze, currentRow, currentColumn);
                        System.out.println("");
                        loadArray(maze);
                       
                    }
                }
                
                 traverseMazeEnter(currentRow - 1, currentColumn, numberOfMoves);//north
                 traverseMazeEnter(currentRow, currentColumn + 1, numberOfMoves);//east
                 traverseMazeEnter(currentRow + 1, currentColumn, numberOfMoves);//south
                 traverseMazeEnter(currentRow, currentColumn - 1, numberOfMoves);//west
                 
                 userInput = keyboardInput.getString("", "Hit ENTER ");
                 if(userInput.equals("")){
                 printMaze(maze, currentRow, currentColumn);
                 System.out.println("");
                 maze[currentRow][currentColumn] = 0;
                 }
                               
}  
    //*****************************************************************************************************************
	//Method:       SolveMaze
	//Description:	This is the same as traverseMazeAuto, but each step is not printed.
        //              Recursively traverses the maze one square at a time. The algorithm progresses as follows:  
        //              First, it checks to see if the current position is in bounds, and returns if it is not.
        //              Then, it checks to see if the current position is a wall or has already been visited, and will 
        //                  return if so.
        //              The current position is then marked as visited.
        //              The number of moves is incremented.
        //              The program checks if the number of moves is longer than shortestPath and returns if so.
        //              The program checks if the current location is an exit.  If the number of moves is less than 
        //                   shortestPath, shortestPath is set to numberOfMoves.
        //                   The maze is printed.
        //                   loadArray is called to set shortMaze[][] equal to the current state of maze[][]
        //              traverseMazeAuto to the north is called; 
        //              traverseMazeAuto to the east is called; 
        //              traverseMazeAuto to the south is called; 
        //              traverseMazeAuto to the west is called;
        //              The current space is vacated (set to 0)
        //              
	//Parameters:	maze-   the 2 dimensional array holding the elements of the maze
        //              currentRow
        //              currentColumn
        //              numberOfMoves - the number of moves from the starting point, not including the starting point
        //
	//Returns:	nothing
	//Throws:	nothing
	//Calls:		printMaze, solveMaze, loadArray
public static void solveMaze(int currentRow, int currentColumn, int numberOfMoves){
    if (currentRow < 0 || currentRow > maze.length - 1 
                || currentColumn < 0 || currentColumn > maze[currentRow].length - 1) {
            return;//check bounds
        }
    if (maze[currentRow][currentColumn] == 1 || maze[currentRow][currentColumn] == 2) {
            return;//check if wall or already visited
        }
   
                maze[currentRow][currentColumn] = 2;
                spacesVisited++;
                numberOfMoves++;
                
                
                if (numberOfMoves > shortestPath) {//don't keep going if it's already too long
                maze[currentRow][currentColumn] = 0;
                return;
                 }
                
                if(checkAllDirections(maze, currentRow, currentColumn) == 3){//check if exit
                    if(numberOfMoves <= shortestPath) {//Set new shortest path
                        shortestPath = numberOfMoves;
                        //System.out.println("////////Here is a path that took " + numberOfMoves + " moves:");
                        //printMaze(maze, currentRow, currentColumn);
                        //System.out.println("");
                        loadArray(maze);
                    }
                }
                
                 solveMaze(currentRow - 1, currentColumn, numberOfMoves);//north
                 solveMaze(currentRow, currentColumn + 1, numberOfMoves);//east
                 solveMaze(currentRow + 1, currentColumn, numberOfMoves);//south
                 solveMaze(currentRow, currentColumn - 1, numberOfMoves);//west
                 
                 maze[currentRow][currentColumn] = 0;
                 
                 
                 
                 

            }
        
        
    

    


//    
    //*****************************************************************************************************************
	//Method:       checkAllDirections
	//Description:	Checks the value of the elements in each of the four directions from the current location 
        //                    in the 2 dimensional array
	//Parameters:	maze-   the 2 dimensional array holding the elements of the maze
        //              currentRow
        //              currentColumn
	//Returns:	int; either 3 representing an exit, or the value of the square to the north
	//Throws:	nothing
	//Calls:		nothing
    public static int checkAllDirections(int[][]maze, int currentRow, int currentColumn){
        
        if (checkNorth(maze, currentRow, currentColumn) == 3 ||
                checkEast(maze, currentRow, currentColumn) == 3 ||
                checkSouth(maze, currentRow, currentColumn) == 3 ||
                checkWest(maze, currentRow, currentColumn) == 3){
            return 3;
        }
        else return 1;
    }
    

 
    
}
//*******************************************************************************
//*******************************************************************************

