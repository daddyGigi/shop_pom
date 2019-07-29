package com.qf.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.entity.Page;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by  .Life on 2019/07/11/0011 16:39
 */
@Service
public class SearchService implements ISearchService {

    @Autowired
    private SolrClient solrClient;
    @Override
    public Page searchByKey(Page page) {
        SolrQuery solrQuery = null;
        List<Goods> goods = new ArrayList<>();
        if (page.getKeyword() == null || page.getKeyword().trim().equals("")){
            solrQuery = new SolrQuery("*:*");
        }else {
            String str = "gname:%s || ginfo:%s";
            String format = String.format(str, page.getKeyword(), page.getKeyword());
            solrQuery = new SolrQuery(format);

            //只有在关键字查询的时候设置高亮
            solrQuery.setHighlight(true);
            //高亮前缀
            solrQuery.setHighlightSimplePre("<font color='red'>");
            //高亮后缀
            solrQuery.setHighlightSimplePost("</font>");
            //高亮字段
            solrQuery.addHighlightField("gname");

            //查询总条数
            Integer totalCount = 0;
            try {
                //响应流
                QueryResponse queryResponse = solrClient.query(solrQuery);
                totalCount = queryResponse.getResults().size();
                //获得高亮结果
                Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
                //设置当前页
                solrQuery.setStart((page.getPageNum()-1)*page.getPageSize());
                //设置每页显示多少条
                solrQuery.setRows(page.getPageSize());

                //通过响应拿到结果集
                QueryResponse query = solrClient.query(solrQuery);
                SolrDocumentList responseResults = query.getResults();
                //遍历
                for (SolrDocument document : responseResults) {
                        Goods good = new Goods();
                    int id = Integer.parseInt(document.getFieldValue("id")+"");
                    String gname = document.getFieldValue("gname")+"";
                    String ginfo = document.getFieldValue("ginfo")+"";
                    String gimage = document.getFieldValue("gimage")+"";
                    BigDecimal gprice = new BigDecimal(document.getFieldValue("gprice")+"");
                    int gsave = (int)document.getFieldValue("gsave");

                    good.setId(id);
                    good.setGname(gname);
                    good.setGimage(gimage);
                    good.setGinfo(ginfo);
                    good.setGprice(gprice);
                    good.setGsave(gsave);

                    goods.add(good);

                    if (highlighting.containsKey(id+"")){
                        Map<String, List<String>> stringListMap = highlighting.get(id + "");
                        String highligname = stringListMap.get("gname").get(0);
                        good.setGname(highligname);
                    }
                }
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            page.setTotalCount(totalCount);
            page.setGoods(goods);
        }
        return page;
    }

    @Override
    public int addGoods(Goods goods) {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",goods.getId());
        document.setField("gname",goods.getGname());
        document.setField("ginfo",goods.getGinfo());
        document.setField("gimage",goods.getGimage());
        document.setField("gprice",goods.getGprice().floatValue());
        document.setField("gsave",goods.getGsave());

        try {
            solrClient.add(document);
            solrClient.commit();
            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
