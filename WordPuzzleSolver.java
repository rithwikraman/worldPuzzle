package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import hash.HashTable;

public class WordPuzzleSolver {

	public static void main(String[] args) {
		/* Kick everything off by calling solve() */
		try {
			Scanner in = new Scanner(System.in);
			String dicFile = in.next();
			String gridFile = in.next();
			in.close();
			new WordPuzzleSolver().solve(dicFile, gridFile);
		}   
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Traversal method for each cardinal direction 
	private String getWord(int column, int row, String dir, int length, char[][] chars) {
			String word = "";
			if (dir == "N") {
				if (row-length >= -1) {
					while (word.length() < length) {
						word += chars[row][column];
						row--;
					}
					return word;
				}
			}
			else if (dir == "S") { 
				if (row + length <= chars.length) {
					while (word.length() < length) {
						word += chars[row][column];
						row++;
					}
					return word;
				}		
			}
			else if (dir == "E") { 
				if (column + length <= chars[row].length) {
					while (word.length() < length) {
						word += chars[row][column];
						column++;
					}
					return word;
				}	
			}
			else if (dir == "W") { 
				if (column - length >= -1) {
					while (word.length() < length) {
						word += chars[row][column];
						column--;
					}
					return word;
				}	
			}
			else if (dir == "NE") { 
				if (row - length >= -1 && column+length <= chars[row].length) {
					while (word.length() < length) {
						word += chars[row][column];
						row--;
						column++;
					}
					return word;
				}	
			}
			else if (dir == "SE") {
				if (row + length <= chars.length && column+length <= chars[row].length) {
					while (word.length() < length) {
						word += chars[row][column];
						row++;
						column++;
					}
					return word;
				}	
			}
			else if (dir == "NW") { 
				if (row-length >= -1 && column - length >= -1) {
					while (word.length() < length) {
						word += chars[row][column];
						row--;
						column--;
					}
					return word;
				}
			}
			else if (dir == "SW") {
				if (row + length <= chars.length && column - length >= -1) {
					while (word.length() < length) {
						word += chars[row][column];
						row++;
						column--;
					}
					return word;
				}	
			}
			return null;
		}
	
	/* Solve the puzzle */
	private void solve(String dictFile, String gridFile) throws IOException {
		 
		BufferedReader in = new BufferedReader(new FileReader(dictFile));
		
		// Creating a Hash Table containing dictionary words 
		hash.HashTable<String, String> dict = new hash.HashTable<String, String>();
		String dicttext = in.readLine();
		while (dicttext != null) {
			dict.insertQuad(dicttext,dicttext);
			dicttext = in.readLine();
		}
		in.close();
		BufferedReader in2 = new BufferedReader(new FileReader (gridFile));
		int rows = Integer.parseInt(in2.readLine());
		int columns = Integer.parseInt(in2.readLine());
		
		// Creating a 2D grid
		char[][] grid = new char[rows][columns];
		String chars = in2.readLine();
		int row = 0;
		int col = 0;
		for (char letter : chars.toCharArray()) { 
			if (col/columns == 1) {
				col = 0;
				row++;
			}
			grid[row][col] = letter;
			col++;
		}
		in2.close(); 
		
		String[] directions = {"N","E","S","W","NE","SE","NW","SW"};
		
		int t1 = (int) System.currentTimeMillis();
		// Traversing the 2D grid and counting the number of word combos that exist in the dictionary
		int count = 0;		
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				for (String dir : directions) {
					for (int len = 3; len <= 22; len++) {
						String gridWord = getWord(i,j,dir,len,grid);
						if (gridWord != null && dict.contains(gridWord)) {
							System.out.println(gridWord);
							count++;
						}
					}
				}
			}
		}
		System.out.println(count + " words found");
		int t2 = (int) System.currentTimeMillis();
		System.out.println("Time taken: " + (t2-t1));
	}
}
