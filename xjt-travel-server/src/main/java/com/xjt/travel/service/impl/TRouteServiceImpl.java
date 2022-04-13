package com.xjt.travel.service.impl;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.extra.tokenizer.engine.word.WordWord;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjt.travel.domain.TRoute;
import com.xjt.travel.domain.TRouteImg;
import com.xjt.travel.mapper.TRouteImgMapper;
import com.xjt.travel.mapper.TRouteMapper;
import com.xjt.travel.service.TRouteService;
import com.xjt.travel.utils.RespBean;
import org.apdplat.word.WordFrequencyStatistics;
import org.apdplat.word.dictionary.DictionaryFactory;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.util.WordConfTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TRouteServiceImpl implements TRouteService {
    @Autowired
    private TRouteMapper tRouteMapper;

    @Autowired
    private TRouteImgMapper tRouteImgMapper;

    @Override
    public RespBean getRouteListByPage(String currentPage, String pageSize, String category_id, String route_name) {
        Page<TRoute> page = new Page<TRoute>(Integer.parseInt(currentPage), Integer.parseInt(pageSize));
        QueryWrapper<TRoute> wrapper = new QueryWrapper<>();

        if(StringUtils.hasText(category_id)){
            wrapper.eq("category_id",category_id);
        }
        if(StringUtils.hasText(route_name)){
            wrapper.like("title",route_name);
        }
        tRouteMapper.selectPage(page, wrapper);

        List<TRoute> routeList = page.getRecords();
        if(ObjectUtils.isEmpty(routeList)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",page);
        }
    }

    @Override
    public RespBean getRouteById(String id) {
        Map<String,String> map = tRouteMapper.getRouteDetailById(id);

        if(ObjectUtils.isEmpty(map)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",map);
        }
    }

    @Override
    public RespBean getRouteImgsById(String id) {
        QueryWrapper<TRouteImg> wrapper = new QueryWrapper<>();
        wrapper.eq("route_id",id);
        List<TRouteImg> routeImgList = tRouteImgMapper.selectList(wrapper);

        if(ObjectUtils.isEmpty(routeImgList)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",routeImgList);
        }
    }

    @Override
    public RespBean getAllRouteBySellerId(String id) {
        QueryWrapper<TRoute> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id",id);
        List<TRoute> routeList = tRouteMapper.selectList(wrapper);

        if(ObjectUtils.isEmpty(routeList)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",routeList);
        }
    }

    @Override
    public RespBean queryRoutesByConditions(String title, String flag, String category_id, String lowel_price, String high_price, String currentPage, String pageSize) {
        Page<TRoute> page = new Page<TRoute>(Integer.parseInt(currentPage), Integer.parseInt(pageSize));

        QueryWrapper<TRoute> wrapper = new QueryWrapper<>();
        if(StringUtils.hasText(title) && !ObjectUtils.isEmpty(title)){
            wrapper.like("title",title);
        }

        if(StringUtils.hasText(flag) && !ObjectUtils.isEmpty(flag)){
            wrapper.eq("flag",flag);
        }
        if(!category_id.equals("所有") && StringUtils.hasText(category_id) && !ObjectUtils.isEmpty(category_id)){
            wrapper.like("category_id",category_id);
        }
        if(StringUtils.hasText(lowel_price) && !ObjectUtils.isEmpty(lowel_price)){
            wrapper.ge("lowel_price",lowel_price);
        }
        if(StringUtils.hasText(high_price) && !ObjectUtils.isEmpty(high_price)){
            wrapper.le("high_price",high_price);
        }

        tRouteMapper.selectPage(page, wrapper);

        if(ObjectUtils.isEmpty(page)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",page);
        }
    }

    @Override
    public RespBean deleteRouteById(String id) {
        int i = tRouteMapper.deleteById(id);
        if(i<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean toggleRouteFlagById(String id) {
        TRoute tRoute = tRouteMapper.selectById(id);
        tRoute.setFlag(!tRoute.getFlag());
        int update = tRouteMapper.updateById(tRoute);
        if(update<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean generateRouteWordCloud() {
        final String filename = System.getProperty("user.dir") + File.separator + "word-frequency-statistics.txt";

        StringBuilder words = new StringBuilder();
        List<Map<String, Object>> mapList = tRouteMapper.selectMaps(new QueryWrapper<TRoute>().select("title"));
        for (Map<String, Object> map: mapList) {
            String title = (String) map.get("title");
            words.append(title + " ");
        }

        //词频统计设置
        WordFrequencyStatistics wordFrequencyStatistics = new WordFrequencyStatistics();
        wordFrequencyStatistics.setRemoveStopWord(false);
        wordFrequencyStatistics.setResultPath(filename);
        //wordFrequencyStatistics.setSegmentationAlgorithm(SegmentationAlgorithm.MaxNgramScore);
        //自定义停用词库
        System.out.println("===============>");
        System.out.println(this.getClass().getResource("").getPath());
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
            String num = line.split(" ")[1];
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

        if(ObjectUtils.isEmpty(arrayList)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",arrayList);
        }
    }

    @Override
    public RespBean getAllRouteNum() {
        int count = tRouteMapper.selectCount(null);
        if(count<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",count);
        }
    }

    @Override
    public RespBean addRoute(HashMap<String, String> params) {
        //{seller=1326, flag=, price=1760, route_photo=https://sales.mafengwo.net/mfs/s13/M00/85/BA/wKgEaVy0KQGAIl00AApkg4Gzzho78.jpeg, https://sales.mafengwo.net/mfs/s15/M00/38/22/CoUBGV4Wv6iATkcDACXkqVGEZ6c691.png, description=云南5日私人全景休闲游，享受云南美食美景 陶醉于自然风光, location=云南, title=云南5日私人全景休闲定制游（深度体验云南美食美景+独立用车全程陪同+热门网红景点一键打卡+洱海吉普车旅拍留下美好记忆+少数民族篝火晚会体验+两山登顶苍山和雪山）, routeCategory=2}
        String title = params.get("title");
        String route_photo = params.get("route_photo");
        String price = params.get("price");
        Boolean flag = params.get("flag") == "true" ? true:false;
        String seller = params.get("seller");
        String routeCategory = params.get("routeCategory");
        String description = params.get("description");
        String location = params.get("location");
        TRoute route = new TRoute().setTitle(title).setRoutePhoto(route_photo).setPrice(Integer.parseInt(price))
                .setFlag(flag).setSellerId(Integer.parseInt(seller)).setCategoryId(Integer.parseInt(routeCategory)).setIntroduction(description).setLocation(location);
        int insert = tRouteMapper.insert(route);

        if(insert<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean getNumByCategory() {
        List<Map> routeNumList = tRouteMapper.selectNumGroupByCategory();

        return RespBean.success("ok",routeNumList);

    }

    @Override
    public RespBean getNumBySeller() {
        List<Map> routeNumList = tRouteMapper.selectNumGroupBySeller();

        return RespBean.success("ok",routeNumList);


    }
}
