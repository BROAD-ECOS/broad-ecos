package br.ufjf.nenc.broadecos.platform.handler;

import br.ufjf.nenc.broadecos.platform.api.ResourceType;
import br.ufjf.nenc.broadecos.platform.model.MatchedResource;
import br.ufjf.nenc.broadecos.platform.model.RequestParams;
import br.ufjf.nenc.broadecos.platform.model.TokenContext;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QueryResourceHandler implements ResourceHandler {


    private final NamedParameterJdbcTemplate jdbc;

    @Autowired
    public QueryResourceHandler(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Boolean handles(ResourceType type) {
        return type != null && type == ResourceType.PLATFORM_QUERY;
    }

    @Override
    public Object handle(MatchedResource resource, TokenContext context, RequestParams params) {

        String query = resource.getResource().getQuery();
        Map<String, String> queryParams = buildParams(resource, context, params);

        List<Map<String, Object>> rows = jdbc.query(query, queryParams, (rs, rowNum) -> extractData(rs));

        return rows;
    }

    private Map<String, String> buildParams(MatchedResource resource, TokenContext context, RequestParams requestParams) {
        ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
        params.putAll(requestParams.getParamsFlat());
        params.putAll(prefixed("url", resource.getUrlParams()));
        params.putAll(prefixed("context", contextParams(context)));
        return params.build();
    }

    private Map<String, Object> extractData(ResultSet rs) throws SQLException {
        final ImmutableMap.Builder<String, Object> row = ImmutableMap.builder();
        final ResultSetMetaData md = rs.getMetaData();
        final int columns = md.getColumnCount();
        for (int i = 1; i <= columns; ++i) {
            row.put(md.getColumnName(i), rs.getObject(i));
        }
        return row.build();
    }

    private Map<String, String> prefixed(String prefix, Map<String, String> params) {
        ImmutableMap.Builder<String, String> flatParams = ImmutableMap.builder();
        params.entrySet().forEach(e -> flatParams.put(String.join("_", prefix, e.getKey()), e.getValue()));
        return flatParams.build();
    }

    private Map<String, String> contextParams(TokenContext context) {
        return new HashMap<>();
    }
}
