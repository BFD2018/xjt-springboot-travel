package com.xjt.travel;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import com.xjt.travel.controller.TRouteController;
import com.xjt.travel.domain.TRoute;
import com.xjt.travel.mapper.TRouteMapper;
import com.xjt.travel.service.TRouteService;
import com.xjt.travel.utils.MyConstant;
import com.xjt.travel.utils.RespBean;
import org.apdplat.word.WordFrequencyStatistics;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.dictionary.DictionaryFactory;
import org.apdplat.word.lucene.ChineseWordTokenizer;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.apdplat.word.util.WordConfTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringbootDemoApplicationTests {

    @Test
    void contextLoads() {
        FileReader fileReader = new FileReader(MyConstant.ProjectPath + "files/2021-12-27\\23479471.html");
        String s = fileReader.readString();
        System.out.println(s);
    }

    @Autowired
    private TRouteService tRouteService;

    @Autowired
    private TRouteController routeController;

    @Test
    void test01(){
        RespBean allRouteNum = routeController.getAllRouteNum();
        System.out.println(allRouteNum);

        System.out.println(routeController.queryRoutesByConditions("","","","","","1","6"));
    }

    @Autowired
    private TRouteMapper routeMapper;

    @Test
    void generateCiyun() throws IOException {
        StringBuilder words = new StringBuilder();
        List<Map<String, Object>> mapList = routeMapper.selectMaps(new QueryWrapper<TRoute>().select("title"));
        for (Map<String, Object> map: mapList) {
            String title = (String) map.get("title");
            words.append(title + " ");
        }

        List<Word> seg = WordSegmenter.seg(words.toString());
        System.out.println(seg);
    }

    @Test
    void test010(){
        final String filename = System.getProperty("user.dir") + File.separator + "word-frequency-statistics.txt";

        StringBuilder words = new StringBuilder();
        List<Map<String, Object>> mapList = routeMapper.selectMaps(new QueryWrapper<TRoute>().select("title"));
        for (Map<String, Object> map: mapList) {
            String title = (String) map.get("title");
            words.append(title + " ");
        }

        //词频统计设置
        WordFrequencyStatistics wordFrequencyStatistics = new WordFrequencyStatistics();
        wordFrequencyStatistics.setRemoveStopWord(true);            //根据stopwords.txt去掉某些词
        wordFrequencyStatistics.setResultPath(filename);
        //wordFrequencyStatistics.setSegmentationAlgorithm(SegmentationAlgorithm.MaxNgramScore);
        //自定义停用词库
        String path = System.getProperty("user.dir") + "/src/main/resources/static/word_conf/stopwords.txt";
        WordConfTools.set("stopwords.path",path);
        DictionaryFactory.reload();
        //开始分词
        wordFrequencyStatistics.seg(words.toString());
        //输出词频统计结果
        wordFrequencyStatistics.dump();

        List<Map> arrayList = new ArrayList<>();
        FileReader fileReader = new FileReader(filename);
        List<String> strings = fileReader.readLines();
        for (String line : strings) {
            String word = line.split(" ")[0];
            Integer num = Integer.parseInt(line.split(" ")[1]) * 10;
            HashMap<String, Object> map = new HashMap<>();
            map.put("name",word);
            map.put("value",num);
            arrayList.add(map);
        }

        try {
            File file = new File(filename);
            file.delete();
            System.out.println("==============>删除成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println(arrayList);
    }

    @Test
    void test002() throws IOException {
        String path = System.getProperty("user.dir") + "/src/main/resources/static/backend/adminLTE/js/demo.js";
        System.out.println(path);

        File file = new File(path);
        System.out.println(file.isFile());
        System.out.println(file.getName());

        //FileInputStream fis = new FileInputStream(file);
        //byte[] bs = new byte[1024];
        //int len = 0;
        //while ((len = fis.read(bs))!=-1){
        //    System.out.println(new String(bs,0,len));
        //}
        //
        //fis.close();

        List<String> strings = FileUtil.readLines(file, "utf-8");
        System.out.println(strings.toString());
    }

    @Test
    void test003() throws IOException {
        StringBuilder words = new StringBuilder();
        List<Map<String, Object>> mapList = routeMapper.selectMaps(new QueryWrapper<TRoute>().select("title"));
        for (Map<String, Object> map: mapList) {
            String title = (String) map.get("title");
            words.append(title + " ");
        }

        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> strings = segmenter.sentenceProcess(words.toString());
        System.out.println(strings);
    }

    @Test
    public void testPath() throws FileNotFoundException {
        String travelFiles = ResourceUtils.getURL("travelFiles").getPath() + "files/" + DateUtil.today();
        System.out.println(travelFiles);
    }

}
