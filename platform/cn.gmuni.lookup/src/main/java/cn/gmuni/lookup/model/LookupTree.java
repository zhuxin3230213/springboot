package cn.gmuni.lookup.model;

import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询字典树模型
 */
@ApiModel(value = "字典树模型")
public class LookupTree extends Lookup {
  private List<LookupTree> children = new ArrayList<>();

    public List<LookupTree> getChildren() {
        return children;
    }

    public void setChildren(List<LookupTree> children) {
        this.children = children;
    }
}
