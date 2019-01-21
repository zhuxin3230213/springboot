package cn.gmuni.sc.pay.service.impl;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.pay.cache.NetPackageCache;
import cn.gmuni.sc.pay.mapper.NetPackageMapper;
import cn.gmuni.sc.pay.model.NetPackage;
import cn.gmuni.sc.pay.service.INetPackageService;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class NetPackageServiceImpl implements INetPackageService {

    @Autowired
    NetPackageMapper mapper;

    @Override
    public List<NetPackage> getNetPackage(Map<String,Object> params) {
        return mapper.getNetPackage(params);
    }

    @Override
    public Content addNetPackage(NetPackage netPackage) {
        Content con = new Content();
        int f = mapper.checkNetPackage(netPackage);
        if (f!=0){
            con.setFlag(1);
            con.setMessage("编码重复！");
            return con;
        }
        netPackage.setId(IdGenerator.getId());
        netPackage.setCreateTime(new Date());
        f = mapper.addNetPackage(netPackage) == 1 ? 0 : 1;
        con.setFlag(f);
        if (f == 0){
            NetPackageCache.add(netPackage);
            con.setMessage("添加成功！");
            con.setContent(netPackage);
        }else {
            con.setMessage("添加失败！");
        }
        return con;
    }

    @Override
    public Content updateNetPackage(NetPackage netPackage) {
        Content con = new Content();
        int f = mapper.checkNetPackage(netPackage);
        if (f!=0){
            con.setFlag(1);
            con.setMessage("编码重复！");
            return con;
        }
        f = mapper.updateNetPackage(netPackage) == 1 ? 0 : 1;
        con.setFlag(f);
        if (f == 0){
            NetPackageCache.update(netPackage);
            con.setMessage("修改成功！");
            con.setContent(netPackage);
        }else {
            con.setMessage("修改失败！");
        }
        return con;
    }

    @Override
    public Content delNetPackage(List<String> ids) {
        Content con = new Content();
        int f = mapper.delNetPackage(ids) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0){
            NetPackageCache.del(ids);
            con.setMessage("删除成功！");
        }else {
            con.setMessage("删除失败！");
        }
        return con;
    }
}
