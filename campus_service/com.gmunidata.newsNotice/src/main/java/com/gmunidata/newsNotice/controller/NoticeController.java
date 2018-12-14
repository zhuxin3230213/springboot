package com.gmunidata.newsNotice.controller;

import cn.gmuni.upload.model.Upload;
import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.BaseResponse;
import com.gmunidata.base.response.Content;
import com.gmunidata.newsNotice.mapper.NewsMapper;
import com.gmunidata.newsNotice.mapper.NoticeMapper;
import com.gmunidata.newsNotice.model.Notice;
import com.gmunidata.newsNotice.service.IClickThroughService;
import com.gmunidata.newsNotice.service.IFileService;
import com.gmunidata.newsNotice.service.INoticeService;
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

import java.util.*;

@Api(value = "/notice", description = "通知公告管理控制类")
@RestController
@RequestMapping(value = "notice")
public class NoticeController {


    @Autowired
    @Qualifier("noticeServiceImp")
    INoticeService noticeServiceImp;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    @Qualifier("clickThroughServiceImpl")
    IClickThroughService clickThroughService;

    @Autowired
    @Qualifier("fileServiceImpl")
    IFileService iFileService;

    @ApiOperation(value = "新增或者修改通知公告")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    public Content addOrUpdate(@RequestBody Notice notice) {
        return noticeServiceImp.addOrUpdate(notice);
    }

    @ApiOperation(value = "删除通知公告")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "所要删除的通知公告的id", required = true)
    })
    public Content del(@RequestBody @ApiIgnore Map<String, Object> params) {
        return noticeServiceImp.del((String) params.get("ids"));
    }

    @ApiOperation(value = "查询通知公告")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "文章标题"),
            @ApiImplicitParam(name = "userName", value = "添加人"),
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "截止时间"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<Notice> list(@RequestBody @ApiIgnore Map<String, Object> params) {
        return new PageInfo<>(noticeServiceImp.list(params));
    }

    @ApiOperation(value = "查询正文")
    @RequestMapping(value = "/selectContent", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "所查的通知公告的id", required = true)})
    public Content selectContent(@RequestBody @ApiIgnore Map<String, Object> params) {
        Content con = new Content();
        String str = noticeMapper.selectContent((String) params.get("id"));
        con.setFlag(0);
        con.setContent(str);
        return con;
    }

    //app
    @ApiOperation(value = "app通知公告查询列表")
    @RequestMapping(value = "/listNotice")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true)
    })
    public List<Map<String, Object>> listNotice(@RequestParam @ApiIgnore Map<String, String> params) {
        return noticeServiceImp.listNotice(params);
    }

    @ApiOperation(value = "app通知下拉刷新顶部数据更新")
    @RequestMapping(value = "/addNoticeTop")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "initDataFirstUpdateTime", value = "列表最大时间", required = true),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true)
    })
    public List<Map<String, Object>> addNewsTop(@RequestParam @ApiIgnore Map<String, String> params) {
        return noticeServiceImp.addNoticeTop(params);
    }

    @ApiOperation(value = "app通知上拉加载数据更新")
    @RequestMapping(value = "/addNoticeBottom")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "initDataLastUpdateTime", value = "列表最小时间", required = true),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true)
    })
    public List<Map<String, Object>> addNewsBottom(@RequestParam @ApiIgnore Map<String, String> params) {
        return noticeServiceImp.addNoticeBottom(params);
    }

    @ApiOperation(value = "app获取通知对象")
    @RequestMapping(value = "/noticeDetail/{id}")
    @ApiImplicitParam(name = "id", value = "通知id", required = true)
    public Map<String, Object> newById(@PathVariable("id") String id) {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        params.put("articleId", id);
        params.put("type", "1");
        Map<String, Object> map = clickThroughService.updateClickThrough(params);
        map1 = noticeServiceImp.noticeById(map.get("article_id").toString());
        if (StringUtils.isEmpty(map1.get("attachment"))) {
            List<Upload> uploads = new ArrayList<>();
            map1.put("attachment", uploads);
        } else {
            List<String> ids = Arrays.asList(map1.get("attachment").toString().split(","));
            List<Upload> uploads = iFileService.listUploadByIds(ids);
            map1.put("attachment", uploads);
        }
        if (StringUtils.isEmpty(map1.get("keywords"))) {
            map1.put("keywords", "");
        }
        if (StringUtils.isEmpty(map1.get("description"))) {
            map1.put("description", "");
        }
        return map1;
    }

    @ApiOperation(value = "app获取列表最后一条数据")
    @RequestMapping(value = "/lastNotice")
    public Map<String, Object> lastNew() {
        Map<String, String> params = new HashMap<>();
        params.put("status", "1");
        return noticeServiceImp.lastNotice(params);
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
        int f = noticeMapper.updateStatus(params) == 1 ? 0 : 1;
        con.setFlag(f);
        if (("1").equals(params.get("flag").toString())) {
            if (f == 0) {
                List<Notice> notices = noticeMapper.list(params);
                //查询出来一条则发布，否则不发布
                NoticeServiceImp noticeServiceImp1 = new NoticeServiceImp();
                noticeServiceImp1.messagePush(notices.get(0));
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
