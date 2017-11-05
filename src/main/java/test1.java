import java.io.PrintStream;
import java.util.ArrayList;

import org.fnlp.ml.types.Dictionary;
import org.fnlp.nlp.cn.tag.CWSTagger;
import org.fnlp.util.exception.LoadModelException;
import java.util.HashMap;
import java.util.Vector;

import org.fnlp.app.keyword.AbstractExtractor;
import org.fnlp.app.keyword.WordExtract;
import org.fnlp.nlp.cn.tag.POSTagger;
import org.fnlp.nlp.parser.dep.DependencyTree;
import org.fnlp.nlp.parser.dep.JointParser;
import org.fnlp.nlp.cn.tag.CWSTagger;
import org.fnlp.nlp.corpus.StopWords;
import org.fnlp.nlp.cn.tag.NERTagger;
import org.fnlp.nlp.cn.tag.POSTagger;
import org.fnlp.nlp.parser.dep.DependencyTree;
import org.fnlp.nlp.parser.dep.JointParser;
import org.fnlp.ml.types.Dictionary;
import org.fnlp.nlp.cn.tag.CWSTagger;
import org.fnlp.nlp.cn.tag.POSTagger;
import static org.fnlp.nlp.cn.CNFactory.parser;
import org.fnlp.nlp.cn.ner.TimeNormalizer;
import org.fnlp.nlp.cn.ner.TimeUnit;
import wubingchen_utilty.Wbc_str_news;


