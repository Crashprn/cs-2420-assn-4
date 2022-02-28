import java.util.*;

public class WordFreqInfo {
    public String word;
    public int occurCt;
    ArrayList<Freq> followList;

    public WordFreqInfo(String word, int count) {
        this.word = word;
        this.occurCt = count;
        this.followList = new ArrayList<Freq>();
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Word :" + word+":");
        sb.append(" (" + occurCt + ") : ");
        for (Freq f : followList)
            sb.append(f.toString());

        return sb.toString();
    }
    public void update(String word){
        if (word.equals(this.word)){ this.occurCt +=1;}
        else {updateFollows(word);}

    }

    public void updateFollows(String follow) {
       //System.out.println("updateFollows " + word + " " + follow);
        for (Freq f : followList) {
            if (follow.compareTo(f.follow) == 0) {
                f.followCt++;
                return;
            }
        }
        followList.add(new Freq(follow, 1));
    }

    public static class Freq {
        String follow;
        int followCt;

        public Freq(String follow, int ct) {
            this.follow = follow;
            this.followCt = ct;
        }

        public String toString() {
            return follow + " [" + followCt + "] ";
        }

        public boolean equals(Freq f2) {
            return this.follow.equals(f2.follow);
        }
    }

    public static void main(String[] args) {
        WordFreqInfo info = new WordFreqInfo("do", 1);
        info.update("not");
        info.update("not");
        info.update("do");
        System.out.println(info);
    }


}

