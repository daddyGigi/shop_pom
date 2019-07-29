package com.qf.shop_search_service;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchServiceApplicationTests {
    @Autowired
    private SolrClient solrClient;
    @Test
    public void add() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id",1);
        document.addField("gname","方天画戟");
        document.addField("ginfo","吕布用过的，贼猛的武器");
        document.addField("gimage","www.ali.com");
        document.addField("gprice",999.12);
        document.addField("gsave",1);

        solrClient.add(document);
        solrClient.commit();
    }
    @Test
    public void delete() throws IOException, SolrServerException {
        solrClient.deleteById("6dec7651-85b3-4c16-b8f3-38a089815bda");
        solrClient.commit();
    }

}
