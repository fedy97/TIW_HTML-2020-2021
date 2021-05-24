
package it.polimi.tiw.bean;

public class ArticleBean {

    String id;
    String name;
    String description;
    String category;
    String photo;

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {

        this.category = category;
    }

    public String getPhoto() {

        return photo;
    }

    public void setPhoto(String photo) {

        this.photo = photo;
    }

    @Override
    public String toString() {

        return "ArticleBean{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", description='" + description + '\''
                + ", category='" + category + '\'' + ", photo='" + photo + '\'' + '}';
    }
}
