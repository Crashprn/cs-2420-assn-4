import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
import java.util.random.RandomGenerator;

public class WritePoetry {
    private HashTable<String, WordFreqInfo> hashTable;

    WritePoetry(){}

    public String WritePoem(String textFile, String seedWord1, String seedWord2, int lines, Boolean bool) throws FileNotFoundException {
        hashTable = new HashTable<>();
        makeHashTable(textFile);
        ArrayList<String> poem = new ArrayList<>();
        poem.add(seedWord1);
        poem.add(seedWord2);
        for (int i=1 ; i <  lines; i ++){
            StringBuilder word = new StringBuilder();
            word.append(poem.get(i-1));
            word.append(" ");
            word.append(poem.get(i));
            poem.add(getNextWord(word.toString()));
        }
        if (bool) System.out.println(hashTable.toString(hashTable.size()));
        return createString(poem);
    }


    private String createString(ArrayList<String> a){
        int iter = 0;
        ArrayList<String> formattedList = new ArrayList<>();
        for (String word: a){
            if ((word.equals(".") || word.equals("?") || word.equals("!") || word.equals(",")) && iter <a.size()){
                formattedList.add(word);
                formattedList.add("\n");
            }
            else{
                formattedList.add(word);
            }
            iter += 1;
        }
        formattedList.add(".");

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < formattedList.size(); i++){
            if ((i -1 >=0 && i +1 < formattedList.size()) && (!formattedList.get(i-1).equals("\n")&& !formattedList.get(i+1).equals("\n"))){
                message.append(" ");
                message.append(formattedList.get(i));
            } else{
                message.append(formattedList.get(i));
            }
        }
        return message.toString();
    }

    private String getNextWord(String word){
        if (!hashTable.contains(word)) return "Could not find seed word";
        RandomGenerator r = new Random();
        WordFreqInfo info = hashTable.find(word);
        ArrayList<String> frequencyOfWordsArray = new ArrayList<>();
        for (WordFreqInfo.Freq freq:info.followList){
            for (int i = 0; i < freq.followCt; i++) frequencyOfWordsArray.add(freq.follow);
        }
        return frequencyOfWordsArray.get(r.nextInt(frequencyOfWordsArray.size()));

    }

    private void makeHashTable(String textFile) throws FileNotFoundException {
        File file = new File(textFile);
        Scanner sc = new Scanner(file);
        ArrayList<String> oldLine = null;

        while (sc.hasNextLine()) {
            ArrayList<String> newline = new ArrayList<String>();
            Collections.addAll(newline,sc.nextLine().split(" "));
            if ( newline.get(0).equals("")) continue;
            for (int i = 2; i < newline.size(); i++){

                    StringBuilder word = new StringBuilder();
                    word.append(newline.get(i-2).toLowerCase());
                    word.append(" ");
                    word.append(newline.get(i -1).toLowerCase());

                    if (hashTable.contains(word.toString())) {
                        hashTable.find(word.toString()).update(newline.get(i).toLowerCase());
                    } else {
                        hashTable.insert(word.toString(), new WordFreqInfo(word.toString(), 1));
                        hashTable.find(word.toString()).update(newline.get(i).toLowerCase());
                    }

            }

            if (oldLine != null){
                StringBuilder word2 = new StringBuilder();
                word2.append(oldLine.get(oldLine.size()-2).toLowerCase());
                word2.append(" ");
                word2.append(oldLine.get(oldLine.size()-1).toLowerCase());
                if (hashTable.contains(word2.toString())) {
                    hashTable.find(word2.toString()).update(newline.get(0).toLowerCase());
                }
                else {
                    hashTable.insert(word2.toString(), new WordFreqInfo(word2.toString(), 1));
                    hashTable.find(word2.toString()).update(newline.get(0).toLowerCase());
                }
                StringBuilder word3 = new StringBuilder();
                word3.append(oldLine.get(oldLine.size()-1).toLowerCase());
                word3.append(" ");
                word3.append(newline.get(0).toLowerCase());
                if (hashTable.contains(word3.toString())) {
                    hashTable.find(word3.toString()).update(newline.get(1).toLowerCase());
                }
                else {
                    hashTable.insert(word3.toString(), new WordFreqInfo(word3.toString(), 1));
                    hashTable.find(word3.toString()).update(newline.get(1).toLowerCase());
                }
            }
            oldLine = newline;

        }
    }

}
