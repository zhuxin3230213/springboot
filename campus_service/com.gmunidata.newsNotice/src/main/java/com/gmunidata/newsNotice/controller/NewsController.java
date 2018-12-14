package com.gmunidata.newsNotice.controller;

import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.Content;
import com.gmunidata.newsNotice.mapper.NewsMapper;
import com.gmunidata.newsNotice.model.News;
import com.gmunidata.newsNotice.model.Notice;
import com.gmunidata.newsNotice.service.IClickThroughService;
import com.gmunidata.newsNotice.service.IFileService;
import com.gmunidata.newsNotice.service.INewsService;
import com.gmunidata.newsNotice.service.impl.NewsServiceImp;
import com.gmunidata.newsNotice.service.impl.NoticeServiceImp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "/news", description = "新闻资讯管理控制类")
@RestController
@RequestMapping(value = "news")
public class NewsController {

    @Autowired
    @Qualifier("newsServiceImp")
    INewsService newsServiceImp;

    @Autowired
    NewsMapper newsMapper;

    @Autowired
    @Qualifier("clickThroughServiceImpl")
    IClickThroughService clickThroughService;

    @Autowired
    @Qualifier("fileServiceImpl")
    IFileService iFileService;

    @ApiOperation(value = "新增或者修改新闻资讯")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    public Content addOrUpdate(@RequestBody News news) {
        return newsServiceImp.addOrUpdate(news);
    }

    @ApiOperation(value = "删除新闻资讯")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "所要删除的新闻资讯的id", required = true)
    })
    public Content del(@RequestBody @ApiIgnore Map<String, Object> params) {
        return newsServiceImp.del((String) params.get("ids"));
    }

    @ApiOperation(value = "查询新闻资讯")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "文章标题"),
            @ApiImplicitParam(name = "userName", value = "添加人"),
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "截止时间"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<News> list(@RequestBody @ApiIgnore Map<String, Object> params) {
        return new PageInfo<>(newsServiceImp.list(params));
    }

    @ApiOperation(value = "查询正文")
    @RequestMapping(value = "/selectContent", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "所查的新闻资讯id", required = true)})
    public Content selectContent(@RequestBody @ApiIgnore Map<String, Object> params) {
        String str = newsMapper.selectContent((String) params.get("id"));
        Content con = new Content();
        con.setFlag(0);
        con.setContent(str);
        return con;
    }

    //app
    @ApiOperation(value = "app新闻查询列表")
    @RequestMapping(value = "/listNews")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true)
    })
    public List<Map<String, Object>> listNews(@RequestParam @ApiIgnore Map<String, String> params) {
        return newsServiceImp.listNews(params);
    }

    @ApiOperation(value = "app新闻下拉刷新顶部数据更新")
    @RequestMapping(value = "/addNewsTop")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "initDataFirstUpdateTime", value = "列表最大时间", required = true),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true)
    })
    public List<Map<String, Object>> addNewsTop(@RequestParam @ApiIgnore Map<String, String> params) {
        return newsServiceImp.addNewsTop(params);
    }

    @ApiOperation(value = "app新闻上拉加载数据更新")
    @RequestMapping(value = "/addNewsBottom")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "initDataLastUpdateTime", value = "列表最小时间", required = true),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true)
    })
    public List<Map<String, Object>> addNewsBottom(@RequestParam @ApiIgnore Map<String, String> params) {
        return newsServiceImp.addNewsBottom(params);
    }

    @ApiOperation(value = "app获取新闻对象")
    @RequestMapping(value = "/newsDtetail/{id}")
    @ApiImplicitParam(name = "id", value = "新闻id", required = true)
    public Map<String, Object> newById(@PathVariable("id") String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("articleId", id);
        params.put("type", "0");
        Map<String, Object> map = clickThroughService.updateClickThrough(params);
        Map<String, Object> map1 = newsServiceImp.newById(map.get("article_id").toString());
        if (StringUtils.isEmpty(map1.get("keywords"))) {
            map1.put("keywords", "");
        }
        if (StringUtils.isEmpty(map1.get("description"))) {
            map1.put("description", "");
        }
        return map1;
    }

    @ApiOperation(value = "app获取列表最后一条数据")
    @RequestMapping(value = "/lastNew")
    public Map<String, Object> lastNew() {
        Map<String, String> params = new HashMap<>();
        params.put("status", "1");
        return newsServiceImp.lastNew(params);
    }


    @ApiOperation(value = "发布推送给中转层")
    @RequestMapping(value = "/push", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "所要推送的的新闻资讯id", required = true)})
    public Content push(@RequestBody @ApiIgnore Map<String, Object> params) {
        return null;
    }

    //发布和撤销
    @ApiOperation(value = "发布和撤销")
    @RequestMapping(value = "/relOrRev", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "通知公告的id", required = true),
            @ApiImplicitParam(name = "flag", value = "通知公告的id", required = true)})
    public Content relOrRev(@RequestBody @ApiIgnore Map<String, Object> params) {
        Content con = new Content();
        //修改状态 1:发布 0:撤销
        int f = newsMapper.updateStatus(params) == 1 ? 0 : 1;
        con.setFlag(f);
        if (("1").equals(params.get("flag").toString())) {
            if (f == 0) {
                List<News> news = newsMapper.list(params);
                //查询出来一条则发布，否则不发布
                NewsServiceImp newsServiceImp1 = new NewsServiceImp();
                newsServiceImp1.messagePush(news.get(0));
                con.setMessage("发布成功");
            } else {
                con.setMessage("发布失败");
            }

        } else {
            if (f == 0) {
                con.setMessage("撤销成功！");
            } else {
                con.setMessage("撤销失败！");
            }
        }
        return con;
    }


}
