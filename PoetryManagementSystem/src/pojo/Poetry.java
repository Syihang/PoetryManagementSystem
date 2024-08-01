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
        // �����Ϊ�ָ����ָ��ı�����ȥ����β�հ׺��������
        String[] sentences = text.split("[����?]");

        // ȥ��ÿ������ǰ��Ŀհ׷�
        for (int i = 0; i < sentences.length; i++) {
            sentences[i] = sentences[i].trim() + "��";
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
        String text = "���¼�ʱ�У��Ѿ������졣��֪���Ϲ��ڣ���Ϧ�Ǻ��ꡣ�����˷��ȥ���ֿ���¥����ߴ���ʤ��������Ū��Ӱ���������˼䣿(���� һ������ʱ���ֿ� һ����Ω / Ψ��)\n" +
                "ת��󣬵�粻��������ߡ���Ӧ�кޣ����³����ʱԲ�����б�����ϣ���������Բȱ�����¹���ȫ����Ը�˳��ã�ǧ�ﹲ濾ꡣ";
        String[] strs = toStrings(text);
        for (String s:strs) {
            System.out.println(s);
        }
    }
}
