package cn.enncloud.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.enncloud.controller.ServletController;
import cn.enncloud.dao.CompanyDao;
import cn.enncloud.util.DataUtil;
@Repository
public class CompanyDaoImpl implements CompanyDao{
	private static final Logger logger = LoggerFactory.getLogger(ServletController.class);
	private SessionFactory sessionFactory; 
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getCompanySimpleInfoList(Map<String, Object> params, int page, int limit) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT com.*,img.ImgUrl FROM t_company_info com "
				+ "left JOIN (SELECT CompanyID,MAX(ImgUrl) ImgUrl from t_company_img where Type='01' GROUP BY CompanyID ) img on com.CompanyID=img.CompanyID "
				+ "where com.`Status`=1 ");
		String city=(String)params.get("city");
		List<String> sphere = (List<String>)params.get("sphere");
		List<String> grade = (List<String>)params.get("grade");
		List<String> age = (List<String>)params.get("age");
		List<String> county = (List<String>)params.get("county");
		String sphereStr="", gradeStr="";
		if(sphere != null && sphere.size()>0){
			for(String item:sphere){
				if(item.length()<=4){
					sphereStr+="'"+item+"',";
				}else {
					logger.error("查询公司信息参数异常，sphereStr为："+item);
				}
			}
			sphereStr = "".equals(sphereStr)?"":sphereStr.substring(0, sphereStr.length()-1);
		}
		if(grade != null && grade.size()>0){
			for(String item:grade){
				if(item.length()<=4){
					gradeStr+="'"+item+"',";
				}else {
					logger.error("查询公司信息参数异常，gradeStr为："+item);
				}
			}
			gradeStr = "".equals(gradeStr)?"":gradeStr.substring(0, gradeStr.length()-1);
		}
		if(!DataUtil.IsNull(city)){
			sb.append(" and com.City =:City ");
		}
		if(!"".equals(sphereStr) && !"".equals(gradeStr)){
			sb.append(" and com.CompanyID in ("
					+ "select distinct CompanyID from t_company_sphere where SphereID in ("+sphereStr+") "
					+ "UNION "
					+ "SELECT distinct CompanyID  from t_company_grade where GradeID in("+gradeStr+") "
					+ ")");
		}else if(!"".equals(sphereStr)){
			sb.append(" and com.CompanyID in ("
					+ "select distinct CompanyID from t_company_sphere where SphereID in ("+sphereStr+") "
					+ ")");
		}else if(!"".equals(gradeStr)){
			sb.append(" and com.CompanyID in ("
					+ "SELECT distinct CompanyID  from t_company_grade where GradeID in("+gradeStr+") "
					+ ")");
		}
		if(age!=null &&age.size()>0 ){
			String agestr = " and ( ";
			for(String item:age){
				if(item.length()<=3){
					agestr+=" (com.BeginAge<="+item+" and com.EndAge>="+item+") or";
				}else {
					logger.error("查询公司信息参数异常，年龄为："+item);
				}
			}
			sb.append(agestr.substring(0, agestr.length()-2)+") ");
		}
		if(county!=null &&county.size()>0 ){
			String countystr = " and com.County in ( ";
			for(String item :county){
				if(item.length()<=3){
					countystr+="'"+item+"',";
				}else {
					logger.error("查询公司信息参数异常，区县为："+item);
				}
			}
			sb.append(countystr.substring(0, countystr.length()-1)+")");
		}
		if(!DataUtil.IsNull(params.get("search"))){
			sb.append(" and (com.SimpleName like :search or com.FullName like :search "
					+ "or com.Provence like :search or com.City like :search "
					+ "or com.County like :search or com.Address like :search ) ");
		}
		String order="";
		if("view".equals(params.get("ageViewOrder"))){//优先点击量排序
			if("0".equals(params.get("viewOrder")) ){//点击量升序
				order= " order by com.VisitNum asc ";
			}else if("1".equals(params.get("viewOrder")) ){
				order= " order by com.VisitNum desc ";
			}
			if("0".equals(params.get("ageOrder")) ){//年龄升序
				if(order==""){
					order= " order by com.BeginAge asc ";
				}else {
					order+= " ,com.BeginAge asc ";
				}
			}else if("1".equals(params.get("ageOrder")) ){
				if(order==""){
					order= " order by com.BeginAge desc ";
				}else {
					order+= " ,com.BeginAge desc ";
				}
			}
		}else{
			if("0".equals(params.get("ageOrder")) ){//年龄升序
				order= " order by com.BeginAge asc ";
			}else if("1".equals(params.get("ageOrder")) ){
				order= " order by com.BeginAge desc ";
			}
			if("0".equals(params.get("viewOrder")) ){//点击量升序
				if(order==""){
					order= " order by com.VisitNum asc ";
				}else {
					order+= " ,com.VisitNum asc ";
				}
			}else if("1".equals(params.get("viewOrder")) ){
				if(order==""){
					order= " order by com.VisitNum desc ";
				}else {
					order+= " ,com.VisitNum desc ";
				}
			}
		}
		
		sb.append(order);
		sb.append(" LIMIT :sl,:line ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString())
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(!DataUtil.IsNull(city)){
			query.setString("City", city);
		}
		if(!DataUtil.IsNull(params.get("search"))){
			query.setString("search", "%"+params.get("search")+"%");
		}
		query.setInteger("sl", (page-1)*limit);
		query.setInteger("line", limit);
		return query.list();
	}

	@Override
	public Map<String, Object> getCompanyCompleteInfoByID(String companyid) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sqlcom = "SELECT com.*  FROM t_company_info com where com.CompanyID=:CompanyID ";
		String sqlrich="select * from t_company_rich_detail where `Status`='1' and CompanyID=:CompanyID ";
		String sqlimg = "select * from t_company_img where CompanyID=:CompanyID ";
		String sqlgrade = "select s.* from t_company_grade g join t_select_info s on g.GradeID=s.ID where g.CompanyID=:CompanyID ";
		String sqlsphere = "select s.* from t_company_sphere g join t_select_info s on g.SphereID=s.ID where g.CompanyID=:CompanyID ";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlcom)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("CompanyID", companyid);
		map.put("company", query.list());
		query = sessionFactory.getCurrentSession().createSQLQuery(sqlrich)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("CompanyID", companyid);
		map.put("rich", query.list());
		query = sessionFactory.getCurrentSession().createSQLQuery(sqlimg)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("CompanyID", companyid);
		map.put("img", query.list());
		query = sessionFactory.getCurrentSession().createSQLQuery(sqlgrade)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("CompanyID", companyid);
		map.put("grade", query.list());
		query = sessionFactory.getCurrentSession().createSQLQuery(sqlsphere)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("CompanyID", companyid);
		map.put("sphere", query.list());
		return map;
	}

}