public class test1 {
    static POSTagger tag;
    public static void main(String[] args)throws Exception{
////////////        CWSTagger tag = new CWSTagger("models/seg.m");
////////////        System.out.println("不使用词典的分词：");
////////////        String str = " 媒体计算研究所成立了, 高级数据挖掘(data mining)很难。 乐phone热卖！";
////////////        String s = tag.tag(str);
////////////        System.out.println(s);
////////////
////////////        //设置英文预处理
////////////        tag.setEnFilter(true);
////////////        s = tag.tag(str);
////////////        System.out.println(s);
////////////        //		tag.setEnFilter(false);
////////////
////////////        System.out.println("\n设置临时词典：");
////////////        ArrayList<String> al = new ArrayList<String>();
////////////        al.add("数据挖掘");
////////////        al.add("媒体计算研究所");
////////////        al.add("乐phone");
////////////        Dictionary dict = new Dictionary(false);
////////////        dict.addSegDict(al);
////////////        tag.setDictionary(dict);
////////////        s = tag.tag(str);
////////////        System.out.println(s);
////////////
////////////
////////////        CWSTagger tag2 = new CWSTagger("models/seg.m", new Dictionary("models/dict.txt"));
////////////        System.out.println("\n使用词典的分词：");
////////////        String str2 = "媒体计算研究所成立了, 高级数据挖掘很难。 乐phone热卖！";
////////////        String s2 = tag2.tag(str2);
////////////        System.out.println(s2);
////////////
////////////        //使用不严格的词典
////////////        CWSTagger tag3 = new CWSTagger("models/seg.m", new Dictionary("models/dict_ambiguity.txt", true));
////////////        //尽量满足词典，比如词典中有“成立”“成立了”和“了”, 会使用Viterbi决定更合理的输出
////////////        System.out.println("\n使用不严格的词典的分词：");
////////////        String str3 = "媒体计算研究所成立了, 高级数据挖掘很难";
////////////        String s3 = tag3.tag(str3);
////////////        System.out.println(s3);
////////////        str3 = "我送给力学系的同学一个玩具 (送给给力力学力学系都在词典中)";
////////////        s3 = tag3.tag(str3);
////////////        System.out.println(s3);
//////////          NERTagger tag = new NERTagger("models/seg.m", "models/pos.m");
//////////          String str = " 新浪体育讯　北京时间4月15日03:00(英国当地时间14日20:00)，2009/10赛季英格兰足球超级联赛第34轮一场焦点战在白鹿巷球场展开角逐，阿森纳客场1比2不敌托特纳姆热刺，丹尼-罗斯和拜尔先入两球，本特纳扳回一城。阿森纳仍落后切尔西6分(净胜球少15个)，夺冠几成泡影。热刺近 7轮联赛取得6胜，继续以1分之差紧逼曼城。";
//////////          HashMap<String, String> map = new HashMap<String, String>();
//////////          tag.tag(str, map);
//////////          System.out.println(map);
//////////          System.out.println("Done!");
////////
////////            StopWords sw = new StopWords("models/stopwords");
////////            CWSTagger seg = new CWSTagger("models/seg.m");
////////            AbstractExtractor key = new WordExtract(seg, sw);
////////
////////            System.out.println(key.extract("甬温线特别重大铁路交通事故车辆经过近24小时的清理工作，26日深夜已经全部移出事故现场，之前埋下的D301次动车车头被挖出运走", 20, true));
////////
////////            //处理已经分好词的句子
////////            sw = null;
////////            key = new WordExtract(seg, sw);
////////            System.out.println(key.extract("甬温线 特别 重大 铁路交通事故车辆经过近24小时的清理工作，26日深夜已经全部移出事故现场，之前埋下的D301次动车车头被挖出运走", 20));
////////            System.out.println(key.extract("赵嘉亿 是 好人 还是 坏人", 5));
////////
////////            key = new WordExtract();
////////            System.out.println(key.extract("", 5));
//////              parser = new JointParser("models/dep.m");
//////
//////              System.out.println("得到支持的依存关系类型集合");
//////              System.out.println(parser.getSupportedTypes());
//////
//////              String word = "中国进出口银行与中国银行加强合作。";
//////              test(word);
////
////        CWSTagger cws = new CWSTagger("models/seg.m");
////        tag = new POSTagger(cws, "models/pos.m");
////
////        System.out.println("得到支持的词性标签集合");
////        System.out.println(tag.getSupportedTags());
////        System.out.println(tag.getSupportedTags().size());
////        System.out.println("\n");
////
////        String str = "媒体计算研究所成立了，高级数据挖掘很难。乐phone很好！";
////        String s = tag.tag(str);
////        System.out.println("处理未分词的句子");
////        System.out.println(s);
////
////        System.out.println("使用英文标签");
////        tag.SetTagType("en");
////        System.out.println(tag.getSupportedTags());
////        System.out.println(tag.getSupportedTags().size());
////        s = tag.tag(str);
////        System.out.println(s);
////        System.out.println();
////
////        CWSTagger cws2 = new CWSTagger("models/seg.m", new Dictionary("models/dict.txt"));
////
////        //bool值指定该dict是否用于cws分词（分词和词性可以使用不同的词典）
////        tag = new POSTagger(cws2, "models/pos.m"
////                , new Dictionary("models/dict.txt"), true);//true就替换了之前的dict.txt
////        tag.removeDictionary(false);//不移除分词的词典
////        tag.setDictionary(new Dictionary("models/dict.txt"), false);//设置POS词典，分词使用原来设置
////
////        String str2 = "媒体计算研究所成立了，高级数据挖掘很难。乐phone很好！";
////        String s2 = tag.tag(str2);
////        System.out.println("处理未分词的句子，使用词典");
////        System.out.println(s2);
////        System.out.println();
////
////        Dictionary dict = new Dictionary();
////        dict.add("媒体计算", "mypos1", "mypos2");
////        dict.add("乐phone", "专有名");
////        tag.setDictionary(dict, true);
////        String s22 = tag.tag(str2);
////        System.out.println(s22);
////        System.out.println();
////
////        POSTagger tag1 = new POSTagger("models/pos.m");
////        String str1 = "媒体计算 研究所 成立 了 , 高级 数据挖掘 很 难";
////        String[] w = str1.split(" ");
////        String[] s1 = tag1.tagSeged(w);
////        System.out.println("直接处理分好词的句子:++++++++++");
////        for (int i = 0; i < s1.length; i++) {
////            System.out.print(w[i] + "/" + s1[i] + " ");
////        }
////        System.out.println("\n");
////
////        POSTagger tag3 = new POSTagger("models/pos.m", new Dictionary("models/dict.txt"));
////        String str3 = "媒体计算 研究所 成立 了 , 高级 数据挖掘 很 难 ";
////        String[] w3 = str3.split(" ");
////        String[] s3 = tag3.tagSeged(w3);
////        System.out.println("直接处理分好词的句子，使用词典");
////        for (int i = 0; i < s3.length; i++) {
////            System.out.print(w3[i] + "/" + s3[i] + " ");
////        }
////        System.out.println("\n");
////
////        //????????????????????????????
////
////        System.out.println("重新构造");
////        cws = new CWSTagger("models/seg.m");
////        tag = new POSTagger(cws, "models/pos.m");
////        str = "媒体计算研究所成立了, 高级数据挖掘很难";
////        System.out.println(tag.tag(str));
////        String[][] sa = tag.tag2Array(str);
////        for (int i = 0; i < sa.length; i++) {
////            for (int j = 0; j < sa[i].length; j++) {
////                System.out.print(sa[i][j] + " ");
////            }
////            System.out.println();
////        }
////
////    }
////        private static void test(String word) throws Exception {
////                POSTagger tag = new POSTagger("models/seg.m", "models/pos.m");
////                String[][] s = tag.tag2Array(word);
////                try {
////                        DependencyTree tree = parser.parse2T(s[0], s[1]);
////                        System.out.println(tree.toString());
////                        String stree = parser.parse2String(s[0], s[1], true);
////                        System.out.println(stree);
////                } catch (Exception e) {
////                        e.printStackTrace();
////                }
//
//        StopWords sw = new StopWords("models/stopwords");
//        CWSTagger seg = new CWSTagger("models/seg.m");
//        AbstractExtractor key = new WordExtract(seg, sw);
//
//        System.out.println(key.extract("甬温线特别重大铁路交通事故车辆经过近24小时的清理工作，26日深夜已经全部移出事故现场，之前埋下的D301次动车车头被挖出运走", 20, true));
//
//        //处理已经分好词的句子
//        sw = null;
//        key = new WordExtract(seg, sw);
//        System.out.println(key.extract("甬温线 特别 重大 铁路交通事故车辆经过近24小时的清理工作，26日深夜已经全部移出事故现场，之前埋下的D301次动车车头被挖出运走", 20));
//        System.out.println(key.extract("赵嘉亿 是 好人 还是 坏人", 5));
//
//        key = new WordExtract();
//        System.out.println(key.extract("", 5));

//        String target = "08年北京申办奥运会，8月8号开幕式，九月十八号闭幕式。" +
//                "1年后的7月21号发生了件大事。" +
//                "今天我本想去世博会，但是人太多了，直到晚上9点人还是那么多。" +
//                "考虑到明天和后天人还是那么多，决定下周日再去。";
//        TimeNormalizer normalizer;
//        normalizer = new TimeNormalizer("models/time.m");
//        normalizer.parse(target);
//        TimeUnit[] unit = normalizer.getTimeUnit();
//        for (int i = 0; i < unit.length; i++) {
//            System.out.println(unit[i]);
//        }
        Vector<Wbc_str_news> vec=new Vector<Wbc_str_news>();
        Wbc_str_news wbc_str_news=new Wbc_str_news();
        wbc_str_news.title="das";
        vec.add(wbc_str_news);
        vec.get(0).url="www.baidu.com";
        System.out.print(vec.get(0).url);


        }

}
