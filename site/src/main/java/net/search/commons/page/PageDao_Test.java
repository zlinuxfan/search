package net.search.commons.page;



import net.search.commons.Page;
import org.junit.Assert;


public class PageDao_Test {
    @org.junit.Test
    public void getPage() throws Exception {

        Page page = PageDao.getPage("возврат налога за квартиру");

        if (page != null) {
            System.out.println(page.getId());
        } else {
            System.out.println("page is null");
        }

        Assert.assertNotNull(page);

    }

}