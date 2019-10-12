package hg.party.server.pageView;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.pageViewDao.PageViewsDao;
import hg.party.entity.pageView.Hg_Pageviews_Info;



@Component(immediate = true,service = PageViewsService.class)
public class PageViewsService {
	@Reference
	private PageViewsDao pw;
	
	//11.3 16:47 zlm pw.addEntity改为了pw.save
	public void addEntity(Hg_Pageviews_Info pageViews){
		pw.save(pageViews);
	}
	
}
