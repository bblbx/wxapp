package cn.enncloud.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.enncloud.dao.CompanyDao;
@Repository
public class CompanyDaoImpl implements CompanyDao{
	
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
		List<String> sphere = (List<String>)params.get("sphere");
		List<String> grade = (List<String>)params.get("grade");
		String sphereStr="", gradeStr="";
		if(sphere != null && sphere.size()>0){
			for(String item:sphere){
				sphereStr+="'"+item+"',";
			}
			sphereStr = "".equals(sphereStr)?"":sphereStr.substring(0, sphereStr.length()-1);
		}
		if(grade != null && grade.size()>0){
			for(String item:grade){
				gradeStr+="'"+item+"',";
			}
			gradeStr = "".equals(gradeStr)?"":gradeStr.substring(0, gradeStr.length()-1);
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
		sb.append(" LIMIT :sl,:line ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString())
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
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
