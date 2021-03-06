package com.langnatech.ipms.service;

import java.util.List;
import java.util.Map;
import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.ipms.bean.SubNetResBean;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.entity.SubNetResEntity;

public interface SubNetResService {

	 public List<SubNetResBean> getAllSubNet(String subnetPid, Integer planStatus);

	    public PageList<SubNetResBean> getAssignSubnetByPoolId(String poolId, PageQuery pageQuery);

	    public PageList<SubNetResBean> getAllSubNet(String subnetPid, PageQuery pageQuery);

	    public PageList<Map<String, Object>> getSubNetsBySelfQuery(String[] poolIds, SubNetResEntity subNetRes, IPArchiveInfoEntity archiveInfo, PageQuery page);

	    public SubNetResBean selectSubnetById(String subnetId);
	    
	    public List<SubNetResEntity> getParentSubNetsBySubId(String subnetId);

	    public boolean splitSubnet(SubNetResEntity[] entitys);

	    public boolean planSubnet(SubNetResEntity entity);

	    public boolean deleteSubnetById(String subnetId);

	    public boolean mergeSubnetById(String subnetId);

	    public boolean isExistSubnet(String desc);

	    public boolean insertSubNet(String ip, int maskbits, String area, String pool);

	    public boolean updateSubnetUseStatus(String subnetId,Integer status);
}
