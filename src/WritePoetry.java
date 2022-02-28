import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
import java.util.random.RandomGenerator;

public class WritePoetry {
    private HashTable<String, WordFreqInfo> hashTable;

    WritePoetry(){}

    public String WritePoem(String textFile, String seedWord, int lines, Boolean bool) throws FileNotFoundException {
        hashTable = new HashTable<>();
        makeHashTable(textFile);
        ArrayList<String> poem = new ArrayList<>();
        poem.add(seedWord);
        for (int i=0 ; i <  lines; i ++){
            poem.add(getNextWord(poem.get(i)));
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

            for (int i = 0; i < newline.size(); i++){
                if (!hashTable.contains(newline.get(i).toLowerCase())) {
                    hashTable.insert(newline.get(i).toLowerCase() , new WordFreqInfo(newline.get(i).toLowerCase(), 1));
                }else{
                    hashTable.find(newline.get(i).toLowerCase()).update(newline.get(i).toLowerCase());
                }
                if (i -1 >=0){
                    hashTable.find(newline.get(i-1).toLowerCase()).update(newline.get(i).toLowerCase());
                }



            }

            if (oldLine != null){
                String oldWord = oldLine.get(oldLine.size()-1).toLowerCase();
                hashTable.find(oldWord).update(newline.get(0).toLowerCase());
            }
            oldLine = newline;

        }
    }

}
