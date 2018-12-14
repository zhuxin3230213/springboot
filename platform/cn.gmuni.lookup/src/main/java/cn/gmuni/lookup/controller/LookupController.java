package cn.gmuni.lookup.controller;

import cn.gmuni.lookup.container.LookupCache;
import cn.gmuni.lookup.model.Lookup;
import cn.gmuni.lookup.model.LookupTree;
import cn.gmuni.lookup.service.ILookupService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lookup")
@Api(value = "数据字典处理")
public class LookupController {

    @Autowired
    @Qualifier(value = "lookupServiceImpl")
    ILookupService lookupService;

    @ApiOperation(value = "获取字典树，不含字典，只包含目录")
    @PostMapping("/listTree")
    @ResponseBody
    public List<LookupTree> listTree() {
        return lookupService.listTree();
    }

    @ApiOperation(value = "获取字典表格")
    @PostMapping("/listAllByPid")
    @ResponseBody
    public PageInfo<Lookup> listAllByPid(@RequestBody Map<String, String> params) {
        return lookupService.listAllByPid(params);
    }


    @ApiOperation(value = "添加数据字典")
    @PostMapping("/add")
    @ResponseBody
    public Lookup add(@RequestBody Lookup lookup) {
        return lookupService.add(lookup);
    }

    @ApiOperation(value = "修改数据字典")
    @PostMapping("/update")
    @ResponseBody
    public Lookup update(@RequestBody Lookup lookup) {
        return lookupService.update(lookup);
    }

    @ApiOperation(value = "删除数据字典")
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody Map<String, String> params) {
        List<String> ids = Arrays.asList(params.get("ids").split(","));
        return lookupService.delete(ids);
    }

    @ApiOperation(value = "校验名称编码是否重复")
    @PostMapping("/checkNameCode")
    @ResponseBody
    public Map<String, Object> checkNameCode(@RequestBody Lookup lookup) {
        return lookupService.checkNameCode(lookup);

    }

    @ApiOperation(value = "更新状态")
    @PostMapping("/updateStatus")
    @ResponseBody
    public Map<String,Object> updateStatus(@RequestBody Map<String,String> params){
        return lookupService.updateStatus(Arrays.asList(params.get("ids").split(",")),params.get("status"));

    }

    @ApiOperation(value = "在页面打开时，加载数据字典信息")
    @PostMapping("/loadAllLookups")
    @ResponseBody
    public List<LookupTree> loadAllLookups(){
        List<LookupTree> trees = LookupCache.list();
        return clearDisLookup(trees);
    }

    /**
     * 清理禁用的数据字典
     * @param trees
     * @return
     */
    private List<LookupTree> clearDisLookup(List<LookupTree> trees){
        List<LookupTree> result = new ArrayList<>();
        for(LookupTree tree : trees){
            if("1".equals(tree.getStatus())){
                LookupTree newTree = new LookupTree();
                BeanUtils.copyProperties(tree,newTree,"children");
                result.add(newTree);
                if(tree.getChildren().size()>0){
                    for(LookupTree t : tree.getChildren()){
                        if("1".equals(t.getStatus())){
                            newTree.getChildren().add(t);
                        }
                    }
                }
            }
        }
        return result;
    }
}
