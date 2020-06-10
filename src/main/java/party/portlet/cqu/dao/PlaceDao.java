package party.portlet.cqu.dao;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.StringUtils;
import party.portlet.cqu.entity.CheckPerson;
import party.portlet.cqu.entity.Place;

import java.util.List;
import java.util.Map;

@Component(immediate = true,service = PlaceDao.class)
public class PlaceDao extends PostgresqlDaoImpl<Place> {

    public List<Place> getPlace(String orgId, String campus) {
        String sql = "select * from hg_party_place where org_id = ? and campus = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Place.class), orgId, campus);
    }

    public Place findByPlaceId(String placeId) {
        String sql = "select * from hg_party_place where place_id = ? ";
        List<Place> list =  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Place.class), placeId);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
    public Place findById(int id) {
        String sql = "select * from hg_party_place where id = ? ";
        List<Place> list =  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Place.class), id);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
