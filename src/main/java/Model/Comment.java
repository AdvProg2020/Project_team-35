package Model;

import java.io.Serializable;

public class Comment implements Serializable {
    private Product commentedProduct;
    private CommentStatus commentStatus;
    private String commentText;
    private Customer commenter;
    private String title;

    private boolean doesItBought;
    public Comment(Product commentedProduct, String commentText, Customer commenter,String title) {
        this.commentedProduct = commentedProduct;
        this.commentText = commentText;
        this.commenter = commenter;
        this.title = title;
        commentedProduct.getCommentsList().add(this);
    }
    public boolean isDoesItBought(){
        if (commentedProduct.getWhoBoughtThisGood().contains(commenter)){
            return true;
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public String getCommentText() {
        return commentText;
    }

    public Customer getCommenter() {
        return commenter;
    }
    public String getCommentInfo(){
        String toString = null;
        toString = commenter.getUsername() + " : "+commentText+"\n";
        return toString;
    }
}
