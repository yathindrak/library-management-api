import play.api.mvc.EssentialFilter;
import play.filters.cors.CORSFilter;
import play.http.HttpFilters;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class Filters implements HttpFilters {

    @Inject
    CORSFilter corsFilter;

    public play.mvc.EssentialFilter[] filters() {
        return (play.mvc.EssentialFilter[]) new EssentialFilter[] { corsFilter };
    }

    @Override
    public List<play.mvc.EssentialFilter> getFilters() {
        return Arrays.asList((play.mvc.EssentialFilter[]) new EssentialFilter[]{corsFilter});
    }
}