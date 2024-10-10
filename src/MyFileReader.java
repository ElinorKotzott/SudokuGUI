import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFileReader {

    public static List<List<SudokuMap>> getSudokuTemplate() {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("C:/Users/Elinor/IdeaProjects/Sudoku/src/SudokuTemplates.txt"));
            List<List<SudokuMap>> mainList = new ArrayList<>();
            List <Integer> sudokuNumbers;

            String line = br.readLine();
            try {
                while (line != null) {
                    List<String> elements = Arrays.stream(line.split(",")).toList();
                    try {
                        sudokuNumbers = (elements.stream().map(o -> Integer.valueOf(o.trim())).toList());
                        List<SudokuMap> temporaryList = new ArrayList<>();
                            for(int i = 0; i < sudokuNumbers.size(); i++) {
                                if (sudokuNumbers.get(i) == 0) {
                                    temporaryList.add(null);
                                } else {
                                    SudokuMap temporarySudokuMap = new SudokuMap();
                                    temporarySudokuMap.setNumber(sudokuNumbers.get(i));
                                    temporarySudokuMap.setFixedNumber(true);
                                    temporaryList.add(temporarySudokuMap);
                                }
                            }
                        mainList.add(temporaryList);
                    } catch (NumberFormatException nfe) {
                        System.out.println(nfe + "The file could not be read.");
                    }
                    line = br.readLine();
                }
                return mainList;
            } catch (NumberFormatException nfe) {
                System.out.println(nfe + "The file could not be read. Wrong format provided.");
            }
        } catch (IOException ioe) {
            System.out.println(ioe + "The file could not be read.");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException io) {
                System.out.println(io + "A problem occurred.");
            }
        }
        return null;
    }
}