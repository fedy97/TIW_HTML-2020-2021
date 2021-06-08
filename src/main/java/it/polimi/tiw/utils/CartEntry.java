
package it.polimi.tiw.utils;

public class CartEntry {

    private String  articleId;
    private Integer qty;

    public CartEntry(String articleId, Integer qty) {
        this.articleId = articleId;
        this.qty = qty;
    }

    public String getArticleId() {

        return articleId;
    }

    public void setArticleId(String articleId) {

        this.articleId = articleId;
    }

    public Integer getQty() {

        return qty;
    }

    public void setQty(Integer qty) {

        this.qty = qty;
    }

    @Override
    public String toString() {

        return "CartEntry{" + "articleId='" + articleId + '\'' + ", qty=" + qty + '}';
    }
}
