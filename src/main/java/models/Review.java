package models;

public class Review {


    private String title ;
    private int id_review ;
    private String comments ;

    private int value ;
    private int id_evenement ;


    public Review(){}

    public Review(int id_review ,String title , String comments , int value ,  int id_evenement)
    {
        this.id_review = id_review ;
        this.title = title ;
        this.comments = comments ;
        this.value = value ;
        this.id_evenement = id_evenement ;
    }
    public Review(String title , String comments , int value ,  int id_evenement)
    {
        this.title = title ;
        this.comments = comments ;
        this.value = value ;
        this.id_evenement = id_evenement ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getId_review() {
        return id_review;
    }

    public void setId_review(int id_review) {
        this.id_review = id_review;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    @Override
    public String toString() {
        return "Review{" +
                "title='" + title + '\'' +
                ", id_review=" + id_review +
                ", comments='" + comments + '\'' +
                ", value=" + value +
                ", id_evenement=" + id_evenement +
                '}';
    }
}
