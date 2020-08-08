package collection.anew.multiutilapp;

/**
 * Created by hussaina on 07-02-2017.
 */

public class CategoryDataProvider {
    private String category_name;

    public CategoryDataProvider(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

}
