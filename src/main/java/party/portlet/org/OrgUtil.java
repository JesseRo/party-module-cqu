package party.portlet.org;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import hg.party.entity.organization.Organization;

import org.springframework.beans.BeanUtils;

import com.liferay.portal.kernel.util.ParamUtil;

public class OrgUtil {
	
	//分组
    public static <R,T>Map<R, T> classify(Collection<T> v, Function<T, R> identifier){
        return v.stream().reduce(new HashMap<R, T>(), (m, t)-> {m.putIfAbsent(identifier.apply(t), t);return m;}, (m,n)->{m.putAll(n); return m;});
    }

    //更新党员
    public static <K, T, R>Map<String, List<T>> upgradeMember(
    		Map<K, List<T>> org, Map<K, List<T>> current, Function<T, R> identifier, Consumer<T> historic){
        Map<String, List<T>> ret = new HashMap<>();
        ret.put("insert", new ArrayList<>());
        ret.put("update", new ArrayList<>());
        ret.put("historic", new ArrayList<>());
        Set<K> orgKeys = org.keySet();
        Set<K> currentKeys = current.keySet();
        Set<K> mergedKeys = new HashSet<>();
        mergedKeys.addAll(currentKeys);
        mergedKeys.addAll(orgKeys);
        for (K k : mergedKeys){
        	List<T> orgValues;
        	List<T> currentValues;
        	if (org.containsKey(k)) {
            	orgValues = org.get(k);
			}else {
				orgValues = new ArrayList<>();
			}
           	if (current.containsKey(k)) {
           		currentValues = current.get(k);
			}else {
				currentValues = new ArrayList<>();
			}
        	
            Map<R, T> orgTable = classify(orgValues, identifier);
            Map<R, T> currentTable = classify(currentValues, identifier);
            Set<R> allKeys = new HashSet<>();
            allKeys.addAll(orgTable.keySet());
            allKeys.addAll(currentTable.keySet());

            allKeys.forEach(p->{
            	if(p.equals("510215198201267118")){
            		p.toString();
            	}
                if (orgTable.containsKey(p) && currentTable.containsKey(p)){
                    if (orgTable.get(p).equals(currentTable.get(p))){

                    }else{
                        BeanUtils.copyProperties(currentTable.get(p), orgTable.get(p));
                        ret.get("update").add(currentTable.get(p));
                    }
                }else if(orgTable.containsKey(p) && !currentTable.containsKey(p)){
                    historic.accept(orgTable.get(p));
                    ret.get("historic").add(orgTable.get(p));
                }else {
                    orgTable.put(p, currentTable.get(p));
                    ret.get("insert").add(currentTable.get(p));
                }
            });
        }
        return ret;
    }
    
    //更新党组织
    public static <K>Map<String, List<Organization>> updateOrg(K k, Map<K, List<Organization>> org,
    		Map<K, List<Organization>> current, Function<Organization, K> identifier, Consumer<Organization> historic) {
        Map<String, List<Organization>> ret = new HashMap<>();
        List<Organization> insert = new ArrayList<>();
        List<Organization> update = new ArrayList<>();
        List<Organization> _historic = new ArrayList<>();
        ret.put("insert", insert);
        ret.put("update", update);
        ret.put("historic", _historic);
        Map<K, Organization> orgT;
        if (!org.containsKey(k)) {
        	orgT = new HashMap<>();
		}else {
	        orgT = classify(org.get(k), identifier);
		}
        Map<K, Organization> currentT;
        if (!current.containsKey(k)) {
        	currentT = new HashMap<>();
  		}else{
  	        currentT = classify(current.get(k), identifier);
  		}
        Set<K> allKeys = new HashSet<>();
        allKeys.addAll(orgT.keySet());
        allKeys.addAll(currentT.keySet());

        final Map<K, Organization> orgTable = orgT;
        final Map<K, Organization> currentTable = currentT;
        allKeys.forEach(p->{
            if (orgTable.containsKey(p) && currentTable.containsKey(p)){
                if (!orgTable.get(p).getOrg_name().equals(currentTable.get(p).getOrg_name())){
                	orgTable.get(p).setOrg_name(currentTable.get(p).getOrg_name());
                	update.add(orgTable.get(p));
                }
            }else if(orgTable.containsKey(p) && !currentTable.containsKey(p)){
                historic.accept(orgTable.get(p));
                _historic.add(orgTable.get(p));
            }else if(!orgTable.containsKey(p) && currentTable.containsKey(p)){
                orgTable.put(p, currentTable.get(p));
                insert.add(currentTable.get(p));
            }
        	Map<String, List<Organization>> m = updateOrg(identifier.apply(orgTable.get(p)), org, current, identifier, historic);
        	insert.addAll(m.get("insert"));
        	update.addAll(m.get("update"));
        	_historic.addAll(m.get("historic"));
        });
        
        return ret;
	}
}
