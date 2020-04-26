package Model;

public class Comment {
    private Product commentedProduct;
    private CommentStatus commentStatus;
    private String commentText;
    private Customer commenter;

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
