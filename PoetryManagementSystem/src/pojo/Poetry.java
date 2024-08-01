package pojo;

import java.util.Map;

public class Poetry {

    private int poetry_id;
    private String title;
    private String author;
    private String dynasty;
    private int star;
    private String type;
    private String[] text;

    public Poetry(int poetry_id, String title, String author, String dynasty, int star, String type, String text) {
        this.poetry_id =  poetry_id; // Integer.parseInt(poetry_id);
        this.title = title;
        this.author = author;
        this.dynasty = dynasty;
        this.star = star; // Integer.parseInt(star);
        this.type = type;
        this.text = toStrings(text);
    }

    public Poetry() {}

    private static String[] toStrings(String text) {
        // 按句号为分隔符分割文本，并去除首尾空白后存入数组
        String[] sentences = text.split("[。？?]");

        // 去除每个句子前后的空白符
        for (int i = 0; i < sentences.length; i++) {
            sentences[i] = sentences[i].trim() + "。";
        }

        return sentences;
    }

    public int getPoetry_id() {
        return poetry_id;
    }

    public void setPoetry_id(String poetry_id) {
        this.poetry_id = Integer.parseInt(poetry_id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public int getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = Integer.parseInt(star);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String text) {
        this.text = toStrings(text);
    }

    // debug
    public static void main(String[] args) {
        String text = "明月几时有？把酒问青天。不知天上宫阙，今夕是何年。我欲乘风归去，又恐琼楼玉宇，高处不胜寒。起舞弄清影，何似在人间？(何似 一作：何时；又恐 一作：惟 / 唯恐)\n" +
                "转朱阁，低绮户，照无眠。不应有恨，何事长向别时圆？人有悲欢离合，月有阴晴圆缺，此事古难全。但愿人长久，千里共婵娟。";
        String[] strs = toStrings(text);
        for (String s:strs) {
            System.out.println(s);
        }
    }
}
