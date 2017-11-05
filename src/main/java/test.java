import com.sun.javafx.collections.MappingChange;
import javafx.util.Pair;
import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.util.ValueComparator;
import org.fnlp.util.exception.LoadModelException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import wubingchen_utilty.Wbc_str_news;
import wubingchen_utilty.Wbc_utility;

import  java.util.Comparator;

import java.io.*;
import java.util.*;
import java.util.Collections;
import  java.util.Collection;
import org.fnlp.nlp.corpus.StopWords;

import java.util.*;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.LucenePackage.*;
import  org.apache.lucene.analysis.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import java.io.IOException;
import org.apache.lucene.util.*;
import  org.apache.lucene.search.*;
import  org.apache.lucene.LucenePackage.*;
import org.apache.lucene.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.analysis.*;





public class test {
;
    public static void main(String[] args) throws Exception {
        CNFactory factory = CNFactory.getInstance("models");

        //存取新闻的list
        Wbc_utility wbc_utilty = new Wbc_utility();
        Vector<Wbc_str_news> vecStruNews=new Vector<Wbc_str_news>();
      //  Vector<Pair<String,Integer>> vecWordtoNum=new Vector<Pair<>>();
      //  Vector<Pair<String,Integer>> vecEntityNum=new Vector<Pair<String,Integer>>();
        Map<String,Integer> mapWordtoNum=new HashMap<String, Integer>();
        Map<String,Integer> mapEntitytoNum=new HashMap<String, Integer>();
        List<String> listStopWord=new LinkedList<String>();

        //爬取主页
        String url;//= in.nextLine();
        url = "http://newsxq.xjtu.edu.cn/";
        Document doc = Jsoup.connect(url).get();
        String chinesePunctuation = "？。，：；、“”《》「」『』－（）【】 \\n \\t  " +
                "\" -> \"796\"";

        // /判断是否获取文档
        if (doc == null) {
            System.out.println("no docoment avaliable!");
            return;
        }
        Elements elements = doc.select(".i_li[title][href]");
        //获取标题与url
        for (Element element : elements) {
            //vec_news_url.add(element.attr("abs:href"));
            Wbc_str_news wbc_str_news=new Wbc_str_news();
            wbc_str_news.title=element.attr("title");
            wbc_str_news.url=element.attr("abs:href");
            vecStruNews.add(wbc_str_news );
        }

        //获取作者，日期，文本
        for (int i=0;i<vecStruNews.size();i++)
        {
            Document tmp_Document = Jsoup.connect(vecStruNews.get(i).url).get();
            Elements author_dates_ele = tmp_Document.select("div.d_author");
            String titleAndDate=author_dates_ele.first().text();
            vecStruNews.get(i).author=titleAndDate.substring(titleAndDate.indexOf("："),titleAndDate.indexOf(" "));
            vecStruNews.get(i).date=titleAndDate.substring(titleAndDate.indexOf("期")+1,titleAndDate.lastIndexOf(" "));
           // Elements news_elements = tmp_Document.select("#vsb_newscontent").select("p:not([align])");
            Elements news_elements = tmp_Document.select("#vsb_newscontent").select("p:not([img])");

            String tmp_String ="";
            for (int j=0;j<news_elements.size();j++)
            {
                //判断空文本现象出现
                if (news_elements.get(j).text().isEmpty())continue;
                tmp_String += news_elements.get(j).text() + "\n";
            }
            //排除纯图片新闻
            if (tmp_String ==""||tmp_String.length()<10)
            {
                vecStruNews.remove(i);
                i--;
                continue;
            }
            vecStruNews.get(i).text=tmp_String;
            tmp_String="";
        }

        //读取停用词
        FileReader fileReader=new FileReader("E:\\Code_Storage\\idea\\demo\\models\\stopword.dic");
        BufferedReader bufferedReader=new BufferedReader(fileReader);
        String readline="";
        while ((readline=bufferedReader.readLine())!=null)
        {
            listStopWord.add(readline);
        }

        //处理文本-分词
        for(int i=0;i<vecStruNews.size();i++)
        {
            // 使用分词器对中文句子进行分词，得到分词结果
            if (vecStruNews.get(i).text.equals(""))
            {
                System.out.print("erroe!!");
            }
            String[] words = factory.seg(vecStruNews.get(i).text);
            for (String tmpStr:words)
            {
                if (!listStopWord.contains(tmpStr)&&!tmpStr.contains("\n"))
                {

                    if (mapWordtoNum.containsKey(tmpStr))
                    {
                        mapWordtoNum.put(tmpStr,mapWordtoNum.get(tmpStr)+1);
                    }
                    else
                    {
                        mapWordtoNum.put(tmpStr,1);
                    }

                }
            }

            //处理实体
            Map<String, String> result = factory.ner(vecStruNews.get(i).text);
            Set<String>  setResult=result.keySet();
            for (String tmpStr:setResult)
            {
                if (mapEntitytoNum.containsKey(tmpStr))
                {
                    mapEntitytoNum.put(tmpStr,mapEntitytoNum.get(tmpStr)+1);
                }
                else
                {
                    mapEntitytoNum.put(tmpStr,1);
                }
            }
        }
        //对分词与实体按照频率排序
        List<Map.Entry<String, Integer>> vecWordtoNum = new ArrayList<>(mapWordtoNum.entrySet());
        Collections.sort(vecWordtoNum, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (int) (o2.getValue()-o1.getValue() );
            }
        });
        List<Map.Entry<String, Integer>> vecEntitytoNum = new ArrayList<>(mapEntitytoNum.entrySet());
        Collections.sort(vecEntitytoNum, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()-o1.getValue() );
            }
        });

        //使用lucene建立索引并提交
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory index = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter w = new IndexWriter(index, config);
        int lenVecStruNews=vecStruNews.size();
        for (int i=0;i<lenVecStruNews;i++)
        {
            addDoc(w, vecStruNews.get(i).title, vecStruNews.get(i).url,vecStruNews.get(i).text);
        }
        w.commit();
        w.close();


        //读取查询词语
        Scanner sc=new Scanner(System.in);
        System.out.print("输入查询分词:");
        String inputQueryWord;
        do {
            inputQueryWord=sc.nextLine();
        }while (inputQueryWord.equals(""));
        Query query = new QueryParser( "text", analyzer).parse(inputQueryWord);

        //查找并分析查询关键词
        //设置最大可查询数量
        int hitsPerPage = 200;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(query, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        //打印查询结果
        System.out.println("查询《 " + inputQueryWord + "》 结果为:");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            org.apache.lucene.document.Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("title") + "\t" + d.get("url"));
        }
        reader.close();

        //对于文件的输出
        for (int i=0;i<vecStruNews.size();i++)
        {
            Wbc_utility.wbc_write_file(vecStruNews.get(i).text,"target\\news_categery\\"+vecStruNews.get(i).title.toString(),"",false);
        }
        Wbc_utility.wbc_write_file("","target\\news_categery\\"+"fengci.txt","\n",false);
        for (String tmpStr:mapWordtoNum.keySet())
        {
            Wbc_utility.wbc_write_file(tmpStr+"\t"+mapWordtoNum.get(tmpStr),"target\\news_categery\\"+"fengci.txt","\n",true);

        }
        Wbc_utility.wbc_write_file("","target\\news_categery\\"+"entity.txt","\n",false);
        for (String tmpStr:mapEntitytoNum.keySet())
        {
            Wbc_utility.wbc_write_file(tmpStr+"\t"+mapEntitytoNum.get(tmpStr),"target\\news_categery\\"+"entity.txt","\n",true);

        }
        Wbc_utility.wbc_write_file("","target\\news_categery\\"+"sorted_fengci.txt","\n",false);
        for (int i=0;i<vecWordtoNum.size();i++)
        {
            Wbc_utility.wbc_write_file(vecWordtoNum.get(i).getKey()+"\t"+vecWordtoNum.get(i).getValue(),"target\\news_categery\\"+"sorted_fengci.txt","\n",true);

        }
        Wbc_utility.wbc_write_file("","target\\news_categery\\"+"sorted_entity.txt","\n",false);
        for (int i=0;i<vecEntitytoNum.size();i++)
        {
            Wbc_utility.wbc_write_file(vecEntitytoNum.get(i).getKey()+"\t"+vecEntitytoNum.get(i).getValue(),"target\\news_categery\\"+"sorted_entity.txt","\n",true);

        }


    }
    private static void addDoc(IndexWriter w, String title, String url,String text) throws IOException {
        org.apache.lucene.document.Document doc = new  org.apache.lucene.document.Document();
        doc.add(new StringField("title", title, Field.Store.YES));
        doc.add(new StringField("url", url, Field.Store.YES));
        doc.add(new TextField("text",text, Field.Store.YES));
        w.addDocument(doc);
    }

    static int findwordinvector( Vector<Pair<String,Integer>> parvec,String word)
    {
        int index=-1;
        int len=parvec.size();
        for (int i=0;i<parvec.size();i++)
        {
            if (parvec.get(i).getKey().equals(word))
            {
                index=i;
                break;
            }
        }
        return  index;
    }



}
