package mylab.puzzles;

/**
 * File Fix-It
 *
 * Best description can be found here : https://code.google.com/codejam/contest/635101/dashboard
 *
 * Indra Gunawan - March 3, 2013
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class FileFixIt {

    public int solve(FileFixItRequest request) {
        int result = 0;

        for (String newFolderName : request.newFolderNames) {
            boolean isFolderExisting = false;
            for (String existingFolderName : request.existingFolderNames) {
                if (existingFolderName.equals(newFolderName)) {
                    isFolderExisting = true;
                    break;
                }
            }

            if (!isFolderExisting) {
                String _tokens[] = newFolderName.split("/");
                String _compare = "";
                int _result = 0;
                for (String _token : _tokens) {
                    if (!"".equals(_token)) {
                        _compare += "/" + _token;
                        boolean anyMatch = false;
                        for (String existingFolderName : request.existingFolderNames) {
                            if (existingFolderName.equals(_compare)) {
                                anyMatch = true;
                                break;
                            }
                        }
                        if (!anyMatch) {
                            _result++;
                            request.existingFolderNames.add(_compare);
                        }
                    }
                }
                result += _result;
            }
        }

        return result;
    }

    public static void main(String args[]) throws Exception {
        FileFixIt problem = new FileFixIt();

        BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

        String numberOfTestCases = userInputReader.readLine();
        for (int i = 0;i < Integer.parseInt(numberOfTestCases);i++) {
            String _folderNumbers = userInputReader.readLine();
            String folderNumbers[] = _folderNumbers.split(" ");

            FileFixIt.FileFixItRequest request = new FileFixIt().new FileFixItRequest();
            request.existingFolder = Integer.parseInt(folderNumbers[0]);
            request.newFolder = Integer.parseInt(folderNumbers[1]);

            if (request.existingFolder > 0) {
                for (int j = 0;j < request.existingFolder;j++) {
                    String _existingFolderName = userInputReader.readLine();
                    request.existingFolderNames.add(_existingFolderName);
                }
            }

            for (int k = 0;k < request.newFolder;k++) {
                String _newFolderName = userInputReader.readLine();
                request.newFolderNames.add(_newFolderName);
            }

            int result = problem.solve(request);
            System.out.println("Case #" + (i + 1) + ": " + result);
        }
    }

    class FileFixItRequest {
        public int existingFolder;
        public int newFolder;

        public List<String> existingFolderNames = new LinkedList<String>();
        public List<String> newFolderNames = new LinkedList<String>();
    }

}
