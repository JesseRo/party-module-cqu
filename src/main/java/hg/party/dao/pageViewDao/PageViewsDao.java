package hg.party.dao.pageViewDao;

import org.osgi.service.component.annotations.Component;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.pageView.Hg_Pageviews_Info;


@Component(immediate = true,service = PageViewsDao.class)
public class PageViewsDao extends PostgresqlDaoImpl<Hg_Pageviews_Info> {
}
